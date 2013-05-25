package br.mackenzie.caixaEletronico.test.mocks;

import java.util.Scanner;

import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Console;

public class ConsoleImpl implements Console {

	@Override
	public void imprimir(String texto) {
		System.out.print(texto);
	}
	
	@Override
	public void imprimirFormatado(String texto, Object... parametros) {
		System.out.printf(texto, parametros);
	}

	@Override
	public void solicitarEntrada(String texto) {
		new Scanner(System.in).next();
	}

}