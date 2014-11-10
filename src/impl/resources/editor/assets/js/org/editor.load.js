(function(o){var K=o.$LAB,y="UseLocalXHR",z="AlwaysPreserveOrder",u="AllowDuplicates",A="CacheBust",B="BasePath",C=/^[^?#]*\//.exec(location.href)[0],D=/^\w+\:\/\/\/?[^\/]+/.exec(C)[0],i=document.head||document.getElementsByTagName("head"),L=(o.opera&&Object.prototype.toString.call(o.opera)=="[object Opera]")||("MozAppearance"in document.documentElement.style),q=document.createElement("script"),E=typeof q.preload=="boolean",r=E||(q.readyState&&q.readyState=="uninitialized"),F=!r&&q.async===true,M=!r&&!F&&!L;function G(a){return Object.prototype.toString.call(a)=="[object Function]"}function H(a){return Object.prototype.toString.call(a)=="[object Array]"}function N(a,c){var b=/^\w+\:\/\//;if(/^\/\/\/?/.test(a)){a=location.protocol+a}else if(!b.test(a)&&a.charAt(0)!="/"){a=(c||"")+a}return b.test(a)?a:((a.charAt(0)=="/"?D:C)+a)}function s(a,c){for(var b in a){if(a.hasOwnProperty(b)){c[b]=a[b]}}return c}function O(a){var c=false;for(var b=0;b<a.scripts.length;b++){if(a.scripts[b].ready&&a.scripts[b].exec_trigger){c=true;a.scripts[b].exec_trigger();a.scripts[b].exec_trigger=null}}return c}function t(a,c,b,d){a.onload=a.onreadystatechange=function(){if((a.readyState&&a.readyState!="complete"&&a.readyState!="loaded")||c[b])return;a.onload=a.onreadystatechange=null;d()}}function I(a){a.ready=a.finished=true;for(var c=0;c<a.finished_listeners.length;c++){a.finished_listeners[c]()}a.ready_listeners=[];a.finished_listeners=[]}function P(d,f,e,g,h){setTimeout(function(){var a,c=f.real_src,b;if("item"in i){if(!i[0]){setTimeout(arguments.callee,25);return}i=i[0]}a=document.createElement("script");if(f.type)a.type=f.type;if(f.charset)a.charset=f.charset;if(h){if(r){e.elem=a;if(E){a.preload=true;a.onpreload=g}else{a.onreadystatechange=function(){if(a.readyState=="loaded")g()}}a.src=c}else if(h&&c.indexOf(D)==0&&d[y]){b=new XMLHttpRequest();b.onreadystatechange=function(){if(b.readyState==4){b.onreadystatechange=function(){};e.text=b.responseText+"\n//@ sourceURL="+c;g()}};b.open("GET",c);b.send()}else{a.type="text/cache-script";t(a,e,"ready",function(){i.removeChild(a);g()});a.src=c;i.insertBefore(a,i.firstChild)}}else if(F){a.async=false;t(a,e,"finished",g);a.src=c;i.insertBefore(a,i.firstChild)}else{t(a,e,"finished",g);a.src=c;i.insertBefore(a,i.firstChild)}},0)}function J(){var l={},Q=r||M,n=[],p={},m;l[y]=true;l[z]=false;l[u]=false;l[A]=false;l[B]="";function R(a,c,b){var d;function f(){if(d!=null){d=null;I(b)}}if(p[c.src].finished)return;if(!a[u])p[c.src].finished=true;d=b.elem||document.createElement("script");if(c.type)d.type=c.type;if(c.charset)d.charset=c.charset;t(d,b,"finished",f);if(b.elem){b.elem=null}else if(b.text){d.onload=d.onreadystatechange=null;d.text=b.text}else{d.src=c.real_src}i.insertBefore(d,i.firstChild);if(b.text){f()}}function S(c,b,d,f){var e,g,h=function(){b.ready_cb(b,function(){R(c,b,e)})},j=function(){b.finished_cb(b,d)};b.src=N(b.src,c[B]);b.real_src=b.src+(c[A]?((/\?.*$/.test(b.src)?"&_":"?_")+~~(Math.random()*1E9)+"="):"");if(!p[b.src])p[b.src]={items:[],finished:false};g=p[b.src].items;if(c[u]||g.length==0){e=g[g.length]={ready:false,finished:false,ready_listeners:[h],finished_listeners:[j]};P(c,b,e,((f)?function(){e.ready=true;for(var a=0;a<e.ready_listeners.length;a++){e.ready_listeners[a]()}e.ready_listeners=[]}:function(){I(e)}),f)}else{e=g[0];if(e.finished){j()}else{e.finished_listeners.push(j)}}}function v(){var e,g=s(l,{}),h=[],j=0,w=false,k;function T(a,c){a.ready=true;a.exec_trigger=c;x()}function U(a,c){a.ready=a.finished=true;a.exec_trigger=null;for(var b=0;b<c.scripts.length;b++){if(!c.scripts[b].finished)return}c.finished=true;x()}function x(){while(j<h.length){if(G(h[j])){try{h[j++]()}catch(err){}continue}else if(!h[j].finished){if(O(h[j]))continue;break}j++}if(j==h.length){w=false;k=false}}function V(){if(!k||!k.scripts){h.push(k={scripts:[],finished:true})}}e={script:function(){for(var f=0;f<arguments.length;f++){(function(a,c){var b;if(!H(a)){c=[a]}for(var d=0;d<c.length;d++){V();a=c[d];if(G(a))a=a();if(!a)continue;if(H(a)){b=[].slice.call(a);b.unshift(d,1);[].splice.apply(c,b);d--;continue}if(typeof a=="string")a={src:a};a=s(a,{ready:false,ready_cb:T,finished:false,finished_cb:U});k.finished=false;k.scripts.push(a);S(g,a,k,(Q&&w));w=true;if(g[z])e.wait()}})(arguments[f],arguments[f])}return e},wait:function(){if(arguments.length>0){for(var a=0;a<arguments.length;a++){h.push(arguments[a])}k=h[h.length-1]}else k=false;x();return e}};return{script:e.script,wait:e.wait,setOptions:function(a){s(a,g);return e}}}m={setGlobalDefaults:function(a){s(a,l);return m},setOptions:function(){return v().setOptions.apply(null,arguments)},script:function(){return v().script.apply(null,arguments)},wait:function(){return v().wait.apply(null,arguments)},queueScript:function(){n[n.length]={type:"script",args:[].slice.call(arguments)};return m},queueWait:function(){n[n.length]={type:"wait",args:[].slice.call(arguments)};return m},runQueue:function(){var a=m,c=n.length,b=c,d;for(;--b>=0;){d=n.shift();a=a[d.type].apply(null,d.args)}return a},noConflict:function(){o.$LAB=K;return m},sandbox:function(){return J()}};return m}o.$LAB=J();(function(a,c,b){if(document.readyState==null&&document[a]){document.readyState="loading";document[a](c,b=function(){document.removeEventListener(c,b,false);document.readyState="complete"},false)}})("addEventListener","DOMContentLoaded")})(this);

var baseUrl = "/ui-widgets/editor/";

$LAB
		.setOptions({
			AlwaysPreserveOrder : true
		})
		.script(baseUrl + "js/jquery.js")
		.script(baseUrl + "js/bootstrap.min.js")
		.script(
				"/static/plugins/editor/server/assets/plugins/sceeditor/minified/jquery.sceditor.bbcode.min.js")
		.script(
				"/static/plugins/editor/server/assets/plugins/sceeditor/minified/jquery.sceditor.xhtml.min.js")
		.script(
				"/static/plugins/editor/server/assets/plugins/select2/select2.min.js")
		.script("/static/plugins/editor/server/assets/js/jsoneditor.min.js")
		.script("/static/plugins/editor/server/assets/js/promise.min.js")
		.script("/static/plugins/editor/server/assets/js/iframeResizer.min.js")
		.script("/static/plugins/editor/server/assets/js/wizard-engine.js")
		.script(baseUrl + "js/editor.load.css.js")
		.wait(
				function() {

					console.log(EditorConfig);

					JSONEditor.defaults.theme = 'bootstrap3';
					JSONEditor.defaults.iconlib = 'fontawesome4';
					JSONEditor.plugins.sceditor = {
						toolbar : "bold,italic,underline,strike,subscript,superscript|left,center,right,justify|font,size,color,removeformat|table|source",
						width : "560px",
					};
					JSONEditor.plugins.select2.width = "300px";


					Wizard.init(EditorConfig);

					iFrameResize({
						log : false,
						enablePublicMethods : true,
						resizedCallback : function(messageData) {
							console.log(messageData);
						},
						messageCallback : function(messageData) {
							var data = JSON.parse(messageData.message);
							if (data.action == 'edit-block') {
								window.location = "#state=edit-block-"
										+ data.block + "-type-" + data.type;
								$('#wizard-wrapper').modal("show");
								$('#modal').toggle();
							}
							if (data.action == 'insertbefore-block') {
								window.location = "#state=insertbefore-block-"
										+ data.block + "-type-" + data.type;
								$('#wizard-wrapper').modal("show");
								$('#modal').toggle();
							}
							if (data.action == 'insertafter-block') {
								window.location = "#state=insertafter-block-"
										+ data.block + "-type-" + data.type;
								$('#wizard-wrapper').modal("show");
								$('#modal').toggle();
							}
							if (data.action == 'delete-block') {
								window.location = "#state=delete-block-"
										+ data.block + "-type-" + data.type;
								$('#delete-form').toggle();
								$('#modal').toggle();
							}
						},
						closedCallback : function(id) {
							console.log(id);
						}
					});

					$('#delete-form').click(
							function() {

								var hash = location.hash.split('=');
								var data = hash[1].split('-');
								var tpl = "";
								try {
									tpl = Wizard.widgetJson[data[4]].template;
								} catch (e) {

								}
								var meta = {
									"absolute-url" : document.getElementById(
											'iframe').getAttribute('src'),
									"state" : data[0],
									"block" : data[2],
									"type" : data[4],
									"template" : tpl
								}
								console.log(meta);

								var fullData = {
									"meta" : meta
								}

								var jsonString = JSON.stringify(fullData);

								var data = {
									"id" : 112,
									"data" : jsonString
								};
								promise.post('/api/block', data)
										.then(
												function(error, text, xhr) {
													if (error) {
														return;
													}
													console.log(text);
													location = location.href
															.split('#')[0];
												});

							});

					if (typeof String.prototype.startsWith != 'function') {
						String.prototype.startsWith = function(str) {
							return this.indexOf(str) == 0;
						};
					}

					if (location.hash.startsWith("#state=edit-block-")) {
						$('#wizard-wrapper').modal("show");
						$('#modal').toggle();
					}

					$('.close-modal').click(function() {
						location = location.href.split('#')[0];
					});

					$('#variants').click(function() {
						$('#page-selector').toggle();
					});

					initMyPage(baseUrl);
				});
