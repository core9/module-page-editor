"use strict";
// https://github.com/stackp/promisejs
(function(a){function b(){this._callbacks=[];}b.prototype.then=function(a,c){var d;if(this._isdone)d=a.apply(c,this.result);else{d=new b();this._callbacks.push(function(){var b=a.apply(c,arguments);if(b&&typeof b.then==='function')b.then(d.done,d);});}return d;};b.prototype.done=function(){this.result=arguments;this._isdone=true;for(var a=0;a<this._callbacks.length;a++)this._callbacks[a].apply(null,arguments);this._callbacks=[];};function c(a){var c=new b();var d=[];if(!a||!a.length){c.done(d);return c;}var e=0;var f=a.length;function g(a){return function(){e+=1;d[a]=Array.prototype.slice.call(arguments);if(e===f)c.done(d);};}for(var h=0;h<f;h++)a[h].then(g(h));return c;}function d(a,c){var e=new b();if(a.length===0)e.done.apply(e,c);else a[0].apply(null,c).then(function(){a.splice(0,1);d(a,arguments).then(function(){e.done.apply(e,arguments);});});return e;}function e(a){var b="";if(typeof a==="string")b=a;else{var c=encodeURIComponent;for(var d in a)if(a.hasOwnProperty(d))b+='&'+c(d)+'='+c(a[d]);}return b;}function f(){var a;if(window.XMLHttpRequest)a=new XMLHttpRequest();else if(window.ActiveXObject)try{a=new ActiveXObject("Msxml2.XMLHTTP");}catch(b){a=new ActiveXObject("Microsoft.XMLHTTP");}return a;}function g(a,c,d,g){var h=new b();var j,k;d=d||{};g=g||{};try{j=f();}catch(l){h.done(i.ENOXHR,"");return h;}k=e(d);if(a==='GET'&&k){c+='?'+k;k=null;}j.open(a,c);j.setRequestHeader('Content-type','application/x-www-form-urlencoded');for(var m in g)if(g.hasOwnProperty(m))j.setRequestHeader(m,g[m]);function n(){j.abort();h.done(i.ETIMEOUT,"",j);}var o=i.ajaxTimeout;if(o)var p=setTimeout(n,o);j.onreadystatechange=function(){if(o)clearTimeout(p);if(j.readyState===4){var a=(!j.status||(j.status<200||j.status>=300)&&j.status!==304);h.done(a,j.responseText,j);}};j.send(k);return h;}function h(a){return function(b,c,d){return g(a,b,c,d);};}var i={Promise:b,join:c,chain:d,ajax:g,get:h('GET'),post:h('POST'),put:h('PUT'),del:h('DELETE'),ENOXHR:1,ETIMEOUT:2,ajaxTimeout:0};if(typeof define==='function'&&define.amd)define(function(){return i;});else a.promise=i;})(this);// http://davidjbradshaw.github.io/iframe-resizer/#iframe-methods
!function(){"use strict";function a(a,b,c){"addEventListener"in window?a.addEventListener(b,c,!1):"attachEvent"in window&&a.attachEvent("on"+b,c)}function b(a){return $+"["+ab+"] "+a}function c(a){Z&&"object"==typeof window.console&&console.log(b(a))}function d(a){"object"==typeof window.console&&console.warn(b(a))}function e(){c("Initialising iFrame"),f(),i(),h("background",L),h("padding",O),o(),m(),j(),p(),n(),D("init","Init message from host page")}function f(){function a(a){return"true"===a?!0:!1}var b=X.substr(_).split(":");ab=b[0],M=void 0!==b[1]?Number(b[1]):M,P=void 0!==b[2]?a(b[2]):P,Z=void 0!==b[3]?a(b[3]):Z,Y=void 0!==b[4]?Number(b[4]):Y,bb=void 0!==b[5]?a(b[5]):bb,J=void 0!==b[6]?a(b[6]):J,N=b[7],V=void 0!==b[8]?b[8]:V,L=b[9],O=b[10],fb=void 0!==b[11]?Number(b[11]):fb}function g(a,b){return-1!==b.indexOf("-")&&(d("Negative CSS value ignored for "+a),b=""),b}function h(a,b){void 0!==b&&""!==b&&"null"!==b&&(document.body.style[a]=b,c("Body "+a+' set to "'+b+'"'))}function i(){void 0===N&&(N=M+"px"),g("margin",N),h("margin",N)}function j(){document.documentElement.style.height="",document.body.style.height="",c('HTML & body height set to "auto"')}function k(){a(window,"resize",function(){D("resize","Window resized")})}function l(){a(window,"click",function(){D("click","Window clicked")})}function m(){U!==V&&(V in jb||(d(V+" is not a valid option for heightCalculationMethod."),V="bodyScroll"),c('Height calculation method set to "'+V+'"'))}function n(){!0===J?(k(),l(),s()):c("Auto Resize disabled")}function o(){var a=document.createElement("div");a.style.clear="both",a.style.display="block",document.body.appendChild(a)}function p(){bb&&(c("Enable public methods"),window.parentIFrame={close:function(){D("close","parentIFrame.close()",0,0)},getId:function(){return ab},reset:function(){G("parentIFrame.size")},scrollTo:function(a,b){H(b,a,"scrollTo")},sendMessage:function(a,b){H(0,0,"message",a,b)},setHeightCalculationMethod:function(a){V=a,m()},setTargetOrigin:function(a){c("Set targetOrigin: "+a),db=a},size:function(a,b){var c=""+(a?a:"")+(b?","+b:"");E(),D("size","parentIFrame.size("+c+")",a,b)}})}function q(){0!==Y&&(c("setInterval: "+Y+"ms"),setInterval(function(){D("interval","setInterval: "+Y)},Math.abs(Y)))}function r(b){function d(b){(void 0===b.height||void 0===b.width||0===b.height||0===b.width)&&(c("Attach listerner to "+b.src),a(b,"load",function(){D("imageLoad","Image loaded")}))}b.forEach(function(a){if("attributes"===a.type&&"src"===a.attributeName)d(a.target);else if("childList"===a.type){var b=a.target.querySelectorAll("img");Array.prototype.forEach.call(b,function(a){d(a)})}})}function s(){function a(){var a=document.querySelector("body"),d={attributes:!0,attributeOldValue:!1,characterData:!0,characterDataOldValue:!1,childList:!0,subtree:!0},e=new b(function(a){D("mutationObserver","mutationObserver: "+a[0].target+" "+a[0].type),r(a)});c("Enable MutationObserver"),e.observe(a,d)}var b=window.MutationObserver||window.WebKitMutationObserver;b?0>Y?q():a():(d("MutationObserver not supported in this browser!"),q())}function t(){function a(a){function b(a){var b=/^\d+(px)?$/i;if(b.test(a))return parseInt(a,K);var d=c.style.left,e=c.runtimeStyle.left;return c.runtimeStyle.left=c.currentStyle.left,c.style.left=a||0,a=c.style.pixelLeft,c.style.left=d,c.runtimeStyle.left=e,a}var c=document.body,d=0;return"defaultView"in document&&"getComputedStyle"in document.defaultView?(d=document.defaultView.getComputedStyle(c,null),d=null!==d?d[a]:0):d=b(c.currentStyle[a]),parseInt(d,K)}return document.body.offsetHeight+a("marginTop")+a("marginBottom")}function u(){return document.body.scrollHeight}function v(){return document.documentElement.offsetHeight}function w(){return document.documentElement.scrollHeight}function x(){for(var a=document.querySelectorAll("body *"),b=a.length,d=0,e=(new Date).getTime(),f=0;b>f;f++)a[f].getBoundingClientRect().bottom>d&&(d=a[f].getBoundingClientRect().bottom);return e=(new Date).getTime()-e,c("Parsed "+b+" HTML elements"),c("LowestElement bottom position calculated in "+e+"ms"),d}function y(){return[t(),u(),v(),w()]}function z(){return Math.max.apply(null,y())}function A(){return Math.min.apply(null,y())}function B(){return Math.max(t(),x())}function C(){return Math.max(document.documentElement.scrollWidth,document.body.scrollWidth)}function D(a,b,d,e){function f(){a in{reset:1,resetPage:1,init:1}||c("Trigger event: "+b)}function g(){S=n,ib=o,H(S,ib,a)}function h(){return gb&&a in Q}function i(){function a(a,b){var c=Math.abs(a-b)<=fb;return!c}return n=void 0!==d?d:jb[V](),o=void 0!==e?e:C(),a(S,n)||P&&a(ib,o)}function j(){return!(a in{init:1,interval:1,size:1})}function k(){return V in cb}function l(){c("No change in size detected")}function m(){j()&&k()?G(b):a in{interval:1}||(f(),l())}var n,o;h()?c("Trigger event cancelled: "+a):i()?(f(),E(),g()):m()}function E(){gb||(gb=!0,c("Trigger event lock on")),clearTimeout(hb),hb=setTimeout(function(){gb=!1,c("Trigger event lock off"),c("--")},R)}function F(a){S=jb[V](),ib=C(),H(S,ib,a)}function G(a){var b=V;V=U,c("Reset trigger event: "+a),E(),F("reset"),V=b}function H(a,b,d,e,f){function g(){void 0===f?f=db:c("Message targetOrigin: "+f)}function h(){var g=a+":"+b,h=ab+":"+g+":"+d+(void 0!==e?":"+e:"");c("Sending message to host page ("+h+")"),eb.postMessage($+h,f)}g(),h()}function I(a){function b(){return $===(""+a.data).substr(0,_)}function f(){X=a.data,eb=a.source,e(),T=!1,setTimeout(function(){W=!1},R)}function g(){W?c("Page reset ignored by init"):(c("Page size reset by host page"),F("resetPage"))}function h(){return a.data.split("]")[1]}function i(){return"iFrameResize"in window}function j(){return a.data.split(":")[2]in{"true":1,"false":1}}b()&&(T&&j()?f():"reset"===h()?g():a.data===X||i()||d("Unexpected message ("+a.data+")"))}var J=!0,K=10,L="",M=0,N="",O="",P=!1,Q={resize:1,click:1},R=128,S=1,T=!0,U="offset",V=U,W=!0,X="",Y=32,Z=!1,$="[iFrameSizer]",_=$.length,ab="",bb=!1,cb={max:1,scroll:1,bodyScroll:1,documentElementScroll:1},db="*",eb=window.parent,fb=0,gb=!1,hb=null,ib=1,jb={offset:t,bodyOffset:t,bodyScroll:u,documentElementOffset:v,scroll:w,documentElementScroll:w,max:z,min:A,grow:z,lowestElement:B};a(window,"message",I)}();

