
package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.evaluation.RecommendationEvaluationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.RecommendationEvaluationService;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.base.UserRecommendationService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name = "relatedProjectMB")
@ViewScoped
@URLMappings(mappings={
		@URLMapping(
				id="relatedProjectMapping",
				beanName="relatedProjectMB", 
				pattern="/detail/#{ /[0-9]+/ projectId}/relatedProject",
				viewId="/detail/relatedProjectList.jsf")	
})
public class RelatedProjectManagedBean extends RecommendedProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722099582447182349L;
	
	private static final Integer SAMPLE_MAX_RESULTS = 6;
	private static final Integer TOP10_MAX_RESULTS = 10;

	


	@ManagedProperty("#{searchService}")
	private SearchService searchService;
	
	@ManagedProperty("#{mahoutColaborativeFilteringService}")
	private ColaborativeFilteringService colaborativeFilteringService;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{userRecommendationService}")
	private UserRecommendationService userRecommendationService;
	
	
	private UserRecommendationEntity alsoViewedProjectsRecommendation;
	private UserRecommendationEntity similarProjectsRecommendation;
	

	private Integer maxResults;

	public RelatedProjectManagedBean() {
		super();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}
	
	public void configureTopTenResults(ComponentSystemEvent event) {
		this.maxResults = TOP10_MAX_RESULTS;
	}
	
	public void enableSimilarProjectsRecommendation(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			configureSimilarProjectsRecommendation();
		}
	}

	public void enableAlsoViewedProjectsRecommendation(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			try {
				configureAlsoViewedProjectsRecommendation();
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	protected RecommendationEvaluationEntity buildRecommendationEvaluation(Long selectedProjectId) {
		RecommendationEvaluationEntity recommendationEvaluation = null;
		
		UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		
		if (similarProjectsRecommendation != null 
				&& similarProjectsRecommendation.getRecommendedProjects() != null) {
			for (OpenHubProjectEntity project : similarProjectsRecommendation.getRecommendedProjects()) {
				if (project.getId().equals(selectedProjectId)) {
					recommendationEvaluation = new RecommendationEvaluationEntity();
					recommendationEvaluation.setSelectedProject(project);
					recommendationEvaluation.setUserRecommendation(similarProjectsRecommendation);
					recommendationEvaluation.getUserRecommendation().setUser(currentUser);
					recommendationEvaluation.setEvaluationDate(new Timestamp(System.currentTimeMillis()));
					break;
				}
			}
		}
		
		if (recommendationEvaluation == null
				&& alsoViewedProjectsRecommendation != null 
				&& alsoViewedProjectsRecommendation.getRecommendedProjects() != null) {
			
			for (OpenHubProjectEntity project : alsoViewedProjectsRecommendation.getRecommendedProjects()) {
				if (project.getId().equals(selectedProjectId)) {
					recommendationEvaluation = new RecommendationEvaluationEntity();
					recommendationEvaluation.setSelectedProject(project);
					recommendationEvaluation.setUserRecommendation(alsoViewedProjectsRecommendation);
					recommendationEvaluation.getUserRecommendation().setUser(currentUser);
					recommendationEvaluation.setEvaluationDate(new Timestamp(System.currentTimeMillis()));
					break;
				}
			}			
		}
		
		return recommendationEvaluation;
	}
	
	protected void configureAlsoViewedProjectsRecommendation() throws TasteException {
		alsoViewedProjectsRecommendation = new UserRecommendationEntity();	
		
		List<RecommendedItem> recommendedItems = getColaborativeFilteringService().recommendViewedProjectsByItem(getProject().getId(), getMaxResults());
		List<OpenHubProjectEntity> recommendationList = getColaborativeFilteringService().getRecommendedProjects(recommendedItems);
		
		UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
		Timestamp recommendationDate = new Timestamp(System.currentTimeMillis());
		String viewUri = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		
		alsoViewedProjectsRecommendation.setRecommendationType(br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum.COLABORATIVE_FILTERING_ITEM_BASED_RECOMMENDATION);
		alsoViewedProjectsRecommendation.setRecommendedProjects(recommendationList);
		alsoViewedProjectsRecommendation.setUser(currentUser);
		alsoViewedProjectsRecommendation.setRecommendationDate(recommendationDate);	
		alsoViewedProjectsRecommendation.setViewUri(viewUri);
		
		try {
			alsoViewedProjectsRecommendation = getUserRecommendationService().save(alsoViewedProjectsRecommendation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Utiliza busca por conte�do pra recomendar projetos similares.
	 */
	protected void configureSimilarProjectsRecommendation() {
		try {
			
			similarProjectsRecommendation = new UserRecommendationEntity();
			
			List <OpenHubProjectEntity> recommendationList = getSearchService().findRelatedProjects(getProject(), 0, getMaxResults());
			UserEntity currentUser = getUserDetailsService().loadFullLoggedUser();
			Timestamp recommendationDate = new Timestamp(System.currentTimeMillis());
			String viewUri = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			
			similarProjectsRecommendation.setRecommendationType(RecommendationTypeEnum.CONTENT_BASED_RECOMMENDATION);
			similarProjectsRecommendation.setRecommendedProjects(recommendationList);
			similarProjectsRecommendation.setUser(currentUser);
			similarProjectsRecommendation.setRecommendationDate(recommendationDate);
			similarProjectsRecommendation.setViewUri(viewUri);
						
			try {
				similarProjectsRecommendation = getUserRecommendationService().save(similarProjectsRecommendation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public ColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			ColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
	}
	
	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public UserRecommendationService getUserRecommendationService() {
		return userRecommendationService;
	}

	public void setUserRecommendationService(
			UserRecommendationService userRecommendationService) {
		this.userRecommendationService = userRecommendationService;
	}

	public UserRecommendationEntity getAlsoViewedProjectsRecommendation() {
		return alsoViewedProjectsRecommendation;
	}

	public void setAlsoViewedProjectsRecommendation(
			UserRecommendationEntity alsoViewedProjectsRecommendation) {
		this.alsoViewedProjectsRecommendation = alsoViewedProjectsRecommendation;
	}

	public UserRecommendationEntity getSimilarProjectsRecommendation() {
		return similarProjectsRecommendation;
	}

	public void setSimilarProjectsRecommendation(
			UserRecommendationEntity similarProjectsRecommendation) {
		this.similarProjectsRecommendation = similarProjectsRecommendation;
	}

	
	
}

