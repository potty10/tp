package seedu.internship.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code InternBuddy} that keeps track of its own history.
 */
public class VersionedInternBuddy extends InternBuddy {

    private final List<ReadOnlyInternBuddy> internBuddyStateList;
    private int currentStatePointer;

    public VersionedInternBuddy(ReadOnlyInternBuddy initialState) {
        super(initialState);

        internBuddyStateList = new ArrayList<>();
        internBuddyStateList.add(new InternBuddy(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code InternBuddy} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        internBuddyStateList.add(new InternBuddy(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        internBuddyStateList.subList(currentStatePointer + 1, internBuddyStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(internBuddyStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(internBuddyStateList.get(currentStatePointer));
    }

    public void saveCurrentInternBuddy() {
        internBuddyStateList.set(currentStatePointer, new InternBuddy(this));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < internBuddyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedInternBuddy)) {
            return false;
        }

        VersionedInternBuddy otherVersionedInternBuddy = (VersionedInternBuddy) other;

        // state check
        return super.equals(otherVersionedInternBuddy)
                && internBuddyStateList.equals(otherVersionedInternBuddy.internBuddyStateList)
                && currentStatePointer == otherVersionedInternBuddy.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}

