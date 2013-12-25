package parser;

import inter.Id;
import inter.Node;
import lexer.Num;
import lexer.Tag;
import lexer.Token;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 12/25/13
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ThreeAddressCodeGenarator {
    public StringBuffer threeAddressCodes=new StringBuffer();

    Node getNode(AbstractNode l,AbstractNode r,String op) throws IOException {

        Node n = null,temp;

        for (int i = 0; i < AbstractNode.used.size(); i++) {

            if(AbstractNode.used.get(i).getClass().toString().equals("class compilerimp.Node")){
                n=(Node)AbstractNode.used.get(i);
                if(n.op.equals(op)&&l==n.left&&r==n.right){
                    return n;
                }

            }
        }
        if(!op.equals("=")){ // widen is done for both operands if not assignment
            if(widen(l, typeMax(l, r))){
                AbstractNode.statVal++;
                l=new Node("(float)", l, null); //make a unary op for casting
                l.type="float";
                toIncCode(l); //generate code for temporary
            }
            if(widen(r, typeMax(l, r))){
                AbstractNode.statVal++;
                r=new Node("(float)", r, null); //make a unary op for casting
                r.type="float";
                toIncCode(r); //generate code for temporary
            }
        }
        else{ // if assignment only right side is updated
            if(widen(r, typeMax(l, r))){
                AbstractNode.statVal++;
                r=new Node("(float)", r, null); //make a unary op for casting
                r.type="float";
                toIncCode(r); //generate code for temporary
            }

        }


        AbstractNode.statVal++; //give a new number to the temperory
        n=new Node(op, l, r);


        if(!n.op.equals("=")){
            n.type=typeMax(l, r);  // in assignment we can't change type
        }
        toIncCode(n);


        AbstractNode.used.add(n);

        return n;
    }

    Leaf getLeaf(Token token) throws IOException{

        Leaf l;
        for (int i = 0; i < AbstractNode.used.size(); i++) {

            if(AbstractNode.used.get(i).getClass().toString().equals("class parser.Leaf")){
                l=(Leaf)AbstractNode.used.get(i);
                if(token==(l.token))
                    return l;

            }
        }
        //    System.out.print("leaf ="+token.tag);
        l=new Leaf(token);
        l.type=token.type;
        AbstractNode.used.add(l);
        // toIncCode(l, bw);
        return l;
    }




    private void toIncCode(AbstractNode inNode) throws IOException{

        Leaf l;
        Node n;
        Id id = null,assId = null;
        Num num;
        String val;
        String leftstr,rightstr = null;
        n=(Node)inNode;
        if(n.right!=null) {

            if(!n.op.equals("=")) {
                leftstr="t"+n.left.value;
                rightstr="t"+n.right.value;
                if(n.left.getClass().toString().equals("class parser.Leaf")){ //if the child is leaf print its lexeme
                    l=(Leaf)n.left;
                    if(l.token.tag== Tag.ID)
                    {
                        id=(Id)l.token;
                        leftstr=id.lexeme;
                        //  if(widen(n.left, n.type)){}

                    }
                    else{
                        num=(Num)l.token;
                        leftstr=num.lexeme;
                    }
                }
                if(n.right.getClass().toString().equals("class parser.Leaf")){
                    l=(Leaf)n.right;
                    if(l.token.tag==Tag.ID)
                    {
                        id=(Id)l.token;
                        leftstr=id.lexeme;
                    }
                    else{
                        num=(Num)l.token;
                        rightstr=num.lexeme;
                    }
                }

                System.out.println("t"+n.value+"= "+leftstr+n.op+rightstr);

            }
            else{  //an assignment

                //  System.out.println(n.left.type+" operand");
                l=(Leaf)n.left;
                assId=(Id)l.token; // left of assignment is definitely id

                if(assId.type.equals("int")&&n.right.type.equals("float")){
                    throw new Error("narrowing conversion");
                }
                if(!n.right.getClass().toString().equals("class parser.Leaf")) //if the right side is node
                {
                    rightstr="t"+n.right.value;
                }
                else{

                    l=(Leaf)n.right;
                    if(l.token.tag==Tag.ID)
                    {
                        id=(Id)l.token;
                        leftstr=id.lexeme;
                    }
                    else{
                        num=(Num)l.token;
                        rightstr=num.lexeme;
                    }
                }
                System.out.println(assId.lexeme+"= "+rightstr);
                   /* else{ // not an assignment of an expression to a terminal but to a temp

                                if(l.token.tag==Tag.ID){ //if id is assigned to temp
                                    id=(Id)l.token;
                            bw.write("t"+(i+1)+"= "+id.lexeme);  }

                                if(l.token.tag==Tag.NUM){ //if num is assigned to temp
                                    num=(Num)l.token;
                            bw.write("t"+(i+1)+"= "+num.lexeme);  }
                    }*/

            }
            System.out.println();
        }
        else{
            if(n.left.getClass().toString().equals("class parser.Leaf")){
                l=(Leaf)n.left;
                System.out.println("t"+n.value+"= "+n.op+l.token.lexeme);
            }
            else{
                System.out.println("t"+n.value+"= "+n.op+"t"+n.left.value);
            }
            System.out.println();
        }
    }

    private boolean widen(AbstractNode n,String maxtype) throws IOException{//returns true if a widening conversion should be done

        if ( maxtype.equals(n.type) ) return false;
        else{
            return true;
        }


    }

    private String typeMax(AbstractNode l,AbstractNode r){

        String max="int";

        if(r.type.equals("float")||l.type.equals("float")){
            max="float";
        }
        return max;
    }
}
