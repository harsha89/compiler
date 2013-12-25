/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class AbstractNode {
    public static ArrayList<AbstractNode> used=new ArrayList<AbstractNode>();
    public int value=0;
    public static int statVal=0;
    public String type;
}
