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
import db.SQLforPOS;
import db.SQLforSaveUseList;
import server.HandlerForCC.messageType;
import test.ModongServer;

public class HandlerForPOS {
	private ModongServer server;
	private ModongUserAdmin ua;
	private CoinCollectorAdmin cca;
	private DonationOrgnzAdmin doa;
	private StoreAdmin sa;
	
	// db accessor
	private SQLforPOS sqlForPos; 
	private SQLforSaveUseList sqlForSaveUseList;
	

	
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
		sqlForPos = new SQLforPOS();
		sqlForSaveUseList = new SQLforSaveUseList();
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header
		System.out.println("hfp ������");
		switch (tokens[0]) {
		case messageType.IDENTIFY: // Pos�� ����
			processIdentify(client, tokens);
			break;
		case messageType.POINTADD: // point ����
			processPointAdd(client, tokens);
			break;
		case messageType.POINTREMOVE: // point ���
			processPointRemove(client, tokens);
			break;
		}
	}

	// #PosIdentify%32, ������ #true
	// ���н� �޼���
	// identify�� �ƴϰ� login�̰� logout�� �ʿ��Ұ� ����
	public void processIdentify(ConnectionToClient client, String... tokens) {
		System.out.println("Pos�⿡���� ��û : pos�� ����� ����");

//		if (!sa.findStore(Integer.parseInt(tokens[1]))) {
//			System.out.println(">> ���� ���� �ʴ� pid �Դϴ�. pid : " + tokens[1]);
//			server.sendToMyClient(client, "���� ���� �ʴ� pid �Դϴ�.");
//		} else// ���� o
//			server.sendToMyClient(client, "#true");
		System.out.println(Integer.parseInt(tokens[1]));
		if ( !sqlForPos.login(client.getConn(), Integer.parseInt(tokens[1])))
		{
			System.out.println(">> ���� ���� �ʴ� pid �Դϴ�. pid : " + tokens[1]);
			server.sendToMyClient(client, "���� ���� �ʴ� pid �Դϴ�.");			
		}
		else
			server.sendToMyClient(client, "#true");
	}

	// #PosPointAdd%pid%bacode%point ������
	// add�� point rt
	public void processPointAdd(ConnectionToClient client, String... tokens) {
		System.out.println("Pos�⿡���� ��û : ����Ʈ ����");
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String barcode = tokens[2];
		int amount = Integer.parseInt(tokens[3]);
		System.out.printf("pid: %d, barcode:%s, point:%d\n", pid, barcode, amount);

		// �׷� ���ڵ� ó��

//		if (!ua.isThereUser_asBacode(bacode)) {
//			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
//			// sendToMyClient(client, "���� ���� �ʴ� user bacode �Դϴ�.");
//		} else {
//			ModongUser tempUser = ua.findUser_asBacode(bacode);
//			tempUser.addPoint(point);
//			sa.recordAddPoint_asBacode(bacode, pid, point);
//
//			System.out.println(">>" + pid + "�� ������ : " + point + "���� �Ǿ����ϴ�.");
//			server.sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
//		}
		if ( !sqlForPos.checkUserbyBarcode(client.getConn(), barcode))
		{
			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			server.sendToMyClient(client, "barcode error");
		}
		else
		{
			if ( !sqlForPos.addPointbyBarcode(client.getConn(), amount, barcode) )
			{
				System.out.println(">> ������ �����߽��ϴ�.");
				server.sendToMyClient(client, "failed to save");
			}
			else
			{
				System.out.println(">> �����Ͽ����ϴ�.");
				server.sendToMyClient(client, "success to save");
				if ( !sqlForSaveUseList.logResult(client.getConn(), pid, barcode, amount, 's'))
					System.out.println("log ����");
				else
					System.out.println("log ����");
			}
		}
	}

	// #PosPointRemove%pid%bacode%point
	public void processPointRemove(ConnectionToClient client, String... tokens) {
		System.out.println("Pos�⿡���� ��û : ����Ʈ ���");
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String barcode = tokens[2];
		int amount = Integer.parseInt(tokens[3]);
		System.out.printf("pid: %d, barcode:%s, point:%d\n", pid, barcode, amount);
//		// �׷� ���ڵ� ó��
//
//		if (!ua.isThereUser_asBacode(barcode)) {
//			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
//			server.sendToMyClient(client, "���� ���� �ʴ�  user bacode �Դϴ�.");
//		} else {
//			ModongUser tempUser = ua.findUser_asBacode(barcode);
//			if (tempUser.removePoint(amount)) {
//				sa.recordAddPoint_asBacode(barcode, pid, amount);
//				System.out.println(">>" + pid + "�� ������ : " + amount + "��� �Ǿ����ϴ�.");
//				server.sendToMyClient(client, "#" + ua.getModongUser_asBacode(barcode).getUser_point());
//			} else
//				server.sendToMyClient(client, "point�� �����մϴ�.");
//		}
		if ( !sqlForPos.checkUserbyBarcode(client.getConn(), barcode))
		{
			System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
			server.sendToMyClient(client, "barcode error");
		}
		else
		{
			if ( !sqlForPos.withdrawPointbyBarcode(client.getConn(), amount, barcode) )
			{
				System.out.println(">> �ܾ��� �����մϴ�.");
				server.sendToMyClient(client, "failed to use");
			}
			else
			{
				System.out.println(">> ����Ͽ����ϴ�.");
				server.sendToMyClient(client, "success to use");
				if ( !sqlForSaveUseList.logResult(client.getConn(), pid, barcode, amount, 'u'))
					System.out.println("log ����");
				else
					System.out.println("log ����");
			}
		}		
	}
	
	
}
