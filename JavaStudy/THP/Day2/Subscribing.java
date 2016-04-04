
class Subscribing
{
public static void main(String args[])
{

SubscriberInfo obj1, obj2;

obj1 = new SubscriberInfo("연흥부", "poorman", "zebi");
obj2 = new SubscriberInfo("연놀부", "richman", "money", 
"010-0000-0000", "타워팰리스");

printSubscribe(obj1);
printSubscribe(obj2);

}

static void printSubscribe(SubscriberInfo obj)
{
System.out.println("이름 : "+obj.name);
System.out.println("아이디 : "+obj.id);
System.out.println("패스워드 : "+obj.pw);
System.out.println("전화번호 : "+obj.pho);
System.out.println("주소 : "+obj.addr);
System.out.println();
}

} 