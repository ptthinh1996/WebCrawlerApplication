import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class DatViet {
	private HashSet<String> links;
	private List<List<String>> articles;
	
	public DatViet() {
		links = new HashSet<String>();
		articles = new ArrayList<List<String>>();
	}
	
	public void getPageLinks(String URL) {
		if(!links.contains(URL)) {
			try {
				Document document = Jsoup.connect(URL).get();
                Elements otherLinks = document.select("a[href^=\"https://datviettour.com.vn/du-lich-trong-nuoc.shtml/page/\"]");
                for(Element page : otherLinks)
                {
                	if(links.add(URL)) {
                		System.out.println(URL);
                	}
                	getPageLinks(page.attr("abs:href"));
                }
			}catch(Exception e)
			{
				System.err.println(e.getMessage());
			}
			
		}
	}
	
	public void getArticles() {
		links.forEach(a->{
			Document doc;
			try {
				doc = Jsoup.connect(a).get();
			    Elements ahref = doc.getElementsByTag("a");
		        for (Element href : ahref) {
		            String link = href.absUrl("href");
		            String c = href.className();
		            if (link.contains("tour-du-lich") == true && c.equals("more-detail1") == true) {
		                Element x = href.select("div.name1.clearfix > div.name2 > h3 > b").first();
		                Element y = href.select("div.name1.clearfix > div.price-list.clearfix > cite").first();
						System.out.println(x.text()+"\nGia: "+y.text()+"\nLink: "+ link + "\n----------");
		            }
		        }
			}catch(Exception e)
			{
                System.err.println(e.getMessage());
			}
		});
	}
	
}

