package br.com.lucasmadeira.apipessoas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Table(uniqueConstraints = { @UniqueConstraint(name = "uk_apelido", columnNames = { "apelido"}) })
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false,  length = 32)
    private String apelido;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private LocalDate nascimento;

    @ElementCollection
    private List<String> stack;

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String facet;


    public Pessoa(String apelido, String nome, LocalDate nascimento, List<String> stack) {
        this.id = UUID.randomUUID();
        this.apelido = apelido;
        this.nome =  nome;
        this.nascimento = nascimento;
        this.stack = stack;
        gerarFacet();
    }

    public void gerarFacet(){
        this.facet = this.apelido.concat(this.nome);
        if(this.stack!=null && !this.stack.isEmpty()){
            this.facet = this.facet.concat(stack.stream().reduce("",(a,b) -> a.concat(b)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(getApelido(), pessoa.getApelido());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApelido());
    }
}