// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import client.*;
import common.*;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.*;

import GUI.Pos;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	JFrame clientGUI;

	boolean msgFlag = false;

	public JFrame getClientGUI() {
		return clientGUI;
	}

	public void setClientGUI(JFrame clientGUI) {
		this.clientGUI = clientGUI;
	}

	int currentPoint;

	// Constructors ****************************************************

	public int getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(int currentPoint) {
		this.currentPoint = currentPoint;
	}

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host
	 *            The server to connect to.
	 * @param port
	 *            The port number to connect on.
	 * @param clientUI
	 *            The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		System.out.println("ddd2");
		this.clientUI = clientUI;
		openConnection();
		System.out.println("ddd3");
		currentPoint = 0;
	}

	public ChatClient(String host, int port, ChatIF clientUI, JFrame clientGUI) // 쓰지않는
																				// 메소드
			throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
		currentPoint = 0;
		this.clientGUI = clientGUI;
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg
	 *            The message from the server.
	 * @throws Exception
	 */
	@Override
	public void handleMessageFromServer(Object msg) {
		if (msg == null || msg.toString().equals("SERVER MSG> null")) {
			this.connectionClosed();
		}
		clientUI.display(msg.toString());
		

		String line = msg.toString();
		String[] token = line.split("%");


		if (line.startsWith("#"))// 성공 메세지
		{
			setMsgFlag(false);
			String point = token[1];
			// 조회
			currentPoint = Integer.parseInt(point);
			System.out.println(currentPoint);
			setMsgFlag(true);
		}

		System.out.println("exit");
		
	/*	  if(line.startsWith("#"))//성공 메세지 { String point = token[1]; //조회
		  if(line.startsWith("#MdUserIdentify")){//사용자 인증 답이 왓을때 {
		  
		  
		  }else if(line.startsWith("#PosPointAdd"))

	{

	}else if(line.startsWith("#PosPointRemove"))
	{

	}//currentPoint=Integer.parseInt(token[1]);
	//}else// 실패 메세지를 받음. {                           */

	}

	public boolean isMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(boolean msgFlag) {
		this.msgFlag = msgFlag;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message
	 *            The message from the UI.
	 */
	public void handleMessageFromClientUI(String message) {
		try {
			sendToServer(message);
		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		logout();
		System.exit(0);
	}

	// 로그아웃
	public void logout() {
		try {
			sendToServer(null);
			closeConnection();
		} catch (IOException e) {
		}
	}
}
// End of ChatClient class
