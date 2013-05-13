package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

import br.mackenzie.caixaEletronico.sistemaPrincipal.Sessao;

public interface Banco {
    public abstract Sessao iniciarSessao(String conta, String senha);
    
    public abstract boolean aprovarSaque(String sessao, double valor);
    
    public void realizarTransferencia();
    
	boolean aprovarDeposito(Double Valor, String Conta); 	

	boolean autorizarTransferencia(String ContaDebitada, String ContaCreditada);

	Double obterSaldo(String Conta);    
    
}