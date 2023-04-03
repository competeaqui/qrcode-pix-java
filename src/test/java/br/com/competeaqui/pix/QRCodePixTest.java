package br.com.competeaqui.pix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static br.com.competeaqui.pix.QRCodePix.tempImgFilePath;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerais para a classe {@link QRCodePix}.
 * @author Manoel Campos da Silva Filho
 */
class QRCodePixTest {
    /**
     * Caminho da imagem cujo conteúdo é esperado que seja igual ao da imagem gerada nos testes.
     */
    private static final String QRCODE_FILENAME = "src/test/resources/qrcode-test.png";

    private static final DadosEnvioPix DADOS = new DadosEnvioPix("Manoel", "11111111111", new BigDecimal("1.0"), "Palmas");

    /**
     * QRCode que deve ser gerado para os {@link #DADOS} definidos anteriormente.
     */
    private static final String QRCODE = "00020126370014BR.GOV.BCB.PIX011111111111111020052040000530398654041.005802BR5906Manoel6006Palmas62070503***630477F1";
    private QRCodePix instance;

    /**
     * Cria uma instância de {@link QRCodePix} a partir de dados pré-definidos.
     * <p>
     *     <b>AVISO:</b> Se estes dados forem alterados, o arquivo {@link #QRCODE_FILENAME} precisa ser atualizado.
     * </p>
     */
    @BeforeEach
    void setUp() {
        instance = new QRCodePix(DADOS);
    }

    /**
     * Ao chamar o método {@link QRCodePix#generate()},
     * ele deve armazenar o resultado em um atributo retornado pelo toString().
     */
    @Test
    void toStringEmptyBeforeGenerate() {
        assertTrue(instance.toString().isEmpty());
        instance.generate();
        assertFalse(instance.toString().isEmpty());
    }

    /**
     * Ao chamar o método {@link QRCodePix#save(String)} antes do {@link QRCodePix#generate()},
     * ele deve chamar o segundo, e então armazenar o resultado em um atributo retornado pelo toString().
     */
    @Test
    void toStringEmptyBeforeSave() {
        assertTrue(instance.toString().isEmpty());
        instance.save(tempImgFilePath());
        assertFalse(instance.toString().isEmpty());
    }

    /**
     * Verifica se o QRCode foi gerado corretamente e se o toString tá retornando o mesmo
     * resultado de generate.
     */
    @Test
    void generateAndToString() {
        assertEquals(QRCODE, instance.generate());
        assertEquals(QRCODE, instance.toString());
    }

    @Test
    void saveAndCheckFileContent(final TestInfo info) throws IOException {
        final var testName = getTestName(info);
        final String caminhoImgGerada = "target/test-classes/%s.png".formatted(testName);
        System.out.printf("Gerando arquivo temporário com QRCode em %s%n", caminhoImgGerada);
        final byte[] bytesArqImgGerado = instance.saveInternal(caminhoImgGerada);

        final byte[] bytesArqImgEsperado = Files.readAllBytes(Paths.get(QRCODE_FILENAME));
        assertArrayEquals(bytesArqImgEsperado, bytesArqImgGerado);
    }

    private static String getTestName(final TestInfo info) {
        return info.getTestMethod()
                   .map(m -> m.getDeclaringClass().getSimpleName() + "." + m.getName())
                   .orElse(String.valueOf(System.currentTimeMillis()));
    }

    @Test
    void saveRandomFileCheckExists() {
        final String caminhoImgGerada = instance.save();
        System.out.printf("Gerado arquivo temporário com QRCode em %s%n", caminhoImgGerada);
        assertTrue(Files.exists(Paths.get(caminhoImgGerada)));
    }

    @Test
    void saveInvalidFile() {
        final String invalidFileName = "\\///&&&.png";
        final var exception = assertThrows(RuntimeException.class, () -> instance.save(invalidFileName));
        assertInstanceOf(IOException.class, exception.getCause());
    }

    @Test
    void saveFilenameWithoutExtension() {
        assertThrows(IllegalArgumentException.class, () -> instance.save("nome-do-arquivo-sem-extensao"));
    }

    @Test
    void constructorIdTransacaoMuitoGrande() {
        final var idInvalido = "i".repeat(26);
        assertThrows(IllegalArgumentException.class, () -> new QRCodePix(DADOS, idInvalido));
    }

    @Test
    void strLenLeftPadded() {
        final var strInvalidLen = "a".repeat(100);
        assertThrows(IllegalArgumentException.class, () -> QRCodePix.strLenLeftPadded(strInvalidLen));
    }
}