package inter;

import lexer.Token;
import symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/30/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Op extends Expr {
    public Op(Token tok, Type p) {
        super(tok, p);
    }

    public Expr reduce() {
        Expr x = gene();
        Temp t = new Temp(type) ;
        emit(t.tostring() + " = " + x.tostring()) ;
        return t;
    }

}
