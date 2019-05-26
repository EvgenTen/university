/**
 * @author 312501844_320466238
 */
public class Ex3A {
	
	
	private class threadisprime extends Thread {
		long num;
		boolean hasfinish = false, isprime;
		
		/**
		 * Constructor
		 * @param name String
		 * @param num long
		 */
		public threadisprime(String name, long num) {
			super(name);
			this.num = num;
		}
		
		/**
		 *Running the Ex3A_tester
		 */
		public void run() {
			isprime = Ex3A_tester.isPrime(num);
			hasfinish = true;

		}
	}
	/**
	 * Function to check for a prime number
	 * @param n long
	 * @param maxTime double
	 * @return boolean  true or false
	 */
	@SuppressWarnings("deprecation")
	public boolean isPrime(long n, double maxTime) throws RuntimeException {
		threadisprime tip = new threadisprime("threadisprime", n);
		try {
			tip.start();
			tip.join((long) (1000 * (maxTime % 1)));
			if (tip.hasfinish)
				return tip.isprime;

		} catch (Throwable e) {
			System.out.println(e);
		}finally {
			tip.stop();
		}
		throw new RuntimeException("time is up!");
		
	}


}