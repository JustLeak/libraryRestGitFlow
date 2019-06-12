package by.intexsoft.restlibrary.model.enumeration;

public enum Genre {
    ACTION, ANTHOLOGY, CLASSIC, COMIC, DETECTIVE, DRAMA, FANTASY,
    HORROR, LEGEND, MYSTERY, ROMANCE, POETRY;

    public static Genre forString(String format) {
        for (Genre type : values()) {
            if (type.name().equalsIgnoreCase(format)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid genre: " + format + ".");
    }
}
