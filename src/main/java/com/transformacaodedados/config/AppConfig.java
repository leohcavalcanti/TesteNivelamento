package com.transformacaodedados.config;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try {
            java.io.InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream("application.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                properties.setProperty("pdf.path", "arquivo/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
                properties.setProperty("csv.filename", "procedimentos.csv");
                properties.setProperty("zip.filename", "Teste_LeonardoCavalcanti.zip");
            }
        } catch (IOException e) {
            properties.setProperty("pdf.path", "arquivo/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf");
            properties.setProperty("csv.filename", "procedimentos.csv");
            properties.setProperty("zip.filename", "Teste_LeonardoCavalcanti.zip");
        }
    }

    public static String getPdfPath() {
        String pdfPath = properties.getProperty("pdf.path");
        if (pdfPath != null && !pdfPath.isEmpty()) {
            return pdfPath;
        }
        return "arquivo/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
    }

    public static  String getCsvFileName() {
        return properties.getProperty("csv.filename", "procedimentos.csv");
    }

    public static String getZipFileName() {
        return properties.getProperty("zip.filename", "Teste_LeonardoCavalcanti.zip");
    }
}
