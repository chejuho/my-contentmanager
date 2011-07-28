
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

function replace(text, fromText, toText) {

	var pos = 0;
    var pos2 = 0;
    var buf = "";
    do
    {
        pos = text.indexOf(fromText, pos2);
        if(pos == 0)
        {
        	buf += toText;
            pos2 = fromText.length;
        } else
        if(pos > 0)
        {
        	buf += text.substring(pos2, pos);
        	buf += toText;
            pos2 = pos + fromText.length;
        } else
        {
        	buf += text.substring(pos2);
            return buf;
        }
    } while(true);

}

