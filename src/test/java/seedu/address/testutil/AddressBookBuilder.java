package seedu.address.testutil;

import seedu.address.model.BugTracker;
import seedu.address.model.bug.Bug;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private BugTracker bugTracker;

    public AddressBookBuilder() {
        bugTracker = new BugTracker();
    }

    public AddressBookBuilder(BugTracker bugTracker) {
        this.bugTracker = bugTracker;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Bug bug) {
        bugTracker.addPerson(bug);
        return this;
    }

    public BugTracker build() {
        return bugTracker;
    }
}
