package caida.xinxi.ShapeAndAnimation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ShapeAndAnimationWindow extends JFrame implements ActionListener{
	DrawShapePanel shapePanel;
	JButton shapeButton,animationButton,stopButton,restartButton,exitButton;
	
	public ShapeAndAnimationWindow(){
		setTitle("图形与动画");

		shapePanel=new DrawShapePanel();
		JPanel pSouth=new JPanel();
		shapeButton=new JButton("随机生成图形");
		animationButton=new JButton("动画开始");
		stopButton=new JButton("动画结束");
		restartButton=new JButton("动画继续");
		exitButton=new JButton("退出程序");
		
		shapeButton.addActionListener(this);
		animationButton.addActionListener(this);
		stopButton.addActionListener(this);
		restartButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		pSouth.add(this.shapeButton);
		pSouth.add(this.animationButton);
		pSouth.add(this.stopButton);
		pSouth.add(this.restartButton);
		pSouth.add(this.exitButton);

		add(shapePanel,BorderLayout.CENTER);
		add(pSouth,BorderLayout.SOUTH);
		
		setBounds(100,100,600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.shapeButton){
			shapePanel.drawShapes();
		}
		else if(e.getSource()==this.animationButton){
			shapePanel.startMove();
		}
		else if(e.getSource()==this.stopButton){
			shapePanel.stopMove();
		}
		else if(e.getSource()==this.restartButton){
			shapePanel.continueMove();
		}
		else if(e.getSource()==this.exitButton){
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new ShapeAndAnimationWindow();
	}
}
