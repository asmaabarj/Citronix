package com.projet.citronix.exceptions;

public class DetailRecolteException extends RuntimeException {
    public DetailRecolteException(String message) {
        super(message);
    }



    public static class ArbreInexistantException extends DetailRecolteException {
        public ArbreInexistantException() {
            super("L'arbre spécifié n'existe pas");
        }
    }


    public static class ArbreDejaRecolteException extends DetailRecolteException {
        public ArbreDejaRecolteException() {
            super("Cet arbre a déjà été récolté pour cette saison cette année");
        }
    }
} 