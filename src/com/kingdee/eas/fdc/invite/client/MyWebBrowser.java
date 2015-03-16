package com.kingdee.eas.fdc.invite.client;

import java.io.InputStream;
import java.util.Arrays;
import java.util.EventListener;

import chrriis.common.ObjectRegistry;
import chrriis.common.Utils;
import chrriis.common.WebServer;
import chrriis.common.WebServer.HTTPRequest;
import chrriis.common.WebServer.WebServerContent;
import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.swtimpl.EventDispatchUtils;
import chrriis.dj.nativeswing.swtimpl.LocalMessage;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

public class MyWebBrowser extends JWebBrowser {
	// private int instanceID;

	private static final String PACKAGE_PREFIX = "/fckeditor/";
	private static final String EDITOR_INSTANCE = "HTMLeditor1";

	private String fckeditorURL = null;

	private String customJavascriptConfiguration;

	static interface JHTMLEditorImplementation {

		public WebServerContent getWebServerContent(HTTPRequest httpRequest,
				String resourcePath, final int instanceID);

		public String getHTMLContent();

		public void setHTMLContent(String html);

	}

	private JHTMLEditorImplementation implementation;

	class EditorImplementation implements JHTMLEditorImplementation {
		private MyWebBrowser webBrowser = null;

		public EditorImplementation(MyWebBrowser webBrowser) {
			this.webBrowser = webBrowser;
		}

		public String getHTMLContent() {
			// TODO Auto-generated method stub
			return null;
		}

		public WebServerContent getWebServerContent(HTTPRequest httpRequest,
				String resourcePath, int instanceID) {
			return this.webBrowser.getWebServerContent(httpRequest,
					resourcePath, instanceID);
		}

		public void setHTMLContent(String html) {
			// TODO Auto-generated method stub

		}

	}

	JHTMLEditorImplementation getImplementation() {
		return implementation;
	}

	public MyWebBrowser(String fckEditorURL, boolean navOnInit,
			NSOption options) {
		this(fckEditorURL,navOnInit,new NSOption[]{options});
	}
	public MyWebBrowser(String fckEditorURL, boolean navOnInit,
			NSOption[] options) {
		super(options);
		this.fckeditorURL = fckEditorURL;
		this.addWebBrowserListener(new WebBrowserAdapter() {
			public void commandReceived(WebBrowserEvent e, String command,
					String[] args) {
				if ("JH_setLoaded".equals(command)) {
					Object[] listeners = listenerList.getListenerList();
					for (int i = listeners.length - 2; i >= 0; i -= 2) {
						if (listeners[i] == InitializationListener.class) {
							((InitializationListener) listeners[i + 1])
									.objectInitialized();
						}
					}
				}
			}
		});
		this.setBarsVisible(false);
		// instanceID = ObjectRegistry.getInstance().add(this);
		implementation = new EditorImplementation(this);
		
		final Boolean[] resultArray = new Boolean[1];
		InitializationListener initializationListener = new InitializationListener() {
			public void objectInitialized() {
				removeInitializationListener(this);
				resultArray[0] = Boolean.TRUE;
			}
		};
		addInitializationListener(initializationListener);
		// this.navigate(WebServer.getDefaultWebServer().getDynamicContentURL(
		// MyWebBrowser.class.getName(), String.valueOf(instanceID),
		// "index.html"));
		if (this.fckeditorURL != null && navOnInit)
			this.navigate(this.fckeditorURL);

		LocalMessage localMessage = new LocalMessage() {
			public Object run(Object[] args) {
				InitializationListener initializationListener = (InitializationListener) args[0];
				final Boolean[] resultArray = (Boolean[]) args[1];
				EventDispatchUtils.sleepWithEventDispatch(
						new EventDispatchUtils.Condition() {
							public boolean getValue() {
								if(resultArray[0] == null)
									return true;
								return resultArray[0].booleanValue();
							}
						}, 4000);
				removeInitializationListener(initializationListener);
				return null;
			}
		};
	
		
		this.getNativeComponent().runSync(localMessage, new Object[]{initializationListener, resultArray});

		this.addWebBrowserListener(new WebBrowserAdapter() {
			public void commandReceived(WebBrowserEvent e, String command,
					String[] args) {
				String commandText = command;
				if (args.length > 0) {
					commandText += " " + Arrays.toString(args);
				}
				tempResult = commandText;
			}
		});
	}

	private String html = "";

