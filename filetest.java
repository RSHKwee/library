import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileGenerationTest {

    @Test
    void testGeneratedFileContent() throws IOException {
        // Set up the test environment
        Path tempDir = Files.createTempDirectory("file-gen-test");
        Path generatedFilePath = tempDir.resolve("generated.txt");

        // Generate the file
        generateFile(generatedFilePath);

        // Read the file content
        String fileContent = Files.readString(generatedFilePath);

        // Define the expected content
        String expectedContent = "This is the expected content.";

        // Write the JUnit test
        Assertions.assertEquals(expectedContent, fileContent);

        // Clean up
        Files.delete(generatedFilePath);
        Files.delete(tempDir);
    }

    private void generateFile(Path filePath) throws IOException {
        // Write code to generate the file with content
        String content = "This is the expected content.";
        Files.write(filePath, content.getBytes());
    }
}
