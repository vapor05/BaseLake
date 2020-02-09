/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.baselake;

/**
 *
 * @author NicholasBocchini
 */
public class BaseLakeException extends Exception {
    
    public BaseLakeException() {}
    
    public BaseLakeException(String message)
    {
        super(message);
    }
}