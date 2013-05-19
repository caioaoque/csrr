package br.mackenzie.caixaEletronico.test.sistemaPrincipal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.mackenzie.caixaEletronico.sistemaPrincipal.CaixaEletronico;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Sessao;
import br.mackenzie.caixaEletronico.test.mocks.BancoMockFactory;

public class TesteCaixaEletronico {
	
	@Test
	public void TesteValidacaoSenhaInvalida() throws Exception {
		CaixaEletronico caixa = new CaixaEletronico(BancoMockFactory.getBancoSenhaInvalida());
		try{
			caixa.sacarValor(0, "", new Sessao());	
		}catch(Exception e){
			assertEquals(e.getMessage(),"Senha Invalida");
		}
		
	}
}