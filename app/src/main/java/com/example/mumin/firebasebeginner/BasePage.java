package com.example.mumin.firebasebeginner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BasePage extends AppCompatActivity {

    Button btnAdd;
    Button btnShow;
    EditText etName;
    TextView tvGoster;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_page);

        database = FirebaseDatabase.getInstance();
        btnAdd =(Button) findViewById(R.id.btnTik);
        etName=(EditText) findViewById(R.id.etAd);
        tvGoster=(TextView)findViewById(R.id.tvGoster);
        btnShow=(Button)findViewById(R.id.btnGoster);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference dbRef = database.getReference("ekleme");
                String key=dbRef.push().getKey();
                DatabaseReference dbRefYeni = database.getReference("ekleme/"+key);
                dbRefYeni.setValue(etName.getText().toString().trim());
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference reader = database.getReference("ekleme");
                reader.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tvGoster.setText("");
                        Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                        for (DataSnapshot key :keys)
                        {
                            tvGoster.append(key.getValue().toString()+"\n");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
