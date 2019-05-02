package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dto.BankDto;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.SystemColor;

public class CheckPW extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btn4;
	private JButton btn5;
	private JButton btn7;
	private JButton btn6;
	private JButton btn8;
	private JButton btn9;
	private JLabel label_whitespace;
	private JButton btn0;
	private JButton btn_confirm;
	private JLabel label_txt;
	private JTextField txtPW1;
	private JTextField txtPW4;
	private JTextField txtPW2;
	private JTextField txtPW3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckPW frame = new CheckPW();
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
	public CheckPW() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 600);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_insertPW = new JPanel();
		panel_insertPW.setBounds(5, 270, 340, 300);
		panel_insertPW.setLayout(new GridLayout(4, 3, 0, 0));
		panel_insertPW.setPreferredSize(new Dimension(350, 500));
		
		JButton btn1 = new JButton("1");
		btn1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn1);
		
		JButton btn2 = new JButton("2");
		btn2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		panel_insertPW.add(btn2);
		
		JButton btn3 = new JButton("3");
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
		
		btn_confirm = new JButton("확  인");
		btn_confirm.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel_insertPW.add(btn_confirm);
		contentPane.add(panel_insertPW);
		
		JPanel panel_showPW = new JPanel();
		panel_showPW.setBounds(5, 150, 340, 80);
		panel_showPW.setLayout(new BoxLayout(panel_showPW, BoxLayout.X_AXIS));
		
		txtPW1 = new JTextField();
		txtPW1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtPW1.setColumns(10);
		txtPW1.setEditable(false);
		panel_showPW.add(txtPW1);
		
		txtPW2 = new JTextField();
		txtPW2.setColumns(10);
		txtPW2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		panel_showPW.add(txtPW2);
		
		txtPW3 = new JTextField();
		txtPW3.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		panel_showPW.add(txtPW3);
		txtPW3.setColumns(10);
		
		txtPW4 = new JTextField();
		txtPW4.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtPW4.setEditable(false);
		txtPW4.setColumns(10);
		panel_showPW.add(txtPW4);
		contentPane.add(panel_showPW);
		
		label_txt = new JLabel("비밀번호를 입력하세요.");
		label_txt.setBounds(102, 67, 150, 20);
		label_txt.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label_txt.setEnabled(false);
		label_txt.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_txt);
		
		btn_confirm.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==btn_confirm) {
		}
		
	}
	
	public boolean passwordCheck(BankDto dto, ActionEvent e) {
		boolean YN = false;
		

		return false;
	}
	
	

}
