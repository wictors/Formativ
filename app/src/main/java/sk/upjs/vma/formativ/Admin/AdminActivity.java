package sk.upjs.vma.formativ.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import sk.upjs.vma.formativ.R;

public class AdminActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextMeno = findViewById(R.id.editTextMeno);
        editTextPriezvisko = findViewById(R.id.editTextPriezvisko);
        editTextHeslo = findViewById(R.id.editTextHeslo);
        editTextHeslo2 = findViewById(R.id.editTextZopakovatHeslo);
        spinnerTyp = findViewById(R.id.spinnerTyp);

    }

    public void registracia(View view) {
        editTextMeno.setError(null);
        editTextPriezvisko.setError(null);
        editTextHeslo.setError(null);
        editTextHeslo2.setError(null);

        meno = editTextMeno.getText().toString();
        priezvisko = editTextPriezvisko.getText().toString();
        heslo = editTextHeslo.getText().toString();
        heslo2 = editTextHeslo2.getText().toString();

        typ = spinnerTyp.getSelectedItem().toString().substring(0,1);
        prihlasovacie_meno = meno.substring(0,1).toLowerCase() + priezvisko.toLowerCase();

        Log.d("REGUJEM","typ: " + typ + "prihlasovacie: " + prihlasovacie_meno);


    }
}
