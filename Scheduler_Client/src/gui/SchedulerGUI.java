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

public class SchedulerGUI {

	private JFrame frame;
	private Image cell, time, today, refresh, setting;
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
		RefreshComponent refreshComp = new RefreshComponent();
		SettingComponent settingComp = new SettingComponent();
		refreshComp.addMouseListener(new RefreshClick());
		refreshComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshComp.setBounds(670, 420, 20, 20);
		settingComp.addMouseListener(new RefreshClick());
		settingComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		settingComp.setBounds(700, 420, 20, 20);
		panel.setBounds(10, 80, 650, 500);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(refreshComp);
		frame.getContentPane().add(settingComp);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("공지");
		textPane.setBounds(200, 17, 500, 25);
		frame.getContentPane().add(textPane);
		panel.setVisible(true);
		refreshComp.setVisible(true);
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
	class RefreshComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			refresh = tkit.getImage(SchedulerGUI.class.getResource("/resource/refresh.png"));
			super.paintComponent(g);
			g.drawImage(refresh, 0, 0, 20, 20, this);
		}
	}
	class SettingComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			refresh = tkit.getImage(SchedulerGUI.class.getResource("/resource/setting.png"));
			super.paintComponent(g);
			g.drawImage(refresh, 0, 0, 20, 20, this);
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
			JOptionPane.showMessageDialog(null, "Please check IP, PORT or ID", "error", JOptionPane.OK_OPTION);
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
