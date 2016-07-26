<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.kja.server.resources.webinterface.EditContentView -->

<html>
<head>
	<meta charset="utf-8"/>
	<title>Inhalt bearbeiten</title>
</head>
<body>
	<form method="POST" action="/webinterface/edit">
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
		Text:<br/>
		<input type="textarea" name="text" value="${content.text}"/><br/>
		<button type="submit" name="button" value="save">Speichern</button>
		<button type="submit" name="button" value="saveexit">Speichern und Schließen</button>
	</form>
</body>
</html>
