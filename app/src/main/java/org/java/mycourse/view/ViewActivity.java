package org.java.mycourse.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.java.mycourse.R;
import org.java.mycourse.model.Student;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String>courseList=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        listView=findViewById(R.id.list1);
        SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("coursedb", Context.MODE_PRIVATE,null);
        final Cursor cursor=sqLiteDatabase.rawQuery("select * from courses",null);
        int id=cursor.getColumnIndex("id");
        int name=cursor.getColumnIndex("name");
        int course=cursor.getColumnIndex("course");
        int fee=cursor.getColumnIndex("fee");

        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,courseList);
        listView.setAdapter(arrayAdapter);

        final ArrayList<Student>studentsList=new ArrayList<>();

        if (cursor.moveToNext()){
            do {
                Student student=new Student();
                student.setId(cursor.getString(id));
                student.setName(cursor.getString(name));
                student.setCourse(cursor.getString(course));
                student.setFee(cursor.getString(fee));

                studentsList.add(student);
                courseList.add(cursor.getString(id)+"\t"+
                        cursor.getString(name)+"\t"+
                        cursor.getString(course)+"\t"+
                        cursor.getString(fee)+"\t"
                );

            }while (cursor.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            listView.invalidateViews();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                String s = courseList.get(position).toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                Student student=studentsList.get(position);
                Intent intent=new Intent(getApplicationContext(),EditActivity.class);
                intent.putExtra("id",student.getId());
                intent.putExtra("name",student.getName());
                intent.putExtra("course",student.getCourse());
                intent.putExtra("fee",student.getFee());
                startActivity(intent);

            }
        });

    }
}