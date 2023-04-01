package br.com.competeaqui.pix;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Dados a serem preenchidos pelo usuário para envio de um PIX.
 *
 * @author Manoel Campos da Silva Filho
 * @param chavePix chave PIX de destino
 * @param descricao descrição da transação (opcional)
 * @param nomeRecebedor nome do destinatário
 * @param cidade cidade de origem do PIX
 * @param valor valor a ser transferido
 * @author Manoel Campos da Silva Filho
 */
@Builder
public record DadosEnvioPix(String chavePix, String descricao, String nomeRecebedor, String cidade, BigDecimal valor) {
    public DadosEnvioPix {
        if(nomeRecebedor.length() > 25) {
            final var msg = "Nome do recebedor não pode ter mais que 25 caracteres. '%s' tem %d caracteres."
                            .formatted(nomeRecebedor, nomeRecebedor.length());
            throw new IllegalArgumentException(msg);
        }
    }
}

