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

}







