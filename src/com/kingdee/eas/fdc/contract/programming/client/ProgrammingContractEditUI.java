/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.invite.InviteFormEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProgrammingContractEditUI extends AbstractProgrammingContractEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(ProgrammingContractEditUI.class);

	protected KDWorkButton btnAddnewLine_cost;
	protected KDWorkButton btnRemoveLines_cost;
	protected KDWorkButton btnAddnewLine_economy;
	protected KDWorkButton btnRemoveLines_economy;

	private ProgrammingContractInfo pcInfo;
	private ProgrammingContractInfo oldPcInfo;
	private ProgrammingContractCollection pcCollection;

	private BigDecimal oldAmount;
	private BigDecimal oldControlAmount;
	private BigDecimal oldbalance;
	private BigDecimal oldcontrolBalance;

	// 成本构成分录表格列名
	private static final String COST_ID = "id";// ID
	private static final String PROJECT = "project";// 工程项目(F7)
	private static final String COSTACCOUNT_NUMBER = "number";// 成本科目编码
	private static final String COSTACCOUNT = "name";// 成本科目名称(F7)
	private static final String GOALCOST = "goalCost";// 目标成本
	private static final String ASSIGNED = "assigned";// 已分配
	private static final String ASSIGNING = "assigning";// 待分配
	private static final String CONTRACTASSIGN = "contractAssign";// 本合约分配
	private static final String COST_DES = "description";// 备注

	// 经济条款分录表格列名
	private static final String ECONOMY_ID = "id";// ID
	private static final String PAYMENTTYPE = "paymentType";// 付款类型
	private static final String SCALE = "scale";// 付款比例
	private static final String AMOUNT = "amount";// 付款金额
	private static final String PAYMENTDATE = "paymentDate";// 付款日期
	private static final String CONDITION = "condition";// 付款条件
	private static final String ECONOMY_DES = "description";// 备注

	public ProgrammingContractEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initFormat();
		setSmallButton();
		if (this.oprtState.equals(OprtState.VIEW)) {
			isEditable(false);
			ctrButtonEnable(false);
		} else if (this.oprtState.equals(OprtState.EDIT) || this.oprtState.equals(OprtState.ADDNEW)) {
			isEditable(true);
			ctrButtonEnable(true);
		}

		txtAmount.addDataChangeListener(new DataChangeListener() {
			/*
			 * 规划金额改变后需要处理的事情有以下几种情况
			 * 1. 判断规划金额录入的数值是否小于0
			 * 2. 经济条款—付款金额依据原付款比例动态改变
			 * 3.
			 */
			public void dataChanged(DataChangeEvent e) {
				if (e.getOldValue() != e.getNewValue()) {
					/**
					 * 经济条款分录动态变化情况
					 */
					int economyRowCount = kdtEconomy.getRowCount();
					BigDecimal planAmount = null;// 规划金额
					if (!FDCHelper.isEmpty(e.getNewValue())) {
						planAmount = new BigDecimal(e.getNewValue().toString());
						/* 1.判断规划金额录入的数值是否小于0 */
						// if (planAmount.compareTo(new BigDecimal(0)) <= 0) {
						// FDCMsgBox.showInfo("\"规划金额\"不能小于0");
						// txtAmount.setValue(e.getOldValue());
						// SysUtil.abort();
						// }
						/* 2.经济条款—付款金额依据原付款比例动态改变 */
						// 2.1 规划金额变不为空值情况——付款金额 = 原付款比例*新规划金额
						for (int i = 0; i < economyRowCount; i++) {
							// BigDecimal amount = null;
							Object obj = kdtEconomy.getCell(i, SCALE).getValue();
							if (obj != null) {
								BigDecimal scale = new BigDecimal(obj.toString());
								scale = FDCHelper.divide(scale, new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
								kdtEconomy.getCell(i, AMOUNT).setValue(scale.multiply(planAmount));
							}
						}
					} else {
						// 2.2 规划金额变为空值情况——付款金额全部置null
						for (int i = 0; i < economyRowCount; i++) {
							kdtEconomy.getCell(i, AMOUNT).setValue(null);
						}
					}

					/**
					 * 成本构成分录动态变化情况
					 */
				}
			}
		});
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		btnAddRowinfo = (KDWorkButton)kdContainerCost.add(this.actionSelect);
		btnAddRowinfo.setText("成本科目选择");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionEdit.setVisible(false);
	}
	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		Map ctx = new HashMap();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
