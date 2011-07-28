<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<table>
	<tr>
		<td>
			ユーザID： ${userInfo.user_id}
		</td>
		<td>
			&nbsp;
		</td>
		<td>
			<a href="${f:url('/logout"')}" >ログアウト</a>
		</td>
	</tr>

</table>