	public void loadHTMLEditor(String html) {
		final Boolean[] resultArray = new Boolean[1];
		this.html = html;
		InitializationListener initializationListener = new InitializationListener() {
			public void objectInitialized() {
				removeInitializationListener(this);
				resultArray[0] = Boolean.TRUE;
			}
		};
		addInitializationListener(initializationListener);
		this.navigate(this.fckeditorURL);
		LocalMessage localMessage = new LocalMessage() {
			public Object run(Object[] args) {
				InitializationListener initializationListener = (InitializationListener) args[0];
				final Boolean[] resultArray = (Boolean[]) args[1];
				EventDispatchUtils.sleepWithEventDispatch(
						new EventDispatchUtils.Condition() {
							public boolean getValue() {
								if(resultArray[0] == null)
									return true;
								return resultArray[0].booleanValue();
							}
						}, 4000);
				removeInitializationListener(initializationListener);
				return null;
			}
		};
		this.getNativeComponent().runSync(localMessage, new Object[]{initializationListener, resultArray});
		NativeComponent nativeComponent = MyWebBrowser.this
				.getNativeComponent();
		boolean isEnabled = nativeComponent.isEnabled();
		nativeComponent.setEnabled(false);
		new Message().syncSend(true);
		MyWebBrowser.this.executeJavascript("JH_setData('"
				+ Utils.encodeURL(MyWebBrowser.this.html) + "');");
		new Message().syncSend(true);
		nativeComponent.setEnabled(isEnabled);
	}

	private static interface InitializationListener extends EventListener {
		public void objectInitialized();
	}

	private void removeInitializationListener(InitializationListener listener) {
		listenerList.remove(InitializationListener.class, listener);
	}

	private void addInitializationListener(InitializationListener listener) {
		listenerList.add(InitializationListener.class, listener);
	}

	private static final String LS = Utils.LINE_SEPARATOR;

	public WebServerContent getWebServerContent(final HTTPRequest httpRequest,
			final String resourcePath, final int instanceID) {
		if ("index.html".equals(resourcePath)) {
			return new WebServerContent() {
				public String getContentType() {
					int index = resourcePath.lastIndexOf('.');
					return getDefaultMimeType(index == -1 ? null : resourcePath
							.substring(index));
				}

				public InputStream getInputStream() {
					String content = "<html>"
							+ LS
							+ "  <head>"
							+ LS
							+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>"
							+ LS
							+ "    <style type=\"text/css\">"
							+ LS
							+ "      body, form {margin: 0; padding: 0; overflow: auto;}"
							+ LS
							+ "    </style>"
							+ LS
							+ "    <script type=\"text/javascript\" src=\"fckeditor.js\"></script>"
							+ LS
							+ "    <script type=\"text/javascript\">"
							+ LS
							+
							// We override the FCK editor function because on
							// Linux this may return false if navigator.product
							// is empty (instead of "Gecko")
							"      function FCKeditor_IsCompatibleBrowser() {"
							+ LS
							+ "        return true;"
							+ LS
							+ "      }"
							+ LS
							+ "      function sendCommand(command) {"
							+ LS
							+ "        var s = '"
							+ JWebBrowser.COMMAND_STATUS_PREFIX
							+ "' + encodeURIComponent(command);"
							+ LS
							+ "        for(var i=1; i<arguments.length; s+='&'+encodeURIComponent(arguments[i++]));"
							+ LS
							+
							// We have to use the status, because if
							// window.location is called too early it may stop
							// page loading.
							"        window.status = s;"
							+ LS
							+ "      }"
							+ LS
							+ "      function JH_setData(html) {"
							+ LS
							+ "        var inst = FCKeditorAPI.GetInstance('"
							+ EDITOR_INSTANCE
							+ "');"
							+ LS
							+ "        inst.SetHTML(decodeURIComponent(html));"
							+ LS
							+ "      }"
							+ LS
							+ "      function JH_sendData() {"
							+ LS
							+ "        document.jhtml_form.action = 'jhtml_sendData';"
							+ LS
							+ "        document.jhtml_form.submit();"
							+ LS
							+ "        return false;"
							+ LS
							+ "      }"
							+ LS
							+ "      function JH_doSave() {"
							+ LS
							+ "        document.jhtml_form.action = 'jhtml_save';"
							+ LS
							+ "        document.jhtml_form.submit();"
							+ LS
							+ "        return false;"
							+ LS
							+ "      }"
							+ LS
							+ "      function createEditor() {"
							+ LS
							+ "        var oFCKeditor = new FCKeditor('"
							+ EDITOR_INSTANCE
							+ "');"
							+ LS
							+ "        oFCKeditor.Width = \"100%\";"
							+ LS
							+ "        oFCKeditor.Height = \"100%\";"
							+ LS
							+ "        oFCKeditor.BasePath = \"\";"
							+ LS
							+ (customJavascriptConfiguration != null ? "        oFCKeditor.Config[\"CustomConfigurationsPath\"] = '"
									+ WebServer
											.getDefaultWebServer()
											.getDynamicContentURL(
													MyWebBrowser.class
															.getName(),
													String.valueOf(instanceID),
													"customConfigurationScript.js")
									+ "';" + LS
									: "")
							+ "        oFCKeditor.Create();"
							+ LS
							+ "      }"
							+ LS
							+ "      function FCKeditor_OnComplete(editorInstance) {"
							+ LS
							+ "        editorInstance.LinkedField.form.onsubmit = JH_doSave;"
							+ LS
							+ "        sendCommand('JH_setLoaded');"
							+ LS
							+ "      }"
							+ LS
							+ "    </script>"
							+ LS
							+ "  </head>"
							+ LS
							+ "  <body>"
							+ LS
							+ "  <iframe style=\"display:none;\" name=\"j_iframe\"></iframe>"
							+ LS
							+ "  <form name=\"jhtml_form\" method=\"POST\" target=\"j_iframe\">"
							+ LS + "    <script type=\"text/javascript\">" + LS
							+ "      createEditor();" + LS + "    </script>"
							+ "</form>" + LS + // No space at the begining of
							// this line or else a scrollbar
							// appears.
							"  </body>" + LS + "</html>" + LS;
					return getInputStream(content);
				}
			};
		}
		if ("customConfigurationScript.js".equals(resourcePath)) {
			return new WebServerContent() {
				public String getContentType() {
					return getDefaultMimeType(".js");
				}

				public InputStream getInputStream() {
					return getInputStream(customJavascriptConfiguration);
				}
			};
		}
		if ("jhtml_sendData".equals(resourcePath)) {
			Object data = httpRequest.getHTTPPostDataArray()[0].getHeaderMap()
					.get(EDITOR_INSTANCE);
			tempResult = data;
			return new WebServerContent() {
				public InputStream getInputStream() {
					String content = "<html>" + LS + "  <body>" + LS
							+ "    Send data successful." + LS + "  </body>"
							+ LS + "</html>" + LS;
					return getInputStream(content);
				}
			};
		}

		return WebServer.getDefaultWebServer().getURLContent(
				WebServer.getDefaultWebServer().getClassPathResourceURL(
						MyWebBrowser.class.getName(),
						PACKAGE_PREFIX + resourcePath));
	}

