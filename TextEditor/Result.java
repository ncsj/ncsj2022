public class Result{
	String	word;
	int		count;

	public Result(){
		this.word = null;
		this.count = 0;
	}

	public Result(String word,int count){
		this.word	= word;
		this.count	= count;
	}

	public String toString(){
		String s = this.word + " (" + this.count + ")";
		return s;
	}
}
