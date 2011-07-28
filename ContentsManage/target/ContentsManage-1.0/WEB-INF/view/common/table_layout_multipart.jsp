<%@page pageEncoding="UTF-8"%>
<html>
<head>
<!--
	<c:if test="${selectedEv == 'dev'}">
		<link rel="stylesheet" type="text/css" href="${initParam.context}/css/dev.css" />
	</c:if>
	<c:if test="${selectedEv == 'stg'}">
		<link rel="stylesheet" type="text/css" href="${initParam.context}/css/stg.css" />
	</c:if>
	<c:if test="${selectedEv == 'pub'}">
		<link rel="stylesheet" type="text/css" href="${initParam.context}/css/pub.css" />
	</c:if>
 -->
 	<c:if test="${selectedEv == 'dev'}">
		<link rel="stylesheet" type="text/css" href="${f:url('/css/dev.css')}" />
	</c:if>
	<c:if test="${selectedEv == 'stg'}">
		<link rel="stylesheet" type="text/css" href="${f:url('/css/stg.css')}" />
	</c:if>
	<c:if test="${selectedEv == 'pub'}">
		<link rel="stylesheet" type="text/css" href="${f:url('/css/pub.css')}" />
	</c:if>
<link rel="stylesheet" type="text/css" href="${f:url('/css/common.css')}" />
<script src=${initParam.context}/js/common.js"  type="text/javascript" ></script>

<title><tiles:getAsString name="title" /></title>
<tiles:insert attribute="javascripctInput" />
</head>
<body>
<s:form enctype="multipart/form-data">
	<input type="hidden" name ="selectDestinationId"  value="${selectDestinationId}"/>
	<input type="hidden" name ="selectedEv"  value="${selectedEv}"/>
	<table width="950" >
	  <tr>
	  	<td align="left"><tiles:insert attribute="header"/></td>
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
	  	<td>
	  		<div id="globalnavi">
				<ul>
					<c:if test="${selectedEv == 'dev'}">
						<li>
	  						<a class="dev" href="changeEv?selectedEv=dev" >開発環境(dev)</a>
		  				</li>
						<li>
	  						<a href="changeEv?selectedEv=stg">編集環境(stg)</a>
		  				</li>
		  				<li>
		  					<a href="changeEv?selectedEv=pub">配信環境(pub)</a>
		  				</li>
				    </c:if>
				    <c:if test="${selectedEv == 'stg'}">
						<li>
	  						<a href="changeEv?selectedEv=dev" >開発環境(dev)</a>
		  				</li>
						<li>
	  						<a  class="stg" href="changeEv?selectedEv=stg">編集環境(stg)</a>
		  				</li>
		  				<li>
		  					<a href="changeEv?selectedEv=pub">配信環境(pub)</a>
		  				</li>
				    </c:if>
				    <c:if test="${selectedEv == 'pub'}">
						<li>
	  						<a href="changeEv?selectedEv=dev" >開発環境(dev)</a>
		  				</li>
						<li>
	  						<a href="changeEv?selectedEv=stg">編集環境(stg)</a>
		  				</li>
		  				<li>
		  					<a class="pub"  href="changeEv?selectedEv=pub">配信環境(pub)</a>
		  				</li>
				    </c:if>
				   	<li>
	  					<a href="${f:url('/contentlist')}"><span>コンテンツ一覧へ</span></a>
		  			</li>
				</ul>
			</div>
	  	</td>
	  </tr>
	  <tr>
	    <td><tiles:insert attribute="content" /></td>
	  </tr>
	</table>
</s:form>
</body>
</html>