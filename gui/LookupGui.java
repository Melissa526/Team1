package com.Gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.Biz.BankBiz;
import com.dao.LookupDao;
import com.dto.BankDto;
import javax.swing.UIManager;

public class LookupGui implements ActionListener,ItemListener {
	List<BankDto> tradeList;
	LookupDao dao ;
	
	JFrame frame;
	JPanel pan02;
	JTextField fname,faccount,fbalance;
	JTable Looktable;
	JButton bLookup ;
	JTextArea area;
	DefaultTableModel model;	
	String name,account;
	int balance;
	private final JPanel pan00 = new JPanel();
	private JLabel imageLabel;
	private JButton bBack;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LookupGui window = new LookupGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LookupGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("::계좌조회::");
		frame.setResizable(false);
		frame.setForeground(Color.GRAY);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel pan01 = new JPanel();
		pan01.setBounds(0, 100, 800, 170);
		pan01.setForeground(Color.GRAY);
		frame.getContentPane().add(pan01);
		pan01.setLayout(null);
		
		JLabel label = new JLabel("님의 ");
		label.setBounds(276, 46, 58, 30);
		pan01.add(label);
		
		fname = new JTextField();
		fname.setHorizontalAlignment(SwingConstants.CENTER);
		fname.setBackground(SystemColor.menu);
		fname.setBounds(83, 46, 169, 30);
		pan01.add(fname);
		fname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("계좌");
		lblNewLabel.setBounds(533, 46, 58, 30);
		pan01.add(lblNewLabel);
		
		faccount = new JTextField();
		faccount.setHorizontalAlignment(SwingConstants.CENTER);
		faccount.setFont(new Font("굴림", Font.PLAIN, 15));
		faccount.setBackground(SystemColor.menu);
		faccount.setBounds(338, 46, 169, 30);
		pan01.add(faccount);
		faccount.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("잔액");
		lblNewLabel_1.setBounds(124, 115, 58, 30);
		pan01.add(lblNewLabel_1);
		
		fbalance = new JTextField();
		fbalance.setHorizontalAlignment(SwingConstants.RIGHT);
		fbalance.setFont(new Font("굴림", Font.BOLD, 17));
		fbalance.setBackground(SystemColor.menu);
		fbalance.setBounds(196, 106, 239, 46);
		pan01.add(fbalance);
		fbalance.setColumns(10);
		
		bLookup = new JButton("거래조회하기");
		bLookup.setBackground(new Color(32, 178, 170));
		bLookup .setBounds(587, 88, 142, 61);
		bLookup.addActionListener(this);
		pan01.add(bLookup);
		
		JLabel label_1 = new JLabel("원");
		label_1.setBounds(449, 115, 58, 30);
		pan01.add(label_1);
		
		bBack = new JButton("메인으로가기");
		bBack.setBackground(new Color(255, 255, 255));
		bBack.setBounds(587, 27, 142, 46);
		bBack.addActionListener(this);
		pan01.add(bBack);
		
		pan02 = new JPanel();
		pan02.setBounds(0, 270, 800, 280);
		pan02.setLayout(null);
		frame.getContentPane().add(pan02);
		model = new DefaultTableModel();
		model.addColumn("거래날짜");
		model.addColumn("보낸분/받는분");
		model.addColumn("송금메모");
		model.addColumn("입금액");
		model.addColumn("출금액");
		model.addColumn("잔액");
		
		Looktable = new JTable();
		Looktable.setBackground(SystemColor.inactiveCaption);
		Looktable.setForeground(SystemColor.windowBorder);
		Looktable.setFont(new Font("굴림", Font.PLAIN, 15));
		
		Looktable.setModel(model);
		Looktable.setSize(600, 200);
		Looktable.setLocation(50, 50);		
		Looktable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPane01 = new JScrollPane(Looktable);
		scrollPane01.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane01.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane01.setBounds(121, 25, 630, 199);
		pan02.add(scrollPane01);
		
		JComboBox<String> combo = new JComboBox<String>();
		combo.setBounds(14, 25, 99, 34);
		pan02.add(combo);
		combo.addItem("입금+출금");
		combo.addItem("입금");
		combo.addItem("출금");
		combo.addItemListener(this);
		pan00.setBounds(0, 0, 800, 100);
		frame.getContentPane().add(pan00);
		pan00.setLayout(null);
		
