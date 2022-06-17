public class IntEvenCheckItem implements CheckItem{
	public IntEvenCheckItem(){
	}

	@Override
	public boolean check(Object o) throws CheckItemException{
		if(o instanceof Integer){
			;
		}
		else{
			throw new CheckItemException("Not Support Type");
		}

		boolean rtc = true;

		Integer v = (Integer)o;
		int value = v.intValue();

		int n = value % 2;
		if(n == 0){
			rtc = true;
		}
		else{
			rtc = false;
		}

		return rtc;
	}

	public String toString(){
		String s =  "Even Check";
		return s;
	}

	/**
	  Test Code
	**/
	public static void main(String args[]){
		Integer  n  = Integer.valueOf(args[0]);

		try{
			IntEvenCheckItem item = new IntEvenCheckItem();
			if(item.check(n)){
				System.out.println(item.toString());
				System.out.println(n + " is even.");
			}
		}
		catch(CheckItemException e){
			System.out.println(e.toString());
		}
	}
}
