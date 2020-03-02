package gui;

import backend.Connection;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectionProItem implements PropertySheet.Item {

    private String key = null;
    private Object value = null;
    private Class<?> type = String.class;

    public static SettingsController get_settingsController() {
        return _settingsController;
    }

    public static void set_settingsController(SettingsController _settingsController) {
        ConnectionProItem._settingsController = _settingsController;
    }


    public static Object getValue(ObservableList<PropertySheet.Item> properties, String key) {
        for (PropertySheet.Item connItem : properties) {
            ConnectionProItem connectionProItem = (ConnectionProItem) connItem;
            if (connectionProItem.key != null && connectionProItem.key.equalsIgnoreCase(key))
                return connectionProItem.value;

        }
        return null;
    }

    public static void setValue(ObservableList<PropertySheet.Item> properties, String key, Object value) {
        for (PropertySheet.Item connItem : properties) {
            ConnectionProItem connectionProItem = (ConnectionProItem) connItem;
            if (connectionProItem.key != null && connectionProItem.key.equalsIgnoreCase(key)) {
                connectionProItem.value = value;
            }

        }

    }

    private static SettingsController _settingsController;

    public static Connection getConnection(ObservableList<PropertySheet.Item> properties) {

        String name = "";
        String server = "";
        String port = "";
        String user = "";
        String password = "";
        String base_dn = "";
        String display_attribute = "";

        boolean ssl = false;
        boolean read_only = false;
        boolean jndi = false;
        for (PropertySheet.Item connItem : properties) {
            ConnectionProItem connectionProItem = (ConnectionProItem) connItem;
            if (connectionProItem.key.equalsIgnoreCase("name") && connectionProItem.value != null)
                name = (String) connectionProItem.value;
            if (connectionProItem.key.equalsIgnoreCase("server") && connectionProItem.value != null)
                server = (String) connectionProItem.value;
            if (connectionProItem.key.equalsIgnoreCase("port") && connectionProItem.value != null)
                port = String.valueOf(connectionProItem.value);
            if (connectionProItem.key.equalsIgnoreCase("user") && connectionProItem.value != null)
                user = (String) connectionProItem.value;
            if (connectionProItem.key.equalsIgnoreCase("password") && connectionProItem.value != null)
                password = (String) connectionProItem.value;
            if (connectionProItem.key.equalsIgnoreCase("base_dn") && connectionProItem.value != null)
                base_dn = (String) connectionProItem.value;
            if (connectionProItem.key.equalsIgnoreCase("display_attribute") && connectionProItem.value != null)
                display_attribute = (String) connectionProItem.value;
            if (connectionProItem.key.equalsIgnoreCase("ssl") && connectionProItem.value != null) {
                ssl = (boolean) connectionProItem.value;
            }
            if (connectionProItem.key.equalsIgnoreCase("read_only") && connectionProItem.value != null) {
                read_only = (boolean) connectionProItem.value;
            }
            if (connectionProItem.key.equalsIgnoreCase("jndi") && connectionProItem.value != null) {
                jndi = (boolean) connectionProItem.value;
            }

        }

        Connection connection = new Connection(name, server, port, user, password, base_dn, "",display_attribute);
        connection.setSSL(ssl);
        connection.setUseJNDI(jndi);
        connection.set_readOnly(read_only);
        return connection;
    }

    public static List<ConnectionProItem> createFromConnection(Connection connection) {
        List<ConnectionProItem> pros = new ArrayList<>();
        ConnectionProItem name = new ConnectionProItem("NAME", connection.getName());
        ConnectionProItem server = new ConnectionProItem("SERVER", connection.getServer());
        ConnectionProItem port = new ConnectionProItem("PORT", Integer.parseInt(connection.getPort()));
        ConnectionProItem user = new ConnectionProItem("USER", connection.getUser());
        ConnectionProItem password = new ConnectionProItem("PASSWORD", connection.getPassword());
        ConnectionProItem base_dn = new ConnectionProItem("BASE_DN", connection.getBaseDN());
        ConnectionProItem displayAttribute = new ConnectionProItem("DISPLAY_ATTRIBUTE", connection.getDisplayAttribute());

        ConnectionProItem ssl = new ConnectionProItem("SSL", connection.isSSL());
        ConnectionProItem jndi = new ConnectionProItem("JNDI", connection.isUseJNDI());
        ConnectionProItem readOnly = new ConnectionProItem("READ_ONLY", connection.is_readOnly());
        ssl.type = Boolean.class;
        port.type = Integer.class;
        pros.add(name);
        pros.add(server);
        pros.add(port);
        pros.add(user);
        pros.add(base_dn);
        pros.add(displayAttribute);
        pros.add(password);
        pros.add(ssl);
        pros.add(jndi);
        pros.add(readOnly);
        return pros;
    }

    public ConnectionProItem(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Class<?> getType() {

        return type;
    }

    @Override
    public String getCategory() {
        return "Connection";
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (this.value != null && !this.value.equals(value)) {
            if (_settingsController._buttonSaveConnections.isDisable())
                _settingsController._buttonSaveConnections.setDisable(false);
        }
        this.value = value;
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }

}