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
    public List<Usuario> httpLogin(String name, String password) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(Constants.HTTP_LOCALHOST_8080_USUARIOS_LOGIN)
                    .header("accept", "application/json")
                    .queryString("name", name) // Pasa el parámetro de nombre de usuario
                    .queryString("password", password) // Pasa el parámetro de contraseña
                    .asJson();

            int statusCode = jsonResponse.getStatus();
            if (statusCode == 200) { // Verifica el estado de la respuesta
                JSONArray jsonArray = jsonResponse.getBody().getArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Integer id = jsonObject.getInt("id");
                    String nombre = jsonObject.getString("nombre");
                    String apellidos = jsonObject.getString("apellidos");
                    String emailUsuario = jsonObject.getString("email");
                    String passwordUsuario = jsonObject.getString("password");
                    boolean admin = jsonObject.getBoolean("admin");
                    Usuario usuario = new Usuario(id, nombre, apellidos, emailUsuario, passwordUsuario, admin, null);
                    usuarios.add(usuario);
                }
            } else {
                System.err.println("Error: " + jsonResponse.getStatusText());
            }
        } catch (UnirestException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return usuarios;
    }
    public Usuario login2 (String email, String password){
        try{
            Usuario user = Unirest.get("localhost:8080/usuarios/{email}/{pass}")
                    .routeParam("email",email)
                    .routeParam("pass",password)
                    .asObject(Usuario.class).getBody();
            return user;

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

    }

}
