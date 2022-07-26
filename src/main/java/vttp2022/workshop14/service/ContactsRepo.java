package vttp2022.workshop14.service;

import vttp2022.workshop14.model.Contact;

public interface ContactsRepo {
    public void save(final Contact ctc);
    public Contact findById(final String contactId);
}
