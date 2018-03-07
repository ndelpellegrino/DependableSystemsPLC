package druidmetrics;

import java.util.List;

public interface CyclomaticMetric {
    
    public int calculateNoOfLoops(List<String> noOfLines);
    public int calculateNoOfExceptions(List<String> noOfLines);
    public int calculateNoOfReturns(List<String> noOfLines);
    public int calculateNoOfConditions(List<String> noOfLines);
         
}
