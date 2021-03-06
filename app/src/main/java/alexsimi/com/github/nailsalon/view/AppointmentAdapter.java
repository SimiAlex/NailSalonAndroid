package alexsimi.com.github.nailsalon.view;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import alexsimi.com.github.nailsalon.R;
import alexsimi.com.github.nailsalon.controller.DatabaseHandler;
import alexsimi.com.github.nailsalon.model.Appointment;

public class AppointmentAdapter extends BaseAdapter
{
    //fields
    private Activity myActivity;
    private List<Appointment> db;

    // fields = DateTimeFormatter
    private DateTimeFormatter dtf_date;
    private DateTimeFormatter dtf_time;

    //constructor
    public AppointmentAdapter(Activity myActivity, List<Appointment> db)
    {
        this.myActivity = myActivity;
        this.db = db;
        dtf_date = DateTimeFormatter.ofPattern("dd.MM.yy");
        dtf_time = DateTimeFormatter.ofPattern("HH:mm");
    }

    // methods - setList()
    public void setList(List<Appointment> appList)
    {
        db = appList;
    }

    //methods from parent
    @Override
    public int getCount()
    {
        return db.size();
    }

    @Override
    public Appointment getItem(int position)
    {
        return db.get(position);
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

        LinearLayout ll = oneAppointmentView.findViewById(R.id.appItem_LinearLayout_AI);
        TextView id_app = oneAppointmentView.findViewById(R.id.id_tv);
        TextView name_app = oneAppointmentView.findViewById(R.id.name_tv);
        TextView date_app = oneAppointmentView.findViewById(R.id.date_tv);
        TextView time_app = oneAppointmentView.findViewById(R.id.time_tv);
        TextView procedure_app = oneAppointmentView.findViewById(R.id.procedure_tv);
        TextView price_app = oneAppointmentView.findViewById(R.id.price_tv);

        Appointment app = getItem(position);

        id_app.setText(Integer.toString(app.getId()));
        name_app.setText(app.getName());

        // date and time setup
        date_app.setText(dtf_date.format(app.getTime()));
        time_app.setText(dtf_time.format(app.getTime()));
        if (app.getTime().isBefore(LocalDateTime.now()))
        {
            ll.setBackgroundColor(Color.parseColor("#CAD3C8"));
        }

        procedure_app.setText(app.getProcedure());
        price_app.setText(String.format("%.0f", app.getPrice()));

        return oneAppointmentView;
    }
}
