package druidmetrics;

import java.util.List;

public class CyclomaticMetric {
    
    private String[] loopWords = {"for(", "while("};
    private String[] exceptionWords = {"throws","catch"};
    private String[] returnWords = {"return"};
    private String[] conditionWords = {"if(", "elseif{", "switch("};
    
    //The List<> parameter needs to be the return of getSortedCodeToList()
    //Acceptable loops: for() while() do-while()
    //Possible improvements: streams
    public int calculateNoOfLoops(List<String> noOfLines) {
        int noOfLoops = 0;
        for(String s : noOfLines) {
            for(String keyWord : loopWords) {
                s = s.replaceAll(" ", "");
                if(s.contains(keyWord) && !s.trim().startsWith("//"))
                    noOfLoops++;
            }
        }
        return noOfLoops;
    }
    
    public int calculateNoOfExceptions(List<String> noOfLines) {
        int noOfExceptions = 0;
        for(String s : noOfLines) {
            s = s.replaceAll(" ", "");
            for(String keyWord : exceptionWords) {
                if(s.contains(keyWord) && !s.trim().startsWith("//"))
                    noOfExceptions++;
            }
        }
        return noOfExceptions;
    }
    
    //Need to test
    //returns number of return statements
    //Won't have to check for comments, as getSortedCodeToList ignores them.
    public int calculateNoOfReturns(List<String> noOfLines) {
      int noOfReturns = 0;
        for(String s : noOfLines) {
            s = s.replaceAll(" ", "");
            for(String keyWord : returnWords) {
                if(s.contains(keyWord) && !s.trim().startsWith("//"))
                    noOfReturns++;
            }
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
            for(String keyWord : conditionWords) {
                if(s.contains(keyWord) && !s.trim().startsWith("//"))
                    noOfConditions++;
            }
        }
        return noOfConditions;
    }
    
}
