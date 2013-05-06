package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Banco {
    public abstract String iniciarSessao(String conta, String senha);
    public abstract boolean aprovarSaque(String sessao, double valor);
    public abstract String getUltimoErro();
    public abstract void setUltimoErro(String lastError);
    
}