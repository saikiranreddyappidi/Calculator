package com.example.calculator;

import android.content.ClipboardManager;
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
    boolean isLongPressed = false,normal=true,selected=false;
    DataServer dataControl = new DataServer();
    FloatingActionButton delete;
    int id=0,sel=0;

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
                if (line.length() > 0)
                    addButton(line);
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
        newBtn.setText(text);
        setLayout(normal,newBtn);
        newBtn.setId(id++);
        System.out.println(text+" id: "+newBtn.getId());
        newBtn.setSelected(false);
        layout.addView(newBtn);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        if (!isLongPressed){
            String text = button.getText().toString();
            System.out.println(text);
            String[] parts = text.split("=");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("inp", parts[0]);
            intent.putExtra("out", parts[1]);
            startActivity(intent);
            finish();
        }
        else{
            if(button.isSelected()){
                setLayout(normal,button);
                sel--;
            }
            else{
                setLayout(selected,button);
                System.out.println("selected "+button.getId());
                sel++;
            }
            button.setSelected(!button.isSelected());
            if(sel==0){
                LinearLayout layout = findViewById(R.id.titleButtons);
                layout.setVisibility(View.VISIBLE);
                LinearLayout options = findViewById(R.id.userOptions);
                options.setVisibility(View.GONE);
                isLongPressed = false;
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Button button = (Button) v;
        setLayout(selected,button); //selected
        button.setSelected(true);
        isLongPressed = true;
        LinearLayout layout = findViewById(R.id.titleButtons);
        layout.setVisibility(View.GONE);
        LinearLayout options = findViewById(R.id.userOptions);
        options.setVisibility(View.VISIBLE);
        System.out.println("long pressed"+button.getId());
        sel++;
        return true;
    }

    public void deleteSelected(View v){
        LinearLayout layout = findViewById(R.id.buttonList);
        for(int i=0;i<layout.getChildCount();i++){
            Button button = (Button) layout.getChildAt(i);
            if(button.isSelected()){
                System.out.println("deleting "+button.getId());
                layout.removeView(button);
                dataControl.deleteLine(this, button.getText().toString());
                sel--;
                if (sel==0){
                    LinearLayout layout1 = findViewById(R.id.titleButtons);
                    layout1.setVisibility(View.VISIBLE);
                    LinearLayout options = findViewById(R.id.userOptions);
                    options.setVisibility(View.GONE);
                    isLongPressed = false;
                }
            }
        }
    }

    private void setLayout(boolean state, Button newBtn) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        newBtn.setLayoutParams(params);
        if (state)
            newBtn.setBackground(AppCompatResources.getDrawable(this, R.drawable.output_round_corner));
        else
            newBtn.setBackground(AppCompatResources.getDrawable(this, R.drawable.buttonselectebackground));
        newBtn.setTextColor(getResources().getColor(R.color.white));
        newBtn.setPadding(40, 40, 40, 40);
        newBtn.setTextSize(20);
        newBtn.setOnClickListener(this);
        newBtn.setOnLongClickListener(this);
    }

    public boolean copySelected(View v) {
        StringBuilder text = new StringBuilder();
        LinearLayout layout = findViewById(R.id.buttonList);
        for (int i = 0; i < layout.getChildCount(); i++) {
            Button button = (Button) layout.getChildAt(i);
            if (button.isSelected()) {
                text.append(button.getText().toString()).append("\n");
            }
        }
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(text.toString());
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void selectAll(View v){
        boolean selectedAll = true;
        LinearLayout layout = findViewById(R.id.buttonList);
        for(int i=0;i<layout.getChildCount();i++){
            Button button = (Button) layout.getChildAt(i);
            if(!button.isSelected()){
                setLayout(selected,button);
                button.setSelected(true);
                selectedAll = false;
                sel++;
            }
        }
        if(selectedAll){
            for(int i=0;i<layout.getChildCount();i++){
                Button button = (Button) layout.getChildAt(i);
                setLayout(normal,button);
                button.setSelected(false);
                sel--;
            }
        }
        if (sel==0){
            LinearLayout layout1 = findViewById(R.id.titleButtons);
            layout1.setVisibility(View.VISIBLE);
            LinearLayout options = findViewById(R.id.userOptions);
            options.setVisibility(View.GONE);
            isLongPressed = false;
        }
    }

    public void shareSelected(View v){
        StringBuilder text = new StringBuilder();
        LinearLayout layout = findViewById(R.id.buttonList);
        for (int i = 0; i < layout.getChildCount(); i++) {
            Button button = (Button) layout.getChildAt(i);
            if (button.isSelected()) {
                text.append(button.getText().toString()).append("\n");
            }
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text.toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}