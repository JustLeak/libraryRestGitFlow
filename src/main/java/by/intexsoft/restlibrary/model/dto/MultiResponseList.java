package by.intexsoft.restlibrary.model.dto;

import java.util.List;

public class MultiResponseList<T> {
    private List<T> content;
    private String error;

    public MultiResponseList(String error) {
        this.error = error;
    }

    public MultiResponseList(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public String getError() {
        return error;
    }
}
