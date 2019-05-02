package com.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.BankBiz;
import com.dao.BankDao;
import com.dto.BankDto;

public class BankView {
	static BankDto logindto = null;
	static Scanner sc = new Scanner(System.in);

	public static int getMenu() {
		StringBuffer sb = new StringBuffer();
		int select = -1;

		sb.append("*****************\n")
		.append("1.��  ��  ��  ��\n")
		.append("2.��    ��    ��\n")
		.append("3.��  ��  ��  ȸ\n")
		.append("4.�ֱٰŷ�����\n")
		.append("5.��            ü\n")
		.append("6.��            ��\n")
		.append("7.��            ��\n")
		.append("8.������   �Ա�\n")
		.append("9.��  ��  ��  ��\n")		
		.append("0.��            ��\n");

		System.out.println(sb);
		System.out.println("�ŷ� ��ư�� �����ּ���!!: ");
		select = sc.nextInt();
		return select;
	}

	public static void main(String[] args) {
		int select = -1;
		String name, account, password, sender, message;
		int t_balance, input, output;
		BankBiz biz = new BankBiz();

		while (select != 0) {
			select = getMenu();
			switch (select) {
			case 1:// ���°���
				System.out.println("���°��� ȭ���Դϴ�.");
				System.out.println("�̸�: ");
				name = sc.next();
				System.out.println("�޴�����ȣ(���¹�ȣ[ex)01012345678]):");
				account = sc.next();
				System.out.println("��й�ȣ :");
				password = sc.next();
				System.out.println("�Աݾ�: ");
				t_balance = sc.nextInt();
				BankDto dto = new BankDto(account, name, password, t_balance, null);
				int accountinsert = biz.AccountCreate(dto);
				if (accountinsert > 0) {
					System.out.println("���°��� ����");
				} else {
					System.out.println("���°��� ����");
				}
				break;
			case 2:// �޴�����ȣ�� �α���
				if (logindto == null) {// �α������ϱ���
					System.out.println("�α��� ȭ���Դϴ�.");
					System.out.println("���¹�ȣ(�޴�����ȣ)[ex)01012345678]: ");
					account = sc.next();
					System.out.println("��й�ȣ :");
					password = sc.next();
					logindto = biz.login(account, password);
					if (logindto.getAccount() != null) {
						System.out.println("���¹�ȣ: " + logindto.getAccount());
					} else {
						System.out.println("���¹�ȣ �Ǵ� ��й�ȣ�� �߸� �Է��ϼ̽��ϴ�. �ʱ� ȭ������ ���ư��ϴ�.");
					}
				} else {// �α����� �ѻ���
					System.out.println("�̹� �������� ���̵� �Դϴ�.");
				}
				break;
			case 3:
				if (logindto != null) {
					System.out.println("���¹�ȣ: " + logindto.getAccount());
					System.out.println("������ȸȭ���̴�.");
					BankDto Ldto = biz.myaccount(logindto.getAccount());
					int bal = Ldto.getT_balance();
					System.out.println("�ܾ�:" + bal);
				} else {
					System.out.println("�α��� ���� ���ּ���.");
				}

				break;
			case 4:
				if (logindto != null) {
					System.out.println("�ŷ�������ȸȭ���̴�.");
					BankDao dao = new BankDao();
					List<BankDto> list = new ArrayList<BankDto>();
					list = biz.tradeList(logindto.getAccount());
					for (int i = 0; i < list.size(); i++) {
						System.out.print(list.get(i).getAccount());
						System.out.print(list.get(i).getTrade_date());
						System.out.print(list.get(i).getSender());
						System.out.print(list.get(i).getMessage());
						System.out.print(list.get(i).getInput());
						System.out.print(list.get(i).getOutput());
						System.out.println(list.get(i).getBalance());
					}

				} else {
					System.out.println("�α��� ���� ���ּ���.");
				}

			case 5:// ��üBankDto sender(�α�������dto(bank���̺����), String receiverAccount, int money,
					// String message
				if (logindto != null) {
					System.out.println("��üȭ���Դϴ�.");
					System.out.println("���¹�ȣ:");
					account = sc.next();
					System.out.println("��ü�ݾ� :");
					input = sc.nextInt();
					System.out.println("�����޼���: ");
					message = sc.next();
					boolean successProcess = biz.transferMoney(logindto, account, input, message);
					System.out.println("[��ü�Ͻ� ���¹�ȣ : " + account + " ]");
					System.out.println("[��ü�Ͻ� �ݾ� : " + input + " ]");
					System.out.println("������ Ȯ���� ��й�ȣ�� �Է��ϼ���.");
					System.out.println("���� ��й�ȣ : ");
					password = sc.next();
					System.out.println(logindto.getAccount() + logindto.getName() + logindto.getPassword()
							+ logindto.getT_balance() + logindto.getReg_date());
					if (logindto.getPassword().equals(password)) {
						if (successProcess == true) {
							System.out.println("��ü�� �����Ͽ����ϴ�.");
						} else {
							System.out.println("��й�ȣȮ��o ��üx");
						}
					} else {
						System.out.println("��ü ����");
					}
				}else {
					System.out.println("�α����� ���� ���ּ���!");
				}
				break;
			case 6:
				if (logindto != null) {
					System.out.println("�Ա� ȭ��");
					System.out.println("�޽��� �Է� : ");
					message = sc.next();
					System.out.println("�ӱ��� �ݾ� : ");
					input = sc.nextInt();
					int deposit = biz.Deposit(logindto.getAccount(), message, input);
					if (deposit >= 2) {
						System.out.println("�Ա� ����!");
					} else {
						System.out.println("�Ա� ����");
					}
				}else {
					System.out.println("�α����� ���� ���ּ���!");
				}
				break;
			case 7:
				if (logindto != null) {
					System.out.println("��� ȭ��");
					System.out.println("�޽��� �Է� : ");
					message = sc.next();
					System.out.println("����� �ݾ� : ");
					output = sc.nextInt();
					System.out.println("��й�ȣ�� �ѹ��� �Է��ϼ���");
					password = sc.next();
					if (password.equals(logindto.getPassword())) {
						int withdraw = biz.Withdraw(logindto.getAccount(), message, output);
						if (withdraw >= 2) {
							System.out.println("��� ����!");
						} else {
							System.out.println("��� ����");
						}
					} else {
						System.out.println("��й�ȣ�� Ʋ�Ƚ��ϴ�.");
					}
				}else {
					System.out.println("�α����� ���� ���ּ���!");
				}
				break;
			case 8:// �������Ա�
				System.out.println("�������Ա��Դϴ�.");
				System.out.println("�Ա��� ����: ");
				account = sc.next();
				System.out.println("�Ա��� �ݾ�: ");
				input = sc.nextInt();
				System.out.println("������ ��� : ");
				sender = sc.next();
				System.out.println("�޼��� �Է�: ");
				message = sc.next();
				System.out.println();
				BankDto asisbalancedto = biz.BankBalance(account);
				BankDto trade_listdto = new BankDto(account, null, sender, message, input, 0, 0);
				String res = biz.NoPassbookDeposit(account, asisbalancedto, trade_listdto);
				if (res.equals("11")) {
					System.out.println("�������Ա� ����!");
				} else {
					System.out.println("�������Ա� ����!");
				}
				break;
			case 9:// �α׾ƿ�
				logindto = null;
				if (logindto == null) {
					System.out.println("���������� �α׾ƿ� �Ǿ����ϴ�.");
				}
				break;
			case 0:// ����
				break;

			}
		}
	}

}
