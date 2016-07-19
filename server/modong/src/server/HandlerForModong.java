/*
 * HandleMessageFromMoblie.java
 * 
 * 모바일로부터 오는 메세지 처리
*/
package server;

import java.util.ArrayList;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnz;
import ProblemDomain.LogList;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.GroupUser;
import ProblemDomain.ModongUser;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import ProblemDomain.SyncData;
import db.SQLforModong;
import db.CommonSQL.QueryParameter;
import test.ModongServer;

public class HandlerForModong {
	private ModongServer server;
	private ModongUserAdmin ua;
	private CoinCollectorAdmin cca;
	private DonationOrgnzAdmin doa;
	private StoreAdmin sa;

	private SQLforModong sqlForModong;

	// class for message
	public final class messageType {
		public static final String LOGIN = "#ModongLogin";
		public static final String LOGOUT = "#ModongLogout";
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
		public static final String LOADDONATIONLIST = "#ModongLoadDonationList";
	}

	public HandlerForModong(ModongServer server) {
		// TODO Auto-generated constructor stub
		this.server = server;

		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance();

		sqlForModong = new SQLforModong();
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header

		switch (tokens[0]) {
		case messageType.LOGIN: // 로그인
			processLogin(client, tokens);
			break;
		case messageType.LOGOUT: // 로그아웃
			// processLogin(client, tokens);
			break;
		case messageType.JOIN: // 회원가입
			processJoin(client, tokens);
			break;
		case messageType.EXSISTID: // ID중복검사
			processExistedID(client, tokens);
			break;
		case messageType.MODIFIY: // 회원정보수정
			processModify(client, tokens);
			break;
		case messageType.DONATIONLIST: // 기부목록보기
			processDonationList(client, tokens);
			break;
		case messageType.DONATION: // 기부하기
			processDonation(client, tokens);
			break;
		case messageType.GIVEPOINT: // 선물하기
			processGivePoint(client, tokens);
			break;
		case messageType.USELIST: // 사용목록보기
			processUseList(client, tokens);
			break;
		case messageType.GROUPIN: // 그룹 가입
			processGroupIn(client, tokens);
			break;
		case messageType.GROUPOUT: // 그룹 탈퇴
			processGroupOut(client, tokens);
			break;
		case messageType.SYN: // 동기화요청
			// server.sendToMyClient(client, "login 실패");
			processSYN(client, tokens);
			break;
		case messageType.MYBARCODE: // 내 바코드 요청
			processMyBarcode(client, tokens);
			break;
		case messageType.GROUPBARCODE: // 그룹바코드 요청
			processGroupIn(client, tokens);
			break;

		case messageType.GETUSERINFO:
			processGetUserInfo(client, tokens);
			break;
		case messageType.LOADDONATIONLIST:
			processModongLoadDonationList(client, tokens);
			break;
		default:
			System.out.println("modong 파라미터 에러");
			break;

		}
	}

