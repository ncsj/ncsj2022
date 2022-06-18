import  java.awt.*;
import  java.awt.event.*;

import  java.net.URL;
import  java.net.MalformedURLException;

class URLDialog extends Dialog implements Closable{
	TextEditor editor = null;
	
	Label		label = new Label("URL");
	TextField	field = new TextField();
	Button		btn1  = new Button("OK");
	Button		btn2  = new Button("CLOSE");
	
	public URLDialog(Frame parent){
		// modal dialog box
		super(parent,"Load URL ...",true);

		if(parent instanceof TextEditor){
			this.editor = (TextEditor)parent;
		}
			
		setBounds(600,300,400,250);
		setLayout(null);

		initControls();

		addWindowListener(new Closer(this));
		setVisible(true);
	}

	void initControls(){
		add(label);
		label.setBounds(50,80,50,20);

		add(field);
		field.setBounds(100,80,280,20);

		add(btn1);
		btn1.setBounds(200,120,80,30);
		btn1.addActionListener((ActionEvent e)->{ openURL(); });
		
		add(btn2);
		btn2.setBounds(290,120,80,30);
		btn2.addActionListener((ActionEvent e)->{ close(); });
	}

	URL  url = null;
	public void openURL(){
		try{
			String s = field.getText();
			this.url = new URL( s );
		}
		catch(MalformedURLException e){
			System.out.println(e.toString());
		}
		
		close();
	}

	@Override
	public void close(){
		setVisible(false);
		dispose();
	}

	/**
	  Test Code
	**/
	public static void main(String args[]){
		Frame frame = new Frame();
		frame.setBounds(100,100,100,100);
		frame.setVisible(true);
		new URLDialog(frame);
	}
}
