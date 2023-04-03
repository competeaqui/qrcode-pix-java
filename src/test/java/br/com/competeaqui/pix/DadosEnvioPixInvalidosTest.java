package br.com.competeaqui.pix;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.competeaqui.pix.DadosEnvioPixValorTest.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes de validação de parâmetros dos construtores de {@link DadosEnvioPix}.
 *
 * <p>As constantes posuem nomes abreviados por são irrelevantes para os testes.
 * Cada teste considera apenas o valor inválido passado por meio de uma variável
 * (as constantes são valores válidos).</p>
 *
 * @author Manoel Campos da Silva Filho
 */
class DadosEnvioPixInvalidosTest {
    private static final BigDecimal V = new BigDecimal(1);

    /** Nome no limite do tamanho máximo. */
    @Test
    void nomeDestinatarioNoLimite() {
        final var nomeInvalido = "a".repeat(25);
        assertDoesNotThrow(() -> new DadosEnvioPix(nomeInvalido, CD, V, CR));
    }

    @Test
    void nomeDestinatarioMuitoGrande() {
        final var nomeInvalido = "a".repeat(26);
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(nomeInvalido, CD, V, CR));
    }


    /** Valor no limite do tamanho máximo. */
    @Test
    void valorNoLimite() {
        final var valorInvalido = new BigDecimal("1234567890.00"); // 13 caracteres (com o ponto)
        assertDoesNotThrow(() -> new DadosEnvioPix(ND, CD, valorInvalido, CR));
    }

    /** Quando o total de caracteres do valor (incluíndo o ponto) é maior do que o suportado. */
    @Test
    void valorDoubleMuitoGrande() {
        final var valorInvalido = new BigDecimal("12345678901.00"); // 14 caracteres (com o ponto)
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, CD, valorInvalido, CR));
    }

    /** Quando o total de caracteres do valor (incluíndo o ponto) é maior do que o suportado. */
    @Test
    void valorIntMuitoGrande() {
        // 11 caracteres, mas neste caso será incluído .00 ficando com 14 (além do limite)
        final var valorInvalido = new BigDecimal("12345678901");
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, CD, valorInvalido, CR));
    }

    /** Cidade no limite do tamanho máximo. */
    @Test
    void cidadeRemetenteNoLimite() {
        final var cidadeInvalida = "a".repeat(15);
        assertDoesNotThrow(() -> new DadosEnvioPix(ND, CD, V, cidadeInvalida));
    }

    @Test
    void cidadeRemetenteMuitoGrande() {
        final var cidadeInvalida = "a".repeat(16);
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, CD, V, cidadeInvalida));
    }
}