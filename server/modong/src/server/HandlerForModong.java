/*
 * HandleMessageFromMoblie.java
 * 
 * ����Ϸκ��� ���� �޼��� ó��
*/
package server;

import java.util.ArrayList;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUser;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import test.ModongServer;

public class HandlerForModong {
	private ModongServer server;
	private ModongUserAdmin ua;
	private CoinCollectorAdmin cca;
	private DonationOrgnzAdmin doa;
	private StoreAdmin sa;

	// class for message
	public final class messageType {
		public static final String LOGIN = "#ModongLogin";
		public static final String JOIN = "#ModongJoin";
		public static final String EXSISTID = "#ModongExistId";
		public static final String MODIFIY = "#ModongModify";
		
		public static final String GETUSERINFO = "#ModongGetUserInfo";
		
		public static final String DONATION = "#ModongDonation";
		public static final String GIVEPOINT = "#ModongGivePoint";
		public static final String USELIST = "#ModongUseList";

		public static final String DONATIONLIST = "#ModongDonationList";
		public static final String GROUPIN = "#ModongGroupIn";
		public static final String GROUPOUT = "#ModongGroupOut";
		public static final String SYN = "#ModongSyn";
		public static final String MYBARCODE = "#ModongMyBarcode";
		public static final String GROUPBARCODE = "#ModongGroupBarcode";
	}

	public HandlerForModong(ModongServer server) {
		// TODO Auto-generated constructor stub
		this.server = server;

		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance();
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header
		
		switch (tokens[0]) {
		case messageType.LOGIN: // ȸ������
			processLoin(client, tokens);
			break;
		case messageType.JOIN: // ȸ������
			processJoin(client, tokens);
			break;
		case messageType.EXSISTID: // ID�ߺ��˻�
			processExsistedID(client, tokens);
			break;
		case messageType.MODIFIY: // ȸ����������
			processModify(client, tokens);
			break;
		case messageType.DONATION: // ����ϱ�
			processDonation(client, tokens);
			break;
		case messageType.GIVEPOINT: // �����ϱ�
			processGivePoint(client, tokens);
			break;
		case messageType.USELIST: // ����Ϻ���
			processUseList(client, tokens);
			break;
		case messageType.DONATIONLIST: // ��θ�Ϻ���
			processDonationList(client, tokens);
			break;
		case messageType.GROUPIN: // �׷� ����
			processGroupIn(client, tokens);
			break;
		case messageType.GROUPOUT: // �׷� Ż��
			processGroupOut(client, tokens);
			break;
		case messageType.SYN: // ����ȭ��û
			//server.sendToMyClient(client, "login ����");
			processSYN(client, tokens);
			break;
		case messageType.MYBARCODE: // �� ���ڵ� ��û
			processMyBarcode(client, tokens);
			break;
		case messageType.GROUPBARCODE: // �׷���ڵ� ��û
			processGroupIn(client, tokens);
			break;
			
		case messageType.GETUSERINFO :
			processGetUserInfo(client, tokens);
			
		}
	}

	// #ModongLogin%id%pw
	public void processLoin(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : login");
		String id = tokens[1], pw = tokens[2];

		if (ua.loginUser(id, pw)) {
			ModongUser md = ua.getModongUser_asId(id);
			int groupCode = md.getGroupCode();
			String groupBarcode, groupName;
			if (md.getGroupCode() == -1) {
				groupBarcode = "0";
				groupName = "null";
			} else {
				groupName = ua.getGroupName(groupCode);
				groupBarcode = ua.getGroupBarcode(groupCode);
			}

			server.sendToMyClient(client,
					"#" + md.getUser_id() + "%" + md.getUser_pw() + "%" + md.getUser_name()+ "%" + md.getUser_bacode()
						+ "%" + groupCode + "%" + groupName + "%" + groupBarcode);
		} else {
			server.sendToMyClient(client, "login ����");
		}
	}

