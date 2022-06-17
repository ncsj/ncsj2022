import  java.awt.*;
import  java.awt.event.*;

import  java.util.ArrayList;
import  java.io.FileInputStream;
import  java.io.InputStreamReader;
import  java.io.BufferedReader;

class Ex01c extends Frame{
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

	boolean isReady = false;

	public Ex01c(){
		setBounds(300,50,800,600);

		initChecker();
		
		for(MouseListener l : mouseListeners){
			addMouseListener( l );
		}

		addWindowListener(new WindowAdapter(){
			@Override
			public void windowOpened(WindowEvent e){
				setReady();
			}
		});

		setVisible(true);
	}

	void setReady(){
		isReady = true;
		repaint();
	}

	void initChecker(){
		ArrayList <Integer> xlist = new ArrayList <Integer> ();
		ArrayList <Integer> ylist = new ArrayList <Integer> ();
		ArrayList <Integer> wlist = new ArrayList <Integer> ();
		ArrayList <Integer> hlist = new ArrayList <Integer> ();
		ArrayList <Color>	clist = new ArrayList <Color> ();
		try{
			FileInputStream fin = new FileInputStream("rectangles.csv");
			InputStreamReader is = new InputStreamReader(fin);
			BufferedReader reader = new BufferedReader(is);

			while(true){
				String line = reader.readLine();
				if(line == null){
					break;
				}

				String [] cols = line.split(",");
				try{
					Integer ix = Integer.valueOf(cols[0]);
					Integer iy = Integer.valueOf(cols[1]);
					Integer iw = Integer.valueOf(cols[2]);
					Integer ih = Integer.valueOf(cols[3]);

					xlist.add(ix);
					ylist.add(iy);
					wlist.add(iw);
					hlist.add(ih);

					Color color = Color.gray;
					switch(cols[4]){
						case "blue":
							color = Color.blue;
							break;
						case "yellow":
							color = Color.yellow;
							break;
						case "pink":
							color = Color.pink;
							break;
						case "red":
							color = Color.red;
							break;
						case "green":
							color = Color.green;
							break;
						case "orange":
							color = Color.orange;
							break;
						case "cyan":
							color = Color.cyan;
							break;
						case "magenta":
							color = Color.magenta;
							break;
						default:
							break;
					}

					clist.add(color);
				}
				catch(NumberFormatException ne){
					;
				}
			}
			reader.close();
			is.close();
			fin.close();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		this.mouseListeners = new MouseListener [clist.size()];
		this.comps			= new PaintComponent [clist.size()];
		

		for(int i=0;i<clist.size();i++){
			int x			= xlist.get(i).intValue();
			int y			= ylist.get(i).intValue();
			int w			= wlist.get(i).intValue();
			int h			= hlist.get(i).intValue();
			Color color		= clist.get(i);
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color);
			this.comps[i] = checker;
			this.mouseListeners[i] = checker;
		}
	}

	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0,0,800,600);

		if(isReady){
			if(this.comps != null){
				for(PaintComponent comp : this.comps){
					comp.paint(g);
				}
			}
		}
	}

	public static void main(String args[]){
		new Ex01c();
	}
}
