package com.dao;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dto.BankDto;

public class BankDao {
	
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
		String outputSql = " INSERT INTO TRADE_LIST VALUES(?,SYSDATE,?,?,?,?,?) ";
		
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
		String sql02 = " INSERT INTO TRADE_LIST(ACCOUNT, TRADE_DATE, SENDER, MESSAGE, IN_MONEY, OUT_MONEY, BALANCE) VALUES(?,SYSDATE,?,?,?,?,?) ";
		
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
		} catch (SQLException e) {
			System.out.println("03/04 에러2");
			e.printStackTrace();
		}finally {
			close(pstm02,con);
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
		String sql02 = " INSERT INTO TRADE_LIST(ACCOUNT, TRADE_DATE, SENDER, MESSAGE, IN_MONEY, OUT_MONEY, BALANCE) VALUES(?,SYSDATE,?,?,?,?,?) ";
		
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
		} catch (SQLException e) {
			System.out.println("03/04 에러2");
			e.printStackTrace();
		}finally {
			close(pstm02,con);
		}
		
		return res01+res02;
	}

}







