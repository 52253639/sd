/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FullDyContractInfoUI extends AbstractFullDyContractInfoUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FullDyContractInfoUI.class);

	/**
	 * output class constructor
	 */
	public FullDyContractInfoUI() throws Exception {
		super();
	}

	/**
	 * 判断选择行是不是无文本合同，选择多行返回false
	 * @author pengling
	 */
	private static final BOSObjectType withoutTxtConBosType = new ContractWithoutTextInfo()
			.getBOSType();

	private boolean isConWithoutTxt(KDTable table) {
		boolean isConWithoutTxt = false;
		int selectRows[] = KDTableUtil.getSelectedRows(table);
		if (selectRows.length == 1) {
			Object obj = table.getCell(selectRows[0], "contractID").getValue();
			if (obj != null) {
				String contractId = obj.toString();
				isConWithoutTxt = BOSUuid.read(contractId).getType().equals(
						withoutTxtConBosType);
			}
		}
		return isConWithoutTxt;
	}

	/**
	 * 查看合同录入
	 * @author pengling Date 2009-5-25
	 */
	private void viewContract() throws Exception {
		
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
//			MsgBox.showWarning(this, FDCSplitClientHelper
//					.getRes("multiRowSelected"));
//			SysUtil.abort();
		} else if (selectRows.length == 1) {
			if (!isConWithoutTxt(getMainTable())) {
				Object obj = getMainTable()
						.getCell(selectRows[0], "contractID").getValue();
				if (obj != null) {
					String contractId = obj.toString();
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.ID, contractId);
					IUIWindow contractBillEditUI = UIFactory.createUIFactory(
							UIFactoryName.NEWTAB).create(
							ContractBillEditUI.class.getName(), uiContext,
							null, OprtState.VIEW);
					contractBillEditUI.show();
				}
			} else {
				Object obj = getMainTable()
						.getCell(selectRows[0], "contractID").getValue();
				if (obj != null) {
					String contractId = obj.toString();
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.ID, contractId);
					IUIWindow conWithoutTextEditUI = UIFactory
							.createUIFactory(UIFactoryName.NEWTAB)
							.create(
									ContractWithoutTextEditUI.class
											.getName(), uiContext, null,
									OprtState.VIEW);
					conWithoutTextEditUI.show();
				}
			}
		}
	}

	/**
	 * @author ling_peng
	 */
	protected void showContractEditUI_tableClicked(KDTMouseEvent e)
			throws Exception {
		// 单击
		if (e.getClickCount() == 1) {
			// ...do nothing
		}
		// 双击
		else if (e.getClickCount() == 2) {
			viewContract();
		}
	}

	private KDTable getMainTable() {
		return kDTable1;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// tHelper.addMenuToTable(getMainTable());
		
		FDCTableHelper.setColumnMoveable(getMainTable(), true);
		getMainTable().checkParsed();
		// getMainTable().setSortMange(new KDTSortManager(getMainTable()));
		FDCHelper.formatTableNumber(getMainTable(), new String[] { "amt",
				"splitAmt", "settleAmt", "settleSplitAmt" });

		Map dataMap = (Map) getUIContext().get("dataMap");
		String acctId = (String) dataMap.get("acctId");

		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*
		 * builder.appendSql("select FState, FNumber, FName, FAmount, sum(splitAmt) splitAmt, FSettleAmt, sum(setSplitAmt) setSplitAmt from ("
		 * );builder.appendSql(
		 * "select con.FState, con.FNumber, con.FName, con.FAmount, entry.FAmount splitAmt, con.FSettleAmt,"
		 * );builder.appendSql(
		 * "(select sum(b.FAmount) FAmount from T_CON_SettlementCostSplit a inner join T_CON_SettlementCostSplitEntry b on a.fid = b.fparentid and b.fisleaf=1 inner join T_CON_ContractSettlementBill c on a.FSettlementBillID = c.fid where c.FContractBillID = con.FID "
		 * ); builder.appendSql(" and  b.FCostAccountID = ");
		 * builder.appendParam(acctId); builder.appendSql(" and a.FState != ");
		 * builder.appendParam(FDCBillStateEnum.INVALID_VALUE);
		 * builder.appendSql(" group by c.FID) setSplitAmt");builder.appendSql(
		 * " from T_CON_ContractCostSplit head inner join T_CON_ContractCostSplitEntry entry on head.fid = entry.FParentID inner join T_CON_ContractBill con on head.FContractBillID = con.fid"
		 * );
		 * builder.appendSql(" where entry.FIsLeaf = 1 and entry.FCostAccountID = "
		 * ); builder.appendParam(acctId);
		 * builder.appendSql(" and head.FState != ");
		 * builder.appendParam(FDCBillStateEnum.INVALID_VALUE);
		 * builder.appendSql
		 * (") x group by FState, FNumber, FName, FAmount, FSettleAmt");
		 */
		/*
		 * builder.appendSql(" SELECT cb.FHassettled, cb.FNumber, cb.FName, cb.FAmount, cssum.SPLITAMT,cssum.ProductName, cb.FSettleAmt, sssum.SETSPLITAMT "
		 * ); builder.appendSql(" FROM T_CON_ContractBill cb ");
		 * builder.appendSql(
		 * " INNER JOIN (SELECT ccs.FContractBillID,sum(ccse.FAmount) SPLITAMT,prod.fname_l2 ProductName FROM T_CON_ContractCostSplit ccs "
		 * );builder.appendSql(
		 * " 	INNER JOIN T_CON_ContractCostSplitEntry ccse ON ccs.fid = ccse.FParentID "
		 * );builder.appendSql(
		 * " 	left outer JOIN T_FDC_ProductType prod ON ccse.fproductid = prod.fid "
		 * );
		 * builder.appendSql(" 	WHERE (ccse.FIsLeaf = 1 AND ccse.FCostAccountID = "
		 * ); builder.appendParam(acctId);
		 * builder.appendSql(" ) AND ccs.FState != ");
		 * builder.appendParam(FDCBillStateEnum.INVALID_VALUE);
		 * builder.appendSql(
		 * " 	GROUP BY ccs.FContractBillID,prod.fname_l2) cssum ON cssum.FContractBillID=cb.FID "
		 * );builder.appendSql(
		 * " LEFT JOIN (SELECT csb.FContractBillID,sum(scse.FAmount) SETSPLITAMT FROM T_CON_SettlementCostSplit scs "
		 * );builder.appendSql(
		 * " 	INNER JOIN T_CON_SettlementCostSplitEntry scse ON scs.fid = scse.fparentid "
		 * );builder.appendSql(
		 * " 	INNER JOIN T_CON_ContractSettlementBill csb ON scs.FSettlementBillID = csb.fid "
		 * );
		 * builder.appendSql(" 	WHERE (scse.FIsLeaf = 1 AND scse.FCostAccountID = "
		 * ); builder.appendParam(acctId);
		 * builder.appendSql(" ) AND scs.FState != ");
		 * builder.appendParam(FDCBillStateEnum.INVALID_VALUE);
		 * builder.appendSql(
		 * " 	GROUP BY csb.FContractBillID) sssum ON sssum.FContractBillID=cb.FID order by cb.fnumber"
		 * );
		 */
		
		
		/*
		 *@author pengling
		 */
		builder
				.appendSql(" Select cb.FID, cb.FHassettled, cb.FNumber, cb.FName, cb.FAmount, cssum.SPLITAMT SPLITAMT,prod.fname_l2 ProductName, cb.FSettleAmt, sssum.SETSPLITAMT  SETSPLITAMT \n");
		builder.appendSql(" FROM T_CON_ContractBill cb  INNER JOIN (\n");
		builder
				.appendSql("      SELECT ccs.FContractBillID,sum(ccse.FAmount) SPLITAMT,ccse.fproductid \n");
		builder
				.appendSql("      FROM T_CON_ContractCostSplit ccs  	INNER JOIN T_CON_ContractCostSplitEntry ccse ON ccs.fid = ccse.FParentID  	\n");
		builder
				.appendSql("      WHERE (ccse.FIsLeaf = 1 AND ccse.FCostAccountID = ? ) AND ccs.FState != ? 	\n");
		builder
				.appendSql("      GROUP BY ccs.FContractBillID,ccse.fproductid \n");
		builder.appendSql(" ) cssum ON cssum.FContractBillID=cb.FID  \n");
		builder.appendSql(" LEFT JOIN (\n");
		builder
				.appendSql("      SELECT csb.FContractBillID,sum(scse.FAmount) SETSPLITAMT ,scse.fproductid FROM T_CON_SettlementCostSplit scs  	\n");
		builder
				.appendSql("      INNER JOIN T_CON_SettlementCostSplitEntry scse ON scs.fid = scse.fparentid  	\n");
		builder
				.appendSql("      INNER JOIN T_CON_ContractSettlementBill csb ON scs.FSettlementBillID = csb.fid  	\n");
		builder
				.appendSql("      WHERE (scse.FIsLeaf = 1 AND scse.FCostAccountID = ? ) AND scs.FState != ? 	\n");
		builder
				.appendSql("      GROUP BY csb.FContractBillID,scse.fproductid \n");
		//db2 数据类型必须一致  by hpw
//		builder
//		.appendSql(" ) sssum ON sssum.FContractBillID=cb.FID And cssum.FContractBillID=sssum.FContractBillID And (isNull(cssum.fproductid,0)=isNull(sssum.fproductid,0))  \n");
		builder
				.appendSql(" ) sssum ON sssum.FContractBillID=cb.FID And cssum.FContractBillID=sssum.FContractBillID And (isNull(cssum.fproductid,'0')=isNull(sssum.fproductid,'0'))  \n");
		builder
				.appendSql(" left outer JOIN T_FDC_ProductType prod ON cssum.fproductid = prod.fid \n");
		builder.appendSql(" order by cb.fnumber,prod.fid \n");
		builder.addParam(acctId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		builder.addParam(acctId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		ResultSet set = builder.executeQuery();
		BigDecimal amtTotal = FDCHelper.ZERO;
		BigDecimal splitAmtTotal = FDCHelper.ZERO;
		BigDecimal settleAmtTotal = FDCHelper.ZERO;
		BigDecimal setSplitAmtTotal = FDCHelper.ZERO;

		boolean b = false;
		String oldNumber = null;
		KDTMergeManager mm = getMainTable().getMergeManager();
		//以下5列按合同进行行融合
		int stateCol = getMainTable().getColumnIndex("state");
		int numberCol = getMainTable().getColumnIndex("number");
		int nameCol = getMainTable().getColumnIndex("name");
		int amtCol = getMainTable().getColumnIndex("amt");
		int settleAmtCol = getMainTable().getColumnIndex("settleAmt");
		
		int beginRow = 0;
		int endRow = 0;
		while (set.next()) {
			b = true;
			boolean hasSettled = set.getBoolean("FHassettled");
			String number = set.getString("FNumber");
			String name = set.getString("FName");
			BigDecimal amount = set.getBigDecimal("FAmount");
			BigDecimal splitAmt = set.getBigDecimal("splitAmt");
			BigDecimal settleAmt = set.getBigDecimal("FSettleAmt");
			BigDecimal setSplitAmt = set.getBigDecimal("setSplitAmt");
			String productName = set.getString("ProductName");
			
			/*
			 *@author pengling
			 */
			String contractID = set.getString("FID");

			
			IRow row = getMainTable().addRow();
			String state = null;
			if (hasSettled) {
				state = "已结算";
			} else {
				state = "未结算";
			}
			/*
			 * @author pengling
			 */
			row.getCell("contractID").setValue(contractID);

			
			row.getCell("state").setValue(state);
			row.getCell("number").setValue(number);
			row.getCell("name").setValue(name);
			row.getCell("amt").setValue(amount);
			row.getCell("splitAmt").setValue(splitAmt);
			row.getCell("settleAmt").setValue(settleAmt);
			row.getCell("settleSplitAmt").setValue(setSplitAmt);
			row.getCell("product").setValue(productName);

			if (amount != null && !number.equals(oldNumber)) {
				amtTotal = amtTotal.add(amount);
			}
			if (splitAmt != null) {
				splitAmtTotal = splitAmtTotal.add(splitAmt);
			}
			if (settleAmt != null && !number.equals(oldNumber)) {
				settleAmtTotal = settleAmtTotal.add(settleAmt);
			}
			if (setSplitAmt != null) {
				setSplitAmtTotal = setSplitAmtTotal.add(setSplitAmt);
			}
			//按合同进行行融合
			if(!number.equals(oldNumber)){
				endRow = getMainTable().getRowCount()-1-1;//索引从0开始，故减1；编码不一样时，已经是另外的合同了，故再减1
				if(endRow > beginRow){
					mm.mergeBlock(beginRow,stateCol,endRow,stateCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,numberCol,endRow,numberCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,nameCol,endRow,nameCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,amtCol,endRow,amtCol,KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow,settleAmtCol,endRow,settleAmtCol,KDTMergeManager.SPECIFY_MERGE);
				}
				
				oldNumber = number;
				beginRow = getMainTable().getRowCount()-1;//索引从0开始，故减1
			}
		}
		//如果最后几行需要融合，上面逻辑无法处理，在此处理
		endRow = getMainTable().getRowCount()-1;//索引从0开始，故减1
		if(endRow > beginRow){
			mm.mergeBlock(beginRow,stateCol,endRow,stateCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,numberCol,endRow,numberCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,nameCol,endRow,nameCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,amtCol,endRow,amtCol,KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow,settleAmtCol,endRow,settleAmtCol,KDTMergeManager.SPECIFY_MERGE);
		}
		if (b) {
			IRow totalRow = getMainTable().addRow();
			totalRow.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			totalRow.getCell("state").setValue("合计");
			totalRow.getCell("amt").setValue(amtTotal);
			totalRow.getCell("splitAmt").setValue(splitAmtTotal);
			totalRow.getCell("settleAmt").setValue(settleAmtTotal);
			totalRow.getCell("settleSplitAmt").setValue(setSplitAmtTotal);
		}
		getMainTable().getStyleAttributes().setLocked(true);
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
}