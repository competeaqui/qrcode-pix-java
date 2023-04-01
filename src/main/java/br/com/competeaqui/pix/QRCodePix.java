package br.com.competeaqui.pix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.EnumMap;

import static java.lang.Integer.toHexString;

/**
 * Gera um QRCode para fazer transferências PIX "Copia e Cola".
 * @author Manoel Campos da Silva Filho
 * @see #generate()
 */
public final class QRCodePix {
    /**
     * Payload Format Indicator. Código 00 com valor fixo 01 (obrigatório)
     */
    private static final String PFI = "01";

    /**
     * Código para identificar o campo com o checksum do QRCode gerado.
     * @see #crcChecksum(String)
     */
    public static final String CRC_CODE = "6304";

    /**
     * Código de país no formato ISO3166-1 alpha 2
     */
    private static final String COD_PAIS = "BR";

    /**
     * Moeda, "986" = BRL: Real Brasileiro - ISO4217
     */
    private static final String MOEDA = "986";

    /**
     * Arranjo específico; Código "00" (GUI) obrigatório e valor fixo: br.gov.bcb.pix
     */
    private static final String ARRANJO_PAGAMENTO = "BR.GOV.BCB.PIX";

    /**
     * Merchant Category Code (MCC ISO18245)
     */
    private static final String MCC = "0000";

    /**
     * Código do campo que armazena o valor do PIX.
     */
    private static final String COD_CAMPO_VALOR = "54";

    /**
     * Identificador único da transação (máx 25 caracteres). Usar *** quando for omitido.
     *
     * <p>Logo do lançamento do pix os aplicativos não estavam implicando com esse campo e até mesmo
     * os BRCodes gerados em aplicativos de alguns bancos não apresentavam esse campo.
     * Porém recentemente identifiquei que algumas instituições já não estão processando os pix na ausência
     * desse campo. Trtaado na issue https://github.com/renatomb/php_qrcode_pix/issues/2
     *
     * Conforme o Manual de Padrões para Iniciação do Pix ver 2.2 na página 5, nota de rodapé, temos: "Conclui-se
     * que, se o gerador do QR optar por não utilizar um  transactionID, o valor ‘***’ deverá ser usado para indicar
     * essa escolha." Diante disso estou atribuindo como sugestão neste exemplo, linha 38, o uso de *** no 6205.
     *
     * O conteúdo desse campo é gerado pelo recebedor do pix. Devendo ser um valor único para cada transação, ou ***
     * quando não for usado pois esse passa a ser gerado automaticamente. Dada a necessidade de identificador único,
     * caso haja a opção pelo uso do mesmo recomendo a utilização de um UUID vinculado ao sistema do recebedor, o que
     * permitirá a conciliação dos pagamentos que foram recebidos.
     *
     * Entretanto, conforme discutido na issue https://github.com/bacen/pix-api/issues/214 o Banco Itaú bloqueia
     * qualquer código de transação que não tenha sido gerado previamente no aplicativo da instituição. Necessário
     * solicitar ao gerente da conta a liberação para que a conta do recebedor possa gerar qrcode do pix fora do
     * aplicativo do banco. É possível que outras instituições passem a adotar esse posicionamento no futuro e até
     * mesmo venham a cobrar por isso.
     *
     * Com o uso de QR Code dinâmicos é possível inclusive definir um webHook onde o cliente final seja notificado
     * automaticamente quando determinada transação for recebida. Mas para isso consulte os detalhes da API da sua
     * instituição.</p>
     */
    private final String idTransacao;

    private final DadosEnvioPix dadosPix;

    /**
     * Último QRCode gerado.
     */
    private String qrCode;

    /**
     * Cria um objeto QRCodePix gerando um UUID como id da transação
     * @param dadosPix Dados preenchidos pelo usuário para envio do PIX
     */
    public QRCodePix(final DadosEnvioPix dadosPix) {
        this(dadosPix, "***");
    }

    /**
     * @param dadosPix Dados preenchidos pelo usuário para envio do PIX
     * @param idTransacao Identificador único da transação (máx 25 caracteres). Usar *** quando for omitido.
     */
    public QRCodePix(final DadosEnvioPix dadosPix, final String idTransacao) {
        if(idTransacao.length() > 25) {
            final var msg = "idTransacao deve ter no máximo 25 caracteres. Valor %s tem %d caracteres.".formatted(idTransacao, idTransacao.length());
            throw new IllegalArgumentException(msg);
        }

        this.idTransacao = idTransacao;
        this.dadosPix = dadosPix;
    }

