package org.proyecto.tfgfront.model.interfaz;

import org.proyecto.tfgfront.model.Categoria;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface UICategoria {
    @GET("categorias")
    Call<List<Categoria>> getCategorias();
}
