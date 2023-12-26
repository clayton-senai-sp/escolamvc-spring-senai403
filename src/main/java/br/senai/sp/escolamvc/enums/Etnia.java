package br.senai.sp.escolamvc.enums;

public enum Etnia {
    BRANCA("Branca"),
    PRETA("Preta"),
    PARDA("Parda"),
    AMARELA("Amarela"),
    INDIGENA("Indígena"),
    NAO_DECLARADA("Não declarada");

    private String descricao;

    Etnia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
