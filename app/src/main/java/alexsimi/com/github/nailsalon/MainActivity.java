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
    protected void onCreate(Bundle savedInstanceState)
    {
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
        db.loadDb(sourceFile);

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

        // log
        Log.d("NailSalon", "onCreate() was called");

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // save DB to disk
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.saveDb(sourceFile);

        // log
        Log.d("NailSalon", "onDestroy() was called");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        lv.setAdapter(appointmentAdapter);

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

    }

    public void onResetButtonClicked()
    {
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.getAppointments().clear();
        lv.setAdapter(appointmentAdapter);
    }



}