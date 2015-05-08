/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.ShareStyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.LinkTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;

/**
 * output class name
 */
public class WBSTemplatePrefixEntryF7UI extends	AbstractWBSTemplatePrefixEntryF7UI {
	private static final Logger logger = CoreUIObject
			.getLogger(WBSTemplatePrefixEntryF7UI.class);

	private final String COL_NUM = "number";
	private final String COL_NAME = "name";
	private final String COL_ID = "id";
	private final String COL_LEVEL = "level";
	private WBSTemplatePrefixEntryCollection prifixTaskCollection;
	public WBSTemplatePrefixEntryF7UI() throws Exception {
		super();
	}
	public void storeFields() {
		super.storeFields();
	}

	private boolean isCancel = true;
	
	public boolean isCancel(){
		return isCancel;
	}
	
	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		isCancel = true;
		this.disposeUIWindow();
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		isCancel = false;
		this.disposeUIWindow();
	}

	/**
	 * 过滤情况： 
	 * 		1.默认根节点00；
	 * 		2.所选节点的下级；
	 * 		3.所选节点的直系上级；
	 * 		4.已有的不能重复选择；
	 * 		4.校验避免所选的前置任务不出现死循环
	 */
	private void fillTable() {
		KDTable table = (KDTable)getUIContext().get("table");
		String selecteNum = null;
		if(getUIContext().get("selectedNum") != null){
			selecteNum = getUIContext().get("selectedNum").toString();
		}
		if(table != null && table.getRowCount()>0){
			for(int i=0;i<table.getRowCount();i++){
				if(table.getCell(i, COL_NUM).getValue()!= null
						&& !(table.getCell(i, COL_NUM).getValue().toString().equals("00"))){				//默认根节点00；
					//所选节点的下级及所选节点的直系上级
					if(table.getCell(i,COL_NUM).getValue().toString().startsWith(selecteNum)
							|| selecteNum.startsWith(table.getCell(i,COL_NUM).getValue().toString())){			
						continue;
					}
					IRow row = kdtPrefixTask.addRow();
					row .getCell(COL_NAME).setValue(table.getCell(i, COL_NAME).getValue());
					row .getCell(COL_ID).setValue(table.getCell(i, COL_ID).getValue());
					row .getCell(COL_NUM).setValue(table.getCell(i, COL_NUM).getValue());
					row .getCell(COL_LEVEL).setValue(table.getCell(i, COL_LEVEL).getValue());
				}
			}
		}
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat(){
			public String format(Object o) {
				return o.toString().replace('!', '.');
			}
		});
		IColumn colNum = kdtPrefixTask.getColumn(COL_NUM);
		colNum.setRenderer(render);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		return sic;
	}

	public void onLoad() throws Exception {
		kdtPrefixTask.checkParsed();
		IColumn colName = kdtPrefixTask.getColumn(COL_NAME);
		IColumn colNum = kdtPrefixTask.getColumn(COL_NUM);
		IColumn colLevel = kdtPrefixTask.getColumn(COL_LEVEL);
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(80);
		colName.setEditor(new KDTDefaultCellEditor(txtField));
		colNum.setEditor(new KDTDefaultCellEditor(txtField));
		KDFormattedTextField leveTxt = new KDFormattedTextField();
		leveTxt.setMaximumValue(new BigDecimal(1000));
		leveTxt.setMinimumValue(new BigDecimal(0));
		leveTxt.setPrecision(0);
		leveTxt.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		colLevel.setEditor(new KDTDefaultCellEditor(leveTxt));
		super.onLoad();
		fillTable();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		menuBar.setVisible(false);
	}
	public Object[] getData(){
		if(kdtPrefixTask == null || kdtPrefixTask.getRowCount()<1) return null;
		int actRowIndex = kdtPrefixTask.getSelectManager().getActiveRowIndex();
		if(actRowIndex < 0) return null;
		WBSTemplateEntryInfo wbsTMPEntryInfo = new WBSTemplateEntryInfo();
		if(kdtPrefixTask.getCell(actRowIndex, COL_ID).getValue() != null){
			wbsTMPEntryInfo.setId(BOSUuid.read(
					kdtPrefixTask.getCell(actRowIndex, COL_ID).getValue().toString()));
		}
		if(kdtPrefixTask.getCell(actRowIndex, COL_NAME).getValue() != null){
			wbsTMPEntryInfo.setName(kdtPrefixTask.getCell(actRowIndex, COL_NAME).getValue().toString());
		}
		if(kdtPrefixTask.getCell(actRowIndex, COL_NUM).getValue()!= null){
			wbsTMPEntryInfo.setNumber(kdtPrefixTask.getCell(actRowIndex, COL_NUM).getValue().toString());
		}
		Object[] os = new Object[1];
		os[0] = wbsTMPEntryInfo;
		return os;
	}
	public boolean destroyWindow() {
		isCancel=true;
		return super.destroyWindow();
	}
}