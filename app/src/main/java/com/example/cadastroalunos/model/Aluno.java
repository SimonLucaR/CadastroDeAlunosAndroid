package com.example.cadastroalunos.model;

import com.orm.SugarRecord;

import java.util.Objects;

public class Aluno extends SugarRecord {
    private int    ra;
    private String nome;
    private String cpf;
    private String dtNascimento;
    private String dtMatricula;
    private String curso;
    private String periodo;

    public Aluno(){

    }

    public Aluno(int ra, String nome, String cpf, String dtNascimento, String dtMatricula, String curso, String periodo) {
        this.ra = ra;
        this.nome = nome;
        this.cpf = cpf;
        this.dtNascimento = dtNascimento;
        this.dtMatricula = dtMatricula;
        this.curso = curso;
        this.periodo = periodo;
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getDtMatricula() {
        return dtMatricula;
    }

    public void setDtMatricula(String dtMatricula) {
        this.dtMatricula = dtMatricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return ra == aluno.ra;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ra);
    }
}
