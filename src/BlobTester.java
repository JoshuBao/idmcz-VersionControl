import java.io.IOException;
import java.util.HashMap;

public class BlobTester {
	public static void main(String [] args) throws IOException {
		Blob a = new Blob("TREE1.txt");
		Blob b = new Blob("TREE2.txt");
		Blob c = new Blob("BLOB1.txt");
		Blob d = new Blob("BLOB2.txt");
		
		String[] str = new String[] {
			"tree : 4843a77c34c464f3582f2fd10b6d52fde3c41744", // Bee Movie
			"tree : a4ea92f9ab7458324cc66c8bb7062d6b4e536510", // Morbius
			"blob : a4ea325cb2b904d0f914125b3347ce094c54be61", // A Bug's Life
			"blob : 8c9b13eb3c5b29cadc65dad4596fee04162c5d72" // The Emoji Movie
		};
		Tree tree = new Tree(str);
	}
}

	