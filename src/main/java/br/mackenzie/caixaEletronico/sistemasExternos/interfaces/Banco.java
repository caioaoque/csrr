package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public abstract class Banco {
    public abstract String iniciarSessao(String conta, String senha);
    public abstract boolean aprovarSaque(String sessao, double valor);
    public String getLastError() { return m_lastError; }
    public void setLastError(String lastError) { m_lastError = lastError; }
    private String m_lastError;
    
    
}
