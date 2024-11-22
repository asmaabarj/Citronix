package com.projet.citronix.exceptions;

public class RecolteException extends RuntimeException {
    public RecolteException(String message) {
        super(message);
    }

    public static class RecolteExistantePourSaisonException extends RecolteException {
        public RecolteExistantePourSaisonException() {
            super("Une récolte existe déjà pour cette saison");
        }
    }

    public static class RecolteInexistanteException extends RecolteException {
        public RecolteInexistanteException() {
            super("Récolte non trouvée");
        }
    }

    public static class DateRecolteInvalideException extends RecolteException {
        public DateRecolteInvalideException() {
            super("La date de récolte est invalide");
        }
    }

    public static class SaisonInvalideException extends RecolteException {
        public SaisonInvalideException() {
            super("La saison spécifiée est invalide");
        }
    }
} 