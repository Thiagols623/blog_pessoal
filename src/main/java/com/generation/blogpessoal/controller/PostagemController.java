package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

// Anotações: alterar e/ou definir comportamentos

@RestController  // Indica que a classe é uma Controller (Recebe Requisições e Responde)
@RequestMapping("/postagens")  // Indica que as requisições do endpoint das postagens serão tratadas por essa Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")  // Libera o acesso a qualquer Front
public class PostagemController {
	
	@Autowired  // Inversão de Dependência / Controle
	private PostagemRepository postagemRepository;
	
	// Cria a classe repo | Implementa os métodos da interface | instancia um objeto da classe repo
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
	return postagemRepository. findById(id)
	.map(resposta -> ResponseEntity. ok(resposta))
	.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND) .build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
	return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {

	postagem.setId(null);

	return ResponseEntity. status (HttpStatus. CREATED)
	.body (postagemRepository. save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
	return postagemRepository. findById(postagem.getId())
	.map(resposta -> ResponseEntity.status(HttpStatus. OK)
	.body (postagemRepository. save(postagem) ))
	.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	Optional<Postagem> postagem = postagemRepository.findById(id);

	if(postagem.isEmpty())
	throw new ResponseStatusException(HttpStatus.NOT_FOUND);

	postagemRepository.deleteById(id);
	}
}




