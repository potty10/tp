package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.model.InternBuddy;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all internships whose company names contain any"
            + " of the specified name, role, status and tag keywords and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_COMPANY_NAME + "NAME_KEYWORD]... "
            + "[" + PREFIX_ROLE + "ROLE_KEYWORD]... "
            + "[" + PREFIX_STATUS + "STATUS_KEYWORD]... "
            + "[" + PREFIX_TAG + "TAG_KEYWORD]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_COMPANY_NAME + "apple "
            + PREFIX_COMPANY_NAME + "google "
            + PREFIX_ROLE + "software "
            + PREFIX_ROLE + "developer "
            + PREFIX_STATUS + "applied "
            + PREFIX_STATUS + "offered "
            + PREFIX_TAG + "python "
            + PREFIX_TAG + "java";
    public static final String MESSAGE_SUCCESS_CLEAR = "InternBuddy data has been cleared!";

    public static final String MESSAGE_SUCCESS_CLEAR_WITH_FILTER = "%1$d internships have been delete!";

    private final InternshipContainsKeywordsPredicate predicate;

    public ClearCommand(InternshipContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int previousSize = model.getFilteredInternshipList().size();
        if (this.predicate.isEmpty()) {
            model.setInternBuddy(new InternBuddy());
            return new CommandResult(MESSAGE_SUCCESS_CLEAR);
        }
        model.deleteInternshipByPredicate(predicate);
        int newSize = model.getFilteredInternshipList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS_CLEAR_WITH_FILTER, previousSize - newSize));
    }
}
