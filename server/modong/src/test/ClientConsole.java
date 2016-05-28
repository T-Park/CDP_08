package test;
// This file contains material supporting section 3.7 of the textbook:

import java.awt.image.BufferedImage;

// "Object Oriented Software Engineering" and is issued under the open-source

// license found at www.lloseng.com 
//

//enter 입력시 멈춤 현상 있음
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.imageio.ImageIO;

import client.ChatClient;
import common.ChatIF;

public class ClientConsole implements ChatIF {
	// Class variables *************************************************
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	ChatClient client;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host
	 *            The host to connect to.
	 * @param port
	 *            The port to connect on.
	 */
	public ClientConsole(String host, int port) {
		InitChatClient(host, port);
	}

	// Chat Client 생성
	public void InitChatClient(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
			System.out.println("===== 서버와 연결 성공 =====");
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it
	 * sends it to the client's message handler.
	 */
	public void accept() {
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) {
				message = fromConsole.readLine();

				if (message != null) {

				}

				if (client.isConnected())
					client.handleMessageFromClientUI(message);
				else
					System.out.println("===== 서버와 연결되지 않은 상태 입니다. =====");
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message
	 *            The string to be displayed.
	 */
	public void display(Object message) {
		System.out.println("get message");
		System.out.println(">>" + message.toString());
//		try {
//		String bytes = (String)message;
//		System.out.println("> " + bytes.getBytes());
//		
//		byte[] byteImage = Base64.getDecoder().decode(bytes);
//		InputStream in = new ByteArrayInputStream(byteImage);
//		
//			BufferedImage bufferedImage = ImageIO.read(in);
//			
//			File outputfile = new File("result.png");
//			ImageIO.write(bufferedImage, "png", outputfile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
	}

	public void clientOCSFCommand(String message) {
		String subMsg = message.length() > 1 ? message.substring(1) : "";

		if (subMsg == null) {
			System.out.println("===== 잘못된 명령어 입니다. =====");
		} else if (subMsg.equals("quit")) {
			System.out.println("===== 프로그램을 종료 =====");
			client.quit();
		} else if (subMsg.equals("logoff")) {
			if (client.isConnected()) {
				System.out.println("===== 로그오프 =====");
				client.logout();
			} else
				System.out.println("===== 이미 로그오프한 상태입니다. =====");
		} else if (subMsg.length() > 8 && subMsg.substring(0, 7).equals("sethost")) {
			if (client.isConnected()) {
				System.out.println("===== 연결을 해지해야 가능합니다. =====");
			} else {
				client.setHost(subMsg.substring(8));
				System.out.println("===== 현재 호스트 명 : " + client.getHost() + "=====");
			}
		} else if (subMsg.length() > 8 && subMsg.substring(0, 7).equals("setport")) {
			if (client.isConnected()) {
				System.out.println("===== 연결을 해지해야 가능합니다. =====");
			} else {
				client.setPort(Integer.parseInt(subMsg.substring(8)));
				System.out.println("===== 현재 포트 번호 : " + client.getPort() + "=====");
			}
		} else if (subMsg.equals("login")) {
			if (!client.isConnected()) {
				System.out.println("===== 로그인 =====");
				InitChatClient(client.getHost(), client.getPort());
			} else
				System.out.println("===== 이미 로그인한 상태입니다. =====");
		} else if (subMsg.equals("gethost")) {
			System.out.println("===== 현재 호스트 명 : " + client.getHost() + "=====");
		} else if (subMsg.equals("getport")) {
			System.out.println("===== 현재 포트 번호 : " + client.getPort() + "=====");
		} else {
			System.out.println("===== 잘못된 명령어 입니다. =====");
		}
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0]
	 *            The host to connect to.
	 */
	public static void main(String[] args) {
		String host = "";
		int port = 0; // The port number

		// IP 입력
		try {
			host = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			host = "localhost"; // 127.0.0.1
		}

		// 포트번호 입력
		try {
			port = Integer.parseInt(args[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			port = DEFAULT_PORT; // 5555
		}

		ClientConsole chat = new ClientConsole(host, port);
		chat.accept(); // Wait for console data
	}
}
// End of ConsoleChat class
/*
 * // if(message.substring(0, 1).equals("#")) // { //
 * clientOCSFCommand(message); // continue; // }
 */