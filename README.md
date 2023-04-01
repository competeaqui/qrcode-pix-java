# QRCode PIX Java [![build](https://github.com/competeaqui/qrcode-pix-java/actions/workflows/build.yml/badge.svg)](https://github.com/competeaqui/qrcode-pix-java/actions/workflows/build.yml)

Classes para geração de QRCode PIX "Copia e Cola"

## 1. Exemplo de PIX Gerado

### 1.1. Você pode usar o código abaixo para o PIX "Copia e Cola".
Copie o código e cole no app do seu banco.

```
00020126590014BR.GOV.BCB.PIX0122manoelcampos@gmail.com0211PIX em Java52040000530398654041.005802BR5925Manoel Campos da Silva Fh6006Palmas62070503***6304FEC1
```

### 1.2. Ou escaneie o QRCode abaixo no app do seu banco:

![qrcode.png](images%2Fqrcode.png)

## 2. Build

O projeto requer maven e o JDK 17 instalados para fazer o build.

## 3. Rodando a Aplicação

Abra a classe [AppPixQRCodeGenerator.java](src/main/java/br/com/competeaqui/pix/AppPixQRCodeGenerator.java) no seu IDE e execute.

Uma imagem `qrcode.png` é salva na raiz do projeto quando a aplicação termina.

## 4. Atribuição

Este projeto foi baseado em uma biblioteca PHP disponível em https://github.com/renatomb/php_qrcode_pix.

## 5. Links

- [Documentação oficial do PIX no repositório do Banco Central](https://github.com/bacen/pix-api)
