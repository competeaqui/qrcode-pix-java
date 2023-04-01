package br.com.competeaqui.pix;

import java.math.BigDecimal;

/**
 * Gera um QRCode PIX "Copia e Cola" e a devida imagem na pasta atual.
 *
 * @see <a href="https://qrcodepix.dinheiro.tech>https://qrcodepix.dinheiro.tech</a>
 * @see <a href="https://www.bcb.gov.br/content/estabilidadefinanceira/pix/Regulamento_Pix/II_ManualdePadroesparaIniciacaodoPix.pdf">Manual de Padrões para Iniciação do Pix (BCB)</a>
 * @author Manoel Campos da Silva Filho
 */
public class PixQRCodeGenerator {
    public static void main(String[] args) {
        final var imageFileName = "qrcode.png";

        final var dadosPix =
                DadosEnvioPix.builder()
                        .chavePix("manoelcampos@gmail.com")
                        .descricao("lib")
                        .nomeRecebedor("MANOEL")
                        .cidade("PMW")
                        .valor(new BigDecimal(1.0))
                        .build();

        final var qrCodePix = new QRCodePix(dadosPix);
        qrCodePix.save(imageFileName);
        System.out.println("QRCode:\n" + qrCodePix);
        System.out.println("Arquivo gerado em " + imageFileName);
    }
}
