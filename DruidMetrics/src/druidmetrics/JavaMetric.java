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
import java.util.stream.Collectors;




public class JavaMetric implements MetricInterface{
    // found at https://www.computerhope.com/jargon/j/java_reserved_words.htm 
    String[] reservedWords = {"abstract", "assert", "boolean", "break", "byte",
        "case", "catch", "char", "class", "const", "default", "do", "double", 
        "else", "enum", "extends", "false", "final", "finally", "float", "for",
        "goto", "if", "implements", "import", "instanceof", "int", "interface",
        "long", "native", "new", "null", "package", "private", "protected", 
        "public", "return", "short", "static", "strictfp", "super", "switch",
        "synchronized", "this", "throw", "throws", "transient", "true", "try", 
        "void", "volatile", "while", "continue"};

    // found at https://docstore.mik.ua/orelly/java-ent/jnut/ch02_05.htm
    // bracket operators ([] and ()) found in implementation
    String[] operators = {".", "++", "--", "+", "-", "~", "!", "new",
        "*", "/", "%", "<<", ">>", ">>>", "<", "<=", ">", ">=", "instanceof",
        "==", "!=", "&", "^", "|", "&&", "||", "?:", "=", "*=", "/=", "%=", 
        "+=", "-=", "%=", "+=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|="};
  
    public int calculateNoOfLines(String lines){        
        return 1;
    }
    
    // Halstead metrics
    
