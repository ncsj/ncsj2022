import  java.awt.*;
import  java.awt.event.*;


class Ex01 extends Frame{
	class PositionChecker{
		IntCheckList  list1 = new IntCheckList();
		IntCheckList  list2 = new IntCheckList();

		public PositionChecker(int x,int y,int w,int h){
			IntOverCheckItem xcheck1 = new IntOverCheckItem(x);
			IntUnderCheckItem xcheck2 = new IntUnderCheckItem(w);

			IntOverCheckItem ycheck1 = new IntOverCheckItem(y);
			IntUnderCheckItem ycheck2 = new IntUnderCheckItem(h);

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
	}

	PositionChecker blueChecker = null;
	PositionChecker orangeChecker = null;
	PositionChecker redChecker = null;

	public Ex01(){
		setBounds(300,50,800,600);

		{
			int x = 50;
			int y = 50;
			int w = 150;
			int h = 200;
			this.blueChecker = new PositionChecker(x,y,x+w,y+h);
		}
		{
			int x = 250;
			int y = 50;
			int w = 150;
			int h = 200;
			this.orangeChecker = new PositionChecker(x,y,x+w,y+h);
		}
		{
			int x = 450;
			int y = 50;
			int w = 150;
			int h = 200;
			this.redChecker = new PositionChecker(x,y,x+w,y+h);
		}

		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
			
				if(blueChecker.check(x,y)){
					blue();
				}
				else if(orangeChecker.check(x,y)){
					orange();
				}
				else if(redChecker.check(x,y)){
					red();
				}
			}
		});

		setVisible(true);
	}

	void blue(){
		System.out.println("BLUE!");
	}
	void orange(){
		System.out.println("ORANGE!");
	}
	void red(){
		System.out.println("RED!");
	}

	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0,0,800,600);

		g.setColor(Color.blue);
		g.drawRect(50,50,150,200);
		
		g.setColor(Color.orange);
		g.drawRect(250,50,150,200);
		
		g.setColor(Color.red);
		g.drawRect(450,50,150,200);
	}

	public static void main(String args[]){
		new Ex01();
	}
}
