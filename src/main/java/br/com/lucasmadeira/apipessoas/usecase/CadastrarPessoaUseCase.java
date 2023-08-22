package br.com.lucasmadeira.apipessoas.usecase;

import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import br.com.lucasmadeira.apipessoas.domain.repository.PessoaRepository;
import br.com.lucasmadeira.apipessoas.usecase.dto.InputCadastrarPessoaDTO;
import br.com.lucasmadeira.apipessoas.usecase.dto.OutputCadastrarPessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CadastrarPessoaUseCase {

    @Autowired
    private PessoaRepository repository;

    @Transactional
    public OutputCadastrarPessoaDTO execute(InputCadastrarPessoaDTO input){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dataNascimento = LocalDate.parse(input.getNascimento(), formatter);

        Pessoa pessoa = new Pessoa(input.getApelido(), input.getNome(), dataNascimento, input.getStack());

        Pessoa pessoaSalva = repository.save(pessoa);

        return new OutputCadastrarPessoaDTO(pessoaSalva.getId().toString(),
                pessoaSalva.getApelido(),
                pessoaSalva.getNome(),
                pessoaSalva.getNascimento().toString(),
                pessoaSalva.getStack()!=null?pessoaSalva.getStack().toString():"");

    }
}
