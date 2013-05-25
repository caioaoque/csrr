package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

import br.mackenzie.caixaEletronico.objetosCompartilhados.Sessao;

public interface Banco {
	Sessao iniciarSessao(String cartao, String senha, String conta) throws Exception;

	void sacar(Sessao sessao, String conta, double valor) throws Exception;

	void iniciarDeposito(Sessao sessao, String conta, double valor) throws Exception;

	void sinalizarDepositoEnvelope(Sessao sessao, String conta) throws Exception;

	void transferir(Sessao sessao, String contaDebitada, String contaCreditada, double valor) throws Exception;

	double obterSaldo(Sessao sessao, String conta) throws Exception;
	
	void finalizarSessao(Sessao sessao) throws Exception;
	
	void consultarSaldo(Sessao sessao, String conta) throws Exception;
	
	void anularOperacao(Sessao sessao) throws Exception;

}