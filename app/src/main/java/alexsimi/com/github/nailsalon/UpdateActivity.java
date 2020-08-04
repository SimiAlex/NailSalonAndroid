package alexsimi.com.github.nailsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import alexsimi.com.github.nailsalon.utils.Validation;

public class UpdateActivity extends AppCompatActivity
{
    //fields layout
    private TextInputLayout id_til;
    private TextInputLayout name_til;
    private TextInputLayout date_til;
    private TextInputLayout time_til;
    private TextInputLayout procedure_til;
    private TextInputLayout price_til;
    private Button update_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_appointment);
        initializeLayout();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateButtonClicked();
            }
        });
    }

    // methods - buttons behaviour
    public void onUpdateButtonClicked()
    {
        if(validateID() & validatePrice() & validateStringField(name_til) &
                validateStringField(procedure_til) & validateDate() & validateTime())
        {
            int clientId = Integer.parseInt(id_til.getEditText().getText().toString());
            String name = Validation.removeCommaFromTextFields(name_til.getEditText().getText().toString());
            String procedure = Validation.removeCommaFromTextFields(procedure_til.getEditText().getText().toString());
            double price = Double.parseDouble(price_til.getEditText().getText().toString());

            Intent updateAppointmentIntent = new Intent(this, MainActivity.class);
            updateAppointmentIntent.putExtra("id", clientId);
            updateAppointmentIntent.putExtra("name", name);
            updateAppointmentIntent.putExtra("date", date_til.getEditText().getText().toString());
            updateAppointmentIntent.putExtra("time", time_til.getEditText().getText().toString());
            updateAppointmentIntent.putExtra("procedure", procedure);
            updateAppointmentIntent.putExtra("price", price);

            // set result to pass to MainActivity
            setResult(RESULT_OK, updateAppointmentIntent);

            // finish current activity without starting a new MainActivity
            finish();
        }
    }

    // methods - validation
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

    private boolean validatePrice()
    {
        boolean isValid;
        isValid = Validation.validatePriceInput(price_til.getEditText().getText().toString());
        if(isValid)
        {
            price_til.setError(null);
            return true;
        }
        else
        {
            price_til.setError("Enter a number");
            return false;
        }
    }

    private boolean validateStringField(TextInputLayout til)
    {
        boolean isValid = Validation.validateNameField(til.getEditText().getText().toString());
        if(isValid)
        {
            til.setError(null);
            return true;
        }
        else
        {
            til.setError("Invalid input");
            return false;
        }
    }

    public boolean validateDate()
    {
        boolean isValid = Validation.validateDateField(date_til.getEditText().getText().toString());
        if(isValid)
        {
            date_til.setError(null);
            return true;
        }
        else
        {
            date_til.setError("Invalid date");
            return false;
        }
    }

    public boolean validateTime()
    {
        boolean isValid = Validation.validateTimeField(time_til.getEditText().getText().toString());
        if(isValid)
        {
            time_til.setError(null);
            return true;
        }
        else
        {
            time_til.setError("Invalid time");
            return false;
        }
    }

    // methods - other
    public void initializeLayout()
    {
        id_til = findViewById(R.id.client_id_til);
        name_til = findViewById(R.id.client_name_til);
        date_til = findViewById(R.id.appointment_date_til);
        time_til = findViewById(R.id.appointment_time_til);
        procedure_til = findViewById(R.id.procedure_til);
        price_til = findViewById(R.id.price_til);
        update_button = findViewById(R.id.update_button_UA);
    }
}