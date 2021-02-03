package org.jenkins.plugins.statistics.gatherer.util;

import java.time.LocalDateTime;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public final class Constants {

    public static final String ACTIVE = "ACTIVE";
    public static final String DELETED = "DELETED";
    public static final String DISABLED = "DISABLED";
    public static final String ENTERED = "ENTERED";
    public static final String LEFT = "LEFT";
    public static final String SYSTEM = "SYSTEM";
    public static final String ANONYMOUS = "anonymous";
    public static final String UNKNOWN = "UNKNOWN";
    public static final LocalDateTime TIME_EPOCH = LocalDateTime.of(1970, 1, 1, 0, 0, 0);

    protected Constants() {
        throw new IllegalAccessError("Utility class");
    }
}
