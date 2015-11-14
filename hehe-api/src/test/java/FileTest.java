import java.io.File;
import java.io.IOException;

/**
 * @author elvis.xu
 * @since 2015-11-14 16:27
 */
public class FileTest {
	public static void main(String[] args) throws IOException {
		File f = File.createTempFile("HAHA", ".UP");
		System.out.println(f.getAbsolutePath());
	}
}
