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
public class ThreeAddressCodeGenerator {

    public StringBuffer threeAddressCodes=new StringBuffer();

    Node generateCodeForNode(AbstractNode left, AbstractNode right, String op) throws IOException {

        Node n = null,temp;

        for (AbstractNode abstractNode:AbstractNode.used) {

            if(abstractNode instanceof Node){
                n=(Node)abstractNode;
                if(n.op.equals(op)&&left==n.left&&right==n.right){
                    return n;
                }

            }
        }

        if(!op.equals("=")){ // widen is done for both operands if not assignment
            if(widen(left, getMaxType(left, right))){
                AbstractNode.statVal++;
                left=new Node("(float)", left, null); //make a unary op for casting
                left.type="float";
                generateCode(left); //generate code for temporary
            }
            if(widen(right, getMaxType(left, right))){
                AbstractNode.statVal++;
                right=new Node("(float)", right, null); //make a unary op for casting
                right.type="float";
                generateCode(right); //generate code for temporary
            }
        }
        else{ // if assignment only right side is updated
            if(widen(right, getMaxType(left, right))){
                AbstractNode.statVal++;
                right=new Node("(float)", right, null); //make a unary op for casting
                right.type="float";
                generateCode(right); //generate code for temporary
            }
        }
        AbstractNode.statVal++; //give a new number to the temperory
        n=new Node(op, left, right);
        if(!n.op.equals("=")){
            n.type= getMaxType(left, right);  // in assignment we can't change type
        }
        generateCode(n);
        AbstractNode.used.add(n);
        return n;
    }

    Leaf insertAndGetLeaf(Token token) throws IOException{

        Leaf leaf;
        for (AbstractNode abstractNode:AbstractNode.used) {
            if(abstractNode instanceof Leaf){
                leaf=(Leaf)abstractNode;
                if(token==(leaf.token))
                    return leaf;
            }
        }
        leaf=new Leaf(token);
        leaf.type=token.type;
        AbstractNode.used.add(leaf);
        return leaf;
    }

    private void generateCode(AbstractNode inNode) throws IOException{
        Leaf l;
        Node node;
        Id id = null,assId = null;
        Num num;
        String leftSym,rightSym = null;
        node=(Node)inNode;
        if(node.right!=null) {
            if(!node.op.equals("=")) {
                leftSym="t"+node.left.value;
                rightSym="t"+node.right.value;
                if(node.left instanceof Leaf){ //if the child is leaf print its lexeme
                    l=(Leaf)node.left;
                    if(l.token.tag== Tag.ID)
                    {
                        id=(Id)l.token;
                        leftSym=id.lexeme;
                    } else{
                        num=(Num)l.token;
                        leftSym=num.lexeme;
                    }
                } if(node.right instanceof Leaf){
                    l=(Leaf)node.right;
                    if(l.token.tag==Tag.ID)
                    {
                        id=(Id)l.token;
                        rightSym=id.lexeme;
                    }
                    else{
                        num=(Num)l.token;
                        rightSym=num.lexeme;
                    }
                }
                System.out.println("t"+node.value+"= "+leftSym+node.op+rightSym);
            }
            else{  //an assignment
                l=(Leaf)node.left;
                assId=(Id)l.token; // left of assignment is definitely id
                if(assId.type.equals("int")&&node.right.type.equals("float")){
                    throw new Error("Narrowing conversion is not allowed ");
                }
                if(!(node.right instanceof Leaf)) //if the right side is node
                {
                    rightSym="t"+node.right.value;
                }
                else{

                    l=(Leaf)node.right;
                    if(l.token.tag==Tag.ID)
                    {
                        id=(Id)l.token;
                        leftSym=id.lexeme;
                    }
                    else{
                        num=(Num)l.token;
                        rightSym=num.lexeme;
                    }
                }
                System.out.println(assId.lexeme+"= "+rightSym);
            }
            System.out.println();
        } else{
            if(node.left instanceof Leaf){
                l=(Leaf)node.left;
                System.out.println("t"+node.value+"= "+node.op+l.token.lexeme);
            }
            else{
                System.out.println("t"+node.value+"= "+node.op+"t"+node.left.value);
            }
            System.out.println();
        }
    }

    private boolean widen(AbstractNode n,String maxtype) throws IOException{//returns true if a widening conversion should be done
        if (maxtype.equals(n.type))
        {
            return false;
        } else{
            return true;
        }
    }

    private String getMaxType(AbstractNode l, AbstractNode r) {
        String max="int";
        if(r.type.equals("float")||l.type.equals("float")){
            max="float";
        }
        return max;
    }
}
