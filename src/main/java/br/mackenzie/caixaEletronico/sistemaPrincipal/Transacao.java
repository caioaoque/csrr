package br.mackenzie.caixaEletronico.sistemaPrincipal;

public abstract class Transacao {
    
    abstract void cancelarTransacao();
    abstract void executarTransacao();

}