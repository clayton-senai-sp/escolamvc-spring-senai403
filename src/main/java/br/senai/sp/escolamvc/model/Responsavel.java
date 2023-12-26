package br.senai.sp.escolamvc.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "R")
public class Responsavel  extends Pessoa{
}
