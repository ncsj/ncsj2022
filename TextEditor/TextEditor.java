import  java.awt.*;
import  java.awt.event.*;

import  java.io.*;

class TextEditor extends Frame implements Closable{
	TextArea  area = new TextArea();
	Panel  bottom = new Panel();

	String  dir		= null;
	String  file	= null;

	MenuBar	mbar		= new MenuBar();
	Menu	fileMenu	= new Menu("File");
	Menu	editMenu	= new Menu("Edit");
	Menu	srchMenu	= new Menu("Search");
	Menu	helpMenu	= new Menu("Help");

	String [] fileMenuItems = {"New","-","Open","URL","-","Save","SaveAs","-","Print","-","Exit"};
	String [] editMenuItems = {"Undo","-","Cut","Copy","-","Past"};
	String [] srchMenuItems = {"Search String","-","Replace"};
	String [] helpMenuItems = {"Index","-","about This"};

	Label label = null;

	public TextEditor(){
		setBounds(0,0,1024,768);
		add("Center",area);

		label = new Label("365");
		bottom.add(label);
		add("South",bottom);

		initMenu();

		addWindowListener(new Closer(this));
		setVisible(true);
	}

	void initMenu(){
		mbar.add(fileMenu);
		mbar.add(editMenu);
		mbar.add(srchMenu);
		mbar.add(helpMenu);

		{
			ActionListener [] listeners = new ActionListener [fileMenuItems.length];
			listeners[0] = (ActionEvent e)->{newContents();};	// New
			listeners[1] = null;								// ---
			listeners[2] = (ActionEvent e)->{openFile();};		// Open
			listeners[3] = (ActionEvent e)->{openURL();};		// URL
			listeners[4] = null;								// ---
			listeners[5] = (ActionEvent e)->{saveFile();};		// Save
			listeners[6] = (ActionEvent e)->{saveAsFile();};	// SaveAs
			listeners[7] = null;								// ---
			listeners[8] = (ActionEvent e)->{printContents();};	// Print
			listeners[9] = null;								// ---
			listeners[10] = (ActionEvent e)->{close();};		// Exit

			setMenu(fileMenu,fileMenuItems,listeners);
		}

		setMenu(editMenu,editMenuItems);

		{
			ActionListener [] listeners = new ActionListener [srchMenuItems.length];
			listeners[0] = (ActionEvent e)->{ openSearchDialog(); };
			listeners[1] = null;
			listeners[2] = (ActionEvent e)->{ openReplaceDialog(); };
			setMenu(srchMenu,srchMenuItems,listeners);
		}

		setMenu(helpMenu,helpMenuItems);

		setMenuBar(mbar);
	}

	void setMenu(Menu menu,String [] items){
		for(String item : items){
			MenuItem  menuItem = new MenuItem(item);
			menu.add(menuItem);
		}
	}

	void setMenu(Menu menu,String [] items,ActionListener [] listeners){
		for(int i=0;i<items.length;i++){
			MenuItem  menuItem = new MenuItem(items[i]);
			if(listeners[i] != null){
				menuItem.addActionListener(listeners[i]);
			}
			menu.add(menuItem);
		}
	}

	void newContents(){
		area.setText("");
	}

	void openFile(){
		FileDialog dlg = new FileDialog(this,"Open Text File ...",FileDialog.LOAD);
		dlg.setVisible(true);

		String fname = dlg.getFile();
		String dname = dlg.getDirectory();
		if(fname != null && dname != null){
			try{
				FileInputStream fin = new FileInputStream(dname + fname);
				InputStreamReader is = new InputStreamReader(fin);

				this.file = fname;
				this.dir  = dname;
			
				int count = 0;	
				StringBuilder sb = new StringBuilder();
				while(true){
					int c = is.read();
					if(c > -1){
						sb.append((char)c);
						count++;
					}
					else{
						break;
					}
				}
				
				is.close();
				fin.close();

				String text = sb.toString();
				area.setText(text);

				String s = count + "c";
				label.setText( s );
			}
			catch(FileNotFoundException e){
				System.out.println(e.toString());
			}
			catch(IOException e){
				System.out.println(e.toString());
			}
		}
	}

	void openURL(){
		URLDialog dlg = new URLDialog(this);

		java.net.URL url = dlg.url;
		if(url != null){
			loadURL(url);
		}
	}

	void loadURL(java.net.URL url){
		try{
			java.net.URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);

			StringBuilder sb = new StringBuilder();
			while(true){
				int c = reader.read();
				if(c > -1){
					sb.append((char)c);
				}
				else{
					break;
				}
			}

			String text = sb.toString();
			area.setText(text);

			reader.close();
			is.close();

		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	void saveFile(){
		if(file != null && dir != null){
			String text = area.getText();

			try{
				FileOutputStream fout = new FileOutputStream(this.dir + this.file);
				PrintStream ps = new PrintStream(fout);

				ps.print(text);

				ps.close();
				fout.close();
			}
			catch(FileNotFoundException e){
				System.out.println(e.toString());
			}
			catch(IOException e){
				System.out.println(e.toString());
			}
		}
		else{
			saveAsFile();
		}
	}

	void saveAsFile(){
		FileDialog dlg = new FileDialog(this,"Save As File ...",FileDialog.SAVE);
		dlg.setVisible(true);

		String file = dlg.getFile();
		String dir  = dlg.getDirectory();
		if(file != null && dir != null){
			this.file = file;
			this.dir  = dir;
			saveFile();
		}
	}

	void printContents(){
		System.out.println("Print Contents ...");
	}

	void openSearchDialog(){
		new SearchDialog(this);
	}

	void openReplaceDialog(){
	}

	@Override
	public void close(){
		setVisible(false);
		dispose();
	}

	public static void main(String args[]){
		new TextEditor();
	}
}
