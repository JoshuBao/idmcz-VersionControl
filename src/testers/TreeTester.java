package testers;
import git.MyTree;

public class TreeTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] str = new String[] {
				"tree : 4843a77c34c464f3582f2fd10b6d52fde3c41744", // Bee Movie
				"tree : a4ea92f9ab7458324cc66c8bb7062d6b4e536510", // Morbius
				"blob : a4ea325cb2b904d0f914125b3347ce094c54be61", // A Bug's Life
				"blob : 8c9b13eb3c5b29cadc65dad4596fee04162c5d72" // The Emoji Movie
			};
			MyTree tree = new MyTree(str);
			if (tree.getName().matches("^[a-fA-F0-9]{40}$"))
				System.out.println("Correct SHA1");
			else
				System.out.println("Incorrect SHA1");
			String c = tree.contents();
			if (c.substring(0,48).equals("tree : 4843a77c34c464f3582f2fd10b6d52fde3c41744"))
				System.out.println("Correct contents (1/4)");
			if (c.substring(48,97).equals("tree : a4ea92f9ab7458324cc66c8bb7062d6b4e536510"))
				System.out.println("Correct contents (2/4)");
			if (c.substring(97,146).equals("blob : a4ea325cb2b904d0f914125b3347ce094c54be61"))
				System.out.println("Correct contents (3/4)");
			if (c.substring(146).equals("blob : 8c9b13eb3c5b29cadc65dad4596fee04162c5d72"))
				System.out.println("Correct contents (4/4)");
}
}