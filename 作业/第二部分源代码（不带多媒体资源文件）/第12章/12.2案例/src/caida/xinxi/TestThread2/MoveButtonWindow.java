package caida.xinxi.TestThread2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MoveButtonWindow extends JFrame implements ActionListener,Runnable{
	
	JButton startButton,stopButton,exitButton;
	JButton rightButton,leftButton;
	Thread left,right;
	boolean boo=true;
	
	public MoveButtonWindow(){
		setTitle("测试多线程主窗口");
		JPanel centerPanel=new JPanel();
		JPanel controlPanel=new JPanel();
		add(centerPanel,BorderLayout.CENTER );
		add(controlPanel,BorderLayout.SOUTH );
		
		startButton=new JButton("开始/继续");
		startButton.addActionListener(this);
		stopButton=new JButton("停止");
		stopButton.addActionListener(this);
		exitButton=new JButton("退出");
		exitButton.addActionListener(this);
		controlPanel.add(this.startButton );
		controlPanel.add(this.stopButton );
		controlPanel.add(this.exitButton );
		
		centerPanel.setLayout(null);
		centerPanel.setBackground(Color.white );
		rightButton=new JButton("向右移动");
		rightButton.setBackground(Color.yellow );
		rightButton.setBounds(0, 5, 100, 30);
		leftButton=new JButton("向左移动");
		leftButton.setBackground(Color.red );
		leftButton.setBounds(395,90,100,30);
		centerPanel.add(rightButton);
		centerPanel.add(leftButton);
		
		right=new Thread(this);
		left=new Thread(this);
		
		setBounds(100,100,500,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		setVisible(true);
		validate();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.startButton ){
			if(!right.isAlive()){
				right=new Thread(this);
			}
			if(!left.isAlive()){
				left=new Thread(this);
			}
			boo=true;
			right.start();
			left.start();
		}
		else if(e.getSource()==this.stopButton){
			boo=false;
		}
		else if(e.getSource()==this.exitButton){
			boo=false;
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new MoveButtonWindow();
	}

	@Override
	public void run() {
		
		while(true){
			if(Thread.currentThread()==right){
				int x=this.rightButton.getBounds().x;
				x=x+5;
				if(x>400){
					x=5;
				}
				rightButton.setLocation(x, 5);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if(Thread.currentThread()==left){
				int x=leftButton.getBounds().x;
				x=x-5;
				if(x<-20){
					x=395;
				}
				leftButton.setLocation(x, 90);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(!boo){
				return;
			}
		}
	}

}

