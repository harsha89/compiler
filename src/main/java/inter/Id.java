package inter;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/30/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */

//File Id.java
import lexer . * ;
import symbols. * ;
public class Id extends Expr {

    public int offset;
    // relative address
    public Id(Word id, Type p, int b) {
        super(id, p);
        offset = b;
    }
}