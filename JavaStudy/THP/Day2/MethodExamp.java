class MethodExamp

{

public static void main(String args[])
{

Account obj;
obj = new Account("111-21-2233", "������", 100000);

obj.deposit(10000);
obj.withdraw(5000);

printAccount(obj);

}

static void printAccount(Account obj)

{

System.out.println("���¹�ȣ : "+obj.accountNo);
System.out.println("�̸� : "+obj.ownerName);
System.out.println("�ܾ� : "+obj.balance);
System.out.println();

}
}