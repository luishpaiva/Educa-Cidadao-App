package com.projects.educacidadaoapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.projects.educacidadaoapp.R;

public class SobreProjetoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_o_projeto);
    }

    public void voltarButtonOnClick(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
