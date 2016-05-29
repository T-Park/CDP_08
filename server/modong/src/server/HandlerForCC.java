/*
 * HandleMessageFromModong.java
 * 
 * ���������̷κ��� ���� �޼��� ó��
*/

package server;

import java.sql.Connection;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import db.SQLforCC;
import server.HandlerForModong.messageType;
import test.ModongServer;

public class HandlerForCC {
	private ModongServer server;
	private ModongUserAdmin ua;
	private CoinCollectorAdmin cca;
	private DonationOrgnzAdmin doa;
	private StoreAdmin sa;
	
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

		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance();
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header
		switch (tokens[0]) {
		case messageType.LOGIN: // �α���
			processLogin(client, tokens);
			break;
		case messageType.LOGOUT: // �α׾ƿ�
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
	public void processLogin(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �α���");
		server.sendToMyClient(client, "#Error%test error");
	}
	
	// #CcLogout
	public void processLogout(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �α׾ƿ�");
		server.sendToMyClient(client, "#Error%test error");
	}
	
	// #CcUpdateInfo%accumulatedAmount
//	���������̰� ������ �Ѿ� ������Ʈ
//	���������̿��� ���� �ݾ��� �Ķ���ͷ� ����
//	������ success
//	���н� Error �޼��� + ���� ����
	public void processUpdateInfo(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ���������� �ݾ� ��������");
	}
	
	// #CcGetOrgNum
	public void processGetOrgNum(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ��δ�ü �� ��ȸ");
	}
	
	// #CcGetOrg%index
	public void processGetOrg(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ��δ�ü ��� ��û");
	}
	
	// #CcGetUserInfo%userBarcode
	public void processGetUserInfo(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �������� ��û");
	}
	
	// #CcSavePoint%userBarcode%point
	public void processSavePoint(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : �����ϱ�");
	}
	
	// #CcDonatePoint%did%point
	public void processDonatePoint(ConnectionToClient client, String... tokens) {
		System.out.println("���������̿����� ��û : ����ϱ�");
	}
	
	// 
	

}
