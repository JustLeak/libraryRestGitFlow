package by.intexsoft.restlibrary.model.dto;

public class SingleResponse<T> {
    private T content;
    private String error;

    public SingleResponse(String error) {
        this.error = error;
    }

    public SingleResponse(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public String getError() {
        return error;
    }
}
