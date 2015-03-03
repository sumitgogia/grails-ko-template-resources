import org.grails.plugin.resource.BundleResourceMapper
import org.grails.plugin.resource.JavaScriptBundleResourceMeta
import org.grails.plugin.resource.ResourceModule
import org.grails.plugin.resource.ResourceProcessor
import org.grails.plugin.resource.ResourceTagLib

class KnockoutTemplateResourcesGrailsPlugin {
	def version = "0.1.4"
	def grailsVersion = "2.0 > *"
    def dependsOn = [resources:'1.0 > *']
    def loadAfter = ["codecs", "resources"]
	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

	def title = "Knockout Template Resources Plugin"
	def author = "Sumit Gogia"
	def authorEmail = "sumitgogia@gmail.com"
	def description = '''\
	Enable processing of knockout templates as grails resources. Hence benefit from various features of the resources plugin e.g. bundling, caching etc.
	'''

	def documentation = "https://github.com/sumitgogia/grails-ko-template-resources/blob/master/README.md"
	def issueManagement = [ system: "GITHUB", url: "https://github.com/sumitgogia/grails-ko-template-resources/issues" ]
	def scm = [ url: "https://github.com/sumitgogia/grails-ko-template-resources" ]

	def license = "APACHE"

	static defaultTemplateExtension = "html"
	
    def doWithSpring = {
		def koTemplateExtn = defaultTemplateExtension
		def extnFromConfig = application.config.flatten().get("grails.plugins.knockoutTemplateResources.templateExtension")
		if(extnFromConfig instanceof CharSequence) koTemplateExtn = extnFromConfig 
		
		BundleResourceMapper.MIMETYPE_TO_RESOURCE_META_CLASS.put("text/$koTemplateExtn", JavaScriptBundleResourceMeta)
		List currentTypes = new ResourceModule().bundleTypes
		ResourceModule.metaClass.getBundleTypes = {  currentTypes << koTemplateExtn }
		ResourceProcessor.DEFAULT_MODULE_SETTINGS[koTemplateExtn] = [disposition: 'defer']
		ResourceTagLib.SUPPORTED_TYPES[koTemplateExtn] = [
			type: "text/javascript",
			writer: "js"
		]
    }
}
