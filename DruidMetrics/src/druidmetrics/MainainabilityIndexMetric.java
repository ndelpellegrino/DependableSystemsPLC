/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package druidmetrics;

/**
 *
 * @author Nico
 */
public interface MainainabilityIndexMetric {
    public double calculateIndexWithoutComments(String pathfile);
    public double calculateIndexCommentWeight(String pathfile);
    public double calculateCommentPercentage(String pathfile);
}
