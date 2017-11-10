package com.cetiti.ddapv2.process.spi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CodecFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Codec<String, String> codec = CodecFactory.getCodec("foo");
		String retn =  codec.encode("123");
		System.out.println(retn);
	}

}
