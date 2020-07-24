package alexsimi.com.github.nailsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
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
    //fields
    private Button addButton;
    private EditText id_et;
    private EditText name_et;
    private EditText date_et;
    private EditText time_et;
    private EditText procedure_et;
    private EditText price_et;

    private ListView lv;
    private AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLayout();

        //setup adapter
        appointmentAdapter = new AppointmentAdapter(MainActivity.this, DatabaseHandler.getInstance());
        lv.setAdapter(appointmentAdapter);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onAddButtonClicked();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //load DB
        DatabaseHandler db = DatabaseHandler.getInstance();

        File databaseCSV = new File(DatabaseHandler.SOURCE);

        try
        {
            databaseCSV.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        db.loadDb();

    }

    @Override
    protected void onStop()
    {
        super.onStop();

        // save DB on disk
        DatabaseHandler db  = DatabaseHandler.getInstance();
        db.saveDb();
    }

    public void initializeLayout()
    {
        addButton = findViewById(R.id.AddButton);
        id_et = findViewById(R.id.ClientID);
        name_et = findViewById(R.id.ClientName);
        date_et = findViewById(R.id.AppointmentDate);
        time_et = findViewById(R.id.AppointmentTime);
        procedure_et = findViewById(R.id.Procedure);
        price_et = findViewById(R.id.Price);
        lv = findViewById(R.id.lv_appointments);
    }

    public void onAddButtonClicked()
    {
        int clientId = Integer.parseInt(id_et.getText().toString());
        String name = name_et.getText().toString();
        LocalDate date = LocalDate.parse(date_et.getText().toString());
        LocalTime time = LocalTime.parse(time_et.getText().toString());
        LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);
        Log.d("NailSalon", "onAddButtonClicked: LocalDateTime = " + appointmentDateTime.toString());
        String procedure = procedure_et.getText().toString();
        double price = Integer.parseInt(price_et.getText().toString());

        Appointment app = new Appointment(clientId, name, appointmentDateTime, procedure, price);
        DatabaseHandler db = DatabaseHandler.getInstance();
        boolean wasAdded = db.addRecord(app);
        if(wasAdded)
        {
            Toast.makeText(this,"Record was added",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Invalid record",Toast.LENGTH_SHORT).show();
        }

        // update adapter
        lv.setAdapter(appointmentAdapter);
    }



}