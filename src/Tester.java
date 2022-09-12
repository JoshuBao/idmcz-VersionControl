import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tester {

	void deleteDirectory(File directoryToBeDeleted) { // https://www.baeldung.com/java-delete-directory
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    directoryToBeDeleted.delete();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		Path testerFile = Paths.get("junit.txt");
		if(!Files.exists(testerFile)) {
			Files.createFile(testerFile);
		}
		Files.writeString(testerFile, "hello from JUnit");
		
//		if(Files.exists(Paths.get("Objects"))) deleteDirectory(Paths.get("Objects").toFile());
//		if(Files.exists(Paths.get("index"))) Files.delete(Paths.get("index"));
		
	}

	@Test
	void TestInit() {
		Index idx;
		try {
			idx = new Index("this string does nothing");
			idx.initialize();
			
			assertTrue(Files.exists(Paths.get("objects")));
			assertTrue(Files.exists(Paths.get("index")));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void TestHash() {
		assertEquals(Blob.encrypt("hello from JUnit"), "d577a8113be0f1aaa3ee3618107b2f95a747423c");
	}
	
	@Test
	void TestBlob() {
		try {
			Blob blob = new Blob("junit.txt");
			Path path = Paths.get("objects/d577a8113be0f1aaa3ee3618107b2f95a747423c");
			assertTrue(Files.exists(path));
			assertEquals(Files.readString(path), "hello from JUnit");
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void TestIndexAdd() {
		Index idx;
		try {
			idx = new Index("this string does nothing");
			idx.addBlob("junit.txt");
			String indexContent = Files.readString(Paths.get("index"));
			// You may need to modify the next line depending on if you're putting spaces between the colon, adding a trailing newline at the end of the file, etc
			assertEquals(indexContent, "junit.txt:d577a8113be0f1aaa3ee3618107b2f95a747423c");
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Test
	void TestIndexRemove() {
		Index idx;
		try {
			idx = new Index("this string does nothing");
			idx.addBlob("junit.txt");
			String indexContent = Files.readString(Paths.get("index"));
			// You may need to modify the next line depending on if you're putting spaces between the colon, adding a trailing newline at the end of the file, etc
			assertEquals(indexContent, "junit.txt:d577a8113be0f1aaa3ee3618107b2f95a747423c");
			idx.removeBlob("junit.txt");
			indexContent = Files.readString(Paths.get("index"));
			// You may need to modify the next line depending on if you're adding a trailing newline at the end of the file, etc
			assertEquals(indexContent, "");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
