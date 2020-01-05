/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: TestSuiteBase.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 * 
 * Students must sub-class this class when building their TestSuite class
 */
package com.algonquincollege.cst8277.assignment3;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.DefaultSessionLog;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSuiteBase {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    public static final String ASSIGNMENT3_PU_NAME = "Assignment3-testing-PU";
    public static final String ECLIPSELINK_LOGGING_SQL = "eclipselink.logging.sql";

    static EntityManagerFactory buildEntityManagerFactory() {
        //
        Map<String, Object> properties = new HashMap<>();
        /* 
         * prevent bug 521424 - if one restarts a PU without stopping the JVM
         * (say in a series of JUnit tests), the singleton Session log has stuff
         * from the last 'live' session. Solution: reset log and then re-create
         * EMF, custom SLF4J logging will now work
         */
        AbstractSessionLog.setLog(new DefaultSessionLog());
        return Persistence.createEntityManagerFactory(ASSIGNMENT3_PU_NAME, properties);
    }

    public static ListAppender<ILoggingEvent> attachListAppender(ch.qos.logback.classic.Logger theLogger, String listAppenderName) {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.setName(listAppenderName);
        listAppender.start();
        theLogger.addAppender(listAppender);
        listAppender.setContext(theLogger.getLoggerContext());
        return listAppender;
    }

    public static void detachListAppender(ch.qos.logback.classic.Logger theLogger, ListAppender<ILoggingEvent> listAppender) {
        theLogger.detachAppender(listAppender);
    }

    // test fixture(s)
    public static EntityManagerFactory emf;
    public static org.h2.tools.Server server;

    @BeforeClass
    public static void setUp() {
        logger.debug("one time setUp");
        try {
            server = org.h2.tools.Server.createTcpServer().start();
            emf = buildEntityManagerFactory();
        }
        catch (SQLException sqle) {
            logger.error("problem starting in-mem H2 database server", sqle);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        logger.debug("one time tearDown");
        if (emf != null) {
            emf.close();
            emf = null;
        }
        if (server != null) {
            server.stop();
            server = null;
        }
    }

}