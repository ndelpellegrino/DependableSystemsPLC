package druidmetrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws IOException {
       
        
        BaseMetric testMetric = new BaseMetric();
        JavaMetric cycloMetric = new JavaMetric();
        
        List<String> testArray1 = testMetric.getCodeToList("src/druidmetrics/Main.java");
        List<String> testArray2 = testMetric.getSortedCodeToList(testArray1);
        
        //Physical Lines, 22
        System.out.println(testMetric.calculateNoOfPhyLines(testArray1));
        
        //Effective Lines, 12
        System.out.println(testMetric.calculateNoOfEffLines(testArray2));
        
        //Test Number of Loops
        System.out.println(cycloMetric.calculateNoOfLoops(testArray2));
        
    }
}