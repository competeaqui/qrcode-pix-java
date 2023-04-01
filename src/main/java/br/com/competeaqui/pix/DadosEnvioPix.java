package br.com.competeaqui.pix;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Dados a serem preenchidos pelo usuário para envio de um PIX.
 *
 * @author Manoel Campos da Silva Filho
 * @param chaveDestinatario chave PIX do destinatário
 * @param descricao descrição da transação (opcional)
 * @param nomeDestinatario nome do destinatário do PIX (máx 25 caracteres)
 * @param cidadeRemetente cidade de origem do PIX (máx 15 caracteres)
 * @param valor valor a ser transferido (máx 13 caracteres)
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

        if(cidadeRemetente.length() > 15) {
            final var msg = "Cidade do remetente não pode ter mais que 15 caracteres. '%s' tem %d caracteres."
                    .formatted(cidadeRemetente, cidadeRemetente.length());
            throw new IllegalArgumentException(msg);
        }

        final var valorStr = formatNumber(valor);
        if(valorStr.length() > 13) {
            final var msg = "Valor não pode ter mais que 13 caracteres. '%s' tem %d caracteres."
                    .formatted(valorStr, valorStr.length());
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Obtém um valor incluindo o ponto como separador de decimais e apenas 2 casas.
     * @return
     */
    public String valorStr(){
        return formatNumber(valor);
    }

    /**
     * Obtém um valor incluindo o ponto como separador de decimais e apenas 2 casas.
     * @return
     */
    private static String formatNumber(final BigDecimal value){
        return String.format("%.2f", value);
    }
}