	private volatile Object tempResult;

	public String getEditorContent() {
		if (!this.isNativePeerInitialized()) {
			return "";
		}
		tempResult = this;
		tempResult = this.executeJavascriptWithResult("var inst = FCKeditorAPI.GetInstance('HTMLeditor1'); return inst.GetHTML();");
		if(tempResult == null)
			tempResult = "";
		return tempResult.toString();
	}

	protected static WebServerContent getWebServerContent(
			final HTTPRequest httpRequest) {
		String resourcePath = httpRequest.getResourcePath();
		int index = resourcePath.indexOf('/');
		int instanceID = Integer.parseInt(resourcePath.substring(0, index));
		MyWebBrowser htmlEditor = (MyWebBrowser) ObjectRegistry.getInstance()
				.get(instanceID);
		if (htmlEditor == null) {
			return null;
		}
		String resourcePath_ = resourcePath.substring(index + 1);
		if (resourcePath_.startsWith("/")) {
			resourcePath_ = resourcePath_.substring(1);
		}
		JHTMLEditorImplementation implementation = htmlEditor
				.getImplementation();
		return implementation.getWebServerContent(httpRequest, resourcePath_,
				instanceID);
	}

	public void setEditorContent(String html) {
		String str = this.getHTMLContent();
		if (str == null || (str != null && str.indexOf("jhtml_form") < 0)) {
//			Logger.info("当前加载的html是" + str);
			loadHTMLEditor(html);

		} else {
			NativeComponent nativeComponent = this.getNativeComponent();
			boolean isEnabled = nativeComponent.isEnabled();
			nativeComponent.setEnabled(false);
			new Message().syncSend(true);
			this.executeJavascript("JH_setData('" + Utils.encodeURL(html)
					+ "');");
			new Message().syncSend(true);
			nativeComponent.setEnabled(isEnabled);
		}
	}

}