
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class PAmt {

	// (��ǰ�ڵ� �Է� -> ��ǰ��, ��ǰ���� ��� -> "�������" ��ȣ ������
	// [�Է� �ڵ尪 ���ھƴ�?] ��ǰ �� ���� �� �Ѿ� ���.
	// �迭���� �Ѿ���� �����ϰ���. ���� �迭�߿� ���ݶ��θ� ��� �޾Ƽ� �����ϸ� ����� ���� ����.

	public static void main(String args[]) {

		Function f = new Function();
		
		f.deserial();
		Scanner scan = new Scanner(System.in);
		
	
		f.cash();
		
		
	}
}