package sistemasExternos.interfaces;

import java.util.HashMap;
import java.util.Date;

public class BancoMackenzie extends Banco {
    
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

        m_contas.put(conta.numeroConta, conta);

        Conta conta2 = new Conta();
        conta2.numeroConta = "002";
        conta2.senha = "4321";
        conta2.saldo = 400;
        conta2.cliente = cliente;

        m_contas.put(conta2.numeroConta, conta2);
    }
    
    public String iniciarSessao(String conta, String senha)
    {
        if(!m_contas.containsKey(conta))
        {
            setLastError("Conta invalida");
            return "";
        }
        
        if(!m_contas.get(conta).senha.equals(senha))
        {
            setLastError("Senha invalida");
            return "";
        }
        
        Sessao sessao = new Sessao();
        sessao.chaveSessao = "frefgefrefgeg";
        sessao.contaSessao = conta;
        sessao.ultimaOperacao = new Date();

        m_sessoes.put(sessao.chaveSessao, sessao);

        return sessao.chaveSessao;
    }
    
    public boolean aprovarSaque(String sessao, double valor)
    {
        return true;
    }
    
    private HashMap<String, Conta> m_contas = new HashMap<>(); 
    private HashMap<String, Sessao> m_sessoes = new HashMap<>();
}
