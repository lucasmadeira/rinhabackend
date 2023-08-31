package br.com.lucasmadeira.apipessoas.controller;


import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import br.com.lucasmadeira.apipessoas.domain.repository.PessoaRepository;
import br.com.lucasmadeira.apipessoas.usecase.CadastrarPessoaUseCase;
import br.com.lucasmadeira.apipessoas.usecase.dto.InputCadastrarPessoaDTO;
import br.com.lucasmadeira.apipessoas.usecase.dto.OutputCadastrarPessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("pessoas")
public class PessoaController {
    @Autowired
    private CadastrarPessoaUseCase cadastrarPessoaUseCase;

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<OutputCadastrarPessoaDTO> criarPessoa(@Valid @RequestBody InputCadastrarPessoaDTO request) {

        OutputCadastrarPessoaDTO response = cadastrarPessoaUseCase.execute(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/pessoas/" + response.getId().toString()));

        return new ResponseEntity<>(response,headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> detalhePessoa(@PathVariable String id){
            Pessoa pessoa = pessoaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    public List<Pessoa> busca(@RequestParam("t") String termo){


        if(termo == null || termo.trim().isEmpty()){
           throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Pessoa> pessoas = pessoaRepository.pesquisarPorTermo(termo);
        return pessoas;
    }


}