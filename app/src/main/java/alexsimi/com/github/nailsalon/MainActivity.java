package alexsimi.com.github.nailsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import alexsimi.com.github.nailsalon.controller.DatabaseHandler;
import alexsimi.com.github.nailsalon.model.Appointment;
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
        Log.d("NailSalon", "onCreate() was called\tDB size before loadDb() = " + dbh.getAppointments().size());
        dbh.loadDb(sourceFile);
        Log.d("NailSalon", "onCreate() was called\tDB size after loadDb() = " + dbh.getAppointments().size());

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

        // retrieve data from other activities
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            int id = bundle.getInt("id", 0);
            String name = bundle.getString("name");
            String date = bundle.getString("date");
            String time = bundle.getString("time");
            String procedure = bundle.getString("procedure");
            double price = bundle.getDouble("price", 0);

            LocalDate dateLd = LocalDate.parse(date);
            LocalTime timeLt = LocalTime.parse(time);
            LocalDateTime dateTime = LocalDateTime.of(dateLd, timeLt);

            Appointment appointment = new Appointment(id, name, dateTime, procedure, price);
            dbh.addRecord(appointment);

            // update ListView
            appointmentAdapter.notifyDataSetChanged();
        }
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
    protected void onDestroy()
    {
        super.onDestroy();

        // log
        Log.d("NailSalon", "onDestroy() was called\tDB size = " + dbh.getAppointments().size());
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // log
        Log.d("NailSalon", "onResume() was called");
    }

    public void initializeLayout()
    {
        resetButton = findViewById(R.id.resetButton);
        addButton = findViewById(R.id.add_button_AAA);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        lv = findViewById(R.id.lv_appointments);
    }

    public void onAddButtonClicked()
    {
        Intent intent = new Intent(this, AddAppointmentActivity.class);
        startActivity(intent);

        finish();
    }

    public void onResetButtonClicked()
    {
        dbh.getAppointments().clear();
        appointmentAdapter.notifyDataSetChanged();
    }



}