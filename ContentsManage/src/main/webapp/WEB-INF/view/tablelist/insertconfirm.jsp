<tiles:insert template="/WEB-INF/view/common/table_layout.jsp" flush="true">
	<tiles:put name="title" value="データ登録確認画面" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">

			function regist() {
				var form = document.forms[0];
				form.action="insert";
				form.submit();
			}
			function returnList() {
				var form = document.forms[0];
				form.action="insertmodoru";
				form.submit();
			}

		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

	<input type="hidden" name ="pageNum"  value="${pageNum}"/>
	<input type="hidden" name = "tableid" value="${tableid}"/>
	<table width="100%" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table width="80%" id="inputtable">
			<tr>
				<th class="top" colspan="2" >
					${f:h(tableInfo_display.logical_name)}(${f:h(tableInfo_display.physical_name)})
				</th>
			</tr>
			<c:forEach var="column" items="${columnList}" varStatus="i">
			<c:if test="${column.autoincrement_sign == 0}">
			<tr>

				<th width="30%"  align="left">
					<input type="hidden" name="${column.physical_name}" value="${f:h(dataMap[column.physical_name])}"  />
					<c:out value="${column.logical_name}"/>(<c:out value="${column.physical_name}"/>)&nbsp;
				</th>
				<td width="70%" bgcolor="#FFFFFF" >
					${f:h(dataMap[column.physical_name])}
				</td>

			</tr>
			</c:if>
			<c:if test="${column.autoincrement_sign == 1}">
			<tr>

				<th width="30%"  align="left">
					<c:out value="${column.logical_name}"/>(<c:out value="${column.physical_name}"/>)&nbsp;
				</th>
				<td width="70%" bgcolor="#FFFFFF" >
					自動設定
				</td>

			</tr>
			</c:if>
			</c:forEach>
			<tr>
				<td class="btn" colspan="2"  align="center">
					<input type="submit"  value="登録" style="font-size=20px;"  onclick="regist();" />
					<input type="submit"  value = "戻る" style="font-size=20px;"  onclick="returnList();"/>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
	</tiles:put>
</tiles:insert>
