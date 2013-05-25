package br.mackenzie.caixaEletronico.sistemaPrincipal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.mackenzie.caixaEletronico.objetosCompartilhados.Sessao;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Console;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Dispenser;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Impressora;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Log;

public class CaixaEletronico {

	private static final String PARAMETRO_LOCALIZACAO = ", localização: São Paulo";
	private static final String PARAMETRO_HORA = ", hora: ";
	private static final String OPERACAO_INICIALIZACAO_DEPOSITO = "inicialização de depósito";
	private static final String OPERACAO_DEPOSITAR_ENVELOPE = "depósito de envelope";
	private static final String OPERACAO_TRANSFERENCIA = "transferência";
	private static final String OPERACAO_INICIALIZAR_CAIXA = "inicialização do caixa eletrônico";
	private static final String PARAMETRO_SALDO_ATUAL = ", saldo atual: ";
	private static final String PARAMETRO_VALOR_SACADO = ", valor sacado: ";
	private static final String PARAMETRO_VALOR_DEPOSITADO = ", valor depositado: ";
	private static final String PARAMETRO_VALOR_TRANSFERIDO = ", valor transferido: ";
	private static final String PARAMETRO_VALOR_DISPONIVEL_SAQUE = ", valor disponível para saque: ";
	private static final String PARAMETRO_NUMERO_CONTA = ", número da conta: ";
	private static final String PARAMETRO_NUMERO_CONTA_DEBITADA = ", número da conta debitada: ";
	private static final String PARAMETRO_NUMERO_CONTA_CREDITADA = ", número da conta creditada: ";
	private static final String PARAMETRO_DATA = "Data: ";
	private static final String PARAMETRO_NUMERO_CARTAO = "número do cartão: ";
	private static final String MENSAGEM_OPERACAO_FALHA = "Erro ao realizar operação de %s.";
	private static final String MENSAGEM_OPERACAO_FALHA_ERRO = "Erro ao realizar operação de %s. Erro: \"%s\". Informações complementares: \"%s\"";
	private static final String OPERACAO_SAQUE = "saque";
	private static final String MESAGEM_OPERACAO_SUCESSO = "A operação de %s foi realizada com sucesso. %s";
	private static final String MENSAGEM_SACAR_VALOR_SUPERIOR_AO_DISPONIVEL = "O valor solicitado é superior ao disponível para saque.";
	private static final String MENSAGEM_SACAR_VALOR_DEVE_SER_MULTIPLO_DE_10 = "Valor inválido. Entre com um valor múltiplo de dez.";
	private static final String MENSAGEM_VALOR_MENOR_QUE_ZERO = "Valor inválido. Entre com um valor maior ou igual a zero.";
	private static final String MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO = "Valor inválido. Entre com um valor maior que zero.";
	private static final String MENSAGEM_CAIXA_INOPERANTE = "Não foi possível realizar a operação pois o caixa não se encontra operante no momento.";

	private static enum Estado {
		OPERANTE, INOPERANTE;
	}

	private final Banco banco;
	private final Console console;
	private Estado estado = Estado.INOPERANTE;
	private double valorDisponivel;
	private Sessao sessao;
	private Dispenser dispenser;
	private Log log;
	private Impressora impressora;

	public CaixaEletronico(Banco banco, Console console, Dispenser dispenser, Log log, Impressora impressora) {
		this.banco = banco;
		this.console = console;
		this.dispenser = dispenser;
		this.log = log;
		this.impressora = impressora;
	}

	public boolean iniciarSessao(String cartao, String senha, String conta) {
		try {
			this.sessao = banco.iniciarSessao(cartao, senha, conta);
			return true;
		} catch (Exception ex) {
			this.sessao = null;
			console.imprimir(ex.getMessage());
			return false;
		}
	}

