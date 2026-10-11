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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ListView lvEvent;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Event Manager Tracker");
        }

        lvEvent = findViewById(R.id.lvEvent);
        tvEmpty = findViewById(R.id.tvEmpty);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, DetailActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();

//        String[] output = new String[GlobalData.evtList.size()];
//        for (int i = 0; i < GlobalData.evtList.size(); i++) {
//            output[i] = GlobalData.evtList.get(i).name;
//        }
//
//        ArrayAdapter ada = new ArrayAdapter(this, android.R.layout.simple_list_item_1, output);
//        lvEvent.setAdapter(ada);
//        lvEvent.setEmptyView(tvEmpty);
    }
}