package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.*;
import br.mackenzie.caixaEletronico.objetosCompartilhados.Sessao;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;

public class BancoMockFactory {

	public static Banco getBancoSenhaInvalida() throws Exception {
		Banco banco = createMock(Banco.class);
		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class))).andThrow(new Exception("Senha Invalida"));
		replay(banco);
		return banco;
	}	
	
	public static Banco getBancoNaoAprovaSaque() throws Exception {
		Sessao sessao = new Sessao("111111");
		Banco banco = createMock(Banco.class);		
		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class))).andStubReturn(sessao);
		banco.sacar(sessao, anyObject(String.class), anyDouble() );
		expectLastCall().andThrow(new Exception("Saque Nao Aprovado."));
		replay(banco);
		return banco;
	}

//	public static Banco getBancoNaoAprovaDeposito() throws Exception {
//		Banco banco = createMock(Banco.class);
//		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class)));
//		banco.iniciarDeposito(anyObject(String.class), anyObject(String.class), anyObject(Double.class));		
//		expectLastCall().andThrow(new Exception("Deposito Nao Aprovado."));
//		replay(banco);
//		return banco;
//	}

	
}
