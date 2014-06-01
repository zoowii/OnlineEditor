package onlineeditor

import com.zoowii.online_editor.orm.Query
import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

@Transactional
class BucketService {

    def findFilesByPaginator(Bucket bucket, Account user, Paginator paginator) {
        def query = new Query(CloudFile)
        query = query.eq('bucket', bucket).eq('author', user).desc('createdTime')
                .offset(paginator.getOffset()).limit(paginator.pageSize)
        paginator.setTotalCount(CloudFile.countByAuthorAndBucket(user, bucket))
        return query.all()
    }
}
