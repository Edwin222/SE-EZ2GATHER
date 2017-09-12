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
	private int personNumber;
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
					window.frame.setVisible(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void launchSceduler(NetClient nc){
		EventQueue.invokeLater(new Launcher(nc));
	}
	
	private class Launcher implements Runnable {
		NetClient nc;
		
		public Launcher(NetClient nc){
			this.nc = nc;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				SchedulerGUI window = new SchedulerGUI(nc);
				window.frame.setVisible(false);
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Create the application.
	 */
	public SchedulerGUI(NetClient nc) {
		this.netClient = nc;
		this.personNumber = netClient.getManager().getPersonNum();
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
		frame.setTitle("EZ2GATHER");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginGUI.class.getResource("/resource/ez2gather.png")));
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		MyPanel panel = new MyPanel();
		RenewalComponent renewalComp = new RenewalComponent();
		SettingComponent settingComp = new SettingComponent();
		PersonComponent personComp = new PersonComponent();
		//JoinableComponent joinableComp = new JoinableComponent();
		panel.setBounds(10, 80, 650, 500);
		//joinableComp.setBounds(50, 100, 600, 350);
		personComp.setBounds(670, 100, 80, 200);
		renewalComp.addMouseListener(new RefreshClick());
		renewalComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		renewalComp.setBounds(670, 430, 20, 20);
		settingComp.addMouseListener(new SettingClick());
		settingComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		settingComp.setBounds(700, 430, 20, 20);
		//joinableComp.setBounds(40, 20, 600, 350);
		
		frame.getContentPane().add(panel);
		frame.getContentPane().add(renewalComp);
		frame.getContentPane().add(settingComp);
		frame.getContentPane().add(personComp);
		//frame.getContentPane().add(joinableComp); erase
		
		noticeTextPane.setEditable(false);
		noticeTextPane.setText(notice + netClient.getManager().getNotice());
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
			
			//////modified//////
			JoinableComponent joinableComp = new JoinableComponent();
			joinableComp.setBounds(40, 20, 600, 350);
			add(joinableComp);
			
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
			
			int max = netClient.getManager().getID_list().length;
			for (int i = 0; i < max; i++) {
				if( netClient.getManager().getID_list()[i] == null){
					continue;
				}
				
				g.drawImage(p[i+1], 0, i * 20 + 4, 10, 10, this);
				JTextPane nameTextPane = new JTextPane();
				nameTextPane.setBackground(getBackground());
				nameTextPane.setEditable(false);
				nameTextPane.setText(netClient.getManager().getID_list()[i]);
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
			g.drawImage(cellFrame, 0, 0, 600, 2, this);
			g.drawImage(cellFrame, 0, 0, 2, 350, this);
			g.drawImage(cellFrame, 600, 0, -2, 350, this);
			g.drawImage(cellFrame, 0, 350, 600, -2, this);
			g.drawImage(cellFrame, (600/6)-2, 0, 4, 350, this);
			g.drawImage(cellFrame, (600/3)-2, 0, 4, 350, this);
			g.drawImage(cellFrame, (600/2)-2, 0, 4, 350, this);
			g.drawImage(cellFrame, (600/3*2)-2, 0, 4, 350, this);
			g.drawImage(cellFrame, (600/6*5)-2, 0, 4, 350, this);
			
			//////modified �걹源뚯� �닔�젙//////
			for (int i = 1; i <= personNumber; i++) {
				String ident = String.format("%s%d%s", "/resource/p", i, ".png");
				p[i] = tkit.getImage(SchedulerGUI.class.getResource(ident));
			}
			
			//600, 350
			for (int j = 0; j < 6; j++) {
				people = netClient.getManager().show_organized_table(0,j);
				for (int i = 0; i < personNumber; i++) {
					if (people.get(i).equals(1))
						g.drawImage(p[i+1], (j * 100) + (i * 9) + 6, 0 + 6, 7, 7, this);
				}
				//begin = 0;
				for (int i = 1; i < 12; i++) {
					people = netClient.getManager().show_organized_table(i,j);
					oldPeople = netClient.getManager().show_organized_table(i-1,j);
					if (!people.equals(oldPeople)) {
						g.drawImage(cellFrame, j * 100, i * (350 / 12)-2, 100, 4, this);
						for (int k = 0; k < personNumber; k++) {
							if (people.get(k).equals(1))
								g.drawImage(p[k+1], (j * 100) + (k * 9) + 6, (350/12*i) + 6, 7, 7, this);
						}
					}
				}
			}
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
			netClient.sendMessage("REFRESH");
			netClient.endConnection();
			
			frame.dispose();
			SchedulerGUI sgui = new SchedulerGUI(netClient);
			sgui.launchSceduler(netClient);
			
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
		boolean clicked;
		
		public SettingClick(){
			clicked = false;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		synchronized public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!clicked)
				clicked = true;
			else return;
			
			if(clicked){
				netClient.getManager().Open_Edit();
				frame.dispose();
				
				SettingGUI sgui = new SettingGUI(netClient);
				sgui.launchSceduler(netClient);
				
				clicked = false;
			}
			
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
