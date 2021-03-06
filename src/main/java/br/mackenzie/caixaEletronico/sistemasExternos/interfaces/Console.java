package br.mackenzie.caixaEletronico.sistemasExternos.interfaces;

public interface Console {

	void imprimir(String texto);
	
	void imprimirFormatado(String texto, Object... parametros);
	
	void solicitarEntrada(String texto);
}