package inter;

import lexer.Lexer;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/29/13
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
     int lexline = 0;

     public Node() {
         lexline = Lexer.line;
     }

    void error(String s) {
         throw new Error("near line "+lexline+": "+s);
    }

    static int labels =0;

    public int newlabel()
    {
        return ++labels;
    }

    public void emit(String s) {
        System.out.println("\t" + s);
    }

}
