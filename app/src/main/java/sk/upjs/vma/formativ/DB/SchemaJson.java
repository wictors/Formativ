package sk.upjs.vma.formativ.DB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sk.upjs.vma.formativ.entity.Odpoved;
import sk.upjs.vma.formativ.entity.Otazka;
import sk.upjs.vma.formativ.entity.Pouzivatel;
import sk.upjs.vma.formativ.entity.Seria;
import sk.upjs.vma.formativ.entity.UspesnostSerie;

public interface SchemaJson {

    @GET("kontrola_prihlasenia.php")
    Call<List<Pouzivatel>> kontrola_prihlasenia(@Query("meno") String meno);

    @GET("zoznam_serii.php")
    Call<List<Seria>> daj_zoznam_serii(@Query("id") int id);

    @POST("registracia.php")
    Call<Boolean> registruj(@Body Pouzivatel pouzivatel);

    @GET("zoznam_pouzivatelov.php")
    Call<List<Pouzivatel>> daj_zoznam_pouzivatelov();

    @POST("nova_seria.php")
    Call<List<Seria>> vytvor_seriu(@Body Seria seria, @Query("id") int id);

//    @POST("otazkyNaVyplnenie.php")
//    Call<List<Otazka>> vyplnitOtazky(@Body Seria seria, @Query("id") int id);

    @GET("zoznam_otazok.php")
    Call<List<Otazka>> daj_zoznam_otazok(@Query("id") int idSerie, @Query("pouzivatel") int idPouzivatel);

    @POST("pridajOtazku.php")
    Call<Boolean> pridajOtazku(@Body Otazka otazka);

    @POST("updateSeria.php")
    Call<Boolean> updateSeria(@Body Seria seria);

    @POST("updateSpustena.php")
    Call<Boolean> updateSpustena(@Body Seria seria);

    @POST("pridajSeriuStudent.php")
    Call<Boolean> pridajSeriuStudent(@Query("seria") int cislo, @Query("pouzivatel") int id);

    @POST("odpovedaj.php")
    Call<Boolean> odpovedaj(@Body Odpoved odpoved);

    @POST("uspesnostSerie.php")
    Call<Boolean> uspesnostSerie(@Body UspesnostSerie uspesnostSerie);

    @GET("uspesnosti.php")
    Call<List<UspesnostSerie>> dajUspesnosti(@Query("seria") int id);

    @POST("updateOtazka.php")
    Call<Boolean> updateOtazka(@Body Otazka otazka);

    @POST("zmazOtazka.php")
    Call<Boolean> zmazOtazka(@Query("idecko") int otazka);

    @POST("zmazSerie.php")
    Call<Boolean> vymazSerie(@Query("idecko") int seria);

    @GET("odpovedeUcitelovi.php")
    Call<List<Odpoved>> dajOdpovedeUcitelovi(@Query("otazka") int id);
}
