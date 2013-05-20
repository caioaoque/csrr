package br.mackenzie.caixaEletronico.sistemaPrincipal;

import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Console;

public class CaixaEletronico {

	private final Banco banco;
	private final Console console;

	public CaixaEletronico(Banco banco, Console console) {
		this.banco = banco;
		this.console = console;
	}

	public boolean sacarValor(double valor, String conta, Sessao sessao) {
		if (valor <= 0) {
			console.imprimir("Valor inválido. Entre com um valor maior que zero.");
		} else if (valor % 10 != 0) {
			console.imprimir("Valor inválido. Entre com um valor múltiplo de dez.");
		}
		try {
			return banco.sacar(sessao.getChave(), valor);
		} catch (Exception ex) {
			console.imprimir(ex.getMessage());
			return false;
		}
	}
	
}