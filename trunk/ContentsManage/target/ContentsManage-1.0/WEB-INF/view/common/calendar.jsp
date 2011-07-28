<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS" />
<title>日付を選択してください。</title>
<link rel="stylesheet" href="${f:url('/css/calendar.css')}"  type="text/css"/>
<script type="text/javascript" language="JavaScript" src="${f:url('/js/calendar.js')}" /></script>
<script type = "text/javascript">

//***************今日日付初期化**********************
genzaiDt = new Date(); //今日日付を取得
global_year = genzaiDt.getYear(); //YYYYを取る
if (global_year < 2000) {
	global_year = 1900 + global_year;
}
global_month = genzaiDt.getMonth() + 1; //MMを取る
global_date = genzaiDt.getDate(); //DDを取る
//****************************************************************************


//**********************************************************************
//* 関数名：init
//* 引数：なし
//* 説明：初期化
//**********************************************************************
	function setValue(target) {
		if(!window.opener
				|| window.opener.closed) {
			alert( "すでにオープンしたウインドウは無くなっています" );
			window.close();
			return;
		}
 		var val = global_year + '-' + formatNum(2,global_month) + '-' + formatNum(2,target.innerHTML) + " " + genzaiDt.getHours() + ":" + genzaiDt.getMinutes() + ":" + genzaiDt.getSeconds();
		var workDate = window.opener.document.getElementById("${fieldname}");
		workDate.value = val;
		window.opener.focus();
		window.close();
	}

//**********************************************************************
//* 関数名：nowDate
//* 引数：なし
//* 説明：今日のカレンダーを描く
//**********************************************************************
	function nowDate() {
		var main = document.getElementById("main");
		main.innerHTML = global_year + "年" + global_month + "月";
		var calendarList = getCalendarList(global_year, global_month);
		_drawCalendar(calendarList);
	}
//**********************************************************************
//* 関数名：goPrevMonth
//* 引数：なし
//* 説明：表示している日付の前月のカレンダーを描く
//**********************************************************************
	function goPrevMonth() {
		global_month--;
		//年が変わる
		if (global_month == 0) {
			global_month = 12;
			global_year--;
		}
		var main = document.getElementById("main");
		main.innerHTML = global_year + "年" + global_month + "月";
		var calendarList = getCalendarList(global_year,global_month);
		_drawCalendar(calendarList);
	}
//**********************************************************************
//* 関数名：goNextMonth
//* 引数：なし
//* 説明：表示している日付の前月のカレンダーを描く
//**********************************************************************
	function goNextMonth() {
		global_month++;
		//年が変わる
		if (global_month == 13) {
			global_month = 1;
			global_year++;
		}
		var main = document.getElementById("main");
		main.innerHTML = global_year + "年" + global_month + "月";
		var calendarList = getCalendarList(global_year,global_month);
		_drawCalendar(calendarList);
	}
//**********************************************************************
//* 関数名：drawCalendar
//* 引数：calendarList
//* 説明：calendarListを画面に表示する
//**********************************************************************
	function _drawCalendar(calendarList) {
		_clear();
		var index = 1;
		for (var i in calendarList) {
			var num = "p" + index;
			var p = document.getElementById(num);
			var date = calendarList[i];
			if (_isOtherMonth(date.getMonth())) {
				setStyle(p, OTHER_MONTH_STYLE);
			} else if (isHoliday(date)) {
				setStyle(p, HOLIDAY_STYLE);
			} else if (SATURDAY == date.getDay()) {
				setStyle(p, SATURDAY_STYLE);
			} else if (SUNDAY == date.getDay()) {
				setStyle(p, SUNDAY_STYLE);
			} else {
				setStyle(p, WEEKDAY_STYLE);
			}
			if (_isToday(date)) {
				setStyle(p, TODAY_STYLE);
			}
			p.innerHTML = date.getDate();
			index++;
		}
	 }
//**********************************************************************
//* 関数名：_isToday
//* 引数：date
//* 説明：実行する日かを判断する
//**********************************************************************
	 function _isToday(date) {
	 	if (date.getYear() == genzaiDt.getYear()
	 		&& date.getMonth() == genzaiDt.getMonth()
	 		&& date.getDate() == genzaiDt.getDate()) {
	 		return true;
	 	}
	 	return false;
	 }
//**********************************************************************
//* 関数名：_isOtherMonth
//* 引数：month
//* 説明：表示する月かを判別する
//**********************************************************************
	  function _isOtherMonth(month) {
	 	if (global_month != month + 1) {
	 		return true;
	 	}
	 	return false;

	 }
