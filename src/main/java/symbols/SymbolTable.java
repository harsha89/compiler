package symbols;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/26/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class SymbolTable {
    public Hashtable<String,Symbol> symbolTable;

    public SymbolTable(){
        symbolTable=new Hashtable<String, Symbol>();
    }

    public void putSymbol(String key,Symbol symbol) {
        symbolTable.put(key,symbol);
    }

    public void isSymbolExist(String key) {

    }

    public Symbol getSymbol(String key) {
        return symbolTable.get(key);
    }

}
