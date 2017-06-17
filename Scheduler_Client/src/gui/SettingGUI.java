package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import client.*;
import common.*;

public class SettingGUI {
	private NetClient netClient;
	private JFrame frame;
	private Image cell, time, today, save, exit;
	private Image fplus, fminus, tplus, tminus, tstr, fstr;
	private Image dragBox;
	
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
				
				SettingGUI window = new SettingGUI(nc);
				window.frame.setVisible(false);
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
		ButtonPanel bPanel = new ButtonPanel();
		SchedulePanel sPanel = new SchedulePanel();
		SaveComponent saveComp = new SaveComponent();
		ExitComponent exitComp = new ExitComponent();
		CellComponent cellComp = new CellComponent();
		
		saveComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveComp.setBounds(670, 420, 20, 20);
		
		exitComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exitComp.setBounds(700, 420, 20, 20);
		
		cellComp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cellComp.setBounds(50, 100, 600, 350);
		
		panel.setBounds(10, 80, 650, 500);
		bPanel.setBounds(660, 150, 80, 140);
		sPanel.setBounds(50,100,600,350);
		sPanel.setBackground(new Color(255,0,0,0));
		frame.getContentPane().add(panel);
		frame.getContentPane().add(bPanel);
		frame.getContentPane().add(sPanel);
		frame.getContentPane().add(saveComp);
		frame.getContentPane().add(exitComp);
		frame.getContentPane().add(cellComp);
		frame.getContentPane().setComponentZOrder(cellComp, 0);
		frame.getContentPane().setComponentZOrder(sPanel, 1);
		frame.getContentPane().setComponentZOrder(panel, 2);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("공지사항:" + netClient.getManager().getNotice());
		textPane.setBounds(200, 17, 500, 25);
		frame.getContentPane().add(textPane);
		
