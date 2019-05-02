package com.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.dto.BankDto;

public class CheckPW extends JDialog {

	private JPanel contentPane;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn7;
	private JButton btn6;
	private JButton btn8;
	private JButton btn9;
	private JLabel label_whitespace;
	private JButton btn0;
	private JButton btn_delete;
	private JLabel label_txt;
	private JTextField txtPW1;
	private JTextField txtPW4;
	private JTextField txtPW2;
	private JTextField txtPW3;

	private BankDto my;
	private StringBuffer pwArr = new StringBuffer();
	private boolean pwChk = false;
	int btnCnt = 0;
	
	/**
	 * Create the frame.
	 */
	public CheckPW(JFrame onwer, String title, BankDto dto) {
		super(onwer, title, true);
		my = dto;
		
		setBounds(100, 100, 350, 600);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel_insertPW = new JPanel();
		panel_insertPW.setBounds(5, 270, 340, 300);
		panel_insertPW.setLayout(new GridLayout(4, 3, 0, 0));
		panel_insertPW.setPreferredSize(new Dimension(350, 500));

		btn1 = new JButton("1");
		btn1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn1);

		btn2 = new JButton("2");
		btn2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn2);

		btn3 = new JButton("3");
		btn3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn3);

		btn4 = new JButton("4");
		btn4.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn4);

		btn5 = new JButton("5");
		btn5.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn5);

		btn6 = new JButton("6");
		btn6.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn6);

		btn7 = new JButton("7");
		btn7.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		contentPane.setLayout(null);
		panel_insertPW.add(btn7);

		btn8 = new JButton("8");
		btn8.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn8);

		btn9 = new JButton("9");
		btn9.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn9);

		label_whitespace = new JLabel(" ");
		panel_insertPW.add(label_whitespace);

		btn0 = new JButton("0");
		btn0.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn0);

		btn_delete = new JButton("◁");
		btn_delete.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel_insertPW.add(btn_delete);
		contentPane.add(panel_insertPW);

		JPanel panel_showPW = new JPanel();
		panel_showPW.setBounds(5, 150, 340, 80);
		panel_showPW.setLayout(new BoxLayout(panel_showPW, BoxLayout.X_AXIS));

		txtPW1 = new JTextField("");
		txtPW1.setHorizontalAlignment(SwingConstants.CENTER);
		txtPW1.setEditable(false);
		txtPW1.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		txtPW1.setColumns(10);
		panel_showPW.add(txtPW1);

		txtPW2 = new JTextField();
		txtPW2.setHorizontalAlignment(SwingConstants.CENTER);
		txtPW2.setEditable(false);
		txtPW2.setColumns(10);
		txtPW2.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		panel_showPW.add(txtPW2);

		txtPW3 = new JTextField();
		txtPW3.setHorizontalAlignment(SwingConstants.CENTER);
		txtPW3.setEditable(false);
		txtPW3.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		panel_showPW.add(txtPW3);
		txtPW3.setColumns(10);

		txtPW4 = new JTextField();
		txtPW4.setHorizontalAlignment(SwingConstants.CENTER);
		txtPW4.setEditable(false);
		txtPW4.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		txtPW4.setColumns(10);
		panel_showPW.add(txtPW4);
		contentPane.add(panel_showPW);

		label_txt = new JLabel("비밀번호를 입력하세요.");
		label_txt.setBounds(102, 67, 150, 20);
		label_txt.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label_txt.setEnabled(false);
		label_txt.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_txt);

		myEvent my = new myEvent();
		btn_delete.addActionListener(my);
		btn1.addActionListener(my);
		btn2.addActionListener(my);
		btn3.addActionListener(my);
		btn4.addActionListener(my);
		btn5.addActionListener(my);
		btn6.addActionListener(my);
		btn7.addActionListener(my);
		btn8.addActionListener(my);
		btn9.addActionListener(my);
		btn0.addActionListener(my);
	}

	class myEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == btn_delete) {
				if (!txtPW4.getText().equals("")) {
					pwArr.deleteCharAt(3);
					txtPW4.setText("");
					btnCnt--;
				} else if (!txtPW3.getText().equals("")) {
					pwArr.deleteCharAt(2);					
					txtPW3.setText("");
					btnCnt--;
				} else if (!txtPW2.getText().equals("")) {
					pwArr.deleteCharAt(1);
					txtPW2.setText("");
					btnCnt--;
				} else if (!txtPW1.getText().equals("")) {
					pwArr.deleteCharAt(0);
					txtPW1.setText("");
					btnCnt--;
				}
				//dispose();
			} else {
				String numberOfbtn = (((JButton) e.getSource()).getText());
				System.out.println(pwArr);
				if(btnCnt<4) {
					pwArr.append(numberOfbtn);
					if (txtPW1.getText().equals("")) {
						txtPW1.setText("●");
					} else if (txtPW2.getText().equals("")) {
						txtPW2.setText("●");
					} else if (txtPW3.getText().equals("")) {
						txtPW3.setText("●");
					} else if (txtPW4.getText().equals("")) {
						txtPW4.setText("●");
					}
					btnCnt++;
				}
				if(pwArr.length()==4) {
					System.out.println(pwArr.toString());
					if(my.getPassword().equals(pwArr.toString())) {
						pwChk = true;
						System.out.println("(#)비밀번호 일치");
						setVisible(false);
						//dispose();
					}else {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다!!", "비밀번호 오류", JOptionPane.ERROR_MESSAGE);					}
				}
				
			}
			
		}
	}	
	
	public boolean isPwChk() {
		return pwChk;
	}
}
