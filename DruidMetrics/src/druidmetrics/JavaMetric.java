package druidmetrics;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;




public class JavaMetric implements MaintainabilityIndexMetric{
    String pathFile;
    
    public JavaMetric(String pathFile){
        this.pathFile = pathFile;
    }
    
    // Halstead metrics
    public double calculateCommentPercentage(){
        String line;
        
        int totalLines = 0;
        int commentedLines = 0;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            ArrayList<String> listOfIdentifiers = new ArrayList<>();
            
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {   
                totalLines++;
                if(line.contains("//")){
                    commentedLines++;
                }
            }
        }
        catch(Exception e){
            
        }
        
        return commentedLines / totalLines * 100;
    }
    
    public double calculateIndexWithoutComments(){
        return 0;
    }
    
    public double calculateIndexCommentWeight(){
        return 0;
    }
}