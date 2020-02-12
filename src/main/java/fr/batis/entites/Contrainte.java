package main.java.fr.batis.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "CONTRAINTESS")
public class Contrainte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7669268348623624134L;
	private Long id;
	private String type;
	private String description;

	public Contrainte() {
		super();
	}

	public Contrainte(String type, String description) {
		super();
		this.type = type;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_CONTRAINTE")
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

}
