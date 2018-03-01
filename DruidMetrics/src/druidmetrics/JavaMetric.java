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
    
    //Differentiates a line of code and squashed code
    //Can be passed to calculateNoOfEffLines (does not accept empty lines) )
    //First if statement removes comments, won't be added to output.
    public List<String> getSortedCodeToList(List<String> linesOfCode) throws IOException {
        List<String> output = new ArrayList<>();
        for(String s : linesOfCode) {
            if(!s.replaceAll(" ", "").startsWith("//")) {
                String[] snippet = s.split("(?=[{}])|[;]");
                for(String c : snippet)
                    if(!c.trim().isEmpty())
                        output.add(c.trim());
            }
        }
        return output;
    }
    
    //Returns number of physical lines of code
    public int calculateNoOfPhyLines(List<String> linesOfCode) {
        return linesOfCode.size();
    }
    
    //Returns number of Effective lines of code.
    //Won't need to check empty lines if using getSortedCodeToList.
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