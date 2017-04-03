package org.eagle.rpc.api;

import org.eagle.common.api.Lifecycle;

public abstract class AbstractCallerInvoker implements Lifecycle,Invoker{
	
	@Override
	public void init() {
		initInternal();
	}

	protected abstract void initInternal();
	
	public void invokeGeneric(){
		
	}
	
	
}
