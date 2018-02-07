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

    public int calculateNoOfPhyLines(String pathFile) throws IOException{
    
        try (InputStream is = new BufferedInputStream(new FileInputStream(pathFile))) {
            byte[] c = new byte[1024];
            int phyLinesOfCode = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for(int i = 0; i < readChars; ++i)
                    if (c[i] == '\n') {
                        ++phyLinesOfCode;
                    }
            }
            return (phyLinesOfCode == 0 && !empty) ? 1 : phyLinesOfCode;
        }
    }
    
    //Checks {, }, //, /*, */, empty lines.
    //Counts only line of code.
    //Issues with multiple line sof code.
    //Last Edited: Mohammad
    public int calculateNoOfEffLines(String pathFile) throws FileNotFoundException, IOException {
        int effLines = 0;
        List<String> linesOfCode = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new FileReader(pathFile));
        String line = bufReader.readLine();
        while(line != null) {
            linesOfCode.add(line);
            line = bufReader.readLine();
        }
        for(String s : linesOfCode) {
            s = s.trim();
            if(!s.equals("}") && !s.equals("{") && !s.startsWith("//") && !s.startsWith("/*") && !s.startsWith("*/") && !s.isEmpty())
                effLines++;   
        }
        return effLines;
    }
    
}
