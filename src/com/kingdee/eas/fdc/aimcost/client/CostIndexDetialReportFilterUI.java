/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryTypeEnum;
import com.kingdee.eas.fdc.aimcost.FieldTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.F7ProjectTreeSelectorPromptBox;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CostIndexDetialReportFilterUI extends AbstractCostIndexDetialReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexDetialReportFilterUI.class);
    private KDTable table=null;
    private Map value=null;
    protected CostIndexDetialReportUI listUI;
    protected ItemAction actionListOnLoad;
    public CostIndexDetialReportFilterUI(CostIndexDetialReportUI listUI, ItemAction actionListOnLoad) throws Exception
    {
        super();
        this.listUI = listUI;
        this.actionListOnLoad = actionListOnLoad;		
    }
    private F7ProjectTreeSelectorPromptBox getProjectSelecotorBox()throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		
		KDTree tree = new KDTree();
		builder.build(this.listUI, tree, this.actionListOnLoad);
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),"isIsEnabled", Boolean.FALSE);
		F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox=new F7ProjectTreeSelectorPromptBox(this, model, "getId,toString",FDCHelper.getF7Ids(prmtProject));
		return projectTreeSelectorPromptBox;
	}
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
        F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox = getProjectSelecotorBox();
		this.prmtProject.setSelector(projectTreeSelectorPromptBox);
