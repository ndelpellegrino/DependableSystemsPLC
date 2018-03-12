
package com.example.demo.model;

import java.io.IOException;

public interface HalsteadMetric {
    public int calculateNoOfOperands() throws IOException;    
    public int calculateNoOfOperators();    
    public int calculateNoOfUniqueOperands() throws IOException;    
    public int calculateNoOfUniqueOperators();
    
    public int calculateVocabularySize();    
    public double calculateProgramLevel();    
    public int calculateProgramLength();    
    public double calculateImplementationTime();    
    public double calculateVolume();    
    public double calculateEstimatedNoBugs();    
    public double calculateDifficultyLevel();    
    public double calculateEffortToImplement();
}
