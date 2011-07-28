<tiles:insert template="/WEB-INF/view/common/table_layout.jsp" flush="true">
	<tiles:put name="title" value="テーブル詳細情報" />
	<tiles:put name="javascripctInput" type="string">
		<script type="text/javascript">

		//csvimport
		function csvimport() {
			var form = document.forms[0];
			form.action="../../importopen";
			form.submit();

			return;
		}

		//csvexport
		function csvexport() {
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
				alert("エクスポートする項目がありません。");
				return;
			}


			form.action="../../exportopen";
			form.submit();
		}

		function searchkeyDown(event){
			var keycode;
			if(event.keyCode != 0){
				keycode = event.keyCode;
			}
			else {
				keycode = event.charCode;
			}
			if (keycode == 13) {
				if (searchData()) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}


		//検索
		function searchData() {

			var form = document.forms[0];

			if (form.search.value.length == 0) {
				form.action="../../detail/${tableid}/init";
				form.submit();
				return true;
			}
			form.action="../../searchdetail/${tableid}/${pageNum}";
			form.submit();
			return true;

		}

		//編集
		function editData(id) {

			var form = document.forms[0];
			form.action="../../editopen";
			form.primaryid.value = id;
			form.submit();

			return;
		}

		//挿入
		function insertData() {

			var form = document.forms[0];
			form.action="../../insertopen";
			form.submit();

			return;
		}
		//削除
		function deleteData() {

			var form = document.forms[0];
			var checked = false;
			form.action="../../deleteopen";

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
		//パブリッシュする
		function publishData() {
			var form = document.forms[0];
			var checked = false;
			form.action="../../publishopen";

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

				alert("パブリッシュする項目がありません。");
				return;
			}
			form.submit();
		}

		//表示件数変更
		function displayKensuChange() {
			var form = document.forms[0];
			form.action="../../displayKensuChange";
			form.submit();
		}
		//ALLチェック
		function allcheck() {

			var form = document.forms[0];

			var checkedAll = true;

			for (i = 0; i < form.elements.length; i++ ) {
				// エレメントがチェックボックスの時に処理を行う。
				if (form.elements[i].type == "checkbox" ) {
					if (form.elements[i].checked == false) {
						checkedAll = false;

					}
				}
			}

			for (i = 0; i < form.elements.length; i++ ) {
				// エレメントがチェックボックスの時に処理を行う。
				if (form.elements[i].type == "checkbox" ) {
					form.elements[i].checked = !checkedAll;
				}
			}

			return;
		}
		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">
	<input type="hidden" name = "primaryid" />
	<input type="hidden" name = "tableid" value="${tableid}"/>
	<input type="hidden" name = "pageNum"  value="${pageNum}"/>
	<table width="3050" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table>
				<tr>
					<td colspan="6">
						<span style="font-size: 18px; color: #000080">
						<s:link href="list/${selectDestinationId}/${selectedEv}"> <img src="${f:url('/images/modoru_btn.png')}"  align="absmiddle" width="24"  height="24" ></s:link>&nbsp;&nbsp;${f:h(content_sns_info.content.content_name)}->${f:h(content_sns_info.destination_name)}->${f:h(tableInfo_display.logical_name)}(${f:h(tableInfo_display.physical_name)})	</span>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" >
				<tr>
					<td>
						<button type="button" onclick="insertData()">
							登録
						</button>
					</td>
					<td>
						<button type="button" onclick="deleteData()">
							削除
						</button>
					</td>
					<td>
						<button type="button" onclick="publishData()">
							パブリッシュ
						</button>
					</td>
					<td>
						<button type="button" onclick="csvimport()">
							インポート
						</button>
					</td>
					<td>
						<button type="button" onclick="csvexport()">
							エクスポート
						</button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" >
				<tr>
					<td>
						<select name="searchoption">

							<c:if test="${searchoption == 'all'}">
								<option value="all" selected>全体</option>
							</c:if>
							<c:if test="${searchoption != 'all'}">
								<option value="all">全体</option>
							</c:if>
							<c:forEach var="column" items="${columnList}">
								<c:if test="${column.primary_key_sign == 1 || column.unique_key_sign == 1 || column.index_sign == 1}">
									<c:if test="${searchoption == column.physical_name}">
										<option value="${column.physical_name}"  selected>${column.physical_name}</option>
									</c:if>
									<c:if test="${searchoption != column.physical_name}">
										<option value="${column.physical_name}" >${column.physical_name}</option>
									</c:if>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td>
						<input name="search" id="keyword" type="text"  style="width:200px;" value="${search}" onkeydown="return searchkeyDown(event)" />

					</td>
					<td>
						<input type="button" value="検索" id="submit_button" onclick="searchData()" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td>
						${pageLink}
						表示件数：
						<select name="displayKensu" onchange="displayKensuChange()">
							<c:if test="${displaykensu == 10}">
								<option value="10" selected>10件</option>
								<option value="20">20件</option>
								<option value="30">30件</option>
								<option value="50">50件</option>
								<option value="100">100件</option>
								<option value="300">300件</option>
								<option value="500">500件</option>
							</c:if>
							<c:if test="${displaykensu == 20}">
								<option value="10">10件</option>
								<option value="20" selected>20件</option>
								<option value="30">30件</option>
								<option value="50">50件</option>
								<option value="100">100件</option>
								<option value="300">300件</option>
								<option value="500">500件</option>
							</c:if>
							<c:if test="${displaykensu == 30}">
								<option value="10">10件</option>
								<option value="20">20件</option>
								<option value="30" selected>30件</option>
								<option value="50">50件</option>
								<option value="100">100件</option>
								<option value="300">300件</option>
								<option value="500">500件</option>
							</c:if>
							<c:if test="${displaykensu == 50}">
								<option value="10">10件</option>
								<option value="20">20件</option>
								<option value="30">30件</option>
								<option value="50" selected>50件</option>
								<option value="100">100件</option>
								<option value="300">300件</option>
								<option value="500">500件</option>
							</c:if>
							<c:if test="${displaykensu == 100}">
								<option value="10">10件</option>
								<option value="20">20件</option>
								<option value="30">30件</option>
								<option value="50">50件</option>
								<option value="100" selected>100件</option>
								<option value="300">300件</option>
								<option value="500">500件</option>
							</c:if>
							<c:if test="${displaykensu == 300}">
								<option value="10">10件</option>
								<option value="20">20件</option>
								<option value="30">30件</option>
								<option value="50">50件</option>
								<option value="100">100件</option>
								<option value="300" selected>300件</option>
								<option value="500">500件</option>
							</c:if>
							<c:if test="${displaykensu == 500}">
								<option value="10">10件</option>
								<option value="20">20件</option>
								<option value="30">30件</option>
								<option value="50">50件</option>
								<option value="100">100件</option>
								<option value="300">300件</option>
								<option value="500" selected>500件</option>
							</c:if>

						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<span style="font-size:10px; color: #cc0033">&nbsp;&nbsp;${sessionScope[pageInfoKey].totalCount}   件中 ${sessionScope[pageInfoKey].startIndex} - ${sessionScope[pageInfoKey].endIndex} 件</span>
		</td>
	</tr>
	<tr>
		<td>
			<table id="table"  cellspacing="0" cellpadding="0">
			<tr>
				<th width="80"  align="center" ><a href="javascript:allcheck();"><img src="${f:url('/images/tick.png')}" ></a></th>
				<th width="80"  align="center" >編集</th>
				<c:forEach var="columnInfo" items="${columnList}" varStatus="i">
					<th align="center" >
						${f:h(columnInfo.logical_name)}<br>(${f:h(columnInfo.physical_name)})
					</th>
				</c:forEach>
			</tr>
			<!--テーブル情報表示 -->
			<c:choose>
			<c:when test="${!empty dataList}">
				<c:forEach var="datas" items="${dataList}" varStatus="i">
				<tr>
					<td align="center">
						<input type=checkbox name="checks" value="${cum:makePrimaryKeyList(datas, columnList)}"/>
					</td>
					<td align="center">
						<a href="javascript:editData('${cum:makePrimaryKeyList(datas, columnList)}');">編集&nbsp;</a>
					</td>
					<c:forEach var="data" items="${datas}" varStatus="i">
						<td align="center" >
							<c:out value="${data.value}"/>&nbsp;
						</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
			</c:otherwise>
			</c:choose>

			</table>
		</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td>
	</tr>
	<c:if test="${!empty dataList}">
	<tr>
		<td align="left">${pageLink}</td>
	</tr>
	</c:if>
	</table>

	</tiles:put>
</tiles:insert>