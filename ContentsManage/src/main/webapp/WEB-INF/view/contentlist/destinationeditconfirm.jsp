<%@page pageEncoding="UTF-8"%>

<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="配布先更新確認" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
			function returnList() {
				var form = document.forms[0];
				form.action="destinationEditOpen";
				form.submit();
			}
		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

		<s:form action="destinationEdit" >
			<input type="hidden"  name = "editDestinationId"  value="${editDestinationId}" />
			<input type="hidden" name="form.contentid"  value="${f:h(form.contentid)}"  />
			<input type="hidden" name="form.destinationName"  value="${f:h(form.destinationName)}"  />
			<input type="hidden" name="form.devUrl"  value="${f:h(form.devUrl)}"  />
			<input type="hidden" name="form.devId"  value="${f:h(form.devId)}"  />
			<input type="hidden" name="form.devPassword"  value="${f:h(form.devPassword)}"  />
			<input type="hidden" name="form.stgUrl"  value="${f:h(form.stgUrl)}"  />
			<input type="hidden" name="form.stgId"  value="${f:h(form.stgId)}"  />
			<input type="hidden" name="form.stgPassword"  value="${f:h(form.stgPassword)}"  />
			<input type="hidden" name="form.pubUrl"  value="${f:h(form.pubUrl)}"  />
			<input type="hidden" name="form.pubId"  value="${f:h(form.pubId)}"  />
			<input type="hidden" name="form.pubPassword"  value="${f:h(form.pubPassword)}"  />

			<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th align="center">
							コンテンツ名
						</th>
						<td >
							${f:h(form.contentname)}
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
							<input type="submit" name="edit" value="更新" style="font-size=20px;" />
							<input type="submit"  value = "キャンセル" style="font-size=20px;"  onclick="returnList();"/>

						</td>
					</tr>
			</table>
		</s:form>
	</tiles:put>
</tiles:insert>