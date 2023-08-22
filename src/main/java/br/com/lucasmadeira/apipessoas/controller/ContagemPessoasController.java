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
@RequestMapping("contagem-pessoas")
public class ContagemPessoasController {

    @Autowired
    private PessoaRepository pessoaRepository;


    @GetMapping
    public Long totaPessoas(){
        return pessoaRepository.count();
    }


}