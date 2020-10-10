package wuziqi;

public class Chess implements Comparable<Chess> {
	
	//（0，0）---（14，14）
	private int x;
	private int y;
	
	//表示哪方玩家，0：空，1：黑子，2：白子
	private int player;
	
	//顺序	
	private int order;
	
	//
	private int offense;
	private int defense;
	public int getOffense() {
		return offense;
	}

	public void setOffense(int offense) {
		this.offense = offense;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public StringBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

	private int sum;
	
	private StringBuffer buffer=new StringBuffer();
	public Chess(){
		
	}
	
	public Chess(int x,int y,int player,int order) {
		this.x=x;
		this.y=y;
		this.player=player;
		this.order=order;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int compareTo(Chess o) {
		if(this.getSum()>o.getSum()) {
			return -1;
		}else {
			if(this.getSum()<o.getSum()) {
				return 1;
			}
		}
		return 0;
	}
}
