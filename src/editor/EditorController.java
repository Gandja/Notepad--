package editor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class EditorController {

    @FXML
    private TextArea textArea;

    private TextFile currentTextFile;

    private EditorModel model;

    public EditorController(EditorModel model) {
        this.model = model;
    }

    @FXML
    private void onSave() {
        TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(textArea.getText().split("\n")));
        model.save(textFile);
    }

    @FXML
    private void onLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            IOResult<TextFile> ioResult = model.load(file.toPath());

            if(ioResult.isOk() && ioResult.hasData()){
                currentTextFile = ioResult.getData();
                currentTextFile.getContent().forEach(line -> textArea.appendText(line + "\n"));
            }else {
                System.out.println("Failed");
            }
        }
    }

    @FXML
    private void onClosed() {
        model.close();
    }

    @FXML
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is text editor Notepad--");
        alert.show();
    }
}
