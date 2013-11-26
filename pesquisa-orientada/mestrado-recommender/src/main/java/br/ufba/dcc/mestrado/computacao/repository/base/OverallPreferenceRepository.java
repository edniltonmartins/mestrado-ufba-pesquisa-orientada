package br.ufba.dcc.mestrado.computacao.repository.base;

import java.io.Serializable;
import java.util.List;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

/**
 * 
 * @author leandro.ferreira
 *
 */
public interface OverallPreferenceRepository extends BaseRepository<Long, PreferenceEntity>, Serializable {

	/**
	 * Conta quantas avalia��es existem para um dado projeto. Caso um usu�rio tenha avaliado o mesmo
	 * projeto mais de uma vez, conta apenas a avalia��o mais recente
	 * @param project
	 * @return
	 */
	Long countAllLastByProject(OhLohProjectEntity project);
	
	/**
	 * Calcula o valor m�dio das avalia��es gerais para cada projeto.
	 * Conta apenas a avalia��o mais recente de cada usu�rio 
	 * @param project
	 * @return
	 */
	Double averagePreferenceByProject(OhLohProjectEntity project);
	
	/**
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	Long countAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
	/**
	 * Retorna todos os �ltimos registros de {@link PreferenceEntity}, com base no campo <code>registeredAt</code>,
	 * para cada projeto
	 * 
	 * @param project Projeto usado como filtro
	 * @return
	 */
	List<PreferenceEntity> findAllLastByProject(OhLohProjectEntity project);
	
	/**
	 * Retorna todos os �ltimos registros de {@link PreferenceEntity}, com base no campo <code>registeredAt</code>,
	 * para cada projeto. Utiliza par�metros para pagina��o.
	 *  
	 * @param project
	 * @param startAt
	 * @param offset
	 * @return
	 */
	List<PreferenceEntity> findAllLastByProject(OhLohProjectEntity project, Integer startAt, Integer offset);
	
	/**
	 * Retorna todas as avalia��es feitas por um usu�rio para um determinado projeto
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	List<PreferenceEntity> findAllByProjectAndUser(OhLohProjectEntity project, UserEntity user);
	
}
