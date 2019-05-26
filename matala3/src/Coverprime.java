/**
 * @author 312501844_320466238
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class  PrimeWrapper implements Runnable {
	    long n;
	    boolean result;
	    /**
		 * Constructor
		 * @param n long
		 *
		 */
	    PrimeWrapper(long n){
	        this.n = n;
	    }
	    /**
		 *Running the Ex3A_tester
		 */
	    @Override public void run() {
	        result = Ex3A_tester.isPrime(n);
	    }
	

	public static void main(String[] args) {
	    long n = 7;
	    PrimeWrapper isPrime = new PrimeWrapper(n);
	    ExecutorService executor = Executors.newSingleThreadExecutor();
	    Future<?> future = executor.submit(isPrime);

	    try {
	        future.get(3, TimeUnit.SECONDS);
	    } catch (Exception e) { // If future could not get() the result
	        future.cancel(true);
	        executor.shutdownNow();
	        System.out.println("isPrime went in to an infintie loop. Exiting...");
	        System.exit(-1);
	    }
	    future.cancel(true);
	    executor.shutdownNow();
	    System.out.println("Is " + n + " a prime number: " + isPrime.result);
	}
}