package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.*;
import client.*;

public class SchedulerGUI {
	
	private NetClient netClient;
	private JFrame frame;
	private Image cell, time, today, renewal, setting;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args)*/
	public void launchSceduler(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchedulerGUI window = new SchedulerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SchedulerGUI(NetClient nc) {
		this.netClient = nc;
		initialize();
	}
	public SchedulerGUI() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		MyPanel panel = new MyPanel();
		RenewalComponent renewalComp = new RenewalComponent();
		SettingComponent settingComp = new SettingComponent();
		renewalComp.addMouseListener(new RefreshClick());
		renewalComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		renewalComp.setBounds(670, 420, 20, 20);
		settingComp.addMouseListener(new RefreshClick());
		settingComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		settingComp.setBounds(700, 420, 20, 20);
		panel.setBounds(10, 80, 650, 500);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(renewalComp);
		frame.getContentPane().add(settingComp);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("공지사항 받아와서 출력하기");
		textPane.setBounds(200, 17, 500, 25);
		frame.getContentPane().add(textPane);
		panel.setVisible(true);
		renewalComp.setVisible(true);
		settingComp.setVisible(true);
	}
	class MyPanel extends JPanel {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			cell = tkit.getImage(SchedulerGUI.class.getResource("/resource/SchedulerBack.png"));
			time = tkit.getImage(SchedulerGUI.class.getResource("/resource/time.png"));
			today = tkit.getImage(SchedulerGUI.class.getResource("/resource/today.png"));
			//refresh = tkit.getImage(SchedulerGUI.class.getResource("/resource/refresh.png"));
			//setting = tkit.getImage(SchedulerGUI.class.getResource("/resource/setting.png"));
			super.paintComponent(g);
			g.drawImage(cell, 40, 20, 600, 350, this);
			g.drawImage(time, 0, 20, 25, 350, this);
			g.drawImage(today, 60, 0, 60, 20, this);
		}
	}
	class RenewalComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			renewal = tkit.getImage(SchedulerGUI.class.getResource("/resource/refresh.png"));
			super.paintComponent(g);
			g.drawImage(renewal, 0, 0, 20, 20, this);
		}
	}
	
	class SettingComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			renewal = tkit.getImage(SchedulerGUI.class.getResource("/resource/setting.png"));
			super.paintComponent(g);
			g.drawImage(renewal, 0, 0, 20, 20, this);
		}
	}
	
	class RefreshClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			netClient.connectToServer();
			netClient.sendMessage("RENEWAL");
			netClient.endConnection();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	class SettingClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			netClient.getManager().Open_Edit();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
