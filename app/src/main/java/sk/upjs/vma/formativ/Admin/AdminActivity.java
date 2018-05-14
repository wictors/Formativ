package sk.upjs.vma.formativ.Admin;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sk.upjs.vma.formativ.R;
import sk.upjs.vma.formativ.entity.Seria;

public class AdminActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    String[] pouzivatelia = {"vseno", "rziadne", "ttest"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listView = findViewById(R.id.zoznam_pouzivatelov_list);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pouzivatelia);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pouzivatel = (String) adapterView.getAdapter().getItem(i);
                klikPouzivatel(pouzivatel);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_admin_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registracia();
            }
        });

    }

    private void registracia() {
        Intent intent = new Intent(this, RegistraciaActivity.class);
        startActivity(intent);
        finish();
    }

    private void klikPouzivatel(String pouzivatel) {
        Toast.makeText(this, pouzivatel, Toast.LENGTH_LONG).show();

    }
}
