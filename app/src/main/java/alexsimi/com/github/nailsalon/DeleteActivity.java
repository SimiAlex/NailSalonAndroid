package alexsimi.com.github.nailsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import alexsimi.com.github.nailsalon.utils.Validation;

public class DeleteActivity extends AppCompatActivity {

    //fields
    private Button deleteButton;
    private TextInputLayout idTil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        initializeLayout();

        deleteButton.setOnClickListener((v -> onDeleteButtonClicked()));
    }

    public void onDeleteButtonClicked()
    {
        String idString = idTil.getEditText().getText().toString();
        if(Validation.validateIdInput(idString))
        {
            Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
            intent.putExtra("id", Integer.parseInt(idString));
            // set result to pass to MainActivity
            setResult(RESULT_OK, intent);

            // finish current activity without starting a new MainActivity
            finish();
        }
    }

    //methods
    public void initializeLayout()
    {
        deleteButton = findViewById(R.id.delete_button_DA);
        idTil = findViewById(R.id.client_id_til_delete_activity);
    }


}