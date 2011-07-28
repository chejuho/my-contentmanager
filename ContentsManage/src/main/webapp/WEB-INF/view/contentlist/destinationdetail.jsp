<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="配布先登録" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
			function returnList() {
				var form = document.forms[0];
				form.action="index";
				form.submit();
			}
		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">
		<s:form>
		<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th align="center">
						コンテンツ名
					</th>
					<td >

						<c:forEach var="conent" items="${conents}" varStatus="i">
							<c:if test="${selected == conent.id}">
								${f:h(conent.content_name)}
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th align="center">
						配布先名
					</th>
					<td >
						${f:h(form.destinationName)}
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2" bgcolor="#F5DEB3">
						開発環境(dev)
					</td>
				</tr>
				<tr>
					<th align="center">
						URL
					</th>
					<td >
						jdbc:mysql://${f:h(form.devUrl)}
					</td>
				</tr>
				<tr>
					<th align="center">
						ID
					</th>
					<td >
						${f:h(form.devId)}
					</td>
				</tr>
				<tr>
					<th align="center">
						PASSWORD
					</th>
					<td >
						${f:h(form.devPassword)}
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2"  bgcolor="#1E90FF">
						編集環境(stg)
					</td>
				</tr>
				<tr>
					<th align="center">
						URL
					</th>
					<td >
						jdbc:mysql://${f:h(form.stgUrl)}
					</td>
				</tr>
				<tr>
					<th align="center">
						ID
					</th>
					<td >
						${f:h(form.stgId)}
					</td>
				</tr>
				<tr>
					<th align="center">
						PASSWORD
					</th>
					<td >
						${f:h(form.stgPassword)}
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2"  bgcolor="#DC143C">
						配信環境(pub)
					</td>
				</tr>
				<tr>
					<th align="center">
						URL
					</th>
					<td >
						jdbc:mysql://${f:h(form.pubUrl)}
					</td>
				</tr>
				<tr>
					<th align="center">
						ID
					</th>
					<td >
						${f:h(form.pubId)}
					</td>
				</tr>
				<tr>
					<th align="center">
						PASSWORD
					</th>
					<td >
						${f:h(form.pubPassword)}
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit"  value = "確認" style="font-size=20px;"  onclick="returnList();"/>

					</td>
				</tr>
		</table>
		</s:form>
	</tiles:put>
</tiles:insert>