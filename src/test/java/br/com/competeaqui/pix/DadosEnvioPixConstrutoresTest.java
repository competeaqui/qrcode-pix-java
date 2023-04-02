package br.com.competeaqui.pix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Testes para os construtores de {@link DadosEnvioPix}.
 * @author Manoel Campos da Silva Filho
 */
class DadosEnvioPixConstrutoresTest {
    private static final String NOME_DESTINATARIO = "Manoel";
    private static final String CHAVE_DESTINATARIO = "11111111111";
    private static final BigDecimal VALOR = new BigDecimal(1);
    private static final String CIDADE_REMETENTE = "Palmas";
    private static final String DESCRICAO = "PIX em Java";
    private DadosEnvioPix instance;

    @BeforeEach
    void setUp() {
        instance = new DadosEnvioPix(
                NOME_DESTINATARIO, CHAVE_DESTINATARIO,
                VALOR, CIDADE_REMETENTE, DESCRICAO);
    }

    @Test
    void valor() {
        assertEquals(new BigDecimal(1), instance.valor());
        assertSame(VALOR, instance.valor());
    }

    @Test
    void valorStr() {
        assertEquals("1.00", instance.valorStr());
    }

    @Test
    void nomeDestinatario() {
        assertEquals(NOME_DESTINATARIO, instance.nomeDestinatario());
    }

    @Test
    void chaveDestinatario() {
        assertEquals(CHAVE_DESTINATARIO, instance.chaveDestinatario());
    }

    @Test
    void cidadeRemetente() {
        assertEquals(CIDADE_REMETENTE, instance.cidadeRemetente());
    }

    @Test
    void descricao() {
        assertEquals(DESCRICAO, instance.descricao());
    }

    @Test
    void descricaoVazia() {
        final var instance = new DadosEnvioPix(
                NOME_DESTINATARIO, CHAVE_DESTINATARIO,
                VALOR, CIDADE_REMETENTE);
        assertEquals("", instance.descricao());
    }
}