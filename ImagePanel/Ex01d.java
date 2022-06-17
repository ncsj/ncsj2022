import  java.awt.*;
import  java.awt.event.*;

import  java.util.ArrayList;
import  java.io.FileInputStream;
import  java.io.InputStreamReader;
import  java.io.BufferedReader;

class Ex01d extends Frame{
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

		String photo = null;
		Image image = null;

		public PositionChecker(Frame frame,int x,int y,int w,int h,Color color,String photo){
			this.frame = frame;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.color = color;
			this.photo = photo;

			IntOverCheckItem xcheck1 = new IntOverCheckItem(x);
			IntUnderCheckItem xcheck2 = new IntUnderCheckItem(x+w);

			IntOverCheckItem ycheck1 = new IntOverCheckItem(y);
			IntUnderCheckItem ycheck2 = new IntUnderCheckItem(y+h);

			list1.add(xcheck1);
			list1.add(xcheck2);

			list2.add(ycheck1);
			list2.add(ycheck2);

			setImage();
		}

		void setImage(){
			this.image = loadImage(this.photo);
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
				g.drawRect(x,y,w,h);
				g.drawImage(image,x,y,frame);
			}
			else{
				g.fillRect(x,y,w,h);
			}
		}
	}

	PositionChecker [] checkers		= null;
	PaintComponent [] comps			= null;
	MouseListener [] mouseListeners = null;

	boolean isReady = false;

	Button  btn1 = new Button("RESET");

	public Ex01d(){
		setBounds(0,50,1300,600);
		setLayout(null);

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

		add(btn1);
		btn1.setBounds(650,540,100,30);
		btn1.addActionListener((ActionEvent e)->{ reset(); });

		setVisible(true);
	}

	void reset(){
		if(this.checkers != null){
			for(PositionChecker checker : this.checkers){
				checker.state = false;
			}

			repaint();
		}
	}

	void setReady(){
		isReady = true;
		repaint();
	}

	int image_count = 0;
	Image loadImage(String photo){
		Image image = null;
		try{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			image = toolkit.getImage(photo);

			MediaTracker mt = new MediaTracker(this);
			mt.addImage(image,image_count);
			mt.waitForID(image_count);

			image_count++;
		}
		catch(Exception e){
		}
		return image;
	}

	void initChecker(){
		ArrayList <Integer> xlist = new ArrayList <Integer> ();
		ArrayList <Integer> ylist = new ArrayList <Integer> ();
		ArrayList <Integer> wlist = new ArrayList <Integer> ();
		ArrayList <Integer> hlist = new ArrayList <Integer> ();
		ArrayList <Color>	clist = new ArrayList <Color> ();
		ArrayList <String>	plist = new ArrayList <String> ();
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
					plist.add(cols[5]);
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
		this.checkers		= new PositionChecker [clist.size()];

		for(int i=0;i<clist.size();i++){
			int x			= xlist.get(i).intValue();
			int y			= ylist.get(i).intValue();
			int w			= wlist.get(i).intValue();
			int h			= hlist.get(i).intValue();
			Color color		= clist.get(i);
			String photo	= plist.get(i);
			
			PositionChecker checker = new PositionChecker(this,x,y,w,h,color,photo);
	
			this.checkers[i] = checker;
			this.comps[i] = checker;
			this.mouseListeners[i] = checker;
		}
	}

	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0,0,1300,600);

		if(isReady){
			if(this.comps != null){
				for(PaintComponent comp : this.comps){
					comp.paint(g);
				}
			}
		}
	}

	public static void main(String args[]){
		new Ex01d();
	}
}
