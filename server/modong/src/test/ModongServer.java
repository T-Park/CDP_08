package test;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUser;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import server.AbstractServer;
import server.ConnectionToClient;
import util.BarcodeCreator;

public class ModongServer extends AbstractServer {
	ModongUserAdmin ua;
	CoinCollectorAdmin cca;
	DonationOrgnzAdmin doa;
	StoreAdmin sa;
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port
	 *            The port number to connect on.
	 */
	public ModongServer(int port) {
		super(port);
		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance();

	}

	// Instance methods ************************************************

	public void handleMessageFromClient // ������ #msg, ���н� ���л���
	(Object msg, ConnectionToClient client) {
		String line = (String) msg;
		String[] token = line.split("%");
		if (line.startsWith("#PosIdentify"))// ex) #PosIdentify%32, ������ #true
											// ���н� �޼���
		{
			System.out.println("Pos�⿡���� ��û : pos�� ����� ����");

			if (!sa.findStore(Integer.parseInt(token[1]))) {
				System.out.println(">> ���� ���� �ʴ� pid �Դϴ�. pid : " + token[1]);
				sendToMyClient(client, "���� ���� �ʴ� pid �Դϴ�.");
			} else// ���� o
				sendToMyClient(client, "#true");
		}
		if (line.startsWith("#PosPointAdd"))// #PosPointAdd%pid%bacode%point ������
											// add�� point rt
		{
			System.out.println("Pos�⿡���� ��û : ����Ʈ ����");
			// String[] token = line.split("%");
			int pid = Integer.parseInt(token[1]);
			String bacode = token[2];
			int point = Integer.parseInt(token[3]);

			// �׷� ���ڵ� ó��

			if (!ua.isThereUser_asBacode(bacode)) {
				System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
				// sendToMyClient(client, "���� ���� �ʴ� user bacode �Դϴ�.");
			} else {
				ModongUser tempUser = ua.findUser_asBacode(bacode);
				tempUser.addPoint(point);
				sa.recordAddPoint_asBacode(bacode, pid, point);

				System.out.println(">>" + pid + "�� ������ : " + point + "���� �Ǿ����ϴ�.");
				sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
			}

		} else if (line.startsWith("#PosPointRemove"))// #PosPointRemove%pid%bacode%point
														// ������ remove�� point rt
		{
			System.out.println("Pos�⿡���� ��û : ����Ʈ ���");
			// String[] token = line.split("%");
			int pid = Integer.parseInt(token[1]);
			String bacode = token[2];
			int point = Integer.parseInt(token[3]);

			// �׷� ���ڵ� ó��

			if (!ua.isThereUser_asBacode(bacode)) {
				System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
				sendToMyClient(client, "���� ���� �ʴ�  user bacode �Դϴ�.");
			} else {
				ModongUser tempUser = ua.findUser_asBacode(bacode);
				if (tempUser.removePoint(point)) {
					sa.recordAddPoint_asBacode(bacode, pid, point);
					System.out.println(">>" + pid + "�� ������ : " + point + "��� �Ǿ����ϴ�.");
					sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
				} else
					sendToMyClient(client, "point�� �����մϴ�.");
			}
		} else if (line.startsWith("#MdUserIdentify"))// #MdUserIdentify%bacode
														// ������user point ����
		{// pos/cc��
			System.out.println("����� �ĺ� ��û");
			// String[] token = line.split("%");
			String bacode = token[1];

			// �׷� ���ڵ� ó��

			if (!ua.isThereUser_asBacode(bacode)) {
				System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
				sendToMyClient(client, "���� ���� �ʴ�user bacode �Դϴ�.");
			} else {
				sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
			}

		}
		// ****************************�����
		// ��**************************************//
		else if (line.startsWith("#ModongLogin"))// #ModongLogin%id%pw
		{
			System.out.println("����Ͽ����� ��û : login");
			String id = token[1], pw = token[2];
			if (ua.loginUser(id, pw)) {
				ModongUser md = ua.getModongUser_asId(id);
				String groupFlag, groupName;
				if (md.getGroupCode() == -1) {
					groupFlag = "0";
					groupName = "null";
				} else {
					groupFlag = "1";
					groupName = ua.getGroupName(md.getGroupCode());
				}

				sendToMyClient(client,
						"#" + md.getUser_id() + "%" + md.getUser_pw() + "%" + md.getUser_name() + "%" + md.getUser_job()
								+ "%" + md.getUser_age() + "%" + md.getUser_tel() + "%" + groupFlag + "%" + groupName);
			} else {
				sendToMyClient(client, "login ����");
			}
		} else if (line.startsWith("#ModongJoin"))// #ModongJoin%id%pw%name%job%age%tel
		{
			System.out.println("����Ͽ����� ��û : ȸ������");
			if (ua.joinModong(token[1], token[2], token[3], token[4], Integer.parseInt(token[5]), token[6])) {
				sendToMyClient(client, "#true");
			} else {
				sendToMyClient(client, "ȸ������ ����");
			}

		} else if (line.startsWith("#ModongExistId"))// #ModongExistId%id
		{
			System.out.println("����Ͽ����� ��û : �ߺ� idȮ��");
			if (ua.isThereUser_asId(token[1])) {
				sendToMyClient(client, "#true");
			} else
				sendToMyClient(client, "false");

		} else if (line.startsWith("#ModongModify"))// #ModongModify%id%pw%name%job%age%tel
		{
			System.out.println("����Ͽ����� ��û : ȸ����������");
			String id = token[1];
			String job = token[4];
			String pw = token[2];
			int age = Integer.parseInt(token[5]);
			String name = token[3];
			String tel = token[6];

			ua.modifyUser(id, pw, name, job, age, tel);
			sendToMyClient(client, "#true");

		} else if (line.startsWith("#ModongDonation")) // #ModongDonation%id%gname%point
		{
			System.out.println("����Ͽ����� ��û : ����ϱ�");
			String id = token[1];
			int did = doa.dNameToDid(token[2]);
			int point = Integer.parseInt(token[3]);

			ModongUser user = ua.findUser_asId(id);
			int uid = ua.idToUid(id);
			if (user.removePoint(point)) {
				doa.recordDonationPoint(uid, did, point);
				doa.findDonationOrgnz(did).addPoint(point);
				sendToMyClient(client, "#true");
			} else
				sendToMyClient(client, "����Ʈ�� �����մϴ�.");

		} else if (line.startsWith("#ModongGivePoint"))// #ModongGivePoint%fromId%toId%point
		{
			System.out.println("����Ͽ����� ��û : ����Ʈ ����");
			String fromId = token[1], toId = token[2];
			int point = Integer.parseInt(token[3]);

			ModongUser fromUser = ua.findUser_asId(fromId);
			ModongUser toUser = ua.findUser_asId(toId);

			if (fromUser.removePoint(point)) {
				toUser.addPoint(point);
				sendToMyClient(client, "#true");
			} else
				sendToMyClient(client, "����Ʈ�� �����մϴ�.");

		} else if (line.startsWith("#ModongUseList"))// #ModongUseList%id
		{
			System.out.println("����Ͽ����� ��û : ��볻�� list");
			String id = token[1];
			ArrayList<ModongUserAdmin.Item> myList = ua.getMyUseList(ua.idToUid(id));
			sendToMyClient(client, myList);

		} else if (line.startsWith("#ModongDonationList"))// #ModongDonationList%id
		{
			System.out.println("����Ͽ����� ��û : ��γ��� list");
			String id = token[1];
			ArrayList<DonationOrgnzAdmin.dItem> myList = doa.getMyDonationList(ua.idToUid(id));
			sendToMyClient(client, myList);

		} else if (line.startsWith("#ModongGroupIn"))// #ModongGroupIn%3%id%id%id%group
														// name
		{
			System.out.println("����Ͽ����� ��û : �׷쿡 ����");
			String[] ids = new String[Integer.parseInt(token[1])];
			int i = 0;
			for (; i < ids.length; i++) {
				ids[i] = token[i + 2];
			}
			if (ua.groupingUser(ids, token[i]))
				sendToMyClient(client, "#true");
			else
				sendToMyClient(client, "�����߽��ϴ�.");

		} else if (line.startsWith("#ModongGroupOut"))// #ModongGroupOut%id
		{
			System.out.println("����Ͽ����� ��û : �׷쿡�� ������");

			ModongUser user = ua.findUser_asId(token[1]);
			user.leaveGroup();
			sendToMyClient(client, "#true");

		} else if (line.startsWith("#ModongSyn"))// #ModongSyn%id rt
													// point%groupflag%groupName
		{
			System.out.println("����Ͽ����� ��û : ����ȭ ��û");
			ua.updateGroupList();
			ua.updateUserList();

			ModongUser user = ua.findUser_asId(token[1]);
			int point = user.getUser_point();
			int groupCode = user.getGroupCode();
			if (groupCode == ua.default_groupCode) {
				String groupName = ua.gcodeTogname(groupCode);
				sendToMyClient(client, "#" + point + "%" + "true" + "%" + groupName);
			} else {
				sendToMyClient(client, "#" + point + "%" + "false" + "%" + "null");
			}
		} else if (line.startsWith("#ModongMyBacode"))// #ModongMyBacode%id
		{
			System.out.println("����Ͽ����� ��û : ���ڵ� ��û");

		} else if (line.startsWith("#ModongGroupBacode"))// #ModongGroupBacode%id
		{
			System.out.println("����Ͽ����� ��û : �׷� ���ڵ� ��û");

		}
		// ****************************����
		// ������**************************************//
		/*
		 * 2016.5.23 modified by kth
		 * 
		 * 2016.5.25 add function
		 * 
		 * 
		 */
		else if (line.startsWith("#CcLogin"))// #CcLogin%cid
		{
			System.out.println("���������̿����� ��û : �α���");
			sendToMyClient(client, "#Error%test error");
		} else if (line.startsWith("#CcLogout"))// #CcPointAdd%cid%point
		{
			System.out.println("���������̿����� ��û : �α׾ƿ�");

		} 
//		���������̰� ������ �Ѿ� ������Ʈ
//		���������̿��� ���� �ݾ��� �Ķ���ͷ� ����
//		������ success
//		���н� Error �޼��� + ���� ����
		 else if (line.startsWith("#CcUpdateInfo"))// #CcUpdateInfo%accumulatedAmount
			{
				System.out.println("���������̿����� ��û : ���������� �ݾ� ��������");

			}
//		��δ�ü�� ���� �䱸�ϰ� ������
		 else if (line.startsWith("#CcGetOrgNum"))// #CcGetOrgNum
			{
				System.out.println("���������̿����� ��û : ��δ�ü �� ��ȸ");

			}
		// ��δ�ü ��� ��û index�� ��û��
//		�ش� �ε����� ��δ�ü�� ��ȯ��
//		��ȯ���� : did, name, point, tel, type
		 else if (line.startsWith("#CcGetOrg"))// #CcGetOrg%index
			{
				System.out.println("���������̿����� ��û : ��δ�ü ��� ��û");

			}
//		������ ������ ��û, ���ڵ带 �Ķ���ͷ� ����
		
		 else if (line.startsWith("#CcGetUserInfo"))// #CcGetUserInfo%userBarcode
			{
				System.out.println("���������̿����� ��û : �������� ��û");
			} 		
//		�������� ����Ʈ ����
//		�Ķ���ͷ� ���ڵ�, ������ ����Ʈ �縦 ����
//		������ success
//		���н� Error �޼��� + ���� ����
		 else if (line.startsWith("#CcSavePoint"))// #CcSavePoint%userBarcode%point
		{
			System.out.println("���������̿����� ��û : �����ϱ�");

		} 
//		��δ�ü�� ����Ʈ ����
//		�Ķ���ͷ� ��δ�ü did, ������ ����Ʈ ���� ����
//		������ success
//		���н� Error �޼��� + ���� ����		 
		 else if (line.startsWith("#CcDonatePoint"))// #CcDonatePoint%did%point
		{
			System.out.println("���������̿����� ��û : ����ϱ�");
		}
		/* teset */
		else if (line.startsWith("#test")) {
			try {
				// TODO Auto-generated method stub
				BarcodeCreator barcodeCreator = new BarcodeCreator();
				
				// Create image file
				barcodeCreator.makeImageFile("201605061111", "test");				
				
				File f = new File("test.png");
				BufferedImage bufferedImage = ImageIO.read(f);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				ImageIO.write(bufferedImage, "png", baos);
				byte[] byteImage = baos.toByteArray();

				
				sendToMyClient(client, Base64.getEncoder().encodeToString(byteImage));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			System.out.println("Message received: " + msg + " from " + client);
			this.sendToAllClients(msg);
		}
	}

	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	public void clientConnected(ConnectionToClient client) {

		System.out.print("New Client connected !!! (IP :");
		System.out.print(client.getInetAddress().getHostAddress());
		System.out.println(")");
	}

	public void sendToMyClient(ConnectionToClient client, Object msg) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Class methods ***************************************************

	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		ModongServer sv = new ModongServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}

		ServerConsole sc = new ServerConsole(sv);
		sc.accept();
	}
}
// End of EchoServer class
