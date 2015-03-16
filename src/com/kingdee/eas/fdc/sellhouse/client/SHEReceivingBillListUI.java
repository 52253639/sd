/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.client.UpdateSubjectUI;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.client.CasReceivingBillListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHEReceivingBillListUI extends AbstractSHEReceivingBillListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3093948863553679391L;
	private static final Logger logger = CoreUIObject.getLogger(SHEReceivingBillListUI.class);
	
    /**
     * output class constructor
     */
    public SHEReceivingBillListUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();

		this.actionAddNew.setEnabled(false);
		this.actionDelVoucher.setVisible(true);
		this.actionTraceDown.setVisible(true);
		this.actionUpdateSubject.setVisible(true);
		this.actionUpdateSubject.setEnabled(true);
		
		if(!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.treeMain.setSelectionRow(0);
		this.actionCreateBill.setVisible(true);
		this.actionCreateBill.setEnabled(true);
		this.actionRelatePaymentBill.setEnabled(true);
	}

	protected String getHandleClazzName() {
		return "com.kingdee.eas.fdc.sellhouse.app.SheRevHandle";
	}

	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys;
	}
		
	protected String getEditUIName() {
		return SHEReceivingBillEditUI.class.getName();
	}

	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("receiptNumber"));
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("moneyDefine.name"));
        sic.add(new SelectorItemInfo("settlementType.name"));
        sic.add(new SelectorItemInfo("moneyDefine.moneyType"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("roomLongNumber"));
        sic.add(new SelectorItemInfo("room.name"));
//        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("room.displayName"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("revBillType"));
        sic.add(new SelectorItemInfo("revBizType"));
        sic.add(new SelectorItemInfo("customer.name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("entries.*"));
        sic.add(new SelectorItemInfo("entries.revAccount.*"));
        sic.add(new SelectorItemInfo("entries.oppAccount.*"));
        return sic;
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getUnitTree(this.actionOnLoad, this.getSystemType()));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	 public void actionUpdateSubject_actionPerformed(ActionEvent e)
		throws Exception {
	    checkSelected();
		checkUpdateSubject();
		ArrayList list=this.getSelectedIdValues();
		HashSet set=new HashSet(list);
		int[] indexs=KDTableUtil.getSelectedRows(tblMain);
		String revBillType=tblMain.getRow(indexs[0]).getCell("revBillType").getValue().toString();
		IUIFactory uiFactory = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", set);
		uiContext.put("revBillType", revBillType);// 单据类型
		uiContext.put("sysType", MoneySysTypeEnum.SalehouseSys);
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(UpdateSubjectUI.class.getName(),uiContext, null, OprtState.ADDNEW);
		curDialog.show();
	 }

      private void checkUpdateSubject(){
		int[] indexs=KDTableUtil.getSelectedRows(tblMain);
		int count = 0;
		if(indexs[0]!=-1)count = indexs[0];
		String revBillType=tblMain.getRow(count).getCell("revBillType").getValue().toString();
		for(int i=0;i<indexs.length;i++){
			IRow row=tblMain.getRow(indexs[i]);
			Boolean b=Boolean.valueOf(row.getCell("fiVouchered").getValue().toString());
			if(!revBillType.equals(row.getCell("revBillType").getValue().toString())){
				MsgBox.showWarning("选择的单据类型不一致，不能进行科目维护！");
				abort();
			}
			if(b.booleanValue()){
				MsgBox.showWarning("选择中包含了已生成凭证的单据,不能进行科目维护！");
				abort();
			}
		}
}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, curSellProNode);
		super.prepareUIContext(uiContext, e);
	}
	
	protected void setColGroups() {
		super.setColGroups();
		
		setColGroup("building");
		setColGroup("buildUnit");
		setColGroup("room.number");
		setColGroup("room.roomNo");
		setColGroup("purchase.number");
		setColGroup("saleMan");
		setColGroup("creator");
		setColGroup("receiptState");
		setColGroup("invoiceAmount");
	}
	
	
	public void actionBatchReceiving_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if (node == null || !(node.getUserObject() instanceof BuildingInfo || node.getUserObject() instanceof BuildingUnitInfo)) {
			FDCMsgBox.showInfo("必须先选中楼栋或单元节点！");
			return;
		}
		
		UIContext uiContext = new UIContext(this);
		if(node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo buildInfo = (BuildingInfo)node.getUserObject();
			uiContext.put("TreeNodeInfo", buildInfo);
			uiContext.put("sellProject", buildInfo.getSellProject());
		}else if(node.getUserObject() instanceof BuildingUnitInfo){
			BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)node.getUserObject();
			uiContext.put("TreeNodeInfo", buildUnitInfo);			
			uiContext.put("sellProject", ((BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject()).getSellProject());
		}
		

		int retResult = FDCMsgBox.showConfirm2New(null,"请选择要批量收款的款项类别！                                        " +
				" "+MoneyTypeEnum.AccFundAmount.getAlias()+"‘是’                                                             " +
						""+MoneyTypeEnum.LoanAmount.getAlias()+"‘否’");   //.showTableDetailAndOK(null, error, errorDetailMap, 3);
		if(retResult==JOptionPane.YES_OPTION) {
			uiContext.put("MoneyTypeEnum", MoneyTypeEnum.AccFundAmount);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create(BatchReceiveBillEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
			uiWindow.show();
		}else if(retResult==JOptionPane.NO_OPTION){
			uiContext.put("MoneyTypeEnum", MoneyTypeEnum.LoanAmount);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create(BatchReceiveBillEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
			uiWindow.show();	
		}
		

	}

	/**
	 * 根据收款单生成出纳系统的收款单
	 * 
	 */
	public void actionCreateBill_actionPerformed(ActionEvent e) throws Exception {
		
		checkBillType();
		ArrayList idList = this.getSelectedIdValues();
		
		if(idList!=null && idList.size()>1){
			MsgBox.showWarning(this,"请选择单行数据进行此操作！");
			SysUtil.abort();
		}else{
				if(idList.size()==0 && tblMain.getSelectManager().getActiveRowIndex()==-1){
					idList.clear();
					IRow row = tblMain.getRow(0);
					if(row==null){
						return;
					}
					String id = row.getCell("id").getValue().toString();
					idList.add(id);
				}
				
				checkBillStatusForCreate(idList);
				isCreateVouchered(idList);
				boolean res = isCreateBill(idList);
				if(!res){
					
					boolean result = findBillFromCasPaymentBillbyId(idList);
					if(!result){
						MsgBox.showWarning(this,"已经生成出纳收款单，不允许重复生成！");
						SysUtil.abort();
					}
				}
				try{
					FDCReceivingBillFactory.getRemoteInstance().createCashBill(idList, false);
					MsgBox.showWarning(this, "出纳收款单生成成功！");
					SysUtil.abort();
				}catch(Exception ex){
					if(ex instanceof BOSException&&ex.getMessage().startsWith("科目不按指定币别核算")){
						MsgBox.showWarning(this, "科目不按指定币别核算，请选择其它币别！");
						SysUtil.abort();
					}else if(ex instanceof BOSException&&ex.getMessage().startsWith("生成出纳收款单失败")){
						MsgBox.showWarning(this, "生成出纳收款单失败！");
						SysUtil.abort();
					}
				}
		}
	}
	
	private void checkBillType(){
		int index=this.tblMain.getSelectManager().getActiveRowIndex();
		
		if(index==-1){
			return;
		}
		
		IRow row = this.tblMain.getRow(index);
		
		if(row ==null){
			return;
		}
		
		BizEnumValueDTO bizDTO=(BizEnumValueDTO)row.getCell("revBillType").getValue();
		
		if(bizDTO.getName().equals("transfer")){
			FDCMsgBox.showWarning(this, "转款类型的收款单不能生成出纳收款单!");
			SysUtil.abort();
		}/*else if(bizDTO.getName().equals("adjust")){
			FDCMsgBox.showWarning(this, "调整类型的收款单不能生成出纳收款单!");
			SysUtil.abort();
		}*/
	}
	private boolean isCreateBill(ArrayList idList){
		
			boolean result = true;
		
			try {
					for (int i = 0; i < idList.size(); i++) {
						EntityViewInfo evi = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(i).toString(), CompareType.EQUALS));
						evi.setFilter(filterInfo);
						SelectorItemCollection coll = new SelectorItemCollection();
						coll.add(new SelectorItemInfo("id"));
						coll.add(new SelectorItemInfo("isCreateBill"));
						evi.setSelector(coll);
						IFDCReceivingBill fdcRece = FDCReceivingBillFactory.getRemoteInstance();
						FDCReceivingBillCollection collection = fdcRece.getFDCReceivingBillCollection(evi);
						if (collection != null && collection.size() > 0) {
							for (int j = 0; j < collection.size(); j++) {
								FDCReceivingBillInfo info = collection.get(j);
								if(info.isIsCreateBill()){
									result = false;
								}
							}
						}
					}
					
					
				} catch (BOSException e) {
					logger.error(e.getMessage()+"获取房地产售楼收款单状态失败!");
				}
		
		return result;
	}
	
	private void checkCreateBillStatus(ArrayList idList,String msg){

		try {
				for (int i = 0; i < idList.size(); i++) {
					IReceivingBill rece = ReceivingBillFactory.getRemoteInstance();
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("sourceBillId", idList.get(i).toString(), CompareType.EQUALS));
					evi.setFilter(filterInfo);
					SelectorItemCollection coll = new SelectorItemCollection();
					coll.add(new SelectorItemInfo("sourceBillId"));
					coll.add(new SelectorItemInfo("billStatus"));
					evi.setSelector(coll);
					ReceivingBillCollection collection = rece.getReceivingBillCollection(evi);
					if(collection!=null && collection.size()>0){
						//result = true;
						MsgBox.showWarning(this, msg);
						SysUtil.abort();
					}
				}
			} catch (BOSException e) {
				logger.error(e.getMessage()+"获取是否已生成出纳收款单状态失败!");
			}
			
	}
	
	private void isCreateVouchered(ArrayList idList){
		
		boolean result = false;
		
		try {
				for (int i = 0; i < idList.size(); i++) {
					IFDCReceivingBill fdcRece = FDCReceivingBillFactory.getRemoteInstance();
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(i).toString(), CompareType.EQUALS));
					evi.setFilter(filterInfo);
					SelectorItemCollection coll = new SelectorItemCollection();
					coll.add(new SelectorItemInfo("id"));
					coll.add(new SelectorItemInfo("fiVouchered"));
					evi.setSelector(coll);
					FDCReceivingBillCollection collection = fdcRece.getFDCReceivingBillCollection(evi);
					if(collection!=null && collection.size()>0){
						FDCReceivingBillInfo info = collection.get(i);
						if(info.isFiVouchered()){
							result = true;
						}
					}
				}
			} catch (BOSException e) {
				logger.error(e.getMessage()+"获取是否已生成凭证状态失败!");
			}
			
			if(result){
				MsgBox.showWarning(this, "已生成凭证，不能生成出纳收款单！");
				SysUtil.abort();
			}
	}
	
	private boolean findBillFromCasPaymentBillbyId(ArrayList idList){
		
		boolean result = true;
		try {
			
			for (int i = 0; i < idList.size(); i++) {
				IReceivingBill rece = ReceivingBillFactory.getRemoteInstance();
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("sourceBillId", idList.get(0).toString(), CompareType.EQUALS));
				evi.setFilter(filterInfo);
				SelectorItemCollection coll = new SelectorItemCollection();
				coll.add(new SelectorItemInfo("sourceBillId"));
				coll.add(new SelectorItemInfo("billStatus"));
				evi.setSelector(coll);
				ReceivingBillCollection collection = rece.getReceivingBillCollection(evi);
				if(collection!=null && collection.size()>0){
					result = false;
				}
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void checkBillStatusForCreate(ArrayList idList){
		
		try {
			
			for (int i = 0; i < idList.size(); i++) {
				
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(i).toString(), CompareType.EQUALS));
				evi.setFilter(filterInfo);
				SelectorItemCollection coll = new SelectorItemCollection();
				coll.add(new SelectorItemInfo("id"));
				coll.add(new SelectorItemInfo("billStatus"));
				evi.setSelector(coll);
				IFDCReceivingBill fdcRece = FDCReceivingBillFactory.getRemoteInstance();
				FDCReceivingBillCollection collection = fdcRece.getFDCReceivingBillCollection(evi);
				if (collection != null && collection.size() > 0) {
					for (int j = 0; j < collection.size(); j++) {
						FDCReceivingBillInfo info = collection.get(j);
					
						if (info.getBillStatus().equals(RevBillStatusEnum.SAVE)) {
							MsgBox.showWarning(this, "所选单据状态不对，请检查！");
							SysUtil.abort();
							break;
						}else if (info.getBillStatus().equals(RevBillStatusEnum.SUBMIT)){
							MsgBox.showWarning(this, "所选单据状态不对，请检查！");
							SysUtil.abort();
							break;
						}else if (info.getBillStatus().equals(RevBillStatusEnum.AUDITING)){
							MsgBox.showWarning(this, "所选单据状态不对，请检查！");
							SysUtil.abort();
							break;
						}
					}
				}
			}
			
			
		} catch (BOSException e) {
			logger.error(e.getMessage()+"获取房地产售楼收款单状态失败!");
		}
		
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"所选收款单中已有部分单据生成出纳收款单，不允许删除操作！");
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"所选收款单中已有部分单据生成出纳收款单，不允许修改操作！");
		super.actionEdit_actionPerformed(e);
	}
	
	//已生成出纳收款单的记录不能进行"反审批"、"取消收款"操作 lww 12272010
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"所选收款单中已有部分单据生成出纳收款单，不允许修改操作！");
		super.actionUnAudit_actionPerformed(e);
	}
	
	public void actionCanceReceive_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"所选收款单中已有部分单据生成出纳收款单，不允许修改操作！");
		super.actionCanceReceive_actionPerformed(e);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	
		super.actionView_actionPerformed(e);
	}
	protected boolean isVerifyAdjustBillEditable() {
		return false;
	}
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"所选收款单中已有部分单据生成出纳收款单，请重新选择待生成凭证的收款单！");
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select top 1 bill.FID ");
		builder.appendSql(" from T_BDC_FDCRECEIVINGBILL bill ");
		builder.appendSql(" left join T_BDC_FDCReceivingBillEntry entry ");
		builder.appendSql(" on entry.FHeadID = bill.FID ");
		builder.appendSql(" where ");
		builder.appendParam("bill.FID", idList.toArray());
		builder.appendSql(" and (entry.FRevAccountID IS NULL or entry.FOppAccountID IS NULL) ");
		if(builder.isExist()){
			MsgBox.showWarning(this, "存在分录科目为空的记录，不能生成凭证!");
			SysUtil.abort();
		}
		
		super.actionVoucher_actionPerformed(e);
	}

	public void actionClearInvoice_actionPerformed(ActionEvent e) throws Exception {
		try{
			super.actionClearInvoice_actionPerformed(e);
			
			SysUtil.abort();
		}catch(Exception ex){
			logger.error(ex.getMessage()+"发票清除失败！");
		}
		
	}
	
	/**
	 * 根据收款单 批量 生成出纳系统的收款单 by lww 12102010
	 */
	public void actionBatchCreateBill_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList IdList = this.getSelectedIdValues();
		if (IdList == null || IdList.size() == 0) {
			FDCMsgBox.showWarning(this, "请选择要生成出纳收款单的记录！");
			SysUtil.abort();
		}
		batchCreatePaymentBillCheck(getReceivingBillCollection(IdList), IdList);

		try {
			FDCReceivingBillFactory.getRemoteInstance().createCashBill(IdList,
					false);
			FDCMsgBox.showWarning(this, "出纳收款单生成成功！");
			SysUtil.abort();
		} catch (Exception ex) {
			if (ex instanceof BOSException
					&& ex.getMessage().startsWith("科目不按指定币别核算")) {
				FDCMsgBox.showWarning(this, "科目不按指定币别核算，请选择其它币别！");
				SysUtil.abort();
			} else if (ex instanceof BOSException
					&& ex.getMessage().startsWith("生成出纳收款单失败")) {
				FDCMsgBox.showWarning(this, "生成出纳收款单失败！");
				SysUtil.abort();
			}
			ex.printStackTrace();
		}
	}

	/**
	 * 根据所选行的ID返回FDCReceivingBillCollection by lww 12102010
	 * @param IdList
	 * @return
	 */
	private FDCReceivingBillCollection getReceivingBillCollection (ArrayList IdList){
		Set IdSet = new HashSet(IdList);
		
		FilterItemInfo  IDFilter = new FilterItemInfo("id",IdSet,CompareType.INCLUDE);
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItemCollection = filter.getFilterItems();
		filterItemCollection.add(IDFilter);
		
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("revBillType"));
		coll.add(new SelectorItemInfo("billStatus"));
		coll.add(new SelectorItemInfo("fiVouchered"));
		coll.add(new SelectorItemInfo("isCreateBill"));
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(coll);
		
		try {
			IFDCReceivingBill fdcrece = FDCReceivingBillFactory.getRemoteInstance();
			FDCReceivingBillCollection collection = fdcrece.getFDCReceivingBillCollection(view);
			return collection;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 校验单据类型、单据状态、是否已经生成凭证、是否已经生成出纳收款单 by lww 12102010
	 * @param collection
	 * @param IdList
	 */
	private void batchCreatePaymentBillCheck(FDCReceivingBillCollection collection,ArrayList IdList){
	    String warningMsg = "存在不符合生成出纳收款单的记录，" +
	    		"请检查所选收款单记录的单据状态、单据类型、是否已生成出纳收款单、是否已生成凭证!";
		if(collection!=null && collection.size()>0){
			for(int i=0; i<collection.size(); i++){
				FDCReceivingBillInfo info = collection.get(i);
				//判断单据类型如果 不是 "收款"、"退款"、"调整"就警告(即类型为"转款"就警告）
				if(info.getRevBillType().equals(RevBillTypeEnum.transfer)){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}
				//判断单据状态如果 不是"已审批"、"已收款"就警告
				if (info.getBillStatus().equals(RevBillStatusEnum.SAVE)) {
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}else if (info.getBillStatus().equals(RevBillStatusEnum.SUBMIT)){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}else if (info.getBillStatus().equals(RevBillStatusEnum.AUDITING)){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}
				//判断单据是否已经生成凭证
				if(info.isFiVouchered()){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}
				//判断单据是否已经生成出纳收款单(先判断是否创建了出纳单这属性值，再查存储出纳单的表是否有这条记录)
				if(info.isIsCreateBill()){
					boolean result = findBillFromCasPaymentBillbyId(IdList);
					if(!result){
						FDCMsgBox.showWarning(this, warningMsg);
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * 根据收款单关联出纳收款单        by lww 12132010
	 */
	public void acitonRelatePaymentBill_actionPerformed(ActionEvent e) 
			throws Exception {
		ArrayList IdList = this.getSelectedIdValues();
		if (IdList == null || IdList.size() == 0) {
			FDCMsgBox.showWarning(this, "请选择记录行！");
			SysUtil.abort();
		}
		if (IdList != null && IdList.size() > 1) {
			FDCMsgBox.showWarning(this, "请选择单行数据进行此操作！");
			SysUtil.abort();
		}
		// 如果出纳单创建了，isCreateBill()返回的是false
		boolean billStatus = isCreateBill(IdList);
		if (billStatus) {
			FDCMsgBox.showWarning(this, "所选行没有生成收款出纳单，请重新选择!");
			SysUtil.abort();
		} else {
			// 出纳单创建了，但给删掉了
			boolean result = findBillFromCasPaymentBillbyId(IdList);
			if (result) {
				FDCMsgBox.showWarning(this, "所选行没有生成收款出纳单，请重新选择!");
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sourceBillId", IdList.get(0).toString(),
						CompareType.EQUALS));
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(coll);
		IReceivingBill rec = ReceivingBillFactory.getRemoteInstance();
		ReceivingBillCollection collection = rec
				.getReceivingBillCollection(view);
		String[] payMentBillIdList = new String[collection.size()];
		if (collection != null && collection.size() > 0) {
			for (int i = 0; i < collection.size(); i++) {
				ReceivingBillInfo info = collection.get(i);
				payMentBillIdList[i] = info.getId().toString();
			}
		}
		UIContext ctx = new UIContext(this);
		ctx.put(UIContext.IDLIST, payMentBillIdList);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(CasReceivingBillListUI.class.getName(), ctx, null,
						OprtState.VIEW);

		// CasReceivingBillListUI ui = (CasReceivingBillListUI)
		// uiWindow.getUIObject();
		// ui.setIsNeedDefaultFilter(false);
		uiWindow.show();
	}
	
	/*
	 * 设置图标 lww(non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnBatchCreateBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_newbatch"));
		this.btnRelatePaymentBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associateentry"));
	}
	/**
     * 是否应用在单据查询之前增加ID处理，为了保证单据头正确地融合。 默认增加。
     * 当有不为空的默认排序查询方案的时候，retun false，能让系统能够按照排序方案排序
     * xiaoao——liu
     * @return
     */
    protected boolean isOrderByIDForBill()
    {
    	IQuerySolutionFacade iQuery=null;
    	QuerySolutionInfo querySolution=null;
		try {
			iQuery = QuerySolutionFacadeFactory.getRemoteInstance();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
        String queryName = (getQueryInfo(this.mainQueryPK)).getFullName();
//        if (preference!=null)
//            return;
        String uiName=getQueryUiName();
        String name  =getQueryName();
        if (uiName==null)
        {
            uiName=this.getClass().getName();
        }
        if (name==null)
        {
            name=queryName;
        }
        if (iQuery!=null && name!=null)
        {
        	try {
				querySolution = iQuery.getDefaultSolution(uiName, name);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			EntityViewInfo ev =null;

	        if (querySolution!=null&&querySolution.getEntityViewInfo()!=null)
	        {
	            try {
					ev=Util.getInnerFilterInfo(querySolution);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
	        }
	        if(ev!=null){
	        	return false;
	        }
        }
       
        return true;
    }
}