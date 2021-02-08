package org.jenkins.plugins.statistics.gatherer.model.stage;

import hudson.model.Result;
import lombok.Data;

import javax.annotation.Nonnull;

/**
 *
 * @author jasper
 */
@Data
public class StageStats {
    /**
     * name
     */
    private String name;
    /**
     * state
     */
    private State state;

    /**
     * execute duration
     */
    private long duration;
    /**
     * stage or not
     */
    private boolean isStage = true;
    /**
     * pass or not
     */
    private boolean passed;

    public enum State {
        Pending,
        SkippedConditional,
        SkippedUnstable,
        SkippedFailure,
        CompletedSuccess,
        CompletedError,
        Unstable,
        NotBuilt,
        Aborted,
        Unknown;

        public static @Nonnull State fromResult(Result result) {
            switch (result.ordinal) {
                case 0:
                    return State.CompletedSuccess;
                case 1:
                    return Unstable;
                case 2:
                    return CompletedError;
                case 3:
                    return NotBuilt;
                case 4:
                    return Aborted;
                default:
                    return Unknown;
            }
        }
    }

    public StageStats(String name) {
        this(name, State.Pending);
    }

    public StageStats(String name, State state) {
        this.name = name;
        this.state = state;
        this.passed = state != State.CompletedError;
    }

    public void setState(State state) {
        this.state = state;
        passed = state != State.CompletedError;
    }

}
