/*
 * HandleMessageFromModong.java
 * 
 * 동전모음이로부터 오는 메세지 처리
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
		case messageType.LOGIN: // 로그인
			processLogin(client, tokens);
			break;
		case messageType.LOGOUT: // 로그아웃
			processLogout(client, tokens);
			break;
		case messageType.UPDATEINFO: // 모음이 정보 갱신
			processUpdateInfo(client, tokens);
			break;
		case messageType.GETORGNUM: // 기부단체 목록 수 요청
			processGetOrgNum(client, tokens);
			break;
		case messageType.GETORG: // 기부단체 요청
			processGetOrg(client, tokens);
			break;
		case messageType.GETUSERINFO: // 유저정보 요청
			processGetUserInfo(client, tokens);
			break;
		case messageType.SAVEPOINT: // 포인트 저장
			processSavePoint(client, tokens);
			break;
		case messageType.DONATEPOINT: // 포인트 기부
			processDonatePoint(client, tokens);
			break;
		}
	}

	// #CcLogin%cid
	// login and return acculmulated amount
	public void processLogin(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 로그인");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processLogin 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
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
					// 현재 해당 id로 로그인한 인원이 있음
					accesible = false; // 중복로그인
					msg = "#Error%already logined cid";
					break;
				}
			}
			// 해당 id로 로그인 된 인원이 없을 경우 접속정보를 등록
			if (accesible) {
				client.setInfo("cid", cid); // client 쓰레드에 등록
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
		System.out.println("동전모음이에서의 요청 : 로그아웃");
		// check parameter validity
		if (tokens.length != 1) {
			System.out.println(">> processLogout 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		client.setInfo("cid", null);
		server.sendToMyClient(client, "#Success");
	}

	// #CcUpdateInfo%accumulatedAmount
	// 동전모음이가 적립한 총액 업데이트
	// 동전모음이에서 모은 금액을 파라미터로 받음
	// 성공시 success
	// 실패시 Error 메세지 + 사유 전송
	public void processUpdateInfo(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 동전모음이 금액 업데이터");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processUpdateInfo 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
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
		System.out.println("동전모음이에서의 요청 : 기부단체 수 조회");
		// check parameter validity
		if (tokens.length != 1) {
			System.out.println(">> processGetOrgNum 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
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
		System.out.println("동전모음이에서의 요청 : 기부단체 목록 요청");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGetOrg 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
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
		System.out.println("동전모음이에서의 요청 : 유저정보 요청");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGetUserInfo 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
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
		System.out.println("동전모음이에서의 요청 : 적립하기");
		// check parameter validity
		if (tokens.length != 3) {
			System.out.println(">> processSavePoint 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}
		String barcode = tokens[1];
		int amount = Integer.parseInt(tokens[2]);

		// check validity of barcode
		if (!sqlForCC.checkUserbyParam(client.getConn(), barcode, QueryParameter.BARCODE)
				&& !sqlForCC.checkGroupBarcodebyParam(client.getConn(), barcode, QueryParameter.BARCODE)) { // check group barcode) {
			System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			server.sendToMyClient(client, "#Error%Invalid barcode");
		}
		// check CC was login
		else if (client.getInfo("cid") == null) {
			System.out.println("인증되지 않은 동전모음이입니다.");
			server.sendToMyClient(client, "#Error%Not logined Cc login first");
		} else {
			if (sqlForCC.addPointbyParam(client.getConn(), amount, barcode, QueryParameter.BARCODE)) {
				System.out.println(">> 적립하였습니다.");
				server.sendToMyClient(client, "#Success");
				// log save result
				if (!sqlForCC.logSaveResult(client.getConn(), (int) client.getInfo("cid"), barcode, amount))
					System.out.println("log 실패");
				else
					System.out.println("log 성공");
			}
		}
	}

	// #CcDonatePoint%did%userbarcode%point
	public void processDonatePoint(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 기부하기");
		// check parameter validity
		if (tokens.length != 4) {
			System.out.println(">> processDonatePoint 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}
		
		int did = Integer.parseInt(tokens[1]);
		String barcode = tokens[2];
		int amount = Integer.parseInt(tokens[3]);
		
		// check validity of barcode
		if (!sqlForCC.checkUserbyParam(client.getConn(), barcode, QueryParameter.BARCODE)) {
			System.out.println(">> 존재 하지 않는  user bacode 입니다.: " + barcode);
			server.sendToMyClient(client, "#Error%Invalid barcode");
		} else {
			if (sqlForCC.donatePointbyParam(client.getConn(), amount, barcode, QueryParameter.BARCODE) // update user
					&& sqlForCC.addPointToOrg(client.getConn(), did, amount)) { // update organization
				System.out.println(">> 기부하였습니다.");
				server.sendToMyClient(client, "#Success");
				// log donate result
				if (!sqlForCC.logDonateResult(client.getConn(), did, barcode, amount, QueryParameter.BARCODE))
					System.out.println("log 실패");
				else
					System.out.println("log 성공");
			}
		}		
	}

	//

}
