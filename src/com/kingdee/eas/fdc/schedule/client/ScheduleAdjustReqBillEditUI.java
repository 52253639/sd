/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillFactory;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskAdjustManager;
import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ScheduleAdjustReqBillEditUI extends AbstractScheduleAdjustReqBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleAdjustReqBillEditUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleAdjustReqBillEditUI() throws Exception
    {
        super();
    }

    protected IObjectValue createNewData() {
    	ScheduleAdjustReqBillInfo info=getNewData();
    	if(info==null){
    		SysUtil.abort();
    	}
    	info.setCreateTime(new Timestamp(System.currentTimeMillis()));
    	info.setState(FDCBillStateEnum.SAVED);
    	String prjId;
    	if(getUIContext().get("projectId") != null){
    		prjId = getUIContext().get("projectId").toString();
    		
    		try {
				CurProjectInfo curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)));
				info.setProject(curProjectInfo);
			} catch (Exception e) {
				this.handleException(e);
			} 
    	}
//    	info.setba
    	return info;
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ScheduleAdjustReqBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return tblAdjustTask;
	}
	protected KDTable getAdjustTaskTable(){
		return this.tblAdjustTask;
	}
	protected KDTable getNewTaskTable(){
		return this.tblNewTask;
	}
	protected KDTable getRpTable(){
		return this.tblRpt;
	}
	

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	private ScheduleAdjustTaskHelper adjustTaskHelper=null;
	private ScheduleNewTaskHelper newTaskHelper=null;
	private ScheduleAdjustRptHelper rptHelper=null;
	public void onLoad() throws Exception {
		getAdjustTaskTable().checkParsed();
		getNewTaskTable().checkParsed();
		getRpTable().checkParsed();
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		
		super.onLoad();
		adjustTaskHelper=new ScheduleAdjustTaskHelper(this);
		newTaskHelper=new ScheduleNewTaskHelper(this);
		rptHelper=new ScheduleAdjustRptHelper(this);
		this.txtTotalTimes.setEditable(false);
		this.txtTotalTimes.setPrecision(0);
		this.txtTotalTimes.setRemoveingZeroInDispaly(true);
		this.txtTotalTimes.setRemoveingZeroInEdit(true);
		for(Iterator iter=this.editData.getAdjustEntrys().iterator();iter.hasNext();){
			ScheduleAdjustTaskEntryInfo entry=(ScheduleAdjustTaskEntryInfo)iter.next();
			getTaskList().updateItem(entry.getTask().getId().toString(), entry);
		}
		
		for(Iterator iter=this.editData.getNewTaskEntrys().iterator();iter.hasNext();){
			ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)iter.next();
			getTaskList().addNewItem(entry.getParentTask()!=null?entry.getParentTask().getId().toString():null, entry);
		}
		setDataChanged();
		
		//重新设置一下按钮的状态,房地产的基类搞的太乱了
		setOprtState(getOprtState());
		menuBiz.setVisible(true);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		FDCClientHelper.setActionEnable(new ItemAction[]{
				this.actionFirst,
				this.actionNext,
				this.actionPre,
				this.actionLast,
				this.actionRemoveLine,
				this.actionAddLine,
				this.actionInsertLine,
				this.actionCopy,
				this.actionCreateFrom,
				this.actionCreateTo,
				this.actionTraceDown,
				this.actionTraceUp,
				this.actionCopyFrom
				
		}, false);
		this.menuTable1.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
	}
	
	public void loadFields() {
		super.loadFields();
		if(baseInfo == null && editData.get("schedule") != null){
			baseInfo=(FDCScheduleInfo)this.editData.get("schedule");
		}else if(editData.get("schedule") == null){
			
		}
		if(baseInfo!=null&&baseInfo.getCalendar()==null){
			//设置使用默认的假期
			ScheduleCalendarInfo calendar=new ScheduleCalendarInfo();
			calendar.setId(BOSUuid.create(calendar.getBOSType()));
			DefaultWeekendEntryInfo weekendEntry=new DefaultWeekendEntryInfo();
			weekendEntry.setParent(calendar);
			weekendEntry.setWeekend(ScheduleWeekendEnum.Saturday);
			calendar.getWeekendEntrys().add(weekendEntry);
			weekendEntry=new DefaultWeekendEntryInfo();
			weekendEntry.setParent(calendar);
			weekendEntry.setWeekend(ScheduleWeekendEnum.Sunday);
			calendar.getWeekendEntrys().add(weekendEntry);
			baseInfo.setCalendar(calendar);
		}
		getUIContext().put("projectId", this.editData.getProject().getId());
		setOprtState(getOprtState());
		
		for(int i=0;i<tblNewTask.getRowCount();i++){
			IRow row=tblNewTask.getRow(i);
			if(!(row.getUserObject() instanceof ScheduleNewTaskEntryInfo)){
				continue;
			}
			ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)row.getUserObject();
			String prefixTask=null;
			if(entry.getPrefixEntrys()!=null){
				for(int j=0;j<entry.getPrefixEntrys().size();j++){
					ScheduleAdjustPrefixTaskInfo scheduleAdjustPrefixTaskInfo = entry.getPrefixEntrys().get(j);
					if(scheduleAdjustPrefixTaskInfo==null){
						continue;
					}
					FDCScheduleTaskInfo task = scheduleAdjustPrefixTaskInfo.getPrefixTask();
					if(task==null){
						continue;
					}
					if(prefixTask==null){
						prefixTask=task.getName();
					}else{
						prefixTask=prefixTask+";"+task.getName();
					}
				}
			}
			row.getCell("dependTask").setValue(prefixTask);
		}
		
	}
	public void onShow() throws Exception {
		super.onShow();
	}
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		if(this.editData!=null){
			if(this.editData.getId()==null){
				this.editData.setId(BOSUuid.read(pk.toString()));
			}
			//保存后不重新取值
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("number");
			ScheduleAdjustReqBillInfo  info = ScheduleAdjustReqBillFactory.getRemoteInstance().getScheduleAdjustReqBillInfo(pk, sic);
			if(info.getNumber() != null && info.getNumber().length() >0){
				editData.setNumber(info.getNumber());
			}
			return this.editData;
		}
		return ScheduleAdjustReqBillFactory.getRemoteInstance().getValue2(pk, getSelectors());
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		actionAddNew.setVisible(false);
		if(this.editData!=null&&this.editData.getState()==FDCBillStateEnum.SUBMITTED){
			actionSave.setEnabled(false);
		}
		if(this.editData!=null&&this.editData.getState()==FDCBillStateEnum.AUDITTED){
			actionRemove.setEnabled(false);
			actionEdit.setEnabled(false);
		}
		this.actionUnAudit.setVisible(false);
		this.actionAudit.setVisible(true);
		if(this.editData!=null&&this.editData.getState()==FDCBillStateEnum.AUDITTED){
			this.actionAudit.setEnabled(false);
		}else if(this.editData!=null&&this.editData.getState()==FDCBillStateEnum.SUBMITTED){
			this.actionAudit.setEnabled(true);
		}
	}
	
	protected void fetchInitData() throws Exception {
		
	}
	
	public void fillRptTable(){
		this.rptHelper.fillTable();
	}
	
	private FDCScheduleInfo baseInfo=null;
	protected FDCScheduleInfo getBaseScheduleInfo(){
		return baseInfo;
	}
	
	protected TaskAdjustManager getTaskList(){
		return this.rptHelper.getTaskList();
	}
	
	private ScheduleAdjustReqBillInfo getNewData(){
		Map param=new HashMap();
		param.put("projectId", getUIContext().get("projectId").toString());
		ScheduleAdjustReqBillInfo info=null;
		try {
			info = ScheduleAdjustReqBillFactory.getRemoteInstance().getNewData(param);
			info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		} catch (Exception e) {
			this.handUIException(e);
		}
		return info;
	}
	
	private boolean isModify=false;
	public boolean isModify() {
		return isModify;
	}
	
	protected void setDataChanged(){
		rptHelper.setChanged();
	}
	
	protected void mainTabbedPane_stateChanged(ChangeEvent e) throws Exception {
		if(mainTabbedPane.getSelectedIndex()==2){
			//select rptTable 
			fillRptTable();
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		
		/*
		 * 1.不允许在只调整工期内同时调整同一个任务
		 * 2.不允许在调整工期内调整过又在新增任务调整内调整
		 */
		int adjustEntrySize = this.editData.getAdjustEntrys().size();
		int newTaskSize = this.editData.getNewTaskEntrys().size();
		if(adjustEntrySize==0&&newTaskSize==0){
			FDCMsgBox.showWarning(this, "未进行过任何调整,不能保存");
			SysUtil.abort();
		}
		ScheduleCalendarInfo calendar=this.getBaseScheduleInfo().getCalendar();
		
		for(int i=0;i<adjustEntrySize;i++){
			ScheduleAdjustTaskEntryInfo entry=this.editData.getAdjustEntrys().get(i);
			
			if(entry.getTask()==null){
				FDCMsgBox.showWarning(this, "调整工期分录内任务不能为空!");
				SysUtil.abort();
			}
			
			//调整后的开始结束时间不能为假日
			if(calendar.isWeekTime(entry.getStartDate())||calendar.isWeekTime(entry.getEndDate())){
				FDCMsgBox.showWarning(this, "调整工期分录内存在调整后开始/结束时间在假期内");
				SysUtil.abort();
			}
		}
		for(int j=0;j<newTaskSize;j++){
			ScheduleNewTaskEntryInfo entry=this.editData.getNewTaskEntrys().get(j);
			if(entry.getParentTask()==null){
				FDCMsgBox.showWarning(this, "新增任务的上级任务不能为空!");
				SysUtil.abort();
			}
			if(FDCHelper.isEmpty(entry.getName())||entry.getStart()==null||entry.getEnd()==null){
				FDCMsgBox.showWarning(this, "新增任务并调整工期分录内名称,开始时间,结束时间不能为空!");
				SysUtil.abort();
			}
			
			//调整后的开始结束时间不能为假日
			if(calendar.isWeekTime(entry.getStart())||calendar.isWeekTime(entry.getEnd())){
				FDCMsgBox.showWarning(this, "新增任务并调整工期分录内存在开始/结束时间在非工作日的情况!");
				SysUtil.abort();
			}
			
		}
		
		for(int i=0;i<adjustEntrySize;i++){
			ScheduleAdjustTaskEntryInfo entry=this.editData.getAdjustEntrys().get(i);
			for(int j=i+1;j<adjustEntrySize;j++){
				ScheduleAdjustTaskEntryInfo tempEntry=this.editData.getAdjustEntrys().get(j);
				if(tempEntry.getTask().getId().equals(entry.getTask().getId())){
					FDCMsgBox.showWarning(this, "不允许在只调整工期内同时调整同一个任务:"+entry.getTask().getName());
					SysUtil.abort();
				}
			}
			for(int j=0;j<newTaskSize;j++){
				ScheduleNewTaskEntryInfo tempEntry=this.editData.getNewTaskEntrys().get(j);
				if(tempEntry.getParentTask()!=null&&tempEntry.getParentTask().getId().equals(entry.getTask().getId())){
					FDCMsgBox.showWarning(this, "不允许在调整工期内调整过又在新增任务调整内做为上级进行调整:"+entry.getTask().getName());
					SysUtil.abort();
				}
			}
			
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("project.id"));
        sic.add(new SelectorItemInfo("baseVer.id"));
        sic.add(new SelectorItemInfo("*"));
        sic.add("newTaskEntrys.prefixEntrys.*");
        sic.add("newTaskEntrys.prefixEntrys.prefixTask.id");
        sic.add("newTaskEntrys.prefixEntrys.prefixTask.name");
        sic.add("newTaskEntrys.prefixEntrys.prefixTask.longNumber");
        sic.add("newTaskEntrys.prefixEntrys.prefixTask.number");
        sic.add("newTaskEntrys.wbs.id");
        sic.add("newTaskEntrys.respDept.id");
        sic.add("newTaskEntrys.respDept.name");
        sic.add("newTaskEntrys.respDept.number");
		return sic;
	}
	
	protected void initListener() {
		
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		txtTotalTimes.setValue(rptHelper.getTotalTimes());
		editData.remove("schedule");
		super.actionSave_actionPerformed(e);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		txtTotalTimes.setValue(rptHelper.getTotalTimes());
		editData.remove("schedule");
		super.actionSubmit_actionPerformed(e);
	}
	protected void disposeUIWindow() {
		Set wbsIds = new HashSet();
		for(int i=0;i<editData.getNewTaskEntrys().size();i++){
			if(editData.getNewTaskEntrys().get(i).getWbs() != null
					&& editData.getNewTaskEntrys().get(i).getWbs().getId() != null){
				wbsIds.add(editData.getNewTaskEntrys().get(i).getWbs().getId().toString());
			}
		}
		try {
			FDCScheduleTaskExtFactory.getRemoteInstance().deletePropByWBSIDs(wbsIds);
		} catch (Exception e) {
			this.handleException(e);
		} 
		super.disposeUIWindow();
	}
}