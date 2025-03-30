package com.webscraping.service;

import com.webscraping.model.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentScapingService {

    private final String url;
    private final List<String> documentExtensions;

    public DocumentScapingService(String url){
        this.url = url;
        this.documentExtensions = List.of(".pdf");
    }

    public List<Document> scrapeDocuments() throws IOException {
        List<Document> foundDocuments = new ArrayList<>();

        org.jsoup.nodes.Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36 Edg/134.0.3124.93")
                .get();

        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("abs.href");
            for (String ext : documentExtensions) {
                if (href.toLowerCase().endsWith(ext)) {
                    foundDocuments.add(new Document(href, ext));
                    break;
                }
            }
        }

        return foundDocuments;
    }
}
