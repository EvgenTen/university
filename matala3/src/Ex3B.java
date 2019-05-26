
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 312501844_320466238
 */
public class Ex3B {
	/**
	 * Creates n number of files with random number of lines with the phase
	 *  "Hello World!"
	 * 
	 * @param n Number of files to create
	 * 
	 * @return fileNames type of String[] includes the file paths.
	 */

	public static String[] createFiles(int n) {
		String[] fileNames = null;
		if (n > 0) { // validation, else return null
			fileNames = new String[n];
			final String LINEINFILE = "Hello World!";
			Random r = new Random(123);
			for (int i = 0; i < n; i++) { // iterate n times
				String path = "src/File_" + (i + 1);
				int numLines = r.nextInt(1000);
				try {
					FileWriter fw = new FileWriter(path); // create the i file
					PrintWriter pw = new PrintWriter(fw);
					fileNames[i] = path;
					for (int j = 0; j < numLines; j++) {
						if (j + 1 < numLines)
							pw.println(LINEINFILE);
						else
							pw.print(LINEINFILE);
					}
					pw.close();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileNames;
	}

	/**
	 * deletes the files contained in the fileName array
	 * 
	 * @param fileNames array of fileNames (paths) to delete
	 */
	public static void deleteFiles(String[] fileNames) {
		if (fileNames != null) { // validation
			for (int i = 0; i < fileNames.length; i++) { // iterate over the array
				File file = new File(fileNames[i]);
				if (file.exists()) { // check if the file exists.
					file.delete();
				}
			}
		}
	}

	/**
	 * creating files, reading how many lines the files contains and deletes them
	 * also, printing the execution time it took to read the number of lines. *using
	 * the main thread only* - time of counting the files' lines is linear
	 * 
	 * @param numFiles number of files to manipulate.
	 * 
	 */
	private static void countLinesOneProcess(int numFiles) {
		// creating files
		String[] files = createFiles(numFiles);

		// initializing numberOfLines
		int numberOfLines = 0;
		// snapshot start time stamp
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < files.length; i++) {
			try {
				FileReader fr = new FileReader(files[i]);
				BufferedReader br = new BufferedReader(fr);
				while (br.readLine() != null) {
					numberOfLines++;
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// snapshot end time stamp
		long endTime = System.currentTimeMillis();
		System.out.println("linear time = " + (endTime - startTime) + " ms, lines = " + numberOfLines);

		// deleting files
		deleteFiles(files);
	}

	/**
	 * creating files, reading how many lines the files contains and deletes them
	 * also, printing the execution time it took to read the number of lines.Thread pool is used to reduce the
	 * number of application threads and provide management of the worker threads.
	 * @param numFiles number of files to manipulate.
	 * 
	 */
	public static void countLinesThreadPool(int numFiles) {

		String[] filesNames = createFiles(numFiles);
		int numberOfLines = 0;
		ExecutorService executor = Executors.newFixedThreadPool(numFiles);
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < filesNames.length; i++) {
			LineCounterPool lc = new LineCounterPool(filesNames[i]);
			Future<Integer> fut = executor.submit(lc);

			try {
				numberOfLines = numberOfLines + fut.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("ThreadPool time = " + (endTime - startTime) + " ms, lines = " + numberOfLines);

		deleteFiles(filesNames);
		executor.shutdown();
	}

	/**
	 * creating files, reading how many lines the files contains and deletes them
	 * also, printing the execution time it took to read the number of lines.
	 * *creates thread for each file* - counting the lines simultaneously using the
	 * threads
	 * 
	 * @param numFiles number of files to manipulate.
	 * 
	 */
	public static void countLinesThread(int numFiles) {
		String[] files = createFiles(numFiles);
		ArrayList<LineCounter> threads = new ArrayList<LineCounter>();

		int numberOfLines = 0;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < files.length; i++) {
			threads.add(new LineCounter(files[i]));
			threads.get(i).start();
		}
		while (!threads.isEmpty()) { // as long there are alive threads, keep iterate over the threads.
			Iterator<LineCounter> it = threads.iterator(); // get list iterator to iterate over the threads
			while (it.hasNext()) {
				LineCounter thread = it.next();
				if (!thread.isAlive()) { // if a thread is not alive anymore, get the numberOfLines.
					numberOfLines += thread.getNumberOfLines(); // sum the lines.
					it.remove(); // remove from the thread from the list.
				}
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Threads time = " + (endTime - startTime) + " ms, lines = " + numberOfLines);

		deleteFiles(files);
	}

////	for tests
	public static void main(String[] args) {

			int numFiles = 1000;
			System.out.println("num = " + numFiles);
			countLinesOneProcess(numFiles);
			countLinesThread(numFiles);
			countLinesThreadPool(numFiles);
			

		

	}
}