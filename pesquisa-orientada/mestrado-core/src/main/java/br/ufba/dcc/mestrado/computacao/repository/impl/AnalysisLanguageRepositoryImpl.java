package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisLanguageRepository;

@Repository(AnalysisLanguageRepositoryImpl.BEAN_NAME)
public class AnalysisLanguageRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubAnalysisLanguageEntity>
		implements AnalysisLanguageRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "analysisLanguageRepository";

	public AnalysisLanguageRepositoryImpl() {
		super(OpenHubAnalysisLanguageEntity.class);
	}

	
}
