package wuziqi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Toolkit;

import javax.swing.*;

public class GoBangBoard extends JPanel{

	private int currentPlay=GameData.BLACK;
	
	private Chess chessBeanByTree;
	
	//true:游戏结束
	private boolean isGameOver=false;
	//true：人人
	private boolean model;
	//true：估值函数
	private boolean intel;
	//搜索深度
	private int depth;
	//节点数量
	private int nodeCount;
	
	//true:人先手
	private boolean first;
	//显示顺序
	private boolean showOrder;
	//多少个棋子
	private int count=0;
	//true则是选中显示，false则不是
	private JTextArea textArea;
	
	//鼠标属于的交叉点，（0，0）---（14，14）
	private int x,y;
	
	Chess[][] chessBeans=new Chess[GameData.LINE_NUMBER][GameData.LINE_NUMBER];
	//替换前：
//	public GoBangBoard(){
//		setPreferredSize(new Dimension(GameData.PAINT_WIDTH, GameData.PAINT_HEIGHT));
//		setBackground(Color.ORANGE);
//		//为棋盘绑定鼠标移动事件
//		//this.image=image;
//		addMouseMotionListener(mouseMotionListener);
//		
//		addMouseListener(mouseListener);
//		//对棋子初始化
//		for(int i=0;i<chessBeans.length;i++) {
//			for(int j=0;j<chessBeans[i].length;j++) {
//				//chessBeans[i][j]=new Chess(0, 0, 0, 0);
//				
//				chessBeans[i][j]=new Chess(i, j, currentPlay, i*chessBeans.length+j);
//				currentPlay=3-currentPlay;
//			}
//		}
//	}
	
	//替换后：
	ImageIcon icon;
	Image img;
	private static String s1="IMG\\sight3.png";
	private static String s2="IMG_late\\sight3.png";
	private static String[] s3={"IMG_late_vir\\sight1.png","IMG_late_vir\\sight2.png","IMG_late_vir\\sight3.png"};
	//private static String s3="IMG_late_vir\\sight3.png";
	private int picPos=0;
	private int virtualDeg=6;
	private String[] clicks= {"music\\sound.wav","music\\sound1.wav","music\\sound2.wav"};
	private int clickpos=0;
	
	public GoBangBoard() {
//		ImageCut.CUT(s1,s2);
//		changeAlpha.ChAlpha(s2, s3[picPos], virtualDeg);//virtualDegree是虚化程度
//		icon=new ImageIcon(s3[picPos]);
		
//		changeAlpha.ChAlpha(s2, s3, virtualDeg);//virtualDegree是虚化程度
//		icon=new ImageIcon(s3);
//		img=icon.getImage();
		
		setPreferredSize(new Dimension(GameData.PAINT_WIDTH, GameData.PAINT_HEIGHT));
		addMouseMotionListener(mouseMotionListener);
		
		addMouseListener(mouseListener);
		//对棋子初始化
		for(int i=0;i<chessBeans.length;i++) {
			for(int j=0;j<chessBeans[i].length;j++) {
				chessBeans[i][j]=new Chess(0, 0, 0, 0);
				
				//chessBeans[i][j]=new Chess(i, j, currentPlay, i*chessBeans.length+j);
				//currentPlay=3-currentPlay;
			}
		}
	}

	MouseMotionListener mouseMotionListener=new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			//鼠标移动，获取鼠标的坐标
			int x_tmp=e.getX();
			int y_tmp=e.getY();
			
