package br.com.competeaqui.pix;

/*
 * Classe baseada em uma biblioteca PHP disponível em https://github.com/renatomb/php_qrcode_pix.
 */

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Dados a serem preenchidos pelo usuário para envio de um PIX.
 *
 * @param nomeDestinatario  nome do destinatário (máx 25 caracteres)
 * @param chaveDestinatario chave PIX do destinatário
 * @param valor             valor a ser transferido (máx 13 caracteres)
 * @param cidadeRemetente   cidade de origem do remetente (máx 15 caracteres)
 * @param descricao         descrição da transação (opcional)
 * @see QRCodePix
 * @author Manoel Campos da Silva Filho
 */
public record DadosEnvioPix(String nomeDestinatario, String chaveDestinatario, BigDecimal valor, String cidadeRemetente, String descricao) {
    public DadosEnvioPix(String nomeDestinatario, String chaveDestinatario, BigDecimal valor, String cidadeRemetente) {
        this(nomeDestinatario, chaveDestinatario, valor, cidadeRemetente, "");
    }

    public DadosEnvioPix {
        if(requireNonNull(nomeDestinatario).isBlank())
            throw new IllegalArgumentException("O nome do destinatário é obrigatório.");
        nomeDestinatario = nomeDestinatario.trim();
        if(nomeDestinatario.length() > 25) {
            final var msg = "Nome do destinatário não pode ter mais que 25 caracteres. '%s' tem %d caracteres."
                    .formatted(nomeDestinatario, nomeDestinatario.length());
            throw new IllegalArgumentException(msg);
        }

        if(requireNonNull(chaveDestinatario).isBlank())
            throw new IllegalArgumentException("A chave PIX do destinatário é obrigatória.");
        chaveDestinatario = chaveDestinatario.trim();

        if(requireNonNull(cidadeRemetente).isBlank())
            throw new IllegalArgumentException("A cidade do remetente é obrigatória.");
        cidadeRemetente = cidadeRemetente.trim();
        if(cidadeRemetente.length() > 15) {
            final var msg = "Cidade do remetente não pode ter mais que 15 caracteres. '%s' tem %d caracteres."
                    .formatted(cidadeRemetente, cidadeRemetente.length());
            throw new IllegalArgumentException(msg);
        }

        requireNonNull(descricao, "A descrição não pode ser nula. Informe um texto vazio no lugar.");
        descricao = descricao.trim();
        if(descricao.length() > 72) {
            final var msg = "Descrição não pode ter mais que 72 caracteres. '%s' tem %d caracteres."
                    .formatted(descricao, descricao.length());
            throw new IllegalArgumentException(msg);
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("O valor do PIX deve ser maior que zero.");

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
        return String.format("%.2f", value).formatted().replace(",", ".");
    }
}

