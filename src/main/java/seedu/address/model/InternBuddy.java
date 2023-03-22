package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.UniqueInternshipList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameInternship comparison)
 */
public class InternBuddy implements ReadOnlyInternBuddy {

    private final UniqueInternshipList internships;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        internships = new UniqueInternshipList();
    }

    public InternBuddy() {}

    /**
     * Creates an InternBuddy using the Internships in the {@code toBeCopied}
     */
    public InternBuddy(ReadOnlyInternBuddy toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Internship list with {@code internships}.
     * {@code internships} must not contain duplicate internships.
     */
    public void setInternships(List<Internship> internships) {
        this.internships.setInternships(internships);
    }

    /**
     * Resets the existing data of this {@code InternBuddy} with {@code newData}.
     */
    public void resetData(ReadOnlyInternBuddy newData) {
        requireNonNull(newData);

        setInternships(newData.getInternshipList());
    }

    //// internship-level operations

    /**
     * Returns true if an internship with the same identity as {@code internship} exists in the address book.
     */
    public boolean hasInternship(Internship internship) {
        requireNonNull(internship);
        return internships.contains(internship);
    }

    /**
     * Adds an internship to the address book.
     * The internship must not already exist in the address book.
     */
    public void addInternship(Internship p) {
        internships.add(p);
    }

    /**
     * Replaces the given internship {@code target} in the list with {@code editedInternship}.
     * {@code target} must exist in the address book.
     * The internship identity of {@code editedInternship} must not be the same as another existing internship
     * in the address book.
     */
    public void setInternship(Internship target, Internship editedInternship) {
        requireNonNull(editedInternship);

        internships.setInternship(target, editedInternship);
    }

    /**
     * Removes {@code key} from this {@code InternBuddy}.
     * {@code key} must exist in InternBuddy.
     */
    public void removeInternship(Internship key) {
        internships.remove(key);
    }

    /**
     * Removes all internships matching {@code predicate} from this {@code InternBuddy}.
     * {@code key} must exist in InternBuddy.
     */
    public void removeInternshipByPredicate(Predicate<Internship> predicate) {
        internships.removeByPredicate(predicate);
    }

    /**
     * Gets {@code key} from this {@code InternBuddy}.
     * {@code key} must exist in InternBuddy.
     *
     */
    public void viewInternship(Internship key) {
        internships.view(key);
    }



    //// util methods

    @Override
    public String toString() {
        return internships.asUnmodifiableObservableList().size() + " internships";
        // TODO: refine later
    }

    @Override
    public ObservableList<Internship> getInternshipList() {
        return internships.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternBuddy // instanceof handles nulls
                && internships.equals(((InternBuddy) other).internships));
    }

    @Override
    public int hashCode() {
        return internships.hashCode();
    }
}
