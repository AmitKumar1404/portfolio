package dev.amitkumar.portfolio.contact;

public record ContactResponse(long id) {

    static ContactResponse from(ContactMessage saved) {
        return new ContactResponse(saved.getId());
    }
}
