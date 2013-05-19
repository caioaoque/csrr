package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;

public class BancoMockFactory {

	public Banco getBancoNaoAprovaSaque() {
		Banco banco = createMock(Banco.class);
		expect(banco.sacar(anyObject(String.class), anyObject(Double.class)))
				.andThrow(new Exception("Saque Não Aprovado"));
		replay(banco);
		return banco;
	}

	public Banco getBancoAprovaSaque() {
		Banco banco = createMock(Banco.class);
		expect(banco.sacar(anyObject(String.class), anyObject(Double.class)))
				.andReturn(true);
		replay(banco);
		return banco;
	}

	public Banco getBancoNaoAprovaDeposito() {
		Banco banco = createMock(Banco.class);
		expect(
				banco.iniciarDeposito(anyObject(String.class),
						anyObject(Double.class))).andThrow(
				new Exception("Deposito Não Aprovado"));
		replay(banco);
		return banco;
	}

	public Banco getBancoAprovaDeposito() {
		Banco banco = createMock(Banco.class);
		expect(
				banco.iniciarDeposito(anyObject(String.class),
						anyObject(Double.class))).andReturn(true);
		replay(banco);
		return banco;
	}

	public static Banco getBancoSenhaInvalida() throws Exception {
		Banco banco = createMock(Banco.class);
		expect(
				banco.iniciarSessao(anyObject(String.class),
						anyObject(String.class), anyObject(String.class)))
				.andThrow(new Exception("Senha Invalida"));
		replay(banco);
		return banco;
	}

}
