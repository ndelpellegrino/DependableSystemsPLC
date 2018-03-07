package druidmetrics;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JavaMetric implements CyclomaticMetric {
    
    private String[] loopWords = {"for(", "while("};
    private String[] exceptionWords = {"throws","catch"};
    private String[] returnWords = {"return"};
    private String[] conditionWords = {"if(", "elseif{", "switch("};

    //The List<> parameter needs to be the return of getSortedCodeToList()
    //Acceptable loops: for() while() do-while()
    //Possible improvements: streams
    @Override
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
    
     @Override
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
    @Override
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
    @Override
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