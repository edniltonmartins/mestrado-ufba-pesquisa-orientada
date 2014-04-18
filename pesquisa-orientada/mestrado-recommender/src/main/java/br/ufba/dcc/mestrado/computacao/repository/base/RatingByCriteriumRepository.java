package br.ufba.dcc.mestrado.computacao.repository.base;

import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Criando uma API gen�rica para recuperar um dataset dos valores de prefer�ncia para um crit�rio espec�fico
 * 
 * @author leandro.ferreira
 *
 */
public interface RatingByCriteriumRepository {
	
	
	Double averagePreferenceByItemAndCriterium(Long itemId, Long criteriumId);
	
	/**
	 * 
	 * @param criteriumId
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriterium(Long criteriumId);
	
	/**
	 * 
	 * @param criteriumId
	 * @param itemId
	 * @return
	 */
	Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriteriumAndItem(Long criteriumId, Long itemId);
	
	/**
	 * 
	 * @param criteriumID
	 * @return
	 */
	Long countAllLastByCriterium(Long criteriumId);

}
