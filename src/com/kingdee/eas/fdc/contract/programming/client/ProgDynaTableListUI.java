/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProgDynaTableListUI extends AbstractProgDynaTableListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgDynaTableListUI.class);
	// 获取有权限的组织
	protected Set authorizedOrgs = null;
    /**
     * output class constructor
     */
    public ProgDynaTableListUI() throws Exception
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

	protected String getEditUIName() {
		return ProgDynaTableListUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		initTable();
		initIsVisable();
	}

	protected String getEntityBOSType() throws Exception {
		return new ProgrammingInfo().getBOSType().toString();
	}


	/**
	 * 初始化数据
	 * 
	 * @param selectObjId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws Exception
	 */
	private void fetchAndFill(String selectObjId) throws EASBizException, BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null) {
			return;
		}
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				String selectObjId = getSelectObjId();
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
				treeMain.getLastSelectedPathComponent();
				if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
					if (selectObjId == null) {
						return null;
					}
					List listData = fetchData(selectObjId);
					listData = mergeData(listData);
					fillTable(listData);
				} else {
					tblMain.removeRows();
				}
				return null;
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		dialog.show();
	}

	/**
	 * 获取树型结点ID
	 * 
	 * @return
	 */
	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		}
		return null;
	}

	/**
	 * 获取出本工程项目最新版本框架合约及其引用合约的招标立项和合同的相关数据
	 * 
	 * @param selectObjID
	 * @param allData
	 * @return
	 */
	private List fetchData(String selectObjID) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		List allData = new ArrayList();
		builder.appendSql(" select  ccb.fid ccbfid,programming.FProjectID,pc.fid progId,pc.flevel progLevel,pc.flongNumber progLongNumber, ");
		builder.appendSql(" pc.fname_l2 progName,pc.famount progAmount,pc.fcontrolAmount progControlAmount,pc.FDescription_l2 remark, ");
		builder.appendSql(" pc.fbalance progBalance,pc.fcontrolBalance progControlBalance,cb.fid contractBillID,");
		//修改无文本合同名称、编码
		builder.appendSql(" isnull(cb.fname,pc.FWithOutTextName) contractName,supplier.fname_l2 supplierName,cb.famount contractAmount,ccb.famount changeAmount, ");
		//添加合同编码，业务日期
		builder.appendSql(" isnull(cb.fnumber,pc.FWithOutTextNumber) contractNumber,cb.fbookeddate bookeddate,");
		//预估合同变动，无文本合同，未签合同金额
		builder.appendSql("pc.FEstimateAmount estimateAmount,pc.FWithOutTextAmount withOutTextAmount ,pc.FBudgetAmount budgetAmount, ");
		
		builder.appendSql(" csb.FTotalSettlePrice FTotalSettlePrice,prb.FLatestPrice lastPrice from T_CON_ProgrammingContract pc ");
		builder.appendSql(" inner join T_CON_Programming programming on pc.FProgrammingID = programming.fid ");
		//添加合同编码，业务日期
		builder.appendSql(" left join (select cb1.fid,cb1.FSrcProID,cb1.FPartBID,cb1.fname,cb1.fstate,cb1.fisAmtWithoutCost, ");
		builder.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') then cb1.famount else null end famount, ");
		builder.appendSql(" cb1.fnumber fnumber,cb1.fbookeddate fbookeddate ");
		
		builder.appendSql(" from (select fid,FSrcProID,FPartBID,famount,fstate,fisAmtWithoutCost,  ");
		builder.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') then fname else null end fname, ");
		//添加合同编码，业务日期
		builder.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') then fnumber else null end fnumber, ");
		builder.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') then fbookeddate else null end fbookeddate ");
		
		builder.appendSql(" from T_CON_ContractBill) cb1 ");
		builder.appendSql(" ) cb on cb.FSrcProID = pc.fid and cb.fisAmtWithoutCost=0 ");

		builder.appendSql(" left join T_BD_Supplier supplier on cb.FPartBID = supplier.fid ");

		builder.appendSql(" left join (select fid,FContractBillID, ");
		builder.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') then famount else null end famount ");
		builder
				.appendSql(" from T_CON_ContractChangeBill where fhassettled = 0 UNION ALL select fid,FContractBillID,fbalanceAmount famount ");
		builder.appendSql(" from T_CON_ContractChangeBill where fhassettled = 1 ) ccb on  ccb.FContractBillID = cb.fid ");

		builder.appendSql(" left join (select fid,FContractBillID, ");
		builder
				.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') and FIsFinalSettle = 1 then FTotalSettlePrice else null end FTotalSettlePrice ");
		builder.appendSql(" from T_CON_ContractSettlementBill) csb on csb.FContractBillID = cb.fid  ");

		builder.appendSql(" left join (select fid,FContractId, ");
		builder.appendSql(" case when fstate in ('4AUDITTED','7ANNOUNCE','8VISA') then FLatestPrice else null end FLatestPrice ");
		builder.appendSql(" from T_CON_PayRequestBill) prb on prb.FContractId = cb.fid  ");

		builder.appendSql(" where programming.FIsLatest = 1 ");
		builder.appendSql(" and programming.fstate = '4AUDITTED' ");

		builder.appendSql(" and programming.FProjectID = '" + selectObjID + "' ");
		builder.appendSql(" order by pc.flongNumber");
		System.err.println(builder.getSql());
		try {
			IRowSet rowSet = builder.executeQuery();
			BigDecimal zero = FDCHelper.ZERO;
			while (rowSet.next()) {
				ProgDynaTableObject pdo = new ProgDynaTableObject();
				String ccbFid = rowSet.getString("ccbfid");// 变更单ID
				String id = rowSet.getString("progId");// 框架合约id
				int level = rowSet.getInt("progLevel");// 树层次
				String longNumber = rowSet.getString("progLongNumber");// 框架合约长编码
				String name = rowSet.getString("progName");// 框架合约名称
				BigDecimal amount = rowSet.getBigDecimal("progAmount");// 框架合约规划金额
				BigDecimal controlAmount = rowSet.getBigDecimal("progControlAmount");// 框架合约控制金额
				BigDecimal balance = rowSet.getBigDecimal("progBalance");// 框架合约规划余额
				BigDecimal controlBalance = rowSet.getBigDecimal("progControlBalance");// 框架合约控制余额
				// String inviteName = rowSet.getString("inviteName");// 招标立项名称
				String contractName = rowSet.getString("contractName");// 合同名称
				String supplierName = rowSet.getString("supplierName");// 签约乙方
				String remark = rowSet.getString("remark"); // 框架合约备注
				BigDecimal contractAmount = rowSet.getBigDecimal("contractAmount");// 签约金额
				BigDecimal changeAmount = rowSet.getBigDecimal("changeAmount");// 变更金额
				BigDecimal settleAmount = rowSet.getBigDecimal("FTotalSettlePrice");// 结算金额
				//添加合同编码，业务日期
				String contractNumber = rowSet.getString("contractNumber");
				Date bookeddate = rowSet.getDate("bookeddate");
				BigDecimal estimateAmount = rowSet.getBigDecimal("estimateAmount");// 预估合同变动
				BigDecimal withOutTextAmount = rowSet.getBigDecimal("withOutTextAmount");// 无文本合同
				BigDecimal budgetAmount = rowSet.getBigDecimal("budgetAmount");// 未签合同
				
				pdo.setCcbFid(ccbFid);
				pdo.setId(id);
				pdo.setLevel(level);
				pdo.setLongNumber(longNumber);
				pdo.setName(name);

				// 框架合约规划金额
				if (amount == null || amount.compareTo(zero) == 0) {
					pdo.setAmount(zero);
				} else {
					pdo.setAmount(amount);
				}
				// 框架合约控制金额
				if (controlAmount == null || controlAmount.compareTo(zero) == 0) {
					pdo.setControlAmount(zero);
				} else {
					pdo.setControlAmount(controlAmount);
				}
				// 框架合约规划余额
				if (balance == null || balance.compareTo(zero) == 0) {
					pdo.setBalance(zero);
				} else {
					pdo.setBalance(balance);
				}
				// 框架合约控制余额
				if (controlBalance == null || controlBalance.compareTo(zero) == 0) {
					pdo.setControlBalance(zero);
				} else {
					pdo.setControlBalance(controlBalance);
				}

				// pdo.setInvite(inviteName);
				pdo.setContractname(contractName);
				if (contractName != null) {
					pdo.setSignSec(supplierName);
				}
				// 签约金额
				if (contractAmount == null || contractAmount.compareTo(zero) == 0) {
					pdo.setSignUpAmount(zero);
				} else {
					pdo.setSignUpAmount(contractAmount);
				}
				// 变更金额
				if (changeAmount == null || changeAmount.compareTo(zero) == 0) {
					pdo.setChangeAmount(zero);
				} else {
					pdo.setChangeAmount(changeAmount);
				}
				// 结算金额
				if (settleAmount == null || settleAmount.compareTo(zero) == 0) {
					pdo.setSettleAmount(zero);
				} else {
					pdo.setSettleAmount(settleAmount);
				}
				//合同编码
				pdo.setContractNumber(contractNumber);
				// 业务日期
				pdo.setBookedDate(bookeddate);
				// 预估合同变动
				if (estimateAmount == null || estimateAmount.compareTo(zero) == 0) {
					pdo.setEstimateAmount(zero);
				} else {
					pdo.setEstimateAmount(estimateAmount);
				}
				// 无文本合同
				if (withOutTextAmount == null || withOutTextAmount.compareTo(zero) == 0) {
					pdo.setWithOutTextAmount(zero);
				} else {
					pdo.setWithOutTextAmount(withOutTextAmount);
				}
				// 未签合同
				if (budgetAmount == null || budgetAmount.compareTo(zero) == 0) {
					pdo.setBudgetAmount(zero);
				} else {
					pdo.setBudgetAmount(budgetAmount);
				}
				
				
				// 最新造价
				if (contractName != null) {
					Map lastAmtBatch = FDCUtils.getLastAmt_Batch(null, new String[] { rowSet.getString("contractBillID") });
					Object newCost = lastAmtBatch.get(rowSet.getString("contractBillID"));
					if (newCost == null || FDCHelper.toBigDecimal(newCost.toString()).compareTo(zero) == 0) {
						pdo.setNewCost(zero);
					} else {
						pdo.setNewCost(FDCHelper.toBigDecimal(newCost));
					}
				} else {
					pdo.setNewCost(zero);
				}
				pdo.setRemark(remark);
				allData.add(pdo);
			}

			// 由中标通知书生成合同的招标立项名称
			builder.clear();
			builder.appendSql(" select invite.fname ipName,con.fname conname from t_con_contractbill con ");
			builder.appendSql(" inner join T_INV_AcceptanceLetter accep on accep.fid = con.fsourcebillid ");
			builder.appendSql(" inner join t_inv_inviteProject invite on invite.fid = accep.FInviteProjectID ");
			builder.appendSql(" where con.FSrcProID = invite.FSrcProID ");
			IRowSet irowSet0 = builder.executeQuery();
			while (irowSet0.next()) {
				for (int i = 0; i < allData.size(); i++) {
					ProgDynaTableObject pdo = (ProgDynaTableObject) allData.get(i);
					if (pdo.getContractname() == null) {
						continue;
					}
					if (pdo.getContractname().equals(irowSet0.getString("conname"))) {
						pdo.setInvite(irowSet0.getString("ipName"));
						allData.add(i, pdo);
						allData.remove(i + 1);
					}
				}
			}

			// 招标立项未生成合同时取出招标立项名称及关联的框架合约信息
			List lists = new ArrayList();
			for (int i = 0; i < allData.size(); i++) {
				builder.clear();
				ProgDynaTableObject pdo = (ProgDynaTableObject) allData.get(i);
				if (i > 0) {
					if (pdo.getLongNumber().equals(((ProgDynaTableObject) allData.get(i - 1)).getLongNumber())) {
						continue;
					}
				}
				builder.appendSql(" select programming.FProjectID,pc.fid progId,pc.flevel progLevel,pc.flongNumber progLongNumber,");
				builder.appendSql(" pc.fname_l2 progName,pc.famount progAmount,pc.fcontrolAmount progControlAmount,");
				builder.appendSql(" pc.FDescription_l2 remark,pc.fbalance progBalance,pc.fcontrolBalance progControlBalance,ip.");
				builder.appendSql(" fname inviteName from T_CON_ProgrammingContract pc");
				builder.appendSql(" inner join T_CON_Programming programming on pc.FProgrammingID = programming.fid");
				builder.appendSql(" left join T_INV_InviteProject ip on ip.FSrcProID = pc.fid");
				builder.appendSql(" where programming.FIsLatest = 1");
				builder.appendSql(" and programming.fstate = '4AUDITTED'");
				builder.appendSql(" and pc.fid = '" + pdo.getId() + "'");
				IRowSet irowSet = builder.executeQuery();
				while (irowSet.next()) {
					ProgDynaTableObject pdto = new ProgDynaTableObject();
					String id = irowSet.getString("progId");// 框架合约id
					int level = irowSet.getInt("progLevel");// 树层次
					String longNumber = irowSet.getString("progLongNumber");// 框架合约长编码
					String name = irowSet.getString("progName");// 框架合约名称
					BigDecimal amount = irowSet.getBigDecimal("progAmount");// 框架合约规划金额
					BigDecimal controlAmount = irowSet.getBigDecimal("progControlAmount");// 框架合约控制金额
					BigDecimal balance = irowSet.getBigDecimal("progBalance");// 框架合约规划余额
					BigDecimal controlBalance = irowSet.getBigDecimal("progControlBalance");// 框架合约控制余额
					String inviteName = irowSet.getString("inviteName");// 招标立项名称
					pdto.setId(id);
					pdto.setLevel(level);
					pdto.setLongNumber(longNumber);
					pdto.setName(name);

					// 框架合约规划金额
					if (amount == null || amount.compareTo(zero) == 0) {
						pdto.setAmount(zero);
					} else {
						pdto.setAmount(amount);
					}
					// 框架合约控制金额
					if (controlAmount == null || controlAmount.compareTo(zero) == 0) {
						pdto.setControlAmount(zero);
					} else {
						pdto.setControlAmount(controlAmount);
					}
					// 框架合约规划余额
					if (balance == null || balance.compareTo(zero) == 0) {
						pdto.setBalance(zero);
					} else {
						pdto.setBalance(balance);
					}
					// 框架合约控制余额
					if (controlBalance == null || controlBalance.compareTo(zero) == 0) {
						pdto.setControlBalance(zero);
					} else {
						pdto.setControlBalance(controlBalance);
					}
					pdto.setInvite(inviteName);
					pdto.setSignUpAmount(zero);
					pdto.setSettleAmount(zero);
					pdto.setChangeAmount(zero);
					pdto.setNewCost(zero);
					lists.add(pdto);
				}
			}
			int srcSize = allData.size();
			int srcNewSize = lists.size();
			for (int i = 0; i < srcNewSize; i++) {
				ProgDynaTableObject pdto = (ProgDynaTableObject) lists.get(i);
				int flag = 0;
				for (int j = 0; j < srcSize; j++) {
					ProgDynaTableObject pdo = (ProgDynaTableObject) allData.get(j);
					if (pdo.getInvite() != null && pdo.getInvite().equals(pdto.getInvite())) {
						flag++;
					}
				}
				if (flag == 0) {
					if (!FDCHelper.isEmpty(pdto.getInvite())) {
						allData.add(pdto);
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}

		return allData;
	}

	/**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 0; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * 名称以缩进效果显示
	 */
	private void setNameDisplay() {
		int rowCount = tblMain.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = tblMain.getRow(i);
			int level = row.getTreeLevel();
			Object name = row.getCell("name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank + name.toString());
			}
		}
	}

	/**
	 * 表格融合设置
	 */
	private void setMergeColumn() {
		tblMain.checkParsed();
		tblMain.getGroupManager().setGroup(true);
		int longNumberTop = 1;
		boolean longNumberMerge = false;
		for (int i = 1; i < tblMain.getRowCount(); i++) {
			// 框架合约长编码
			Object topLN = tblMain.getCell(i - 1, "longNumber").getValue();
			Object bottomLN = tblMain.getCell(i, "longNumber").getValue();
			// 招标立项
			// Object topInvite = tblMain.getCell(i - 1, "invite").getValue();
			// Object bottomInvite = tblMain.getCell(i, "invite").getValue();
			// 合同名称
			// Object topContract = tblMain.getCell(i - 1, "contractName").getValue();
			// Object bottomContract = tblMain.getCell(i, "contractName").getValue();
			// 签约乙方
			// Object topSignSec = tblMain.getCell(i - 1, "signSec").getValue();
			// Object bottomSignSec = tblMain.getCell(i, "signSec").getValue();
			// 签约金额
			// Object topSignUpAmount = tblMain.getCell(i - 1, "signUpAmount").getValue();
			// Object bottomSignUpAmount = tblMain.getCell(i, "signUpAmount").getValue();

			if (topLN != null && bottomLN != null) {
				if (topLN.toString().equals(bottomLN.toString())) {
					if (tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 0, i, 0)) {
						longNumberMerge = true;
					}
					tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 1, i, 1);
					tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 2, i, 2);
					tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 3, i, 3);
					tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 4, i, 4);
					tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 5, i, 5);
					tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 13, i, 13);
					// if (topInvite != null && bottomInvite != null) {
					// if (topInvite.toString().equals(bottomInvite.toString())) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 6, i, 6);
					// }
					// } else if (topInvite == null && bottomInvite == null) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 6, i, 6);
					// }
					//
					// if (topContract != null && bottomContract != null) {
					// if (topContract.toString().equals(bottomContract.toString())) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 7, i, 7);
					// }
					// } else if (topContract == null && bottomContract == null) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 7, i, 7);
					// }
					//
					// if (topSignSec != null && bottomSignSec != null) {
					// if (topSignSec.toString().equals(bottomSignSec.toString())) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 8, i, 8);
					// }
					// } else if (topSignSec == null && bottomSignSec == null) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 8, i, 8);
					// }

					// if (topSignUpAmount != null && bottomSignUpAmount != null) {
					// if (topSignUpAmount.toString().equals(bottomSignUpAmount.toString())) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 9, i, 9);
					// }
					// } else if (topSignUpAmount == null && bottomSignUpAmount == null) {
					// tblMain.getMergeManager().mergeBlock(longNumberTop - 1, 9, i, 9);
					// }


				} else {
					longNumberMerge = false;
				}
			}
			if (!longNumberMerge) {
				longNumberTop = i + 1;
			}
		}
	}

	public List mergeData(List allData) {
		// 先把结果集中有相同变更签证指令单的记录去从
		for (int i = 0; i < allData.size(); i++) {
			if ((i + 1) == allData.size()) {
				break;
			}
			ProgDynaTableObject oneObj = (ProgDynaTableObject) allData.get(i);
			for (int j = i; j < allData.size(); j++) {
				if (i == j) {
					continue;
				}
				ProgDynaTableObject twoObj = (ProgDynaTableObject) allData.get(j);
				if (oneObj.getInvite() != null && twoObj.getInvite() != null && oneObj.getContractname() != null
						&& twoObj.getContractname() != null) {
					if (oneObj.getId().equals(twoObj.getId()) && oneObj.getInvite().equals(twoObj.getInvite())
							&& oneObj.getContractname().equals(twoObj.getContractname())) {
						if (oneObj.getCcbFid() != null && twoObj.getCcbFid() != null && oneObj.getCcbFid().equals(twoObj.getCcbFid())) {
							if (oneObj.getSettleAmount().compareTo(FDCHelper.ZERO) == 0) {
								oneObj.setSettleAmount(twoObj.getSettleAmount());
							}
							allData.remove(i);
							allData.add(i, oneObj);
							allData.remove(j);
							j--;
						}
					}
				} else if (oneObj.getInvite() == null && twoObj.getInvite() == null && oneObj.getContractname() != null
						&& twoObj.getContractname() != null) {
					if (oneObj.getId().equals(twoObj.getId()) && oneObj.getContractname().equals(twoObj.getContractname())) {
						if (oneObj.getCcbFid() != null && twoObj.getCcbFid() != null && oneObj.getCcbFid().equals(twoObj.getCcbFid())) {
							if (oneObj.getSettleAmount().compareTo(FDCHelper.ZERO) == 0) {
								oneObj.setSettleAmount(twoObj.getSettleAmount());
							}
							allData.remove(i);
							allData.add(i, oneObj);
							allData.remove(j);
							j--;
						}
					}
				}
			}
		}

		for (int i = 0; i < allData.size(); i++) {
			if ((i + 1) == allData.size()) {
				break;
			}
			ProgDynaTableObject oneObj = (ProgDynaTableObject) allData.get(i);
			for (int j = i; j < allData.size(); j++) {
				if (i == j) {
					continue;
				}
				ProgDynaTableObject twoObj = (ProgDynaTableObject) allData.get(j);
				if (oneObj.getInvite() != null && twoObj.getInvite() != null && oneObj.getContractname() != null
						&& twoObj.getContractname() != null) {
					if (oneObj.getId().equals(twoObj.getId()) && oneObj.getInvite().equals(twoObj.getInvite())
							&& oneObj.getContractname().equals(twoObj.getContractname())) {
						oneObj.setChangeAmount(oneObj.getChangeAmount().add(twoObj.getChangeAmount()));
						if (oneObj.getSettleAmount().compareTo(FDCHelper.ZERO) == 0) {
							oneObj.setSettleAmount(twoObj.getSettleAmount());
						}
						allData.remove(i);
						allData.add(i, oneObj);
						allData.remove(j);
						j--;
					}
				} else if (oneObj.getInvite() == null && twoObj.getInvite() == null && oneObj.getContractname() != null
						&& twoObj.getContractname() != null) {
					if (oneObj.getId().equals(twoObj.getId()) && oneObj.getContractname().equals(twoObj.getContractname())) {
						oneObj.setChangeAmount(oneObj.getChangeAmount().add(twoObj.getChangeAmount()));
						if (oneObj.getSettleAmount().compareTo(FDCHelper.ZERO) == 0) {
							oneObj.setSettleAmount(twoObj.getSettleAmount());
						}
						allData.remove(i);
						allData.add(i, oneObj);
						allData.remove(j);
						j--;
					}
				}
			}
		}
		return allData;
	}
	/**
	 * 绑字数据到table中
	 * 
	 * @param allData
	 * @throws Exception
	 */
	public void fillTable(List allData) throws Exception {
		tblMain.removeRows();
		if (allData.size() == 0)
			return;
		// 开始填充数据
		for (Iterator it = ((List) allData).iterator(); it.hasNext();) {
			ProgDynaTableObject pdo = (ProgDynaTableObject) it.next();
			IRow row = tblMain.addRow();
			row.setTreeLevel(pdo.getLevel() - 1);
			tblMain.getTreeColumn().setDepth(pdo.getLevel());
			row.getCell("id").setValue(pdo.getId());
			row.getCell("level").setValue(new Integer(pdo.getLevel()));
			row.getCell("longNumber").setValue(pdo.getLongNumber());
			row.getCell("name").setValue(pdo.getName());
			row.getCell("amount").setValue(pdo.getAmount());
			row.getCell("controlAmount").setValue(pdo.getControlAmount());
			row.getCell("balance").setValue(pdo.getBalance());
			row.getCell("controlBalance").setValue(pdo.getControlBalance());
			row.getCell("invite").setValue(pdo.getInvite());
			row.getCell("contractName").setValue(pdo.getContractname());
			row.getCell("signSec").setValue(pdo.getSignSec());
			row.getCell("signUpAmount").setValue(pdo.getSignUpAmount());
			row.getCell("changeAmount").setValue(pdo.getChangeAmount());
			row.getCell("newCost").setValue(pdo.getNewCost());
			row.getCell("settleAmount").setValue(pdo.getSettleAmount());
			row.getCell("remark").setValue(pdo.getRemark());
			
			row.getCell("estimateAmount").setValue(pdo.getEstimateAmount());
			row.getCell("withOutContent").setValue(pdo.getWithOutTextAmount());
			row.getCell("budgetAmount").setValue(pdo.getBudgetAmount());
			row.getCell("contractNumber").setValue(pdo.getContractNumber());
			row.getCell("date").setValue(pdo.getBookedDate());

			if (pdo.getBalance().compareTo(FDCHelper.ZERO) >= 0) {
				row.getCell("balance").getStyleAttributes().setFontColor(Color.blue);
			} else {
				row.getCell("balance").getStyleAttributes().setFontColor(Color.red);
			}
		}
		List rows = this.tblMain.getBody().getRows();
		Collections.sort(rows, new TableCellComparator(tblMain.getColumnIndex("longNumber"), KDTSortManager.SORT_ASCEND));
		for (int i = 1; i < rows.size(); i++) {
			if (tblMain.getCell(i - 1, "longNumber").getValue().toString().equals(tblMain.getCell(i, "longNumber").getValue().toString())) {
				if (FDCHelper.isEmpty(tblMain.getCell(i - 1, "invite").getValue())
						&& FDCHelper.isEmpty(tblMain.getCell(i - 1, "contractName").getValue())) {
					rows.remove(i - 1);
				}
			}
		}
		setMergeColumn();
	}

	public void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws EASBizException, BOSException, Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			tblMain.removeRows();
			return;
		}
		fetchAndFill(selectObjId);
		initTable();
		setNameDisplay();
		caclTotalAmount(tblMain);
		caclTotalMergeAmount(tblMain);
		// 填充完数据之后重新刷新tblMain
		tblMain.reLayoutAndPaint();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (FDCHelper.toBigDecimal(row.getCell("balance").getValue()).compareTo(FDCHelper.ZERO) >= 0) {
				row.getCell("balance").getStyleAttributes().setFontColor(Color.blue);
			} else {
				row.getCell("balance").getStyleAttributes().setFontColor(Color.red);
			}
		}
		FDCTableHelper.apendFootRow(tblMain, new String[]{"amount","controlAmount","controlBalance","signUpAmount","changeAmount","newCost","settleAmount","withOutContent","budgetAmount","estimateAmount","balance"});
	}
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}


	private int[] getFormatColumnKeys() {
		int[] formatColumnIndex = new int[11];
		String[] formatFields = new String[] { "amount", "controlAmount", "balance", "controlBalance", "signUpAmount", "changeAmount",
				"newCost", "settleAmount" ,"withOutContent","budgetAmount","estimateAmount"};
		for (int i = 0; i < formatFields.length; i++) {
			formatColumnIndex[i] = tblMain.getColumnIndex(formatFields[i]);
		}

		return formatColumnIndex;
	}

	private void initIsVisable() {
		menuFile.setVisible(false);
		menuEdit.setVisible(false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuTool.setVisible(false);
		menuWorkFlow.setVisible(false);

		btnAddNew.setVisible(false);
		btnView.setVisible(false);
		btnEdit.setVisible(false);
		btnRemove.setVisible(false);
//		btnRefresh.setVisible(false);
		btnQuery.setVisible(false);
		btnLocate.setVisible(false);
		btnAttachment.setVisible(false);
		btnPageSetup.setVisible(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCopyTo.setVisible(false);
		btnQueryScheme.setVisible(false);
		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		btnWorkFlowG.setVisible(false);
		btnWorkFlowList.setVisible(false);
		btnSignature.setVisible(false);
		btnViewSignature.setVisible(false);
		btnVoucher.setVisible(false);
		btnDelVoucher.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);
		btnAuditResult.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnWFViewdoProccess.setVisible(false);
	}

	private void initTable() {
		tblMain.getColumn("longNumber").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tblMain.getColumn("name").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tblMain.getColumn("invite").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tblMain.getColumn("contractName").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tblMain.getColumn("signSec").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		tblMain.getColumn("remark").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);

		FDCHelper.formatTableNumber(tblMain, getFormatColumnKeys());
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}

	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}


	// 覆盖父类方法
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {

	}

	public void initUIContentLayout() {
		super.initUIContentLayout();
		this.setBounds(new Rectangle(10, 10, 1250, 850));
		this.setLayout(new KDLayout());
		this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1250, 850));
		kDSplitPane1.setBounds(new Rectangle(10, 10, 1230, 830));
		this.add(kDSplitPane1, new KDLayout.Constraints(10, 10, 1230, 830, KDLayout.Constraints.ANCHOR_TOP
				| KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
		kDSplitPane1.add(kDContainer1, "right");
		kDSplitPane1.add(kDTreeView1, "left");
		kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));
		kDContainer1.getContentPane().add(tblMain, BorderLayout.CENTER);
		kDTreeView1.setTree(treeMain);
	}

	/**
	 * 非融合行金额类字段自动向上汇总
	 * 
	 * @param table
	 */
	private void caclTotalAmount(KDTable table) {
		int maxLevel = 0;
		int[] levelArray = new int[table.getRowCount()];
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}

		if (maxLevel == 1) {
			return;
		}
		while (maxLevel > 0) {
			List parentList = new ArrayList();
			Map columnMap = new HashMap();
			String[] sumColNames = getProSumFields();
			for (int j = 0; j < sumColNames.length; j++) {
				parentList.clear();
				// 先找去最深层次的数据
				for (int i = 0; i < table.getRowCount(); i++) {
					BigDecimal dbSum = FDCHelper.ZERO;
					columnMap.clear();
					int level = new Integer(table.getCell(i, "level").getValue().toString()).intValue();
					// 如果最后一行，如果最后一行是根节点。则不进行汇总计算
					if (i == table.getRowCount() - 1) {
						if (level == 1)
							continue;
					}
					boolean isHasChild = false;
					dbSum = FDCHelper.ZERO;
					String parentNumber = "";
					if (maxLevel == level) {
						if (level == 1) {
							parentNumber = table.getCell(i, "longNumber").getValue().toString();
						} else {
							String longNumberTemp = table.getCell(i, "longNumber").getValue().toString();
							parentNumber = longNumberTemp.substring(0, longNumberTemp.lastIndexOf('.'));
						}
						// 累加当前级下的金额。以上级编码为依据
						for (int k = i; k < table.getRowCount(); k++) {
							int level_k = new Integer(table.getCell(k, "level").getValue().toString()).intValue();
							String headNumber = "";
							String longNumberTemp = table.getCell(k, "longNumber").getValue().toString();
							if (level_k == 1) {
								continue;
							} else {
								headNumber = longNumberTemp.substring(0, longNumberTemp.lastIndexOf('.'));
							}
							if (headNumber.equals(parentNumber)) {
								ICell cell = table.getRow(k).getCell(sumColNames[j]);
								String cellValue = table.getCellDisplayText(cell);
								if (cellValue != null)
									cellValue = cellValue.toString().replaceAll(",", "");

								if (!StringUtility.isNumber(cellValue)) {
									Object cellObj = cell.getValue();
									if (cellObj != null)
										cellValue = cellObj.toString();
									if (!StringUtility.isNumber(cellValue))
										continue;
								}
								BigDecimal bigdem = new BigDecimal(String.valueOf(cellValue).trim());
								dbSum = dbSum.add(bigdem);
								isHasChild = true;
							}
						}
						columnMap.put(parentNumber, dbSum);
					}
					if (!isHasChild) {
						continue;
					}
					// 设置金额
					if (parentList.contains(parentNumber)) {
						continue;
					}
					if (columnMap.size() > 0) {
						for (int k = 0; k < table.getRowCount(); k++) {
							String lnumber = table.getCell(k, "longNumber").getValue().toString();
							if (columnMap.containsKey(lnumber)) {
								table.getCell(k, sumColNames[j]).setValue(columnMap.get(lnumber));
								parentList.add(parentNumber);
								break;
							}
						}
					}
				}
			}
			maxLevel--;
		}
	}

	/**
	 * 融合行金额类字段自动向上汇总
	 * 
	 * @param table
	 */
	private void caclTotalMergeAmount(KDTable table) {
		int maxLevel = 0;
		int[] levelArray = new int[table.getRowCount()];
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}

		if (maxLevel == 1) {
			return;
		}

		while (maxLevel > 0) {
			List parentList = new ArrayList();
			Map columnMap = new HashMap();
			String[] sumColNames = getMergeSumFields();

			for (int j = 0; j < sumColNames.length; j++) {
				parentList.clear();
				// 先找去最深层次的数据
				for (int i = 0; i < table.getRowCount(); i++) {
					BigDecimal dbSum = FDCHelper.ZERO;
					columnMap.clear();
					int level = new Integer(table.getCell(i, "level").getValue().toString()).intValue();
					// 如果最后一行，如果最后一行是根节点。则不进行汇总计算
					if (i == table.getRowCount() - 1) {
						if (level == 1)
							continue;
					}
					boolean isHasChild = false;
					dbSum = FDCHelper.ZERO;
					String parentNumber = "";
					if (maxLevel == level) {
						if (level == 1) {
							parentNumber = table.getCell(i, "longNumber").getValue().toString();
						} else {
							String longNumberTemp = table.getCell(i, "longNumber").getValue().toString();
							parentNumber = longNumberTemp.substring(0, longNumberTemp.lastIndexOf('.'));
						}
						// 累加当前级下的金额。以上级编码为依据
						for (int k = i; k < table.getRowCount(); k++) {
							int level_k = new Integer(table.getCell(k, "level").getValue().toString()).intValue();
							String headNumber = "";
							String longNumberFor = table.getCell(k, "longNumber").getValue().toString();
							if (level_k == 1) {
								continue;
							} else {
								headNumber = longNumberFor.substring(0, longNumberFor.lastIndexOf('.'));
							}
							if (headNumber.equals(parentNumber)) {
								ICell cell = table.getRow(k).getCell(sumColNames[j]);
								String cellValue = table.getCellDisplayText(cell);
								if (cellValue != null)
									cellValue = cellValue.toString().replaceAll(",", "");
								if (!StringUtility.isNumber(cellValue)) {
									Object cellObj = cell.getValue();
									if (cellObj != null)
										cellValue = cellObj.toString();
									if (!StringUtility.isNumber(cellValue))
										continue;
								}
								BigDecimal bigdem = new BigDecimal(String.valueOf(cellValue).trim());
								KDTMergeBlock mergeBlockOfCell = table.getMergeManager().getMergeBlockOfCell(k,
										table.getColumnIndex("balance"));
								if (mergeBlockOfCell != null) {
									if (mergeBlockOfCell.getBottom() == k) {
										dbSum = dbSum.add(bigdem);
									}
								} else {
									dbSum = dbSum.add(bigdem);
								}
								isHasChild = true;
							}
						}
						columnMap.put(parentNumber, dbSum);
					}
					if (!isHasChild) {
						continue;
					}
					// 设置金额
					if (parentList.contains(parentNumber)) {
						continue;
					}
					if (columnMap.size() > 0) {
						for (int k = 0; k < table.getRowCount(); k++) {
							String lnumber = table.getCell(k, "longNumber").getValue().toString();
							if (columnMap.containsKey(lnumber)) {
								table.getCell(k, sumColNames[j]).setValue(columnMap.get(lnumber));
								parentList.add(parentNumber);
								break;
							}
						}
					}
				}
			}
			maxLevel--;
		}
	}

	private String[] getMergeSumFields() {
		return new String[] { "amount", "controlAmount", "balance", "controlBalance" };
	}
	private String[] getProSumFields() {
		return new String[] { "signUpAmount", "changeAmount", "settleAmount", "newCost" };
	}
}