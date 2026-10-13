package com.gino.eventmanagertracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
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

    RecyclerView rvMain;
    RecyclerView.LayoutManager rvLM;
    RecyclerView.Adapter rvADA;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Event Manager Tracker");
        }

        EventDBHelper db = new EventDBHelper(this, "EventDB", null, 1);
        rvMain = findViewById(R.id.rvMain);
        rvLM = new LinearLayoutManager(this);
        rvADA = new ItemAdapter(db);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        rvMain.setLayoutManager(rvLM);
        rvMain.setAdapter(rvADA);
    }
}