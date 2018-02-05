/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package druidmetrics;

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

    @Test
    public void testCalculateNoOfPhyLines() throws Exception {
        System.out.println("calculateNoOfPhyLines");
        String pathFile = "src/druidmetrics/JavaMetric.java";
        JavaMetric instance = new JavaMetric();
        int expResult = 26;
        int result = instance.calculateNoOfPhyLines(pathFile);
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testCalculateNoOfEffLines() throws Exception {
        System.out.println("calculateNoOfEffLines");
        String pathFile = "src/druidmetrics/Main.java";
        JavaMetric instance = new JavaMetric();
        int expResult = 3;
        int result = instance.calculateNoOfEffLines(pathFile);
        System.out.println(result);
        assertEquals(expResult, result);
    }
}
