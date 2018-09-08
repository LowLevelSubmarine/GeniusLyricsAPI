package genius;

import core.GLA;
import core.ProjectInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class LyricsParser {

    private static final String GENIUS_URL = "http://genius.com";

    private GLA gla;

    public LyricsParser(GLA gla) {

        //Construct crawlCache
        this.gla = gla;

    }

    public String get(String path) {
        if (this.gla.getParseCache().containsKey(path)) {
            return this.gla.getParseCache().get(path);
        } else {
            String lyricsText = parseLyricsText(path);
            this.gla.getParseCache().put(path, lyricsText);
            return lyricsText;
        }
    }

    private String parseLyricsText(String path) {
        try {
            Document doc = Jsoup.connect(GENIUS_URL + path).referrer("http://discordapp.com").userAgent(ProjectInfo.NAME).get();
            Element element = doc.getElementsByClass("lyrics").first();
            return getText(element);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getText(Element lyricsElement) {
        lyricsElement.getElementsByTag("a").unwrap();
        lyricsElement.getElementsByTag("p").unwrap();
        Elements elements = lyricsElement.getAllElements();
        for (Element element1 : elements) {
            if (!element1.tag().getName().equals("a") && !element1.tag().getName().equals("br") && !element1.tag().getName().equals("p")) element1.remove();
        }
        removeComments(lyricsElement);
        String lyrics = lyricsElement.html();
        lyrics = lyrics.replaceAll("\n", "");
        lyrics = lyrics.replaceAll("<br> ", "\n");
        lyrics = lyrics.replaceAll("<p>", "").replaceAll("</p>", "");
        return lyrics;
    }

    private void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }

}
