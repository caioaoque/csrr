package br.mackenzie.caixaEletronico.test.sistemaPrincipal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.mackenzie.caixaEletronico.sistemaPrincipal.CaixaEletronico;
import br.mackenzie.caixaEletronico.sistemaPrincipal.Sessao;
import br.mackenzie.caixaEletronico.test.mocks.BancoMockFactory;
import br.mackenzie.caixaEletronico.test.mocks.ConsoleImpl;

public class TesteCaixaEletronico {
	
	@Test
	public void TesteValidacaoSenhaInvalida() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoSenhaInvalida(), new ConsoleImpl());
		try{
			caixa.sacarValor(0, "", new Sessao("conta1"));
		}catch(Exception e){
			assertEquals(e.getMessage(),"Senha Invalida");
		}
		
	}
}