package br.com.lucasmadeira.apipessoas.controller;


import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import br.com.lucasmadeira.apipessoas.domain.repository.PessoaRepository;
import br.com.lucasmadeira.apipessoas.shared.RedisCacheService;
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

    @Autowired
    private RedisCacheService cache;

    @PostMapping
    public ResponseEntity<?> criarPessoa(@Valid @RequestBody InputCadastrarPessoaDTO request) {


        if(stackInvalida(request.getStack())){
            return  ResponseEntity.badRequest().build();
        }


        HttpHeaders headers = new HttpHeaders();
        Pessoa pessoa = cache.getCachedItem(request.getApelido());

        if(pessoa  != null){
            return new ResponseEntity<>("NOK",headers, HttpStatus.UNPROCESSABLE_ENTITY);
        }


        OutputCadastrarPessoaDTO response = cadastrarPessoaUseCase.execute(request);

        headers.setLocation(URI.create("/pessoas/" + response.getId().toString()));

        return new ResponseEntity<>("OK",headers, HttpStatus.CREATED);
    }

    private boolean stackInvalida(List<String> stack) {
        if(stack == null){
            return false;
        }

        if(stack.stream().anyMatch(s -> s == null || s.trim().isEmpty() || s.length() > 32)){
            return true;
        }

        return false;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> detalhePessoa(@PathVariable String id){

            Pessoa pessoa = cache.getCachedItem(id);
            if(pessoa == null){
                pessoa = pessoaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            }

            return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> busca(@RequestParam("t") String termo){


        if(termo == null || termo.trim().isEmpty()){
           throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Pessoa> pessoas = pessoaRepository.pesquisarPorTermo(termo);
        return ResponseEntity.ok(pessoas);
    }


}