import lexer.Lexer;
import parser.Parser;
import postfix.StackMachine;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/27/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProgramController {
    public static void main(String[] args) throws IOException {
        Lexer lexer=new Lexer();
        StackMachine stackMachine=new StackMachine();
        Parser parser=new Parser(lexer,stackMachine);
        parser.P();
        System.out.println("Finished Successfully");
    }
}
