package org.proyecto.tfgfront.clients;

import org.proyecto.tfgfront.clients.dto.CategoriaDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GeneratorControllerClient {


    @GET("/categorias/{categoriaId}")
    Call<CategoriaDto> getCategoria(
            @Path("categoriaId") String categoriaId
    );
}
