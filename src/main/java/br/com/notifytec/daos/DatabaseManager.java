package br.com.notifytec.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {

    public EntityManager open(boolean transaction) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("notifytec-per");
        EntityManager manager = entityManagerFactory.createEntityManager();
        if (transaction) {
            if (!manager.getTransaction().isActive()) {
                manager.getTransaction().begin();
            }
        }
        return manager;
    }

    public EntityManager open() {
        return open(false);
    }

    public void close(EntityManager manager) {
        close(manager, false);
    }

    public void close(EntityManager manager, boolean commit) {
        if (manager.isOpen() && manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
        }

        if (manager.getEntityManagerFactory().isOpen()) {
            manager.getEntityManagerFactory().close();
        }
        if (manager.isOpen()) {
            manager.close();
        }
    }
}
