package com.algonquincollege.cst8277.assignment3.model;

import org.junit.Test;
import static org.junit.Assert.fail;

import com.algonquincollege.cst8277.assignment3.TestSuiteBase;

public class FailingTestSuite extends TestSuiteBase {
    
    @Test
    public void this_is_a_failing_test() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        fail("the test case called '" + methodName + "' is obviously failing");
    }

}
