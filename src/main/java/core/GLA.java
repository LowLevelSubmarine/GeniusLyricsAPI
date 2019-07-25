package core;

import genius.SongSearch;

import java.io.IOException;

public class GLA {

    private HttpManager httpManager = new HttpManager();

    public SongSearch search(String query) throws IOException {
        return new SongSearch(this, query);
    }

    public HttpManager getHttpManager() {
        return this.httpManager;
    }

    public static void main(String[] args) throws Exception {
        GLA gla = new GLA();
        System.out.println("Searching...");
        long startMs = System.currentTimeMillis();
        System.out.println(gla.search("Kygo").getHits().get(0).fetchLyrics());
        System.out.println(System.currentTimeMillis() - startMs + "ms");
    }

}
