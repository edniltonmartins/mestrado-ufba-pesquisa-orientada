package br.ufba.dcc.mestrado.computacao.dto.pageview;

import java.io.Serializable;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;


/**
 * 
 * Necessito fazer uma consulta � base de dados listando quantas reviews existem para cada projeto.
 * Essa informa��o ter� uma agrega��o, e acredito que uma nova entidade n�o seja necess�ria. Entretanto,
 * preciso retornar essas informa��es de algum modo pass�vel de ser utilizado numa p�gina JSF.
 * 
 * @author leandro.ferreira
 *
 */
public class ProjectReviewsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3315276865633299958L;

	private OhLohProjectEntity project;
	private Long reviewsCount;

	public OhLohProjectEntity getProject() {
		return project;
	}

	public void setProject(OhLohProjectEntity project) {
		this.project = project;
	}

	public Long getReviewsCount() {
		return reviewsCount;
	}

	public void setReviewsCount(Long reviewsCount) {
		this.reviewsCount = reviewsCount;
	}

}
