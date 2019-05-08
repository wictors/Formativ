package sk.upjs.vma.formativ.DB;


import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetroFitFactory {

    private static Retrofit getRetrofit(){
        String url = "https://thesis.science.upjs.sk/~vseno/";

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static SchemaJson getSchema() {
        return getRetrofit().create(SchemaJson.class);
    }
}
