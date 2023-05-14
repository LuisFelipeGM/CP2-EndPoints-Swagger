package br.com.fiap.progamer.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.file.UploadedFile;

import br.com.fiap.progamer.dao.SetupDao;
import br.com.fiap.progamer.model.SetupModel;
import br.com.fiap.progamer.service.UploadService;

@Named
@RequestScoped
public class SetupBean {

	SetupModel setupModel = new SetupModel();
	
	@Inject
	private SetupDao setupDao;
	
	private SetupModel selectedSetup;
	
	private UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public SetupModel getSetupModel() {
		return setupModel;
	}

	public void setSetupModel(SetupModel setupModel) {
		this.setupModel = setupModel;
	}
	
	public SetupModel getSelectedSetup() {
		return selectedSetup;
	}

	public void setSelectedSetup(SetupModel selectedSetup) {
		this.selectedSetup = selectedSetup;
	}

	@Transactional
	public void save() {
		
		UploadService upload = new UploadService();
		
		if(this.setupModel.getName()!="" && this.setupModel.getDescription()!="") {
			
			upload.salvarArquivo(getFile());
			setupModel.setFile(getFile().getFileName());
			
			setupDao.save(this.setupModel);
			this.setupModel = new SetupModel();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"As informações foram salvas com sucesso!","INFO"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro ao tentar salvar!","ERROR"));
		}
	}
	
	public List<SetupModel> findAll(){
		return this.setupDao.findAll();
	}

	
}