
package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.recommender.base.ColaborativeFilteringService;

@ManagedBean(name="alsoViewedProjectMB")
@ViewScoped
public class AlsoViewedProjectManagedBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -722099582447182349L;
	
	private static final Integer SAMPLE_MAX_RESULTS = 6;
	private static final Integer TOP10_MAX_RESULTS = 10;
	
	
	private Integer maxResults;
	
	@ManagedProperty("#{mahoutColaborativeFilteringService}")
	private ColaborativeFilteringService colaborativeFilteringService;
	
	private List<OpenHubProjectEntity> alsoViewedProjectList;
	
	public AlsoViewedProjectManagedBean() {
		super();
		this.maxResults = SAMPLE_MAX_RESULTS;
	}

	public ColaborativeFilteringService getColaborativeFilteringService() {
		return colaborativeFilteringService;
	}

	public void setColaborativeFilteringService(
			ColaborativeFilteringService colaborativeFilteringService) {
		this.colaborativeFilteringService = colaborativeFilteringService;
	}



	public void configureTopTenResults(ComponentSystemEvent event) {
		this.maxResults = TOP10_MAX_RESULTS;
	}
	
	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getProject() != null && getProject().getId() != null) {
			try {
				findAlsoViewedProjectList();
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Recomenda outros projetos vistos pelos mesmos usu�rios que viram o projeto corrente
	 * @throws TasteException 
	 */
	protected void findAlsoViewedProjectList() throws TasteException {
		//limpa lista atual de projetos recomendados
		List<RecommendedItem> recommendedItems = getColaborativeFilteringService().recommendViewedProjectsByItem(getProject().getId(), getMaxResults());
		this.alsoViewedProjectList = getColaborativeFilteringService().getRecommendedProjects(recommendedItems);
	}


	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	
	public List<OpenHubProjectEntity> getAlsoViewedProjectList() {
		return alsoViewedProjectList;
	}

	public void setAlsoViewedProjectList(
			List<OpenHubProjectEntity> alsoViewedProjectList) {
		this.alsoViewedProjectList = alsoViewedProjectList;
	}

}