    /**
     * Cria uma representação em JSON dos dados completos para gerar o QRCode.
     * @return uma String contendo um objeto JSON
     */
    private String toJson() {
        final var jsonTemplate =
            """
            {
                '00': '%s',
                '26': {
                    '00': '%s',
                    '01': '%s',
                    '02': '%s'
                },
                '52': '%s',
                '53': '%s',
                '%s':  %s,
                '58': '%s',
                '59': '%s',
                '60': '%s',
                '62': {
                    '05': '%s'
                }
            }
            """;

        final var json =
            jsonTemplate
                .formatted(
                        PFI, ARRANJO_PAGAMENTO, dadosPix.chavePix(), dadosPix.descricao(),
                        MCC, MOEDA, COD_CAMPO_VALOR, formatNumber(dadosPix.valor()), COD_PAIS,
                        dadosPix.nomeRecebedor(), dadosPix.cidade(), idTransacao);
        return json;
    }

    /**
     * Gera o QRCode PIX "Copia e Cola" para os dados informados.
     * @return o código gerado
     * @see #save(String)
     * @see #toString()
     */
    public String generate() {
        final String partialCode = generateInternal(new JSONObject(toJson())) + CRC_CODE;
        final String checksum = crcChecksum(partialCode);
        this.qrCode = partialCode + checksum;
        return this.qrCode;
    }

    private String generateInternal(final JSONObject jsonObj) {
        final var sb = new StringBuilder();
        jsonObj.keySet().stream().sorted().forEach(keyName -> {
            final Object keyValue = jsonObj.get(keyName);
            if(keyValue instanceof JSONObject jsonObjValue) {
                final var str = generateInternal(jsonObjValue);
                sb.append(leftPad(keyName)).append(strLenLeftPadded(str)).append(str);
            } else {
                final var str = keyName.equals(COD_CAMPO_VALOR) ? keyValue.toString() : removeSpecialChars(keyValue);
                sb.append(leftPad(keyName)).append(strLenLeftPadded(str)).append(str);
            }
        });

        return sb.toString();
    }

    /**
     * Calcula o checksum CRC16 a partir de um código parcial do PIX.
     * @param partialCode código parcial do QRCode
     * @return o checksum em hexadecimal
     */
    private String crcChecksum(final String partialCode){
        int crc = 0xFFFF;
        final var len = partialCode.length();
        for (int c = 0; c < len; c++) {
            crc ^= partialCode.codePointAt(c) << 8;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) == 0)
                    crc = crc << 1;
                else crc = (crc << 1) ^ 0x1021;
            }
        }

        final int decimal = crc & 0xFFFF;
        return leftPad(toHexString(decimal), 4).toUpperCase();
    }

    private String removeSpecialChars(final Object value) {
        return value.toString().replaceAll("[^a-zA-Z0-9@\\.\\*\\s]", "");
    }

    /**
    * Obtém o total de caracteres de uma String incluindo zero a esquerda se necessário.
    * @return o total como uma String de dois dígitos (incluindo zero à esquerda se necessário).
    */
    private String strLenLeftPadded(final String value) {
        if (value.length() > 99) {
            final var msg = "Tamanho máximo dos valores dos campos deve ser 99. '%s' tem %d caracteres.".formatted(value, value.length());
            throw new IllegalArgumentException(msg);
        }

        final String len = String.valueOf(value.length());
        return leftPad(len);
    }

    private String leftPad(final String code) {
        return leftPad(code, 2);
    }

    private String leftPad(final String code, final int len) {
        final var format = "%1$" + len + "s";
        return format.formatted(code).replace(' ', '0');
    }

    /**
     * Obtém um valor com ponto como separador de decimais e apenas 2 casas
     * @return
     */
    public String formatNumber(final BigDecimal valor){
        return String.format("%.2f", valor);
    }

    /**
     * Salva o QRCode gerado com {@link #generate()}
     * em um arquivo de imagem.
     * Se o código não foi gerado ainda, chama automaticamente o {@link #generate()}.
     */
    public void save(final String imageFileName) {
        final var hintsMap = new EnumMap<>(EncodeHintType.class);
        hintsMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintsMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        final int tamanho = 300; // Tamanho da imagem do QRCode em pixels

        final var writer = new QRCodeWriter();
        try {
            if(qrCode == null || qrCode.isBlank())
                generate();

            final var bitMatrix = writer.encode(qrCode, BarcodeFormat.QR_CODE, tamanho, tamanho, hintsMap);
            final var image = new BufferedImage(tamanho, tamanho, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < tamanho; y++) {
                for (int x = 0; x < tamanho; x++) {
                    final boolean bit = bitMatrix.get(x, y);
                    final int cor = (bit ? 0 : 1) & 0xff;
                    image.setRGB(x, y, (cor == 0 ? 0 : 0xFFFFFF));
                }
            }

            //Obtém a extensão do arquivo
            final String[] fileNameParts = imageFileName.split("\\.(?=[^\\.]+$)");
            if(fileNameParts.length == 1)
                throw new IllegalArgumentException("Nome do arquivo deve conter a extensão para indicar o formato da imagem");

            final var format = fileNameParts[1];
            ImageIO.write(image, format, new File(imageFileName));
        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtém o último QRCode gerado.
     * @return
     * @see #generate()
     */
    @Override
    public String toString() {
        return qrCode;
    }
}
