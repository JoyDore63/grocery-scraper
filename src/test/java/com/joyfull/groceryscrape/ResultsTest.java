package com.joyfull.groceryscrape;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.joyfull.groceryscrape.Result;
import com.joyfull.groceryscrape.Results;

public class ResultsTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultsTest.class);

	@Test
	public void testResultsTotal() {
		Result p1 = new Result("Avacodo", 12345L, 1.8, "Fresh and green");
		Result p2 = new Result("Peach", 12345L, 1.2, "Fresh and yellow");
		Result p3 = new Result("Plum", 12345L, 1.5, "Fresh and purple");
		ArrayList<Result> results_list = new ArrayList<Result>(3);
		results_list.add(p1);
		results_list.add(p2);
		results_list.add(p3);
		
		Results results = new Results(results_list);
		logger.info(results.toString());
		
		assertEquals("4.5", results.getTotal());
	}

}
