/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplateEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryCollection;
import com.kingdee.eas.fdc.schedule.WBSTemplatePrefixEntryInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class WBSTemplatePrefixTaskF7UI extends	AbstractWBSTemplatePrefixTaskF7UI {
	private static final Logger logger = CoreUIObject
			.getLogger(WBSTemplatePrefixTaskF7UI.class);
	private WBSTemplateEntryCollection WBSEntryCollection;
	private final String COL_ID = "id";
	private final String COL_NAME = "name";
	private final String COL_NUMBER = "number";
	private final String COL_LINKTYPE = "linkType";
	private final String COL_LINKDAY = "linkDay";
	private WBSTemplatePrefixEntryCollection entCollection = new WBSTemplatePrefixEntryCollection();
	public WBSTemplatePrefixTaskF7UI() throws Exception {
		super();
	}
	
	public void storeFields() {
		super.storeFields();
	}

	private boolean isCancel = true;
	
	public boolean isCancel(){
		return isCancel;
	}
	
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		for(int i=0;i<kdtPrefixEntry.getRowCount();i++){
			IRow row = kdtPrefixEntry.getRow(i);
			if(row.getCell(COL_NAME).getValue() == null
					|| row.getCell(COL_NAME).getValue().toString().trim().length() < 1){
				FDCMsgBox.showWarning("第"+(i+1)+"行任务名称不能为空！");
				SysUtil.abort();
			}
			if(row.getCell(COL_NAME).getValue() != null
					&& row.getCell(COL_LINKTYPE).getValue() == null){
				FDCMsgBox.showWarning("第"+(i+1)+"行搭接关系不能为空！");
				SysUtil.abort();
			}
			for(int j=i+1;j<kdtPrefixEntry.getRowCount();j++){
				IRow row2 = kdtPrefixEntry.getRow(j);
				
				if(row.getCell(COL_NUMBER).getValue() != null
					&&row2.getCell(COL_NUMBER).getValue() != null
					&&row.getCell(COL_NUMBER).getValue().toString().equals(row2.getCell(COL_NUMBER).getValue().toString())){
					FDCMsgBox.showWarning("第"+(i+1)+"行前置任务与第"+(j+1)+"行前置任务重复！");
					SysUtil.abort();
				}
			}
		}
		isCancel = false;
		this.disposeUIWindow();
	}

	protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		isCancel = true;
		this.disposeUIWindow();
	}

	public void onLoad() throws Exception {
		kdtPrefixEntry.checkParsed();
		super.onLoad();
		initTabCol();
		fillTable();
		IColumn colNumber = kdtPrefixEntry.getColumn(COL_NUMBER);
		IColumn colLinkDay = kdtPrefixEntry.getColumn(COL_LINKDAY);
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(80);
		colNumber.setEditor(new KDTDefaultCellEditor(txtField));
		KDFormattedTextField linkTxt = new KDFormattedTextField();
		linkTxt.setMaximumValue(new BigDecimal(10000));
		linkTxt.setMinimumValue(new BigDecimal(-10000));
		linkTxt.setPrecision(0);
		colLinkDay.setEditor(new KDTDefaultCellEditor(linkTxt));
		kdtPrefixEntry.getColumn(COL_NAME).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		kdtPrefixEntry.getColumn(COL_LINKTYPE).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		menuBar.setVisible(false);
	}

	private void fillTable() {
		WBSTemplatePrefixEntryCollection entrys = (WBSTemplatePrefixEntryCollection) getUIContext().get("WBSTMPPreTaskEntrys");
		if(entrys == null || entrys.size()<1) return;
		kdtPrefixEntry.removeRows();
		for(int i=0;i<entrys.size();i++){
			IRow row = kdtPrefixEntry.addRow();
			row.getCell(COL_NAME).setValue(entrys.get(i).getPrefixNode());
			row.getCell(COL_NUMBER).setValue(entrys.get(i).getPrefixNode().getNumber());
			row.getCell(COL_LINKDAY).setValue(new Integer(entrys.get(i).getLinkDay()));
			row.getCell(COL_LINKTYPE).setValue(entrys.get(i).getLinkType());
		}
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat(){
			public String format(Object o) {
				return o.toString().replace('!', '.');
			}
		});
		IColumn colNum = kdtPrefixEntry.getColumn(COL_NUMBER);
		colNum.setRenderer(render);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		return sic;
	}
	private void initTabCol(){
		KDWorkButton btnAddLine = new KDWorkButton("新增行",SCHClientHelper.ICON_ADDLINE);
		KDWorkButton btnInsertLine = new KDWorkButton("插入行",SCHClientHelper.ICON_INSERTLINE);
		KDWorkButton btnDeleteLine = new KDWorkButton("删除行",SCHClientHelper.ICON_REMOVELINE);
		conPrefixEntry.addButton(btnAddLine);
		conPrefixEntry.addButton(btnInsertLine);
		conPrefixEntry.addButton(btnDeleteLine);
		btnAddLine.addActionListener(actionAddLine);
		btnInsertLine.addActionListener(actionInsertLine);
		btnDeleteLine.addActionListener(actionDeleteLine);
		
		IColumn colLinkType = kdtPrefixEntry.getColumn(COL_LINKTYPE);
		IColumn colLinkDay = kdtPrefixEntry.getColumn(COL_LINKDAY);
		IColumn colName = kdtPrefixEntry.getColumn(COL_NAME);
		IColumn colNumber = kdtPrefixEntry.getColumn(COL_NUMBER);
		ObjectValueRender numRender = new ObjectValueRender();
		numRender.setFormat(new IDataFormat(){
			public String format(Object o) {
				if(o != null){
					return o.toString().replace('!', '.');
				}
				return null;
			}
		});
		colNumber.setRenderer(numRender);
		KDComboBox linkTypesPrmp = new KDComboBox();
		linkTypesPrmp.addItems(TaskLinkTypeEnum.getEnumList().toArray());
		KDTDefaultCellEditor linkTypeEditor = new KDTDefaultCellEditor(linkTypesPrmp);
		colLinkType.setEditor(linkTypeEditor);
		KDNumberTextField linkDayFormat = new KDNumberTextField();
		KDTDefaultCellEditor linkDayEditor = new KDTDefaultCellEditor(linkDayFormat);
		colLinkDay.setEditor(linkDayEditor);
		
		//添加前置任务f7控件
		WBSTemplatePromptBox selector = new WBSTemplatePromptBox(this,(KDTable)getUIContext().get("table"),false);
		KDBizPromptBox preTaskPrmtBox=new  KDBizPromptBox(){
			protected String valueToString(Object o) {
				String str = "";
				if (o != null && o instanceof WBSTemplateEntryInfo) {
					str = ((WBSTemplateEntryInfo) o).getName();
				}
				return str;
			}
		};
		preTaskPrmtBox.setEditFormat("$name$");
		if(getUIContext().get("selectedNum") != null)
			selector.setSelectedNum(getUIContext().get("selectedNum").toString());
		preTaskPrmtBox.setSelector(selector);
		KDTDefaultCellEditor preTaskEditor = new KDTDefaultCellEditor(preTaskPrmtBox);
		preTaskPrmtBox.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue() instanceof WBSTemplateEntryInfo) {
					int actRowIdx = kdtPrefixEntry.getSelectManager().getActiveRowIndex();
					kdtPrefixEntry.getCell(actRowIdx, COL_NUMBER).setValue(
							((WBSTemplateEntryInfo)eventObj.getNewValue()).getNumber());
//					kdtPrefixEntry.getCell(actRowIdx,COL_LINKTYPE).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
				}
			}
		});
		preTaskPrmtBox.setEditFormatter(new IFormatter(){
			public void applyPattern(String pattern) {
			}
			public String valueToString(Object o) {
				if(o != null && o instanceof WBSTemplateEntryInfo){
					return ((WBSTemplateEntryInfo) o).getName();
				}
				return null;
			}
		});
		preTaskPrmtBox.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				KDBizPromptBox bizBox = (KDBizPromptBox) e.getSource();
				WBSTemplatePromptBox myBox = (WBSTemplatePromptBox)bizBox.getSelector();
				
			}
		});
		preTaskPrmtBox.setEnabledMultiSelection(false);
		preTaskPrmtBox.setEditable(false);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new IDataFormat(){
			public String format(Object o) {
					if(o!=null){
						String str = new String();
						if(o instanceof WBSTemplateEntryInfo){
							str = ((WBSTemplateEntryInfo) o).getName();
						}else if(o instanceof String){
							str = o.toString();
						}
						return str;
					}else return null;
				}
			});
		colName.setEditor(preTaskEditor);
		colName.setRenderer(render);
		colName.getStyleAttributes().setLocked(false);
	}
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if (kdtPrefixEntry == null)	return;
		WBSTemplatePrefixEntryInfo detailData = new WBSTemplatePrefixEntryInfo();
		detailData.setId(BOSUuid.create(detailData.getBOSType()));
		IRow row = kdtPrefixEntry.addRow();
