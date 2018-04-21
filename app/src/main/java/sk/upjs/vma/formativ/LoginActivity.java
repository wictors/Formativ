package sk.upjs.vma.formativ;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText meno;
    private EditText heslo;
    private View mProgressView;
    private LinearLayout loginLinearLayout;
    private static final int REQUEST_CODE = 8;
    private boolean pristup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginLinearLayout = findViewById(R.id.login_linear_layout);
        mProgressView = findViewById(R.id.login_progress);
        meno =  findViewById(R.id.text_Meno);
        heslo = findViewById(R.id.text_Heslo);

        Button prihlasitButton = findViewById(R.id.prihlasit_Button);
        prihlasitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    attemptLogin();
            }
        });
    }

    private boolean povolenie(){
        if(!pristup) {
            Snackbar.make(loginLinearLayout, "Aplikacia bude vyuzivat informacie o sieti.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pristup = true;
                        }
                    }).show();
        }
        return pristup;
    }

    private void attemptLogin() {
        // Reset chyb.
        meno.setError(null);
        heslo.setError(null);

        String email = meno.getText().toString();
        String password = heslo.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Kontrola vyplnenia a dlzky hesla
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            heslo.setError(getString(R.string.error_invalid_password));
            focusView = heslo;
            cancel = true;
        }

        // Kontrola vyplnenia prihlasovacieho mena
        if (TextUtils.isEmpty(email)) {
            meno.setError(getString(R.string.error_field_required));
            focusView = meno;
            cancel = true;
        }

        if (cancel) {
            // Zameranie na posledne chybne policko
            focusView.requestFocus();
        } else {
            // Spustenie progressu
            showProgress(true);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        loginLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){


        }else{
            mProgressView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginLinearLayout.setVisibility(show ? View.VISIBLE : View.GONE);
            Snackbar.make(loginLinearLayout,"Žiadne internetové pripojenie",Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}



