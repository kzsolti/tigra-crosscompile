package hu.tigra.crosscompile.validator

import org.grooscript.GrooScript
import org.grooscript.util.GrooScriptException

public class UgyletValidatorFactory {

    private static final GroovyClassLoader GROOVY_CLASS_LOADER = new GroovyClassLoader(
        UgyletValidatorFactory.class.getClassLoader());

    private static final String closureFactory = """class ClosureFactory {
    def create() {
        //source
    }
}"""

    private static final String groovyScript = "{ \n    def ugylet ->\n        def messages = []\n        if (!(ugylet.tajszam ==~ /\\d{9}/)) {\n            messages << 'A tajszám formátuma kilenc számjegy kell legyen!'\n        }\n        if (!(ugylet.ugyletszam ==~ /.*2015/)) {\n            messages << 'Csak idei ügyletet lehet indítani!'\n        }\n//        if (ugylet.beerkezesIdeje.isAfter(java.time.LocalDate.now())) {\n//            messages << 'Beérkezés ideje nem lehet a jövőben!'\n//        }\n        if (ugylet.mellekletSzam > 10) {\n            messages << 'Nem lehet több mint 10 melléklet!'\n        }\n        messages\n}";

    public static String getJavaScript() throws GrooScriptException {
        return GrooScript.convert(groovyScript);
    }

    public static Closure getGroovyScript() {
        def clazz = GROOVY_CLASS_LOADER.parseClass(closureFactory.replace("//source", groovyScript));
        clazz.newInstance().create()
    }

}
