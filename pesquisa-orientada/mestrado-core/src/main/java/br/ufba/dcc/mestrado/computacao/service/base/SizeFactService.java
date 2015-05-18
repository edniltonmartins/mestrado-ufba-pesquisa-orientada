package br.ufba.dcc.mestrado.computacao.service.base;

import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.sizefact.OpenHubSizeFactEntity;

public interface SizeFactService extends BaseService<Long, OpenHubSizeFactEntity>{

	
	/**
	 * 
	 * @param project
	 * @return
	 */
	Long countAllByProject(OpenHubProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	List<OpenHubSizeFactEntity> findByProject(OpenHubProjectEntity project);
	
}
