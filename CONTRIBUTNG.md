# Como contribuir para o projeto

Contribuições são sempre bem vindas. Você pode contribuir de diferentes formas, como apresentado a seguir.

## 1. Formas de contribuir

1. Clicando no botão **Star** ⭐️ no topo da página do projeto no GitHub, nos dando mais visibilidade.
1. Participando das discurssões nas issues ou na aba [Discussions](https://github.com/competeaqui/qrcode-pix-java/discussions).
1. Relatando problemas ou solicitando recursos (features), por meio de uma nova issue (veja instruções a seguir). É necessário fornecer os dados solicitados ao criar a issue. Quanto mais dados você fornecer, maior a probabilidade da issue ser resolvida.
1. Melhorando a documentação do projeto.
1. Corrigindo bugs e implementando novos recursos.

## 2. Iniciando sua Contribuição

Existem algumas diretrizes que precisamos que os colaboradores sigam para alguns tipos de contribuição mostrados abaixo.

### 2.1 Solicitando recurso ou reportando um bug

Se você quer solicitar um recurso ou relatar um problema, verifique primeiro se o problema/recurso que você quer reportar/requisitar já foi reportado/requisitado na página de [issues](https://github.com/competeaqui/qrcode-pix-java/issues). Tente pesquisar as issues existentes usando algumas palavras-chave antes de criar uma nova. Se não existe uma issue relacionada, sinta-se livre para criar uma. Por fim, tenha certeza de que cada issue criada esteja relacionada a um único recurso a ser solicitado ou bug a ser reportado.

### 2.2 Corrigindo um bug ou implementando novo recurso

#### 2.2.1. Criando um fork para iniciar sua contribuição

Antes de começar a programar, você precisa fazer um fork do repositório do projeto no GitHub. Você pode corrigir um bug ou implementar um recurso de uma issue já aberta por outra pessoa ou por você mesmo, seguindo mandatoriamente os passos abaixo:

- Se quiser resolver uma issue existente, inicie uma conversa na página da issue para os mantenedores do projeto saberem que você pretende trabalhar nela. Isso evita trabalho duplicado e ainda permite discutir questões de modelagem e implementação.
- Para começar a programar, crie um novo branch a partir do branch `main` para conter suas alterações. O nome de tal branch deve ter o formato `issue-ID` (onde ID é o código da issue). Para criar um novo branch a partir de `main`, execute: `git checkout main -b issue-ID`. Por favor, evite fazer alterações diretamente no branch `main`.

#### 2.2.2 Diretrizes de qualidade de código

Enquanto você programa, é preciso ter em mente as seguintes diretrizes, de forma que a probabilidade das suas contribuições serem incluídas no projeto serão maiores:

- Evite duplicação de código.
- Crie funções pequenas e com uma única responsabilidade.
- Inclua documentação e testes para as funções.


#### 2.2.3 Faça seus commits

Crie commits pequenos, específicos. Assim como suas funções devem ser pequenas, seus commits devem ser focados em resolver um único problema. A resolução de uma issue normalmente pode requerer vários commits. Gaste algum tempo escrevendo mensagens de commit estruturadas, informativas e que descrevem claramente o que você fez em cada commit.

Para enviar as alterações:

- Seu último commit deve incluir na primeira linha a mensagem `Close #ID` para indicar o número da issue que está finalizando.
- Execute `git push` para enviar seu branch para o GitHub.
- A partir do seu fork no GitHub, abra uma Pull Request (responda às perguntas do template).
- Aguarde suas contribuições serem avaliadas e obrigado antecipadamente.

