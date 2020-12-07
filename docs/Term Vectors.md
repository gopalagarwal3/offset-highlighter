# Term Vectors

original solr server state/data:

![Term%20Vectors%201243fc495ca44f83b1ea51e1f66ecdcf/Untitled.png](Term%20Vectors%201243fc495ca44f83b1ea51e1f66ecdcf/Untitled.png)

```xml
  <!-- Configure the standard formatter -->

```

```xml
bin/solr start -c -Denable.packages=true
bin/solr package list-installed
bin/solr package list-available
bin/solr package add-repo osc [https://raw.githubusercontent.com/o19s/payload-component/master/repo](https://raw.githubusercontent.com/o19s/payload-component/master/repo)
bin/solr package install solr-payloads:1.1.4
bin/solr package list-installed

http://localhost:8983/api/cluster/package?omitHeader=true
```

```xml
bin/solr create -c techproducts
```

```xml
bin/solr package deploy solr-payloads:1.1.4 -collections techproducts
```

 refer to: https://lucene.apache.org/solr/guide/8_7/package-manager-internals.html
https://lucene.apache.org/solr/guide/8_6/command-line-utilities.html

change in the solrconfig.xml:

```xml
Zookeper command:
```

```xml
./server/scripts/cloud-scripts/zkcli.sh -cmd upconfig -confdir server/solr/configsets/_default/conf -confname techproducts -z 127.0.0.1:9983
```

```xml
<!-- <formatter name="html"
default="true"
class="solr.highlight.HtmlFormatter">
<lst name="defaults">
<str name="hl.simple.pre"><![CDATA[<em>]]></str>
<str name="hl.simple.post"><![CDATA[</em>]]></str>
</lst>
</formatter> -->
```

```xml
<!-- Configure the standard formatter -->
<formatter name="html"
default="true"
class="**solr-payloads**:com.o19s.hl.OffsetFormatter">
</formatter>
```

reload the collection

Add the field:

![Term%20Vectors%201243fc495ca44f83b1ea51e1f66ecdcf/Untitled%201.png](Term%20Vectors%201243fc495ca44f83b1ea51e1f66ecdcf/Untitled%201.png)

change the xml file to have content field and data in it

bin/post -c techproducts example/exampledocs/manufacturers.xml

[http://localhost:8983/solr/techproducts/select?hl.fl=content&hl=on&q=content%3Aca&hl.snippets=10](http://localhost:8983/solr/techproducts/select?hl.fl=content&hl=on&q=content%3Aca&hl.snippets=10)

Kill:
Delete the core from the SOLR ADMIN UI
And bin/solr stop -all

```xml
<!--
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

<add>
  <doc>
    <field name="id">adata</field>
    <field name="compName_s">A-Data Technology</field>
    <field name="content">12045 E. Waterfront Drive Playa Vista, CA 90094 46221 Landing Parkway Fremont, CA 94538 33 Commerce Valley Drive East Thornhill, ca ON L3T 7N6 Canada 800 Corporate Way Fremont, CA 94539</field>
  </doc>
  <doc>
    <field name="id">apple</field>
    <field name="compName_s">Apple</field>
    <field name="content">1 Infinite Way, Cupertino CA</field>
  </doc>
  <doc>
    <field name="id">asus</field>
    <field name="compName_s">ASUS Computer</field>
    <field name="content">800 Corporate Way Fremont, CA 94539</field>
  </doc>
  <doc>
    <field name="id">ati</field>
    <field name="compName_s">ATI Technologies</field>
    <field name="content">33 Commerce Valley Drive East Thornhill, ON L3T 7N6 Canada</field>
  </doc>
  <doc>
    <field name="id">belkin</field>
    <field name="compName_s">Belkin</field>
    <field name="content">12045 E. Waterfront Drive Playa Vista, CA 90094</field>
  </doc>
  <doc>
    <field name="id">canon</field>
    <field name="compName_s">Canon, Inc.</field>
    <field name="content">One Canon Plaza Lake Success, NY 11042</field>
  </doc>
  <doc>
    <field name="id">corsair</field>
    <field name="compName_s">Corsair Microsystems</field>
    <field name="content">46221 Landing Parkway Fremont, CA 94538</field>
  </doc>
  <doc>
    <field name="id">dell</field>
    <field name="compName_s">Dell, Inc.</field>
    <field name="content">One Dell Way Round Rock, Texas 78682</field>
  </doc>
  <doc>
    <field name="id">maxtor</field>
    <field name="compName_s">Maxtor Corporation</field>
    <field name="content">920 Disc Drive Scotts Valley, CA 95066</field>
  </doc>
  <doc>
    <field name="id">samsung</field>
    <field name="compName_s">Samsung Electronics Co. Ltd.</field>
    <field name="content">105 Challenger Rd. Ridgefield Park, NJ 07660-0511</field>
  </doc>
  <doc>
    <field name="id">viewsonic</field>
    <field name="compName_s">ViewSonic Corp</field>
    <field name="content">381 Brea Canyon Road Walnut, CA 91789-0708</field>
  </doc>
</add>
```

[Plugin](Term%20Vectors%201243fc495ca44f83b1ea51e1f66ecdcf/Plugin%20a11d39a1d44b4d7bb58f7f6387a8dc49.md)