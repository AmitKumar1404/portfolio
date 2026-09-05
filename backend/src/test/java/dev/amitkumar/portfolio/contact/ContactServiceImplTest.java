package dev.amitkumar.portfolio.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactMessageRepository contactMessageRepository;

    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactServiceImpl(contactMessageRepository);
    }

    @Test
    void submitMapsRequestAndPersistsNewMessage() {
        when(contactMessageRepository.save(any(ContactMessage.class))).thenReturn(ContactFixtures.saved());

        ContactResponse response = contactService.submit(ContactFixtures.validRequest());

        ArgumentCaptor<ContactMessage> captor = ArgumentCaptor.forClass(ContactMessage.class);
        verify(contactMessageRepository).save(captor.capture());
        ContactMessage persisted = captor.getValue();

        assertThat(persisted.getId()).isNull();
        assertThat(persisted.getName()).isEqualTo("Test Visitor");
        assertThat(persisted.getEmail()).isEqualTo("visitor@example.com");
        assertThat(persisted.getSubject()).isEqualTo("Portfolio Contact Test");
        assertThat(persisted.getMessage()).isEqualTo("This is a test contact message.");
        assertThat(persisted.getStatus()).isEqualTo(ContactMessageStatus.NEW);
        assertThat(persisted.getIpHash()).isNull();
        assertThat(persisted.getUserAgent()).isNull();
        assertThat(response.id()).isEqualTo(42L);
    }

    @Test
    void submitReturnsOnlyPublicResponseFields() {
        when(contactMessageRepository.save(any(ContactMessage.class))).thenReturn(ContactFixtures.saved());

        ContactResponse response = contactService.submit(ContactFixtures.validRequest());

        assertThat(response.id()).isEqualTo(42L);
        assertThat(response).hasNoNullFieldsOrProperties();
        assertThat(ContactResponse.class.getRecordComponents()).hasSize(1);
        assertThat(ContactResponse.class.getRecordComponents()[0].getName()).isEqualTo("id");
    }
}
