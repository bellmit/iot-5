package com.cetiti.ddapv2.process.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class RuleExecutorTest {
	
	private RuleExecutor fel = new FelRuleExecutor();
	private RuleExecutor aviator = new AviatorRuleExecutor();
	private Random random = new Random();
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testExec() {
		String rule = "20<T&&T<50&&60<H&&H<95";
		//String rule = "20<T";
		Map<String, Object> data = new HashMap<>();
		data.put("T", 30);
		data.put("H", 80);
		data.put("M", 100);
		data.put("S", 1.23);
		assertTrue(fel.isDataLegal(rule, data));
		//assertTrue(aviator.isDataLegal(rule, data));
		data.put("T", 0);
		assertFalse(fel.isDataLegal(rule, data));
		//assertFalse(aviator.isDataLegal(rule, data));
		long start = System.nanoTime();
		for(int i=0; i<10000; i++){
			data.put("T", random.nextInt(100));
			data.put("H", random.nextInt(100));
			fel.isDataLegal(rule, data);
		}
		System.out.println("fel: "+(System.nanoTime()-start)/10000000);
		start = System.nanoTime();
		for(int i=0; i<10000; i++){
			data.put("T", random.nextInt(100));
			data.put("H", random.nextInt(100));
			aviator.isDataLegal(rule, data);
		}
		System.out.println("aviator: "+(System.nanoTime()-start)/10000000);
	}
	
	@Ignore
	public void testfel(){
		/*FelEngine fel = new FelEngineImpl();
		FelContext ctx= fel.getContext();
		ctx.set("A", 5);
		ctx.set("B", 12);
		ctx.set("C", 75);
		Object result= fel.eval("A>1&&A<10&&B<20");
		System.out.println(result);*/
		
		FelEngine fel= new FelEngineImpl();
		FelContext ctx= fel.getContext();
		ctx.set("单价", 5000);
		ctx.set("数量", 12);
		ctx.set("运费", 7500);
		Expression exp= fel.compile("单价*数量+运费",ctx);
		Object result= exp.eval(ctx);
		System.out.println(result);
	}
	
	@Test
	public void testFloat(){
		float f = (float) (100/3.0);
		System.out.println(f);
	}

}
