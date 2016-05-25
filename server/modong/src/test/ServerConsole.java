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
				System.out.println(">>�𿩶� ���� server�� ���Ű��� ȯ���մϴ�.");
				System.out.println("# - : ������ Ŀ�ǵ� ");
				System.out.println("* - : Ŭ���̾�Ʈ���� ���� ");
				System.out.println("0. ȸ�� �߰� 1. ȸ�� ��� ���� 2. ȸ�� ����  3. ��δ�ü ���� 4. ���������� ���� 5. ������ ����");
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
							System.out.println("===== ������ ���� �����Դϴ�. =====");
					} else {
						switch (message) {
						case "0":
							ua.print_currentUserListInfo();

							System.out.println("id �Է�(string)");
							String id = scan.next();

							System.out.println("pw �Է�(string)");
							String pw = scan.next();

							System.out.println("name �Է�(string)");
							String name = scan.next();

							System.out.println("���� �Է�(string)");
							String job = scan.next();

							System.out.println("���� �Է�(int)");
							int age = scan.nextInt();

							System.out.println("tel �Է�(-�� ���� �Է����ּ���.)");
							String tel = scan.next();

							ua.joinModong(id, pw, name, job, age, tel);
							System.out.println(">> ȸ������ �Ǽ̽��ϴ�.");
							break;

						case "1":
							ua.print_currentUserListInfo();
							break;
						case "2":
							ua.print_currentUserListInfo();
							System.out.println("ȸ���� �����մϴ�. ���̵� �Է��� �ּ���.");
							String tmepId = fromConsole.readLine();
							if (ua.leaveModong(tmepId))
								System.out.println(">> Ż�� �Ǿ����ϴ�.");
							else
								System.out.println(">> ���� �Ͽ����ϴ�.");
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
							System.out.println(">> �߸��� �Է��Դϴ�.");
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
			System.out.println("===== �߸��� ��ɾ� �Դϴ�. =====");
		} else if (subMsg.equals("quit")) {
			sv.close();
			System.out.println("===== ���α׷� ���� =====");
			System.exit(0);
		} else if (subMsg.equals("stop")) {
			sv.stopListening();
			System.out.println("===== ���� ���� =====");
		} else if (subMsg.equals("close")) {
			sv.close();
			System.out.println("===== ���� �ݱ� =====");

		} else if (subMsg.equals("start")) {
			if (!sv.isListening()) {
				sv.listen();
				System.out.println("=====���� ����======");
			} else
				System.out.println("===== �̹� ���� ������ =====");
		} else if (subMsg.length() > 8 && subMsg.substring(0, 7).equals("setport")) {
			if (sv.isListening()) {
				System.out.println("===== ������ ���� ���¿��� �����մϴ�. =====");
			} else {
				sv.setPort(Integer.parseInt(subMsg.substring(8)));
				System.out.println("===== ���� ��Ʈ ��ȣ : " + sv.getPort() + "=====");
			}

		} else if (subMsg.equals("getport")) {
			System.out.println("=====  ���� ��Ʈ ��ȣ : " + sv.getPort() + "=====");
		} else {
			System.out.println("===== �߸��� ��ɾ� �Դϴ�. =====");
		}
	}

	public void adminDonationUi() {
		while (true) {
			System.out.println(">> ��δ�ü ������ �����մϴ�.");
			System.out.println("0. ���ư��� 1. ��δ�ü �߰� 2. ��δ�ü ���� 3. ��Ϻ���");
			int choice = scan.nextInt();
			switch (choice) {
			case 0:
				return;
			case 1:
				System.out.println("��� ��ü�� �߰��մϴ�. did, name, point, tel, type�� �Է� ���ּ���.");

				System.out.println("did �Է�(int)");
				int did = scan.nextInt();

				System.out.println("name �Է�(string)");
				String name = scan.next();

				System.out.println("point �Է�(int)");
				int point = scan.nextInt();

				System.out.println("tel �Է�(-�� ���� �Է����ּ���.)");
				String tel = scan.next();
				System.out.println("type �Է�(����1)");
				String tempType = scan.next();
				char[] type = tempType.toCharArray();

				if (doa.addDonationOrgnz(did, name, point, tel, type[0]))
					System.out.println(">> �߰� �Ǿ����ϴ�.");
				else
					System.out.println(">> ���� �Ͽ����ϴ�.");
				break;

			case 2:
				doa.print_currentDonationOrgnzListInfo();
				System.out.println("��δ�ü�� �����մϴ�. did�� �Է��� �ּ���.");
				int did2 = scan.nextInt();
				if (doa.removeDonationOrgnz(did2))
					System.out.println(">> ���� �Ǿ����ϴ�.");
				else
					System.out.println(">> ���� �Ͽ����ϴ�.");
				break;

			case 3:
				doa.print_currentDonationOrgnzListInfo();
				break;
			default:
				System.out.println(">> �߸��� �Է��Դϴ�.");
			}

		}

	}

	public void adminCoinCollectorUi() {
		while (true) {
			System.out.println(">> ���������� ������ �����մϴ�.");
			System.out.println("0. ���ư��� 1. ���������� �߰� 2. ���������� ���� 3. ��Ϻ���");
			int choice = scan.nextInt();
			switch (choice) {
			case 0:
				return;
			case 1:
				System.out.println("���������̸� �߰��մϴ�. did, name, point, tel, type�� �Է� ���ּ���.");

				System.out.println("cid �Է�(int)");
				int cid = scan.nextInt();

				System.out.println("city �Է�(string)");
				String city = scan.next();

				System.out.println("district �Է�(string)");
				String district = scan.next();

				System.out.println("detail �ּ� �Է�(string)");
				String detail = scan.next();

				if (cca.addCoinCollector(cid, city, district, detail))
					System.out.println(">> �߰� �Ǿ����ϴ�.");
				else
					System.out.println(">> ���� �Ͽ����ϴ�.");
				break;

			case 2:
				cca.print_currentCoinCollectorListInfo();
				System.out.println("���������̸� �����մϴ�. did�� �Է��� �ּ���.");
				int cid2 = scan.nextInt();
				if (cca.removeCoinCollector(cid2))
					System.out.println(">> ���� �Ǿ����ϴ�.");
				else
					System.out.println(">> ���� �Ͽ����ϴ�.");
				break;

			case 3:
				cca.print_currentCoinCollectorListInfo();
				break;
			default:
				System.out.println(">> �߸��� �Է��Դϴ�.");
			}

		}

	}

	public void adminStoreUi() {
		while (true) {
			System.out.println(">> ������ ������ �����մϴ�.");
			System.out.println("0. ���ư��� 1. ������ �߰� 2. ������ ���� 3. ��Ϻ���");
			int choice = scan.nextInt();
			switch (choice) {
			case 0:
				return;
			case 1:
				System.out.println("�������� �߰��մϴ�. pid, name, city, district, detail, tel, type�� �Է� ���ּ���.");

				System.out.println("pid �Է�(int)");
				int pid = scan.nextInt();

				System.out.println("name �Է�(string)");
				String name = scan.next();

				System.out.println("city �Է�(string)");
				String city = scan.next();

				System.out.println("district �Է�(string)");
				String district = scan.next();

				System.out.println("detail �ּ� �Է�(string)");
				String detail = scan.next();

				System.out.println("tel �Է�(-�� ���� �Է����ּ���.)");
				String tel = scan.next();
				System.out.println("type �Է�(����1)");
				String tempType = scan.next();
				char[] type = tempType.toCharArray();

				if (sa.addStore(pid, name, type[0], city, district, detail, tel))
					System.out.println(">> �߰� �Ǿ����ϴ�.");
				else
					System.out.println(">> ���� �Ͽ����ϴ�.");
				break;

			case 2:
				sa.print_currentStoreInfo();
				System.out.println("�������� �����մϴ�. pid�� �Է��� �ּ���.");
				int pid2 = scan.nextInt();
				if (sa.removeStore(pid2))
					System.out.println(">> ���� �Ǿ����ϴ�.");
				else
					System.out.println(">> ���� �Ͽ����ϴ�.");
				break;

			case 3:
				sa.print_currentStoreInfo();
				break;
			default:
				System.out.println(">> �߸��� �Է��Դϴ�.");
			}

		}

	}

}
