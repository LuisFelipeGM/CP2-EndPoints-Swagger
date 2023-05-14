package br.com.fiap.progamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.progamer.model.ProfileModel;

public interface ProfileRepository  extends JpaRepository<ProfileModel, Long>{

}
