package org.proyecto.tfgfront.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proyecto.tfgfront.constants.Constants;
import org.proyecto.tfgfront.model.Categoria;
import org.proyecto.tfgfront.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class UniRestController {


    public List<Categoria> httpCategoria() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(Constants.HTTP_LOCALHOST_8080_CATEGORIAS)
                    .header("accept", "application/json")
                    .asJson();
            JSONArray jsonArray = jsonResponse.getBody().getArray();
            for (Object obj : jsonArray) {
                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    int id = jsonObject.getInt("id");
                    String nombre = jsonObject.getString("nombre");
                    Categoria categoria = new Categoria(id, nombre);
                    categorias.add(categoria);
                }
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return categorias;
    }
    public Usuario httpLogin(String name, String password) {
        Usuario usuario = null;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(Constants.HTTP_LOCALHOST_8080_USUARIOS_LOGIN)
                    .header("accept", "application/json")
                    .queryString("name", name) // Pasa el parámetro de nombre de usuario
                    .queryString("password", password) // Pasa el parámetro de contraseña
                    .asJson();


            int statusCode = jsonResponse.getStatus();

            if (statusCode == 200) { // Verifica el estado de la respuesta
                JSONArray jsonArray = jsonResponse.getBody().getArray();
                if (jsonArray.length() == 1) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Integer id = jsonObject.getInt("id");
                    String nombre = jsonObject.getString("nombre");
                    String apellidos = jsonObject.getString("apellidos");
                    String emailUsuario = jsonObject.getString("email");
                    String passwordUsuario = jsonObject.getString("password");
                    boolean admin = jsonObject.getBoolean("admin");
                    usuario = new Usuario(id, nombre, apellidos, emailUsuario, passwordUsuario, admin, null);
                } else {
                    System.err.println("Error: No se encontró el objeto JSON esperado");
                }
            }









        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return usuario;
    }


}
