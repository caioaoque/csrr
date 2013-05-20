package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Banco {
	String iniciarSessao(String cartao, String senha, String conta) throws Exception;

	boolean sacar(String sessao, double valor) throws Exception;

	boolean iniciarDeposito(String sessao, double Valor) throws Exception;

	boolean sinalizarDepositoEnvelope(String sessao) throws Exception;

	boolean transferir(String sessao, String ContaCreditada, double valor) throws Exception;

	double obterSaldo(String sessao) throws Exception;
	
	void finalizarSessao(String sessao) throws Exception;

}