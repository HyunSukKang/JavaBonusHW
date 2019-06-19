package edu.handong.csee.bonus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class LSCommand {
	
	boolean a;
	boolean t;
	boolean s;
	boolean f;
	boolean d;
	boolean help;
	
	public void run(String[] args) {
		Options options = createOptions();
		if(parseOptions(options, args)){
			if (help) {
				printHelp(options);
				return;
			}
			
			try {
				if(!args[0].equals("ls")) {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("There is no command \"" + args[0] + "\".");
				System.exit(0);
			}
			
			String pwd = System.getProperty("user.dir");
			
			File file = new File(pwd);
			String[] files = file.list();
			File[] files1 = file.listFiles();
			
			if(a) {
				if(t) {
					for(File f : files1) {
						String d = new SimpleDateFormat("MMM dd hh:mm").format(
								new Date(f.lastModified())
						);
						System.out.println(f.getName() + " " + d);
					}
				}
				else if(s) {
					Arrays.sort(files);
					for(String s : files) System.out.println(s);
				}
				else if (f && d) {
					for(File f : files1) {
						if(f.isFile()) System.out.println(f.getName() + ", Type : File");
						else if(f.isDirectory()) System.out.println(f.getName() + ", Type : Directory");
					}
				}
				else if(f) {
					for(File f : files1) if(f.isFile()) System.out.println(f.getName());
				}
				else if(d) {
					for(File f : files1) if(f.isDirectory()) System.out.println(f.getName());
				}
				else for(String s : files) System.out.println(s);
			}
			else if(t) {
				for(File f : files1) {
					String d = new SimpleDateFormat("MMM dd hh:mm").format(
							new Date(f.lastModified())
					);
					if(!f.getName().startsWith(".")) {
						System.out.println(f.getName() + " " + d);
					}
				}
			}
			else if(s) {
				Arrays.sort(files);
				for(String s : files) if(!s.startsWith(".")) System.out.println(s);
			}
			else if(d && f) {
				for(File f : files1) {
					if(f.isFile()) {
						if(!f.getName().startsWith(".")) {
							System.out.println(f.getName() + ", Type : File");
						}
					}
					else if(f.isDirectory()) {
						if(!f.getName().startsWith(".")) {
							System.out.println(f.getName() + ", Type : Directory");
						}
					}
				}
			}
			else if(f) {
				for(File f : files1) {
					if(f.isFile()) {
						if(!f.getName().startsWith(".")) {
							System.out.println(f.getName());
						}
					}
				}
			}
			else if(d) {
				for(File f : files1) {
					if(f.isDirectory()) {
						if(!f.getName().startsWith(".")) {
							System.out.println(f.getName());
						}
					}
				}
			}
			else {
				for(String s : files) if(!s.startsWith(".")) System.out.println(s);
			}
		}
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);

			a = cmd.hasOption("a");
			t = cmd.hasOption("t");
			s = cmd.hasOption("s");
			f = cmd.hasOption("f");
			d = cmd.hasOption("d");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("a").longOpt("all")
				.desc("List the whole files in directory include hidden files")
				.argName("aOption")
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("t").longOpt("time")
				.desc("List the files with time")
				.argName("tOption")
				.build());
		
		options.addOption(Option.builder("s").longOpt("sort")
				.desc("List the files sorted by name")
				.argName("sOption")
				.build());
		
		options.addOption(Option.builder("f").longOpt("file")
				.desc("List only the files")
				.argName("fOption")
				.build());
		
		options.addOption(Option.builder("d").longOpt("directory")
				.desc("List only the directories")
				.argName("dOption")
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.argName("Help")
				.build());
		
		
		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "ls Command Implementation";
		String footer ="";
		formatter.printHelp("JavaBonusHW", header, options, footer, true);
	}
}
