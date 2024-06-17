package com.mycompany.dukemarket.javafx.model;

/**
 *
 * @author Aluno
 */
public class Cliente {
    
    int id;
    String nome;
    String endereco;
    String cidade;
    String uf;
    String cep;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    @Override
    public String toString() {
        return String.format("%3d | %-80s | %-80s | %-60s | %-2s | %-9s", 
                this.id,
                this.nome,
                this.endereco,
                this.cidade,
                this.uf,
                this.uf);
    }   
}
