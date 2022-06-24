import  java.awt.*;
import  java.awt.event.*;

import  java.util.ArrayList;
import  java.util.Properties;
import  java.io.FileInputStream;
import  java.io.InputStreamReader;
import  java.io.BufferedReader;

public
class ImagePanel extends Frame{
	public static String HOME = null;
	public static final String IMAGE_PANEL_DIR = "/ImagePanel/";

	static{
		// Comment
		Properties props = System.getProperties();
		HOME = props.getProperty("user.dir") + IMAGE_PANEL_DIR;
	}

	PositionChecker [] checkers		= null;
	PaintComponent [] comps			= null;
	MouseAdapter [] mouseListeners = null;

	boolean isReady = false;

	Button  btn1 = new Button("RESET");

	ArrayList <Integer> xlist = new ArrayList <Integer> ();		// X
	ArrayList <Integer> ylist = new ArrayList <Integer> ();		// Y
	ArrayList <Integer> wlist = new ArrayList <Integer> ();		// Width
	ArrayList <Integer> hlist = new ArrayList <Integer> ();		// Hight
	ArrayList <Color>	clist = new ArrayList <Color> ();		// Color
	ArrayList <String>	plist = new ArrayList <String> ();		// Photo(Image) File
	ArrayList <String>	dlist = new ArrayList <String> ();		// Desc File

	public ImagePanel(){
		setBounds(0,50,1300,600);
		setLayout(null);

		loadRectangles();
		initChecker();

		for(MouseAdapter l : mouseListeners){
			addMouseListener( l );
			addMouseMotionListener( l );
		}

		addWindowListener(new WindowAdapter(){
			@Override
			public void windowOpened(WindowEvent e){
				setReady();
			}
		});

		add(btn1);
		btn1.setBounds(650,540,100,30);
		btn1.setBackground(Color.white);
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
	public Image loadImage(String photo){
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

	void loadRectangles(){
		try{
			FileInputStream fin = new FileInputStream(HOME + "rectangles.csv");
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

					Color color = checkColor(cols[4]);

					clist.add(color);
					plist.add(HOME + cols[5]);
					dlist.add(HOME + cols[6]);
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
	}

	void initChecker(){
		this.mouseListeners = new MouseAdapter [clist.size()];
		this.comps			= new PaintComponent [clist.size()];
		this.checkers		= new PositionChecker [clist.size()];

		for(int i=0;i<clist.size();i++){
			int x			= xlist.get(i).intValue();
			int y			= ylist.get(i).intValue();
			int w			= wlist.get(i).intValue();
			int h			= hlist.get(i).intValue();
			Color color		= clist.get(i);
			String photo	= plist.get(i);
			String desc		= dlist.get(i);

			PositionChecker checker = new PositionChecker(this,x,y,w,h,color,photo);

			DescWindow window = new DescWindow(this,desc);
			// Window window = new Window(this);
			window.setBounds(x+30,y+100,400,180);
			checker.setWindow(window);

			this.checkers[i] = checker;
			this.comps[i] = checker;
			this.mouseListeners[i] = checker;
		}
	}

	Color checkColor(String s){
		Color color = Color.gray;
		switch( s ){
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

		return color;
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
		new ImagePanel();
	}
}
