function init(step){

promise.get('../clients/easydrain/wizards/video/data/video.json').then(
				function(error, text, xhr) {
					if (error) {
						alert('Error ' + xhr.status);
						return;
					}
					var starting_value = [ text ];
					// Initialize the editor
					var editor = new JSONEditor(document
							.getElementById('editor_holder-' + step), {
						// Enable fetching schemas via ajax
						ajax : true,
						// The schema for the editor
						schema : {
							type : "object",
							title : "Video",
							  "properties": {
							    "url": {
							      "type": "string",
							      "default": "http://youtu.be/kDfw4yt554g",
							    },
							    "height": {
							      "type": "string",
							      "default": "315",
							    },
							    "width": {
								      "type": "string",
								      "default": "560",
								},
							  }
						},
						// Seed the form with a starting value
						startval : starting_value,
						// Disable additional properties
						no_additional_properties : true,
						disable_edit_json : true,
						disable_properties : true,
						disable_collapse : true,
						// Require all properties by default
						required_by_default : true
					});
				      // Hook up the submit button to log to the console
				      document.getElementById('submit-' + step).addEventListener('click',function(event) {
				    	  event.stopPropagation();
				    	  console.log(editor.validate());
				    	  Wizard.goToNextStep(event);
				    	  console.log('sending to swagger api');
				    	  console.log(editor.getValue());
				      });

				});

}