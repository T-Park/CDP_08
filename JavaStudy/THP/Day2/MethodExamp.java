class MethodExamp

{

public static void main(String args[])
{

Account obj;
obj = new Account("111-21-2233", "하하하", 100000);

obj.deposit(10000);
obj.withdraw(5000);

printAccount(obj);

}

static void printAccount(Account obj)

{

System.out.println("계좌번호 : "+obj.accountNo);
System.out.println("이름 : "+obj.ownerName);
System.out.println("잔액 : "+obj.balance);
System.out.println();

}
}