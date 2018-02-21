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
}
