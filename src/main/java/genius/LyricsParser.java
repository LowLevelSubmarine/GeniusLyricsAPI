package genius;

import core.GLA;
import core.ProjectInfo;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class LyricsParser {

    private static final String GENIUS_EMBED_URL_HEAD = "https://genius.com/songs/";
    private static final String GENIUS_EMBED_URL_TAIL = "/embed.js";

    private GLA gla;

    public LyricsParser(GLA gla) {

        //Construct crawlCache
        this.gla = gla;

    }

    public String get(String id) {
        if (this.gla.getParseCache().containsKey(id)) {
            return this.gla.getParseCache().get(id);
        } else {
            String lyricsText = parseLyrics(id);
            this.gla.getParseCache().put(id, lyricsText);
            return lyricsText;
        }
    }

    private String parseLyrics(String id) {
        try {
            URLConnection connection = new URL(GENIUS_EMBED_URL_HEAD + id + GENIUS_EMBED_URL_TAIL).openConnection();
            connection.setRequestProperty("User-Agent", ProjectInfo.VERSION);
            Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.toString());
            scanner.useDelimiter("\\A");
            String raw = "";
            while (scanner.hasNext()) {
                raw += scanner.next();
            }
            if (raw.equals("")) {
                return null;
            }
            return getReadable(raw);
        } catch (IOException e) {
            return null;
        }
    }

    private String getReadable(String rawLyrics) {
        //Remove start
        rawLyrics = rawLyrics.replaceAll("[\\S\\s]*<div class=\\\\\\\\\\\\\"rg_embed_body\\\\\\\\\\\\\">[ (\\\\\\\\n)]*", "");
        //Remove end
        rawLyrics = rawLyrics.replaceAll("[ (\\\\\\\\n)]*<\\\\/div>[\\S\\s]*", "");
        //Remove tags between
        rawLyrics = rawLyrics.replaceAll("<[^<>]*>", "");
        //Unescape spaces
        rawLyrics = rawLyrics.replaceAll("\\\\\\\\n","\n");
        //Unescape '
        rawLyrics = rawLyrics.replaceAll("\\\\'", "'");
        //Unescape "
        rawLyrics = rawLyrics.replaceAll("\\\\\\\\\\\\\"", "\"");
        return rawLyrics;
    }
}
