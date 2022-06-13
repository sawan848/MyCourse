package org.java.mycourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.java.mycourse.view.ViewActivity;

public class MainActivity extends AppCompatActivity {
    TextInputLayout nameInputLayout,courseInputLayout,feeInputLayout;
    Button submit,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInputLayout=findViewById(R.id.nameTextInputLayout);
        courseInputLayout=findViewById(R.id.courseTextInputLayout);
        feeInputLayout=findViewById(R.id.feeTextInputLayout);
        submit=findViewById(R.id.submitButton);
        view=findViewById(R.id.viewButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ViewActivity.class);
                startActivity(intent);
            }
        });

    }
    public void insert(){
        try {
           String name=nameInputLayout.getEditText().getText().toString();
           String course=courseInputLayout.getEditText().getText().toString();
           String fee=feeInputLayout.getEditText().getText().toString();
           SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("coursedb", Context.MODE_PRIVATE,null);
           String createQuery="create table if not exists courses" +
                   " (id integer primary key autoincrement," +
                   "name varchar2(50),course varchar2(50),fee varchar2(20))";
           sqLiteDatabase.execSQL(createQuery);
           String insertQuery="insert into courses(name,course,fee) values (?,?,?)";
           SQLiteStatement statement=sqLiteDatabase.compileStatement(insertQuery);
           statement.bindString(1,name);
           statement.bindString(2,course);
           statement.bindString(3,fee);
           statement.execute();
            Toast.makeText(this,"Record Inserted",Toast.LENGTH_SHORT).show();

            nameInputLayout.getEditText().setText("");
            courseInputLayout.getEditText().setText("");
            feeInputLayout.getEditText().setText("");
            nameInputLayout.getEditText().requestFocus();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Record failed",Toast.LENGTH_SHORT).show();

        }
    }
}