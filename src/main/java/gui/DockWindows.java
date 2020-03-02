package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.dockfx.DockNode;
import org.dockfx.DockPos;
import org.dockfx.demo.DockFX;

public class DockWindows {

    DockNode _dockNodeEntryView;
    DockNode _dockNodeSourceExplorer;
    DockNode _dockNodeTargetExplorer;
    DockNode _dockNodeCompareResult = null;
    DockNode _dockNodeSearchResult = null;
    DockNode _dockNodeEntry;

    Main _main = null;

    public DockWindows(Main main) {
        _main = main;
        Image dockImage = new Image(DockFX.class.getResource("/icons/docknode.png").toExternalForm());

        _dockNodeSourceExplorer = new DockNode(_main._ldapSourceExploreController.get_window(), "Source", new ImageView(dockImage));
        _dockNodeSourceExplorer.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.LDAP_TREE_SMALL));
        _dockNodeTargetExplorer = new DockNode(_main._ldapTargetExploreController.get_window(), "Target", new ImageView(dockImage));
        _dockNodeTargetExplorer.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.TARGET_SMALL));
        _dockNodeEntryView = new DockNode(_main._showEntryController.get_window(), "EntryView", new ImageView(dockImage));
        _dockNodeSearchResult = new DockNode(_main._searchResultController.get_window(), "Searchresult", Icons.get_iconInstance().getIcon(Icons.ICON_NAME.SEARCH_RESULT));
        _dockNodeCompareResult = new DockNode(_main._ldapCompareController.get_window(), "Compare", Icons.get_iconInstance().getIcon(Icons.ICON_NAME.COMPARE));
        _dockNodeEntry = new DockNode(_main.get_entryDiffView(), "Entry");
        _dockNodeSourceExplorer.dock(_main._dockPane, DockPos.RIGHT);
        _dockNodeEntryView.dock(_main._dockPane, DockPos.RIGHT);
        String style = "-fx-font:12px \"Segoe UI\";-fx-font-weight:bold;";
        _dockNodeSourceExplorer.getDockTitleBar().getLabel().setStyle(style);
        _dockNodeTargetExplorer.getDockTitleBar().getLabel().setStyle(style);
        _dockNodeEntryView.getDockTitleBar().getLabel().setStyle(style);
        _dockNodeSearchResult.getDockTitleBar().getLabel().setStyle(style);
        _dockNodeEntry.getDockTitleBar().getLabel().setStyle(style);
        _dockNodeCompareResult.getDockTitleBar().getLabel().setStyle(style);
        _dockNodeSourceExplorer.setMinWidth(300);
        _dockNodeEntryView.setMinWidth(300);
        _dockNodeTargetExplorer.setMinWidth(300);
        _dockNodeCompareResult.setMinWidth(300);
        _dockNodeSearchResult.setMinWidth(300);
        _main._toolBar.buttonLdapExplorerSourceWindow.setDisable(true);
        _main._toolBar.buttonEntryView.setDisable(true);
        _dockNodeSourceExplorer.getDockTitleBar().getCloseButton().setOnAction(x -> {
            _main._toolBar.buttonLdapExplorerSourceWindow.setDisable(false);
            _dockNodeSourceExplorer.close();
        });
        _dockNodeTargetExplorer.getDockTitleBar().getCloseButton().setOnAction(x -> {
            _main._toolBar.buttonLdapExplorerTargetWindow.setDisable(false);
            _dockNodeTargetExplorer.close();
        });
        _dockNodeEntryView.getDockTitleBar().getCloseButton().setOnAction(x -> {
            _main._toolBar.buttonEntryView.setDisable(false);
            _dockNodeEntryView.close();
        });
    }


    public void dock(boolean dock, String label, DockNode dockNode) {
        if (label != null) dockNode.setTitle(label);
        if (dock) {
            if (!dockNode.isDocked()) {
                dockNode.dock(_main._dockPane, DockPos.RIGHT);

                if (!dockNode.equals(_dockNodeEntryView) && _dockNodeEntryView.isDocked()) {

                    _dockNodeEntryView.resize(_dockNodeEntryView.getWidth() / 2.0, _dockNodeEntryView.getHeight());
                }
                if (!dockNode.equals(_dockNodeSourceExplorer) && _dockNodeSourceExplorer.isDocked()) {
                    _dockNodeSourceExplorer.resize(_dockNodeSourceExplorer.getWidth() / 2.0, _dockNodeSourceExplorer.getHeight());
                }
                if (!dockNode.equals(_dockNodeTargetExplorer) && _dockNodeTargetExplorer.isDocked()) {
                    _dockNodeTargetExplorer.resize(_dockNodeTargetExplorer.getWidth() / 2.0, _dockNodeTargetExplorer.getHeight());
                }
                if (!dockNode.equals(_dockNodeCompareResult) && _dockNodeCompareResult.isDocked()) {
                    _dockNodeCompareResult.resize(_dockNodeCompareResult.getWidth() / 2.0, _dockNodeCompareResult.getHeight());
                }
                if (!dockNode.equals(_dockNodeSearchResult) && _dockNodeSearchResult.isDocked()) {
                    _dockNodeSearchResult.resize(_dockNodeSearchResult.getWidth() / 2.0, _dockNodeSearchResult.getHeight());
                }
            }
        } else {
            if (dockNode.isDocked()) dockNode.undock();
        }
    }


    public void reloadStyles() {
        String css = Main.class.getResource("/styles.css").toExternalForm();
        _dockNodeSourceExplorer.getStylesheets().clear();
        _dockNodeSourceExplorer.getStylesheets().add(css);
        _dockNodeEntryView.getStylesheets().clear();
        _dockNodeTargetExplorer.getStylesheets().clear();
        _dockNodeCompareResult.getStylesheets().clear();
        _dockNodeSearchResult.getStylesheets().clear();
        _dockNodeEntryView.getStylesheets().add(css);
        _dockNodeTargetExplorer.getStylesheets().add(css);
        _dockNodeCompareResult.getStylesheets().add(css);
        _dockNodeSearchResult.getStylesheets().add(css);
    }


}
