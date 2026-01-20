package de.bexa.errorMessages;

public class UserErrorMessages {
    public static final String USER_ALREADY_EXISTS = "Benutzer existiert bereits";
    public static final String PASSWORD_TOO_SHORT = "Das Passwort ist zu kurz (min. 8 Zeichen)";
    public static final String USERNAME_TOO_SHORT = "Der Benutzername ist zu kurz";
    public static final String USERNAME_TOO_LONG = "Der Benutzername ist zu lang";
    public static final String CANNOT_BE_NULL = "Benutzername oder Passwort darf nicht null sein";

    public static String USER_NOT_FOUND(String username){
        return "Benutzer " + username + " nicht gefunden";
    }
}