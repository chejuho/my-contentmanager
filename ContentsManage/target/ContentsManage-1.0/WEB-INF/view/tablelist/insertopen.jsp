<tiles:insert template="/WEB-INF/view/common/table_layout.jsp"
	flush="true">
	<tiles:put name="title" value="データ登録画面" />
	<tiles:put name="javascripctInput" type="string">
		<link rel="Stylesheet" type="text/css" href="${f:url('/css/jHtmlArea/jHtmlArea.css')}" />
    	<link rel="Stylesheet" type="text/css" href="${f:url('/css/jHtmlArea/jHtmlArea.ColorPickerMenu.css')}" />
    	<link rel="Stylesheet" type="text/css" href="${f:url('/css/pc_emoji.css')}" />
		<script src="${f:url('/js/ui.core.js')}"></script>
		<script src="${f:url('/js/jquery.ui.draggable.js')}"></script>
		<script src="${f:url('/js/jquery.bgiframe.js')}"></script>
		<script src="${f:url('/js/jquery-ui-1.7.2.custom.min.js')}"></script>
		<script src="${f:url('/js/jHtmlArea-0.7.0.js')}"></script>
		<script src="${f:url('/js/jHtmlArea.ColorPickerMenu-0.7.0.js')}"
			type="text/javascript"></script>
		<script type="text/javascript">


		jHtmlAreaColorPickerMenu.defaultOptions.colors = ${colors};

		  $(document).ready(function(){

		    $(".emoji_palette_close").bind("click", function(){
		      $(".emoji_palette").hide("normal");
		    });

		    //Event
		    $("div.emoji_palette td").bind("click", function(event){

			    if (!this.id)  return;
			    var value;
			    var target = $(event.target);
			    if(target.is("td")){
			     	value = target.html();
			    }
			    var field = $("div.emoji_palette").attr('value');


				var textarea = $('#id' + field);
				var pos = getAreaRangeText(textarea);

				var val = textarea.val();
	            //alert(val);
	    		var range = val.slice(pos.start, pos.end);
	    		var beforeNode = val.slice(0, pos.start);
	    		var afterNode = val.slice(pos.end);
	    		var insertNode;

	    		if (pos.start == pos.end) {
	    			insertNode = '$e(' + value + ')';
	    			textarea.val(beforeNode + insertNode + afterNode);
	    		}
				$(".emoji_palette").hide("normal");

		    });
		    $("div.emoji_palette td").hover(
		      function(){
		        if (this.id) $(this).css("background-color","#999");
		      },
		      function(){
		        if (this.id) $(this).css("background-color","#FFF");
		      }
		    );
		  });
			function registconfirm() {

				var form = document.forms[0];
				form.action="insertconfirm";
				form.submit();
			}
			function returnList() {
				var form = document.forms[0];

				<c:if test="${empty search}">
					form.action="detail/${tableid}/${pageNum}";
				</c:if>
				<c:if test="${!empty search}">
					form.action="searchdetail/${tableid}/${pageNum}";
				</c:if>

				form.submit();
			}

			function changeInput(field) {
				var value = $("#input" + field).val();
				//var str = replace(value, "%0D%0A", "\n");
				$('#' + field).html("<textarea id='id" + field + "' name='" + field + "'rows='15' style='width:95%'>" +  value + "</textarea>");

				 $('#id' + field).htmlarea({
		                toolbar: [
			  		                ["html", "|",
			                        "forecolor",  // <-- Add the "forecolor" Toolbar Button
			                        "|"],
							        [ "justifyLeft", "justifyCenter", "justifyRight"],
							        [{
							        	css: "emozi",
										text: "絵文字",
										action: function(event) {
								        	var off = event.offset();
											$("div.emoji_palette").bgiframe().css("top", off.top + 15).css("left", off.left).show("normal").attr('value', field).draggable();
										}

							        }]
						        ]


		                });

			}

			function getAreaRangeText(obj) {

				var pos = new Object();

				if ($.browser.msie) {

					obj.focus();
					var range = document.selection.createRange();
					var clone = range.duplicate();
					var textid = obj.attr("id");
					clone.moveToElementText(document.getElementById(textid));
					clone.setEndPoint( 'EndToEnd', range );
					var num = clone.text.match(/\r\n/g);
					var enternum = 0;
	        		if (num != null) {
	        			enternum = num.length;
	        		}
	        		pos.start = clone.text.length - range.text.length - enternum;
	        		pos.end = pos.start + range.text.length;

				} else if (window.getSelection()){

					var textid = obj.attr("id");
					pos.start = document.getElementById(textid).selectionStart;
					pos.end = document.getElementById(textid).selectionEnd;

				}
				return pos;
			}

			 //**********************************************************************
			//* 関数名：openCalendar
			//* 引数：nsEvent
			//* 説明：カレンダーを開く
			//**********************************************************************
			 function openCalendar(field, nsEvent) {
				var theEvent = nsEvent ? nsEvent : window.event;
				var newWindow =  window.open("${f:url('/common')}?filed=" + field, "カレンダー", "width = 300, height=240, location=no, toolbar=no");
				newWindow.moveTo(500,400);
			}


		</script>
	</tiles:put>
	<tiles:put name="header" value="/WEB-INF/view/common/header.jsp" />
	<tiles:put name="content" type="string">

		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="tableid" value="${tableid}" />
		<table width="100%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table id="inputtable" width="80%">
					<tr>
						<th class="top" colspan="2">
						${f:h(tableInfo_display.logical_name)}(${f:h(tableInfo_display.physical_name)})
						</th>
					</tr>
					<c:forEach var="column" items="${columnList}" varStatus="i">
						<tr>
							<th width="30%" class="table-title-text"><c:out
								value="${column.logical_name}" />(<c:out
								value="${column.physical_name}" />)&nbsp;</th>
							<c:set var="field" value="${column.physical_name}" />
							<c:set var="type" value="${column.type}" />
							<c:set var="date" value="<%=new java.util.Date()%>" />
							<%
								//<c:set var="text_sign" value="${column.text_sign}" />
							%>
							<td width="70%" bgcolor="#FFFFFF" class="table-cont-text">
								<!--  作成日付は今日に、作成ユーザはログインユーザ -->
								<c:if test="${field == 'created_at'}">
									<input type="text" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss" />" class="updateinput" disabled />
									<input type="hidden" name="${field}" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss" />" />
								</c:if>
								<c:if test="${field == 'created_by'}">
									<input type="text" value="${userInfo.user_id}" class="updateinput" disabled />
									<input type="hidden" name="${field}" value="${userInfo.user_id}" class="updateinput" />
								</c:if>
								<!--  更新日付は今日に、更新ユーザはログインユーザ -->
								<c:if test="${field == 'updated_at'}">
									<input type="text" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss" />" class="updateinput" disabled />
									<input type="hidden" name="${field}" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss" />" />
								</c:if>
								<c:if test="${field == 'updated_by'}">
									<input type="text" value="${userInfo.user_id}" class="updateinput" disabled />
									<input type="hidden" name="${field}" value="${userInfo.user_id}" />
								</c:if>
								<!--   作成情報,更新情報以外 -->
								<c:if test="${field != 'created_at' && field != 'created_by' && field != 'updated_at' && field != 'updated_by'}">
									<!--   タイプがdatetimeの場合カレンダー表示 -->
									<c:if test="${type == 'datetime'}">
										<input type="text" id="${field}" name="${field}" value="${f:h(dataMap[field])}" class="updateinput" />
										<a href="javascript:openCalendar('${field}');"><img src="${f:url('/images/calendar.jpeg')}" align="absmiddle" width="20" height="22"></a>
									</c:if>
									<!--   タイプがnvarcharの場合入力モードを変えるようにする -->
									<c:if test="${type == 'nvarchar(n)'}">
										<div id="${field}">
											<!--<input type="text" name="${field}" id="input${field}" value="${f:h(dataMap[field])}" class="updateinput" />-->
											<textarea name="${field}" id = "input${field}"  class="updateinput">${f:h(dataMap[field])}</textarea>
											<a href="javascript:changeInput('${field}');"><img src="${f:url('/images/textmode.jpeg')}" align="absmiddle" width="20" height="22"></a>
											<!--<input type="hidden" id="input${field}" value="${cum:changeRnToN(f:h(dataMap[field]))}" />-->
										</div>
									</c:if>
								<!--   タイプがnvarchar、datetime以外のそのまま表示するただautoincrementの場合は非活性化する -->
								<c:if test="${type != 'nvarchar(n)' && type != 'datetime'}">
									<c:if test="${column.autoincrement_sign == 1}">
										<input type="text" id="${field}" value="自動生成" name="${field}" class="updateinput" disabled />
									</c:if>
									<c:if test="${column.autoincrement_sign == 0}">
										<input type="text" id="${field}" name="${field}" value="${f:h(dataMap[field])}" class="updateinput" />
									</c:if>
								</c:if>
							</c:if></td>
						</tr>
					</c:forEach>
					<tr>
						<td class="btn" colspan="2" align="center"><input
							type="submit" value="登録確認" style="" onclick="registconfirm();" />
						<input type="submit" value="キャンセル" style=""
							onclick="returnList();" /></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<div id="emoji_palette" class="emoji_palette" style="display:none;">
	  		<div class="emoji_palette_title">
		    	<div class="emoji_palette_title_left">絵文字</div>
		    	<div class="emoji_palette_title_right">
		      		<span class="emoji_palette_close">閉じる</span>
		    	</div>
		  	</div>
		  <emoji:table configname="emojiStoreConfig"/>
		</div>
	</tiles:put>
</tiles:insert>
