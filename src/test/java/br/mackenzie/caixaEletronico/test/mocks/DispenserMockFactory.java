package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.createMock;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Dispenser;

public class DispenserMockFactory {

	public static Dispenser getDispenserOK() throws Exception {
		Dispenser dispenser = createMock(Dispenser.class);
		return dispenser;		
	}

}
