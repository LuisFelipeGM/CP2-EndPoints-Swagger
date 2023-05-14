package br.com.fiap.progamer.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.progamer.dao.SetupDao;
import br.com.fiap.progamer.model.SetupModel;
import br.com.fiap.progamer.repository.SetupRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/setups")
@Api(value = "Progamer API")
public class SetupController {

	@Inject
	private SetupDao setupDao;
	
	@Inject
	private SetupRepository repository;

	@GetMapping()
	@ApiOperation("Obter todos os setups")
	public ResponseEntity<List<SetupModel>> index() {
		return ResponseEntity.ok(setupDao.findAll());
	}

	@PostMapping()
	@ApiOperation("Criar setup novo")
	public ResponseEntity<String> create(@RequestBody SetupModel setupRequest) {
		SetupModel setup = new SetupModel();

		try {
			if (setupRequest.getName() == null || setupRequest.getDescription() == null || setupRequest.getPrice() <= 0
					|| setupRequest.getFile() == null) {

				System.out.println("===== ERRO =====");
				System.out.println("Todos os Parâmetros são obrigatórios!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			setup.setName(setupRequest.getName());
			setup.setDescription(setupRequest.getDescription());
			setup.setPrice(setupRequest.getPrice());
			setup.setFile(setupRequest.getFile());

			setupDao.save(setup);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("{id}")
	@ApiOperation("Obter setup por ID")
	public ResponseEntity<SetupModel> show(@PathVariable("id") long id) {
		
		SetupModel setup = setupDao.buscarPorId(id);
				
		if (setup == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(setup);
	}

	@PutMapping("{id}")
	@ApiOperation("Atualização de Setup")
	public  ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody SetupModel setupRequest) {
		
		try {
			SetupModel setup = setupDao.buscarPorId(id);
			
			if (setup == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
			setup.setName(setupRequest.getName());
			setup.setDescription(setupRequest.getDescription());
			setup.setPrice(setupRequest.getPrice());
			setup.setFile(setupRequest.getFile());
			
			setupDao.save(setup);
			
			return ResponseEntity.ok("Setup atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PatchMapping("{id}")
	@ApiOperation("Atualização parcial de Setups")
	public ResponseEntity<String> patch(@PathVariable("id") long id, @RequestBody SetupModel setupRequest){
		try {
			SetupModel setup = setupDao.buscarPorId(id);
			
			if (setup == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
			if (setupRequest.getName() != null)
				setup.setName(setupRequest.getName());
			if (setupRequest.getDescription() != null)
				setup.setDescription(setupRequest.getDescription());
			if (setupRequest.getPrice() > 0)
				setup.setPrice(setupRequest.getPrice());
			if (setupRequest.getFile() != null)
				setup.setFile(setupRequest.getFile());
			
			setupDao.save(setup);
			return ResponseEntity.ok("Setup atualizado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("{id}")
	@ApiOperation("Excluir Setup")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			SetupModel setup = setupDao.buscarPorId(id);
			if (setup == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			setupDao.deletar(setup);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("delete/{id}")
	@ApiOperation("Excluindo Setup com JPA")
	public ResponseEntity<String> deleteRepository(@PathVariable("id") long id){
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
