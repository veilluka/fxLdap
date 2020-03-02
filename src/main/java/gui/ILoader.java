package gui;

import javafx.scene.Parent;
import javafx.stage.Stage;

public interface   ILoader {
    public void setMainApp(Main main);
    public void setWindow(Parent parent);
    public void setOwner(Stage stage);
}
