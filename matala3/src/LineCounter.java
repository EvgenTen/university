
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * @author 312501844_320466238
 */
public class LineCounter extends Thread{
	private String fileName;
	private int numberOfLines;
	
	/**
	 * creates LineCounter Thread Object using fileName as a path to the file
	 * @param fileName	path to the be tested file.
	 */

	public LineCounter(String fileName){
		this.fileName = fileName;
		this.numberOfLines=0;
	}
	@Override
	/**
	 * runs the check.
	 */
	public void run() {
		try {
			FileReader fr = new FileReader(this.fileName);
			BufferedReader br = new BufferedReader(fr);
			while(br.readLine() != null) {
				numberOfLines++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * get Number Of Lines
	 * @return number integer
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}
	
	
}
