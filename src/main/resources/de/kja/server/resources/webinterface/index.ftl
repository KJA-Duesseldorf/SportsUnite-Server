<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.kja.server.resources.webinterface.IndexView -->

<html>
<head>
	<meta charset="utf-8"/>
	<title>Web Interface</title>
</head>
<body>
	<noscript>
		<h3>Die Sicherheitsabfrage beim Löschen von Inhalten funktioniert nicht mit deaktiviertem JavaScript!</h3>
	</noscript>
	<#if message??>
		${message}
	</#if>
	<#list contents>
		<table border=1>
			<#items as content>
				<tr>
					<td>${content.title}</td>
					<td>${content.shortText}</td>
					<td>
						<form method="get" action="/webinterface/edit">
							<input type="hidden" name="id" value="${content.id}"/>
							<button type="submit">Bearbeiten</button>
						</form>
					</td>
					<td>
						<form method="post" action="/webinterface">
							<input type="hidden" name="id" value="${content.id}"/>
							<button type="submit" onclick="return confirm('Wollen Sie wirklich diesen Artikel löschen?');">Löschen</button>
						</form>
					</td>
				</tr>
			</#items>
		</table>
	<#else>
		Keine Inhalte gespeichert!
	</#list>
	<form method="get" action="/webinterface/edit">
		<input type="hidden" name="id" value="new"/>
		<button type="submit">Neu</button>
	</form>
</body>
</html>
