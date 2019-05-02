package com.biz;

import com.dao.BankDao;
import com.dto.BankDto;

public class BankBiz {

	BankDao dao = new BankDao();

	// (1) 				(2)	 	(3) 		(4) 			(5) 		(6)
	// 계좌유효성 -> 잔액 유효성 -> 돈을 빼 -> 거래내역 추가(출금) -> 돈을 넣어 -> 거래내역 추가
	public boolean transferMoney(BankDto sender, String receiverAccount, int money, String message) {
		int[] resProcess = new int[4];
		String processRes = "";
		int cnt = 0;
		boolean successProcess = false;


		// (1-1) 계좌번호 유효성검사 true
		if (dao.accountCheck(receiverAccount)) {
			System.out.println(">>>>>>>>>> process 1: 유효한 계좌!");
			//(1-1-1) 본인계좌와 같은지아닌지 확인
			if (!(sender.getAccount().equals(receiverAccount))) {
				// (2-1) 잔액유효성 검사 true
				if (dao.getBalance(sender.getAccount()) >= money) {
					System.out.println(">>>>>>>>>> process 2 : 유효한 계좌 잔액!");
					//내돈 먼저뺴 (3)(4)   => 11
					processRes += dao.updateBalance(sender, sender.getAccount(), -money, "");
					//쟤한테 줘 (5)(6)   => 11
					processRes += dao.updateBalance(sender, receiverAccount, money, message);
				// (2-2) 잔액유효성 검사 false
				} else {
					successProcess = false;
					System.out.println(">>>>>>>>>> process 2 : error!");
					System.err.println("!!!!잔액이 부족합니다!!!!\n");
				}
			} else {
				System.err.println("본인계좌로 송금하실 수 없습니다.");
			}
			// (1-2) 계좌번호 유효성 검사 false
		} else {
			successProcess = false;
			System.out.println(">>>>>>>>>> process1 : error!");
			System.err.println("올바르지 않은 계좌번호입니다!");
		}
		// processRes가 22이면(모든 쿼리가 실행이 잘되었다면) sucessProcess(true)
		if(processRes.equals("22")) {
			successProcess = true;
		}

		return successProcess;
	}

	public BankDto selectOne(String account) {
		return dao.selectOne(account);
	}

	public boolean accountCheck(String account) {
		return dao.accountCheck(account);
	}
	
	public int Deposit(String account, String message, int in_money) {
		return dao.Deposit(account, message, in_money);
	}
	
	public int Withdraw(String account, String message, int out_money) {
		return dao.Withdraw(account, message, out_money);
	}

}
