/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplateFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplateInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryFactory;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class WBSTemplateReferEditUI extends AbstractWBSTemplateReferEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(WBSTemplateReferEditUI.class);

	/**
	 * output class constructor
	 */
	private static final String COL_ID = "id";
	private static final String COL_NAME = "name";
	private static final String COL_NUMBER = "number";
	private static final String COL_ISSELECTED = "isSelected";
	private static final String COL_LEVEL = "level";
	private static final String COL_PRETASKS = "preTasks";
	private static final String COL_ISLOCKED= "isLocked";
	private static final String COL_ESTIMATEDAYS = "estimateDays";
	
	
	
	
	public WBSTemplateReferEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 在服务端处理导入，
	 */
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
//		WBSTemplateEntryCollection templateEntrys = new WBSTemplateEntryCollection();
		Map param = new HashMap();
		Map templateMap = new HashMap();
		ArrayList keys = new ArrayList();
		param.put("parent4Import", getUIContext().get("parent4Import"));
		param.put("prj4Import", getUIContext().get("prj4Import"));
		for(int i=0;i<kdtEntrys.getRowCount();i++){
			IRow row = kdtEntrys.getRow(i);
			if(!Boolean.TRUE.equals(row.getCell(COL_ISSELECTED).getValue()) 
					||(row.getCell(COL_ID).getValue()==null)) continue;
//			templateEntrys.add((WBSTemplateEntryInfo) row.getUserObject());
			templateMap.put(row.getCell(COL_NUMBER).getValue().toString(), row.getUserObject());
			keys.add(row.getCell(COL_NUMBER).getValue().toString());
		}
		param.put("selectedWBS", templateMap);
		param.put("keys", keys);
		FDCWBSFactory.getRemoteInstance().importWBSTemplate(param);
		this.uiWindow.close();
	}

	/**
	 * output btnNotOK_actionPerformed method
	 */
	protected void btnNotOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		actionExitCurrent_actionPerformed(e);
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}

	protected IObjectValue createNewData() {
		return new WBSTemplateInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WBSTemplateFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		kdtEntrys.checkParsed();
		super.onLoad();
		if(kdtEntrys.getCell(0, COL_NUMBER).getValue().toString().equals("0")){
			kdtEntrys.getRow(0).getStyleAttributes().setLocked(true);
		}
//		kdtEntrys.setEditable(true);
		kdtEntrys.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		kdtEntrys.setEditable(true);
		setTblStyle();
	}

	/**
	 * 取不到第三级entry的数据，再重新取一遍，以后优化		-by neo
	 */
	public void loadFields() {
		WBSTemplateEntryInfo wbsTPEntryInfo = new WBSTemplateEntryInfo();
		wbsTPEntryInfo.setNumber("0");
		wbsTPEntryInfo.setName("任务模板");
		wbsTPEntryInfo.setLevel(0);
		editData.getEntrys().addObject(0,wbsTPEntryInfo);
		super.loadFields();
		Set entryIds = new HashSet();
		Map preTasks = new HashMap();
		for(int i=1;i<editData.getEntrys().size();i++){
			entryIds.add(editData.getEntrys().get(i).getId().toString());
		}
		try {
			preTasks = WBSTemplatePrefixEntryFactory.getRemoteInstance().getPreTasks(entryIds);
		} catch (ScheduleException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		for(int i=1;i<editData.getEntrys().size();i++){
			WBSTemplateEntryInfo entryInfo = editData.getEntrys().get(i);
			entryInfo.getPrefixEntrys().addCollection(
					(WBSTemplatePrefixEntryCollection)preTasks.get(entryInfo.getId().toString()));
		}
		for(int i=1;i<kdtEntrys.getRowCount();i++){
			if(((WBSTemplatePrefixEntryCollection)
					preTasks.get(kdtEntrys.getCell(i, COL_ID).getValue().toString())).size()>0){
				kdtEntrys.getCell(i, COL_PRETASKS).setValue(((WBSTemplatePrefixEntryCollection)
						preTasks.get(kdtEntrys.getCell(i, COL_ID).getValue().toString())).toArray());
			}
		}
		
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		KDWorkButton btnSelectAll = new KDWorkButton("全选",	SCHClientHelper.ICON_SELECTALL);
		KDWorkButton btnDeleteAll = new KDWorkButton("全清",	SCHClientHelper.ICON_DELETEALL);
		conTbl.addButton(btnSelectAll);
		conTbl.addButton(btnDeleteAll);
		actionAddNew.setVisible(false);
		actionAttachment.setVisible(false);
		actionEdit.setVisible(false);
		actionRemove.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionSave.setVisible(false);
		btnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<kdtEntrys.getRowCount();i++){
					if(kdtEntrys.getCell(i, COL_ID).getValue() == null) continue;
					kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.TRUE);
				}
			}
		});
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<kdtEntrys.getRowCount();i++){
					if(kdtEntrys.getCell(i, COL_ID).getValue() == null) continue;
					kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.FALSE);
				}
			}
		});
	}
	/**
	 * 设置表格前置任务、编码列的显示格式
	 */
	private void setTblStyle(){
		for(int i=0;i<kdtEntrys.getRowCount();i++){
			kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.FALSE);
		}
		
		IColumn colNum = kdtEntrys.getColumn(COL_NUMBER);
		ObjectValueRender numRender = new ObjectValueRender();
		numRender.setFormat(new IDataFormat(){
			public String format(Object o) {
				if(o instanceof String){
					return o.toString().replace('!', '.');
				}else return o.toString();
			}
		});
		colNum.setRenderer(numRender);
		
		IColumn colPreTask = kdtEntrys.getColumn(COL_PRETASKS);
		ObjectValueRender preTaskRender = new ObjectValueRender();
		preTaskRender.setFormat(new IDataFormat(){
			public String format(Object o) {
				if(o instanceof WBSTemplatePrefixEntryCollection) return "";
				Object[] os = (Object[])o;
				if(o!=null && os.length>=1){
					String str = new String();
					for(int i=0;i<os.length;i++){
						if(os[i] instanceof WBSTemplatePrefixEntryInfo
								&& ((WBSTemplatePrefixEntryInfo)os[i]).getPrefixNode() !=null){
							str = str + ((WBSTemplatePrefixEntryInfo)os[i]).getPrefixNode().getNumber()+";";
						}
					}
					str = str.substring(0,str.lastIndexOf(";"));
					return str.replace('!', '.');
				}else return "";
			}
		});
		colPreTask.setRenderer(preTaskRender);
		kdtEntrys.getColumn(COL_ISSELECTED).getStyleAttributes().setLocked(false);
		IColumn colIsSelected = kdtEntrys.getColumn(COL_ISSELECTED);
		/*KDCheckBox selectBox = new KDCheckBox();
		//重点关注
		selectBox.setEditable(true);
		selectBox.setEnabled(true);
		selectBox.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int actRowIdx = kdtEntrys.getSelectManager().getActiveRowIndex();
				int actColIdx = kdtEntrys.getSelectManager().getActiveColumnIndex();
				if(actColIdx==0 && e.getClickCount() == 1 && actRowIdx>0){
					boolean isSelected = false;
					String number = kdtEntrys.getCell(actRowIdx, COL_NUMBER).getValue().toString();
					isSelected = !((Boolean)
							kdtEntrys.getCell(actRowIdx, COL_ISSELECTED).getValue()).booleanValue();
					for(int i=actRowIdx+1;i<kdtEntrys.getRowCount();i++){
						String childNum = kdtEntrys.getCell(i, COL_NUMBER).getValue().toString();
						if(childNum.startsWith(number)){
							if(isSelected)
								kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.TRUE);
							else
								kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.FALSE);
						}
					}
				}
			}
		});
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(selectBox);
		colIsSelected.setEditor(editor);*/
	}
	
	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
	/*	if(e.getColIndex()==0 && e.getClickCount() == 1 && e.getType() == 1&& e.getRowIndex()>0){
			int rowIdx = e.getRowIndex();
			boolean isSelected = false;
			String number = kdtEntrys.getCell(rowIdx, COL_NUMBER).getValue().toString();
			isSelected = !((Boolean)
					kdtEntrys.getCell(rowIdx, COL_ISSELECTED).getValue()).booleanValue();
			for(int i=rowIdx+1;i<kdtEntrys.getRowCount();i++){
				String childNum = kdtEntrys.getCell(i, COL_NUMBER).getValue().toString();
				if(childNum.startsWith(number)){
					if(isSelected)
						kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.TRUE);
					else
						kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.FALSE);
				}
			}
		}*/
	}
	
	protected void kdtEntrys_editValueChanged(KDTEditEvent e) throws Exception {
		boolean oldValue = ((Boolean) e.getOldValue()).booleanValue();
		int actRowIdx = e.getRowIndex();
		int actColIdx = e.getColIndex();
		if(actColIdx==0 && actRowIdx>0){
			String number = kdtEntrys.getCell(actRowIdx, COL_NUMBER).getValue().toString();
			for(int i=actRowIdx+1;i<kdtEntrys.getRowCount();i++){
				String childNum = kdtEntrys.getCell(i, COL_NUMBER).getValue().toString();
				if(childNum.startsWith(number)){
					//原来选中则新值是不选
					if(!oldValue)
						kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.TRUE);
					else
						kdtEntrys.getCell(i, COL_ISSELECTED).setValue(Boolean.FALSE);
				}
			}
		}
	
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("entrys.prefixEntrys.name"));
		sic.add(new SelectorItemInfo("entrys.prefixEntrys.id"));
		sic.add(new SelectorItemInfo("entrys.prefixEntrys.number"));
		return sic;
	}
	public void onShow() throws Exception {
		super.onShow();
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnSave.setVisible(false);
	}
}