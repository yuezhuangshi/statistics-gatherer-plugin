package org.jenkins.plugins.statistics.model;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class SCMInfo {

  private String url;

  private String branch;

  private String commit;

  public String getUrl ()
  {
    return url;
  }

  public void setUrl ( String url )
  {
    this.url = url;
  }

  public String getBranch ()
  {
    return branch;
  }

  public void setBranch ( String branch )
  {
    this.branch = branch;
  }

  public String getCommit ()
  {
    return commit;
  }

  public void setCommit ( String commit )
  {
    this.commit = commit;
  }

}
