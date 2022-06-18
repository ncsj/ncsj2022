import  java.awt.*;
import  java.awt.event.*;

import  java.util.ArrayList;

public
class SearchDialog extends Dialog implements Closable{
	TextEditor editor = null;
	
	Label		label = new Label("STRING");
	TextField	field = new TextField();
	List		list  = new List();
	Button		btn1  = new Button("SEARCH");
	Button		btn2  = new Button("CLOSE");

	int		target = 0;

	ArrayList <Result> array = new ArrayList <Result> ();
	
	public SearchDialog(Frame parent){
		// modeless dialog box
		super(parent,"Search String ...",false);
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
		int  x1 = 50;
		int  x2 = 140;
		int  y1 = 50;
		int  y2 = 80;
		int  y3 = 210;

		add(label);
		label.setBounds(x1,y1,80,20);

		add(field);
		field.setBounds(x2,y1,210,20);

		add(list);
		list.setBounds(x2,y2,210,90);
		list.addItemListener((ItemEvent e)->{ selectItem(); });

		add(btn1);
		btn1.setBounds(200,y3,80,30);
		btn1.addActionListener((ActionEvent e)->{ searchString(); });
		
		add(btn2);
		btn2.setBounds(290,y3,80,30);
		btn2.addActionListener((ActionEvent e)->{ close(); });
	}

	void searchString(){
		String word = field.getText();
		String text = editor.area.getText();

		TextProcessor tp = new TextProcessor(text);
		int count = tp.count(word);

		if(count > 0){
			boolean flag = true;
			for(Result r : array){
				if(r.word.equals(word)){
					flag = false;
					break;
				}
			}

			if(flag){
				Result result = new Result(word,count);
				array.add(result);

				String item = result.toString();
				list.add(item);
			}

			int [] indexes = tp.search(word);
			int begin = indexes[target];
			int end = begin + word.length();
			editor.area.select(begin,end);

			target++;
			if(target >= count){
				target = 0;
			}
		}
	}

	void selectItem(){
		int index = list.getSelectedIndex();
		if(index > -1){
			Result result = array.get(index);
			field.setText(result.word);
		}
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
		new SearchDialog(frame);
	}
}
