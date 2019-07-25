package genius;

import com.sun.jndi.toolkit.url.Uri;
import core.GLA;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class SongSearch {

    private final GLA gla;
    private int status;
    private int nextPage;
    private LinkedList<Hit> hits = new LinkedList<>();

    public SongSearch(GLA gla, String query) throws IOException {
        this.gla = gla;
        query = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        try {
            URI uri = new URI("https://genius.com/api/search/song?page=1&q=" + query);
            request(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public SongSearch(GLA gla, String query, int page) throws IOException {
        this.gla = gla;
        query = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        try {
            URI uri = new URI("https://genius.com/api/search/song?page=" + page + "&q=" + query);
            request(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void request(URI uri) throws IOException {
        try {
            HttpURLConnection con = this.gla.getHttpManager().getConnection(uri.toURL());
            String result = this.gla.getHttpManager().executeGet(con);
            parse(new JSONObject(result));
        } catch (MalformedURLException e) {
            throw new InternalError();
        }
    }

    private void parse(JSONObject jRoot) {
        this.status = jRoot.getJSONObject("meta").getInt("status");
        JSONObject response = jRoot.getJSONObject("response");
        if (!response.isNull("next_page")) {
            this.nextPage = response.getInt("next_page");
        }
        JSONObject section = response.getJSONArray("sections").getJSONObject(0);
        JSONArray hits = section.getJSONArray("hits");
        for (int i = 0; i < hits.length(); i++) {
            JSONObject hitRoot = hits.getJSONObject(i).getJSONObject("result");
            this.hits.add(new Hit(hitRoot));
        }
    }

    public GLA getGla() {
        return gla;
    }

    public int getStatus() {
        return status;
    }

    public int getNextPage() {
        return nextPage;
    }

    public LinkedList<Hit> getHits() {
        return hits;
    }

    public class Hit {

        private long id;
        private String title;
        private String titleWithFeatured;
        private String url;
        private String imageUrl;
        private String thumbnailUrl;
        private Artist artist;

        public Hit(JSONObject jRoot) {
            this.id = jRoot.getLong("id");
            this.title = jRoot.getString("title");
            this.titleWithFeatured = jRoot.getString("title_with_featured");
            this.url = jRoot.getString("url");
            this.imageUrl = jRoot.getString("header_image_url");
            this.thumbnailUrl = jRoot.getString("song_art_image_thumbnail_url");
            this.artist = new Artist(jRoot.getJSONObject("primary_artist"));
        }

        public long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getTitleWithFeatured() {
            return titleWithFeatured;
        }

        public String getUrl() {
            return url;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public Artist getArtist() {
            return this.artist;
        }

        public String fetchLyrics() {
            return new LyricsParser(SongSearch.this.gla).get(this.id + "");
        }

    }

    public class Artist {

        private long id;
        private String imageUrl;
        private String name;
        private String slug;
        private String url;

        public Artist(JSONObject jRoot) {
            this.id = jRoot.getLong("id");
            this.imageUrl = jRoot.getString("image_url");
            this.name = jRoot.getString("name");
            this.slug = jRoot.getString("slug");
            this.url = jRoot.getString("url");
        }

        public long getId() {
            return id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getSlug() {
            return slug;
        }

        public String getUrl() {
            return url;
        }

    }

}
