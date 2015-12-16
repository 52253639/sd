/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCAutoSplitHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.ViewCostInfoUI;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryCollection;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryFactory;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialEntryCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractCostSplitEditUI extends AbstractContractCostSplitEditUI
{
	private boolean isClearFlag = false;
	private boolean checkAllSplit = true;
    private static final Logger logger = CoreUIObject.getLogger(ContractCostSplitEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractCostSplitEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	//处理分录保存顺序
    	/*ContractCostSplitEntryInfo entry=null;    	
        for(int i=0; i<kdtEntrys.getRowCount(); i++){
    		entry = (ContractCostSplitEntryInfo)kdtEntrys.getRow(i).getUserObject();
        	entry.setIndex(i+1);
        }*/
        
    	super.actionSave_actionPerformed(e);
//    	this.btnSubmit.doClick();
    }

    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

    public void actionViewCostInfo_actionPerformed(ActionEvent e)
	throws Exception {
// TODO Auto-generated method stub
    	super.actionViewCostInfo_actionPerformed(e);
    	if(kdtEntrys.getRowCount()==0){
			return;
		}
    	
		int[] selectedRows = KDTableUtil.getSelectedRows(kdtEntrys);
		ContractCostSplitEntryCollection c=new ContractCostSplitEntryCollection();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=kdtEntrys.getRow(selectedRows[i]);
			ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)row.getUserObject();
			c.add(entry);
		}
		boolean isAddThisAmt = false;
    	if(getOprtState().equals(OprtState.ADDNEW) || editData.getState() == null){
    		isAddThisAmt = true;
    	}
		UIContext uiContext = new UIContext(this);
		uiContext.put("entryCollection", c);
		uiContext.put("isAddThis", Boolean.valueOf(isAddThisAmt));
		IUIWindow dlg =  UIFactory.createUIFactory(UIFactoryName.MODEL).create(ViewCostInfoUI.class.getName(), uiContext,null,OprtState.VIEW);
		dlg.show();
}
    
    
	/**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	String costBillID=null;
//    	if(getUIContext().get("costBillID")==null){
        	costBillID=editData.getContractBill().getId().toString();    		
//    	}else{
//        	costBillID = (String)getUIContext().get("costBillID");
//    	}    	
//    	getUIContext().put("costBillID",costBillID);
        checkConInWF();
		// 如果合同拆分删除还要判断是否已经生成过付款申请单
		if (PayReqUtils.isContractBill(costBillID)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractId", costBillID));

			// 该合同已经存在付款申请单，不能进行此操作
			boolean hasPayRequest = PayRequestBillFactory
					.getRemoteInstance().exists(filter);
			if (hasPayRequest) {
				MsgBox.showError(this, FDCSplitClientHelper
						.getRes("hasPayRequest"));
				SysUtil.abort();
			}
		}
		checkBeforeRemove();
        super.actionRemove_actionPerformed(e);
    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
    	checkConInWF();
		super.actionRemoveLine_actionPerformed(e);
	}
    private void checkConInWF() {
    	//是否必须审核前拆分
    	HashMap param;
		try {
			param = FDCUtils.getDefaultFDCParam(null,editData.getContractBill().getOrgUnit().getId().toString());
			boolean splitBeforeAudit = false;
			if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
				splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
			}
			if(splitBeforeAudit && FDCUtils.isRunningWorkflow(editData.getContractBill().getId().toString())
					&& CostSplitStateEnum.ALLSPLIT.equals(editData.getSplitState())){
				MsgBox.showWarning(this,"合同在工作流，不能删除合同拆分！");
				SysUtil.abort();
			}
		} catch (EASBizException e) {
		} catch (BOSException e) {
			e.printStackTrace();
		}		
    }
    
    public void actionProgrAcctSelect_actionPerformed(ActionEvent e)
    		throws Exception {
//    	checkContractWithProgram();
//    	IRow row=null;
//		FDCSplitBillEntryInfo entry=null;
//		
//		for(int i=0; i<kdtEntrys.getRowCount(); i++){
//			row=kdtEntrys.getRow(i);
//			entry=(FDCSplitBillEntryInfo)row.getUserObject();
//			
//			if(entry.getCostBillId()!=null){
//				FDCMsgBox.showInfo(this,"已经引入了合同规划，不能重复引入！");
//				return;
//			}
//		}
//		importContractWithProgram();    	
//		setDisplay();    
ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(getContractBillId()));
    	
    	String checkIsExistProg = ContractCostSplitFactory.getRemoteInstance().checkIsExistProg(this.getContractBillId().toString());
    	checkProgSub(checkIsExistProg);
		BigDecimal allAssigned = FDCHelper.ZERO;
    	if(checkIsExistProg != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("costEntries.*");
			sic.add("costEntries.costAccount.*");
			sic.add("costEntries.costAccount.curProject.*");
			ProgrammingContractInfo programmingContractInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(BOSUuid.read(checkIsExistProg)),sic);
//			programmingContractInfo.getCostEntries().get(key);
//			FDCSplitBillEntryCollection entrys = getEntrys();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
//			for(Iterator it = costEntries.iterator();it.hasNext(); ){
////				pteco programmingContractInfo = (ProgrammingContractInfo)it.next();
//				ProgrammingContracCostInfo programmingContracCostInfo = (ProgrammingContracCostInfo)it.next();
//				programmingContracCostInfo.getCostAccount().getLongNumber();
//				programmingContracCostInfo.getCostAccount().getDisplayName();
//			}
			//if costEntries is null then abort
			if(costEntries.size() == 0){
				FDCMsgBox.showInfo("当前合同对应的框架合约的成本构成为空，原拆分数据不变");
				this.abort();
			}
			getDetailTable().removeRows(false);
	    	IRow row;
	    	ProgrammingContracCostInfo acct=null;
	    	FDCSplitBillEntryInfo entry;
	    	int groupIndex = 0;
	    	for(Iterator iter = costEntries.iterator();iter.hasNext();){
	    		acct=(ProgrammingContracCostInfo)iter.next();
	    		//总的已分配金额
				allAssigned = allAssigned.add((acct.getContractAssign() == null ? FDCHelper.ZERO:acct.getContractAssign()));
	    	}
	    	int tempCount = 0;
	    	BigDecimal tempAll = FDCHelper.ZERO;
	    	BigDecimal splitAmount = FDCHelper.ZERO;
	    	for(Iterator iter = costEntries.iterator();iter.hasNext();){
	    		acct=(ProgrammingContracCostInfo)iter.next();
				entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);
				entry.setCostAccount(acct.getCostAccount());  
				entry.setLevel(0);
				entry.setIsLeaf(true);		
//				entry.setAmount(acct.getContractAssign());
	    		tempCount++;
//	    		if(tempCount < costEntries.size()){
//	    			tempAll = FDCHelper.add(tempAll, acct.getContractAssign());
//	    		}
				//拆分组号	
				groupIndex++;
				entry.setIndex(groupIndex);
				row=addEntry(entry);
//	    		if(entry.getLevel()==0){
//					groupIndex++;				
//				}
//	    		row=getDetailTable().addRow();
//	    		loadLineFields(getDetailTable(), row, entry);
				//以下当前项目的需要等到合约规划的框架合约完成才能测试，现在注释
//	    		row = kdtEntrys.addRow();
	    		row.getCell("costAccount.curProject.number").setValue(
						acct.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
	    		row.getCell("costAccount.number").setValue(
	    				acct.getCostAccount().getLongNumber().replace('!','.'));
	    		row.getCell("costAccount.curProject.name").setValue(
						acct.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
	    		row.getCell("costAccount.id").setValue(acct.getCostAccount().getId());
	    		row.getCell("costAccount.name").setValue(
	    				acct.getCostAccount().getDisplayName().replace('_','\\'));
	    		if(tempCount == costEntries.size()){
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("splitScale").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("splitScale").setValue(FDCHelper.subtract(new BigDecimal(100), tempAll));
		    		}
	    			//金额
	    			if(contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("amount").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("amount").setValue(FDCHelper.divide(contractBillInfo.getAmount().multiply(FDCHelper.subtract(new BigDecimal(100), tempAll)), new BigDecimal(100), 4, 5));
		    		}
	    		}else{
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
	    				row.getCell("splitScale").setValue(FDCHelper.ZERO);
	    			}else{
	    				row.getCell("splitScale").setValue(FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
	    			}
	    			if(contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
	    				row.getCell("amount").setValue(FDCHelper.ZERO);
	    			}else{
	    				row.getCell("amount").setValue(contractBillInfo.getAmount().multiply(FDCHelper.divide(acct.getContractAssign(), allAssigned,4,5)));
	    			}
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0){
	    				tempAll = FDCHelper.add(tempAll, FDCHelper.ZERO);
	    			}else{
	    				tempAll = FDCHelper.add(tempAll, FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
	    			}
	    		}
	    		splitAmount = FDCHelper.add(FDCHelper.toBigDecimal(row.getCell("amount").getValue()), splitAmount);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.name").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.name").getStyleAttributes().setLocked(false);
				row.getCell("splitScale").getStyleAttributes().setLocked(false);
				row.getCell("amount").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.curProject.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("standard").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				entry.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
	    	}
//	    	setNameDisplay();
	    	setDisplay();
//	    	txtSplitedAmount.setValue(splitAmount);
	    	isClearFlag = true;
		}

    }
    private void checkProgSub(String checkIsExistProg) throws Exception {
//    	FDCSplitBillEntryInfo entry=null;
//    	for(int i=0; i<kdtEntrys.getRowCount(); i++){			
//			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
//			if(entry.getCostAccount().getId() != null){
    	if(checkIsExistProg == null){
    		FDCMsgBox.showWarning("该合同没有关联框架合约");
			this.abort();
    	}
    		boolean costSplit = FDCUtils.isCostSplit(null, getContractBillId().toString());
    		if(!costSplit){
    			FDCMsgBox.showWarning("当前合同是非才成本类合同，不能关联规划科目");
    			this.abort();
    		}
    		if(kdtEntrys.getRowCount() > 0){
				int ret = MsgBox.showConfirm2(this,"选择规划科目将清空原拆分数据，是否继续，是，清空并继续操作，否，放弃本次操作");
				if (ret==MsgBox.YES) {
//					changeProgSplitEntry();
				} else if(ret==MsgBox.CANCEL){
					this.abort();
				}
			}
//    	}
    }
    /**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent( ) {
		StringBuffer blank = new StringBuffer("");
//		for (int i = level; i > 0; i--) {
			blank.append("       ");
//		}
		return blank.toString();
	}

	/**
	 * 名称以缩进效果显示
	 */
	private void setNameDisplay() {
		int rowCount = kdtEntrys.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntrys.getRow(i);
//			int level = row.getTreeLevel();
			Object name = row.getCell("costAccount.curProject.name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				String blank = setNameIndent();
				row.getCell("costAccount.curProject.name").setValue(blank + name.toString());
			}
		}
	}
    private void checkContractWithProgram() throws Exception{
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", this.getContractBillId()));
    	boolean isConProgram = ContractWithProgramFactory.getRemoteInstance().exists(filter);
    	if(!isConProgram){
    		FDCMsgBox.showWarning(this, "该合同没有关联合约规划!");
    		this.abort();
    	}
    }
    private void importContractWithProgram() throws Exception{
    	ContractProgrammingEntryCollection entryColls = getContractWithProgamData();
    	FDCSplitBillEntryInfo entry=null;
    	BigDecimal totalAmt = FDCHelper.ZERO;
    	BigDecimal shouldSplitAmt = txtAmount.getBigDecimalValue();
    	BigDecimal balanceAmt = shouldSplitAmt;
    	int groupIndex = 0;
    	int curIndex = 0;
    	BOSUuid costBillId=BOSUuid.read(getContractBillId());

    	if(entryColls.size()==0){
			return;
		}
    	Map entryMap = new HashMap();
    	ContractProgrammingEntryCollection newColls = new ContractProgrammingEntryCollection();
		for (int i = 0; i < entryColls.size(); i++) {
			ContractProgrammingEntryInfo entryInfo = entryColls.get(i);
			totalAmt = FDCHelper.add(entryInfo.getProgrammingMoney(), totalAmt);
			//同一工程项目，同一科目，金额合并
			String key = entryInfo.getCostAccount().getId().toString()
					+ entryInfo.getCostAccount().getCurProject().getId()
							.toString();
			if (entryMap.containsKey(key)) {
				ContractProgrammingEntryInfo acctEntryInfo = (ContractProgrammingEntryInfo) entryMap
						.get(key);
				acctEntryInfo.setProgrammingMoney(FDCHelper
						.add(acctEntryInfo.getProgrammingMoney(), entryInfo
								.getProgrammingMoney()));
			} else {
				newColls.add(entryInfo);
				entryMap.put(key, entryInfo);
			}
		}
		for (Iterator iter = newColls.iterator(); iter.hasNext();){
			ContractProgrammingEntryInfo entryInfo = (ContractProgrammingEntryInfo)iter.next();
			
			entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);							
			entry.setCostAccount(entryInfo.getCostAccount());	
			entry.setCostBillId(costBillId);
			entry.setLevel(0);
			entry.setIsLeaf(true);
			groupIndex++;
			entry.setIndex(groupIndex);
			
			BigDecimal programAmt = FDCHelper.toBigDecimal(entryInfo.getProgrammingMoney()).setScale(5);
			// 保留两位
			BigDecimal amount = FDCHelper.ZERO;
			if (totalAmt.compareTo(FDCHelper.ZERO) != 0) {
				amount = FDCHelper.divide(programAmt.multiply(shouldSplitAmt),totalAmt, 2, BigDecimal.ROUND_HALF_UP);
			}
			if(balanceAmt.compareTo(amount)>0){
				entry.setAmount(amount);
				balanceAmt = FDCHelper.subtract(balanceAmt, amount);
			}else{
				entry.setAmount(balanceAmt);
				balanceAmt=FDCHelper.ZERO;
			}
			//尾数给最后一个分录
			///if(curIndex==(entryColls.size()-1)&&entry!=null){
			//update by renliang
			if(curIndex==(entryColls.size()-1)){
				if(balanceAmt.compareTo(FDCHelper.ZERO)!=0){
					entry.setAmount(FDCHelper.toBigDecimal(entry.getAmount()).add(balanceAmt));
					balanceAmt = FDCHelper.ZERO;
				}
			}
			addEntry(entry);
			++curIndex;
		}
		txtSplitedAmount.setValue(shouldSplitAmt);
		txtUnSplitAmount.setValue(balanceAmt);
    }
    private ContractProgrammingEntryCollection getContractWithProgamData() throws Exception{
    	String contractId = getContractBillId();
    	ContractProgrammingEntryCollection entryColls = null;
    	
    	Set idSet = new HashSet();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select fprogrammingid from t_con_contractwithProgram where fcontractbillid=?");
    	builder.addParam(contractId);
    	IRowSet rs = builder.executeQuery();
    	while(rs.next()){
    		idSet.add(rs.getString("fprogrammingid"));
    	}
    	if(idSet.size()==0){
    		return new ContractProgrammingEntryCollection();
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent.id", idSet, CompareType.INCLUDE));
    	view.getSelector().add("programmingMoney");
    	view.getSelector().add("costAccount.id");
    	view.getSelector().add("costAccount.longnumber");
    	view.getSelector().add("costAccount.displayName");
    	view.getSelector().add("costAccount.name");
    	view.getSelector().add("costAccount.curproject.id");
    	view.getSelector().add("costAccount.curproject.longnumber");
    	view.getSelector().add("costAccount.curproject.displayName");
    	view.getSelector().add("costAccount.curproject.name");
    	view.getSelector().add("costAccount.curproject.isLeaf");
    	SorterItemInfo sort = new SorterItemInfo("costAccount.curproject.longnumber");
    	sort.setSortType(SortType.ASCEND);
    	view.getSorter().add(sort);
    	sort = new SorterItemInfo("costAccount.longnumber");
    	sort.setSortType(SortType.ASCEND);
    	view.getSorter().add(sort);
    	entryColls = ContractProgrammingEntryFactory.getRemoteInstance().getContractProgrammingEntryCollection(view);
    	return entryColls;
    }
    protected void checkDupBeforeSave() {
    	FDCSplitBillEntryCollection entrys = getEntrys();
    	Map dupMap = new HashMap();
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)iter.next();
    		if(entry.getCostAccount()==null){
				continue;
			}
			String key=entry.getCostAccount().getId().toString();
			String costbillid = entry.getCostBillId()==null?"":entry.getCostBillId().toString();
			key=key+costbillid;
//			if(entry.getSplitType()!=null){
//				key=key+entry.getSplitType().getValue();
//			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
			FDCSplitBillEntryInfo mapEntry=(FDCSplitBillEntryInfo)dupMap.get(key);
			if(mapEntry==null){
				mapEntry=new PaymentSplitEntryInfo();
				dupMap.put(key, mapEntry);
			}else{
				FDCMsgBox.showWarning(this,"非明细项目拆分（末级拆分或自动拆分）科目与明细项目拆分科目相同，不能保存!");
				this.abort();
			}
    	}
    }
    
    protected void checkbeforeSave() {
    	if(isImportConSplit()&&isEmpty()){
    		try {
    			splitByAimCostSplitScale();
    		} catch (Exception e) {
    			//TODO 后续统一处理
    			e.printStackTrace();
    		}
    	}
    	super.checkbeforeSave();
    }

	private void checkBeforeRemove() throws Exception {
    	if(editData == null || editData.getId() == null || editData.getId().equals("")){
    		return;
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
    /**
     * output actionImpContrSplit_actionPerformed
     */
    public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	System.out.println("\n\nentrys.size:"+editData.getEntrys().size());
    	FDCCostSplit costSplit=new FDCCostSplit(null);
    	costSplit.refreshApportionAmount(editData,null);
    	updateAmountColumn(getEntrys());

//    	super.actionImpContrSplit_actionPerformed(e);
    }
             
    //------------------------------------------------------------
    

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
	}
		
	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ContractCostSplitFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
        return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		//return null;
		
		return new com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo();
	}
	

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		//return super.createNewData();
				
		ContractCostSplitInfo objectValue=new ContractCostSplitInfo();
		
        //objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        
        String costBillID=null;
        
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()&&this.getUIContext().get("billID")!=null){
        	costBillID=this.getUIContext().get("billID").toString();
        	getUIContext().remove(UIContext.ID);
        }else{
        	costBillID = (String)getUIContext().get("costBillID");
        }
        ContractBillInfo costBillInfo=null;
              
        SelectorItemCollection selectors = new SelectorItemCollection();
		//selectors.add("*");
        selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		selectors.add(new SelectorItemInfo("state"));
		selectors.add(new SelectorItemInfo("orgUnit.id"));
        try {
        	costBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(costBillID)),selectors);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		
    	objectValue.setContractBill(costBillInfo);
    	if(costBillInfo.getCurProject()!=null)
    		objectValue.setCurProject(costBillInfo.getCurProject());
        txtCostBillNumber.setText(costBillInfo.getNumber());
        //txtCostBillName.setText(costBillInfo());    	
        txtAmount.setValue(costBillInfo.getAmount());
        objectValue.setIsInvalid(false);
		setContractBillId(costBillInfo.getId().toString());
		objectValue.setIsConfirm(true);        
        return objectValue;
	}

	public void onLoad() throws Exception {
		/* TODO 自动生成方法存根 */
		getDetailTable().getLayoutManager().setVSplitButtonVisible(false);
		getDetailTable().getLayoutManager().setHSplitButtonVisible(false);
		super.onLoad();
		//暂时屏蔽提交
//		this.btnSubmit.setVisible(true);
//		this.actionSubmit.setEnabled(true);
//		tHelper.getDisabledTables().add(getDetailTable());
		initWorkButton();
		setSplitBillType(CostSplitBillTypeEnum.CONTRACTSPLIT);
		
		//是否必须审核前拆分
		HashMap param = FDCUtils.getDefaultFDCParam(null,editData.getContractBill().getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
		//付款单提交时，是否检查合同拆分
		if(param.get(FDCConstants.FDC_PARAM_CHECKALLSPLIT )!= null){
	    	checkAllSplit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CHECKALLSPLIT).toString()).booleanValue();
		}
		String id = "";
		if(editData.getContractBill()!=null){
			id = editData.getContractBill().getId().toString();
		}
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("isAmtWithoutCost"));
        sic.add(new SelectorItemInfo("isCoseSplit"));
        
        //2008-12-25 .如果启用参数 并且进入成本的，按钮应该是亮
		ContractBillInfo contractBillInfo = ContractBillFactory
			.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(id)),sic);
		if(splitBeforeAudit && !FDCBillStateEnum.SUBMITTED.equals(contractBillInfo.getState())
		 && !contractBillInfo.isIsCoseSplit()){
			super.setOprtState(OprtState.VIEW); 
		}
		