			//判断鼠标是否越界
			if(x_tmp>0 && x_tmp<GameData.LINE_SIZE*GameData.LINE_NUMBER &&
					y_tmp>0 && y_tmp<GameData.LINE_SIZE*GameData.LINE_NUMBER) {
				x=(x_tmp-GameData.OFFSET/2)/GameData.LINE_SIZE;
				y=(y_tmp-GameData.OFFSET/2)/GameData.LINE_SIZE;
			}
			repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	protected void paintComponent(Graphics g) {
//		ImageCut.CUT(s1,s2);
//		changeAlpha.ChAlpha(s2, s3[picPos], virtualDeg);//virtualDegree是虚化程度
		super.paintComponent(g);
		icon=new ImageIcon(s3[picPos]);
		img=icon.getImage();
		g.drawImage(img,0,0,null);
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));	
		
			drawLine(g2d);
			drawStar(g2d);
			drawTrips(g2d);
			drawNumber(g2d);
			drawChess(g2d);
			drawOrderNum(g2d);
	}
	
	private void drawOrderNum(Graphics2D g2d) {
		if(showOrder) {
			g2d.setColor(Color.RED);
			for(int i=0;i<chessBeans.length;i++) {
				for(int j=0;j<chessBeans[i].length;j++) {
					Chess bean=chessBeans[i][j];
					if(bean.getPlayer()!=GameData.EMPTY) {
						int number=bean.getOrder();
						FontMetrics fontMetrics=g2d.getFontMetrics();
						int width=fontMetrics.stringWidth(number+"");
						int height=fontMetrics.getHeight();
						
						int x=GameData.OFFSET+bean.getX()*GameData.LINE_SIZE;
						int y=GameData.OFFSET+bean.getY()*GameData.LINE_SIZE;
						
						g2d.drawString(number+"", x-width/2, y+height/3);
					}
				}
			}
		}
		
	}

	private void drawChess(Graphics2D g2d) {
		for(int i=0;i<chessBeans.length;i++) {
			for(int j=0;j<chessBeans[i].length;j++) {
				Chess bean=chessBeans[i][j];
				if(bean.getPlayer()!=GameData.EMPTY) {
					//有棋子
					if(bean.getPlayer()==GameData.BLACK) {
						g2d.setColor(Color.black);
					}else if(bean.getPlayer()==GameData.WHITE) {
						g2d.setColor(Color.WHITE);
					}
					
					int x_tmp=GameData.OFFSET+bean.getX()*GameData.LINE_SIZE;
					int y_tmp=GameData.OFFSET+bean.getY()*GameData.LINE_SIZE;
					
					int width=GameData.LINE_SIZE*4/5;
					//playMusic p=new playMusic("music\\sound.wav");
					
					g2d.fillOval(x_tmp-width/2, y_tmp-width/2, width, width);
					//System.out.println(bean.getX()+","+bean.getY());
				}
			}
	}
		
		//棋子上要么最后一个棋子做标识，要么显示顺序
		if(!showOrder) {
			int widthNote=GameData.LINE_SIZE/3;
			//获取最后一个棋子
			Chess bean=getLastBean();
			
			int x=GameData.OFFSET+GameData.LINE_SIZE*bean.getX();
			int y=GameData.OFFSET+GameData.LINE_SIZE*bean.getY();
			g2d.setColor(Color.RED);
			if(bean.getPlayer()!=GameData.EMPTY)
			g2d.fillRect(x-widthNote/2, y-widthNote/2, widthNote, widthNote);
			
		}
	}
	private Chess getLastBean() {
		Chess bean=null;
		for(int i=0;i<chessBeans.length;i++) {
			for(int j=0;j<chessBeans[i].length;j++) {
				Chess tmp=chessBeans[i][j];
				if(bean==null) {
					bean=tmp;
				}else if(tmp.getOrder()>bean.getOrder()) {
					bean=tmp;
				}
			}
		}
		return bean;
	}

	private void drawNumber(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		
		FontMetrics fontMetrics=g2d.getFontMetrics();
		int height=fontMetrics.getHeight();
		
		for(int i=0;i<GameData.LINE_NUMBER;i++) {
			g2d.drawString((GameData.LINE_NUMBER-i)+"",
					GameData.OFFSET/8, GameData.OFFSET+i*GameData.LINE_SIZE+height/6);
		}
		
		char c='A';
		for(int i=0;i<GameData.LINE_NUMBER;i++) {
			g2d.drawString((c)+"",
					GameData.OFFSET+i*GameData.LINE_SIZE-GameData.OFFSET/9, GameData.OFFSET+(GameData.LINE_SIZE+3)*(GameData.LINE_NUMBER-1));
			c++;
		}
	}

	private void drawTrips(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		
		//计算交叉点坐标
		int x_tmp=GameData.OFFSET+x*GameData.LINE_SIZE;
		int y_tmp=GameData.OFFSET+y*GameData.LINE_SIZE;
		
		int half=GameData.LINE_SIZE/2;
		int quater=half/2;
		//左上角
		g2d.drawLine(x_tmp-half, y_tmp-half, x_tmp-half+quater, y_tmp-half);
		g2d.drawLine(x_tmp-half, y_tmp-half, x_tmp-half, y_tmp-half+quater);
		
		g2d.drawLine(x_tmp-half, y_tmp+half, x_tmp-half+quater, y_tmp+half);
		g2d.drawLine(x_tmp-half, y_tmp+half, x_tmp-half, y_tmp+half-quater);
		
		g2d.drawLine(x_tmp+half, y_tmp-half, x_tmp+half-quater, y_tmp-half);
		g2d.drawLine(x_tmp+half, y_tmp-half, x_tmp+half, y_tmp-half+quater);
		
		g2d.drawLine(x_tmp+half, y_tmp+half, x_tmp+half-quater, y_tmp+half);
		g2d.drawLine(x_tmp+half, y_tmp+half, x_tmp+half, y_tmp+half-quater);
	}

	private void drawStar(Graphics2D g2d) {
			//g2d.fillOval(100, 100, 100, 100);
		int half=GameData.LINE_NUMBER/2;
		int quater=half/2;
		g2d.fillOval(GameData.OFFSET+half*GameData.LINE_SIZE-GameData.STAR/2, GameData.OFFSET+half*GameData.LINE_SIZE-GameData.STAR/2,
				GameData.STAR, GameData.STAR);
		//左上
		g2d.fillOval(GameData.OFFSET+quater*GameData.LINE_SIZE-GameData.STAR/2, GameData.OFFSET+quater*GameData.LINE_SIZE-GameData.STAR/2,
				GameData.STAR, GameData.STAR);
		//左下
		g2d.fillOval(GameData.OFFSET+quater*GameData.LINE_SIZE-GameData.STAR/2, GameData.OFFSET+(GameData.LINE_NUMBER-4)*GameData.LINE_SIZE-GameData.STAR/2,
				GameData.STAR, GameData.STAR);
		//右上
		g2d.fillOval(GameData.OFFSET+(GameData.LINE_NUMBER-4)*GameData.LINE_SIZE-GameData.STAR/2, GameData.OFFSET+quater*GameData.LINE_SIZE-GameData.STAR/2,
				GameData.STAR, GameData.STAR);
		//右下
		g2d.fillOval(GameData.OFFSET+(GameData.LINE_NUMBER-4)*GameData.LINE_SIZE-GameData.STAR/2, GameData.OFFSET+(GameData.LINE_NUMBER-4)*GameData.LINE_SIZE-GameData.STAR/2,
				GameData.STAR, GameData.STAR);
	}
	private void drawLine(Graphics2D g2d) {
		for(int i=0;i<GameData.LINE_NUMBER;i++) {
			g2d.drawLine(GameData.OFFSET, GameData.OFFSET+i*GameData.LINE_SIZE,
					GameData.OFFSET+(GameData.LINE_NUMBER-1)*GameData.LINE_SIZE,
					GameData.OFFSET+i*GameData.LINE_SIZE);
		}
		for(int i=0;i<GameData.LINE_NUMBER;i++) {
			g2d.drawLine(GameData.OFFSET+i*GameData.LINE_SIZE, GameData.OFFSET,
					GameData.OFFSET+i*GameData.LINE_SIZE,
					GameData.OFFSET+(GameData.LINE_NUMBER-1)*GameData.LINE_SIZE);
		}
		
	}


	public void showOrder(boolean showOrder) {
		this.showOrder=showOrder;
		
		repaint();
	}


	public void NewGame(boolean model, boolean intel, int depth, int nodeCount, boolean first, JTextArea textArea, boolean showOrder,int picturePos,int clickPos) {
		this.model=model;
		this.intel=intel;
		this.depth=depth;
		this.nodeCount=nodeCount;
		this.first=first;
		this.textArea=textArea;
		this.showOrder=showOrder;
		this.picPos=picturePos-1;
		this.clickpos=clickPos-1;
		textArea.setText("");
		
		currentPlay=GameData.BLACK;
		//对棋子初始化
		for(int i=0;i<chessBeans.length;i++) {
			for(int j=0;j<chessBeans[i].length;j++) {
				chessBeans[i][j]=new Chess(i, j, 0, 0);
			}
		}
		isGameOver=false;
		count=0;
		
		JOptionPane.showMessageDialog(GoBangBoard.this, "新游戏开始!");
		
		if(!model && !first) {
			//人机对战机器先手
			int center=GameData.LINE_NUMBER/2;
			chessBeans[center][center].setPlayer(currentPlay);
			chessBeans[center][center].setOrder(count);
			count++;
			currentPlay=3-currentPlay;
			
			 
		}
		
		repaint();
	}
	
	//检查是否赢棋
	public boolean checkWin(Chess chess) {
		boolean result = false;
		//四个方向进行计算
		if(checkChessCount(chess,-1,0)+checkChessCount(chess,1,0)>=4) {
			result=true;
		}
		if(checkChessCount(chess,0,-1)+checkChessCount(chess,0,1)>=4) {
			result=true;
		}
		if(checkChessCount(chess,1,1)+checkChessCount(chess,-1,-1)>=4) {
			result=true;
		}
		if(checkChessCount(chess,-1,1)+checkChessCount(chess,1,-1)>=4) {
			result=true;
		}
		
		if(result) {
			JOptionPane.showMessageDialog(GoBangBoard.this, "游戏结束！");
			isGameOver=true;
			return true;
		}
		return false;
	}
	private int checkChessCount(Chess chess, int x, int y) {
		int sum=0;
		int x_tmp=chess.getX();
		int y_tmp=chess.getY();
		for(int i=0;i<4;i++) {
			x_tmp+=x;
			y_tmp+=y;
			
			if(x_tmp>=0 && x_tmp<GameData.LINE_NUMBER && y_tmp>=0 && y_tmp<GameData.LINE_NUMBER) {
				if(chessBeans[x_tmp][y_tmp].getPlayer()==chess.getPlayer()) {
					//棋子相同
					sum++;
				}else {
					break;
				}
			}
		}
		return sum;
	}

	MouseListener mouseListener=new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(isGameOver) {
				JOptionPane.showMessageDialog(GoBangBoard.this,"游戏已经结束！");
				return ;
			}

			//鼠标移动，获取鼠标的坐标
			int x_tmp=e.getX();
			int y_tmp=e.getY();
			
			//判断鼠标是否越界
			if(x_tmp>0 && x_tmp<GameData.LINE_SIZE*GameData.LINE_NUMBER &&
					y_tmp>0 && y_tmp<GameData.LINE_SIZE*GameData.LINE_NUMBER) {
				x=(x_tmp-GameData.OFFSET/2)/GameData.LINE_SIZE;
				y=(y_tmp-GameData.OFFSET/2)/GameData.LINE_SIZE;
					
				if(e.getButton()==MouseEvent.BUTTON1) {
					
					if(chessBeans[x][y].getOrder()==GameData.EMPTY) {
						playMusic p=new playMusic(clicks[clickpos]);
					}
					//点击鼠标左键
					if(model) {
						//人人对战
						//落子操作
						if(chessBeans[x][y].getPlayer()==GameData.EMPTY) {
							chessBeans[x][y]=new Chess(x,y,currentPlay,count);
							count++;
							
							currentPlay=3-currentPlay;
							
							checkWin(chessBeans[x][y]);
							

							repaint();
						}
					}else {
						//人机对战
						
						if(intel) {
							//估值函数
							//落子操作
							if(chessBeans[x][y].getPlayer()==GameData.EMPTY) {
								
								//该位置为空
								chessBeans[x][y]=new Chess(x,y,currentPlay,count);
								count++;
								
								currentPlay=3-currentPlay;
								
								boolean win=checkWin(chessBeans[x][y]);			
								repaint();
								
								//机器下棋
								if(!win) {
									List<Chess>list=getSordedBean(currentPlay);
									
									if(list.size()>0) {
										//获取分值最大
										Chess bean=list.get(0);
										bean.setPlayer(currentPlay);
										bean.setOrder(count);
										
										count++;
										currentPlay=3-currentPlay;
										chessBeans[bean.getX()][bean.getY()]=bean;
										checkWin(bean);
										repaint();
									}
								}
							}
						}
						else {
							//估值函数+搜索树
							if(chessBeans[x][y].getPlayer()==GameData.EMPTY) {
								
								//该位置为空
								chessBeans[x][y]=new Chess(x,y,currentPlay,count);
								count++;
								
								currentPlay=3-currentPlay;
								
								boolean win=checkWin(chessBeans[x][y]);
								
								repaint();
								
								//机器下棋
								if(!win) {
										
										getByTree2(0, currentPlay, chessBeans,-Integer.MAX_VALUE,Integer.MAX_VALUE);
										
										Chess bean = new Chess();
										bean=chessBeanByTree;
										if(bean!=null) {
											bean.setPlayer(currentPlay);
											bean.setOrder(count);
											
											count++;
											currentPlay=3-currentPlay;
											chessBeans[bean.getX()][bean.getY()]=bean;
											checkWin(chessBeans[bean.getX()][bean.getY()]);
											repaint();
										}				
								}
								
							}						
						}
					}

					
				}else if(e.getButton()==MouseEvent.BUTTON3) {
					//点击鼠标右键

					Chess[][] temBeans = GoBangBoard.this.clone(chessBeans);
					int offense = getValue(temBeans[x][y], currentPlay);
					// 计算该点对对手的价值
					int defentse = getValue(temBeans[x][y], 3-currentPlay);

					temBeans[x][y].getBuffer().append("点(" + x + "," +y + ")的" + "攻击:" + offense + " "
							+ "防御:" + defentse + " " + "总和:" + (defentse + offense) + "\n\n");
					textArea.append(temBeans[x][y].getBuffer().toString());
					// }
	
				}

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

	public void huiqi() {
		if(isGameOver) {
			JOptionPane.showMessageDialog(GoBangBoard.this, "请先开始游戏！");
			return ;
		}else {
			if(count>0) {
				Chess chess=getLastBean();
				chessBeans[chess.getX()][chess.getY()].setOrder(0);
				chessBeans[chess.getX()][chess.getY()].setPlayer(GameData.EMPTY);
				count--;
				currentPlay=3-currentPlay;
				repaint();
			}
			else {
				JOptionPane.showMessageDialog(GoBangBoard.this, "请先下棋！");
			}
		}
		
	}
	
	
	//根据极大极小值搜索
	protected int getByTree2(int depth, int currentPlay, Chess[][] chessBeans, int alpha, int beta) {
		// TODO Auto-generated method stub
		Chess[][] chessBeans2=clone(chessBeans);
		
		//计算空位置的得分
		List<Chess> list=getSordedBean2(currentPlay,chessBeans2);
		
		if(this.depth==depth) {
			//搜索到指定深度
			return list.get(0).getSum();
		}
		for(int i=0;i<nodeCount;i++) {
			Chess chessBean=list.get(i);
			int score;
			if(chessBean.getSum()>Level.ALIVE_4.score) {
				score= chessBean.getSum();
			}else {
				//模拟下棋，继续递归
				chessBeans2[chessBean.getX()][chessBean.getY()].setPlayer(currentPlay);
				score= getByTree2(depth+1,3-currentPlay,chessBeans2,alpha,beta);
			}
			if(depth%2==0) {
				//最大层
				if(score>alpha) {
					alpha=score;
					if(depth==0) {
						//结束
						chessBeanByTree=chessBean;
					}
				}
				
				if(alpha>=beta) {
					//剪枝
					score=alpha;
					return score;
				}
			}else {
				if(score<beta) {
					beta=score;
				}
				
				if(alpha>=beta) {
					//剪枝
					score=beta;
					return score;
				}
			}

		}
		return depth%2==0?alpha:beta;
	}


	//根据搜索树查询
	public Chess getByTree(int depth, int currentPlay, Chess[][] chessBeans) {
		//涉及到模拟下棋，不能在真正棋盘上进行修改，进行数组的克隆
		Chess[][] chessBeans2=clone(chessBeans);
		
		//计算空位置的得分
		List<Chess> list=getSordedBean2(currentPlay,chessBeans2);
		
		if(this.depth==depth) {
			//搜索到指定深度
			return list.get(0);
		}
		for(Chess chessBean:list) {
			if(chessBean.getSum()>Level.ALIVE_4.score) {
				return chessBean;
			}else {
				//模拟下棋，继续递归
				chessBeans2[chessBean.getX()][chessBean.getY()].setPlayer(currentPlay);
				return getByTree(depth+1,3-currentPlay,chessBeans2);
			}
			
		}	
		return null;
	}

	public List<Chess> getSordedBean2(int currentPlay, Chess[][] chessBeans) {
		List<Chess> list=new ArrayList<Chess>();
		for(int i=0;i<chessBeans.length;i++) {
			for(int j=0;j<chessBeans[i].length;j++) {
				Chess bean=chessBeans[i][j];
				if(bean.getPlayer()==GameData.EMPTY) {
					//该位置为空,计算得分
					int offense=getValue(bean,currentPlay);
					int defense=getValue(bean,3-currentPlay);
					
					bean.setOffense(offense);
					bean.setDefense(defense);
					bean.setSum(offense+defense);
					list.add(bean);
				}
			}
		}
		
		//根据总得分从大到小排序
		Collections.sort(list);
		return list;
		
	}


	//数组克隆
	public Chess[][] clone(Chess[][] chessBeans) {
		Chess[][] result=new Chess[GameData.LINE_NUMBER][GameData.LINE_NUMBER];
		for(int i=0;i<result.length;i++) {
			for(int j=0;j<result[i].length;j++) {
				result[i][j]=new Chess(chessBeans[i][j].getX(),chessBeans[i][j].getY(),
						chessBeans[i][j].getPlayer(),chessBeans[i][j].getOrder());

			}
		}
		return result;
	}


	//对所有空值进行得分计算
	protected List<Chess> getSordedBean(int currentPlay) {
		
		List<Chess>list=new ArrayList<Chess>();
		for(int i=0;i<chessBeans.length;i++) {
			for(int j=0;j<chessBeans[i].length;j++) {
				Chess bean=chessBeans[i][j];
				if(bean.getPlayer()==GameData.EMPTY) {
					//该位置为空,计算得分
					int offense=getValue(bean,currentPlay);
					int defense=getValue(bean,3-currentPlay);
					
					bean.setOffense(offense);
					bean.setDefense(defense);
					bean.setSum(offense+defense);
					list.add(bean);
				}
			}
		}
		
		//根据总得分从大到小排序
		Collections.sort(list);
		return list;
	}


	//根据四个方向棋形计分
	private int getValue(Chess bean, int currentPlay) {
		//计算4个方向的棋型level
		Level level1=getLevel(bean,currentPlay,Direction.HENG);
		Level level2=getLevel(bean,currentPlay,Direction.SHU);
		Level level3=getLevel(bean,currentPlay,Direction.PIE);
		Level level4=getLevel(bean,currentPlay,Direction.NA);
		return getValueByLevel(level1,level2,level3,level4)+position[bean.getX()][bean.getY()];
	}
	
	//检查四个方向棋型是否有重复
	private int getValueByLevel(Level level1, Level level2, Level level3, Level level4) {
		int []levelCount=new int[Level.values().length];
		for(int i=0;i<levelCount.length;i++) {
			levelCount[i]=0;
		}
		levelCount[level1.index]++;
		levelCount[level2.index]++;
		levelCount[level3.index]++;
		levelCount[level4.index]++;
		int score = 0;
//		if (levelCount[Level.GO_4.index] >= 2
//				|| levelCount[Level.GO_4.index] >= 1 && levelCount[Level.ALIVE_3.index] >= 1)// 双活4，冲4活三
//			score = 10000;
//		else if (levelCount[Level.ALIVE_3.index] >= 2)// 双活3
//			score = 5000;
//		else if (levelCount[Level.SLEEP_3.index] >= 1 && levelCount[Level.ALIVE_3.index] >= 1)// 活3眠3
//			score = 1000;
//		else if (levelCount[Level.ALIVE_2.index] >= 2)// 双活2
//			score = 100;
//		else if (levelCount[Level.SLEEP_2.index] >= 1 && levelCount[Level.ALIVE_2.index] >= 1)// 活2眠2
//			score = 10;
		
		if (levelCount[Level.GO_4.index] >= 2
				|| levelCount[Level.GO_4.index] >= 1 && levelCount[Level.ALIVE_3.index] >= 1)// 双活4，冲4活三
			score = 10000;
		else if (levelCount[Level.ALIVE_3.index] >= 2)// 双活3
			score = 5000;
		else if (levelCount[Level.SLEEP_3.index] >= 1 && levelCount[Level.ALIVE_3.index] >= 1)// 活3眠3
			score = 1000;
		else if (levelCount[Level.ALIVE_2.index] >= 2)// 双活2
			score = 100;
		else if (levelCount[Level.SLEEP_2.index] >= 1 && levelCount[Level.ALIVE_2.index] >= 1)// 活2眠2
			score = 10;
		score = Math.max(score, Math.max(Math.max(level1.score, level2.score), Math.max(level3.score, level4.score)));
		return score;

	}


	//计算某个方向棋型
	private Level getLevel(Chess bean, int currentPlay, Direction dire) {
		String left="";
		String right="";
		if(dire==Direction.HENG) {
			left=getStringByDire(bean,currentPlay,-1,0);
			right=getStringByDire(bean,currentPlay,1,0);
		}else 	if(dire==Direction.SHU) {
			left=getStringByDire(bean,currentPlay,0,1);
			right=getStringByDire(bean,currentPlay,0,-1);
		}else 	if(dire==Direction.PIE) {
			left=getStringByDire(bean,currentPlay,1,1);
			right=getStringByDire(bean,currentPlay,-1,-1);
		}else 	if(dire==Direction.NA) {
			left=getStringByDire(bean,currentPlay,-1,1);
			right=getStringByDire(bean,currentPlay,1,-1);
		}
		//正方向
		String str=left+currentPlay+right;
		//反转字符串
		String strres=new StringBuffer(str).reverse().toString();
		
		for(Level level:Level.values()) {
			//根据正则表达式进行比较
			
			Pattern pattern=Pattern.compile(level.regex[currentPlay-1]);
			
			Matcher matcher=pattern.matcher(str);
			//如果返回true匹配成功
			boolean b1=matcher.find();
			Matcher matcher2=pattern.matcher(strres);
			boolean b2=matcher2.find();
			
			if(b1||b2) {
				//匹配成功
				return level;
			} 
		}
		
		
		return Level.NULL;
	}
	private String getStringByDire(Chess bean, int currentPlay2, int x, int y) {
		
		//false为正向
		boolean res=false;
//		if(y>0 ||(y==0&&y<0)) {
//			//反向拼接
//			
//		}
		if(x>0) {
			res=true;
		}
		int x_tmp=bean.getX();
		int y_tmp=bean.getY();
		String str="";
		for(int i=0;i<5;i++) {
			x_tmp+=x;
			y_tmp+=y;
			if(x_tmp>=0 && x_tmp<GameData.LINE_NUMBER && y_tmp>=0 && y_tmp<GameData.LINE_NUMBER) {
				if(res) {
					//反向拼接
					str=chessBeans[x_tmp][y_tmp].getPlayer()+str;
				}else {
					//正向拼接
					str+=chessBeans[x_tmp][y_tmp].getPlayer();
				}
			}
		}
		
		return str;
	}
	public static enum Level {
		CON_5("长连", 0, new String[] { "11111", "22222" }, 100000),
		ALIVE_4("活四", 1, new String[] { "011110", "022220" }, 10000),
		GO_4("冲四", 2, new String[] { "011112|0101110|0110110", "022221|0202220|0220220" }, 500),
		DEAD_4("死四", 3, new String[] { "211112", "122221" }, -5),
		ALIVE_3("活三", 4, new String[] { "01110|010110", "02220|020220" }, 200),
		SLEEP_3("眠三", 5,
				new String[] { "001112|010112|011012|10011|10101|2011102", "002221|020221|022021|20022|20202|1022201" },
				50),
		DEAD_3("死三", 6, new String[] { "21112", "12221" }, -5),
		ALIVE_2("活二", 7, new String[] { "00110|01010|010010", "00220|02020|020020" }, 5),
		SLEEP_2("眠二", 8,
				new String[] { "000112|001012|010012|10001|2010102|2011002",
						"000221|002021|020021|20002|1020201|1022001" },
				3),
		DEAD_2("死二", 9, new String[] { "2112", "1221" }, -5), NULL("null", 10, new String[] { "", "" }, 0);
		private String name;
		private int index;
		private String[] regex;// 正则表达式
		int score;// 分值

		// 构造方法
		private Level(String name, int index, String[] regex, int score) {
			this.name = name;
			this.index = index;
			this.regex = regex;
			this.score = score;
		}

		// 覆盖方法
		@Override
		public String toString() {
			return this.name;
		}
	};
	// 方向
	private static enum Direction {
		HENG, SHU, PIE, NA
	};

	// 位置分
	private static int[][] position = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0 },
			{ 0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0 }, 
			{ 0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 0 }, 
			{ 0, 1, 2, 3, 4, 5, 6, 6, 6, 5, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0 }, 
			{ 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0 },
			{ 0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0 }, 
			{ 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	public void huiqi2() {
		if (isGameOver) {
			JOptionPane.showMessageDialog(GoBangBoard.this, "请先开始新游戏！");
		} else {
			if (count > 2) {
				for (int i = 0; i < 2; i++) {
					Chess tempBean = getLastBean();
					currentPlay = tempBean.getPlayer();
					chessBeans[tempBean.getX()][tempBean.getY()].setPlayer(GameData.EMPTY); //
					chessBeans[tempBean.getX()][tempBean.getY()].setOrder(0);
					count--;
					//System.out.println("悔棋");
					repaint();
				}
			} else {
				JOptionPane.showMessageDialog(GoBangBoard.this, "你还没下棋呢！");
			}
		}

	}
	
}

