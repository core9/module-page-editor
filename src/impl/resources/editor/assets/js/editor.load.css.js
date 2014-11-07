function loadCss(url){
	var link = document.createElement("link");
	link.setAttribute("rel","stylesheet");
	link.setAttribute("type","text/css");
	link.setAttribute("href",url);
	document.getElementsByTagName("head")[0].appendChild(link);
}
function loadCssUrls(baseUrl){
loadCss(baseUrl + "css/bootstrap.min.css");
loadCss(baseUrl + "css/bootstrap-theme.min.css");
loadCss(baseUrl + "css/jquery.sceditor.default.min.css");
loadCss(baseUrl + "css/default.min.css");
loadCss(baseUrl + "css/select2.css");
loadCss(baseUrl + "css/font-awesome.min.css");
loadCss(baseUrl + "css/editor.css");
loadCss(baseUrl + "css/wizard-engine.css");
}

function  initMyPage(baseUrl){
	loadCssUrls(baseUrl);
}
initMyPage(baseUrl);