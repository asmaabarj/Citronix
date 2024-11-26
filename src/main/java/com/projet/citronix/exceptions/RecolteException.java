package com.projet.citronix.exceptions;

public class RecolteException extends RuntimeException {
        public RecolteException(String message) {
        super(message);
    }

    public static class RecolteExistantePourSaisonException extends RecolteException {
        public RecolteExistantePourSaisonException() {
            super("Une récolte existe déjà pour cette saison cette année");
        }
    }

    public static class RecolteInexistanteException extends RecolteException {
        public RecolteInexistanteException() {
            super("Récolte non trouvée");
        }
    }


    public static class SaisonDateIncompatibleException extends RecolteException {
        public SaisonDateIncompatibleException() {
            super("La date de récolte n'est pas compatible avec la saison sélectionnée");
        }
    }

    public static class ArbreDejaRecolteException extends RecolteException {
        public ArbreDejaRecolteException() {
            super("Cet arbre a déjà été récolté pour cette saison cette année");
        }
    }
} 