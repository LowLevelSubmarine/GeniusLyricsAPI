package genius;

import core.GLA;

public class Lyrics {

    private String path;
    private GLA gla;

    public Lyrics(String path, GLA gla) {
        this.path = path;
        this.gla = gla;
    }

    public String getText() {
        return new LyricsParser(this.gla).get(this.path);
    }

    @Override
    public String toString() {
        return this.path;
    }

}
