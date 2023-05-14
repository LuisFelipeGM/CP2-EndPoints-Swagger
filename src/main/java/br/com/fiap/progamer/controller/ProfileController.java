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

import br.com.fiap.progamer.dao.ProfileDao;
import br.com.fiap.progamer.model.ProfileModel;
import br.com.fiap.progamer.repository.ProfileRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/profiles")
@Api(value = "Progamer API")
public class ProfileController {

	@Inject
	private ProfileDao profileDao;

	@Inject
	private ProfileRepository repository;

	@GetMapping()
	@ApiOperation("Obter todos os profiles")
	public ResponseEntity<List<ProfileModel>> index() {
		return ResponseEntity.ok(profileDao.findAll());
	}

	@PostMapping()
	@ApiOperation("Criar profile novo")
	public ResponseEntity<String> create(@RequestBody ProfileModel profileRequest) {
		ProfileModel profile = new ProfileModel();

		try {
			if (profileRequest.getName() == null || profileRequest.getEmail() == null
					|| profileRequest.getPassword() == null || profileRequest.getProfile() == null) {
				System.out.println("===== ERRO =====");
				System.out.println("Todos os Parâmetros são obrigatórios!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			profile.setName(profileRequest.getName());
			profile.setEmail(profileRequest.getEmail());
			profile.setPassword(profileRequest.getPassword());
			profile.setProfile(profileRequest.getProfile());

			profileDao.save(profile);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("{id}")
	@ApiOperation("Obter profile por ID")
	public ResponseEntity<ProfileModel> show(@PathVariable("id") long id) {

		ProfileModel profile = profileDao.buscaPorId(id);

		if (profile == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		return ResponseEntity.ok(profile);
	}

	@PutMapping("{id}")
	@ApiOperation("Atualização de Profile")
	public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody ProfileModel profileRequest) {

		try {
			ProfileModel profile = profileDao.buscaPorId(id);

			if (profile == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			profile.setName(profileRequest.getName());
			profile.setEmail(profileRequest.getEmail());
			profile.setPassword(profileRequest.getPassword());
			profile.setProfile(profileRequest.getProfile());

			profileDao.save(profile);

			return ResponseEntity.ok("Profile atualizado com sucesso! {PUT}");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("{id}")
	@ApiOperation("Atualização parcial de Profile")
	public ResponseEntity<String> patch(@PathVariable("id") long id, @RequestBody ProfileModel profileRequest) {

		try {
			ProfileModel profile = profileDao.buscaPorId(id);

			if (profile == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			if (profileRequest.getName() != null)
				profile.setName(profileRequest.getName());
			if (profileRequest.getEmail() != null)
				profile.setEmail(profileRequest.getEmail());
			if (profileRequest.getPassword() != null)
				profile.setPassword(profileRequest.getPassword());
			if (profileRequest.getProfile() != null)
				profile.setProfile(profileRequest.getProfile());

			profileDao.save(profile);

			return ResponseEntity.ok("Profile atualizado com sucesso! {PATCH}");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("{id}")
	@ApiOperation("Excluir profile")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			ProfileModel profile = profileDao.buscaPorId(id);
			if (profile == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			profileDao.deletar(profile);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping("delete/{id}")
	@ApiOperation("Excluindo profile com JPA")
	public ResponseEntity<String> deleteRepository(@PathVariable("id") long id) {
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
