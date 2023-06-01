package org.proyecto.tfgfront.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.proyecto.tfgfront.model.Categoria;
import org.proyecto.tfgfront.model.Test;
import org.proyecto.tfgfront.model.TestGestor;
import org.proyecto.tfgfront.model.Usuario;
import org.proyecto.tfgfront.session.Session;
import org.proyecto.tfgfront.session.TestConfigurator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UniRestController {

    private final String hora = " 02:00:00.000";

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


    public static Usuario login(String email, String password) {
        try {
            HttpResponse<String> response = Unirest.get("http://localhost:8080/usuarios/{email}/{pass}")
                    .routeParam("email", email)
                    .routeParam("pass", password)
                    .asString();

            if (response.getStatus() == 200 && !response.getBody().isEmpty()) {
                Gson gson = new Gson();
                Usuario user = gson.fromJson(response.getBody(), Usuario.class);
                Session.setUsuario(user);
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Test getTest(Integer usuarioId, Integer categoriaId, Integer numPreguntas) {
        String url = "http://localhost:8080/tests/preguntas2/{usuarioId}/{categoriaId}";
        if (numPreguntas != null) {
            url += "?numPreguntas=" + numPreguntas;
        }
        try {
            Gson gson = new Gson();

            HttpResponse<String> response = Unirest.get(url)
                    .routeParam("usuarioId", String.valueOf(usuarioId))
                    .routeParam("categoriaId", String.valueOf(categoriaId))
                    .asString();

            int statusCode = response.getStatus();
            if (statusCode == 200) {
                String responseBody = response.getBody();
                Test test = gson.fromJson(responseBody, Test.class);
                TestConfigurator.setfecha(test.getFecha() + hora);

                return test;
            } else {
                System.err.println("Error: Unexpected response status - " + statusCode);
                System.err.println("Response body: " + response.getBody());
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
    public List<TestGestor> getTestByUserId(Integer usuarioId) {
        String url = "http://localhost:8080/test/{usuarioId}";

        try {
            Gson gson = new Gson();

            HttpResponse<String> response = Unirest.get(url)
                    .routeParam("usuarioId", String.valueOf(usuarioId))
                    .asString();

            int statusCode = response.getStatus();
            if (statusCode == 200) {
                String responseBody = response.getBody();
                Type listType = new TypeToken<List<TestGestor>>(){}.getType();
                List<TestGestor> tests = gson.fromJson(responseBody, listType);
                return tests;
            } else if (statusCode == 204) {
                System.out.println("No tests found for user with id - " + usuarioId);
                return new ArrayList<>();
            } else {
                System.err.println("Error: Unexpected response status - " + statusCode);
                System.err.println("Response body: " + response.getBody());
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


    public void postTest(Test test) {
        String url = "http://localhost:8080/test/correccion";

        try {
            Gson gson = new Gson();

            HttpResponse<String> response = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(test))
                    .asString();

            int statusCode = response.getStatus();
            if (statusCode != 200) {
                System.err.println("Error: Unexpected response status - " + statusCode);
                System.err.println("Response body: " + response.getBody());
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public TestGestor getCorreccion() {
        String url = "http://localhost:8080/test/{usuarioId}/{fechaTest}";

        try {
            Gson gson = new Gson();

            HttpResponse<String> response = Unirest.get(url)
                    .routeParam("usuarioId", String.valueOf(Session.getUsuario().getId()))
                    .routeParam("fechaTest", TestConfigurator.getfecha())
                    .asString();

            int statusCode = response.getStatus();
            if (statusCode == 200) {
                String responseBody = response.getBody();
                TestGestor test = gson.fromJson(responseBody, TestGestor.class);
                return test;
            } else {
                System.err.println("Error: Unexpected response status - " + statusCode);
                System.err.println("Response body: " + response.getBody());
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }


    public void altaUsuario(Usuario user) {
        String url = "http://localhost:8080/usuarios";

        try {
            Gson gson = new Gson();

            HttpResponse<String> response = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(gson.toJson(user))
                    .asString();

            int statusCode = response.getStatus();
            if (statusCode != 200) {
                System.err.println("Error: Unexpected response status - " + statusCode);
                System.err.println("Response body: " + response.getBody());
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public boolean enviarCorreo(String email) {
        String url = "http://localhost:8080/recuperaPass";

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);

            HttpResponse<String> response = Unirest.post(url)
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                    .asString();

            int statusCode = response.getStatus();
            if (statusCode == 200) {
                return true;
            } else {
                return false;
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }

    }





}

