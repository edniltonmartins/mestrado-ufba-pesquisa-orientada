package br.ufba.dcc.mestrado.computacao.repository.impl;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisRepository;

@Repository(OhLohAnalysisRepositoryImpl.BEAN_NAME)
public class OhLohAnalysisRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAnalysisEntity>
		implements OhLohAnalysisRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohAnalysisRepository";

	public OhLohAnalysisRepositoryImpl() {
		super(OhLohAnalysisEntity.class);
	}
	
	@Override
	public OhLohAnalysisEntity findById(Long id) {
		// TODO Auto-generated method stub
		OhLohAnalysisEntity result = super.findById(id);
		if (result != null) {
			if (result.getAnalysisLanguages() != null) {
				result.getAnalysisLanguages().getContent();
			}
		}
		
		return result;
	}
	
}
