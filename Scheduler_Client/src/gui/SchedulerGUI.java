package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;

public class SchedulerGUI {

	private JFrame frame;
	private Image cell, time;
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
		MyPanel panel = new MyPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		
	}
	class MyPanel extends JPanel {
		Toolkit tkit;
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			cell = tkit.getImage(SchedulerGUI.class.getResource("/resource/SchedulerBack.png"));
			time = tkit.getImage(SchedulerGUI.class.getResource("/resource/time.png"));
			super.paintComponent(g);
			g.drawImage(cell, 50, 100, 600, 350, this);
			g.drawImage(time, 10, 100, 30, 350, this);
		}
	}
}
