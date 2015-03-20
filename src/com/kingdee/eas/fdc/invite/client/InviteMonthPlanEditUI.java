/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditDataProvider;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.invite.DirectAccepterResultCollection;
import com.kingdee.eas.fdc.invite.DirectAccepterResultFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteFormInfo;
import com.kingdee.eas.fdc.invite.InviteMonthPlanCollection;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysInfo;
import com.kingdee.eas.fdc.invite.InviteMonthPlanFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultCollection;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.merch.FDCStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InviteMonthPlanEditUI extends AbstractInviteMonthPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteMonthPlanEditUI.class);
    protected final static String LONGNUMBER = "longNumber";// 长编码
	protected final static String ACTPURCHASEMODE = "actPurchaseMode";// 实际采购模式
	protected final static String ACTINVITEFORM = "actInviteForm";// 实际采购方式
	protected final static String ACTDOCUMENTSAUDITDATE = "actDocumentsAuditDate";// 实际招标文件审批日期
	public static final String ACTRESULTAUDITDATE = "actResultAuditDate"; //实际中标审批日期
	public static final String ACTCONTRACTAUDITDATE = "actContractAuditDate"; //实际合同审批日期
    
    public InviteMonthPlanEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
		addDataChangeListener(this.prmtProgramming);
		addDataChangeListener(this.prmtProject);
	}
    
	protected void detachListeners() {
		removeDataChangeListener(this.prmtProgramming);
		removeDataChangeListener(this.prmtProject);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteMonthPlanFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	/*private void checkCanSave() throws EASBizException, BOSException {
//		try {
			if(beforeCreateOrSaveNewData( this.spYear.getIntegerVlaue().intValue(), spMonth.getIntegerVlaue().intValue())) {
				
			}else {
				FDCMsgBox.showWarning(this, "项目中存在未审批的历史月度采购招标计划，请先审批");
				SysUtil.abort();
			}
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
	}*/
	
	boolean checkHistoryData = true;	//是否检查历史数据
	
	/**
	 * 若工程项目下已存在单据，不可新建
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void checkCreateNewData() throws EASBizException, BOSException {
		if(this.getUIContext().get("set") !=null ) {//修订
			return;
		}
		Object projectObj = null;
		if(this.prmtProject.getValue() != null ) {
			projectObj = this.prmtProject.getValue();
		} else {
			projectObj  = this.getUIContext().get("curProject");
		}
		CurProjectInfo project = null;
		
		if(projectObj !=null && projectObj instanceof CurProjectInfo) {
			project = (CurProjectInfo) projectObj;
		}else {
			MsgBox.showWarning("请选择明细节点");
			SysUtil.abort();
		}
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
		/*if(this.editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
		}*/
		
		if(InviteMonthPlanFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this, "项目中已存在采购计划，请对最新版进行修订");
			SysUtil.abort();
		}
	}
	
	/**
	 * 检查该项目下是否存在未审批的历史单据。
	 * @param year
	 * @param month
	 * @throws EASBizException
	 * @throws BOSException
	 */
	/*private boolean beforeCreateOrSaveNewData(Integer year, Integer month ) throws EASBizException, BOSException {
		if(!checkHistoryData) {
			return true;
		}
		
		Object projectObj = null;
		if(this.prmtProject.getValue() != null ) {
			projectObj = this.prmtProject.getValue();
		} else {
			projectObj  = this.getUIContext().get("curProject");
		}
		CurProjectInfo project = null;
		
		if(projectObj !=null && projectObj instanceof CurProjectInfo) {
			project = (CurProjectInfo) projectObj;
		}else {
			MsgBox.showWarning("请选择明细节点");
			SysUtil.abort();
		}
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("planYear",year,CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("planMonth",month,CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		if(this.editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
		}
		
		if(InviteMonthPlanFactory.getRemoteInstance().exists(filter)){
			return false;
		}
		
		return true;
	
	}*/
	
	/*protected InviteMonthPlanInfo getLastInfo() throws BOSException {
		CurProjectInfo project = (CurProjectInfo) this.prmtProject.getValue();
	
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("planYear",spYear.getIntegerVlaue(),CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("planMonth",spMonth.getIntegerVlaue(),CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo yearSorter = new SorterItemInfo("planYear");
		yearSorter.setSortType(SortType.DESCEND);
		SorterItemInfo monthSorter = new SorterItemInfo("planMonth");
		monthSorter.setSortType(SortType.DESCEND);
		SorterItemInfo versionSorter = new SorterItemInfo("version");
		versionSorter.setSortType(SortType.DESCEND);
		sorter.add(yearSorter);
		sorter.add(monthSorter);
		sorter.add(versionSorter);
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("*");
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("programming.*");
    	sel.add("measureStage.*");
    	sel.add("creator.*");
    	sel.add("auditor.*");
    	sel.add("project.*");
    	sel.add("entry.*");
    	sel.add("entry.requiredInviteForm.*");
		
    	view.setSelector(sel);
		view.setSorter(sorter);
		view.setFilter(filter);
		view.setTopCount(1);
		
		InviteMonthPlanCollection resultCol = InviteMonthPlanFactory.getRemoteInstance().getInviteMonthPlanCollection(view);
		if(resultCol!= null && resultCol.size()>0) {
			return resultCol.get(0);
		}
		return null;
	}*/
	
	
	protected IObjectValue createNewData() {
		try {
			this.checkCreateNewData();
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		
		
		InviteMonthPlanInfo info=(InviteMonthPlanInfo)this.getUIContext().get("info");
		
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		if(info==null){//
			info= new InviteMonthPlanInfo();
			info.setVersion(new BigDecimal("1.0"));
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(now);
//			info.setPlanMonth(cal.get(Calendar.MONTH)+1);
//			info.setPlanYear(cal.get(Calendar.YEAR));
		
		}else{//修订
			info.setState(FDCBillStateEnum.SAVED );
			info.setDescription(info.getId().toString());
			info.setVersion(info.getVersion().add(new BigDecimal(1)));
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				info.getEntry().get(i).setId(null);
			}
		}
		info.setBizDate(now);
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		Object project = this.getUIContext().get("curProject");
		if(project !=null && project instanceof CurProjectInfo ) {
			info.setProject((CurProjectInfo) project);
		}
		
		
		return info;
	}
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		initControl();
		
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
	}
	
	private int paperDateIndex = -1;
		
	protected void initTable(){
		this.kdtEntry.checkParsed();
		this.kdtEntry.getStyleAttributes().setLocked(true);
		KDFormattedTextField level = new KDFormattedTextField();
		level.setDataType(KDFormattedTextField.INTEGER);
		level.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		KDTDefaultCellEditor levelEditor = new KDTDefaultCellEditor(level);
		this.kdtEntry.getColumn("level").setEditor(levelEditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("indicator").setEditor(amountEditor);
		this.kdtEntry.getColumn("indicator").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("indicator").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("indicator").getStyleAttributes().setLocked(true);
	 	
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		String formatString = "yyyy-MM-dd";
		this.kdtEntry.getColumn("paperDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("paperDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("paperDate").setEditor(dateEditor);
		
		
		this.kdtEntry.getColumn("documentsAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("documentsAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("documentsAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("resultAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("resultAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("resultAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("contractAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("contractAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("contractAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("enterAuditDate").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("enterAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("enterAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("actDocumentsAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("actDocumentsAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("actResultAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("actResultAuditDate").setEditor(dateEditor);
		
		this.kdtEntry.getColumn("actContractAuditDate").getStyleAttributes().setNumberFormat(formatString);
		this.kdtEntry.getColumn("actContractAuditDate").setEditor(dateEditor);
		
		KDBizPromptBox inviteFormPromptBox = new KDBizPromptBox();
		inviteFormPromptBox.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteFormQuery");
		KDTDefaultCellEditor inviteFormEditor = new KDTDefaultCellEditor(inviteFormPromptBox);
		inviteFormPromptBox.setCommitFormat("$number$");
		inviteFormPromptBox.setEditFormat("$name$");
		inviteFormPromptBox.setDisplayFormat("$name$");
		this.kdtEntry.getColumn("actInviteForm").setEditor(inviteFormEditor);
		this.kdtEntry.getColumn("requiredInviteForm").setEditor(inviteFormEditor);
		this.kdtEntry.getColumn("requiredInviteForm").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("reason").getStyleAttributes().setLocked(false);
		
		this.kdtEntry.getColumn("longNumber").setSortable(false);
		paperDateIndex = kdtEntry.getColumnIndex("paperDate");
	}
	
	public void storeFields()
    {
        super.storeFields();
    }
	
	protected void verifyInput(ActionEvent e) throws Exception {
//		checkHasPlan();
//		checkCanSave();
		super.verifyInput(e);
		
	}
	
	HashMap<String, Date> actContractDateMap = new HashMap<String, Date>();
	HashMap<String, Date> actDocDateMap = new HashMap<String, Date>();
	HashMap<String, Date> actAccepterDateMap = new HashMap<String, Date>();
	HashMap<String, InviteFormInfo> actInviteFormMap = new HashMap<String, InviteFormInfo>();
	HashMap<String, InvitePurchaseModeInfo> actPurchaseModeMap = new HashMap<String, InvitePurchaseModeInfo>();
	boolean firstLoad = true;
	
	public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		HashMap<String,String> pc2ipMap = new HashMap<String,String>();  //programmingContractID---InviteProjectID
		leafMap.clear();
		this.checkLeaf(leafMap);
		
		for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
			boolean checkRequired = true;
			
			IRow row=this.kdtEntry.getRow(i);
			InviteMonthPlanEntrysInfo entry=(InviteMonthPlanEntrysInfo) row.getUserObject();
			if(entry.getProgrammingContractID()!=null){
				String pcId = entry.getProgrammingContractID().toString();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo isExist=new FilterInfo();
				isExist.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcId));
	            isExist.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
	            view.getSelector().add("auditTime");
	            view.setFilter(isExist);
	        	try {
	        		boolean leaf = true;
	        		FilterInfo filter2 = new FilterInfo();
	            	filter2.getFilterItems().add(new FilterItemInfo("parent.id",pcId));
	                /*if(ProgrammingContractFactory.getRemoteInstance().exists(filter2)){
	                	leaf=false;
	                }*/
	                if(leafMap.get(pcId)!=null && !leafMap.get(pcId).booleanValue() ) {//从界面上判断，因合约规划修订后，原节点可能不再为叶子节点
	                	leaf=false;
	                }
	        		
//	                if(leaf) {
//	                	row.getCell("indicator").getStyleAttributes().setLocked(false);
//	                }else {
//	                	row.getStyleAttributes().setLocked(true);
//	                	row.getStyleAttributes().setBackground(new Color(245,245,245));
//	                }
	                
                	EntityViewInfo view3=new EntityViewInfo();
                	FilterInfo filterInfo3=new FilterInfo();
                	filterInfo3.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcId));
                	filterInfo3.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
                	SelectorItemCollection sic = new SelectorItemCollection();
                	sic.add("programmingContract");
                	sic.add("parent.inviteForm.*");
                	sic.add("parent.invitePurchaseMode.*");
                	sic.add("parent.state");
                	sic.add("parent.id");
                	view3.setFilter(filterInfo3);
                	view3.setSelector(sic);
	                	
                	InviteProjectEntriesCollection entryColl = InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection(view3);
                	if(entryColl!=null && entryColl.size()>0) {//有实际采购方式和采购模式
                		InviteFormInfo  form = entryColl.get(0).getParent().getInviteForm();
                		InvitePurchaseModeInfo mode =  entryColl.get(0).getParent().getInvitePurchaseMode();
                		if(leaf) {
                			row.getCell("actInviteForm").setValue(form);
                			row.getCell("actPurchaseMode").setValue(mode);
                		}else {
                			this.setChildValue(row, form, 1);
                			this.setChildValue(row, mode, 5);
                		}
                		if(firstLoad) {
                			actInviteFormMap.put(pcId, form);
                			actPurchaseModeMap.put(pcId, mode);
                		}
                		for(int j=0; j<entryColl.size(); j++   ) {
                			pc2ipMap.put(entry.getProgrammingContractID().toString(), entryColl.get(j).getParent().getId().toString());
                		}
                	}
	                
	        		ContractBillCollection contracts=ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
					if(contracts.size()>0 || !leaf){//有合同审批日期 或为叶子节点
						row.getStyleAttributes().setBackground(new Color(245,245,245));
						
						if(contracts.size()>0) {
							if(leaf) {
								row.getCell("actContractAuditDate").setValue(contracts.get(0).getAuditTime());
							}else {
								this.setChildValue(row, contracts.get(0).getAuditTime(), 4);
							}
							
							if(firstLoad) {
								actContractDateMap.put(entry.getProgrammingContractID().toString(), contracts.get(0).getAuditTime());
							}
						}
						checkRequired=false;
					}
					
					view=new EntityViewInfo();
					isExist=new FilterInfo();
					isExist.getFilterItems().add(new FilterItemInfo("inviteProject.id",pc2ipMap.get(entry.getProgrammingContractID().toString())) );
//					isExist.getFilterItems().add(new FilterItemInfo("inviteProject.programmingContract.id",entry.getProgrammingContractID().toString()));
		            isExist.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		            view.setFilter(isExist);
		            
		            sic = new SelectorItemCollection();
                	sic.add("inviteProject.id");
                	sic.add("auditTime");
                	sic.add("state");
                	view.setSelector(sic);
                	
                	SorterItemCollection sorter = new SorterItemCollection();
                	SorterItemInfo sorterInfo = new SorterItemInfo("auditTime");
                	sorterInfo.setSortType(SortType.DESCEND);
                	view.setSorter(sorter);
		            
		            InviteDocumentsCollection documtns=InviteDocumentsFactory.getRemoteInstance().getInviteDocumentsCollection(view);
		            if(documtns.size()>0 ){//有招标文件审批日期
		            	Date date = documtns.get(0).getAuditTime();
		            	if(leaf) {
		            		row.getCell("actDocumentsAuditDate").setValue(date);
		            	}else {//因为没写入此实体表里，每次找到的还是父节点的信息，所以要写到下级子节点
		            		this.setChildValue(row, date, 2);
		            	}
		            	
//		            	row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
//		            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
		            	   
		            	if(firstLoad) {
							actDocDateMap.put(pcId, documtns.get(0).getAuditTime());
						}
		            }
		            
		            TenderAccepterResultCollection results=TenderAccepterResultFactory.getRemoteInstance().getTenderAccepterResultCollection(view);
		            if(results.size()>0){//有中标审批日期
		            	if(leaf) {
		            		row.getCell("actResultAuditDate").setValue(results.get(0).getAuditTime());
		            	}else {
		            		this.setChildValue(row, results.get(0).getAuditTime(), 3);
		            	}
		            	
//		            	row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
//		            	row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
//		            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
		            	
		            	if(firstLoad) {
							actAccepterDateMap.put(entry.getProgrammingContractID().toString(), results.get(0).getAuditTime());
						}
		            }
		            DirectAccepterResultCollection resultsTwo=DirectAccepterResultFactory.getRemoteInstance().getDirectAccepterResultCollection(view);
		            if(resultsTwo.size()>0){//有中标审批日期
		            	if(leaf) {
		            		row.getCell("actResultAuditDate").setValue(resultsTwo.get(0).getAuditTime());
		            	}else {
		            		this.setChildValue(row, resultsTwo.get(0).getAuditTime(), 3);
		            	}
		            	
//		            	row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
//		            	row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
//		            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
		            	
		            	if(firstLoad) {
							actAccepterDateMap.put(entry.getProgrammingContractID().toString(), resultsTwo.get(0).getAuditTime());
						}
		            }
//	            	this.setRequiredColor(row);
		            
				} catch (BOSException e) {
					e.printStackTrace();
				}/* catch (EASBizException e) {
					this.handleException(e);
				}*/
			}
		}
		setTableTree();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
//		this.isCheckPlan = true;
		this.firstLoad = false;
		lockCells();
	}
    
	
	private void lockCells() {
		for( int i=0; i<kdtEntry.getRowCount(); i++ ) {
			boolean leaf = true;
			IRow row = kdtEntry.getRow(i);
			InviteMonthPlanEntrysInfo entry=(InviteMonthPlanEntrysInfo) row.getUserObject();
			if(entry.getProgrammingContractID()!=null){
				String pcId = entry.getProgrammingContractID().toString();
				if(leafMap.get(pcId)!=null && !leafMap.get(pcId).booleanValue() ) {
					leaf = false;
				}
			}
			
			if(leaf && row.getCell("actInviteForm").getValue()!=null ) {
				row.getCell("requiredInviteForm").getStyleAttributes().setLocked(true);
				row.getCell("requiredInviteForm").getStyleAttributes().setBackground(new Color(245,245,245));
			}
			
			if(row.getCell("actContractAuditDate").getValue()!=null || !leaf) {//有合同审批日期
				if(!leaf){
//					row.getCell("indicator").getStyleAttributes().setLocked(true);
					row.getCell("requiredInviteForm").getStyleAttributes().setLocked(true);
					row.getCell("paperDate").getStyleAttributes().setLocked(true);
					row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
					row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
					row.getCell("contractAuditDate").getStyleAttributes().setLocked(true);
					row.getCell("enterAuditDate").getStyleAttributes().setLocked(true);
				}
				
//				row.getCell("indicator").getStyleAttributes().setBackground(new Color(245,245,245));
				row.getCell("requiredInviteForm").getStyleAttributes().setBackground(new Color(245,245,245));
				row.getCell("paperDate").getStyleAttributes().setBackground(new Color(245,245,245));
				row.getCell("documentsAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
				row.getCell("resultAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
				row.getCell("contractAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
				row.getCell("enterAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
			}else if(row.getCell("actResultAuditDate").getValue()!=null) {
				row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
            	row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
            	
            	row.getCell("resultAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
            	row.getCell("documentsAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
            	row.getCell("paperDate").getStyleAttributes().setBackground(new Color(245,245,245));
			}else if(row.getCell("actDocumentsAuditDate").getValue()!=null) {
				row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
            	
            	row.getCell("documentsAuditDate").getStyleAttributes().setBackground(new Color(245,245,245));
            	row.getCell("paperDate").getStyleAttributes().setBackground(new Color(245,245,245));
			}
			
			try {
				this.setRequiredColor(row);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 把实际日期更新到叶子节点。
	 * 实际日期并非记录到单据表里~而是查出来的。。。。不想改实体了。。。。
	 * @param row 
	 * @param value
	 * @param type 
	 */
	private void setChildValue(IRow row, Object value, int type) {
		int index = row.getRowIndex();
		String longNumber = (String) row.getCell("longNumber").getValue();
		
		for( int j=index+1; j<kdtEntry.getRowCount(); j++ ) {
			String childLongNumber = (String) kdtEntry.getCell(j, "longNumber").getValue();
			if( !childLongNumber.startsWith(longNumber) ) {
				return;
			}
			InviteMonthPlanEntrysInfo entry=(InviteMonthPlanEntrysInfo) kdtEntry.getRow(j).getUserObject();
			if(entry.getProgrammingContractID()!=null){
				String pcId = entry.getProgrammingContractID().toString();
				if(leafMap.get(pcId)!=null && leafMap.get(pcId).booleanValue()) {
					if(type==1) {//实际采购方式
						kdtEntry.getRow(j).getCell("actInviteForm").setValue(value);
					}else if(type==2) {//招标文件审批日期
						kdtEntry.getRow(j).getCell("actDocumentsAuditDate").setValue(value);
					}else if(type==3) {//中标审批日期
						kdtEntry.getRow(j).getCell("actResultAuditDate").setValue(value);
					}else if(type==4) {//合同审批日期
						kdtEntry.getRow(j).getCell("actContractAuditDate").setValue(value);
					}else if(type==5) {//实际采购模式
						kdtEntry.getRow(j).getCell("actPurchaseMode").setValue(value);
					}
				}
			}
		}
	}
	
	/**
	 * 检查表格必填项与计划日期选值
	 */
    private void checkRequired() {
    	Color required = FDCClientHelper.KDTABLE_COMMON_BG_COLOR;
    	for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
    		IRow row = kdtEntry.getRow(i);
    		Color color1 = row.getCell("indicator").getStyleAttributes().getBackground();
    		Color color2 = row.getCell("requiredInviteForm").getStyleAttributes().getBackground();
    		if( (color1.equals(required) && row.getCell("indicator").getValue() == null) || 
    				(color2.equals(required) && row.getCell("requiredInviteForm").getValue() == null) ) {
    			MsgBox.showWarning("第" + (i+1) + "行有必填项未录入");
				SysUtil.abort();
    		}
    		InviteFormInfo inviteForm=(InviteFormInfo) row.getCell("requiredInviteForm").getValue();
    		for(int j=0; j<5; j++ ) {
    			Color color = row.getCell(this.paperDateIndex+j).getStyleAttributes().getBackground();
    			if(color.equals(required) && row.getCell(this.paperDateIndex+j).getValue()==null) {
    				if(!inviteForm.getName().equals("直接委托")){
    					MsgBox.showWarning("第" + (i+1) + "行有必填项未录入");
        				SysUtil.abort();
    				}else {
    					if(j!=1){
    						MsgBox.showWarning("第" + (i+1) + "行有必填项未录入");
            				SysUtil.abort();
    					}
    				}
    			}else if( color.equals(required) ) {//日期可编辑时，检查选值。
    				if( j>1) {//不检查‘图纸及样品’日期
    					Date date1 = (Date) row.getCell(this.paperDateIndex+j-1).getValue();
    					Date date2 = (Date) row.getCell(this.paperDateIndex+j).getValue();
    					if(date1!=null&&date2!=null&&date1.after(date2)) {
    						String name1 = kdtEntry.getHeadRow(1).getCell(this.paperDateIndex+j-1).getValue().toString();
    						String name2 = kdtEntry.getHeadRow(1).getCell(this.paperDateIndex+j).getValue().toString();
    						
    						MsgBox.showWarning("第" + (i+1) + "行，" + name1 + "，" + name2 + "，日期不符合要求");
    	    				SysUtil.abort();
    					}
    				}
    			}
    		}
    	}
    }
    
    private void setRequiredColor(IRow row) throws EASBizException, BOSException {
    	if(this.paperDateIndex>0) {
    		for(int i=0; i<5; i++ ) {
    			if(!row.getCell(this.paperDateIndex+i).getStyleAttributes().isLocked()) {
    				row.getCell(this.paperDateIndex+i).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    			}
    		}
    	}
    	
    	if(!row.getCell("indicator").getStyleAttributes().isLocked()) {
			row.getCell("indicator").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
    	if(!row.getCell("requiredInviteForm").getStyleAttributes().isLocked()) {
			row.getCell("requiredInviteForm").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
    }
    
    
    protected void initControl() {
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.txtVersion.setPrecision(1);
		
	/*	if(this.editData.getDescription()!=null&&!"".equals(this.editData.getDescription())){
			this.prmtProject.setEnabled(false);
			this.spMonth.setEnabled(false);
			this.spYear.setEnabled(false);
		}*/
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtMeasureStage.setEntityViewInfo(view);
		
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",editData.getOrgUnit().getId().toString()));
		view.setFilter(filter);
		this.prmtProject.setEntityViewInfo(view);
		
		setProgrammingFilter();
		
		/*SpinnerNumberModel monthModel = new SpinnerNumberModel(editData.getPlanMonth(),1,12,1);
		this.spMonth.setModel(monthModel);
		
		SpinnerNumberModel yearModel = new SpinnerNumberModel(editData.getPlanYear(),1,9999,1);
		this.spYear.setModel(yearModel);*/
	}
    protected void setProgrammingFilter(){
    	if(this.prmtProject.getValue()!=null){
    		this.prmtProgramming.setEnabled(true);
    		
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)this.prmtProject.getValue()).getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
			
			view.setFilter(filter);
			
			this.prmtProgramming.setEntityViewInfo(view);
    	}else{
    		this.prmtProgramming.setEnabled(false);
    		this.prmtProgramming.setValue(null);
    	}
    }
    
    HashMap<String, Boolean> leafMap = new HashMap<String, Boolean>();
    HashMap<String, Boolean> oldLeafMap = new HashMap<String, Boolean>();
    
    private void checkLeaf(  HashMap<String, Boolean> map ) {
    	for(int i=0; i<kdtEntry.getRowCount(); i++) {
    		String longNumber = (String) kdtEntry.getCell(i, "longNumber").getValue();
    		String pcId = ((InviteMonthPlanEntrysInfo)kdtEntry.getRow(i).getUserObject()).getProgrammingContractID().toString();
    		if( i==kdtEntry.getRowCount()-1 ) {
    			map.put(pcId, Boolean.TRUE);
    		}else {
    			String childNumber = (String) kdtEntry.getCell(i+1, "longNumber").getValue();
    			if( !childNumber.startsWith(longNumber) ) {
    				map.put(pcId, Boolean.TRUE);
    			}else {
    				map.put(pcId, Boolean.FALSE);
    			}
    		}
    		
    	}
    }
    
    private void setPlanValue( Map oldDataMap ) {
    	Iterator iter = oldDataMap.keySet().iterator();
    	while(iter.hasNext() ) {
    		BOSUuid pcUuid = (BOSUuid) iter.next();
    		String pcId = pcUuid.toString();
    		InviteMonthPlanEntrysInfo entry=(InviteMonthPlanEntrysInfo)oldDataMap.get(pcUuid);
    		String entryLongNumber = entry.getLongNumber();
    		
	outer: for(int i=0; i<kdtEntry.getRowCount(); i++) {
    			boolean modified = false;
    			String longNumber = (String) kdtEntry.getCell(i, "longNumber").getValue();
    			if( longNumber.equals(entryLongNumber) ) {
    				if( oldLeafMap.get(pcId)!=null && oldLeafMap.get(pcId).booleanValue() ) { //原本为叶子节点
    					for( int j=i+1; j<kdtEntry.getRowCount(); j++) {
        					String childNumber = (String) kdtEntry.getCell(j, "longNumber").getValue();
        					if( !childNumber.startsWith(longNumber) ) {
        						if(j==i+1) {//还是叶子节点。。
        							break outer;
        						}
        						break;
        					}
        					
        					//变为非叶子节点,修改旗下叶子节点信息。
        					modified = true;
        					String childPCId = ((InviteMonthPlanEntrysInfo)kdtEntry.getRow(j).getUserObject()).getProgrammingContractID().toString();
        					if( leafMap.get(childPCId).booleanValue() ){//
        						IRow row = kdtEntry.getRow(j);
        						row.getCell("requiredInviteForm").setValue(entry.getRequiredInviteForm());
        						
//        						row.getCell("indicator").setValue(entry.getIndicator());
        						row.getCell("paperDate").setValue(entry.getPaperDate());
        						row.getCell("documentsAuditDate").setValue(entry.getDocumentsAuditDate());
        						row.getCell("resultAuditDate").setValue(entry.getResultAuditDate());
        						row.getCell("contractAuditDate").setValue(entry.getContractAuditDate());
        						row.getCell("enterAuditDate").setValue(entry.getEnterAuditDate());
        						
        						//实际日期
        						row.getCell("actDocumentsAuditDate").setValue(this.actDocDateMap.get(pcId));
        						row.getCell("actResultAuditDate").setValue(this.actAccepterDateMap.get(pcId));
        						row.getCell("actContractAuditDate").setValue(this.actContractDateMap.get(pcId));
        						row.getCell("actInviteForm").setValue(this.actInviteFormMap.get(pcId));
        						row.getCell("actPurchaseMode").setValue(this.actPurchaseModeMap.get(pcId));
        						
        						if(row.getCell("actInviteForm").getValue()!=null ) {
        							row.getCell("requiredInviteForm").getStyleAttributes().setLocked(true);
        							row.getCell("requiredInviteForm").getStyleAttributes().setBackground(new Color(245,245,245));
        						}
        						
        						if(actContractDateMap.get(pcId) !=null ) {
//        							row.getCell("indicator").getStyleAttributes().setLocked(true);
        							row.getCell("requiredInviteForm").getStyleAttributes().setLocked(true);
        							row.getCell("paperDate").getStyleAttributes().setLocked(true);
        							row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
        							row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
        							row.getCell("contractAuditDate").getStyleAttributes().setLocked(true);
        							row.getCell("enterAuditDate").getStyleAttributes().setLocked(true);
        						}else if(actAccepterDateMap.get(pcId) != null) {
        			            	row.getCell("resultAuditDate").getStyleAttributes().setLocked(true);
        			            	row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
        			            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
        						}else if(actDocDateMap.get(pcId) !=null ) {
        							row.getCell("documentsAuditDate").getStyleAttributes().setLocked(true);
        			            	row.getCell("paperDate").getStyleAttributes().setLocked(true);
        						}
        						
        					}
        				}
    					
    					if(modified) {//若原子节点变为父节点，设置空值
        					IRow row = kdtEntry.getRow(i);
        					row.getCell("requiredInviteForm").setValue(null);
//        					row.getCell("indicator").setValue(null);
    						row.getCell("paperDate").setValue(null);
    						row.getCell("documentsAuditDate").setValue(null);
    						row.getCell("resultAuditDate").setValue(null);
    						row.getCell("contractAuditDate").setValue(null);
    						row.getCell("enterAuditDate").setValue(null);
        				}
    				}else {//原本非叶子节点
    					if(leafMap.get(pcId)!=null && !leafMap.get(pcId).booleanValue()) {
    						//现在非叶子节点，不处理
    						break outer;
    					}else {
    						//暂不考虑此情况。。。。。
    					}
    				}
    			}
    		}
    	}
    	
    }
    
//    private InviteMonthPlanInfo lastInfo=null;
	protected void prmtProgramming_dataChanged(DataChangeEvent e) throws Exception {
		/* boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }*/
         ProgrammingInfo pro=(ProgrammingInfo)this.prmtProgramming.getValue();
         boolean isShowWarn=false;
         boolean isUpdate=false;
         if(this.kdtEntry.getRowCount()>0||this.kdtEntry.getRowCount()>0){
        	 isShowWarn=true;
         }
         if(isShowWarn){
        	 if(FDCMsgBox.showConfirm2(this, "合约规划改变会覆盖分录数据，是否继续？")== FDCMsgBox.YES){
        		 isUpdate=true;
             }else {
            	 this.removeDataChangeListener(prmtProgramming);
            	 prmtProgramming.setValue(e.getOldValue());
            	 this.addDataChangeListener(prmtProgramming);
             }
         }else{
        	 isUpdate=true;
         }
         if(isUpdate){
        	 Map map=new HashMap();
//        	 if(this.getUIContext().get("set") != null && ((Boolean) this.getUIContext().get("set")).booleanValue() ) {
        		 //修订。。。
        		 for(int i=0;i<this.editData.getEntry().size();i++){
        			 if(this.editData.getEntry().get(i).getProgrammingContractID()!=null){
        				 map.put(this.editData.getEntry().get(i).getProgrammingContractID(), this.editData.getEntry().get(i).clone());
        			 }
        		 }
        		 
        		 
//        		 Set s = map.keySet();
//        		 Iterator it = s.iterator();
//        		 while(it.hasNext()) {
//        			 System.out.println(it.next());
//        		 }
//        		 System.out.println("=======================");
        		 this.checkLeaf(oldLeafMap);
//        	 }
         /*else {
        		 
        		 //新增，需带入上期数据。
        		 lastInfo = this.getLastInfo();
        		 if( lastInfo != null ) {
        			 for(int i=0;i<lastInfo.getEntry().size();i++){
            			 if(lastInfo.getEntry().get(i).getProgrammingContractID()!=null){
            				 InviteMonthPlanEntrysInfo info = (InviteMonthPlanEntrysInfo) lastInfo.getEntry().get(i).clone();
            				 info.setId(null);
            				 map.put(info.getProgrammingContractID(), info);
            			 }
            		 }
        		 }
        	 }*/
        	 this.kdtEntry.removeRows();
        	 this.editData.getEntry().clear();
        	 
        	 if(pro!=null){
//        		 this.checkHistoryData = false;
	        	 this.storeFields();
	        	 
	        	 BigDecimal area=FDCHelper.ZERO;
        		 EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)this.prmtProject.getValue()).getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
				view.setFilter(filter);
				MeasureCostCollection mcCol=MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
				if(mcCol.size()>0){
					view=new EntityViewInfo();
					filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("headID",mcCol.get(0).getId().toString()));
					view.setFilter(filter);
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("totalBuildArea");
					sic.add("totalSellArea");
					view.setSelector(sic);
					PlanIndexCollection col=PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
					if(col.size()>0){
						area=col.get(0).getTotalBuildArea();
					}
				}
					
				
//	        	 this.checkHistoryData = true;
	        	 view=new EntityViewInfo();
	        	 filter = new FilterInfo();
	        	 filter.getFilterItems().add(new FilterItemInfo("programming.id" , pro.getId().toString()));
	        	 filter.getFilterItems().add(new FilterItemInfo("isInvite" , Boolean.TRUE));
	        	 view.setFilter(filter);
	        	 SorterItemCollection sort=new SorterItemCollection();
	        	 SorterItemInfo number=new SorterItemInfo("longNumber");
	        	 number.setSortType(SortType.ASCEND);
	        	 sort.add(number);
	        	 view.setSorter(sort);
	        	 ProgrammingContractCollection col=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view);
//	        	 for(int i=0;i<col.size();i++){
//	        		 System.out.println(col.get(i).getId().toString());
//	        	 }
	        	 
	        	 for(int i=0;i<col.size();i++){
	        		 ProgrammingContractInfo pc=col.get(i);
	        		 InviteMonthPlanEntrysInfo entry=null;
	        		 if(map.get(pc.getId())!=null){
	        			 entry=(InviteMonthPlanEntrysInfo) map.get(pc.getId());
	        		 }else{
	        			 entry=new InviteMonthPlanEntrysInfo();
	        		 }
		        	 entry.setProgrammingContractID(pc.getId());
	            	 entry.setLevel(pc.getLevel());
	            	 entry.setLongNumber(pc.getLongNumber());
	            	 entry.setName(pc.getName());
	            	 entry.setCostAccountNames(pc.getCostAccountNames());
	            	 entry.setWorkContent(pc.getWorkContent());
	            	 entry.setAmount(pc.getAmount());
	            	 if(area!=null&&area.compareTo(FDCHelper.ZERO)!=0){
                		 entry.setIndicator(FDCHelper.divide(pc.getAmount(), area, 2, BigDecimal.ROUND_HALF_UP));
                	 }
	            	 this.editData.getEntry().add(entry);
	        	 }
	        	 this.loadFields();
//	        	 this.checkLeaf(leafMap);
	        	/* if(lastInfo != null) {
	        		 this.oldLeafMap = (HashMap<String, Boolean>) InviteMonthPlanFactory.getRemoteInstance().checkLeaf(lastInfo.getId());
	        	 }*/
	        	 this.setPlanValue(map);
        	 } 
         }
	}
	protected void setTableTree(){
		int maxLevel = 0;
 		int[] levelArray = new int[this.kdtEntry.getRowCount()];
 		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
 			IRow row = this.kdtEntry.getRow(i);
 			int level = Integer.parseInt(row.getCell("level").getValue().toString());
 			levelArray[i] = level;
 			row.setTreeLevel(level - 1);
 		}
 		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
 			maxLevel = Math.max(levelArray[i], maxLevel);
 		}
 		this.kdtEntry.getTreeColumn().setDepth(maxLevel);
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("description");
    	sel.add("entry.programmingContractID");
		return sel;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		setProgrammingFilter();
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtMeasureStage);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProgramming);
	}
	
	protected void verifyInputForSubmint() throws Exception {
		this.checkRequired();
		verifyInputForSave();
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	
	/*protected void checkHasPlan() throws EASBizException, BOSException{
		if(this.prmtProject.getValue()!=null){
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id",((CurProjectInfo)this.prmtProject.getValue()).getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("planYear",this.spYear.getIntegerVlaue(),CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("planMonth",this.spMonth.getIntegerVlaue(),CompareType.GREATER));
			
			if(this.editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
			}
			
			if(this.getUIContext().get("set") == null) {
				if(InviteMonthPlanFactory.getRemoteInstance().exists(filter)){
					FDCMsgBox.showWarning(this, "历史月份不可做月度采购计划或该项目已存在月度采购招标计划，请进行修订或修改月份！");
//				this.prmtProject.setValue(null);
					SysUtil.abort();
					return;
				}
			}
		}
	}*/
	
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
	
	}
	
	/**
	 * 保存表格实际反写数据--打印用
	 * @return
	 */
	private HashMap getTableDataMap() {
		HashMap<String,Map<String,Object>> map = new HashMap<String, Map<String,Object>>();
		for( int i=0; i<kdtEntry.getRowCount(); i++ ) {
			HashMap<String,Object> rowMap = new HashMap<String, Object>();
			IRow row = kdtEntry.getRow(i);
			rowMap.put(InviteMonthPlanEditUI.ACTCONTRACTAUDITDATE, row.getCell(InviteMonthPlanEditUI.ACTCONTRACTAUDITDATE).getValue());
			rowMap.put(InviteMonthPlanEditUI.ACTDOCUMENTSAUDITDATE, row.getCell(InviteMonthPlanEditUI.ACTDOCUMENTSAUDITDATE).getValue());
			rowMap.put(InviteMonthPlanEditUI.ACTRESULTAUDITDATE, row.getCell(InviteMonthPlanEditUI.ACTRESULTAUDITDATE).getValue());
			
			InviteFormInfo inviteForm = (InviteFormInfo) row.getCell(InviteMonthPlanEditUI.ACTINVITEFORM).getValue();
			if(inviteForm !=null ) {
				rowMap.put(InviteMonthPlanEditUI.ACTINVITEFORM, inviteForm.getName());
			}
			
			InvitePurchaseModeInfo mode = (InvitePurchaseModeInfo) row.getCell(InviteMonthPlanEditUI.ACTPURCHASEMODE).getValue();
			if(mode != null ) {
				rowMap.put(InviteMonthPlanEditUI.ACTPURCHASEMODE, mode.getName());
			}
			
			String longNumber = (String) row.getCell(InviteMonthPlanEditUI.LONGNUMBER).getValue();
			map.put(longNumber, rowMap);
		}
		
		return map;
	}
	
	protected String getTDFileName() {
		return "/bim/fdc/invite/InviteMonthPlan";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.InviteMonthPlanPrintQuery");
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		InviteMonthPlanPrintDataProvider dataPvd = new InviteMonthPlanPrintDataProvider(
				editData.getString("id"), getTDQueryPK(),getTableDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		InviteMonthPlanPrintDataProvider dataPvd = new InviteMonthPlanPrintDataProvider(
				editData.getString("id"), getTDQueryPK(),getTableDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	
	/*protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		if(this.isCheckPlan) {
			checkHasPlan();
		}
	}
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		if(this.isCheckPlan) {
			checkHasPlan();
		}
	}*/
	
}