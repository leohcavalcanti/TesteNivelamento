package com.transformacaodedados.service;

import com.transformacaodedados.model.Procedure;
import com.transformacaodedados.util.LoggerUtil;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileProcessorService {
    public void saveToCSVAndZip(List<Procedure> procedures, String csvFileName, String zipFileName) throws IOException {
        LoggerUtil.info("Salvando dados em CSV: " + csvFileName);
        saveToCSV(procedures, csvFileName);
        LoggerUtil.info("Compactando CSV em ZIP: " + zipFileName);
        createZipFile(csvFileName, zipFileName);
        LoggerUtil.info("Processo concluído com sucesso");
    }

    private void saveToCSV(List<Procedure> procedures, String csvFileName) throws IOException {
        try (FileWriter writer = new FileWriter(csvFileName)) {
            writer.write("Procedimento, RH(alteração), Vigência, OD, AMB, HCO, HSO, REF, PAC, DUT, Subgrupo, Grupo, Capítulo\n");
            for (Procedure proc : procedures) {
                writer.write(proc.toCSVRow() + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Erro ao salvar CSV: " + csvFileName, e);
        }
    }

    private void createZipFile(String sourceFile, String zipFileName) throws IOException {
        byte[] buffer = new byte[1024];
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFile)) {

            zos.putNextEntry(new ZipEntry(new File(sourceFile).getName()));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        } catch (IOException e) {
            throw new IOException("Erro ao criar ZIP: " + zipFileName, e);
        }
    }
}
