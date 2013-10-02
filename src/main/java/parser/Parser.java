package parser;

import inter.*;
import lexer.*;
import postfix.StackMachine;
import symbols.Env;
import symbols.Type;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/25/13
 * Time: 6:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    private static String lookahead;
    private Lexer lexer;// lexical analyzer for this parser
    private StackMachine stackMachine;
    private Token look; // lookahead tagen
    Env top = null; // current or top symbol table
    int used = 0; // storage used for declarations
    private Word currentAssigneeSymbol;

    public Parser(Lexer lex , StackMachine stackMachine) throws IOException {
        lexer = lex;
        this.stackMachine=stackMachine;
        move();
    }

    void move() throws IOException {
        look = lexer.scan();
    }

    void error(String s) {
        throw new Error("near line "+lexer.line+":"+s);
    }

    void match(int t) throws IOException {
        if ( look.tag == t ) move();
        else error("syntax error");
    }


    public void P() throws IOException {
        top=new Env(top);
        D();
        L();
    }

    public void D() throws IOException {
        Type b=B();
        N(b);
        lexer.setCurrentType(null);
        match(';');
        D1();
    }

    public Type B() throws IOException {
        Type b=(Type)look;
        match(Tag.BASIC);
        return b;
    }

    public void D1() throws IOException {
        if(look.tag== Tag.BASIC) {
            D();
        } else {

        }
    }

    public void N(Type b) throws IOException {
        Token tok = look;
        Id id = new Id((Word)tok, b, used);
        top.put(tok, id);
        used = used + b.width;
        match(Tag.ID);
        N1(b);
    }

    public void N1(Type b) throws IOException {
        if(look.tag==',') {
            match(',');
            Token tok = look;
            Id id = new Id((Word)tok, b, used);
            top.put(tok, id);
            used = used + b.width;
            match(Tag.ID);
            N1(b);
        } else {

        }

    }

    public void L() throws IOException {
        Stmt stmt;
        stmt=S();
        stackMachine.evaluate(null);
        currentAssigneeSymbol.value=stackMachine.value;
        System.out.println();

        if(currentAssigneeSymbol.type==Type.Int && stackMachine.value instanceof Float) {
            System.out.println("Warning : Stack Machine-Time mismatch (Narrowing convention) between assignee id type ="+currentAssigneeSymbol.type.tostring()+ " calculated value type="+Type.Float.tostring());
        }else if(currentAssigneeSymbol.type==Type.Float && stackMachine.value instanceof Integer) {
            System.out.println("Warning : Stack Machine-Time mismatch (Widening convention) between assignee id type ="+currentAssigneeSymbol.type.tostring()+ " calculated value type="+Type.Int.tostring());
        }

        System.out.println(currentAssigneeSymbol.lexeme+"="+currentAssigneeSymbol.value);
        currentAssigneeSymbol=null;
        match(';');
        L1();
    }

    public void L1() throws IOException {
        if(look.tag==Tag.ID || look.tag==Tag.NUM ||look.tag==Tag.FLOAT|| look.tag==')') {
            L();
        } else {

        }
    }

    public Stmt S() throws IOException {
        Stmt stmt=null;
        if(look.tag==Tag.ID){
            currentAssigneeSymbol= (Word) look;
            Token tok=look;
            Id id = top.get(tok);
            if ( id == null ) error(tok.tostring() + " undeclared") ;
            match(Tag.ID);
            match('=');
            stmt = new Set(id, E());
        } else if(look.tag==Tag.NUM ||look.tag==Tag.FLOAT|| look.tag==')') {
            E();
        } else {
            throw new Error("Syntax Error");
        }
        return stmt;
    }

    public Expr E() throws IOException {
        Expr x=null;
        x=T();
        x=E1(x);
        return x;
    }


    public Expr E1(Expr xx) throws IOException {
            Expr x = xx;
            if(look.tag=='+') {
                Token tok=look;
                match('+');
                x=new Arith(look,x,T());
                System.out.print("+");
                stackMachine.evaluate("+");
                return E1(x);
            }
            return x;
    }

    public Expr T() throws IOException {
        Expr x;
        x=F();
        x=T1(x);
        return x;
    }

    public Expr T1(Expr xx) throws IOException {
            Expr x=xx;
            if(look.tag=='*') {
                Token tok=look;
                match('*');
                x=new Arith(tok,x,F());
                System.out.print("*");
                stackMachine.evaluate("*");
                return T1(x);
            }
            return x;
    }

    public Expr F() throws IOException {
        Expr x=null;
        if(look.tag=='('){
            match('(');
            x=E();
            match(')');
            return x;
        } else if (look.tag==Tag.ID) {
            Word word=(Word) look;
            String workLex=word.lexeme;
            stackMachine.postfixTokenStack.push(word);
            match(Tag.ID);
            System.out.print(workLex);
            Id id = top.get(word);
            if( id == null ) {
                error(look.toString() +"undeclared");
            }
        } else if( look.tag==Tag.NUM) {
            Num num=(Num) look;
            String IntNum=num.tostring();
            match(Tag.NUM);
            stackMachine.postfixTokenStack.push(num);
            System.out.print(IntNum);
            x=new Constant(num,Type.Int);
        } else if( look.tag==Tag.FLOAT) {
            Real real=(Real) look;
            String floatNum =real.tostring();
            match(Tag.FLOAT);
            stackMachine.postfixTokenStack.push(real);
            System.out.print(floatNum);
            x=new Constant(real,Type.Float);
        } else {
            throw new Error("Syntax Error");
        }
        return x;
    }
}
