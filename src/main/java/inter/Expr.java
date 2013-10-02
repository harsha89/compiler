package inter;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/30/13
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
import lexer.* ;
import symbols.* ;
public class Expr extends Node {
    public Token op;

    public Type type;

    public Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }
    public Expr gene() {
        return this;
    }

    public Expr reduce() {
        return this;
    }

    public String tostring() {
        return op.tostring();
    }
}
