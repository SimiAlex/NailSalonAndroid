package alexsimi.com.github.nailsalon.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import alexsimi.com.github.nailsalon.R;
import alexsimi.com.github.nailsalon.controller.DatabaseHandler;
import alexsimi.com.github.nailsalon.model.Appointment;

public class AppointmentAdapter extends BaseAdapter
{
    //fields
    private Activity myActivity;
    private DatabaseHandler db;

    // fields = DateTimeFormatter
    private DateTimeFormatter dtf_date;
    private DateTimeFormatter dtf_time;

    //constructor
    public AppointmentAdapter(Activity myActivity, DatabaseHandler db)
    {
        this.myActivity = myActivity;
        this.db = db;
        dtf_date = DateTimeFormatter.ofPattern("dd.MM.yy");
        dtf_time = DateTimeFormatter.ofPattern("hh:mm");
    }

    //methods from parent
    @Override
    public int getCount()
    {
        return db.getAppointments().size();
    }

    @Override
    public Appointment getItem(int position)
    {
        return db.getAppointments().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View oneAppointmentView ;

        LayoutInflater inflater = LayoutInflater.from(myActivity);
        oneAppointmentView = inflater.inflate(R.layout.appointment_item,parent,false);

        TextView id_app = oneAppointmentView.findViewById(R.id.id_tv);
        TextView name_app = oneAppointmentView.findViewById(R.id.name_tv);
        TextView date_app = oneAppointmentView.findViewById(R.id.date_tv);
        TextView time_app = oneAppointmentView.findViewById(R.id.time_tv);
        TextView procedure_app = oneAppointmentView.findViewById(R.id.procedure_tv);
        TextView price_app = oneAppointmentView.findViewById(R.id.price_tv);

        Appointment app = getItem(position);

        id_app.setText(Integer.toString(app.getId()));
        name_app.setText(app.getName());
        Log.d("NailSalon", "getView: " + app.getTime().toString());

        // date and time setup
        date_app.setText(dtf_date.format(app.getTime()));
        time_app.setText(dtf_time.format(app.getTime()));

        procedure_app.setText(app.getProcedure());
        price_app.setText(String.format("%.0f", app.getPrice()));

        return oneAppointmentView;
    }
}