//		ctx.put("query", this.getQueryInfo());
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		ctx.put("view", entityView);
		ctx.put("col", this.pcCollection);
		ctx.put("aimCost", (AimCostInfo) this.getUIContext().get("aimCostInfo"));
		try  {
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow costAccountDialog = uiFactory.create(CostAccountF7UI.class.getName(),ctx,null,OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	        costAccountDialog.show();
	        CostAccountF7UI caF7UI = (CostAccountF7UI) costAccountDialog.getUIObject();
	        CostAccountInfo [] newCostAccount=null;
	        if(!caF7UI.isCancel()){
	        	newCostAccount=caF7UI.getData();
	        }
	        if(newCostAccount!=null&&newCostAccount.length>0){
	        	
	        	List account=new ArrayList();
	        	for(int j=0;j<newCostAccount.length;j++){
	        		boolean isAdd=true;
	        		CostAccountInfo newCostAccountInfo=newCostAccount[j];
	        		for(int i=0;i<kdtCost.getRowCount();i++){
		        		CostAccountInfo costAccountObj = (CostAccountInfo)kdtCost.getCell(i, COSTACCOUNT).getValue();
	        			if(costAccountObj!=null&&newCostAccountInfo.getId().equals(costAccountObj.getId())){
	        				isAdd=false;
	        				break;
	        			}
	        		}
	        		if(isAdd){
	        			account.add(newCostAccountInfo);
	        		}
        		}
	        	BigDecimal allAssigned = FDCHelper.ZERO;
				for(int i=0;i<account.size();i++){
					CostAccountInfo newCostAccountInfo=(CostAccountInfo) account.get(i);
//					for(int j=0;j<kdtCost.getRowCount();j++){
//						if (isCostAccountDup(newCostAccountInfo, project, j)) {
//							continue;
//						}
//					}
					IRow row=kdtCost.addRow();
					int rowIndex=row.getRowIndex();
					kdtCost.getCell(rowIndex, COSTACCOUNT).setValue(newCostAccountInfo);
					row.getCell(COST_ID).setValue(BOSUuid.create("9E6FDD26"));
					row.getCell(PROJECT).setValue(project);
					//初始化 目标成本，已分配，待分配，本合约分配
					row.getCell(GOALCOST).setValue(FDCHelper.ZERO);
					row.getCell(ASSIGNED).setValue(FDCHelper.ZERO);
					row.getCell(ASSIGNING).setValue(FDCHelper.ZERO);
					row.getCell(CONTRACTASSIGN).setValue(FDCHelper.ZERO);
					projectF7();
					costAccountCellF7(project, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);
					
					kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(newCostAccountInfo.getLongNumber().replace('!', '.'));
					// 2.
					AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// 目标成本
					if (aimCostInfo == null) {
						// 2.1
//						ProgrammingContractUtil.clearCell(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
						ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2是否是已审批
						if (!isAimCostAudit(aimCostInfo)) {
							// 2.2.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
						} else {
							// 2.2.2 取出目标成本的值
							// CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
							if (project != null) {
								BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo,
										aimCostInfo);
								if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
									ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
									afterContractAssignChange();
									afterPlanAmountChange();
								} else {
									allAssigned = getAllContractAssign(newCostAccountInfo, false);// 已分配
									// 算出"待分配" == "目标成本" - "已分配"
									BigDecimal assigning = goalCost.subtract(allAssigned);// 待分配
									// 带出"本合约分配"="待分配"
									BigDecimal contractAssign = assigning;// 本合约分配
									// 显示在单元格中
									kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// 目标成本
									kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// 已分配
									kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// 待分配
									kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// 本合约分配

									// 本合约分配带出后又自动算出"规划金额"
									afterContractAssignChange();
									// 自动算出"规划金额"后，动态改变经济条款中"付款比例"和"付款金额"
									// 默认以"付款比例"为定值，改变"付款金额"
									afterPlanAmountChange();
								}
							}
						}
					}
				}
	        }
		}catch (BOSException ex) {
	        ExceptionHandler.handle(ex);
	        SysUtil.abort();
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		initTabalFormat();
		this.btnAttachment.setIcon(EASResource.getIcon("imgTbtn_affixmanage"));
		this.txtParentLongName.setEditable(false);// 上级合约框架长名称不可编辑
		this.txtAttachment.setEditable(false);// 附件文本框仅提供浏览，可根据操作状态决定在附件管理器里是编辑状态还是查看
		Map uiContext = this.getUIContext();
		Object object = uiContext.get("inviteProject");
		Object contractBillProg = uiContext.get("programmingContractTemp");
		if (object != null || contractBillProg != null) {
			this.btnSave.setVisible(false);// 招标立项关联里查看进入，把保存按钮去掉
		}
		pcInfo = (ProgrammingContractInfo) uiContext.get("programmingContract");
		if (pcInfo.getId() == null) {
			pcInfo.setId(BOSUuid.create(pcInfo.getBOSType()));
		}
		oldPcInfo = pcInfo;

		oldAmount = pcInfo.getAmount();
		if(oldAmount==null){
			oldAmount = FDCHelper.ZERO;
		}
		oldControlAmount = pcInfo.getControlAmount();
		if(oldControlAmount == null){
			oldControlAmount = FDCHelper.ZERO;
		}
		oldbalance = pcInfo.getBalance();
		if (oldbalance == null) {
			oldbalance = FDCHelper.ZERO;
		}
		oldcontrolBalance = pcInfo.getControlBalance();
		if (oldcontrolBalance == null) {
			oldcontrolBalance = FDCHelper.ZERO;
		}
		pcCollection = (ProgrammingContractCollection) uiContext.get("pcCollection");
		preparePCData();
		
		txtName.setDocument(new LimitedLengthDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("^\\.+$")) {
					return;
				}
				super.insertString(offs, str, a);
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);
			}
		});
		txtName.setText(pcInfo.getName() == null ? null : pcInfo.getName().trim());
		
		if(pcInfo.getId()==null||!this.oprtState.equals(OprtState.VIEW)){
			this.btnEdit.setEnabled(false);
			this.btnAddnewLine_economy.setEnabled(false);
			this.btnRemoveLines_economy.setEnabled(false);
		}
		if(pcInfo.getId()!=null&&this.oprtState.equals(OprtState.VIEW)){
			this.btnEdit.setEnabled(true);
			this.btnAddnewLine_economy.setEnabled(true);
			this.btnRemoveLines_economy.setEnabled(true);
		}
	}

	/**
	 * 在成本构成和经济条款页签中添加新增、删除按钮
	 */
	private void setSmallButton() {
		btnAddnewLine_cost = new KDWorkButton();
		btnRemoveLines_cost = new KDWorkButton();
		btnAddnewLine_economy = new KDWorkButton();
		btnRemoveLines_economy = new KDWorkButton();

		btnAddnewLine_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_cost_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnRemoveLines_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_cost_actionPerformed(e);
					if (kdtCost.getRowCount() == 0) {
						btnRemoveLines_cost.setEnabled(false);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddnewLine_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_enocomy_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveLines_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_enocomy_actionPerformed(e);
					if (kdtEconomy.getRowCount() == 0) {
						btnRemoveLines_economy.setEnabled(false);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setButtonStyle(kdContainerCost, btnAddnewLine_cost, "新增行", "imgTbtn_addline");
		setButtonStyle(kdContainerCost, btnRemoveLines_cost, "删除行", "imgTbtn_deleteline");
		setButtonStyle(kDContainerEconomy, btnAddnewLine_economy, "新增行", "imgTbtn_addline");
		setButtonStyle(kDContainerEconomy, btnRemoveLines_economy, "删除行", "imgTbtn_deleteline");

		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
		} else {
			setButtionEnable(true);
		}
	}

	/**
	 * 设置按钮样式
	 * 
	 * @param kdContainer
	 * @param button
	 * @param text
	 * @param icon
	 */
	private void setButtonStyle(KDContainer kdContainer, KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		kdContainer.addButton(button);
	}

	/**
	 * 控制按钮是否可用
	 * 
	 * @param isEnable
	 */
	private void setButtionEnable(boolean isEnable) {
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(isEnable);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(isEnable);
		this.actionSelect.setEnabled(isEnable);
	}
	protected void actionAddnewLine_cost_actionPerformed(ActionEvent e) {
		IRow row = kdtCost.addRow();
		int rowIndex = row.getRowIndex();

		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
		row.getCell(COST_ID).setValue(BOSUuid.create("9E6FDD26"));
		row.getCell(PROJECT).setValue(project);
		//初始化 目标成本，已分配，待分配，本合约分配
		row.getCell(GOALCOST).setValue(FDCHelper.ZERO);
		row.getCell(ASSIGNED).setValue(FDCHelper.ZERO);
		row.getCell(ASSIGNING).setValue(FDCHelper.ZERO);
		row.getCell(CONTRACTASSIGN).setValue(FDCHelper.ZERO);
		projectF7();
		costAccountCellF7(project, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);
	}

	protected void actionRemoveLine_cost_actionPerformed(ActionEvent e) throws Exception {
		if (kdtCost.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("请选择行");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("是否确认删除数据？")) {
//			if(pcInfo.isIsCiting()||pcInfo.isIsWTCiting()){
//	    		MsgBox.showInfo("存在被引用的框架合约,无法删除！");
//	    		SysUtil.abort();
//	    	}
			int rowIndex = this.kdtCost.getSelectManager().getActiveRowIndex();
			Object contractAssignObj = kdtCost.getCell(rowIndex, CONTRACTASSIGN).getValue();
			if (contractAssignObj != null) {
				BigDecimal contractAssign = new BigDecimal(contractAssignObj.toString());
				Object amountObj = this.txtAmount.getValue();
				if (amountObj != null) {
					BigDecimal amount = new BigDecimal(amountObj.toString());
					txtAmount.setValue(amount.subtract(contractAssign));
					afterPlanAmountChange();
				}
			}
			removeLine(kdtCost, rowIndex);
		}
	}

	protected void actionAddnewLine_enocomy_actionPerformed(ActionEvent e) {
		IRow row = kdtEconomy.addRow();
		row.getCell(ECONOMY_ID).setValue(BOSUuid.create("144467E3"));
	}

	protected void actionRemoveLine_enocomy_actionPerformed(ActionEvent e) throws Exception {
		if (kdtEconomy.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("请选择行");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("是否确认删除数据？")) {
			int rowIndex = this.kdtEconomy.getSelectManager().getActiveRowIndex();
			removeLine(kdtEconomy, rowIndex);
		}
	}

	/**
	 * 显示单据行
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		dataBinder.loadLineFields(table, row, obj);
	}

	/**
	 * 新建单据行，返回一个新的分录行的默认值
	 */
	protected IObjectValue createNewDetailData(KDTable table, Object obj) {
		if (table == null) {
			return null;
		}
		if (obj instanceof ProgrammingContracCostInfo) {
			ProgrammingContracCostInfo newDetailInfo = (ProgrammingContracCostInfo) obj;
			newDetailInfo.setId(BOSUuid.create("9E6FDD26"));
			return (IObjectValue) newDetailInfo;
		}
		if (obj instanceof ProgrammingContractEconomyInfo) {
			ProgrammingContractEconomyInfo newDetailInfo = new ProgrammingContractEconomyInfo();
			newDetailInfo.setId(BOSUuid.create("144467E3"));
			return (IObjectValue) newDetailInfo;
		}
		return null;
	}

	/**
	 * 在指定表格中删除指定的行
	 * 
	 * @param table
	 * @param rowIndex
	 * @throws Exception
	 */
	protected void removeLine(KDTable table, int rowIndex) throws Exception {
		IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
		table.removeRow(rowIndex);
		IObjectCollection collection = (IObjectCollection) table.getUserObject();
		if (collection != null) {
			if (detailData != null) {
				collection.removeObject(rowIndex);
			}
		}
	}

	/**
	 * 格式化KDFormattedTextField文本框
	 */
	private void initFormat(){
		setTextFormat(txtAmount);
		setTextFormat(txtControlAmount);
		setTextFormat(txtEstimateAmount);
		filterORG();
	}

	private void filterORG() {
		EntityViewInfo view = prmtInviteOrg.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		if (view.getFilter() == null) {
			view.setFilter(new FilterInfo());
		}
		Set idSet = null;
		try {
			idSet = FDCUtils.getAuthorizedOrgs(null);
		} catch (Exception e) {
			logger.error(e);
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("COSTCENTERORGUNIT.id", idSet, CompareType.INCLUDE));
		prmtInviteOrg.setEntityViewInfo(view);
	}

	private void initTabalFormat(){
		// kdtCost.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtCost.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		// kdtEconomy.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtEconomy.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		/* 成本构成 */
		this.kdtCost.checkParsed();
		// 目标成本
		FDCHelper.formatTableNumber(kdtCost, GOALCOST);
		// 已分配
		FDCHelper.formatTableNumber(kdtCost, ASSIGNED);
		// 待分配
		FDCHelper.formatTableNumber(kdtCost, ASSIGNING);
		// 本合约分配
		FDCHelper.formatTableNumber(kdtCost, CONTRACTASSIGN);
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setSupportedEmpty(true);
		kdftf.setMinimumValue(FDCHelper.ZERO);
		kdftf.setPrecision(2);
		kdtCost.getColumn(CONTRACTASSIGN).setEditor(cellEditor0);
		
		// 备注
		KDTDefaultCellEditor cellEditorDes = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtfDes = (KDTextField) cellEditorDes.getComponent();
		kdtfDes.setMaxLength(80);
		this.kdtCost.getColumn(COST_DES).setEditor(cellEditorDes);

		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(new BigDecimal(100));
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtCost.getColumn("scale").setEditor(weight);
		this.kdtCost.getColumn("scale").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtCost.getColumn("scale").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		/* 经济条款 */

		this.kdtEconomy.checkParsed();
		// 付款日期
		this.kdtEconomy.getColumn(PAYMENTDATE).getStyleAttributes().setNumberFormat("yyyy-MM");
		// 付款条件 -----长度不能超过80个字符
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(80);
		this.kdtEconomy.getColumn(CONDITION).setEditor(cellEditor);
		
		this.kdtEconomy.getColumn("scale").setEditor(weight);
		this.kdtEconomy.getColumn("scale").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEconomy.getColumn("scale").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDTDefaultCellEditor description = new KDTDefaultCellEditor(new KDTextField());
		KDTextField des = (KDTextField) description.getComponent();
		des.setMaxLength(255);
		this.kdtEconomy.getColumn("description").setEditor(description);

	}
	/**
	 * 初始化KDFormattedTextField的相关基础属性
	 * */
	private static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}
	/**
	 * 按钮控制
	 * 
	 * @param b
	 */
	private void ctrButtonEnable(boolean isEnable) {
		btnAttachment.setEnabled(true);
		btnSave.setEnabled(isEnable);
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(false);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(false);
		this.actionSelect.setEnabled(isEnable);
	}

	/**
	 * 编辑控制
	 * 
	 * @param b
	 */
	private void isEditable(boolean isEditable) {
		// 文件编辑框
		txtParentLongName.setEditable(isEditable);
		txtNumber.setEditable(isEditable);
		txtName.setEditable(isEditable);
		txtAmount.setEditable(isEditable);
		txtControlAmount.setEditable(isEditable);
		txtEstimateAmount.setEditable(isEditable);

		txtWorkContent.setEditable(isEditable);
		txtSupMaterial.setEditable(isEditable);
		txtDescription.setEditable(isEditable);
		kdcInviteWay.setEditable(isEditable);
		kdcInviteWay.setEnabled(isEditable);
		prmtInviteOrg.setEditable(isEditable);
		prmtInviteOrg.setEnabled(isEditable);
	    
		// 分录
		kdtCost.setEditable(isEditable);
//		kdtEconomy.setEditable(isEditable);
	}

	/**
	 * 关闭窗口前事件
	 */
	private boolean directExit = false;
	protected boolean checkBeforeWindowClosing() {
		this.kdtCost.getEditManager().editingStopped();
		this.kdtEconomy.getEditManager().editingStopped();
		try {
			this.txtControlAmount.commitEdit();
			this.txtAmount.commitEdit();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (this.oprtState.equals(OprtState.ADDNEW) || this.oprtState.equals(OprtState.EDIT)) {
			if (verifyIsModify()) {
				int i = FDCMsgBox.showConfirm3("数据已修改，是否保存并退出?");
				if (i == FDCMsgBox.OK) {
					try {
						directExit = true;
						actionSubmit_actionPerformed(null);
					} catch (Exception e) {
						SysUtil.abort();
						e.printStackTrace();
					}
				} else if (i == FDCMsgBox.NO) {
					return super.checkBeforeWindowClosing();
				} else if (i == FDCMsgBox.CANCEL) {
					return false;
				}
			}
		}
		return super.checkBeforeWindowClosing();
	}

	/**
	 * 判断单据是否已做修改
	 * 
	 * @return
	 * 
	 *         true表示已修改过
	 * 
	 *         false表示未做修改
	 */
	private boolean verifyIsModify() {
		if (oprtState.equals(OprtState.EDIT) || oprtState.equals(OprtState.ADDNEW)) {
			// 长编码
			if (FDCHelper.isEmpty(txtNumber.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtNumber.getText()) & !FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
				if (!txtNumber.getText().equals(oldPcInfo.getLongNumber())) {
					return true;
				}
			}
			// 名称
			if (FDCHelper.isEmpty(txtName.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getName())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtName.getText()) & !FDCHelper.isEmpty(oldPcInfo.getName())) {
				if (!txtName.getText().equals(oldPcInfo.getName().trim())) {
					return true;
				}
			}
			// 控制金额
			if (FDCHelper.isEmpty(txtControlAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtControlAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
				if (new BigDecimal(txtControlAmount.getNumberValue().toString()).compareTo(oldPcInfo.getControlAmount()) != 0) {
					return true;
				}
			}

			// 规划金额
			if (FDCHelper.isEmpty(txtAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getAmount())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getAmount())) {
				if (new BigDecimal(txtAmount.getNumberValue().toString()).compareTo(oldPcInfo.getAmount()) != 0) {
					return true;
				}
			}

			// 招标方式
			if (FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
				return true;
			}
			if (!FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) & !FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
				if (!kdcInviteWay.getSelectedItem().equals(oldPcInfo.getInviteWay())) {
					return true;
				}
			}

			// 招标组织
			if (FDCHelper.isEmpty(prmtInviteOrg.getData()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
				return true;
			}
			if (!FDCHelper.isEmpty(prmtInviteOrg.getData()) & !FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
				if (!prmtInviteOrg.getData().equals(oldPcInfo.getInviteOrg())) {
					return true;
				}
			}

			// 工作内容
			if (FDCHelper.isEmpty(txtWorkContent.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getWorkContent())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtWorkContent.getText()) & !FDCHelper.isEmpty(oldPcInfo.getWorkContent())) {
				if (!txtWorkContent.getText().equals(oldPcInfo.getWorkContent())) {
					return true;
				}
			}
			// 甲供及甲指材设
			if (FDCHelper.isEmpty(txtSupMaterial.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtSupMaterial.getText()) & !FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
				if (!txtSupMaterial.getText().equals(oldPcInfo.getSupMaterial())) {
					return true;
				}
			}
			
			// 备注
			if (FDCHelper.isEmpty(txtDescription.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getDescription())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtDescription.getText()) & !FDCHelper.isEmpty(oldPcInfo.getDescription())) {
				if (!txtDescription.getText().equals(oldPcInfo.getDescription())) {
					return true;
				}
			}
			// 附件
			// if (FDCHelper.isEmpty(txtAttachment.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getAttachment())) {
			// return true;
			// }
			// if (!FDCHelper.isEmpty(txtAttachment.getText()) & !FDCHelper.isEmpty(oldPcInfo.getAttachment())) {
			// if (!txtAttachment.getText().equals(oldPcInfo.getAttachment())) {
			// return true;
			// }
			// }

			if (kdtCost.getRowCount() == oldPcInfo.getCostEntries().size()) {
				for (int i = 0; i < kdtCost.getRowCount(); i++) {
					for (int j = 0; j < oldPcInfo.getCostEntries().size(); j++) {
						if (oldPcInfo.getCostEntries().get(j).getId() == kdtCost.getCell(i, COST_ID).getValue()) {
							CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
							CostAccountInfo infoCA = oldPcInfo.getCostEntries().get(j).getCostAccount();
							BigDecimal infoContractAssign = oldPcInfo.getCostEntries().get(j).getContractAssign();
							String infoDescription = oldPcInfo.getCostEntries().get(j).getDescription();

							CurProjectInfo tablePro = (CurProjectInfo) kdtCost.getCell(j, PROJECT).getValue();
							CostAccountInfo tableCA = (CostAccountInfo) kdtCost.getCell(j, COSTACCOUNT).getValue();
							Object contractAssign = kdtCost.getCell(j, CONTRACTASSIGN).getValue();
							Object description = kdtCost.getCell(j, COST_DES).getValue();

							// 工程项目
							if (FDCHelper.isEmpty(project) ^ FDCHelper.isEmpty(tablePro)) {
								return true;
							}
							if (!FDCHelper.isEmpty(project) & !FDCHelper.isEmpty(tablePro)) {
								if (!project.getName().equals(tablePro.getName())) {
									return true;
								}
							}
							// 成本科目F7
							if (FDCHelper.isEmpty(infoCA) ^ FDCHelper.isEmpty(tableCA)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoCA) & !FDCHelper.isEmpty(tableCA)) {
								if (!infoCA.getName().equals(tableCA.getName())) {
									return true;
								}
							}
							// 本合约分配
							if (FDCHelper.isEmpty(infoContractAssign) ^ FDCHelper.isEmpty(contractAssign)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoContractAssign) & !FDCHelper.isEmpty(contractAssign)) {
								BigDecimal temp = new BigDecimal(contractAssign.toString());
								if (infoContractAssign.compareTo(temp) != 0) {
									return true;
								}
							}
							// 备注
							if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
								if (!infoDescription.equals(description.toString())) {
									return true;
								}
							}
						}
					}
				}
			} else {
				return true;
			}
			if (kdtEconomy.getRowCount() == oldPcInfo.getEconomyEntries().size()) {
				for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
					for (int j = 0; j < oldPcInfo.getEconomyEntries().size(); j++) {
						if (oldPcInfo.getEconomyEntries().get(j).getId() == kdtEconomy.getCell(i, ECONOMY_ID).getValue()) {

							PaymentTypeInfo infoPT = oldPcInfo.getEconomyEntries().get(j).getPaymentType();
							BigDecimal infoScale = oldPcInfo.getEconomyEntries().get(j).getScale();
							BigDecimal infoAmount = oldPcInfo.getEconomyEntries().get(j).getAmount();
							String infoCondition = oldPcInfo.getEconomyEntries().get(j).getCondition();
							String infoDescription = oldPcInfo.getEconomyEntries().get(j).getDescription();

							PaymentTypeInfo tablePT = (PaymentTypeInfo) kdtEconomy.getCell(j, PAYMENTTYPE).getValue();
							Object scale = kdtEconomy.getCell(j, SCALE).getValue();
							Object amount = kdtEconomy.getCell(j, AMOUNT).getValue();
							Object condition = kdtEconomy.getCell(j, CONDITION).getValue();
							Object description = kdtEconomy.getCell(j, ECONOMY_DES).getValue();

							// 付款类型
							if (FDCHelper.isEmpty(infoPT) ^ FDCHelper.isEmpty(tablePT)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoPT) & !FDCHelper.isEmpty(tablePT)) {
								if (!infoPT.getName().equals(tablePT.getName())) {
									return true;
								}
							}

							// 付款比例
							if (FDCHelper.isEmpty(infoScale) ^ FDCHelper.isEmpty(scale)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoScale) & !FDCHelper.isEmpty(scale)) {
								BigDecimal temp = new BigDecimal(scale.toString());
								if (infoScale.compareTo(temp) != 0) {
									return true;
								}
							}
							// 付款金额
							if (FDCHelper.isEmpty(infoAmount) ^ FDCHelper.isEmpty(amount)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoAmount) & !FDCHelper.isEmpty(amount)) {
								BigDecimal temp = new BigDecimal(amount.toString());
								if (infoAmount.compareTo(temp) != 0) {
									return true;
								}
							}
							// 付款条件
							if (FDCHelper.isEmpty(infoCondition) ^ FDCHelper.isEmpty(condition)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoCondition) & !FDCHelper.isEmpty(condition)) {
								if (!infoCondition.equals(condition.toString())) {
									return true;
								}
							}
							// 备注
							if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
								if (!infoDescription.equals(description.toString())) {
									return true;
								}
							}
						}
					}
				}
			} else {
				return true;
			}
		}
		if (oprtState.equals(OprtState.VIEW)) {
			return false;
		}
		return false;
	}

	/**
	 * 保存
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyIsEmpty();
		verifyAllData();
		/* 保存单据头信息 */
		pcInfo.setLongNumber(txtNumber.getText());// 长编码
		if (FDCHelper.isEmpty(txtParentLongName.getText())) {
			pcInfo.setDisplayName(txtParentLongName.getText());// 上级合约长名称
		} else {
			pcInfo.setDisplayName(txtParentLongName.getText() + "." + txtName.getText());
		}
		pcInfo.setNumber(txtNumber.getText());
		pcInfo.setName(setNameIndent(pcInfo.getLevel()) + txtName.getText());// 名称
		pcInfo.setInviteWay((InviteFormEnum) kdcInviteWay.getSelectedItem());// 招标方式
		pcInfo.setInviteOrg((CostCenterOrgUnitInfo) prmtInviteOrg.getData());		
		pcInfo.setAmount((BigDecimal) txtAmount.getValue());// 规划金额
		pcInfo.setControlAmount((BigDecimal) txtControlAmount.getValue());// 控制金额
		pcInfo.setWorkContent(txtWorkContent.getText());// 工作内容
		pcInfo.setSupMaterial(txtSupMaterial.getText());// 甲供及甲指材设	
		pcInfo.setDescription(txtDescription.getText());// 备注
		if (FDCHelper.isEmpty(txtAttachment.getText())) {
			pcInfo.setAttachment(null);// 附件
		} else {
			pcInfo.setAttachment(txtAttachment.getText());// 附件
		}
		
		updateBalance(pcInfo);
		/* 保存单据分录信息 */
		/* 成本构成 */
		pcInfo.getCostEntries().clear();
		int cost_rowCount = kdtCost.getRowCount();
		StringBuffer costAccountNames = new StringBuffer(); 
		for (int i = 0; i < cost_rowCount; i++) {
			ProgrammingContracCostInfo pccInfo = new ProgrammingContracCostInfo();
			Object project = kdtCost.getCell(i, PROJECT).getValue();// 工程项目
			Object costAccount = kdtCost.getCell(i, COSTACCOUNT).getValue();// 成本科目
			Object goalCost = kdtCost.getCell(i, GOALCOST).getValue();// 目标成本
			Object assigned = kdtCost.getCell(i, ASSIGNED).getValue();// 已分配
			Object assigning = kdtCost.getCell(i, ASSIGNING).getValue();// 待分配
			Object contractAssign = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// 本合约分配
			String description = (String) kdtCost.getCell(i, COST_DES).getValue();// 备注
			CurProjectInfo projectInfo = (CurProjectInfo) project;
			CostAccountInfo costAccountInfo = (CostAccountInfo) costAccount;
			if(i > 0){
				costAccountNames.append(";");
			}
			costAccountNames.append(costAccountInfo.getName());
			costAccountInfo.setCurProject(projectInfo);
			pccInfo.setCostAccount(costAccountInfo);
			if (!FDCHelper.isEmpty(goalCost)) {
				pccInfo.setGoalCost(new BigDecimal(goalCost.toString()));
			}
			if (!FDCHelper.isEmpty(assigned)) {
				pccInfo.setAssigned(new BigDecimal(assigned.toString()));
			}
			if (!FDCHelper.isEmpty(assigning)) {
				pccInfo.setAssigning(new BigDecimal(assigning.toString()));
			}
			if (!FDCHelper.isEmpty(contractAssign)) {
				pccInfo.setContractAssign(new BigDecimal(contractAssign.toString()));
			}
			pccInfo.setDescription(description);
			pcInfo.getCostEntries().add(pccInfo);
		}
		pcInfo.setCostAccountNames(costAccountNames.toString());
		// 动态更新成本构成"待分配"的值
		int pteSize = pcCollection.size();
		for (int i = 0; i < pteSize; i++) {
			ProgrammingContracCostCollection pccCollection = pcCollection.get(i).getCostEntries();// 成本构成集合
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);// 成本构成
				BigDecimal oldContractAssign = pccInfo.getContractAssign();// 本合约分配
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();// 成本科目
				BigDecimal goalCost = pccInfo.getGoalCost();// 目标成本
				if (costAccountInfo != null) {
					BigDecimal allCostAccountAssign = getAllContractAssign(costAccountInfo, true);// "本合约分配"金额之和
					if (oldContractAssign != null) {
						// 更新"待分配":"目标成本"-"本合约分配"金额之和+自身"本合约分配"
						BigDecimal newAssigning = goalCost.subtract(allCostAccountAssign).add(oldContractAssign);
						// 更新"已分配"："本合约分配"金额之和-自身"本合约分配"
						BigDecimal newAssigned = allCostAccountAssign.subtract(oldContractAssign);
						pccInfo.setAssigning(newAssigning);
						pccInfo.setAssigned(newAssigned);
					}
				}
			}

		}
		

		/* 经济条款 */
		pcInfo.getEconomyEntries().clear();
		int enonomy_rowCount = kdtEconomy.getRowCount();
		for(int i=0;i<enonomy_rowCount;i++){
			ProgrammingContractEconomyInfo pciInfo = new ProgrammingContractEconomyInfo();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();// 付款类型
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();//付款比例
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();//付款金额
			Object conditionObj = kdtEconomy.getCell(i, CONDITION).getValue();//付款条件
			Object paymentDateObj = kdtEconomy.getCell(i, PAYMENTDATE).getValue();//付款日期
			Object descriptionObj = kdtEconomy.getCell(i, ECONOMY_DES).getValue();// 备注
			pciInfo.setPaymentType(currentInfo);// 存储付款类型
			if(!FDCHelper.isEmpty(scaleObj)){
				pciInfo.setScale(new BigDecimal(scaleObj.toString()));// 存储付款比例
			}
			if(!FDCHelper.isEmpty(amountObj)){
				pciInfo.setAmount(new BigDecimal(amountObj.toString()));// 存储付款金额
			}
			pciInfo.setCondition((String) conditionObj);// 存储付款条件
			if(!FDCHelper.isEmpty(paymentDateObj)){
				Date paymentDate = (Date) paymentDateObj;
				pciInfo.setPaymentDate(new Timestamp(paymentDate.getTime()));// 存储付款日期
			}
			pciInfo.setDescription((String) descriptionObj);// 存储付款条件
			pcInfo.getEconomyEntries().add(pciInfo);
		}

		if (directExit) {
			// 直接保存退出，不作提示
			// verifyIsEmpty();
			// verifyAllData();
		} else {
			FDCMsgBox.showInfo("保存框架合约成功！");
		}
		oldPcInfo = pcInfo;
		// destroyWindow();
	}

	/**
	 * 更新规划余额，控制余额
	 */
	private void updateBalance(ProgrammingContractInfo pcInfo) {
		BigDecimal newAmount = pcInfo.getAmount();// 规划金额
		if (newAmount == null) {
			newAmount = FDCHelper.ZERO;
		}
		BigDecimal newControlAmount = pcInfo.getControlAmount();// 控制金额
		if (newControlAmount == null) {
			newControlAmount = FDCHelper.ZERO;
		}
		pcInfo.setControlBalance(newAmount);
		pcInfo.setBalance(oldbalance.add(newAmount.subtract(oldAmount)));
		//新增时的未签合同金额
		if(!(pcInfo.isIsCiting()||pcInfo.isIsWTCiting())){
			pcInfo.setBudgetAmount(newAmount);
		}else{
			pcInfo.setBudgetAmount(FDCHelper.ZERO);
		}
	}

	/**
	 * 在名称前添加空格，显示缩进效果
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * 必录项判空验证
	 */
	private void verifyIsEmpty() {
		ProgrammingContractInfo head = pcInfo.getParent();
		if (head != null) {
			String longNumber = head.getLongNumber();
			if (txtNumber.getText().equals(longNumber + ".")) {
				FDCMsgBox.showInfo("框架合约编码不能为空！");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
				if (programmingContractInfo.getLongNumber().equals(txtNumber.getText())
						&& !pcInfo.getId().toString().equals(programmingContractInfo.getId().toString())) {
					FDCMsgBox.showInfo("框架合约编码已存在，请重新输入！");
					txtNumber.requestFocus();
					SysUtil.abort();
				}
			}
		} else {
			if (FDCHelper.isEmpty(txtNumber.getText())) {
				FDCMsgBox.showInfo("框架合约编码不能为空！");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			FDCMsgBox.showInfo("框架合约名称不能为空！");
			txtName.requestFocus();
			SysUtil.abort();
		} else {
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo rowObject = pcCollection.get(i);
				String name = rowObject.getName();
				if (!FDCHelper.isEmpty(name)) {
					if (txtName.getText().equals(name.trim()) && !pcInfo.getId().toString().equals(rowObject.getId().toString())) {
						FDCMsgBox.showInfo("框架合约名称已存在，请重新输入！");
						txtName.requestFocus();
						SysUtil.abort();
					}
				}
			}
		}
		// if (this.oprtState.equals(OprtState.ADDNEW)) {
		// isNameDup(txtName.getText(), pcInfo.getId().toString());
		// isNumberDup(txtNumber.getText(), null);
		// } else if (this.oprtState.equals(OprtState.EDIT)) {
		// isNameDup(txtName.getText(), pcInfo.getId().toString());
		// isNumberDup(txtNumber.getText(), pcInfo.getId().toString());
		// }
		// 成本构成分录必录项验空
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			IRow row = kdtCost.getRow(i);
			ICell projectCell = row.getCell(PROJECT);
			ICell costAccountCell_number = row.getCell(COSTACCOUNT_NUMBER);
			ICell costAccountCell_name = row.getCell(COSTACCOUNT);
			ICell goalCost = row.getCell(GOALCOST);
			ICell assigned = row.getCell(ASSIGNED);
			ICell assigning = row.getCell(ASSIGNING);
			ICell contractAssign = row.getCell(CONTRACTASSIGN);
			if (FDCHelper.isEmpty(projectCell.getValue())) {
				FDCMsgBox.showInfo("工程项目不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell_number.getValue())) {
				FDCMsgBox.showInfo("成本科目编码不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell_name.getValue())) {
				FDCMsgBox.showInfo("成本科目名称不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(goalCost.getValue())) {
				FDCMsgBox.showInfo("目标成本不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigned.getValue())) {
				FDCMsgBox.showInfo("已分配不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigning.getValue())) {
				FDCMsgBox.showInfo("待分配不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(contractAssign.getValue())) {
				FDCMsgBox.showInfo("本合约分配不能为空！");
				SysUtil.abort();
			}
		}
		// 经济条款分录必录项验空
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			IRow row = kdtEconomy.getRow(i);
			ICell costAccountCell = row.getCell(PAYMENTTYPE);
			ICell scale = row.getCell(SCALE);
			
			if (FDCHelper.isEmpty(row.getCell(PAYMENTDATE))) {
				FDCMsgBox.showInfo("计划付款日期不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
				FDCMsgBox.showInfo("付款类型不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(scale.getValue())) {
				FDCMsgBox.showInfo("计划付款比例不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(row.getCell(AMOUNT))) {
				FDCMsgBox.showInfo("计划付款金额不能为空！");
				SysUtil.abort();
			}
		}
	}

	private void verifyAllData() {


		BigDecimal amount = (BigDecimal) txtAmount.getValue();// 规划金额
		BigDecimal controlAmount = (BigDecimal) txtControlAmount.getValue();// 控制金额
		if (amount != null) {
			// 规划金额不能小于0
			if (amount.compareTo(FDCHelper.ZERO) < 0) {
				FDCMsgBox.showInfo("规划金额不能小于0");
				SysUtil.abort();
			}
		}
//		if (controlAmount != null) {
//			// 控制金额不能小于0
//			if (controlAmount.compareTo(FDCHelper.ZERO) < 0) {
//				FDCMsgBox.showInfo("控制金额不能小于0");
//				SysUtil.abort();
//			}
//		}
		if (amount != null && controlAmount != null) {
			// 控制金额不能大于规划金额
//			if (controlAmount.compareTo(amount) > 0) {
//				FDCMsgBox.showInfo("控制金额不得大于规划金额");
//				SysUtil.abort();
//			}
			// 规划金额不能小于引用的合约的已发生金额
			if (amount.compareTo(getHappenedAmount()) < 0) {
				FDCMsgBox.showInfo("规划金额不能小于已发生金额");
				SysUtil.abort();
			}
		}

		int costRowCount = kdtCost.getRowCount();
		for (int i = 0; i < costRowCount; i++) {
			Object assigningObj = kdtCost.getCell(i, ASSIGNING).getValue();
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();
			if (assigningObj != null && contractAssignObj != null) {
				BigDecimal assigning = new BigDecimal(assigningObj.toString());
				if(assigning.compareTo(FDCHelper.ZERO)<0) continue;
				
				BigDecimal contractAssign = new BigDecimal(contractAssignObj.toString());
				if (assigning.compareTo(contractAssign) < 0) {
					FDCMsgBox.showInfo("本合约分配金额不得大于待分配金额");
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * 已发生金额
	 * 
	 * @return
	 */
	private BigDecimal getHappenedAmount() {
		BigDecimal happenedAmount = FDCHelper.ZERO;
		if(pcInfo.getSrcId()!=null&&pcInfo.getId()!=null&&!pcInfo.getSrcId().equals(pcInfo.getId().toString())){
			
		}else{
			BigDecimal signUpAmount = pcInfo.getSignUpAmount();// 签约金额
			BigDecimal changeAmount = pcInfo.getChangeAmount();// 变更金额
			BigDecimal settleAmount = pcInfo.getSettleAmount();// 结算金额
			BigDecimal estimateAmount=pcInfo.getEstimateAmount();
			
			if (FDCHelper.isEmpty(signUpAmount)) {
				signUpAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(changeAmount)) {
				changeAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(settleAmount)) {
				settleAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(settleAmount)) {
				settleAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(estimateAmount)) {
				estimateAmount = new BigDecimal(0);
			}
			if(settleAmount.compareTo(FDCHelper.ZERO)>0){
				happenedAmount=settleAmount;
			}else{
				happenedAmount=signUpAmount.add(changeAmount).add(estimateAmount);
			}
		}
		return happenedAmount;
	}



	/**
	 * 成本构成分录单击事件
	 */
	protected void kdtCost_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		// 选中行则“删除行”按钮可用
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_cost.setEnabled(true);
		}
		if (e.getColIndex() == kdtCost.getColumnIndex(COSTACCOUNT) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
			if (project == null) {
				FDCMsgBox.showInfo("请先录入工程项目");
			} else {
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(false);
			}
		}
	}

	/**
	 * 成本构成分录数据编辑处理
	 */
	protected void kdtCost_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		// 编辑"工程项目"
		if (colIndex == kdtCost.getColumnIndex(PROJECT)) {
			Object projectObj = kdtCost.getCell(rowIndex, colIndex).getValue();
			if (projectObj != null) {
				CurProjectInfo newProject = (CurProjectInfo) projectObj;
				costAccountCellF7(newProject, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);// 通过工程项目为条件重新加载成本科目F7
				// 工程项目不变，则不做处理
				if (oldValue != null) {
					CurProjectInfo oldProject = (CurProjectInfo) oldValue;
					if (newProject.getNumber().equals(oldProject.getNumber())) {
						return;
					}
				}

				// 取目标成本，为空则把当前行除工程项目外所有值置空
				AimCostInfo aimCostInfo = null;
				Object aimCostObj = this.getUIContext().get("aimCostInfo");
				if (aimCostObj == null) {
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED, ASSIGNING,
							CONTRACTASSIGN, COST_DES);
				} else {
					aimCostInfo = (AimCostInfo) aimCostObj;
				}
				/*
				 * 工程项目改变之后
				 * 
				 * 1.
				 * 
				 * 2.判断当前行是否已选了成本科目
				 * 
				 * 2.1:若无：清空除工程项目外所有当前行单元格
				 * 
				 * 2.2:若有：判断所变的工程项目是否也有此成本科目
				 * 
				 * 2.2.1:若无：清空除工程项目外所有当前行单元格
				 * 
				 * 2.2.2:若有：
				 * 
				 * 2.2.2.1:给当前行成本科目单元格重新关联新的成本科目
				 * 
				 * 2.2.2.2：获取"目标成本"
				 * 
				 * 2.2.2.2.1：判断目标成本是否为0
				 * 
				 * 2.2.2.2.1.1:为0，给"目标成本","已分配","待分配","本合约分配"赋值0,备注清空
				 * 
				 * 2.2.2.2.1.2:不为0，算出"已分配","待分配","本合约分配"各值
				 * 
				 * 最后把牵涉到的值：规划金额，经济条款中"付款比例"、"付款金额"等值 更新一遍
				 */
				// 1

				Object costAccountObj = kdtCost.getCell(rowIndex, COSTACCOUNT).getValue();
				// 2
				if (costAccountObj == null) {
					// 2.1
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED, ASSIGNING,
							CONTRACTASSIGN, COST_DES);
				} else {
					// 2.2
					CostAccountInfo costAccount = (CostAccountInfo) costAccountObj;
					String newCostAccountID = ProgrammingContractUtil.isExitCostAccount(newProject, costAccount);
					if (newCostAccountID == null) {
						// 2.2.1
						ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
								ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2.2
						// 2.2.2.1给当前行成本科目单元格重新关联新的成本科目
						CostAccountInfo newCostAccountInfo = ProgrammingContractUtil.getCostAccountByNewID(newCostAccountID);
						kdtCost.getCell(rowIndex, COSTACCOUNT).setValue(newCostAccountInfo);
						kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(newCostAccountInfo.getLongNumber().replace('!', '.'));
						// 2.2.2.2：获取"目标成本"
						BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo,
								aimCostInfo);
						// 2.2.2.2.1
						if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
							// 2.2.2.2.1.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
							afterContractAssignChange();
							afterPlanAmountChange();
						} else {
							// 2.2.2.2.1.2 算出"已分配","待分配","本合约分配"各值
							BigDecimal allAssigned = FDCHelper.ZERO;// 已分配
							// 算出"待分配" == "目标成本" - "已分配"
							BigDecimal assigning = goalCost.subtract(allAssigned);
							// 带出"本合约分配"="待分配"
							BigDecimal contractAssign = assigning;
							// 显示在单元格中
							kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// 目标成本
							kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// 已分配
							kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// 待分配
							kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// 本合约分配

							// 本合约分配带出后又自动算出"规划金额"
							afterContractAssignChange();
							// 自动算出"规划金额"后，动态改变经济条款中"付款比例"和"付款金额"
							// 默认以"付款比例"为定值，改变"付款金额"
							afterPlanAmountChange();
						}
					}
				}
			} else {
				//工程项目变为空则清空所在行所有行
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex, PROJECT, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
						ASSIGNING, CONTRACTASSIGN, COST_DES);
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(true);
			}

		}
		// 编辑"本合约分配"
		if (colIndex == kdtCost.getColumnIndex(CONTRACTASSIGN)) {
			afterContractAssignChange();
			
//			ObjectValueRender render_scale = new ObjectValueRender();
//			render_scale.setFormat(new IDataFormat() {
//				public String format(Object o) {
//					String str = o.toString();
//					if (!FDCHelper.isEmpty(str)) {
//						return str + "%";
//					}
//					return str;
//				}
//			});
//			kdtCost.getColumn("scale").setRenderer(render_scale);
			BigDecimal amount=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell(CONTRACTASSIGN).getValue();
			BigDecimal goalCost=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell(GOALCOST).getValue();
			if(amount!=null&&goalCost!=null&&goalCost.compareTo(FDCHelper.ZERO)!=0){
				amount=goalCost==null?FDCHelper.ZERO:amount.multiply(new BigDecimal(100)).divide(goalCost,2,BigDecimal.ROUND_HALF_UP);
				kdtCost.getRow(e.getRowIndex()).getCell("scale").setValue(amount);
			}else{
				kdtCost.getRow(e.getRowIndex()).getCell("scale").setValue(FDCHelper.ZERO);
			}
		}

		// 选择"成本科目F7"
		if (colIndex == kdtCost.getColumnIndex(COSTACCOUNT)) {
			/*
			 * 1.取出成本科目的值
			 * 
			 * 1.1判断成本科目是否为空
			 * 
			 * 1.1.1为空：置空当前行除工程项目外的所有单元格
			 * 
			 * 1.1.2不为空：取值
			 * 
			 * 1.2判断所选的成本科目是否重复，重复直接返回,不重复继续
			 * 
			 * 2.取出目标成本信息
			 * 
			 * 2.1若为空，把"目标成本","已分配","待分配","本合约分配","备注"置空；
			 * 
			 * 2.2若不为空： 判断目标成本是否是已审批之后状态
			 * 
			 * 2.2.1若不为审批之后状态，把各单元格数值置0，备注项置空 ；
			 * 
			 * 2.2.2若为审批之后状态，取出相应成本科目的目标成本值（需要用到成本科目，目标成本作为条件）
			 * 
			 * 3.算出"已分配","待分配","本合约分配"各值
			 * 
			 * 最后把牵涉到的值：规划金额，经济条款中"付款比例"、"付款金额"等值 更新一遍
			 */
			BigDecimal allAssigned = FDCHelper.ZERO;// "已分配"
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();// 工程项目
			// 1.
			Object newValue = kdtCost.getCell(rowIndex, COSTACCOUNT).getValue();
			// 1.1
			if(newValue == null){
				// 1.1.1
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED, ASSIGNING,
						CONTRACTASSIGN, COST_DES);
			}else{
				// 1.1.2
				CostAccountInfo newCostAccountInfo = (CostAccountInfo) newValue;// 成本科目
				kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(newCostAccountInfo.getLongNumber().replace('!', '.'));
				// 1.2
				if (isCostAccountDup(newCostAccountInfo, project, rowIndex)) {
					return;
				}
				// 2.
				AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// 目标成本
				if (aimCostInfo == null) {
					// 2.1
//					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
				} else {
					// 2.2是否是已审批
					if (!isAimCostAudit(aimCostInfo)) {
						// 2.2.1
						ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2.2 取出目标成本的值
						// CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
						if (project != null) {
							BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo,
									aimCostInfo);
							if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
								ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
								afterContractAssignChange();
								afterPlanAmountChange();
							} else {
								allAssigned = getAllContractAssign(newCostAccountInfo, false);// 已分配
								// 算出"待分配" == "目标成本" - "已分配"
								BigDecimal assigning = goalCost.subtract(allAssigned);// 待分配
								// 带出"本合约分配"="待分配"
								BigDecimal contractAssign = assigning;// 本合约分配
								// 显示在单元格中
								kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// 目标成本
								kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// 已分配
								kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// 待分配
								kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// 本合约分配

								// 本合约分配带出后又自动算出"规划金额"
								afterContractAssignChange();
								// 自动算出"规划金额"后，动态改变经济条款中"付款比例"和"付款金额"
								// 默认以"付款比例"为定值，改变"付款金额"
								afterPlanAmountChange();
							}
						}
					}
				}
			}
		}
		if(colIndex == kdtCost.getColumnIndex("scale")){
			// 绘制付款比例显示 郊果
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtCost.getColumn("scale").setRenderer(render_scale);
			BigDecimal amount=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell("scale").getValue();
			BigDecimal goalCost=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell(GOALCOST).getValue();
			if(amount!=null){
				amount=goalCost==null?FDCHelper.ZERO:goalCost.multiply(amount).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				kdtCost.getRow(e.getRowIndex()).getCell(CONTRACTASSIGN).setValue(amount);
			}else{
				kdtCost.getRow(e.getRowIndex()).getCell(CONTRACTASSIGN).setValue(FDCHelper.ZERO);
			}
			afterContractAssignChange();
		}
	}

	/**
	 * "本合约分配"值改变后，汇总规划金额
	 */
	private void afterContractAssignChange() {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// 本合约分配
			if (contractAssignObj == null) {
				allContractAssign = allContractAssign.add(FDCHelper.ZERO);
			} else {
				allContractAssign = allContractAssign.add(new BigDecimal(contractAssignObj.toString()));
			}
		}
		txtAmount.setValue(allContractAssign);
		txtControlAmount.setValue(allContractAssign);
	}

	/**
	 * "规划金额"值改变后，动态改变经济条款中付款金额的值
	 */
	private void afterPlanAmountChange() {
		BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();
			if (scaleObj != null) {
				BigDecimal scale = new BigDecimal(scaleObj.toString());
				BigDecimal hundren = FDCHelper.ONE_HUNDRED;
				scale = FDCHelper.divide(scale, hundren);
				BigDecimal amount = planAmount.multiply(scale);
				kdtEconomy.getCell(i, AMOUNT).setValue(amount);
			}
		}
	}

	/**
	 * 判断在一个合约之内相同工程项目下成本科目是否重复
	 * 
	 * @param rowIndex
	 */
	private boolean isCostAccountDup(CostAccountInfo currentInfo, CurProjectInfo project, int rowIndex) {
		ICell costAccountNameCell = kdtCost.getCell(rowIndex, COSTACCOUNT);
		ICell costAccountNumberCell = kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER);
		if (FDCHelper.isEmpty(currentInfo)) {
			return false;
		}
		int rowCount = kdtCost.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getLongNumber())) {
			int flag = 0;
			for (int i = 0; i < rowCount; i++) {
				CostAccountInfo forInfo = (CostAccountInfo) kdtCost.getCell(i, COSTACCOUNT).getValue();
				CurProjectInfo forProjectInfo = (CurProjectInfo) kdtCost.getCell(i, PROJECT).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getLongNumber())) {
					if (currentInfo.getLongNumber().equals(forInfo.getLongNumber()) && project.getNumber().equals(forProjectInfo.getNumber())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("本框架合约成本构成内已经有\"" + currentInfo.getName() + "\"成本科目，不能再继续添加此成本科目了！");
							costAccountNameCell.setValue(null);
							costAccountNumberCell.setValue(null);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 算出框架内相同成本科目的"本合约分配"金额之和
	 * 
	 * @param caInfo
	 * 
	 * @param flag
	 * 
	 *            true表示求出所有成本构成中"本合约分配"之和
	 * 
	 *            false 表示求出除本合约之外所有成本构成中"本合约分配"之和
	 * @return
	 */
	private BigDecimal getAllContractAssign(CostAccountInfo caInfo, boolean flag) {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (flag) {
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {
						if(costAccountInfo.getLongNumber()!=null){
							if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
								BigDecimal contractAssign = pccInfo.getContractAssign();
								if (contractAssign == null) {
									contractAssign = FDCHelper.ZERO;
								}
								allContractAssign = allContractAssign.add(contractAssign);
							}
						}
					}
				}
			} else {
				if (!programmingContractInfo.getId().toString().equals(pcInfo.getId().toString())) {
					ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
					for (int j = 0; j < costEntries.size(); j++) {
						ProgrammingContracCostInfo pccInfo = costEntries.get(j);
						CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
						if (costAccountInfo != null) {
							if(costAccountInfo.getLongNumber()!=null){
								if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
									BigDecimal contractAssign = pccInfo.getContractAssign();
									if (contractAssign == null) {
										contractAssign = FDCHelper.ZERO;
									}
									allContractAssign = allContractAssign.add(contractAssign);
								}
							}
						}
					}
				}
			}
		}
		return allContractAssign;
	}

	/**
	 * 经济条款分录单击事件
	 */
	protected void kdtEconomy_tableClicked(KDTMouseEvent e) throws Exception {
		// 选中行则“删除行”按钮可用
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_economy.setEnabled(true);
		}
	}

	/**
	 * 经济条款分录数据编辑处理
	 */
	protected void kdtEconomy_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		/* 选择付款类型 */
		// if (colIndex == kdtEconomy.getColumnIndex(PAYMENTTYPE)) {
		// isEnonomDup();
		// }

		/* 编辑付款比例 */
		if (colIndex == kdtEconomy.getColumnIndex(SCALE)) {
			ICell scaleCell = kdtEconomy.getCell(rowIndex, SCALE);
			ICell amountCell = kdtEconomy.getCell(rowIndex, AMOUNT);
			if (scaleCell.getValue() != null && oldValue != null) {
				BigDecimal newScale = FDCHelper.toBigDecimal(scaleCell.getValue().toString());
				BigDecimal oldScale = FDCHelper.toBigDecimal(oldValue.toString());
				if ((newScale).compareTo(oldScale) == 0) {
					return;
				}
			}
			// 绘制付款比例显示 郊果
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

			// 绘制付款金额显示 郊果
			ObjectValueRender render_amount = new ObjectValueRender();
			render_amount.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str;
					}
					return str;
				}
			});
			kdtEconomy.getColumn(AMOUNT).setRenderer(render_amount);

			BigDecimal scale = (BigDecimal) scaleCell.getValue();
			if (!FDCHelper.isEmpty(scale)) {
				// Object newScaleValue = e.getValue();
				Object oldScaleValue = e.getOldValue();
				Object oldAmountValue = kdtEconomy.getCell(rowIndex, AMOUNT).getValue();
				// editStopedCheckIsChange(oldScaleValue, newScaleValue);
				BigDecimal planAmount = txtAmount.getBigDecimalValue();
				if (!FDCHelper.isEmpty(planAmount)) {
					BigDecimal amount = FDCHelper.divide(planAmount.multiply(scale), FDCHelper.ONE_HUNDRED);
					amountCell.setValue(amount.toString());
				}

				// 判断
				if (getPayScaleAll().compareTo(new BigDecimal(100)) > 0) {
					FDCMsgBox.showInfo("计划付款比例累计不得超过100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0) : new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("计划付款金额总和不能超出规划金额");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				amountCell.setValue(null);
			}
		}

		/* 编辑付款金额 */
		if (colIndex == kdtEconomy.getColumnIndex(AMOUNT)) {
			ICell scaleCell = kdtEconomy.getCell(rowIndex, SCALE);
			ICell amountCell = kdtEconomy.getCell(rowIndex, AMOUNT);
			if (amountCell.getValue() != null && oldValue != null) {
				BigDecimal newAmount = FDCHelper.toBigDecimal(amountCell.getValue().toString());
				BigDecimal oldAmount = FDCHelper.toBigDecimal(oldValue.toString());
				if ((newAmount).compareTo(oldAmount) == 0) {
					return;
				}
			}
			String amountStr = (String) amountCell.getValue();
			// 绘制付款比例显示 郊果
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

			// 绘制付款金额 显示郊果
			ObjectValueRender render_amount = new ObjectValueRender();
			render_amount.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str;
					}
					return str;
				}
			});
			kdtEconomy.getColumn(AMOUNT).setRenderer(render_amount);

			if (!FDCHelper.isEmpty(amountStr)) {
				// Object newAmountValue = e.getValue();
				Object oldAmountValue = e.getOldValue();
				Object oldScaleValue = kdtEconomy.getCell(rowIndex, SCALE).getValue();
				// editStopedCheckIsChange(oldAmountValue, newAmountValue);

				if (amountStr.matches("^\\d*$")) {
					// 填写付款金额后自动计算出付款比例
					BigDecimal amount = new BigDecimal(amountStr);
					BigDecimal hundrenBig = FDCHelper.ONE_HUNDRED;
					amount = amount.multiply(hundrenBig);
					BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
					if (!FDCHelper.isEmpty(planAmount)) {
						BigDecimal scale = FDCHelper.divide(amount, planAmount, 10, BigDecimal.ROUND_HALF_UP);
						scaleCell.setValue(scale);
					}
				} else {
					// scaleCell.setValue(null);
					amountCell.setValue(null);
					FDCMsgBox.showInfo("请录入整数！");
				}

				// 判断
				if (getPayScaleAll().compareTo(FDCHelper.ONE_HUNDRED) > 0) {
					FDCMsgBox.showInfo("您所填写的计划付款金额过大，计划付款比例已超出100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0) : new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("计划付款金额总和不能超出规划金额");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				scaleCell.setValue(null);
			}

		}

	}

	/**
	 * 判断目标成本是否已审批
	 * 
	 * @return true:审批中之后状态; false:未审批
	 */
	private boolean isAimCostAudit(AimCostInfo aimCostInfo) {
		if (aimCostInfo != null) {
			FDCBillStateEnum state = aimCostInfo.getState();
			if (state.equals(FDCBillStateEnum.SAVED) || state.equals(FDCBillStateEnum.SUBMITTED)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断单元格的值在编辑前后是否改变
	 * 
	 * @param oldValue
	 * @param newValue
	 */
	private void editStopedCheckIsChange(Object oldValue, Object newValue) {
		if (newValue != null && oldValue != null) {
			if (new BigDecimal(newValue.toString()).compareTo(new BigDecimal(oldValue.toString())) == 0) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * 付款比例总和
	 * 
	 * @return
	 */
	private BigDecimal getPayScaleAll() {
		int rowCount = kdtEconomy.getRowCount();
		BigDecimal scale = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();
			if (!FDCHelper.isEmpty(scaleObj)) {
				BigDecimal temp = new BigDecimal(scaleObj.toString());
				scale = scale.add(temp);
			}
		}
		return scale;
	}

	/**
	 * 付款金额总和
	 * 
	 * @return
	 */
	private BigDecimal getPayAmountAll() {
		int rowCount = kdtEconomy.getRowCount();
		BigDecimal amount = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();
			if (!FDCHelper.isEmpty(amountObj)) {
				BigDecimal temp = new BigDecimal(amountObj.toString());
				amount = amount.add(temp);
			}
		}
		return amount;
	}

	/**
	 * 付款金额或付款比例单元格值删除后处理情况
	 */
	// protected void kdtEconomy_activeCellChanged(KDTActiveCellEvent e) throws Exception {
	// if (e.getPrevColumnIndex() == kdtEconomy.getColumnIndex(SCALE)) {
	// if (kdtEconomy.getCell(e.getPrevRowIndex(), e.getPrevColumnIndex()).getValue() == null) {
	// kdtEconomy.getCell(e.getPrevRowIndex(), AMOUNT).setValue(null);
	// }
	// }
	// if (e.getPrevColumnIndex() == kdtEconomy.getColumnIndex(AMOUNT)) {
	// if (kdtEconomy.getCell(e.getPrevRowIndex(), e.getPrevColumnIndex()).getValue() == null) {
	// kdtEconomy.getCell(e.getPrevRowIndex(), SCALE).setValue(null);
	// }
	// }
	// }

	/**
	 * 判断付款类型是否重复
	 * 
	 * @param enonomy_rowCount
	 */
	private void isEnonomDup() {
		int rowIndex = kdtEconomy.getSelectManager().getActiveRowIndex();
		ICell economyCell = kdtEconomy.getCell(rowIndex, PAYMENTTYPE);
		PaymentTypeInfo currentInfo = (PaymentTypeInfo) economyCell.getValue();
		if (FDCHelper.isEmpty(currentInfo)) {
			return;
		}
		int columnCount = kdtEconomy.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getName())) {
			int flag = 0;
			for (int i = 0; i < columnCount; i++) {
				PaymentTypeInfo forInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getName())) {
					if (currentInfo.getName().equals(forInfo.getName())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("本框架合约经济条款内已经有\"" + currentInfo.getName() + "\"付款类型，不能再继续添加此付款类型了！");
							economyCell.setValue(null);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	private void preparePCData() {
		// 单据头信息
		if (pcInfo.getParent() != null) {
			String longName = pcInfo.getDisplayName();
			if(longName!=null){
				String headName = longName.substring(0, longName.lastIndexOf('.'));
				this.txtParentLongName.setText(headName);// 上级框架合约长名称
			}
		}
		txtNumber.setText(pcInfo.getLongNumber());// 框架合约长编码
		txtName.setText(pcInfo.getName() == null ? null : pcInfo.getName().trim());// 名称
		txtAmount.setValue(pcInfo.getAmount());// 规划金额
		txtControlAmount.setValue(pcInfo.getControlAmount());// 控制金额
		kdcInviteWay.setSelectedItem(pcInfo.getInviteWay());// 招标方式
		txtEstimateAmount.setValue(pcInfo.getEstimateAmount());// 预估金额
		if(this.oprtState.equals(OprtState.VIEW)){
			if(pcInfo.getInviteOrg()!=null){
				CostCenterOrgUnitInfo orgUnitInfo = null;
				try {
					orgUnitInfo = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitInfo(new ObjectUuidPK(pcInfo.getInviteOrg().getId()));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				prmtInviteOrg.setData(orgUnitInfo);
			}
		}else{
			prmtInviteOrg.setData(pcInfo.getInviteOrg());// 招标组织
		}
		txtWorkContent.setText(pcInfo.getWorkContent());// 工作内容
		txtSupMaterial.setText(pcInfo.getSupMaterial());// 甲供及甲指材设		
		txtDescription.setText(pcInfo.getDescription());// 备注
		txtDescription.setToolTipText(pcInfo.getDescription());
		txtAttachment.setText(getAllAttachmentName(pcInfo.getId().toString()).toString());// 附件

		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

		// 分录信息
		addCostLine(kdtCost);
		addEconomyLine(kdtEconomy);

		int level = pcInfo.getLevel();
		if (level > 1) {
			String longNumber = pcInfo.getLongNumber();
			if (!FDCHelper.isEmpty(longNumber)) {
				LimitedTextDocument document = new LimitedTextDocument(longNumber);
				String text = txtNumber.getText();
				txtNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtNumber.setText(text);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
		}
		if (level == 1) {
			LimitedTextDocument document = new LimitedTextDocument("");
			String text = txtNumber.getText();
			txtNumber.setDocument(document);
			document.setIsAutoUpdate(true);
			document.setIsOnload(true);
			txtNumber.setText(text);
			document.setIsAutoUpdate(false);
			document.setIsOnload(false);
		}
	}

	/**
	 * 初始化框架合约成本构成分录数据
	 * 
	 * @param table
	 */
	private void addCostLine(KDTable table) {
		projectF7();
		
		if (pcInfo.getCostEntries().size() > 0) {
			sortCollection(pcInfo.getCostEntries(), "costAccount.longNumber", true);
			IRow row;
			for (int i = 0; i < pcInfo.getCostEntries().size(); i++) {
				ProgrammingContracCostInfo pccInfo = pcInfo.getCostEntries().get(i);
				row = table.addRow();
				if (pccInfo.getId() == null) {
					pccInfo.setId(BOSUuid.create(pccInfo.getBOSType()));
				}
				row.getCell(COST_ID).setValue(pccInfo.getId());
				CurProjectInfo project = null;
				if (pccInfo.getCostAccount() != null) {
					project = pccInfo.getCostAccount().getCurProject();
				}
				row.getCell(PROJECT).setValue(project);
				costAccountCellF7(project, i, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);// 根据当前行工程项目加载F7成本科目
				row.getCell(COSTACCOUNT_NUMBER).setValue(pccInfo.getCostAccount().getLongNumber().replace('!', '.'));
				row.getCell(COSTACCOUNT).setValue(pccInfo.getCostAccount());
				row.getCell(GOALCOST).setValue(pccInfo.getGoalCost());
				row.getCell(ASSIGNED).setValue(pccInfo.getAssigned());
				row.getCell(ASSIGNING).setValue(pccInfo.getAssigning());
				row.getCell(CONTRACTASSIGN).setValue(pccInfo.getContractAssign());
				row.getCell(COST_DES).setValue(pccInfo.getDescription());
			}
		}
	}
	
	/**
	 * 对集合按某字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public  void sortCollection(IObjectCollection cols, final String sortColName, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	
	public  Object getValue(IObjectValue value, String key){
		int in = key.indexOf(".");
		if(in == -1){
			return value.get(key);
		}else{
			Object tmp = value.get(key.substring(0, in));
			if(tmp != null  &&  tmp instanceof IObjectValue){
				return getValue((IObjectValue) tmp, key.substring(in + 1, key.length()));
			}
		}
		return null;
	}
	/**
	 * 初始化框架合约经济条款分录数据
	 * 
	 * @param table
	 */
	private void addEconomyLine(KDTable table) {
		ProgrammingContractEconomyCollection pcEnonomyCollection = pcInfo.getEconomyEntries();
		IRow row;
		for (int i = 0; i < pcEnonomyCollection.size(); i++) {
			ProgrammingContractEconomyInfo pcEnonomyInfo = pcEnonomyCollection.get(i);
			row = table.addRow();
			if (pcEnonomyInfo.getId() == null) {
				pcEnonomyInfo.setId(BOSUuid.create(pcEnonomyInfo.getBOSType()));
			}
			row.getCell(ECONOMY_ID).setValue(pcEnonomyInfo.getId());
			row.getCell(PAYMENTTYPE).setValue(pcEnonomyInfo.getPaymentType());
			if (pcEnonomyInfo.getScale() != null) {
				row.getCell(SCALE).setValue(pcEnonomyInfo.getScale());
			}
			row.getCell(AMOUNT).setValue(pcEnonomyInfo.getAmount());
			row.getCell(CONDITION).setValue(pcEnonomyInfo.getCondition());
			row.getCell(PAYMENTDATE).setValue(pcEnonomyInfo.getPaymentDate());
			row.getCell(ECONOMY_DES).setValue(pcEnonomyInfo.getDescription());
		}
		paymentTypeF7();
		paymentDate();
	}

	/**
	 * 通过框架ID获取所关联的经济条款
	 * 
	 * @param pteID
	 * @return
	 */
	private ProgrammingContractEconomyCollection getPCEconomy(String pcID) {
		ProgrammingContractEconomyInfo pcEnonomyInfo = null;
		PaymentTypeInfo paymentInfo = null;
		ProgrammingContractEconomyCollection pceCollection = new ProgrammingContractEconomyCollection();
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select FID,FPAYMENTTYPEID  from T_CON_ProgContEconomy ");
		fdcBuilder.appendSql(" where FContractID = '" + pcID + "'");
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			for (int i = 0; i < rs.size(); i++) {
				if (rs.next()) {
					pcEnonomyInfo = ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyInfo(
							new ObjectUuidPK(rs.getString("FID")));
					paymentInfo = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(
							new ObjectUuidPK(rs.getString("FPAYMENTTYPEID")));
					pcEnonomyInfo.setPaymentType(paymentInfo);
					pceCollection.add(pcEnonomyInfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return pceCollection;
	}

	/**
	 * 框架合约名称是否已存在
	 * 
	 * @param name
	 */
	private void isNameDup(String name, String id) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingContract where FName_l2 = '" + name + "' ");
		fdcBuilder.appendSql(" and FID <> '" + id + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("框架合约名称已存在，请重新输入");
				txtName.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 框架合约编码是否已存在
	 * 
	 * @param number
	 */
	private void isNumberDup(String longNumber, String id) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingContract where FLongNumber = '" + longNumber + "' ");
		fdcBuilder.appendSql(" and FID <> '" + id + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("框架合约编码已存在，请重新输入");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// 在新增状态下，要进行附件的新增，故保留临时附件的id
	private String attachMentTempID = null;

	/**
	 * 附件管理
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		boolean isEdit = false;// 默认为查看状态
		if (OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) {
			isEdit = true;
		}
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		AttachmentUIContextInfo info = getAttacheInfo();
		if (info == null) {
			info = new AttachmentUIContextInfo();
		}
		if (FDCHelper.isEmpty(info.getBoID())) {
			String boID = getSelectBOID();
			if (boID == null) {
				if (!isEdit) {
					if (attachMentTempID == null) {
						boID = acm.getAttID().toString();
						attachMentTempID = boID;
					} else {
						boID = attachMentTempID;
					}
				} else {
					return;
				}
			}
			info.setBoID(boID);
			acm.showAttachmentListUIByBoID(boID, this, isEdit);
		}
		info.setEdit(isEdit);
		if (getSelectBOID() != null) {
			StringBuffer allAttachmentName = getAllAttachmentName(getSelectBOID());
			if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
				pcInfo.setAttachment(allAttachmentName.toString());
				this.txtAttachment.setText(allAttachmentName.toString());
				this.txtAttachment.setToolTipText(allAttachmentName.toString());
			} else {
				pcInfo.setAttachment(null);
				this.txtAttachment.setText(null);
			}
		}
	}

	protected AttachmentUIContextInfo getAttacheInfo() {
		return null;
	}

	protected final String getSelectBOID() {
		if (pcInfo == null)
			return null;
		String boID = pcInfo.getId() != null ? pcInfo.getId().toString() : null;
		return boID;
	}

	/**
	 * 获取合约框架所有附件名称字符串，名称与乐称以";"相隔
	 * 
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		System.out.println("sql:" + fdcBuilder.getSql().toString());
		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (rs.isLast()) {
					sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
				} else {
					sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 工程项目F7
	 * 
	 */
	private void projectF7() {
		KDBizPromptBox kdtEconomyEntriese_costAccount_PromptBox = new KDBizPromptBox();
		kdtEconomyEntriese_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		kdtEconomyEntriese_costAccount_PromptBox.setVisible(true);
		kdtEconomyEntriese_costAccount_PromptBox.setEditable(true);
		kdtEconomyEntriese_costAccount_PromptBox.setDisplayFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setEditFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEconomyEntriese_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEconomyEntriese_costAccount_PromptBox);
		this.kdtCost.getColumn(PROJECT).setEditor(kdtEconomyEntriese_costAccount_CellEditor);
		ObjectValueRender kdtCostEntries_paymentType_OVR = new ObjectValueRender();
		kdtCostEntries_paymentType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCost.getColumn(PROJECT).setRenderer(kdtCostEntries_paymentType_OVR);
	}

	/**
	 * 成本科目F7 根椐工程项目过滤
	 */
	private void costAccountF7(CurProjectInfo project,ProgrammingContractCollection pcCol) {
		CostAccountPromptBox selector = new CostAccountPromptBox(this,pcCol,(AimCostInfo) this.getUIContext().get("aimCostInfo"));
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");

		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		kdtCost.getColumn(COSTACCOUNT).setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCost.getColumn(COSTACCOUNT).setRenderer(kdtCostEntries_costAccount_OVR);
	}

	/**
	 * 成本科目F7工程项目过滤
	 */
	private void costAccountCellF7(CurProjectInfo project,int rowIndex,int colIndex,ProgrammingContractCollection pcCol) {
		CostAccountPromptBox selector = new CostAccountPromptBox(this,pcCol,(AimCostInfo) this.getUIContext().get("aimCostInfo"));
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");

		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		caEditor.setValue(prmtCostAccount);
		kdtCost.getCell(rowIndex, colIndex).setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		kdtCost.getCell(rowIndex, colIndex).setRenderer(kdtCostEntries_costAccount_OVR);
	}

	/**
	 * 付款类型F7
	 */
	private void paymentTypeF7() {
		KDBizPromptBox kdtEconomyEntriese_costAccount_PromptBox = new KDBizPromptBox();
		kdtEconomyEntriese_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
		kdtEconomyEntriese_costAccount_PromptBox.setVisible(true);
		kdtEconomyEntriese_costAccount_PromptBox.setEditable(true);
		kdtEconomyEntriese_costAccount_PromptBox.setDisplayFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setEditFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEconomyEntriese_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEconomyEntriese_costAccount_PromptBox);
		this.kdtEconomy.getColumn(PAYMENTTYPE).setEditor(kdtEconomyEntriese_costAccount_CellEditor);
		ObjectValueRender kdtCostEntries_paymentType_OVR = new ObjectValueRender();
		kdtCostEntries_paymentType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEconomy.getColumn(PAYMENTTYPE).setRenderer(kdtCostEntries_paymentType_OVR);
	}

	/**
	 * 付款日期
	 */
	private void paymentDate() {
		KDDatePicker kdDataPicker = new KDDatePicker();
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(kdDataPicker);
		kdtEconomy.getColumn(PAYMENTDATE).setEditor(cellEditor);
		ObjectValueRender ovr = new ObjectValueRender();
		kdtEconomy.getColumn(PAYMENTDATE).setRenderer(ovr);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("是否确定修改？") == MsgBox.CANCEL) {
			return;
		}
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			IRow row = kdtEconomy.getRow(i);
			ICell costAccountCell = row.getCell(PAYMENTTYPE);
			ICell scale = row.getCell(SCALE);
			
			if (FDCHelper.isEmpty(row.getCell(PAYMENTDATE))) {
				FDCMsgBox.showInfo("计划付款日期不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
				FDCMsgBox.showInfo("付款类型不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(scale.getValue())) {
				FDCMsgBox.showInfo("计划付款比例不能为空！");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(row.getCell(AMOUNT))) {
				FDCMsgBox.showInfo("计划付款金额不能为空！");
				SysUtil.abort();
			}
		}
		pcInfo.getEconomyEntries().clear();
		int enonomy_rowCount = kdtEconomy.getRowCount();
		for(int i=0;i<enonomy_rowCount;i++){
			ProgrammingContractEconomyInfo pciInfo = new ProgrammingContractEconomyInfo();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();// 付款类型
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();//付款比例
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();//付款金额
			Object conditionObj = kdtEconomy.getCell(i, CONDITION).getValue();//付款条件
			Object paymentDateObj = kdtEconomy.getCell(i, PAYMENTDATE).getValue();//付款日期
			Object descriptionObj = kdtEconomy.getCell(i, ECONOMY_DES).getValue();// 备注
			pciInfo.setPaymentType(currentInfo);// 存储付款类型
			if(!FDCHelper.isEmpty(scaleObj)){
				pciInfo.setScale(new BigDecimal(scaleObj.toString()));// 存储付款比例
			}
			if(!FDCHelper.isEmpty(amountObj)){
				pciInfo.setAmount(new BigDecimal(amountObj.toString()));// 存储付款金额
			}
			pciInfo.setCondition((String) conditionObj);// 存储付款条件
			if(!FDCHelper.isEmpty(paymentDateObj)){
				Date paymentDate = (Date) paymentDateObj;
				pciInfo.setPaymentDate(new Timestamp(paymentDate.getTime()));// 存储付款日期
			}
			pciInfo.setDescription((String) descriptionObj);// 存储付款条件
			pcInfo.getEconomyEntries().add(pciInfo);
		}
		ProgrammingContractFactory.getRemoteInstance().submit(pcInfo);
	}
}