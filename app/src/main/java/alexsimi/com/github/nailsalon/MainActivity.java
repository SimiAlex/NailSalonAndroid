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
    private final String DATABASE_NAME = "database.csv";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceFile = new File(getApplicationContext().getFilesDir(), DATABASE_NAME);
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
        DatabaseHandler db = DatabaseHandler.getInstance();
        Log.d("NailSalon", "onCreate() was called\tDB size before loadDb() = " + db.getAppointments().size());
        db.loadDb(sourceFile);
        Log.d("NailSalon", "onCreate() was called\tDB size after loadDb() = " + db.getAppointments().size());

        // setup adapter
        appointmentAdapter = new AppointmentAdapter(MainActivity.this, db);
        lv.setAdapter(appointmentAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            db.addRecord(appointment);

            // update ListView
            lv.setAdapter(appointmentAdapter);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // save DB to disk
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.saveDb(sourceFile);

        // log
        Log.d("NailSalon", "onDestroy() was called\tDB size = " + db.getAppointments().size());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        lv.setAdapter(appointmentAdapter);

        // log
        Log.d("NailSalon", "onResume() was called\tDB size = " + DatabaseHandler.getInstance().getAppointments().size());
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
    }

    public void onResetButtonClicked()
    {
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.getAppointments().clear();
        lv.setAdapter(appointmentAdapter);
    }



}