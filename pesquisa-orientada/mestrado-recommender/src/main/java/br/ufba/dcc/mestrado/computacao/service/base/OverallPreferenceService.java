package br.ufba.dcc.mestrado.computacao.service.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface OverallPreferenceService extends BaseOhLohService<Long, PreferenceEntity> , Serializable {

	/**
	 * Conta quantos usu�rios opinaram sobre um determinado projeto
	 * @param project Projeto sobre os quais os usu�rios opinaram
	 * @return
	 */
	Long countAllByProject(OhLohProjectEntity project);
	
	/**
	 * Retorna a m�dia dos valores gerais das avalia��es dos usu�rios sobre um
	 * determinado projeto
	 * @param project
	 * @return
	 */
	Double averagePreferenceByProject(OhLohProjectEntity project);
	
	/**
	 * Conta quantas avalia��es existem para um determinado usu�rio e projeto.
	 */
	Long countAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
	/**
	 * Retorna todas as avalia��es para um dado projeto
	 */
	List<PreferenceEntity> findAllByProject(OhLohProjectEntity project);
	
	/**
	 * Retorna todas as avalia��es para um dado projeto e usu�rio
	 */
	List<PreferenceEntity> findAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
}
