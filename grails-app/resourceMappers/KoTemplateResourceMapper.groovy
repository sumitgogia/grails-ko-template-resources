import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.plugin.resource.mapper.MapperPhase
import org.codehaus.groovy.grails.plugins.codecs.JavaScriptCodec

/**
 * Ko-Template resource mapping. Convert knockout template(s) to .js files
 *
 * @author Sumit Gogia
 */
class KoTemplateResourceMapper {
	static phase = MapperPhase.GENERATION
	static defaultIncludes = [ "pages/**/*.html" ]
	static defaultPathToTemplatesFolder = "/pages"

	GrailsApplication grailsApplication

	def map(resource, config){
		String pathToTemplatesFolder = defaultPathToTemplatesFolder
		def pathFromConfig = grailsApplication.config.flatten().get("grails.plugins.knockoutTemplateResources.pathToTemplatesFolder")
		if(pathFromConfig instanceof CharSequence) pathToTemplatesFolder = pathFromConfig

		if(!resource.originalUrl?.startsWith(pathToTemplatesFolder)) {
			log.error "Not processing resource [${resource.originalUrl}] as it is not under the templates folder [$pathToTemplatesFolder]. You should change/add this in your config: grails.resources.mappers.kotemplateresource.includes = ['${pathToTemplatesFolder[1..-1]}/**/*.${resource.sourceUrlExtension}']"
			return
		}

		File koFile = resource.processedFile
		File jsFile = new File(koFile.absolutePath + '.js')

		try {
			log.debug "Converting Ko-Template file [${koFile}] into [${jsFile}]"

			int s = pathToTemplatesFolder.size()+1
			int e = resource.sourceUrlExtension.size()+2
			String pathRelativeToTemplatesFolder = resource.originalUrl[s..-e]

			String koTmplString = "<script type='text/html' id='${pathRelativeToTemplatesFolder}'>${koFile?.text}</script>"
			String escapedKoTmplString = JavaScriptCodec.ENCODER.encode(koTmplString)

			jsFile.write("\$('body').append('${escapedKoTmplString}');")

			resource.processedFile = jsFile
			resource.contentType = 'text/javascript'
			resource.updateActualUrlFromProcessedFile()
		} catch (Exception e) {
			log.error("Error Converting Ko-Template file [${koFile}]", e)
		}
	}

}
