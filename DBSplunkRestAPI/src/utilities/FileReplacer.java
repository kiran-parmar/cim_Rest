package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReplacer {
	public static void main(String[] args) {
		String dir = System.getProperty("user.dir");
		String fileLocation = dir + "\\src\\utilities\\test.txt";
		updateContent(fileLocation, "irvine", "alexandria");
	}

	public static void updateContent(String target, String originalString, String newString) {
		try {
			Path path = Paths.get(target);
			Stream<String> lines = Files.lines(path);
			List<String> replaced = lines.map(line -> line.replaceAll(originalString, ""))
					.collect(Collectors.toList());
			Files.write(path, replaced);
			lines.close();
			System.out.println("Find and Replace done!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
