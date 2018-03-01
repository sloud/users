package com.reynke.sloud.users;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.reynke.sloud.core.ICorePlugin;
import com.reynke.sloud.core.controller.IControllerFactory;
import com.reynke.sloud.core.exception.CoreException;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.users.controller.UserController;
import com.reynke.sloud.users.dependencyinjection.UsersModule;
import com.reynke.sloud.users.entity.User;
import com.reynke.sloud.users.listener.UserListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class UsersPlugin extends JavaPlugin implements IUsersPlugin {

    private Injector corePluginInjector;

    @Override
    public void onEnable() {

        super.onEnable();

        this.corePluginInjector = this.retrieveCorePluginInjector();
        this.registerEvents();
    }

    private Injector retrieveCorePluginInjector() {

        ICorePlugin corePlugin = (ICorePlugin) this.getServer().getPluginManager().getPlugin(ICorePlugin.PLUGIN_NAME);

        return corePlugin.getInjector();
    }

    @Override
    public Module getModule() {
        return new UsersModule(this);
    }

    @Override
    public Injector getCorePluginInjector() {
        return corePluginInjector;
    }

    private void registerEvents() {

        try {

            this.getServer().getPluginManager().registerEvents(
                    new UserListener((UserController) corePluginInjector.getInstance(IControllerFactory.class).getController(User.class)),
                    this
            );

        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getPackages() {

        List<String> packages = new ArrayList<>();
        packages.add("com.reynke.sloud.users.entity");

        return packages;
    }

    @Override
    public List<Class<? extends IEntity>> getAnnotatedClasses() {

        List<Class<? extends IEntity>> annotatedClasses = new ArrayList<>();
        annotatedClasses.add(User.class);

        return annotatedClasses;
    }
}
