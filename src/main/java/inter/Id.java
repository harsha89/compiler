package inter;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/30/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */

//File Id.java
import lexer . * ;
import symbols. * ;
public class Id extends Token {
    public final String lexeme;
    public Object value;
    //String type;
    public Id(int t, String s) {
        super(t) ; lexeme = new String(s) ;
        super.lexeme=lexeme;
        value=0;
    }
}