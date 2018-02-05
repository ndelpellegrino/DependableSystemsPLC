package druidmetrics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    
    public int calculateNoOfEffLines(String pathFile) throws IOException{
    
        try (InputStream is = new BufferedInputStream(new FileInputStream(pathFile))) {
            byte[] c = new byte[1024];
            int effLinesOfCode = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for(int i = 0; i < readChars; ++i){
                    if(c[i] == '\t' || c[i] == '}' || c[i] == '{'){
                        --effLinesOfCode;
                        System.out.println("Minus 1 line");
                    }
                    if (c[i] == '\n') {
                        ++effLinesOfCode;
                        System.out.println("Plus 1 line");
                    } 
                }
            }
            return (effLinesOfCode == 0 && !empty) ? 1 : effLinesOfCode;
        }
    }

}
