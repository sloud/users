package com.reynke.sloud.users.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.core.controller.IController;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;
import com.reynke.sloud.databaseutilities.repository.IRepositoryFactory;
import com.reynke.sloud.users.IUsersPlugin;
import com.reynke.sloud.users.entity.User;
import com.reynke.sloud.users.repository.UserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import java.util.Date;
import java.util.UUID;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class UserController implements IController {

    private IUsersPlugin usersPlugin;
    private IRepositoryFactory repositoryFactory;

    @Inject
    public UserController(IUsersPlugin usersPlugin) {
        this.usersPlugin = usersPlugin;
        this.repositoryFactory = usersPlugin.getCorePluginInjector().getInstance(IRepositoryFactory.class);
    }

    /**
     * @param playerEvent The event that triggered this action.
     */
    public void joinAction(PlayerEvent playerEvent) {

        Player player = playerEvent.getPlayer();

        // Get user specific repository
        UserRepository userRepository;

        try {
            userRepository = (UserRepository) repositoryFactory.getRepository(User.class);
        } catch (DatabaseUtilitiesException e) {
            player.kickPlayer("Couldn't get the UserRepository. Please contact a team member as soon as possible!");
            throw new RuntimeException(e);
        }

        UUID playerUuid = player.getUniqueId();
        String playerName = player.getName();
        User user = userRepository.findOneByUuid(playerUuid);

        if (user != null) {

            // Detect a name change
            if (!user.getName().equals(playerName)) {
                user.setName(playerName);
            }

            // Update the players data
            user.setLastJoin(new Date());
            user.setOnline();

            // Update user in database
            userRepository.update(user);

            // Store the unique identifier in the players metadata
            this.storeUserIdInPlayersMetadata(player, user);

            return;
        }

        // Create a new user
        user = new User();

        // Set its data
        user.setName(player.getName());
        user.setUuid(playerUuid);

        // Persist the new user to the database
        userRepository.create(user);

        // Store the unique identifier in the players metadata
        this.storeUserIdInPlayersMetadata(player, user);
    }

    private void storeUserIdInPlayersMetadata(Metadatable metadatable, User user) {
        metadatable.setMetadata(
                IUsersPlugin.METADATA_NAMESPACE + "user_id",
                new FixedMetadataValue(usersPlugin, user.getId())
        );
    }

    /**
     * @param playerEvent The event that triggered this action.
     */
    public void quitAction(PlayerEvent playerEvent) {

        Player player = playerEvent.getPlayer();

        // Get user specific repository
        UserRepository userRepository;

        try {
            userRepository = (UserRepository) repositoryFactory.getRepository(User.class);
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        for (MetadataValue metadataValue : player.getMetadata(IUsersPlugin.METADATA_NAMESPACE + "user_id")) {
            // @todo Check that inserted metadata is still valid. In the end try to get the users id to find the user in the db by its id and not its uuid.
            System.out.println(metadataValue.value());
        }

        // Find existing user
        User user = userRepository.findOneByUuid(player.getUniqueId());

        // Exit quietly if the user was not found
        if (user == null) {
            return;
        }

        // Update its data
        user.setLastQuit(new Date());
        user.setPlayTime(((new Date()).getTime() - user.getLastJoin().getTime()) + user.getPlayTime());
        user.setOffline();

        // Update user
        userRepository.update(user);
    }
}
