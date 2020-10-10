package wuziqi;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


//总窗体
public class JFrameAll extends JFrame{

	
	private JCheckBox showOrder;
	private JButton undo;
	private JButton newGame;
	GoBangBoard panelLeft=new GoBangBoard();
	
	private JTextArea textArea;
	private JRadioButton model;
	private JRadioButton intel;
	private JComboBox<Integer> depth;
	private JComboBox<Integer> nodeCount;
	private JComboBox<Integer> pictures;
	private JComboBox<Integer> click;
	private JRadioButton first;
	
	public void start() {
		

		//左边棋盘部分
		//add(panelLeft, BorderLayout.WEST);  
		add(panelLeft,BorderLayout.WEST);
		
		
		//右边功能部分
		//GoBangOper panelRight=new GoBangOper();
		JPanel panelRight=new JPanel();
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		
		//多行文本框
		JPanel jp1=new JPanel();
		jp1.setBorder(new TitledBorder("单击鼠标右键，查看各个估值"));
		jp1.setLayout(new BorderLayout());
		textArea=new JTextArea();
		textArea.setEditable(false);
		jp1.add(textArea);
		panelRight.add(jp1);
	
		//模式
		JPanel jp2=new JPanel();
		jp2.setBorder(new TitledBorder("模式"));
		//单选框
		model=new JRadioButton("人人对战");
		model.setSelected(true);
		JRadioButton radioButton2=new JRadioButton("人机对战");
		//单选框互斥
		ButtonGroup buttonGroup1=new ButtonGroup();
		buttonGroup1.add(model);
		buttonGroup1.add(radioButton2);
		jp2.add(model);
		jp2.add(radioButton2);
		panelRight.add(jp2);
		
		//智能
		JPanel jp3=new JPanel();
		jp3.setBorder(new TitledBorder("智能"));
		//单选框
		intel=new JRadioButton("估值函数");
		intel.setSelected(true);
		JRadioButton radioButton4=new JRadioButton("估值函数+搜索树");
		//让单选框互斥
		ButtonGroup buttonGroup2=new ButtonGroup();
		buttonGroup2.add(intel);
		buttonGroup2.add(radioButton4);
		jp3.add(intel);
		jp3.add(radioButton4);
		panelRight.add(jp3);
		
		//搜索树
		JPanel jp4=new JPanel();
		jp4.setBorder(new TitledBorder("搜索树"));
		JLabel label1=new JLabel("搜索深度:");
		depth=new JComboBox<>(new Integer[] {1,2,3});
		JLabel label2=new JLabel("每层节点:");
		nodeCount=new JComboBox<>(new Integer[] {1,2,3});

		jp4.add(label1);
		jp4.add(depth);
		jp4.add(label2);
		jp4.add(nodeCount);
		panelRight.add(jp4);
		
		//其他
		JPanel jp5=new JPanel();
		jp5.setBorder(new TitledBorder("其他"));

		showOrder=new JCheckBox("显示顺序:");
		undo=new JButton("悔棋");
		newGame=new JButton("新游戏");
		
		showOrder.addMouseListener(mouseListener);
		undo.addMouseListener(mouseListener);
		newGame.addMouseListener(mouseListener);
		
		jp5.add(showOrder);
		jp5.add(undo);
		jp5.add(newGame);
		panelRight.add(jp5);
		
		//人机模式
		JPanel jp6=new JPanel();
		jp6.setBorder(new TitledBorder("人机模式"));
		first=new JRadioButton("人类先手");
		first.setSelected(true);
		JRadioButton radioButton6=new JRadioButton("机器先手");
		ButtonGroup buttonGroup3=new ButtonGroup();
		buttonGroup3.add(first);
		buttonGroup3.add(radioButton6);
		
		jp6.add(first);
		jp6.add(radioButton6);
		panelRight.add(jp6);
		add(panelRight);
		
		JPanel jp7=new JPanel();
		jp7.setBorder(new TitledBorder("个性设置"));
		JLabel label3=new JLabel("棋盘背景");
		JLabel label4=new JLabel("落子音效");
		pictures=new JComboBox<>(new Integer[] {1,2,3});
		click =new JComboBox<>(new Integer[] {1,2,3});
		jp7.add(label3);
		jp7.add(pictures);
		jp7.add(label4);
		jp7.add(click);
		panelRight.add(jp7);
		
		
		//设置总界面
		setSize(GameData.GAME_WIDTH, GameData.GAME_HEIGHT);
		setLocation(250, 0);
		setTitle("五子棋");
		setResizable(false);   
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	MouseListener mouseListener= new MouseListener() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object obj=e.getSource();
			if(obj==showOrder) {
				//System.out.println(obj);
				panelLeft.showOrder(showOrder.isSelected());
			}else if(obj==undo) {
				if(model.isSelected())
				panelLeft.huiqi();
				else
					panelLeft.huiqi2();
				
			}else if(obj==newGame) {
				panelLeft.NewGame(model.isSelected()?true:false,
						intel.isSelected()?true:false,
								(int)depth.getSelectedItem(),
								(int)nodeCount.getSelectedItem(),
								first.isSelected()?true:false,
								textArea,
								showOrder.isSelected()?true:false,
								(int)pictures.getSelectedItem(),
								(int)click.getSelectedItem());
				
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	};
}