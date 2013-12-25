package lexer;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/25/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Token {
    public final int tag;
    public String type;
    public String lexeme;

    public Token(int t) {
    tag = t;
    }

    public String tostring() {
        return "" + (char)tag;
    }
}
