package tech.saikiranappidi.calculator;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    TextView input, output;
    ToggleButton toggle;
    Construct str = new Construct();
    boolean toggleState = false;
    Button history;
    Button more;
    Button finance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        input.setSelected(true);
        output = findViewById(R.id.output);
        output.setSelected(true);
        toggle = findViewById(R.id.togglebutton);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Button plus = findViewById(R.id.button_add);
            Button minus = findViewById(R.id.button_subtract);
            Button multiply = findViewById(R.id.button_multiply);
            Button divide = findViewById(R.id.button_divide);
            Button dot = findViewById(R.id.button_dot);
            if (isChecked) {
                plus.setText("|");
                minus.setText("&");
                multiply.setText("^");
                divide.setText("~");
                plus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33ff77")));
                minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33ff77")));
                multiply.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33ff77")));
                divide.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33ff77")));
                toggleState = true;
            }
            else{
                plus.setText("+");
                minus.setText("-");
                multiply.setText("*");
                divide.setText("/");
                dot.setText(".");
                toggleState = false;
                plus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00e6e6")));
                minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00e6e6")));
                multiply.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00e6e6")));
                divide.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00e6e6")));
            }
        });
        ImageButton backspace = findViewById(R.id.backspace);
        backspace.setOnLongClickListener(v -> {
            str.arr="";
            input.setText("0");
            output.setText("0");
            return true;
        });
        history = findViewById(R.id.history);
        history.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);
        });
        more = findViewById(R.id.more);
        more.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
        });
        finance = findViewById(R.id.finance);
        finance.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
        });

        try{
            Intent intent = getIntent();
            String inp = Objects.requireNonNull(intent.getStringExtra("inp")).trim();
            String out = Objects.requireNonNull(intent.getStringExtra("out")).trim();
            str.arr = inp;
            input.setText(inp);
            output.setText(out);
        }
        catch (Exception e){
            System.out.println("Exception in onCreate "+e.getMessage());
        }
    }
    public void one(View view) {
        input.setText(str.extend('1'));
        output.setText(str.ans());
    }
    public void two(View view) {
        input.setText(str.extend('2'));
        output.setText(str.ans());
    }
    public void three(View view) {
        input.setText(str.extend('3'));
        output.setText(str.ans());
    }
    public void four(View view) {
        input.setText(str.extend('4'));
        output.setText(str.ans());
    }
    public void five(View view) {
        input.setText(str.extend('5'));
        output.setText(str.ans());
    }
    public void six(View view) {
        input.setText(str.extend('6'));
        output.setText(str.ans());
    }
    public void seven(View view) {
        input.setText(str.extend('7'));
        output.setText(str.ans());
    }
    public void eight(View view) {
        input.setText(str.extend('8'));
        output.setText(str.ans());
    }
    public void nine(View view) {
        input.setText(str.extend('9'));
        output.setText(str.ans());
    }
    public void zero(View view) {
        input.setText(str.extend('0'));
        output.setText(str.ans());
    }
    public void equal(View view) {
        str.arr= str.ans();
        DataServer dataControl = new DataServer();
        String data = input.getText().toString()+" = "+output.getText().toString();
        boolean b=dataControl.write(data, this);
        if (b)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        input.setText(str.arr);
        str.equalPressed=true;
        output.setText("0");
    }
    public void plus(View view) {
        if(toggleState)
            input.setText(str.extend('|'));
        else
            input.setText(str.extend('+'));
    }
    public void minus(View view) {
        if (toggleState)
            input.setText(str.extend('&'));
        else
            input.setText(str.extend('-'));
    }
    public void multiply(View view) {
        if (toggleState)
            input.setText(str.extend('^'));
        else
            input.setText(str.extend('*'));
    }
    public void divide(View view) {
        if (toggleState)
            input.setText(str.extend('~'));
        else
            input.setText(str.extend('/'));
    }
    public void modulus(View view) {
        input.setText(str.extend('%'));
    }
    public void dot(View view) {
        input.setText(str.extend('.'));
    }
    public void backspace(View view) {
        if(str.arr.length()>0) {
            str.arr = str.arr.substring(0, str.arr.length() - 1);
            input.setText(str.arr);
            output.setText(str.ans());
        }
    }
    public void clear(View view) {
        input.setText("0");
        output.setText("0");
        str.arr="";
        str.result=BigDecimal.ZERO;
    }
}


