package org.example.utils.enums;

public enum ContextMenuOptions {
    PLAY_MOVIE("Play Movie"),
    DELETE("Delete"),
    APP_DETAILS("App Details"),
    OPEN_CONTENT_DETAILS("Open Content Details Page");
    private final String optionName;

    ContextMenuOptions(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }
}
