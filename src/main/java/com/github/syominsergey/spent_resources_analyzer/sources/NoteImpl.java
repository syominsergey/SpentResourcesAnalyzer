package com.github.syominsergey.spent_resources_analyzer.sources;

import java.util.List;

/**
 * Created by Sergey on 28.06.2017.
 */
public class NoteImpl implements Note {

    String title;

    public void setTitle(String title) {
        this.title = title;
    }

    List<String> tags;

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTags() {
        return tags;
    }
}
