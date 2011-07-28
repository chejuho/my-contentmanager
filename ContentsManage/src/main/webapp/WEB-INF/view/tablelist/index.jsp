<tiles:insert template="/WEB-INF/view/common/table_layout.jsp" flush="true">
	<tiles:put name="title" value="配布先編集" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
			function returnList() {
				var form = document.forms[0];
				form.action="index";
				form.submit();
			}
			//CSVファイルをエクスポート
			function csvexport(tableid) {

				if(window.confirm('本当すべてのデータをエクスポートしますか？')){

					var form = document.forms[0];
					form.exporttableid.value = tableid;
					form.action="allexport";
					form.submit();
				}
				return;

			}
		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">
		<input type="hidden" name = "exporttableid" />
		<table width="850"  align="left" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table id="table" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="50%" align="center" ><span class="style8">テーブル名</span></th>
					<!-- 性能のため使わない<th width="20%" align="center" ><span class="style8">レコード数</span></th>-->
					<th width="30%" align="center" ><span class="style8">csvファイルエクスポート</span></th>
				</tr>
				<!--テーブル情報表示 -->
				<c:choose>
				<c:when test="${!empty tableInfoList}">
					<c:forEach var="tableInfo" items="${tableInfoList}" varStatus="i">
					<tr>
						<td height="52" class="left" >
							<s:link  href="${selectDestinationId}/${selectedEv}/detail/${tableInfo.tableid}/init" styleClass="list">
								<c:out value="${tableInfo.tableLogicName}"/>&nbsp;<c:out value="(${tableInfo.tablename})"/>&nbsp;
							</s:link>

						</td>
						<!-- 性能のため使わない
						<td align="left">
							<c:out value="${tableInfo.tableCnt}"/></a>&nbsp;
						</td>-->

						<td align="center">
							<button type="button" onclick="csvexport('${tableInfo.tableid}')">
								エクスポート
							</button>
						</td>
					</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td width="100%" align="center">
						</td>
					</tr>
				</c:otherwise>
				</c:choose>
				</table>
			</td>
		</tr>
		<tr>
			<td align="center">&nbsp;</td>
		</tr>
		</table>
	</tiles:put>
</tiles:insert>
