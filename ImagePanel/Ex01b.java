import  java.awt.*;
import  java.awt.event.*;

import  java.io.*;

class Ex01b extends Frame{
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

	PaintComponent [] comps = null;
	MouseListener [] mouseListeners = null;

	public Ex01b(){
		setBounds(300,50,800,600);

		this.mouseListeners = new MouseListener [6];
		this.comps			= new PaintComponent [6];
		
		initChecker();
		
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
			Color color = Color.blue;
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[0] = checker;
			this.mouseListeners[0] = checker;
		}
		{
			int x = 250;
			int y = 50;
			int w = 150;
			int h = 200;
			Color color = Color.orange;
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[1] = checker;
			this.mouseListeners[1] = checker;
		}
		{
			int x = 450;
			int y = 50;
			int w = 150;
			int h = 200;
			Color color = Color.red;
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[2] = checker;
			this.mouseListeners[2] = checker;
		}
		{
			int x = 50;
			int y = 300;
			int w = 150;
			int h = 200;
			Color color = Color.green;
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[3] = checker;
			this.mouseListeners[3] = checker;
		}
		{
			int x = 250;
			int y = 300;
			int w = 150;
			int h = 200;
			Color color = Color.yellow;
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[4] = checker;
			this.mouseListeners[4] = checker;
		}
		{
			int x = 450;
			int y = 300;
			int w = 150;
			int h = 200;
			Color color = Color.pink;
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[5] = checker;
			this.mouseListeners[5] = checker;
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
		new Ex01b();
	}
}
