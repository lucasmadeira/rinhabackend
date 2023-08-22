package br.com.lucasmadeira.apipessoas.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OutputCadastrarPessoaDTO {
    private String id;
    private String apelido;
    private String nome;
    private String nascimento;
    private String stack;
}
