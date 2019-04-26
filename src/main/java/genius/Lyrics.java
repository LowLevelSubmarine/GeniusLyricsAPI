package genius;

import core.GLA;

public class Lyrics {

    private final GLA gla;
    private final String path;
    private final String id;

    public Lyrics(GLA gla, String id, String path) {
        this.path = path;
        this.gla = gla;
        this.id = id;
    }

    public String getText() {
        return new LyricsParser(this.gla).get(this.id);
    }

    @Override
    public String toString() {
        return this.path;
    }

}
