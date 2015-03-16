/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.programming.PTECostCollection;
import com.kingdee.eas.fdc.contract.programming.PTECostInfo;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection;
import com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProgrammingImportUI extends AbstractProgrammingImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingImportUI.class);
    
	public ProgrammingImportUI() throws Exception {
		super();
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	menuBar.setVisible(false);
		toolBar.setVisible(false);
		actionConfirm.setEnabled(true);
		actionExit.setEnabled(true);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    	tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    }
    
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		EntityViewInfo view = this.getMainQuery();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled" , new Integer(1) , CompareType.EQUALS));
		view.setFilter(filter);
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				executeImport();
			}
		}
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		executeImport();
	}
	
	private void executeImport() throws Exception {
		if (MsgBox.OK != MsgBox.showConfirm2(this, "从模板导入将覆盖当前数据，继续么？")) {
			return;
		}
		Map uiContext = getUIContext();
		ProgrammingInfo programming = (ProgrammingInfo) uiContext.get("editData"); // 被导入的合约规划
		DataChangeListener dataChangeListener = (DataChangeListener) uiContext.get("dataChangeListener");
		//导入模板的时候默认是没有目标成本的
		ProgrammingEditUI editUI = (ProgrammingEditUI)uiContext.get("Owner");
		editUI.prmtAimCost.removeDataChangeListener(dataChangeListener);
		editUI.prmtAimCost.setValue(null);
		editUI.prmtAimCost.addDataChangeListener(dataChangeListener);
		AimCostInfo aimCost = (AimCostInfo) uiContext.get("aimCost"); // 目标成本
		aimCost = null;
		programming.setAimCost(aimCost);
		CurProjectInfo project = (CurProjectInfo) uiContext.get("project"); // 框架合约的关联的工程项目
		if (programming == null || project == null) {
			return;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", getSelectedKeyValue()));
		
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getTemplateEntrySelector());
		evi.setFilter(filter);
		
		ProgrammingTemplateEntireCollection templateEntireCollection = 
			ProgrammingTemplateEntireFactory.getRemoteInstance().getProgrammingTemplateEntireCollection(evi);
		
		templateImport(programming, templateEntireCollection, project, aimCost);
		MsgBox.showInfo(this, "导入成功");
		disposeUIWindow();
	}
	
	private void templateImport(ProgrammingInfo programming, ProgrammingTemplateEntireCollection 
			templateEntryCollection, CurProjectInfo project, AimCostInfo aimCost) 
	throws EASBizException, BOSException {
		ProgrammingContractCollection entries = programming.getEntries();
		entries.clear();
		for (int i = 0, size = templateEntryCollection.size(); i < size; i++) {
			ProgrammingTemplateEntireInfo templateEntry = templateEntryCollection.get(i);
			ProgrammingContractInfo programmingEntry = new ProgrammingContractInfo();
			initEntryAttribute(templateEntry, programmingEntry);
			copyAttachment(templateEntry, programmingEntry);
			entryImport(programmingEntry, templateEntry, project, aimCost);
			initEntryOtherAmout(programmingEntry);
			entries.add(programmingEntry);
		}
		
		setEntryParent(entries);
	}
	
	/**
	 * 初始化导入的合约的其他金额
	 */
	private void initEntryOtherAmout(ProgrammingContractInfo info) {
		info.setControlBalance(FDCHelper.ZERO); // 控制余额
		info.setSignUpAmount(FDCHelper.ZERO);	// 签约金额
		info.setChangeAmount(FDCHelper.ZERO);	// 变更金额
		info.setSettleAmount(FDCHelper.ZERO);	// 结算金额
	}

	private void initEntryAttribute(ProgrammingTemplateEntireInfo templateEntry,
			ProgrammingContractInfo programmingEntry) {
		programmingEntry.setId(BOSUuid.create(programmingEntry.getBOSType()));
		programmingEntry.setLevel(templateEntry.getLevel());
		programmingEntry.setLongNumber(templateEntry.getLongNumber());
		programmingEntry.setNumber(templateEntry.getNumber());
		programmingEntry.setDisplayName(templateEntry.getDisplayName());
		programmingEntry.setName(templateEntry.getName().trim());
		programmingEntry.setWorkContent(templateEntry.getScope());
		programmingEntry.setSupMaterial(templateEntry.getProblem());
		programmingEntry.setSortNumber(templateEntry.getSortNumber());
		if(templateEntry.getAttachment() != null){
			programmingEntry.setAttachment("已有附件");
		}
		programmingEntry.setDescription(templateEntry.getDescription());
		programmingEntry.setContractType(templateEntry.getContractType());
	}

	// 设置分录父子级关系
	private void setEntryParent(ProgrammingContractCollection entries) {
		for (int i = 0, size = entries.size(); i < size; i++) {
			ProgrammingContractInfo entry = entries.get(i);
			String longNumber = entry.getLongNumber();
			for (int j = 0; j < size; j++) {
				ProgrammingContractInfo entryParent = entries.get(j);
				String longNumberParent = entryParent.getLongNumber();
				if (longNumber.startsWith(longNumberParent)) {
					int count = longNumber.split("\\.").length;
					int countParent = longNumberParent.split("\\.").length;
					if (count - countParent == 1) {
						entry.setParent(entryParent);
						break;
					}
				}
			}
		}
	}
	
	/**
     * 复制附件
     */
    private void copyAttachment(ProgrammingTemplateEntireInfo proTempInfo, ProgrammingContractInfo proInfo){
    	FDCSQLBuilder sql = new FDCSQLBuilder("select * from T_BAS_BoAttchAsso where FBOID = '"+proTempInfo.getId()+"'");
    	BoAttchAssoInfo info = null;
    	try {
			IRowSet rs = sql.executeQuery();
			while(rs.next()){
				info = new BoAttchAssoInfo();
				String attID = rs.getString("FATTACHMENTID");
				if(!StringUtils.isEmpty(attID))
					info.setAttachment(AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attID)));
				info.setBoID(proInfo.getId().toString());
				info.setAssoBusObjType(proInfo.getBOSType().toString());
				info.setAssoType("系统已有附件");
				BoAttchAssoFactory.getRemoteInstance().addnew(info);
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		} catch (EASBizException e) {
			logger.error(e);
		}
    	
    }
	
	private void entryImport(ProgrammingContractInfo programmingEntry, 
			ProgrammingTemplateEntireInfo templateEntry, CurProjectInfo project, AimCostInfo aimCost) 
	throws EASBizException, BOSException {
		BigDecimal planAmountTotal = FDCHelper.ZERO; // 所有成本对象本合约分配成规划余额
		// 模板中成本构成
		PTECostCollection pteCostCollection = templateEntry.getPteCost();
		// 合约中成本构成
		ProgrammingContracCostCollection costEntries = programmingEntry.getCostEntries();
		for (int i = 0, size = pteCostCollection.size(); i < size; i++) {
			PTECostInfo pteCostInfo = pteCostCollection.get(i);
			// 模板导入后产生的成本构成
			ProgrammingContracCostCollection programmingCollection = costEntryImport(pteCostInfo, project, aimCost);
			
			// 导入的模板中的成本科目不重复
			// 直接添加到分录中
			for (int j = 0, sizeJ = programmingCollection.size(); j < sizeJ; j++) {
				ProgrammingContracCostInfo info = programmingCollection.get(j);
				planAmountTotal = planAmountTotal.add(info.getContractAssign());
				costEntries.add(programmingCollection.get(j));
			}
		}
		
		programmingEntry.setAmount(planAmountTotal);
		programmingEntry.setControlAmount(FDCHelper.ZERO);
		// 模板中经济条款
		PTEEnonomyCollection pteEnonomyCollection = templateEntry.getPteEnonomy();
		enonomyImport(programmingEntry, pteEnonomyCollection);
	}
	
	/**
	 * 导入经济条款
	 * @param programmingCollection
	 * @param pteEnonomyCollection
	 */
	private void enonomyImport(ProgrammingContractInfo programmingEntry, 
			PTEEnonomyCollection pteEnonomyCollection) {
		ProgrammingContractEconomyCollection programmingEconomyEntries = programmingEntry.getEconomyEntries();
		programmingEconomyEntries.clear();
		for (int i = 0, size = pteEnonomyCollection.size(); i < size; i ++) {
			ProgrammingContractEconomyInfo economy = new ProgrammingContractEconomyInfo();
			PTEEnonomyInfo pteEnonomy = pteEnonomyCollection.get(i);
			economy.setScale(pteEnonomy.getScale()); // 付款比例 
			economy.setCondition(pteEnonomy.getCondition()); // 付款条件
			economy.setPaymentType(pteEnonomy.getPaymentType()); // 付款类型
			economy.setDescription(pteEnonomy.getDescription()); // 备注
			economy.setAmount(pteEnonomy.getScale().multiply(programmingEntry.getAmount())); // 付款金额
			economy.setAmount(FDCHelper.divide(economy.getAmount(), FDCHelper.ONE_HUNDRED)); // 付款金额
			programmingEconomyEntries.add(economy);
		}
	}
	
	/**
	 * 导入成本
	 * @param pteCostInfo
	 * @param project
	 * @param aimCost
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private ProgrammingContracCostCollection costEntryImport(PTECostInfo pteCostInfo, 
			CurProjectInfo project, AimCostInfo aimCost) 
	throws BOSException, EASBizException {
		ProgrammingContracCostCollection retCollection = new ProgrammingContracCostCollection();
		CostAccountInfo pteCostAccount = pteCostInfo.getCostAccount(); // 模板中成本科目
		CostAccountInfo costAccount = changePteCost2CurProject(pteCostAccount, project);
		if (costAccount == null) {
			return retCollection;
		}
		
		CostAccountCollection proCostCollection = getProCostAccount(costAccount);
		if (aimCost != null) {
			// 指定了目标成本
			BigDecimal aimCostValue = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(
					costAccount, aimCost);
			for (int i = 0, size = proCostCollection.size(); i <size; i++) {
				ProgrammingContracCostInfo info = new ProgrammingContracCostInfo();
				info.setCostAccount(costAccount);
				info.setGoalCost(aimCostValue); // 目标成本
				
				BigDecimal contractScale = pteCostInfo.getContractScale() == null ? FDCHelper.ZERO :
					pteCostInfo.getContractScale();
				contractScale = FDCHelper.divide(contractScale, FDCHelper.ONE_HUNDRED);
				info.setContractAssign(aimCostValue.multiply(contractScale)); // 本合约分配
				
				BigDecimal assignScale = pteCostInfo.getAssignScale() == null ? FDCHelper.ZERO :
					pteCostInfo.getAssignScale();
				assignScale = FDCHelper.divide(assignScale, FDCHelper.ONE_HUNDRED);
				BigDecimal assigning = aimCostValue.multiply(assignScale);
				info.setAssigning(assigning); // 待分配
				info.setAssigned(aimCostValue.subtract(assigning)); // 已分配
				info.setDescription(pteCostAccount.getDescription()); // 备注
				retCollection.add(info);
			}
		} else {
			for (int i = 0, size = proCostCollection.size(); i <size; i++) {
				ProgrammingContracCostInfo info = new ProgrammingContracCostInfo();
				info.setCostAccount(costAccount);
				info.setGoalCost(FDCHelper.ZERO); // 目标成本
				info.setAssigned(FDCHelper.ZERO); // 已分配
				info.setAssigning(FDCHelper.ZERO); // 待分配
				info.setContractAssign(FDCHelper.ZERO); // 本合约分配
				info.setDescription(pteCostAccount.getDescription()); // 备注
				retCollection.add(info);
			}
		}
		return retCollection;
	}
	
	/**
	 * 转换模板中的组织成本科目为 工程项目下的成本科目
	 * @param pteCostAccount
	 * @param project
	 * @return
	 */
	private CostAccountInfo changePteCost2CurProject(CostAccountInfo pteCostAccount, CurProjectInfo project) {
		String longNumber = pteCostAccount.getLongNumber();
		String projectId = project.getId().toString();
		StringBuffer oql = new StringBuffer();
		
		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longnumber = '").append(longNumber).append("'");
		oql.append(" and curProject = '").append(projectId).append("'");
		oql.append(" and fullOrgUnit is null");
		try {
			return CostAccountFactory.getRemoteInstance().getCostAccountInfo(oql.toString());
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 如果模板中的末级成本科目在项目中变为父级成本科目
	 * 则在框架合约的成本构成关系中自动把该父级科目替换为对应的所有末级成本科目
	 * @param pteCostAccount 模板成本科目
	 * @return CostAccountCollection
	 * @throws BOSException
	 */
	private CostAccountCollection getProCostAccount(CostAccountInfo pteCostAccount) throws BOSException {
		CostAccountCollection costCollection = new CostAccountCollection();
		String longNumber = pteCostAccount.getLongNumber();
		
		StringBuffer oql = new StringBuffer();
		oql.append("select id, number, name, longNumber, curProject.id, curProject.number, curProject.name where longNumber like '").append(longNumber).append("!%'");
		oql.append(" and fullOrgUnit is null");
		costCollection = CostAccountFactory.getRemoteInstance().getCostAccountCollection(oql.toString());
		if (costCollection.isEmpty()) {
			costCollection.add(pteCostAccount);
		}
		return costCollection;
	}
	
	private SelectorItemCollection getTemplateEntrySelector() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("pteCost.costAccount");
		sic.add("pteCost.costAccount.id");
		sic.add("pteCost.costAccount.number");
		sic.add("pteCost.costAccount.name");
		sic.add("pteCost.costAccount.longNumber");
		sic.add("pteCost.assignScale");
		sic.add("pteCost.contractScale");
		sic.add("pteEnonomy.scale");
		sic.add("pteEnonomy.condition");
		sic.add("pteEnonomy.description");
		sic.add("pteEnonomy.parent");
		sic.add("pteEnonomy.paymentType");
		sic.add("pteEnonomy.paymentType.name");
		sic.add("pteEnonomy.paymentType.number");
		sic.add("name");
		sic.add("number");
		sic.add("description");
		sic.add("id");
		sic.add("level");
		sic.add("longNumber");
		sic.add("attachment");
		sic.add("displayName");
		sic.add("sortNumber");
		sic.add("contractType.*");
		return sic;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProgrammingTemplateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.programming.client.ProgrammingTemplateEditUI.class.getName();
	}
	
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}
}