	// #ModongLogin%id%pw
	public void processLogin(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : login");
		// check parameter validity
		if (tokens.length != 3) {
			System.out.println(">> processLogin 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		int res;
		boolean accesible = true; // to check duplicate login
		String id = tokens[1], pw = tokens[2];
		String msg = "Error"; // message send for client
		ModongUser user;
		/*
		 * if (ua.loginUser(id, pw)) { ModongUser md =
		 * ua.getModongUser_asId(id); int groupCode = md.getGroupCode(); String
		 * groupBarcode, groupName; if (md.getGroupCode() == -1) { groupBarcode
		 * = "0"; groupName = "null"; } else { groupName =
		 * ua.getGroupName(groupCode); groupBarcode =
		 * ua.getGroupBarcode(groupCode); }
		 * 
		 * server.sendToMyClient(client, "#" + md.getUser_id() + "%" +
		 * md.getUser_pw() + "%" + md.getUser_name()+ "%" + md.getUser_bacode()
		 * + "%" + groupCode + "%" + groupName + "%" + groupBarcode); } else {
		 * server.sendToMyClient(client, "login 실패"); }
		 */

		// prevent sevral login in one client
		if (client.getInfo("uid") != null) {
			System.out.println("한 클라이언트에서 여러 로그인 시도");
			server.sendToMyClient(client, "한 클라이언트에서는 하나의 계정으로만 접속할 수 있습니다.");
			return;
		}
		res = sqlForModong.processLogin(client.getConn(), id, pw);
		// success to login
		if (res > 0) {
			Thread[] clientThreadList = server.getClientConnections();
			for (int i = 0; i < clientThreadList.length; i++) {
				if (((ConnectionToClient) clientThreadList[i]).getInfo("uid") != null
						&& ((ConnectionToClient) clientThreadList[i]).getInfo("uid").equals(res)) {
					// 현재 해당 id로 로그인한 인원이 있음
					accesible = false; // 중복로그인
					msg = "이미 로그인 되어있는 아이디입니다";
					break;
				}
			}
			// 해당 id로 로그인 된 인원이 없을 경우 접속정보를 등록
			if (accesible) {
				user = sqlForModong.getUserInfo(client.getConn(), res); // get
																		// userinfo
				System.out.println("사용자정보 조회성공");
				GroupUser gUser = sqlForModong.getGroupInfobyUid(client.getConn(), res);
				System.out.println("그룹정보 조회성공");
				client.setInfo("uid", res); // client 쓰레드에 등록
				if (gUser != null)
					msg = "#" + user.getUser_id() + "%" + user.getUser_pw() + "%" + user.getUser_name() + "%"
							+ user.getUser_bacode() + "%" + user.getGroupCode() + "%" + gUser.getGroup_name() + "%"
							+ gUser.getGroup_barcode();
				else
					msg = "#" + user.getUser_id() + "%" + user.getUser_pw() + "%" + user.getUser_name() + "%"
							+ user.getUser_bacode() + "%" + "null" + "%" + "null" + "%" + "-1";
			}
			System.out.println("msg: " + msg);
			server.sendToMyClient(client, msg); // send result
		} else {
			System.out.println("id/pw Error");
			server.sendToMyClient(client, "id/pw가 올바르지 않습니다."); // send result
		}
	}

	// #ModongJoin%id%pw%name%job%age%tel
	public void processJoin(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 회원가입");
		// check parameter validity
		if (tokens.length != 7) {
			System.out.println(">> processJoin 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		/*
		 * if (ua.joinModong(tokens[1], tokens[2], tokens[3], tokens[4],
		 * Integer.parseInt(tokens[5]), tokens[6])) {
		 * server.sendToMyClient(client, "#signIn"); } else {
		 * server.sendToMyClient(client, "회원가입 실패"); }
		 */

		String id = tokens[1];
		// check id
		if (sqlForModong.checkUserByID(client.getConn(), id)) {
			System.out.println("이미 존재하는 id입니다.");
			server.sendToMyClient(client, "이미 존재하는 id입니다.");
			return;
		}
		String pw = tokens[2];
		String name = tokens[3];
		String job = tokens[4];
		int age = Integer.parseInt(tokens[5]);
		String tel = tokens[6];

		ModongUser user = new ModongUser();

		user.setUser_id(id);
		user.setUser_pw(pw);
		user.setUser_name(name);
		user.setUser_job(job);
		user.setUser_age(age);
		user.setUser_tel(tel);
		// user.setUser_groupCode(ua.default_groupCode);

		if (sqlForModong.processJoin(client.getConn(), user)) {
			System.out.println("가입성공");
			server.sendToMyClient(client, "#signIn");
		} else {
			System.out.println("가입실패");
			server.sendToMyClient(client, "회원가입 실패");
		}

	}

	// #ModongExistId%id
	public void processExistedID(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 중복 id확인");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processExistedID 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String id = tokens[1];
		/*
		 * if (!ua.isThereUser_asId(tokens[1])) { server.sendToMyClient(client,
		 * "#id"); } else server.sendToMyClient(client, "false");
		 */

		if (sqlForModong.checkUserByID(client.getConn(), id)) {
			System.out.println("이미 사용중인 id");
			server.sendToMyClient(client, "false");
		} else {
			System.out.println("사용가능 id");
			server.sendToMyClient(client, "#id");
		}
	}

	// #ModongGetUserInfo%id%pw
	public void processGetUserInfo(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 회원가져오기");

		// check parameter validity
		if (tokens.length != 3) {
			System.out.println(">> processGetUserInfo 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		/*
		 * if(ua.loginUser(id, pw)) { ModongUser user = ua.findUser_asId(id);
		 * server.sendToMyClient(client, "#info%" + id + "%" + pw + "%" +
		 * user.getUser_name() + "%" + user.getUser_job() + "%" +
		 * user.getUser_age()+ "%" + user.getUser_tel()); } else {
		 * server.sendToMyClient(client, "false_password"); }
		 */

		String id = tokens[1];
		String pw = tokens[2];
		ModongUser user;

		user = sqlForModong.getUserInfoByIdPw(client.getConn(), id, pw);
		if (user != null) {
			System.out.println("유저정보 조회성공");
			server.sendToMyClient(client, "#info%" + id + "%" + pw + "%" + user.getUser_name() + "%"
					+ user.getUser_job() + "%" + user.getUser_age() + "%" + user.getUser_tel());
		} else {
			System.out.println("유저정보 조회실패");
			server.sendToMyClient(client, "false_password");
		}

	}

	// #ModongModify%id%pw%name%job%age%tel
	public void processModify(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 회원정보수정");
		// check parameter validity
		if (tokens.length != 7) {
			System.out.println(">> processModify 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String id = tokens[1];
		String pw = tokens[2];
		String name = tokens[3];
		String job = tokens[4];
		int age = Integer.parseInt(tokens[5]);
		String tel = tokens[6];
		ModongUser user = new ModongUser();
		user.setUser_id(id);
		user.setUser_pw(pw);
		user.setUser_name(name);
		user.setUser_job(job);
		user.setUser_age(age);
		user.setUser_tel(tel);

		/*
		 * if(ua.modifyUser(id, pw, name, job, age, tel)) {
		 * server.sendToMyClient(client, "#modify"); }
		 * server.sendToMyClient(client, "fail");
		 */

		// check validity of id
		if (sqlForModong.checkUserByID(client.getConn(), id)) {
			if (sqlForModong.processModify(client.getConn(), user)) {
				System.out.println("수정성공");
				server.sendToMyClient(client, "#modify");
			} else {
				System.out.println("수정실패");
				server.sendToMyClient(client, "fail");
			}
		} else {
			System.out.println("잘못된 id입니다.");
			server.sendToMyClient(client, "invalid id");
		}
	}

	// #ModongDonation%id%gname%point
	// #ModongDonation%id%gid%point -- gid일듯 gid가 아니고 기부단체이름
	public void processDonation(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 기부하기");
		// check parameter validity
		if (tokens.length != 4) {
			System.out.println(">> processModify 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String id = tokens[1];
		String gName = tokens[2];
		int did;
		// gid로
		// did = Integer.parseInt(tokens[2]);
		did = sqlForModong.getdidbyDname(client.getConn(), gName);
		if (did < 0) {
			System.out.println("해당 이름의 단체가 없습니다.");
			server.sendToMyClient(client, "해당 이름의 단체가 없습니다.");
			return;
		}
		int amount = Integer.parseInt(tokens[3]);

		// String id = tokens[1];
		// int did = doa.dNameToDid(tokens[2]);
		// int amount = Integer.parseInt(tokens[3]);
		/*
		 * ModongUser user = ua.findUser_asId(id); int uid = ua.idToUid(id); if
		 * (user.removePoint(point)) { doa.recordDonationPoint(uid, did, point);
		 * // doa.findDonationOrgnz(did).addPoint(point);
		 * server.sendToMyClient(client, "#true"); } else
		 * server.sendToMyClient(client, "포인트가 부족합니다.");
		 */

		// check availability of amount
		if (sqlForModong.withdrawPointbyParam(client.getConn(), amount, id, QueryParameter.ID)) {
			// update info
			if (sqlForModong.donatePointbyParam(client.getConn(), amount, id, QueryParameter.ID) // update
																									// user
					&& sqlForModong.addPointToOrg(client.getConn(), did, amount)) { // update
																					// organization
				System.out.println(">> 기부하였습니다.");
				server.sendToMyClient(client, "#true");
				// log donate result
				if (!sqlForModong.logDonateResult(client.getConn(), did, id, amount, QueryParameter.ID))
					System.out.println("log 실패");
				else
					System.out.println("log 성공");
			} else {
				System.out.println("오류발생");
				server.sendToMyClient(client, "오류가 발생했습니다.");
			}
		} else {
			System.out.println("포인트 부족");
			server.sendToMyClient(client, "포인트가 부족합니다.");
		}
	}

	// #ModongGivePoint%fromId%toId%point
	public void processGivePoint(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 포인트 선물");
		// check parameter validity
		if (tokens.length != 4) {
			System.out.println(">> processGivePoint 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String fromId = tokens[1], toId = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		/*
		 * ModongUser fromUser = ua.findUser_asId(fromId); ModongUser toUser =
		 * ua.findUser_asId(toId);
		 * 
		 * if (fromUser.removePoint(point)) { toUser.addPoint(point);
		 * server.sendToMyClient(client, "#give"); } else
		 * server.sendToMyClient(client, "포인트가 부족합니다.");
		 */
		if (sqlForModong.checkEnoughBalance(client.getConn(), point, fromId, QueryParameter.ID)) {
			if (sqlForModong.withdrawPointbyParam(client.getConn(), point, fromId, QueryParameter.ID)
					&& sqlForModong.addPointbyParam(client.getConn(), point, toId, QueryParameter.ID)) {
				System.out.println("선물 성공");
				server.sendToMyClient(client, "#give");
			} else {
				System.out.println("내부 에러");
				server.sendToMyClient(client, "에러가 발생했습니다.");
			}
		} else {
			System.out.println("포인트가 부족합니다.");
			server.sendToMyClient(client, "포인트가 부족합니다.");
		}

	}

	// #ModongUseList%id
	public void processUseList(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 사용내역 list");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGivePoint 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String id = tokens[1];
		/*
		 * ArrayList<ModongUserAdmin.Item> myList =
		 * ua.getMyUseList(ua.idToUid(id)); server.sendToMyClient(client,
		 * myList);
		 */

		ArrayList<LogList> list = sqlForModong.getUseList(client.getConn(), id, QueryParameter.ID);
		String msg = "#" + list.size();
		server.sendToMyClient(client, msg);
		System.out.println(msg);

		String sendMsg = "";
		for (LogList l : list) {
			String tempSendMsg = l.getWhen() + "@" + l.getWhere() + "@" + l.getPoint();
			System.out.println(tempSendMsg);
			sendMsg = sendMsg + "%" + tempSendMsg;
		}
		server.sendToMyClient(client, sendMsg);
	}

	// #ModongDonationList%id
	public void processDonationList(ConnectionToClient client, String... tokens) {

		System.out.println("모바일에서의 요청 : 기부내역 list");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processDonationList 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String id = tokens[1];
		/*
		 * ArrayList<DonationOrgnzAdmin.dItem> myList =
		 * doa.getMyDonationList(ua.idToUid(id));
		 * 
		 * server.sendToMyClient(client, myList.size());
		 * 
		 * for(int i=0; i< myList.size(); i++) { server.sendToMyClient(client,
		 * myList.get(i).getWhen() + "@"+ myList.get(i).getWhere() + "@" +
		 * myList.get(i).getPoint()); }
		 */
		ArrayList<LogList> list = sqlForModong.getDonationList(client.getConn(), id, QueryParameter.ID);
		String msg = "#" + list.size();
		server.sendToMyClient(client, msg);

		System.out.println(msg);
		String sendMsg = "";
		for (LogList l : list) {
			String tempSendMsg = l.getWhen() + "@" + l.getWhere() + "@" + l.getPoint();
			System.out.println(tempSendMsg);
			sendMsg = sendMsg + "%" + tempSendMsg;
		}
		server.sendToMyClient(client, sendMsg);
	}

	// #ModongGroupIn%3%id%id%id%group
	public void processGroupIn(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 그룹에 들어가기");
		// check parameter validity
		if (tokens.length < 2) {
			System.out.println(">> processGroupIn 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		int partyNum = Integer.parseInt(tokens[1]);

		// check parameter validity
		if (tokens.length != partyNum + 3) {
			System.out.println(">> processDonationList 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String msg = "";

		String[] ids = new String[Integer.parseInt(tokens[1])];
		boolean flag = true; // indicate validity of id's
		for (int i = 0; i < ids.length; i++) {
			if (sqlForModong.checkExistGroup(client.getConn(), tokens[i + 2], QueryParameter.ID)) {
				msg = tokens[i + 2] + "는 이미 그룹이 있습니다.";
				server.sendToMyClient(client, msg);
				flag = false;
				break;
			}
			ids[i] = tokens[i + 2];
		}

		if (flag) {
			int gid = sqlForModong.createNewGroup(client.getConn(), tokens[partyNum + 2]);
			if (gid <= 0) {
				msg = "그룹생성 실패";
				System.out.println(msg);
				server.sendToMyClient(client, msg);
			} else {
				for (String id : ids) {
					if (!sqlForModong.joinUserToGroup(client.getConn(), gid, id, QueryParameter.ID)) {
						msg = id + " 등록에 실패했습니다.";
						System.out.println(msg);
						server.sendToMyClient(client, msg);
						return;
					} else
						System.out.println(id + "를 그룹에 등록했습니다.");
				}
				// Success to group
				System.out.println("그룹에 추가하였습니다.");
				server.sendToMyClient(client, "#group");
			}
		}

		/*
		 * if (ua.groupingUser(ids, tokens[partyNum+2]))
		 * server.sendToMyClient(client, "#group"); else
		 * server.sendToMyClient(client, "실패했습니다.");
		 */

	}

	// #ModongGroupOut%id
	public void processGroupOut(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 그룹에서 나오기");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGroupOut 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		String id = tokens[1];

		/*
		 * ModongUser user = ua.findUser_asId(tokens[1]); user.leaveGroup();
		 * server.sendToMyClient(client, "#group");
		 */
		if (!sqlForModong.checkExistGroup(client.getConn(), id, QueryParameter.ID)) {
			System.out.println("그룹이 없습니다.");
			server.sendToMyClient(client, "그룹이 없습니다.");
		} else if (sqlForModong.processGroupOut(client.getConn(), id, QueryParameter.ID)) {
			System.out.println("그룹탈퇴 성공");
			server.sendToMyClient(client, "#groupout");
		} else {
			System.out.println("그룹탈퇴 실패");
			server.sendToMyClient(client, "그룹탈퇴 실패");
		}
	}

	// #ModongSyn%id rt
	public void processSYN(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 동기화");
		// check parameter validity
		if (tokens.length != 2) {
			System.out.println(">> processGroupOut 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}
		// ua.updateGroupList();
		// ua.updateUserList();

		SyncData syncData;

		String id = tokens[1];

		syncData = sqlForModong.getSyncData(client.getConn(),
				sqlForModong.checkExistGroup(client.getConn(), id, QueryParameter.ID), id, QueryParameter.ID);
		if (syncData != null) {

			String msg;
			// no group
			if (syncData.getGid() == 0) {
				msg = "#" + syncData.getPoint() + "%" + "false" + "%" + "-1" + "%" + "null";
				System.out.println(msg);
				server.sendToMyClient(client, msg);
			} else {
				msg = "#" + syncData.getPoint() + "%" + syncData.getGid() + "%" + syncData.getGroupName() + "%"
						+ syncData.getGroupBarcode();
				System.out.println(msg);
				server.sendToMyClient(client, msg);
			}
		} else {
			System.out.println("동기화 데이터를 가져오는데 실패했습니다.");
			server.sendToMyClient(client, "동기화 데이터를 가져오는데 실패했습니다.");
		}
		/*
		 * if (!ua.isThereUser_asId(id)) { server.sendToMyClient(client,
		 * "fail"); }
		 * 
		 * ModongUser user = ua.findUser_asId(id); int point =
		 * user.getUser_point(); int groupCode = user.getGroupCode();
		 * System.out.println(user); System.out.println(point);
		 * System.out.println(groupCode);
		 * 
		 * if (groupCode == ua.default_groupCode) { String groupName = "null";
		 * String a = new String("#" + point + "%" + groupCode + "%" +
		 * groupName); server.sendToMyClient(client, a); } else {
		 * server.sendToMyClient(client, "#" + point + "%" + "false" + "%" +
		 * "null"); } System.out.println("모바일에서의 요청 : 동기화 요청");
		 */
	}

	// #ModongMyBacode%id
	public void processMyBarcode(ConnectionToClient client, String... tokens) {
		// System.out.println("모바일에서의 요청 : 바코드 요청");
	}

	// #ModongGroupBacode%id
	public void processGroupBarcode(ConnectionToClient client, String... tokens) {
		// System.out.println("모바일에서의 요청 : 그룸 바코드 요청");
	}

	// #modongloaddonationlist
	public void processModongLoadDonationList(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 기부단체 목록 요청");
		// check parameter validity
		if (tokens.length != 1) {
			System.out.println(">> processGroupOut 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}
		
		ArrayList<DonationOrgnz> orgList = sqlForModong.getOrg(client.getConn());
		
		String msg = "#" + orgList.size();
		server.sendToMyClient(client, msg);
		System.out.println(msg);

		String sendMsg = "";
		for (DonationOrgnz dl : orgList) {
			String tempSendMsg = dl.getDonation_name();
			System.out.println(tempSendMsg);
			sendMsg = sendMsg + "%" + tempSendMsg;
		}
		server.sendToMyClient(client, sendMsg);	
	}
}