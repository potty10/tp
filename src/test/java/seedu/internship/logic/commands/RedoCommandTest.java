package seedu.internship.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.internship.model.Model;
import seedu.internship.model.ModelManager;
import seedu.internship.model.UserPrefs;

import static seedu.internship.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.internship.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.internship.testutil.TypicalInternships.getTypicalInternBuddy;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalInternBuddy(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalInternBuddy(), new UserPrefs());

    @Test
    public void execute_undo_success() {
        model.deleteInternship(model.getFilteredInternshipList().get(0));
        model.commitInternBuddy();
        model.deleteInternship(model.getFilteredInternshipList().get(0));
        model.commitInternBuddy();

        // multiple undoable states in model
        expectedModel.deleteInternship(expectedModel.getFilteredInternshipList().get(0));
        expectedModel.commitInternBuddy();
        expectedModel.deleteInternship(expectedModel.getFilteredInternshipList().get(0));
        expectedModel.commitInternBuddy();

        expectedModel.undoInternBuddy();
        expectedModel.redoInternBuddy();
        model.undoInternBuddy();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
