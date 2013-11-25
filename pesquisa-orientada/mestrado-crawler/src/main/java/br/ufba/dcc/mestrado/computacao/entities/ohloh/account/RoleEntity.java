package br.ufba.dcc.mestrado.computacao.entities.ohloh.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

/**
 * Representa��o dos perfis de acesso no m�dulo de seguran�a
 * 
 * Perfis utilizados nas configura��es:
 * 
 * ROLE_USER: Usu�rio comum
 * ROLE_ADMIN: Usu�rio administrador do sistema
 * 
 * @author leandro.ferreira
 *
 */
@Entity
@Table(name = RoleEntity.NODE_NAME)
public class RoleEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3804141437260560911L;

	public final static String NODE_NAME = "role";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
