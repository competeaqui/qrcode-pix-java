package br.com.competeaqui.pix;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.com.competeaqui.pix.DadosEnvioPixValorTest.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de validação de parâmetros dos construtores de {@link DadosEnvioPix}.
 *
 * <p>As constantes com nomes abreviados são irrelevantes para os testes.
 * Cada teste considera apenas o valor inválido passado por meio de uma variável
 * (estas constantes são valores válidos).</p>
 *
 * @author Manoel Campos da Silva Filho
 */
class DadosEnvioPixInvalidosTest {
    static final String EMPTY = "";
    static final String BLANK = "    ";
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

    @Test
    void chaveMuitoGrande() {
        final var chaveInvalido = "a".repeat(78);
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, chaveInvalido, V, CR));
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

    @Test
    void descricaoBlankAlteradaPraEmpty() {
        final var instance = new DadosEnvioPix(ND, CD, V, CR, BLANK);
        assertTrue(instance.descricao().isEmpty());
    }

    @Test
    void descricaoNull() {
        assertThrows(NullPointerException.class, () -> new DadosEnvioPix(ND, CD, V, CR, null));
    }

    @Test
    void descricaoMuitoGrande() {
        final var descricaoInvalida = "a".repeat(73);
        assertThrows(IllegalArgumentException.class, () -> new DadosEnvioPix(ND, CD, V, CR, descricaoInvalida));
    }
}