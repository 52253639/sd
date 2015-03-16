/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenBillBaseListUI extends AbstractTenBillBaseListUI
{
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	/*----------------------------------------------------以下为FDCBill复制过来的，待修改------------------------------------------------*/
    private static final Logger logger = CoreUIObject.getLogger(TenBillBaseListUI.class);
    
    /**
     * output class constructor
     */
    public TenBillBaseListUI() throws Exception
    {
        super();
    }

	public static final String COL_PERIOD= "period";
	public static final String COL_PERIODNUMBER= "period.number";
	public static final String COL_DATE = "bookedDate";
	
	//启用成本月结
	protected boolean isIncorporation = false;
	//启用财务成本一体化
	protected boolean isFinacial = false;
	
    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.getClass().getName().equalsIgnoreCase("com.kingdee.eas.fdc.contract.client.contractBillListUI")) {
    		fdcDataImport();
    	}else{
    		super.actionImportData_actionPerformed(e);
    	}
    }
    
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAttachment_actionPerformed(e);
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
        if(getBillStatePropertyName()!=null){
        	int rowIdx=getMainTable().getSelectManager().getActiveRowIndex();
        	ICell cell = getMainTable().getCell(rowIdx, getBillStatePropertyName());
        	Object obj=cell.getValue();
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
			
        }
        acm.showAttachmentListUIByBoID(boID, this,isEdit); // boID 是 与附件关联的 业务对象的 ID
    }
    
    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();

		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForCancel");
		
		checkBeforeCancel();
		
		int confirm = MsgBox.showConfirm2(this, "确认终止? 该操作不可撤销！");
		if(confirm == MsgBox.OK) {
			cancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
			
			showOprtOKMsgAndRefresh();
		}
    }

    /**
     * 作废
     * @param ids
     * @throws Exception
     */
    protected void cancel(List ids) throws Exception{}
    
    /**
     * 生效
     * @param ids
     * @throws Exception
     */
    protected void cancelCancel(List ids) throws Exception{}

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();

		checkBillState(new String[]{FDCBillStateEnum.CANCEL_VALUE}, "selectRightRowForCancelCancel");

		cancelCancel(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
	
		showOprtOKMsgAndRefresh();
    }
    
    /**
     * 作废前的业务检查
     * @throws Exception
     */
    protected void checkBeforeCancel() throws Exception{
    	
    }
    
    /**
	 * 
	 * 描述：检查单据状态
	 * 
	 * @param states
	 *            状态
	 * @param res
	 *            提示信息资源名称
	 * @throws BOSException
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

		if(idList==null){
			MsgBox.showWarning(this, "请选中行");
			abort();
			return ;
		}
		
		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	  /**
     * 
     * 描述：单据状态属性名称，基类提供缺省实现
     * @return
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected String getBillStatePropertyName() {
    	return "state";
    }
    
    /**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-8-2 <p>
	 */
	protected KDTable getBillListTable() {
		return getMainTable();
	}
	
    protected SelectorItemCollection getStateSelectors() {
    	SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("state");
		
		return selectors;
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
       	checkSelected();
    	String fieldName = getQueryFieldNameBindingWf();
        String id = (String)getSelectedFDCFieldValues(fieldName).get(0);
        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo processInstInfo = null;
        ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(id);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if (procInsts[i].getState().startsWith("open")) {
            	processInstInfo = procInsts[i];
            }
        }
        if (processInstInfo == null) {
        	//如果没有运行时流程，判断是否有可以展现的流程图并展现
        	procInsts = service.getAllProcessInstancesByBizobjId(id);
        	if(procInsts== null || procInsts.length <=0)
        		MsgBox.showInfo(this ,EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_WFHasNotInstance"));
        	else if(procInsts.length ==1){
	        	showWorkflowDiagram(procInsts[0]);
        	}else{
        		UIContext uiContext = new UIContext(this);
                 uiContext.put("procInsts",procInsts);
                 String className = ProcessRunningListUI.class.getName();
                 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className,uiContext);
                 uiWindow.show();
        	}
		} else {
			showWorkflowDiagram(processInstInfo);
        }
    }
    
	/**
	 * 所见即所选，不处理虚模式。
	 * @param fieldName
	 * @return
	 */
	public final ArrayList getSelectedFDCFieldValues(String fieldName) {
        ArrayList list = new ArrayList();
        int[] selectRows = KDTableUtil.getSelectedRows( getBillListTable());
        for (int i = 0; i < selectRows.length; i++) {
        	ICell cell =  getBillListTable().getRow(selectRows[i]).getCell(fieldName);
        	if (cell == null) {
        		MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
        		SysUtil.abort();
        	}
        	if (cell.getValue()!=null)
        	{
        		String id = cell.getValue().toString();
            	if (!list.contains(id))
            		list.add(id);
        	}

        }
        return list;
    }
    
    private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(className, uiContext);
		uiWindow.show();
	}

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

	protected String getEditUIName() {
		/* TODO 自动生成方法存根 */
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		/* TODO 自动生成方法存根 */
		return null;
	}
	
	protected  void fetchInitData() throws Exception{		

		try {
			//启用成本财务一体化
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			//财务组织级参数,用当前组织去判断,当前组织为成本中心,上级实体财务组织此参数禁用时,获取的参数是错误的
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
				isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			}
			isFinacial = FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		}catch (Exception e) {
			handUIException(e);
		}
	}
	
	public void onLoad() throws Exception {
		
		//获取初始化界面数据
		fetchInitData();
		
		super.onLoad();
		if(isUseMainMenuAsTitle()){
			FDCClientHelper.setUIMainMenuAsTitle(this);
		}
		//初始化表格
		initTable();
		
		getMainTable().setColumnMoveable(true);
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		this.actionCreateTo.setVisible(false);
    	this.actionCopyTo.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);    	
	}
	
	
	/**
	 * 
	 * 描述：初始化表格
	 * @author:liupd
	 * 创建时间：2006-8-3 <p>
	 */
	protected void initTable() {
		
		getMainTable().setColumnMoveable(true);
		
		//freezeMainTableColumn();
		if(getBillListTable()!=null){
			//freezeBillTableColumn();
			getBillListTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "auditTime");
			}
			if(getBillListTable().getColumn("createDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createDate");
			}
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("signDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "signDate");
			}
			if(getBillListTable().getColumn("entrys.deductDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");
			}
