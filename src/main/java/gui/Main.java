package gui;

import backend.*;
import ch.cnc.SecStorage;
import ch.cnc.SecureString;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dockfx.DockNode;
import org.dockfx.DockPane;
import org.dockfx.DockPos;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.Random;

/*
Author: Bauer Vedran
2019
 */


public class Main extends Application {

    static Logger logger = LogManager.getLogger(Application.class);
    public Config get_configuration() {
        return _configuration;
    }

    Config _configuration;
    private Stage _primaryStage;
    private TabPane _rootLayout;

    public Stage get_primaryStage() {
        return _primaryStage;
    }

    LdapExploreController _ldapSourceExploreController;
    LdapExploreController _ldapTargetExploreController;
    FilterWindowController _filterController;
    LdapCompareController _ldapCompareController;
    CollectionsController _collectionsController;
    StartSearchController _startSearchController;
    SearchResultController _searchResultController;
    ProgressWindowController _progressWindowController;
    SettingsController _settingsController;
    StartLdapCompareController _startLdapCompareController;
    ExportWindowController _exportWindowController;
    ModificationsWindowController _modificationsWindowController;
    ModificationsViewController _modificationsViewController;
    DeleteEntriesController _deleteEntriesController;

    public EntryView get_entryView() {
        return _entryView;
    }
    EntryView _entryView = null;
    KeyStoreController _keyStoreController;
    DockWindows _dockWindows;
    EntryDiffView _entryDiffView = null;
    ShowEntryController _showEntryController = null;
    public EntryDiffView get_entryDiffView() {return _entryDiffView;}

// TODO extendable search pane in javafx

    public StartSearchController get_startSearchController() {
        return _startSearchController;
    }
    public SettingsController get_SettingsController() { return _settingsController;}
    public SearchResultController get_searchResultController() {
        return _searchResultController;
    }
    public ProgressWindowController get_progressWindowController() {
        return _progressWindowController;
    }
    public LdapExploreController get_ldapSourceExploreController() {
        return _ldapSourceExploreController;
    }
    public LdapExploreController get_ldapTargetExploreController() {
        return _ldapTargetExploreController;
    }
    public StartLdapCompareController get_startLdapCompareController() {
        return _startLdapCompareController;
    }
    public ExportWindowController get_exportWindowController() {return _exportWindowController;}
    public ModificationsWindowController get_modificationsWindowController() {return _modificationsWindowController;}
    public ModificationsViewController get_ModificationsViewController() {return _modificationsViewController;}
    public KeyStoreController get_keyStoreController() {return _keyStoreController;}
    public DeleteEntriesController get_deleteEntriesController(){return _deleteEntriesController;}


    //public EntryViewController get_entryViewController() {return _entryViewController;}
    public ShowEntryController get_showEntryController(){return _showEntryController;}
    public LdapCompareController get_ldapCompareController() {
        return _ldapCompareController;
    }

    /************************* MENU **********************/

    final Menu _fileMenu = new Menu("File");
    MenuBar _menuBar = new MenuBar();
    CustomLdapFxToolbar _toolBar = null;
    DockPane _dockPane = new DockPane();

    final MenuItem _settings = new MenuItem("Settings");
    final MenuItem _keyStore = new MenuItem("KeyStore");

    final MenuItem _openProject = new MenuItem("Open Project");
    final MenuItem _newProject = new MenuItem("New Project");
    final MenuItem _saveProject = new MenuItem("Save Project");
    final MenuItem _closeProject = new MenuItem("Close Project");
    final MenuItem _changeMasterPassword = new MenuItem("Change master password");
    final MenuItem _exit = new MenuItem("Close");


    public TabPane get_tabPane() {
        return _tabPane;
    }

    TabPane _tabPane = new TabPane();
    DockNode _collectionProjectNode = null;

    public Main(){}
    @FXML private void initialize() {}

    private ILoader initController(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource(fxmlFile);
        if(url == null) throw new Exception("URL could not be resolved with->" + fxmlFile) ;
        loader.setLocation(url);
        Parent parent = loader.load();
        ILoader iLoader = loader.getController();
        iLoader.setMainApp(this);
        iLoader.setWindow(parent);
        iLoader.setOwner(_primaryStage);
        return iLoader;
    }

