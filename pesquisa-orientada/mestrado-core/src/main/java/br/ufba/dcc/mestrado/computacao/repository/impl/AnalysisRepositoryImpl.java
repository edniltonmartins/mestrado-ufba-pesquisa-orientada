package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisRepository;

@Repository(AnalysisRepositoryImpl.BEAN_NAME)
public class AnalysisRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubAnalysisEntity>
		implements AnalysisRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "analysisRepository";

	public AnalysisRepositoryImpl() {
		super(OpenHubAnalysisEntity.class);
	}
	
	@Override
	public OpenHubAnalysisEntity findById(Long id) {
		// TODO Auto-generated method stub
		OpenHubAnalysisEntity result = super.findById(id);
		if (result != null) {
			if (result.getAnalysisLanguages() != null) {
				result.getAnalysisLanguages().getContent();
			}
		}
		
		return result;
	}
	
}
