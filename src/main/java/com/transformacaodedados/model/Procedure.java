package com.transformacaodedados.model;

public class Procedure {
    private final String procedimento;
    private final String rhAlteracao;
    private final String vigencia;
    private final String od;
    private final String amb;
    private final String hco;
    private final String hso;
    private final String ref;
    private final String pac;
    private final String dut;
    private final String subgrupo;
    private final String grupo;
    private final String capitulo;

    public Procedure(String procedimento, String rhAlteracao, String vigencia, String od,
                        String amb, String hco, String hso, String ref, String pac, String dut,
                        String subgrupo, String grupo, String capitulo) {
        expirationData(procedimento);
        this.procedimento = procedimento;
        this.rhAlteracao = rhAlteracao != null ? rhAlteracao : "";
        this.vigencia = vigencia != null ? vigencia : "";
        this.od = od != null ? od : "";
        this.amb = amb != null ? amb : "";
        this.hco = hco != null ? hco : "";
        this.hso = hso != null ? hso : "";
        this.ref = ref != null ? ref : "";
        this.pac = pac != null ? pac : "";
        this.dut = dut != null ? dut : "";
        this.subgrupo = subgrupo != null ? subgrupo : "";
        this.grupo = grupo != null ? grupo : "";
        this.capitulo = capitulo != null ? capitulo : "";
    }

    private void expirationData(String procedimento) {
        if (procedimento == null || procedimento.trim().isEmpty()) {
            throw  new IllegalArgumentException("Procedimento não pode ser vazio!");
        }
    }

    public String toCSVRow() {
        return String.format("\"%s\",%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",%s,%s",
                procedimento, rhAlteracao, vigencia, od, amb,
                hco, hso, ref, pac, dut, subgrupo, grupo, capitulo);
    }
}
