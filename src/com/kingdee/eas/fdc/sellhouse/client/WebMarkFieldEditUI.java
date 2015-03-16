///**
// * output package name
// */
//package com.kingdee.eas.fdc.sellhouse.client;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.Vector;
//
//import javax.swing.Action;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.MutableTreeNode;
//import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeSelectionModel;
//
//import org.apache.log4j.Logger;
//import org.htmlparser.Node;
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.filters.NodeClassFilter;
//import org.htmlparser.filters.OrFilter;
//import org.htmlparser.nodes.TagNode;
//import org.htmlparser.tags.InputTag;
//import org.htmlparser.tags.OptionTag;
//import org.htmlparser.tags.SelectTag;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.util.ParserException;
//import org.jdesktop.jdic.browser.WebBrowser;
//import org.jdesktop.jdic.browser.WebBrowserEvent;
//import org.jdesktop.jdic.browser.WebBrowserListener;
//import org.jdesktop.jdic.init.JdicManager;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.ctrl.kdf.table.ICell;
//import com.kingdee.bos.ctrl.kdf.table.IColumn;
//import com.kingdee.bos.ctrl.kdf.table.IRow;
//import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
//import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
//import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
//import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
//import com.kingdee.bos.ctrl.swing.KDComboBox;
//import com.kingdee.bos.ctrl.swing.KDTextField;
//import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
//import com.kingdee.bos.dao.IObjectValue;
//import com.kingdee.bos.dao.query.SQLExecutorFactory;
//import com.kingdee.bos.ui.face.CoreUIObject;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
//import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldFactory;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFieldInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection;
//import com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo;
//import com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum;
//import com.kingdee.eas.framework.CoreBaseCollection;
//import com.kingdee.eas.framework.ICoreBase;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.client.EASResource;
//import com.kingdee.eas.util.client.MsgBox;
//import com.kingdee.jdbc.rowset.IRowSet;
//import com.kingdee.util.enums.EnumUtils;
//
///**
// * output class name
// */
//public class WebMarkFieldEditUI extends AbstractWebMarkFieldEditUI {
//	private static final Logger logger = CoreUIObject
//			.getLogger(WebMarkFieldEditUI.class);
//
//	private boolean dataChange = false;
//
//	String sSchmID = "";
//
//	// String sModule = "";
//
//	IRowSet rsFunc = null;
//
//	HashMap fdcFieldMap = null;
//
//	WebBrowser webBrowser = null;
//
//	boolean isLoadProcEnd = false;
//
//	protected boolean isEnabled = false;
//
//	// HashMap funcMap = null;
//	// WebMarkFunctionCollection funcColls = null;
//	// HashMap procMap = null;
//	// WebMarkProcessCollection procColls = null;
//
//	/**
//	 * output class constructor
//	 */
//	public WebMarkFieldEditUI() throws Exception {
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
//	 * output actionOnLoad_actionPerformed
//	 */
//	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
//		super.actionOnLoad_actionPerformed(e);
//	}
//
//	/**
//	 * output actionSave_actionPerformed
//	 */
//	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
//		// super.actionSave_actionPerformed(e);
//		saveField();
//	}
//
//	/*
//	 * 装载表格
//	 */
//	private void setFields(IRowSet rs) {
//		this.tblEntrys.removeRows();
//		try {
//			while (rs.next()) {
//				IRow row = this.tblEntrys.addRow();
//				// 描述名称
//				row.getCell("FName").setValue(rs.getString("FName"));
//				// ID
//				row.getCell("FWebID").setValue(rs.getString("FWebID"));
//				// name
//				row.getCell("FWebName").setValue(rs.getString("FWebName"));
//				// 元素类型
//				row.getCell("FWebMetaType").setValue(
//						WebMetaTypeEnum.getEnum(rs.getString("FWebMetaType")));
//				// 实际值
//				row.getCell("FWebValue").setValue(rs.getString("FWebValue"));
//				// 系统数据
//				row.getCell("FFileName").setValue(
//						reMapName(fdcFieldMap, rs.getString("FFileName")));
//				// 对应数据
//				row.getCell("FDefultValue").setValue(
//						rs.getString("FDefultValue"));
//				// webid
//				row.getCell("FID").setValue(rs.getString("FID"));
//			}
//		} catch (SQLException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//	}
//
//	private String reMapName(HashMap map, String value) {
//		String sRe = "";
//		Set set = map.entrySet();
//		Iterator it = set.iterator();
//		while (it.hasNext()) {
//			Map.Entry entry = (Map.Entry) it.next();
//			if (entry.getValue().equals(value)) {
//				sRe = entry.getKey().toString();
//				break;
//			}
//		}
//		return sRe;
//	} /*
//		 * pack Entrys
//		 */
//
//	private CoreBaseCollection packFields() {
//		WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProc
//				.getUserObject();
//		if (procColls == null || procColls.size() < 1)
//			return null;
//		WebMarkProcessInfo procInfo = procColls.get(cmbProc.getSelectedIndex());
//		String pk = procInfo.getId().toString();
//
//		CoreBaseCollection colls = new CoreBaseCollection();
//		WebMarkFieldInfo wkFieldInfo = null;
//		for (int i = 0; i < this.tblEntrys.getRowCount(); i++) {
//			IRow row = this.tblEntrys.getRow(i);
//			wkFieldInfo = new WebMarkFieldInfo();
//			if (row.getCell(1).getValue()==null || row.getCell(1).getValue().toString().equals(""))
//				break;
//
//			// 描述名称
//			wkFieldInfo.setName(toString(row.getCell("FName").getValue(), ""));
//			// ID
//			wkFieldInfo
//					.setWebID(toString(row.getCell("FWebID").getValue(), ""));
//			// name
//			wkFieldInfo.setWebName(toString(row.getCell("FWebName").getValue(),
//					""));
//			// 元素类型
//			wkFieldInfo.setWebMetaType((WebMetaTypeEnum) row.getCell(
//					"FWebMetaType").getValue());
//			// 实际值
//			wkFieldInfo.setWebValue(toString(row.getCell("FWebValue")
//					.getValue(), ""));
//			// 系统数据
//			String selField = toString(fdcFieldMap.get(toString(row.getCell(
//					"FFileName").getValue().toString().trim(), "")), "");
//			wkFieldInfo.setFileName(selField.toString());
//			// 对应数据
//			wkFieldInfo.setDefultValue(toString(row.getCell("FDefultValue")
//					.getValue(), ""));
//			// parentid
//			WebMarkProcessInfo wkProcInfo = new WebMarkProcessInfo();
//			// String pk = ((HashMap)
//			// procMap.get(cmbProc.getSelectedItem())).get(
//			// "FID").toString().trim();
//			wkProcInfo.setId(BOSUuid.read(pk));
//			wkFieldInfo.setParent(wkProcInfo);
//
//			// fseq
//			wkFieldInfo.setSeq(i);
//			colls.add(wkFieldInfo);
//		}
//		return colls;
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
//	/**
//	 * output actionSubmit_actionPerformed
//	 */
//	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		// super.actionSubmit_actionPerformed(e);
//		saveField();
//	}
//
//	public void loadFields() {
//		super.loadFields();
//	}
//
//	/*
//	 * 保存、暂存、修改操作
//	 */
//
//	public void saveField() throws Exception {
//		try {
//			
//			checkSystemData();
//			CoreBaseCollection wkFieldColls = packFields();
//			if (wkFieldColls == null)
//				return;
//			if (wkFieldColls.size() < 1)
//				return;
//			WebMarkFieldFactory.getRemoteInstance().submit(wkFieldColls);
//			IRowSet rs = loadField();
//			if (rs != null)
//				setFields(rs);
//			dataChange = false;
//			FDCMsgBox.showInfo("保存成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 校验“系统数据”不能为空
//	 */
//	private void checkSystemData(){
//		for (int i = 0; i < this.tblEntrys.getRowCount(); i++) {
//			IRow row = tblEntrys.getRow(i);
//			if(row==null){
//				continue;
//			}
//			if(row.getCell(1).getValue()==null || "".equals(row.getCell(1).getValue().toString())){
//				FDCMsgBox.showWarning(this,"第"+(i+1)+"行的ID不能为空!");
//				SysUtil.abort();
//			}else if(row.getCell("FFileName").getValue()==null || "".equals(row.getCell("FFileName").getValue().toString())){
//				FDCMsgBox.showWarning(this,"第"+(i+1)+"行的系统数据不能为空!");
//				SysUtil.abort();
//			}
//		}
//	}
//	/**
//	 * output actionAddMeta_actionPerformed method
//	 */
//	public void actionAddMeta_actionPerformed(ActionEvent e) throws Exception {
//		if (this.kDTreeWeb.getRowCount() <= 1) {
//			FDCMsgBox.showInfo("元素tree中没有元素，请重新刷新元素！");
//			return;
//		}
//		if (kDTreeWeb.getSelectionCount() < 1) {
//			FDCMsgBox.showInfo("没有选中元素！");
//			return;
//		}
//		this.tblEntrys.checkParsed();
//		int index = -1;
//		index = this.tblEntrys.getRowCount();
//
//		if (index != -1) {
//			tblEntrys.addRow(index);
//		} else {
//			tblEntrys.addRow();
//		}
//	
//		if(this.tblEntrys.getColumn("FWebID")!=null){
//			this.tblEntrys.getColumn("FWebID").setRequired(true);
//		}
//		if(this.tblEntrys.getColumn("FFileName")!=null){
//			this.tblEntrys.getColumn("FFileName").setRequired(true);
//		}
//		
//		index = this.tblEntrys.getRowCount() - 1;
//		HashMap treeMap = (HashMap) ((DefaultKingdeeTreeNode) kDTreeWeb
//				.getLastSelectedPathComponent()).getUserObject();
//		if (treeMap == null)
//			return;
//
//		// ID
//		this.tblEntrys.getCell(index, "FWebID").setValue(
//				toString(treeMap.get("id"), ""));
//		// name
//		this.tblEntrys.getCell(index, "FWebName").setValue(
//				toString(treeMap.get("name"), ""));
//		// 元素类型
//		WebMetaTypeEnum metaType = (WebMetaTypeEnum) treeMap.get("type");
//		this.tblEntrys.getCell(index, "FWebMetaType").setValue(metaType);
//		// 系统字段
//		// KDComboBox combo= (KDComboBox)
//		// this.tblEntrys.getCell(index,"FFileName").getEditor().getComponent();
//		// if(combo.getItemCount()>0)
//		// combo.setSelectedIndex(0);
////		KDTDefaultCellEditor txtEditor = (KDTDefaultCellEditor) this.tblEntrys
////				.getCell(index, "FFileName").getEditor();
//		// 实际值
//		tblEntrys.getCell(index, "FWebValue").setEditor(
//				new KDTDefaultCellEditor(new KDTextField()));
//		if (metaType.getValue() == WebMetaTypeEnum.select.getValue()) {
//			tblEntrys.getCell(index, "FWebValue").setEditor(
//					new KDTDefaultCellEditor(new KDComboBox()));
//			KDTDefaultCellEditor txtEditor = (KDTDefaultCellEditor) tblEntrys
//					.getCell(index, "FWebValue").getEditor();
//			KDComboBox combobox = (KDComboBox) txtEditor.getComponent();
//			Vector v = (Vector) treeMap.get("selectList");
//			if (v == null)
//				return;
//			for (int i = 0; i < v.size(); i++) {
//				combobox.addItem(v.get(i));
//			}
//		}
//
//	}
//
//	/**
//	 * output actionDelMeta_actionPerformed method
//	 */
//	public void actionDelMeta_actionPerformed(ActionEvent e) throws Exception {
//		tblEntrys.checkParsed();
//		int i = -1;
//		i = this.tblEntrys.getSelectManager().getActiveRowIndex();
//		if (i == -1) {
//			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString(
//					"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
//					"Selected_Delete"));
//			return;
//		}
//		this.tblEntrys.removeRow(i);
//	}
//
//	/**
//	 * output actionUpMeta_actionPerformed method
//	 */
//	public void actionUpMeta_actionPerformed(ActionEvent e) throws Exception {
//		try {
//			tblEntrys.checkParsed();
//			int lCurRow = this.tblEntrys.getSelectManager().getActiveRowIndex();
//			if (lCurRow < 1)
//				return;
//			this.tblEntrys.moveRow(lCurRow, lCurRow - 1);
//			this.tblEntrys.getSelectManager().setActiveRowIndex(lCurRow - 1);
//			this.tblEntrys.getSelectManager().select(lCurRow - 1, 0,
//					lCurRow - 1, this.tblEntrys.getColumnCount());
//		} catch (Exception e1) {
//			e1 = null;
//		}
//	}
//
//	/**
//	 * output actionDownMeta_actionPerformed method
//	 */
//	public void actionDownMeta_actionPerformed(ActionEvent e) throws Exception {
//		try {
//			int lCurRow = this.tblEntrys.getSelectManager().getActiveRowIndex();
//			if (lCurRow == this.tblEntrys.getRowCount() - 1)
//				return;
//			boolean bRe = this.tblEntrys.exchangeRow(lCurRow, lCurRow + 1);
//			System.out.println(bRe);
//			this.tblEntrys.getSelectManager().setActiveRowIndex(lCurRow + 1);
//			this.tblEntrys.getSelectManager().select(lCurRow + 1, 0,
//					lCurRow + 1, this.tblEntrys.getColumnCount());
//		} catch (Exception e1) {
//			e1 = null;
//		}
//
//	}
//
//	/**
//	 * output actionRefreshMate_actionPerformed method
//	 */
//	public void actionRefreshMate_actionPerformed(ActionEvent e)
//			throws Exception {
//		if (this.cmbMetaType.getSelectedItem().toString().trim().equals(""))
//			return;
//		extractText(cmbMetaType.getSelectedItem().toString());
//	}
//
//	/**
//	 * output actionRefreshWeb_actionPerformed method
//	 */
//	public void actionRefreshWeb_actionPerformed(ActionEvent e)
//			throws Exception {
//		loadURL();
//	}
//
//	private void loadContext() {
//		// rsFunc = (IRowSet) this.getUIContext().get("funcData");
//		sSchmID = (String) this.getUIContext().get("schemaid");
//		// sModule = (String) this.getUIContext().get("moduleid");
//	}
//
//	/*
//	 * 得到方案功能
//	 */
//	private IRowSet getFunctionData(String sSchmID) {
//		StringBuffer SQLProcess = new StringBuffer();
//		// String ssql = arrrayList2String(sqlList);
//		SQLProcess.append("SELECT * FROM t_she_WebMarkFunction where 1=1");
//
//		if (!sSchmID.trim().equals("")) {
//			SQLProcess.append(" and FParentID='" + sSchmID.toString() + "'");
//		}
//		try {
//			IRowSet rs = SQLExecutorFactory.getRemoteInstance(
//					SQLProcess.toString()).executeSQL();
//			return rs;
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	private void funcCombox_ActionChange(ActionEvent e) {
//		isLoadProcEnd = false;
//		cmbProc.removeAllItems();
//		WebMarkProcessCollection procColls = new WebMarkProcessCollection();
//		// procMap = new HashMap();
//		// HashMap innerMap = null;
//		if (cmbFunc.getUserObject() == null)
//			return;
//		WebMarkFunctionInfo funcInfo = ((WebMarkFunctionCollection) cmbFunc
//				.getUserObject()).get(cmbFunc.getSelectedIndex());
//		if (funcInfo == null || funcInfo.getId().toString().equals(""))
//			return;
//		KDComboBox cb = (KDComboBox) e.getSource();
//		String sName = (String) cb.getSelectedItem();
//		// String sID = funcMap.get(sName).toString();
//		IRowSet rsProc = getProcessData(toString(funcInfo.getId(), ""));
//		WebMarkProcessInfo procInfo;
//		try {
//			while (rsProc.next()) {
//				// innerMap = new HashMap();
//				procInfo = new WebMarkProcessInfo();
//				procInfo.setId(BOSUuid.read(rsProc.getString("FID").trim()
//						.toString()));
//				procInfo.setUrl(rsProc.getString("FUrl").trim().toString());
//				procInfo.setProcessName(rsProc.getString("FProcessName_l2")
//						.trim().toString());
//				// innerMap
//				// .put("FUrl", rsProc.getString("FUrl").trim().toString());
//				// innerMap.put("FID",
//				// rsProc.getString("FID").trim().toString());
//				// rsProc.getString("FUrl").trim().toString()
//				// procMap.put(rsProc.getString("FProcessName_l2").trim()
//				// .toString(), innerMap);
//				cmbProc.addItem(rsProc.getString("FProcessName_l2").trim()
//						.toString());
//				procColls.add(procInfo);
//			}
//			cmbProc.setUserObject(procColls);
//		} catch (SQLException e1) {
//			// TODO 自动生成 catch 块
//			e1.printStackTrace();
//		}
//		if (cb.getItemCount() < 1) {
//			return;
//		}
//		cmbProc.setSelectedIndex(-1);
//		isLoadProcEnd = true;
//	}
//
//	/*
//	 * 步骤COMBOX选择事件
//	 */
//	private void procCombox_itemStateChanged(ItemEvent e) throws SQLException {
//		if (isLoadProcEnd == false) {
//			return;
//		}
//		KDComboBox cb = (KDComboBox) e.getSource();
//		if (cb.getItemCount() < 1) {
//			return;
//		}
//		this.txtURL.setText("");
//		WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProc
//				.getUserObject();
//		if (procColls == null) {
//			return;
//		}
//		if (cb == null) {
//			return;
//		}
//		// String sName = (String) cb.getSelectedItem();
//		WebMarkProcessInfo procInfo = procColls.get(cmbProc.getSelectedIndex());
//		this.txtURL.setText(procInfo.getUrl().toString());
//		// this.txtURL.setText(((HashMap) procMap.get(sName)).get("FUrl")
//		// .toString().trim());
//
//		loadURL();
//		// extractText("root");
//		IRowSet rs = loadField();
//		if (rs != null)
//			setFields(rs);
//	}
//
//	/*
//	 * 加载字段
//	 */
//	private IRowSet loadField() {
//		IRowSet rs = null;
//		try {
//			WebMarkProcessCollection procColls = (WebMarkProcessCollection) cmbProc
//					.getUserObject();
//			if (procColls == null || procColls.size() < 1)
//				return null;
//			WebMarkProcessInfo procInfo = procColls.get(cmbProc
//					.getSelectedIndex());
//
//			String sFuncID = procInfo.getId().toString();
//			// String sFuncID = ((HashMap) procMap.get(this.cmbProc
//			// .getSelectedItem())).get("FID").toString().trim();
//			StringBuffer SQLProcess = new StringBuffer();
//			SQLProcess
//					.append("select * from t_SHE_WebMarkField where FParentID='"
//							+ sFuncID.toString() + "' order by FSeq");
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
//	public void setFilterComb() {
//		this.cmbMetaType.removeAllItems();
//		this.cmbMetaType.addItem("ALL");
//		this.cmbMetaType.addItems(EnumUtils.getEnumList(
//				"com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum").toArray());
//		this.cmbMetaType.setSelectedIndex(0);
//	}
//
//	private void setControlEvent() {
//		// 功能-事件加载
//		this.cmbFunc.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				funcCombox_ActionChange(arg0);
//			}
//		});
//		// 步骤-事件加载
//		this.cmbProc.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent arg0) {
//				// TODO 自动生成方法存根
//				try {
//					procCombox_itemStateChanged(arg0);
//				} catch (SQLException e) {
//					// TODO 自动生成 catch 块
//					e.printStackTrace();
//				}
//			}
//		});
//
//		tblEntrys.addKDTEditListener(new KDTEditAdapter() {
//			// 编辑结束后
//			public void editStopped(KDTEditEvent e) {
//				dataChange = true;
//			}
//		});
//
//	}
//
//	private void setBtnStatus() {
//		this.actionAddRow.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_addline"));
//		this.actionInsertRow.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_insert"));
//		this.actionDelRow.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_deleteline"));
//
//		// this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.SellProjectQuery");
//	}
//
//	
//	
//	/*
//	 * 初始化
//	 */
//	public void onLoad() throws Exception {
//		loadContext();
//		super.onLoad();
//		// 控件初始化
//		txtMsg.setText("");
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
//		
//		this.btnAddMeta.setEnabled(true);
//		this.btnDelMeta.setEnabled(true);
//		this.btnUpMeta.setEnabled(true);
//		this.btnDownMeta.setEnabled(true);
//		this.btnRefreshMate.setEnabled(true);
//		this.btnRefreshWeb.setEnabled(true);
//		this.tblEntrys.setColumnMoveable(true);
//		this.kDTreeWeb
//				.removeAllChildrenFromParent((MutableTreeNode) this.kDTreeWeb
//						.getModel().getRoot());
//		this.kDTreeWeb.getSelectionModel().setSelectionMode(
//				TreeSelectionModel.SINGLE_TREE_SELECTION);// 设置单选模式
//		tblEntrys.checkParsed();
//	
//		//setBtnStatus();		by jeegan 感觉没什么用
//		setFilterComb();
//		cmbFunc.removeAllItems();
//		// 加载功能和步骤
//		rsFunc = this.getFunctionData(sSchmID);
//		if (rsFunc != null) {
//			// funcMap = new HashMap();
//			WebMarkFunctionCollection funcColls = new WebMarkFunctionCollection();
//			WebMarkFunctionInfo funcInfo;
//			while (rsFunc.next()) {
//				funcInfo = new WebMarkFunctionInfo();
//				if(rsFunc.getString("FID")!=null && !"".equals(rsFunc.getString("FID").toString())){
//					funcInfo.setId(BOSUuid.read(rsFunc.getString("FID").trim()
//							.toString()));
//				
//				}
//				
//				if(rsFunc.getString("FFunctionName_l2")!=null && !"".equals(rsFunc.getString("FFunctionName_l2").toString())){
//					funcInfo.setFunctionName(rsFunc.getString("FFunctionName_l2")
//							.trim().toString());
//				}
//				
//				funcColls.add(funcInfo);
//				// funcMap.put(rsFunc.getString("FFunctionName_l2").trim()
//				// .toString(), rsFunc.getString("FID").trim().toString());
//				
//				if(rsFunc.getString("FFunctionName_l2")!=null && !"".equals(rsFunc.getString("FFunctionName_l2").toString())){
//					this.cmbFunc.addItem(rsFunc.getString("FFunctionName_l2")
//							.trim().toString());
//				}
//				
//			
//			}
//			this.cmbFunc.setUserObject(funcColls);
//		}
//		cmbFunc.setSelectedIndex(-1);
//		// 控件事件
//		setControlEvent();
//		// 分录初始化
//		setEntrys();
//		// this.tblEntrys.getViewManager().freezeView(1,1);
//		try {
//			// 初始化WebBrowser
//			// 这里开始已经改造了jdic的org.jdesktop.jdic.init.JdicManager,initShareNative方法
//			// 和加入了setBinaryPath()方法
//			String path = System.getProperty("easclient.root");
//			/**
//			 * 重新整理的加载dll的目录问题
//			 * update by renliang 
//			 */
//			CRMClientHelper.getDLLForWebMark(path);
//			
//			logger.info("WebMark:***JdicManager.getManager().setBinaryPath(path);");
//			JdicManager.getManager().setBinaryPath(path);
//			logger.info("WebMark:***webBrowser = new WebBrowser();");
//			webBrowser = new WebBrowser();
//			logger.info("WebMark:***initWebBowser();");
//			initWebBowser();
//			logger.info("WebMark:***kdPWebBowser.setAutoscrolls(true);");
//			kdPWebBowser.setAutoscrolls(true);
//			sclPanel.setViewportView(webBrowser);
//			sclPanel.setKeyBoardControl(true);
//			sclPanel.setAutoscrolls(true);
//
//			logger.info("WebMark:***this.webBrowser.setURL();");
//			this.webBrowser.setURL();
//		} catch (Exception e) {
////			e.printStackTrace();
//			MsgBox.showError("easclient.root="+System.getProperty("easclient.root")+";jdic.dll可能已经其它模块加载！");
//		}
//	}
//
//	/*
//	 * htmlparser -webBowser 初始化网页浏览窗口事件
//	 */
//	private void initWebBowser() {
//		webBrowser.addWebBrowserListener(new WebBrowserListener() {
//			public void downloadStarted(WebBrowserEvent event) {
//				updateStatusInfo("Loading started.");
//			}
//
//			public void downloadCompleted(WebBrowserEvent event) {
//				updateStatusInfo("Loading completed.");
//			}
//
//			public void downloadProgress(WebBrowserEvent event) {
//				// updateStatusInfo("Loading in progress...");
//			}
//
//			public void downloadError(WebBrowserEvent event) {
//				updateStatusInfo("Loading error.");
//			}
//
//			public void documentCompleted(WebBrowserEvent event) {
//				webBrowser
//						.executeScript("document.onclick=function(){document.title=document.activeElement.name;}");
//				// updateStatusInfo("Document loading completed.");
//			}
//
//			public void titleChange(WebBrowserEvent event) {
//				setTreeSelect(event);
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
//	 * 根据webBrowsers事件中的字段，对kdTree中的字段进行选择。
//	 */
//	private void setTreeSelect(WebBrowserEvent event) {
//		boolean isGet = false;
//		txtMsg.setText("");
//		if (webBrowser.getURL() == null)
//			return;
//		if (webBrowser.getURL().toString().equals(""))
//			return;
//		try {
//			String nodeName = event.getData().toString();
//			// System.out.println(nodeName);
//			if (kDTreeWeb.getRowCount() < 1)
//				return;
//			DefaultMutableTreeNode myRoot = (DefaultMutableTreeNode) this.kDTreeWeb
//					.getModel().getRoot();
//			Enumeration e = myRoot.breadthFirstEnumeration();
//			DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
//			String sSelNode;
//			while (e.hasMoreElements()) {
//				node = (DefaultMutableTreeNode) e.nextElement();
//				sSelNode = node.toString();
//				if (sSelNode.equals(nodeName)) {
//					kDTreeWeb.setSelectionPath(new TreePath(node.getPath()));
//					kDTreeWeb.scrollPathToVisible(new TreePath(node.getPath()));
//					isGet = true;
//					txtMsg.setText("元素在Tree中已选定");
//					break;
//				}
//			}
//			if (isGet == false
//					&& kDTreeWeb.getRowCount() > 1
//					&& kDTreeWeb.getModel().getChildCount(
//							kDTreeWeb.getModel().getRoot()) > 0) {
//				txtMsg.setText("提示信息：没有找到网页元素！");
//				kDTreeWeb.setSelectionRow(-1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			e = null;
//		}
//	}
//
//	/**
//	 * 加载网页
//	 * 输入的地址可以是客户端文件的地址 比如：c:/test.txt 会转换为 file:///C:/txt.txt
//	 * 另外如果地址类似于 ftp.*.* 会转换为 ftp://ftp.*.* 
//	 * gopher.*.* 会转换为 gopher://gopher.*.*
//	 * http.*.* 会转换为 http://http.*.*
//	 */
//	void loadURL() {
//		this.webBrowser.setURL();
//		this.kDTreeWeb
//				.removeAllChildrenFromParent((MutableTreeNode) this.kDTreeWeb
//						.getModel().getRoot());
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
//				updateStatusInfo("Loading " + curUrl.toString() + " ......");
//
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
//	/**
//	 * 得到网页元素
//	 */
//	private Node[] getWebNodes() {
//		Node[] nodes = null;
//		NodeClassFilter tag = null;
//		String sFilter = "";
//		try {
//			StringBuffer inputHtml = new StringBuffer();
//			System.out.println("1122");
//			inputHtml.append(webBrowser.getContent().toString());
//			System.out.println("getContent");
//			if (inputHtml == null)
//				return null;
//			if (inputHtml.toString().trim().equals(""))
//				return null;
//			Parser parser = Parser.createParser(inputHtml.toString(), "GBK");
//			// 方法一：
//			OrFilter lastFilter = new OrFilter();
//			int lSel = this.cmbMetaType.getSelectedIndex();
//			switch (lSel) {
//			default:
//				System.out.println("default");
//				lastFilter.setPredicates(new NodeFilter[] {
//						new NodeClassFilter(InputTag.class),
//						new NodeClassFilter(SelectTag.class) });
//				break;
//			case 1:
//				System.out.println("input");
//				sFilter = WebMetaTypeEnum.INPUT_VALUE;
//				lastFilter
//						.setPredicates(new NodeFilter[] { new NodeClassFilter(
//								InputTag.class) });
//				break;
//			case 2:
//				System.out.println("select");
//				sFilter = WebMetaTypeEnum.SELECT_VALUE;
//				lastFilter
//						.setPredicates(new NodeFilter[] { new NodeClassFilter(
//								SelectTag.class) });
//				break;
//			}
//			NodeList nodelist;
//			nodelist = parser.parse(lastFilter);
//			nodes = nodelist.toNodeArray();
//
//			// System.out.println("nodes.length=" + nodes.length);
//			// 方法二：
//			// NodeList nodelist = parser
//			// .extractAllNodesThatMatch(new TagNameFilter(sFilter));
//			// System.out.println(" nodes size 1=== " + nodelist.size());
//			// for (int i = 0; i < nodelist.size(); i++) {
//			// treeMap = new HashMap();
//			// // LinkTag link=(LinkTag) nodelist.elementAt(i);
//			// InputTag input = (InputTag) nodelist.elementAt(i);
//			// System.out.println(input.getAttribute("name") + "\n");
//			// treeMap.put("name", input.getAttribute("name"));
//			// }
//			// 方法三：
//			// HtmlPage page = new HtmlPage(parser);
//			// parser.visitAllNodesWith(page);
//			// NodeList nodelist = page.getBody();
//			// System.out.println(" nodes size 1=== " + nodelist.size());
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
//	 * 遍历所有的节点
//	 */
//	private void extractText(String sRoot) {
//		try {
//			// System.out.println("11111");
//			Node[] nodes = getWebNodes();
//			if (nodes == null)
//				return;
//			DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(sRoot);
//			((DefaultTreeModel) this.kDTreeWeb.getModel()).setRoot(root);
//			// this.kDTreeWeb.setNodeText(root,root.toString());
//			DefaultKingdeeTreeNode child = null;
//			HashMap treeMap = null;
//			TagNode tagNode = null;
//			for (int i = 0; i < nodes.length; i++) {
//				treeMap = new HashMap();
//				Node node = nodes[i];
//				if (node instanceof InputTag) {
//					tagNode = (InputTag) node;
//					treeMap.put("type", WebMetaTypeEnum.input);
//				} else if (node instanceof SelectTag) {
//					tagNode = (SelectTag) node;
//					treeMap.put("type", WebMetaTypeEnum.select);
//					// Vector v = tagNode.getAttributesEx();
//					Vector vector = new Vector();
//					NodeList nlist = node.getChildren();
//					Node[] nodeArry = nlist.toNodeArray();
//					for (int j = 0; j < nodeArry.length; j++) {
//						Node optnode = (Node) nodeArry[j];
//						if (optnode instanceof OptionTag) {
//							OptionTag opttag = (OptionTag) optnode;
//							// Vector vv = opttag.getAttributesEx();
//							vector.add(opttag.getOptionText());
//							// if (vv.toString().indexOf("selected") != -1)
//							// select_value = opttag.getOptionText();
//						}
//					}
//					treeMap.put("selectList", vector);
//				} else {
//					treeMap.put("type", "");
//				}
//				treeMap.put("name", tagNode.getAttribute("name"));
//				treeMap.put("id", tagNode.getAttribute("id"));
//				child = new DefaultKingdeeTreeNode(tagNode.getAttribute("name"));
//				child.setUserObject(treeMap);
//				kDTreeWeb.addNodeInto(child, root);
//			}
//			kDTreeWeb.expandAllNodes(true, (DefaultKingdeeTreeNode) kDTreeWeb
//					.getModel().getRoot());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 得到功能步骤
//	 */
//	private IRowSet getProcessData(String sFuncID) {
//		if (sFuncID.trim().equals("")) {
//			return null;
//		}
//		StringBuffer SQLProcess = new StringBuffer();
//		// String ssql = arrrayList2String(sqlList);
//		SQLProcess.append("SELECT A.* FROM t_she_WebMarkProcess a");
//		SQLProcess
//				.append(" inner join t_she_WebMarkFunction b on a.FParentID=b.FID ");
//		SQLProcess.append(" WHERE 1 = 1 AND A.FParentID= '"
//				+ sFuncID.toString() + "'");
//		try {
//			IRowSet rs = SQLExecutorFactory.getRemoteInstance(
//					SQLProcess.toString()).executeSQL();
//			return rs;
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	void updateStatusInfo(String statusMessage) {
//
//	}
//
//	private void closeBrowser() {
//		try {
//			webBrowser.stop();
//			webBrowser.setVisible(false);
//			// webBrowser.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		super.disposeUIWindow();
//	}
//
//	protected void disposeUIWindow() {
//		// 需要根据字段对应设置来同步更新dataChange状态
//		if (dataChange) {
//			int rtn = MsgBox.showConfirm3("数据已修改,是否在保存之后退出?");
//			if (rtn == MsgBox.OK) {
//				try {
//					saveField();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				closeBrowser();
//			} else if (rtn == MsgBox.NO) {
//				closeBrowser();
//			} else if (rtn == MsgBox.CANCEL) {
//				return;
//			}
//		} else {
//			closeBrowser();
//		}
//	}
//
//	protected void verifyInput(ActionEvent e) throws Exception {
//		super.verifyInput(e);
//	}
//
//	protected IObjectValue createNewData() {
//		// TODO 自动生成方法存根
//		// WebMarkFieldInfo fieldInfo=new WebMarkFieldInfo();
//		// fieldInfo.setIsEnabled(isEnabled);
//		return null;
//	}
//
//	protected ICoreBase getBizInterface() throws Exception {
//		// TODO 自动生成方法存根
//		return WebMarkFieldFactory.getRemoteInstance();
//	}
//
//	private void setEntrys() {
//		// 表格设置
//		tblEntrys.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		tblEntrys.removeHeadRows();
//		tblEntrys.removeRows();
//		tblEntrys.addHeadRow();
//		tblEntrys.checkParsed();
//		tblEntrys.getStyleAttributes().setLocked(false);
//
//		addTxtFieldColumn("FName", "描述名称", 80, false);
//		addTxtFieldColumn("FWebID", "ID", 80, false);
//		addTxtFieldColumn("FWebName", "name", 80, false);
//		addCombColumn("FWebMetaType", "元素类型", null,
//				"com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum", false);
//		addTxtFieldColumn("FWebValue", "实际值", 80, false);
//
//		KDComboBox combobox = setFieldComb(new KDComboBox());
//		addCombColumn("FFileName", "系统数据", combobox, "", false);
//
//		addTxtFieldColumn("FDefultValue", "对应数据", 80, false);
//		addTxtFieldColumn("FID", "webID", 80, true);
//	}
//
//	/*
//	 * 增加一列textField格式的Column ; 只限于增加一行标题行 并格式属性设置;
//	 */
//	private void addTxtFieldColumn(String sKey, String sTitle,
//			int txtMaxLenght, boolean isHide) {
//		// 表格标题设置
//		IRow headRow = tblEntrys.getHeadRow(0);
//		// 增加一列
//		IColumn headColumn = tblEntrys.addColumn();
//		headColumn.setKey(sKey);
//		headColumn.getStyleAttributes().setHided(isHide);
//		ICell headCell = headRow.getCell(sKey);
//		headCell.setValue(sTitle);
//		KDTextField textField = new KDTextField();
//		textField.setMaxLength(txtMaxLenght);
//		ICellEditor cellEditor = new KDTDefaultCellEditor(textField);
//		headColumn.setEditor(cellEditor);
//	}
//
//	/*
//	 * 增加一列Combobox格式的Column; 只限于增加一行标题行 并格式属性设置;
//	 */
//	private void addCombColumn(String sKey, String sTitle, KDComboBox comb,
//			String sEnumClass, boolean isHide) {
//		// 表格标题设置
//		IRow headRow = tblEntrys.getHeadRow(0);
//		// 增加一列
//		IColumn headColumn = tblEntrys.addColumn();
//		headColumn.getStyleAttributes().setHided(isHide);
//		headColumn.setKey(sKey);
//		ICell headCell = headRow.getCell(sKey);
//		headCell.setValue(sTitle);
//		KDComboBox combobox = new KDComboBox();
//		if (comb != null) {
//			combobox = comb;
//		} else if (!sEnumClass.trim().equals("")) {
//			combobox = new KDComboBox();
//			combobox.addItems(EnumUtils.getEnumList(
//					toString(sEnumClass, "").trim()).toArray());
//		}
//		combobox.setLightWeightPopupEnabled(false);
//		ICellEditor cellEditor = new KDTDefaultCellEditor(combobox);
//		headColumn.setEditor(cellEditor);
//	}
//
//	/*
//	 * xwb取字段信息并实例化COMBOBOX
//	 */
//	private KDComboBox setFieldComb(KDComboBox combobox) {
//		StringBuffer SQLProcess = new StringBuffer();
//		SQLProcess
//				.append("select FID,FName,FField,FFromTable,FFilter,FEntry from t_she_webmarkfieldRelation order by FModule,FID");
//		// if (!sModule.trim().equals(""))
//		// SQLProcess.append(" where FModule=" + sModule);
//		try {
//			IRowSet rs = SQLExecutorFactory.getRemoteInstance(
//					SQLProcess.toString()).executeSQL();
//			fdcFieldMap = new HashMap();
//			// 加入none字段，表示使用实际默认值
//			combobox.addItem("none");
//			fdcFieldMap.put("none", "none");
//			while (rs.next()) {
//				fdcFieldMap.put(rs.getString("FName").trim(), rs
//						.getString("FID"));
//				combobox.addItem(rs.getString("FName").trim());
//			}
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} finally {
//			return combobox;
//		}
//	}
//}
