public class IntPrimeCheckItem implements CheckItem{
	public IntPrimeCheckItem(){
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

		for(int i=2;i<value/2;i++){
			int n = value % i;
			if(n == 0){
				rtc = false;
				break;
			}
		}

		return rtc;
	}

	public String toString(){
		String s =  "Prime Check";
		return s;
	}

	/**
	  Test Code
	**/
	public static void main(String args[]){
		java.util.ArrayList <Integer> list = new java.util.ArrayList <Integer> ();
		for(int i=3;i<=100;i++){
			Integer  n  = Integer.valueOf(i);

			try{
				IntPrimeCheckItem item = new IntPrimeCheckItem();
				if(item.check(n)){
					// System.out.println(item.toString());
					// System.out.println(n + " is prime number.");

					list.add( n );
				}
				else{
					// System.out.println(n + " is not prime number.");
				}
			}
			catch(CheckItemException e){
				System.out.println(e.toString());
			}
		}

		for(int i=0;i<list.size();i++){
			if(i>0){
				System.out.print(",");
			}
			Integer n = list.get(i);
			System.out.print(n);
		}

		System.out.println();
	}
}
