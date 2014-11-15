package ru.multicabinet.servermodules.exampleserver;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import ru.multicabinet.module.ServerModuleData;
import ru.multicabinet.module.api.ServerModule;
import ru.multicabinet.module.option.MultiSelectValue;
import ru.multicabinet.module.option.template.CheckboxModuleOptionTemplate;
import ru.multicabinet.module.option.template.DropdownModuleOptionTemplate;
import ru.multicabinet.module.option.template.ModuleOptionTemplate;
import ru.multicabinet.module.option.template.PasswordModuleOptionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public List<ModuleOptionTemplate> getAccountAccessOptions() {
        List<ModuleOptionTemplate> accountAccessOptions = new ArrayList<ModuleOptionTemplate>();

        accountAccessOptions.add(new ModuleOptionTemplate() {
            @Override
            public String getOptionName() {
                return "username";
            }

            @Override
            public String getDescription() {
                return "Username";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return "/\\d/";
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return true;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });

        accountAccessOptions.add(new PasswordModuleOptionTemplate() {
            @Override
            public String getOptionName() {
                return "password";
            }

            @Override
            public String getDescription() {
                return "Password";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return null;
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return true;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });
        return accountAccessOptions;
    }

    @Override
    public List<ModuleOptionTemplate> getAccountOptions() {
        List<ModuleOptionTemplate> accountOptions = new ArrayList<ModuleOptionTemplate>();

        accountOptions.add(new ModuleOptionTemplate() {
            @Override
            public String getOptionName() {
                return "diskspace";
            }

            @Override
            public String getDescription() {
                return "Disk space";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return "/[0-9]+/";
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return false;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });
        accountOptions.add(new CheckboxModuleOptionTemplate() {
            @Override
            public Boolean isChecked() {
                return true;
            }

            @Override
            public String getOptionName() {
                return "checkbox";
            }

            @Override
            public String getDescription() {
                return "Checkbox";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return null;
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return null;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });

        accountOptions.add(new DropdownModuleOptionTemplate() {
            @Override
            public List<MultiSelectValue> getValues() {
                List<MultiSelectValue> values = new ArrayList<MultiSelectValue>();
                values.add(new MultiSelectValue("value1", "Value 1"));
                values.add(new MultiSelectValue("value2", "Value 2"));
                values.add(new MultiSelectValue("value3", "Value 3"));
                return values;
            }

            @Override
            public String getOptionName() {
                return "dropdown";
            }

            @Override
            public String getDescription() {
                return "Drop down";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return null;
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return null;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });
        return accountOptions;
    }

    @Override
    public List<ModuleOptionTemplate> getServerAccessOptions() {
        List<ModuleOptionTemplate> serverAccessOptions = new ArrayList<ModuleOptionTemplate>();
        serverAccessOptions.add(new ModuleOptionTemplate() {
            @Override
            public String getOptionName() {
                return "serverip";
            }

            @Override
            public String getDescription() {
                return "Server IP";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return null;
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return true;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });
        serverAccessOptions.add(new ModuleOptionTemplate() {
            @Override
            public String getOptionName() {
                return "port";
            }

            @Override
            public String getDescription() {
                return "Port";
            }

            @Override
            public String getI18NDescriptionCode() {
                return null;
            }

            @Override
            public String getValidator() {
                return null;
            }

            @Override
            public String getDefaultValue() {
                return null;
            }

            @Override
            public Boolean isRequired() {
                return null;
            }

            @Override
            public Boolean isAdminOnly() {
                return null;
            }
        });
        return serverAccessOptions;
    }
    @Override
    public List<ModuleOptionTemplate> getCustomOptions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String create(ServerModuleData serverModuleData) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void suspend(String s, ServerModuleData serverModuleData) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void terminate(String s, ServerModuleData serverModuleData) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unsuspend(String s, ServerModuleData serverModuleData) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(String s, ServerModuleData serverModuleData) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private ServiceRegistration registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registration = bundleContext.registerService(ServerModule.class.getName(), this, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
