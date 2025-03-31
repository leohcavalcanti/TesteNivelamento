package com.webscraping.service;

import com.webscraping.model.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DocumentScraperService {
    private final List<String> documentExtensions = List.of(".pdf");
    private final String downloadFolder = "download_docs";

    public DocumentScraperService(){
        try {
            Files.createDirectories(Paths.get(downloadFolder));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar pasta de download: " + e.getMessage());
        }
    }

    public List<Document> scrapeAndDownloadDocuments(String url) throws IOException {
        List<Document> foundDocuments = new ArrayList<>();

        org.jsoup.nodes.Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/134.0.3124.93")
                .get();

        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("abs:href");
            for (String ext : documentExtensions) {
                if (href.toLowerCase().endsWith(ext)) {
                    Document document = new Document(href, ext);
                    downloadFile(document);
                    foundDocuments.add(document);
                    break;
                }
            }
        }

        return foundDocuments;
    }

    private void downloadFile(Document document) throws IOException {
        URL fileUrl = new URL(document.getUrl());
        Path filePath = Paths.get(downloadFolder, document.getFileName());

        try (InputStream in = fileUrl.openStream();
             FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    public String getDownloadFolder() {
        return downloadFolder;
    }
}
