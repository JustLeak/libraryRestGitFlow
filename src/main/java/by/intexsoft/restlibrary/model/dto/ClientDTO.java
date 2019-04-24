package by.intexsoft.restlibrary.model.dto;

public class ClientDTO {
    private Long clientId;
    private Long libraryCardId;
    private String name;
    private String surname;
    private String birthday;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
