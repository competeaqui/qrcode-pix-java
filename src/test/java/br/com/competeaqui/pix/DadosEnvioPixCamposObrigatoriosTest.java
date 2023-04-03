package br.com.competeaqui.pix;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.competeaqui.pix.DadosEnvioPixInvalidosTest.BLANK;
import static br.com.competeaqui.pix.DadosEnvioPixInvalidosTest.EMPTY;
import static br.com.competeaqui.pix.DadosEnvioPixValorTest.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes para verificar se os campos obrigatórios da classe {@link DadosEnvioPix}
 * estão sendo verificados.
 * @author Manoel Campos da Silva Filho
 */
class DadosEnvioPixCamposObrigatoriosTest {
    private static final BigDecimal V = BigDecimal.ONE;

    @Test
    void nomeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(EMPTY, CD, V, CR));
    }

    @Test
    void nomeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(BLANK, CD, V, CR));
    }

    @Test
    void nomeNull() {
        assertThrows(NullPointerException.class, () -> new DadosEnvioPix(null, CD, V, CR));
    }

    @Test
    void chaveEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, EMPTY, V, CR));
    }

    @Test
    void chaveBlank() {
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, BLANK, V, CR));
    }

    @Test
    void chaveNull() {
        assertThrows(NullPointerException.class, () -> new DadosEnvioPix(ND, null, V, CR));
    }

    @Test
    void cidadeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, CD, V, EMPTY));
    }

    @Test
    void cidadeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, CD, V, BLANK));
    }

    @Test
    void cidadeNull() {
        assertThrows(NullPointerException.class, () -> new DadosEnvioPix(ND, CD, V, null));
    }
}