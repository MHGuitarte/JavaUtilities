package base;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLParser {

	public void printHTML(String file, PrintWriter out) {
		String fileOutput = "";
		File f = null;
		FileReader reader = null;
		BufferedReader buffer = null;

		try {
			f = new File(file);
			System.out.println(f.getPath());
			System.out.println(f.getAbsolutePath());
			System.out.println(f.getCanonicalPath());
			reader = new FileReader(f);
			buffer = new BufferedReader(reader);

			while ((fileOutput = buffer.readLine()) != null) {
				out.println(fileOutput);
			}

		} catch (FileNotFoundException e) {
			// TODO Controlar esto más adelante -> PROVISIONAL

			e.printStackTrace();

			/*
			 * try { reader.close(); buffer.close(); } catch (IOException e1) {
			 * System.err.println("Error fatal en el cierre del archivo"); }
			 */
		} catch (IOException e) {
			// TODO Controlar esto más adelante -> PROVISIONAL
			e.printStackTrace();
			/*
			 * try { reader.close(); buffer.close(); } catch (IOException e1) {
			 * System.err.println("Error fatal en el cierre del archivo"); }
			 */
		}
	}

}
