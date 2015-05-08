package com.kingdee.eas.fdc.schedule.framework.ext.plugins;

import java.io.InputStream;
import java.net.URI;

import org.bardsoftware.eclipsito.Boot;
import org.bardsoftware.impl.eclipsito.MyApplicationLauncher;
import org.bardsoftware.impl.eclipsito.MyDescriptorParser;
import org.bardsoftware.impl.eclipsito.PluginDescriptor;

public class SchedulePluginManager {
	private static boolean hasResolve=false; 
	public static void resolvePlugins(){
		if(hasResolve){
			return;
		}
		try {
			innerResolvePlugins();
			hasResolve=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static PlatformImplExt platform=new PlatformImplExt(null); 
	private static void innerResolvePlugins() throws Exception{
/*		URI home=new URI("file:/C:/");
		platform=new PlatformImplExt(home);*/
		ShutdownHook.install();
		platform.setup(getPluginDescriptors());
		platform.start();
		String applicationId="net.sourceforge.ganttproject.GanttProject";
		String []commandLineArgs=new String[]{"-log","-open"};
		MyApplicationLauncher.launchApplication(applicationId, commandLineArgs);
//		GanttProject.main(commandLineArgs);
/*		GanttProject ganttFrame = new GanttProject(false);
		ganttFrame.setVisible(true);
		platform.stop();
*/	}
	private static PluginDescriptor[] getPluginDescriptors() throws Exception{
//		URL pluginDescriptorUrl=GanttProject.class.getResource("plugin.xml");
//		URL pluginDescriptorUrl=new URL("file:/H:/workspace_work/MyGanttPrj/lib/plugin.xml");
//		PluginDescriptor plugin = DescriptorParser.parse(pluginDescriptorUrl);
		InputStream is =SchedulePluginManager.class.getResourceAsStream("GanttProjectPlugin.xml");// GanttProject.class.getResourceAsStream("plugin.xml");
		InputStream is2 =SchedulePluginManager.class.getResourceAsStream("GanttPertPlugin.xml");// GanttProject.class.getResourceAsStream("plugin.xml");
		PluginDescriptor[] pluginDescriptors=null;
		try{
			
			PluginDescriptor plugin = MyDescriptorParser.parse(is);
			PluginDescriptor plugin2 = MyDescriptorParser.parse(is2);
			pluginDescriptors = new PluginDescriptor[]{plugin,plugin2};
			
		}finally{	
			if(is!=null){
				is.close();
			}
			if(is2!=null){
				is2.close();
			}
		}
		return pluginDescriptors;
	}
	
    private static void shutdown() {
    	platform.stop();
        Boot.LOG.info("Eclipsito platform is shut down.");
    }

    private static class ShutdownHook extends Thread {
        public ShutdownHook() {
//            super(topThreadGroup, "ShutdownHook-Thread");
            setPriority(Thread.MAX_PRIORITY);
        }

        public void run() {
        	shutdown();
        }

        public static void install() {
            Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        }
    }
}
