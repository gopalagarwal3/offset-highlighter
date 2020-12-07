# Plugin

start solr normally [7.4]

`bin/solr start`

create a techproducts with following:

bin/solr create -c techproducts

conf and data is stored in the following folder without cloud mode: `solr\server\solr\techproducts`

Solrconfig changes:

<updateRequestProcessorChain name="add-unknown-fields-to-the-schema" default="${update.**autoCreateFields:false**}

 Schema changes:

<field name="content_full_tv" type="text_general" indexed="true" stored="true" required="true" multiValued="false" termVectors="true" **termPositions**="true" **termOffsets**="true" />
<field name="content_light_tv" type="text_general" indexed="true" stored="true" required="true" multiValued="false" **storeOffsetsWithPositions**="true" termVectors="true" />

make each other ass copy field

index this content:

[
{
"id" : "978-0641723445",
"content_light_tv" : "Fundamental to the internals of highlighting are detecting the offsets of the individual words that match the query. Some of the highlighters can run the stored text through the analysis chain defined in the schema, some can look them up from postings, and some can look them up from term vectors.",
}
]

Highlight query:

[http://localhost:8983/solr/techproducts/select?qf=content_light_tv+content_full_tv&hl.fl=content_light_tv+content_full_tv&hl=on&q=schema&hl.snippets=100&defType=edismax](http://localhost:8983/solr/techproducts/select?qf=content_light_tv+content_full_tv&hl.fl=content_light_tv+content_full_tv&hl=on&q=schema&hl.snippets=100&defType=edismax)

Term vector API query:

On full tv field:

[http://localhost:8983/solr/techproducts/tvrh?q=*:*&fq=id:"978-0641723445"&rows=1&indent=true&tv=true&tv.all=true&tv.fl=content_full_tv&fl=id](http://localhost:8983/solr/techproducts/tvrh?q=*:*&fq=id:%22978-0641723445%22&rows=1&indent=true&tv=true&tv.all=true&tv.fl=content_full_tv&fl=id)

Output:

```
        "term",[
          "tf",1,
          "positions",[
            "position",49],
          "offsets",[
            "start",284,
            "end",288],
          "df",1,
          "tf-idf",1.0],
```

On light tv field:

[http://localhost:8983/solr/techproducts/tvrh?q=*:*&fq=id:"978-0641723445"&rows=1&indent=true&tv=true&tv.all=true&tv.fl=content_light_tv&fl=id](http://localhost:8983/solr/techproducts/tvrh?q=*:*&fq=id:%22978-0641723445%22&rows=1&indent=true&tv=true&tv.all=true&tv.fl=content_light_tv&fl=id)

Output:

```
"warnings",[
      "noPositions",["content_light_tv"],
      "noOffsets",["content_light_tv"],
      "noPayloads",["content_light_tv"]],
```

Add the [payload component jar](https://github.com/o19s/payload-component/releases/download/v1.1.4solr8.6.0/payload-component-v1.1.4solr8.6.0.jar) to `solr\contrib\offset\lib`  path

and add the following to solrconfig:

```xml
<lib dir="${solr.install.dir:../../../..}/contrib/offset/lib" regex=".*\.jar" />
```

```xml
<!-- Configure the standard formatter -->
<formatter name="html"
default="true"
class="com.o19s.hl.OffsetFormatter">
</formatter>
```

Reload the collection and fire the following query for original highlighter:

[http://localhost:8983/solr/techproducts/select?qf=content_light_tv+content_full_tv&hl.fl=content_light_tv+content_full_tv&hl=on&q=schema&hl.snippets=100&defType=edismax](http://localhost:8983/solr/techproducts/select?qf=content_light_tv+content_full_tv&hl.fl=content_light_tv+content_full_tv&hl=on&q=schema&hl.snippets=100&defType=edismax)

```
"highlighting":{
    "978-0641723445":{
      "content_light_tv":[" the <em data-num-tokens=\"1\" data-score=\"1.0\" data-end-offset=\"214\" data-start-offset=\"208\">schema</em>, some can look them up from postings, and some can look them up from term vectors."],
      "content_full_tv":[" the <em data-num-tokens=\"1\" data-score=\"1.0\" data-end-offset=\"214\" data-start-offset=\"208\">schema</em>, some can look them up from postings, and some can look them up from term vectors."]}}}
```

The same plugin does not work for unified [does not break it].

Objective:

Compile the jar using java classes

Test the jar

[Unified Highlighter - Offset Plugin](Plugin%20a11d39a1d44b4d7bb58f7f6387a8dc49/Unified%20Highlighter%20-%20Offset%20Plugin%2054215779f2814d18a068ca6d2529e091.md)