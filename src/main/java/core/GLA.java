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

    public List<Lyrics> search(String query) {
        return this.crawler.get(query);
    }

    public List<Lyrics> search(String artist, String title) {
        return this.crawler.get(artist + " " + title);
    }

    public HashMap<String, List<Lyrics>> getCrawlCache() {
        return this.crawlCache;
    }

    public HashMap<String, String> getParseCache() {
        return this.parseCache;
    }

    public static void main(String[] args) {
        GLA gla = new GLA("8RFzUBZ3DF3ZrZAJNHooPeF19q02T0Utds0ZcG5JyL_0BWSzIt2eFI8A8rAMYehA", "4xXT0NinKTI_gO1Y1c6BFsA3SO-lomU6Dtvqw2Dkzo9fq1VsM6qg-Mzv0CTvi9Hf");
        System.out.println("Searching...");
        List<Lyrics> lyricsList = gla.search("billie eilish when the party is over");
        System.out.println(lyricsList.get(0).getText());
    }

}
