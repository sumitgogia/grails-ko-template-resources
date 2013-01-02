import org.grails.plugin.resource.mapper.MapperPhase
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.web.taglib.RenderTagLib;

import java.util.concurrent.ConcurrentHashMap

/**
 * Ko-Template resource mapping. Convert knockout template(s) to .js files
 *
 * @author Sumit Gogia
 */
class KoTemplateResourceMapper {
	//@TODO - Move this to Config.groovy
	static def koTemplateExtn = "html"
	static def koPathToTemplatesFolder = "pages"
	
    static phase = MapperPhase.GENERATION
    static defaultIncludes = [ "$koPathToTemplatesFolder/**/*.$koTemplateExtn" ]

    GrailsApplication grailsApplication

    def paths = [].asSynchronized()

    def map(resource, config){
        File koFile = resource.processedFile
        File jsFile = new File(koFile.absolutePath + '.js')
        
        try {
            log.debug "Converting Ko-Template file [${koFile}] into [${jsFile}]"

            String pathRelativeToTemplatesFolder = resource.originalUrl[(koPathToTemplatesFolder.size()+2)..(-koTemplateExtn.size()-2)]
			String escapedKoTmplString = "<script type='text/html' id='${pathRelativeToTemplatesFolder}'>${koFile?.text}</script>".encodeAsJavaScript()
			jsFile.write("\$('body').append('${escapedKoTmplString}');")
			
            resource.processedFile = jsFile
            resource.contentType = 'text/javascript'
            resource.updateActualUrlFromProcessedFile()
        } catch (Exception e) {
            log.error("Error Converting Ko-Template file [${koFile}]", e)
        }
    }

}
