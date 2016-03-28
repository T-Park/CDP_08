class MethodReturn
{
public static void main(String args[])
{
int result;

result = calc(4,5);

System.out.print(result);




}

static int calc(int num1, int num2)
{
int sum;
sum = num1 + num2;

return sum;
}

}