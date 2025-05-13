package com.br.fiap.fortaleza.sabor.domain.address;

public class Address {

    private String rua;
    private String bairro;
    private String complemento;
    private int numero;
    private String estado;
    private String cidade;
    private int cep;

    public Address(String rua, String bairro, String complemento, int numero, String estado, String cidade, int cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.complemento = complemento;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }
}
