package Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReplacer {
	public static void main(String[] args) {
		
		updateContent("irvine", "alexandria");
	}

	public static void updateContent(String originalPassword, String newPassword) {
		System.out.println("original: "+originalPassword+" new "+ newPassword);
		try {
			Path path = Paths.get("..\\local\\identities.conf");
			Stream<String> lines = Files.lines(path);
			List<String> replaced = lines.map(line -> line.replaceAll(originalPassword, newPassword)).collect(Collectors.toList());
			Files.write(path, replaced);
			lines.close();
			System.out.println("Find and Replace done!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}