package br.mackenzie.caixaEletronico.test.sistemaPrincipal;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.mackenzie.caixaEletronico.sistemaPrincipal.CaixaEletronico;
import br.mackenzie.caixaEletronico.test.mocks.BancoMockFactory;
import br.mackenzie.caixaEletronico.test.mocks.ConsoleImpl;

public class TesteCaixaEletronico {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}	
	
	@Test
	public void TesteValidacaoSenhaInvalida() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoSenhaInvalida(), new ConsoleImpl());		
		boolean result = caixa.iniciarSessao("12345678", "aaaaa", "98765");		
		assertFalse(result);		
		assertEquals(outContent,"Senha Invalida");				
	}
}