<#ftl encoding="utf-8">
<!DOCTYPE html>

<#-- @ftlvariable name="" type="de.kja.server.resources.webinterface.EditContentTranslationView -->

<html>
<style>
tr {
    vertical-align:top
}
</style>
<head>
	<meta charset="utf-8"/>
	<title>Inhalt bearbeiten</title>
</head>
<body>
	<noscript>
		<h3>Die Markdown Preview funktioniert nicht mit deaktiviertem JavaScript!</h3>
	</noscript>
	<form method="POST" action="/webinterface/edit/text">
	
		<input type="hidden" name="id" value="${content.id}"/>
		<input type="hidden" name="language" value="${content.translation.language}"/>
		
		Titel:<br/>
		<input type="text" name="title" value="${content.translation.title}"/><br/>
		
		Kurzbeschreibung:<br/>
		<input type="text" name="shortText" value="${content.translation.shortText}"/><br/>
		
		Text (${content.translation.language}):<br/>
		<table><tr>
			<td>
				<textarea id="text-input" cols="80" rows="30" name="text" oninput="this.editor.update()">${content.translation.text}</textarea>
			</td>
			<td>
				<div id="preview"></div>
			</td>
		</tr></table>
		
		<button type="submit" name="button" value="save">Speichern</button>
		<button type="submit" name="button" value="savereturn">Speichern und Zurückkehren</button>
		<button type="submit" name="button" value="saveexit">Speichern und Schließen</button>
	</form>
    <script src="/js/editcontentbundle.js"></script>
</body>
</html>
