package org.nocket.gen.domain;

import gengui.domain.AbstractDomainReference;
import gengui.domain.DomainClassReference;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.nocket.gen.domain.NocketGenerator.GenerationContext.FileData;
import org.nocket.gen.domain.ref.DomainClassReferenceFactory;
import org.nocket.gen.domain.ref.DomainReferenceFactoryI;
import org.nocket.gen.domain.visitor.NocketGenerationVisitor;
import org.nocket.gen.domain.visitor.DomainElementVisitorI;
import org.nocket.util.ArgReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NocketGenerator {

	final private static Logger log = LoggerFactory.getLogger(NocketGenerator.class);

	public static class GenerationProcessDMDWebGenContext<E extends AbstractDomainReference> extends DMDWebGenContext<E> {

		private final GenerationContext generationContext;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public GenerationProcessDMDWebGenContext(Boolean generatePanel, String srcDir, String genDir, LayoutStrategy layoutStrategy,
				DomainReferenceFactoryI refFactory, GenerationContext generationContext) {
			super(generatePanel, srcDir, genDir, layoutStrategy, refFactory);
			this.generationContext = generationContext;
		}

		public GenerationContext getGenerationContext() {
			return generationContext;
		}
	}

	public static class GenerationContext {

		List<FileData> fileDataList = new ArrayList<NocketGenerator.GenerationContext.FileData>();

		class FileData {
			String fileName;
			boolean changed;
			String error;
			boolean newFile;

			public FileData(String fileName, boolean newFile, boolean changed, String error) {
				this.fileName = fileName;
				this.newFile = newFile;
				this.changed = changed;
				this.error = error;
			}
		}

		public void addFileData(String fileName, boolean newFile, boolean changed, String error) {
			fileDataList.add(new FileData(fileName, newFile, changed, error));
		}

		public boolean hasErrors() {
			for (FileData element : fileDataList) {
				if (StringUtils.isNotBlank(element.error)) {
					return true;
				}
			}
			return false;
		}
	}


	public void run(String[] args) {
		LayoutStrategy layoutStrategy = LayoutStrategy.BOOTSTRAP;
		String sourceDir = null;
		String genDir = null;
		Boolean generatePanel = false;
		char option = ArgReader.ARGEND;
		ArgReader argReader = new ArgReader(args, "t:s:g:y:h");

		do {
			try {
				switch (option = argReader.getArg()) {
				case 'y':
					layoutStrategy = LayoutStrategy.valueOf(argReader.getArgValue().trim().toUpperCase());
					break;
				case 'h':
					printHelp();
					break;
				case 's':
					sourceDir = argReader.getArgValue().trim();
					break;
				case 'g':
					genDir = argReader.getArgValue().trim();
					break;
				case 't':
					generatePanel = argReader.getArgValue().trim().equalsIgnoreCase("panel");
					break;
				}
			} catch (IllegalArgumentException ax) {
				System.err.println(ax.getMessage());
				printHelp();
				return;
			}
		} while (option != ArgReader.ARGEND);

		if (argReader.getPendingArgs().length == 0) {
			System.err.println("Illegal arguments");
			printHelp();
			return;
		}

		//
		new WebDomainProperties().init();

		GenerationContext generationContext = new GenerationContext();

		for (String clazz : argReader.getPendingArgs()) {
			try {
				Class<?> domainClass = Class.forName(clazz);
				if (MarkupContainer.class.isAssignableFrom(domainClass)) {
					log.error("Ignoring: " + clazz + "! It is a MarkupContainer not a Pojo!");
					continue;
				}
				generateHTML(domainClass, generatePanel, sourceDir, genDir, layoutStrategy, generationContext);
			} catch (Throwable e) {
				if (log.isErrorEnabled()) {
					log.error(e.getMessage(), e);
				} else {
					e.printStackTrace();
				}
			}
		}

		printStatistic(generationContext);

		if (generationContext.hasErrors()) {
			// if an error occured, set exit code to -1
			System.exit(-1);
		}
	}

	private void printStatistic(GenerationContext generationContext) {
		StringBuilder builder = new StringBuilder("Generation statistic:\r\n");
		builder.append("jfd.retention.strategy=").append(new WebDomainProperties().getJFDRetentionStrategy()).append("\r\n");

		if (generationContext.fileDataList.isEmpty()) {
			builder.append("No files processed.");
			log.info(builder.toString());
			return;
		}

		int changed = 0;
		int unchanged = 0;
		int errorCount = 0;
		int newFiles = 0;

		StringBuilder errorBuilder = new StringBuilder("The following errors occured during generation process:\r\n");
		for (FileData filedata : generationContext.fileDataList) {
			if (StringUtils.isNotBlank(filedata.error)) {
				errorCount++;
				errorBuilder.append(filedata.error).append("\r\n");
			} else {
				changed += filedata.changed ? 1 : 0;
				unchanged += filedata.changed ? 0 : 1;
				newFiles += filedata.newFile ? 1 : 0;
			}
		}

		builder.append("Number of new files: " + newFiles + "\r\n");
		builder.append("Number of changed files: " + changed + "\r\n");
		builder.append("Number of unchanged files: " + unchanged + "\r\n");
		builder.append("Number of files with error: " + errorCount + "\r\n");

		if (errorCount > 0) {
			builder.append(errorBuilder);
		}
		if (log.isInfoEnabled()) {
			log.info(builder.toString());
		} else {
			System.out.println(builder.toString());
		}
	}

	public void generateHTML(Class<?> domainClass, Boolean generatePanel, String sourceDir, String genDir, LayoutStrategy layoutStrategy,
			GenerationContext generationContext) {
		DomainClassReferenceFactory refFactory = new DomainClassReferenceFactory(domainClass, false);

		DMDWebGenContext<DomainClassReference> context;
		if (generationContext != null) {
			// If there is a need for some statistic, an the specialized
			// DMDWebGenContext will be used
			context = new GenerationProcessDMDWebGenContext<DomainClassReference>(generatePanel, sourceDir, genDir, layoutStrategy,
					refFactory, generationContext);
		} else {
			context = new DMDWebGenContext<DomainClassReference>(generatePanel, sourceDir, genDir, layoutStrategy, refFactory);
		}

		new DomainProcessor<DomainClassReference>(context, new MultiPassStrategy<DomainClassReference>() {

			@Override
			public DomainElementVisitorI<DomainClassReference> createVisitor(DMDWebGenContext<DomainClassReference> _dmdWebGenContext) {
				return new NocketGenerationVisitor<DomainClassReference>(_dmdWebGenContext);
			}

		}).process();
	}

	private static void printHelp() {
		System.err.println("Usage:\tjava " + NocketGenerator.class.getName()
			+ " -s <SRC_DIR> -g <GEN_DIR> [-t (page | panel)] [-y (bootstrap | table)] [-l <CSS_FILE>]* <JAVA_CLASS_1> (<JAVA_CLASS_2> ... <JAVA_CLASS_n>)");
		System.err.println("Params:\t-s\tThe directory where HTML-Output will be written into package specific directories. Usually the \"src\" directory of the project.");
		System.err.println("\t-g\tThe directory where Constants-Class-Output will be written into package specific directories. Usually the \"gen\" directory of the project.");
		System.err.println("\t-t\tThe type of HTML artefact to create. By default, the generator tries to derive the type from existing Page/Panel classes. Pages are the fallback.");
		System.err.println("\t-y\tSpecifies the kind of mask layouting the generated HTML is prepared for.");
		System.err.println("\t...\tFQDN to java classes for which the html should be generated. The classes have to be in the classpath. Usually something like \"some.package.SomeType\".");
		System.err.println("Help:\tThis tool loads the given java classes and uses reflection to parse them in order to create html output for them.");
		System.err.println("\tThe output files are generated into the package folder of the given java class.");
		System.err.println("\tFiles that are written to SRC_DIR are merged with fields that have been added to the specific java class.");
		System.err.println("\tFiles that are written to GEN_DIR are always overwritten if they already exist.");
	}
	
	public static void main(String[] args) {
		new NocketGenerator().run(args);
	}


}
