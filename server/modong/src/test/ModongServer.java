package test;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
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
 
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  String line = (String) msg;
	  if(line.startsWith("#PosIdentify"))//ex) #PosIdentify%32
	  {
		  System.out.println("Pos기에서의 요청 : pos기 사용자 인증");
		  String[] token = line.split("%");
		  if(!sa.findStore(Integer.parseInt(token[1])))
			{
				System.out.println(">> 존재 하지 않는 pid 입니다. pid : " + token[1]);
				this.sendToAllClients(">> 존재 하지 않는 pid 입니다. pid : " + token[1]);
			}	  
	  }
	  if(line.startsWith("#PosPointAdd"))// #PosPointAdd%pid%bacode%point
	  {
		  System.out.println("Pos기에서의 요청 : 포인트 적립");
		  String[] token = line.split("%");
		  int pid = Integer.parseInt(token[1]);
		  String bacode = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			  this.sendToAllClients(">> 존재 하지 않는  user bacode 입니다.");
		  }
		  ModongUser tempUser = ua.findUser_asBacode(bacode);		  
		  tempUser.addPoint(point);
		  sa.recordAddPoint_asBacode(bacode, pid, point);
		  
		  System.out.println(">>"+ pid +"번 포스기 : "+ point +"적립 되었습니다.");
		  
	  } else if(line.startsWith("#PosPointRemove"))
	  {
		  System.out.println("Pos기에서의 요청 : 포인트 사용");
		  String[] token = line.split("%");
		  int pid = Integer.parseInt(token[1]);
		  String bacode = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			  this.sendToAllClients(">> 존재 하지 않는  user bacode 입니다.");
		  }
		  ModongUser tempUser = ua.findUser_asBacode(bacode);		  
		  if(tempUser.removePoint(point))
		  {
			  sa.recordAddPoint_asBacode(bacode, pid, point);
			  System.out.println(">>"+ pid +"번 포스기 : "+ point +"사용 되었습니다.");
		  }
		  else 
			  this.sendToAllClients("point가 부족합니다.");
		  		  
	  }else if(line.startsWith("#MdUserIdentify"))//#MdUserIdentify%bacode
	  {
		  System.out.println("사용자 식별 요청");
		  String[] token = line.split("%");
		  String bacode = token[1];
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			  this.sendToAllClients(">> 존재 하지 않는  user bacode 입니다.");
		  }		  
	  }else if(line.startsWith("#ModongLogin"))
	  {
		  System.out.println("모바일에서의 요청 : login");
		  
	  }else if(line.startsWith("#ModongJoin"))
	  {
		  System.out.println("모바일에서의 요청 : 회원가입");
		  
	  }else if(line.startsWith("#ModongExistId"))
	  {
		  System.out.println("모바일에서의 요청 : 중복 id확인");
		  
	  }else if(line.startsWith("#ModongDonation"))
	  {
		  System.out.println("모바일에서의 요청 : 기부하기");
		  
	  }else if(line.startsWith("#ModongGivePoint"))
	  {
		  System.out.println("모바일에서의 요청 : 포인트 선물");
		  
	  }else if(line.startsWith("#ModongUseList"))
	  {
		  System.out.println("모바일에서의 요청 : 사용내열 list");
		  
	  }else if(line.startsWith("#ModongDonationList"))
	  {
		  System.out.println("모바일에서의 요청 : 기부내역 list");
		  
	  }else if(line.startsWith("#ModongGroupIn"))
	  {
		   System.out.println("모바일에서의 요청 : 그룹에 들어가기");
		 
	  }else if(line.startsWith("#ModongGroupOut"))
	  {
		  System.out.println("모바일에서의 요청 : 그룹에서 나오기");
		  
	  }else if(line.startsWith("#CcPointAdd"))
	  {
		  System.out.println("동전모음이에서의 요청 : 적립하기");
		  
	  }
	  else if(line.startsWith("#CcDonationPoint"))
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
