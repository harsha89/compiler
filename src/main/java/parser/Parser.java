package parser;


import inter.Id;
import lexer.*;
import postfix.StackMachine;
import symbols.Env;
import symbols.Type;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public StringBuffer postFix=new StringBuffer();
    private static String lookahead;
    private Lexer lexer;// lexical analyzer for this parser
    private StackMachine stackMachine;
    private Token look; // lookahead tagen
    Env top = null; // current or top symbol table
    int used = 0; // storage used for declarations
    private Id currentAssigneeSymbol;
    private ThreeAddressCodeGenarator threeAddressCodeGenerator;
    public Parser(Lexer lex , StackMachine stackMachine) throws IOException {
        lexer = lex;
        this.stackMachine=stackMachine;
        threeAddressCodeGenerator=new ThreeAddressCodeGenarator();
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
        Id id = (Id)tok;
        top.put(tok, id);
        used = used + b.width;
        match(Tag.ID);
        N1(b);
    }

    public void N1(Type b) throws IOException {
        if(look.tag==',') {
            match(',');
            Token tok = look;
            Id id = (Id)tok;
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
        //System.out.println();
        postFix.append("\n");
        if(currentAssigneeSymbol.type=="int" && stackMachine.value instanceof Float) {
            postFix.append("Warning : Stack Machine-Time mismatch (Narrowing convention) between assignee id type ="+currentAssigneeSymbol.type+ " calculated value type="+Type.Float.tostring()+"\n");
            // System.out.println("Warning : Stack Machine-Time mismatch (Narrowing convention) between assignee id type ="+currentAssigneeSymbol.typeOb.tostring()+ " calculated value type="+Type.Float.tostring());
        }else if(currentAssigneeSymbol.type=="float" && stackMachine.value instanceof Integer) {
            postFix.append("Warning : Stack Machine-Time mismatch (Widening convention) between assignee id type ="+currentAssigneeSymbol.type+ " calculated value type="+Type.Int.tostring()+"\n");
            // System.out.println("Warning : Stack Machine-Time mismatch (Widening convention) between assignee id type ="+currentAssigneeSymbol.typeOb.tostring()+ " calculated value type="+Type.Int.tostring());
        }

        //System.out.println(currentAssigneeSymbol.lexeme+"="+currentAssigneeSymbol.value);
        postFix.append(currentAssigneeSymbol.lexeme+"="+currentAssigneeSymbol.value+"\n");
        currentAssigneeSymbol=null;

        // toCode(AbsNode.used,bw);
        AbstractNode.used=new ArrayList<AbstractNode>(); // new set of nodes for new stmt
        AbstractNode.statVal=0;
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
        AbstractNode node;
        AbstractNode exprn;
        if(look.tag==Tag.ID){
            currentAssigneeSymbol= (Id) look;
            match(Tag.ID);
            match('=');
            exprn=E();
            node=threeAddressCodeGenerator.getNode(threeAddressCodeGenerator.getLeaf(currentAssigneeSymbol),exprn , "="); //3AC for assignment
        } else if(look.tag==Tag.NUM ||look.tag==Tag.FLOAT|| look.tag==')') {
            node=E();
        } else {
            throw new Error("Syntax Error");
        }
    }

    public AbstractNode E() throws IOException {
        AbstractNode node = null;
        AbstractNode termnode = null;

        termnode=T();
        node=E1(termnode);
        return node;
    }


    public AbstractNode E1(AbstractNode termnodeinh) throws IOException {
        AbstractNode node; // node which rerpesents the operation so far
        AbstractNode snode;  // synthesised attribute which gives the full answer
        AbstractNode pretn=termnodeinh; //previous term nonterminal is inherited
        AbstractNode curtn;
        if(look.tag=='+') {
            match('+');
            curtn=T();
            //System.out.print("+");
            postFix.append("+");
            stackMachine.evaluate("+");
            node=threeAddressCodeGenerator.getNode(pretn, curtn, "+");
            snode=E1(node);
        }
        else {
            snode=termnodeinh;
        }
        return snode;
    }

    public AbstractNode T() throws IOException {
        AbstractNode node = null;
        AbstractNode factnode = null;
        factnode=F();
        node=T1(factnode);
        return node;
    }

    public AbstractNode T1(AbstractNode factnodeinh) throws IOException {
        AbstractNode node; // node which rerpesents the operation so far
        AbstractNode snode;  // synthesised attribute which gives the full answer
        AbstractNode prefn=factnodeinh; //previous factor nonterminal is inherited
        AbstractNode curfn;
        if(look.tag=='*') {
            match('*');
            curfn=F();
            //System.out.print("*");
            postFix.append("*");
            stackMachine.evaluate("*");
            node=threeAddressCodeGenerator.getNode(prefn, curfn, "*");
            snode=T1(node);
        } else {
            snode=factnodeinh;
        }
        return snode;
    }

    public AbstractNode F() throws IOException {
        AbstractNode abstractNode=null;
        if(look.tag=='('){
            match('(');
            abstractNode=E();
            match(')');
        } else if (look.tag==Tag.ID) {
            Id word=(Id) look;
            String workLex=word.lexeme;
            stackMachine.postfixTokenStack.push(word);
            match(Tag.ID);
            abstractNode=threeAddressCodeGenerator.getLeaf(word);// changed
            postFix.append(workLex);
            //System.out.print(workLex);
        } else if( look.tag==Tag.NUM) {
            Num num=(Num) look;
            String IntNum=num.tostring();
            match(Tag.NUM);
            stackMachine.postfixTokenStack.push(num);
            abstractNode=threeAddressCodeGenerator.getLeaf(num);// changed
            postFix.append(IntNum);
            //System.out.print(IntNum);
        } else if( look.tag==Tag.FLOAT) {
            Real real=(Real) look;
            String floatNum =real.tostring();
            match(Tag.FLOAT);
            stackMachine.postfixTokenStack.push(real);
            abstractNode=threeAddressCodeGenerator.getLeaf(real);// changed
            postFix.append(floatNum);
            //System.out.print(floatNum);
        } else {
            throw new Error("Syntax Error");
        }
        return abstractNode;
    }
}
