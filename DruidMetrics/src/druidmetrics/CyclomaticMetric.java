package druidmetrics;

import java.util.List;

public class CyclomaticMetric {
    
    //Only for loops at the moment
    //Put in Cyclomatic Branch
    public int calculateNoOfLoops(List<String> noOfLines) {
        int noOfLoops = 0;
        for(String s : noOfLines) {
            if(s.replaceAll(" ", "").contains("for(") && !s.trim().startsWith("//")) {
                //System.out.println(s);
                noOfLoops++;
            }
        }
        return noOfLoops;
    }     
    
}
