package com.dao;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollBack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.BankDto;

public class BankDao {

	BankDto dto = new BankDto();

	public int AccountCreate(BankDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		String sql = " INSERT INTO BANK VALUES(?,?,?,?,SYSDATE) ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getAccount());
			pstm.setString(2, dto.getName());
			pstm.setString(3, dto.getPassword());
			pstm.setInt(4, dto.getT_balance());

			res = pstm.executeUpdate();

			if (res > 0) {

				commit(con);
			} else {
				rollBack(con);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}
		return res;
	}

	public BankDto login(String account, String password) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		BankDto dto = new BankDto();
		String sql = " SELECT * FROM BANK WHERE ACCOUNT=? AND PASSWORD=? ";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, account);
			pstm.setString(2, password);

			rs = pstm.executeQuery();
			while (rs.next()) {
				dto.setAccount(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setT_balance(rs.getInt(4));
				dto.setReg_date(rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return dto;
	}

	// 나의계좌 조회 - > 계좌번호, 계좌잔액, 이름
	public BankDto myaccount(String account) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM BANK WHERE ACCOUNT=? ";
		BankDto dto = new BankDto();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, account);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto.setAccount(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setT_balance(rs.getInt(4));
				dto.setReg_date(rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}

		return dto;
	}

	// 거래내역조회 -> 계좌번호 , 거래날짜, 송금인 , 메시지 ,출금금액,입금금액,거래후잔액
	public List<BankDto> tradeList(String account) {

		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<BankDto> list = new ArrayList<BankDto>();
		String sql = " SELECT * FROM TRADE_LIST WHERE ACCOUNT=? " + " ORDER BY TRADE_DATE DESC";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, account);
			rs = pstm.executeQuery();
			while (rs.next()) {
				BankDto dto = new BankDto();
				dto.setAccount(rs.getString(1));
				dto.setTrade_date(rs.getDate(2));
				dto.setSender(rs.getString(3));
				dto.setMessage(rs.getString(4));
				dto.setInput(rs.getInt(5));
				dto.setOutput(rs.getInt(6));
				dto.setBalance(rs.getInt(7));
				list.add(dto);
			}

		} catch (SQLException e) {
			System.out.println("1실패");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return list;
	}

	public BankDto BankBalance(String account) {// 계좌번호로 bank테이블 balance값 가져오기
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		BankDto dto = new BankDto();
		String sql = " SELECT T_BALANCE FROM BANK WHERE ACCOUNT=? ";

		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, account);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto.setT_balance(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}
		return dto;
	}

	public String NoPassbookDeposit(String account, int tobe_balance, BankDto trade_listdto) {// 무통장입금 bank - update
		Connection con = getConnection();
		PreparedStatement updatepstm = null;
		PreparedStatement insertpstm = null;
		int res = 0;
		String result="";
		String updatesql = " UPDATE BANK SET T_BALANCE=? WHERE ACCOUNT=? ";
		String insertsql = " INSERT INTO TRADE_LIST VALUES(?,SYSDATE,?,?,?,0,?) ";

		try {
			updatepstm = con.prepareStatement(updatesql);
			updatepstm.setInt(1, tobe_balance);
			updatepstm.setString(2, account);
			res = updatepstm.executeUpdate();
			result=res+"";
			
			insertpstm = con.prepareStatement(insertsql);
			System.out.println("insertpstm"+insertpstm);
			insertpstm.setString(1, trade_listdto.getAccount());
			insertpstm.setString(2, trade_listdto.getSender());
			insertpstm.setString(3, trade_listdto.getMessage());
			insertpstm.setInt(4, trade_listdto.getInput());
			insertpstm.setInt(5, tobe_balance);
			res = insertpstm.executeUpdate();
			result=result+res;
			if(result.equals("11")) {
				commit(con);
			}else {
				rollBack(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(updatepstm);
			close(insertpstm);
			close(con);
		}
		return result;
	}

	
	public BankDto selectOne(String account) {
		System.out.println("[계좌정보 조회중....]");
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM BANK WHERE ACCOUNT = " + account + " ";
		BankDto temp = new BankDto();
		
		try {
			pstm = con.prepareStatement(sql);
			System.out.println("3.query ready..");
			
			rs = pstm.executeQuery();
			while(rs.next()) {
				temp.setAccount(rs.getString(1));
				temp.setName(rs.getString(2));
				temp.setPassword(rs.getString(3));
				temp.setT_balance(rs.getInt(4));
				temp.setReg_date(rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return temp;
	}
	
	public int getBalance(String acc) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT T_BALANCE FROM BANK WHERE ACCOUNT = ? ";
		int bal =0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, acc);
			
			rs = pstm.executeQuery();
			while(rs.next()) {
				bal = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bal;
	}
	
	public String updateBalance(BankDto sender, String receiverAccount, int inputMoney, String message) {
		Connection con = getConnection();
		PreparedStatement updatePstm = null;
		PreparedStatement insertPstm = null;
		int updateRes = 0;
		int insertRes = 0;
		int preBalance = getBalance(receiverAccount);
		int afterBalace = preBalance + inputMoney;
		String updateSql = " UPDATE BANK SET T_BALANCE = ? WHERE ACCOUNT = ? ";
		String inputSql = " INSERT INTO TRADE_LIST VALUES(?,SYSDATE,?,?,?,?,?) ";
		
		//1. update BANK TABLE
		try {
			updatePstm = con.prepareStatement(updateSql);
			updatePstm.setInt(1, afterBalace);
			updatePstm.setString(2, receiverAccount);
			updateRes = updatePstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				updatePstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//2. insert TRADE_LIST TABLE
		try {
			if(inputMoney > 0) {
				insertPstm = con.prepareStatement(inputSql);
				insertPstm.setString(1, receiverAccount);
				insertPstm.setString(2, sender.getName());
				insertPstm.setString(3, message);
				insertPstm.setInt(4, inputMoney);
				insertPstm.setInt(5, 0);
				insertPstm.setInt(6, afterBalace);
			}else {
				insertPstm = con.prepareStatement(inputSql);
				insertPstm.setString(1, receiverAccount);
				insertPstm.setString(2, "이체출금");
				insertPstm.setString(3, "");
				insertPstm.setInt(4, 0);
				insertPstm.setInt(5, -inputMoney);
				insertPstm.setInt(6, afterBalace);	
			}
			insertRes = insertPstm.executeUpdate();
			
			if(updateRes+insertRes==2) {
				commit(con);
			}else {
				rollBack(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return updateRes + insertRes + "";
	}

	public boolean accountCheck(String account) {
		System.out.println("[계좌정보 유효성 검사중.....]");
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String resAccount = "";
		boolean accountChk = false;
		String sql = " SELECT ACCOUNT FROM BANK WHERE ACCOUNT = " + account + " ";
		
		try {
			pstm = con.prepareStatement(sql);
			
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				resAccount = rs.getString(1);
			}
			if(!resAccount.equals("")) {
				accountChk = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return accountChk;
	}
	
	public int Deposit(String account, String message, int in_money) {
		Connection con = getConnection();
		PreparedStatement pstm01 = null;
		PreparedStatement pstm02 = null;
		int res01 = 0;
		int res02 = 0;
		String sql01 = " UPDATE BANK SET T_BALANCE = ? WHERE ACCOUNT = ? ";
		String sql02 = " INSERT INTO TRADE_LIST(ACCOUNT, TRADE_DATE, SENDER, MESSAGE, INPUT, OUTPUT, BALANCE) VALUES(?,SYSDATE,?,?,?,?,?) ";
		
		try {
			pstm01 = con.prepareStatement(sql01);
			pstm01.setInt(1,getBalance(account)+in_money);
			pstm01.setString(2,account);
			System.out.println("03. query 준비");
			
			res01 = pstm01.executeUpdate();
			System.out.println("04. query 실행 및 리턴");
		} catch (SQLException e) {
			System.out.println("03/04 에러1");
			e.printStackTrace();
		}finally {
			close(pstm01);
		}
		
		try {
			pstm02 = con.prepareStatement(sql02);
			pstm02.setString(1,account);
			pstm02.setString(2,"ATM입금");
			pstm02.setString(3,message);
			pstm02.setInt(4,in_money);
			pstm02.setInt(5,0);
			pstm02.setInt(6,getBalance(account));
			System.out.println("03. query 준비");
			
			res02 = pstm02.executeUpdate();
			System.out.println("04. query 실행 및 리턴");
			
			if(res01+res02==2) {
				commit(con);
			}else {
				rollBack(con);
			}
		} catch (SQLException e) {
			System.out.println("03/04 에러2");
			e.printStackTrace();
		}finally {
			close(pstm02);
			close(con);
		}
		
		return res01+res02;
	}
	
	public int Withdraw(String account, String message, int out_money) {
		Connection con = getConnection();
		PreparedStatement pstm01 = null;
		PreparedStatement pstm02 = null;
		int res01 = 0;
		int res02 = 0;
		String sql01 = " UPDATE BANK SET T_BALANCE = ? WHERE ACCOUNT = ? ";
		String sql02 = " INSERT INTO TRADE_LIST(ACCOUNT, TRADE_DATE, SENDER, MESSAGE, INPUT, OUTPUT, BALANCE) VALUES(?,SYSDATE,?,?,?,?,?) ";
		
		try {
			pstm01 = con.prepareStatement(sql01);
			pstm01.setInt(1,getBalance(account)-out_money);
			pstm01.setString(2,account);
			System.out.println("03. query 준비");
			
			res01 = pstm01.executeUpdate();
			System.out.println("04. query 실행 및 리턴");
		} catch (SQLException e) {
			System.out.println("03/04 에러1");
			e.printStackTrace();
		}finally {
			close(pstm01);
		}
		
		try {
			pstm02 = con.prepareStatement(sql02);
			pstm02.setString(1,account);
			pstm02.setString(2,"ATM출금");
			pstm02.setString(3,message);
			pstm02.setInt(4,0);
			pstm02.setInt(5,out_money);
			pstm02.setInt(6,getBalance(account));
			System.out.println("03. query 준비");
			
			res02 = pstm02.executeUpdate();
			System.out.println("03. query 실행 및 리턴");
			
			if(res01+res02==2) {
				commit(con);
			}else {
				rollBack(con);
			}
		} catch (SQLException e) {
			System.out.println("03/04 에러2");
			e.printStackTrace();
		}finally {
			close(pstm02);
			close(con);
		}
		
		return res01+res02;
	}
}
