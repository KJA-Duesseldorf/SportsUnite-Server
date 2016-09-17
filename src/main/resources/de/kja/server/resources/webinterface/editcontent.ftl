<#ftl encoding="utf-8">
<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.kja.server.resources.webinterface.EditContentView -->

<html>
<head>
	<meta charset="utf-8"/>
	<title>Inhalt bearbeiten</title>
</head>
<body>
	<noscript>
		<h3>Die Sicherheitsabfrage beim Löschen von Inhalten funktioniert nicht mit deaktiviertem JavaScript!</h3>
	</noscript>
	<form enctype="multipart/form-data" method="POST" action="/webinterface/edit">
	
		<#if content.id != -1>
			<input type="hidden" name="id" value="${content.id}"/>
		<#else>
			<input type="hidden" name="id" value="new"/>
		</#if>
		
		Stadtbezirk:<br/>
		<input type="text" name="district" value="${content.district}"/><br/>
		
		Bild:<br/>
		<#if content.image??>
			<img src="/service/v1/images?id=${content.image}" alt="Hochgeladenes Bild"/><br/>
		</#if>
		<input type="file" name="image" accept="image/png"/>
		<#if content.image??>
			<button type="submit" name="button" value="deleteimage" onclick="return confirm('Wollen Sie wirklich dieses Bild löschen?');">Bild Löschen</button>
		</#if>
		<br/>
		
		<button type="submit" name="button" value="save">Speichern</button>
		<button type="submit" name="button" value="saveexit">Speichern und Schließen</button>
		<#if content.public>
			<button type="submit" name="button" value="makeprivate">Nicht mehr anzeigen</button>
		<#else>
			<button type="submit" name="button" value="makepublic">Veröffentlichen</button>
		</#if>
	</form>
	<#list translations>
		<table border=1>
			<#items as translation>
				<tr>
					<td>${translation.language}</td>
					<td>
						<#if translation.finished>
							Gespeichert.
						<#else>
							Fehlt!
						</#if>
					</td>
					<td>
						<form method="get" action="/webinterface/edit/text">
							<input type="hidden" name="id" value="${content.id}"/>
							<input type="hidden" name="language" value="${translation.language}"/>
							<button type="submit">Bearbeiten</button>
						</form>
					</td>
				</tr>
			</#items>
		</table>
	</#list>
</body>
</html>
