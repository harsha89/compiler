package inter;

import lexer.Num;
import lexer.Real;
import lexer.Token;
import symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/1/13
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Constant extends Expr {

    public Constant(Token tok, Type p) {
        super(tok, p);
    }

    public Constant(int i) {
        super(new Num(i), Type.Int);
    }

    public Constant(float i) {
        super(new Real(i),Type.Float);
    }

}
