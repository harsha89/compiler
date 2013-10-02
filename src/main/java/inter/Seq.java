package inter;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/1/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class Seq  extends Stmt{

    Stmt stmtl; Stmt stmt2;

    public Seq(Stmt sl, Stmt s2) {
        stmtl = sl;
        stmt2 = s2;
    }

    public void gene(int b, int a) {

        if ( stmtl == Stmt.Null ) {
            stmt2.gen(b, a);
        } else if ( stmt2 == Stmt.Null ) {
            stmtl.gen(b, a) ;
        } else {
            int label = newlabel();
            stmtl.gen(b,label);
            stmt2.gen(label, a) ;
        }
    }
}