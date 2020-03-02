package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

public class CustomLdapFxToolbar extends ToolBar {

    Main _main;
    public Button buttonSettings = new Button();
    public Button buttonLdapExplorerSourceWindow =  new Button();
    public Button buttonLdapExplorerTargetWindow = new Button();
    public Button buttonEntryView= new Button();
    public Button buttonCompareResultWindow = new Button();
    public Button buttonSearchResultWindow = new Button();
    public Button loadStyles = new Button("load styles");

    public CustomLdapFxToolbar(Main main)
    {
        _main = main;
        initButtons();
        String css = Main.class.getResource("/styles.css").toExternalForm();
        this.getStylesheets().clear();
        this.getStylesheets().add(css);
    }

    public void initButtons()
    {
        buttonEntryView.setTooltip( new Tooltip("LDAP Entry-View"));
        buttonEntryView.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.ENTRY));

        buttonCompareResultWindow.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.COMPARE));
        buttonCompareResultWindow.setTooltip(new Tooltip("Compare-Result"));

        buttonSearchResultWindow.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.SEARCH_RESULT));
        buttonSearchResultWindow.setTooltip(new Tooltip("Search-Result"));

        buttonLdapExplorerTargetWindow.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.TARGET));
        buttonLdapExplorerTargetWindow.setTooltip(new Tooltip("LDAP-TARGET Explorer"));

        buttonLdapExplorerSourceWindow.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.LDAP_TREE));
        buttonLdapExplorerTargetWindow.setTooltip(new Tooltip("LDAP-SOURCE Explorer"));
        buttonSettings.setOnAction(x-> _main._settingsController.get_stage().showAndWait());
        buttonSettings.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.SETTINGS));
        buttonLdapExplorerSourceWindow.setOnAction(x->{
            _main._dockWindows.dock(true,null, _main._dockWindows._dockNodeSourceExplorer);
            _main._toolBar.buttonLdapExplorerSourceWindow.setDisable(true);
        });
        buttonLdapExplorerTargetWindow.setOnAction(x->{
            _main._dockWindows.dock(true,null, _main._dockWindows._dockNodeTargetExplorer);
            _main._toolBar.buttonLdapExplorerTargetWindow.setDisable(true);
        });
        buttonEntryView.setOnAction(x->{
            _main._dockWindows.dock(true,null, _main._dockWindows._dockNodeEntryView);
            _main._toolBar.buttonEntryView.setDisable(true);

        });
        buttonCompareResultWindow.setOnAction(x->_main._dockWindows.dock(true,null, _main._dockWindows._dockNodeCompareResult));
        buttonSearchResultWindow.setOnAction(x->_main._dockWindows.dock(true,null, _main._dockWindows._dockNodeSearchResult));
        getItems().addAll(buttonSettings,buttonLdapExplorerSourceWindow,buttonLdapExplorerTargetWindow,
                buttonEntryView,buttonCompareResultWindow,buttonSearchResultWindow);

        loadStyles.setOnAction(x->_main._dockWindows.reloadStyles());
    }
}
