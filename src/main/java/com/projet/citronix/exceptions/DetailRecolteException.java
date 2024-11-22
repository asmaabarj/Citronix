package com.projet.citronix.exceptions;

public class DetailRecolteException extends RuntimeException {
    public DetailRecolteException(String message) {
        super(message);
    }

    public static class DetailRecolteExistantException extends DetailRecolteException {
        public DetailRecolteExistantException() {
            super("Un détail de récolte existe déjà pour cet arbre dans cette récolte");
        }
    }

    public static class DetailRecolteInexistantException extends DetailRecolteException {
        public DetailRecolteInexistantException() {
            super("Détail de récolte non trouvé");
        }
    }

    public static class ArbreInexistantException extends DetailRecolteException {
        public ArbreInexistantException() {
            super("L'arbre spécifié n'existe pas");
        }
    }

    public static class RecolteInexistanteException extends DetailRecolteException {
        public RecolteInexistanteException() {
            super("La récolte spécifiée n'existe pas");
        }
    }

    public static class QuantiteInvalideException extends DetailRecolteException {
        public QuantiteInvalideException() {
            super("La quantité récoltée est invalide");
        }
    }

    public static class ArbreDejaRecolteException extends DetailRecolteException {
        public ArbreDejaRecolteException() {
            super("Cet arbre a déjà été récolté pour cette saison cette année");
        }
    }
} 