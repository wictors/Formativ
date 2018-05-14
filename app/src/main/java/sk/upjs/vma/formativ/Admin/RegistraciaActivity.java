package sk.upjs.vma.formativ.Admin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Pouzivatel;

public class RegistraciaActivity extends AppCompatActivity implements RegistrujListener{

    private EditText editTextMeno;
    private EditText editTextPriezvisko;
    private EditText editTextHeslo;
    private EditText editTextHeslo2;
    private Spinner spinnerTyp;

    private String meno;
    private String priezvisko;
    private String prihlasovacie_meno;
    private String heslo;
    private String heslo2;
    private String typ;

    private static int generovanieSoli = 12;
    private ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracia);

        editTextMeno = findViewById(R.id.editTextMeno);
        editTextPriezvisko = findViewById(R.id.editTextPriezvisko);
        editTextHeslo = findViewById(R.id.editTextHeslo);
        editTextHeslo2 = findViewById(R.id.editTextZopakovatHeslo);
        spinnerTyp = findViewById(R.id.spinnerTyp);

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        Button registrovatButton = findViewById(R.id.buttonRegistrovat);
        registrovatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registracia();
            }
        });

    }

    public void registracia() {
        editTextMeno.setError(null);
        editTextPriezvisko.setError(null);
        editTextHeslo.setError(null);
        editTextHeslo2.setError(null);

        meno = editTextMeno.getText().toString();
        priezvisko = editTextPriezvisko.getText().toString();
        heslo = editTextHeslo.getText().toString();
        heslo2 = editTextHeslo2.getText().toString();
        typ = spinnerTyp.getSelectedItem().toString().substring(0,1);

        boolean cancel = false;
        View focusView = null;

        // Kontrola vyplnenia
        if (TextUtils.isEmpty(meno)) {
            editTextMeno.setError("Meno je povinné");
            focusView = editTextMeno;
            cancel = true;
        }
        // Kontrola vyplnenia
        if (TextUtils.isEmpty(priezvisko)) {
            editTextPriezvisko.setError("Priezvisko je povinné");
            focusView = editTextPriezvisko;
            cancel = true;
        }
        // Kontrola vyplnenia
        if (TextUtils.isEmpty(heslo)) {
            editTextHeslo.setError("Heslo je povinné");
            focusView = editTextHeslo;
            cancel = true;
        }
        // Kontrola vyplnenia
        if (!heslo.equals(heslo2)) {
            editTextHeslo2.setError("Heslo sa nezhoduje !");
            focusView = editTextHeslo2;
            cancel = true;
        }

        if (cancel) {
            // Zameranie na posledne chybne policko
            focusView.requestFocus();
        } else {
            prihlasovacie_meno = meno.substring(0,1).toLowerCase() + priezvisko.toLowerCase();
            odoslanie();
            hotovo();
        }


    }

    private void hotovo() {
        Intent intent = new Intent(this,AdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void odoslanie() {
        Log.d("OVEROVANIE", "Zacinam hashovanie hesla");
        String hash = hashHeslo(heslo);
        Log.d("OVEROVANIE", "KOncim hashovanie hesla");
        Pouzivatel pouzivatel = new Pouzivatel(meno,priezvisko,prihlasovacie_meno,hash,typ);
        if(jeInternet()){
            RegistraciaAsyncTask registraciaAsyncTask = new RegistraciaAsyncTask();
            registraciaAsyncTask.nastavListener(this);
            registraciaAsyncTask.execute(pouzivatel);
        }else{
            Snackbar.make(findViewById(R.id.registraciaLayout), "Žiadne internetové pripojenie !", Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    private static String hashHeslo(String heslo) {
        String sol = BCrypt.gensalt(generovanieSoli);
        String hashovane_heslo = BCrypt.hashpw(heslo, sol);

        return(hashovane_heslo);
    }

    private boolean jeInternet() {
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void vysledokRegistracie(boolean stav) {

    }
}
