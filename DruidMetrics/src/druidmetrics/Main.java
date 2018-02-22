package druidmetrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws IOException {
       
        JavaMetric testMetric = new JavaMetric();
        List<String> testArray1 = testMetric.getCodeToList("src/druidmetrics/Main.java");
        List<String> testArray2 = testMetric.getSortedCodeToList("src/druidmetrics/Main.java"); 
        
        int x = 1;
        int y = 2;
        int z = 1;
        List<Integer> intArray = new ArrayList<>(Arrays.asList(1));
        
        //Test for loop
        for(int i = 0; i < x; i++) {
            System.out.println("This is a for loop");
        }
        
        //Test forEach loop
        for(Integer currentInt : intArray) {
            System.out.println("This is a forEach loop");
        }
        
        //Test while loop
        while(x < y) {
            System.out.println("This is a while loop");
            x++;
        }
        
        //Test do-while loop
        
        //do {
        //    System.out.println("This is a do-while loop");
        //    z++;
        //} while(z < y);
        
        JavaMetric instance = new JavaMetric();
        String pathFile = "src/druidmetrics/Main.java";
        List<String> stringArray = instance.getSortedCodeToList(pathFile);
        for(String s : stringArray) {
            System.out.println(s);
        }
        
    }
}