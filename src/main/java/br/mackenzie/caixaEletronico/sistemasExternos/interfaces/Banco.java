package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Banco {
	String iniciarSessao(String cartao, String senha, String conta) throws Exception;

	void sacar(String sessao, double valor) throws Exception;

	void iniciarDeposito(String sessao, String contaCreditada, double valor) throws Exception;

	void sinalizarDepositoEnvelope(String sessao) throws Exception;

	void transferir(String sessao, String ContaCreditada, double valor) throws Exception;

	double obterSaldo(String sessao) throws Exception;
	
	void finalizarSessao(String sessao) throws Exception;

}