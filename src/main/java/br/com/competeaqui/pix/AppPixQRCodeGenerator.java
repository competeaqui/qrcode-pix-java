package br.com.competeaqui.pix;

import java.math.BigDecimal;

/**
 * Gera um QRCode PIX "Copia e Cola" e a devida imagem na pasta atual.
 * Este projeto foi baseado em uma biblioteca PHP disponível em
 * <a href="https://github.com/renatomb/php_qrcode_pix">https://github.com/renatomb/php_qrcode_pix</a>.
 *
 * @see <a href="https://github.com/bacen/pix-api">Documentação oficial do PIX no repositório do Banco Central</a>
 * @author Manoel Campos da Silva Filho
 */
class AppPixQRCodeGenerator {
    public static void main(String[] args) {
        final var imageFileName = "qrcode.png";

        final var dadosPix =
                new DadosEnvioPix(
                        "Manoel Campos da Silva Fh", "manoelcampos@gmail.com",
                        new BigDecimal(1.0), "Palmas", "PIX em Java");

        final var qrCodePix = new QRCodePix(dadosPix);
        qrCodePix.save(imageFileName);
        System.out.println("QRCode:");
        System.out.println(qrCodePix);
        System.out.printf("%nArquivo gerado em " + imageFileName);
    }
}
