package seedu.internship.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internship.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import seedu.internship.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all internships";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
