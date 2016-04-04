
class SubscriberInfo
{

String name, id, pw, pho, addr;

SubscriberInfo(String name, String id, String pw)
{
this.name = name;
this.id = id;
this.pw = pw;
}

SubscriberInfo(String name, String id, String pw, String pho,
String addr)
{
this.name = name;
this.id = id;
this.pw = pw;
this.pho = pho;
this.addr = addr;
}

void changePassword (String pw)
{
this.pw = pw;
}

void setPhoneNo (String pho)
{
this.pho = pho;
}

void setAddress (String addr)
{
this.addr = addr;
}

} 