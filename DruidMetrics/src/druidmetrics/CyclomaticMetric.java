package druidmetrics;

import java.util.List;

public class CyclomaticMetric {
    
    //Only for loops at the moment
    //The List<> parameter needs to be the return of getSortedCodeToList()
    //Acceptable loops: for() while() .stream(maybe)
    public int calculateNoOfLoops(List<String> noOfLines) {
        int noOfLoops = 0;
        for(String s : noOfLines) {
            if(s.replaceAll(" ", "").contains("for(") || s.replaceAll(" ", "").contains("while(") && !s.trim().startsWith("//")) {
                System.out.println(s);
                noOfLoops++;
            }
        }
        return noOfLoops;
    }     
    
}
