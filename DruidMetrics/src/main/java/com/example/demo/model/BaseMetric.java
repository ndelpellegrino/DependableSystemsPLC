package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface BaseMetric extends CyclomaticMetric,HalsteadMetric {

    public default int calculateNoOfEffLines(List<String> linesOfCode) {
        int effLines = 0;
        for (String s : linesOfCode) {
            s = s.trim();
            if (!s.equals("}") && !s.equals("{") && !s.startsWith("//") && !s.startsWith("/*") && !s.startsWith("*/") && !s.isEmpty()) {
                //System.out.println(s);
                effLines++;
                //System.out.println("line "+effLines);
            }
            //System.out.println("line " + effLines);
        }
        return effLines;
    }

    public default int calculateNoOfPhyLines(List<String> linesOfCode) {
        return linesOfCode.size();
    }

    public default int calculateNoOfLoops(List<String> noOfLines) {
        int noOfLoops = 0;
        for (String s : noOfLines) {
            if (s.replaceAll(" ", "").contains("for(") || s.replaceAll(" ", "").contains("while(") && !s.trim().startsWith("//")) {
                noOfLoops++;
            }
        }
        return noOfLoops;
    }

    public default int calculateNoOfReturns(List<String> noOfLines) {
        int noOfReturns = 0;
        for (String s : noOfLines) {
            if (s.trim().replaceAll(" ", "").startsWith("return")) {
                noOfReturns++;
            }
        }
        return noOfReturns;
    }

    public default int calculateNoOfConditions(List<String> noOfLines) {
        int noOfConditions = 0;
        for (String s : noOfLines) {
            s = s.replaceAll(" ", "");
            if (s.contains("if(") || s.contains("elseif(") || s.contains("switch(")) {
                noOfConditions++;
            }
        }
        return noOfConditions;
    }

    public default List<String> getCodeToList(String pathFile) throws FileNotFoundException, IOException {
        List<String> linesOfCode = new ArrayList<>();
        BufferedReader bufReader = new BufferedReader(new FileReader(pathFile));
        String line = bufReader.readLine();
        while (line != null) {
            linesOfCode.add(line);
            line = bufReader.readLine();
        }
        return linesOfCode;
    }

    public default List<String> getSortedCodeToList(List<String> linesOfCode) throws IOException {
        List<String> output = new ArrayList<>();
        for (String s : linesOfCode) {
            if (!s.replaceAll(" ", "").startsWith("//")) {
                String[] snippet = s.split("(?=[{}])|[;]");
                for (String c : snippet) {
                    if (!c.trim().isEmpty()) {
                        output.add(c.trim());
                    }
                }
            }
        }
        return output;
    }
}
