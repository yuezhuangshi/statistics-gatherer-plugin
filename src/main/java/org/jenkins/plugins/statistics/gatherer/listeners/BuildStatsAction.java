/*
 * The MIT License
 *
 * Copyright 2017 Jeff Pearce (jeffpea@gmail.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.model.InvisibleAction;
import hudson.model.Job;
import hudson.model.Run;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jenkins.plugins.statistics.gatherer.model.build.BuildStats;
import org.jenkins.plugins.statistics.gatherer.model.stage.StageStats;

import java.util.Map;

/**
 * Keeps track of build status for each stage in a build, and provides
 * mechanisms for notifying various subscribers as stages and jobs are
 * completed.
 *
 * @author jasper
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BuildStatsAction extends InvisibleAction {

    private BuildStats buildStats;

    private transient Job<?, ?> job;
    private transient Run<?, ?> run;

    public BuildStatsAction(Job<?, ?> job, Run<?, ?> run) {
        this.job = job;
        this.run = run;
        this.buildStats = new BuildStats();
    }

    public void setIsDeclarativePipeline(boolean isDeclarativePipeline) {
        this.buildStats.setDeclarativePipeline(isDeclarativePipeline);
    }

    /**
     * Add stage stats for the start of a stage.
     */
    public void addStageStats(String stageName, StageStats.State state) {
        StageStats stageItem = new StageStats(stageName, state);
        buildStats.getStages().put(stageName, stageItem);
    }

    /**
     * Add non stage stats for an error that happens outside of a stage.
     */
    public void addNonStageError(String nodeName, StageStats.State state) {
        if (buildStats.getStages().get(nodeName) != null) {
            // We already reported this error
            return;
        }
        StageStats stageItem = new StageStats(nodeName, state);
        stageItem.setStage(false);
        buildStats.getStages().put(nodeName, stageItem);
    }

    /**
     * Update stats for a completed stage.
     */
    public void updateStageStats(String nodeName, StageStats.State buildState, long time) {
        StageStats stageStats = buildStats.getStages().get(nodeName);
        if (stageStats != null) {
            StageStats.State currentStatus = stageStats.getState();
            if (currentStatus == StageStats.State.Pending) {
                stageStats.setState(buildState);
                stageStats.setDuration(time);
            }
        }
    }

    /**
     * Update stage stats with default duration
     */
    public void updateStageStats(String nodeName, StageStats.State buildState) {
        updateStageStats(nodeName, buildState, 0);
    }

    /**
     * Cleans up by sending "complete" status to any steps that are currently
     * pending. Needed because some complex jobs, particularly using down
     */
    public void close() {
        Map<String, StageStats> stages = this.buildStats.getStages();
        if (null != stages) {
            stages.forEach((nodeName, stageItem) -> {
                if (stageItem.getState() == StageStats.State.Pending) {
                    this.updateStageStats(nodeName, StageStats.State.CompletedSuccess);
                }
            });
        }
    }

    /**
     * Update build stats when build complete.
     */
    public void updateBuildStatusForJob(StageStats.State buildState) {
        close();
    }

}
