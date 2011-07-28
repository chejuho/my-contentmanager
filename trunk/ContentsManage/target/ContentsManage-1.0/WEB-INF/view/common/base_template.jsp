<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <title><tiles:getAsString name="title" /></title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" type="text/css" href="${f:url('/css/base_template.css')}" />
</head>
<body>
  <div id="headerContainer">
    <div id="PageTitle">

    </div>
    <div id="LoginContainer">
		<tiles:insert page="/common/header.jsp" />
    </div>
  </div>
  <hr class="Spacer"/>
  <div id="mainContainer">
    <div id="BodyContainer">
      <tiles:insert attribute="BodyContent" />
    </div>
  </div>
</body>
</html>