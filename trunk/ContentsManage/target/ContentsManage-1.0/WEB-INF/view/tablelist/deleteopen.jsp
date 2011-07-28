<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/table_layout.jsp" flush="true">
	<tiles:put name="title" value="データ削除確認画面" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
		//削除
		function deleteData() {


			if(window.confirm('本当に削除しますか？')){

				var form = document.forms[0];
				form.action="delete";
				form.submit();
			} else {
				return false;
			}


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
	<input type="hidden" name = "tableid" value="${tableid}"/>
	<c:forEach var="deleteid" items="${deleteList}" varStatus="i">
		<input type="hidden" name="deleteList" value="${deleteid}"  />
	</c:forEach>
	<table width="31050" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			削除確認
		</td>
	</tr>

	<tr>
		<td>
			<table id="table"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<c:forEach var="columnInfo" items="${columnList}" varStatus="i">
					<th align="center" >
						${f:h(columnInfo.logical_name)}(${f:h(columnInfo.physical_name)})&nbsp;
					</th>
				</c:forEach>
			</tr>
			<c:choose>
			<c:when test="${!empty dataList}">
				<c:forEach var="datas" items="${dataList}" varStatus="i">

				<tr>
					<c:forEach var="data" items="${datas}" varStatus="i">
						<td align="center" >
							<c:out value="${data.value}"/>&nbsp;
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
		<input type="submit"  value = "削除" onclick="return deleteData();"/>
		<input type="submit"  value = "キャンセル" onclick="returnList();"/>
	</tiles:put>
</tiles:insert>