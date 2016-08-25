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
		Titel:<br/>
		<input type="text" name="title" value="${content.title}"/><br/>
		Kurzbeschreibung:<br/>
		<input type="text" name="shortText" value="${content.shortText}"/><br/>
		Stadtbezirk:<br/>
		<input type="text" name="district" value="${content.district}"/><br/>
		Bild:<br/>
		<#if content.image??>
			<img src="/images?id=${content.image}" alt="Hochgeladenes Bild"/><br/>
		</#if>
		<input type="file" name="image" accept="image/png"/>
		<#if content.image??>
			<button type="submit" name="button" value="deleteimage" onclick="return confirm('Wollen Sie wirklich dieses Bild löschen?');">Bild Löschen</button>
		</#if>
		<br/>
		Text:<br/>
		<textarea cols="30" wrap="hard" rows="30" name="text">${content.text}</textarea><br/>
		<button type="submit" name="button" value="save">Speichern</button>
		<button type="submit" name="button" value="saveexit">Speichern und Schließen</button>
	</form>
</body>
</html>
