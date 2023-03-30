package seedu.internship.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.internship.logic.commands.exceptions.CommandException;
import seedu.internship.model.Model;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoInternBuddy()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoInternBuddy();
        // model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
