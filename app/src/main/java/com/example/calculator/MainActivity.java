package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
            String temp = "";
            for (int i = 0;i < input.length();i++)
            {
                char ch = input.charAt(i);
                if (ch == '-')
                    temp = "-" + temp;
                else if (ch != '+' &&  ch != '*' && ch != '/' && ch!='%')
                    temp = temp + ch;
                else
                {
                    val.push(Double.parseDouble(temp));
                    op.push((int)ch);
                    temp = "";
                }
            }
            val.push(Double.parseDouble(temp));
            char[] operators = {'/','*','%','+'};
            for (int i = 0; i < 4; i++)
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
                        else {
                            valtmp.push(v2 + v1);
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
            System.out.println("Invalid Expression");
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
            if(arr.charAt(i)=='+'||arr.charAt(i)=='-'||arr.charAt(i)=='*'||arr.charAt(i)=='/'||arr.charAt(i)=='%'||arr.charAt(i)=='.'||
                    (arr.charAt(i)=='0' &&(arr.charAt(i-1)=='/'||arr.charAt(i-1)=='%'))
            )
            {
                System.out.println("Invalid Expression");
                return "Invalid Expression";
            }
            result = EvaluateString.evaluate(arr);
            return String.valueOf(result);
        }
        catch (Exception e){
            System.out.println("Invalid Expression an exception");
            return String.valueOf(result);
        }
    }
}
public class MainActivity extends AppCompatActivity {
    TextView input, output;
    ToggleButton toggle;
    construct str = new construct();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
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
        input.setText(str.extend('+'));
    }
    public void minus(View view) {
        input.setText(str.extend('-'));
    }
    public void multiply(View view) {
        input.setText(str.extend('*'));
    }
    public void divide(View view) {
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
        input.setText("");
        output.setText("");
        str.arr="";
        str.result=0;
    }
}
