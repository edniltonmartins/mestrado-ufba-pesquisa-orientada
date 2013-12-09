package br.ufba.dcc.mestrado.computacao.entities.ohloh.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import br.ufba.dcc.mestrado.computacao.entities.BaseEntity;

@Entity
@Table(name = OhLohLinkEntity.NODE_NAME)
public class OhLohLinkEntity implements BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2167520665751768527L;

	public final static String NODE_NAME = "link";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String category;
	
	@ManyToOne(optional = false)	
	private OhLohProjectEntity ohlohProject;
	
	@Column(name = "project_id", updatable = false, insertable = false)
	private Long projectId;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String title;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(length=2048)
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	public OhLohProjectEntity getOhlohProject() {
		return ohlohProject;
	}
	
	public void setOhlohProject(OhLohProjectEntity ohlohProject) {
		this.ohlohProject = ohlohProject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OhLohLinkEntity other = (OhLohLinkEntity) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getTitle() + " " + getUrl();
	}
	

}
