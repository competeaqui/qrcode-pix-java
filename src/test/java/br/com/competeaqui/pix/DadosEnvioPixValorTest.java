package br.com.competeaqui.pix;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes para o campo {@link DadosEnvioPix#valor()}.
 *
 * <p>As constantes posuem nomes abreviados por são irrelevantes para os testes.
 * Cada teste considera apenas o valor inválido passado por meio de uma variável
 * (as constantes são valores válidos).</p>
 *
 * @author Manoel Campos da Silva Filho
 */
class DadosEnvioPixValorTest {
    static final String ND = "Manoel";
    static final String CD = "11111111111";
    static final String CR = "Palmas";

    @Test
    void valorStr1Casa() {
        final var instance = newInstance(new BigDecimal("1.0"));
        assertEquals("1.00", instance.valorStr());
    }

    @Test
    void valorStr2Casas() {
        final var instance = newInstance(new BigDecimal("1.00"));
        assertEquals("1.00", instance.valorStr());
    }

    @Test
    void valorStr3Casas() {
        final var instance = newInstance(new BigDecimal("1.234"));
        assertEquals("1.23", instance.valorStr());
    }

    @Test
    void valorStr3CasasZerosNoMeio() {
        final var instance = newInstance(new BigDecimal("1.001"));
        assertEquals("1.00", instance.valorStr());
    }

    @Test
    void valorStr3CasasZeroFinal() {
        final var instance = newInstance(new BigDecimal("1.230"));
        assertEquals("1.23", instance.valorStr());
    }

    /** Teste de arredondamento pra cima. */
    @Test
    void valorStrMaisDe2CasasCeil() {
        final var instance = newInstance(new BigDecimal("1.229"));
        assertEquals("1.23", instance.valorStr());
    }

    @Test
    void valorStrMaisDe2CasasZeros() {
        final var instance = newInstance(new BigDecimal("1.000"));
        assertEquals("1.00", instance.valorStr());
    }

    private static DadosEnvioPix newInstance(final BigDecimal valor) {
        return new DadosEnvioPix(ND, CD, valor, CR);
    }
}