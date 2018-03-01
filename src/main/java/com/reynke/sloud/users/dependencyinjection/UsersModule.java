package com.reynke.sloud.users.dependencyinjection;

import com.google.inject.AbstractModule;
import com.reynke.sloud.users.IUsersPlugin;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public class UsersModule extends AbstractModule {

    private IUsersPlugin plugin;

    public UsersModule(IUsersPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(IUsersPlugin.class).toInstance(plugin);
    }
}
