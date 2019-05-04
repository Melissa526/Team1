package GuiJframe;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.Biz.BankBiz;
import com.dto.BankDto;

public class LookGui extends JFrame implements ActionListener,ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pan00,pan01,pan02;
	private JButton bBack,bLook;
	private JTextField fName;
	private JTextField fAccount;
	private JTextField fBalance;
	private JTable lookTable;
	private DefaultTableModel model;
	private BankBiz biz = new BankBiz();
	private BankDto logDto = biz.myaccount("01012345678");
	private String account = logDto.getAccount();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LookGui frame = new LookGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LookGui() {
		setTitle("::계좌조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pan00 = new JPanel();
		pan00.setBounds(0, 0, 800, 100);
		contentPane.add(pan00);
		pan00.setLayout(null);
		
		ImageIcon iconLogo = new ImageIcon("images/banner_showmeMy.png");
		JLabel BanLabel = new JLabel("");
		BanLabel.setIcon(iconLogo);
		BanLabel.setBounds(0, 0, 800, 100);
		pan00.add(BanLabel);
		this.getContentPane().add(pan00);
		
		pan01 = new JPanel();
		pan01.setBounds(0, 100, 800, 178);
		contentPane.add(pan01);
		pan01.setLayout(null);
		
		fName = new JTextField();
		fName.setEditable(false);
		fName.setBackground(SystemColor.menu);
		fName.setHorizontalAlignment(SwingConstants.CENTER);
		fName.setBounds(83, 46, 169, 30);
		fName.setText(logDto.getName());
		pan01.add(fName);
		fName.setColumns(10);
		
		fAccount = new JTextField();
		fAccount.setEditable(false);
		fAccount.setHorizontalAlignment(SwingConstants.CENTER);
		fAccount.setBackground(SystemColor.menu);
		fAccount.setBounds(338, 46, 169, 30);
		fAccount.setText(logDto.getAccount());
		pan01.add(fAccount);
		fAccount.setColumns(10);
		
		fBalance = new JTextField();
		fBalance.setEditable(false);
		fBalance.setFont(new Font("굴림", Font.BOLD, 17));
		fBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		fBalance.setBounds(196, 106, 239, 46);
		fBalance.setText(logDto.getT_balance()+"");
		pan01.add(fBalance);
		fBalance.setColumns(10);
		
		JLabel L00 = new JLabel("님의");
		L00.setBounds(276, 46, 58, 30);
		pan01.add(L00);
		
		JLabel L01 = new JLabel("계좌");
		L01.setBounds(533, 46, 58, 30);
		pan01.add(L01);
		
		JLabel L02 = new JLabel("잔액 :");
		L02.setBounds(124, 115, 58, 30);
		pan01.add(L02);
		
		JLabel L03 = new JLabel("원");
		L03.setBounds(449, 115, 58, 30);
		pan01.add(L03);
		
		bBack = new JButton("돌아가기");
		bBack.setBounds(587, 27, 142, 46);
		bBack.addActionListener(this);
		pan01.add(bBack);
		
		bLook = new JButton("조회하기");
		bLook.setBounds(587, 88, 142, 61);
		bLook.addActionListener(this);
		pan01.add(bLook);		
		this.getContentPane().add(pan01);
		
		pan02 = new JPanel();
		pan02.setBounds(0, 277, 800, 273);
		contentPane.add(pan02);
		pan02.setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(14, 37, 99, 34);
		pan02.add(comboBox);
		comboBox.addItem("입금+출금");
		comboBox.addItem("입금");
		comboBox.addItem("출금");
		comboBox.addItemListener(this);
		
		lookTable = new JTable();
		lookTable.setBackground(SystemColor.inactiveCaption);
		lookTable.setForeground(SystemColor.windowBorder);
		model = new DefaultTableModel();
		model.addColumn("거래날짜");
		model.addColumn("보낸분/받는분");
		model.addColumn("송금메모");
		model.addColumn("입금액");
		model.addColumn("출금액");
		model.addColumn("잔액");
		lookTable.setModel(model);
		lookTable.setSize(600,200);
		lookTable.setLocation(50,50);
		lookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(lookTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(122, 37, 630, 199);
		pan02.add(scrollPane);
		pan02.setVisible(false);
		this.getContentPane().add(pan02);
		this.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = e.getItem();
		BankBiz biz = new BankBiz();
		BankDto dto = biz.myaccount(account);
	if(e.getStateChange()==ItemEvent.SELECTED) {
		if(obj.equals("입금+출금")) {
			
			List<BankDto>tradeListAll = biz.tradeList(account);
			if(tradeListAll.size()==0) {
				JOptionPane.showMessageDialog(null,"거래 내역이 존재하지않습니다.");
			}else {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(this, tradeListAll.size()+"건의 내역이 존재합니다.");
				for(int i=0;i<tradeListAll.size();i++) {
					dto=(BankDto)tradeListAll.get(i);
					Date trade_date = dto.getTrade_date();
					String sender = dto.getSender();
					String message = dto.getMessage();
					int input = dto.getInput();
					int output = dto.getOutput();
					int balance = dto.getBalance();
					model.addRow(new Object[] {trade_date,sender,message,input,output,balance});
				}
				lookTable.setModel(model);
				
			}
		}else if(obj.equals("입금")) {
			
			List<BankDto> tradeListIn = biz.tradeListIn(account);
			if(tradeListIn.size()==0) {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null, "입금내역이 존재하지않습니다.");
			}else {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(this, tradeListIn.size()+"건의 입금 내역이 존재합니다.");
				for(int i =0 ;i<tradeListIn.size();i++) {
					dto=(BankDto)tradeListIn.get(i);
					Date trade_date =dto.getTrade_date();
					String sender = dto.getSender();
					String message = dto.getMessage();
					int input = dto.getInput();
					int output = dto.getOutput();
					int balance = dto.getBalance();
					model.addRow(new Object[] {trade_date,sender,message,input,output,balance});
				}
				lookTable.setModel(model);
				
				
			}
		}else if(obj.equals("출금")) {
			
			List<BankDto> tradeListOut = biz.tradeListOut(account);
			if(tradeListOut.size()==0) {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null, "출금내역이 존재하지 않습니다.");
			}else {
				model.setRowCount(0);
				JOptionPane.showMessageDialog(this,tradeListOut.size()+"건의 출금 내역이 존재합니다.");
				for(int i = 0 ; i<tradeListOut.size();i++) {
				dto = (BankDto)tradeListOut.get(i);
				Date trade_date =dto.getTrade_date();
				String sender = dto.getSender();
				String message = dto.getMessage();
				int input = dto.getInput();
				int output = dto.getOutput();
				int balance = dto.getBalance();	
				model.addRow(new Object[] {trade_date,sender,message,input,output,balance});	
				}

				lookTable.setModel(model);
			
				
			}
		}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bBack) {
			JOptionPane.showConfirmDialog(null, "메인으로 돌아가시겠습니까?");
		}else if(e.getSource()==bLook) {
			pan02.setVisible(true);
			Lookup();
		}
	}

	private void Lookup() {
		List<BankDto> lookList = biz.tradeList(account);
		if(lookList.size()==0) {
			JOptionPane.showMessageDialog(null, "해당 계좌내역이 존재하지않는다.");
		}else {
			model.setRowCount(0);
			//JOptionPane.showMessageDialog(this, lookList.size()+"건의 내역이 존재합니다.");
			for(int i =0;i<lookList.size();i++) {
				BankDto dto =(BankDto)lookList.get(i);
				Date trade_date =dto.getTrade_date();
				String sender = dto.getSender();
				String message = dto.getMessage();
				int input = dto.getInput();
				int output = dto.getOutput();
				int balance = dto.getBalance();
				model.addRow(new Object[] {trade_date,sender,message,input,output,balance});
			}
				lookTable.setModel(model);
			
		}
	}
}
