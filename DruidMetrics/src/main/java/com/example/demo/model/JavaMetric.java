
package com.example.demo.model;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaMetric implements BaseMetric {
    
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
    // bracket operators ([] and ()) not included
    String[] operators = {".", "++", "--", "+", "-", "~", "!",
        "*", "/", "%", "<<", ">>", ">>>", "<", "<=", ">", ">=",
        "==", "!=", "&", "^", "|", "&&", "||", "?:", "=", "*=", "/=", "%=", 
        "+=", "-=", "%=", "+=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|="};    
    String[] numberOperators = {"++", "--", "~", "+", "-", "!", "new",
        "*", "/", "%", "<<", ">>", ">>>", "<", "<=", ">", ">=", "instanceof",
        "==", "!=", "&", "^", "|", "&&", "||", "?:", "=", "*=", "/=", "%=", 
        "+=", "-=", "%=", "+=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|="};  
    
    String[] types = {"byte", "short", "int", "long", "float", "double", "char", "boolean"}; 
    private String[] loopWords = {"for(", "while("};
    private String[] exceptionWords = {"throws","catch"};
    private String[] returnWords = {"return"};
    private String[] conditionWords = {"if(", "elseif{", "switch("};
    
    String pathFile;
    
    public JavaMetric(String pathFile){
        this.pathFile = pathFile;
    }
    
    

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
    
    // Halstead metrics
    
    public ArrayList<String> getListOfIdentifiers(){ 
        String line;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            //System.out.println("inside get list of identfiers");
            ArrayList<String> listOfIdentifiers = new ArrayList<>();
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {                
                char[] charsOnLine = line.toCharArray(); // used to iterate
                
                ArrayList<String> listOfLines = new ArrayList<>();
                
                int currentStartIndex = 0;
                int position = 0;
                // split semi-colons
                for(char currentChar : charsOnLine){   
                    if(Character.toString(currentChar).equals(";")){
                        listOfLines.add(line.substring(currentStartIndex, position));
                        currentStartIndex = position;
                    }
                    position++;
                }
                //System.out.println("i have passed the while loop");
                // check semi-colon lines for identifiers
                for(String currentLine : listOfLines){
                    char[] currentLineArray = currentLine.toCharArray();
                    
                    // remove all beginning spaces
                    int afterSpacesIndex = 0;
                    for(char currentChar : currentLineArray){
                        if(currentChar != ' '){
                            break;
                        }
                        afterSpacesIndex++;
                    }
                        
                    int asgnOpIndex = 0;                    
                    
                    boolean foundAssignment = false;
                    if(currentLine.contains("=")){
                        int indexOfAssign = currentLine.indexOf("=");                    

                        // check it's not part of an operator that includes '='
                        if(currentLine.charAt(indexOfAssign - 1) != '<' && 
                                currentLine.charAt(indexOfAssign - 1) != '>' && 
                                currentLine.charAt(indexOfAssign - 1) != '=' && 
                                currentLine.charAt(indexOfAssign - 1) != '!' && 
                                currentLine.charAt(indexOfAssign - 1) != '*' && 
                                currentLine.charAt(indexOfAssign - 1) != '/' && 
                                currentLine.charAt(indexOfAssign - 1) != '%' && 
                                currentLine.charAt(indexOfAssign - 1) != '+' && 
                                currentLine.charAt(indexOfAssign - 1) != '-' && 
                                currentLine.charAt(indexOfAssign - 1) != '&' && 
                                currentLine.charAt(indexOfAssign - 1) != '^' && 
                                currentLine.charAt(indexOfAssign - 1) != '|'){
                            foundAssignment = true;
                        }                        
                    }
                    //System.out.println("i have passed the currentLine if stmt");
                    // gets index of the '='
                    if(foundAssignment){
                        //System.out.println("inside if(foundassignment)");
                        asgnOpIndex = currentLine.indexOf("="); // gets first occurence of '='
                        
                        String beforeAsgnOp = currentLine.substring(afterSpacesIndex, asgnOpIndex);
                        
                        int numberOfSpaces = beforeAsgnOp.length() - beforeAsgnOp.replace(" ", "").length();
                        
                        // there are definitely 2 words
                        if(numberOfSpaces == 2){ 
                            listOfIdentifiers.add(beforeAsgnOp.substring(beforeAsgnOp.indexOf(" ") + 1, beforeAsgnOp.length() - 1));
                        }
                        // defintely 2 words, and the space is inbetween the words
                        else if(numberOfSpaces == 1 && !(beforeAsgnOp.charAt(asgnOpIndex - 1) == ' ')){                            
                            listOfIdentifiers.add(beforeAsgnOp.substring(beforeAsgnOp.indexOf(" ") + 1, beforeAsgnOp.length()));
                        }                        
                    }
                }
            }
            //System.out.println("im about to return the list of identifiers");
            return listOfIdentifiers;
        }
        catch(Exception e){
            //System.out.println("in the catch for list of identifiers");
            return new ArrayList<String>();            
        }
    }
    
    public int calculateNoOfOperators(){
        // counts reserved words, operators        
        String line;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            System.out.println("inside calc no of operators");
            int operatorAmount = 0;
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {
                
                
                // checks the line for reserved words
                
                line = line.replaceAll("\t", " "); // replaces tab character constants with a space
                line = line.replaceAll("\b", " "); // replaces tab character constants with a space
                line = line.replaceAll("\n", " "); // replaces tab character constants with a space
                line = line.replaceAll("\f", " "); // replaces tab character constants with a space
                line = line.replaceAll("\r", " "); // replaces tab character constants with a space
                line = line.replaceAll("\"", " "); // replaces tab character constants with a space
                line = line.replaceAll("\'", " "); // replaces tab character constants with a space
                
                System.out.println("passed line.replaceAll stuff");
                
                String[] wordsOnLine = line.split(" "); // Split line into seperate words
                                
                for(String currentWord : wordsOnLine){
                    for(String currentReservedWord : reservedWords){
                        if(currentWord.equals(currentReservedWord)){
                            operatorAmount++;
                        }
                    }                     
                }
                System.out.println("passed the for loop");
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
                                    break;
                                }
                            }     
                            
                            foundMultipleCharacters = false;
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
                        for(String currentOperator : operators){
                            if(currentString.equals(currentOperator)){
                                operatorAmount++;
                                break;
                            }
                        }
                    }
                    
                    listOfMatches = new ArrayList<>();
                }
                int tempInt = 0;
            }
            
            //System.out.println(operatorAmount);
            
            return operatorAmount;
        } 
        catch(Exception e){
            System.out.println("in the catch for counting operator amount");
            return 0;
        }
    }
    
    // does not support "int number;" - only "int number = 0;" 
    public int calculateNoOfOperands(){
        // counts reserved words, operators        
        String line;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            //System.out.println("inside calc no op operands");
            ArrayList<String> identifiers = getListOfIdentifiers();
            
            int identifierAmount = 0;
            int numberAmount = 0;
            int arrayAmount = 0;
            int charAmount = 0;
            int stringAmount = 0;
            
            int currentArrayCount = 0; // for multi-dimensional arrays
            
            boolean foundArrays = false;
            boolean foundString = false;
            boolean foundChar = false;
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {
                
                
                // checks the line for reserved words                
                line = line.replaceAll("\t", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\b", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\n", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\f", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\r", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\"", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\'", " "); // replaces tab character constants with a space       
                
                // checks the line for identifiers                 
                char[] charsOnLine = line.toCharArray(); // used to iterate

                // used to manage multi-character identifiers
                String currentString = "";
                ArrayList<String> listOfMatches = new ArrayList<>();
                boolean foundMultipleCharacters = false;
                
                boolean followingNumber = false;
                
                String progressInLine = "";
                
                int positionInList = 0;
                for(char currentChar : charsOnLine){  
                    progressInLine = progressInLine.concat(Character.toString(currentChar));
                    if(progressInLine.contains("//")){
                        break;
                    }         
                    else if(foundChar){
                        if(currentChar == "'".charAt(0)){
                            foundChar = false;
                            charAmount++;
                        }
                    }
                    else if(currentChar == "'".charAt(0) && !foundString){
                        foundMultipleCharacters = false;
                        foundChar = true;
                    }
                    else if(foundString){
                        if(currentChar == '"'){
                            foundString = false;
                            stringAmount++;
                        }
                    }
                    else if(currentChar == '"' && !foundChar){
                        foundMultipleCharacters = false;
                        foundString = true;
                    }        
                    else{
                        if(currentChar == '{' && !foundChar && !foundString){
                            foundMultipleCharacters = false;
                            foundArrays = true;
                        }
                        
                        // enter identifer code here                        
                        if(currentChar != '.' && !foundMultipleCharacters){
                            if(followingNumber){                        
                                try{
                                    double d = Double.parseDouble(Character.toString(currentChar));
                                    currentString = currentString.concat(Character.toString(currentChar));
                                    followingNumber = true;
                                }
                                // tracks number until non-number found, then adds it
                                catch(Exception e){
                                    boolean foundOperator = false;
                                    
                                    // length of highest operator (not including words)
                                    
                                    // check left side
                                    for(int i = 1; i < positionInList - currentString.length() - 1; i++){          
                                        Character currentOpChar = ' ';
                                        try{
                                            currentOpChar = line.charAt(positionInList - i - currentString.length());   
                                            
                                            for(String currentOperator : operators){
                                                if(!currentOperator.equals("instanceof") || !currentOperator.equals("new")){
                                                    if(currentOperator.contains(Character.toString(currentOpChar))){
                                                        foundOperator = true;
                                                    }
                                                }
                                            }        
                                        }                                        
                                        catch(Exception ex){
                                        }
                                        
                                        try{
                                            double d = Double.parseDouble(Character.toString(currentChar));
                                        }
                                        catch(Exception ex){
                                            if(currentOpChar != ' '){
                                                break;
                                            }                
                                        }
                                    }
                                    
                                    // check right side
                                    for(int i = 1; i < line.length() - positionInList; i++){          
                                        Character currentOpChar = ' ';
                                        try{
                                            currentOpChar = line.charAt(positionInList + i);     
                                            
                                            for(String currentOperator : operators){
                                                if(!currentOperator.equals("instanceof") || !currentOperator.equals("new")){
                                                    if(currentOperator.contains(Character.toString(currentOpChar))){
                                                        foundOperator = true;
                                                    }
                                                }
                                            }                                            
                                        }
                                        catch(Exception ex){
                                        }
                                        
                                        try{
                                            double d = Double.parseDouble(Character.toString(currentChar));
                                        }
                                        catch(Exception ex){
                                            if(currentOpChar != ' '){
                                                break;
                                            }
                                        }
                                    }
                                    
                                    if(foundOperator){
                                    // it's a number so add it
                                        followingNumber = false;
                                        currentString = "";
                                        numberAmount++;                                        
                                    }                                    
                                }
                            }
                            else{
                                try{
                                    double d = Double.parseDouble(Character.toString(currentChar));
                                        currentString = Character.toString(currentChar);
                                    followingNumber = true;
                                }
                                catch(Exception e){                        
                                }                                   
                            }                     
                        }                        
                    }   
                    
                    // continues a chain of characters
                    if(foundMultipleCharacters){
                        String combinedChars = currentString.concat(Character.toString(currentChar));
                        
                        boolean foundCombinedChars = false;
                        
                        if(combinedChars.equals("//")){
                            break;
                        }
                        
                        // check to see if it matches an identifier
                        for(String currentOperator : identifiers){
                            if(currentOperator.contains(combinedChars)){
                                currentString = combinedChars;
                                foundCombinedChars = true;
                                break;
                            }
                        }
                        
                        if(!foundCombinedChars){ // check to see that the old string matched                       
                            String oldCharAsString = combinedChars.substring(0, combinedChars.length()-1);
                                                        
                            ArrayList<String> oldListOfMatches = new ArrayList<>();
                            
                            for(String currentOperator : identifiers){
                                if(currentOperator.contains(oldCharAsString)){
                                    oldListOfMatches.add(currentOperator);
                                }
                            }
                            
                            for(String currentMatch : oldListOfMatches){
                                if(currentMatch.equals(oldCharAsString)){
                                    identifierAmount++;
                                    currentString = "";
                                    break;
                                }
                            }        
                            foundMultipleCharacters = false;
                        }
                    }                    
                    if(!foundMultipleCharacters && !foundArrays && !followingNumber){
                        currentString = Character.toString(currentChar);
                        
                        // check to see if it matches an identifier
                        for(String currentOperator : identifiers){
                            if(currentOperator.contains(currentString)){
                                listOfMatches.add(currentOperator);
                            }
                        }                        
                        
                        if(listOfMatches.size() == 1){
                            for(String currentMatch : listOfMatches){
                                if(currentMatch.length() == 1){
                                    identifierAmount++;                                    
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
                            // is not an identifier in any way
                        }
                    }
                    
                    if(foundMultipleCharacters && positionInList == charsOnLine.length){
                        for(String currentMatch : listOfMatches){
                            if(currentMatch.equals(currentString)){
                                identifierAmount++;
                                break;
                            }
                        }        
                    }                    
                    
                    if(foundArrays){
                        if(currentChar == '}'){
                            currentArrayCount++;
                        }
                        else if(currentChar == '{'){
                            foundArrays = false;
                            currentArrayCount = 0;
                        }
                        else if(currentChar == ';'){
                            arrayAmount += currentArrayCount;
                            currentArrayCount = 0;
                            foundArrays = false;                            
                        }                        
                    }
                    
                    positionInList++;
                    
                    listOfMatches = new ArrayList<>();
                }
            }
            
            System.out.println(identifierAmount + numberAmount + arrayAmount + charAmount + stringAmount);
                        
            return (identifierAmount + numberAmount + arrayAmount + charAmount + stringAmount);
        } 
        catch(Exception e){
            System.out.println("in the catch for calc no of operands");
            return 0;
        }        
    }
    
    public int calculateNoOfUniqueOperators(){
        // counts reserved words, operators        
        String line;
        Set<String> operatorsSet = new HashSet<>();
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            //System.out.println("inside calc no of unique operators");
            int operatorAmount = 0;
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {
                
                
                // checks the line for reserved words
                
                line = line.replaceAll("\t", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\b", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\n", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\f", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\r", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\"", " "); // replaces tab character constants with a space               
                line = line.replaceAll("\'", " "); // replaces tab character constants with a space     
                
                String[] wordsOnLine = line.split(" "); // Split line into seperate words
                                
                for(String currentWord : wordsOnLine){
                    for(String currentReservedWord : reservedWords){
                        if(currentWord.equals(currentReservedWord)){
                            int sizeBeforeInsert = operatorsSet.size();
                            operatorsSet.add(currentReservedWord);
                            if(operatorsSet.size() > sizeBeforeInsert){
                                operatorAmount++;                                
                            }                            
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
                                    int sizeBeforeInsert = operatorsSet.size();
                                    operatorsSet.add(oldCharAsString);
                                    if(operatorsSet.size() > sizeBeforeInsert){
                                        operatorAmount++;                    
                                    }              
                                    currentString = "";
                                    break;            
                                }
                            }     
                            
                            foundMultipleCharacters = false;
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
                                    int sizeBeforeInsert = operatorsSet.size();
                                    operatorsSet.add(currentString);
                                    if(operatorsSet.size() > sizeBeforeInsert){
                                        operatorAmount++;                     
                                    }                                               
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
                        for(String currentOperator : operators){
                            if(currentString.equals(currentOperator)){
                                int sizeBeforeInsert = operatorsSet.size();
                                operatorsSet.add(currentOperator);
                                if(operatorsSet.size() > sizeBeforeInsert){
                                    operatorAmount++;                        
                                }            
                                break;
                            }
                        }
                    }
                    
                    listOfMatches = new ArrayList<>();
                }
                int tempInt = 0;
            }
            
            System.out.println(operatorsSet.size());
            
            return operatorsSet.size();
        } 
        catch(Exception e){
            //System.out.println("in the catch for unique operators");
            return 0;
        }
    }
    
    // does not support "int number;" - only "int number = 0;" 
    public int calculateNoOfUniqueOperands(){
        // counts reserved words, operators        
        String line;
        Set<String> operandsSet = new HashSet<>();
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){
            //System.out.println("inside calc no of unique operands");
            ArrayList<String> identifiers = getListOfIdentifiers();
            
            int identifierAmount = 0;
            int numberAmount = 0;
            int arrayAmount = 0;
            int charAmount = 0;
            int stringAmount = 0;
            
            Set<String> currentArrays = new HashSet<>();
            int currentArrayCount = 0; // for multi-dimensional arrays
            
            String currentCharString = "";
            String currentStringString = "";
            
            boolean foundArrays = false;
            boolean foundString = false;
            boolean foundChar = false;
            
            // start reading file line by line            
            while ((line = br.readLine()) != null) {
                
                
                // checks the line for reserved words                
                line = line.replaceAll("\t", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\b", " "); // replaces tab character constants with a space               
                line = line.replaceAll("\n", " "); // replaces tab character constants with a space                
                line = line.replaceAll("\f", " "); // replaces tab character constants with a space              
                line = line.replaceAll("\r", " "); // replaces tab character constants with a space              
                line = line.replaceAll("\"", " "); // replaces tab character constants with a space              
                line = line.replaceAll("\'", " "); // replaces tab character constants with a space                
                
                // checks the line for identifiers                 
                char[] charsOnLine = line.toCharArray(); // used to iterate

                // used to manage multi-character identifiers
                String currentString = "";
                String currentArrayString = "";
                ArrayList<String> listOfMatches = new ArrayList<>();
                boolean foundMultipleCharacters = false;
                
                boolean followingNumber = false;
                
                String progressInLine = "";
                
                int positionInList = 0;
                for(char currentChar : charsOnLine){  
                    progressInLine = progressInLine.concat(Character.toString(currentChar));
                    if(progressInLine.contains("//")){
                        break;
                    }         
                    else if(foundChar){
                        if(currentChar == "'".charAt(0)){
                            currentCharString = currentCharString.concat(Character.toString(currentChar));
                            foundChar = false;
                                                        
                            int sizeBeforeInsert = operandsSet.size();
                            operandsSet.add(currentCharString);
                            if(operandsSet.size() > sizeBeforeInsert){
                                charAmount++;                
                            }        
                            
                            currentCharString = "";
                        }
                        else{                            
                            currentCharString = currentCharString.concat(Character.toString(currentChar));
                        }
                    }
                    else if(currentChar == "'".charAt(0) && !foundString){
                        foundMultipleCharacters = false;
                        foundChar = true;
                        currentCharString = currentCharString.concat(Character.toString(currentChar));
                    }
                    else if(foundString){
                        if(currentChar == '"'){
                            foundString = false;
                                                        
                            int sizeBeforeInsert = operandsSet.size();
                            operandsSet.add(currentStringString);
                            if(operandsSet.size() > sizeBeforeInsert){
                                stringAmount++;                
                            }        
                            
                            currentStringString = "";
                        }
                        else{
                            currentStringString = currentStringString.concat(Character.toString(currentChar));
                        }
                    }
                    else if(currentChar == '"' && !foundChar){
                        foundMultipleCharacters = false;
                        foundString = true;
                        currentStringString = currentStringString.concat(Character.toString(currentChar));
                    }        
                    else{
                        if(currentChar == '{' && !foundChar && !foundString){
                            foundMultipleCharacters = false;
                            foundArrays = true;
                            currentArrayString = currentArrayString.concat(Character.toString(currentChar));
                        }
                        
                        // enter identifer code here                        
                        if(currentChar != '.' && !foundMultipleCharacters){
                            if(followingNumber){                        
                                try{
                                    double d = Double.parseDouble(Character.toString(currentChar));
                                    currentString = currentString.concat(Character.toString(currentChar));
                                    followingNumber = true;
                                }
                                // tracks number until non-number found, then adds it
                                catch(Exception e){
                                    boolean foundOperator = false;
                                    
                                    // length of highest operator (not including words)
                                    
                                    // check left side
                                    for(int i = 1; i < positionInList - currentString.length() - 1; i++){          
                                        Character currentOpChar = ' ';
                                        try{
                                            currentOpChar = line.charAt(positionInList - i - currentString.length());   
                                            
                                            for(String currentOperator : operators){
                                                if(!currentOperator.equals("instanceof") || !currentOperator.equals("new")){
                                                    if(currentOperator.contains(Character.toString(currentOpChar))){
                                                        foundOperator = true;
                                                    }
                                                }
                                            }        
                                        }                                        
                                        catch(Exception ex){
                                        }
                                        
                                        try{
                                            double d = Double.parseDouble(Character.toString(currentChar));
                                        }
                                        catch(Exception ex){
                                            if(currentOpChar != ' '){
                                                break;
                                            }                
                                        }
                                    }
                                    
                                    // check right side
                                    for(int i = 1; i < line.length() - positionInList; i++){          
                                        Character currentOpChar = ' ';
                                        try{
                                            currentOpChar = line.charAt(positionInList + i);     
                                            
                                            for(String currentOperator : operators){
                                                if(!currentOperator.equals("instanceof") || !currentOperator.equals("new")){
                                                    if(currentOperator.contains(Character.toString(currentOpChar))){
                                                        foundOperator = true;
                                                    }
                                                }
                                            }                                            
                                        }
                                        catch(Exception ex){
                                        }
                                        
                                        try{
                                            double d = Double.parseDouble(Character.toString(currentChar));
                                        }
                                        catch(Exception ex){
                                            if(currentOpChar != ' '){
                                                break;
                                            }
                                        }
                                    }
                                    
                                    if(foundOperator){
                                    // it's a number so add it
                                        int sizeBeforeInsert = operandsSet.size();
                                        operandsSet.add(currentString);
                                        if(operandsSet.size() > sizeBeforeInsert){
                                            numberAmount++;                
                                        }        
                                        followingNumber = false;
                                        currentString = "";                                         
                                    }                                    
                                }
                            }
                            else{
                                try{
                                    double d = Double.parseDouble(Character.toString(currentChar));
                                        currentString = Character.toString(currentChar);
                                    followingNumber = true;
                                }
                                catch(Exception e){                        
                                }                                   
                            }                     
                        }                        
                    }   
                    
                    // continues a chain of characters
                    if(foundMultipleCharacters){
                        String combinedChars = currentString.concat(Character.toString(currentChar));
                        
                        boolean foundCombinedChars = false;
                        
                        if(combinedChars.equals("//")){
                            break;
                        }
                        
                        // check to see if it matches an identifier
                        for(String currentOperator : identifiers){
                            if(currentOperator.contains(combinedChars)){
                                currentString = combinedChars;
                                foundCombinedChars = true;
                                break;
                            }
                        }
                        
                        if(!foundCombinedChars){ // check to see that the old string matched                       
                            String oldCharAsString = combinedChars.substring(0, combinedChars.length()-1);
                                                        
                            ArrayList<String> oldListOfMatches = new ArrayList<>();
                            
                            for(String currentOperator : identifiers){
                                if(currentOperator.contains(oldCharAsString)){
                                    oldListOfMatches.add(currentOperator);
                                }
                            }
                            
                            for(String currentMatch : oldListOfMatches){
                                if(currentMatch.equals(oldCharAsString)){
                                    int sizeBeforeInsert = operandsSet.size();
                                    operandsSet.add(oldCharAsString);
                                    if(operandsSet.size() > sizeBeforeInsert){
                                        identifierAmount++;               
                                    }              
                                    currentString = "";
                                    break;                
                                }
                            }        
                            foundMultipleCharacters = false;
                        }
                    }                    
                    if(!foundMultipleCharacters && !foundArrays && !followingNumber){
                        currentString = Character.toString(currentChar);
                        
                        // check to see if it matches an identifier
                        for(String currentOperator : identifiers){
                            if(currentOperator.contains(currentString)){
                                listOfMatches.add(currentOperator);
                            }
                        }                        
                        
                        if(listOfMatches.size() == 1){
                            for(String currentMatch : listOfMatches){
                                if(currentMatch.length() == 1){
                                    int sizeBeforeInsert = operandsSet.size();
                                    operandsSet.add(currentString);
                                    if(operandsSet.size() > sizeBeforeInsert){
                                        identifierAmount++;                   
                                    }                      
                                    currentString = "";
                                    break;                                       
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
                            // is not an identifier in any way
                        }
                    }
                    
                    if(foundMultipleCharacters && positionInList == charsOnLine.length){
                        for(String currentMatch : listOfMatches){
                            if(currentMatch.equals(currentString)){
                                int sizeBeforeInsert = operandsSet.size();
                                operandsSet.add(currentString);
                                if(operandsSet.size() > sizeBeforeInsert){
                                    identifierAmount++;                    
                                }             
                                break;           
                            }
                        }        
                    }
                    
                    if(foundArrays){
                        if(currentChar == '}'){
                            currentArrayString = currentArrayString.concat(Character.toString(currentChar));
                            currentArrays.add(currentArrayString);
                            currentArrayString = "";
                        }
                        else if(currentChar == '{'){
                            currentArrayString = currentArrayString.concat(Character.toString(currentChar));
                            foundArrays = false;
                            currentArrayCount = 0;
                        }
                        else if(currentChar == ';'){
                            operandsSet.addAll(currentArrays);
                            
                            currentArrayCount = 0;
                            currentArrays = new HashSet<>();
                            foundArrays = false;                            
                        }                   
                        else{                            
                            currentArrayString = currentArrayString.concat(Character.toString(currentChar));
                        }
                    }
                    
                    positionInList++;
                    
                    listOfMatches = new ArrayList<>();
                }
            }
            
            System.out.println(operandsSet.size());
            
            return (operandsSet.size());
        } 
        catch(Exception e){
            //System.out.println("in the catch for unique operands");
            return 0;
        }        
    }
    
    public int calculateVocabularySize(){
        return calculateNoOfOperands() + calculateNoOfOperators();
    }
    
    public double calculateProgramLevel(){
        return 1 / calculateDifficultyLevel();
    }
    
    public int calculateProgramLength(){
        return calculateNoOfOperands() + calculateNoOfOperators();
    }
    
    public double calculateImplementationTime(){   
        return calculateEffortToImplement() / 18;
    }
    
    public double calculateVolume(){
        return calculateProgramLength() * (Math.log(calculateVocabularySize()) / Math.log(2));             
    }
    
    public double calculateEstimatedNoBugs(){
        return ((calculateEffortToImplement() - calculateEffortToImplement() / 3) / 3000);
    }
    
    public double calculateDifficultyLevel(){    
        if(calculateNoOfUniqueOperators() != 0 || calculateNoOfUniqueOperands() != 0 || calculateNoOfOperands() != 0){        
            return (calculateNoOfUniqueOperators() / 2) * (calculateNoOfUniqueOperands() / calculateNoOfOperands());
        }
        else{
            return 0;
            //return 1;
        }            
    }
   
    public double calculateEffortToImplement(){
        return calculateVolume() * calculateDifficultyLevel();
    }
    
    //maintainability
    public double calculateCommentPercentage(){
        String line;
        
        double totalLines = 0;
        double commentedLines = 0;
        
        try(
            InputStream fis = new FileInputStream(pathFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ){  
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
    
    public double calculateIndexWithoutComments(List<String> noOfLines){ 
        if(calculateVolume() == 0){
            return 1337;
        }
        else{
            return 171 - 5.2 * Math.log(calculateVolume()) - 0.23 * getCyclomaticMetric(noOfLines) - 16.2 * Math.log(calculateNoOfPhyLines(noOfLines));
        }
    }
    
    public double calculateIndexCommentWeight(){
        return 50 * Math.sin(Math.toRadians(Math.sqrt((2.4) * calculateCommentPercentage())));
    }
    
    public double calculateMainainabilityIndex(List<String> noOfLines){
        return calculateIndexWithoutComments(noOfLines) + calculateIndexCommentWeight();
    }
}