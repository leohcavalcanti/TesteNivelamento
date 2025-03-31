package com.webscraping.view;

import com.webscraping.model.Document;

import java.util.List;

public class ConsoleView {
    public void displayDocuments(List<Document> documents) {
        if (documents.isEmpty()) {
            System.out.println("Nenhum documento encontrado.");
        } else {
            System.out.println("Documentos encontrados e baixados: ");
            for (Document doc : documents) {
                System.out.println(doc);
            }
        }
    }

    public void displayError(String msg) {
        System.out.println("Errp: " + msg);
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }
}
