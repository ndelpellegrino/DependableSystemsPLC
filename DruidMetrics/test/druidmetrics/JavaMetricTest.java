/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package druidmetrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author k1459663
 */
public class JavaMetricTest {
    
    public JavaMetricTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    //SortedCodeToList must be passed to calculateNoEfflines()
    //for correct behaviour
    @Test //PASSED
    public void testCalclateNoEfflines() throws Exception {
        System.out.println("calculateNoOfEffLines");
        String pathFile = "src/druidmetrics/codeTest.txt";
        BaseMetric instance = new BaseMetric();
        int expResult = 7;
        List<String> codeArray = instance.getCodeToList(pathFile);
        List<String> finalArray = instance.getSortedCodeToList(codeArray);
        int result = instance.calculateNoOfEffLines(finalArray);
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    
}