/*
 * HandleMessageFromMoblie.java
 * 
 * 모바일로부터 오는 메세지 처리
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
		case messageType.LOGIN: // 회원가입
			processLoin(client, tokens);
			break;
		case messageType.JOIN: // 회원가입
			processJoin(client, tokens);
			break;
		case messageType.EXSISTID: // ID중복검사
			processExsistedID(client, tokens);
			break;
		case messageType.MODIFIY: // 회원정보수정
			processModify(client, tokens);
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
		case messageType.DONATIONLIST: // 기부목록보기
			processDonationList(client, tokens);
			break;
		case messageType.GROUPIN: // 그룹 가입
			processGroupIn(client, tokens);
			break;
		case messageType.GROUPOUT: // 그룹 탈퇴
			processGroupOut(client, tokens);
			break;
		case messageType.SYN: // 동기화요청
			processSYN(client, tokens);
			break;
		case messageType.MYBARCODE: // 내 바코드 요청
			processMyBarcode(client, tokens);
			break;
		case messageType.GROUPBARCODE: // 그룹바코드 요청
			processGroupIn(client, tokens);
			break;
		}
	}

	// #ModongLogin%id%pw
	public void processLoin(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : login");
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
			server.sendToMyClient(client, "login 실패");
		}
	}

	// #ModongJoin%id%pw%name%job%age%tel
	public void processJoin(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 회원가입");
		if (ua.joinModong(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6])) {
			server.sendToMyClient(client, "#true");
		} else {
			server.sendToMyClient(client, "회원가입 실패");
		}
	}

	// #ModongExistId%id
	public void processExsistedID(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 중복 id확인");
		if (ua.isThereUser_asId(tokens[1])) {
			server.sendToMyClient(client, "#true");
		} else
			server.sendToMyClient(client, "false");
	}

	// #ModongModify%id%pw%name%job%age%tel
	public void processModify(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 회원정보수정");
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
		System.out.println("모바일에서의 요청 : 기부하기");
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
			server.sendToMyClient(client, "포인트가 부족합니다.");
	}

	// #ModongGivePoint%fromId%toId%point
	public void processGivePoint(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 포인트 선물");
		String fromId = tokens[1], toId = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		ModongUser fromUser = ua.findUser_asId(fromId);
		ModongUser toUser = ua.findUser_asId(toId);

		if (fromUser.removePoint(point)) {
			toUser.addPoint(point);
			server.sendToMyClient(client, "#true");
		} else
			server.sendToMyClient(client, "포인트가 부족합니다.");

	}

	// #ModongUseList%id
	public void processUseList(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 사용내열 list");
		String id = tokens[1];
		ArrayList<ModongUserAdmin.Item> myList = ua.getMyUseList(ua.idToUid(id));
		server.sendToMyClient(client, myList);
	}

	// #ModongDonationList%id
	public void processDonationList(ConnectionToClient client, String... tokens) {

		System.out.println("모바일에서의 요청 : 기부내역 list");
		String id = tokens[1];
		ArrayList<DonationOrgnzAdmin.dItem> myList = doa.getMyDonationList(ua.idToUid(id));
		server.sendToMyClient(client, myList);
	}

	// #ModongGroupIn%3%id%id%id%group
	public void processGroupIn(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 그룹에 들어가기");
		String[] ids = new String[Integer.parseInt(tokens[1])];
		int i = 0;
		for (; i < ids.length; i++) {
			ids[i] = tokens[i + 2];
		}
		if (ua.groupingUser(ids, tokens[i]))
			server.sendToMyClient(client, "#true");
		else
			server.sendToMyClient(client, "실패했습니다.");
	}

	// #ModongGroupOut%id
	public void processGroupOut(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 그룹에서 나오기");

		ModongUser user = ua.findUser_asId(tokens[1]);
		user.leaveGroup();
		server.sendToMyClient(client, "#true");
	}

	// #ModongSyn%id rt
	public void processSYN(ConnectionToClient client, String... tokens) {
		System.out.println("모바일에서의 요청 : 동기화 요청");
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
		// System.out.println("모바일에서의 요청 : 바코드 요청");
	}

	// #ModongGroupBacode%id
	public void processGroupBarcode(ConnectionToClient client, String... tokens) {
		// System.out.println("모바일에서의 요청 : 그룸 바코드 요청");
	}
}