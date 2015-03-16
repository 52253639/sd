/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.calc.CalculatorDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.AmusementUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.tenancy.ITenBillBase;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class TenBillBaseEditUI extends AbstractTenBillBaseEditUI {
	private final static String CANTAUDIT = "cantAudit";

	private static final String CANTEDIT = "cantEdit";

	private static final String CANTREMOVE = "cantRemove";

	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";

	private final static int PRECSION = 2;

	//是否保存操作
	public boolean isSaveAction = false;

	private static final Logger logger = CoreUIObject.getLogger(TenBillBaseEditUI.class);

	//财务组织
	protected CompanyOrgUnitInfo company = null;
	
	//当前组织
	protected FullOrgUnitInfo orgUnitInfo = null;
	
	//本位币
	protected CurrencyInfo baseCurrency = null;
	//合同单据
	protected ContractBillInfo contractBill = null;
	
	//当前工程项目
	protected CurProjectInfo curProject = null;

    // 界面标题：主标题，新增，修改，查看，凭证索引
    protected String titleMain, titleNew, titleModify, titleView, billIndex = "";

	//启用成本成本月结
	protected boolean isIncorporation = false;
	
	//登记日期
	protected Date bookedDate = null;	
	//当前期间
	protected PeriodInfo curPeriod = null ;
	//成本还是财务
	protected boolean isCost = true;
	//是否已经冻结
	protected boolean isFreeze = false;
	//可录入期间
	protected PeriodInfo canBookedPeriod = null ;
	
	/**
	 * output class constructor
	 */
	public TenBillBaseEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {
	    //titleMain = getUITitle();
	    titleNew = getStr("titleNew");
	    titleModify = getStr("titleModify");
	    titleView = getStr("titleView");
	}

    public String getStr(String name) {
        return getStr(FDCConstants.VoucherResource, name);
    }

    public String getStr(String resFile,String name) {
        return EASResource.getString(resFile, name);
    }

    protected void refreshUITitle() {
    	if(titleMain==null){
    		return;
    	}

        String state = getOprtState();
        if (OprtState.ADDNEW.equals(state)) {
            billIndex = "";
            this.setUITitle(titleMain + " - " + titleNew);
        } else {
            if (OprtState.EDIT.equals(state)) {
                this.setUITitle(titleMain + " - " + titleModify + billIndex);
            } else {
                boolean isFromMsgCenter = false;
                Map uiContext = getUIContext();
                if (uiContext != null) {
                    isFromMsgCenter = Boolean.TRUE.equals(uiContext.get("isFromWorkflow"));
                }
                if (!isFromMsgCenter) {
                    this.setUITitle(titleMain + " - " + titleView + billIndex);
                }
            }
        }
    }
    
    //控件数据变化统一事件,使用该功能统一控件事件,以免在loadFields时，不停触发控件事件
	public class ControlDateChangeListener implements DataChangeListener {
		private String shortCut = null; 
		public ControlDateChangeListener(String shortCut){
			this.shortCut = shortCut;
		}
        public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
            try {
            	controlDate_dataChanged(e,this);
            } catch (Exception exc) {
                handUIException(exc);
            } finally {
            }
        }
		public String getShortCut() {
			return shortCut;
		}
    };
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	//业务EditUI重载，实现控件数据变化事件
    }
    

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		String id = getSelectBOID();
		if (id != null) {
			getTenBillInterface().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
	}
	
	private ITenBillBase getTenBillInterface() throws Exception {
		return (ITenBillBase)getBizInterface();
	}

	/**
	 * 同步数据库数据到界面,用于审批/反审批后显示审批人,审批日期
	 * @throws Exception
	 */
	protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
		setSaveActionStatus();
		setNumberTextEnabled();
		
		//启用财务成本一体化
		enablePeriodComponent(this,isIncorporation);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTREMOVE);
		checkRef(getSelectBOID());
		super.actionRemove_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(getTenBillBaseInfo().getState() == null)
			getTenBillBaseInfo().setState(FDCBillStateEnum.SAVED);
		setSaveAction(true);
		super.actionSave_actionPerformed(e);

		handleOldData();


	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setSaveAction(false);

		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审核中或者已审核，不能再提交");
			SysUtil.abort();
		}

		super.actionSubmit_actionPerformed(e);

		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			handleCodingRule();
		}
		handleOldData();
	}

	protected boolean checkCanSubmit() throws Exception {
		
//		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
//			MsgBox.showWarning(this,"启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
//			SysUtil.abort();
//		}
		
		if(editData.getId()==null){
			return true;
		}

		return ((ITenBillBase)getBillInterface()).checkCanSubmit(editData.getId().toString());
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        }
        boolean isEdit = false;
        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
        {
            isEdit = true;
        }
        acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID 是 与附件关联的 业务对象的 ID
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		checkRef(getSelectBOID());
		String id = getSelectBOID();
		if (id != null) {
			getTenBillInterface().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
	}


	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) {

		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = getTenBillBaseInfo() != null
				&& getTenBillBaseInfo().getState() != null
				&& getTenBillBaseInfo().getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		 if(getOprtState().equals(STATUS_EDIT)) {
			 String warn = null;
			 if(state.equals(FDCBillStateEnum.AUDITTED)) {
				 warn = CANTUNAUDITEDITSTATE;
			 }
			 else {
				 warn = CANTAUDITEDITSTATE;
			 }
			 MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			 SysUtil.abort();
		 }
	}

	protected void checkBeforeEditOrRemove(String warning) {
		FDCBillStateEnum state = getTenBillBaseInfo().getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
	}

	/**
	 *
	 * 描述：根据当前单据的状态决定执行保存还是提交操作
	 *
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.EditUI#checkBeforeWindowClosing()
	 */
	public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}

		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}

		boolean b = true;

		if (!b) {
			return b;
		} else {

			// 校验storeFields()是否抛出异常
			// Exception err = verifyStoreFields();

			// storeFields()抛出异常或者editdata有改变，询问是否保存退出
			if (isModify()) {
				// editdata有改变
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));

				if (result == KDOptionPane.YES_OPTION) {

					try {
						if (getTenBillBaseInfo().getState() == null
								|| getTenBillBaseInfo().getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSave,
									ActionEvent.ACTION_PERFORMED, btnSave
											.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSubmit,
									ActionEvent.ACTION_PERFORMED, btnSubmit
											.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
							// actionSubmit_actionPerformed(event);
						}
						// return true;
					} catch (Exception exc) {
						// handUIException(exc);
						return false;
					}

				} else if (result == KDOptionPane.NO_OPTION) {
					// stopTempSave();
					return true;
				} else {
					return false;
				}
			} else {
				// stopTempSave();
				return true;
			}

		}

	}

	protected IObjectValue createNewData() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		/* TODO 自动生成方法存根 */
		return null;
	}

	private void disablePrintFunc() {
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
	}

	/* 不合理接口,将其改成抽象方法子类必须实现
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getBizInterface()
	 */
	protected abstract ICoreBase getBizInterface() throws Exception;
	
	/*
	 *  不合理接口,将其改成抽象方法子类必须实现,如果没有分录table,返回null
	 * (non-Javadoc)
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getDetailTable()
	 */
	protected abstract KDTable getDetailTable();

	private TenBillBaseInfo getTenBillBaseInfo() {
		return (TenBillBaseInfo) editData;
	}

	//虚体可以新增之后,虚体就可以修改等操作.所以将此方法放开,以便控制
	protected void handleActionStatusByCurOrg() {
		if(true){
			//取消了虚体不允许操作的规定 by sxhong
			return;
		}
		// 如果是虚体成本中心，则不能增、删、改
		if (!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionAttachment.setEnabled(false);
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionCopy.setEnabled(false);
			actionCopy.setVisible(false);
		}
	}

	/**
	 *
	 * 描述：避免在新增单据（未作修改）直接关闭时，出现保存提示
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected void handleOldData() {
		this.storeFields();
		this.initOldData(this.editData);
	}

	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}

	
	//监听器
	protected Map listenersMap = new HashMap();
    protected abstract void attachListeners();
    protected abstract void detachListeners();
    
    protected void addDataChangeListener(JComponent com) {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    
	public void loadFields() {
		//注销监听器
		detachListeners();
		
		super.loadFields();

		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		
		//最后加上监听器
		attachListeners();
	}

	protected void setSaveActionStatus() {
		if (getTenBillBaseInfo().getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

	//子类可以重载
	protected  void fetchInitParam() throws Exception{

		if(company==null){
			return ;
		}
		//启用成本财务一体化
		HashMap paramItem = FDCUtils.getDefaultFDCParam(null,company.getId().toString());
		if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}				
	}

	//子类可以重载
	protected  void fetchInitData() throws Exception{
		//合同Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if(contractBillId==null){
			Object object  = getUIContext().get("ID");
			if(object instanceof String){
				contractBillId = (String)object;
			}else if(object!=null){
				contractBillId = object.toString();
			}
		}
		//工程项目Id
		BOSUuid projectId = ((BOSUuid) getUIContext().get("projectId"));
		
		Map param = new HashMap();
		param.put("contractBillId",contractBillId);
		if(projectId!=null){
			param.put("projectId",projectId.toString());
		}
		Map initData = ((ITenBillBase)getBizInterface()).fetchInitData(param);

		//本位币
		baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		//本位币
		company = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		if(company==null){
			company =  SysContext.getSysContext().getCurrentFIUnit();
		}
		//
		orgUnitInfo = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		if(orgUnitInfo==null){
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		
		//合同单据
		contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
		
		//当前日期
		bookedDate = (Date)initData.get(FDCConstants.FDC_INIT_DATE);
		if(bookedDate==null){
//			bookedDate = new Date();
			bookedDate = new Date(FDCDateHelper.getServerTimeStamp().getTime());
		}		
		//当前期间
		curPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_PERIOD);
		//是否已经冻结
		if(initData.get(FDCConstants.FDC_INIT_ISFREEZE)!=null){
			isFreeze = ((Boolean)initData.get(FDCConstants.FDC_INIT_ISFREEZE)).booleanValue();
		}
		//可录入期间
		if(isFreeze){
			canBookedPeriod = (PeriodInfo) initData.get(FDCConstants.FDC_INIT_BOOKEDPERIOD);
		}else{
			canBookedPeriod = curPeriod;
		}		

		//工程项目
		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
	}

	public void onLoad() throws Exception {

		//获取初始化界面数据
		fetchInitData();
		
		//获取参数
		fetchInitParam();
		
		super.onLoad();
		if(isUseMainMenuAsTitle()){
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		handleCodingRule();

		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		btnSubmit.setToolTipText(FDCClientUtils.getRes("submit"));

		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.actionRemove.setVisible(false);
		
		disablePrintFunc();
		updateButtonStatus();

		// 设置所有金额控件KDFormattedTextField，BasicNumberTextField的最大最小值，精度，右对齐
		FDCHelper.setComponentPrecision(this.getComponents(), TenBillBaseEditUI.PRECSION);

		//
		setNumberTextEnabled();

		//启用财务成本一体化
		enablePeriodComponent(this,isIncorporation);

//		handleOldData();
		AmusementUI.addHideMenuItem(this, this.menuBiz);
		
		setAuditButtonStatus(this.getOprtState());
		
		this.actionCopy.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	
    	this.actionCreateFrom.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	
	}

	//获取期间
	public void fetchPeriod(DataChangeEvent e,KDDatePicker pkbookedDate,KDBizPromptBox cbPeriod,String projectId, boolean isCost) throws Exception {
		
		if(!getOprtState().equals(OprtState.ADDNEW)&& !getOprtState().equals(OprtState.EDIT)){
			return ;
		}
		
    	if(bookedDate==null){
    		return ;
    	}
    	
		Date bookedDate  = (Date)pkbookedDate.getValue();
		PeriodInfo period = null;
		if(isIncorporation){    	
	        if (canBookedPeriod!=null && bookedDate.before(canBookedPeriod.getBeginDate())) {
	        	MsgBox.showConfirm2(this,"所选日期不能在工程当前期间之前");
	        	pkbookedDate.setValue(e.getOldValue());
	        	this.abort();
	        }
	        
	        if(isCost){
	        	period = FDCUtils.fetchCostPeriod(projectId,bookedDate);
	        }else{
	        	period = FDCUtils.fetchFinacialPeriod(projectId,bookedDate);
	        }
	        
	        if (period!=null && bookedDate.before(period.getBeginDate())) {
	        	MsgBox.showConfirm2(this,"所选日期不能在工程当前期间之前");
	        	
	        	if(((Date)e.getOldValue()).before(period.getBeginDate())){
	        		pkbookedDate.setValue(period.getBeginDate());
	        	}else{
	        		pkbookedDate.setValue(e.getOldValue());
	        	}
	        	return ;
	        	//this.abort();
	        }
    	}else{
    		period = PeriodUtils.getPeriodInfo(null ,bookedDate ,new ObjectUuidPK(company.getId().toString()));
    		if(period==null){
	        	MsgBox.showConfirm2(this,"没有对应的期间。请先在基础资料维护期间");
	        	pkbookedDate.setValue(e.getOldValue());
	        	this.abort();
    		}
    	}
    	
    	cbPeriod.setValue(period);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);

		handleCodingRule();
	}

	protected void enablePeriodComponent(Container aa,boolean isIncorporation){
		Component[] cc = aa.getComponents();

		Component c;
		for (int i = 0, n = cc.length; i < n; i++) {
			c = cc[i];

			if (c instanceof Container) {
				enablePeriodComponent((Container)c,isIncorporation);
			}

			if (c instanceof KDDatePicker) {
				//业务日期客户可以自行修改
//				KDDatePicker obj = (KDDatePicker)c;
//				if("pkbookedDate".equals(obj.getName())){
//					obj.setEnabled(isIncorporation);
//				}
			}else if (c instanceof KDBizPromptBox) {
				KDBizPromptBox obj = (KDBizPromptBox)c;
				if("cbPeriod".equals(obj.getName())){
					obj.setEnabled(false);
				}
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

		/*
		 * 2008-09-27编码规则的取值取错了，应当取FDCBillInfo中关联的ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		if(((TenBillBaseInfo)editData).getOrgUnit() != null){
			currentOrgId = ((TenBillBaseInfo)editData).getOrgUnit().getId().toString();
		}else{
			currentOrgId = SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回
		 * 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRule只提供了获取编码的功能，没有提供设置到控件的功能，实现此方法将根据编码规则的"是否新增显示"属性设置编码到控件
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}

	protected void setNumberTextEnabled() {

		if(getNumberCtrl() != null) {
			SaleOrgUnitInfo currentSaleUnit = SysContext.getSysContext().getCurrentSaleUnit();

			if(currentSaleUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(getTenBillBaseInfo(), currentSaleUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	/**
	 *
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	abstract protected KDTextField getNumberCtrl();

	/**
	 * 子类重载此方法，负责清空一些不能重复的域的值
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		TenBillBaseInfo info = (TenBillBaseInfo) newData;
		info.setNumber(null);
		info.setName(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setCreateTime(null);
		info.setCreator(null);
		info.setLastUpdateTime(null);
		info.setLastUpdateUser(null);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
	}

	protected void updateButtonStatus() {

		super.updateButtonStatus();
		actionUnAudit.setEnabled(true);
		handleActionStatusByCurOrg();

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		//保存不进行任何校验
		if(isSaveAction()) {
			verifyInputForSave();
			return;
		}

		verifyInputForSubmint();
		
		super.verifyInput(e);

	}

	/**
	 * 保存的时候需要校验的内容,
	 *  一般是必须录入的,如果不录入会出现异常的字段,如工程项目,如果不录入,则单据无法在序时簿显示
	 *
	 */
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}

	}
	
	protected void verifyInputForSubmint() throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
	}

	protected void initWorkButton() {

		super.initWorkButton();

		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);

		btnCalculator.setIcon(FDCClientHelper.ICON_CALCULATOR);
		menuItemCalculator.setIcon(FDCClientHelper.ICON_CALCULATOR);

		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');

		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
	}

	public boolean isSaveAction() {
		return isSaveAction;
	}

	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}

	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		//super.actionCalculator_actionPerformed(e);
