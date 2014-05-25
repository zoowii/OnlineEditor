package onlineeditor

import grails.transaction.Transactional
import org.markdown4j.Markdown4jProcessor

@Transactional
class MarkDownService {

    def static markdownProcessor = new Markdown4jProcessor()

    def renderMarkDown(String markDownText) {
        return markdownProcessor.process(markDownText)
    }
}
