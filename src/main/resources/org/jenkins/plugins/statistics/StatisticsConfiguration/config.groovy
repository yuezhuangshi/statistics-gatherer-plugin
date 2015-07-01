package org.jenkins.plugins.statistics.StatisticsConfiguration

def f=namespace(lib.FormTagLib)

f.section(title:_("Statistics Notification")) {
    f.entry(title:_("Notification URL"), field:"notificationUrl") {
        f.textbox(default: "http://ci.mycompany.com/api/")
    }
    f.entry(title:_("Connection Timeout"), field:"connectionTimeout") {
        f.textbox(default: 2000)
    }
    f.entry(title:_("Socket Timeout"), field:"socketTimeout") {
        f.textbox(default: 2000)
    }
}
