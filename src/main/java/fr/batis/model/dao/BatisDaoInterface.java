package main.java.fr.batis.model.dao;

import java.io.Serializable;
import java.util.List;

public interface BatisDaoInterface <T, Id extends Serializable>{
	
	public void saveOrUpdate(T entity);
	
	
	public T findById(Id id);
	
	public void delete(T entity);
	
	public List<T> findAll();
	
	public void deleteAll();
	
	public T findByName(String name);
	
	//public T findByNameIdChantierAndStatut(String name,Long idChantier,Integer codeStatut);


	List<T> findFirstTen();
}
