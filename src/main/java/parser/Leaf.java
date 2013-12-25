/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import lexer.Token;

/**
 *
 * @author admin
 */
public class Leaf extends AbstractNode{
    
    public Token token;

    public Leaf(Token token) {
        
        super.value=AbstractNode.used.size()+1;
        this.token = token;
    }
    
}
