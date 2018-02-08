/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package druidmetrics;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

    @Test //Add expResult content
    public void testGetCodeToList() throws Exception {
        System.out.println("getCodeToList");
        String pathFile = "src/druidmetrics/JavaMetric.java";
        JavaMetric instance = new JavaMetric();
        List<String> expResult = new ArrayList<>(); 
        List<String> result = instance.getCodeToList(pathFile);
        assertEquals(expResult, result);
    }
    
    @Test //PASSED
    public void testCalculateNoOfPhyLines() throws Exception {
        System.out.println("calculateNoOfPhyLines");
        String pathFile = "src/druidmetrics/Main.java";
        JavaMetric instance = new JavaMetric();
        int expResult = 13;
        List<String> codeArray = instance.getCodeToList(pathFile);
        int result = instance.calculateNoOfPhyLines(codeArray);
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    @Test //PASSED
    public void testCalculateNoOfEffLines() throws Exception {
        System.out.println("calculateNoOfEffLines");
        String pathFile = "src/druidmetrics/Main.java";
        JavaMetric instance = new JavaMetric();
        int expResult = 3;
        List<String> codeArray = instance.getCodeToList(pathFile);
        int result = instance.calculateNoOfEffLines(codeArray);
        System.out.println(result);
        assertEquals(expResult, result);
    }
}