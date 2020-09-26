package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bug.Bug;
import seedu.address.model.bug.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class DescriptionBookTest {

    private final BugTracker bugTracker = new BugTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), bugTracker.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bugTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        BugTracker newData = getTypicalAddressBook();
        bugTracker.resetData(newData);
        assertEquals(newData, bugTracker);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Bug editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bug> newBugs = Arrays.asList(ALICE, editedAlice);
        BugTrackerStub newData = new BugTrackerStub(newBugs);

        assertThrows(DuplicatePersonException.class, () -> bugTracker.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bugTracker.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(bugTracker.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        bugTracker.addPerson(ALICE);
        assertTrue(bugTracker.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        bugTracker.addPerson(ALICE);
        Bug editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(bugTracker.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> bugTracker.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class BugTrackerStub implements ReadOnlyBugTracker {
        private final ObservableList<Bug> bugs = FXCollections.observableArrayList();

        BugTrackerStub(Collection<Bug> bugs) {
            this.bugs.setAll(bugs);
        }

        @Override
        public ObservableList<Bug> getPersonList() {
            return bugs;
        }
    }

}
