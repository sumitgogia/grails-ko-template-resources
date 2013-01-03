grails-ko-template-resources
================================

Grails Plugin to enable processing of knockout templates as grails resources. Hence benefit from various features of the resources plugin e.g. bundling, caching etc.

Works best with page-resource plugin.

E.g.
* Keep all knockout templates under the web-app/pages folder. Organize them under sub-folders as necessary.
* All templates will be converted to .js files and auto bundled.

Defaults that can be changed via Config - 
grails.plugins.knockoutTemplateResources.pathToTemplatesFolder = "/pages"
grails.plugins.knockoutTemplateResources.templateExtension = "html"
grails.resources.mappers.kotemplateresource.includes = ['pages/**/*.html']
