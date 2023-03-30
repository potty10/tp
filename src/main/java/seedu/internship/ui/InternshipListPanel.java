package seedu.internship.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.internship.commons.core.LogsCenter;
import seedu.internship.model.Model;
import seedu.internship.model.internship.Internship;

/**
 * Panel containing the list of internships
 */
public class InternshipListPanel extends UiPart<Region> {
    private static final String FXML = "InternshipListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InternshipListPanel.class);

    private Model model;

    private MainWindow mainWindow;
    @FXML
    private ListView<Internship> internshipListView;

    /**
     * Creates a {@code InternshipListPanel} with the given {@code ObservableList}.
     */
    public InternshipListPanel(ObservableList<Internship> internshipList, Model selectedModel,
                               MainWindow selectedMainWindow) {
        super(FXML);
        internshipListView.setItems(internshipList);
        internshipListView.setCellFactory(listView -> new InternshipListViewCell());
        model = selectedModel;
        mainWindow = selectedMainWindow;
        internshipListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            model.updateSelectedInternship(newValue);
            mainWindow.updateRightPanel();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Internship} using a {@code InternshipCard}.
     */
    class InternshipListViewCell extends ListCell<Internship> {
        @Override
        protected void updateItem(Internship internship, boolean empty) {
            super.updateItem(internship, empty);

            if (empty || internship == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InternshipCard(internship, getIndex() + 1).getRoot());
            }
        }
    }

}
