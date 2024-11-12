package com.example.filkomfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RangkumanHistory extends AppCompatActivity implements View.OnClickListener {

    private ImageButton inikeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangkuman_history);

        this.inikeluar = this.findViewById(R.id.imageButton3);
        this.inikeluar.setOnClickListener(RangkumanHistory.this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, CekHistory.class);
        startActivity(i);
    }
}