package caida.xinxi.TestThread4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RelayRaceWindow extends JFrame implements Runnable,KeyListener{
	Thread first,second,third,fourth;
	JButton redButton,greenButton,blueButton,yellowButton;
	int distance;
	JPanel centerPanel;

	
	public static void main(String[] args) {
		new RelayRaceWindow();
	}
	
	public RelayRaceWindow(){
		setTitle("线程同步——4x100接力");
		
		first=new Thread(this);
		second=new Thread(this);
		third=new Thread(this);
		fourth=new Thread(this);
		
		centerPanel=new JPanel();
		JLabel message=new JLabel("按‘S’键开始接力，按‘Q’键退出程序",JLabel.CENTER);
		message.setFont(new Font("宋体",Font.BOLD,15));
		add(centerPanel,BorderLayout.CENTER );
		add(message,BorderLayout.SOUTH );
		
		redButton=new JButton();
		redButton.setBackground(Color.red);
		greenButton=new JButton();
		greenButton.setBackground(Color.green);
		blueButton=new JButton();
		blueButton.setBackground(Color.blue);
		yellowButton=new JButton();
		yellowButton.setBackground(Color.yellow );
		centerPanel.setLayout(null);
		centerPanel.setBackground(Color.white );
		centerPanel.add(redButton);
		redButton.setBounds(0,60,15,15);
		centerPanel.add(greenButton);
		greenButton.setBounds(100,60,15,15);
		centerPanel.add(blueButton);
		blueButton.setBounds(200,60,15,15);
		centerPanel.add(yellowButton);
		yellowButton.setBounds(300,60,15,15);
		
		//让窗口获得焦点，要不然窗口无法响应按键事件
		this.setFocusable(true);
		
		addKeyListener(this);
		setBounds(100,100,430,200);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		validate();
	}

	@Override
	public void run() {
		while(true){
			if(Thread.currentThread()==first){
				move(redButton);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(Thread.currentThread()==second){
				move(greenButton);
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(Thread.currentThread()==third){
				move(blueButton);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e){ 
					e.printStackTrace();
				}
			}
			if(Thread.currentThread()==fourth){
				move(yellowButton);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void move(JButton button) {
		if(Thread.currentThread()==first){
			while(!(distance>=0&&distance<=100)){
				try {
					wait();
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			distance=distance+1;
			button.setLocation(distance, 60);
			if(distance>=100){
				button.setLocation(0,60);
				notifyAll();
			}
		}
		if(Thread.currentThread()==second){
			while(!(distance>=100&&distance<=200)){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			distance=distance+1;
			button.setLocation(distance, 60);
			if(distance>=200){
				button.setLocation(100,60);
				notifyAll();
			}
		}
		if(Thread.currentThread()==third){
			while(!(distance>=200&&distance<=300)){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			distance=distance+1;
			button.setLocation(distance, 60);
			if(distance>=300){
				button.setLocation(200,60);
				notifyAll();
			}
		}
		if(Thread.currentThread()==fourth){
			while(!(distance>=300&&distance<=400)){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			distance=distance+1;
			button.setLocation(distance, 60);
			if(distance>=400){
				distance=0;
				button.setLocation(300,60);
				notifyAll();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_S){
			first.start();
			second.start();
			third.start();
			fourth.start();
		}
		else if(e.getKeyCode()==KeyEvent.VK_Q){
			System.exit(0);
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
