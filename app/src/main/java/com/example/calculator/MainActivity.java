package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;

class EvaluateString
{
    public static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new
                Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new
                Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {

            // Current token is a
            // whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number,
            // push it to stack for numbers
            if (tokens[i] >= '0' &&
                    tokens[i] <= '9')
            {
                StringBuffer sbuf = new
                        StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length &&
                        tokens[i] >= '0' &&
                        tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.
                        toString()));

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                // token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }

            // Current token is an opening brace,
            // push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered,
                // solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' ||
                    tokens[i] == '-' ||
                    tokens[i] == '*' ||
                    tokens[i] == '/')
            {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() &&
                        hasPrecedence(tokens[i],
                                ops.peek()))
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        try{
            while (!ops.empty())
                values.push(applyOp(ops.pop(),
                        values.pop(),
                        values.pop()));

            // Top of 'values' contains
            // result, return it
            return values.pop();
        }
        catch (Exception e){
            System.out.println("Invalid Expression");
            return 0;
        }
    }

    // Returns true if 'op2' has higher
    // or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') &&
                (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an
    // operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static int applyOp(char op,
                              int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException(
                            "Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

}


class construct{
    char num;
    String arr="";
    float result=0;
    public String extend(char num){
        this.num = num;
        arr+=num;
        System.out.println(arr+" from extend");
        return arr;
    }
    public String ans() {
        int i=arr.length()-1;
        if(arr.charAt(i)=='+'||arr.charAt(i)=='-'||arr.charAt(i)=='*'||arr.charAt(i)=='/'){
            System.out.println("Invalid Expression");
            return String.valueOf(result);
        }
        result = EvaluateString.evaluate(arr);
        return String.valueOf(result);
    }
}
public class MainActivity extends AppCompatActivity {
    TextView input, output;
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
    public void clear(View view) {
        input.setText("");
        output.setText("");
        str.arr="";
        str.result=0;
    }
}
