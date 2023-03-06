package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Collections;


public class History extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    Integer no_lines;
    DataServer dataControl = new DataServer();
    FloatingActionButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        no_lines = dataControl.getLines(this);
        System.out.println("lines: "+no_lines);
        if(no_lines == 0){
            Toast.makeText(this, "No history found", Toast.LENGTH_SHORT).show();
            delete = findViewById(R.id.delete);
            delete.hide();
        }
        else{
            String text = dataControl.read(this);
            String[] lines = text.split("\n");
            Collections.reverse(Arrays.asList(lines));
            for (String line : lines) {
                addButton(line);
                System.out.println(line);
            }
            delete = findViewById(R.id.delete);
            delete.setOnClickListener(v -> {
                if(dataControl.clear(History.this))
                    Toast.makeText(History.this, "History Cleared", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(History.this, "History not Cleared", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
    private void addButton(String text) {
        LinearLayout layout = findViewById(R.id.buttonList);
        Button newBtn = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10,10, 10);
        newBtn.setLayoutParams(params);
        newBtn.setBackground(AppCompatResources.getDrawable(this,R.drawable.output_round_corner));
        newBtn.setTextColor(getResources().getColor(R.color.white));
        newBtn.setPadding(40, 40, 40, 40);
        newBtn.setTextSize(20);
        newBtn.setText(text);
        newBtn.setOnClickListener(this);
        newBtn.setOnLongClickListener(this);
        layout.addView(newBtn);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String text = button.getText().toString();
        System.out.println(text);
        String[] parts = text.split("=");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("inp", parts[0]);
        intent.putExtra("out", parts[1]);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onLongClick(View v) {
        Button button = (Button) v;
        String text = button.getText().toString();
        System.out.println(text+" long clicked");
        if(dataControl.deleteLine(this,text)){
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            ViewGroup layout = (ViewGroup) button.getParent();
            if(null!=layout)
                layout.removeView(button);
            no_lines=dataControl.getLines(this);
            if(no_lines==0){
                delete.hide();
            }
        }
        else{
            Toast.makeText(this, "Not Deleted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}