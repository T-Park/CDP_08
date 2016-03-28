class MethodCall3
{
public static void main(String args[])
{

cat('*',30);
System.out.println("Hello, Java.... Process Done!");
cat('#',30);

}

static void cat(char ch, int num)
{
for(int i=0;i<num;i++)
System.out.print(ch);
System.out.println();
}
}