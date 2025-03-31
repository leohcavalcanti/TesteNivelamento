package com.webscraping;

import com.webscraping.controller.DocumentScraperController;
import com.webscraping.service.DocumentScraperService;
import com.webscraping.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        String targeUrl = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

        DocumentScraperService service = new DocumentScraperService();
        ConsoleView view = new ConsoleView();
        DocumentScraperController controller = new DocumentScraperController(service, view);

        controller.scrapeAndProcess(targeUrl);
    }
}
