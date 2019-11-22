package com.algonquincollege.cst8277.assignment4.model;

import org.junit.Test;

import com.algonquincollege.cst8277.assignment4.TestSuiteBase;

import static org.junit.Assert.fail;

public class FailingTestSuite extends TestSuiteBase {
    
    @Test
    public void this_is_a_failing_test() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        fail("the test case called '" + methodName + "' is obviously failing");
    }

}
