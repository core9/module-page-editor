var Wizard = {
	json : {},
	getStep : function(step, label) {
		var li = document.createElement("li");
		li.setAttribute("class", "step");
		var a = document.createElement("a");
		a.setAttribute("href", "#");
		a.appendChild(document.createTextNode(label));
		li.appendChild(a);
		var div = document.createElement("div");
		div.setAttribute("class", "main-container form-container");
		var innerDiv = document.createElement("div");
		innerDiv.setAttribute("id", "editor_holder-" + step);
		div.appendChild(innerDiv);
		var button = document.createElement("button");
		button.setAttribute("id", "submit-" + step);
		button.appendChild(document.createTextNode("Submit"));
		div.appendChild(button);
		li.appendChild(div);
		return li;
	},

	getScript : function(script, step) {
		$LAB.script(script).wait(function() {
			init(step);
		});
	},

	goToNextStep : function(event){
  	  var selectedLi = Wizard.getParentElementWithClass(event.target, 'step');
	  Wizard.hideAllDivs();
	  selectedLi.nextSibling.querySelector('.main-container').style.display = "block";
	},

	createWizard : function(steps) {
		var ul = document.getElementById('tab-ul');
		for ( var step in steps) {
			var script = steps[step].file;
			ul.appendChild(Wizard.getStep(step, steps[step].label));
			Wizard.getScript(script, step);
		}
		Wizard.hideAllDivs();
		Wizard.showChooseDiv();
	},

	showChooseDiv : function() {
		document.getElementById('choose-div').style.display = "block";
	},

	hideAllDivs : function() {
		var divs = document.getElementsByClassName('main-container');
		for ( var div in divs) {
			var elem = divs[div];
			if (elem.tagName == 'DIV') {
				elem.style.display = "none";
			}
		}
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

	endsWith : function(str, suffix) {
		return str.indexOf(suffix, str.length - suffix.length) !== -1;
	},

	activateWidget : function(widget, widgets) {
		console.log('activating : ' + widget);
		var stepFile = widgets[widget].steps;
		if (!Wizard.endsWith(stepFile, ".json")) {
			console.log("Oops wrong file : " + stepFile);
			return;
		}

		promise.get(stepFile).then(function(error, text, xhr) {
			if (error) {
				alert('Error ' + xhr.status);
				return;
			}
			Wizard.createWizard(JSON.parse(text))
		});

		document.getElementById('tab-ul')
				.addEventListener(
						"click",
						function(e) {
							if (e.target.tagName != 'A') {
								return;
							}
							Wizard.hideAllDivs();
							var elem = e.target.parentNode
									.getElementsByTagName('div')[0];
							if (elem !== undefined && elem.tagName == 'DIV') {
								elem.style.display = "block";
							}
						});

	},

	activateChooseButtons : function(json) {
		var button = document.getElementById("choose-button");
		button.addEventListener("click", function(event) {
			Wizard.activateWidget(document.getElementById("data-list").value,
					json);
		}, false);

	},

	setUpChooseOptions : function(json) {
		var options = '';
		for ( var key in json) {
			if (json.hasOwnProperty(key)) {
				options += '<option value="' + key + '" />';
			}
		}
		document.getElementById('widgets').innerHTML = options;
	},

	init : function(config) {
		promise.get(config.widgets).then(function(error, text, xhr) {
			if (error) {
				return;
			}
			var json = JSON.parse(text);
			Wizard.json = json;
			Wizard.setUpChooseOptions(json);
			Wizard.activateChooseButtons(json);
		});
	}

}
