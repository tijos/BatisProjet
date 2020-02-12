package main.java.fr.batis.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "EmployeRole", uniqueConstraints = @UniqueConstraint(columnNames = { "ID_EMPLOYE", "ID_CHANTIER",
		"ID_ROLE" }))

/**
 * 
 * @author tijos
 *
 */
public class EmployeRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3330524000952661709L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ID_EMPLOYE")
	private Long idChantier;

	@Column(name = "ID_CHANTIER")
	private Long idEmploye;

	@Column(name = "ID_ROLE")
	private Long idRole;

	/**
	 * @return the idChantier
	 */
	public Long getIdChantier() {
		return idChantier;
	}

	/**
	 * @param idChantier the idChantier to set
	 */
	public void setIdChantier(Long idChantier) {
		this.idChantier = idChantier;
	}

	/**
	 * @return the idEmploye
	 */
	public Long getIdEmploye() {
		return idEmploye;
	}

	/**
	 * @param idEmploye the idEmploye to set
	 */
	public void setIdEmploye(Long idEmploye) {
		this.idEmploye = idEmploye;
	}

	/**
	 * @return the idRole
	 */
	public Long getIdRole() {
		return idRole;
	}

	/**
	 * @param idRole the idRole to set
	 */
	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

}
