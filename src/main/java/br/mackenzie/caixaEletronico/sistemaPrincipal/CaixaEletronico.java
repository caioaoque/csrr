package br.mackenzie.caixaEletronico.sistemaPrincipal;

import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Sessao;

public class CaixaEletronico {
	
	private final Banco banco;
	
	public CaixaEletronico(Banco banco) {
		this.banco = banco;
	}
	
	public boolean sacarValor(double valor, String conta, Sessao sessao) {
//		return banco.aprovarSaque(sessao, valor)
		return false;
	}
}