package test;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUser;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import common.ChatIF;
import server.*;


public class ModongServer extends AbstractServer
{
	ModongUserAdmin ua;
	CoinCollectorAdmin cca;
	DonationOrgnzAdmin doa;
	StoreAdmin sa;
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public ModongServer(int port) 
  {
    super(port);
    ua = ModongUserAdmin.getInstance();
    cca = CoinCollectorAdmin.getInstance();
    doa = DonationOrgnzAdmin.getInstance();
    sa = StoreAdmin.getInstance();
    
  }

  
  //Instance methods ************************************************
 
  public void handleMessageFromClient //성공시 #msg,  실패시 실패사유
    (Object msg, ConnectionToClient client)
  {
	  String line = (String) msg;
	  String[] token = line.split("%");
	  if(line.startsWith("#PosIdentify"))//ex) #PosIdentify%32, 성공시 #true 실패시 메세지 
	  {
		  System.out.println("Pos기에서의 요청 : pos기 사용자 인증");
		  
		  if(!sa.findStore(Integer.parseInt(token[1])))
			{
				System.out.println(">> 존재 하지 않는 pid 입니다. pid : " + token[1]);
				sendToMyClient(client, "존재 하지 않는 pid 입니다.");
			}
		  else//인증 o
			  sendToMyClient(client, "#true");
	  }
	  if(line.startsWith("#PosPointAdd"))// #PosPointAdd%pid%bacode%point 성공시 add후 point rt
	  {
		  System.out.println("Pos기에서의 요청 : 포인트 적립");
		  //String[] token = line.split("%");
		  int pid = Integer.parseInt(token[1]);
		  String bacode = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  //그룹 바코드 처리
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			 // sendToMyClient(client, "존재 하지 않는 user bacode 입니다.");
		  }
		  else
		  {
			  ModongUser tempUser = ua.findUser_asBacode(bacode);		  
			  tempUser.addPoint(point);
			  sa.recordAddPoint_asBacode(bacode, pid, point);
			  
			  System.out.println(">>"+ pid +"번 포스기 : "+ point +"적립 되었습니다.");
			  sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());			  
		  }
				  
	  } else if(line.startsWith("#PosPointRemove"))// #PosPointRemove%pid%bacode%point 성공시 remove후 point rt
	  {
		  System.out.println("Pos기에서의 요청 : 포인트 사용");
		 // String[] token = line.split("%");
		  int pid = Integer.parseInt(token[1]);
		  String bacode = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  //그룹 바코드 처리
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			  sendToMyClient(client, "존재 하지 않는  user bacode 입니다.");
		  }
		  else
		  {
			  ModongUser tempUser = ua.findUser_asBacode(bacode);		  
			  if(tempUser.removePoint(point))
			  {
				  sa.recordAddPoint_asBacode(bacode, pid, point);
				  System.out.println(">>"+ pid +"번 포스기 : "+ point +"사용 되었습니다.");
				  sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());	
			  }
			  else 
				  sendToMyClient(client, "point가 부족합니다.");		  
		  }		  		  		  
	  }else if(line.startsWith("#MdUserIdentify"))//#MdUserIdentify%bacode 성공시user point 리턴
	  {//pos/cc용
		  System.out.println("사용자 식별 요청");
		  //String[] token = line.split("%");
		  String bacode = token[1];
		  
		  //그룹 바코드 처리
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			  sendToMyClient(client, "존재 하지 않는user bacode 입니다.");
		  }
		  else
		  {			  
			  sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
		  }
		  
	  }
	  //****************************모바일 앱**************************************//
	  else if(line.startsWith("#ModongLogin"))//#ModongLogin%id%pw
	  {
		  System.out.println("모바일에서의 요청 : login");
		 String id = token[1], pw = token[2];
		  if(ua.loginUser(id, pw))
		  {
			  ModongUser md = ua.getModongUser_asId(id);
			  String groupFlag, groupName;
			  if(md.getGroupCode() == -1)
			  {
				  groupFlag = "0";
				  groupName = "null";
			  }
			  else
			  {
				  groupFlag = "1";
				  groupName = ua.getGroupName(md.getGroupCode());
			  }
			  
			  sendToMyClient(client, "#" + md.getUser_id() + 
					  "%" +  md.getUser_pw() +
					  "%" +  md.getUser_name() +
					  "%" +  md.getUser_job() +
					  "%" +  md.getUser_age() +
					  "%" +  md.getUser_tel() +
					  "%" +  groupFlag +
					  "%" +  groupName
					  );
		  }
		  else
		  {
			  sendToMyClient(client, "login 실패");	
		  }
	  }else if(line.startsWith("#ModongJoin"))//#ModongJoin%id%pw%name%job%age%tel
	  {
		  System.out.println("모바일에서의 요청 : 회원가입");
		  if(ua.joinModong(token[1], token[2], 
				  token[3], token[4], Integer.parseInt(token[5]), token[6]))
		  {
			  sendToMyClient(client, "#true");
		  }
		  else
		  {
			  sendToMyClient(client, "회원가입 실패");
		  }
		  	
	  }else if(line.startsWith("#ModongExistId"))//#ModongExistId%id
	  {
		  System.out.println("모바일에서의 요청 : 중복 id확인");
		  if(ua.isThereUser_asId(token[1]))
		  {
			  sendToMyClient(client, "#true");
		  }
		  else
			  sendToMyClient(client, "false");
		  
	  }else if(line.startsWith("#ModongModify"))//#ModongModify%id%pw%name%job%age%tel
	  {
		  System.out.println("모바일에서의 요청 : 회원정보수정");
		 String id = token[1]; String job = token[4];
		 String pw = token[2]; int age = Integer.parseInt(token[5]);
		 String name = token[3]; String tel = token[6];
		  
		 ua.modifyUser(id, pw, name, job, age, tel);
		 sendToMyClient(client, "#true");
		  
	  }else if(line.startsWith("#ModongDonation")) //#ModongDonation%id%gname%point
	  {
		  System.out.println("모바일에서의 요청 : 기부하기");
		  String id = token[1];
		  int did = doa.dNameToDid(token[2]);
		  int point = Integer.parseInt(token[3]);
		  
		  ModongUser user = ua.findUser_asId(id);
		  int uid = ua.idToUid(id);
		  if(user.removePoint(point))
		  {
			  doa.recordDonationPoint(uid, did, point);
			  doa.findDonationOrgnz(did).addPoint(point);
			  sendToMyClient(client, "#true");
		  }
		  else sendToMyClient(client, "포인트가 부족합니다.");
		  
	  }else if(line.startsWith("#ModongGivePoint"))//#ModongGivePoint%fromId%toId%point
	  {
		  System.out.println("모바일에서의 요청 : 포인트 선물");
		  String fromId = token[1], toId = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  ModongUser fromUser = ua.findUser_asId(fromId);
		  ModongUser toUser = ua.findUser_asId(toId);
		  
		  if(fromUser.removePoint(point))
		  {
			  toUser.addPoint(point);
			  sendToMyClient(client, "#true");
		  }
		  else
			 sendToMyClient(client, "포인트가 부족합니다.");
		  
		 
	  }else if(line.startsWith("#ModongUseList"))//#ModongUseList%id
	  {
		  System.out.println("모바일에서의 요청 : 사용내열 list");
		  String id = token[1];
		  ArrayList<ModongUserAdmin.Item> myList = ua.getMyUseList(ua.idToUid(id));
		  sendToMyClient(client, myList);
		  
	  }else if(line.startsWith("#ModongDonationList"))//#ModongDonationList%id
	  {
		  System.out.println("모바일에서의 요청 : 기부내역 list");
		  String id = token[1];
		  ArrayList<DonationOrgnzAdmin.dItem> myList = doa.getMyDonationList(ua.idToUid(id));
		  sendToMyClient(client, myList);
		  
	  }else if(line.startsWith("#ModongGroupIn"))//#ModongGroupIn%3%id%id%id%group name
	  {
		   System.out.println("모바일에서의 요청 : 그룹에 들어가기");
		   String[] ids = new String[Integer.parseInt(token[1])];
		   int i=0;
		   for(; i< ids.length; i++)
		   {
			   ids[i] = token[i+2];
		   }
		   if(ua.groupingUser(ids, token[i]))
			   sendToMyClient(client, "#true");
		   else
			   sendToMyClient(client, "실패했습니다.");
		   
		   
	  }else if(line.startsWith("#ModongGroupOut"))//#ModongGroupOut%id
	  {
		  System.out.println("모바일에서의 요청 : 그룹에서 나오기");
		  
		  ModongUser user = ua.findUser_asId(token[1]);
		  user.leaveGroup();
		  sendToMyClient(client, "#true");
		  	  
	  }else if(line.startsWith("#ModongSyn"))//#ModongSyn%id	rt point%groupflag%groupName
	  {
		   System.out.println("모바일에서의 요청 : 동기화 요청");
		   ua.updateGroupList();
		   ua.updateUserList();
		   
		   ModongUser user = ua.findUser_asId(token[1]);
		   int point = user.getUser_point();
		   int groupCode = user.getGroupCode();
		   if(groupCode == ua.default_groupCode)
		   {
			   String groupName = ua.gcodeTogname(groupCode);
			   sendToMyClient(client, "#" + point + "%" + "true" + "%" + groupName);
		   }
		   else
		   {
			   sendToMyClient(client, "#" + point + "%" + "false" + "%" + "null");
		   }
	  }else if(line.startsWith("#ModongMyBacode"))//#ModongMyBacode%id
	  {
		   System.out.println("모바일에서의 요청 : 바코드 요청");
		   
		   
		   
	  }else if(line.startsWith("#ModongGroupBacode"))//#ModongGroupBacode%id
	  {
		   System.out.println("모바일에서의 요청 : 그룸 바코드 요청");
		   
		   
		   
	  }
	  //****************************동전 모음이**************************************//
	  /*
	   * 2016.5.23
	   * modified by kth
	   */
	  else if(line.startsWith("#CcLogin"))//#CcLogin%cid
	  {
		  System.out.println("동전모음이에서의 요청 : 로그인");
		  sendToMyClient(client, "#Error%test error");
	  }
	  else if(line.startsWith("#CcLogin"))//#CcPointAdd%cid%point
	  {
		  System.out.println("동전모음이에서의 요청 : 로그인");
		  
	  }
	  else if(line.startsWith("#CcPointAdd"))//#CcPointAdd%cid%bacode%point
	  {
		  System.out.println("동전모음이에서의 요청 : 적립하기");
		  
	  }
	  else if(line.startsWith("#CcDonationPoint"))//#CcDonationPoint%cid%bacode%point
	  {
		  
		  System.out.println("동전모음이에서의 요청 : 기부하기");
	  }
	  else
	  {
		   System.out.println("Message received: " + msg + " from " + client);
		   this.sendToAllClients(msg);
	  }	  
  }
    
  protected void serverStarted()
  {
	  System.out.println
      	("Server listening for connections on port " + getPort());
  }
  
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  @Override
  public void clientConnected(ConnectionToClient client) {

	  System.out.print("New Client connected !!! (IP :");
	  System.out.print(client.getInetAddress().getHostAddress());	 
	  System.out.println(")"); 
  }
  
  public void sendToMyClient(ConnectionToClient client, Object msg)
  {
	  try
	  {
		  client.sendToClient(msg);
	  }
	  catch(IOException e)
	  {
		  e.printStackTrace();
	  }
  }
  
  //Class methods ***************************************************
  
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    ModongServer sv = new ModongServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    
    ServerConsole sc = new ServerConsole(sv);
    sc.accept();
  }
}
//End of EchoServer class
