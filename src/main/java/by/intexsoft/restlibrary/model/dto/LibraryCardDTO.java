package by.intexsoft.restlibrary.model.dto;

public class LibraryCardDTO {
    private Long clientId;
    private Long libraryCardId;
    private String startDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getLibraryCardId() {
        return libraryCardId;
    }

    public void setLibraryCardId(Long libraryCardId) {
        this.libraryCardId = libraryCardId;
    }
}
