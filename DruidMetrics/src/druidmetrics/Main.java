package druidmetrics;

import java.io.IOException;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws IOException {
       
        int x = 10; String s = "Hello";
        
        JavaMetric testMetric = new JavaMetric();
        List<String> testArray1 = testMetric.getCodeToList("src/druidmetrics/Main.java");
        List<String> testArray2 = testMetric.getSortedCodeToList(testArray1);
        
        //Physical Lines, 22
        System.out.println(testMetric.calculateNoOfPhyLines(testArray1));
        
        //Effective Lines, 12
        System.out.println(testMetric.calculateNoOfEffLines(testArray2));
        
    }
}