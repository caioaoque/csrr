package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Banco {
	String iniciarSessao(String cartao, String senha, String conta) throws Exception;

	boolean sacar(String sessao, double valor);

	boolean iniciarDeposito(String sessao, double Valor);

	boolean sinalizarDepositoEnvelope(String sessao);

	boolean transferir(String sessao, String ContaCreditada, double valor);

	double obterSaldo(String sessao);
	
	void finalizarSessao(String sessao);

}