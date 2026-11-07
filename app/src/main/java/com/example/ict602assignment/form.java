package com.example.ict602assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class form extends AppCompatActivity {
    Toolbar myToolBar;
    TextView tvWeight, tvClass, tvExcess, tvResult2;
    EditText etWeight, etExcess;
    RadioButton rbBusiness, rbEconomy;
    Button btnCalculate;
    private int ticket = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        com.google.android.material.appbar.MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        tvWeight = findViewById(R.id.tvWeight);
        tvClass = findViewById(R.id.tvClass);
        tvExcess = findViewById(R.id.tvExcess);
        tvResult2 = findViewById(R.id.tvResult2);
        etWeight = findViewById(R.id.etWeight);
        etExcess = findViewById(R.id.etExcess);
        rbBusiness = findViewById(R.id.rbBusiness);
        rbEconomy = findViewById(R.id.rbEconomy);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String weight = etWeight.getText().toString();
                        String excess = etExcess.getText().toString();
                        double dWeight = 0.0;
                        double dExcess = 0.0;
                        double baggage = 0.0;
                        double charge = 0.0;
                        double total = 0.0;

                        try {
                            dWeight = Double.parseDouble(weight);
                            dExcess = Double.parseDouble(excess);
                        } catch (NumberFormatException e) {
                            Toast.makeText(form.this, "Please enter valid values.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        baggage = Math.max(0.0, dWeight - ticket);
                        charge = baggage * dExcess;
                        total = charge + (charge * 0.08);

                        tvResult2.setText("The excess baggage weight: " + baggage + " kg.\nThe excess baggage charge: RM " + charge + "\nThe total amount payable: RM " + total);
                    }
                }
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply top inset to AppBarLayout so toolbar content sits below status bar,
            // but the status bar area background matches the toolbar!
            View appBarLayout = findViewById(R.id.appBarLayout);
            appBarLayout.setPadding(0, systemBars.top, 0, 0);

            // Apply left, right, bottom padding to root view for navigation bar & gesture insets
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);

            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_share){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "BaggageBuddy: Application to calculate excess baggage fees. - https://github.com/ashmeeza-17/BaggageBuddy");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }
        else if (item.getItemId() == R.id.item_about){
            Intent aboutIntent = new Intent(form.this, about.class);
            startActivity(aboutIntent);
        }

        return false;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        int type = view.getId();
        if(type == R.id.rbBusiness){
            if (checked){
                ticket = 40;
            }
        }
        else if (type == R.id.rbEconomy) {
            if (checked){
                ticket = 20;
            }

        }
    }
}
