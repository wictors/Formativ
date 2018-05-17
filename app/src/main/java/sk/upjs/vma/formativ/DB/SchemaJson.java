package sk.upjs.vma.formativ.DB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.entity.Seria;

public interface SchemaJson {

    @GET("kontrola_prihlasenia.php")
    Call<List<Pouzivatel>> kontrola_prihlasenia(@Query("meno") String meno);

    @GET("zoznam_serii.php")
    Call<List<Seria>> daj_zoznam_serii(@Query("id") int id);

    @POST("registracia.php")
    Call<Boolean> registruj(@Body Pouzivatel pouzivatel);

    @GET("zoznam_pouzivatelov.php")
    Call<List<Pouzivatel>> daj_zoznam_pouzivatelov();
}
