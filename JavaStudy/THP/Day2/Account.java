class Account {

String accountNo;
String ownerName;
int balance;

Account(String accountNo, String ownerName, int balance)
{
this.accountNo = accountNo;
this.ownerName = ownerName;
this.balance = balance;

}

void deposit (int amount)
{
balance += amount;

}

int withdraw (int amount)
{
if (amount>balance)
return 0;

balance -= amount;

return amount;

}
}

