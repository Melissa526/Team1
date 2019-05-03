package com.Biz;

import java.util.List;

import com.dao.LookupDao;
import com.dto.BankDto;

public class BankBiz {
	private LookupDao dao = new LookupDao();
	
	public BankDto myaccount(String account) {
		return dao.myaccount(account);
	}
	
	public List<BankDto> tradeList(String account){
		return dao.tradeList(account);
	}
	public List<BankDto> tradeListIn(String account){
		return dao.tradeListIn(account);
	}
	public List<BankDto> tradeListOut(String account){
		return dao.tradeListOut(account);
	}
}
