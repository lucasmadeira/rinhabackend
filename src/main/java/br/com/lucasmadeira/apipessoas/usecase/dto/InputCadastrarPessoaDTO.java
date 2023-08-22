package br.com.lucasmadeira.apipessoas.usecase.dto;
import br.com.lucasmadeira.apipessoas.shared.DateFormat;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class InputCadastrarPessoaDTO {

    @NotEmpty(message = "o apelido tem que ser informado")
    @Size(min = 1, max = 32, message = "O apelido tem que ter entre 1 e 32 caracteres")
    private String apelido;

    @NotEmpty(message = "o nome tem que ser informado")
    @Size(min = 1, max = 100, message = "O nome tem que ter entre 1 e 100 caracteres")
    private String nome;

    @NotEmpty(message = "a data de nascimento tem que ser informada")
    @DateFormat
    private String nascimento;

    private List<String> stack;
}
