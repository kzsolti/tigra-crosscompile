package hu.tigra.crosscompile.validator

import hu.tigra.crosscompile.domain.Ugylet

import java.time.LocalDate

/**
 * Created by KZS on 2015.11.28..
 */
class UgyletValidatorFactoryTest extends GroovyTestCase {

    void testScript() {
        def ugylet = new Ugylet(id: 1, ugyfel: 'Teszt Elek', ugyletszam: '123',
            beerkezesIdeje: LocalDate.now().plusDays(2), tajszam: '12345678', mellekletSzam: 12)
        println UgyletValidatorFactory.javaScript
        def messages = UgyletValidatorFactory.groovyScript(ugylet)
        println messages
    }
}
