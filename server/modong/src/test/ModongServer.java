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
		  System.out.println("Pos�⿡���� ��û : pos�� ����� ����");
		  String[] token = line.split("%");
		  if(!sa.findStore(Integer.parseInt(token[1])))
			{
				System.out.println(">> ���� ���� �ʴ� pid �Դϴ�. pid : " + token[1]);
				this.sendToAllClients(">> ���� ���� �ʴ� pid �Դϴ�. pid : " + token[1]);
			}	  
	  }
	  if(line.startsWith("#PosPointAdd"))// #PosPointAdd%pid%bacode%point
	  {
		  System.out.println("Pos�⿡���� ��û : ����Ʈ ����");
		  String[] token = line.split("%");
		  int pid = Integer.parseInt(token[1]);
		  String bacode = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			  this.sendToAllClients(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
		  }
		  ModongUser tempUser = ua.findUser_asBacode(bacode);		  
		  tempUser.addPoint(point);
		  sa.recordAddPoint_asBacode(bacode, pid, point);
		  
		  System.out.println(">>"+ pid +"�� ������ : "+ point +"���� �Ǿ����ϴ�.");
		  
	  } else if(line.startsWith("#PosPointRemove"))
	  {
		  System.out.println("Pos�⿡���� ��û : ����Ʈ ���");
		  String[] token = line.split("%");
		  int pid = Integer.parseInt(token[1]);
		  String bacode = token[2];
		  int point = Integer.parseInt(token[3]);
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			  this.sendToAllClients(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
		  }
		  ModongUser tempUser = ua.findUser_asBacode(bacode);		  
		  if(tempUser.removePoint(point))
		  {
			  sa.recordAddPoint_asBacode(bacode, pid, point);
			  System.out.println(">>"+ pid +"�� ������ : "+ point +"��� �Ǿ����ϴ�.");
		  }
		  else 
			  this.sendToAllClients("point�� �����մϴ�.");
		  		  
	  }else if(line.startsWith("#MdUserIdentify"))//#MdUserIdentify%bacode
	  {
		  System.out.println("����� �ĺ� ��û");
		  String[] token = line.split("%");
		  String bacode = token[1];
		  
		  if(!ua.isThereUser_asBacode(bacode))
		  {
			  System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			  this.sendToAllClients(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
		  }		  
	  }else if(line.startsWith("#ModongLogin"))
	  {
		  System.out.println("����Ͽ����� ��û : login");
		  
	  }else if(line.startsWith("#ModongJoin"))
	  {
		  System.out.println("����Ͽ����� ��û : ȸ������");
		  
	  }else if(line.startsWith("#ModongExistId"))
	  {
		  System.out.println("����Ͽ����� ��û : �ߺ� idȮ��");
		  
	  }else if(line.startsWith("#ModongDonation"))
	  {
		  System.out.println("����Ͽ����� ��û : ����ϱ�");
		  
	  }else if(line.startsWith("#ModongGivePoint"))
	  {
		  System.out.println("����Ͽ����� ��û : ����Ʈ ����");
		  
	  }else if(line.startsWith("#ModongUseList"))
	  {
		  System.out.println("����Ͽ����� ��û : ��볻�� list");
		  
	  }else if(line.startsWith("#ModongDonationList"))
	  {
		  System.out.println("����Ͽ����� ��û : ��γ��� list");
		  
	  }else if(line.startsWith("#ModongGroupIn"))
	  {
		   System.out.println("����Ͽ����� ��û : �׷쿡 ����");
		 
	  }else if(line.startsWith("#ModongGroupOut"))
	  {
		  System.out.println("����Ͽ����� ��û : �׷쿡�� ������");
		  
	  }else if(line.startsWith("#CcPointAdd"))
	  {
		  System.out.println("���������̿����� ��û : �����ϱ�");
		  
	  }
	  else if(line.startsWith("#CcDonationPoint"))
	  {
		  
		  System.out.println("���������̿����� ��û : ����ϱ�");
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
