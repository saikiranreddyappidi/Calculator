package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

class EvaluateString
{
    public static double evaluate(String input)
    {
        try{
            Stack<Integer> op  = new Stack<Integer>();
            Stack<Double> val = new Stack<Double>();
            Stack<Integer> optmp  = new Stack<Integer>();
            Stack<Double> valtmp = new Stack<Double>();
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
                    val.push(Double.parseDouble(temp.toString()));
                    op.push((int)ch);
                    temp = new StringBuilder();
                }
            }
            val.push(Double.parseDouble(temp.toString()));
            char[] operators = {'/','*','%','+','&','|','^','~'};
            for (int i = 0; i < 8; i++)
            {
                boolean it = false;
                while (!op.isEmpty())
                {
                    int optr = op.pop();
                    double v1 = val.pop();
                    double v2 = val.pop();
                    if (optr == operators[i])
                    {
                        if (i == 0)
                        {
                            double res=0;
                            if (v1 == 0)
                            {
                                System.out.println("Can't divide by 0");
                            }
                            else {
                                res = v2 / v1;
                            }
                            valtmp.push(res);
                            it = true;
                            break;
                        }
                        else if (i == 1)
                        {
                            valtmp.push(v2 * v1);
                            it = true;
                            break;
                        }
                        else if(i==2) {
                            double res=0;
                            if (v1 == 0)
                            {
                                System.out.println("Can't divide by 0");
                            }
                            else {
                                res = v2 % v1;
                            }
                            valtmp.push(res);
                            it = true;
                            break;
                        }
                        else if(i==3){
                            valtmp.push(v2 + v1);
                            it = true;
                            break;
                        }
                        else if(i==4){
                            valtmp.push((double) ((int)v2 & (int)v1));
                            it = true;
                            break;
                        }
                        else if(i==5){
                            valtmp.push((double) ((int)v2 | (int)v1));
                            it = true;
                            break;
                        }
                        else if(i==6){
                            valtmp.push((double) ((int)v2 ^ (int)v1));
                            it = true;
                            break;
                        }
                        else {
                            valtmp.push((double) (~ (int)v1));
                            it = true;
                            break;
                        }
                    }
                    else
                    {
                        valtmp.push(v1);
                        val.push(v2);
                        optmp.push(optr);
                    }
                }
                while (!valtmp.isEmpty())
                    val.push(valtmp.pop());
                while (!optmp.isEmpty())
                    op.push(optmp.pop());
                if (it)
                    i--;
            }
            double value=val.pop();
            System.out.println("\nResult = "+value);
            return value;
        }
        catch (Exception e){
            System.out.println("Invalid Expression Evaluation"+e.getMessage());
            return 0.00;
        }
    }
}



class construct{
    char num;
    String arr="";
    double result=0;
    public String extend(char num){
        this.num = num;
        arr+=num;
        System.out.println(arr+" from extend");
        return arr;
    }
    public String ans() {
        try{
            int i=arr.length()-1;
            if(arr.charAt(i)=='+'||arr.charAt(i)=='-'||arr.charAt(i)=='*'||arr.charAt(i)=='/'||
                    arr.charAt(i)=='%'||arr.charAt(i)=='.'||arr.charAt(i)=='&'||arr.charAt(i)=='|'||arr.charAt(i)=='^'||
                    (arr.charAt(i)=='0' &&(arr.charAt(i-1)=='/'||arr.charAt(i-1)=='%'))
            )
            {
                System.out.println("Invalid Expression constructor");
                return "Invalid Expression";
            }
            result = EvaluateString.evaluate(arr);
            return String.valueOf(result);
        }
        catch (Exception e){
            System.out.println("Invalid Expression an exception constructor");
            return String.valueOf(result);
        }
    }
}
public class MainActivity extends AppCompatActivity {
    TextView input, output;
    ToggleButton toggle;
    construct str = new construct();
    boolean toggleState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
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
                toggleState = true;
                dot.setText("N/A");

            }
            else{
                plus.setText("+");
                minus.setText("-");
                multiply.setText("*");
                divide.setText("/");
                dot.setText(".");
                toggleState = false;
            }
        });
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
    public void doubleZero(View view) {
        input.setText(str.extend('0'));
        input.setText(str.extend('0'));
        output.setText(str.ans());
    }
    public void equal(View view) {
        str.arr= str.ans();
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
        if (!toggleState)
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
        input.setText("");
        output.setText("");
        str.arr="";
        str.result=0;
    }
}
