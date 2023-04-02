# QRCode PIX Java [![build](https://github.com/competeaqui/qrcode-pix-java/actions/workflows/build.yml/badge.svg)](https://github.com/competeaqui/qrcode-pix-java/actions/workflows/build.yml)

Biblioteca Java para geração de QRCode PIX "Copia e Cola" e exportação do código para imagem.

## 1. Exemplo de PIX Gerado

O projeto vem com uma aplicação console de exemplo que gera o código do PIX "Copia e Cola" e salva o QRCode em um arquivo `qrcode.png` na raiz do projeto.

Para testar, você pode abrir o app do seu banco e executar qualquer um dos passos a seguir. 

> **Note:** Considere pagar o PIX como uma contribuição para o projeto. Isso nos ajuda a continuar o desenvolvimento.

### 1.1. Copiar o código abaixo e colar na opção "PIX Copia e Cola".

```
00020126590014BR.GOV.BCB.PIX0122manoelcampos@gmail.com0211PIX em Java52040000530398654041.005802BR5925Manoel Campos da Silva Fh6006Palmas62070503***6304FEC1
```

### 1.2. Escanear este QRCode

![qrcode.png](images%2Fqrcode.png)

## 2. Requisitos

O projeto requer uma versão atualizada do maven e o JDK 17+ instalados para fazer build e JRE 17+ para execução.

## 3. Rodando a Aplicação

A aplicação de exemplo disponível na classe [AppPixQRCodeGenerator.java](src/main/java/br/com/competeaqui/pix/AppPixQRCodeGenerator.java) gerou o código PIX mostrando anteriormente. A forma mais fácil de executar a aplicação é abrir o projeto no seu IDE e executar tal classe, onde você pode alterar os dados do PIX como desejar.

## 4. Atribuição

Este projeto foi baseado em uma biblioteca PHP disponível em https://github.com/renatomb/php_qrcode_pix.

## 5. Licença

O projeto é licenciado sob a [GPLv3](LICENSE) e disponibilizado como está. Nenhuma responsabilidade deve ser atribuída aos desenvolvedores pelo uso da biblioteca.

## 6. Contribuição

Uma das formas de contribuir com o projeto é considerar o pagamento do QRCode mostrado acima, mas há diferentes outras forma que você pode verificar no [Guia de Contribuição](CONTRIBUTNG.md).

## 7. Links

- [Documentação oficial do PIX no repositório do Banco Central do Brasil](https://github.com/bacen/pix-api)
