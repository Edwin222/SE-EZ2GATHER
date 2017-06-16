package gui;

import java.awt.EventQueue;
import java.awt.Font;
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
import java.util.ArrayList;

import client.*;
import common.*;

public class SchedulerGUI {
	
	private NetClient netClient;
	private JFrame frame;
	private Image cell, time, today, renewal, setting;
	private Image p[] = new Image[9];
	private Image cellFrame;
	private int personNumber = 8;//netClient.getPerson_Number();
	private JTextPane noticeTextPane = new JTextPane();
	private String notice = "공지사항 : ";
	private ScheduleUnit scheduleUnit;
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
		PersonComponent personComp = new PersonComponent();
		JoinableComponent joinableComp = new JoinableComponent();
		panel.setBounds(10, 80, 650, 500);
		//joinableComp.setBounds(50, 100, 600, 350);
		personComp.setBounds(670, 100, 80, 200);
		renewalComp.addMouseListener(new RefreshClick());
		renewalComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		renewalComp.setBounds(670, 430, 20, 20);
		settingComp.addMouseListener(new RefreshClick());
		settingComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		settingComp.setBounds(700, 430, 20, 20);
		joinableComp.setBounds(40, 20, 600, 350);
		
		frame.getContentPane().add(panel);
		frame.getContentPane().add(renewalComp);
		frame.getContentPane().add(settingComp);
		frame.getContentPane().add(personComp);
		frame.getContentPane().add(joinableComp);
		frame.getContentPane().setComponentZOrder(joinableComp, 0);
		frame.getContentPane().setComponentZOrder(panel, 3);
		//frame.getContentPane().add(joinableComp);
		
		noticeTextPane.setEditable(false);
		noticeTextPane.setText(notice);
		noticeTextPane.setFont(new Font(Font.DIALOG,0,20));
		noticeTextPane.setBackground(frame.getBackground());
		noticeTextPane.setBounds(50, 30, 500, 30);
		frame.getContentPane().add(noticeTextPane);
		
		//panel.setVisible(true);
		//renewalComp.setVisible(true);
		//settingComp.setVisible(true);
	}
	class MyPanel extends JPanel {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			System.out.println("draw");
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
	class PersonComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			
			for (int i = 1; i <= personNumber; i++) {
				String ident = String.format("%s%d%s", "/resource/p", i, ".png");
				p[i] = tkit.getImage(SchedulerGUI.class.getResource(ident));
			}
			super.paintComponent(g);
			for (int i = 0; i < personNumber; i++) {
				g.drawImage(p[i+1], 0, i * 20 + 4, 10, 10, this);
				JTextPane nameTextPane = new JTextPane();
				nameTextPane.setBackground(getBackground());
				nameTextPane.setEditable(false);
				nameTextPane.setText("홍길동");
				nameTextPane.setBounds(15, i * 20, 50, 15);
				add(nameTextPane);
				//frame.getContentPane().add(nameTextPane);
			}
			
		}
	}
	class JoinableComponent extends JComponent {
		Toolkit tkit;
		ArrayList<Integer> people, oldPeople;
		int begin = 0;
		
		
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			cellFrame = tkit.getImage(SchedulerGUI.class.getResource("/resource/HL.png"));
			super.paintComponent(g);
			g.drawImage(cellFrame, 0, 0, 600, 4, this);
			g.drawImage(cellFrame, 0, 0, 4, 350, this);
			g.drawImage(cellFrame, 600, 0, -4, 350, this);
			g.drawImage(cellFrame, 0, 350, 600, -4, this);
			g.drawImage(cellFrame, 0, 10, 4, 350, this);
			g.drawImage(cellFrame, 0, 350/3-2, 4, 350, this);
			g.drawImage(cellFrame, 0, 350/2-2, 4, 350, this);
			g.drawImage(cellFrame, 0, 350/3*2-2, 4, 350, this);
			g.drawImage(cellFrame, 0, 350/6*5-2, 4, 350, this);
			
			//600, 350
			/*for (int j = 0; j < 6; j++) {
				begin = 0;
				for (int i = 1; i < 12; i++) {
					//people = netClient.getManager().show_organized_table(i, j);
					//oldPeople = netClient.getManager().show_organized_table(i-1, j);
					if (/*people.equals(oldPeople)true) {
					}
					else {
						g.drawImage(cellFrame, j * 100, begin * (350 / 12), 100, (i - begin + 1) * (350 / 12), this);
						begin = i;
					}
				}
				g.drawImage(cellFrame, j * 100, begin * (350 / 12), 100, (12 - begin) * (350 / 12), this);
			}*/
			//this.setVisible(false);
			//this.setVisible(true);
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
