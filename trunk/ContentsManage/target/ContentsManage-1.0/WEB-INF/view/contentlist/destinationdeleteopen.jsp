<%@page pageEncoding="UTF-8"%>

<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="配布先削除" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">

			function returnList() {
				var form = document.forms[0];
				form.action="index";
				form.submit();

			}

			//削除
			function deleteData() {

				// 「OK」時の処理開始 ＋ 確認ダイアログの表示
				if(window.confirm('本当削除しますか？')){

					var form = document.forms[0];
					form.action="destinationDelete";
					form.submit();
				} else {
					return false;
				}


			}


		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

		<s:form>
			<c:forEach var="destination" items="${destinations}" varStatus="i">
				<input type="hidden" name = "deleteDestinations"  value="${f:h(destination.id)}"/>
			</c:forEach>
			<table align="center"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th align="center">
							削除配布先リスト
						</th>
					</tr>

					<c:forEach var="destination" items="${destinations}" varStatus="i">
						<tr>
							<td width="570">
								${f:h(destination.destination_name)}
							</td>
						</tr>
					</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="submit" value="削除" style="font-size=20px;" onclick="return deleteData();" />
					<input type="submit"  value = "キャンセル" style="font-size=20px;"  onclick="returnList();"/>
				</td>
			</tr>
			</table>
		</s:form>
	</tiles:put>
</tiles:insert>