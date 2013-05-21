package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;

public class BancoMockFactory {

	public static Banco getBancoSenhaInvalida() throws Exception {
		Banco banco = createMock(Banco.class);
		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class))).andThrow(new Exception("Senha Invalida"));
		replay(banco);
		return banco;
	}
	
	public static Banco getBancoNaoaprovaSaque() throws Exception {
		Banco banco = createMock(Banco.class);
		expect(banco.iniciarSessao(anyObject(String.class),	anyObject(String.class), anyObject(String.class))).andThrow(new Exception("Saque Nao Aprovado."));
		replay(banco);
		return banco;
	}

}
