package com.webscraping.controller;

import com.webscraping.model.Document;
import com.webscraping.service.DocumentScraperService;
import com.webscraping.view.ConsoleView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DocumentScraperController {
    private final DocumentScraperService service;
    private final ConsoleView view;

    public DocumentScraperController(DocumentScraperService service, ConsoleView view) {
        this.service = service;
        this.view = view;
    }

    public void scrapeAndProcess(String url) {
        try {
            List<Document> documents = service.scrapeAndDownloadDocuments(url);
            view.displayDocuments(documents);

            if(!documents.isEmpty()) {
                zipDocuments(documents, service.getDownloadFolder());
                view.displayMessage("Arquivos compactados com sucesso em documentos.zip");
            }
        }catch (IOException e) {
            view.displayError("Erro ao procesasr: " + e.getMessage());
        }
    }

    private void zipDocuments(List<Document> documents, String folder) throws IOException {
        try (FileOutputStream fos = new FileOutputStream("documentos.zip");
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            byte[] buffer = new byte[1024];

            for (Document doc : documents) {
                File file = new File(folder, doc.getFileName());
                try (FileInputStream fis = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(doc.getFileName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }
    }
}