    public static ILoader initController(String fxmlFile, Main main) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL url = Main.class.getResource(fxmlFile);
        if(url == null) throw new Exception("URL could not be resolved with->" + fxmlFile) ;
        loader.setLocation(url);
        loader.setLocation(url);
        Parent parent = loader.load();
        ILoader iLoader = loader.getController();
        iLoader.setMainApp(main);
        iLoader.setWindow(parent);
        return iLoader;
    }
    private void initAllControllers()
    {
        try{
            _settingsController = (SettingsController) initController("/FXML/Settings.fxml");
            _settingsController.setOwner(_primaryStage);
            _entryView = new EntryView(this);
            VBox.setVgrow(_entryView, Priority.ALWAYS);
            _entryView.setMaxHeight(Double.MAX_VALUE);
            _progressWindowController = (ProgressWindowController)  initController("/FXML/ProgressWindow.fxml");
            _ldapSourceExploreController = (LdapExploreController) initController("/FXML/LdapExplore.fxml");
            _ldapTargetExploreController = (LdapExploreController) initController("/FXML/LdapExplore.fxml");
            _ldapTargetExploreController.setTargetExplorerMode();
            _collectionsController = (CollectionsController)    initController("/FXML/CollectionsWindow.fxml");
            _collectionsController.setOwner(_primaryStage);
            _ldapSourceExploreController.set_collectionTree(_collectionsController._treeView);
            _searchResultController = (SearchResultController) initController("/FXML/SearchResult.fxml") ;
            _startLdapCompareController = (StartLdapCompareController)  initController("/FXML/StartLdapCompare.fxml");
            _startLdapCompareController.setOwner(_primaryStage);
            _ldapCompareController = (LdapCompareController) initController("/FXML/LdapCompareWindow.fxml");
            //_entryViewController = (EntryViewController) initController("/FXML/EntryDiffView.fxml");
            _startSearchController = (StartSearchController)   initController("/FXML/StartLdapSearch.fxml");
            _showEntryController = (ShowEntryController) initController("/FXML/ShowEntry.fxml");
            _exportWindowController = (ExportWindowController) initController("/FXML/ExportWindow.fxml");
            _modificationsWindowController = (ModificationsWindowController) initController("/FXML/ModificationsWindow.fxml");
            _modificationsViewController = (ModificationsViewController) initController("/FXML/ModificationView.fxml");
            _deleteEntriesController = (DeleteEntriesController) initController("/FXML/DeleteEntriesWindow.fxml");
            _keyStoreController = (KeyStoreController) initController("/FXML/KeyStoreWindow.fxml");
            if(_configuration.get_keyStore() != null)
            {
                _settingsController.addKeyStoreTab();
                _keyStoreController.initWindow();
            }
            _dockWindows = new DockWindows(this);
        }
        catch (Exception e)
        {
            logger.error("Exception",e);
            GuiHelper.EXCEPTION("Initialization error",e.toString(),e);
            return;
        }
    }

    private void initDock()
    {
        logger.info("Init dock");
        _entryDiffView = new EntryDiffView(this);

        readConfiguration();
        initMenu();
        _toolBar = new CustomLdapFxToolbar(this);
        initAllControllers();

        _settingsController.readConfiguration();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(_menuBar, _toolBar, _dockPane);
         VBox.setVgrow(_dockPane, Priority.ALWAYS);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        _primaryStage.setX(bounds.getMinX());
        _primaryStage.setY(bounds.getMinY());
        _primaryStage.setWidth(bounds.getWidth());
        _primaryStage.setHeight(bounds.getHeight());

        _primaryStage.setScene(new Scene(vbox, bounds.getWidth(), bounds.getHeight()));
        _primaryStage.sizeToScene();
        _primaryStage.show();

        _primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F5) {
                    keyEvent.consume();
                    get_ldapSourceExploreController().refreshTree();
                }
                else if(keyEvent.getCode() == KeyCode.DELETE)
                {
                    get_ldapSourceExploreController().deleteEntry();
                }
            }
        });
         Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        DockPane.initializeDefaultUserAgentStylesheet();
        _dockWindows._dockNodeEntryView.setGraphic(Icons.get_iconInstance().getIcon(Icons.ICON_NAME.ENTRY_SMALL));
        _dockWindows.reloadStyles();
    }

    private void initMenu()
    {
        final Menu help = new Menu("Help");
        _fileMenu.getItems().addAll(_newProject, _openProject, _saveProject, _closeProject,_changeMasterPassword,_settings,_keyStore,_exit);
        _saveProject.setDisable(true);
        _closeProject.setDisable(true);
        _settings.setOnAction(event -> _settingsController.showWindow());
        _keyStore.setOnAction(x->{
            if(get_configuration().get_keyStore() == null)
            {
                GuiHelper.ERROR("No keystore defined","Create or open keystore in settings first");
                return;
            }
            _keyStoreController.showWindow();
        });
        _changeMasterPassword.setOnAction(x->{
            String currentMaster = null;
            if(get_configuration().is_configSecured())
            {
                currentMaster = GuiHelper.enterPassword("Password","Enter current master password");

                if(currentMaster == null || !_configuration.get_secStorage().checkMasterKey(new SecureString(currentMaster)))
                {
                    GuiHelper.ERROR("Master key no match","Master key does not match current master key");
                    return;
                }
            }
            String first = GuiHelper.enterPassword("Password","Enter new password");
            if(first == null) return;
            if(first.length() < 8){
                GuiHelper.ERROR("Password error","Password length must be at least 8");
                return;
            }
            String second = GuiHelper.enterPassword("Password","Retype new password");
            if(second == null) return;
            if(!second.equals(first))
            {
                    GuiHelper.ERROR("Password error","Passwords do not match");
                    return;
            }
            try {
                SecStorage.changeMasterPassword(Config.getConfigurationFile(),new SecureString(currentMaster),new SecureString(second));
                _configuration.openConfiguration(Config.getConfigurationFile(),new SecureString(second));
            } catch (Exception e) {
                logger.error("Exception occured", e);
                GuiHelper.EXCEPTION("Error changing master password", e.getMessage(), e);
            }
        });
        _exit.setOnAction(x->{
            System.exit(0);
        });
        _newProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(CollectionsController.get_currentCollectionsProject() != null)
                {
                    if(!GuiHelper.confirm("New Project","Close current project?","Current project->" +
                            CollectionsController.get_currentCollectionsProject().get_projectName() + " shall be closed now")) return;

                }
                if(!_collectionsController.newProject()) return;
                if(_collectionProjectNode == null)
                {
                    _collectionProjectNode = new DockNode(_collectionsController.getWindow(),
                            CollectionsController.get_currentCollectionsProject().get_projectName(), null);
                    _collectionProjectNode.setPrefSize(800,800);

                    _collectionProjectNode.dock(_dockPane, DockPos.RIGHT);
                    _dockWindows._dockNodeSearchResult.undock();
                    _dockWindows._dockNodeCompareResult.undock();

                    _ldapSourceExploreController.switch2CollectionTree();
                    _saveProject.setDisable(false);
                    _closeProject.setDisable(false);
                }
                else _collectionProjectNode.setTitle(CollectionsController.get_currentCollectionsProject().get_projectName());
            }
        });

        _openProject.setOnAction(event -> {
            if(CollectionsController.get_currentCollectionsProject() != null)
            {
                if(!GuiHelper.confirm("New Project","Close current project?","Current project->" +
                        CollectionsController.get_currentCollectionsProject().get_projectName() + " shall be closed now")) return;
                else
                {
                    _collectionsController.closeProject();
                }
            }
            if(!_collectionsController.openProject()) return;
            if(_collectionProjectNode == null)
            {
                _collectionProjectNode = new DockNode(_collectionsController.getWindow(),
                        CollectionsController.get_currentCollectionsProject().get_projectName(), null);
                _collectionProjectNode.setPrefSize(800,800);
                _collectionProjectNode.dock(_dockPane, DockPos.RIGHT);
                _dockWindows.dock(false,null,_dockWindows._dockNodeCompareResult);
                _dockWindows.dock(false,null,_dockWindows._dockNodeSearchResult);
                _ldapSourceExploreController.switch2CollectionTree();
                _dockWindows._dockNodeSourceExplorer.setTitle("Collection Explore");
                _saveProject.setDisable(false);
                _closeProject.setDisable(false);
            }
            else
            {
                _collectionProjectNode.setTitle(CollectionsController.get_currentCollectionsProject().get_projectName());
                _dockWindows.dock(false,null,_dockWindows._dockNodeCompareResult);
                _dockWindows.dock(false,null,_dockWindows._dockNodeSearchResult);
                if(!_collectionProjectNode.isDocked())
                {
                    _collectionProjectNode.dock(_dockPane, DockPos.RIGHT);
                    _collectionProjectNode.setPrefSize(800,800);
                }
                _ldapSourceExploreController.switch2CollectionTree();
                _dockWindows._dockNodeSourceExplorer.setTitle("Collection Explore");
                _saveProject.setDisable(false);
                _closeProject.setDisable(false);
            }
        });

        _saveProject.setOnAction(e->{
            try {
                _collectionsController.saveProject();
            } catch (Exception e1) {
                logger.error("Exception during project save",e);
                GuiHelper.EXCEPTION("Error saving project",e1.getMessage(),e1);
            }
        });
        _closeProject.setOnAction(e->{
            _collectionsController.closeProject();
            _tabPane.getTabs().clear();
            _ldapSourceExploreController.switch2LdapTree();
            _saveProject.setDisable(true);
            _closeProject.setDisable(true);
                _collectionProjectNode.close();
            _dockWindows._dockNodeSourceExplorer.setTitle("LDAP Explore");
        });
        _menuBar.getMenus().addAll(_fileMenu, help);
        for(Node child: _dockPane.getChildren())
        {
            String css = Main.class.getResource("/styles.css").toExternalForm();
            child.setStyle(css);
        }
    }

    public static void main(String[] args) {
        String[] testArguments = null;
        if(testArguments != null)
        {
            CmdLine cLine = new CmdLine();
            try {
                cLine.runCmd(testArguments);
            } catch (Exception e) {
                logger.error("Exception in main",e);
                System.err.println(e.getMessage());

            }
            System.exit(0);
        }
        if(args!= null && args.length > 0)
        {
            logger.info("Input arguments found, running console only");
            CmdLine cLine = new CmdLine();
            try {
                cLine.runCmd(args);
            } catch (Exception e) {
                logger.error("Exception in main",e);
                System.err.println(e.getMessage());
            }
            System.exit(0);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this._primaryStage = primaryStage;
        this._primaryStage.setTitle("FxLDAP by Bauer Vedran, " + Version.VERSION);
        ImageView image = (ImageView) Icons.get_iconInstance().getIcon(Icons.ICON_NAME.APP);
        this._primaryStage.getIcons().add(image.getImage());
        initDock();
        this._primaryStage.setOnCloseRequest(x->{System.exit(0);});
    }

    private void readConfiguration() {

        try {
            _configuration = new Config();
            String err = _configuration.openConfiguration(Config.getConfigurationFile());
            if(err != null)
            {
                if(err.equalsIgnoreCase(Errors.MASTER_PASSWORD_SECURED_ONLY))
                {
                    String pass = GuiHelper.enterPassword("Enter master password","Not secured with windows ");
                    _configuration.openConfiguration(Config.getConfigurationFile(),new SecureString(pass));

                }
                if(err.equalsIgnoreCase(Errors.WINDOWS_NOT_SECURED_WITH_CURRENT_USER))
                {
                    SecureString pass = null;
                    if(GuiHelper.confirm("Config file locked","Config file is windows secured with other user",
                            "You are using config file in home directory, " +
                            " which is windows secured with other user. To unlock the config file, you must enter the master password of the file." +
                            "If you do not know the password, you shall not be able to read any passwords from the file"))
                    {
                        pass = new SecureString(GuiHelper.enterPassword("Enter master password","Not secured with current user"));
                    }
                     _configuration.openConfiguration(Config.getConfigurationFile(),pass);
                }
                else if(err.equalsIgnoreCase(Errors.FILE_NOT_FOUND))
                {
                    if(GuiHelper.confirm("Config file error","Did not find config file, create new config with Master-Password?",
                            "If you choose not to set master password, you will not be able to store connection passwords in " +
                                    "config file. If you are running windows, you will not be asked for master password again, after you set one, but " +
                                    "do not forget the password, as you may need it if you want to migrate config file to other pc!"))
                    {
                        String pass = null;
                        while (true)
                        {
                            pass = GuiHelper.enterPassword("Enter master password","Enter password to protect config file");
                            if(pass != null) {
                                if (pass.getBytes().length > 7) break;
                                else
                                {
                                    GuiHelper.INFO("Password length to short","Please at least 8 charachters for master password!");
                                }
                            }
                            else
                            {
                                if(GuiHelper.confirm("No password entered","You have not entered any password, create config file without password?",
                                        "If you choose not to set master password, you will not be able to store connection passwords in " +
                                                "config file. If you are running windows, you will not be asked for master password again, after you set one, but" +
                                                "do not forget the password, as you may need it if you want to migrate config file to other pc!"))
                                {
                                    break;
                                }
                            }
                        }
                        if(pass == null)
                        {
                            _configuration.createConfigurationFile(Config.getConfigurationFile(),null);
                            _configuration.openConfiguration(Config.getConfigurationFile(),null);
                        }
                        else
                        {
                            _configuration.createConfigurationFile(Config.getConfigurationFile(),pass);
                            _configuration.openConfiguration(Config.getConfigurationFile(),new SecureString(pass));
                        }
                    }
                    else
                    {
                        _configuration.createConfigurationFile(Config.getConfigurationFile(),null);
                        _configuration.openConfiguration(Config.getConfigurationFile(),null);
                    }
                }
            }
            if(_configuration.get_lastUsedDirectory() == null) _configuration.set_lastUsedDirectory(System.getProperty("user.home"));

        } catch (Exception e) {
            GuiHelper.EXCEPTION("Error reading configuration", e.toString(),e);
        }
    }

    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }



}
