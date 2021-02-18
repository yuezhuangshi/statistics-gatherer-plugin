package org.jenkins.plugins.statistics.gatherer.util;

import java.time.LocalDateTime;

/**
 * Common constants
 * @author jasper
 */
public interface Constants {

    String ACTIVE = "ACTIVE";
    String DELETED = "DELETED";
    String DISABLED = "DISABLED";

    String SYSTEM = "SYSTEM";
    String ANONYMOUS = "anonymous";
    String UNKNOWN = "UNKNOWN";

    String UPSTREAM = "UPSTREAM";
    String SCM = "SCM";
    String TIMER = "TIMER";
    String REPLAY = "REPLAY";

    String GITLAB_URL = "gitlabSourceRepoURL";
    String GITLAB_BRANCH = "gitlabBranch";
    String GITLAB_COMMIT = "gitlabMergeRequestLastCommit";
    String GITLAB_USERNAME = "gitlabUserName";
    String GITLAB_ACTION = "gitlabActionType";

    String NODE_NAME = "NODE_NAME";
    String NODE_LABELS = "NODE_LABELS";
    String EXECUTOR_NUMBER = "EXECUTOR_NUMBER";

    String BUILD_FAILURE_ANALYZER_PLUGIN = "Build Failure Analyzer";
    String FOUND_FAILURE_CAUSES = "foundFailureCauses";
    String BUILD_FAILURE_URL_TO_APPEND = "api/json?depth=2&tree=actions[foundFailureCauses[categories,description,id,name]]";

}
