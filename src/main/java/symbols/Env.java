package symbols; /**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/25/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
import inter.Id;
import lexer.Token;
import sun.awt.Symbol;

import java. util.* ;

public class Env {
    private Hashtable table ;
    protected Env prev;
    public Env(Env p) {
        table = new Hashtable() ;
        prev = p;
    }

    public void put (Token w, Id i) {
        table.put(w, i);
    }

    public Id get(Token w){
        for( Env e = this; e != null; e = e.prev ) {
            Id found = (Id)(e.table.get(w)) ;
            if ( found != null ) return found;
        }
        return null;
    }
}