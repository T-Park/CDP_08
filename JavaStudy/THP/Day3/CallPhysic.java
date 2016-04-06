
class CallPhysic

{

public static void main(String args[])

{

PhysicalInfo obj;

obj = new PhysicalInfo("Çý¸®", 22, 155.0f, 45.0f);

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
System.out.println("ÀÌ¸§ : "+obj.name);
System.out.println("³ªÀÌ : "+obj.age);
System.out.println("Å° : "+obj.height);
System.out.println("¸ö¹«°Ô : "+obj.weight);
System.out.println();
}

}




