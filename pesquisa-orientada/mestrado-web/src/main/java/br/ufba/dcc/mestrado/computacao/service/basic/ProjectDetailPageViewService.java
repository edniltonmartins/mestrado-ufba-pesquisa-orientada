package br.ufba.dcc.mestrado.computacao.service.basic;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
import br.ufba.dcc.mestrado.computacao.service.base.BaseService;

public interface ProjectDetailPageViewService extends BaseService<Long, ProjectDetailPageViewEntity>{

	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo();
	List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(Integer startAt, Integer offset);
	
	List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			Integer startAt, 
			Integer offset);
	
	
	/**
	 * Cria um recomendador do tipo "Slope One".
	 * 
	 * Avalia usu�rios que visualizaram os mesmos �tens que o usu�rio corrente, e ent�o 
	 * pode recomendar itens que o usu�rio corrente ainda n�o viu.
	 * 
	 * @return
	 * @throws TasteException
	 */
	GenericBooleanPrefItemBasedRecommender buildProjectRecommender() throws TasteException;
	
}