/*		//这种处理方式会与表格的树型目录冲突,故去掉这个易用功能 sxhong
		if(getCalcValue() == null) return;

		boolean hasSet = setValueForFocusComp(getComponents(), getCalcValue());

		if(!hasSet && getDetailTable() != null) {
			int c = getDetailTable().getSelectManager().getActiveColumnIndex();
	    	int r = getDetailTable().getSelectManager().getActiveRowIndex();

	    	ICell cell = getDetailTable().getCell(r, c);
	    	if(cell!=null&&!cell.getStyleAttributes().isLocked()){
				cell.setValue(getCalcValue());
			}
		}
*/

    	CalculatorDialog calc = new CalculatorDialog(this);
        calc.showDialog(2, true);
	}

	/**
	 * 为当前焦点控件设置value
	 * @param components
	 * @param value
	 * @return
	 */
	private boolean setValueForFocusComp(Component[] components, BigDecimal value) {
		boolean found = false;
		if(found) return found;
		for (int i = 0; i < components.length; i++) {
			Component comp = components[i];
			if(comp instanceof KDLabelContainer) {
				JComponent boundEditor = ((KDLabelContainer)components[i]).getBoundEditor();
				if(boundEditor == null) continue;

				if(boundEditor instanceof KDFormattedTextField) {
					if(boundEditor.isFocusOwner()) {	//不知道为啥不是焦点
						((KDFormattedTextField)boundEditor).setValue(value);
						found = true;
					}
				}
			}
			else if(comp instanceof KDTabbedPane || comp instanceof KDPanel) {
				setValueForFocusComp(((JComponent)comp).getComponents(), value);
			}
		}

		return found;
	}

	/**
     *
     * 描述：检查是否有关联对象
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected void checkRef(String id) throws Exception {

    }

    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionCopy_actionPerformed(e);
    	handleCodingRule();
    }

    public void setOprtState(String oprtType) {
    	// TODO Auto-generated method stub
    	super.setOprtState(oprtType);

//    	FDCClientHelper.setCtrlEnabledByState(this.getComponents(), oprtType);

    	setAuditButtonStatus(oprtType);

    	refreshUITitle();
    }
    
    protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		TenBillBaseInfo bill = (TenBillBaseInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    }
    /**
     * 用菜单名做为UI的页签名,子类可重载
     * @return 默认返回true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
}