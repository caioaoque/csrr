package br.mackenzie.caixaEletronico.objetosCompartilhados;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Sessao implements Serializable {
	
	private static final long serialVersionUID = -136200824031958169L;
	
	private final String cartao;
	private Date ultimaOperacao;
	private final String chave;
	
	public Sessao(String cartao) {
		this.cartao = cartao;
		this.ultimaOperacao = new Date();
		this.chave = construirChave();
	}
	
	public String getCartao() {
		return cartao;
	}

	public Date getUltimaOperacao() {
		return ultimaOperacao;
	}
	public void setUltimaOperacao(Date ultimaOperacao) {
		this.ultimaOperacao = ultimaOperacao;
	}
	
	public String getChave() {
		return chave;
	}

	private static String construirChave() {
		String chave = new Date().toString();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return "" + chave.hashCode();
		}
		md.reset();
		md.update(chave.getBytes());
		byte[] hashMd5 = md.digest();


		StringBuilder s = new StringBuilder();
		for (int i = 0; i < hashMd5.length; i++) {
			int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
			int parteBaixa = hashMd5[i] & 0xf;
			if (parteAlta == 0)
				s.append('0');
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}


		return s.toString();
	}

}