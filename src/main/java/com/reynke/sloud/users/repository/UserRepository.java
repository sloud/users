package com.reynke.sloud.users.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.reynke.sloud.databaseutilities.database.IDatabase;
import com.reynke.sloud.databaseutilities.exception.DatabaseUtilitiesException;
import com.reynke.sloud.databaseutilities.repository.AbstractRepository;
import com.reynke.sloud.users.entity.User;
import org.hibernate.Session;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Singleton
public class UserRepository extends AbstractRepository<User> {

    @Inject
    public UserRepository(IDatabase database, Logger logger) {
        super(database, logger);
    }

    public User findOneByUuid(UUID uuid) {

        Session session;

        try {
            session = this.getDatabase().openSession();
        } catch (DatabaseUtilitiesException e) {
            throw new RuntimeException(e);
        }

        Object retrievedObject = session.createQuery("FROM " + User.class.getName() + " WHERE uuid = :uuid").setParameter("uuid", uuid).uniqueResult();
        session.close();

        if (retrievedObject == null) {
            return null;
        }

        return (User) retrievedObject;
    }
}
