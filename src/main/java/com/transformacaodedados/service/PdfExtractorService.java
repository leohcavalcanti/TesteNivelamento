package com.transformacaodedados.service;

import com.transformacaodedados.model.Procedure;
import com.transformacaodedados.util.LoggerUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfExtractorService {
    private final String pdfPath;

    public PdfExtractorService(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public List<Procedure> extractProcedure() throws IOException {
        LoggerUtil.info("Iniciando extração de PDF: " + pdfPath);

        PDDocument document;

        if (!pdfPath.startsWith("/") && !pdfPath.contains(":")) {
            InputStream pdfStream = getClass().getClassLoader().getResourceAsStream(pdfPath);
            if (pdfStream == null) {
                throw new IOException("Arquivo PDF não encontrado no classpath: " + pdfPath);
            }
            document = PDDocument.load(pdfStream);
        } else {
            File file = new File(pdfPath);
            if (!file.exists()) {
                throw new IOException("Arquivo PDF não encontrado: " + pdfPath);
            }
            document = PDDocument.load(file);
        }

        try {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return processTextToProcedure(text);
        } finally {
            document.close();
        }
    }

    private List<Procedure> processTextToProcedure(String text) {
        List<Procedure> procedures = new ArrayList<>();
        String[] lines = text.split("\n");

        for (String line : lines) {
            try {
                Procedure procedure = parseLine(line);
                if (procedure != null) {
                    procedures.add(procedure);
                }
            } catch (Exception e) {
                LoggerUtil.error("Erro ao processar linha: " + line, e);
            }
        }
        LoggerUtil.info("Extraídos " + procedures.size() + " procedimentos");
        return procedures;
    }

    private Procedure parseLine(String line) {
        String[] parts = line.trim().split("\\s+");

        if (parts.length < 13) {
            return null;
        }

        String capitulo = parts[parts.length - 1];
        String grupo = parts[parts.length - 2];
        String subgrupo = String.join(" ", java.util.Arrays.copyOfRange(parts, parts.length - 5, parts.length - 2));

        int fixedColumnsStart = parts.length - 10; // 10 colunas fixas antes de SUBGRUPO
        String rhAlteracao = parts[fixedColumnsStart];
        String vigencia = parts[fixedColumnsStart + 1];
        String od = expandAbbreviation(parts[fixedColumnsStart + 2], "OD");
        String amb = expandAbbreviation(parts[fixedColumnsStart + 3], "AMB");
        String hco = parts[fixedColumnsStart + 4];
        String hso = parts[fixedColumnsStart + 5];
        String ref = parts[fixedColumnsStart + 6];
        String pac = parts[fixedColumnsStart + 7];
        String dut = parts[fixedColumnsStart + 8];

        String procedimento = String.join(" ", java.util.Arrays.copyOfRange(parts, 0, fixedColumnsStart));

        if (procedimento.trim().isEmpty()) {
            return null;
        }

        return new Procedure(procedimento, rhAlteracao, vigencia, od, amb, hco, hso, ref, pac, dut, subgrupo, grupo, capitulo);
    }

    private String expandAbbreviation(String abbr, String type) {
        if (abbr == null || abbr.trim().isEmpty()) {
            return "";
        }
        if (type.equals("OD") || type.equals("AMB")) {
            switch (abbr) {
                case "S": return "Sim";
                case "N": return "Não";
                default: return abbr; // Mantém como está se não for S ou N
            }
        }
        return abbr;
    }
}
