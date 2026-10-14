package com.gino.eventmanagertracker;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DetailActivity extends AppCompatActivity {

    EventDBHelper db = new EventDBHelper(this, "EventDB", null, 1);

    EventModel evtRecord = new EventModel();
    TextView tvID;
    EditText etEventName, etEventDate, etEventLocation, etEventOrganiser;
    RadioGroup rgEventType;
    RadioButton rbEventTypeConference, rbEventTypeWorkshop, rbEventTypeSeminar, rbEventTypeSocial, rbEventTypeOther;
    Spinner spEventStatus;

    String[] statusOptions = { "Upcoming", "Completed", "Cancelled" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ===== initial
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Event Detail - Add");
        }

        tvID = findViewById(R.id.tvID);
        etEventName = findViewById(R.id.etEventName);
        etEventDate = findViewById(R.id.etEventDate);
        etEventLocation = findViewById(R.id.etEventLocation);
        etEventOrganiser = findViewById(R.id.etEventOrganiser);
        rgEventType = findViewById(R.id.rgEventType);
        rbEventTypeConference = findViewById(R.id.rbEventTypeConference);
        rbEventTypeWorkshop = findViewById(R.id.rbEventTypeWorkshop);
        rbEventTypeSeminar = findViewById(R.id.rbEventTypeSeminar);
        rbEventTypeSocial = findViewById(R.id.rbEventTypeSocial);
        rbEventTypeOther = findViewById(R.id.rbEventTypeOther);
        spEventStatus = findViewById(R.id.spEventStatus);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<String> ada = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEventStatus.setAdapter(ada);

        // ===== set listener
        btnClear.setOnClickListener(v -> {
            inputClear();
        });

        btnSave.setOnClickListener(v -> {
            if (inputValidation()) {
                saveData();
            }
        });

        // ===== load data
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadData() {
        Bundle era = getIntent().getExtras();
        if (era != null) {
            int id = era.getInt("evtID");
            evtRecord = db.getSingle(id);
            tvID.setText("ID: " + id);
            etEventName.setText(evtRecord.eventName);
            etEventDate.setText(evtRecord.eventDate);
            etEventLocation.setText(evtRecord.eventLocation);
            etEventOrganiser.setText(evtRecord.eventOrganiser);
            if (evtRecord.eventType.equals("Conference")) {
                rbEventTypeConference.setChecked(true);
            }
            else if (evtRecord.eventType.equals("Workshop")) {
                rbEventTypeWorkshop.setChecked(true);
            }
            else if (evtRecord.eventType.equals("Seminar")) {
                rbEventTypeSeminar.setChecked(true);
            }
            else if (evtRecord.eventType.equals("Social")) {
                rbEventTypeSocial.setChecked(true);
            }
            else {
                rbEventTypeOther.setChecked(true);
            }
            if (evtRecord.eventStatus.equals("Upcoming")) {
                spEventStatus.setSelection(0);
            }
            else if (evtRecord.eventStatus.equals("Completed")) {
                spEventStatus.setSelection(1);
            }
            else {
                spEventStatus.setSelection(2);
            }
        }
    }

    private void inputClear() {
        etEventName.setText("");
        etEventDate.setText("");
        etEventLocation.setText("");
        etEventOrganiser.setText("");
        rgEventType.clearCheck();
        spEventStatus.setSelection(0);
    }

    private Boolean inputValidation() {
        if (etEventName.getText().toString().isEmpty()) {
            showErrorMessage("Please input event name");
            return false;
        }
        if (etEventDate.getText().toString().isEmpty()) {
            showErrorMessage("Please input event date");
            return false;
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setLenient(false);
            try {
                sdf.parse(etEventDate.getText().toString());
            } catch (ParseException e) {
                showErrorMessage("Invalid date format");
                return false;
            }
        }
        if (etEventLocation.getText().toString().isEmpty()) {
            showErrorMessage("Please input event location");
            return false;
        }
        if (etEventOrganiser.getText().toString().isEmpty()) {
            showErrorMessage("Please input event organiser");
            return false;
        }
        if (rgEventType.getCheckedRadioButtonId() == -1) {
            showErrorMessage("Please select event type");
            return false;
        }


        return true;
    }
    private void showErrorMessage(String msg) {
        Snackbar scb = Snackbar.make(findViewById(R.id.main), msg, Snackbar.LENGTH_LONG);
        scb.setAnchorView(findViewById(R.id.btnSave));
        scb.show();
    }

    private void saveData() {
        String eType = "";
        if (rbEventTypeConference.isChecked()) {
            eType = "Conference";
        }
        else if (rbEventTypeWorkshop.isChecked()) {
            eType = "Workshop";
        }
        else if (rbEventTypeSeminar.isChecked()) {
            eType = "Seminar";
        }
        else if (rbEventTypeSocial.isChecked()) {
            eType = "Social";
        }
        else {
            eType = "Other";
        }


        EventModel evt = new EventModel(etEventName.getText().toString(), etEventDate.getText().toString(), etEventLocation.getText().toString(),
                etEventOrganiser.getText().toString(), eType, spEventStatus.getSelectedItem().toString());
        if (evtRecord.id == 0) {
            db.addRecord(evt);
        }
        else {
            evt.id = evtRecord.id;
            db.updateRecord(evt);
        }

        finish();
    }
}