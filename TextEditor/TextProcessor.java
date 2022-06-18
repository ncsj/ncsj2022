import  java.util.ArrayList;

public
class TextProcessor{
	String text = null;
	public TextProcessor(String text){
		this.text = text;
	}

	public int count(String word){
		char c = word.charAt(0);

		int end = text.length() - (word.length() - 1);
		int count = 0;
		for(int i=0;i<end;i++){
			char m = text.charAt(i);
			if(m == c){
				count++;
				System.out.println("i : " + i);
			}
		}

		return count;
	}

	public int [] search(String word){
		int [] indexes = null;
		ArrayList <Integer> array = new ArrayList <Integer> ();

		char c = word.charAt(0);

		int end = text.length() - (word.length() - 1);
		for(int i=0;i<end;i++){
			char m = text.charAt(i);
			if(m == c){
				System.out.println("i : " + i);
				Integer val = Integer.valueOf( i );
				array.add(val);
			}
		}

		indexes = new int [array.size()];
		for(int i=0;i<array.size();i++){
			Integer val = array.get(i);
			indexes[i] = val.intValue();
		}

		return indexes;
	}
}

