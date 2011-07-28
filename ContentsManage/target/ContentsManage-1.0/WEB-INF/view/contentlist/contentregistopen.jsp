<%@page pageEncoding="UTF-8"%>

<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="コンテンツ登録" />
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

		<s:form action="contentRegist" enctype="multipart/form-data">
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
							<input type="submit" name="regist" value="登録" style="font-size=20px;" />
							<input type="submit"  value = "キャンセル" style="font-size=20px;"  onclick="returnList();"/>

						</td>
					</tr>
			</table>
		</s:form>
	</tiles:put>
</tiles:insert>