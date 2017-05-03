package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.*;

public class SchedulerGui {

	private JFrame frmEzgather;
	private JTextField text_IP;
	private JTextField text_PORT;
	private JTextField text_ID;
	private char key;
	private String IP = "", PORT = "", ID = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchedulerGui window = new SchedulerGui();
					window.frmEzgather.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SchedulerGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEzgather = new JFrame();
		frmEzgather.getContentPane().setBackground(new Color(255, 250, 250));
		frmEzgather.setIconImage(Toolkit.getDefaultToolkit().getImage(SchedulerGui.class.getResource("/resource/ez2gather.png")));
		frmEzgather.setTitle("Ez2Gather");
		frmEzgather.setBounds(100, 100, 316, 173);
		frmEzgather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		text_IP = new JTextField();
		text_IP.addKeyListener(new EnterKeyListener());
		text_IP.setColumns(10);
		
		text_PORT = new JTextField();
		text_PORT.addKeyListener(new EnterKeyListener());
		text_PORT.setColumns(10);
		
		text_ID = new JTextField();
		text_ID.addKeyListener(new EnterKeyListener());
		text_ID.setColumns(10);
		
		JTextPane txtpnIP = new JTextPane();
		txtpnIP.setBackground(new Color(255, 250, 250));
		txtpnIP.setEnabled(false);
		txtpnIP.setEditable(false);
		txtpnIP.setText("IP");
		
		JTextPane txtpnPORT = new JTextPane();
		txtpnPORT.setBackground(new Color(255, 250, 250));
		txtpnPORT.setText("PORT");
		txtpnPORT.setEnabled(false);
		txtpnPORT.setEditable(false);
		
		JTextPane txtpnID = new JTextPane();
		txtpnID.setBackground(new Color(255, 250, 250));
		txtpnID.setText("ID");
		txtpnID.setEnabled(false);
		txtpnID.setEditable(false);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new LoginActionListener());
		btnLogin.addKeyListener(new EnterKeyListener());
		btnLogin.setBackground(new Color(230, 230, 250));
		GroupLayout groupLayout = new GroupLayout(frmEzgather.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnIP, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtpnID, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtpnPORT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(text_ID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(btnLogin))
						.addComponent(text_PORT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(text_IP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(text_IP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtpnIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(text_PORT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtpnPORT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(text_ID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLogin)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		frmEzgather.getContentPane().setLayout(groupLayout);
	}
	public boolean check() {
		boolean empty = false;
		
		if (text_IP.getText().equals("")) {
			text_IP.setText("Enter IP");
			empty = true;
		}
		if (text_PORT.getText().equals("")) {
			text_PORT.setText("Enter PORT");
			empty = true;
		}
		if (text_ID.getText().equals("")) {
			text_ID.setText("Enter ID");
			empty = true;
		}
		
		return empty;
	}
	public void clickLogin() { //login¿ª ¥≠∑∂¿ª∂ß
		IP = text_IP.getText();
		PORT = text_PORT.getText();
		ID = text_ID.getText();
		
		if (!check()) {
			frmEzgather.dispose();
		
			//request login to client
			
			if (/*login fail*/true) {
				try {
					SchedulerGui window = new SchedulerGui();
					window.frmEzgather.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "check IP, PORT or ID", "error", JOptionPane.OK_OPTION);
			}
		}
	}
	class LoginActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			clickLogin();
		}
	}
	class EnterKeyListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10)
				clickLogin();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
