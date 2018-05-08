package sk.upjs.vma.formativ.DB;


import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetroFitFactory {

    private static Retrofit getRetrofit(){
        String url = "http://192.168.0.100:80/formativ/";

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static SchemaJson getSchema() {
        return getRetrofit().create(SchemaJson.class);
    }
}
