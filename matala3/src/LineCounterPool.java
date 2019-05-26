
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
/**
 * @author 312501844_320466238
 */
public class LineCounterPool implements Callable<Integer>{

	private String fileName;
	private int numberOfLines;
	/**
	 * creates LineCounterPool Thread Object using fileName as a path to the file
	 * @param name	path to the be tested file.
	 */
	public LineCounterPool(String name) {
		this.fileName = name;
		this.numberOfLines = 0;
	}
	/**
	 * get Number Of Lines
	 * @return number integer
	 */
	public Integer call() throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
			while(br.readLine() != null) {
				numberOfLines++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numberOfLines;
	}

}
