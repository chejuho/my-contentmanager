<%@ page isErrorPage="true" %>
<html><head><title>error.jsp</title></head>
<body>
<table border="0" style="width:100%;">
    <tr>
        <td align="center">
            <h1>
                <font color="red"><strong>
                    !!&nbsp;ERROR&nbsp;!!
                </strong></font>
            </h1>
        </td>
    </tr>
    <tr>
        <td align="center">
          <html:errors/>
           <a href="${f:url('/index')}" >初期画面</a>
        </td>
    </tr>
      <tr>
        <td align="center">
         <%= exception %>
        </td>
    </tr>
</table>

</body></html>