function loadCss(url){
	var link = document.createElement("link");
	link.setAttribute("rel","stylesheet");
	link.setAttribute("type","text/css");
	link.setAttribute("href",url);
	document.getElementsByTagName("head")[0].appendChild(link);
}
function loadCssUrls(baseUrl){
loadCss("/static/plugins/editor/server/assets/plugins/bootstrap/3.2.0/css/bootstrap.min.css");
loadCss("/static/plugins/editor/server/assets/plugins/bootstrap/3.2.0/css/bootstrap-theme.min.css");
loadCss("/static/plugins/editor/server/assets/plugins/sceeditor/minified/jquery.sceditor.default.min.css");
loadCss("/static/plugins/editor/server/assets/plugins/sceeditor/minified/themes/default.min.css");
loadCss("/static/plugins/editor/server/assets/plugins/select2/select2.css");
loadCss('/static/plugins/editor/server/assets/plugins/font-awesome-4.2.0/css/font-awesome.min.css');
loadCss("/static/plugins/editor/server/assets/css/editor.css");
loadCss("/static/plugins/editor/server/assets/css/wizard-engine.css");
}

function  initMyPage(baseUrl){
	loadCssUrls(baseUrl);
}
