package org.eagle.common;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.junit.Test;

public class testRPCException {

	@Test
	public void testThrowRPCException() throws RPCException{
		throw new RPCException(RPCExcptionStatus.SERVER_PORT_NOT_CLAIM);
	}
}
