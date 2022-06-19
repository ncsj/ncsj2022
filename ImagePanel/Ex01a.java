import  java.awt.*;
import  java.awt.event.*;

class Ex01a extends Frame{
	class PositionChecker extends MouseAdapter implements PaintComponent{
		boolean  state = false;
		IntCheckList  list1 = new IntCheckList();
		IntCheckList  list2 = new IntCheckList();

		Frame  frame = null;
		int x;
		int y;
		int w;
		int h;
		Color  color = null;

		public PositionChecker(Frame frame,int x,int y,int w,int h,Color color){
			this.frame = frame;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.color = color;

			IntOverCheckItem xcheck1 = new IntOverCheckItem(x);
			IntUnderCheckItem xcheck2 = new IntUnderCheckItem(x+w);

			IntOverCheckItem ycheck1 = new IntOverCheckItem(y);
			IntUnderCheckItem ycheck2 = new IntUnderCheckItem(y+h);

			list1.add(xcheck1);
			list1.add(xcheck2);

			list2.add(ycheck1);
			list2.add(ycheck2);
		}

		public boolean check(int x,int y){
			boolean rtc = false;
			try{
				if(list1.check(x) && list2.check(y)){
					rtc = true;
				}
				else{
					rtc = false;
				}
			}
			catch(CheckItemException e){
			}

			return rtc;
		}

		@Override
		public void mouseClicked(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			if(check(x,y)){
				if(state){
					state = false;
				}
				else{
					state = true;
				}
				
				frame.repaint();
			}
		}

		@Override
		public void paint(Graphics g){
			g.setColor(color);
			if(state){
				g.fillRect(x,y,w,h);
			}
			else{
				g.drawRect(x,y,w,h);
			}
		}
	}

	PositionChecker blueChecker = null;
	PositionChecker orangeChecker = null;
	PositionChecker redChecker = null;

	PositionChecker greenChecker = null;
	PositionChecker yellowChecker = null;
	PositionChecker pinkChecker = null;

	PaintComponent [] comps = null;
	MouseListener [] mouseListeners = null;

	public Ex01a(){
		setBounds(300,50,800,600);

		initChecker();

		this.mouseListeners = new MouseListener [6];
		this.comps			= new PaintComponent [6];
		this.comps[0] = this.blueChecker;
		this.comps[1] = this.orangeChecker;
		this.comps[2] = this.redChecker;
		this.comps[3] = this.greenChecker;
		this.comps[4] = this.yellowChecker;
		this.comps[5] = this.pinkChecker;

		mouseListeners[0] = this.blueChecker;
		mouseListeners[1] = this.orangeChecker;
		mouseListeners[2] = this.redChecker;
		mouseListeners[3] = this.greenChecker;
		mouseListeners[4] = this.yellowChecker;
		mouseListeners[5] = this.pinkChecker;

		for(MouseListener l : mouseListeners){
			addMouseListener( l );
		}

		setVisible(true);
	}

	void initChecker(){
		{
			int x = 50;
			int y = 50;
			int w = 150;
			int h = 200;
			this.blueChecker = new PositionChecker(this,x,y,w,h,Color.blue);
		}
		{
			int x = 250;
			int y = 50;
			int w = 150;
			int h = 200;
			this.orangeChecker = new PositionChecker(this,x,y,w,h,Color.orange);
		}
		{
			int x = 450;
			int y = 50;
			int w = 150;
			int h = 200;
			this.redChecker = new PositionChecker(this,x,y,w,h,Color.red);
		}
		{
			int x = 50;
			int y = 300;
			int w = 150;
			int h = 200;
			this.greenChecker = new PositionChecker(this,x,y,w,h,Color.green);
		}
		{
			int x = 250;
			int y = 300;
			int w = 150;
			int h = 200;
			this.yellowChecker = new PositionChecker(this,x,y,w,h,Color.yellow);
		}
		{
			int x = 450;
			int y = 300;
			int w = 150;
			int h = 200;
			this.pinkChecker = new PositionChecker(this,x,y,w,h,Color.pink);
		}
	}

	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0,0,800,600);

		if(this.comps != null){
			for(PaintComponent comp : this.comps){
				comp.paint(g);
			}
		}
	}

	public static void main(String args[]){
		new Ex01a();
	}
}
