package com.projet.citronix.exceptions;

public abstract class ArbreException extends RuntimeException {
    protected ArbreException(String message) {
        super(message);
    }

    public static class PeriodePlantationInvalideException extends ArbreException {
        public PeriodePlantationInvalideException() {
            super("Les arbres ne peuvent être plantés qu'entre mars et mai");
        }
    }

    public static class DensiteMaximaleException extends ArbreException {
        public DensiteMaximaleException() {
            super("La densité maximale d'arbres est atteinte pour ce champ (100 arbres/hectare)");
        }
    }

    public static class ArbreNonProductifException extends ArbreException {
        public ArbreNonProductifException() {
            super("L'arbre a dépassé sa durée de vie productive (20 ans)");
        }
    }

    public static class ChampInexistantException extends ArbreException {
        public ChampInexistantException() {
            super("Le champ spécifié n'existe pas");
        }
    }

    public static class ArbreInexistantException extends ArbreException {
        public ArbreInexistantException() {
            super("L'arbre spécifié n'existe pas");
        }
    }

    public static class DatePlantationRequiseException extends ArbreException {
        public DatePlantationRequiseException() {
            super("La date de plantation est obligatoire");
        }
    }

    public static class ChampRequiredException extends ArbreException {
        public ChampRequiredException() {
            super("Le champ d'appartenance est obligatoire");
        }
    }

    public static class DatePlantationFutureException extends ArbreException {
        public DatePlantationFutureException() {
            super("La date de plantation doit être dans le passé");
        }
    }
}