//			if(getBillListTable().getColumn("entrys.deductDate")!=null){
//				FDCHelper.formatTableDate(getBillListTable(), "entrys.deductDate");
//			}
			if(getBillListTable().getColumn("bookedDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "bookedDate");
			}
		}
		//getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		if(getMainTable().getColumn("amount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "amount");
		}
		if(getMainTable().getColumn("originalAmount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		}
//		FDCHelper.formatTableDateTime(getMainTable(), "signDate");
		
		//
		if(!isIncorporation){
//			if(getMainTable().getColumn(COL_DATE)!=null){
//				getMainTable().getColumn(COL_DATE).getStyleAttributes().setHided(true);
//			}
			if(getMainTable().getColumn(COL_PERIOD)!=null){
				getMainTable().getColumn(COL_PERIOD).getStyleAttributes().setHided(true);
			}
			if(getMainTable().getColumn(COL_PERIODNUMBER)!=null){
				getMainTable().getColumn(COL_PERIODNUMBER).getStyleAttributes().setHided(true);
			}			
//			if(getBillListTable().getColumn(COL_DATE)!=null){
//				getBillListTable().getColumn(COL_DATE).getStyleAttributes().setHided(true);
//			}
			if(getBillListTable().getColumn(COL_PERIOD)!=null){
				getBillListTable().getColumn(COL_PERIOD).getStyleAttributes().setHided(true);
			}
			if(getBillListTable().getColumn(COL_PERIODNUMBER)!=null){
				getBillListTable().getColumn(COL_PERIODNUMBER).getStyleAttributes().setHided(true);
			}
		}
		
		//Table填充数据之后，设置原币的精度
		getMainTable().getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						//FDCClientHelper.tblMainAfterDataFill(e,getMainTable(),TenBillBaseListUI.this);//TODO
					}
				}
		);	
	}
	


	/**
	 * 
	 * 描述：提示操作成功
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		btnCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		btnCancelCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
	}
	
	protected void fdcDataImport() throws Exception{
/*        FDCDatataskCaller task = new FDCDatataskCaller();
        task.setParentComponent(this);
        final ArrayList dataImportParam = getFDCDataImportParam();
        if (dataImportParam != null)
        {
            task.invoke(dataImportParam, DatataskMode.ImpMode);
        }*/
	}
    protected ArrayList getFDCDataImportParam()
    {
      DatataskParameter param = new DatataskParameter();
      //        String solutionName = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountTypeImportSolutionPath"); //引入总方案.引入方案-基础资料.全局资料.会计科目类别子目录
//      String solutionName = "引入总方案.引入方案-基础资料.全局资料.会计科目类别";
      String solutionName = "eas.fdc.bill.contractBill";
      param.solutionName = solutionName;
//      param.alias = EASResource.getString(AccountClientUtils.ACCOUNT_RESOURCE, "accountType"); //会计科目类别
      param.alias="合同";
      Hashtable dataTaskCtx = new Hashtable();
      
      param.setContextParam(dataTaskCtx);

      ArrayList paramList = new ArrayList();
      paramList.add(param);

      return paramList;
  
    }
    
    /** 获得选中行的ID,如果没有选中行给出提示,并中断操作,注意Table中是否有id列 */
    protected String getSelectedId(){
    	IRow row = getSelectedRow();
		String id = (String) row.getCell("id").getValue();
		return id;
    }
    
    /** 获得选中行,如果没有选中行给出提示,并中断操作,注意Table中是否有id列 */
    protected IRow getSelectedRow(){
    	this.checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		return row;
    }
    
    /**
     * 用菜单名做为UI的页签名,子类可重载
     * @return 默认返回true
     */
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
}