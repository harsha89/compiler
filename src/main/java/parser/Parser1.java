package parser;

import inter.Expr;
import inter.Id;
import lexer.*;
import postfix.StackMachine;
import symbols.Env;
import symbols.Type;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 10/1/13
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser1 {
    /*package parser;

    import inter.Expr;
    import inter.Id;
    import lexer.*;
    import postfix.StackMachine;
    import symbols.Env;
    import symbols.Type;

    import javax.script.ScriptException;
    import java.io.IOException;

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

            S();
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

        public void S() throws IOException {
            if(look.tag==Tag.ID){
                currentAssigneeSymbol= (Word) look;
                match(Tag.ID);
                match('=');
                E();
            } else if(look.tag==Tag.NUM ||look.tag==Tag.FLOAT|| look.tag==')') {
                E();
            } else {
                throw new Error("Syntax Error");
            }
        }

        public void E() throws IOException {
            T();
            E1();
        }


        public void E1() throws IOException {

            if(look.tag=='+') {
                match('+');
                T();
                System.out.print("+");
                stackMachine.evaluate("+");
                E1();
            }
            else {

            }
        }

        public void T() throws IOException {
            F();
            T1();
        }

        public Expr T1() throws IOException {
            Expr x;
            if(look.tag=='*') {
                match('*');
                x=F();
                System.out.print("*");
                stackMachine.evaluate("*");
                T1();
            } else {

            }

        }

        public void F() throws IOException {
            if(look.tag=='('){
                match('(');
                E();
                match(')');
            } else if (look.tag==Tag.ID) {
                Word word=(Word) look;
                String workLex=word.lexeme;
                stackMachine.postfixTokenStack.push(word);
                match(Tag.ID);
                System.out.print(workLex);
            } else if( look.tag==Tag.NUM) {
                Num num=(Num) look;
                String IntNum=num.tostring();
                match(Tag.NUM);
                stackMachine.postfixTokenStack.push(num);
                System.out.print(IntNum);
            } else if( look.tag==Tag.FLOAT) {
                Real real=(Real) look;
                String floatNum =real.tostring();
                match(Tag.FLOAT);
                stackMachine.postfixTokenStack.push(real);
                System.out.print(floatNum);
            } else {
                throw new Error("Syntax Error");
            }
        }
    }
    */
}