	// #ModongJoin%id%pw%name%job%age%tel
	public void processJoin(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ȸ������");
		if (ua.joinModong(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6])) {
			server.sendToMyClient(client, "#signIn");
		} else {
			server.sendToMyClient(client, "ȸ������ ����");
		}
	}

	// #ModongExistId%id
	public void processExsistedID(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : �ߺ� idȮ��");
		if (!ua.isThereUser_asId(tokens[1])) {
			server.sendToMyClient(client, "#id");
		} else
			server.sendToMyClient(client, "false");
	}
	
	// #ModongGetUserInfo%id%pw
	public void processGetUserInfo(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ȸ����������");
		String id = tokens[1];
		String pw = tokens[2];
		
		if(ua.loginUser(id, pw))
		{
			ModongUser user = ua.findUser_asId(id);
			server.sendToMyClient(client, "#info%" + id + "%" + pw + "%" + user.getUser_name()
			+ "%" + user.getUser_job() + "%" + user.getUser_age()+ "%" + user.getUser_tel());
		}
		else
		{			
			server.sendToMyClient(client, "false_password");
		}		
	}

	// #ModongModify%id%pw%name%job%age%tel
	public void processModify(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ȸ����������");
		String id = tokens[1];
		String job = tokens[4];
		String pw = tokens[2];
		int age = Integer.parseInt(tokens[5]);
		String name = tokens[3];
		String tel = tokens[6];

		if(ua.modifyUser(id, pw, name, job, age, tel))
		{
			server.sendToMyClient(client, "#modify");
		}
		server.sendToMyClient(client, "fail");
	}

	// #ModongDonation%id%gname%point
	public void processDonation(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ����ϱ�");
		String id = tokens[1];
		int did = doa.dNameToDid(tokens[2]);
		int point = Integer.parseInt(tokens[3]);

		ModongUser user = ua.findUser_asId(id);
		int uid = ua.idToUid(id);
		if (user.removePoint(point)) {
			doa.recordDonationPoint(uid, did, point);
			doa.findDonationOrgnz(did).addPoint(point);
			server.sendToMyClient(client, "#true");
		} else
			server.sendToMyClient(client, "����Ʈ�� �����մϴ�.");
	}

	// #ModongGivePoint%fromId%toId%point
	public void processGivePoint(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ����Ʈ ����");
		String fromId = tokens[1], toId = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		ModongUser fromUser = ua.findUser_asId(fromId);
		ModongUser toUser = ua.findUser_asId(toId);

		if (fromUser.removePoint(point)) {
			toUser.addPoint(point);
			server.sendToMyClient(client, "#give");
		} else
			server.sendToMyClient(client, "����Ʈ�� �����մϴ�.");

	}

	// #ModongUseList%id
	public void processUseList(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ��볻�� list");
		String id = tokens[1];
		ArrayList<ModongUserAdmin.Item> myList = ua.getMyUseList(ua.idToUid(id));
		server.sendToMyClient(client, myList);
	}

	// #ModongDonationList%id
	public void processDonationList(ConnectionToClient client, String... tokens) {

		System.out.println("����Ͽ����� ��û : ��γ��� list");
		String id = tokens[1];
		ArrayList<DonationOrgnzAdmin.dItem> myList = doa.getMyDonationList(ua.idToUid(id));
		server.sendToMyClient(client, myList);
	}

	// #ModongGroupIn%3%id%id%id%group
	public void processGroupIn(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : �׷쿡 ����");
		String[] ids = new String[Integer.parseInt(tokens[1])];
		int i = 0;
		for (; i < ids.length; i++) {
			ids[i] = tokens[i + 2];
		}
		if (ua.groupingUser(ids, tokens[i]))
			server.sendToMyClient(client, "#group");
		else
			server.sendToMyClient(client, "�����߽��ϴ�.");
	}

	// #ModongGroupOut%id
	public void processGroupOut(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : �׷쿡�� ������");

		ModongUser user = ua.findUser_asId(tokens[1]);
		user.leaveGroup();
		server.sendToMyClient(client, "#true");
	}

	// #ModongSyn%id rt
	public void processSYN(ConnectionToClient client, String... tokens) {
			
		ua.updateGroupList();
		ua.updateUserList();
			
		String id = tokens[1];
		
		if(!ua.isThereUser_asId(id))
		{
			server.sendToMyClient(client, "fail");
		}
			
		ModongUser user = ua.findUser_asId(id);
		int point = user.getUser_point();
		int groupCode = user.getGroupCode();
		System.out.println(user);
		System.out.println(point);
		System.out.println(groupCode);
				
		if (groupCode == ua.default_groupCode) {
			String groupName = "null";
			String a = new String("#" + point + "%" + groupCode + "%" + groupName);
			server.sendToMyClient(client, a);
		} else {
			server.sendToMyClient(client, "#" + point + "%" + "false" + "%" + "null");
		}
		System.out.println("����Ͽ����� ��û : ����ȭ ��û");
	}

	// #ModongMyBacode%id
	public void processMyBarcode(ConnectionToClient client, String... tokens) {
		// System.out.println("����Ͽ����� ��û : ���ڵ� ��û");
	}

	// #ModongGroupBacode%id
	public void processGroupBarcode(ConnectionToClient client, String... tokens) {
		// System.out.println("����Ͽ����� ��û : �׷� ���ڵ� ��û");
	}
}