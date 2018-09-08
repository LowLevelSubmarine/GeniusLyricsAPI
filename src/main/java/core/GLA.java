package core;

import genius.Lyrics;
import genius.LyricsCrawler;

import java.util.HashMap;
import java.util.List;

public class GLA {

    private HashMap<String, List<Lyrics>> crawlCache;
    private HashMap<String, String> parseCache;
    private LyricsCrawler crawler;

    public GLA(String clientId, String accessToken) {

        //Construct LyricsCrawler
        this.crawler = new LyricsCrawler(clientId, accessToken, this);

        //Construct empty caches
        this.crawlCache = new HashMap<>();
        this.parseCache = new HashMap<>();

    }

    public List<Lyrics> search(String parameter) {
        return this.crawler.get(parameter);
    }

    public HashMap<String, List<Lyrics>> getCrawlCache() {
        return this.crawlCache;
    }

    public HashMap<String, String> getParseCache() {
        return this.parseCache;
    }

}
