/*
 * HandleMessageFromModong.java
 * 
 * 동전모음이로부터 오는 메세지 처리
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
		case messageType.LOGIN: // 로그인
			processLogin(client, tokens);
			break;
		case messageType.LOGOUT: // 로그아웃
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
	public void processLogin(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 로그인");
		server.sendToMyClient(client, "#Error%test error");
	}
	
	// #CcLogout
	public void processLogout(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 로그아웃");
		server.sendToMyClient(client, "#Error%test error");
	}
	
	// #CcUpdateInfo%accumulatedAmount
//	동전모음이가 적립한 총액 업데이트
//	동전모음이에서 모은 금액을 파라미터로 받음
//	성공시 success
//	실패시 Error 메세지 + 사유 전송
	public void processUpdateInfo(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 동전모음이 금액 업데이터");
	}
	
	// #CcGetOrgNum
	public void processGetOrgNum(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 기부단체 수 조회");
	}
	
	// #CcGetOrg%index
	public void processGetOrg(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 기부단체 목록 요청");
	}
	
	// #CcGetUserInfo%userBarcode
	public void processGetUserInfo(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 유저정보 요청");
	}
	
	// #CcSavePoint%userBarcode%point
	public void processSavePoint(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 적립하기");
	}
	
	// #CcDonatePoint%did%point
	public void processDonatePoint(ConnectionToClient client, String... tokens) {
		System.out.println("동전모음이에서의 요청 : 기부하기");
	}
	
	// 
	

}
