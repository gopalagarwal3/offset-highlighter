# Unified Highlighter - Offset Plugin

Repos for plugin:

ES: [https://github.com/gopalagarwal3/es-offset-highlighter](https://github.com/gopalagarwal3/es-offset-highlighter)

SOLR: [https://github.com/gopalagarwal3/offset-highlighter](https://github.com/gopalagarwal3/offset-highlighter)

Test UI: [https://github.com/gopalagarwal3/search-highlights](https://github.com/gopalagarwal3/search-highlights)

SOLR changes:

<searchComponent class="solr.HighlightComponent" name="highlight">
<highlighting class="com.search.offsetPlugin.OffsetHighlighter">
</highlighting>
</searchComponent>

Start ES:

elasticsearch-7.6.2>bin\elasticsearch.bat

Download ES

`bin\elasticsearch-plugin install [file:///D:/work/eclipse-workspace/offset-highlighter/target/releases/offset-highlighter-0.0.1-SNAPSHOT.zip](file:///D:/work/eclipse-workspace/offset-highlighter/target/releases/offset-highlighter-0.0.1-SNAPSHOT.zip)`

`bin\elasticsearch-plugin remove offset-highlighter`

Followed Tutorial:

[How to create an Elasticsearch 6.4.1 Plugin – TechnocratSid Blogs](http://www.technocratsid.com/how-to-create-an-elasticsearch-6-4-1-plugin/)

SOLR Plugin changes:

Before:

[http://localhost:8983/solr/techproducts/select?qf=content_light_tv&hl=on&q=learn&hl.snippets=1&defType=edismax&hl.fragsize=100&hl.method=unified](http://localhost:8983/solr/techproducts/select?qf=content_light_tv&hl=on&q=learn&hl.snippets=1&defType=edismax&hl.fragsize=100&hl.method=unified)

```
"highlighting":{
    "doc_1":{
      "content_light_tv":["The easiest way to <em>learn</em> how to use Streamlit is to try things out yourself. As you read through this guide, test each method. "]}}}
```

After:

[http://localhost:8983/solr/techproducts/offset?qf=content_light_tv&hl=on&q=learn&hl.snippets=1&defType=edismax&hl.fragsize=100](http://localhost:8983/solr/techproducts/offset?qf=content_light_tv&hl=on&q=learn&hl.snippets=1&defType=edismax&hl.fragsize=100)

```
 "highlighting":{
    "doc_1":{
      "content_light_tv":["<offset start=\"0\" end=\"127\">The easiest way to <keyword start=\"19\" end=\"24\"/><em>learn</em> how to use Streamlit is to try things out yourself. As you read through this guide, test each method. </offset>"]}}}
```

ES Plugin:

ES query:

test-offset/_search

```json
{
  "query": {
    "query_string": {
        "query": "can"
    }
  },
  "highlight": {
    "fields": {
      "content": {
        "type": "offset",
        "require_field_match": false
      }
    },
    "fragment_size": 50
  }
}
```

Before:

```json
"_source": {
"content": "Fundamental to the internals of highlighting are detecting the offsets of the individual words that match the query. Some of the highlighters can run the stored text through the analysis chain defined in the schema, some can look them up from postings, and some can look them up from term vectors."
},
"highlight": {
"content": [
"Some of the highlighters <em>can</em> run the stored text through",
"the analysis chain defined in the schema, some <em>can</em>",
"look them up from postings, and some <em>can</em> look them"]
}
```

After:

```json
"_source": {
"content": "Fundamental to the internals of highlighting are detecting the offsets of the individual words that match the query. Some of the highlighters can run the stored text through the analysis chain defined in the schema, some can look them up from postings, and some can look them up from term vectors."
},
"highlight": {
"content": [
"<offset start=\"117\" end=\"173\">Some of the highlighters <keyword start=\"142\" end=\"145\"/><b>can</b> run the stored text through</offset>",
"<offset start=\"173\" end=\"224\"> the analysis chain defined in the schema, some <keyword start=\"221\" end=\"224\"/><b>can</b></offset>",
"<offset start=\"224\" end=\"275\"> look them up from postings, and some <keyword start=\"262\" end=\"265\"/><b>can</b> look them</offset>"]
}
```