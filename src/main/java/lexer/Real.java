package lexer;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/27/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
// File Real.java
public class Real extends Token {
    public final float value;
    public final String lexeme;
    public Real(float v) {
        super(Tag.FLOAT) ;
        value = v;
        lexeme=Float.toString(value);
        type="float";
    }

    public String tostring(){
        return "" + value;
    }
}