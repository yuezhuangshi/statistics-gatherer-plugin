[![Build Status](https://travis-ci.org/jenkinsci/statistics-gatherer-plugin.svg?branch=statistics-gatherer-2.0.2)](https://travis-ci.org/jenkinsci/statistics-gatherer-plugin)
[![Coverage Status](https://coveralls.io/repos/github/jenkinsci/statistics-gatherer-plugin/badge.svg?branch=statistics-gatherer-2.0.2)](https://coveralls.io/github/jenkinsci/statistics-gatherer-plugin?branch=statistics-gatherer-2.0.2)

Jenkins Statistics Gatherer Plugin
==================================

This plugin gathers information on specific events on Jenkins and sends them to an external API. That way, you can get the statistics that matters for your needs.

Issues
======

If you find an issue in this plugin, please open a ticket on [issues.jenkins-ci.org](https://issues.jenkins-ci.org/issues/?jql=component%20%3D%20statistics-gatherer-plugin) using the `statistics-gatherer-plugin` component.

Documentation
=============

In this section, you'll see which data is sent when. Please note that adding a field to JSON is not considered as a breaking change. However, removing a field is considered as a breaking change.

Build
-----

On the finalize part of a build:

```json
{
    "buildCause": "Started by user anonymous",
    "buildNumber": 64,
    "buildUrl": "job/Clone/64/",
    "declarativePipeline": true,
    "duration": 26677,
    "endTime": "2021-02-08 12:36:42",
    "fullJobName": "Clone",
    "jobName": "Clone",
    "parameters": {
        "abc": "123",
        "test": "false"
    },
    "queueTime": 8,
    "result": "SUCCESS",
    "rootUrl": "http://localhost:8080/jenkins/",
    "scmInfo": {
        "url": "xxx",
        "branch": "yyy",
        "commit": "zzz"
    },
    "slaveInfo": {
        "executor": "-1",
        "label": "master",
        "slaveName": "master"
    },
    "stages": {
        "Echo": {
            "duration": 15355,
            "name": "Echo",
            "passed": true,
            "stage": true,
            "state": "CompletedSuccess"
        },
        "Clone": {
            "duration": 1942,
            "name": "Clone",
            "passed": true,
            "stage": true,
            "state": "CompletedSuccess"
        }
    },
    "startTime": "2021-02-08 12:36:16",
    "startedUserId": "anonymous",
    "startedUserName": "anonymous"
}
```

The field `result` can contain "SUCCESS", "UNSTABLE", "FAILURE", "ABORTED" or "NOT_BUILT".

If the [Build Failure Analyzer](https://plugins.jenkins.io/build-failure-analyzer) plugin is installed and the build failed:

```json
{
    "buildCause": "Started by user anonymous",
    "buildFailureCauses": [
        {
            "name": "aborted",
            "categories": [
                "aCategory"
            ],
            "id": "ccff5d15-1003-4570-b38f-f844255c6be1",
            "description": "Build was manually aborted"
        },
        {...}
    ],
    "buildNumber": 64,
    "buildUrl": "job/Clone/64/",
    "declarativePipeline": true,
    "duration": 26677,
    "endTime": "2021-02-08 12:36:42",
    "fullJobName": "Clone",
    "jobName": "Clone",
    "parameters": {
        "abc": "123",
        "test": "false"
    },
    "queueTime": 8,
    "result": "ABORTED",
    "rootUrl": "http://localhost:8080/jenkins/",
    "scmInfo": {
        "url": "xxx",
        "branch": "yyy",
        "commit": "zzz"
    },
    "slaveInfo": {
        "executor": "-1",
        "label": "master",
        "slaveName": "master"
    },
    "stages": {
        "Echo": {
            "duration": 15355,
            "name": "Echo",
            "passed": true,
            "stage": true,
            "state": "CompletedSuccess"
        },
        "Clone": {
            "duration": 1942,
            "name": "Clone",
            "passed": true,
            "stage": true,
            "state": "CompletedSuccess"
        }
    },
    "startTime": "2021-02-08 12:36:16",
    "startedUserId": "anonymous",
    "startedUserName": "anonymous"
}
```

Project/Job
-----------

When a job is created:

```json
{
    "configFile": "<?xml version='1.1' encoding='UTF-8'?>\n<flow-definition plugin=\"workflow-job@2.40\">\n  <keepDependencies>false</keepDependencies>\n  <properties/>\n  <triggers/>\n  <disabled>false</disabled>\n</flow-definition>",
    "createdDate": "2021-02-08T12:45:18.325",
    "fullName": "234",
    "jobUrl": "job/234/",
    "name": "234",
    "rootUrl": "http://localhost:8080/jenkins/",
    "status": "ACTIVE",
    "updatedDate": "2021-02-08T12:45:18.325"
}
```

The `configFile` field contains the job's XML configuration.

When a job is updated:

```json
{
    "configFile": "<?xml version='1.1' encoding='UTF-8'?>\n<flow-definition plugin=\"workflow-job@2.40\">\n  <actions>\n    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobAction plugin=\"pipeline-model-definition@1.8.0\"/>\n    <org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction plugin=\"pipeline-model-definition@1.8.0\">\n      <jobProperties/>\n      <triggers/>\n      <parameters/>\n      <options/>\n    </org.jenkinsci.plugins.pipeline.modeldefinition.actions.DeclarativeJobPropertyTrackerAction>\n  </actions>\n  <description>12312</description>\n  <keepDependencies>false</keepDependencies>\n  <properties>\n    <com.sonyericsson.jenkins.plugins.bfa.model.ScannerJobProperty plugin=\"build-failure-analyzer@1.27.1\">\n      <doNotScan>false</doNotScan>\n    </com.sonyericsson.jenkins.plugins.bfa.model.ScannerJobProperty>\n    <hudson.model.ParametersDefinitionProperty>\n      <parameterDefinitions>\n        <hudson.model.BooleanParameterDefinition>\n          <name>test</name>\n          <description>123</description>\n          <defaultValue>false</defaultValue>\n        </hudson.model.BooleanParameterDefinition>\n        <hudson.model.StringParameterDefinition>\n          <name>abc</name>\n          <description>123</description>\n          <defaultValue>123</defaultValue>\n          <trim>false</trim>\n        </hudson.model.StringParameterDefinition>\n      </parameterDefinitions>\n    </hudson.model.ParametersDefinitionProperty>\n    <org.jenkinsci.plugins.workflow.job.properties.DisableConcurrentBuildsJobProperty/>\n  </properties>\n  <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2.87\">\n    <script>pipeline {\n    agent any\n\n    stages {\n        stage(&apos;Clone&apos;) {\n            steps {\n                git branch: &apos;master&apos;, credentialsId: &apos;gitee&apos;, poll: false, url: &apos;git@gitee.com:jasperyue/test-repo.git&apos;\n            }\n        }\n        stage(&apos;Echo&apos;) {\n            steps {\n                sh &apos;printenv | sort&apos;\n                sleep 15\n            }\n        }\n    }\n}\n</script>\n    <sandbox>true</sandbox>\n  </definition>\n  <triggers/>\n  <disabled>false</disabled>\n</flow-definition>",
    "fullName": "Clone",
    "jobUrl": "job/Clone/",
    "name": "Clone",
    "rootUrl": "http://localhost:8080/jenkins/",
    "status": "ACTIVE",
    "updatedDate": "2021-02-08T12:43:01.750"
}
```

When a job is deleted:

```json
{
    "fullName": "Checkout",
    "jobUrl": "job/Checkout/",
    "name": "Checkout",
    "rootUrl": "http://localhost:8080/jenkins/",
    "status": "DELETED",
    "updatedDate": "2021-02-08T12:44:19.419"
}
```

The field `status` can contain "ACTIVE", "DISABLED" or "DELETED".

Development
===========

Start a local Jenkins instance:

```
mvn hpi:run
```

Jenkins Plugin Maven goals
--------------------------

```
hpi:create  Creates a skeleton of a new plugin.
hpi:hpi     Builds the .hpi file.
hpi:hpl     Generates the .hpl file.
hpi:run     Runs Jenkins with the current plugin project.
hpi:upload  Posts the hpi file to java.net. Used during the release.
```

How to install
--------------

To create the plugin .hpi file:

```
mvn hpi:hpi
```

To install the plugin:

1. Copy the resulting ./target/statistics-gatherer.hpi file to the $JENKINS_HOME/plugins directory. Don't forget to restart Jenkins afterwards.
2. Or use the plugin management console (http://example.com:8080/pluginManager/advanced) to upload the hpi file. You have to restart Jenkins in order to find the plugin in the installed plugins list.

Plugin releases
---------------

```
mvn release:prepare release:perform -Dusername=user -Dpassword=******
```

Changelog
---------

Please refer to the [CHANGELOG.md](CHANGELOG.md) file.

License
-------

```
(The MIT License)

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
'Software'), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
