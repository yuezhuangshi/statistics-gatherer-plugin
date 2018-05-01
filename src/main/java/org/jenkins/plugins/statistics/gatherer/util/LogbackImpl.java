package org.jenkins.plugins.statistics.gatherer.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

import java.net.MalformedURLException;
import java.net.URL;

public class LogbackImpl implements Logback {
    private static Logger logger;

    @Override
    public Logback setLoggerName(String loggerName) {
        LoggerContext loggerContext = new LoggerContext();
        ContextInitializer contextInitializer = new ContextInitializer(loggerContext);
        try {
            String configurationUrlString = PropertyLoader.getLogbackConfigXmlUrl();
            if (configurationUrlString == null) {
                throw new IllegalStateException("LOGBack XML configuration file not specified");
            }

            URL configurationUrl = new URL(configurationUrlString);
            contextInitializer.configureByResource(configurationUrl);
            logger = loggerContext.getLogger(loggerName);
            return this;
        } catch (JoranException e) {
            throw new RuntimeException("Unable to configure logger", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to find LOGBack XML configuration", e);
        }
    }

    @Override
    public void log(String msg) {
        if (logger != null) {
            logger.info(msg);
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
