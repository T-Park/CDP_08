/*
 * HandleMessageFromModong.java
 * 
 * ���������̷κ��� ���� �޼��� ó��
*/

package server;

import java.sql.Connection;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnz;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUser;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import db.SQLforCC;
import db.CommonSQL.QueryParameter;
import server.HandlerForModong.messageType;
import test.ModongServer;

public class HandlerForCC {
	private ModongServer server;
	/*
	 * private ModongUserAdmin ua; private CoinCollectorAdmin cca; private
	 * DonationOrgnzAdmin doa; private StoreAdmin sa;
	 */

	private SQLforCC sqlForCC; // db function

	// class for message
	public final class messageType {
		public static final String LOGIN = "#CcLogin";
		public static final String LOGOUT = "#CcLogout";
		public static final String UPDATEINFO = "#CcUpdateInfo";
		public static final String GETORGNUM = "#CcGetOrgNum";
		public static final String GETORG = "#CcGetOrg";
		public static final String GETUSERINFO = "#CcGetUserInfo";
		public static final String SAVEPOINT = "#CcSavePoint";
		public static final String DONATEPOINT = "#CcDonatePoint";

	}

	public HandlerForCC(ModongServer server) {
		// TODO Auto-generated constructor stub
		this.server = server;

		/*
		 * ua = ModongUserAdmin.getInstance(); cca =
		 * CoinCollectorAdmin.getInstance(); doa =
		 * DonationOrgnzAdmin.getInstance(); sa = StoreAdmin.getInstance();
		 */

		sqlForCC = new SQLforCC();
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header
		switch (tokens[0]) {
		case messageType.LOGIN: // �α���
			processLogin(client, tokens);
			break;
		case messageType.LOGOUT: // �α׾ƿ�
			processLogout(client, tokens);
			break;
		case messageType.UPDATEINFO: // ������ ���� ����
			processUpdateInfo(client, tokens);
			break;
		case messageType.GETORGNUM: // ��δ�ü ��� �� ��û
			processGetOrgNum(client, tokens);
			break;
		case messageType.GETORG: // ��δ�ü ��û
			processGetOrg(client, tokens);
			break;
		case messageType.GETUSERINFO: // �������� ��û
			processGetUserInfo(client, tokens);
			break;
		case messageType.SAVEPOINT: // ����Ʈ ����
			processSavePoint(client, tokens);
			break;
		case messageType.DONATEPOINT: // ����Ʈ ���
			processDonatePoint(client, tokens);
			break;
		}
	}

	// #CcLogin%cid
	// login and return acculmulated amount
	public void processLogin(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �α���");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processLogin �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}

		boolean accesible = true;
		int cid = Integer.parseInt(tokens[1]);
		int amount;
		String msg = "";

		// check aquired cid is valid
		if (sqlForCC.checkCCbyCid(client.getConn(), cid)) {
			Thread[] clientThreadList = server.getClientConnections();
			for (int i = 0; i < clientThreadList.length; i++) {
				if (((ConnectionToClient) clientThreadList[i]).getInfo("cid") != null
						&& ((ConnectionToClient) clientThreadList[i]).getInfo("cid").equals(cid)) {
					// ���� �ش� id�� �α����� �ο��� ����
					accesible = false; // �ߺ��α���
					msg = "#Error%already logined cid";
					break;
				}
			}
			// �ش� id�� �α��� �� �ο��� ���� ��� ���������� ���
			if (accesible) {
				client.setInfo("cid", cid); // client �����忡 ���
				amount = sqlForCC.getCCAccumulatedAmount(client.getConn(), cid);
				msg = "#Success%" + amount;
			}
		}
		// no exsisted cid
		else
			msg = "#Error%no existed cid";
		server.sendToMyClient(client, msg);
	}

