package gui;

import backend.SearchEntry;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModifyRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class ModificationsWindowController implements ILoader{


    @FXML  Button _buttonModify;
    @FXML  Button _buttonExportLDIF;
    @FXML  Button _buttonCancel;

    Main _main = null;
    Scene _scene;
    Stage _stage;
    VBox _showModificationsWindow;


    @FXML VBox _vboxEntries;
    //@FXML  ObservableList<ModificationEntryController> _observableListModifications = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
       // _listViewModifications.setItems(_observableListModifications);
        _buttonCancel.setOnAction(x->_stage.close());

    }

    public VBox get_window(){return _showModificationsWindow;}

    EventHandler<ActionEvent> removeItemHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
                   try {

               //_observableListModifications.remove(modificationEntryController);
            }
            catch (Exception e)
            {

            }


        }
    };

    public void setModificationsEntries(Map<TreeItem<SearchEntry>, List<ModifyRequest>> modifications)
    {
        //_observableListModifications.clear();
        for(TreeItem<SearchEntry> mod: modifications.keySet())
        {
            List<ModifyRequest> modifyRequests = modifications.get(mod);
            for(ModifyRequest modifyRequest: modifyRequests)
            {
                for(Modification modification: modifyRequest.getModifications())
                {
                    try {
                        ModificationEntryController modificationEntryController = (ModificationEntryController) Main.initController("/FXML/ModificationEntry.fxml",_main);
                        modificationEntryController._textFieldDN.setText(mod.getValue().getDn());
                        for(String val: mod.getValue().getEntry().getAttribute(modification.getAttributeName()).getValues())
                        {
                            modificationEntryController._observableListValuesBefore.add(val);
                        }
                        for(String val: modification.getValues())
                        {
                            modificationEntryController._observableListValuesAfter.add(val);
                        }
                        modificationEntryController._buttonRemove.setOnAction(removeItemHandler);
                        modificationEntryController._textFieldAttribute.setText(modification.getAttributeName());
                        _vboxEntries.getChildren().add(modificationEntryController._scene.getRoot());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }






                    //_listViewModifications.getItems().add(modificationEntryController);
                    //_observableListModifications.add(modificationEntryController);
                }
            }
        }
    }

    @Override
    public void setMainApp(Main main) {
        _main = main;
    }

    @Override
    public void setWindow(Parent parent) {
        _showModificationsWindow = (VBox) parent;
        _scene = new Scene(_showModificationsWindow);
        _stage = new Stage();
        _stage.setScene(_scene);
    }



    @Override
    public void setOwner(Stage stage) {

    }
}
