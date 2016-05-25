package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import ProblemDomain.CoinCollectorAdmin;
import ProblemDomain.DonationOrgnzAdmin;
import ProblemDomain.ModongUserAdmin;
import ProblemDomain.StoreAdmin;
import common.*;

public class ServerConsole implements ChatIF {
	private ModongServer sv;
	BufferedReader fromConsole;

	Scanner scan;
	ModongUserAdmin ua;
	CoinCollectorAdmin cca;
	DonationOrgnzAdmin doa;
	StoreAdmin sa;

	ServerConsole(ModongServer sv) {
		this.sv = sv;
		scan = new Scanner(System.in);
		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance();
	}

	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		System.out.println("SERVER MSG> " + message);
	}

	public void accept() {
		try {
			fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) {
				System.out.println(">>모여라 동전 server에 오신것을 환영합니다.");
				System.out.println("# - : 관리자 커맨드 ");
				System.out.println("* - : 클라이언트에게 공지 ");
				System.out.println("0. 회원 추가 1. 회원 목록 보기 2. 회원 삭제  3. 기부단체 관리 4. 동전모음이 관리 5. 가맹점 관리");
				message = fromConsole.readLine();

				if (message != null) {
					if (message.substring(0, 1).equals("#")) {
						serverOCSFCommand(message);
						continue;
					} else if (message.substring(0, 1).equals("*")) {
						if (sv.isListening()) {
							sv.sendToAllClients("SERVER MSG> " + message);
							display(message);
						} else
							System.out.println("===== 서버가 멈춘 상태입니다. =====");
					} else {
						switch (message) {
						case "0":
							ua.print_currentUserListInfo();

							System.out.println("id 입력(string)");
							String id = scan.next();

							System.out.println("pw 입력(string)");
							String pw = scan.next();

							System.out.println("name 입력(string)");
							String name = scan.next();

							System.out.println("직업 입력(string)");
							String job = scan.next();

							System.out.println("나이 입력(int)");
							int age = scan.nextInt();

							System.out.println("tel 입력(-를 빼고 입력해주세요.)");
							String tel = scan.next();

							ua.joinModong(id, pw, name, job, age, tel);
							System.out.println(">> 회원가입 되셨습니다.");
							break;

						case "1":
							ua.print_currentUserListInfo();
							break;
						case "2":
							ua.print_currentUserListInfo();
							System.out.println("회원을 삭제합니다. 아이디를 입력해 주세요.");
							String tmepId = fromConsole.readLine();
							if (ua.leaveModong(tmepId))
								System.out.println(">> 탈퇴 되었습니다.");
							else
								System.out.println(">> 실패 하였습니다.");
							break;

						case "3":
							adminDonationUi();
							break;
						case "4":
							adminCoinCollectorUi();
							break;
						case "5":
							adminStoreUi();
							break;
						default:
							System.out.println(">> 잘못된 입력입니다.");
						}

					}

				}

			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	public void serverOCSFCommand(String message) throws Exception {
		String subMsg = message.length() > 1 ? message.substring(1) : "";

		if (subMsg == null) {
			System.out.println("===== 잘못된 명령어 입니다. =====");
		} else if (subMsg.equals("quit")) {
			sv.close();
			System.out.println("===== 프로그램 종료 =====");
			System.exit(0);
		} else if (subMsg.equals("stop")) {
			sv.stopListening();
			System.out.println("===== 서버 정지 =====");
		} else if (subMsg.equals("close")) {
			sv.close();
			System.out.println("===== 서버 닫기 =====");

		} else if (subMsg.equals("start")) {
			if (!sv.isListening()) {
				sv.listen();
				System.out.println("=====서버 시작======");
			} else
				System.out.println("===== 이미 서버 가동중 =====");
		} else if (subMsg.length() > 8 && subMsg.substring(0, 7).equals("setport")) {
			if (sv.isListening()) {
				System.out.println("===== 서버가 멈춘 상태에서 가능합니다. =====");
			} else {
				sv.setPort(Integer.parseInt(subMsg.substring(8)));
				System.out.println("===== 현재 포트 번호 : " + sv.getPort() + "=====");
			}

		} else if (subMsg.equals("getport")) {
			System.out.println("=====  현재 포트 번호 : " + sv.getPort() + "=====");
		} else {
			System.out.println("===== 잘못된 명령어 입니다. =====");
		}
	}

	public void adminDonationUi() {
		while (true) {
			System.out.println(">> 기부단체 관리를 시작합니다.");
			System.out.println("0. 돌아가기 1. 기부단체 추가 2. 기부단체 삭제 3. 목록보기");
			int choice = scan.nextInt();
			switch (choice) {
			case 0:
				return;
			case 1:
				System.out.println("기부 단체를 추가합니다. did, name, point, tel, type을 입력 해주세요.");

				System.out.println("did 입력(int)");
				int did = scan.nextInt();

				System.out.println("name 입력(string)");
				String name = scan.next();

				System.out.println("point 입력(int)");
				int point = scan.nextInt();

				System.out.println("tel 입력(-를 빼고 입력해주세요.)");
				String tel = scan.next();
				System.out.println("type 입력(뮨자1)");
				String tempType = scan.next();
				char[] type = tempType.toCharArray();

				if (doa.addDonationOrgnz(did, name, point, tel, type[0]))
					System.out.println(">> 추가 되었습니다.");
				else
					System.out.println(">> 실패 하였습니다.");
				break;

			case 2:
				doa.print_currentDonationOrgnzListInfo();
				System.out.println("기부단체를 삭제합니다. did를 입력해 주세요.");
				int did2 = scan.nextInt();
				if (doa.removeDonationOrgnz(did2))
					System.out.println(">> 삭제 되었습니다.");
				else
					System.out.println(">> 실패 하였습니다.");
				break;

			case 3:
				doa.print_currentDonationOrgnzListInfo();
				break;
			default:
				System.out.println(">> 잘못된 입력입니다.");
			}

		}

	}

	public void adminCoinCollectorUi() {
		while (true) {
			System.out.println(">> 동전모음이 관리를 시작합니다.");
			System.out.println("0. 돌아가기 1. 동전모음이 추가 2. 동전모음이 삭제 3. 목록보기");
			int choice = scan.nextInt();
			switch (choice) {
			case 0:
				return;
			case 1:
				System.out.println("동전모음이를 추가합니다. did, name, point, tel, type을 입력 해주세요.");

				System.out.println("cid 입력(int)");
				int cid = scan.nextInt();

				System.out.println("city 입력(string)");
				String city = scan.next();

				System.out.println("district 입력(string)");
				String district = scan.next();

				System.out.println("detail 주소 입력(string)");
				String detail = scan.next();

				if (cca.addCoinCollector(cid, city, district, detail))
					System.out.println(">> 추가 되었습니다.");
				else
					System.out.println(">> 실패 하였습니다.");
				break;

			case 2:
				cca.print_currentCoinCollectorListInfo();
				System.out.println("동전모음이를 삭제합니다. did를 입력해 주세요.");
				int cid2 = scan.nextInt();
				if (cca.removeCoinCollector(cid2))
					System.out.println(">> 삭제 되었습니다.");
				else
					System.out.println(">> 실패 하였습니다.");
				break;

			case 3:
				cca.print_currentCoinCollectorListInfo();
				break;
			default:
				System.out.println(">> 잘못된 입력입니다.");
			}

		}

	}

	public void adminStoreUi() {
		while (true) {
			System.out.println(">> 가맹점 관리를 시작합니다.");
			System.out.println("0. 돌아가기 1. 가맹점 추가 2. 가맹점 삭제 3. 목록보기");
			int choice = scan.nextInt();
			switch (choice) {
			case 0:
				return;
			case 1:
				System.out.println("가맹점을 추가합니다. pid, name, city, district, detail, tel, type을 입력 해주세요.");

				System.out.println("pid 입력(int)");
				int pid = scan.nextInt();

				System.out.println("name 입력(string)");
				String name = scan.next();

				System.out.println("city 입력(string)");
				String city = scan.next();

				System.out.println("district 입력(string)");
				String district = scan.next();

				System.out.println("detail 주소 입력(string)");
				String detail = scan.next();

				System.out.println("tel 입력(-를 빼고 입력해주세요.)");
				String tel = scan.next();
				System.out.println("type 입력(뮨자1)");
				String tempType = scan.next();
				char[] type = tempType.toCharArray();

				if (sa.addStore(pid, name, type[0], city, district, detail, tel))
					System.out.println(">> 추가 되었습니다.");
				else
					System.out.println(">> 실패 하였습니다.");
				break;

			case 2:
				sa.print_currentStoreInfo();
				System.out.println("가맹점을 삭제합니다. pid를 입력해 주세요.");
				int pid2 = scan.nextInt();
				if (sa.removeStore(pid2))
					System.out.println(">> 삭제 되었습니다.");
				else
					System.out.println(">> 실패 하였습니다.");
				break;

			case 3:
				sa.print_currentStoreInfo();
				break;
			default:
				System.out.println(">> 잘못된 입력입니다.");
			}

		}

	}

}
