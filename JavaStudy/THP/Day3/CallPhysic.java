
class CallPhysic

{

public static void main(String args[])

{

PhysicalInfo obj;

obj = new PhysicalInfo("����", 22, 155.0f, 45.0f);

printPhy(obj);

obj.update(23, 158.0f, 48.0f);

printPhy(obj);

obj.update(24, 160.0f);

printPhy(obj);

obj.update(25);

printPhy(obj);

}

static void printPhy(PhysicalInfo obj)
{
System.out.println("�̸� : "+obj.name);
System.out.println("���� : "+obj.age);
System.out.println("Ű : "+obj.height);
System.out.println("������ : "+obj.weight);
System.out.println();
}

}




