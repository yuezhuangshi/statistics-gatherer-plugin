package org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration

def f=namespace(lib.FormTagLib)

f.section(title:_("Statistics Gatherer")) {
    f.entry(title:_("Queue URL"), field:"queueUrl") {
        f.textbox(default: "https://prodmetrics.frimastudio.com/queue")
    }
    f.entry(title:_("Build URL"), field:"buildUrl") {
        f.textbox(default: "https://prodmetrics.frimastudio.com/builds")
    }
    f.entry(title:_("Project URL"), field:"projectUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/projects")
    }

    f.entry(title:_("Send Queue Info"), field:"queueInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Build Info"), field:"buildInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Job Info"), field:"projectInfo") {
        f.checkbox(default: false)
    }
}
