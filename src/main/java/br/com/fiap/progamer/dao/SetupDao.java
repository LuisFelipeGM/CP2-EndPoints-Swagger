package br.com.fiap.progamer.dao;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import br.com.fiap.progamer.model.SetupModel;

@Named
public class SetupDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public SetupDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	public void save(SetupModel setupModel) {
		this.entityManager.merge(setupModel);
	}
	
	public SetupModel buscarPorId(Long id) {
		return entityManager.find(SetupModel.class, id);
	}
	
	public List<SetupModel> findAll(){
		@SuppressWarnings("unchecked")
		TypedQuery<SetupModel> query = (TypedQuery<SetupModel>) entityManager.createQuery("SELECT e FROM SetupModel e");
		return query.getResultList();
	}
	
	@Transactional
	public void deletar(SetupModel setup) {
		entityManager.remove(setup);
	}
	
}