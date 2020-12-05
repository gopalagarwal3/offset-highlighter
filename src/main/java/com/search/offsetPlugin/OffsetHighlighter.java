package com.search.offsetPlugin;

import org.apache.lucene.search.uhighlight.PassageFormatter;
import org.apache.lucene.search.uhighlight.UnifiedHighlighter;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.highlight.UnifiedSolrHighlighter;
import org.apache.solr.request.SolrQueryRequest;

public class OffsetHighlighter extends UnifiedSolrHighlighter {

/**
* Creates an instance of the Lucene {@link UnifiedHighlighter}. Provided for subclass extension so that
* a subclass can return a subclass of {@link SolrExtendedUnifiedHighlighter}.
*/
protected UnifiedHighlighter getHighlighter(SolrQueryRequest req) {
return new OffsetSolrExtendedUnifiedHighlighter(req);
}

/**
   * From {@link #getHighlighter(org.apache.solr.request.SolrQueryRequest)}.
   */
  protected static class OffsetSolrExtendedUnifiedHighlighter extends SolrExtendedUnifiedHighlighter {

    public OffsetSolrExtendedUnifiedHighlighter(SolrQueryRequest req) {
      super(req);
    }

    @Override
    protected PassageFormatter getFormatter(String fieldName) {
      String preTag = params.getFieldParam(fieldName, HighlightParams.TAG_PRE,
          params.getFieldParam(fieldName, HighlightParams.SIMPLE_PRE, "<em>")
      );

      String postTag = params.getFieldParam(fieldName, HighlightParams.TAG_POST,
          params.getFieldParam(fieldName, HighlightParams.SIMPLE_POST, "</em>")
      );
      String ellipsis = params.getFieldParam(fieldName, HighlightParams.TAG_ELLIPSIS, SNIPPET_SEPARATOR);
      String encoder = params.getFieldParam(fieldName, HighlightParams.ENCODER, "simple");
      return new OffsetPassageFormatter(preTag, postTag, ellipsis, "html".equals(encoder));
    }

  }
}
  
  
  
  
