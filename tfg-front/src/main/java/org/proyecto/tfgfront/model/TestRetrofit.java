package org.proyecto.tfgfront.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.proyecto.tfgfront.constants.Constants;
import org.proyecto.tfgfront.model.interfaz.UICategoria;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
@Getter
public class TestRetrofit {

    private  ObservableList<Categoria> categoriaObservableList;

    public ObservableList<Categoria> getDataFromJson() {
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HTTP_LOCALHOST_8080_CATEGORIAS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        UICategoria uiCategoria = retrofit.create(UICategoria.class);
        Call<List<Categoria>> call = uiCategoria.getCategorias();

        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                List<Categoria> categorias = response.body();
                categoriaObservableList = FXCollections.observableArrayList(categorias);
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
        return categoriaObservableList;
    }
}
