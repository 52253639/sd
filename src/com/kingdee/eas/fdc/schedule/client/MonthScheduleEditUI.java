/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCMonthTaskEntryCollection;
import com.kingdee.eas.fdc.schedule.FDCMonthTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCMonthTempTaskEntryCollection;
import com.kingdee.eas.fdc.schedule.FDCMonthTempTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.IMonthSchedule;
import com.kingdee.eas.fdc.schedule.MonthScheduleFactory;
import com.kingdee.eas.fdc.schedule.MonthScheduleInfo;
import com.kingdee.eas.fdc.schedule.PlanTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class MonthScheduleEditUI extends AbstractMonthScheduleEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthScheduleEditUI.class);
    
    private static final String ID_COL = "ID";
    private static final String NUMBER_COL = "number";
    private static final String NAME_COL = "name";
    private static final String STARTDATE_COL = "startDate";
    private static final String ENDDATE_COL = "endDate";
    private static final String EFFECTTIMES1_COL = "effectTimes1";
    private static final String DESC_COL = "desc";
    
    private boolean codingRuleEnabled = false;
    
    
    
    /**
     * output class constructor
     */
    public MonthScheduleEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    	if(OprtState.ADDNEW.equals(getOprtState())){
    		FDCMonthTaskEntryCollection entryCol = editData.getTaskEntrys();
    		for(int i=0;i<entryCol.size();i++){
    			entryCol.get(i).setId(null);
    		}
    	}
    }

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		//kdtTempTaskEntrys
		return this.kdtTaskEntrys;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return MonthScheduleFactory.getRemoteInstance();
	}
    public void loadFields(){
//    	super.loadFields();
//    	kdtTaskEntrys.checkParsed();	
    	
//    	detachListeners();
		super.loadFields();
//		attachListeners();
		this.kdtTempTaskEntrys.checkParsed();
		//
		kdtTempTaskEntrys.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		loadEntries(this.kdtTempTaskEntrys);// 装入结算明细
		
		getDetailTable().getStyleAttributes().setLocked(true);
		
		getDetailTable().getColumn("theMonthRate").getStyleAttributes().setLocked(false);
		
		try {
			handlePrefixDepend();
		} catch (Exception e) {
			handUIException(e);
		}
    }
    public void setOprtState(String oprtType) {
    	super.setOprtState(oprtType);
    	if (OprtState.VIEW.equals(oprtState) || oprtState.equals(OprtState.EDIT)) {
			txtNumber.setEnabled(false);
		} else if (OprtState.ADDNEW.equals(oprtState)) {
			txtNumber.setEnabled(true);
		}
		if (oprtState != null && (oprtState.equals(OprtState.ADDNEW)||oprtState.equals(OprtState.EDIT))) {
			actionAddLine.setEnabled(true);
			actionRemoveLine.setEnabled(true);
			actionInputMonthTask.setEnabled(true);
			actionInputUltimoTask.setEnabled(true);
			actionDeleteTask.setEnabled(true);
			actionNewTempTask.setEnabled(true);
			actionDeleteTempTask.setEnabled(true);
			actionNewTempTask.setEnabled(true);
		} else{
			actionAddLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionInputMonthTask.setEnabled(false);
			actionInputUltimoTask.setEnabled(false);
			actionDeleteTask.setEnabled(false);
			actionNewTempTask.setEnabled(false);
			actionDeleteTempTask.setEnabled(false);
			actionNewTempTask.setEnabled(false);
		}
    }
	public void onLoad() throws Exception {
    	super.onLoad();
    	handleCodingRule();
    	txtNumber.setMaxLength(20);    	
    	txtName.setMaxLength(50);
    	initCtrl();
    	pkMonth.setDatePattern("yyyy-MM");
    	ScheduleClientHelper.setPersonF7(prmtAdminPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId()!=null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():null);
    	kdtTaskEntrys.getColumn("effectTimes").getStyleAttributes().setNumberFormat("%r-[ ]0.0n");
    	kdtTaskEntrys.getColumn("natureTimes").getStyleAttributes().setNumberFormat("%r-[ ]0.0n");
    	KDFormattedTextField formattedNum = new KDFormattedTextField();
		formattedNum.setMaximumValue(new BigDecimal("10000"));
//		formattedNum.setMinimumValue(new BigDecimal("0"));
		formattedNum.setRemoveingZeroInDispaly(true);
		formattedNum.setRemoveingZeroInEdit(true);
		formattedNum.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		formattedNum.setMinimumValue(new BigDecimal("1"));
    	kdtTempTaskEntrys.getColumn("effectTimes1").setEditor(new KDTDefaultCellEditor(formattedNum));
    }
	
	private void initCtrl() throws Exception{
		
		KDFormattedTextField theMonthRate = new KDFormattedTextField();
		theMonthRate.setDataType(1);
		theMonthRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		theMonthRate.setMinimumValue(FDCHelper.ZERO);
		theMonthRate.setPrecision(2);
		
		KDTDefaultCellEditor theMonthRate_CellEditor = new KDTDefaultCellEditor(theMonthRate);
		kdtTaskEntrys.getColumn("theMonthRate").setEditor(theMonthRate_CellEditor);
		kdtTaskEntrys.getColumn("theMonthRate").getStyleAttributes().setNumberFormat("###,##0.00");
		kdtTaskEntrys.getColumn("theMonthRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDFormattedTextField lastMonthRate = new KDFormattedTextField();
		lastMonthRate.setDataType(1);
		lastMonthRate.setPrecision(2);
		
		KDTDefaultCellEditor lastMonthRate_CellEditor = new KDTDefaultCellEditor(lastMonthRate);
		kdtTaskEntrys.getColumn("lastMonthRate").setEditor(lastMonthRate_CellEditor);
		kdtTaskEntrys.getColumn("lastMonthRate").getStyleAttributes().setNumberFormat("###,##0.00");
		kdtTaskEntrys.getColumn("lastMonthRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDDatePicker startDatePicker = new KDDatePicker();	
		startDatePicker.setDatePattern("yyyy-MM-dd");
		KDTDefaultCellEditor startDate_CellEditor = new KDTDefaultCellEditor(startDatePicker);
		kdtTempTaskEntrys.getColumn(STARTDATE_COL).setEditor(startDate_CellEditor);
		
		KDDatePicker endDatePicker = new KDDatePicker();			
		endDatePicker.setDatePattern("yyyy-MM-dd");
		KDTDefaultCellEditor endDatePicker_CellEditor = new KDTDefaultCellEditor(endDatePicker);
		kdtTempTaskEntrys.getColumn(ENDDATE_COL).setEditor(endDatePicker_CellEditor);
		
		KDTextField descField = new KDTextField();
		descField.setMaxLength(200);
		KDTDefaultCellEditor descField_CellEditor = new KDTDefaultCellEditor(descField);
		kdtTempTaskEntrys.getColumn(DESC_COL).setEditor(descField_CellEditor);
		
		if(editData.getState() != null){
			if(!editData.getState().getValue().equals(ScheduleStateEnum.SAVED_VALUE)){
				btnSave.setEnabled(false);
			}
			
			if(editData.getState().getValue().equals(ScheduleStateEnum.AUDITTED_VALUE)){
				btnSave.setEnabled(false);
				btnSubmit.setEnabled(false);
				btnEdit.setEnabled(false);
				btnRemove.setEnabled(false);
			}
		}
		btnInputUltimoTask.setText("引入上月未完成任务");
		btnInputUltimoTask.setToolTipText("引入上月未完成任务");
		this.kdtTaskEntrys.getColumn("wbsNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
//		FDCHelper.formatTableDate(this.kdtTempTaskEntrys, STARTDATE_COL);
		
		KDTextField tblTempTaskEntry_number_TextField = new KDTextField();
		tblTempTaskEntry_number_TextField.setName("tblTempTaskEntry_number_TextField");
		tblTempTaskEntry_number_TextField.setMaxLength(80);
		KDTDefaultCellEditor tblTempTaskEntry_number_editor = new KDTDefaultCellEditor(
				tblTempTaskEntry_number_TextField);
		kdtTempTaskEntrys.getColumn("number").setEditor(tblTempTaskEntry_number_editor);
		
		KDTextField tblTempTaskEntry_name_TextField = new KDTextField();
		tblTempTaskEntry_name_TextField.setName("tblTempTaskEntry_name_TextField");
		tblTempTaskEntry_name_TextField.setMaxLength(80);
		KDTDefaultCellEditor tblTempTaskEntry_name_Editor = new KDTDefaultCellEditor(
				tblTempTaskEntry_name_TextField);
		kdtTempTaskEntrys.getColumn("name").setEditor(tblTempTaskEntry_name_Editor);
		
		KDTextField tblTempTaskEntry_desc_TextField = new KDTextField();
		tblTempTaskEntry_desc_TextField.setName("tblTempTaskEntry_desc_TextField");
		tblTempTaskEntry_desc_TextField.setMaxLength(200);
		KDTDefaultCellEditor tblTempTaskEntry_desc_CellEditor = new KDTDefaultCellEditor(
				tblTempTaskEntry_desc_TextField);
		kdtTempTaskEntrys.getColumn("desc").setEditor(tblTempTaskEntry_desc_CellEditor);
		
		
	}
	
	protected IObjectValue createNewData() {
		MonthScheduleInfo newData = new MonthScheduleInfo();
		super.createNewData();
		CurProjectInfo prj=(CurProjectInfo)getUIContext().get("CurProject");
		newData.setProject(prj);
//		FullOrgUnitInfo adminDept = (FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit();
		OrgUnitInfo unit = SysContext.getSysContext().getCurrentOrgUnit();
		FullOrgUnitInfo adminDept = unit.castToFullOrgUnitInfo();
		
		newData.setAdminDept(adminDept);
		newData.setPlanType(PlanTypeEnum.dept);
		newData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		newData.setState(ScheduleStateEnum.SAVED);
		
		return newData;
	}
	
	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		FDCMonthTempTaskEntryInfo entry = new FDCMonthTempTaskEntryInfo();
		
		return entry;
	}

	protected void initWorkButton() {
    	super.initWorkButton();
    	
    	remove(btnInputMonthTask);
    	remove(btnInputUltimoTask);
    	remove(btnDeleteTask);
    	remove(btnNewTempTask);
    	remove(btnDeleteTempTask);
    	
    	kDContainer1.addButton(btnInputMonthTask);
    	kDContainer1.addButton(btnInputUltimoTask);
    	kDContainer1.addButton(btnDeleteTask);
    	
    	kDContainer2.addButton(btnNewTempTask);
    	kDContainer2.addButton(btnDeleteTempTask);
    	
    	this.btnCreateFrom.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		
		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		this.btnCopy.setVisible(false);
		
		menuTable1.setVisible(false);
		menuItemCancel.setVisible(false);
		menuItemCancelCancel.setVisible(false);
		
		menuItemCreateFrom.setVisible(false);
		menuItemCreateTo.setVisible(false);
		menuItemCopyFrom.setVisible(false);
		menuItemCopy.setVisible(false);
		prmtCreator.setDisplayFormat("$name$");
    }
	
	
	/**
     * output actionInputMonthTask_actionPerformed method
     */
    public void actionInputMonthTask_actionPerformed(ActionEvent e) throws Exception {
      int row=this.kdtTaskEntrys.getRowCount();
      
//      for(int i=0;i<row;i++)
//      {
    	  this.kdtTaskEntrys.removeRows();
//      }
    	importMonthTask();
    }
    	

    /**
     * output actionInputUltimoTask_actionPerformed method
     */
    public void actionInputUltimoTask_actionPerformed(ActionEvent e) throws Exception{
    	Date d  = null;
    	if(pkMonth.getValue() == null ) {
    		MsgBox.showWarning(this, "请输入计划月份");    		
    		SysUtil.abort();    		
    	}else{
    		d = (Date)pkMonth.getValue();
    	}
    	
//    	kdtTaskEntrys.removeRows();
    	
    	Calendar   cd   =   Calendar.getInstance();   
    	cd.set(cd.YEAR,(d.getYear()+1900 ));   
    	cd.set(cd.MONTH,(d.getMonth() ));  
    	cd.set(cd.DATE, 1);
    
    	
    	Date startDate = (Date) pkMonth.getValue();
    	cd.set(cd.MONTH,(d.getMonth())); 
    	cd.add(cd.DATE, -1);
    	Date endDate = (Date) pkMonth.getValue();
    	
//    	FullOrgUnitInfo adminDept = (FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit();
    	
    	String adminDeptId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
    	CurProjectInfo prj=null;
    	
    	if((CurProjectInfo)getUIContext().get("CurProject") == null){
    		prj = editData.getProject();
    	}else{
    		prj = (CurProjectInfo)getUIContext().get("CurProject");
    	}
    	FDCScheduleTaskCollection col = MonthScheduleFactory.getRemoteInstance().fetchTask0(startDate, endDate, adminDeptId, null,false,prj);
		int count = 0;

		if(col != null){
			fillTable(col);
		}
    }
    	

    /**
     * output actionDeleteTask_actionPerformed method
     */
    public void actionDeleteTask_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow selectedRow = FDCTableHelper.getSelectedRow(kdtTaskEntrys);
    	
    	if(kdtTaskEntrys.getRowCount() == 0){
    		MsgBox.showWarning(this, "没有需要删除的数据！");
        	abort();
    	}
    	
    	if(selectedRow == null){
    		MsgBox.showWarning(this, "请选择要删除的行！");
        	abort();
    	}
//    	Boolean a = (Boolean)selectedRow.getCell("isLeaf").getValue();
//    	if(selectedRow.getCell("isLeaf").getValue() != null && "false".equalsIgnoreCase(selectedRow.getCell("isLeaf").getValue().toString())){
//    		MsgBox.showWarning(this, "不是叶子节点，不能删除！");
//        	abort();
//    	}
    	
    	String taskNumber = selectedRow.getCell("taskNumber").getValue().toString();
    	
    	String[] taskNumbers =this.getColumnValues("taskNumber");
    	int flag = 0;
    	for(int i = 0; i <taskNumbers.length ; i ++){
    		if(taskNumbers[i].indexOf(taskNumber) >= 0){
    			flag ++;
    		}
    	}
    	
    	if(flag > 1){
    		MsgBox.showWarning(this, "不是叶子节点，不能删除！");
        	abort();
    	}
    	
    	kdtTaskEntrys.removeRow(selectedRow.getRowIndex());
    }
    	

    /**
     * output actionNewTempTask_actionPerformed method
     */
    public void actionNewTempTask_actionPerformed(ActionEvent e) throws Exception
    {
    	addLine(this.kdtTempTaskEntrys);
    }
    	

    /**
     * output actionDeleteTempTask_actionPerformed method
     */
    public void actionDeleteTempTask_actionPerformed(ActionEvent e) throws Exception
    {
    	if (kdtTempTaskEntrys != null) {
			removeLine(kdtTempTaskEntrys);
			appendFootRow(kdtTempTaskEntrys);
			// 实现删除后的焦点策略 2007-10-09 haiti_yang
//			if (kdtTempTaskEntrys.getRowCount() == 0) {
//				FocusTraversalPolicy policy = null;
//				Container container = null;
//				Component initComponent = null;
//				if (this.getFocusTraversalPolicy() != null
//						&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
//					policy = this.getFocusTraversalPolicy();
//					container = this;
//					Component[] traverComponent = ((UIFocusTraversalPolicy) policy)
//							.getComponents();
//					for (int i = 0; i < traverComponent.length; i++) {
//						if (traverComponent[i] == this.getDetailTable()) {
//							initComponent = traverComponent[i];
//							break;
//						}
//					}
//					if (initComponent == null) {
//						initComponent = policy.getLastComponent(container);
//						initComponent.requestFocusInWindow();
//					} else if (initComponent != null) {
//						Component component = policy.getComponentBefore(
//								container, initComponent);
//						while ((component instanceof IKDTextComponent) == false
//								|| component.isEnabled() == false) {
//							component = policy.getComponentBefore(container,
//									component);
//						}
//						component.requestFocusInWindow();
//					}
//				} else if (policy == null) {
//					if (this.getUIWindow() instanceof Dialog) {
//						policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
//						container = (Dialog) uiWindow;
//					} else if (this.getUIWindow() instanceof Window) {
//						policy = ((Window) uiWindow).getFocusTraversalPolicy();
//						container = (Window) uiWindow;
//					}
//
//					if (policy != null) {
//						try {
//							Component component = policy.getComponentBefore(
//									container, getDetailTable());
//							while ((component instanceof IKDTextComponent) == false
//									|| component.isEnabled() == false) {
//								component = policy.getComponentBefore(
//										container, component);
//							}
//							component.requestFocusInWindow();
//						} catch (Exception ex) {
//						}
//					}
//				}
//			}
		}
    }
    
    
	private void verifyInputNotNull(KDTextField txt, String msg) {
		if(txt.getText() == null || txt.getText().trim().length()<1){
			FDCMsgBox.showError(msg);
			SysUtil.abort();
		}
		
	}
	
	/**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAddLine_actionPerformed(e);
    	addLine(this.kdtTempTaskEntrys);
    }
	
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAddLine_actionPerformed(e);
    	insertLine(this.kdtTempTaskEntrys);
    	
    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionRemoveLine_actionPerformed(e);
    	removeLine(this.kdtTempTaskEntrys);
    }
    /**
     * @deprecated 
     * 此方法在这里根本没用 by cassiel 2010-06-21
     */
    protected int fillTable(ResultSet rs) throws Exception{
    	
		int rowNum = 0;
		
						
		try {
			while(rs.next()){
				IRow row = kdtTaskEntrys.addRow();
				
				ObjectUuidPK pk = new ObjectUuidPK(rs.getString("TASKID"));
				FDCScheduleTaskInfo  info = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(pk);
	    		FDCMonthTaskEntryInfo entry=new FDCMonthTaskEntryInfo();
	    		entry.setTask(info);
	    		row.setUserObject(entry);
	    		
				row.getCell("taskName").setValue(info);								
				 
				row.getCell("wbs").setValue(rs.getString("WBSNUMBER"));
				row.getCell("thisMonthRate").setValue(null);
				row.getCell("ultimoRate").setValue("");
				row.getCell("start").setValue(rs.getString("STARTDATE"));
				row.getCell("end").setValue(rs.getString("ENDDATE"));
				row.getCell("effectTimes").setValue(rs.getString("EFFECTTIMES"));
				row.getCell("natureTimes").setValue(rs.getString("NATURETIMES"));
				row.getCell("parentTask").setValue(rs.getString("PARENTTASK"));
				row.getCell("project").setValue(rs.getString("PROJECT"));
				row.getCell("taskID").setValue(rs.getString("TASKID"));
				
				
				rowNum ++ ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	    	
    	return rowNum;
    }
    
    protected int fillTable(FDCScheduleTaskCollection col) throws Exception{
    	int rowNum = 0;
    	for(int i =0 ; i < col.size(); i++ ){
    		FDCScheduleTaskInfo info = col.get(i);
    		String number = info.getNumber();
    		String[] numbers= getColumnValues("taskNumber");
    		int flag = 0;
    		if(numbers != null){
    			
    	    	for(int j = 0; j <numbers.length ; j ++){
    	    		if(numbers[j].equals(number)){
    	    			flag ++;
    	    			break;
    	    		}
    	    	}
    	    	
    		}else if(number == null){
    			return 0;
    		}
    		
    		if(flag >0){
    			continue;
    		}
    		IRow row = kdtTaskEntrys.addRow();
    		FDCMonthTaskEntryInfo entry=new FDCMonthTaskEntryInfo();
    		entry.setTask(info);
    		row.setUserObject(entry);
    		row.getCell(0).setValue(info);								
			 
			row.getCell(1).setValue(info.getWbs().getLongNumber());
			row.getCell(2).setValue(null);
			row.getCell(3).setValue(info.getComplete());
			row.getCell(4).setValue(info.getStart());
			row.getCell(5).setValue(info.getEnd());
			row.getCell(6).setValue(info.getEffectTimes());
			row.getCell(7).setValue(info.getNatureTimes());
			row.getCell(8).setValue(info.getParent() != null ? info.getParent().getName() :"");
			row.getCell(9).setValue(info.getWbs().getCurProject().getName());
			row.getCell(10).setValue(info.getId());
			row.getCell(11).setValue(info.isIsLeaf()+"");
			row.getCell(12).setValue(info.getNumber());
    	}
    	try {
			handlePrefixDepend();
		} catch (Exception e) {
			handUIException(e);
		}
    	return rowNum;
    }
    
    /**
     * 校验值对象的合法性
     */
    protected void verifyInput(ActionEvent e) throws Exception {
    	
    	if(!codingRuleEnabled){
    		verifyInputNotNull(txtNumber, "计划编码不能为空");
    	}
    	verifyInputNotNull(txtName, "计划名称不能为空");
    	
    	
    	if(pkMonth.getValue() == null){
    		MsgBox.showWarning(this, "计划月份不能为空");    		
    		SysUtil.abort();
    		
    	}
    	if(kdtTempTaskEntrys == null){
    		MsgBox.showWarning(this, "本月临时任务不能为空");    		
    		SysUtil.abort();
    	}else{
    		for(int i = 0; i < kdtTempTaskEntrys.getRowCount() ; i ++ ){
    			if(kdtTempTaskEntrys.getCell(i, "number").getValue() == null){
    				MsgBox.showWarning(this, "本月临时任务中第" + (i+1) + "行“编码”为空，请重新输入！");    		
    	    		SysUtil.abort();
    			}
    			
    			if(kdtTempTaskEntrys.getCell(i, "name").getValue() == null){
    				MsgBox.showWarning(this, "本月临时任务中第" + (i+1) + "行“名称”为空，请重新输入！");    		
    	    		SysUtil.abort();
    			}
    			
    			if(kdtTempTaskEntrys.getCell(i, "startDate").getValue() == null){
    				MsgBox.showWarning(this, "本月临时任务中第" + (i+1) + "行“开始时间”为空，请重新输入！");    		
    	    		SysUtil.abort();
    			}
    			
    			if(kdtTempTaskEntrys.getCell(i, "endDate").getValue() == null){
    				MsgBox.showWarning(this, "本月临时任务中第" + (i+1) + "行“结束时间”为空，请重新输入！");    		
    	    		SysUtil.abort();
    			}
    			
    			if(kdtTempTaskEntrys.getCell(i, "effectTimes1").getValue() == null){
    				MsgBox.showWarning(this, "本月临时任务中第" + (i+1) + "行“工期”为空，请重新输入！");    		
    	    		SysUtil.abort();
    			}
    		}
    	}
    	
    	
    	storeEntries(this.kdtTempTaskEntrys);
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verifyInput(e);
//    	storeFields();
		super.actionSave_actionPerformed(e);
		
		btnSave.setEnabled(false);
		setSaved(true);
//		initOldData(editData);
		oldData = editData;
	}
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	verifyInput(e);
//    	storeFields();
		super.actionSubmit_actionPerformed(e);
		storeFields();
		btnSave.setEnabled(false);
		setSaved(true);
		oldData = editData;
//		initOldData(editData);
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception{
    	if(editData.getState().equals(ScheduleStateEnum.AUDITTED)){
    		MsgBox.showWarning(this, "单据已经审核，不允许修改！") ;
    		
    		SysUtil.abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception{
    	if(editData.getState().equals(ScheduleStateEnum.AUDITTED)){
    		MsgBox.showWarning(this, "单据已经审核，不允许删除！") ;
    		
    		SysUtil.abort();
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    /**
	 * 新增行
	 * 
	 * @param table
	 */
	protected void addLine(KDTable table) {
		table.checkParsed();
		int index = -1;
		index = table.getRowCount();
		IRow row;
		if (index > 0) {
			int lastIndex = index -1;
			IRow lastRow = table.getRow(lastIndex);
			
//			table.getColumn(STARTDATE_COL).setRenderer(FDCRenderHelper.getLongNumberRender());
//			table.getColumn(ENDDATE_COL).setRenderer(FDCRenderHelper.getLongNumberRender());
//			
/*			KDDatePicker startDatePicker = new KDDatePicker();	startDatePicker.setDatePattern("yyyy-mm-dd");		
			KDTDefaultCellEditor startDate_CellEditor = new KDTDefaultCellEditor(startDatePicker);
//			lastRow.getCell(STARTDATE_COL).setEditor(startDate_CellEditor);
			table.getColumn(STARTDATE_COL).setEditor(startDate_CellEditor);
			
			KDDatePicker endDatePicker = new KDDatePicker();			
			
			KDTDefaultCellEditor endDatePicker_CellEditor = new KDTDefaultCellEditor(endDatePicker);
			table.getColumn(ENDDATE_COL).setEditor(endDatePicker_CellEditor);*/
			
			row = table.addRow(index);
//			Object obj = lastRow.getCell(WBS_COL).getValue();
//			if (obj != null) {
//				BaseWBSInfo wbs = (BaseWBSInfo) obj;
//				row = table.addRow(index);
//				// 默认等于上一行的WBS
//				row.getCell(WBS_COL).setValue(wbs);
//				// row.getCell(ITEMNAME_COL).setValue(wbs.getName());
//			}else{
//				row = table.addRow(index);
//				row.getCell(WBS_COL).setValue(null);
//			}
		} else {
			row = table.addRow();
		}
	}

	/**
	 * 插入行
	 */
	protected void insertLine(KDTable table) {
		int i = -1;
		i = table.getSelectManager().getActiveRowIndex();
		if (i == -1) {
			MsgBox.showWarning(this, "请选择行！");
			return;
		}
		IRow row = table.addRow(i);
		int top = table.getSelectManager().get().getTop();
		if (top >= 1) {
			IRow topRow = table.getRow(top - 1);
//			Object obj = topRow.getCell(WBS_COL).getValue();
//			if (obj != null) {
//				BaseWBSInfo wbs = (BaseWBSInfo) obj;
//				row.getCell(WBS_COL).setValue(wbs);
//				// row.getCell(ITEMNAME_COL).setValue(wbs.getName());
//			}
		}
	}

	/**
	 * 在指定表格中删除当前选中行 增加隔行删除功能
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		
		if ((table.getSelectManager().size() == 0) || isTableColumnSelected(table))
		{
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));

			return;
		}

		// [begin]进行删除分录的提示处理。
		if (confirmRemove()) {
			// 获取选择块的总个数
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// 因为先择块的顺序可能并非是表中行的顺序，所以先要排序使选择块的顺序正好是由小到大
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
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
			if (indexArr == null)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// 避免有合计行的分录处理。
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
			}

			// 如果现在有记录定位到第一行
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}
	
	/**
	 * 锁住单无格。
	 * 
	 * @param row
	 * 
	 */
	private void lockRowAndColumn(IRow row) {
//		row.getCell(PRICE_COL).getStyleAttributes().setLocked(true);
//		row.getCell(PLANNUMBER_COL).getStyleAttributes().setLocked(true);
//		row.getCell(CHANGENUMBER_COL).getStyleAttributes().setLocked(true);
//		row.getCell(TOTALNUMBER_COL).getStyleAttributes().setLocked(true);
//		row.getCell(AUDITNUMBER_COL).getStyleAttributes().setLocked(true);
//		row.getCell(AMOUNT_COL).getStyleAttributes().setLocked(true);
//		row.getCell(CONFIRMPLANNUM_COL).getStyleAttributes().setLocked(true);
//		row.getCell(CONFIRMCHANGENUM_COL).getStyleAttributes().setLocked(true);
//		//
//		row.getCell(PRICE_COL).setValue(null);
//		row.getCell(PLANNUMBER_COL).setValue(null);
//		row.getCell(CHANGENUMBER_COL).setValue(null);
//		row.getCell(TOTALNUMBER_COL).setValue(null);
//		row.getCell(AUDITNUMBER_COL).setValue(null);
//		row.getCell(AMOUNT_COL).setValue(null);
//		row.getCell(CONFIRMPLANNUM_COL).setValue(null);
//		row.getCell(CONFIRMCHANGENUM_COL).setValue(null);
	}
	
	private void loadEntries(KDTable table) {
		if (STATUS_ADDNEW.equals(getOprtState())) {
			return;
		}
		
		table.removeRows(false);
		FDCMonthTempTaskEntryCollection coll = editData.getTempTaskEntrys();
		if (coll == null || coll.size() <= 0) {
			return;
		}
		int size = coll.size();
		for (int i = 0; i < size; i++) {
			FDCMonthTempTaskEntryInfo entryInfo = (FDCMonthTempTaskEntryInfo) coll.get(i);
			IRow row = table.addRow();
			
//			table.getColumn(STARTDATE_COL).setRenderer(FDCRenderHelper.getLongNumberRender());
//			table.getColumn(ENDDATE_COL).setRenderer(FDCRenderHelper.getLongNumberRender());
//			
//			KDDatePicker startDatePicker = new KDDatePicker();			
//			
//			KDTDefaultCellEditor startDate_CellEditor = new KDTDefaultCellEditor(startDatePicker);
//			row.getCell(STARTDATE_COL).setEditor(startDate_CellEditor);
//			
//			KDDatePicker endDatePicker = new KDDatePicker();			
//			
//			KDTDefaultCellEditor endDatePicker_CellEditor = new KDTDefaultCellEditor(endDatePicker);
//			row.getCell(ENDDATE_COL).setEditor(endDatePicker_CellEditor);
//			
//			
			
			row.getCell(ID_COL).setValue(entryInfo.getId());
			row.getCell(NUMBER_COL).setValue(entryInfo.getNumber());
			row.getCell(NAME_COL).setValue(entryInfo.getName());
			row.getCell(STARTDATE_COL).setValue(entryInfo.getStartDate());
			row.getCell(ENDDATE_COL).setValue(entryInfo.getEndDate());
			row.getCell(EFFECTTIMES1_COL).setValue(entryInfo.getEffectTimes());
			row.getCell(DESC_COL).setValue(entryInfo.getDesc());
			
		}
		// 数据树表显示处理
		// setTableTreeDisplayStyle(table);
	}

	/**
	 * 
	 * 
	 * @param table
	 */
	private void storeEntries(KDTable table) {
		int count = table.getRowCount();
		MonthScheduleInfo billInfo = new MonthScheduleInfo();
		billInfo.setId(editData.getId());
		editData.getTempTaskEntrys().clear();
		Object obj = null;
		for (int i = 0; i < count; i++) {
			FDCMonthTempTaskEntryInfo entryInfo = new FDCMonthTempTaskEntryInfo();
			entryInfo.setParent(billInfo);
			//
			String number = null;
			String name = null;
			String desc = null;
			BigDecimal effectTimes = EcConstant.ZERO;
			Date startDate = null;
			Date endDate = null;
			//
			IRow row = table.getRow(i);
			BOSUuid fID = null;// FID
			obj = row.getCell(ID_COL).getValue();
			if (obj != null) {
				if (obj instanceof BOSUuid) {
					fID = (BOSUuid) obj;
				}
			}
			if (fID == null) {
				fID = BOSUuid.create(entryInfo.getBOSType());
			}

			obj = row.getCell(NUMBER_COL).getValue();
			if (obj != null) {
				if (obj instanceof NumberExpandInfo) {
					NumberExpandInfo numberVo = (NumberExpandInfo) obj;
					number = numberVo.getNumber();
				} else {
					number = obj.toString().trim();
				}
			}
			obj = row.getCell(NAME_COL).getValue();
			if (obj != null) {
				name = obj.toString().trim();
			}
			
			obj = row.getCell(DESC_COL).getValue();
			if (obj != null) {
				desc = obj.toString().trim();
			}
			
			obj = row.getCell(STARTDATE_COL).getValue();
			if (obj != null) {
				if (obj instanceof Date) {
					startDate = (Date) obj;
				} 
			}
			
			obj = row.getCell(ENDDATE_COL).getValue();
			if (obj != null) {
				if (obj instanceof Date) {
					endDate = (Date) obj;
				} 
			}
			
			obj = row.getCell(EFFECTTIMES1_COL).getValue();
			
			if (obj != null) {
				if (obj instanceof BigDecimal) {
					effectTimes = (BigDecimal) obj;
				} 
			}

			entryInfo.setId(fID);
			entryInfo.setNumber(number);
			entryInfo.setName(name);
			entryInfo.setDesc(desc);
			entryInfo.setEffectTimes(effectTimes);
			entryInfo.setStartDate(startDate);
			entryInfo.setEndDate(endDate);
			
			editData.getTempTaskEntrys().add(entryInfo);
		}
	}
	
	// 因为目前ObjectValue比较是按值比较，但集合中使用，如果分录值相同，
	// 都会删除找到的第一个，会出错。自行实现按指针比较。
	protected int getCollectionIndex(IObjectCollection collection, IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		
//		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("tempTaskEntrys"));
		sic.add(new SelectorItemInfo("tempTaskEntrys.*"));
		
		return sic;
	}
	
	/**
     * output tempTaskEntrys_editStopped method
     */
    protected void tempTaskEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	Date startDate = null;
		Date endDate = null;
		startDate = (Date)kdtTempTaskEntrys.getCell(e.getRowIndex(), "startDate").getValue();
		endDate = (Date)kdtTempTaskEntrys.getCell(e.getRowIndex(), "endDate").getValue();
		
		Date d  = null;
    	if(pkMonth.getValue() == null ) {
    		MsgBox.showWarning(this, "请输入计划月份");    		
    		SysUtil.abort();    		
    	}else{
    		d = (Date)pkMonth.getValue();
    	}
		
		Calendar   cd   =   Calendar.getInstance();   
    	cd.set(cd.YEAR,(d.getYear()+1900 ));   
    	cd.set(cd.MONTH,(d.getMonth() ));  
    	cd.set(cd.DATE, 1);
    	    
//    	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
//    	String day_end = df.format(cd.getTime());
    	
    	Date startDate0 = (Date)cd.getTime().clone(); //一个月的开始时间
    	cd.set(cd.MONTH,(d.getMonth() +1)); 
    	cd.add(cd.DATE, -1);
    	Date endDate0 = (Date)cd.getTime().clone(); //一个月的结束时间
		
		if(startDate != null){
			startDate = DateTimeUtils.truncateDate(startDate);
			startDate0 = DateTimeUtils.truncateDate(startDate0);
			endDate0 = DateTimeUtils.truncateDate(endDate0);
			if(startDate.getTime() - startDate0.getTime() < 0){
				MsgBox.showWarning(this, "开始时间必须在计划月份之内!");
				kdtTempTaskEntrys.getCell(e.getRowIndex(), "startDate").setValue(null);
				SysUtil.abort();
			}
			
			if(startDate.getTime() - endDate0.getTime() > 0){
				MsgBox.showWarning(this, "开始时间必须在计划月份之内!");
				kdtTempTaskEntrys.getCell(e.getRowIndex(), "startDate").setValue(null);
				SysUtil.abort();
			}
		}
		
		if(endDate != null){
			
			endDate = DateTimeUtils.truncateDate(endDate);
			startDate = DateTimeUtils.truncateDate(startDate);
			
			if(startDate != null && endDate.compareTo(startDate) < 0){
				MsgBox.showWarning(this, "结束时间不能小于开始时间!");
				kdtTempTaskEntrys.getCell(e.getRowIndex(), "endDate").setValue(null);
				SysUtil.abort();
			}
		}
		long effectTimes = 0;
		
		if(startDate != null && endDate != null){
			endDate = DateTimeUtils.truncateDate(endDate);
			startDate = DateTimeUtils.truncateDate(startDate);
			if(endDate.getTime() < startDate.getTime()){
				MsgBox.showWarning(this, "开始时间不能大于结束时间!");
				kdtTempTaskEntrys.getCell(e.getRowIndex(), "startDate").setValue(null);
				SysUtil.abort();
			}
			
			
//			long effectTimes0 = ScheduleCalendarHelper.getNatureTimes(startDate,endDate).intValue();
			
//			effectTimes = effectTimes0/24/60/60/1000;
			ScheduleCalendarInfo calendar = new ScheduleCalendarInfo();
			calendar.setObjectId(this.editData.getProject().getId().toString());
			kdtTempTaskEntrys.getCell(e.getRowIndex(), "effectTimes1").setValue(ScheduleCalendarHelper.getEffectTimes(startDate,endDate,calendar));
		}		
    }
    
    /**
     * output planType_itemStateChanged method
     */
    protected void planType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
    	if("部门".equals(e.getItem().toString()) && pkMonth.getValue() != null){
    		if(editData.getTaskEntrys()==null){
    			importMonthTask();
    		}
    		
    	} 	
    }

    /**
     * output month_dataChanged method
     */
    protected void month_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception{
    	if("部门".equals(comboPlanType.getSelectedItem().toString()) && e.getNewValue() != null){
    		if(editData.getTaskEntrys()==null){
    			importMonthTask();
    		}
    	} 
    }

    /*
     * 导入本月任务
     */
    private void importMonthTask() throws Exception{
    	Date d  = null;
    	if(pkMonth.getValue() == null ) {
    		MsgBox.showWarning(this, "请输入计划月份");    		
    		SysUtil.abort();    		
    	}else{
    		d = (Date)pkMonth.getValue();
    	}
    	
//    	if(kdtTaskEntrys.getRowCount()>0){
//    		MsgBox.showConfirm3(this, "本月已经有固有任务了，你确认")
//    	}
    	
//    	kdtTaskEntrys.removeRows();
    	
    	Calendar   cd   =   Calendar.getInstance();   
    	cd.set(cd.YEAR,(d.getYear()+1900 ));   
    	cd.set(cd.MONTH,(d.getMonth() ));  
    	cd.set(cd.DATE, 1);
    	    
//    	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
//    	String day_end = df.format(cd.getTime());
    	
    	Date startDate = cd.getTime();
    	cd.set(cd.MONTH,(d.getMonth() +1)); 
    	cd.add(cd.DATE, -1);
    	Date endDate = cd.getTime();
    	
//    	FullOrgUnitInfo adminDept = (FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit();
    	String adminDeptId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
    	CurProjectInfo prj = null;
    	
    	
    	if((CurProjectInfo)getUIContext().get("CurProject") == null){
    		prj = editData.getProject();
    	}else{
    		prj = (CurProjectInfo)getUIContext().get("CurProject");
    	}
    	
    	FDCScheduleTaskCollection col = MonthScheduleFactory.getRemoteInstance().fetchTask0(startDate, endDate, adminDeptId, null,true,prj);
		int count = 0;
		
		if(col != null){
			fillTable(col);
		}
    }
    
    /**
     * output taskEntrys_editStopped method
     */
    protected void taskEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
    	if(kdtTempTaskEntrys.getCell(e.getRowIndex(), "theMonthRate") != null){
    		BigDecimal theMonthRate = (BigDecimal) kdtTempTaskEntrys.getCell(e.getRowIndex(), "theMonthRate").getValue();
        	if(theMonthRate.compareTo(new BigDecimal("0.00")) < 0){
        		MsgBox.showWarning(this, "本月计划完成百分比不能小于零！");
        		SysUtil.abort();
        	}
    	}

    }
 
	
	private String[] getColumnValues(String columnNumber){
		if(kdtTaskEntrys.getRowCount() >0 ){
			String[] colValues = new String[kdtTaskEntrys.getRowCount()];
			
			if(kdtTaskEntrys.getRowCount() >0){
				for(int i = 0; i < kdtTaskEntrys.getRowCount();i++){
					colValues[i] = kdtTaskEntrys.getCell(i, columnNumber).getValue().toString();
				}
			}
			return colValues;
		}else{
			return null;
		}
		
	}
	public boolean isModify() {
		return false;
	}
	private void handlePrefixDepend() throws EASBizException,BOSException{
		Set taskIds=new HashSet();
		for(int i=0;i<kdtTaskEntrys.getRowCount();i++){
			IRow row=kdtTaskEntrys.getRow(i);
			if(row.getUserObject() instanceof FDCMonthTaskEntryInfo){
				FDCMonthTaskEntryInfo entry=(FDCMonthTaskEntryInfo)row.getUserObject();
				taskIds.add(entry.getTask().getId().toString());
			}
		}
		
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("dependTask.id",taskIds,CompareType.INCLUDE));
		view.getSelector().add("task.id");
		view.getSelector().add("task.name");
		view.getSelector().add("dependTask.id");
		view.getSorter().add(new SorterItemInfo("task.longNumber"));
		FDCScheduleTaskDependCollection fdcScheduleTaskDependCollection = FDCScheduleTaskDependFactory.getRemoteInstance().getFDCScheduleTaskDependCollection(view);
		
		Map prefixMap=new HashMap();
		for(Iterator iter=fdcScheduleTaskDependCollection.iterator();iter.hasNext();){
			FDCScheduleTaskDependInfo dependInfo=(FDCScheduleTaskDependInfo)iter.next();
			if(dependInfo.getDependTask()==null||dependInfo.getDependTask().getId()==null){
				continue;
			}
			String dependId = dependInfo.getDependTask().getId().toString();
			String prefixTaskName = (String)prefixMap.get(dependId);
			if(prefixTaskName==null){
				prefixTaskName=dependInfo.getTask().getName();
			}else{
				prefixTaskName=prefixTaskName+";"+dependInfo.getTask().getName();
			}
			prefixMap.put(dependId, prefixTaskName);
		}
		
		for(int i=0;i<kdtTaskEntrys.getRowCount();i++){
			IRow row=kdtTaskEntrys.getRow(i);
			if(row.getUserObject() instanceof FDCMonthTaskEntryInfo){
				FDCMonthTaskEntryInfo entry=(FDCMonthTaskEntryInfo)row.getUserObject();
				String dependId = entry.getTask().getId().toString();
				row.getCell("parent").setValue(prefixMap.get(dependId));
			}
		}
	}
	/**
	 * 处理编码规则
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = "";
		if(editData instanceof MonthScheduleInfo && ((MonthScheduleInfo) editData).getOrgUnit() != null){
			currentOrgId = ((MonthScheduleInfo) editData).getOrgUnit().getId().toString();
		}else{
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			currentOrgId = org.getId().toString();
		}
		ICodingRuleManager codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if(!OprtState.ADDNEW.equals(getOprtState())){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length() == 0 || !codingRuleManager.isExist(editData, currentOrgId)){
			isExist = false;
		}
		this.codingRuleEnabled = isExist;
		if(isExist){
//			是否新增显示
			boolean isAddView = codingRuleManager.isAddView(editData, currentOrgId);
			if(isAddView){			//新增显示
				getNumberByCodingRule(editData, currentOrgId);
			}else{
				String number = null;
				if(codingRuleManager.isUseIntermitNumber(editData, currentOrgId)
						&& codingRuleManager.isUserSelect(editData, currentOrgId)){
					CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData, currentOrgId, null, null);
					Object obj = null;
					if(codingRuleManager.isDHExist(editData, currentOrgId)){
						intermilNOF7.show();
						obj = intermilNOF7.getData();
					}
					if(obj != null){
						number = obj.toString();
					}
				}
				this.txtNumber.setText(number);
			}
			setNumberTextEnabled();
		}
	}
	
	/**
	 * 根据编码规则获取编码
	 */
	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		try {
			ICodingRuleManager codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if(orgId == null || orgId.trim().length() == 0){
				orgId = OrgConstants.DEF_CU_ID;
			}
			if(codingRuleManager.isExist(caller,orgId)){

                String number = "";
                if (codingRuleManager.isUseIntermitNumber(caller, orgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    if (codingRuleManager.isUserSelect(caller, orgId)){
                        // 启用了断号支持功能,同时启用了用户选择断号功能
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(caller, orgId, null, null);
                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
                        Object object = null;
                        if (codingRuleManager.isDHExist(caller, orgId)){
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null){
                            number = object.toString();
                        }else{
                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
                            number = codingRuleManager .getNumber(caller, orgId);
                        }
                    } else{
                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = codingRuleManager.readNumber(caller, orgId);
                    }
                }else{
                    // 没有启用断号支持功能，此时获取了编码规则产生的编码
                	String costCenterId = null;
            		if(editData instanceof MonthScheduleInfo && ((MonthScheduleInfo)editData).getOrgUnit() != null){
            			costCenterId = ((MonthScheduleInfo)editData).getOrgUnit().getId().toString();
            		}else{		
//            			costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();售楼组织成本中心为空 
            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
            		}
                    number =  prepareNumberForAddnew(codingRuleManager,(MonthScheduleInfo)editData, orgId,costCenterId,null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }
                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);
                if (codingRuleManager.isModifiable(caller, orgId)){
                    //如果启用了用户可修改
                    setNumberTextEnabled();
                }
                return;
			}
		}catch (Exception e) {
			handleCodingRuleError(e);
			setNumberTextEnabled();
			e.printStackTrace();
		}
		setNumberTextEnabled();
	}
	
	/**
	 * 编码规则中启用了“新增显示”,必须检验是否已经重复
	 * @param iCodingRuleManager
	 * @param info
	 * @param orgId
	 * @param costerId
	 * @param bindingProperty
	 * @return
	 * @throws Exception
	 */
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,MonthScheduleInfo info,String orgId,String costerId,String bindingProperty)throws Exception{
		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//如果编码重复重新取编码
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(),CompareType.NOTEQUALS));		
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),CompareType.NOTEQUALS));
			}
			i++;
		}while (((IMonthSchedule)getBizInterface()).exists(filter) && i<1000);
		return number;
	}
	protected void setNumberTextEnabled() {
		if(this.txtNumber != null) {
			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
			if (currentCostUnit == null) {
				currentCostUnit = SysContext.getSysContext().getCurrentSaleUnit();
			}
			if (currentCostUnit != null) {
				boolean isAllowModify = true;
				try {
					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
					if (iCodingRuleManager.isExist(editData, currentCostUnit.getId().toString())) {
						isAllowModify = iCodingRuleManager.isModifiable(editData, currentCostUnit.getId().toString());
					} else {
						isAllowModify = true;
					}
				} catch (Exception e) {
					ExceptionHandler.handle(e);
				}
				this.txtNumber.setEnabled(isAllowModify);
				this.txtNumber.setEditable(isAllowModify);
			}
		}	
	}
	protected void prepareNumber(IObjectValue caller, String number) {
		this.txtNumber.setText(number);

	}
}