//**********************************************************************
//* 関数名：_clear
//* 引数：無し
//* 説明：コンテンツを掃除する
//**********************************************************************
	function _clear() {
		for (var i = 1; i <= 42; i++) {
			var num = "p" + i;
			var p = document.getElementById(num);
			p.innerHTML = "";
		}
	}
//**********************************************************************
//* 関数名：_printDate
//* 引数：calendarList
//* 説明：Debug用取得したカレンダー情報を出力
//**********************************************************************
function _printDate(calendarList) {
	for (var i in calendarList) {
		var date = calendarList[i];
		document.writeln(date.getDate());
	}
	document.writeln("<br>");
}
</script>
</head>

<body onload = "nowDate();">
<table id = "table">
<tr>
<td colspan = "1" ></td>
<td colspan = "1">&nbsp;&nbsp; <a href="" onclick="goPrevMonth(); return false"> << </a></td>
<td colspan = "3" align="center"><p id = "main"></p></td>
<td colspan = "1"><a href="" onclick="goNextMonth(); return false"> >> </a></td>
<td colspan = "3" ></td>
</tr>
<tr id = "tr">
<th width='25' class='sch_month_sunday'>日</th>
<th width='25' class='sch_month_weekday'>月</th>
<th width='25' class='sch_month_weekday'>火</th>
<th width='25' class='sch_month_weekday'>水</th>
<th width='25' class='sch_month_weekday'>木</th>
<th width='25' class='sch_month_weekday'>金</th>
<th width='25' class='sch_month_saturday'>土</th>
</tr>
</table>
<table id = "valueTable">
<tr>
<td width='33' id = "p1" onclick="setValue(this);"></td>
<td width='33' id = "p2" onclick="setValue(this);"></td>
<td width='33' id = "p3" onclick="setValue(this);"></td>
<td width='33' id = "p4" onclick="setValue(this);"></td>
<td width='33' id = "p5" onclick="setValue(this);"></td>
<td width='33' id = "p6" onclick="setValue(this);"></td>
<td width='33' id = "p7" onclick="setValue(this);"></td>
</tr>
<tr>
<td width='33' id = "p8" onclick="setValue(this);"></td>
<td width='33' id = "p9" onclick="setValue(this);"></td>
<td width='33' id = "p10" onclick="setValue(this);"></td>
<td width='33' id = "p11" onclick="setValue(this);"></td>
<td width='33' id = "p12" onclick="setValue(this);"></td>
<td width='33' id = "p13" onclick="setValue(this);"></td>
<td width='33' id = "p14" onclick="setValue(this);"></td>
</tr>
<tr>
<td width='33' id = "p15" onclick="setValue(this);"></td>
<td width='33' id = "p16" onclick="setValue(this);"></td>
<td width='33' id = "p17" onclick="setValue(this);"></td>
<td width='33' id = "p18" onclick="setValue(this);"></td>
<td width='33' id = "p19" onclick="setValue(this);"></td>
<td width='33' id = "p20" onclick="setValue(this);"></td>
<td width='33' id = "p21" onclick="setValue(this);"></td>
</tr>
<tr>
<td width='33' id = "p22" onclick="setValue(this);"></td>
<td width='33' id = "p23" onclick="setValue(this);"></td>
<td width='33' id = "p24" onclick="setValue(this);"></td>
<td width='33' id = "p25" onclick="setValue(this);"></td>
<td width='33' id = "p26" onclick="setValue(this);"></td>
<td width='33' id = "p27" onclick="setValue(this);"></td>
<td width='33' id = "p28" onclick="setValue(this);"></td>
</tr>
<tr>
<td width='33' id = "p29" onclick="setValue(this);"></td>
<td width='33' id = "p30" onclick="setValue(this);"></td>
<td width='33' id = "p31" onclick="setValue(this);"></td>
<td width='33' id = "p32" onclick="setValue(this);"></td>
<td width='33' id = "p33" onclick="setValue(this);"></td>
<td width='33' id = "p34" onclick="setValue(this);"></td>
<td width='33' id = "p35" onclick="setValue(this);"></td>

</tr>
<tr>
<td width='33' id = "p36" onclick="setValue(this);"></td>
<td width='33' id = "p37" onclick="setValue(this);"></td>
<td width='33' id = "p38" onclick="setValue(this);"></td>
<td width='33' id = "p39" onclick="setValue(this);"></td>
<td width='33' id = "p40" onclick="setValue(this);"></td>
<td width='33' id = "p41" onclick="setValue(this);"></td>
<td width='33' id = "p42" onclick="setValue(this);"></td>

</tr>
</table>
</body>
</html>
