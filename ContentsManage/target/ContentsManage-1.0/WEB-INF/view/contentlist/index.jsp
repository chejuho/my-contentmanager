<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
	<tiles:put name="title" value="コンテンツ一覧" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">

			//コンテンツ管理
			function contentManageOpen(){
				var form = document.forms[0];
				form.action="contentManageOpen";
				form.submit();
			}
			//配布先登録
			function destinationRegistOpen(){
				var form = document.forms[0];
				form.action="destinationRegistOpen";
				form.submit();
			}
			//配布先削除
			function destinationDeleteOpen(){
				var form = document.forms[0];
				var checked = false;

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

				form.action="destinationDeleteOpen";
				form.submit();
			}
			//メニュー選択
			function selectContent(id) {
				var form = document.forms[0];
				form.selected.value = id;
				form.action="contentSelect";
				form.submit();

			}


			//詳細
			function detailDestination(id) {
				var form = document.forms[0];
				form.detailDestinationId.value = id;
				form.action="destinationDetail";
				form.submit();
			}
			//編集
			function editDestination(id) {
				var form = document.forms[0];
				form.editDestinationId.value = id;
				form.action="destinationEditOpen";
				form.submit();
			}

			//テーブル管理
			function goTableManage(id) {
				var form = document.forms[0];
				form.detailDestinationId.value = id;
				form.action="goTableManage";
				form.submit();

			}

		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

		<s:form>
			<input type="hidden"  name = "selected"  value="${selected}" />
			<input type="hidden"  name = "editDestinationId"   />
			<input type="hidden"  name = "detailDestinationId"   />
			<table width="1150"   align="center"  border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					<button type="button" onclick="contentManageOpen()">
						コンテンツ管理
					</button>

				</td>
				<td align="right">
					<button type="button" onclick="destinationRegistOpen()">
						配布先登録
					</button>
					<button type="button" onclick="destinationDeleteOpen()">
						配布先削除
					</button>

				</td>
			</tr>
			<tr>
				<td>
					&nbsp;

				</td>
			</tr>
			<tr>
				<td width="300" valign="top">
					<table class="menulist" width="300" height = "1100" bgcolor="#FFE4B5"  border="1" cellspacing="0" cellpadding="0" >
					<tr>
						<th align="left" bgcolor="#E6E6FA"  height = "30">
							コンテンツ一覧
						</th>
					</tr>
					<tr>
						<td valign="top">
							<div id="menu_v1">
								<ul >
									<c:if test="${selected == 'main'}">
				            			<li><a href="index"  class = "selected">全体&nbsp;</a></li>
				            		</c:if>
				            		<c:if test="${selected != 'main'}">
				            			<li><a href="index"  >全体&nbsp;</a></li>
				            		</c:if>
									<c:choose>
										<c:when test="${!empty conents}">
											<c:forEach var="conent" items="${conents}" varStatus="i">
												<c:if test="${selected == f:h(conent.id)}">
													<li><a href="javascript:selectContent('${f:h(conent.id)}');"  class = "selected"><c:out value="${f:h(conent.content_name)}"/>&nbsp;</a></li>
												</c:if>
												<c:if test="${selected != f:h(conent.id)}">
													<li><a href="javascript:selectContent('${f:h(conent.id)}');" ><c:out value="${f:h(conent.content_name)}"/>&nbsp;</a></li>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li style="margin-bottom:10px;">コンテンツを登録してください。</li>
										</c:otherwise>
									</c:choose>
								</ul>
							</div>
						</td>
					</tr>
					</table>
				</td>
				<td valign="top">
					<table class="basic" width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th align="center">
							<a href="javascript:allcheck();"><img src="${f:url('/images/tick.png')}" ></a>
						</th>
						<th align="center">
							コンテンツ名
						</th>
						<th align="center">
							配布先名
						</th>
						<th align="center">
							詳細
						</th>
						<th align="center">
							編集
						</th>
					</tr>

					<c:choose>
						<c:when test="${!empty destinations}">
							<c:forEach var="destination" items="${destinations}" varStatus="i">
								<tr>
									<td width="15">
										<input type=checkbox name="deleteDestinations" value="${f:h(destination.id)}"/>
									</td>
									<td width="250" align="center">
										${f:h(destination.content.content_name)}
									</td>
									<td width="450">
										<a href="javascript:goTableManage('${f:h(destination.id)}');">${f:h(destination.destination_name)}</a>
									</td>
									<td width="75" align="center">
										<a href="javascript:detailDestination('${f:h(destination.id)}');"  class = "basic">詳細</a>
									</td>
									<td width="75" align="center">
										<a href="javascript:editDestination('${f:h(destination.id)}');"  class = "basic">編集</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td align="center" colspan="5">
								配布先を登録してください。
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