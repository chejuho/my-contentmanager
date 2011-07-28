<%@page pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Login</title>
<link rel="stylesheet" type="text/css" href="${f:url('/css/basic.css')}" />
<script type="text/javascript">

	function passchange() {
		var form = document.forms[0];
		form.action="passchange";
		form.submit();
	}

</script>
</head>
<body style="margin:0 0 0 0">

	<table border="0" >
	<!-- <tr>
		<td>
			${pageContext.request.requestURL}
		</td>
	</tr>
	<tr>
		<td>
			${pageContext.request.contextPath}

		</td>
	</tr>
	<tr>
		<td>
			${pageContext.request.requestURI}
		</td>
	</tr> -->
	<tr>
		<td height="20">&nbsp;</td>
	</tr>
	<tr>
		<td>

			<s:form>
				<input type="hidden" name="id"  value="${id}"  />
				<table width="1200"  border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30" valign="top">&nbsp;</td>

				</tr>
				<tr>
					<td align="center" valign="top">
						<table width="340" height="310" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="center" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="61%" height="96">&nbsp;</td>
									</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="162" align="center">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan ="2">
											<html:errors/>
										</td>
									</tr>
									<tr>
										<td width="40%" height="29" align="left"><strong>パスワード</strong></td>
										<td width="60%" align="left">
											<!--<input type="text" name="id" id="textfield2" class="idForm" style="width:150;height:20" onkeydown="if(Keycode(event) ==13) login(lf);" tabindex="1" />-->
											<input type="password" name="password"  style="width:150;height:20" size="20"  tabindex="1" />

										</td>
									</tr>
									<tr>
										<td align="left"><strong>パスワード確認</strong></td>
										<td align="left">
											<input type="password" name="repassword"  style="width:150;height:20" size="20"  tabindex="2" />
										</td>
									</tr>
									<tr>
										<td colspan ="2">&nbsp;</td>
									</tr>
<%
/*
									<tr>
										<td align="left"><strong>ãã¼ã¿ãã¼ã¹</strong></td>
										<td align="left">
											<html:select property="database">
												<html:option value="1">dev</html:option>
												<html:option value="2">stg</html:option>
												<html:option value="3">æ¬çª</html:option>

											</html:select>
										</td>
									</tr>
*/
%>
									<tr>
										<td colspan="2" align="center">
											<input type="submit" value="変更"  onclick="passchange()"/>
										</td>
									</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</table>
			</s:form>
		</td>
	</tr>
	<tr>
		<td height="50">&nbsp;</td>
	</tr>
	</table>
</body>

</html>