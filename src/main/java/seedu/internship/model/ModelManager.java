package seedu.internship.model;

import static java.util.Objects.requireNonNull;
import static seedu.internship.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.internship.commons.core.GuiSettings;
import seedu.internship.commons.core.LogsCenter;
import seedu.internship.model.internship.Internship;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedInternBuddy versionedInternBuddy;
    private final UserPrefs userPrefs;
    private final FilteredList<Internship> filteredInternships;
    private Internship selectedInternship;

    /**
     * Initializes a ModelManager with the given versionedInternBuddy and userPrefs.
     */
    public ModelManager(ReadOnlyInternBuddy versionedInternBuddy, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(versionedInternBuddy, userPrefs);

        logger.fine("Initializing with address book: " + versionedInternBuddy + " and user prefs " + userPrefs);

        this.versionedInternBuddy = new VersionedInternBuddy(versionedInternBuddy);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredInternships = new FilteredList<>(this.versionedInternBuddy.getInternshipList());
        selectedInternship = null;
    }

    public ModelManager() {
        this(new InternBuddy(), new UserPrefs());
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
    public Path getInternBuddyFilePath() {
        return userPrefs.getInternBuddyFilePath();
    }

    @Override
    public void setInternBuddyFilePath(Path versionedInternBuddyFilePath) {
        requireNonNull(versionedInternBuddyFilePath);
        userPrefs.setInternBuddyFilePath(versionedInternBuddyFilePath);
    }

    //=========== InternBuddy ================================================================================

    @Override
    public void setInternBuddy(ReadOnlyInternBuddy versionedInternBuddy) {
        this.versionedInternBuddy.resetData(versionedInternBuddy);
    }

    @Override
    public ReadOnlyInternBuddy getInternBuddy() {
        return versionedInternBuddy;
    }

    @Override
    public boolean hasInternship(Internship internship) {
        requireNonNull(internship);
        return versionedInternBuddy.hasInternship(internship);
    }

    @Override
    public void deleteInternship(Internship target) {
        versionedInternBuddy.removeInternship(target);
    }

    @Override
    public void viewInternship(Internship target) {
        versionedInternBuddy.viewInternship(target);
    }

    @Override
    public void addInternship(Internship internship) {
        versionedInternBuddy.addInternship(internship);
        updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
    }

    @Override
    public void setInternship(Internship target, Internship editedInternship) {
        requireAllNonNull(target, editedInternship);

        versionedInternBuddy.setInternship(target, editedInternship);
    }

    //=========== Filtered Internship List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Internship} backed by the internal list of
     * {@code versionedInternBuddy}
     */
    @Override
    public ObservableList<Internship> getFilteredInternshipList() {
        return filteredInternships;
    }

    @Override
    public void updateFilteredInternshipList(Predicate<Internship> predicate) {
        requireNonNull(predicate);
        filteredInternships.setPredicate(predicate);
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
        return versionedInternBuddy.equals(other.versionedInternBuddy)
                && userPrefs.equals(other.userPrefs)
                && filteredInternships.equals(other.filteredInternships);
    }

    //=========== Selected internship =============================================================

    @Override
    public Internship getSelectedInternship() {
        return this.versionedInternBuddy.getSelectedInternship();
    }

    @Override
    public void updateSelectedInternship(Internship target) {
        this.versionedInternBuddy.updateSelectedInternship(target);
    }

    @Override
    public void copyInternship(Internship target) {
        versionedInternBuddy.copyInternship(target);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoInternBuddy() {
        return versionedInternBuddy.canUndo();
    }

    @Override
    public boolean canRedoInternBuddy() {
        return versionedInternBuddy.canRedo();
    }

    @Override
    public void undoInternBuddy() {
        versionedInternBuddy.undo();
    }

    @Override
    public void redoInternBuddy() {
        versionedInternBuddy.redo();
    }

    @Override
    public void commitInternBuddy() {
        versionedInternBuddy.commit();
    }

    @Override
    public void saveCurrentInternBuddy() {
        versionedInternBuddy.saveCurrentInternBuddy();
    }
}
