package alexsimi.com.github.nailsalon.controller;

import android.util.Log;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import alexsimi.com.github.nailsalon.model.Appointment;

public class DatabaseHandler implements DatabaseCRUD<Appointment>
{
    // fields
    public static final String TAG = "NailSalon";
    public static final String FILE_NAME = "database.csv";
    private List<Appointment> appointments;

    // constructor
    public DatabaseHandler()
    {
        appointments = new ArrayList<Appointment>();
    }

    // methods
    @Override
    public void loadDb(File sourceFile)
    {
        try (BufferedReader reader  = new BufferedReader(new FileReader(sourceFile)))
        {
            String row;
            while((row = reader.readLine()) != null)
            {
                Appointment appointment = null;
                String[] oneRow = row.split(",");
                int id = Integer.parseInt(oneRow[0]);
                String name = oneRow[1];
                LocalDateTime date = LocalDateTime.parse(oneRow[2]);
                String procedure = oneRow[3];
                double price = Double.parseDouble(oneRow[4]);

                appointment = new Appointment(id, name, date, procedure, price);
                appointments.add(appointment);
            }
        }
        catch (IOException e)
        {
            Log.d(TAG, "loadDb: failure\n" + e.toString());
        }
    }

    @Override
    public void saveDb(File sourceFile)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile)))
        {
            String oneRow;
            for (Appointment app : appointments)
            {
                oneRow = app.getId() + "," + app.getName() + "," + app.getTime() + "," + app.getProcedure() + "," + app.getPrice();
                writer.write(oneRow);
                writer.newLine();
                writer.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.d(TAG, "saveDb: failure\n" + e.toString());
        }
    }

    public List<Appointment> getAppointments()
    {
        return appointments;
    }

    @Override
    public boolean addRecord(Appointment app)
    {
        return appointments.add(app);
    }

    @Override
    public Appointment readRecord(int index)
    {
        return appointments.get(index);
    }

    @Override
    public void updateRecord(int index, Appointment app)
    {
        appointments.set(index, app);
    }

    @Override
    public void deleteRecord(int index)
    {
        appointments.remove(index);
    }
}
