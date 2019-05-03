package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.BankDto;

import common.JDBCTemplate;

public class LookupDao extends JDBCTemplate{
	//나의계좌 조회 - > 계좌번호, 계좌잔액, 이름 
	public BankDto myaccount(String account) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = " SELECT * FROM BANK WHERE ACCOUNT=? ";
		BankDto dto = new BankDto();
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,account);
			rs=pstm.executeQuery();
			while(rs.next()) {
				dto.setAccount(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setPassword(rs.getString(3));
				dto.setT_balance(rs.getInt(4));
				dto.setReg_date(rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs,pstm,con);
		}
		
		return dto;
	}
	
	
	//거래내역조회 -> 계좌번호 , 거래날짜, 송금인 , 메시지 ,출금금액,입금금액,거래후잔액
	public List<BankDto> tradeList(String account){
		
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<BankDto> list = new ArrayList<BankDto>();
		String sql = " SELECT * FROM TRADE_LIST WHERE ACCOUNT=? "+
		               " ORDER BY TRADE_DATE DESC";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,account);
			rs =pstm.executeQuery();
			while(rs.next()) {
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
			close(rs,pstm,con);
		}	
		return list;
	}
	public List<BankDto> tradeListIn(String account){
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<BankDto> list = new ArrayList<BankDto>();
		String sql = " SELECT * FROM TRADE_LIST WHERE ACCOUNT=? AND OUTPUT=0"+
		               " ORDER BY TRADE_DATE DESC";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,account);
			rs =pstm.executeQuery();
			while(rs.next()) {
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
			close(rs,pstm,con);
		}	
		return list;
	}
	public List<BankDto> tradeListOut(String account){
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<BankDto> list = new ArrayList<BankDto>();
		String sql = " SELECT * FROM TRADE_LIST WHERE ACCOUNT=? AND INPUT=0"+
		               " ORDER BY TRADE_DATE DESC";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,account);
			rs =pstm.executeQuery();
			while(rs.next()) {
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
			close(rs,pstm,con);
		}	
		return list;
	}
	
	
}
