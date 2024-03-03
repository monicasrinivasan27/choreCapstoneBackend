package org.launchcode.taskcrusher.enums;

public enum ContactStatus {

    QUESTIONS ("Questions"),
    TECHNICAL ("Technical"),
    FEEDBACK ("Feedback"),
    OTHER ("Other");

    private final String displayName;

    ContactStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
