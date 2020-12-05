# offset-highlighter
 Offset highlighter
 
# changes needed in the solrconfig.xml
  <searchComponent class="solr.HighlightComponent" name="highlight">
    <highlighting class="com.search.offsetPlugin.OffsetHighlighter">
    </highlighting>
  </searchComponent>


