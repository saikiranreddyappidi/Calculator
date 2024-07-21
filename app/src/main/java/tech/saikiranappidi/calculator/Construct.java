package tech.saikiranappidi.calculator;

import android.icu.math.BigDecimal;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Stack;

class EvaluateExpression
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



public class Construct{
    char num;
    String arr="";
    BigDecimal result = new BigDecimal(0);
    boolean flag=false,zero_flag=false,equalPressed=false;
    public String extend(char num){
        this.num = num;
        if(equalPressed){
            if (!(num=='+'||num=='-'||num=='*'||num=='/'||num=='%'||num=='&'||num=='|'||num=='^'||num=='~'))
                arr = "";
            equalPressed=false;
        }
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
        result = EvaluateExpression.evaluate(arr);
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
