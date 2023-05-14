package br.com.fiap.progamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.progamer.model.SetupModel;

public interface SetupRepository extends JpaRepository<SetupModel, Long> {

}
