
class Subscribing
{
public static void main(String args[])
{

SubscriberInfo obj1, obj2;

obj1 = new SubscriberInfo("�����", "poorman", "zebi");
obj2 = new SubscriberInfo("�����", "richman", "money", 
"010-0000-0000", "Ÿ���Ӹ���");

printSubscribe(obj1);
printSubscribe(obj2);

}

static void printSubscribe(SubscriberInfo obj)
{
System.out.println("�̸� : "+obj.name);
System.out.println("���̵� : "+obj.id);
System.out.println("�н����� : "+obj.pw);
System.out.println("��ȭ��ȣ : "+obj.pho);
System.out.println("�ּ� : "+obj.addr);
System.out.println();
}

} 