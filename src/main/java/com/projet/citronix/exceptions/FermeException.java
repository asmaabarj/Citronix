package com.projet.citronix.exceptions;

public class FermeException extends RuntimeException {
    public FermeException(String message) {
        super(message);
    }

    public static class SuperficieInvalideException extends FermeException {
        public SuperficieInvalideException() {
            super("La superficie de la ferme doit être supérieure ou égale à 0.2 hectare");
        }

        public SuperficieInvalideException(String message) {
            super(message);
        }
    }

    public static class NomExistantException extends FermeException {
        public NomExistantException() {
            super("Une ferme avec ce nom existe déjà");
        }
    }
}