package inter;

import symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/1/13
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Set extends Stmt {

    public Id id;
    public Expr expr;

    public Set(Id i, Expr x) {
        id = i;
        expr = x;
        if ( check(id.type, expr.type) == null ) {
            error("type error");
        }
    }

    public Type check(Type p1, Type p2) {
        if ( Type.numeric(p1) && Type.numeric(p2) ) {
            return p2;
        }  else {
            return null;
        }
    }

    public void gen(int b, int a) {
        emit( id.toString() +"=" + expr.gene().tostring());
    }

}
