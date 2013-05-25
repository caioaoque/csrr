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

	public static Banco getBancoOK() throws Exception {
		Banco banco = createNiceMock(Banco.class);
		replay(banco);
		return banco;
	}	
	
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
		banco.sacar(anyObject(Sessao.class), anyObject(String.class), anyDouble());
		expectLastCall().andThrow(new Exception("Saque Nao Aprovado."));
		replay(banco);
		return banco;
	}

	public static Banco getBancoNaoAprovaDeposito() throws Exception {
		Sessao sessao = new Sessao("111111");
		Banco banco = createMock(Banco.class);
		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class))).andStubReturn(sessao);
		banco.iniciarDeposito(anyObject(Sessao.class), anyObject(String.class), anyDouble());		
		expectLastCall().andThrow(new Exception("Deposito Nao Aprovado."));
		replay(banco);
		return banco;
	}

	public static Banco getBancoNaoRealizaTransferencia() throws Exception {
		Sessao sessao = new Sessao("111111");
		Banco banco = createMock(Banco.class);
		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class))).andStubReturn(sessao);
		banco.transferir(anyObject(Sessao.class), anyObject(String.class), anyObject(String.class), anyDouble());	
		expectLastCall().andThrow(new Exception("Transferencia nao aprovada. O saldo é inferior ao valor da transferencia!"));
		replay(banco);
		return banco;
	}	
	
	public static Banco getBancoConsultaSaldo300Reais() throws Exception {
		Banco banco = createMock(Banco.class);
		expect(banco.consultarSaldo(anyObject(Sessao.class), anyObject(String.class))).andReturn(300.0);
		replay(banco);
		return banco;
	}	
	
	public static Banco getBancoCancelaOperacao() throws Exception {
		Banco banco = createMock(Banco.class);
		banco.anularOperacao(anyObject(Sessao.class));
		expectLastCall().once();
		replay(banco);
		return banco;
	}	
	
}
