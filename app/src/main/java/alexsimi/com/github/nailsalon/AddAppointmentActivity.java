package alexsimi.com.github.nailsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import alexsimi.com.github.nailsalon.utils.Validation;

public class AddAppointmentActivity extends AppCompatActivity
{
    //fields layout
    private TextInputLayout id_til;
    private TextInputLayout name_til;
    private TextInputLayout date_til;
    private TextInputLayout time_til;
    private TextInputLayout procedure_til;
    private TextInputLayout price_til;
    private Button add_client_button;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_appointment);
        initializeLayout();

        add_client_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });

    }

    public void initializeLayout()
    {
        id_til = findViewById(R.id.client_id_til);
        name_til = findViewById(R.id.client_name_til);
        date_til = findViewById(R.id.appointment_date_til);
        time_til = findViewById(R.id.appointment_time_til);
        procedure_til = findViewById(R.id.procedure_til);
        price_til = findViewById(R.id.price_til);
        add_client_button = findViewById(R.id.add_button_AAA);
    }

    //validation methods
    private boolean validateID()
    {
        boolean isValid;
        isValid = Validation.validateIdInput(id_til.getEditText().getText().toString());
        if(isValid)
        {
            id_til.setError(null);
            return true;
        }
        else
        {
            id_til.setError("Enter a number");
            return false;
        }
    }

    public void onAddButtonClicked()
    {
        validateID();
    }
}