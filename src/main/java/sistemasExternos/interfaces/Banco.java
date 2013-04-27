package sistemasExternos.interfaces;

public interface Banco {

	boolean autenticarCliente(String Conta, String Senha);

	boolean aprovarSaque(Double Valor, String Conta);

	boolean aprovarDeposito(Double Valor, String Conta); // TODO como avisar que o valor ja foi depositado? criar um novo metodo?	

	boolean autorizarTransferencia(String ContaDebitada, String ContaCreditada);

	String obterSaldo(String Conta);	
	
}
