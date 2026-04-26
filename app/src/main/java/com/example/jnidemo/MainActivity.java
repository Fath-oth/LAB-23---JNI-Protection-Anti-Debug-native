package com.example.jnidemo;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public native String helloFromJNI();
    public native int factorial(int n);
    public native String reverseString(String s);
    public native int sumArray(int[] values);
    public native int getSecurityStatus();

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvStatus = findViewById(R.id.tvStatus);
        TextView tvHello = findViewById(R.id.tvHello);
        TextView tvFact = findViewById(R.id.tvFact);
        TextView tvReverse = findViewById(R.id.tvReverse);
        TextView tvArray = findViewById(R.id.tvArray);

        int status = getSecurityStatus();

        if (status != 0) {
            String reason = "";
            if (status == 1) reason = " (Debug/Ptrace)";
            else if (status == 2) reason = " (Maps suspectes)";
            else if (status == 3) reason = " (Multiple)";

            tvStatus.setText("Etat securite : ALERTE" + reason);
            tvStatus.setTextColor(Color.RED);

            tvHello.setText("Fonction native bloquee");
            tvFact.setText("Calcul bloque");
            tvReverse.setText("Bloqué");
            tvArray.setText("Bloqué");
        } else {
            tvStatus.setText("Etat securite : OK");
            tvStatus.setTextColor(Color.parseColor("#2E7D32"));

            tvHello.setText(helloFromJNI());

            int result = factorial(10);
            if (result >= 0) {
                tvFact.setText("Factoriel de 10 = " + result);
            } else {
                tvFact.setText("Erreur factoriel");
            }

            String reversed = reverseString("JNI is powerful!");
            tvReverse.setText("Texte inverse : " + reversed);

            int[] numbers = {10, 20, 30, 40, 50};
            int sum = sumArray(numbers);
            tvArray.setText("Somme du tableau = " + sum);
        }
    }
}