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
<script src="${f:url('/js/jquery-1.3.2.min.js')}"></script>
<script src="${f:url('/js/common.js')}" type="text/javascript" ></script>
<title><tiles:getAsString name="title" /></title>
<tiles:insert attribute="javascripctInput" />
</head>
<body>
<s:form onsubmit="return false;">
	<input type="hidden" name ="selectDestinationId"  value="${selectDestinationId}"/>
	<input type="hidden" name ="selectedEv"  value="${selectedEv}"/>
	<table width="100%" border="0">
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
							<s:link styleClass="dev" href="changeEv/${selectDestinationId}/dev/${tableid}">開発環境(dev)</s:link>
		  				</li>
						<li>
							<s:link href="changeEv/${selectDestinationId}/stg/${tableid}">編集環境(stg)</s:link>
		  				</li>
		  				<li>
		  					<s:link href="changeEv/${selectDestinationId}/pub/${tableid}">配信環境(pub)</s:link>
		  				</li>
				    </c:if>
				    <c:if test="${selectedEv == 'stg'}">
						<li>
							<s:link href="changeEv/${selectDestinationId}/dev/${tableid}">開発環境(dev)</s:link>
		  				</li>
						<li>
							<s:link styleClass="stg" href="changeEv/${selectDestinationId}/stg/${tableid}">編集環境(stg)</s:link>
		  				</li>
		  				<li>
		  					<s:link href="changeEv/${selectDestinationId}/pub/${tableid}">配信環境(pub)</s:link>
		  				</li>
				    </c:if>
				    <c:if test="${selectedEv == 'pub'}">
						<li>
							<s:link href="changeEv/${selectDestinationId}/dev/${tableid}">開発環境(dev)</s:link>
		  				</li>
						<li>
							<s:link href="changeEv/${selectDestinationId}/stg/${tableid}">編集環境(stg)</s:link>
		  				</li>
		  				<li>
		  					<s:link styleClass="pub" href="changeEv/${selectDestinationId}/pub/${tableid}">配信環境(pub)</s:link>
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