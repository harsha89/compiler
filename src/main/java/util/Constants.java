package util;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: harsha
 * Date: 9/27/13
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static Hashtable<String,String> keywords=new Hashtable<String,String>();
    static {
        keywords.put("int","int");
        keywords.put("float","float");
    }
}
