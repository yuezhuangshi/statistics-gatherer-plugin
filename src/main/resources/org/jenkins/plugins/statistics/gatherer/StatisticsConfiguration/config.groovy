package org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration

def f=namespace(lib.FormTagLib)

f.section(title:_("Statistics Gatherer")) {
    f.entry(title:_("Build URL"), field:"buildUrl") {
        f.textbox()
    }
    f.entry(title:_("Project URL"), field:"projectUrl") {
        f.textbox()
    }
    f.entry(title:_("Send Build Info"), field:"buildInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Send Project Info"), field:"projectInfo") {
        f.checkbox(default: true)
    }
    f.entry(title:_("Enable HTTP publishing?"), field:"shouldSendApiHttpRequests") {
        f.checkbox(default: instance.shouldSendApiHttpRequests == true)
    }
}
