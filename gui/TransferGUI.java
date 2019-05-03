package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.biz.BankBiz;
import com.dto.BankDto;
import java.awt.SystemColor;

public class TransferGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel banner_transfer;
	// 1.
	private JPanel panel_transfer_top, panel_transfer_mid, panel_transfer_bottom;
	private JButton btn_transfer, btn_goback;
	private JTextField txt_account, txt_money, txt_message;
	private JLabel label_account, label_money, label_message;
	private BankBiz biz = new BankBiz();
	private BankDto my = biz.selectOne("01085278040");
	private CheckPW passwordChk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransferGUI frame = new TransferGUI();
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
	public TransferGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// 2.
		panel_transfer_top = new JPanel();
		panel_transfer_top.setBounds(0, 0, 800, 100);
		panel_transfer_mid = new JPanel();
		panel_transfer_mid.setBackground(SystemColor.window);
		panel_transfer_mid.setBounds(0, 154, 800, 257);
		panel_transfer_bottom = new JPanel();
		panel_transfer_bottom.setBackground(SystemColor.window);
		panel_transfer_bottom.setBounds(0, 430, 800, 122);

		label_message = new JLabel("받는 분 통장메모");
		label_message.setBounds(143, 173, 150, 40);
		label_message.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label_message.setHorizontalAlignment(SwingConstants.CENTER);

		txt_message = new JTextField(40);
		txt_message.setBackground(SystemColor.text);
		txt_message.setBounds(294, 169, 354, 50);
		btn_transfer = new JButton("이   체");
		btn_transfer.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btn_transfer.setBounds(243, 6, 150, 50);
		btn_transfer.setBackground(SystemColor.control);
		btn_transfer.setForeground(new Color(0, 0, 0));
		btn_goback = new JButton("되돌아 가기");
		btn_goback.setBackground(SystemColor.control);
		btn_goback.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btn_goback.setBounds(422, 7, 150, 50);
		panel_transfer_bottom.add(btn_goback);
		contentPane.setLayout(null);

		panel_transfer_mid.setLayout(null);
		label_account = new JLabel("받는 분 계좌번호");
		label_account.setBounds(143, 47, 150, 40);
		label_account.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label_account.setHorizontalAlignment(SwingConstants.CENTER);

		panel_transfer_mid.add(label_account);
		txt_account = new JTextField(40);
		txt_account.setBackground(SystemColor.text);
		txt_account.setBounds(294, 43, 354, 50);
		panel_transfer_mid.add(txt_account);
		label_money = new JLabel("보내실 금액");
		label_money.setBounds(143, 110, 150, 40);
		label_money.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label_money.setHorizontalAlignment(SwingConstants.CENTER);
		panel_transfer_mid.add(label_money);
		txt_money = new JTextField(20);
		txt_money.setBackground(SystemColor.text);
		txt_money.setForeground(Color.BLACK);
		txt_money.setBounds(294, 106, 354, 50);
		panel_transfer_mid.add(txt_money);
		panel_transfer_mid.add(label_message);
		panel_transfer_mid.add(txt_message);
		panel_transfer_bottom.setLayout(null);
		panel_transfer_bottom.add(btn_transfer);

		// event
		btn_transfer.addActionListener(this);
		btn_goback.addActionListener(this);
		contentPane.setLayout(null);

		// 4.
		getContentPane().add(panel_transfer_top);
		ImageIcon img = new ImageIcon("images/banner_transfer.png");
		Image preImg = img.getImage(); // ImageIcon을 Image로 변환.
		Image afterImg = preImg.getScaledInstance(800, 100, java.awt.Image.SCALE_SMOOTH);
		ImageIcon afterIcon = new ImageIcon(afterImg); // Image로 ImageIcon 생성
		panel_transfer_top.setLayout(new GridLayout(0, 1, 0, 0));
		banner_transfer = new JLabel("");
		panel_transfer_top.add(banner_transfer);
		banner_transfer.setVerticalAlignment(SwingConstants.TOP);
		banner_transfer.setIcon(afterIcon);
		getContentPane().add(panel_transfer_mid);
		getContentPane().add(panel_transfer_bottom);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_transfer) {
			String trans_account = txt_account.getText();
			String trans_message = txt_message.getText();
			int trans_money = Integer.parseInt(txt_money.getText());
			transferMoney(my, trans_account, trans_money, trans_message);
		} else if (e.getSource() == btn_goback) {
			JOptionPane.showConfirmDialog(null, "이 페이지를 나가시겠습니까?", "되돌아 가기", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}
	}

	public void transferMoney(BankDto sender, String receiverAccount, int sendMoney, String message) {
		String processRes = "";

		// (1)계좌 유효성 검사
		if (biz.accountCheck(receiverAccount)) {
			System.out.println("++++++ 계좌 유효성 OK! ++++++");
			// (2)잔액 검사
			if (my.getT_balance() >= sendMoney) {
				System.out.println("++++++ 거래잔액 OK! ++++++");
				// (3-0) 본인계좌 여부확인
				if (!sender.getAccount().equals(receiverAccount)) {
					// (3)예금주 확인
					if (JOptionPane.showConfirmDialog(null,"[" + biz.selectOne(receiverAccount).getName() + "]님께 이체하시겠습니까?",
							"예금주 확인창", JOptionPane.YES_NO_OPTION) == 0) {
						// (4) 비밀번호 확인
						passwordChk = new CheckPW(this, "비밀번호 확인", sender);
						passwordChk.setVisible(true);
						if (passwordChk.isPwChk()) {
							System.out.println("++++++ 비밀번호 확인 OK! ++++++");
							// (5)내 계좌 출금
							processRes += biz.updateBalance(sender, sender.getAccount(), -sendMoney, "이체출금");
							// (6)상대 계좌 입금
							processRes += biz.updateBalance(sender, receiverAccount, sendMoney, message);
							if (processRes.equals("22")) {
								JOptionPane.showMessageDialog(null, "성공적으로 이체되었습니다.", "이체 완료!", JOptionPane.INFORMATION_MESSAGE);
								System.out.println("++++++ 계좌이체 OK! ++++++");
								setVisible(false);
							} else {
								JOptionPane.showMessageDialog(null, "이체가 실패되었습니다.\n다시 시도해주세요.", "이체 실패", JOptionPane.INFORMATION_MESSAGE);
								System.err.println("++++++ 계좌이체 실패! ++++++");
							}
						}
					}
				} else {
					System.err.println("++++++ 본인계좌 이체 Fail! ++++++");
					JOptionPane.showMessageDialog(null, "본인 계좌로 이체하실 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.err.println("++++++ 거래잔액 Fail! ++++++");
				JOptionPane.showMessageDialog(null, "잔액이 부족합니다.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			System.err.println("++++++ 계좌 유효성 Fail! ++++++");
			JOptionPane.showMessageDialog(null, "존재하지않는 계좌번호입니다.\n다시 확인해주세요", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}