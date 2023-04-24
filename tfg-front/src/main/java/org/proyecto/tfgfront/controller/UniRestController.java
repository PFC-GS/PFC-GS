package org.proyecto.tfgfront.controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.proyecto.tfgfront.model.Categoria;
import org.proyecto.tfgfront.model.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UniRestController {

    

    public List<Categoria> httpCategoria() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            HttpResponse<String> response = Unirest.get("http://localhost:8080/categorias")
                    .header("accept", "application/json")
                    .asString();
            String responseBody = response.getBody();
            Gson gson = new Gson();
            Categoria[] categoriaArray = gson.fromJson(responseBody, Categoria[].class);
            categorias = Arrays.asList(categoriaArray);
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return categorias;
    }


    public static Usuario login2(String email, String password) {
    try {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/usuarios/{email}/{pass}")
                .routeParam("email", email)
                .routeParam("pass", password)
                .asString();

        if (response.getStatus() == 200 && !response.getBody().isEmpty()) {
            Gson gson = new Gson();
            Usuario user = gson.fromJson(response.getBody(), Usuario.class);
            return user;
        } else {
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


}
