package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cetiti.ddapv2.process.dao.RuleDao;
import com.cetiti.ddapv2.process.model.RuleExpression;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml", 
		"classpath:spring/httpClient.xml"})
public class RuleDaoImpleTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private RuleDao ruleDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertRule() {
		RuleExpression rule = new RuleExpression();
		rule.setName("testRuleName");
		rule.setDescription("testRuleDescription");
		rule.setDeviceId("D1505467235604");
		
		rule.setExpression("temperature<=40&&humidity>60");
		rule.setOwner("admin");
		ruleDao.insertRule(rule);
	}

	@Test
	public void testDeleteRule() {
		ruleDao.deleteRule("R1505886702480");
	}

	@Test
	public void testUpdateRule() {
		RuleExpression rule = new RuleExpression();
		rule.setId("P1505886386875");
		rule.setName("testRuleNameu");
		rule.setDescription("testRuleDescriptionu");
		rule.setDeviceId("P150148832294u");
		rule.setProductId("D150546723560u");
		rule.setExpression("data<=10&&data>0u");
		rule.setOwner("adminu");
		ruleDao.updateRule(rule);
	}

	@Test
	public void testSelectRule() {
		RuleExpression rule = ruleDao.selectRule("R1505886702480");
		System.out.println("rule:"+rule);
	}

	@Test
	public void testSelectRuleList() {
		RuleExpression rule = new RuleExpression();
		rule.setName("testRuleName");
		rule.setDescription("testRuleDescription");
		rule.setDeviceId("P1501488322947");
		rule.setProductId("D1505467235604");
		rule.setExpression("data<=10&&data>0");
		rule.setOwner("admin");
		ruleDao.selectRuleList(rule).stream().forEach(System.out::println);
	}

}
