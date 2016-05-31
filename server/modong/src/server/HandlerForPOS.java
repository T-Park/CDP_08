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
import db.SQLforPOS;
import server.HandlerForCC.messageType;
import test.ModongServer;

public class HandlerForPOS {
	private ModongServer server;
	/*
	 * private ModongUserAdmin ua; private CoinCollectorAdmin cca; private
	 * DonationOrgnzAdmin doa; private StoreAdmin sa;
	 */

	// db accessor
	private SQLforPOS sqlForPos;

	// class for message
	public final class messageType {
		public static final String IDENTIFY = "#PosIdentify";
		public static final String POINTADD = "#PosPointAdd";
		public static final String POINTREMOVE = "#PosPointRemove";

	}

	public HandlerForPOS(ModongServer server) {
		this.server = server;
		/*
		 * ua = ModongUserAdmin.getInstance(); cca =
		 * CoinCollectorAdmin.getInstance(); doa =
		 * DonationOrgnzAdmin.getInstance(); sa = StoreAdmin.getInstance(); //
		 * TODO Auto-generated constructor stub
		 */ sqlForPos = new SQLforPOS();
	}

	public void processMessage(ConnectionToClient client, String... tokens) {
		// tokens[0] is header
		System.out.println("hfp 까지옴");
		switch (tokens[0]) {
		case messageType.IDENTIFY: // Pos기 인증
			processIdentify(client, tokens);
			break;
		case messageType.POINTADD: // point 적립
			processPointAdd(client, tokens);
			break;
		case messageType.POINTREMOVE: // point 사용
			processPointRemove(client, tokens);
			break;
		}
	}

	// #PosIdentify%32, 성공시 #true
	// 실패시 메세지
	// identify가 아니고 login이고 logout도 필요할거 같음
	public void processIdentify(ConnectionToClient client, String... tokens) {
		System.out.println("Pos기에서의 요청 : pos기 사용자 인증");
		if (tokens.length != 2) {
			System.out.println(">> processIdentify 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}

		// if (!sa.findStore(Integer.parseInt(tokens[1]))) {
		// System.out.println(">> 존재 하지 않는 pid 입니다. pid : " + tokens[1]);
		// server.sendToMyClient(client, "존재 하지 않는 pid 입니다.");
		// } else// 인증 o
		// server.sendToMyClient(client, "#true");
		System.out.println(Integer.parseInt(tokens[1]));

		// check parameter validity
		if (!sqlForPos.login(client.getConn(), Integer.parseInt(tokens[1]))) {
			System.out.println(">> 존재 하지 않는 pid 입니다. pid : " + tokens[1]);
			server.sendToMyClient(client, "존재 하지 않는 pid 입니다.");
		} else
			server.sendToMyClient(client, "#true");
	}

	// #PosPointAdd%pid%barcode%point 성공시
	// add후 point rt
	public void processPointAdd(ConnectionToClient client, String... tokens) {
		System.out.println("Pos기에서의 요청 : 포인트 적립");
		if (tokens.length != 4) {
			System.out.println(">> processPointAdd 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String barcode = tokens[2];
		int amount = Integer.parseInt(tokens[3]);
		System.out.printf("pid: %d, barcode:%s, point:%d\n", pid, barcode, amount);

		// 그룹 바코드 처리

		// if (!ua.isThereUser_asBacode(bacode)) {
		// System.out.println(">> 존재 하지 않는 user bacode 입니다.");
		// // sendToMyClient(client, "존재 하지 않는 user bacode 입니다.");
		// } else {
		// ModongUser tempUser = ua.findUser_asBacode(bacode);
		// tempUser.addPoint(point);
		// sa.recordAddPoint_asBacode(bacode, pid, point);
		//
		// System.out.println(">>" + pid + "번 포스기 : " + point + "적립 되었습니다.");
		// server.sendToMyClient(client, "#" +
		// ua.getModongUser_asBacode(bacode).getUser_point());
		// }
		// check parameter validity
		if (!sqlForPos.checkUserbyBarcode(client.getConn(), barcode)) {
			System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			server.sendToMyClient(client, "barcode error");
		} else {
			if (!sqlForPos.addPointbyBarcode(client.getConn(), amount, barcode)) {
				System.out.println(">> 적립에 실패했습니다.");
				server.sendToMyClient(client, "failed to save");
			} else {
				System.out.println(">> 적립하였습니다.");
				server.sendToMyClient(client, "success to save");
				if (!sqlForPos.logResult(client.getConn(), pid, barcode, amount, 's'))
					System.out.println("log 실패");
				else
					System.out.println("log 성공");
			}
		}
	}

	// #PosPointRemove%pid%bacode%point
	public void processPointRemove(ConnectionToClient client, String... tokens) {
		System.out.println("Pos기에서의 요청 : 포인트 사용");
		// check parameter validity
		if (tokens.length != 4) {
			System.out.println(">> processPointRemove 파라미터 에러");
			server.sendToMyClient(client, "파라미터 에러입니다.");
			return;
		}
		// String[] token = line.split("%");
		int pid = Integer.parseInt(tokens[1]);
		String barcode = tokens[2];
		int amount = Integer.parseInt(tokens[3]);
		System.out.printf("pid: %d, barcode:%s, point:%d\n", pid, barcode, amount);
		// // 그룹 바코드 처리
		//
		// if (!ua.isThereUser_asBacode(barcode)) {
		// System.out.println(">> 존재 하지 않는 user bacode 입니다.");
		// server.sendToMyClient(client, "존재 하지 않는 user bacode 입니다.");
		// } else {
		// ModongUser tempUser = ua.findUser_asBacode(barcode);
		// if (tempUser.removePoint(amount)) {
		// sa.recordAddPoint_asBacode(barcode, pid, amount);
		// System.out.println(">>" + pid + "번 포스기 : " + amount + "사용 되었습니다.");
		// server.sendToMyClient(client, "#" +
		// ua.getModongUser_asBacode(barcode).getUser_point());
		// } else
		// server.sendToMyClient(client, "point가 부족합니다.");
		// }

		// check is valid user
		if (!sqlForPos.checkUserbyBarcode(client.getConn(), barcode)) {
			System.out.println(">> 존재 하지 않는  user bacode 입니다.");
			server.sendToMyClient(client, "barcode error");
		} else {
			if (!sqlForPos.withdrawPointbyBarcode(client.getConn(), amount, barcode)) {
				System.out.println(">> 잔액이 부족합니다.");
				server.sendToMyClient(client, "failed to use");
			} else {
				System.out.println(">> 사용하였습니다.");
				server.sendToMyClient(client, "success to use");
				if (!sqlForPos.logResult(client.getConn(), pid, barcode, amount, 'u'))
					System.out.println("log 실패");
				else
					System.out.println("log 성공");
			}
		}
	}

}
