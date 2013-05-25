package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.createNiceMock;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Impressora;

public class ImpressoraMockFactory {

	public static Impressora getImpressoraOK() throws Exception {
		Impressora impressora = createNiceMock(Impressora.class);
		return impressora;		
	}
	
}
