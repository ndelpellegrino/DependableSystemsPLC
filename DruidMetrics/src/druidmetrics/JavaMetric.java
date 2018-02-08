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

public class JavaMetric {
    
    //Grabs code content from specified path into ArrayList.
    public List<String> getCodeToList(String pathFile) throws FileNotFoundException, IOException {
        List<String> linesOfCode = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new FileReader(pathFile));
        String line = bufReader.readLine();
        while(line != null) {
            linesOfCode.add(line);
            line = bufReader.readLine();
        }
        return linesOfCode;
    }
    
    //Returns number of lines of code
    public int calculateNoOfPhyLines(List<String> linesOfCode) {
        int noOfLines = linesOfCode.size();
        return noOfLines;
    }
    
    //Returns number of Effective lines of code
    public int calculateNoOfEffLines(List<String> linesOfCode) {
        int effLines = 0;
        for(String s : linesOfCode) {
            s = s.trim();
            if(!s.equals("}") && !s.equals("{") && !s.startsWith("//") && !s.startsWith("/*") && !s.startsWith("*/") && !s.isEmpty())
                effLines++;   
        }
        return effLines;
    }
    
}