		ImageIcon iconLogo = new ImageIcon("images/banner_showmeMy.png");
		imageLabel = new JLabel("");
		imageLabel.setIcon(iconLogo);
		imageLabel.setBounds(0, 0, 800, 100);
		pan00.add(imageLabel);
		pan02.setVisible(false);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bLookup) {
			pan02.setVisible(true);
			LookupList();
			bLookup.setVisible(true);
		}else if(e.getSource()==bBack) {
			JOptionPane.showConfirmDialog(null, "메인으로돌아가시겠습니까?");
		}
	}
	public void LookupList() {
		account = faccount.getText();
		if(account.contentEquals("")) {
			JOptionPane.showMessageDialog(null, "계좌번호가 제데로들어오지못하였다.");
		}else { 
			BankBiz biz = new BankBiz();
			BankDto dto = biz.myaccount(account);
			
			fname.setText(dto.getName());
			faccount.setText(dto.getAccount());
			fbalance.setText(""+(dto.getT_balance()));
		}
		if(account.contentEquals("")) {
			JOptionPane.showMessageDialog(null, "계좌번호가 제데로들어오지못하였다.");
		}else {
			BankBiz biz = new BankBiz();
			tradeList = biz.tradeList(account);
			
			if(tradeList.size()==0) {
				JOptionPane.showMessageDialog(null, "해당계좌가없습니다.");
			}else {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null,tradeList.size()+"건의 내역이 존재합니다.");
				for(int i = 0 ; i<tradeList.size();i++) {
				BankDto dto = (BankDto)tradeList.get(i);
				
				String account =dto.getAccount();
				Date trade_date =dto.getTrade_date();
				String sender = dto.getSender();
				String message = dto.getMessage();
				int input = dto.getInput();
				int output = dto.getOutput();
				int balance = dto.getBalance();
				
				model.addRow(new Object[] {trade_date,sender,message,input,output,balance});	
				}

				Looktable.setModel(model);
			}
		
		
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		Object obj = e.getItem();
		account = faccount.getText();
		BankBiz biz = new BankBiz();
		BankDto dto = biz.myaccount(account);
		
		if(obj.equals("입금+출금")) {
			List<BankDto> tradeListAll = biz.tradeList(account);
			if(tradeList.size()==0) {
				JOptionPane.showMessageDialog(null, "해당내역이없습니다.");
			}else {
				model.setRowCount(0);
				for(int i = 0 ; i<tradeListAll.size();i++) {
				dto = (BankDto)tradeListAll.get(i);
				
				String account =dto.getAccount();
				Date trade_date =dto.getTrade_date();
				String sender = dto.getSender();
				String message = dto.getMessage();
				int input = dto.getInput();
				int output = dto.getOutput();
				int balance = dto.getBalance();
				
				model.addRow(new Object[] {trade_date,sender,message,input,output,balance});	
				}

				Looktable.setModel(model);
			}
		}else if(obj.equals("입금")) {
			List<BankDto> tradeListIn = biz.tradeListIn(account);
			if(tradeListIn.size()==0) {
				JOptionPane.showMessageDialog(null, "입금내역이 존재하지않습니다.");
			}else {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null,tradeListIn.size()+"건의 입금 내역이 존재합니다.");
				for(int i = 0 ; i<tradeListIn.size();i++) {
				dto = (BankDto)tradeListIn.get(i);
				
				String account =dto.getAccount();
				Date trade_date =dto.getTrade_date();
				String sender = dto.getSender();
				String message = dto.getMessage();
				int input = dto.getInput();
				int output = dto.getOutput();
				int balance = dto.getBalance();
				
				model.addRow(new Object[] {trade_date,sender,message,input,output,balance});	
				}

				Looktable.setModel(model);
			}
		}else if(obj.equals("출금")) {
			List<BankDto> tradeListOut = biz.tradeListOut(account);
			if(tradeListOut.size()==0) {
				JOptionPane.showMessageDialog(null, "출금내역이 존재하지 않습니다.");
			}else {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null,tradeListOut.size()+"건의 출금 내역이 존재합니다.");
				for(int i = 0 ; i<tradeListOut.size();i++) {
				dto = (BankDto)tradeListOut.get(i);
				
				String account =dto.getAccount();
				Date trade_date =dto.getTrade_date();
				String sender = dto.getSender();
				String message = dto.getMessage();
				int input = dto.getInput();
				int output = dto.getOutput();
				int balance = dto.getBalance();
				
				model.addRow(new Object[] {trade_date,sender,message,input,output,balance});	
				}

				Looktable.setModel(model);
			}
		}
		
	}
}
