class ArrayExample
{
public static void main(String args[])
{
int arr[][] = new int[10][10];
int sum = 0;
arr[0][0] = 10;
arr[5][5] = 1;
arr[9][9] = 5;

sum = arr[0][0] + arr[9][9];

System.out.println("합계는 "+sum+" 입니다.");

}
}