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
import java.awt.Cursor;
import java.awt.event.*;
import client.*;
public class LoginGUI {
	
	private NetClient netClient;
	private JFrame frmEzgather;
	private JTextField text_IP;
	private JTextField text_PORT;
	private JTextField text_ID;
	private String IP = "", PORT = "", ID = "";
	private Boolean flag = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
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
	public LoginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEzgather = new JFrame();
		frmEzgather.getContentPane().setBackground(new Color(255, 250, 250));
		frmEzgather.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginGUI.class.getResource("/resource/ez2gather.png")));
		frmEzgather.setTitle("Ez2Gather");
		frmEzgather.setBounds(100, 100, 316, 173);
		frmEzgather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEzgather.setResizable(false);
		
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
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
	public boolean empty() {
		boolean empty = false;
		
		if (text_IP.getText().equals("") || text_PORT.getText().equals("") || text_ID.getText().equals("")) {
			empty = true;
		}
		
		return empty;
	}
	public void clickLogin() { //login�쓣 �닃���쓣 �븣
		IP = text_IP.getText();
		PORT = text_PORT.getText();
		ID = text_ID.getText();
		
		if (empty())		//IP�삉�뒗 PORT�삉�뒗 ID李쎌씠 鍮꾩뿀�쓣 �븣
			JOptionPane.showMessageDialog(null, "Please enter IP, PORT or ID", "error", JOptionPane.OK_OPTION);
		
		else {
			boolean login = false;
			frmEzgather.dispose();
			netClient = new NetClient(IP, Integer.parseInt(PORT), ID);
			netClient.connectToServer();
			login = netClient.sendMessage("LOGIN");
			//request login to client
			
			if (!login) {	//login F
				try {
					LoginGUI window = new LoginGUI();
					window.frmEzgather.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Please check IP, PORT or ID", "error", JOptionPane.OK_OPTION);
			}
			else {			//login S
				SchedulerGUI Scheduler = new SchedulerGUI();
				Scheduler.launchSceduler(netClient);
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
