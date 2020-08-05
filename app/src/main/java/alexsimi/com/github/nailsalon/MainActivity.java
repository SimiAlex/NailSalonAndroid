package alexsimi.com.github.nailsalon;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import alexsimi.com.github.nailsalon.controller.DatabaseHandler;
import alexsimi.com.github.nailsalon.model.Appointment;
import alexsimi.com.github.nailsalon.utils.Validation;
import alexsimi.com.github.nailsalon.view.AppointmentAdapter;

public class MainActivity extends AppCompatActivity
{
    //fields - layout
    private Button resetButton;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private ListView lv;

    // fields - other
    private AppointmentAdapter appointmentAdapter;
    private File sourceFile;
    private DatabaseHandler dbh;

    // methods - activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceFile = new File(getApplicationContext().getFilesDir(), DatabaseHandler.FILE_NAME);
        initializeLayout();

        // create the .csv file if it doesn't exist (for first time users)
        try
        {
            sourceFile.createNewFile();
        }
        catch (IOException e)
        {
            Log.e("NailSalon", "onStart() " + e.toString());
        }

        // load DB from disk
        dbh = new DatabaseHandler();
        dbh.loadDb(sourceFile);

        // setup adapter
        appointmentAdapter = new AppointmentAdapter(MainActivity.this, dbh);
        lv.setAdapter(appointmentAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onResetButtonClicked();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateButtonClicked();
            }
        });

        deleteButton.setOnClickListener(v -> onDeleteButtonClicked());
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        // save DB to disk
        dbh.saveDb(sourceFile);

        Log.d("NailSalon", "onStop() was called\tDB size = " + dbh.getAppointments().size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("id", 0);
            int index = Validation.getIndexFromID(dbh.getAppointments(), id);

            if(index == -1) {
                String name = data.getStringExtra("name");
                String date = data.getStringExtra("date");
                String time = data.getStringExtra("time");
                String procedure = data.getStringExtra("procedure");
                double price = data.getDoubleExtra("price", 0);

                LocalDate dateLd = LocalDate.parse(date);
                LocalTime timeLt = LocalTime.parse(time);
                LocalDateTime dateTime = LocalDateTime.of(dateLd, timeLt);

                Appointment appointment = new Appointment(id, name, dateTime, procedure, price);
                dbh.addRecord(appointment);

                // display a toast message
                Toast.makeText(this, "Appointment added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "ID already in database", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 2 && resultCode == RESULT_OK)
        {

            int id = data.getIntExtra("id", 0);
            int index = Validation.getIndexFromID(dbh.getAppointments(), id);

            if(index != -1) {

                String name = data.getStringExtra("name");
                String date = data.getStringExtra("date");
                String time = data.getStringExtra("time");
                String procedure = data.getStringExtra("procedure");
                double price = data.getDoubleExtra("price", 0);

                LocalDate dateLd = LocalDate.parse(date);
                LocalTime timeLt = LocalTime.parse(time);
                LocalDateTime dateTime = LocalDateTime.of(dateLd, timeLt);

                Appointment appointment = new Appointment(id, name, dateTime, procedure, price);
                dbh.updateRecord(Validation.getIndexFromID(dbh.getAppointments(), id), appointment);

                // display a toast message
                Toast.makeText(this, "Appointment updated", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == 3 && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("id", 0);

            int index = Validation.getIndexFromID(dbh.getAppointments(), id);

            if(index != -1)
            {
                dbh.deleteRecord(index);
                Toast.makeText(this, "Appointment deleted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            }
        }

        // sort database after each operation
        Collections.sort(dbh.getAppointments(), (a1, a2)-> a1.getTime().compareTo(a2.getTime()));
        appointmentAdapter.notifyDataSetChanged();
    }

    // methods - handle button clicks
    public void onAddButtonClicked()
    {
        Intent addIntent = new Intent(MainActivity.this, AddAppointmentActivity.class);

        int requestCode = 1;
        startActivityForResult(addIntent, requestCode);
    }

    public void onResetButtonClicked()
    {
        dbh.getAppointments().clear();
        appointmentAdapter.notifyDataSetChanged();
    }

    public void onUpdateButtonClicked()
    {
        Intent updateIntent = new Intent(MainActivity.this, UpdateActivity.class);
        int requestCode = 2;
        startActivityForResult(updateIntent, requestCode);
    }

    public void onDeleteButtonClicked()
    {
        Intent deleteIntent = new Intent(MainActivity.this, DeleteActivity.class);
        int requestCode = 3;
        startActivityForResult(deleteIntent, requestCode);
    }

//    public void onSwitchStateChangedChecked()
//    {
//        List<Appointment> activeAppointments = dbh.getAppointments()
//                .stream()
//                .filter(appointment -> appointment.getTime().isAfter(LocalDateTime.now()))
//                .collect(Collectors.toList());
//    }


    // methods - other
    public void initializeLayout()
    {
        resetButton = findViewById(R.id.resetButton);
        addButton = findViewById(R.id.add_button_AAA);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        lv = findViewById(R.id.lv_appointments);
    }

}
