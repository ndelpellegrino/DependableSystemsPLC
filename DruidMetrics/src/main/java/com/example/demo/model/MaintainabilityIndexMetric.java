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
public interface MaintainabilityIndexMetric {
    
    public double calculateIndexWithoutComments();
    public double calculateIndexCommentWeight();
    public double calculateCommentPercentage();
    public double calculateMainainabilityIndex();
    
}