		panel.setVisible(true);
		bPanel.setVisible(true);
		sPanel.setVisible(false);
		sPanel.setVisible(true);
		saveComp.setVisible(true);
		exitComp.setVisible(true);
		cellComp.setVisible(true);
	}
	
	class SchedulePanel extends JPanel implements MouseListener {
		private final int part_width = 100;
		private final int part_height = 29;
		private final int COL = 6;
		private final int ROW = 12;
		private final int BLANK = 0;
		private final int UNFIXED = 1;
		
		Toolkit tkit;
		
		public void paintComponent(Graphics g){
			tkit = tkit.getDefaultToolkit();
			
			Image temp = tkit.getImage(SchedulerGUI.class.getResource("/resource/temp.png"));
			Image fixed = tkit.getImage(SchedulerGUI.class.getResource("/resource/fixed.png"));
			
			super.paintComponent(g);
			//temp schedule
			for(int i=0;i<COL;i++){
				int start = -1;
				int length = 0;
				for(int j=0;j<ROW;j++){
					if(netClient.getManager().getPointTTable(j, i) == UNFIXED){
						
						if(start < 0){
							start = j;
							length++;
						} else {
							length++;
						}
						
					} else if(netClient.getManager().getPointTTable(j, i) == BLANK || (j == ROW-1 && start > 0)){
						g.drawImage(temp, i*part_width, start*part_height, part_width, length*part_height,this);
						length = 0;
						start = -1;
					}
				}
			}
			
			//fixed schedule
			int fscLength = netClient.getManager().getFixedSchedule().size();
			for(int i=0;i<fscLength;i++){
				FixedScheduleUnit target = netClient.getManager().getFixedSchedule().get(i);
				
				for(int j=0;j<COL;j++){
					
					if(netClient.getManager().getday(j).equals(target.getDay())){
						g.drawImage(fixed, j*part_width, target.getBegin()*part_height , part_width, part_height*(target.getEnd() - target.getBegin()+1),this);
						break;
					}
					
				}
				
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
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

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			this.setVisible(false);
			this.setVisible(true);
		}
	}
	
	class ButtonPanel extends JPanel implements MouseListener {
		Toolkit tkit;
		
		public void paintComponent(Graphics g){
			tkit = tkit.getDefaultToolkit();
			
			fstr = tkit.getImage(SchedulerGUI.class.getResource("/resource/fixedSchedule.png"));
			tstr = tkit.getImage(SchedulerGUI.class.getResource("/resource/tempSchedule.png"));
			
			switch(command){
			case FIXED_PLUS:
				fplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus-HL.png"));
				fminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus.png"));
				tplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus.png"));
				tminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus.png"));
				break;
				
			case FIXED_MINUS:
				fplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus.png"));
				fminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus-HL.png"));
				tplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus.png"));
				tminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus.png"));
				break;
				
			case TEMP_PLUS:
				fplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus.png"));
				fminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus.png"));
				tplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus-HL.png"));
				tminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus.png"));
				break;
				
			case TEMP_MINUS:
				fplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus.png"));
				fminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus.png"));
				tplus = tkit.getImage(SchedulerGUI.class.getResource("/resource/plus.png"));
				tminus = tkit.getImage(SchedulerGUI.class.getResource("/resource/minus-HL.png"));
				break;
			}
			
			super.paintComponent(g);
			g.drawImage(fstr, 0,0, 80, 15, this);
			g.drawImage(fplus, 0, 15, 40, 40, this);
			g.drawImage(fminus, 40, 15, 40, 40, this);
			
			g.drawImage(tstr, 0, 80, 80, 15, this);
			g.drawImage(tplus, 0, 95, 40, 40, this);
			g.drawImage(tminus, 40, 95, 40, 40, this);
			
			addMouseListener(this);
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
			int y = e.getY();
			
			if( (x >= 0 && x <= 40) && (y >= 15 && y <= 55)){
				command = FIXED_PLUS;
			}
			else if( (x >= 40 && x <= 80) && (y >= 15 && y <= 55)){
				command = FIXED_MINUS;
			}
			else if( (x >= 0 && x <= 40) && (y >= 95 && y <= 135)){
				command = TEMP_PLUS;
			}
			else if( (x >= 40 && x <= 80) && (y >= 95 && y <= 135)){
				command = TEMP_MINUS;
			}
			
			this.setVisible(false);
			this.setVisible(true);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
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
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
			g.drawImage(cell, 40, 20, 600, 348, this);
			g.drawImage(time, 0, 20, 25, 350, this);
			g.drawImage(today, 60, 0, 60, 20, this);
		}
	}
	
	class SaveComponent extends JComponent implements MouseListener {
		Toolkit tkit;
		public SaveComponent(){
			addMouseListener(this);
		}
		
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			save = tkit.getImage(SchedulerGUI.class.getResource("/resource/save.png"));
			super.paintComponent(g);
			g.drawImage(save, 0, 0, 20, 20, this);
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			netClient.getManager().save_Edit();
			netClient.connectToServer();
			netClient.sendMessage("SAVE");
			netClient.endConnection();
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
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
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class ExitComponent extends JComponent implements MouseListener {
		Toolkit tkit;
		public ExitComponent(){
			addMouseListener(this);
		}
		
		public void paintComponent(Graphics g) {
			tkit = tkit.getDefaultToolkit();
			exit= tkit.getImage(SchedulerGUI.class.getResource("/resource/exit.png"));
			super.paintComponent(g);
			g.drawImage(exit, 0, 0, 20, 20, this);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(netClient.getManager().Check_Edited()){
				
				int selected = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				switch(selected){
				case JOptionPane.YES_OPTION:
					netClient.getManager().save_Edit();
					netClient.connectToServer();
					netClient.sendMessage("SAVE");
					netClient.endConnection();
					
				case JOptionPane.NO_OPTION:
					frame.dispose();
					netClient.getManager().cancel_Edit();
					SchedulerGUI scgui = new SchedulerGUI(netClient);
					scgui.launchSceduler(netClient);
					break;
					
				case JOptionPane.CANCEL_OPTION:
				case JOptionPane.CLOSED_OPTION:
					break;
					
				}
				
			} else {
				frame.dispose();
				netClient.getManager().cancel_Edit();
				SchedulerGUI scgui = new SchedulerGUI(netClient);
				scgui.launchSceduler(netClient);
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {
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
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class CellComponent extends JComponent implements MouseMotionListener, MouseListener {
		private final int width = 600;
		private final int height = 348;
		private final int part_width = 100;
		private final int part_height = 29;
		private int count;
		
		int start_x;
		int start_y;
		
		int checked_width;
		int checked_height;
		
		Toolkit tkit;
		
		public CellComponent(){
			this.setOpaque(false);
			count = 0;
		}
		
		public void paintComponent(Graphics g){
			tkit = tkit.getDefaultToolkit();
			//cell = tkit.getImage(SchedulerGUI.class.getResource("/resource/SchedulerBack.png"));
			super.paintComponent(g);
			//g.drawImage(cell, 0, 0, width, height, this);
			
			g.drawImage(dragBox, start_x, start_y, checked_width*(part_width), checked_height*(part_height), this);	
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int x = e.getX();
			int y = e.getY();
			
			checked_width = 0;
			checked_height = 0;
			
			start_x = part_width*(x/part_width);
			start_y = part_height*(y/part_height);
			
			ImageFilter filter = new RGBImageFilter() {
				 int transparentColor = Color.white.getRGB() | 0xFF000000;

		         public final int filterRGB(int x, int y, int rgb) {
		            if ((rgb | 0xFF000000) == transparentColor) {
		               return 0x00FFFFFF & rgb;
		            } else {
		               return rgb;
		            }
		         }
			};
			
			Image box;
			if(command == FIXED_PLUS || command == FIXED_MINUS){
				box = tkit.getImage(SchedulerGUI.class.getResource("/resource/fixed.png"));
			} else {
				box = tkit.getImage(SchedulerGUI.class.getResource("/resource/temp.png"));
			}
			
			ImageProducer filteredImgProd = new FilteredImageSource(box.getSource(), filter);
			dragBox = tkit.createImage(filteredImgProd);
		}
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			boolean changed = false;
			
			// TODO Auto-generated method stub
			int x = arg0.getX();
			int y = arg0.getY();
			
			if( x > start_x + (checked_width*part_width) + (int)(part_width * 0.6) ){
				checked_width++;
				changed = true;
			} 
			else if ( x < start_x + (checked_width*part_width) - (int)(part_width * 0.4)){
				checked_width--;
				changed = true;
			}
			
			if(y > start_y + (checked_height*part_height) + (int)(part_height * 0.6) ) {
				checked_height++;
				changed = true;
			} 
			else if ( y < start_y + (checked_height*part_height) - (int)(part_height * 0.4)){
				checked_height--;
				changed = true;
			}
			
			if(changed){
				this.setVisible(false);
				this.setVisible(true);
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			int sX = start_x/part_width;
			int sY = start_y/part_height;
			int row = checked_height;
			int col = checked_width;
			
			System.out.println("("+sX+","+sY+")"+"/"+"("+col+","+row+")" );
			
			ScheduleUnit sc;
			if(command <= FIXED_MINUS){
				//fixed
				sc = new FixedScheduleUnit(sY, sY + row-1, netClient.getManager().getday(sX+ checked_width));
				
			} else {
				//temp
				sc = new ScheduleUnit(sY, sY + row-1);
			}
			
			switch(command){
			case FIXED_PLUS:
				netClient.getManager().add_FixedSchedule( (FixedScheduleUnit) sc, sX, sX+checked_width-1);
				break;
			case FIXED_MINUS:
				netClient.getManager().remove_FixedSchedule( (FixedScheduleUnit) sc, sX, sX+checked_width-1);
				break;
			case TEMP_PLUS:
				netClient.getManager().add_Schedule( sc, sX, sX+checked_width-1);
				break;
			case TEMP_MINUS:
				netClient.getManager().remove_Schedule( sc, sX, sX+checked_width-1);
				break;
			}
			
			checked_width = 0;
			checked_height = 0;
			this.setVisible(false);
			this.setVisible(true);
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseClicked(MouseEvent e) {
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
