package br.mackenzie.caixaEletronico.sistemasExternos;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Banco;


public class BancoMackenzie implements Banco {
	
	private String ultimoErro;
    
    public BancoMackenzie()
    {
        Cliente cliente = new Cliente();
        cliente.nome = "Rafael";
        cliente.contas.add("001");
        cliente.contas.add("002");

        Conta conta = new Conta();
        conta.numeroConta = "001";
        conta.senha = "1234";
        conta.saldo = 10000;
        conta.cliente = cliente;

        contas.put(conta.numeroConta, conta);

        Conta conta2 = new Conta();
        conta2.numeroConta = "002";
        conta2.senha = "4321";
        conta2.saldo = 400;
        conta2.cliente = cliente;

        contas.put(conta2.numeroConta, conta2);
    }
    
    public String iniciarSessao(String conta, String senha)
    {
        if(!contas.containsKey(conta))
        {
            setUltimoErro("Conta inválida");
            return "";
        }
        
        if(!contas.get(conta).senha.equals(senha))
        {
            setUltimoErro("Senha inválida");
            return "";
        }
        
        Sessao sessao = new Sessao();      
        sessao.contaSessao = conta;
        sessao.ultimaOperacao = new Date();
        
        try
        {
            String chave = sessao.ultimaOperacao.toString();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(chave.getBytes());
            byte[] hashMd5 = md.digest();
            
            StringBuilder s = new StringBuilder();      
            for (int i = 0; i < hashMd5.length; i++) {
                int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
                int parteBaixa = hashMd5[i] & 0xf;
                if (parteAlta == 0) s.append('0');
                s.append(Integer.toHexString(parteAlta | parteBaixa));
            }
            
            sessao.chaveSessao = s.toString();
        }
        catch (Exception e){
        }

        sessoes.put(sessao.chaveSessao, sessao);

        return sessao.chaveSessao;
    }
    
    private boolean validaSessao(String sessao)
    {
        if(!sessoes.containsKey(sessao))
        {
            setUltimoErro("Sessão inválida");
            return false;
        }
        
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(sessoes.get(sessao).ultimaOperacao);
        gc.add(Calendar.SECOND, 120);
        
        if(gc.getTime().before(new Date()))
        {
            sessoes.remove(sessao);
            setUltimoErro("Sessão expirada");
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean aprovarSaque(String sessao, double valor)
    {
        if(!validaSessao(sessao))
        {
            return false;
        }

        Conta conta = contas.get(sessoes.get(sessao).contaSessao);

        if(conta.saldo < valor)
        {
            setUltimoErro("Saldo insuficiente");
            return false;
        }

        conta.saldo -= valor;

        return true;
    }
    
    private HashMap<String, Conta> contas = new HashMap<String, Conta>();
    private HashMap<String, Sessao> sessoes = new HashMap<String, Sessao>();
    
	@Override
	public String getUltimoErro() { return ultimoErro; }

	@Override
	public void setUltimoErro(String ultimoErro) {
		this.ultimoErro = ultimoErro;
	}
}
