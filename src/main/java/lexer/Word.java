package lexer;

import symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/25/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word extends Token {
    public final String lexeme;
    public Object value;
    public Type type;
    public static final Word temp =new Word("t",Tag.TEMP);

    public Word(String s,int t) {
        super(t);
        this.lexeme = new String(s) ;
    }

    public Word(String s,int t,Object value,Type type) {
        super(t);
        this.lexeme = new String(s) ;
        this.type=type;
    }
}
