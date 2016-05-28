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

public class HandleMessageFromMobile {
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
		public static final String MODIFIY = "ModongModify";
		public static final String DONATION = "ModongDonation";
		public static final String GIVEPOINT = "ModongGivePoint";
		public static final String USELIST = "ModongUseList";

		public static final String DONATIONLIST = "#ModongDonationList";
		public static final String GROUPIN = "#ModongGroupIn";
		public static final String GROUPOUT = "#ModongGroupOut";
		public static final String SYN = "#ModongSyn";
		public static final String MYBARCODE = "#ModongMyBarcode";
		public static final String GROUPBARCODE = "#ModongGroupBarcode";
	}

	public HandleMessageFromMobile(ModongServer server) {
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
			processSYN(client, tokens);
			break;
		case messageType.MYBARCODE: // �� ���ڵ� ��û
			processMyBarcode(client, tokens);
			break;
		case messageType.GROUPBARCODE: // �׷���ڵ� ��û
			processGroupIn(client, tokens);
			break;
		}
	}

	// #ModongLogin%id%pw
	public void processLoin(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : login");
		String id = tokens[1], pw = tokens[2];
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

			server.sendToMyClient(client,
					"#" + md.getUser_id() + "%" + md.getUser_pw() + "%" + md.getUser_name() + "%" + md.getUser_job()
							+ "%" + md.getUser_age() + "%" + md.getUser_tel() + "%" + groupFlag + "%" + groupName);
		} else {
			server.sendToMyClient(client, "login ����");
		}
	}

	// #ModongJoin%id%pw%name%job%age%tel
	public void processJoin(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : ȸ������");
		if (ua.joinModong(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6])) {
			server.sendToMyClient(client, "#true");
		} else {
			server.sendToMyClient(client, "ȸ������ ����");
		}
	}

	// #ModongExistId%id
	public void processExsistedID(ConnectionToClient client, String... tokens) {
		System.out.println("����Ͽ����� ��û : �ߺ� idȮ��");
		if (ua.isThereUser_asId(tokens[1])) {
			server.sendToMyClient(client, "#true");
		} else
			server.sendToMyClient(client, "false");
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

		ua.modifyUser(id, pw, name, job, age, tel);
		server.sendToMyClient(client, "#true");
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
			server.sendToMyClient(client, "#true");
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
			server.sendToMyClient(client, "#true");
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
		System.out.println("����Ͽ����� ��û : ����ȭ ��û");
		ua.updateGroupList();
		ua.updateUserList();

		ModongUser user = ua.findUser_asId(tokens[1]);
		int point = user.getUser_point();
		int groupCode = user.getGroupCode();
		if (groupCode == ua.default_groupCode) {
			String groupName = ua.gcodeTogname(groupCode);
			server.sendToMyClient(client, "#" + point + "%" + "true" + "%" + groupName);
		} else {
			server.sendToMyClient(client, "#" + point + "%" + "false" + "%" + "null");
		}
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