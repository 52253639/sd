///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.lang.reflect.Method;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.StringTokenizer;
//
//import org.apache.log4j.Logger;
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.filters.NodeClassFilter;
//import org.htmlparser.filters.OrFilter;
//import org.htmlparser.nodes.TagNode;
//import org.htmlparser.tags.InputTag;
//import org.htmlparser.tags.SelectTag;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//import org.jdesktop.jdic.browser.WebBrowser;
//import org.jdesktop.jdic.browser.WebBrowserEvent;
//import org.jdesktop.jdic.browser.WebBrowserListener;
//import org.jdesktop.jdic.init.JdicManager;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.ctrl.data.engine.script.beanshell.function.character.SUBSTRING;
//import com.kingdee.bos.ctrl.kdf.table.IRow;
//import com.kingdee.bos.ctrl.swing.KDComboBox;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.dao.query.SQLExecutorFactory;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.eas.base.uiframe.client.LoginHelper;
//import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
//import com.kingdee.eas.fdc.basedata.FDCHelper;
//import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.sellhouse.RoomCollection;
//import com.kingdee.eas.fdc.sellhouse.RoomFactory;
//import com.kingdee.eas.fdc.sellhouse.RoomInfo;
//import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
//import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.MsgBox;
//import com.kingdee.jdbc.rowset.IRowSet;
//
///**
// * output class name
// */
//public class WebMarkPutInEditUI extends AbstractWebMarkPutInEditUI {
//	private static final Logger logger = CoreUIObject
//			.getLogger(WebMarkPutInEditUI.class);
//
//	WebMarkFieldCollection fieldColls = null;
//
//	BOSUuid sellProjID = null;
//
//	String roomNumber = null;
//
//	WebBrowser webBrowser = null;
//
//	/**
//	 * output class constructor
//	 */
//	public WebMarkPutInEditUI() throws Exception {
//		super();
//	}
//
//	/**
//	 * output storeFields method
//	 */
//	public void storeFields() {
//		super.storeFields();
//	}
//
//	/**
//	 * output kdtFunctionEntrys_tableSelectChanged method
//	 */
//	protected void kdtFunctionEntrys_tableSelectChanged(
//			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
//			throws Exception {
//		super.kdtFunctionEntrys_tableSelectChanged(e);
//	}
//
//	private void loadContext() {
//		// rsFunc = (IRowSet) this.getUIContext().get("funcData");
//		sellProjID = BOSUuid.read(this.getUIContext().get("sellProjID")
//				.toString());
//		roomNumber = this.getUIContext().get("roomNumber").toString();
//	}
//
//	private void setControlEvent() {
//		// 房间-事件加载
//		this.cmbRoom.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO 自动生成方法存根
//				cmbRoom_Changed(arg0);
//			}
//		});
//
//		// 功能-事件加载
//		cmbFunction.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				cmbFunction_Changed(arg0);
//			}
//		});
//		// 步骤-事件加载
//		cmbProcess.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				cmbProcess_Changed(arg0);
//			}
//		});
//	}
//
//	/**
//	 * output actionSetWeb_actionPerformed method
//	 */
//	public void actionSetWeb_actionPerformed(ActionEvent e) throws Exception {
//		fillWebMeta();
//	}
//
//	/**
//	 * output actionNextProc_actionPerformed method
//	 */
//	public void actionNextProc_actionPerformed(ActionEvent e) throws Exception {
//		loadURL();
//	}
//
//	private void nextProcess() {
//		int lSel = cmbProcess.getSelectedIndex();
//		if (lSel == cmbProcess.getItemCount() - 1) {
//			FDCMsgBox.showInfo("已经是最后一步！");
//			return;
//		}
//		cmbProcess.setSelectedIndex(lSel + 1);
//	}
//
//	/**
//	 * 得到网页元素
//	 */
//	private Node[] getWebNodes(String enumSel) {
//		Node[] nodes = null;
//		NodeClassFilter tag = null;
//		String sFilter = "";
//		try {
//			StringBuffer inputHtml = new StringBuffer();
//			inputHtml.append(webBrowser.getContent().toString());
//			if (inputHtml == null)
//				return null;
//			if (inputHtml.toString().trim().equals(""))
//				return null;
//			Parser parser = Parser.createParser(inputHtml.toString(), "GBK");
//			// 方法一：
//			OrFilter lastFilter = new OrFilter();
//			if (enumSel.equals(WebMetaTypeEnum.INPUT_VALUE)) {
//
//				lastFilter
//						.setPredicates(new NodeFilter[] { new NodeClassFilter(
//								InputTag.class) });
//			} else if (enumSel.equals(WebMetaTypeEnum.SELECT_VALUE)) {
//				lastFilter
//						.setPredicates(new NodeFilter[] { new NodeClassFilter(
//								SelectTag.class) });
//			} else {
//				lastFilter.setPredicates(new NodeFilter[] {
//						new NodeClassFilter(InputTag.class),
//						new NodeClassFilter(SelectTag.class) });
//			}
//
//			NodeList nodelist;
//			nodelist = parser.parse(lastFilter);
//			nodes = nodelist.toNodeArray();
//
//		} catch (ParserException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} finally {
//			return nodes;
//		}
//	}
//
//	/**
//	 * 加载网页
//	 */
//	private void loadURL() {
//		this.webBrowser.setURL();
//		String sInputUrl = txtURL.getText();
//		if (sInputUrl == null) {
//			FDCMsgBox.showInfo("加载地址为空!");
//			return;
//		} else {
//			URL curUrl = null;
//			try {
//				// 检查是否是本地文件
//				File[] roots = File.listRoots();
//
//				for (int i = 0; i < roots.length; i++) {
//					if (sInputUrl.toLowerCase().startsWith(
//							roots[i].toString().toLowerCase())) {
//						File curLocalFile = new File(sInputUrl);
//
//						curUrl = curLocalFile.toURL();
//						break;
//					}
//				}
//
//				if (curUrl == null) {
//					// Check if the text value is a valid URL.
//					try {
//						curUrl = new URL(sInputUrl);
//					} catch (MalformedURLException e) {
//						if (sInputUrl.toLowerCase().startsWith("ftp.")) {
//							curUrl = new URL("ftp://" + sInputUrl);
//						} else if (sInputUrl.toLowerCase()
//								.startsWith("gopher.")) {
//							curUrl = new URL("gopher://" + sInputUrl);
//						} else if (sInputUrl.toLowerCase().startsWith("http.")) {
//							curUrl = new URL("http://" + sInputUrl);
//						} else {
//							curUrl = new URL(sInputUrl);
//						}
//					}
//				}
//				webBrowser.setURL(curUrl);
//			} catch (MalformedURLException mue) {
//				FDCMsgBox.showWarning("没有找到路径:" + sInputUrl);
//			} catch (Exception e) {
//				MsgBox
//						.showError(com.kingdee.eas.util.client.EASResource
//								.getString(
//										"com.kingdee.eas.fdc.sellhouse.client.AbstractWebMarkFieldEditUI:"
//												+ e.getMessage().toString(),
//										"loadURL"));
//			}
//
//		}
//	}
//
//	/*
//	 * 功能变化事件
//	 */
//	private void cmbFunction_Changed(ActionEvent arg0) {
//		try {
//			initProcess();
//			cmbProcess.setSelectedIndex(-1);
//			this.kdtWebField.removeRows();
//			this.txtURL.setText("");
//			// this.webBrowser.setURL();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/*
//	 * 房间变化 步骤选中第一步
//	 */
//	private void cmbRoom_Changed(ActionEvent arg0) {
//		try {
//			cmbProcess.setSelectedIndex(-1);
//			this.kdtWebField.removeRows();
//			this.txtURL.setText("");
//			// this.webBrowser.setURL();
//		} catch (Exception ex) {
//			ex = null;
//		}
//	}
//
//	/*
//	 * 步骤变化改变网页及字段配置取值
//	 */
//	private void cmbProcess_Changed(ActionEvent arg0) {
//		try {
//			KDComboBox cb = (KDComboBox) arg0.getSource();
//			WebMarkProcessCollection procColls = (WebMarkProcessCollection) cb
//					.getUserObject();
//			if (procColls == null)
//				return;
//			if (cb.getItemCount() < 1)
//				return;
//			if (cb.getSelectedIndex() < 0)
//				return;
//			fillFieldEntrys();
//
//			WebMarkProcessInfo procInfo = procColls.get(cb.getSelectedIndex());
//			this.txtURL.setText(procInfo.getUrl().toString());
//			// loadURL();
//		} catch (Exception ex) {
//			ex = null;
//		}
//	}
//
//	/*
//	 * 网页赋值
//	 */
//	private void fillWebMeta() {
//		StringBuffer SQLProcess = new StringBuffer();
//		TagNode tagNote = null;
//		try {
//			if (kdtWebField.getRowCount() < 1)
//				return;
//			// Node[] inputNodes = getWebNodes(WebMetaTypeEnum.INPUT_VALUE);
//			// Node[] selNodes = getWebNodes(WebMetaTypeEnum.SELECT_VALUE);
//			WebMarkFieldCollection fieldColls = (WebMarkFieldCollection) kdtWebField
//					.getUserObject();
//			for (int i = 0; i < kdtWebField.getRowCount(); i++) {
//				IRow row = kdtWebField.getRow(i);
//				WebMarkFieldInfo fieldInfo = fieldColls.get(i);
//				SQLProcess.append("document.all[\"" + fieldInfo.getWebName()		//.getWebID()
//						+ "\"].value=\""
//						+ toString(row.getCell("fileValue").getValue(), "")
//						+ "\"");
//				SQLProcess.append("\t\n");
//			}
//			// System.out.println(SQLProcess.toString());
//			if (!SQLProcess.equals(""))
//				webBrowser.executeScript(SQLProcess.toString());
//			nextProcess();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/*
//	 * 填充字段
//	 */
//	private void fillFieldEntrys() {
//		String sqlStr;
//		String roomStr;
//		String roomValueStr;
//		String fieldName;
//		String fieldValueStr = "";
//		int lMove = -1;
//		// 填写配置字段控制
//		initField();
//		// 系统字段配置信息
//		IRowSet rs = getFieldRelation();
//		// 填值
//		WebMarkFieldCollection disFieldColls = (WebMarkFieldCollection) kdtWebField
//				.getUserObject();
//		if (disFieldColls == null)
//			return;
//
//		if (cmbRoom.getItemCount() < 1)
//			return;
//		if (cmbRoom.getSelectedIndex() < 0)
//			return;
//		roomValueStr = ((RoomInfo) ((RoomCollection) cmbRoom.getUserObject())
//				.get(cmbRoom.getSelectedIndex())).getId().toString();
//
//		if (roomValueStr.trim().equals(""))
//			return;
//		try {
//			for (int i = 0; i < disFieldColls.size(); i++) {
//				WebMarkFieldInfo disFieldInfo = disFieldColls.get(i);
//				rs.beforeFirst();
//				while (rs.next()) {
//					sqlStr = "";
//					roomStr = "";
//					fieldValueStr = "";
//					// 默认值（“系统字段”为“none”）字段逻辑处理
//					if (toString(disFieldInfo.getFileName(), "").equals("")
//							|| toString(disFieldInfo.getFileName(), "").equals(
//									"none")) {
//						fieldValueStr = disFieldInfo.getDefultValue();
//						// 赋值
//						kdtWebField.getCell(i, "fileValue").setValue(
//								fieldValueStr);
//						break;
//					} else if (disFieldInfo.getFileName().equals(
//							rs.getString("FID"))) {
//						if (!rs.getString("FFilter").trim().equals(""))
//							roomStr = " and " + rs.getString("FFilter");
//						sqlStr = "select " + rs.getString("FField") + " from "
//								+ rs.getString("FFromTable") + " where 1=1 "
//								+ roomStr + "='" + roomValueStr + "'";
//						if (!this.toString(rs.getString("FOrdery"), "").equals(
//								""))
//							sqlStr = sqlStr
//									+ " order by "
//									+ this
//											.toString(rs.getString("FOrdery"),
//													"");
//						// 取值字段名
//						fieldName = new String(rs.getString("FField"));
//						if (fieldName.indexOf(".") >= 0) {
//							String field[] = fieldName.split("\\.");
//							fieldName = field[1];
//						}
//
//						IRowSet setRs = SQLExecutorFactory.getRemoteInstance(
//								sqlStr.toString()).executeSQL();
//						if (!setRs.next())
//							break;
//						// -------------处理买家一、二……问题-----------------
//						if (toString(rs.getString("FName"), "").indexOf("买家") >= 0) {
//							if (lMove == -1)
//								lMove = 1;
//							int lSub = Integer.parseInt(toString(
//									rs.getString("FName"), "").substring(
//									toString(rs.getString("FName"), "")
//											.indexOf("(") + 1,
//									toString(rs.getString("FName"), "")
//											.indexOf(")")));
//							if (lMove <= lSub) {
//								for (int j = lMove; j < lSub; j++) {
//									setRs.next();
//									if (j > lMove)
//										lMove = j;
//								}
//								fieldValueStr = toString(setRs
//										.getString(fieldName), "");
//							}
//						}
//						// -----------------------------------------------
//						// 处理枚举类型
//						else if (rs.getString("FFieldType").trim().equals(
//								"enum")) {
//							if (!rs.getString("FFieldRelaClass").trim().equals(
//									"")) {
//								// 利用反射调用枚举得到枚举对象方法,得到别名;枚举类只限于房地产下目录的
//								String className = rs
//										.getString("FFieldRelaClass");
//								Class clazz = Class.forName(className);
//								Method method = clazz.getMethod("getEnum",
//										new Class[] { String.class });
//								Object enumObj = method.invoke(clazz,
//										new Object[] { setRs.getString(
//												fieldName).toString() });
//								fieldValueStr = enumObj.toString();
//							}
//						} else {
////							while (setRs.next())
//								fieldValueStr = toString(setRs
//										.getString(fieldName), "");
//						}
//
//						// ---------- 给对应表格赋值--begin
//						// 处理默认值和文本框类型赋值
//						if (disFieldInfo.getWebMetaType().getValue().equals(
//								"InputTag.class")) {
//							kdtWebField.getCell(i, "fileValue").setValue(
//									fieldValueStr);
//							break;
//							// 处理combobox类型赋值
//						} else if (disFieldInfo.getWebMetaType().getValue()
//								.equals("SelectTag.class")) {
//							getSelTagValue(disFieldInfo.getWebID(),
//									fieldValueStr, i);
//							break;
//						}
//						// ---------- 给对应表格赋值--end
//					}
//				}
//			}
//		} catch (SQLException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * selectTag 值选择
//	 */
//	private void getSelTagValue(String webID, String rsValue, int rowCount) {
//		for (int i = 0; i < fieldColls.size(); i++) {
//			WebMarkFieldInfo filedInfo = fieldColls.get(i);
//			if (webID.equals(filedInfo.getWebID().toString())) {
//				if (rsValue.equals(filedInfo.getDefultValue())) {
//					// 赋予网页ＣＯＭＢＯＢＯＸ的值
//					kdtWebField.getCell(rowCount, "fileValue").setValue(
//							filedInfo.getWebValue());
//					break;
//				}
//			}
//		}
//	}
//
//	/*
//	 * htmlparser -webBowser 初始化网页浏览窗口事件
//	 */
//	private void initWebBowser() {
//		webBrowser.addWebBrowserListener(new WebBrowserListener() {
//			public void downloadStarted(WebBrowserEvent event) {
//				System.out.println("Loading started.");
//			}
//
//			public void downloadCompleted(WebBrowserEvent event) {
//				System.out.println("Loading completed.");
//			}
//
//			public void downloadProgress(WebBrowserEvent event) {
//				// updateStatusInfo("Loading in progress...");
//			}
//
//			public void downloadError(WebBrowserEvent event) {
//			}
//
//			public void documentCompleted(WebBrowserEvent event) {
//				webBrowser
//						.executeScript("document.onclick=function(){document.title=document.activeElement.name;}");
//				// updateStatusInfo("Document loading completed.");
//			}
//
//			public void titleChange(WebBrowserEvent event) {
//				setTableSelect(event);
//				// updateStatusInfo("Title of the browser window changed.");
//			}
//
//			public void statusTextChange(WebBrowserEvent event) {
//				// updateStatusInfo("Status text changed.");
//			}
//
//			public void windowClose(WebBrowserEvent arg0) {
//				// TODO 自动生成方法存根
//
//			}
//
//			public void initializationCompleted(WebBrowserEvent arg0) {
//				// TODO 自动生成方法存根
//				
//			}
//		});
//	}
//
//	/*
//	 * 根据webBrowsers事件中的字段，对kdtable中的字段进行选择。
//	 */
//	private void setTableSelect(WebBrowserEvent event) {
//		boolean isGet = false;
//		kdtWebField.getSelectManager().select(-1, -1);
//		if (webBrowser.getURL() == null)
//			return;
//		if (webBrowser.getURL().toString().equals(""))
//			return;
//		try {
//			String metaName = event.getData().toString();
//			// System.out.println(nodeName);
//			if (kdtWebField.getRowCount() < 1)
//				return;
//			String sSelMate;
//			for (int i = 0; i < kdtWebField.getRowCount(); i++) {
//				IRow row = kdtWebField.getRow(i);
//				sSelMate = toString(row.getCell("webID").getValue(), "");
//				if (metaName.trim().equals(sSelMate)) {
//					kdtWebField.getSelectManager().setActiveRowIndex(i);
//					kdtWebField.getSelectManager().select(i, 0, i,
//							this.kdtWebField.getColumnCount());
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			e = null;
//		}
//	}
//
//	public void onLoad() throws Exception {
//		loadContext();
//		// sellProjID = BOSUuid.read("cQK14wEaEADgAHH7wKgS0S/75aw=");
//		// roomID = BOSUuid.read("cQK14wEaEADgAHLMwKgS0ZA+AjY=");
//		this.cmbSchema.setEnabled(true);
//		this.kdtWebField.getStyleAttributes().setLocked(true);
//		this.cmbRoom.setLightWeightPopupEnabled(false);
//		this.cmbSchema.setLightWeightPopupEnabled(false);
//		this.cmbFunction.setLightWeightPopupEnabled(false);
//		this.cmbProcess.setLightWeightPopupEnabled(false);
//		this.cmbSellProject.setLightWeightPopupEnabled(false);
//		
//		this.actionCopy.setEnabled(false);
//		this.actionCopy.setVisible(false);
//		this.actionAttachment.setEnabled(false);
//		this.actionAttachment.setVisible(false);
//		
//		this.actionNext.setEnabled(false);
//		this.actionNext.setVisible(false);
//		this.actionLast.setEnabled(false);
//		this.actionLast.setVisible(false);
//		this.actionFirst.setEnabled(false);
//		this.actionFirst.setVisible(false);
//		this.actionLast.setEnabled(false);
//		this.actionLast.setVisible(false);
//		this.actionAddNew.setEnabled(false);
//		this.actionAddNew.setVisible(false);
//		this.actionCancel.setEnabled(false);
//		this.actionCancel.setVisible(false);
//		this.actionCancelCancel.setEnabled(false);
//		this.actionCancelCancel.setVisible(false);
//		this.actionPre.setEnabled(false);
//		this.actionPre.setVisible(false);
//		this.actionPrint.setEnabled(false);
//		this.actionPrint.setVisible(false);
//		this.actionPrintPreview.setEnabled(false);
//		this.actionPrintPreview.setVisible(false);
//		this.actionEdit.setEnabled(false);
//		this.actionEdit.setVisible(false);
//		this.actionRemove.setEnabled(false);
//		this.actionRemove.setVisible(false);
//		
//		this.menuSubmitOption.setVisible(false);
//		this.actionSave.setEnabled(false);
//		this.actionSave.setVisible(false);
//		this.actionSubmit.setEnabled(false);
//		this.actionSubmit.setVisible(false);
//
//		// 实例化售楼项目
//		initSellProject();
//		initRooms();
//		initSchema();
//		initFunction();
//		initProcess();
//		try {
//			// 初始化WebBrowser
//			// 这里开始已经改造了jdic的org.jdesktop.jdic.init.JdicManager,initShareNative方法
//			// 和加入了setBinaryPath()方法
//			String path = System.getProperty("easclient.root");
//			
//			/**
//			 * 重新整理的加载dll的目录问题
//			 * update by renliang 
//			 */
//			CRMClientHelper.getDLLForWebMark(path);
//			
//			JdicManager.getManager().setBinaryPath(path);
//			webBrowser = new WebBrowser();
//			initWebBowser();
//			this.kdpBrowser.setAutoscrolls(true);
//			this.kDScrollPane1.setViewportView(webBrowser);
//			kDScrollPane1.setKeyBoardControl(true);
//			kDScrollPane1.setAutoscrolls(true);
//
//			// this.webBrowser.setURL();
//		} catch (Exception e) {
////			e.printStackTrace();
//			MsgBox.showError("easclient.root="+System.getProperty("easclient.root")+";jdic.dll可能已经其它模块加载！");
//		}
//		// 步骤-事件加载
//		setControlEvent();
//		// 不选任何步骤
//		// if (cmbProcess.getItemCount() > 0) {
//		cmbProcess.setSelectedIndex(-1);
//		// }
//	} /*
//		 * 实例化售楼项目
//		 */
//
//	private void initSellProject() {
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		view.getSelector().add("*");
//
//		view.setFilter(filter);
//		filter.getFilterItems().add(new FilterItemInfo("id", sellProjID));
//		SellProjectCollection sellProjects;
//		try {
//			sellProjects = SellProjectFactory.getRemoteInstance()
//					.getSellProjectCollection(view);
//			this.cmbSellProject.setUserObject(sellProjects);
//			for (int i = 0; i < sellProjects.size(); i++) {
//				SellProjectInfo sellProjectInfo = sellProjects.get(i);
//				cmbSellProject.addItem(sellProjectInfo.getName());
//			}
//			// cmbSellProject.setSelectedIndex(0);
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 售楼项目选择变化，房间等处理逻辑
//	 */
//	private void initRooms() {
//		int selIndex = -1;
//		try {
//			if (sellProjID == null)
//				return;
//			if (sellProjID.equals(""))
//				return;
//			EntityViewInfo view = new EntityViewInfo();
//			view.getSelector().add("*");
//			FilterInfo filter = new FilterInfo();
//			view.setFilter(filter);
//			filter.getFilterItems().add(
//					new FilterItemInfo("building.sellProject.id", sellProjID));
//			// filter.getFilterItems().add(
//			// new FilterItemInfo("building.id", sellProjectId));
//			RoomCollection rooms = RoomFactory.getRemoteInstance()
//					.getRoomCollection(view);
//
//			this.cmbRoom.setUserObject(rooms);
//			cmbRoom.removeAllItems();
//			for (int i = 0; i < rooms.size(); i++) {
//				RoomInfo room = rooms.get(i);
//				if (!roomNumber.toString().trim().equals("")
//						& roomNumber.toString().equals(
//								room.getNumber().toString())) {
//					selIndex = i;
//				}
//				cmbRoom.addItem(room.getName().toString());
//			}
//			cmbRoom.setSelectedIndex(selIndex);
//		} catch (BOSException e1) {
//			// TODO 自动生成 catch 块
//			e1.printStackTrace();
//		}
//	}
//
//	/*
//	 * 方案
//	 */
//	private void initSchema() {
//		try {
//			if (sellProjID == null)
//				return;
//			if (sellProjID.equals(""))
//				return;
//			String oql = "select * where sellProject= '"
//					+ sellProjID.toString() + "'";
//			WebMarkSchemaCollection schemaColls = WebMarkSchemaFactory
//					.getRemoteInstance().getWebMarkSchemaCollection(oql);
//			cmbSchema.setUserObject(schemaColls);
//			for (int i = 0; i < schemaColls.size(); i++) {
//				WebMarkSchemaInfo schemaInfo = schemaColls.get(i);
//				cmbSchema.addItem(schemaInfo.getName());
//			}
//		} catch (BOSException e1) {
//			// TODO 自动生成 catch 块
//			e1.printStackTrace();
//		}
//	}
//
//	/*
//	 * 功能
//	 */
//	private void initFunction() {
//		if (cmbSchema.getItemCount() < 1)
//			return;
//		if (cmbSchema.getSelectedIndex() < 0)
//			return;
//		WebMarkSchemaCollection schemaColls = (WebMarkSchemaCollection) cmbSchema
//				.getUserObject();
//		WebMarkSchemaInfo schemaInfo = (WebMarkSchemaInfo) schemaColls
//				.get(this.cmbSchema.getSelectedIndex());
//		// System.out.println(schemaInfo.getName().toString());
//		BOSUuid shemaID = schemaInfo.getId();
//		// System.out.println(shemaID.toString());
//		String oql = "select * where parent= '" + shemaID.toString() + "'";
//		WebMarkFunctionCollection funcColls;
//		try {
//			funcColls = WebMarkFunctionFactory.getRemoteInstance()
//					.getWebMarkFunctionCollection(oql);
//			cmbFunction.setUserObject(funcColls);
//			for (int i = 0; i < funcColls.size(); i++) {
//				WebMarkFunctionInfo funcInfo = funcColls.get(i);
//				cmbFunction.addItem(funcInfo.getFunctionName());
//			}
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 步骤
//	 */
//	private void initProcess() {
//		if (cmbFunction.getItemCount() < 1)
//			return;
//		if (cmbFunction.getSelectedIndex() < 0)
//			return;
//		WebMarkFunctionCollection funcColls = (WebMarkFunctionCollection) cmbFunction
//				.getUserObject();
//		WebMarkFunctionInfo funcInfo = (WebMarkFunctionInfo) funcColls
//				.get(this.cmbFunction.getSelectedIndex());
//		// System.out.println(funcInfo.getFunctionName().toString());
//		BOSUuid funcID = funcInfo.getId();
//		// System.out.println(funcID.toString());
//		String oql = "select * where parent= '" + funcID.toString() + "'";
//		WebMarkProcessCollection procColls;
//		try {
//			procColls = WebMarkProcessFactory.getRemoteInstance()
//					.getWebMarkProcessCollection(oql);
//			cmbProcess.setUserObject(procColls);
//			cmbProcess.removeAllItems();
//			for (int i = 0; i < procColls.size(); i++) {
//				WebMarkProcessInfo procInfo = procColls.get(i);
//				cmbProcess.addItem(procInfo.getProcessName());
//			}
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 字段配置
//	 */
//	private void initField() {
//		String beStr = "";
//		WebMarkFieldCollection disFieldColls = null;
//		if (cmbProcess.getItemCount() < 1)
//			return;
//		if (cmbProcess.getSelectedIndex() < 0)
//			return;
//		kdtWebField.removeRows();
//		WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProcess
//				.getUserObject();
//		WebMarkProcessInfo procInfo = (WebMarkProcessInfo) procColls
//				.get(this.cmbProcess.getSelectedIndex());
//		BOSUuid procID = procInfo.getId();
//		String oql = "select * where parent= '" + procID.toString()
//				+ "' order by Seq";
//		try {
//			fieldColls = WebMarkFieldFactory.getRemoteInstance()
//					.getWebMarkFieldCollection(oql);
//			// kdtWebField.setUserObject(fieldColls);
//			WebMarkFieldInfo fieldInfo = null;
//			WebMetaTypeEnum webMeta;
//			disFieldColls = new WebMarkFieldCollection();
//			for (int i = 0; i < fieldColls.size(); i++) {
//				fieldInfo = fieldColls.get(i);
//				webMeta = fieldInfo.getWebMetaType();
//				if (webMeta.getValue().equals("SelectTag.class")) {
//					if (fieldInfo.getWebID().equals(beStr))
//						continue;
//				}
//				beStr = fieldInfo.getWebID().toString();
//				IRow row = kdtWebField.addRow();
//				row.getCell("webShowName").setValue(fieldInfo.getName());
//				row.getCell("webID").setValue(fieldInfo.getWebID());
//				// 成功后增加不重复集合
//				disFieldColls.add(fieldInfo);
//			}
//			kdtWebField.setUserObject(null);
//			if (disFieldColls != null)
//				kdtWebField.setUserObject(disFieldColls);
//		} catch (BOSException e) {
//			kdtWebField.setUserObject(null);
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//
//	}
//
//	/*
//	 * 系统配置字段信息
//	 */
//	private IRowSet getFieldRelation() {
//		IRowSet rs = null;
//		StringBuffer SQLProcess = new StringBuffer();
//		SQLProcess.append("select *  from t_she_webmarkfieldRelation");
//		try {
//			rs = SQLExecutorFactory.getRemoteInstance(SQLProcess.toString())
//					.executeSQL();
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} finally {
//			return rs;
//		}
//	}
//
//	private String toString(Object str, String rt) {
//		String rrt = rt;
//		try {
//			if (str != null) {
//				rrt = str.toString();
//			}
//		} catch (Exception ex) {
//		} finally {
//			return rrt;
//		}
//	}
//
//	protected IObjectValue createNewData() {
//		// TODO 自动生成方法存根
//		return null;
//	}
//
//	protected ICoreBase getBizInterface() throws Exception {
//		// TODO 自动生成方法存根
//		return null;
//	}
//}
