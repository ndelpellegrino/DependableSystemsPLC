package druidmetrics;

import java.io.IOException;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws IOException {
       
        JavaMetric testMetric = new JavaMetric();
        List<String> testArray1 = testMetric.getCodeToList("src/druidmetrics/Main.java");
        List<String> testArray2 = testMetric.getSortedCodeToList(testArray1); 
        
        int y = 10;
        int z = 11;
        for(int i = 0; i < y; i++) {
            
        }
            
        System.out.println(testMetric.calculateNoOfEffLines(testArray1));
        System.out.println(testMetric.calculateNoOfEffLines(testArray2));
        
        int x;}
    }