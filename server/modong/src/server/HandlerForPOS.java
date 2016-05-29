/*
 * HandleMessageFromPOS.java
 * 
 * ������κ��� ���� �޼��� ó��
*/

package server;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUser;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import server.HandlerForCC.messageType;
import test.ModongServer;

public class HandlerForPOS {
	private ModongServer server;
	private ModongUserAdmin ua;
	private CoinCollectorAdmin cca;
	private DonationOrgnzAdmin doa;
	private StoreAdmin sa;

	// class for message
	public final class messageType {
		public static final String IDENTIFY = "#PosIdentify";
		public static final String POINTADD = "#PosPointAdd";
		public static final String POINTREMOVE = "#PosPointRemove";

	}

	public HandlerForPOS(ModongServer server) {
		this.server = server;
		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance(); // TODO Auto-generated constructor stub
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header
		switch (tokens[0]) {
		case messageType.IDENTIFY: // Pos�� ����
			break;
		case messageType.POINTADD: // point ����
			break;
		case messageType.POINTREMOVE: // point ���
			break;
		}

	}

	// #PosIdentify%32, ������ #true
	// ���н� �޼���
	public void processIdentify(ConnectionToClient client, String... tokens) {
		System.out.println("Pos�⿡���� ��û : pos�� ����� ����");

		if (!sa.findStore(Integer.parseInt(tokens[1]))) {
			System.out.println(">> ���� ���� �ʴ� pid �Դϴ�. pid : " + tokens[1]);
			server.sendToMyClient(client, "���� ���� �ʴ� pid �Դϴ�.");
		} else// ���� o
			server.sendToMyClient(client, "#true");
	}

	// #PosPointAdd%pid%bacode%point ������
	// add�� point rt
	public void processPointAdd(ConnectionToClient client, String... tokens) {
		System.out.println("Pos�⿡���� ��û : ����Ʈ ����");
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String bacode = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		// �׷� ���ڵ� ó��

		if (!ua.isThereUser_asBacode(bacode)) {
			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			// sendToMyClient(client, "���� ���� �ʴ� user bacode �Դϴ�.");
		} else {
			ModongUser tempUser = ua.findUser_asBacode(bacode);
			tempUser.addPoint(point);
			sa.recordAddPoint_asBacode(bacode, pid, point);

			System.out.println(">>" + pid + "�� ������ : " + point + "���� �Ǿ����ϴ�.");
			server.sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
		}

	}

	// #PosPointRemove%pid%bacode%point
	public void processPointRemove(ConnectionToClient client, String... tokens) {
		System.out.println("Pos�⿡���� ��û : ����Ʈ ���");
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String bacode = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		// �׷� ���ڵ� ó��

		if (!ua.isThereUser_asBacode(bacode)) {
			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			server.sendToMyClient(client, "���� ���� �ʴ�  user bacode �Դϴ�.");
		} else {
			ModongUser tempUser = ua.findUser_asBacode(bacode);
			if (tempUser.removePoint(point)) {
				sa.recordAddPoint_asBacode(bacode, pid, point);
				System.out.println(">>" + pid + "�� ������ : " + point + "��� �Ǿ����ϴ�.");
				server.sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
			} else
				server.sendToMyClient(client, "point�� �����մϴ�.");
		}
	}
}
