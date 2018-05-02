package org.jenkins.plugins.statistics.gatherer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogbackAbstractTest implements LogbackFixture {

    public String newSimpleLogbackXmlUrl() throws IOException {
        Path logbackXml = newSimpleLogbackXmlPath();
        return logbackXml.toUri().toURL().toString();
    }

    public Path newSimpleLogbackXmlPath() throws IOException {
        Path logbackXml = Files.createTempFile(LogbackImplTest.class.getName(), ".xml");
        Files.write(logbackXml, SIMPLE_LOGBACK_XML.getBytes());
        return logbackXml;
    }
}
