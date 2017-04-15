package org.eagle.boot.api;

import java.util.List;

import org.eagle.common.annotation.API;
import org.eagle.common.annotation.Arg;

public interface ICallMyApple {

	@API("transferSomething")
	Student transferSomething(@Arg("req") List<String> student );
	
	@API("action")
	String action(@Arg("req1") int a);
}