//		if(splitBeforeAudit && !FDCBillStateEnum.SUBMITTED.equals(editData.getContractBill().getState())){
//			super.setOprtState(OprtState.VIEW); 
//		}
		
		/*
		for(int i=kdtEntrys.getColumnIndex("product")+1; i<kdtEntrys.getColumnCount(); i++){
			kdtEntrys.getColumn(i).getStyleAttributes().setHided(false);
		}
		*/
		
		/*
		
		if (STATUS_ADDNEW.equals(getOprtState())) {
	    	importCostSplitContract();    	
	    	//importCostSplitCntrChange();    	
			setDisplay();
			
			

			IRow row=null;
			FDCSplitBillEntryInfo entry=null;
			
			BOSUuid costBillId=null;
			BigDecimal amount=null;
			
			BOSObjectType contractBill=(new ContractBillInfo()).getBOSType();
			
			for(int i=0; i<kdtEntrys.getRowCount(); i++){
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				
				costBillId=entry.getCostBillId();
				if(costBillId!=null){
					amount=entry.getAmount();
					if(amount!=null){
						if(costBillId.getType().equals(contractBill)){
							row.getCell("contractAmount").setValue(amount);							
						}else{
							row.getCell("cntrChangeAmount").setValue(amount);
						}
						
						entry.setAmount(null);
						row.getCell("amount").setValue(null);
					}
				}
			}
		}
		*/
		 getDetailTable().getColumn("splitScale").setEditor(getScaleCellNumberEdit());
		 
		 //add by david_yang R110325-549 2011.04.28(支持负数)
		 getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper._getTotalCellNumberEdit());
		 if(ContractCostSplitFactory.getRemoteInstance().checkIsExistProg(this.getContractBillId().toString())!=null){
			 this.actionAcctSelect.setEnabled(false);
			 this.btnAcctSelect.setEnabled(false);
		 }
		 
		 if(this.getUIContext().get("amount")!=null){
			 this.txtAmount.setValue(this.getUIContext().get("amount"));
		 }
		 actionViewContract.putValue(Action.SMALL_ICON, EASResource
					.getIcon("imgTbtn_sequencecheck"));
	}
	
	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	public void setOprtState(String oprtType) {
			super.setOprtState(oprtType);
			/*if(!STATUS_ADDNEW.equals(oprtType)) {
				prmtcurProject.setEnabled(false);
				
			}*/
	}
	
	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#loadFields()
	 */
	public void loadFields() {
		// TODO 自动生成方法存根
		super.loadFields();
		if(editData.getContractBill()!=null){
			//setContractBillId(editData.getContractBill().toString()); bug....
			setContractBillId(editData.getContractBill().getId().toString());
			
			//查看的时候量价合同处理
			if(editData.getContractBill().isIsMeasureContract()){
				getUIContext().put("isMeasureSplit",Boolean.TRUE);
				//第二次拆分保存时，丢失editData中的isMeasureContract信息，导致又把合同反写为非量价拆分，故在loadFields时手工写入
				this.editData.setBoolean("isMeasureContract", true);
			}
		}
		setDisplay();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.finance.client.AbstractContractCostSplitEditUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO 自动生成方法存根
		//return super.getSelectors();
		
		SelectorItemCollection sic=super.getSelectors();
		
		//sic.add(new SelectorItemInfo("paymentBill.contractBillId"));
		sic.add("state");
		sic.add(new SelectorItemInfo("contractBill.state"));
		sic.add(new SelectorItemInfo("contractBill.orgUnit.id"));
		sic.add(new SelectorItemInfo("contractBill.isMeasureContract"));
		return setSelectors(sic);
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#getSplitBillEntryClassName()
	 */
	protected String getSplitBillEntryClassName() {
		// TODO 自动生成方法存根
		//return super.getSplitBillEntryClassName();
		
		return ContractCostSplitEntryInfo.class.getName();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		// TODO 自动生成方法存根
		super.initWorkButton();
		
		btnImpContrSplit.setVisible(false);
		actionImpContrSplit.setEnabled(true);
		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		actionCopyLine.setVisible(false);
		actionCopyLine.setEnabled(false);
		
		actionProgrAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		menuItemProgrAcctSelect.setAccelerator(KeyStroke.getKeyStroke("ctrl shift P"));
		menuItemProgrAcctSelect.setText(menuItemProgrAcctSelect.getText().replaceAll("\\(P\\)", "")+"(P)");
		menuItemProgrAcctSelect.setMnemonic('P');
		
		
	}
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		if(OprtState.VIEW==getOprtState()){
			actionProgrAcctSelect.setEnabled(false);
		}
		
	}
    public void updateDetailTable(IObjectCollection entrys){
    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCSplitBillEntryInfo entry;
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		entry=(FDCSplitBillEntryInfo)iter.next();
    		row=getDetailTable().addRow();
    		loadLineFields(getDetailTable(), row, entry);
    	}
    }
    
    public void updateAmountColumn(IObjectCollection entrys){
//    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCSplitBillEntryInfo entry;
    	int rowIndex=0;
    	for(Iterator iter=entrys.iterator();iter.hasNext();rowIndex++){
    		entry=(FDCSplitBillEntryInfo)iter.next();
    		row=getDetailTable().getRow(rowIndex);
    		row.getCell("amount").setValue(entry.getAmount());
    		row.getCell("apportionValue").setValue(entry.getApportionValue());
    		row.getCell("apportionValueTotal").setValue(entry.getApportionValueTotal());
    		row.getCell("directAmountTotal").setValue(entry.getDirectAmountTotal());
    		row.getCell("directAmount").setValue(entry.getDirectAmount());
    		row.getCell("otherRatioTotal").setValue(entry.getOtherRatioTotal());
//    		row.getCell("otherRatio").setValue(entry.getOtherRatio());
    	}
    }
    
    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
