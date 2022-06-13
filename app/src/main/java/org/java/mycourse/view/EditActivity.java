package org.java.mycourse.view;

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

import org.java.mycourse.R;

public class EditActivity extends AppCompatActivity {
    TextInputLayout idInputLayout,nameInputLayout,courseInputLayout,feeInputLayout;
    Button edit,delete,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        idInputLayout=findViewById(R.id.idTextInputLayout);
        nameInputLayout=findViewById(R.id.nameTextInputLayout);
        courseInputLayout=findViewById(R.id.courseTextInputLayout);
        feeInputLayout=findViewById(R.id.feeTextInputLayout);
        edit=findViewById(R.id.editButton);
        delete=findViewById(R.id.deleteButton);
        back=findViewById(R.id.backButton);


        Intent intent=getIntent();
        String id=intent.getStringExtra("id").toString();
        String name=intent.getStringExtra("name").toString();
        String course=intent.getStringExtra("course").toString();
        String fee=intent.getStringExtra("fee").toString();


        idInputLayout.getEditText().setText(id);
        nameInputLayout.getEditText().setText(name);
        courseInputLayout.getEditText().setText(course);
        feeInputLayout.getEditText().setText(fee);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ViewActivity.class);
                startActivity(intent);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {delete();
            }
        });

    }
    public void delete(){
        try {

            String id=idInputLayout.getEditText().getText().toString();
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("coursedb", Context.MODE_PRIVATE,null);

            String insertQuery="delete from  courses where id=?";
            SQLiteStatement statement=sqLiteDatabase.compileStatement(insertQuery);

            statement.bindString(1,id);
            statement.execute();
            Toast.makeText(this,id+" Record Deleted",Toast.LENGTH_SHORT).show();
            nameInputLayout.getEditText().setText("");
            courseInputLayout.getEditText().setText("");
            feeInputLayout.getEditText().setText("");
            nameInputLayout.getEditText().requestFocus();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Record failed",Toast.LENGTH_SHORT).show();

        }
    }
    public void edit(){

        try {

            String id=idInputLayout.getEditText().getText().toString();
            String name=nameInputLayout.getEditText().getText().toString();
            String course=courseInputLayout.getEditText().getText().toString();
            String fee=feeInputLayout.getEditText().getText().toString();
            SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("coursedb", Context.MODE_PRIVATE,null);

            String insertQuery="update  courses set name=?,course=?,fee=? where id=?";
            SQLiteStatement statement=sqLiteDatabase.compileStatement(insertQuery);
            statement.bindString(1,name);
            statement.bindString(2,course);
            statement.bindString(3,fee);
            statement.bindString(4,id);
            statement.execute();
            Toast.makeText(this,"Record Updated",Toast.LENGTH_SHORT).show();
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