var Core9 = {}
Core9.menu = {

		blockClassName : function(){return "block";},
		selectedBlockPosition : "",
		selectedBlock : {},

		endsWith : function endsWith(str, suffix) {
		    return str.indexOf(suffix, str.length - suffix.length) !== -1;
		},
		menu : function(){

			var menu = "";
			menu += '<ul id="context-menu" style="position:absolute; top:{y}px; left:{x}px;background-color:#fff;width: 70px;color:#fff;z-index: 9999999;list-style: none;padding: 20px;">';
			menu += '<li><button style="border: 0 rgba(0,0,0,0); background-color: transparent; width: 125px; text-decoration: none; color:#000; text-align:left;"  onclick="Core9.menu.editBlock()">Edit</button></li>';
			menu += '<li><button style="border: 0 rgba(0,0,0,0); background-color: transparent; width: 125px; text-decoration: none; color:#000; text-align:left;"  onclick="if (\'parentIFrame\' in window) window.parentIFrame.sendMessage(\'edit-block\');return false;">{insertBefore}</button></li>';
			menu += '<li><button style="border: 0 rgba(0,0,0,0); background-color: transparent; width: 125px; text-decoration: none; color:#000; text-align:left;"  onclick="if (\'parentIFrame\' in window) window.parentIFrame.sendMessage(\'edit-block\');return false;">{insertAfter}</button></li>';
			menu += '<li><button style="border: 0 rgba(0,0,0,0); background-color: transparent; width: 125px; text-decoration: none; color:#000; text-align:left;"  onclick="if (\'parentIFrame\' in window) window.parentIFrame.sendMessage(\'edit-block\');return false;">{switchblock}</button></li>';
			menu += '</ul>';
			return menu;
		},

		getParentElementWithClass : function(element, className){
			function collectionHas(a, b) { //helper function (see below)
			    for(var i = 0, len = a.length; i < len; i ++) {
			        if(a[i] == b) return true;
			    }
			    return false;
			}
			function findParentBySelector(elm, selector) {
			    var all = document.querySelectorAll(selector);
			    var cur = elm.parentNode;
			    while(cur && !collectionHas(all, cur)) { //keep going up until you find a match
			        cur = cur.parentNode; //go up
			    }
			    return cur; //will return null if not found
			}


			var parent = findParentBySelector(element, "." + className);
			return parent;
		},

		editBlock : function(event){
			console.log('sending edit message');
			console.log('selected block position : ' + Core9.menu.selectedBlockPosition);
			console.log('selected block : ');
			console.log(Core9.menu.selectedBlock);
			console.log('data attribute type :  ' + Core9.menu.selectedBlock.dataset.type);

			var jsonData = {
					"action": "edit-block",
					"block": Core9.menu.selectedBlockPosition,
					"type" : Core9.menu.selectedBlock.dataset.type
			}

			console.log(event);
			if ('parentIFrame' in window) window.parentIFrame.sendMessage(JSON.stringify(jsonData));return false;
		},

		listener :	function (event) {

			if(Core9.menu.endsWith(event.data, "null:offset:null:null:0")){
				return;
			}

			// if ( event.origin !== location.origin )
			// return
			document.getElementById("test").innerHTML = "received: " + event.data + "<script>var url = " + event.data + "</script>";
			var url = event.data;
			promise.get(event.data).then(function(error, text, xhr) {
			    if (error) {
			        // alert('Error ' + xhr.status);
			        return;
			    }
			    eval(text);

			});


		},

		addEvent : function (element, evnt, funct) {
			if(element == null){
				if (window.attachEvent) {
					window.attachEvent('on' + evnt, funct);
				} else {
					window.addEventListener(evnt, funct, false);
				}
			}else{
				if (element.attachEvent){
					return element.attachEvent('on' + evnt, funct);
				}else if (element.addEventListener){
					return element.addEventListener(evnt, funct, false);
				}
			}
		},

		t : function (s, d) {
			// small template engine ;-)
			for ( var p in d)
				s = s.replace(new RegExp('{' + p + '}', 'g'), d[p]);
			return s;
		},

		setContextMenu :	function () {
			Core9.menu.addEvent(document.body, 'click', Core9.menu.removeContextMenu);
			var contextMenuElements = document.getElementsByClassName(Core9.menu.blockClassName()); // class
																										// for
																										// context
																										// menu
			for (var i = 0; i < contextMenuElements.length; i++) {
				Core9.menu.addEvent(contextMenuElements[i], 'contextmenu', Core9.menu.clicked);
			}
		},

		removeContextMenu :	function () {
			var contextMenu = document.getElementById('context-menu');
			if (contextMenu != null) {
				contextMenu.parentNode.removeChild(contextMenu);
			}
		},

		showMenu :	function (x, y) {
			Core9.menu.removeContextMenu();

			var menuObj = Core9.menu.t(Core9.menu.menu(), {
				insertBefore : "insert before",
				insertAfter : "insert after",
				switchblock : "verwissel",
				x : x,
				y : y
			});
			document.body.innerHTML += menuObj;
			Core9.menu.setContextMenu();
		},

		clicked :	function (event) {


			console.log('Block clicked ');
			console.log(event);


			var blocks = document.getElementsByClassName(Core9.menu.blockClassName());

			console.log(event);

			Core9.menu.selectedBlock = Core9.menu.getParentElementWithClass(event.target,Core9.menu.blockClassName());

			console.log(Core9.menu.selectedBlock);
			var current;
			for(var i = 0; i < blocks.length;i++)
			{
			   if(blocks[i] == Core9.menu.selectedBlock)
			   {
				   current = i;
				   Core9.menu.selectedBlockPosition = i;
			   }
			}

			console.log("Total nr : " + blocks.length);
			console.log("current : " + current);

			event.preventDefault();
			Core9.menu.showMenu(event.clientX, event.clientY);
		}
}



Core9.menu.addEvent(null, "message", Core9.menu.listener, false)
Core9.menu.addEvent(null, "onmessage", Core9.menu.listener)

// document.body.innerHTML += '<div id="test">Send me a message!</div>';
Core9.menu.setContextMenu();
