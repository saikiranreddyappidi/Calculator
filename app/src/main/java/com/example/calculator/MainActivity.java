package com.example.calculator;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Stack;

class EvaluateString
{
    public static BigDecimal evaluate(String input)
    {
        try{
            Stack<BigInteger> op  = new Stack<>();
            Stack<BigDecimal> val = new Stack<>();
            Stack<BigInteger> optmp  = new Stack<>();
            Stack<BigDecimal> valtmp = new Stack<>();
            input = "0" + input;
            input = input.replaceAll("-","+-");
            StringBuilder temp = new StringBuilder();
            for (int i = 0;i < input.length();i++)
            {
                char ch = input.charAt(i);
                if (ch == '-')
                    temp.insert(0, "-");
                else if (ch != '+' &&  ch != '*' && ch != '/' && ch!='%' && ch!='&' && ch!='|' && ch!='^' && ch!='~')
                    temp.append(ch);
                else
                {
                    val.push(BigDecimal.valueOf(Double.parseDouble(temp.toString())));
                    op.push(BigInteger.valueOf(ch));
                    temp = new StringBuilder();
                }
            }
            val.push(BigDecimal.valueOf(Double.parseDouble(temp.toString())));
            char[] operators = {'/','*','%','+','&','|','^','~'};
            for (int i = 0; i < 8; i++)
            {
                boolean it = false;
                while (!op.isEmpty())
                {
                    int optr = op.pop().intValue();
                    BigDecimal v1 = new BigDecimal(String.valueOf(val.pop()));
                    BigDecimal v2 = new BigDecimal(String.valueOf(val.pop()));
                    BigDecimal zero = new BigDecimal(0);
                    BigDecimal res = new BigDecimal(0);
                    if (optr == operators[i])
                    {
                        if (i == 0)
                        {
                            if (v1.equals(zero))
                            {
                                System.out.println("Can't divide by 0");
                            }
                            else {
                                res = v2.divide(v1, 8, BigDecimal.ROUND_HALF_UP);
                            }
                            valtmp.push(res);
                            it = true;
                            break;
                        }
                        else if (i == 1)
                        {
                            valtmp.push(v2.multiply(v1));
                            it = true;
                            break;
                        }
                        else if(i==2) {
                            if (v1.equals(zero))
                            {
                                System.out.println("Can't divide by 0");
                            }
                            else {
                                res = BigDecimal.valueOf(v2.doubleValue()%v1.doubleValue());
                            }
                            valtmp.push(res);
                            it = true;
                            break;
                        }
                        else if(i==3){
                            valtmp.push(v1.add(v2));
                            it = true;
                            break;
                        }
                        else if(i==4){
                            valtmp.push(BigDecimal.valueOf((v2.intValue() & v1.intValue())));
                            it = true;
                            break;
                        }
                        else if(i==5){
                            valtmp.push(BigDecimal.valueOf((v2.intValue() | v1.intValue())));
                            it = true;
                            break;
                        }
                        else if(i==6){
                            valtmp.push(BigDecimal.valueOf((v2.intValue() ^ v1.intValue())));
                            it = true;
                            break;
                        }
                        else {
                            valtmp.push(BigDecimal.valueOf(~ v1.intValue()));
                            it = true;
                            break;
                        }
                    }
                    else
                    {
                        valtmp.push(v1);
                        val.push(v2);
                        optmp.push(BigInteger.valueOf(optr));
                    }
                }
                while (!valtmp.isEmpty())
                    val.push(valtmp.pop());
                while (!optmp.isEmpty())
                    op.push(optmp.pop());
                if (it)
                    i--;
            }
            BigDecimal value=val.pop();
            System.out.println("\nResult = "+value);
            return value;
        }
        catch (Exception e){
            System.out.println("Invalid Expression Evaluation"+e.getMessage());
            return BigDecimal.valueOf(0.00);
        }
    }
}



class construct{
    char num;
    String arr="";
    BigDecimal result = new BigDecimal(0);
    boolean flag=false,zero_flag=false;
    public String extend(char num){
        this.num = num;
        if(ErrorDetection(arr,num)){
            return arr;
        }
        if(flag && !zero_flag){
            System.out.println("flag true"+arr);
            arr = "";
            flag = false;
        }
        try{
            if (zero_flag){
                int i=arr.length()-1;
                System.out.println("zero flag true"+arr.charAt(i));
                if(arr.charAt(i)=='.')
                {
                    zero_flag = false;
                }
            }
        }
        catch (Exception e){
            System.out.println("Invalid Expression constructor"+e.getMessage());
        }
        arr+=num;
        System.out.println(arr+" from extend");
        return arr;
    }
    public String ans() {
        int i=arr.length()-1;
        if(ErrorDetection(arr,num)){
            flag=true;
            return "Invalid Expression";
        }
        try{
            if(arr.charAt(i)=='0' &&(arr.charAt(i-1)=='/'||arr.charAt(i-1)=='%'))
            {
                System.out.println("Invalid Expression constructor zero checking.");
                flag=true;
                zero_flag=true;
                return "Can't divide by 0";
            }
        }
        catch (Exception e){
            System.out.println("Invalid Expression constructor"+e.getMessage());
            flag=true;
            return "0";
        }
        result = EvaluateString.evaluate(arr);
        flag=false;
        DecimalFormat df = new DecimalFormat("#.#######");
        return df.format(result);
    }
    public boolean ErrorDetection(String arr,char num){
        arr+=num;
        try{
            int i=arr.length()-1;
            System.out.println(arr+" from ErrorDetection"+i);
            if((arr.charAt(i)=='+'||arr.charAt(i)=='-'||arr.charAt(i)=='*'||arr.charAt(i)=='/'||arr.charAt(i)=='%'||
                    arr.charAt(i)=='.'||arr.charAt(i)=='&'||arr.charAt(i)=='|'||arr.charAt(i)=='^')&&
                    (arr.charAt(i-1)=='+'||arr.charAt(i-1)=='-'||arr.charAt(i-1)=='*'||arr.charAt(i-1)=='/'||arr.charAt(i-1)=='%'||
                            arr.charAt(i-1)=='.'||arr.charAt(i-1)=='&'||arr.charAt(i-1)=='|'||arr.charAt(i-1)=='^')
            )
            {
                System.out.println("Invalid Expression ErrorDetection");
                return true;
            }
        }
        catch (Exception e){
            System.out.println("Invalid Expression an exception ErrorDetection"+e.getMessage());
            return true;
        }
        return false;
    }
}
public class MainActivity extends AppCompatActivity {
    TextView input, output;
    ToggleButton toggle;
    construct str = new construct();
    boolean toggleState = false;
    Button history;

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
        ImageButton backspace = (ImageButton) findViewById(R.id.backspace);
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
        try{
            Intent intent = getIntent();
            String inp = intent.getStringExtra("inp").trim();
            String out = intent.getStringExtra("out").trim();
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
        DataControl dataControl = new DataControl();
        String data = input.getText().toString()+" = "+output.getText().toString();
        boolean b=dataControl.write(data, this);
        if (b)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        input.setText(str.arr);
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


class DataControl{

    private static Integer lines=0;
    public String read(Context context){
        try {
            FileInputStream fileInputStream = context.openFileInput("data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
                lines++;
            }
            fileInputStream.close();
            return stringBuilder.toString();
        }
        catch (Exception e){
            return "";
        }
    }
    protected boolean write(String data, Context context){
        try {
            data+= "\n";
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_APPEND);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            lines++;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    protected Integer getLines(Context context){
        read(context);
        return lines;
    }
    protected static boolean delete(Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    protected boolean clear(Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
            lines=0;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}