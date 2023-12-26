package br.senai.sp.escolamvc.enums;

public enum EstadoCivil {
    SOLTEIRO("Solteiro"),
    CASADO("Casado"),
    DIVORCIADO("Divorciado"),
    VIUVO("Viúvo"),
    SEPARADO("Separado"),
    UNIAO_ESTAVEL("União Estável");

    private String descricao;

    EstadoCivil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
