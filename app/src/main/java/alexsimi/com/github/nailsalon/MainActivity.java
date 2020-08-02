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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra("id", 0);
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

            // update ListView
            appointmentAdapter.notifyDataSetChanged();

            // display a toast message
            Toast.makeText(this, "Appointment added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // display a toast message
            Toast.makeText(this, "No appointment added", Toast.LENGTH_SHORT).show();
        }
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