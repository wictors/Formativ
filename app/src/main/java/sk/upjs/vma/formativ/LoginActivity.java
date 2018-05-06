package sk.upjs.vma.formativ;

import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText menoEditText;
    private EditText hesloEditText;
    private View mProgressView;
    private LinearLayout loginLinearLayout;
    private static final int REQUEST_CODE = 8;
    private boolean pristup = false;
    private String prihlasovacie_meno;
    private String heslo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        loginLinearLayout = findViewById(R.id.login_linear_layout);
        mProgressView = findViewById(R.id.login_progress);
        menoEditText =  findViewById(R.id.text_Meno);
        hesloEditText = findViewById(R.id.text_Heslo);

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
            hesloEditText.setError(getString(R.string.error_invalid_password));
            focusView = hesloEditText;
            cancel = true;
        }

        // Kontrola vyplnenia prihlasovacieho mena
        if (TextUtils.isEmpty(prihlasovacie_meno)) {
            this.menoEditText.setError(getString(R.string.error_field_required));
            focusView = this.menoEditText;
            cancel = true;
        }

        if (cancel) {
            // Zameranie na posledne chybne policko
            Toast.makeText(this,"Udaje su nespravne",Toast.LENGTH_LONG).show();
            focusView.requestFocus();
        } else {
            // Spustenie progressu
            showProgress();
        }
    }

    private boolean isPasswordValid(String password) { return password.length() > 4; }


    private void showProgress() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mProgressView.setVisibility(View.VISIBLE);
        loginLinearLayout.setVisibility(View.GONE);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            PrihlasenieAsyncTask prihlasenieAsyncTask = new PrihlasenieAsyncTask(this);
            prihlasenieAsyncTask.execute(prihlasovacie_meno,heslo);
        }else{
            mProgressView.setVisibility(View.GONE);
            loginLinearLayout.setVisibility(View.VISIBLE);
            Snackbar.make(loginLinearLayout,"Žiadne internetové pripojenie",Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void prihlasPouzivatela(Pouzivatel pouzivatel){
        if(pouzivatel == null){
            mProgressView.setVisibility(View.GONE);
            loginLinearLayout.setVisibility(View.VISIBLE);
            Snackbar.make(loginLinearLayout,"Ziaden pouzivatel",Snackbar.LENGTH_LONG)
                    .show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }else{
            mProgressView.setVisibility(View.GONE);
            loginLinearLayout.setVisibility(View.VISIBLE);
            Snackbar.make(loginLinearLayout,pouzivatel.getMeno(),Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}