//		row.getCell(COL_NAME).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	}
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		if(kdtPrefixEntry == null) return;
		IRow row = null;
		if (kdtPrefixEntry.getSelectManager().size() > 0) {
			int top = kdtPrefixEntry.getSelectManager().get().getTop();
			row = kdtPrefixEntry.addRow(top);
		} else {
			row = kdtPrefixEntry.addRow();
		}
	}
	public void actionDeleteLine_actionPerformed(ActionEvent e)
			throws Exception {
		if(kdtPrefixEntry == null) return;
		int activeRowIndex = kdtPrefixEntry.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			FDCMsgBox.showWarning("请先选中行！");
			SysUtil.abort();
		}
		kdtPrefixEntry.removeRow(activeRowIndex);
	}
	
	public Object[] getData(){
		for(int i=0;i<kdtPrefixEntry.getRowCount();i++){
			if(kdtPrefixEntry.getCell(i,COL_NAME).getValue()!=null){
				WBSTemplatePrefixEntryInfo preTaskEntInfo = new WBSTemplatePrefixEntryInfo();
				preTaskEntInfo.setId(BOSUuid.create(preTaskEntInfo.getBOSType()));
				if(kdtPrefixEntry.getCell(i, COL_LINKDAY).getValue()!= null){
					preTaskEntInfo.setLinkDay(Integer.parseInt(
						kdtPrefixEntry.getCell(i, COL_LINKDAY).getValue().toString()));
				}
				if(kdtPrefixEntry.getCell(i,COL_LINKTYPE).getValue()!=null){
					preTaskEntInfo.setLinkType((TaskLinkTypeEnum) kdtPrefixEntry.getCell(i, COL_LINKTYPE).getValue());
				}
				WBSTemplateEntryInfo preTaskInfo = new WBSTemplateEntryInfo();
				if(kdtPrefixEntry.getCell(i,COL_NAME).getValue() != null){
					preTaskInfo = (WBSTemplateEntryInfo)kdtPrefixEntry.getCell(i,COL_NAME).getValue();
				}
				preTaskEntInfo.setPrefixNode(preTaskInfo);
				entCollection.add(preTaskEntInfo);
			}
		}
		return entCollection.toArray();
	}
	public boolean destroyWindow() {
		isCancel=true;
		return super.destroyWindow();
	}
}