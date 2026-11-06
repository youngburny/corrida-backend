package br.com.corrida.dto;

import br.com.corrida.model.Categoria;
import br.com.corrida.model.Percurso;

import java.time.LocalDate;

public class InscricaoResponse {

    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Percurso percurso;
    private int idadeNaProva;
    private Categoria categoria;

    public InscricaoResponse(
            String nome,
            String email,
            LocalDate dataNascimento,
            Percurso percurso,
            int idadeNaProva,
            Categoria categoria
    ) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.percurso = percurso;
        this.idadeNaProva = idadeNaProva;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Percurso getPercurso() {
        return percurso;
    }

    public int getIdadeNaProva() {
        return idadeNaProva;
    }

    public Categoria getCategoria() {
        return categoria;
    }

}
