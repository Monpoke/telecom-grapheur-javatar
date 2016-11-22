/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtest.lexem;

import graphtest.parsed.ParsedToken;

/**
 *
 * @author A643012
 */
public interface Rule {
    
    public ParsedToken match(String sentence);
    
    public void init();
    
    public String getName();
    
    public int movePointerFrom();
    
    public void reset();
    
    public String getLastMatch();
}
