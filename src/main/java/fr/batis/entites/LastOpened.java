package main.java.fr.batis.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MEMORY")
public class LastOpened {
	private String projectName;
	private Long projectId;
	private Long id;
	
	/**
	 * 
	 */
	public LastOpened() {
		super();
	}

	/**
	 * @param projectName
	 */
	public LastOpened(String projectName) {
		super();
		this.projectName = projectName;
	}

	@Column(name ="NAME")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name ="ID_CHANTIER")
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
