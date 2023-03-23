package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalInternships.getTypicalInternBuddy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.InternBuddy;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;

public class ClearCommandTest {

    @Test
    public void execute_emptyInternBuddyEmptyPredicate_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        InternshipContainsKeywordsPredicate emptyPredicate = new InternshipContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertCommandSuccess(new ClearCommand(emptyPredicate), model, ClearCommand.MESSAGE_SUCCESS_CLEAR,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyInternBuddyEmptyPredicate_success() {
        Model model = new ModelManager(getTypicalInternBuddy(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInternBuddy(), new UserPrefs());
        expectedModel.setInternBuddy(new InternBuddy());
        InternshipContainsKeywordsPredicate emptyPredicate = new InternshipContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertCommandSuccess(new ClearCommand(emptyPredicate), model, ClearCommand.MESSAGE_SUCCESS_CLEAR,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyInternBuddyNonEmptyPredicate_success() {
        Model model = new ModelManager(getTypicalInternBuddy(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInternBuddy(), new UserPrefs());

        InternshipContainsKeywordsPredicate predicate =
                new InternshipContainsKeywordsPredicate(Arrays.asList("Grab"), Collections.emptyList(),
                        Collections.emptyList(), Collections.emptyList());

        expectedModel.deleteInternshipByPredicate(predicate);
        String expectedMessage = String.format(ClearCommand.MESSAGE_SUCCESS_CLEAR_WITH_FILTER, 1);
        assertCommandSuccess(new ClearCommand(predicate), model, expectedMessage, expectedModel);
    }

}
