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
		
		testerFile = Paths.get("junit2.txt");
		if(!Files.exists(testerFile)) {
			Files.createFile(testerFile);
		}
		Files.writeString(testerFile, "According to all known laws of aviation, there is no way a bee should be able to fly.");
		
		if(Files.exists(Paths.get("objects"))) deleteDirectory(Paths.get("objects").toFile());
		if(Files.exists(Paths.get("index"))) Files.delete(Paths.get("index"));
		
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
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void TestHash() {
		assertEquals(Blob.encrypt("hello from JUnit"), "d577a8113be0f1aaa3ee3618107b2f95a747423c");
	}
	
	@Test
	void TestBlob() {
		try {
			if(!Files.exists(Paths.get("objects"))) Files.createDirectory(Paths.get("objects"));
			Blob blob = new Blob("junit.txt");
			Path path = Paths.get("objects/d577a8113be0f1aaa3ee3618107b2f95a747423c");
			assertTrue(Files.exists(path));
			assertEquals(Files.readString(path), "hello from JUnit");
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
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
			idx.addBlob("junit2.txt");
			indexContent = Files.readString(Paths.get("index"));
			assertEquals(indexContent, "junit.txt:d577a8113be0f1aaa3ee3618107b2f95a747423c\njunit2.txt:6e97dba1aafc15a1358a57d52a710ab6598f12be");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void TestIndexRemove() {
		Index idx;
		try {
			idx = new Index("this string does nothing");
			idx.addBlob("junit.txt");
			idx.addBlob("junit2.txt");
			String indexContent = Files.readString(Paths.get("index"));
			assertEquals(indexContent, "junit.txt:d577a8113be0f1aaa3ee3618107b2f95a747423c\\njunit2.txt:6e97dba1aafc15a1358a57d52a710ab6598f12be");
			
			idx.removeBlob("junit.txt");
			indexContent = Files.readString(Paths.get("index"));
			assertEquals(indexContent, "junit2.txt:6e97dba1aafc15a1358a57d52a710ab6598f12be");
			assertFalse(Files.exists(Paths.get("junit.txt")));
			
			idx.removeBlob("junit2.txt");
			indexContent = Files.readString(Paths.get("index"));
			assertEquals(indexContent, "");
			assertFalse(Files.exists(Paths.get("junit2.txt")));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}
