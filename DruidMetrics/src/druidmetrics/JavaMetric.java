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
}
