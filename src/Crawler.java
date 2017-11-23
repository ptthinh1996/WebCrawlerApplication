import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;


import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class Crawler {
	
	private static final String USER_AGENT =
	"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
			
	private List<Document> gDocs = new ArrayList<>();
	Set<String> result = new HashSet<>();
	
	public void Connect(String... URL)
	{
		for (String url : URL) {
			try {
				Document gDoc = Jsoup.connect(url).userAgent(USER_AGENT).get();
	//			gDoc = Jsoup.parse(new URL(URL).openStream(), "UTF-8", URL);
				gDoc.outputSettings().charset().forName("UTF-8");
				
				gDocs.add(gDoc);
			}catch(IOException ioe)
			{
				System.out.println("Fail to connection to %s"+URL);
			}
		}
	}

	public void crawl(String Word) {
		
		if(gDocs==null || gDocs.size() == 0)
		{
			System.out.println("Fail to find data in global document! gDoc is null");
		}
		else
		{
			for (Document gDoc : gDocs) {
				try {
				Elements ahref = gDoc.getElementsByTag("a");
				for(Element href : ahref)
					{
						if(isContains(href, Word))
						{
							String link="", text="", price="";
							link = href.absUrl("href");
							text = href.text();
							if(link.contains("du-lich-trong-nuoc")==true || link.contains("tour-trong-nuoc")==true || link.contains("tour-du-lich")==true)
							{
								if(link.contains("saigontourist.net")==true)
								{
									price = href.select("h2").text();
									System.out.println("Ten tour: "+ text +"\nGia tien: "+ price +"\n link:" +link);
									System.out.println("---------------");

								}
								else if(link.contains("datviettour.com.vn")==true)
								{
									price = href.select(".price").text();
									System.out.println("Ten tour: "+ text +"\nGia tien: "+ price +"\n link:" +link);
									System.out.println("---------------");
								}
								else
								{
									price = href.select(".mda-money").text();
									System.out.println("Ten tour: "+ text +"\nGia tien: "+ price +"\n link:" +link);
									System.out.println("---------------");
								}
							}
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	public boolean isContains(Element href, String Word)
	{
		String bodyText = href.text();
        return bodyText.toLowerCase().contains(Word.toLowerCase());
	}

}
	
