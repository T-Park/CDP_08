/*
 * HandleMessageFromPOS.java
 * 
 * 포스기로부터 오는 메세지 처리
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
		case messageType.IDENTIFY: // Pos기 인증
			break;
		case messageType.POINTADD: // point 적립
			break;
		case messageType.POINTREMOVE: // point 사용
			break;
		}

	}

	// #PosIdentify%32, 성공시 #true
	// 실패시 메세지
	public void processIdentify(ConnectionToClient client, String... tokens) {
		System.out.println("Pos기에서의 요청 : pos기 사용자 인증");

		if (!sa.findStore(Integer.parseInt(tokens[1]))) {
			System.out.println(">> 존재 하지 않는 pid 입니다. pid : " + tokens[1]);
			server.sendToMyClient(client, "존재 하지 않는 pid 입니다.");
		} else// 인증 o
			server.sendToMyClient(client, "#true");
	}

	// #PosPointAdd%pid%bacode%point 성공시
	// add후 point rt
	public void processPointAdd(ConnectionToClient client, String... tokens) {
		System.out.println("Pos기에서의 요청 : 포인트 적립");
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String bacode = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		// 그룹 바코드 처리

		if (!ua.isThereUser_asBacode(bacode)) {
			System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			// sendToMyClient(client, "존재 하지 않는 user bacode 입니다.");
		} else {
			ModongUser tempUser = ua.findUser_asBacode(bacode);
			tempUser.addPoint(point);
			sa.recordAddPoint_asBacode(bacode, pid, point);

			System.out.println(">>" + pid + "번 포스기 : " + point + "적립 되었습니다.");
			server.sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
		}

	}

	// #PosPointRemove%pid%bacode%point
	public void processPointRemove(ConnectionToClient client, String... tokens) {
		System.out.println("Pos기에서의 요청 : 포인트 사용");
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String bacode = tokens[2];
		int point = Integer.parseInt(tokens[3]);

		// 그룹 바코드 처리

		if (!ua.isThereUser_asBacode(bacode)) {
			System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			server.sendToMyClient(client, "존재 하지 않는  user bacode 입니다.");
		} else {
			ModongUser tempUser = ua.findUser_asBacode(bacode);
			if (tempUser.removePoint(point)) {
				sa.recordAddPoint_asBacode(bacode, pid, point);
				System.out.println(">>" + pid + "번 포스기 : " + point + "사용 되었습니다.");
				server.sendToMyClient(client, "#" + ua.getModongUser_asBacode(bacode).getUser_point());
			} else
				server.sendToMyClient(client, "point가 부족합니다.");
		}
	}
}
