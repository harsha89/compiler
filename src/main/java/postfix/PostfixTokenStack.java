package postfix;

import lexer.Token;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/28/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostfixTokenStack {
    public Stack<Token> stack;

    public PostfixTokenStack() {
        stack=new Stack<Token>();
    }

    public void push(Token token) {
        stack.push(token);
    }

    public Token pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
