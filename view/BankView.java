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
		.append("1.계  좌  개  설\n")
		.append("2.로    그    인\n")
		.append("3.잔  액  조  회\n")
		.append("4.최근거래내역\n")
		.append("5.이            체\n")
		.append("6.입            금\n")
		.append("7.출            금\n")
		.append("8.무통장   입금\n")
		.append("9.로  그  아  웃\n")		
		.append("0.종            료\n");

		System.out.println(sb);
		System.out.println("거래 버튼을 눌러주세요!!: ");
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
			case 1:// 계좌개설
				System.out.println("계좌개설 화면입니다.");
				System.out.println("이름: ");
				name = sc.next();
				System.out.println("휴대폰번호(계좌번호[ex)01012345678]):");
				account = sc.next();
				System.out.println("비밀번호 :");
				password = sc.next();
				System.out.println("입금액: ");
				t_balance = sc.nextInt();
				BankDto dto = new BankDto(account, name, password, t_balance, null);
				int accountinsert = biz.AccountCreate(dto);
				if (accountinsert > 0) {
					System.out.println("계좌개설 성공");
				} else {
					System.out.println("계좌개설 실패");
				}
				break;
			case 2:// 휴대폰번호로 로그인
				if (logindto == null) {// 로그인을하기전
					System.out.println("로그인 화면입니다.");
					System.out.println("계좌번호(휴대폰번호)[ex)01012345678]: ");
					account = sc.next();
					System.out.println("비밀번호 :");
					password = sc.next();
					logindto = biz.login(account, password);
					if (logindto.getAccount() != null) {
						System.out.println("계좌번호: " + logindto.getAccount());
					} else {
						System.out.println("계좌번호 또는 비밀번호를 잘못 입력하셨습니다. 초기 화면으로 돌아갑니다.");
					}
				} else {// 로그인을 한상태
					System.out.println("이미 접속중인 아이디 입니다.");
				}
				break;
			case 3:
				if (logindto != null) {
					System.out.println("계좌번호: " + logindto.getAccount());
					System.out.println("계좌조회화면이다.");
					BankDto Ldto = biz.myaccount(logindto.getAccount());
					int bal = Ldto.getT_balance();
					System.out.println("잔액:" + bal);
				} else {
					System.out.println("로그인 먼저 해주세요.");
				}

				break;
			case 4:
				if (logindto != null) {
					System.out.println("거래내역조회화면이다.");
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
					System.out.println("로그인 먼저 해주세요.");
				}

			case 5:// 이체BankDto sender(로그인정보dto(bank테이블기준), String receiverAccount, int money,
					// String message
				if (logindto != null) {
					System.out.println("이체화면입니다.");
					System.out.println("계좌번호:");
					account = sc.next();
					System.out.println("이체금액 :");
					input = sc.nextInt();
					System.out.println("보낼메세지: ");
					message = sc.next();
					boolean successProcess = biz.transferMoney(logindto, account, input, message);
					System.out.println("[이체하실 계좌번호 : " + account + " ]");
					System.out.println("[이체하실 금액 : " + input + " ]");
					System.out.println("정보를 확인후 비밀번호를 입력하세요.");
					System.out.println("계좌 비밀번호 : ");
					password = sc.next();
					System.out.println(logindto.getAccount() + logindto.getName() + logindto.getPassword()
							+ logindto.getT_balance() + logindto.getReg_date());
					if (logindto.getPassword().equals(password)) {
						if (successProcess == true) {
							System.out.println("이체가 성공하였습니다.");
						} else {
							System.out.println("비밀번호확인o 이체x");
						}
					} else {
						System.out.println("이체 실패");
					}
				}else {
					System.out.println("로그인을 먼저 해주세요!");
				}
				break;
			case 6:
				if (logindto != null) {
					System.out.println("입금 화면");
					System.out.println("메시지 입력 : ");
					message = sc.next();
					System.out.println("임금할 금액 : ");
					input = sc.nextInt();
					int deposit = biz.Deposit(logindto.getAccount(), message, input);
					if (deposit >= 2) {
						System.out.println("입금 성공!");
					} else {
						System.out.println("입금 실패");
					}
				}else {
					System.out.println("로그인을 먼저 해주세요!");
				}
				break;
			case 7:
				if (logindto != null) {
					System.out.println("출금 화면");
					System.out.println("메시지 입력 : ");
					message = sc.next();
					System.out.println("출금할 금액 : ");
					output = sc.nextInt();
					System.out.println("비밀번호를 한번더 입력하세요");
					password = sc.next();
					if (password.equals(logindto.getPassword())) {
						int withdraw = biz.Withdraw(logindto.getAccount(), message, output);
						if (withdraw >= 2) {
							System.out.println("출금 성공!");
						} else {
							System.out.println("출금 실패");
						}
					} else {
						System.out.println("비밀번호가 틀렸습니다.");
					}
				}else {
					System.out.println("로그인을 먼저 해주세요!");
				}
				break;
			case 8:// 무통장입금
				System.out.println("무통장입금입니다.");
				System.out.println("입금할 계좌: ");
				account = sc.next();
				System.out.println("입금할 금액: ");
				input = sc.nextInt();
				System.out.println("보내는 사람 : ");
				sender = sc.next();
				System.out.println("메세지 입력: ");
				message = sc.next();
				System.out.println();
				BankDto asisbalancedto = biz.BankBalance(account);
				BankDto trade_listdto = new BankDto(account, null, sender, message, input, 0, 0);
				String res = biz.NoPassbookDeposit(account, asisbalancedto, trade_listdto);
				if (res.equals("11")) {
					System.out.println("무통장입금 성공!");
				} else {
					System.out.println("무통장입금 실패!");
				}
				break;
			case 9:// 로그아웃
				logindto = null;
				if (logindto == null) {
					System.out.println("정상적으로 로그아웃 되었습니다.");
				}
				break;
			case 0:// 종료
				break;

			}
		}
	}

}