    public int calculateNoOfOperands(String pathFile) throws IOException{
        // counts reserved words, operators        
        String line;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            int operatorAmount = 0;
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {
                
                
                // checks the line for reserved words
                
                String[] wordsOnLine = line.split(" "); // Split line into seperate words
                
                for(String currentWord : wordsOnLine){
                    if(Arrays.stream(reservedWords).parallel().anyMatch(currentWord::contains)){
                        operatorAmount++;                        
                    }           
                    
                }
                
                
                
                // checks the line for operators
                 
                char[] charsOnLine = line.toCharArray(); // used to iterate

                // used to manage multi-character operators
                String currentString = "";
                ArrayList<String> listOfMatches = new ArrayList<>();
                boolean foundMultipleCharacters = false;

                
                int positionInList = 0;
                for(char currentChar : charsOnLine){                        
                    // continues a chain of characters
                    if(foundMultipleCharacters){
                        String combinedChars = currentString.concat(Character.toString(currentChar));
                        
                        boolean foundCombinedChars = false;
                        
                        if(combinedChars.equals("//")){
                            break;
                        }
                        
                        // check to see if it matches an operator
                        for(String currentOperator : operators){
                            if(currentOperator.contains(combinedChars)){
                                currentString = combinedChars;
                                foundCombinedChars = true;
                                break;
                            }
                        }
                        
                        if(!foundCombinedChars){ // check to see that the old string matched                       
                            String oldCharAsString = combinedChars.substring(0, combinedChars.length()-1);
                                                        
                            ArrayList<String> oldListOfMatches = new ArrayList<>();
                            
                            for(String currentOperator : operators){
                                if(currentOperator.contains(oldCharAsString)){
                                    oldListOfMatches.add(currentOperator);
                                }
                            }
                            
                            for(String currentMatch : oldListOfMatches){
                                if(currentMatch.equals(oldCharAsString)){
                                    operatorAmount++;
                                    currentString = "";
                                    foundMultipleCharacters = false; 
                                    break;
                                }
                            }        
                            
                            
                            // else keep looking
                            currentString = Character.toString(currentChar);
                            
                            // check to see if it matches an operator
                            for(String currentOperator : operators){
                                if(currentOperator.contains(currentString)){
                                    listOfMatches.add(currentOperator);
                                }
                            }
                            
                            if(listOfMatches.size() == 1){                            
                                for(String currentMatch : listOfMatches){
                                    if(currentMatch.equals(currentString)){
                                        operatorAmount++;
                                        currentString = "";
                                        foundMultipleCharacters = false;                                    
                                    }
                                }                        
                            }
                        }
                    }                    
                    if(!foundMultipleCharacters){
                        currentString = Character.toString(currentChar);
                        
                        // check to see if it matches an operator
                        for(String currentOperator : operators){
                            if(currentOperator.contains(currentString)){
                                listOfMatches.add(currentOperator);
                            }
                        }                        
                        
                        if(listOfMatches.size() == 1){
                            for(String currentMatch : listOfMatches){
                                if(currentMatch.length() == 1){
                                    operatorAmount++;                                    
                                }
                                else{
                                    foundMultipleCharacters = true;             
                                    currentString = Character.toString(currentChar);                                    
                                }
                            }
                        }
                        else if(listOfMatches.size() > 1){ // multiple matches == possibly multiple characters 
                            foundMultipleCharacters = true;             
                            currentString = Character.toString(currentChar);   
                        }
                        
                        if(listOfMatches.size() == 0 && !foundMultipleCharacters){
                            // is not an operator in any way
                        }
                    }
                    
                    positionInList++;
                    
                    if(foundMultipleCharacters && positionInList == charsOnLine.length){
                        operatorAmount++;
                    }
                    
                    listOfMatches = new ArrayList<>();
                }
            }
            
            System.out.println(operatorAmount);
            
            return operatorAmount;
        } 
        catch(Exception e){
            return 0;
        }
    }
    
    public int calculateNoOfOperators(String pathFile){
        // counts reserved words, operators        
        String line;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            int operatorAmount = 0;
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {
                
                
                // checks the line for reserved words
                
                line = line.replaceAll("\t", ""); // removes tab character constants
                
                String[] wordsOnLine = line.split(" "); // Split line into seperate words
                                
                for(String currentWord : wordsOnLine){
                    for(String currentReservedWord : reservedWords){
                        if(currentWord.equals(currentReservedWord)){
                            operatorAmount++;
                        }
                    } 
                    
                }
                
                
                
                // checks the line for operators
                 
                char[] charsOnLine = line.toCharArray(); // used to iterate

                // used to manage multi-character operators
                String currentString = "";
                ArrayList<String> listOfMatches = new ArrayList<>();
                boolean foundMultipleCharacters = false;

                
                int positionInList = 0;
                for(char currentChar : charsOnLine){                        
                    // continues a chain of characters
                    if(foundMultipleCharacters){
                        String combinedChars = currentString.concat(Character.toString(currentChar));
                        
                        boolean foundCombinedChars = false;
                        
                        if(combinedChars.equals("//")){
                            break;
                        }
                        
                        // check to see if it matches an operator
                        for(String currentOperator : operators){
                            if(currentOperator.contains(combinedChars)){
                                currentString = combinedChars;
                                foundCombinedChars = true;
                                break;
                            }
                        }
                        
                        if(!foundCombinedChars){ // check to see that the old string matched                       
                            String oldCharAsString = combinedChars.substring(0, combinedChars.length()-1);
                                                        
                            ArrayList<String> oldListOfMatches = new ArrayList<>();
                            
                            for(String currentOperator : operators){
                                if(currentOperator.contains(oldCharAsString)){
                                    oldListOfMatches.add(currentOperator);
                                }
                            }
                            
                            for(String currentMatch : oldListOfMatches){
                                if(currentMatch.equals(oldCharAsString)){
                                    operatorAmount++;
                                    currentString = "";
                                    foundMultipleCharacters = false; 
                                    break;
                                }
                            }        
                            
                            
                            // else keep looking
                            currentString = Character.toString(currentChar);
                            
                            // check to see if it matches an operator
                            for(String currentOperator : operators){
                                if(currentOperator.contains(currentString)){
                                    listOfMatches.add(currentOperator);
                                }
                            }
                            
                            if(listOfMatches.size() == 1){                            
                                for(String currentMatch : listOfMatches){
                                    if(currentMatch.equals(currentString)){
                                        operatorAmount++;
                                        currentString = "";
                                        foundMultipleCharacters = false;                                    
                                    }
                                }                        
                            }
                        }
                    }                    
                    if(!foundMultipleCharacters){
                        currentString = Character.toString(currentChar);
                        
                        // check to see if it matches an operator
                        for(String currentOperator : operators){
                            if(currentOperator.contains(currentString)){
                                listOfMatches.add(currentOperator);
                            }
                        }                        
                        
                        if(listOfMatches.size() == 1){
                            for(String currentMatch : listOfMatches){
                                if(currentMatch.length() == 1){
                                    operatorAmount++;                                    
                                }
                                else{
                                    foundMultipleCharacters = true;             
                                    currentString = Character.toString(currentChar);                                    
                                }
                            }
                        }
                        else if(listOfMatches.size() > 1){ // multiple matches == possibly multiple characters 
                            foundMultipleCharacters = true;             
                            currentString = Character.toString(currentChar);   
                        }
                        
                        if(listOfMatches.size() == 0 && !foundMultipleCharacters){
                            // is not an operator in any way
                        }
                    }
                    
                    positionInList++;
                    
                    if(foundMultipleCharacters && positionInList == charsOnLine.length && listOfMatches.size() > 0){
                        operatorAmount++;     
                    }
                    
                    listOfMatches = new ArrayList<>();
                }
            }
            
            System.out.println(operatorAmount);
            
            return operatorAmount;
        } 
        catch(Exception e){
            return 0;
        }
    }
}
