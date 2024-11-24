package com.stadion.stadion_backend.templates;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailTemplates {
    public static String createAccountEmailTemplate(String name, String id) {
        return "<p>Seja bem-vindo ao Stadion!</p>"
            + "<p>Olá " + name + ", Clique no link abaixo para ativar sua conta:</p>"
            + "<a href=\"http://localhost:8080/v1/users/"+ id + "/active\">Ativar conta</a>";
    }
}
