package br.com.lucasmadeira.apipessoas.usecase;

import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import br.com.lucasmadeira.apipessoas.domain.repository.PessoaRepository;
import br.com.lucasmadeira.apipessoas.shared.RedisCacheService;
import br.com.lucasmadeira.apipessoas.usecase.dto.InputCadastrarPessoaDTO;
import br.com.lucasmadeira.apipessoas.usecase.dto.OutputCadastrarPessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Service
public class CadastrarPessoaUseCase {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private RedisCacheService cache;

    private Set<Pessoa> pessoas = Collections.synchronizedSet(new HashSet<>());


    public OutputCadastrarPessoaDTO execute(InputCadastrarPessoaDTO input){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNascimento = LocalDate.parse(input.getNascimento(), formatter);

        Pessoa pessoa = new Pessoa(input.getApelido(), input.getNome(), dataNascimento, input.getStack());

        pessoas.add(pessoa);
        cache.cacheItem(pessoa);

        return new OutputCadastrarPessoaDTO(pessoa.getId().toString(),
                pessoa.getApelido(),
                pessoa.getNome(),
                pessoa.getNascimento().toString(),
                pessoa.getStack()!=null?pessoa.getStack().toString():"");

    }

    @Scheduled(fixedRate = 10000) // Agendado para ser executado a cada 10 segundos
    @Transactional
    public void processarPessoasEmLotes() {
        synchronized (pessoas) {
            if(pessoas.size() < 1000){
                return;
            }
            List<Pessoa> lote = pessoas.stream().limit(1000).collect(Collectors.toList());
            repository.saveAll(lote);
            pessoas.removeAll(lote);
            repository.flush();

        }
    }


}
