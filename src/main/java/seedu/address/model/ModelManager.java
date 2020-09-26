package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.bug.Bug;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BugTracker bugTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<Bug> filteredBugs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyBugTracker addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.bugTracker = new BugTracker(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBugs = new FilteredList<>(this.bugTracker.getPersonList());
    }

    public ModelManager() {
        this(new BugTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setBugTracker(ReadOnlyBugTracker bugTracker) {
        this.bugTracker.resetData(bugTracker);
    }

    @Override
    public ReadOnlyBugTracker getBugTracker() {
        return bugTracker;
    }

    @Override
    public boolean hasPerson(Bug bug) {
        requireNonNull(bug);
        return bugTracker.hasPerson(bug);
    }

    @Override
    public void deletePerson(Bug target) {
        bugTracker.removePerson(target);
    }

    @Override
    public void addPerson(Bug bug) {
        bugTracker.addPerson(bug);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Bug target, Bug editedBug) {
        requireAllNonNull(target, editedBug);

        bugTracker.setPerson(target, editedBug);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Bug> getFilteredPersonList() {
        return filteredBugs;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Bug> predicate) {
        requireNonNull(predicate);
        filteredBugs.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return bugTracker.equals(other.bugTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredBugs.equals(other.filteredBugs);
    }

}
