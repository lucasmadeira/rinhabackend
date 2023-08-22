package br.com.lucasmadeira.apipessoas.domain.repository;

import br.com.lucasmadeira.apipessoas.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PessoaRepository  extends JpaRepository<Pessoa, UUID> {
    @Query(value = "select * from pessoa p where p.facet like %:termo%", nativeQuery = true)
    List<Pessoa> pesquisarPorTermo(@Param("termo") String termo);
}
