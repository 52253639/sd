package org.bardsoftware.impl.eclipsito;

import java.util.logging.Level;

import org.bardsoftware.eclipsito.Boot;
import org.bardsoftware.impl.eclipsito.PluginDescriptor.ExtensionPointDescriptor;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IModel;
import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.Platform;

public class MyApplicationLauncher {

    static final ExtensionPointDescriptor ourRuntimeApplicationExtensionPoint = 
        new ExtensionPointDescriptor(IModel.PI_APPLICATIONS, IModel.PI_RUNTIME, null, null);
    
    static final String ourAppsPointId = ourRuntimeApplicationExtensionPoint.getId();

    public static void launchApplication(String applicationId, String[] commandLineArgs) {
        IConfigurationElement runElement = findAndValidateConfigForApplication(applicationId);
        if (runElement == null) {
            throw new IllegalArgumentException("Could not launch application "+applicationId);
        }
        try {
            IPlatformRunnable runnable = (IPlatformRunnable) runElement.createExecutableExtension(IModel.PI_CLASS);
//            runnable.run(commandLineArgs);
        } catch (Exception e) {
            Boot.LOG.log(Level.WARNING, e.getMessage(), e);
        }
    }
    
    private static IConfigurationElement findAndValidateConfigForApplication(String applicationId) {
        IConfigurationElement result = null;
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        if (registry != null) {
            IExtensionPoint point = registry.getExtensionPoint(ourAppsPointId);
            if (point != null) {
                IExtension[] applications = point.getExtensions();
                for (int i=0; applications != null && i<applications.length; i++) {
                    if (applications[i].getUniqueIdentifier().equals(applicationId)) {
                        IConfigurationElement[] appElements = applications[i].getConfigurationElements();
                        if (appElements != null && appElements.length > 0 && IModel.PI_APP.equals(appElements[0].getName())) {
                            IConfigurationElement[] runElements = appElements[0].getChildren(IModel.PI_RUN);
                            if (runElements != null) {
                                if (runElements.length == 1) {
                                    result = runElements[0];
                                } else {
                                    throw new IllegalStateException("Application "+applicationId+" must have one <run> element");
                                }
                            }
                        } else {
                            throw new IllegalStateException("Extension "+applicationId+" must have one <application> element");
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }
}
