<%@page pageEncoding="UTF-8"%>
<html>
<head>

<!--  <link rel="stylesheet" type="text/css" href="${initParam.context}/css/basic.css" />
<script src="${initParam.context}/js/common.js"  type="text/javascript" ></script>-->

<link rel="stylesheet" type="text/css" href="${f:url('/css/basic.css')}"/>
<script src="${f:url('/js/common.js')}"  type="text/javascript" ></script>
<title><tiles:getAsString name="title" /></title>
<tiles:insert attribute="javascripctInput" />
</head>
<body>
<table width="950" >
  <tr>
  	<td align="right"><tiles:insert attribute="header"/></td>
  </tr>
  <tr>
  	<td>
  		<html:errors/>
		<html:messages id="m" message="true">
			${f:h(m)}<br />
		</html:messages>
  	</td>
  </tr>
  <tr>
    <td><tiles:insert attribute="content" /></td>
  </tr>
</table>
</body>
</html>