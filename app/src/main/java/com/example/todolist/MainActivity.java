package com.example.todolist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button button;
    EditText eview;
    RecyclerView rview;
    ItemsAdapter itemsAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        eview = findViewById(R.id.eview);
        rview = findViewById(R.id.rview);
        
        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rview.setAdapter(itemsAdapter);
        rview.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toDoItem = eview.getText().toString();
                items.add(toDoItem);
                itemsAdapter.notifyItemInserted(items.size()-1);
                eview.setText("");
                Toast.makeText(getApplicationContext(), "item added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
        //dsdad
    }
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "error reading items", e);
        }

    }
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch(IOException e){
            Log.e("MainActivity", "error saving items", e);
        }
    }
}