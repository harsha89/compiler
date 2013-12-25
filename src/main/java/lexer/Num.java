package lexer;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/25/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Num extends Token {
    public final int value;
    public final String lexeme;
    public Num(int v) {
        super(Tag.NUM) ;
        value = v;
        lexeme=Integer.toString(value);
        type="int";
    }

    public String tostring(){
        return "" + value;
    }
}
