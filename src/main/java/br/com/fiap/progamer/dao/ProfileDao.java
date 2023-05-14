package br.com.fiap.progamer.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.com.fiap.progamer.model.ProfileModel;

@Named
public class ProfileDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public ProfileDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	public void save(ProfileModel profileModel) {
		this.entityManager.merge(profileModel);
	}
	
	public ProfileModel buscaPorId(Long id) {
		return entityManager.find(ProfileModel.class, id);
	}
	
	public List<ProfileModel> findAll(){
		@SuppressWarnings("unchecked")
		TypedQuery<ProfileModel> query = (TypedQuery<ProfileModel>) entityManager.createQuery("SELECT e FROM ProfileModel e");
		return query.getResultList();
	}
	
	@Transactional
	public void deletar(ProfileModel profile) {
		entityManager.remove(profile);
	}
	
}
