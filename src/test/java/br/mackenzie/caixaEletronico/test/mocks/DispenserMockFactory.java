package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.createNiceMock;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Dispenser;

public class DispenserMockFactory {

	public static Dispenser getDispenserOK() throws Exception {
		Dispenser dispenser = createNiceMock(Dispenser.class);
		return dispenser;		
	}

}
