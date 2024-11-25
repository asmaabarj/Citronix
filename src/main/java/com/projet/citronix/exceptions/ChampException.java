package com.projet.citronix.exceptions;

public class ChampException extends RuntimeException {
    public ChampException(String message) {
        super(message);
    }
    
    public static class SuperficieMinimaleException extends ChampException {
        public SuperficieMinimaleException() {
            super("La superficie du champ doit être supérieure ou égale à 0.1 hectare");
        }
    }
    
    public static class SuperficieMaximaleException extends ChampException {
        public SuperficieMaximaleException() {
            super("La superficie du champ ne peut pas dépasser 50% de la superficie de la ferme");
        }
    }
    
    public static class NombreMaximalChampsException extends ChampException {
        public NombreMaximalChampsException() {
            super("Une ferme ne peut pas avoir plus de 10 champs");
        }
    }
    
    public static class SuperficieTotaleException extends ChampException {
        public SuperficieTotaleException() {
            super("La somme des superficies des champs doit être strictement inférieure à celle de la ferme");
        }
    }
} 