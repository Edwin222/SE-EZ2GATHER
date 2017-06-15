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

public class SettingGUI {
	private NetClient netClient;
	private JFrame frame;
	private Image cell, time, today, save, exit;
	
	private int command;
	private final int FIXED_PLUS = 1;
	private final int FIXED_MINUS = 2;
	private final int TEMP_PLUS = 3;
	private final int TEMP_MINUS = 4;
	
	public void launchSceduler(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingGUI window = new SettingGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void launchScheduler(NetClient nc){
		EventQueue.invokeLater(new Launcher(nc));
	}
	
	private class Launcher implements Runnable {
		NetClient nc;
		
		public Launcher(NetClient nc){
			this.nc = nc;
		}
		
		public void run(){
			try {
				SettingGUI window = new SettingGUI(nc);
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public SettingGUI(NetClient nc) {
		this.netClient = nc;
		initialize();
	}
	
	public SettingGUI() {
		initialize();
	}
	
	private void initialize() {
		command = TEMP_PLUS;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		MyPanel panel = new MyPanel();
		SaveComponent saveComp = new SaveComponent();
		ExitComponent exitComp = new ExitComponent();
		CellComponent cellComp = new CellComponent();
		
		saveComp.addMouseListener(new SaveClick());
		saveComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveComp.setBounds(670, 420, 20, 20);
		
		exitComp.addMouseListener(new ExitClick());
		exitComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exitComp.setBounds(700, 420, 20, 20);
		
		cellComp.addMouseListener(new CellClick());
		cellComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cellComp.setBounds(50, 100, 600, 350);
		
		panel.setBounds(10, 80, 650, 500);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(saveComp);
		frame.getContentPane().add(exitComp);
		frame.getContentPane().add(cellComp);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("공지사항 받아와서 출력하기");
		textPane.setBounds(200, 17, 500, 25);
		frame.getContentPane().add(textPane);
		panel.setVisible(true);
		saveComp.setVisible(true);
		exitComp.setVisible(true);
		cellComp.setVisible(true);
	}
	
	class ButtonPanel extends JPanel {
		Toolkit tkit;
		public void paintComponent(Graphics g){
			
		}
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
	
	class SaveComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			save = tkit.getImage(SchedulerGUI.class.getResource("/resource/save.png"));
			super.paintComponent(g);
			g.drawImage(save, 0, 0, 20, 20, this);
		}
	}
	
	class ExitComponent extends JComponent {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			exit= tkit.getImage(SchedulerGUI.class.getResource("/resource/exit.png"));
			super.paintComponent(g);
			g.drawImage(exit, 0, 0, 20, 20, this);
		}
	}
	
	class CellComponent extends JComponent {
		private final int width = 600;
		private final int height = 350;
		Toolkit tkit;
		
		public void paintComponent(Graphics g){
			tkit = tkit.getDefaultToolkit();
			cell = tkit.getImage(SchedulerGUI.class.getResource("/resource/SchedulerBack.png"));
			super.paintComponent(g);
			g.drawImage(cell, 0, 0, width, height, this);
		}
	}
	
	class CellClick implements MouseListener {
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class SaveClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
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
	class ExitClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
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
