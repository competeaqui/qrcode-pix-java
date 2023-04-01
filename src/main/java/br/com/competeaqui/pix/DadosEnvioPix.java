package br.com.competeaqui.pix;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Dados a serem preenchidos pelo usuário para envio de um PIX.
 *
 * @author Manoel Campos da Silva Filho
 * @param chaveDestinatario chave PIX do destinatário
 * @param descricao descrição da transação (opcional)
 * @param nomeDestinatario nome do destinatário do PIX
 * @param cidadeRemetente cidade de origem do PIX
 * @param valor valor a ser transferido
 * @author Manoel Campos da Silva Filho
 */
@Builder
public record DadosEnvioPix(String chaveDestinatario, String descricao, String nomeDestinatario, String cidadeRemetente, BigDecimal valor) {
    public DadosEnvioPix {
        if(nomeDestinatario.length() > 25) {
            final var msg = "Nome do destinatário não pode ter mais que 25 caracteres. '%s' tem %d caracteres."
                            .formatted(nomeDestinatario, nomeDestinatario.length());
            throw new IllegalArgumentException(msg);
        }
    }
}

