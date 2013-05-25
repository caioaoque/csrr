package br.mackenzie.caixaEletronico.test.sistemaPrincipal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.mackenzie.caixaEletronico.objetosCompartilhados.Sessao;
import br.mackenzie.caixaEletronico.sistemaPrincipal.CaixaEletronico;
import br.mackenzie.caixaEletronico.test.mocks.BancoMockFactory;
import br.mackenzie.caixaEletronico.test.mocks.ConsoleImpl;
import br.mackenzie.caixaEletronico.test.mocks.DispenserMockFactory;
import br.mackenzie.caixaEletronico.test.mocks.ImpressoraMockFactory;
import br.mackenzie.caixaEletronico.test.mocks.LogMockFactory;

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
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoSenhaInvalida(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK() );		
		boolean ligado = caixa.inicializarCaixa(500);
		assertTrue(ligado);
		boolean result = caixa.iniciarSessao("12345678", "aaaaa", "98765");		
		assertFalse(result);	
		assertEquals(outContent.toString(),"Senha Invalida");
	}
	
	@Test
	public void TesteSaqueCaixaSemDinheiro() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoOK(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean ligado = caixa.inicializarCaixa(500);
		assertTrue(ligado);
		boolean result = caixa.sacarValor(200000.0,new Sessao("111111"), "222222");		
		assertFalse(result);	
		assertEquals(outContent.toString(),"O valor solicitado é superior ao disponível para saque.");
	}
	
	
	@Test
	public void TesteBancoNaoAprovaSaque() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoNaoAprovaSaque(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean ligado = caixa.inicializarCaixa(500);
		assertTrue(ligado);
		boolean result = caixa.sacarValor(10.0, new Sessao("111111"), "222222");		
		assertFalse(result);	
		assertEquals(outContent.toString(),"Saque Nao Aprovado.");
	}
	
	
	@Test
	public void TesteBancoNaoAprovaDeposito() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoNaoAprovaDeposito(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean ligado = caixa.inicializarCaixa(500);
		assertTrue(ligado);
		boolean result2 = caixa.iniciarSessao("12345678", "aaaaa", "98765");		
		assertTrue(result2);
		boolean result = caixa.depositarValor(new Sessao("111111"), "conta", 50.0);	
		assertFalse(result);	
		assertEquals(outContent.toString(),"Deposito Nao Aprovado.");
	}
	
	@Test
	public void TesteTransferenciaSemSaldo() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoNaoRealizaTransferencia(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean ligado = caixa.inicializarCaixa(500);
		assertTrue(ligado);
		boolean result2 = caixa.iniciarSessao("12345678", "aaaaa", "98765");		
		assertTrue(result2);
		boolean result = caixa.transferir(new Sessao("111111"), "aaaaa", "bbbbb", 10000.0);	
		assertFalse(result);	
		assertEquals(outContent.toString(),"Transferencia nao aprovada. O saldo é inferior ao valor da transferencia!");
	}
	
	
	
	
	
	
	
	
	@Test
	public void TesteLigarCaixaOK() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoOK(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean ligado = caixa.inicializarCaixa(500);
		assertTrue(ligado);
	}
	
	@Test
	public void TesteLigarCaixaErroValor() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoOK(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean ligado = caixa.inicializarCaixa(-500);
		assertFalse(ligado);
	}
	
	@Test
	public void TesteDesligarCaixaOK() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoOK(), new ConsoleImpl(), DispenserMockFactory.getDispenserOK(), LogMockFactory.getLogOK(), ImpressoraMockFactory.getImpressoraOK());
		boolean desligado = caixa.encerrarCaixa();
		assertTrue(desligado);
	}
		
	
}