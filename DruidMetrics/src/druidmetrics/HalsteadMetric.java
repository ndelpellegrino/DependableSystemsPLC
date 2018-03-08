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
    public int calculateNoOfOperands(String pathFile) throws IOException;    
    public int calculateNoOfOperators(String pathFile);    
    public int calculateNoOfUniqueOperands(String pathFile) throws IOException;    
    public int calculateNoOfUniqueOperators(String pathFile);
    
    public int calculateVocabularySize(int uniqueOperandAmount, int uniqueOperatorAmount);    
    public double calculateProgramLevel(double difficultyLevel);    
    public int calculateProgramLength(int operandAmount, int operatorAmount);    
    public double calculateImplementationTime(double effortToImplement);    
    public double calculateVolume(int vocabularySize, int programLength);    
    public double calculateEstimatedNoBugs(double effortToImplement);    
    public double calculateDifficultyLevel(int uniqueOperatorAmount, int operandAmount, int uniqueOperandAmount);    
    public double calculateEffortToImplement(double programVolume, double difficultyLevel);
}
