package nosi.base.ActiveRecord;

import nosi.core.config.ConfigApp;
import nosi.core.webapp.Core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Emanuel
 * 29 May 2018
 */

public class HibernateUtils {

    private static final Logger LOG = LogManager.getLogger(HibernateUtils.class);

    private static final Map<String, SessionFactory> SESSION_FACTORY = new HashMap<>();
    private static final SessionFactory SESSION_FACTORY_IGRP;
    public static final StandardServiceRegistryBuilder REGISTRY_BUILDER_IGRP;

    public static final String SUFIX_HIBERNATE_CONFIG = ".cfg.xml";

    static {
        String cfgName = ConfigApp.getInstance().getBaseConnection() + SUFIX_HIBERNATE_CONFIG;
        REGISTRY_BUILDER_IGRP = new StandardServiceRegistryBuilder().configure(cfgName);
        SESSION_FACTORY_IGRP = buildSessionFactory(cfgName);
    }

    public static SessionFactory getSessionFactory(String connectionName) {
        return getSessionFactory(connectionName, Core.getCurrentDadParam());
    }

    public static SessionFactory getSessionFactory(String connectionName, String dad) {

        if (connectionName != null && connectionName.equalsIgnoreCase(ConfigApp.getInstance().getBaseConnection()))
            return SESSION_FACTORY_IGRP;

        final String fileName = dad != null && !dad.isEmpty() ? connectionName + "." + dad : connectionName;

        SessionFactory sessionFactory = SESSION_FACTORY.computeIfAbsent(connectionName, sf -> buildSessionFactory(fileName + SUFIX_HIBERNATE_CONFIG));

        if (sessionFactory != null && sessionFactory.isOpen())
            return sessionFactory;

        removeSessionFactory(connectionName);

        sessionFactory = buildSessionFactory(fileName + SUFIX_HIBERNATE_CONFIG);
        SESSION_FACTORY.put(connectionName, sessionFactory);

        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory(String cfgName) {
        try {
            ServiceRegistry serviceRegistry;
            if (cfgName.startsWith(ConfigApp.getInstance().getBaseConnection()))
                serviceRegistry = REGISTRY_BUILDER_IGRP.build();
            else
                serviceRegistry = new StandardServiceRegistryBuilder().configure(cfgName).build();
            return new Configuration().buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            LOG.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static synchronized void unregisterAllDrivers() {
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeAllConnection() {
        SESSION_FACTORY.values().forEach(SessionFactory::close);
        if (SESSION_FACTORY_IGRP != null)
            SESSION_FACTORY_IGRP.close();
    }

    public static void removeSessionFactory(String connectionName) {
        final SessionFactory sessionFactory = SESSION_FACTORY.remove(connectionName);
        if (null != sessionFactory && sessionFactory.isOpen())
            sessionFactory.close();
    }
}