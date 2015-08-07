package org.bardsoftware.impl.eclipsito;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.bardsoftware.eclipsito.Boot;
import org.eclipse.core.runtime.IModel;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MyDescriptorParser {
   
    private static DocumentBuilder myDocumentBuilder;

    static {
        try {
            myDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // bad! Have to delegate somebody exception handling in this situation
        } catch (ParserConfigurationException e) {
            Boot.LOG.log(Level.SEVERE, e.getMessage(), e);
        } catch (FactoryConfigurationError e) {
            Boot.LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static PluginDescriptor parse(URL pluginDescriptorUrl) {
        PluginDescriptor result = null;
        try {
            System.err.println("[DescriptorParser] parse(): plugin descriptor url="+pluginDescriptorUrl);
            Element root = myDocumentBuilder.parse(pluginDescriptorUrl.openStream()).getDocumentElement();
            result = constructPluginDescriptor(root, pluginDescriptorUrl);
        } catch (Exception e) {
            Boot.LOG.log(Level.WARNING, "Exception happened while parsing "+pluginDescriptorUrl+", ignoring this plugin", e);
        }
        return result;
    }
    
    public static PluginDescriptor parse(InputStream is) {
        PluginDescriptor result = null;
        try {
//            System.err.println("[DescriptorParser] parse(): plugin descriptor url="+is.toString());
            Element root = myDocumentBuilder.parse(is).getDocumentElement();
            URL url=new URL("file://C:");
            result = constructPluginDescriptor(root, url);
        } catch (Exception e) {
//            Boot.LOG.log(Level.WARNING, "Exception happened while parsing "+pluginDescriptorUrl+", ignoring this plugin", e);
        }
        return result;
    }

    protected static PluginDescriptor constructPluginDescriptor(Element pluginElement, URL pluginDescriptorUrl) {
        PluginDescriptor result = null;
        if (pluginElement != null && IModel.PLUGIN.equals(pluginElement.getTagName())) {
            result = new PluginDescriptor(pluginDescriptorUrl);
            handlePluginAttributes(pluginElement, result);
            handleRequiresElements(pluginElement.getElementsByTagName(IModel.PLUGIN_REQUIRES), result);
            handleRuntimeElements(pluginElement.getElementsByTagName(IModel.RUNTIME), result);
            handleExtensionElements(pluginElement.getElementsByTagName(IModel.EXTENSION), result);
            handleExtensionPointElements(pluginElement.getElementsByTagName(IModel.EXTENSION_POINT), result);
        } else {
            Boot.LOG.log(Level.WARNING, "Incorrect plugin descriptor format for "+pluginDescriptorUrl.getPath()+
                    ", ignoring this plugin");
        }
        return result;
    }

    private static void handlePluginAttributes(Element pluginElement, PluginDescriptor pluginDescriptor) {
        String requiredId = pluginElement.getAttribute(IModel.PLUGIN_ID);
        assertAttributeIsNotEmpty(pluginDescriptor,requiredId,IModel.PLUGIN_ID);
        String requiredName = pluginElement.getAttribute(IModel.PLUGIN_NAME);
        assertAttributeIsNotEmpty(pluginDescriptor,requiredName,IModel.PLUGIN_NAME);
        String requiredVersion = pluginElement.getAttribute(IModel.PLUGIN_VERSION);
        assertAttributeIsNotEmpty(pluginDescriptor,requiredVersion,IModel.PLUGIN_VERSION);
        pluginDescriptor.setName(requiredName);
        pluginDescriptor.setId(requiredId);
        pluginDescriptor.setVersion(requiredVersion);
        pluginDescriptor.setProviderName(pluginElement.getAttribute(IModel.PLUGIN_PROVIDER));
        pluginDescriptor.setClassName(pluginElement.getAttribute(IModel.PLUGIN_CLASS));
    }
    
    private static void assertAttributeIsNotEmpty(PluginDescriptor descriptor, String attribute, String attrName) {
        if("".equals(attribute.trim())) {
            throw new IllegalArgumentException("Descriptor of plugin="+descriptor.getLocation()+" is missing required attribute="+attrName);
        }
    }
    private static void handleRequiresElements(NodeList requiresElements, PluginDescriptor pluginDescriptor) {
        if (requiresElements != null && requiresElements.getLength() != 0) {
            if (requiresElements.getLength() != 1) {
                throw new IllegalArgumentException("There can be only one <requires> element in "+pluginDescriptor.myLocationUrl);
            }
            Element element = (Element) requiresElements.item(0);
            NodeList imports = element.getElementsByTagName(IModel.PLUGIN_REQUIRES_IMPORT);
            if (imports == null || imports.getLength() < 1) {
                throw new IllegalArgumentException("<requires> element must have 1+ <import> in "+pluginDescriptor.myLocationUrl);
            }
            for (int i=0; imports != null && i<imports.getLength(); i++) {
                Element imported = (Element) imports.item(i);
                String requiredPluginId = imported.getAttribute(IModel.PLUGIN_REQUIRES_PLUGIN);
                // dependency for <org.eclipse.core.runtime> will be added later by default 
                if (!IModel.PI_RUNTIME.equals(requiredPluginId)) {
                    pluginDescriptor.addRequiredPluginId(requiredPluginId);
                }
            }
        }
    }

    private static void handleRuntimeElements(NodeList runtimeElements, PluginDescriptor pluginDescriptor) {
        if (runtimeElements != null && runtimeElements.getLength() != 0) {
            if (runtimeElements.getLength() != 1) {
                throw new IllegalArgumentException("There can be only one <runtime> element in "+pluginDescriptor.myLocationUrl);
            }
            Element element = (Element) runtimeElements.item(0);
            NodeList libraries = element.getElementsByTagName(IModel.LIBRARY);
            if (libraries == null || libraries.getLength() < 1) {
                throw new IllegalArgumentException("<runtime> element must have 1+ <library> in "+pluginDescriptor.myLocationUrl);
            }
            for (int i=0; libraries != null && i<libraries.getLength(); i++) {
                Element library = (Element) libraries.item(i);
                String relativePath = library.getAttribute(IModel.LIBRARY_NAME);
                pluginDescriptor.addRuntimeLibrary(relativePath);
            }
        }
    }

    private static void handleExtensionPointElements(NodeList extensionPoints, PluginDescriptor pluginDescriptor) {
        for(int i=0; extensionPoints != null && i<extensionPoints.getLength(); i++) {
            Element element = (Element) extensionPoints.item(i);
            String label = element.getAttribute(IModel.EXTENSION_POINT_NAME);
            String uniqueIdentifier = element.getAttribute(IModel.EXTENSION_POINT_ID);
            String schemaReference = element.getAttribute(IModel.EXTENSION_POINT_SCHEMA);
            pluginDescriptor.addExtensionPointDescriptor(uniqueIdentifier, label, schemaReference);
        }
    }
    
    private static void handleExtensionElements(NodeList extensionElements, PluginDescriptor pluginDescriptor) {
        for(int i=0; extensionElements != null && i<extensionElements.getLength(); i++) {
            Element element = (Element) extensionElements.item(i);
            String label = element.getAttribute(IModel.EXTENSION_NAME);
            String id = element.getAttribute(IModel.EXTENSION_ID);
            String pointId = element.getAttribute(IModel.EXTENSION_TARGET);
            NodeList configurationTags = element.getElementsByTagName("*"); 
            pluginDescriptor.addExtension(id, label, pointId, configurationTags);
        }
    }   
}