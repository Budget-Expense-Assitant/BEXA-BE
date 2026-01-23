package de.bexa.errorMessages;

import org.jspecify.annotations.Nullable;

public class UserErrorMessages {
    public static final String USER_NOT_FOUND = "User existiert nicht!";
    public static final String USER_ALREADY_EXISTS = "Benutzer existiert bereits";
    public static final String PASSWORD_TOO_SHORT = "Das Passwort ist zu kurz (min. 8 Zeichen)";
    public static final String USERNAME_TOO_SHORT = "Der Benutzername ist zu kurz";
    public static final String USERNAME_TOO_LONG = "Der Benutzername ist zu lang";
    public static final String CANNOT_BE_NULL = "Benutzername oder Passwort darf nicht null sein";

    public static String USER_NOT_FOUND(String username){
        return "Benutzer " + username + " nicht gefunden";
    }
    public static String USER_ID_NOT_FOUND(String userId){
        return "Benutzer mit ID " + userId + " nicht gefunden";
    }
    public static String USER_SAVINGS_DOCUMENT_ALREADY_EXISTS(String userId) {
        return "Der Benutzer mit der ID \"" + userId + "\" besitzt bereits ein Spar-Dokument";
    }
    public static String USER_SAVINGS_DOCUMENT_NOT_FOUND(String userId) {
        return "Es wurde kein Spar-Dokument für den Benutzer mit der ID \"" + userId + "\" gefunden";
    }
}