package ru.multicabinet.servermodules.exampleserver;

import org.osgi.framework.BundleActivator;
import ru.multicabinet.module.api.ServerModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 11/9/14
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExampleServerModuleService implements ServerModule, BundleActivator {
    @Override
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("name", "exampleserver");
        info.put("desc", "Example Server Module");
        info.put("version", "0.1");
        return info;
    }

    @Override
    public Map<String, Map<String, Object>> getAccountAccessParameters() {
        Map<String, Map<String, Object>> accountAccessParams = new HashMap<String, Map<String, Object>>();

        Map<String, Object> username = new HashMap<String, Object>();
        username.put("type", "text");
        username.put("desc", "Username");
        username.put("validator", "/\\d/");

        Map<String, Object> password = new HashMap<String, Object>();
        password.put("type", "password");
        password.put("desc", "Password");

        accountAccessParams.put("username", username);
        accountAccessParams.put("password", password);

        return accountAccessParams;
    }

    @Override
    public Map<String, Map<String, Object>> getAccountDataParameters() {
        Map<String, Map<String, Object>> accountData = new HashMap<String, Map<String, Object>>();

        Map<String, Object> diskspace = new HashMap<String, Object>();
        diskspace.put("type", "text");
        diskspace.put("desc", "Disk space");
        diskspace.put("validator", "/[0-9]+/");

        Map<String, Object> radiofield = new HashMap<String, Object>();
        radiofield.put("type", "text");
        radiofield.put("desc", "Checkbox");

        Map<String, Object> dropdownfield = new HashMap<String, Object>();
        dropdownfield.put("type", "dropdown");
        dropdownfield.put("desc", "Drop down");
        Map<String, String> values = new HashMap<String, String>();
        values.put("value1", "Value 1");
        values.put("value2", "Value 2");
        values.put("value3", "Value 3");
        dropdownfield.put("values", values);

        accountData.put("diskspace", diskspace);
        accountData.put("radiofield", radiofield);
        accountData.put("dropdownfield", dropdownfield);

        return accountData;
    }

    @Override
    public Map<String, Map<String, Object>> getServerAccessParameters() {
        Map<String, Map<String, Object>> serverAccess = new HashMap<String, Map<String, Object>>();
        Map<String, Object> ip = new HashMap<String, Object>();
        ip.put("type", "text");
        ip.put("desc", "Server IP");

        Map<String, Object> port = new HashMap<String, Object>();
        port.put("type")
    }
}
