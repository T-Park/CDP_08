package main;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
//


//enter �Է½� ���� ���� ����
import java.io.*;

import GUI.demo;
import client.*;
import common.*;

public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
	  InitChatClient(host,port);
  }
  
  // Chat Client ����
  public void InitChatClient(String host, int port)
  {
		try 
	    {
			System.out.println("ddd");
	      client= new ChatClient(host, port, this);
	      System.out.println("===== ������ ���� ���� =====");
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    }	  
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

        //message = fromConsole.readLine();
        
        if(client.isConnected())
        {
        	demo a = new demo(client);
            a.setVisible(true);
            System.out.println("����ƾ��");
        	//client.handleMessageFromClientUI(message);
        }      	
        else
    		System.out.println("===== ������ ������� ���� ���� �Դϴ�. =====");	        	
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  public void clientOCSFCommand(String message)
  {
	  String subMsg = message.length() > 1  ? message.substring(1) : "";
  	
  	if(subMsg == null)
  	{
  		System.out.println("===== �߸��� ��ɾ� �Դϴ�. =====");	        		
  	}	        	
  	else if(subMsg.equals("quit"))
  	{
  		System.out.println("===== ���α׷��� ���� =====");
  		client.quit();
  	}
  	else if(subMsg.equals("logoff"))
  	{
  		if(client.isConnected())
  		{
      		System.out.println("===== �α׿��� =====");
      		client.logout();
  		}
  		else
      		System.out.println("===== �̹� �α׿����� �����Դϴ�. =====");
  	}
  	else if(subMsg.length() > 8 && subMsg.substring(0,7).equals("sethost"))
  	{
  		if(client.isConnected())
  		{
      		System.out.println("===== ������ �����ؾ� �����մϴ�. =====");
  		}
  		else
  		{
  			client.setHost(subMsg.substring(8));
      		System.out.println("===== ���� ȣ��Ʈ �� : " + client.getHost() + "=====");
  		}
  	}
  	else if(subMsg.length() > 8 && subMsg.substring(0,7).equals("setport"))
  	{
  		if(client.isConnected())
  		{
      		System.out.println("===== ������ �����ؾ� �����մϴ�. =====");
  		}
  		else
  		{
  			client.setPort(Integer.parseInt(subMsg.substring(8)));
      		System.out.println("===== ���� ��Ʈ ��ȣ : " + client.getPort() + "=====");	
  		}
  	}
  	else if(subMsg.equals("login"))
  	{
  		if(!client.isConnected())
  		{
      		System.out.println("===== �α��� =====");
      		InitChatClient(client.getHost(),client.getPort());
  		}
  		else
      		System.out.println("===== �̹� �α����� �����Դϴ�. =====");      		
  	}
  	else if(subMsg.equals("gethost"))
  	{
  		System.out.println("===== ���� ȣ��Ʈ �� : " + client.getHost() + "=====");	        		
  	}
  	else if(subMsg.equals("getport"))
  	{
  		System.out.println("===== ���� ��Ʈ ��ȣ : " + client.getPort() + "=====");	        		
  	}
  	else
  	{
  		System.out.println("===== �߸��� ��ɾ� �Դϴ�. =====");	        		
  	}	
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;  //The port number

    // IP �Է�    
    try
    {
      host = "20.20.3.188";
      host = "127.0.0.1";
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "20.20.3.188";	// 127.0.0.1
     
    }
    
    // ��Ʈ��ȣ �Է�
    try
    {
      port = Integer.parseInt(args[1]);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      port = 5555;	// 5555
    }

    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
/*
//	        if(message.substring(0, 1).equals("#"))
//	        {        	        	
//        		clientOCSFCommand(message);
//        		continue;
//	        }
*/