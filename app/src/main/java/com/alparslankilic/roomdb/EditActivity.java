package com.alparslankilic.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alparslankilic.roomdb.database.AppDatabase;
import com.alparslankilic.roomdb.database.AppExecutors;
import com.alparslankilic.roomdb.model.Person;

public class EditActivity extends AppCompatActivity {

    EditText name,email,pincode,phoneNumber,city;
    Button button;
    private AppDatabase mDb;
    Intent intent;
    int mPersonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mDb = AppDatabase.getInstance(getApplicationContext());
        initViews();
        intent = getIntent(); // bir önceki ekrandan gelen data var mı?
        if(intent != null && intent.hasExtra("update_task")) {
            button.setText("Güncelle");
            mPersonId = intent.getIntExtra("update_task",-1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDao().loadPersonById(mPersonId);
                    getPersonByUI(person);
                }
            });
        }
    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        pincode = findViewById(R.id.edit_pincode);
        city = findViewById(R.id.edit_city);
        phoneNumber = findViewById(R.id.edit_number);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonClick();
            }
        });
    }

    public void saveButtonClick() {
        final Person person = new Person(
                name.getText().toString(),
                email.getText().toString(),
                phoneNumber.getText().toString(),
                pincode.getText().toString(),
                city.getText().toString()
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(!intent.hasExtra("update_task")) {
                    mDb.personDao().insertPerson(person);
                }
                else
                {
                    person.setId(mPersonId);
                    mDb.personDao().updatePerson(person);
                }

                finish();
            }
        });
    }

    private void getPersonByUI(Person person) {
        if(person == null)
        {
            return;
        }
        name.setText(person.getName());
        email.setText(person.getEmail());
        phoneNumber.setText(person.getNumber());
        pincode.setText(person.getPincode());
        city.setText(person.getCity());
    }
}