<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/table_layout.jsp" flush="true">
	<tiles:put name="title" value="データパブリッシュ画面" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
		//パブリッシュ確認
		function publishconfirm() {
			var form = document.forms[0];
			form.action="publishconfirm";
			form.submit();

		}

		function returnList() {
			var form = document.forms[0];

			<c:if test="${empty search}">
				form.action="detail/${tableid}/${pageNum}";
			</c:if>
			<c:if test="${!empty search}">
				form.action="searchdetail/${tableid}/${pageNum}";
			</c:if>
			form.submit();
		}
		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">
	<input type="hidden" name ="pageNum"  value="${pageNum}"/>
	<input type="hidden" name = "tableid"  value="${tableid}"/>

	<c:forEach var="publishid" items="${publishidList}" varStatus="i">
		<input type="hidden" name="publishidList" value="${publishid}"  />
	</c:forEach>

	<table width="31050" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			パブリッシュ
		</td>
	</tr>

	<tr>
		<td>
			<table id="table"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<c:forEach var="columnInfo" items="${columnList}" varStatus="i">
					<th align="center" >
						<c:out value="${columnInfo.logical_name}"/>(<c:out value="${columnInfo.physical_name}"/>)&nbsp;
					</th>
				</c:forEach>
			</tr>
			<c:choose>
			<c:when test="${!empty dataList}">
				<c:forEach var="datas" items="${dataList}" varStatus="i">
				<tr>
					<c:forEach var="data" items="${datas}" varStatus="i">
						<td align="center" >

							<c:out value="${f:h(data.value)}"/>&nbsp;
						</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</c:when>
			</c:choose>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
	</table>
		<c:if test="${selectedEv == 'dev'}">
			<select name="publishEv">
				<option selected value="2">編集環境(stg)</option>
				<option value="3">配信環境(pub)</option>
			</select>
		</c:if>
		<c:if test="${selectedEv == 'stg'}">
			<select name="publishEv">
				<option value="1">開発環境(dev)</option>
				<option selected value="3">配信環境(pub)</option>
			</select>
		</c:if>
		<c:if test="${selectedEv == 'pub'}">
			<select name="publishEv">
				<option selected value="1">開発環境(dev)</option>
				<option value="2">編集環境(stg)</option>
			</select>
		</c:if>
		<input type="submit"  value = "パブリッシュ確認" onclick="return publishconfirm();"/>
		<input type="submit"  value = "キャンセル" onclick="returnList();"/>
	</tiles:put>
</tiles:insert>