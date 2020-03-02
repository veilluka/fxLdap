package gui;

import backend.CustomEntry;
import com.unboundid.ldap.sdk.Filter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartSearchController implements ILoader {

    static Logger logger = LogManager.getLogger(StartSearchController.class);
    public StartSearchController(){}


    @FXML TextField _textFieldSearchDN;
    @FXML TextField _textFieldSearchValue;

    @FXML CheckBox _checkBoxSearchIgnoreCase;
    @FXML CheckBox _checkBoxSearchRegex;
    @FXML CheckBox _checkBoxSearchExactMatch;
    @FXML CheckBox _checkBoxDeadLink;
    @FXML CheckBox _checkBoxNOTContains;

    @FXML  Button _buttonCancel;
    @FXML Button _buttonRunSearch;

    @FXML private Parent embeddedFilterView;

    public FilterWindowController getEmbeddedFilterViewController() {
        return embeddedFilterViewController;
    }

    @FXML private FilterWindowController embeddedFilterViewController;

    Main _main;
    Stage _stage;
    Scene _scene;
    VBox _startSearchWindow;


    public boolean is_ldapExplorerTargetAction() {
        return _ldapExplorerTargetAction;
    }

    public void set_ldapExplorerTargetAction(boolean _ldapExplorerTargetAction) {
        this._ldapExplorerTargetAction = _ldapExplorerTargetAction;
        getEmbeddedFilterViewController().set_ldapExplorerTargetAction(_ldapExplorerTargetAction);
    }

    private boolean _ldapExplorerTargetAction = false;


    @FXML
    private void initialize() {

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(StartSearchController.class.getResource("/FXML/FilterWindow.fxml"));
        try {
            mainLoader.load();
        } catch (IOException e) {
            GuiHelper.EXCEPTION("Exception occured","During init of start search controller",e);
            return;
        }
        _buttonCancel.setOnAction(e->_stage.close());
        _textFieldSearchValue.setOnKeyPressed(e->{if(e.getCode().equals(KeyCode.ENTER)) runLdapSearch();});
        getEmbeddedFilterViewController()._textFieldSourceFilter.setOnKeyPressed(e->{if(e.getCode().equals(KeyCode.ENTER))
        {
            try{ Filter f  = Filter.create( getEmbeddedFilterViewController()._textFieldSourceFilter.getText());}catch (Exception exc){return;}
            runLdapSearch();
        }});

        _buttonRunSearch.setOnAction(e->runLdapSearch());
        _checkBoxSearchRegex.setDisable(true);
    }


    public void windowOpened()
    {
        embeddedFilterViewController.get_observableConfigAllAttributes().addAll(_main.get_configuration()._allAttributes);
        _textFieldSearchValue.requestFocus();
    }

    public void runLdapSearch(String dn,Filter filter)
    {
        _main.get_progressWindowController()._stage.show();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            try {
                TreeView<CustomEntry> treeView = null;
                if(_ldapExplorerTargetAction)
                {
                    treeView = _main.get_ldapTargetExploreController()._treeView;
                    _main.get_searchResultController()._searchTree.runSearch(
                            treeView,
                            _main.get_ldapTargetExploreController().get_currentConnection(),
                            dn,
                            null,
                            false,
                            _checkBoxSearchExactMatch.isSelected(),
                            _checkBoxNOTContains.isSelected(),
                            _checkBoxSearchRegex.isSelected(),
                            _main.get_ldapTargetExploreController().get_currentConnection().getDisplayAttribute(),
                            false,
                            filter,
                            null,
                            false,
                            false,
                            _main.get_searchResultController());
                }
                else
                {
                    treeView = _main.get_ldapSourceExploreController()._treeView;
                    _main.get_searchResultController()._searchTree.runSearch(
                            treeView,
                            _main.get_ldapSourceExploreController().get_currentConnection(),
                            dn,
                            null,
                            false,
                            _checkBoxSearchExactMatch.isSelected(),
                            _checkBoxNOTContains.isSelected(),
                            _checkBoxSearchRegex.isSelected(),
                            _main.get_ldapSourceExploreController().get_currentConnection().getDisplayAttribute(),
                            false,
                            filter,
                            null,
                            false,
                            false,
                            _main.get_searchResultController());
                }


            } catch (Exception e) {
                logger.error("Exception in search",e);
                Platform.runLater(()->GuiHelper.ERROR("Search Error, Exception occured ", e.toString()));
            }
        });

    }

    private void runLdapSearch()
    {
        if(!_checkBoxDeadLink.isSelected() &&_textFieldSearchValue.getText().equalsIgnoreCase(""))
        {
            if(embeddedFilterViewController._sourceFilter == null)
            {
                GuiHelper.ERROR("Search ", "Text Search Value or LDAP Filter are not set");
                return;
            }

        }
        if(_main.get_ldapSourceExploreController().get_selectedDN() == null ||
                _main.get_ldapSourceExploreController().get_selectedDN().equalsIgnoreCase(""))
        {
            GuiHelper.ERROR("Search ", "Search-Scope not set");
            return;
        }

        _main.get_startSearchController()._stage.close();
        _main.get_progressWindowController()._labelHeader.setText(_main.get_startSearchController()._textFieldSearchValue.getText());
        _main.get_progressWindowController()._stage.show();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(()-> {
            try {
                TreeView<CustomEntry> treeView = null;
                if(_ldapExplorerTargetAction) treeView = _main.get_ldapTargetExploreController()._treeView;
                else treeView = _main.get_ldapSourceExploreController()._treeView;
                   _main.get_searchResultController()._searchTree.runSearch(
                         treeView,
                        _main.get_ldapSourceExploreController().get_currentConnection(),
                        _main.get_ldapSourceExploreController().get_selectedDN(),
                       _textFieldSearchValue.getText(),
                        _checkBoxSearchIgnoreCase.isSelected(),
                        _checkBoxSearchExactMatch.isSelected(),
                        _checkBoxNOTContains.isSelected(),
                        _checkBoxSearchRegex.isSelected(),
                        _main.get_ldapSourceExploreController().get_currentConnection().getDisplayAttribute(),
                        _checkBoxDeadLink.isSelected(),
                        embeddedFilterViewController._sourceFilter,
                        embeddedFilterViewController._listFilterAttributes.getItems(),
                        embeddedFilterViewController._radioButtonCompareAttributes.isSelected(),
                        embeddedFilterViewController._radioButtonIgnoreAttributes.isSelected(),
                        _main.get_searchResultController());
            } catch (Exception e) {
                logger.error("Exception in search",e);
                Platform.runLater(()->GuiHelper.ERROR("Search Error, Exception occured ", e.toString()));
            }
        });
    }

    @Override
    public void setMainApp(Main main) {
        _main = main;
    }

    @Override
    public void setWindow(Parent parent) {
        _startSearchWindow = (VBox) parent;
        _scene = new Scene(_startSearchWindow);
        _stage = new Stage();
        _stage.setScene(_scene);
        _stage.initStyle(StageStyle.DECORATED);
        _stage.initModality(Modality.WINDOW_MODAL);
        _stage.initOwner(_main.get_primaryStage());
    }

    @Override
    public void setOwner(Stage stage) {

    }
}
