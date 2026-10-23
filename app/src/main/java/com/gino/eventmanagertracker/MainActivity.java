package com.gino.eventmanagertracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    // ===== declare all components
    RecyclerView rvMain;
    RecyclerView.LayoutManager rvLM;
    RecyclerView.Adapter rvADA;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ===== initial
        // ===== connect all components
        rvMain = findViewById(R.id.rvMain);
        rvLM = new LinearLayoutManager(this);
        tvEmpty = findViewById(R.id.tvEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Event Manager Tracker");
        }

        // set listener
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        EventDBHelper db = new EventDBHelper(this, "EventDB", null, 1);
        rvADA = new ItemAdapter(db);

        // ===== if Event list is empty, show empty message
        if (db.getList().isEmpty()) {
            rvMain.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else {
            rvMain.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);

            rvMain.setLayoutManager(rvLM);
            rvMain.setAdapter(rvADA);
        }
    }
}