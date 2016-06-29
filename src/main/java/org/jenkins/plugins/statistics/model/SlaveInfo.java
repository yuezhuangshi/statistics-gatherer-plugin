package org.jenkins.plugins.statistics.model;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class SlaveInfo {

    public SlaveInfo(String slaveName,
                     String vmName,
                     String executor,
                     String label,
                     String description,
                     String remoteFs) {
        this.slaveName = slaveName;
        this.vmName = vmName;
        this.executor = executor;
        this.label = label;
        this.description = description;
        this.remoteFs = remoteFs;
    }

    public SlaveInfo() {
        this.slaveName = "";
        this.vmName = "";
        this.executor = "";
        this.label = "";
        this.description = "";
        this.remoteFs = "";
    }

    private String slaveName;

    private String vmName;

    private String executor;

    private String label;

    private String description;

    private String remoteFs;

    public String getSlaveName() {
        return slaveName;
    }

    public void setSlaveName(String slaveName) {
        this.slaveName = slaveName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemoteFs() {
        return remoteFs;
    }

    public void setRemoteFs(String remoteFs) {
        this.remoteFs = remoteFs;
    }
}
