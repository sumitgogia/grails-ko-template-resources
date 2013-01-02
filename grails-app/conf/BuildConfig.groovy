grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	repositories {
		grailsPlugins()
		grailsHome()
		grailsCentral()
	}
	dependencies {
	}

	plugins {
		runtime ":resources:1.2.RC2"
		build(":tomcat:$grailsVersion",
				":release:2.0.3",
				":rest-client-builder:1.0.2") {
					export = false
				}
	}
}