//		this.prmtProject.getQueryAgent().resetRuntimeEntityView();
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
//		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentCostUnit();
//		if(currentCompany.isIsBizUnit()){
//			Set companySet=new HashSet();
//			companySet.add(currentCompany.getId());
//			//找出它对应的成本中心
//			EntityViewInfo prjView=new EntityViewInfo();
//			FilterInfo myFilter=new FilterInfo();
//			prjView.setFilter(myFilter);
//			myFilter.getFilterItems().add(new FilterItemInfo("costCenterOU",companySet,CompareType.INCLUDE));
//			prjView.getSelector().add("curProject.id");
//			prjView.getSelector().add("curProject.longNumber");
//			final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(prjView);
//			FilterInfo temp=new  FilterInfo();
//			for(int i=0;i<projectWithCostCenterOUCollection.size();i++){
//				String longNumber = projectWithCostCenterOUCollection.get(i).getCurProject().getLongNumber();
//				FilterInfo tempFilter=new FilterInfo();
//				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber,CompareType.EQUALS));
//				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber+".%",CompareType.LIKE));
//				tempFilter.setMaskString("#0 or #1");
//				temp.mergeFilter(tempFilter, "or");
//			}
//			filter.mergeFilter(temp, "and");
//		}
//		view.setFilter(filter);
//		this.prmtProject.setEntityViewInfo(view);
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.prmtConfig.getValue()!=null){
    		 pp.setObject("config", this.prmtConfig.getValue());
         }else{
        	 pp.setObject("config", null);
         }
         if(this.prmtProject.getValue() != null){
 	    	Object[] curProject = (Object[])this.prmtProject.getValue();
 	    	List curProjectList=new ArrayList();
 	    	String curProjectString="";
 	    	for(int i=0;i<curProject.length;i++){
 	    		if(curProject[i]==null||!(curProject[i] instanceof CurProjectInfo)) continue;
 	    		curProjectList.add(curProject[i]);
 	    	}
 	    	for(int i=0;i<curProjectList.toArray().length;i++){
 	    		if(i==0)
 	    			curProjectString+="'"+((CurProjectInfo)curProjectList.toArray()[i]).getId().toString()+"'";
 	    		else
 	    			curProjectString+=",'"+((CurProjectInfo)curProjectList.toArray()[i]).getId().toString()+"'";
 	    	}
 	    	if(!curProjectString.equals("")){
 	    		pp.setObject("curProject", curProjectString);
 	    	}else{
 	    		pp.setObject("curProject", null);
 	    	}
 	    }
         String where=" ";
         for(int i=0;i<table.getRowCount();i++){
        	 IRow row=table.getRow(i);
        	 String fieldName=row.getCell("fieldName").getValue().toString();
        	 String seq=fieldName.substring(fieldName.indexOf("条件")+2,fieldName.indexOf(":"));
        	 CostIndexConfigEntryInfo configEntry=(CostIndexConfigEntryInfo) value.get(seq.trim());
        	 String key="to_char(FField"+seq+")";
        	 String compareType=row.getCell("compareType").getValue().toString();
        	 
        	 
        	 if(FieldTypeEnum.TEXT.equals(configEntry.getFieldType())){
        		 String compareValue=row.getCell("compareValue").getValue().toString();
        		 if(compareType.equals("等于")){
        			 where=where+key+"='"+compareValue.trim()+"' and ";
        		 }else if(compareType.equals("不等于")){
        			 where=where+key+"!='"+compareValue.trim()+"' and ";
        		 }else if(compareType.equals("类似")){
        			 where=where+key+"like'%"+compareValue.trim()+"%' and ";
        		 }
 			}else if(FieldTypeEnum.DIGITAL.equals(configEntry.getFieldType())){
 				String compareValue=row.getCell("compareValue").getValue().toString();
 				if(compareType.equals("等于")){
 					where=where+key+"="+compareValue+" and ";
 				}else if(compareType.equals("不等于")){
 					where=where+key+"!="+compareValue+" and ";
 				}else if(compareType.equals("大于")){
 					where=where+key+">"+compareValue+" and ";
 				}else if(compareType.equals("大于等于")){
 					where=where+key+">="+compareValue+" and ";
 				}else if(compareType.equals("小于")){
 					where=where+key+"<"+compareValue+" and ";
 				}else if(compareType.equals("小于等于")){
 					where=where+key+"<="+compareValue+" and ";
 				}
 			}else if(FieldTypeEnum.TIME.equals(configEntry.getFieldType())){
 				Date compareValue=(Date) row.getCell("compareValue").getValue();
 				if(compareType.equals("等于")){
 					where=where+key+">={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(compareValue))+ "'} and ";
 					where=where+key+"<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(compareValue))+ "'} and ";
 				}else if(compareType.equals("不等于")){
 					where=where+key+"<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(compareValue))+ "'} and ";
 					where=where+key+">{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(compareValue))+ "'} and ";
 				}else if(compareType.equals("大于")){
 					where=where+key+">{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(compareValue))+ "'} and ";
 				}else if(compareType.equals("大于等于")){
 					where=where+key+">={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(compareValue))+ "'} and ";
 				}else if(compareType.equals("小于")){
 					where=where+key+"<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(compareValue))+ "'} and ";
 				}else if(compareType.equals("小于等于")){
 					where=where+key+"<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(compareValue))+ "'} and ";
 				}
 			}else if(FieldTypeEnum.PRODUCTTYPE.equals(configEntry.getFieldType())){
 				Object[] compareValue= (Object[]) row.getCell("compareValue").getValue();
 				StringBuffer value = null;
 			    if(compareValue!=null){
 			    	value=new StringBuffer();
 		        	for(int k=0;k<compareValue.length;k++){
 		            	if(k==0){
 		            		value.append("'"+((ProductTypeInfo)compareValue[k]).getId().toString()+"'");
 		            	}else{
 		            		value.append(",'"+((ProductTypeInfo)compareValue[k]).getId().toString()+"'");
 		            	}
 		            }
 			    }
 			    where=where+key+"in(" + value+ ") and ";
 			}else if(FieldTypeEnum.UNIT.equals(configEntry.getFieldType())){
 				Object[] compareValue= (Object[]) row.getCell("compareValue").getValue();
 				StringBuffer value = null;
 			    if(compareValue!=null){
 			    	value=new StringBuffer();
 		        	for(int k=0;k<compareValue.length;k++){
 		            	if(k==0){
 		            		value.append("'"+((MeasureUnitInfo)compareValue[k]).getId().toString()+"'");
 		            	}else{
 		            		value.append(",'"+((MeasureUnitInfo)compareValue[k]).getId().toString()+"'");
 		            	}
 		            }
 			    }
 			    where=where+key+"in(" + value+ ") and";
 			}
         }
         if(where.equals(" ")){
        	 pp.setObject("where",null);
         }else{
        	 pp.setObject("where",where.substring(0, where.length()-4));
         }
         if(this.pkFromDate.getValue()!=null){
    		 pp.setObject("fromDate", this.pkFromDate.getValue());
         }else{
        	 pp.setObject("fromDate", null);
         }
         if(this.pkToDate.getValue()!=null){
    		 pp.setObject("toDate", this.pkToDate.getValue());
         }else{
        	 pp.setObject("toDate", null);
         }
         return pp;
	}
	public void onInit(RptParams arg0) throws Exception {
		this.prmtInviteType.setValue(null);
		this.cbEntryType.setSelectedItem(null);
		this.prmtConfig.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.prmtProject.setValue(null);
	}
	public void setCustomCondition(RptParams arg0) {
	}
	protected void cbEntryType_itemStateChanged(ItemEvent e) throws Exception {
		this.prmtConfig.setValue(null);
		setFilter();
	}
	protected void prmtInviteType_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        this.prmtConfig.setValue(null);
		setFilter();
	}
	protected void prmtConfig_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        table=null;
        value=null;
		this.entryPanel.removeAll();
		
		if(this.prmtConfig.getValue()!=null){
			SelectorItemCollection sel = super.getSelectors();
			sel.add("id");
			sel.add("name");
			sel.add("number");
			sel.add("entry.*");
			
			CostIndexConfigInfo config=CostIndexConfigFactory.getRemoteInstance().getCostIndexConfigInfo(new ObjectUuidPK(((CostIndexConfigInfo)this.prmtConfig.getValue()).getId()));
			
			CostIndexConfigEntryCollection col=config.getEntry();
			CRMHelper.sortCollection(col, "seq", true);
			table=new KDTable();
			value=new HashMap();
			table.setUserObject(config);
			table.checkParsed();
			IRow headRow=table.addHeadRow();
			IColumn column=table.addColumn();
			column.setKey("fieldName");
			column.setWidth(200);
			headRow.getCell("fieldName").setValue("过滤条件");
			table.getColumn("fieldName").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			
			column=table.addColumn();
			column.setKey("fieldType");
			column.setWidth(60);
			column.getStyleAttributes().setLocked(true);
			headRow.getCell("fieldType").setValue("字段类型");
			table.getColumn("fieldType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			
			column=table.addColumn();
			column.setKey("compareType");
			headRow.getCell("compareType").setValue("比较符");
			table.getColumn("compareType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			
			column=table.addColumn();
			column.setKey("compareValue");
			headRow.getCell("compareValue").setValue("比较值");
			table.getColumn("compareValue").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			
			column=table.addColumn();
			column.setKey("logic");
			column.setWidth(60);
			column.getStyleAttributes().setLocked(true);
			headRow.getCell("logic").setValue("逻辑符");
			
			KDComboBox combo = new KDComboBox();
			for(int i=0;i<col.size();i++){
				if(!col.get(i).isIsHide()){
					combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "条件"+i+":"+col.get(i).getFieldName()}));
					value.put(String.valueOf(i), col.get(i));
				}
			}
	        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
			table.getColumn("fieldName").setEditor(comboEditor);
			
			combo = new KDComboBox();
	        for(int i = 0; i < FieldTypeEnum.getEnumList().size(); i++){
	        	combo.addItem(FieldTypeEnum.getEnumList().get(i));
	        }
	        comboEditor = new KDTDefaultCellEditor(combo);
			table.getColumn("fieldType").setEditor(comboEditor);
			
			KDContainer contEntry = new KDContainer();
			contEntry.setName(table.getName());
			contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
			contEntry.getContentPane().add(table, BorderLayout.CENTER);
			
			KDWorkButton btnAddRowinfo = new KDWorkButton();
			KDWorkButton btnInsertRowinfo = new KDWorkButton();
			KDWorkButton btnDeleteRowinfo = new KDWorkButton();

			this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
			btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
			btnAddRowinfo.setText("新增行");
			btnAddRowinfo.setSize(new Dimension(140, 19));

			this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
			btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
			btnInsertRowinfo.setText("插入行");
			btnInsertRowinfo.setSize(new Dimension(140, 19));

			this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
			btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
			btnDeleteRowinfo.setText("删除行");
			btnDeleteRowinfo.setSize(new Dimension(140, 19));
			
			this.entryPanel.add(contEntry,config.getName());
			
			table.addKDTEditListener(new KDTEditAdapter() {
				public void editStopped(KDTEditEvent e) {
					table_editStopped(e);
				}
			});
		}
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table = (KDTable) e.getSource();
		if(table.getUserObject()!=null){
			if(!table.getColumnKey(e.getColIndex()).equals("fieldName")||e.getValue()==null){
				return;
			}
			
			IRow row=table.getRow(e.getRowIndex());
			String fieldName=row.getCell("fieldName").getValue().toString();
			String seq=fieldName.substring(fieldName.indexOf("条件")+2,fieldName.indexOf(":"));
			CostIndexConfigEntryInfo configEntry=(CostIndexConfigEntryInfo) value.get(seq.trim());
			
			row.getCell("compareValue").setValue(null);
			
			row.setUserObject(configEntry);
			row.getCell("fieldType").setValue(configEntry.getFieldType());
			
			if(FieldTypeEnum.TEXT.equals(configEntry.getFieldType())){
				KDComboBox combo = new KDComboBox();
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "等于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "不等于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "类似"}));
		        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
				row.getCell("compareType").setEditor(comboEditor);
				
				KDTextField text=new KDTextField();
				KDTDefaultCellEditor textEditor = new KDTDefaultCellEditor(text);
				row.getCell("compareValue").setEditor(textEditor);
			}else if(FieldTypeEnum.DIGITAL.equals(configEntry.getFieldType())||FieldTypeEnum.TIME.equals(configEntry.getFieldType())){
				KDComboBox combo = new KDComboBox();
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "等于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "不等于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "大于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "大于等于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "小于"}));
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "小于等于"}));
		        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
				row.getCell("compareType").setEditor(comboEditor);
				
				if(FieldTypeEnum.DIGITAL.equals(configEntry.getFieldType())){
					KDFormattedTextField amount = new KDFormattedTextField();
					amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
					amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
					amount.setPrecision(4);
					KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
					row.getCell("compareValue").setEditor(amountEditor);
				}else{
					KDDatePicker pk = new KDDatePicker();
					KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
					row.getCell("compareValue").setEditor(dateEditor);
					row.getCell("compareValue").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
				}
			}else if(FieldTypeEnum.PRODUCTTYPE.equals(configEntry.getFieldType())){
				KDComboBox combo = new KDComboBox();
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "等于"}));
		        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
				row.getCell("compareType").setEditor(comboEditor);
				
				KDBizPromptBox f7Box = new KDBizPromptBox(); 
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
				f7Box.setDisplayFormat("$name$");
				f7Box.setEditFormat("$number$");
				f7Box.setCommitFormat("$number$");
				f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
				f7Box.setEnabledMultiSelection(true);
				f7Editor = new KDTDefaultCellEditor(f7Box);
				row.getCell("compareValue").setEditor(f7Editor);
				
				ObjectValueRender ovr = new ObjectValueRender();
				ovr.setFormat(new BizDataFormat("$name$"));
				row.getCell("compareValue").setRenderer(ovr);
			}else if(FieldTypeEnum.UNIT.equals(configEntry.getFieldType())){
				KDComboBox combo = new KDComboBox();
				combo.addItem(new KDComboBoxMultiColumnItem(new String[] { "等于"}));
		        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
				row.getCell("compareType").setEditor(comboEditor);
				
				KDBizPromptBox f7Box = new KDBizPromptBox(); 
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
				f7Box.setDisplayFormat("$name$");
				f7Box.setEditFormat("$number$");
				f7Box.setCommitFormat("$number$");
				f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
				f7Box.setEnabledMultiSelection(true);
				f7Editor = new KDTDefaultCellEditor(f7Box);
				row.getCell("compareValue").setEditor(f7Editor);
				
				ObjectValueRender ovr = new ObjectValueRender();
				ovr.setFormat(new BizDataFormat("$name$"));
				row.getCell("compareValue").setRenderer(ovr);
			}
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row=table.addRow();
		row.getCell("logic").setValue("且");
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)) {
				IRow row=table.addRow();
				row.getCell("logic").setValue("且");
			} else {
				IRow row=table.addRow(top);
				row.getCell("logic").setValue("且");
			}
		} else {
			IRow row=table.addRow();
			row.getCell("logic").setValue("且");
		}
	}
	protected final boolean isTableColumnSelected(KDTable table){
		if(table.getSelectManager().size() > 0){
			KDTSelectBlock block = table.getSelectManager().get();
			if(block.getMode() == 4 || block.getMode() == 8)
				return true;
        }
		return false;
    }
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		removeLine(table);
	}
    protected boolean confirmRemove(){
    	return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
    }
    protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
	protected void setFilter(){
		if(this.prmtInviteType.getValue()!=null&&this.cbEntryType.getSelectedItem()!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id",((InviteTypeInfo)this.prmtInviteType.getValue()).getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("entryType",((CostIndexEntryTypeEnum)this.cbEntryType.getSelectedItem()).getValue()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			view.setFilter(filter);
			this.prmtConfig.setEntityViewInfo(view);
			this.prmtConfig.setEnabled(true);
		}else{
			this.prmtConfig.setValue(null);
			this.prmtConfig.setEnabled(false);
		}
	}
	public boolean verify() {
		if(this.prmtProject.getValue()==null){
			 FDCMsgBox.showWarning("工程项目不能为空！");
			 return false;
		}
		if(this.prmtInviteType.getValue()==null){
			 FDCMsgBox.showWarning("采购类别不能为空！");
			 return false;
		}
		if(this.cbEntryType.getSelectedItem()==null){
			 FDCMsgBox.showWarning("分录类别不能为空！");
			 return false;
		}
		if(this.prmtConfig.getValue()==null){
			 FDCMsgBox.showWarning("造价指标库配置不能为空！");
			 return false;
		}
		for(int i=0;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			if(row.getCell("fieldName").getValue()==null){
				FDCMsgBox.showWarning(this,"字段名称不能为空！");
				table.getEditManager().editCellAt(i, table.getColumnIndex("fieldName"));
				return false;
			}
			if(row.getCell("fieldType").getValue()==null){
				FDCMsgBox.showWarning(this,"字段类型不能为空！");
				table.getEditManager().editCellAt(i, table.getColumnIndex("fieldType"));
				return false;
			}
			if(row.getCell("compareType").getValue()==null){
				FDCMsgBox.showWarning(this,"比较符不能为空！");
				table.getEditManager().editCellAt(i, table.getColumnIndex("compareType"));
				return false;
			}
			if(row.getCell("compareValue").getValue()!=null&&row.getCell("compareValue").getValue() instanceof String){
				if("".equals("compareValue")){
					FDCMsgBox.showWarning(this,"比较值不能为空！");
					table.getEditManager().editCellAt(i, table.getColumnIndex("compareValue"));
					return false;
				}
			}else if(row.getCell("compareValue").getValue()==null){
				FDCMsgBox.showWarning(this,"比较值不能为空！");
				table.getEditManager().editCellAt(i, table.getColumnIndex("compareValue"));
				return false;
			}
		}
		return true;
	}
	
	
}