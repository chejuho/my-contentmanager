<%@page pageEncoding="UTF-8"%>


<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="コンテンツ登録" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">
			function contentregistOpen(){
				var form = document.forms[0];
				form.action="contentRegistOpen";
				form.submit();
			}
			//リストに戻る
			function golist(){
				var form = document.forms[0];
				form.action="index";
				form.submit();
			}
			//編集
			function contentEditOpen(id) {
				var form = document.forms[0];
				form.editContentId.value = id
				form.action="contentEditOpen";
				form.submit();


			}
			//テーブル追加
			function contentTableAdd(id) {
				var form = document.forms[0];
				form.editContentId.value = id
				form.action="contentAddOpen";
				form.submit();
			}
			//削除
			function contentDelectOpen() {

				var form = document.forms[0];
				var checked = false;
				form.action="contentDeleteOpen";

				for (i = 0; i < form.elements.length; i++ ) {
					// エレメントがチェックボックスの時に処理を行う。
					if (form.elements[i].type == "checkbox" ) {
						if (form.elements[i].checked == true) {
							checked = true;
							break;
						}
					}
				}

				if (!checked) {

					alert("削除する項目がありません。");
					return;
				}
				form.submit();

				return;
			}


		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

		<s:form onsubmit="return false;">
			<input type="hidden"  name = "editContentId" />
			<table align="center"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">
					<button type="button" onclick="contentregistOpen()">
						コンテンツ登録
					</button>
					<button type="button" onclick="contentDelectOpen()">
						コンテンツ削除
					</button>
					<button type="button" onclick="golist()">
						戻る
					</button>

				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th align="center" width="20">
							<a href="javascript:allcheck();"><img src="${f:url('/images/tick.png')}" ></a>
						</th>
						<th align="center" width="520">
							コンテンツ名
						</th>
						<th align="center" width="50">
							編集
						</th>
						<th align="center" width="150">
							テーブル追加
						</th>
					</tr>
					<c:choose>
							<c:when test="${!empty conents}">
								<c:forEach var="conent" items="${conents}" varStatus="i">
									<tr>
										<td>
											<input type=checkbox name="deleteContents" value="${f:h(conent.id)}"/>
										</td>
										<td>
											${f:h(conent.content_name)}
										</td>
										<td>
											<input type="submit"  value="編集" style="font-size=20px;"  onclick="contentEditOpen(${f:h(conent.id)});"/>
										</td>
										<td align="center">
											<input type="submit"  value="＋" style="font-size=20px;"  onclick="contentTableAdd(${f:h(conent.id)});"/>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td align="center" colspan="4">
										コンテンツを登録してください。
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</td>
			</tr>
			</table>
		</s:form>
	</tiles:put>
</tiles:insert>