package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.bug.Bug;

/**
 * An UI component that displays information of a {@code Bug}.
 */
public class BugCardKanban extends UiPart<Region> {

    private static final String FXML = "BugListCardKanban.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Bug bug;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane priority;

    /**
     * Creates a {@code BugCardKanban} with the given {@code Bug} and index to display.
     */
    public BugCardKanban(Bug bug, int displayedIndex) {
        super(FXML);
        this.bug = bug;
        id.setText(displayedIndex + ". ");
        name.setText(bug.getName().fullName);
        description.setText(bug.getDescription().value);
        description.setWrapText(true);
        if (!bug.getPriority().isNull()) {
            Label label = new Label(bug.getPriority().getValue().toUpperCase());
            //bug.getPriority().getValue().toUpperCase()
            switch (bug.getPriority().getValue()) {
            case "low":
                label.setStyle("-fx-background-color: green;");
                break;
            case "medium":
                label.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
                break;
            case "high":
                label.setStyle("-fx-background-color: red;");
                break;
            default:
                label = new Label("Unexpected error has occurred.");
            }
            priority.getChildren().add(label);
        }
        bug.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BugCardKanban)) {
            return false;
        }

        // state check
        BugCardKanban card = (BugCardKanban) other;
        return id.getText().equals(card.id.getText())
                && bug.equals(card.bug);
    }
}

