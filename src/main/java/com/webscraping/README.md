# **Web Scraper para Documentos**

Este projeto é um web scraper desenvolvido em Java que extrai links de documentos (PDF, DOC, DOCX, TXT) de um site, faz o download desses arquivos mantendo seu formato original e os compacta em um arquivo ZIP. Ele utiliza uma arquitetura MVC (Model-View-Controller) para organização e a biblioteca Jsoup para parsing de HTML.

## Funcionalidades

Scraping: Identifica links para documentos em uma página web.
Download: Baixa os documentos encontrados para uma pasta local (download_docs).
Compactação: Cria um arquivo ZIP (documentos.zip) com todos os documentos baixados.
Configuração: Usa o User-Agent do Microsoft Edge para simular um navegador real.

## Tecnologias Utilizadas

Java: Linguagem principal (JDK 17 ou superior recomendado).
Jsoup: Biblioteca para parsing de HTML (versão 1.17.2).
Maven: Gerenciamento de dependências.
API padrão do Java: Para download (java.net.URL) e compactação (java.util.zip).

## Estrutura do Projeto

O projeto segue o padrão MVC:
Model: Document - Representa um documento com URL, tipo e nome.
Service: DocumentScraperService - Lógica de scraping e download.
Controller: DocumentScraperController - Coordena o processo e compactação.
View: ConsoleView - Exibe resultados no console.

## Pré-requisitos

* JDK: Versão 17 ou superior instalada.
* Maven: Para gerenciar dependências.
* IntelliJ IDEA (opcional): Recomendado para desenvolvimento e execução.

## Configuração

Certifique-se de que o Maven baixou as dependências listadas no pom.xml:
`<dependency>
<groupId>org.jsoup</groupId>
<artifactId>jsoup</artifactId>
<version>1.17.2</version>
</dependency>`

## Como Usar

Edite a URL alvo no arquivo Main.java:<br/>
String targetUrl = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

**Execute o programa:**<br/>
No IntelliJ, clique com o botão direito em Main.java e selecione Run 'Main.main()'.
