package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Log {

	void logarTransacao(String mensagem, Object... parametros);
	void logarTransacao(String mensagem, Exception ex, Object... parametros);

}