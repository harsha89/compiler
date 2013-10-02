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

    public Num(int v) {
        super(Tag.NUM) ;
        value = v;
    }

    public String tostring(){
         return "" + value;
    }
}
