package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Log {

	void logarOperacao(String mensagem, Object... parametros);
	void logarTransacao(String mensagem, Exception ex, Object... parametros);

}