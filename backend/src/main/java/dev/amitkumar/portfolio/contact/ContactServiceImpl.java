package dev.amitkumar.portfolio.contact;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactServiceImpl(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Override
    @Transactional
    public ContactResponse submit(ContactRequest request) {
        ContactMessage message = new ContactMessage(
                null,
                request.name(),
                request.email(),
                request.subject(),
                request.message(),
                ContactMessageStatus.NEW,
                null,
                null,
                null,
                null);
        return ContactResponse.from(contactMessageRepository.save(message));
    }
}
