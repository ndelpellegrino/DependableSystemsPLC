/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

/**
 *
 * @author k1552723
 */
import java.util.List;

public interface CyclomaticMetric {
    
    public int calculateNoOfLoops(List<String> noOfLines);
    public int calculateNoOfExceptions(List<String> noOfLines);
    public int calculateNoOfReturns(List<String> noOfLines);
    public int calculateNoOfConditions(List<String> noOfLines);
    
    public default int getCyclomaticMetric(List<String> noOfLines){
        return calculateNoOfLoops(noOfLines)+calculateNoOfExceptions(noOfLines)+
                calculateNoOfReturns(noOfLines)+calculateNoOfConditions(noOfLines);
    }
}
     