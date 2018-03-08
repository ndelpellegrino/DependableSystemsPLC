/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package druidmetrics;

import java.io.IOException;

/**
 *
 * @author Nico
 */
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
