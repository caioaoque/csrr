package br.mackenzie.caixaEletronico.sistemaPrincipal;

import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Console;

public class CaixaEletronico {

	private static final String VALOR_MENOR_OU_IGUAL_A_ZERO = "Valor inválido. Entre com um valor maior que zero.";

	private static enum Estado {
		OPERANTE, INOPERANTE;
	}

	private final Banco banco;
	private final Console console;
	private Estado estado;
	private double valorDisponivel;
	private String sessao;

	public CaixaEletronico(Banco banco, Console console) {
		this.banco = banco;
		this.console = console;
	}

	public boolean iniciarSessao(String cartao, String senha, String conta) {
		try {
			this.sessao = banco.iniciarSessao(cartao, senha, conta);
			return true;
		} catch (Exception ex) {
			this.sessao = null;
			console.imprimir(ex.getMessage());
			return false;
		}
	}

	public boolean sacarValor(double valor, String sessao) {
		if (valor <= 0) {
			console.imprimir(VALOR_MENOR_OU_IGUAL_A_ZERO);
		} else if (valor % 10 != 0) {
			console.imprimir("Valor inválido. Entre com um valor múltiplo de dez.");
		} else if (valor > this.valorDisponivel) {
			console.imprimir("O valor solicitado é superior ao disponível para saque.");
		} else {
			try {
				return banco.sacar(sessao, valor);
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
			}
		}
		return false;
	}

	public boolean depositarValor(double valor, String contaCreditada,
			String sessao) {
		if (valor <= 0) {
			console.imprimir(VALOR_MENOR_OU_IGUAL_A_ZERO);
		} else {
			try {
				return banco.iniciarDeposito(sessao, contaCreditada, valor);
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
			}
		}
		return false;
	}

	public boolean depositarEnvelope(String sessao) {
		try {
			return banco.sinalizarDepositoEnvelope(sessao);
		} catch (Exception ex) {
			console.imprimir(ex.getMessage());
		}
		return false;
	}

}