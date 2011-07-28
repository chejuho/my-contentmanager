<tiles:insert template="/WEB-INF/view/common/table_layout_multipart.jsp" flush="true">
	<tiles:put name="title" value="データ登録画面" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">

			function importcsvdata() {
				var form = document.forms[0];
				form.action="importcsvdata";
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
	<input type="hidden" name = "tableid" value="${tableid}"/>
	<table width="650" align="left" cellpadding="0" cellspacing="0" id="inputtable">
	<tr>

		<th class="top" colspan="2" >
			${f:h(tableInfo_display.logical_name)}(${f:h(tableInfo_display.physical_name)})
		</th>
	</tr>
	<tr>
		<th width="50%"  align="left">
        				CSVファイル
        		</th>
     			<td width="50%" bgcolor="#FFFFFF" >
     				<input type="file" name="csvFormFile" STYLE="FONT-SIZE:9pt" SIZE="35"/>
     			</td>
	</tr>
	<tr>
		<td class="btn" colspan="2"  align="center">
			<input type="submit"  value="インポート" style="font-size=20px;"  onclick="importcsvdata();" />
			<input type="submit"  value = "キャンセル" style="font-size=20px;"  onclick="returnList();"/>
		</td>
	</tr>
	</table>
	</tiles:put>
</tiles:insert>
