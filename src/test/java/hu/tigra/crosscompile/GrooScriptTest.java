package hu.tigra.crosscompile;

import org.grooscript.GrooScript;
import org.junit.Test;

/**
 * Created by KZS on 2015.11.28..
 */
public class GrooScriptTest {

    @Test
    public void testScript() throws Exception {
        String script = "{ message -> println(message) }";
        String javaScript = GrooScript.convert(script);
        System.out.println(javaScript);
    }
}
