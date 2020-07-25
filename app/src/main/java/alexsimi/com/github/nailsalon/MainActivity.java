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

import alexsimi.com.github.nailsalon.controller.DatabaseHandler;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceFile = new File(getApplicationContext().getFilesDir(), DatabaseHandler.FILE_NAME);
        initializeLayout();

        // create the .csv file if it doesn't exist (for first time users)
        try {
            sourceFile.createNewFile();
        } catch (IOException e) {
            Log.e("NailSalon", "onStart() " + e.toString());

        }

        // load DB from disk
        DatabaseHandler db = DatabaseHandler.getInstance();
//        db.loadDb(sourceFile);

        // setup adapter
        appointmentAdapter = new AppointmentAdapter(MainActivity.this, DatabaseHandler.getInstance());
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

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // save DB to disk
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.saveDb(sourceFile);
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
//        int clientId = Integer.parseInt(id_et.getText().toString());
//        String name = Validation.removeCommaFromTextFields(name_et.getText().toString());
//        LocalDate date = LocalDate.parse(date_et.getText().toString());
//        LocalTime time = LocalTime.parse(time_et.getText().toString());
//        LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);
//        Log.d("NailSalon", "onAddButtonClicked: LocalDateTime = " + appointmentDateTime.toString());
//        String procedure = Validation.removeCommaFromTextFields(procedure_et.getText().toString());
//        double price = Integer.parseInt(price_et.getText().toString());
//
//        Appointment app = new Appointment(clientId, name, appointmentDateTime, procedure, price);
//        DatabaseHandler db = DatabaseHandler.getInstance();
//        boolean wasAdded = db.addRecord(app);
//        if(wasAdded)
//        {
//            Toast.makeText(this,"Record was added",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(this,"Invalid record",Toast.LENGTH_SHORT).show();
//        }
//
//        // update adapter
//        lv.setAdapter(appointmentAdapter);
    }

    public void onResetButtonClicked()
    {
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.getAppointments().clear();
        lv.setAdapter(appointmentAdapter);
    }



}