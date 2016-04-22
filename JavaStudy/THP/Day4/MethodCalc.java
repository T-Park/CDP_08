class MethodCalc
{
public static void main(String args[])
{

int result;

result = calc("10","11","34","53");

System.out.println(result);
}

static int calc(String ch1, String ch2, String ch3, String ch4)
{
int sum;

int num1, num2, num3, num4;

num1 = Integer.parseInt(ch1);
num2 = Integer.parseInt(ch2);

num3 = Integer.parseInt(ch3);
num4 = Integer.parseInt(ch4);

sum = num1 + num2 + num3 + num4;

return sum;

}
}