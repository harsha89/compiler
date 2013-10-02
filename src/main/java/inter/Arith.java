package inter;

import lexer.Token;
import symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/30/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Arith extends Op {
    public Expr expr1, expr2;

    public Arith(Token tok, Expr x1, Expr x2) {
        super (tok, null) ; expr1 = x2 ; expr2 = x2 ;
        type = Type.max(expr1.type, expr2.type) ;
        if (type == null ) {
            error("type error");
        }
    }

    public Expr gene() {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }

    public String tostring() {
    return expr1.tostring()+" "+op.tostring()+" "+expr2.tostring();
    }
}
