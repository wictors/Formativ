package sk.upjs.vma.formativ.prihlasenie;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import sk.upjs.vma.formativ.ActivityStudent.Prehlad_Student;
import sk.upjs.vma.formativ.ActivityUcitel.PrehladUcitelActivity;
import sk.upjs.vma.formativ.Admin.PrehladAdminActivity;
import sk.upjs.vma.formativ.Admin.RegistraciaActivity;
import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Pouzivatel;

public class LoginActivity extends AppCompatActivity implements PrihlasPouzivatelaListener {

    // UI references.
    private EditText menoEditText;
    private EditText hesloEditText;
    private View mProgressView;
    private LinearLayout loginLinearLayout;
    private String prihlasovacie_meno;
    private String heslo;
    private ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        findViewById(R.id.LoginLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        loginLinearLayout = findViewById(R.id.login_linear_layout);
        mProgressView = findViewById(R.id.login_progress);
        menoEditText = findViewById(R.id.text_Meno);
        hesloEditText = findViewById(R.id.text_Heslo);

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        Button prihlasitButton = findViewById(R.id.prihlasit_Button);
        prihlasitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        // Reset chyb.
        menoEditText.setError(null);
        hesloEditText.setError(null);

        prihlasovacie_meno = this.menoEditText.getText().toString();
        heslo = hesloEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Kontrola vyplnenia a dlzky hesla
        if (TextUtils.isEmpty(heslo)) {
            hesloEditText.setError("Heslo je povinné");
            focusView = hesloEditText;
            cancel = true;
        }

        // Kontrola vyplnenia prihlasovacieho mena
        if (TextUtils.isEmpty(prihlasovacie_meno)) {
            this.menoEditText.setError("Meno je povinné");
            focusView = this.menoEditText;
            cancel = true;
        }

        if (cancel) {
            // Zameranie na posledne chybne policko
            Toast.makeText(this, "Udaje su nespravne", Toast.LENGTH_LONG).show();
            focusView.requestFocus();
        } else {
            // Spustenie progressu
            showProgress();
        }
    }

    private boolean jeInternet() {
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showProgress() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mProgressView.setVisibility(View.VISIBLE);
        loginLinearLayout.setVisibility(View.GONE);

        if (jeInternet()) {
            PrihlasenieAsyncTask prihlasenieAsyncTask = new PrihlasenieAsyncTask();
            prihlasenieAsyncTask.nastavListener(this);
            prihlasenieAsyncTask.execute(prihlasovacie_meno, heslo);
        } else {
            mProgressView.setVisibility(View.GONE);
            loginLinearLayout.setVisibility(View.VISIBLE);
            Snackbar.make(loginLinearLayout, "Žiadne internetové pripojenie !", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void prihlasPouzivatela(Pouzivatel pouzivatel) {
        if(pouzivatel == null){
            mProgressView.setVisibility(View.GONE);
            loginLinearLayout.setVisibility(View.VISIBLE);
            Snackbar.make(loginLinearLayout,"Zadany pouzivatel neexistuje !",Snackbar.LENGTH_LONG)
                    .show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }else{
            if(pouzivatel.getRola().equals("U")){
                Intent intent = new Intent(LoginActivity.this, PrehladUcitelActivity.class);
                intent.putExtra("Pouzivatel", pouzivatel);
                startActivity(intent);
                finish();
            }
            if(pouzivatel.getRola().equals("S")){
                Intent intent = new Intent(LoginActivity.this, Prehlad_Student.class);
                intent.putExtra("Pouzivatel", pouzivatel);
                startActivity(intent);
                finish();
            }
            if(pouzivatel.getRola().equals("A")){
                Intent intent = new Intent(LoginActivity.this, PrehladAdminActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


}



