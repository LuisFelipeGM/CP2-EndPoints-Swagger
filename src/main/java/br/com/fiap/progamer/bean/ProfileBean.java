package br.com.fiap.progamer.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.fiap.progamer.dao.ProfileDao;
import br.com.fiap.progamer.model.ProfileModel;

@Named
@RequestScoped
public class ProfileBean {
	
	ProfileModel profileModel = new ProfileModel();
	
	@Inject
	private ProfileDao profileDao;
	
	private ProfileModel selectedProfile;

	public ProfileModel getProfileModel() {
		return profileModel;
	}

	public void setProfileModel(ProfileModel profileModel) {
		this.profileModel = profileModel;
	}

	public ProfileModel getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(ProfileModel selectedProfile) {
		this.selectedProfile = selectedProfile;
	}
	
	@Transactional
	public void save() {
		if(this.profileModel.getEmail()!="" && this.profileModel.getName()!="" && 
				this.profileModel.getPassword()!="" && this.profileModel.getProfile()!="") {
			profileDao.save(this.profileModel);
			this.profileModel = new ProfileModel();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"O usuário foi cadastrado com sucesso!","INFO"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar cadastrar um usuário!","ERROR"));
		}
	}
	
	
	public List<ProfileModel> findAll(){
		return this.profileDao.findAll();
	}
	

}
