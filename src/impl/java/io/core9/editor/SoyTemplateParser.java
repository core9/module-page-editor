package io.core9.editor;

import java.io.File;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;

public class SoyTemplateParser {

	 public static void main (String[] args) {

		    // Bundle the Soy files for your project into a SoyFileSet.
		    SoyFileSet sfs = new SoyFileSet.Builder().add(new File("/var/www/core9-base-theme/src/iframe/wizards/video/video.soy")).build();

		    // Compile the template into a SoyTofu object.
		    // SoyTofu's newRenderer method returns an object that can render any template in the file set.
		    SoyTofu tofu = sfs.compileToTofu();

		    // Render the template with no data.
		    System.out.println(tofu.newRenderer("examples.simple.helloWorld").render());
		  }


}
