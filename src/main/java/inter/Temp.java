package inter;

import lexer.Word;
import symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/30/13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Temp extends Expr {
    static int count =0;
    int number = 0;

    public Temp(Type p) {
        super(Word.temp, p) ;
        number = ++count;
    }

    public String tostring() {
        return "t" + number;
    }

}
