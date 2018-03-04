package druidmetrics;

import java.util.List;

public class CyclomaticMetric {
    
    //The List<> parameter needs to be the return of getSortedCodeToList()
    //Acceptable loops: for() while() do-while()
    //Possible improvements: streams
    public int calculateNoOfLoops(List<String> noOfLines) {
        int noOfLoops = 0;
        for(String s : noOfLines) {
            if(s.replaceAll(" ", "").contains("for(") || s.replaceAll(" ", "").contains("while(") && !s.trim().startsWith("//"))
                noOfLoops++;
        }
        return noOfLoops;
    }     
    
    public int calculateNoOfExceptions(List<String> noOfLines) {
        int noOfExceptions = 0;
        for(String s : noOfLines) {
            if(s.replaceAll(" ", "").contains("throws") || s.replaceAll(" ", "").contains("catch") && !s.trim().startsWith("//"))
                return noOfExceptions++;
        }
        return noOfExceptions;
    }
    
    //Need to test
    //returns number of return statements
    //Won't have to check for comments, as getSortedCodeToList ignores them.
    public int calculateNoOfReturns(List<String> noOfLines) {
        int noOfReturns = 0;
        for(String s : noOfLines) {
            if(s.trim().replaceAll(" ", "").startsWith("return"))
                noOfReturns++;
        }
        return noOfReturns;
    }
    
    //Need to test
    //returns number of conditions
    //Acceptable Conditions: if, else-if, switch
    //Possible improvements: ternary operator, 
    public int calculateNoOfConditions(List<String> noOfLines) {
        int noOfConditions = 0;
        for(String s : noOfLines) {
            s = s.replaceAll(" ", "");
            if(s.contains("if(") || s.contains("elseif(") || s.contains("switch("))
                noOfConditions++;
        }
        return noOfConditions;
    }
    
}
