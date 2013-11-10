package com.remmylife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.remmylife.gui.tools.*;
import com.remmylife.gui.tools.Recorder;

public class VoiceRecordWindow extends JFrame  implements MouseListener {
	
	private JFrame myFrame = new JFrame();
	private JPanel myPanel = new JPanel();
	private JPanelWithBg toolBar = new JPanelWithBg("img\\toolbar.png",250,56);
	private JPanelWithBg VoiceBar = new JPanelWithBg("img\\voicebar.png",250,101);
	private JPanelWithBg SaveBar = new JPanelWithBg("img\\savebar.png",250,57);
	
	ImageButton record=new ImageButton("img//pause.png");
	ImageButton pause=new ImageButton("img//pause.png");
	ImageButton play=new ImageButton("img//play1.png");
	
	JLabel time = new JLabel("00:00:00");
	int hour = 0;
	int minute = 0;
	int second = 0;
	int timeStaus = 0;//0_stop,1_run,2_pause
	
	private ImageButton save = new ImageButton("img\\save.png");
	private ImageButton cancel = new ImageButton("img\\cancel.png");
	
	Recorder recorder = new  Recorder();
	byte[] audioData = null;
	
	Timer recordTimer = new Timer(1000, new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(second < 60)second++;
			else {
				if(minute < 60){
					minute++;
					second = 0;
				}else {
					hour++;
				}				
			}
			
			
			String string = String.format("%02d:%02d:%02d", hour,minute,second);

			time.setText(string);
			
			// TODO Auto-generated method stub
			
		}
		
	});
	
	public VoiceRecordWindow(){
		this.init();
	}
	
	public void init(){	
		myFrame.setTitle("VoiceRecorder");
		myFrame.setSize(250,214);
		myFrame.setVisible(false);
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER, 20,5));
		toolBar.setOpaque(false);
		
		record.addMouseListener(this);
		pause.addMouseListener(this);
		play.addMouseListener(this);
		
		toolBar.add(record);
		//toolBar.add(pause);
		toolBar.add(play);		
		
		time.setFont(new Font("Arial",Font.BOLD,45));
		time.setForeground(new Color(255,255,0));
		VoiceBar.add(time);
		
		save.addMouseListener(this);
		cancel.addMouseListener(this);
		SaveBar.setLayout(new FlowLayout(FlowLayout.CENTER, 30,5));
		SaveBar.setOpaque(false);
		SaveBar.add(save);
		SaveBar.add(cancel);
		
		toolBar.setPreferredSize(new Dimension(250, 56));
		VoiceBar.setPreferredSize(new Dimension(250, 111));
		SaveBar.setPreferredSize(new Dimension(250, 47));
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(toolBar);
		myPanel.add(VoiceBar);
		myPanel.add(SaveBar);
		
		myFrame.add(myPanel);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() == record){
			if(timeStaus == 0 ){
				second = minute = hour = 0;
				recordTimer.start();
				recorder.stopPlay();
				recorder.startRecord();
				audioData = null;
				timeStaus = 1;
				save.setEnabled(false);
				cancel.setEnabled(false);
			}else if(timeStaus == 1){
				recordTimer.stop();
				recorder.stopRecord();
				audioData = recorder.getRecord();				
				timeStaus = 0;
				save.setEnabled(true);
				cancel.setEnabled(true);
			}
		}else if(e.getSource() == play){
			if(timeStaus == 1){
				recordTimer.stop();
				recorder.stopRecord();
				audioData = recorder.getRecord();
				timeStaus = 0;
				save.setEnabled(true);
				cancel.setEnabled(true);
			}
			recorder.playRecord(audioData);
		}else if(e.getSource() == save){
			myFrame.setVisible(false);
		}else if(e.getSource() == cancel){
			audioData = null;
			myFrame.setVisible(false);
		}
		
		
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

	public void setItsVisible(boolean b) {
		myFrame.setVisible(b);
		
	}

	public byte[] getAudioData() {		
		return audioData;
	}
}
