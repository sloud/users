package com.reynke.sloud.users.entity;

import com.reynke.sloud.core.controller.Controller;
import com.reynke.sloud.databaseutilities.entity.IEntity;
import com.reynke.sloud.databaseutilities.repository.Repository;
import com.reynke.sloud.users.IUsersPlugin;
import com.reynke.sloud.users.controller.UserController;
import com.reynke.sloud.users.repository.UserRepository;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Entity
@Table(name = IUsersPlugin.METADATA_NAMESPACE + "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Repository(type = UserRepository.class)
@Controller(type = UserController.class)
public class User implements IEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uuid", nullable = false, unique = true)
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(name = "first_join", nullable = false)
    private Date firstJoin;

    @Column(name = "last_join", nullable = false)
    private Date lastJoin;

    @Column(name = "last_quit")
    private Date lastQuit;

    /**
     * The playtime represents a timestamp resulting from calculating the timestamp
     * of the current date minus the last join timestamp plus the current play time
     * (to add the currently elapsed play time to the current play time).
     * <p>
     * <code>
     * Long playTime = ((new Date()).getTime() - user.getLastJoin().getTime()) + user.getPlayTime();
     * </code>
     */
    @Column(name = "play_time", nullable = false)
    private long playTime;

    @Column(name = "online", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean online;

    /**
     * Constructor.
     * <p>
     * Initially sets ...
     * <p>
     * <ul>
     * <li>
     * {@link #id} to zero (<code>0L</code>)
     * </li>
     * <li>
     * {@link #firstJoin} and {@link #lastJoin} to the current date since an {@link User} object
     * will only be created when a new user joins
     * </li>
     * <li>
     * {@link #playTime} to zero (<code>0L</code>) since the player hasn't played yet that much obviously
     * </li>
     * <li>
     * {@link #online} to <code>true</code> since the player just joined
     * </li>
     * </ul>
     */
    public User() {
        id = 0L;
        firstJoin = new Date();
        lastJoin = new Date();
        playTime = 0L;
        online = true;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getFirstJoin() {
        return firstJoin;
    }

    public void setFirstJoin(Date firstJoin) {
        this.firstJoin = firstJoin;
    }

    public Date getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(Date lastJoin) {
        this.lastJoin = lastJoin;
    }

    public Date getLastQuit() {
        return lastQuit;
    }

    public void setLastQuit(Date lastQuit) {
        this.lastQuit = lastQuit;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline() {
        this.online = true;
    }

    public void setOffline() {
        this.online = false;
    }
}