//    	if (getTableForCommon()!=null||getDetailTable()!=null)
//    	{
//    		KDMenuItem item = new KDMenuItem();
//            item.setName("menuItemAttachment");
//            item.setAction(new ActionAttachMent());
//            return item;
//    	}
    	return null;

    }
//  分录附件
    class ActionAttachMent extends ItemAction {
    	public ActionAttachMent() {
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.NAME, _tempStr);
         }
//        public void actionPerformed(ActionEvent e) {
//        	showSubAttacheMent(null);
//        }
    }
    
    /**
	 * 合同工作流处理方法,达到合同拆分不与工作流的配置相关，只要传放合同ID，即可以正常拆分，以后其它拆分照此实现
	 */
	private void handleSplitWorkFlow() {
		//重载处理工作流问题，将工作流的参数进行转化
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
        	if(this.getUIContext().get(UIContext.ID)!=null){
        		String costBillID=this.getUIContext().get(UIContext.ID).toString();
        		getUIContext().remove(UIContext.ID);
        		getUIContext().put("costBillID",costBillID);
        		setOprtState(OprtState.ADDNEW);
        		//如果合同已经拆分还要再次拆分的话则在原有的基础上进行拆分
        		FDCSQLBuilder builder=new FDCSQLBuilder();
        		builder.appendSql("select top 1 fid from T_Con_ContractCostSplit where fcontractBillId=? and fstate<>?");
        		builder.addParam(costBillID);
        		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
        		try{
        			IRowSet rowSet=builder.executeQuery();
        			if(rowSet.next()){
        				String splitId=rowSet.getString("fid");
        				this.getUIContext().put(UIContext.ID,splitId);
        				setOprtState(OprtState.EDIT);
        			}
        		}catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
        		
        		logger.info("contractsplit costbill id="+costBillID);
        	}else{
        		logger.error("工作流中取不到合同ID，can't get contract id");
        	}
        }
	}
	
	protected void loadData() throws Exception {
		handleSplitWorkFlow();
		super.loadData();
	}
    protected boolean isMeasureContract() {
    	if(getUIContext().get("isMeasureSplit")!=null){
    		return true;
    	}
    	return false;
    }
    public KDTDefaultCellEditor getScaleCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(10);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
    public void actionAcctSelect_actionPerformed(ActionEvent arg0)
	throws Exception {
		if (isClearFlag) {
			int ret = MsgBox.showConfirm2(this,
					"选择成本科目将清空原拆分数据，是否继续，是，清空并继续操作，否，放弃本次操作");
			if (ret == MsgBox.YES) {
				getDetailTable().removeRows(false);
				super.actionAcctSelect_actionPerformed(arg0);
				isClearFlag = false;
			} else if (ret == MsgBox.CANCEL) {
				this.abort();
			}
		} else {
			super.actionAcctSelect_actionPerformed(arg0);
		}
	}
    public void actionViewContract_actionPerformed(ActionEvent e)throws Exception {
		if (editData.getContractBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getContractBill() != null) {
			uiContext.put(UIContext.ID, editData.getContractBill().getId().toString());
			IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(ContractBillEditUI.class.getName(), uiContext,
							null, OprtState.VIEW);
			testUI.show();
		}
	}
}