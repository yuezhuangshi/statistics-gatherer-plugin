package org.jenkins.plugins.statistics.gatherer.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-29.
 */
public class SlaveInfoTest {

    private static final String SLAVE_NAME = "slave";
    private static final String VM_NAME = "vnName";
    private static final String EXECUTOR = "executor";
    private static final String LABEL = "aLabel";
    private static final String DESCRIPTION = "superSlave";
    private static final String REMOTE_FS = "remoteFs";
    private SlaveInfo slaveInfo;

    @Before
    public void initBaseObject() {
        slaveInfo = new SlaveInfo(SLAVE_NAME,
                VM_NAME,
                EXECUTOR,
                LABEL,
                DESCRIPTION,
                REMOTE_FS);
    }

    @Test
    public void givenSlaveInfo_whenGetSlaveName_thenReturnSlaveName() {
        //given

        //when
        String slaveName = slaveInfo.getSlaveName();

        //then
        assertEquals(SLAVE_NAME, slaveName);
    }

    @Test
    public void givenSlaveInfo_whenSetSlaveName_thenSlaveNameIsSet() {
        //given
        String expectedSlaveName = "IHazSlaveName!";
        //when
        slaveInfo.setSlaveName(expectedSlaveName);

        //then
        String actualSlaveName = slaveInfo.getSlaveName();
        assertEquals(expectedSlaveName, actualSlaveName);
    }

    @Test
    public void givenSlaveInfo_whenGetVmName_thenReturnVmName() {
        //given

        //when
        String vmName = slaveInfo.getVmName();

        //then
        assertEquals(VM_NAME, vmName);
    }

    @Test
    public void givenSlaveInfo_whenSetVmName_thenVmNameIsSet() {
        //given
        String expectedVmName = "IHazVmName!";
        //when
        slaveInfo.setVmName(expectedVmName);

        //then
        String actualVmName = slaveInfo.getVmName();
        assertEquals(expectedVmName, actualVmName);
    }

    @Test
    public void givenSlaveInfo_whenGetExecutor_thenReturnExecutor() {
        //given

        //when
        String executor = slaveInfo.getExecutor();

        //then
        assertEquals(EXECUTOR, executor);
    }

    @Test
    public void givenSlaveInfo_whenSetExecutor_thenExecutorIsSet() {
        //given
        String expectedExecutor = "IHazExecutor!";
        //when
        slaveInfo.setExecutor(expectedExecutor);

        //then
        String actualExecutor = slaveInfo.getExecutor();
        assertEquals(expectedExecutor, actualExecutor);
    }

    @Test
    public void givenSlaveInfo_whenGetLabel_thenReturnLabel() {
        //given

        //when
        String label = slaveInfo.getLabel();

        //then
        assertEquals(label, label);
    }

    @Test
    public void givenSlaveInfo_whenSetLabel_thenLabelIsSet() {
        //given
        String expectedLabel = "IHazLabel!";
        //when
        slaveInfo.setLabel(expectedLabel);

        //then
        String actualLabel = slaveInfo.getLabel();
        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    public void givenSlaveInfo_whenGetDescription_thenReturnDescription() {
        //given

        //when
        String description = slaveInfo.getDescription();

        //then
        assertEquals(description, description);
    }

    @Test
    public void givenSlaveInfo_whenSetDescription_thenDescriptionIsSet() {
        //given
        String expectedDescription = "IHazDescription!";
        //when
        slaveInfo.setDescription(expectedDescription);

        //then
        String actualDescription = slaveInfo.getDescription();
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void givenSlaveInfo_whenGetRemoteFs_thenReturnRemoteFs() {
        //given

        //when
        String remoteFs = slaveInfo.getRemoteFs();

        //then
        assertEquals(remoteFs, remoteFs);
    }

    @Test
    public void givenSlaveInfo_whenSetRemoteFs_thenRemoteFsIsSet() {
        //given
        String expectedRemoteFs = "IHazRemoteFs!";
        //when
        slaveInfo.setRemoteFs(expectedRemoteFs);

        //then
        String actualRemoteFs = slaveInfo.getRemoteFs();
        assertEquals(expectedRemoteFs, actualRemoteFs);
    }
}
