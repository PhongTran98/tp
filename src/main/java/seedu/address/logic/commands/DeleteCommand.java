package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLUMN;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bug.Bug;


/**
 * Deletes a bug identified using it's displayed index from the bug tracker.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the bug identified by the index number used in the displayed bug list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COLUMN + "STATE]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BUG_SUCCESS = "Deleted Bug: %1$s";

    protected final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bug> lastShownList = model.getFilteredBugList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUG_DISPLAYED_INDEX);
        }

        Bug bugToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBug(bugToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BUG_SUCCESS, bugToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
