package br.mackenzie.caixaEletronico.sistemaPrincipal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Sessao {
	private final String chave;
	private final String conta;

	public Sessao(String conta) throws NoSuchAlgorithmException {
		this.conta = conta;
		this.chave = construirChave();
	}

	private String construirChave() throws NoSuchAlgorithmException {
		String chave = new Date().toString();
		MessageDigest md = MessageDigest.getInstance("MD5");
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

	public String getChave() {
		return chave;
	}

	public String getConta() {
		return conta;
	}

}