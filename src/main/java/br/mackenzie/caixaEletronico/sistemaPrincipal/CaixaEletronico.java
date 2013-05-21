package br.mackenzie.caixaEletronico.sistemaPrincipal;

import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Console;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Dispenser;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Impressora;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Log;

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
	private Dispenser dispenser;
	private Log log;
	private Impressora impressora;

	public CaixaEletronico(Banco banco, Console console, Dispenser dispenser,
			Log log, Impressora impressora) {
		this.banco = banco;
		this.console = console;
		this.dispenser = dispenser;
		this.log = log;
		this.impressora = impressora;
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
				banco.sacar(sessao, valor);
				dispenser.darNotas(valor);
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
				banco.iniciarDeposito(sessao, contaCreditada, valor);
				return true;
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
			}
		}
		return false;
	}

	public boolean depositarEnvelope(String sessao) {
		try {
			banco.sinalizarDepositoEnvelope(sessao);
			return true;
		} catch (Exception ex) {
			console.imprimir(ex.getMessage());
		}
		return false;
	}

	public boolean transferir(String sessao, String contaCreditada, double valor) {
		if (valor <= 0) {
			console.imprimir(VALOR_MENOR_OU_IGUAL_A_ZERO);
		}
		try {
			banco.transferir(sessao, contaCreditada, valor);
			return true;
		} catch (Exception ex) {
			console.imprimir(ex.getMessage());
		}
		return false;
	}

	public boolean ligarCaixa(double valorInicial) {
		if (valorInicial < 0) {
			console.imprimir("Valor nao pode ser menor do que zero :( ");
			return false;
		}
		valorDisponivel = valorInicial;
		estado = Estado.OPERANTE;
		return true;
	}

	public boolean desligarCaixa() {
		if (!sessao.isEmpty()) {
			try {
				banco.finalizarSessao(sessao);
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
				return false;
			}
		}

		estado = Estado.INOPERANTE;
		return true;

	}

}