	// #CcLogout
	public void processLogout(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �α׾ƿ�");
		// check parameter validity
		if (tokens.length != 1) {
			System.out.println(">> processLogout �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}

		client.setInfo("cid", null);
		server.sendToMyClient(client, "#Success");
	}

	// #CcUpdateInfo%accumulatedAmount
	// ���������̰� ������ �Ѿ� ������Ʈ
	// ���������̿��� ���� �ݾ��� �Ķ���ͷ� ����
	// ������ success
	// ���н� Error �޼��� + ���� ����
	public void processUpdateInfo(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ���������� �ݾ� ��������");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processUpdateInfo �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}

		int cid; // get cid from logined data
		int amount = Integer.parseInt(tokens[1]);

		// check cid
		if (client.getInfo("cid") == null) {
			server.sendToMyClient(client, "#Error%Not logined, login first!");
		} else {
			if (sqlForCC.updateCcAmount(client.getConn(), (int) client.getInfo("cid"), amount))
				server.sendToMyClient(client, "#Success"); // success to update
			else
				server.sendToMyClient(client, "#Error%Update Failed"); // fail
																		// to
																		// update
		}

	}

	// #CcGetOrgNum
	public void processGetOrgNum(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ��δ�ü �� ��ȸ");
		// check parameter validity
		if (tokens.length != 1) {
			System.out.println(">> processGetOrgNum �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}
		int res = sqlForCC.getOrgNum(client.getConn());

		if (res > 0) {
			server.sendToMyClient(client, "#Success%" + res); // success to
																// search
		} else {
			server.sendToMyClient(client, "#Error%Failed to search result"); // failed
																				// to
																				// search
		}
	}

	// #CcGetOrg%index
	public void processGetOrg(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ��δ�ü ��� ��û");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGetOrg �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}

		DonationOrgnz org;

		int index = Integer.parseInt(tokens[1]);

		org = sqlForCC.getOrg(client.getConn(), index);

		if (org != null) {
			// did, name, point, tel, type
			String msg = "#Success%" + org.getDonation_id() + "%" + org.getDonation_name() + "%"
					+ org.getDonation_point() + "%" + org.getDonation_tel() + "%" + org.getDonation_type();
			server.sendToMyClient(client, msg);
		} else
			server.sendToMyClient(client, "#Error%Fail to get data");

	}

	// #CcGetUserInfo%userBarcode
	public void processGetUserInfo(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �������� ��û");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGetUserInfo �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}
		
		ModongUser user;
		String barcode = tokens[1];

		user = sqlForCC.getUserInfo(client.getConn(), barcode);
		if (user != null) {
			// uid, id, name, point, donated_point, phone_number
			String msg = "#Success%" + user.getUid() + "%" + user.getUser_id() + "%" + user.getUser_name() + "%"
					+ user.getUser_point() + "%" + user.getUser_donatePoint() + "%" + user.getUser_tel();
			server.sendToMyClient(client, msg);
		} else
			server.sendToMyClient(client, "#Error%Failed to access user info"); // fail
																				// to
																				// get
																				// info
	}

	// #CcSavePoint%userBarcode%point
	public void processSavePoint(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �����ϱ�");
		// check parameter validity
		if (tokens.length != 3) {
			System.out.println(">> processSavePoint �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}
		String barcode = tokens[1];
		int amount = Integer.parseInt(tokens[2]);

		// check validity of barcode
		if (!sqlForCC.checkUserbyParam(client.getConn(), barcode, QueryParameter.BARCODE)
				&& !sqlForCC.checkGroupBarcodebyParam(client.getConn(), barcode, QueryParameter.BARCODE)) { // check group barcode) {
			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			server.sendToMyClient(client, "#Error%Invalid barcode");
		}
		// check CC was login
		else if (client.getInfo("cid") == null) {
			System.out.println("�������� ���� �����������Դϴ�.");
			server.sendToMyClient(client, "#Error%Not logined Cc login first");
		} else {
			if (sqlForCC.addPointbyParam(client.getConn(), amount, barcode, QueryParameter.BARCODE)) {
				System.out.println(">> �����Ͽ����ϴ�.");
				server.sendToMyClient(client, "#Success");
				// log save result
				if (!sqlForCC.logSaveResult(client.getConn(), (int) client.getInfo("cid"), barcode, amount))
					System.out.println("log ����");
				else
					System.out.println("log ����");
			}
		}
	}

	// #CcDonatePoint%did%userbarcode%point
	public void processDonatePoint(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ����ϱ�");
		// check parameter validity
		if (tokens.length != 4) {
			System.out.println(">> processDonatePoint �Ķ���� ����");
			server.sendToMyClient(client, "�Ķ���� �����Դϴ�.");
			return;
		}
		
		int did = Integer.parseInt(tokens[1]);
		String barcode = tokens[2];
		int amount = Integer.parseInt(tokens[3]);
		
		// check validity of barcode
		if (!sqlForCC.checkUserbyParam(client.getConn(), barcode, QueryParameter.BARCODE)) {
			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.: " + barcode);
			server.sendToMyClient(client, "#Error%Invalid barcode");
		} else {
			if (sqlForCC.donatePointbyParam(client.getConn(), amount, barcode, QueryParameter.BARCODE) // update user
					&& sqlForCC.addPointToOrg(client.getConn(), did, amount)) { // update organization
				System.out.println(">> ����Ͽ����ϴ�.");
				server.sendToMyClient(client, "#Success");
				// log donate result
				if (!sqlForCC.logDonateResult(client.getConn(), did, barcode, amount, QueryParameter.BARCODE))
					System.out.println("log ����");
				else
					System.out.println("log ����");
			}
		}		
	}

	//

}
