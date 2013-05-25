package br.mackenzie.caixaEletronico.test.mocks;

import static org.easymock.EasyMock.createMock;
import br.mackenzie.caixaEletronico.sistemasExternos.interfaces.Log;

public class LogMockFactory {

	public static Log getLogOK() throws Exception {
		Log log = createMock(Log.class);
		return log;		
	}
	
	
}
