package com.projet.citronix.exceptions;

public class VenteException extends RuntimeException {
    public static class VenteInexistanteException extends VenteException {
        public VenteInexistanteException() {
            super("La vente n'existe pas");
        }
    }

    public static class DateVenteFutureException extends VenteException {
        public DateVenteFutureException() {
            super("La date de vente ne peut pas être dans le futur");
        }
    }

    public static class DateVenteAvantRecolteException extends VenteException {
        public DateVenteAvantRecolteException() {
            super("La date de vente ne peut pas être antérieure à la date de récolte");
        }
    }

    public VenteException(String message) {
        super(message);
    }
}