	public boolean sacarValor(double valor, Sessao sessao, String conta) {

		StringBuilder complementoMensagem = criaComplementoMensagem();
		complementoMensagem.append(PARAMETRO_NUMERO_CARTAO);
		complementoMensagem.append(sessao.getCartao());
		complementoMensagem.append(PARAMETRO_NUMERO_CONTA);
		complementoMensagem.append(conta);
		complementoMensagem.append(PARAMETRO_VALOR_SACADO);
		complementoMensagem.append(formatarValor(valor));
		if (estado != Estado.OPERANTE) {
			throw new IllegalStateException(MENSAGEM_CAIXA_INOPERANTE);
		} else if (valor <= 0) {
			console.imprimir(MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO);
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_SAQUE, MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO, complementoMensagem);
		} else if (valor % 10 != 0) {
			console.imprimir(MENSAGEM_SACAR_VALOR_DEVE_SER_MULTIPLO_DE_10);
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_SAQUE, MENSAGEM_SACAR_VALOR_DEVE_SER_MULTIPLO_DE_10,
					complementoMensagem);
		} else if (valor > this.valorDisponivel) {
			console.imprimir(MENSAGEM_SACAR_VALOR_SUPERIOR_AO_DISPONIVEL);
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_SAQUE, MENSAGEM_SACAR_VALOR_SUPERIOR_AO_DISPONIVEL,
					complementoMensagem);
		} else {
			try {
				banco.sacar(sessao, conta, valor);
				complementoMensagem.append(PARAMETRO_SALDO_ATUAL);
				complementoMensagem.append(formatarValor(banco.obterSaldo(sessao, conta)));
				dispenser.darNotas(valor);

				log.logarOperacao(MESAGEM_OPERACAO_SUCESSO, OPERACAO_SAQUE, complementoMensagem);
				impressora.imprimirRecibo(complementoMensagem.toString());
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
				log.logarOperacao(MENSAGEM_OPERACAO_FALHA, OPERACAO_SAQUE, ex.getMessage(), complementoMensagem);
			}
		}
		return false;
	}

	private static StringBuilder criaComplementoMensagem() {
		Date dataHora = new Date();
		StringBuilder complementoMensagem = new StringBuilder(PARAMETRO_DATA);
		complementoMensagem.append(new SimpleDateFormat("dd/MM/yyyy").format(dataHora));
		complementoMensagem.append(PARAMETRO_HORA);
		complementoMensagem.append(new SimpleDateFormat("hh:mm:ss").format(dataHora));
		complementoMensagem.append(PARAMETRO_LOCALIZACAO);
		return complementoMensagem;
	}

	private static String formatarValor(double valor) {
		return new DecimalFormat("$0.00").format(valor);
	}

	public boolean depositarValor(Sessao sessao, String conta, double valor) {
		StringBuilder complementoMensagem = criaComplementoMensagem();
		complementoMensagem.append(PARAMETRO_NUMERO_CARTAO);
		complementoMensagem.append(sessao.getCartao());
		complementoMensagem.append(PARAMETRO_NUMERO_CONTA);
		complementoMensagem.append(conta);
		complementoMensagem.append(PARAMETRO_VALOR_DEPOSITADO);
		complementoMensagem.append(formatarValor(valor));
		if (estado != Estado.OPERANTE) {
			throw new IllegalStateException(MENSAGEM_CAIXA_INOPERANTE);
		} else if (valor <= 0) {
			console.imprimir(MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO);
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_INICIALIZACAO_DEPOSITO, MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO,
					complementoMensagem);
		} else {
			try {
				banco.iniciarDeposito(sessao, conta, valor);
				complementoMensagem.append(PARAMETRO_SALDO_ATUAL);
				complementoMensagem.append(formatarValor(banco.obterSaldo(sessao, conta)));

				log.logarOperacao(MESAGEM_OPERACAO_SUCESSO, OPERACAO_INICIALIZACAO_DEPOSITO, complementoMensagem);
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
				log.logarOperacao(MENSAGEM_OPERACAO_FALHA, OPERACAO_INICIALIZACAO_DEPOSITO, ex.getMessage(), complementoMensagem);
			}
		}
		return false;
	}

	public boolean depositarEnvelope(Sessao sessao, String conta) {
		if (estado != Estado.OPERANTE) {
			throw new IllegalStateException(MENSAGEM_CAIXA_INOPERANTE);
		}
		StringBuilder complementoMensagem = criaComplementoMensagem();
		complementoMensagem.append(PARAMETRO_NUMERO_CARTAO);
		complementoMensagem.append(sessao.getCartao());
		complementoMensagem.append(PARAMETRO_NUMERO_CONTA);
		complementoMensagem.append(conta);
		try {
			banco.sinalizarDepositoEnvelope(sessao, conta);
			log.logarOperacao(MESAGEM_OPERACAO_SUCESSO, OPERACAO_DEPOSITAR_ENVELOPE, complementoMensagem);
			return true;
		} catch (Exception ex) {
			console.imprimir(ex.getMessage());
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA, OPERACAO_DEPOSITAR_ENVELOPE, ex.getMessage(), complementoMensagem);
		}
		return false;
	}

	public boolean transferir(Sessao sessao, String contaDebitada, String contaCreditada, double valor) {
		StringBuilder complementoMensagem = criaComplementoMensagem();
		complementoMensagem.append(PARAMETRO_NUMERO_CARTAO);
		complementoMensagem.append(sessao.getCartao());
		complementoMensagem.append(PARAMETRO_NUMERO_CONTA_DEBITADA);
		complementoMensagem.append(contaDebitada);
		complementoMensagem.append(PARAMETRO_NUMERO_CONTA_CREDITADA);
		complementoMensagem.append(contaCreditada);
		complementoMensagem.append(PARAMETRO_VALOR_TRANSFERIDO);
		complementoMensagem.append(formatarValor(valor));
		if (estado != Estado.OPERANTE) {
			throw new IllegalStateException(MENSAGEM_CAIXA_INOPERANTE);
		} else if (valor <= 0) {
			console.imprimir(MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO);
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_TRANSFERENCIA, MENNSAGEM_VALOR_MENOR_OU_IGUAL_A_ZERO, complementoMensagem);
		}
		try {
			banco.transferir(sessao, contaDebitada, contaCreditada, valor);
			log.logarOperacao(MESAGEM_OPERACAO_SUCESSO, OPERACAO_TRANSFERENCIA, complementoMensagem);
			return true;
		} catch (Exception ex) {
			console.imprimir(ex.getMessage());
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_TRANSFERENCIA, ex.getMessage(), complementoMensagem);
		}
		return false;
	}
	
	public boolean consultarSaldo(Sessao sessao, String conta) {
		return false;
	}
	
	public boolean anularOperacao(Sessao sessao) {
		return false;
	}

	public boolean inicializarCaixa(double valorDisponivelSaque) {
		StringBuilder complementoMensagem = criaComplementoMensagem();
		complementoMensagem.append(PARAMETRO_VALOR_DISPONIVEL_SAQUE);
		complementoMensagem.append(formatarValor(valorDisponivelSaque));
		if (valorDisponivelSaque < 0) {
			console.imprimir("Não é possível inicializar o caixa eletrônico com um valor inferior a zero.");
			log.logarOperacao(MENSAGEM_OPERACAO_FALHA_ERRO, OPERACAO_INICIALIZAR_CAIXA, MENSAGEM_VALOR_MENOR_QUE_ZERO, complementoMensagem);
			return false;
		}
		valorDisponivel = valorDisponivelSaque;
		estado = Estado.OPERANTE;
		log.logarOperacao(MESAGEM_OPERACAO_SUCESSO, OPERACAO_INICIALIZAR_CAIXA, MENSAGEM_VALOR_MENOR_QUE_ZERO, complementoMensagem);
		return true;
	}

	public boolean encerrarCaixa() {
		if (sessao != null) {
			try {
				banco.finalizarSessao(sessao);
			} catch (Exception ex) {
				console.imprimir(ex.getMessage());
				return false;
			}
		}

		estado = Estado.INOPERANTE;
		return true;

	}

}