package inter;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/1/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class Stmt extends Node {

    public Stmt() {}

    public static Stmt Null = new Stmt();

    public void gen(int b, int a) {}

    int after = 0;
// saves label after
    public static Stmt Enclosing = Stmt.Null; // used for break stmts

}
