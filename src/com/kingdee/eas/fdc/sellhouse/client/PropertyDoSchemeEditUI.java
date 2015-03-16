package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class PropertyDoSchemeEditUI extends AbstractPropertyDoSchemeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PropertyDoSchemeEditUI.class);
    
    private KDComboBox refDateCombo;

    public PropertyDoSchemeEditUI() throws Exception
    {
        super();
    }

    public void loadFields()
    {
        super.loadFields();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    private void checkDumpName() throws Exception {
		SellProjectInfo info = (SellProjectInfo) this.editData.getProject();
		if (info == null) {
			return;
		}
		
		String number = this.txtNumber.getText();
	
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
			if (PropertyDoSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(
						new FilterItemInfo("number", number));
		
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
			
				if (PropertyDoSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下编码不能重复！");
					SysUtil.abort();
				}
			}
	
		}
		String name = this.txtName.getSelectedItem().toString();
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (info != null && info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", info.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null,
								CompareType.EQUALS));
			}
			if (PropertyDoSchemeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", this.editData.getId().toString(),
								CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (info != null && info.getId() != null) {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", info.getId()
									.toString()));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("project.id", null,
									CompareType.EQUALS));
				}
				
				if (PropertyDoSchemeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if(OprtState.ADDNEW.equals(getOprtState())){
			setUITitle("产权办理方案-新增");
		}else if(OprtState.EDIT.equals(getOprtState())){
			setUITitle("产权办理方案-修改");
		}else if(OprtState.VIEW.equals(getOprtState())){
			setUITitle("产权办理方案-查看");
		}
    	
    	this.btnApproachAddRow.setEnabled(true);
		this.btnApproachInsertRow.setEnabled(true);
		this.btnApproachDeleteRow.setEnabled(true);
		this.btnDataAddRow.setEnabled(true);
		this.btnDataInsertRow.setEnabled(true);
		this.btnDataDeleteRow.setEnabled(true);
		
		//非售楼组织不能操作
		if(!FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			this.btnApproachAddRow.setEnabled(false);
			this.btnApproachInsertRow.setEnabled(false);
			this.btnApproachDeleteRow.setEnabled(false);
			this.btnDataAddRow.setEnabled(false);
			this.btnDataInsertRow.setEnabled(false);
			this.btnDataDeleteRow.setEnabled(false);
		}
		
		initTable();
    	
    	this.kdtApprEntries.addKDTEditListener(new KDTEditListener(){
			public void editCanceled(KDTEditEvent e) {
				
			}

			public void editStarting(KDTEditEvent e) {
				//组装参照时间下拉框选项
				if(e.getColIndex() == kdtApprEntries.getColumnIndex("refDate")){
					setRefDateEditor(kdtApprEntries.getRow(e.getRowIndex()));
					kdtApprEntries.getRow(e.getRowIndex()).getCell("refDate").setValue(e.getValue());
				}
				//判断流程名称是否可修改
				else if(e.getColIndex()==kdtApprEntries.getColumnIndex("name")){
					KDTextField nameField = new KDTextField();
					if(!isNameUsed(e.getRowIndex())){
						MsgBox.showInfo("流程名称已被引用，不能修改");
						nameField.setEditable(false);
						KDTDefaultCellEditor nameEditor = new KDTDefaultCellEditor(nameField);
						kdtApprEntries.getRow(e.getRowIndex()).getCell("name").setEditor(nameEditor);
						return;
					}
					else{
						nameField.setEditable(true);
						KDTDefaultCellEditor nameEditor = new KDTDefaultCellEditor(nameField);
						kdtApprEntries.getRow(e.getRowIndex()).getCell("name").setEditor(nameEditor);
					}
				}
				//编辑前检查参照时间的值，确定【间隔月】、【间隔日】和【指定日期】是否可编辑
				else if(e.getColIndex()==kdtApprEntries.getColumnIndex("intervalMon")
						|| e.getColIndex()==kdtApprEntries.getColumnIndex("intervalDay")
						|| e.getColIndex() == kdtApprEntries.getColumnIndex("appointedDate")){
					Object refDateObj = kdtApprEntries.getRow(e.getRowIndex()).getCell("refDate").getValue();
					ICell intervalMonCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalMon");
					ICell intervalDayCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalDay");
					ICell appointedDateCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("appointedDate");
					
					PropertyDoSchemeEntryInfo entryInfo = editData.getEntry().get(e.getRowIndex());
					if(refDateObj!=null && refDateObj.equals("指定日期")){
						//清空单元格
						intervalMonCell.setValue(null);
						intervalDayCell.setValue(null);
						
						//清空字段值
						if(entryInfo != null){
							entryInfo.setIntervalMonth(0);
							entryInfo.setIntervalDays(0);
						}
						
						//禁用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(true);
						intervalDayCell.getStyleAttributes().setLocked(true);
						
						//使能指定日期
						appointedDateCell.getStyleAttributes().setLocked(false);
					}
					else{
						//启用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(false);
						intervalDayCell.getStyleAttributes().setLocked(false);
						
						//禁用指定日期
						appointedDateCell.getStyleAttributes().setLocked(true);
						appointedDateCell.setValue(null);
						if(entryInfo != null){
							entryInfo.setScheduledDate(null);
						}
					}
				}
			}
			
			public void editStarted(KDTEditEvent e) {
				
			}

			public void editStopped(KDTEditEvent e) {
				//根据参照时间的值设置间隔月和间隔日是否可编辑
				if(e.getColIndex() == kdtApprEntries.getColumnIndex("refDate")){
					ICell intervalMonCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalMon");
					ICell intervalDayCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("intervalDay");
					ICell appointedDateCell = kdtApprEntries.getRow(e.getRowIndex()).getCell("appointedDate");
					
					PropertyDoSchemeEntryInfo entryInfo = editData.getEntry().get(e.getRowIndex());
					if(e.getValue()!=null && e.getValue().equals("指定日期")){
						//清空单元格
						intervalMonCell.setValue(null);
						intervalDayCell.setValue(null);
						
						//清空字段值
						if(entryInfo != null){
							entryInfo.setIntervalMonth(0);
							entryInfo.setIntervalDays(0);
						}
						
						//禁用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(true);
						intervalDayCell.getStyleAttributes().setLocked(true);
						
						//使能指定日期
						appointedDateCell.getStyleAttributes().setLocked(false);
					}
					else{
						//启用间隔月和天
						intervalMonCell.getStyleAttributes().setLocked(false);
						intervalDayCell.getStyleAttributes().setLocked(false);
						
						//禁用指定日期
						appointedDateCell.getStyleAttributes().setLocked(true);
						appointedDateCell.setValue(null);
						if(entryInfo != null){
							entryInfo.setScheduledDate(null);
						}
					}
				}
			}

			public void editStopping(KDTEditEvent e) {
				
			}

			public void editValueChanged(KDTEditEvent e) {
				//若当前行的是否结束字段为勾选状态，则取消其他分录的勾选状态
				if(e.getColIndex() == kdtApprEntries.getColumnIndex("isFinish")){
					if(e.getValue()!=null && e.getValue().equals(new Boolean(true))){
						cancelOtherIsFinishField(e.getRowIndex());
					}
				}
			}
		});
		
		//设置分录是否结束字段的选择状态
		this.autoSelectIsFinishField();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    }
    
    private void initTable() {
    	refDateCombo = new KDComboBox();
    	kdtApprEntries.getColumn("refDate").setEditor(new KDTDefaultCellEditor(refDateCombo));
    	
    	for(int i=0; i<kdtApprEntries.getRowCount(); i++){
			setRefDateEditor(kdtApprEntries.getRow(i));
			//填充参照日期
			Object refDate = this.editData.getEntry().get(kdtApprEntries.getRow(i).getCell("id").getValue()).getReferenceTime();
			kdtApprEntries.getRow(i).getCell("refDate").setValue(refDate);
			//初始化指定日期的编辑状态
			ICell apptDateCell = kdtApprEntries.getRow(i).getCell("appointedDate");
			if(refDate!=null && refDate.toString().equals("指定日期")){
				apptDateCell.getStyleAttributes().setLocked(false);
			}
			else{
				apptDateCell.getStyleAttributes().setLocked(true);
			}
		}
	}

    protected ICoreBase getBizInterface() throws Exception
    {
        return PropertyDoSchemeFactory.getRemoteInstance();
    }

    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection selectors = super.getSelectors();
        selectors.add(new SelectorItemInfo("longNumber"));
        selectors.add(new SelectorItemInfo("parent.*"));
        selectors.add(new SelectorItemInfo("project.id"));
        selectors.add(new SelectorItemInfo("project.number"));
        selectors.add(new SelectorItemInfo("project.name"));
        selectors.add(new SelectorItemInfo("CU.*"));
        return selectors;
    }

    public void setDataObject(IObjectValue dataObject)
    {
        if(STATUS_ADDNEW.equals(getOprtState())) {
            dataObject.put("parent", getUIContext().get(UIContext.PARENTNODE));
        }
        super.setDataObject(dataObject);
    }
    /*
     * 增加了方法找到sellProject
     * xiaoao_liu
     * 
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	
    	if(this.editData.getProject()==null){
    		SellProjectInfo project = (SellProjectInfo)this.getUIContext().get("sellProject");
        	this.editData.setProject(project);
    	}
    	checkDumpName();
    	super.actionSubmit_actionPerformed(e);
    
    	if(OprtState.ADDNEW.equals(getOprtState())){
    		
    	}else{
			this.setOprtState(OprtState.VIEW);
			lockUIForViewStatus();
		}
    	SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}

	/**
     * 描述：新增数据
     */
    protected IObjectValue createNewData()
    {
        PropertyDoSchemeInfo pdsInfo = new PropertyDoSchemeInfo();
        pdsInfo.setCreator((UserInfo)(SysContext.getSysContext().getCurrentUser()));
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
			.get("sellProject");
		pdsInfo.setProject(sellProject);
		pdsInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return pdsInfo;
    }
    
    
 
    /**
     * 描述：提交验证
     */
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	
    	String companyID = null;
		if (com.kingdee.eas.common.client.SysContext.getSysContext()
				.getCurrentOrgUnit() != null) {
			companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.common.client.SysContext
					.getSysContext().getCurrentOrgUnit()).getString("id");
		}
		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
				.getRemoteInstance();
		if (!iCodingRuleManager.isExist(editData, companyID)) {
			if (this.txtNumber.getText() == null
					|| this.txtNumber.getText().trim().equals("")) {
				MsgBox.showWarning(this, "编码不能为空！");
				this.txtNumber.requestFocus();
				SysUtil.abort();
			}
		}
		if (this.txtName.getSelectedItem() == null
				|| this.txtName.getSelectedItem().toString().trim().equals("")) {
			MsgBox.showWarning(this, "名称不能为空！");
			this.txtName.requestFocus();
			SysUtil.abort();
		}
		
    	if(kdtApprEntries.getRowCount()<1 && kdtDataEntries.getRowCount()<1){
    		MsgBox.showWarning(this, "产权办理流程和产权办理资料不能同时为空！");
    		SysUtil.abort();
    	}
    	for(int i=0;i<kdtApprEntries.getRowCount();i++){
    		IRow row = kdtApprEntries.getRow(i);
    		if(row.getCell("name").getValue() == null || StringUtils.isEmpty(row.getCell("name").getValue().toString().trim())){
    			MsgBox.showWarning(this, "第" + (i+1) + "行的产权办理流程名称不能为空！");
    			SysUtil.abort();
    		}
    	}
    	int isFinishCheckedCount = 0;
    	for(int i=0;i<kdtApprEntries.getRowCount();i++){
    		IRow row = kdtApprEntries.getRow(i);
    		for(int j=i+1;j<kdtApprEntries.getRowCount();j++){
    			IRow r = kdtApprEntries.getRow(j);
    			if(row.getCell("name").getValue().toString().trim().equals(r.getCell("name").getValue().toString().trim())){
    				MsgBox.showWarning(this,"流程名称不能重复！");
    				SysUtil.abort();
    			}
    		}
    		if(kdtApprEntries.getRow(i).getCell("isFinish").getValue()!=null
				&& kdtApprEntries.getRow(i).getCell("isFinish").getValue().equals(new Boolean(true))){
				isFinishCheckedCount++;
			}
    	}
    	if(kdtApprEntries.getRowCount()>0 && isFinishCheckedCount == 0){
			MsgBox.showWarning(this, "产权办理流程-是否结束不能为空");
			SysUtil.abort();
		}
		else if(isFinishCheckedCount > 1){
			MsgBox.showWarning(this, "产权办理流程-是否结束一栏只能有一条记录勾选");
			SysUtil.abort();
		}
    	
    	for(int i=0;i<kdtDataEntries.getRowCount();i++){
    		IRow row = kdtDataEntries.getRow(i);
    		if(row.getCell("name").getValue() == null || StringUtils.isEmpty(row.getCell("name").getValue().toString().trim())){
    			MsgBox.showWarning(this, "第" + (i+1) + "行的产权办理资料名称不能为空！");
    			SysUtil.abort();
    		}
    	}
    	
    	for(int i=0;i<kdtDataEntries.getRowCount();i++){
    		IRow row = kdtDataEntries.getRow(i);
    		for(int j=i+1;j<kdtDataEntries.getRowCount();j++){
    			IRow r = kdtDataEntries.getRow(j);
    			if(row.getCell("name").getValue().toString().trim().equals(r.getCell("name").getValue().toString().trim())){
    				MsgBox.showWarning(this,"资料名称不能重复！");
    				SysUtil.abort();
    			}
    		}
    	}
    	
    	this.chkIsFieldValid();
    }
    
    /**
	 * 检查分录的字段是否满足限制条件
	 */
	private void chkIsFieldValid(){
		//流程分录
		for(int i=0; i<this.kdtApprEntries.getRowCount(); i++){
			ICell name = this.kdtApprEntries.getCell(i, "name");
			if(name.getValue()!=null && name.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的流程名称长度超过255个字符");
				SysUtil.abort();
			}
			ICell remark = this.kdtApprEntries.getCell(i, "remark");
			if(remark.getValue()!=null && remark.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的流程说明长度超过255个字符");
				SysUtil.abort();
			}
			ICell refDate = this.kdtApprEntries.getCell(i, "refDate");
			if(refDate.getValue() == null){
				MsgBox.showInfo("请输入第" + (i+1) + "行的参照日期");
				SysUtil.abort();
			}
			else if("指定日期".equals(refDate.getValue().toString())){
				ICell appointedDate = this.kdtApprEntries.getCell(i, "appointedDate");
				if(appointedDate.getValue()==null || appointedDate.getValue().toString().length()==0){
					MsgBox.showInfo("请输入第" + (i+1) + "行的指定日期");
					SysUtil.abort();
				}
			}
			else{//参照日期的值不是"指定日期"，检查间隔月和间隔天
				ICell intervalMon = this.kdtApprEntries.getCell(i, "intervalMon");
				if(intervalMon.getValue()!=null){
					int mon = Integer.parseInt(intervalMon.getValue().toString());
					if(mon<0 || mon >12){
						MsgBox.showInfo("第" + (i+1) + "行的间隔月的取值应为0~12");
						SysUtil.abort();
					}
				}
				ICell intervalDay = this.kdtApprEntries.getCell(i, "intervalDay");
				if(intervalDay.getValue()!=null){
					int day = Integer.parseInt(intervalDay.getValue().toString());
					if(day<0 || day >31){
						MsgBox.showInfo("第" + (i+1) + "行的间隔天的取值应为0~31");
						SysUtil.abort();
					}
				}				
			}
		}
		
		//资料分录
		for(int i=0; i<this.kdtDataEntries.getRowCount(); i++){
			ICell name = this.kdtDataEntries.getCell(i, "name");
			if(name.getValue() == null){
				MsgBox.showInfo("请输入第" + (i+1) + "行的资料名称");
				SysUtil.abort();
			}
			else if(name.getValue()!=null && name.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的资料名称长度超过255个字符");
				SysUtil.abort();
			}
			ICell remark = this.kdtDataEntries.getCell(i, "remark");
			if(remark.getValue()!=null && remark.getValue().toString().length()>255){
				MsgBox.showInfo("第" + (i+1) + "行的资料说明长度超过255个字符");
				SysUtil.abort();
			}
		}
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	checkIsEnable();
    	checkSchemeReference();
    	super.actionRemove_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkIsEnable();
    	super.actionEdit_actionPerformed(e);
    }
    
    protected void checkSchemeReference() throws EASBizException, BOSException
    {
    	String tmpPk = editData.getId().toString();

    	FilterInfo bookFilter = new FilterInfo();
    	bookFilter.getFilterItems().add(new FilterItemInfo("propertyDoScheme.id", tmpPk));
    	if(RoomPropertyBookFactory.getRemoteInstance().exists(bookFilter))
    	{
    		MsgBox.showWarning("所选项已经被引用，不能执行此操作！");
    		abort();
    	}

    	FilterInfo batchFilter = new FilterInfo();
    	batchFilter.getFilterItems().add(new FilterItemInfo("scheme.id", tmpPk));
    	if(RoomPropertyBatchFactory.getRemoteInstance().exists(batchFilter))
    	{
    		MsgBox.showWarning("所选项已经被引用，不能执行此操作！");
    		abort();
    	}
    }
	
    protected void checkIsEnable() throws EASBizException, BOSException
    {	
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true),CompareType.EQUALS));
		if(PropertyDoSchemeFactory.getRemoteInstance().exists(filter)){
			MsgBox.showWarning("所选项为启用状态，不能执行此操作！");
			abort();
		}
    	
    }
    
    protected void setAutoNumberByOrg(String orgType) {
    	super.setAutoNumberByOrg(orgType);
    }
    
    public void actionApprAddRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	IRow row = this.kdtApprEntries.addRow();
		//显示设置为false，让界面显示勾选框
		row.getCell("isFinish").setValue(new Boolean(false));
		//设置参照日期
		setRefDateEditor(row);
		//设置分录是否结束字段的选择状态
		this.autoSelectIsFinishField();
		super.actionApprAddRow_actionPerformed(e);
    }
    
    public void actionApprInsertRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionApprInsertRow_actionPerformed(e);
		
		int activeRowIndex = kdtApprEntries.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			actionApprAddRow_actionPerformed(e);
		}
		else{
			IRow row = kdtApprEntries.addRow(activeRowIndex);
			row.getCell("isFinish").setValue(new Boolean(false));
			this.autoSelectIsFinishField();
			
			for(int i=activeRowIndex; i<kdtApprEntries.getRowCount(); i++){
				setRefDateEditor(kdtApprEntries.getRow(i));
			}
		}
    }
    
    public void actionApprDeleteRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionApprDeleteRow_actionPerformed(e);
    	
    	int activeRowIndex = kdtApprEntries.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0 ) {
			return;
		}
		else if(!this.isNameUsed(activeRowIndex)){
			MsgBox.showInfo("流程名称已被引用，不能删除");
			SysUtil.abort();
		}
		kdtApprEntries.removeRow(activeRowIndex);
		
		for(int i=activeRowIndex; i<kdtApprEntries.getRowCount(); i++){
			setRefDateEditor(kdtApprEntries.getRow(i));
		}
		this.autoSelectIsFinishField();
    }
    
    /**
	 * 设置参照日期的下拉框选项，由选中行前面分录的流程名称组成
	 * @param row 新增行
	 */
	private void setRefDateEditor(IRow row){
		refDateCombo.removeAllItems();
		
		int rowCount = 0;
		Object[] objArray = null;
		if(row.getRowIndex() < 0){
			return;
		}
		else{
			rowCount = row.getRowIndex();
			objArray = new Object[rowCount + 2];
			objArray[0]="指定日期";
			objArray[1]="签约日期";
			for(int i=0; i<rowCount; i++){
				if(kdtApprEntries.getRow(i).getCell("name").getValue() != null){
					objArray[i+2] = kdtApprEntries.getRow(i).getCell("name").getValue();
				}
			}
		}
		
		refDateCombo.addItems(objArray);

		ICell refDateCell =  row.getCell("refDate");
		refDateCell.setEditor(new KDTDefaultCellEditor(refDateCombo));
	}
	
	/**
	 * 判断选中行的流程名称是否被引用
	 * @param rowIndex - 选中行的索引
	 * @return
	 */
	private boolean isNameUsed(int rowIndex){
		String currentName = (String)kdtApprEntries.getRow(rowIndex).getCell("name").getValue();
		for(int i=rowIndex+1; i<kdtApprEntries.getRowCount(); i++){
			Object refDateValue = kdtApprEntries.getRow(i).getCell("refDate").getValue();
			//判断当前流程名称是否被引用
			if(refDateValue!=null && currentName!=null 
					&& currentName.equals((String)refDateValue)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 如果没有分录勾选是否结束，则默认勾选最后一行
	 */
	private void autoSelectIsFinishField(){
		boolean isHasSelected = false;
		int rowCount = this.kdtApprEntries.getRowCount();
		if(rowCount <= 0){
			return;
		}
		Boolean trueObj = new Boolean(true);
		for(int i=0; i<rowCount; i++){
			if(this.kdtApprEntries.getRow(i).getCell("isFinish").getValue() != null
			    && this.kdtApprEntries.getRow(i).getCell("isFinish").getValue().equals(trueObj)){
				isHasSelected = true;
				break;
			}
		}
		if(!isHasSelected){
			this.kdtApprEntries.getRow(rowCount-1).getCell("isFinish").setValue(trueObj);
		}
	}
	
	/**
	 * 取消其他分录的是否结束字段的勾选状态
	 * @param currentRowIndex - 选中行
	 */
	private void cancelOtherIsFinishField(int currentRowIndex){
		for(int i=0; i<this.kdtApprEntries.getRowCount(); i++){
			if(i != currentRowIndex){
				this.kdtApprEntries.getRow(i).getCell("isFinish").setValue(new Boolean(false));
			}
		}
	}
    
    public void actionDataAddRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionDataAddRow_actionPerformed(e);
    	this.kdtDataEntries.addRow();
    }
    
    public void actionDataInsertRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionDataInsertRow_actionPerformed(e);
    	int activeRowIndex = kdtDataEntries.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			actionDataAddRow_actionPerformed(e);
		}
		else{
			kdtDataEntries.addRow(activeRowIndex);
		}
    }
    
    public void actionDataDeleteRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionDataDeleteRow_actionPerformed(e);
    	int activeRowIndex = kdtDataEntries.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			return;
		}
		kdtDataEntries.removeRow(activeRowIndex);
    }
}