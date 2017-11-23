import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
		
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		DatViet a = new DatViet();
		a.getPageLinks("https://datviettour.com.vn/du-lich-trong-nuoc.shtml");
		a.getArticles();
	}	
}
