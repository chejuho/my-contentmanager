<%@page pageEncoding="UTF-8"%>

<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="コンテンツ編集" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
			function returnList() {
				var form = document.forms[0];
				form.action="contentManageOpen";
				form.submit();
			}
		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

		<s:form action="contentEdit" enctype="multipart/form-data">
			<input type="hidden"  name = "editContentId"  value="${editContentId}" />
			<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th align="center">
							コンテンツ名
						</th>
						<td >
							<input type="text" name="contentsname" value="${contentsname}"  style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							ERMファイル
						</th>
						<td >
							<input type="file" name="ermFile" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" name="edit" value="編集" style="font-size=20px;" />
							<input type="submit"  value = "キャンセル" style="font-size=20px;"  onclick="returnList();"/>

						</td>
					</tr>
			</table>
		</s:form>
	</tiles:put>
</tiles:insert>