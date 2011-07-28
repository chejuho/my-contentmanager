<%@page pageEncoding="UTF-8"%>

<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="配布先編集" />
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

		<s:form action="destinationRegistConfirm" >
			<input type="hidden"  name = "selected"  value="${selected}" />
			<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th align="center">
							コンテンツ名
						</th>
						<td >
							<select style="width: 300px; " name = "form.contentid">
								<c:if test="${selected == 'main'}">
				            		<option value = "main" selected>選択したください。</option>
				            	</c:if>
								<c:forEach var="conent" items="${conents}" varStatus="i">
									<c:if test="${selected == conent.id}">
										<option value = "${f:h(conent.id)}" selected>${f:h(conent.content_name)}</option>
									</c:if>
									<c:if test="${selected != conent.id}">
										<option value = "${f:h(conent.id)}">${f:h(conent.content_name)}</option>
									</c:if>

								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th align="center">
							配布先名
						</th>
						<td >
							<input type="text" name="form.destinationName"  value="${f:h(form.destinationName)}"  style="width:240px;"  />
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
							jdbc:mysql://<input type="text" name="form.devUrl"   value="${f:h(form.devUrl)}"   style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							ID
						</th>
						<td >
							<input type="text" name="form.devId"  value="${f:h(form.devId)}"   style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							PASSWORD
						</th>
						<td >
							<input type="text" name="form.devPassword"  value="${f:h(form.devPassword)}"  style="width:240px;"  />
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
							jdbc:mysql://<input type="text" name="form.stgUrl"  value="${f:h(form.stgUrl)}"    style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							ID
						</th>
						<td >
							<input type="text" name="form.stgId"  value="${f:h(form.stgId)}"  style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							PASSWORD
						</th>
						<td >
							<input type="text" name="form.stgPassword"   value="${f:h(form.stgPassword)}"  style="width:240px;"  />
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
							jdbc:mysql://<input type="text" name="form.pubUrl"  value="${f:h(form.pubUrl)}"   style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							ID
						</th>
						<td >
							<input type="text" name="form.pubId"  value="${f:h(form.pubId)}"   style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<th align="center">
							PASSWORD
						</th>
						<td >
							<input type="text" name="form.pubPassword"  value="${f:h(form.pubPassword)}"     style="width:240px;"  />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" name="regist" value="登録確認" style="font-size=20px;" />
							<input type="submit"  value = "キャンセル" style="font-size=20px;"  onclick="returnList();"/>

						</td>
					</tr>
			</table>
		</s:form>
	</tiles:put>
</tiles:insert>