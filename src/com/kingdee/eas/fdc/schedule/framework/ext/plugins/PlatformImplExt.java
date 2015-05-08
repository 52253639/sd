package com.kingdee.eas.fdc.schedule.framework.ext.plugins;

import java.net.URI;

import org.bardsoftware.impl.eclipsito.PlatformImpl;
import org.bardsoftware.impl.eclipsito.PluginDescriptor;

public class PlatformImplExt extends PlatformImpl {

	public PlatformImplExt(URI home) {
		super(home);
		
	}
	
	protected void setup(PluginDescriptor[] descriptors) {
		super.setup(descriptors);
	}

}
