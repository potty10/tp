package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.internship.Internship;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Internship> PREDICATE_SHOW_ALL_INTERNSHIPS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getInternBuddyFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setInternBuddyFilePath(Path InternBuddyFilePath);

    /**
     * Replaces address book data with the data in {@code internbuddy}.
     */
    void setInternBuddy(ReadOnlyInternBuddy internBuddy);

    /** Returns the InternBuddy */
    ReadOnlyInternBuddy getInternBuddy();

    /**
     * Returns true if an internship with the same identity as {@code Internship} exists in InternBuddy.
     */
    boolean hasInternship(Internship internship);

    /**
     * Deletes the given internship.
     * The internship must exist in InternBuddy.
     */
    void deleteInternship(Internship target);

    /**
     * Deletes all internships based on a predicate
     * @param predicate Predicate to filter out internships
     */
    void deleteInternshipByPredicate(Predicate<Internship> predicate);

    /**
     * Views the given internship.
     * The {@code internship} must exist in InternBuddy.
     */
    void viewInternship(Internship target);

    /**
     * Adds the given internship.
     * {@code internship} must not already exist in InternBuddy.
     */
    void addInternship(Internship internship);

    /**
     * Replaces the given internship {@code target} with {@code editedInternship}.
     * {@code target} must exist in InternBuddy.
     * The internship identity of {@code editedInternship} must not be the same as another existing internship in
     * the address book.
     *
     * @param target The given internship to be replaced.
     * @param editedInternship The internship to replace the original one.
     */
    void setInternship(Internship target, Internship editedInternship);

    /** Returns an unmodifiable view of the filtered internship list */
    ObservableList<Internship> getFilteredInternshipList();

    /**
     * Updates the filter of the filtered internship list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInternshipList(Predicate<Internship> predicate);

    /**
     * Gets the internship that is currently being selected for viewing
     *
     * @return the currently selected internship
     */
    Internship getSelectedInternship();


    /**
     * Updates the internship that is currently being selected for viewing
     *
     * @param target The internship that is selected for viewing
     */
    void updateSelectedInternship(Internship target);
}
