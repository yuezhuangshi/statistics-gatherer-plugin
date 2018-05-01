package org.jenkins.plugins.statistics.gatherer.util;

public class LogbackFactory {

    public static Logback create(String loggerName) throws Exception {
        Class<Logback> logback = (Class<Logback>) Class.forName(Logback.class.getName() + "Impl");
        return logback.newInstance().setLoggerName(loggerName);
    }
}
