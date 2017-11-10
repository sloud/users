package com.reynke.sloud.users;

import com.google.inject.Injector;
import com.reynke.sloud.core.IModuleAware;
import com.reynke.sloud.databaseutilities.database.IDatabaseEntitiesAware;
import org.bukkit.plugin.Plugin;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public interface IUsersPlugin extends Plugin, IDatabaseEntitiesAware, IModuleAware {

    String PLUGIN_NAME = "Users";
    String METADATA_NAMESPACE = "users_";

    /**
     * @return The dependency injection injection retrieved by the core module
     */
    Injector getCorePluginInjector();
}
