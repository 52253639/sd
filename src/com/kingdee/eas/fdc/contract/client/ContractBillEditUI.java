/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.apache.poi1.hwpf.extractor.WordExtractor;

import javax.swing.filechooser.FileFilter;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.activeX.ActiveXDIY;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.Attachment;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.eip.fts.documents.ExtractorMsWord;
import com.kingdee.eas.cp.eip.fts.documents.I_ExtractionResult;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBailEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractModelFactory;
import com.kingdee.eas.fdc.contract.ContractModelInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSettleTypeEnum;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.invite.DirectAccepterResultCollection;
import com.kingdee.eas.fdc.invite.DirectAccepterResultFactory;
import com.kingdee.eas.fdc.invite.DirectAccepterResultInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultCollection;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultInfo;
import com.kingdee.eas.fdc.invite.client.DirectAccepterResultEditUI;
import com.kingdee.eas.fdc.invite.client.TenderAccepterResultEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.ma.bg.client.BgBalanceViewUI;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:合同单据编辑界面
 * @author liupd  date:2006-10-10 <p>
 * @version EAS5.1.3
 */
public class ContractBillEditUI extends AbstractContractBillEditUI implements IWorkflowUISupport {
	private KDComboBox isLonelyCalCombo = null;
	
	private static final String DETAIL_DEF_ID = "detailDef.id";

	private static final Logger logger = CoreUIObject
			.getLogger(ContractBillEditUI.class);

	//责任人是否可以选择其他部门的人员
	private boolean canSelectOtherOrgPerson = false;

	//项目合同签约总金额超过项目
	private String controlCost;

	//控制方式
	private String controlType;

	//合同审批前进行拆分
	private boolean splitBeforeAudit;
	
	//是否显示“合同费用项目”
	private boolean isShowCharge = false;
	//房地产业务系统正文管理是否启用审批笔迹留痕功能
	boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
	
	//补充合同可以选择提交状态的主合同
	private boolean isSupply = false;
	
	
	//add by david_yang PT043562 2011.03.29 此单据是否有附件
	private boolean isHasAttchment = false;
	
	/**
	 * output class constructor
	 */
	public ContractBillEditUI() throws Exception {
		super();
		//放在onLoad方法中
		//		SwingUtilities.invokeLater(new Runnable() {
		//			public void run() {
		//				prmtcontractType.requestFocus(true);
		//			}
		//		});
		jbInit();
	}
	
	/**
	 * 合同提交审批时是否必须与计划任务进行关联 2010-08-09
	 * 
	 * @return
	 */
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return isRealtedTask;
	}
	private void jbInit() {
		titleMain = this.getUITitle();
	}

	//变化事件
	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} else if ("grtAmount".equals(listener.getShortCut())) {
			setGrtAmt("txtGrtAmount", e);
		} else if ("grtRate".equals(listener.getShortCut())) {
			setGrtAmt("txtGrtRate", e);
		} else if ("amount".equals(listener.getShortCut())){
			setGrtAmt("txtGrtRate", e);
		}
	}

	//业务日期变化事件
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	ControlDateChangeListener amountChangeListener = new ControlDateChangeListener(
		"amount");
	
	ControlDateChangeListener grtAmountChangeListener = new ControlDateChangeListener(
			"grtAmount");

	ControlDateChangeListener grtRateChangeListener = new ControlDateChangeListener(
			"grtRate");

	protected void attachListeners() {
		pkbookedDate.addDataChangeListener(bookedDateChangeListener);
		txtamount.addDataChangeListener(amountChangeListener);
		txtGrtAmount.addDataChangeListener(grtAmountChangeListener);
		txtGrtRate.addDataChangeListener(grtRateChangeListener);
		
		addDataChangeListener(comboCurrency);
		addDataChangeListener(contractPropert);
		addDataChangeListener(contractSource);
		addDataChangeListener(prmtModel);
		//添加责任部门监听器
		addDataChangeListener(prmtRespDept);
		//addDataChangeListener(comboCurrency);

		addDataChangeListener(prmtcontractType);
		addDataChangeListener(txtamount);
		addDataChangeListener(txtExRate);
		addDataChangeListener(txtStampTaxRate);
	}

	protected void detachListeners() {
		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
		txtamount.removeDataChangeListener(amountChangeListener);
		txtGrtAmount.removeDataChangeListener(grtAmountChangeListener);
		txtGrtRate.removeDataChangeListener(grtRateChangeListener);
		
		removeDataChangeListener(comboCurrency);
		removeDataChangeListener(contractPropert);
		removeDataChangeListener(contractSource);
		removeDataChangeListener(prmtModel);
		removeDataChangeListener(prmtcontractType);
		removeDataChangeListener(txtamount);
		removeDataChangeListener(txtGrtAmount);
		removeDataChangeListener(txtExRate);
		removeDataChangeListener(txtGrtRate);
		removeDataChangeListener(txtStampTaxRate);
		//除掉责任部门监听器
		removeDataChangeListener(prmtRespDept);
	}

	/** 是否使用不计成本的金额, 是否单据计算 = 是, 此变量值为 fasle, 是否单据计算 = 否, 此变量值 = true, 
	 * 如果详细信息没有是否单独计算, 则此变量值为 false
	 */
	protected boolean isUseAmtWithoutCost = false;

	public void loadFields() {

		detachListeners();

		super.loadFields();
		tblBail.removeRows();
		//很无语的东西，已经都绑定好了的东西，在保存的时候已经存进了数据库，加载数据的时候也取到了数据，可就是不会填充
		//到表格中来，郁闷死了！ 只能自己维护使其填值了，竟然不加载已经绑定好的单据的分录的分录到表格中去  by Cassiel_peng  2008-8-27
		this.tblEconItem.getColumn("payCondition").getStyleAttributes().setWrapText(true);
		this.tblEconItem.getColumn("desc").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("bailCondition").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("desc").getStyleAttributes().setWrapText(true);
		if(this.editData.getBail()!=null&&this.editData.getBail().getEntry()!=null){
			for (int i = 0; i < this.editData.getBail().getEntry().size(); i++) {
				ContractBailEntryInfo entry = this.editData.getBail().getEntry().get(i);
				IRow row=tblBail.addRow();
				row.setUserObject(entry);
				row.getCell("bailAmount").setValue(FDCHelper.toBigDecimal(entry.getAmount(),2));
				row.getCell("bailDate").setValue(entry.getBailDate());
				row.getCell("bailRate").setValue(entry.getProp());
				row.getCell("bailCondition").setValue(entry.getBailConditon());
				row.getCell("desc").setValue(entry.getDesc());
			}
		}
		
		isUseAmtWithoutCost = editData.isIsAmtWithoutCost();
		loadDetailEntries();
          
		this.txtOverAmt.setValue(new Double(editData.getOverRate()));
		
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}

		//币别选择
		GlUtils.setSelectedItem(comboCurrency, editData.getCurrency());

		txtExRate.setValue(editData.getExRate());
		txtLocalAmount.setValue(editData.getAmount());
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(
					baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			}
		}

		if (editData != null && editData.getCurProject() != null) {

			//			String projId = editData.getCurProject().getId().toString();
			//			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);

			//解决bug BT315446 注释代码 txtProj.setText(curProject.getDisplayName());
			//txtProj.setText(curProject.getDisplayName());
			txtProj.setText(editData.getCurProject().getDisplayName());

			FullOrgUnitInfo costOrg = this.orgUnitInfo;
			//FDCHelper.getCostCenter(editData.getCurProject(),null).castToFullOrgUnitInfo(); 
			//FDCClientUtils.getCostOrgByProj(projId);

			txtOrg.setText(editData.getCurProject().getFullOrgUnit().getName());
			editData.setOrgUnit(costOrg);
			editData.setCU(curProject.getCU());
		}

		attachListeners();
		//2009-1-20 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
		
		this.comboCoopLevel.setSelectedItem(editData.getCoopLevel());
		
		setCapticalAmount();
		
		//加载合同结算类型
		loadContractSettleType();
		
		try {
			loadContractModel();
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		//加载附件
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
		
		try {
			loadInvite();
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		try {
			loadSupplyEntry();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void loadContractModel() throws EASBizException, BOSException {
		removeDataChangeListener(prmtModel);
		if(editData.getContractModel()!=null){
			prmtModel.setEnabled(false);
			ContractModelInfo info = ContractModelFactory.getRemoteInstance().getContractModelInfo(new ObjectUuidPK(editData.getContractModel()));
			this.prmtModel.setValue(info);
		}
		addDataChangeListener(prmtModel);
	}

	protected void loadContractSettleType() {
		ContractSettleTypeEnum  cst = this.editData.getContractSettleType();
		if(ContractSettleTypeEnum.AType.equals(cst)){
			this.btnAType.setSelected(true);
		} else if(ContractSettleTypeEnum.BType.equals(cst)){
			this.btnBType.setSelected(true);
		} else if(ContractSettleTypeEnum.CType.equals(cst)){
			this.BtnCType.setSelected(true);
		}
		removeDataChangeListener(prmtCreateOrg);
		this.prmtCreateOrg.setValue(this.editData.getCreateDept());
		addDataChangeListener(prmtCreateOrg);
	}

	/**
	 * 设置原币金额大写和本币金额大写
	 * 
	 * @author owen_wen 2011-06-09
	 */
	private void setCapticalAmount() {
		BigDecimal amount = FDCHelper.toBigDecimal(txtamount.getBigDecimalValue());
		this.txtOrgAmtBig.setText(FDCClientHelper.getChineseFormat(amount, false));
		this.txtOrgAmtBig.setHorizontalAlignment(JTextField.RIGHT);

		BigDecimal localAmt = FDCHelper.toBigDecimal(txtLocalAmount.getBigDecimalValue());
		this.txtAmtBig.setText(FDCClientHelper.getChineseFormat(localAmt, false));
		this.txtAmtBig.setHorizontalAlignment(JTextField.RIGHT);
	}
	
	/*
	 * 查看合同是否关联框架合约
	 * 
	 * @seecom.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#
	 * actionViewProgramCon_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewProgramCon_actionPerformed(ActionEvent e) throws Exception {
		// TODO 合同关联
		if (this.editData.getProgrammingContract() == null) {
			MsgBox.showInfo(this, "该合同没有关联框架合约!");
			this.abort();
		}

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ContractBillInfo contractBillInfo = (ContractBillInfo) editData;
		ProgrammingContractInfo proContInfo = getProContInfo(contractBillInfo.getProgrammingContract().getId().toString());
		contractBillInfo.setProgrammingContract(proContInfo);
		uiContext.put("programmingContract", proContInfo);
		uiContext.put("programmingContractTemp", "programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	/**
	 * 获取所关联的规划合约
	 * 
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id), selItemCol);
		return pcInfo;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		//保存履约保证金及返还部分
		ContractBailInfo contractBailInfo=this.editData.getBail();
		if(contractBailInfo==null){
			contractBailInfo=new ContractBailInfo();
			contractBailInfo.setId(BOSUuid.create(contractBailInfo.getBOSType()));
			this.editData.setBail(contractBailInfo);
		}
		contractBailInfo.setAmount(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2));
		contractBailInfo.setProp(FDCHelper.toBigDecimal(txtBailRate.getBigDecimalValue(),2));
		ContractBailEntryCollection coll=contractBailInfo.getEntry();
		coll.clear();
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			IRow row=tblBail.getRow(i);
			ContractBailEntryInfo bailEntryInfo=(ContractBailEntryInfo)row.getUserObject();
			bailEntryInfo.setParent(contractBailInfo);
			//此处最好还是处理一下有分录行但其实用户并没有真正输入值的情况
			if(row.getCell("bailAmount").getValue()!=null){
				bailEntryInfo.setAmount(FDCHelper.toBigDecimal(row.getCell("bailAmount").getValue(),2));
			}
			if(row.getCell("bailRate").getValue()!=null){
				bailEntryInfo.setProp(FDCHelper.toBigDecimal(row.getCell("bailRate").getValue(),2));
			}
			if(row.getCell("bailDate").getValue()!=null){
				bailEntryInfo.setBailDate(row.getCell("bailDate").getValue()==null?DateTimeUtils.truncateDate(new Date()):(Date)row.getCell("bailDate").getValue());
			}
			bailEntryInfo.setBailConditon((String)row.getCell("bailCondition").getValue());
			bailEntryInfo.setDesc((String)row.getCell("desc").getValue());
			coll.add(bailEntryInfo);
		}

		
		editData.setSignDate(DateTimeUtils.truncateDate(editData.getSignDate()));
		editData.setIsAmtWithoutCost(isUseAmtWithoutCost);
		editData.setOverRate(Double.valueOf(this.txtOverAmt.getText()).doubleValue());
		super.storeFields();
		storeDetailEntries();
		storeContractSettleType();
	
	}
	private void storeContractSettleType() {
		if(this.btnAType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.AType);
		} else if(this.btnBType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.BType);
		} else if(this.BtnCType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.CType);
		}
		this.editData.setCreateDept((AdminOrgUnitInfo)this.prmtCreateOrg.getValue());
	}

	protected void prmtMainContract_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo view = null;		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSubContract",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("contractSourceId.id",ContractSourceInfo.COOP_VALUE));
		if (prmtpartB.getData() != null) 
			//加上过滤条件：战略主合同与子合同的乙方相同，added by Owen_wen 2010-8-18
			filter.getFilterItems().add(new FilterItemInfo("partB.id", ((SupplierInfo)prmtpartB.getData()).getId().toString())); 		
		if(prmtMainContract.getQueryAgent().getEntityViewInfo() != null){
			view = prmtMainContract.getQueryAgent().getEntityViewInfo();
			view.setFilter(filter);
		}else{
			view = new EntityViewInfo();
			view.setFilter(filter);
		}
		prmtMainContract.setEntityViewInfo(view);
		prmtMainContract.getQueryAgent().resetRuntimeEntityView();
		
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
		ContractBillEditDataProvider dataPvd = new ContractBillEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getBail().getId().toString());
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
		ContractBillEditDataProvider dataPvd = new ContractBillEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint");
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillFactory
				.getRemoteInstance();
	}

	/**
	 * 
	 * 描述：合同详细信息Table
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	private KDTable getDetailInfoTable() {
		return tblDetail;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		if(table==this.tblEconItem){
			return new ContractPayItemInfo();
		}else if(table==this.tblBail){
			ContractBailEntryInfo contractBailEntryInfo = new ContractBailEntryInfo();
			contractBailEntryInfo.setId(BOSUuid.create(contractBailEntryInfo.getBOSType()));
			return contractBailEntryInfo;
		}else{
			return new ContractBillEntryInfo();
		}
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setRespPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
		objectValue.setRespPerson(person);
		AdminOrgUnitInfo adminInfo = null;
		if(person!=null){
			try {
				SelectorItemCollection personsel=new SelectorItemCollection();
				personsel.add("position.adminOrgUnit.*");
				if(person!=null){
					EntityViewInfo view = new EntityViewInfo();
		    		FilterInfo filter = new FilterInfo();
		    		filter.getFilterItems().add(new FilterItemInfo("person.id",person.getId()));
		    		filter.getFilterItems().add(new FilterItemInfo("isPrimary",Boolean.TRUE));
		    		view.setFilter(filter);
		    		view.setSelector(personsel);
					
					PositionMemberCollection pmcol=PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
					if(pmcol.size()>0){
						adminInfo=pmcol.get(0).getPosition().getAdminOrgUnit();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
		objectValue.setRespDept(adminInfo);
		objectValue.setCreateDept(adminInfo);
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setSignDate(serverDate);
//		objectValue.setProgrammingContract(item)

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);

		// 默认直接合同
		objectValue.setContractPropert(ContractPropertyEnum.DIRECT);
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		if (projId == null) {
			projId = editData.getCurProject().getId();
		}

		CurProjectInfo curProjectInfo = this.curProject;
		objectValue.setCurProject(curProjectInfo);
		if (curProjectInfo.getLandDeveloper() != null)
			objectValue.setLandDeveloper(curProjectInfo.getLandDeveloper());

		try {
//			默认委托
//			objectValue.setContractSourceId(ContractSourceFactory
//					.getRemoteInstance().getContractSourceInfo(
//							new ObjectUuidPK(ContractSourceInfo.TRUST_VALUE)));
			//以前是默认委托，现在改成成自动选择取列表中的第一项或者为空白
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			evi.setFilter(filter);
			SorterItemCollection sic = new SorterItemCollection();
			SorterItemInfo sorts = new SorterItemInfo();
			sorts.setPropertyName("number");
			sorts.setSortType(SortType.ASCEND);
			sic.add(sorts);	
			evi.setSorter(sic);
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			
			objectValue.setContractSourceId((ContractSourceInfo)(ContractSourceFactory.getRemoteInstance().getCollection(evi).get(0)));
			
			if (typeId != null) {
				ContractTypeInfo contractTypeInfo = null;

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("number");
				selector.add("name");
				selector.add("longNumber");
				selector.add("isLeaf");
				selector.add("dutyOrgUnit.id");
				selector.add("dutyOrgUnit.number");
				selector.add("dutyOrgUnit.name");
				selector.add("payScale");
				selector.add("stampTaxRate");
				selector.add("isCost");
				selector.add("orgType");
				selector.add("contractWFTypeEntry.contractWFType.*");
				contractTypeInfo = ContractTypeFactory
						.getRemoteInstance()
						.getContractTypeInfo(new ObjectUuidPK(typeId), selector);

				objectValue.setContractType(contractTypeInfo);
//				objectValue.setRespDept(contractTypeInfo.getDutyOrgUnit());
				objectValue.setIsCoseSplit(contractTypeInfo.isIsCost());
				objectValue.setPayScale(contractTypeInfo.getPayScale());

				objectValue.setStampTaxRate(contractTypeInfo.getStampTaxRate());
				
				if(ContractTypeOrgTypeEnum.BIGRANGE.equals(contractTypeInfo.getOrgType())||ContractTypeOrgTypeEnum.SMALLRANGE.equals(contractTypeInfo.getOrgType())){
					objectValue.setOrgType(contractTypeInfo.getOrgType());
				}
				if(contractTypeInfo.getContractWFTypeEntry().size()==1){
					objectValue.setContractWFType(contractTypeInfo.getContractWFTypeEntry().get(0).getContractWFType());
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}

		// 签约日期
		// objectValue.setSignDate(new Date());

		//造价性质
		objectValue.setCostProperty(CostPropertyEnum.BASE_CONFIRM);

		/*
		 * 变更提示比例％数值设置缺省值5.00，手工修改（可以在【预警】设置初始值）。结算提示比例％数值设置缺省值100.00，手工修改（可以在【预警】设置初始值）。付款提示比例％数值设置缺省值85.00，手工修改（可以在【预警】设置初始值）。
		 */
		objectValue.setPayPercForWarn(new BigDecimal("85"));
		objectValue.setChgPercForWarn(new BigDecimal("5"));

		//未结算
		objectValue.setHasSettled(false);

		objectValue.setGrtRate(FDCHelper.ZERO);

		objectValue.setCurrency(baseCurrency);
		objectValue.setExRate(FDCHelper.ONE);
		objectValue.setAmount(FDCHelper.ZERO);
		objectValue.setOriginalAmount(FDCHelper.ZERO);
		objectValue.setPayScale(FDCHelper.ZERO);
		objectValue.setGrtAmount(FDCHelper.ZERO);

		objectValue.setCU(curProjectInfo.getCU());
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);

		//期间
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		mainContractId = null;
		detailAmount = FDCHelper.ZERO;
		//还需要保存 履约保证金及返还部分
		ContractBailInfo contractBail=new ContractBailInfo();
		contractBail.setId(BOSUuid.create(contractBail.getBOSType()));
		objectValue.setBail(contractBail);
		objectValue.setIsOpenContract(false);
		objectValue.setIsStardContract(false);
		return objectValue;
	}

	private void setInviteCtrlVisibleByContractSource(
			ContractSourceInfo contractSource) {
		if (contractSource == null || contractSource.getId() == null)
			return;
		String sourceId = contractSource.getId().toString();
		if (ContractSourceInfo.TRUST_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
		} else if (ContractSourceInfo.INVITE_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(true);
			// 二标价
			contsecondPrice.setVisible(false);
			// 底价
			contbasePrice.setVisible(false);

			//备注
			contRemark.setVisible(false);

			//			战略合作级别
			contCoopLevel.setVisible(false);
			// 计价方式
			contPriceType.setVisible(false);
			
			chkIsSubMainContract.setVisible(false);
			conMainContract.setVisible(false);
			conEffectiveEndDate.setVisible(false);
			conEffectiveStartDate.setVisible(false);
			conInformation.setVisible(false);
			kDScrollPane1.setVisible(false);

		} else if (ContractSourceInfo.DISCUSS_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			// 中标价
			contwinPrice.setVisible(true);
			// 中标单位
			contwinUnit.setVisible(true);
			// 二标价
			contsecondPrice.setVisible(true);
			// 底价
			contbasePrice.setVisible(true);

			//备注
			contRemark.setVisible(true);

		} else if (ContractSourceInfo.COOP_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			//战略合作级别
			contCoopLevel.setVisible(true);
//			// 计价方式
//			contPriceType.setVisible(true);
			chkIsSubMainContract.setVisible(true);
			conMainContract.setVisible(true);
			conEffectiveEndDate.setVisible(true);
			conEffectiveStartDate.setVisible(true);
			conInformation.setVisible(true);
			kDScrollPane1.setVisible(true);
			this.prmtMainContract.setEnabled(false);
		} else {
			setInviteCtrlVisible(false);
		}

	}
	
	

	private void setInviteCtrlVisible(boolean visible) {
		Component[] components = pnlInviteInfo.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(visible);
		}
	}

	/**
	 * 
	 * 描述：合同性质变化，合同详细信息的字段变化
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#contractPropert_itemStateChanged(java.awt.event.ItemEvent)
	 */
	ContractPropertyEnum contractPro = null;

	protected void contractPropert_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getStateChange() == ItemEvent.SELECTED) {
			ContractPropertyEnum contractProp = (ContractPropertyEnum) e
					.getItem();
			contractPro = contractProp;
			contractPropertChanged(contractProp);

		}

		super.contractPropert_itemStateChanged(e);

	}

	private void contractPropertChanged(ContractPropertyEnum contractProp) {
		if (contractProp != null) {

			fillDetailByPropert(contractProp);

			if (contractProp == ContractPropertyEnum.THREE_PARTY) {
				prmtpartC.setRequired(true);
			} else {
				prmtpartC.setRequired(false);
			}
		}
	}

	/*
	 * 表列名
	 */
	private static final String DETAIL_COL = ContractClientUtils.CON_DETAIL_DETAIL_COL;

	private static final String CONTENT_COL = ContractClientUtils.CON_DETIAL_CONTENT_COL;

	private static final String ROWKEY_COL = ContractClientUtils.CON_DETIAL_ROWKEY_COL;

	private static final String DESC_COL = ContractClientUtils.CON_DETIAL_DESC_COL;

	private static final String DATATYPE_COL = ContractClientUtils.CON_DETIAL_DATATYPE_COL;

	/*
	 * 行标识
	 */
	private static final String NA_ROW = ContractClientUtils.MAIN_CONTRACT_NAME_ROW;

	private static final String NU_ROW = ContractClientUtils.MAIN_CONTRACT_NUMBER_ROW;

	private static final String AM_ROW = ContractClientUtils.AMOUNT_WITHOUT_COST_ROW;

	private static final String LO_ROW = ContractClientUtils.IS_LONELY_CAL_ROW;
	
	private static final String ES_ROW = ContractClientUtils.MAIN_CONTRACT_ESTIMATEAMOUNT_ROW;
	//预估单ID
	private static final String ESID_ROW = ContractClientUtils.MAIN_CONTRACT_ESTIMATEID_ROW;

	/*
	 * 主合同id 
	 */
	private String mainContractId = null;

	private BigDecimal detailAmount = FDCHelper.ZERO;

	class MyDataChangeListener implements DataChangeListener {

		public void dataChanged(DataChangeEvent eventObj) {
			String name = null;
			ContractBillInfo info = null;
			if (eventObj.getNewValue() != null) {
				info = (ContractBillInfo) eventObj.getNewValue();
				name = info.getName();
				mainContractId = info.getId().toString();
				if (isUseAmtWithoutCost&& detailAmount.compareTo(FDCHelper.ZERO) > 0) {
					try {
						MsgContractHasSplit();
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				comboCurrency.setEnabled(false);
				for (int i = 0; i < comboCurrency.getItemCount(); i++) {
					Object o = comboCurrency.getItemAt(i);
					if (o instanceof CurrencyInfo) {
						CurrencyInfo c = (CurrencyInfo) o;
						if (c.getId().equals(info.getCurrency().getId())) {
							comboCurrency.setSelectedIndex(i);
						}
					}
				}
				comboCurrency.setEditable(false);
				txtExRate.setValue(info.getExRate());
				txtExRate.setEditable(false);
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("programmingContract.*");
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(info.getId().toString()), sic);
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
				
				if (info.getProgrammingContract() != null) {//主合同是关联了合约规划的
					//如果合约框架被引用，此合约不是最新的目标成本的修订版本，则不能被再次使用 20110327 wangxin
//					if(isNotLastestVersion(info.getProgrammingContract())){
//						FDCMsgBox.showWarning("主合同对应合约规划未关联最新的目标成本，请重新修订合约规划！");
//						name=null;
//						prmtFwContractTemp.setValue(null);
//						textFwContract.setText(null);
//						editData.setProgrammingContract(null);
//						getDetailInfoTable().getCell(getRowIndexByRowKey(NU_ROW),CONTENT_COL).setValue(null);
//						getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),CONTENT_COL).setValue(null);
//						return;
//					}
					if(isLonelyCalCombo.getSelectedItem().equals(BooleanEnum.FALSE)){// 如果非单独计算补充合同
						prmtFwContractTemp.setValue(info.getProgrammingContract());
						textFwContract.setText(info.getProgrammingContract().getName());
						editData.setProgrammingContract(info.getProgrammingContract());
					}else{
						prmtFwContractTemp.setValue(info.getProgrammingContract());
						textFwContract.setText(info.getProgrammingContract().getName());
						editData.setProgrammingContract(info.getProgrammingContract());
						//显示预估金额（主合同最新的）
//						if(info!=null){
//							ContractEstimateChangeBillCollection estimateColl;
//							try {
//								EntityViewInfo view = new EntityViewInfo();
//								FilterInfo filter = new FilterInfo();
//								view.setFilter(filter);
//								filter.getFilterItems().add(new FilterItemInfo("contractbill.id",info.getId().toString()));
//								filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//								filter.getFilterItems().add(new FilterItemInfo("isUsed",Boolean.FALSE));
//								estimateColl = ContractEstimateChangeBillFactory.getRemoteInstance().getContractEstimateChangeBillCollection(view);
//								if(estimateColl.size()>0){
//									getDetailInfoTable().getCell(getRowIndexByRowKey(ES_ROW),CONTENT_COL).setValue(estimateColl.get(0).getEstimateAmount());
									//保存预估合同变动的ID
//									getDetailInfoTable().getCell(getRowIndexByRowKey(ESID_ROW),CONTENT_COL).setValue(estimateColl.get(0).getId().toString());
//								}else {
//									getDetailInfoTable().getCell(getRowIndexByRowKey(NU_ROW),CONTENT_COL).setValue(null);
//									getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),CONTENT_COL).setValue(null);
//									getDetailInfoTable().getCell(getRowIndexByRowKey(ES_ROW),CONTENT_COL).setValue(null);
//									FDCMsgBox.showWarning("没有有效的预估合同变动不能签补充合同！");
//								}
//							} catch (BOSException e) {
//								logger.error(e);
//								e.printStackTrace();
//							}
//							
//						}
					}
				}
			} else {
				name = null;
				// 清除主合同也清除关联的框架合约
				prmtFwContractTemp.setValue(null);
				textFwContract.setText(null);
				editData.setProgrammingContract(null);
			}
			//			comboCurrency.setSelectedItem(info.getCurrency());
			//设置补充合同币别和汇率和主合同一致
			getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),CONTENT_COL).setValue(name);
		}

	}
	private boolean isNotLastestVersion(ProgrammingContractInfo pcInfo) {
		try{
			String progId = pcInfo.getId().toString();
			SelectorItemCollection sel= new SelectorItemCollection();
			sel.add("programming.aimCost.id");
			sel.add("programming.project.id");
			while(true){
				FilterInfo filter=new FilterInfo();
				EntityViewInfo view=new EntityViewInfo();
				filter.getFilterItems().add(new FilterItemInfo("srcId",progId));
				view.setFilter(filter);
				ProgrammingContractCollection pcol=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view);
				if(pcol.size()>0){
					progId=pcol.get(0).getId().toString();
				}else{
					break;
				}
			}
			ProgrammingContractInfo progammingInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(progId),sel);
			AimCostInfo aimcost = progammingInfo.getProgramming().getAimCost();
			CurProjectInfo project = progammingInfo.getProgramming().getProject();
			EntityViewInfo entityView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId",project.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
			entityView.setFilter(filter);
			AimCostCollection aimCostColl= AimCostFactory.getRemoteInstance().getAimCostCollection(entityView);
			if(aimCostColl.size()>0){
				if(!aimcost.getId().toString().equals(aimCostColl.get(0).getId().toString())){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 
	 * 描述：根据行标识获取行Index
	 * 
	 * @param rowKey
	 * @return
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	private int getRowIndexByRowKey(String rowKey) {
		int rowIndex = 0;

		int rowCount = getDetailInfoTable().getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = (String) getDetailInfoTable().getCell(i, ROWKEY_COL)
					.getValue();
			if (key != null && key.equals(rowKey)) {
				rowIndex = i;
				break;
			}
		}

		return rowIndex;
	}

	// 是否刚刚显示四条附加记录
	boolean lastDispAddRows = false;

	/**
	 * 
	 * 描述：如果合同性质为三方合同、补充合同，则合同详细信息要多显示4个字段
	 * 
	 * @param contractProp
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	private void fillDetailByPropert(ContractPropertyEnum contractProp) {
		if(contractProp == ContractPropertyEnum.SUPPLY){
			this.prmtpartB.setEnabled(false);
			this.prmtlandDeveloper.setEnabled(false);
		}else{
			this.prmtpartB.setEnabled(true);
			this.prmtlandDeveloper.setEnabled(true);
		}

		if (contractProp == ContractPropertyEnum.THREE_PARTY
				|| contractProp == ContractPropertyEnum.SUPPLY) {

			if (lastDispAddRows) {
				ICell cell = getDetailInfoTable().getRow(
						getRowIndexByRowKey(NU_ROW)).getCell(CONTENT_COL);
				
				ICell loCell = getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL);
				if (contractProp == ContractPropertyEnum.SUPPLY) {
					cell.getStyleAttributes().setBackground(
							FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					if (cell.getEditor() != null
							&& cell.getEditor().getComponent() instanceof KDBizPromptBox) {
						KDBizPromptBox box = (KDBizPromptBox) cell.getEditor()
								.getComponent();
						box.setRequired(true);
					}
					if(loCell.getEditor()!=null&&loCell.getEditor().getComponent() instanceof KDComboBox){
						loCell.setValue(BooleanEnum.FALSE);
						loCell.getStyleAttributes().setLocked(true);
						isLonelyChanged(BooleanEnum.FALSE);
					}
				} else {
					cell.getStyleAttributes().setBackground(Color.WHITE);
					if (cell.getEditor() != null
							&& cell.getEditor().getComponent() instanceof KDBizPromptBox) {
						KDBizPromptBox box = (KDBizPromptBox) cell.getEditor()
								.getComponent();
						box.setRequired(false);
					}
					if(loCell.getEditor()!=null&&loCell.getEditor().getComponent() instanceof KDComboBox){
						loCell.getStyleAttributes().setLocked(false);
					}
				}
				return;
			}

			/*
			 * 内容
			 */
			String isLonelyCal = ContractClientUtils.getRes("isLonelyCal");
			String amountWithOutCost = ContractClientUtils
					.getRes("amountWithOutCost");
			String mainContractNumber = ContractClientUtils
					.getRes("mainContractNumber");
			String mainContractName = ContractClientUtils
					.getRes("mainContractName");

			/*
			 * 是否单独计算
			 */
			IRow isLonelyCalRow = getDetailInfoTable().addRow();
			isLonelyCalRow.getCell(ROWKEY_COL).setValue(LO_ROW);
			isLonelyCalRow.getCell(DETAIL_COL).setValue(isLonelyCal);
			KDComboBox isLonelyCalCombo = getBooleanCombo();
			KDTDefaultCellEditor isLonelyCalComboEditor = new KDTDefaultCellEditor(isLonelyCalCombo);
			isLonelyCalRow.getCell(CONTENT_COL).setEditor(isLonelyCalComboEditor);
			isLonelyCalCombo.addItemListener(new IsLonelyChangeListener());
			isLonelyCalRow.getCell(DATATYPE_COL).setValue(DataTypeEnum.BOOL);
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				isLonelyCalRow.getCell(CONTENT_COL).setValue(BooleanEnum.FALSE);
				isLonelyCalRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
			}else{
				isLonelyCalRow.getCell(CONTENT_COL).setValue(BooleanEnum.TRUE);
				isLonelyCalRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(false);
			}
			/*
			 * 不计成本的金额
			 */
			IRow amountWithOutCostRow = getDetailInfoTable().addRow();
			amountWithOutCostRow.getCell(ROWKEY_COL).setValue(AM_ROW);
			amountWithOutCostRow.getCell(DETAIL_COL)
					.setValue(amountWithOutCost);

			amountWithOutCostRow.getCell(CONTENT_COL).setEditor(
					FDCClientHelper.getNumberCellEditor());
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
			// 如果为是，则“不计成本的金额”不允许录入
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			amountWithOutCostRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.NUMBER);

			/*
			 * 对应主合同编码
			 */
			IRow mainContractNumberRow = getDetailInfoTable().addRow();
			mainContractNumberRow.getCell(ROWKEY_COL).setValue(NU_ROW);
			mainContractNumberRow.getCell(DETAIL_COL).setValue(mainContractNumber);
			KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
			KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(kDBizPromptBoxContract);
			ICell cell = mainContractNumberRow.getCell(CONTENT_COL);
			cell.setEditor(prmtContractEditor);
			ObjectValueRender objectValueRender = getF7Render();
			cell.setRenderer(objectValueRender);

			MyDataChangeListener lis = new MyDataChangeListener();
			kDBizPromptBoxContract.addDataChangeListener(lis);

			mainContractNumberRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				cell.getStyleAttributes().setBackground(
						FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				kDBizPromptBoxContract.setRequired(true);
			} else {
				cell.getStyleAttributes().setBackground(Color.WHITE);
				kDBizPromptBoxContract.setRequired(false);
			}

			/*
			 * 对应主合同名称
			 */
			IRow mainContractNameRow = getDetailInfoTable().addRow();
			mainContractNameRow.getCell(ROWKEY_COL).setValue(NA_ROW);
			mainContractNameRow.getCell(DETAIL_COL).setValue(mainContractName);
			mainContractNameRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			mainContractNameRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
			
			//预估合同的金额 20120330
			IRow estimateAmountRow = getDetailInfoTable().addRow();
			estimateAmountRow.getCell(ROWKEY_COL).setValue(ES_ROW);
			estimateAmountRow.getCell(DETAIL_COL).setValue("预估金额");
			estimateAmountRow.getCell(DATATYPE_COL).setValue(DataTypeEnum.NUMBER);
			estimateAmountRow.getCell(CONTENT_COL).setEditor(FDCClientHelper.getNumberCellEditor());
			estimateAmountRow.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			estimateAmountRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
			
			//预估变动的ID 20120330
			IRow estimateID = getDetailInfoTable().addRow();
			estimateID.getCell(ROWKEY_COL).setValue(ESID_ROW);
			estimateID.getCell(DETAIL_COL).setValue("预估金额ID");
			estimateID.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
			estimateID.getStyleAttributes().setHided(true);
			lastDispAddRows = true;
			
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				isLonelyChanged(BooleanEnum.FALSE);
			}else{
				isLonelyChanged(BooleanEnum.TRUE);
			}
		} else {
			if (lastDispAddRows) {
				isLonelyChanged(BooleanEnum.TRUE);
				getDetailInfoTable().removeRow(getRowIndexByRowKey(LO_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(AM_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NU_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NA_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(ES_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(ESID_ROW));
			}
			lastDispAddRows = false;
		}
	}

	private void addKDTableLisener() {
		tblDetail.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				comboCurrency.setEnabled(true);
				txtExRate.setEditable(true);
				KDTSelectManager sm = tblDetail.getSelectManager();
				if (sm.getActiveRowIndex() == getRowIndexByRowKey(LO_ROW)
						&& sm.getActiveColumnIndex() == 2) {
					if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
						e.setCancel(true);
					}
				}
			}
		});
	}

	private ObjectValueRender getF7Render() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		BizDataFormat format = new BizDataFormat("$number$");
		objectValueRender.setFormat(format);
		return objectValueRender;
	}

	private KDBizPromptBox getContractF7Editor() {
		KDBizPromptBox kDBizPromptBoxContract = new KDBizPromptBox();
		kDBizPromptBoxContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
		kDBizPromptBoxContract.setDisplayFormat("$number$");
		kDBizPromptBoxContract.setEditFormat("$number$");
		kDBizPromptBoxContract.setCommitFormat("$number$");
		kDBizPromptBoxContract.setEditable(true);
		FilterInfo filter = new FilterInfo();
		
		FilterItemCollection filterItems = filter.getFilterItems();
		//		filterItems.add(new FilterItemInfo("contractPropert", ContractPropertyEnum.DIRECT_VALUE));
		//		2009-01-13 yx 支持补充合同的主合同为三方合同(财富联合 R081201-122)
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,
				CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled",
				Boolean.TRUE));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.CANCEL_VALUE, CompareType.NOTEQUALS));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.INVALID, CompareType.NOTEQUALS));
		//1.4.3.2.2 只能是已经审批的合同
//		filterItems.add(new FilterItemInfo("state",
//				FDCBillStateEnum.AUDITTED_VALUE));
		Set stateSet = new HashSet();
		stateSet.add(FDCBillStateEnum.AUDITTED_VALUE);
		if(isSupply){
			stateSet.add(FDCBillStateEnum.AUDITTING_VALUE);
			stateSet.add(FDCBillStateEnum.SUBMITTED_VALUE);
		}
		filterItems.add(new FilterItemInfo("state", stateSet, CompareType.INCLUDE));
		//2008-09-17 周勇 已结算的合同不需要出现
		filterItems.add(new FilterItemInfo("hasSettled", Boolean.FALSE));

		String projId = null;
		if (editData.getCurProject() != null
				&& editData.getCurProject().getId() != null) {
			projId = editData.getCurProject().getId().toString();
		} else if (getUIContext().get("projectId") != null) {
			projId = ((BOSUuid) getUIContext().get("projectId")).toString();
		}
		if (projId != null) {
			filterItems.add(new FilterItemInfo("curProject.id", projId
					.toString()));
		}
		if (editData.getId() != null) {
			filterItems.add(new FilterItemInfo("id", editData.getId()
					.toString(), CompareType.NOTEQUALS));
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		kDBizPromptBoxContract.setEntityViewInfo(view);
		return kDBizPromptBoxContract;
	}

	private KDComboBox getBooleanCombo() {
		isLonelyCalCombo = new KDComboBox();
		isLonelyCalCombo.addItems(BooleanEnum.getEnumList().toArray());
		return isLonelyCalCombo;
	}

	class IsLonelyChangeListener implements ItemListener {

		/*
		 * ? 是否单独计算：布尔型，下拉列表，枚举值：是、否。必录项，缺省为是。不可编辑。
		 * 如果为是，则“不计成本的金额”不允许录入，在“合同金额”录入。 如果为否，则“不计成本的金额”需要录入，“合同金额”不允许录入。
		 * 如果在“合同金额”里录入了数据，“是否单独计算”又改为否，则系统自动将“合同金额”里的数据写到“不计成本的金额”里；反之也一样。
		 */
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				BooleanEnum b = (BooleanEnum) e.getItem();
				isLonelyChanged(b);
			}

		}

	}

	/**
	 * 是否单独计算发生改变
	 * @param b
	 */
	private void isLonelyChanged(BooleanEnum b) {
		ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);

		if (b == BooleanEnum.TRUE) {
			// 将关联框架按钮恢复
			cell.getStyleAttributes().setLocked(true);
			txtamount.setEditable(true);
			txtGrtRate.setValue(FDCHelper.ZERO);
			txtGrtRate.setRequired(true);
			txtGrtRate.setEditable(true);
			if (cell.getValue() != null && cell.getValue() instanceof Number) {
				txtamount.setNumberValue((Number) cell.getValue());
				calLocalAmt();
				cell.setValue(null);
			}
			setAmountRequired(true);

			isUseAmtWithoutCost = false;
			// 清除主合同框架合约
			prmtFwContractTemp.setValue(null);
			textFwContract.setText(null);
			editData.setProgrammingContract(null);
		} else {
			// 将关联框架按钮恢复
			cell.getStyleAttributes().setLocked(false);
			txtamount.setEditable(false);
			//若为不独立结算的补充合同 则保修比例不允许录入
			if (contractPro == ContractPropertyEnum.SUPPLY) {
				txtGrtRate.setRequired(false);
				txtGrtRate.setValue(FDCHelper.ZERO);
				txtGrtRate.setEditable(false);
				txtGrtAmount.setRequired(false);
				txtGrtAmount.setEditable(false);
			}
			if (txtamount.getNumberValue() != null) {
				cell.setValue(txtamount.getNumberValue());
				try {
					MsgContractHasSplit();
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			txtamount.setValue(null);
			txtLocalAmount.setValue(null);
			txtStampTaxAmt.setValue(null);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			calStampTaxAmt();
			// 带入主合同框架合约
			if (mainContractId != null && getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW), CONTENT_COL).getValue() != null) {
				ContractBillInfo info = null;
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("programmingContract.*");
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(mainContractId.toString()), sic);
				} catch (Exception e) {
					logger.error(e);
				}
				// 如果非单独计算补充合同
				if (info.getProgrammingContract() != null) {
					prmtFwContractTemp.setValue(info.getProgrammingContract());
					textFwContract.setText(info.getProgrammingContract().getName());
					editData.setProgrammingContract(info.getProgrammingContract());
				}
			}
		}
	}

	private void setAmountRequired(boolean required) {
		txtamount.setRequired(required);
		txtLocalAmount.setRequired(required);
	}

	/**
	 * 描述：合同形成方式变化，招标/议标信息控件变化
	 */
	protected void contractSource_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		setInviteCtrlVisibleByContractSource((ContractSourceInfo) newValue);
		super.contractSource_dataChanged(e);
	}

	/**
	 * output contractSource_willShow method
	 */
	protected void contractSource_willShow(
			com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception {
	}

	// 子类可以重载
	protected void fetchInitParam() throws Exception {

		super.fetchInitParam();

		Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId()
					.toString());
		}
		
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString())
					.booleanValue();
		}
		if (param.get(FDCConstants.FDC_PARAM_OUTCOST) != null) {
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if (param.get(FDCConstants.FDC_PARAM_CONTROLTYPE) != null) {
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)
					.toString();
		}

		//splitBeforeAudit
		if (param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT) != null) {
			splitBeforeAudit = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString())
					.booleanValue();
		}
		//是否显示“合同费用项目”
		if(param.get(FDCConstants.FDC_PARAM_CHARGETYPE) != null){
			isShowCharge = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CHARGETYPE).toString()).booleanValue();			
		}
		if(param.get(FDCConstants.FDC_PARAM_SELECTSUPPLY) != null){
			isSupply = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTSUPPLY).toString()).booleanValue();			
		}
	}
	protected void txtBailOriAmount_dataChanged(DataChangeEvent e)
			throws Exception {
		EventListener[] listeners = txtBailRate.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			txtBailRate.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		try{
			BigDecimal bailRate= FDCHelper.ZERO;
			BigDecimal bailOriAmount= FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2);
			BigDecimal contractAmount=FDCHelper.toBigDecimal(txtamount.getNumberValue(),2);
			if(contractAmount.compareTo(FDCHelper.ZERO)!=0){
				bailRate=bailOriAmount.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("履约保证金比例大于100%");
					txtBailOriAmount.setValue(null);
					SysUtil.abort();
				}
				this.txtBailRate.setValue(bailRate);
			}
		}finally{
			for(int i=0;i<listeners.length;i++){
				txtBailRate.addDataChangeListener((DataChangeListener)listeners[i]);
			}
		}
		//如果履约保证金发生改变的话，相应的分录也应该发生改变
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()!=null&&txtamount.getNumberValue()!=null){
				BigDecimal bailAmount=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(),FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2)),FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()==null&&txtamount.getNumberValue()!=null&&this.tblBail.getRow(i).getCell("bailAmount").getValue()!=null){
				//反算比例
				BigDecimal bailRate=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE), FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2));
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("返回比例大于100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
		
	}
	
	protected void txtBailRate_dataChanged(DataChangeEvent e) throws Exception {
		EventListener[] listeners = txtBailOriAmount.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			txtBailOriAmount.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		try{
			BigDecimal bailOriAmount= FDCHelper.ZERO;
			BigDecimal bailRate= FDCHelper.toBigDecimal(txtBailRate.getNumberValue(),2);
			BigDecimal contractAmount=FDCHelper.toBigDecimal(txtamount.getNumberValue(),2);
			bailOriAmount=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(bailRate,contractAmount),FDCHelper.ONE_HUNDRED));
			this.txtBailOriAmount.setValue(bailOriAmount);
		
		}finally{
			for(int i=0;i<listeners.length;i++){
				txtBailOriAmount.addDataChangeListener((DataChangeListener)listeners[i]);
			}
		}
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()!=null&&txtamount.getNumberValue()!=null){
				BigDecimal bailAmount=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(),FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2)),FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()==null&&txtamount.getNumberValue()!=null&&this.tblBail.getRow(i).getCell("bailAmount").getValue()!=null){
				//反算比例
				BigDecimal bailRate=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE), FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2));
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("返回比例大于100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
	}
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		this.contAttachmentNameList.setEnabled(true);
		//为comboAttachmentNameList再次设置组件访问权限，否则即使是在可视可编辑的情况下也不能选择下拉列表框中的项  by Cassiel_peng
		this.comboAttachmentNameList.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.comboAttachmentNameList.setEnabled(true);
		this.comboAttachmentNameList.setEditable(true);
		
		//设置容器不可收缩
		this.kDContainer1.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contPayItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contBailItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		
		JButton btnAdd = this.kDContainer1.add(this.actionAddLine);
		JButton btnRemove = this.kDContainer1.add(this.actionRemoveLine);
		this.kDContainer1.setVisibleOfExpandButton(true);
		this.actionAddLine.setVisible(true);
		this.actionAddLine.setEnabled(true);
		this.actionRemoveLine.setVisible(true);
		this.actionRemoveLine.setEnabled(true);
		btnAdd.setRequestFocusEnabled(false);
		btnRemove.setRequestFocusEnabled(false);
		btnAdd.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemove.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		// 工作流处理时修改字段
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editAuditProgColumn(); // 在改类中实现以下方法
			}
		});
		contOrgAmtBig.setLocation(5, 5);
	}
	
	/*
	 * 工作流处理时修改字段
	 */
	private void editAuditProgColumn() {
		// 判断是否在工作流审批中
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		// 调试
		logger.error(isFromWorkflow + "************************");
		logger.error(getOprtState().equals(STATUS_FINDVIEW) + "");
		logger.error(actionSave.isVisible() + "");
		logger.error((editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING) + "");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_FINDVIEW) && actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING)) {

			// 首先锁定界面上所有的空间
			this.lockUIForViewStatus();

			// 首先给控件个访问的权限，然后设置是否可编辑就可以了！不能使用unloack方法，这样会使所用的控件处于edit的状态
			this.prmtFwContractTemp.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtFwContractTemp.setEditable(true);
			this.actionSave.setEnabled(true);

		}
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}
	
	protected void tblEconItem_editStopped(KDTEditEvent e) throws Exception {
		
		BigDecimal contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue(),2);
		
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn payAmountCol=this.tblEconItem.getColumn("payAmount");
		IColumn payRateCol=this.tblEconItem.getColumn("payRate");
		if(colIndex==payAmountCol.getColumnIndex()){
			BigDecimal cellPayRateValue=FDCHelper.ZERO;
			BigDecimal cellPayAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
			if(contractAmount.compareTo(FDCHelper.ZERO)!=0){//除数不能为0
				cellPayRateValue=cellPayAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellPayRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("付款比例大于100%");
					this.tblEconItem.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblEconItem.getCell(rowIndex, "payRate").setValue(cellPayRateValue);
			}
		}
		if(colIndex==payRateCol.getColumnIndex()){
			BigDecimal cellPayAmountValue=FDCHelper.ZERO;
			BigDecimal cellPayRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
			cellPayAmountValue=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(contractAmount,cellPayRateValue),FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_UP),2);
			this.tblEconItem.getCell(rowIndex, "payAmount").setValue(cellPayAmountValue);
		}
		if(colIndex==this.tblEconItem.getColumn("payType").getColumnIndex()){
			//添加付款类型F7
			KDBizPromptBox bizPayTypeBox = new KDBizPromptBox();
			bizPayTypeBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			KDTDefaultCellEditor payTypeEditor=new KDTDefaultCellEditor(bizPayTypeBox);
			this.tblEconItem.getColumn("payType").setEditor(payTypeEditor);
		}
		if(colIndex==this.tblEconItem.getColumn("date").getColumnIndex()){
			KDDatePicker payDataPicker = new KDDatePicker();
			payDataPicker.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
			ICellEditor payDateEditor = new KDTDefaultCellEditor(payDataPicker);
			this.tblEconItem.getColumn("date").setEditor(payDateEditor);
		}
	}
	/**
	 * 2009-11-23日修改：计算返还金额时应该用履约保证金额*返还比例 兰远军
	 */
	protected void tblBail_editStopped(KDTEditEvent e) throws Exception {
		
//		BigDecimal contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue(),2);
		BigDecimal bailOrgAmount=FDCHelper.toBigDecimal(this.txtBailOriAmount.getNumberValue(),2);
		
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn bailAmountCol=this.tblBail.getColumn("bailAmount");
		IColumn bailRateCol=this.tblBail.getColumn("bailRate");
		if(colIndex==bailAmountCol.getColumnIndex()){
			BigDecimal cellBailRateValue=FDCHelper.ZERO;
			BigDecimal cellBailAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
			if(bailOrgAmount.compareTo(FDCHelper.ZERO)!=0){
				cellBailRateValue=cellBailAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(bailOrgAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellBailRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("返还比例大于100%");
					this.tblBail.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getCell(rowIndex, "bailRate").setValue(cellBailRateValue);
			}
		}
		if(colIndex==bailRateCol.getColumnIndex()){
			BigDecimal cellBailAmountValue=FDCHelper.ZERO;
			BigDecimal cellBailRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
			cellBailAmountValue=FDCHelper.divide(FDCHelper.multiply(cellBailRateValue, bailOrgAmount), FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			this.tblBail.getCell(rowIndex, "bailAmount").setValue(cellBailAmountValue);
		}
		if(colIndex==this.tblBail.getColumn("bailDate").getColumnIndex()){
			KDDatePicker bailDatePicker1 = new KDDatePicker();
			bailDatePicker1.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
			ICellEditor bailDateEditor = new KDTDefaultCellEditor(bailDatePicker1);
			this.tblBail.getColumn("bailDate").setEditor(bailDateEditor);
		}
	}
	
	
	/**
	 * 设置"付款事项"、"履约保证金及返还部分"表格的相关样式设置   by Cassiel_peng
	 */
	private void initEcoEntryTableStyle() {
		((KDTTransferAction) tblEconItem.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		((KDTTransferAction) tblBail.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		tblEconItem.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("payRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("date").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		
		tblBail.getColumn("bailAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailDate").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		txtBailOriAmount.setRemoveingZeroInDispaly(false);
		this.txtBailOriAmount.setRequestFocusEnabled(true);
		this.txtBailOriAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailOriAmount.setPrecision(2);
		this.txtBailOriAmount.setNegatived(false);
		this.txtBailRate.setMaximumValue(FDCHelper.MAX_DECIMAL);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailOriAmount.setHorizontalAlignment(JTextField.RIGHT);
//		this.txtBailRate.setPercentDisplay(true);
		this.txtBailRate.setRequestFocusEnabled(true);
		this.txtBailRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailRate.setPrecision(2);
		this.txtBailRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailRate.setNegatived(false);
		this.txtBailRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBailRate.setRemoveingZeroInDispaly(false);
	}
	public void onLoad() throws Exception {
		
		this.tblInvite.checkParsed();
		FDCHelper.formatTableDate(this.tblInvite, "createDate");
		FDCHelper.formatTableDate(this.tblInvite, "auditDate");
		this.tblInvite.getColumn("respDept").getStyleAttributes().setHided(true);
		
//		BOSUuid.create((new ParamInfo ()).getBOSType());
		//2008-12-30 暂时屏蔽新功能 复制分录
		this.actionCopyLine.setVisible(false);
		this.actionCopyLine.setEnabled(false);
	    this.txtOverAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				Object old  = eventObj.getOldValue();
				if(old == null){
					old = Double.valueOf("0.0");
				}
				Object newValue = eventObj.getNewValue();
				if(newValue != null ){
				if( Double.valueOf(newValue.toString()).doubleValue() < 0 || Double.valueOf(newValue.toString()).doubleValue() > 999 ){
				   txtOverAmt.setValue(old,false);
				}}
			}
		});			
	    
	    // 勾选是否战略子合同时，如果合同没有乙方给出提示
	    this.chkIsSubMainContract.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(chkIsSubMainContract.isSelected() && prmtpartB.getData() == null){
					FDCMsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "plsSelectPartBFirst"));
					chkIsSubMainContract.setSelected(false);
					prmtpartB.requestFocus(true);
				}
			}
	    });
	    
	    this.chkIsSubMainContract.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(chkIsSubMainContract.isSelected()){
					if (prmtpartB.getData() != null) {
						prmtMainContract.setEnabled(true);
						prmtMainContract.setRequired(true);
					}
				}else{
					prmtMainContract.setEnabled(false);
					prmtMainContract.setRequired(false);
					prmtMainContract.setDataNoNotify(null);
				}
			}});
	    
	    this.prmtMainContract.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue() != null){
					ContractBillInfo info = (ContractBillInfo) eventObj.getNewValue();
					if(info.getEffectiveEndDate() != null)
					kdpEffectiveEndDate.setValue(info.getEffectiveEndDate());
					if(info.getEffectiveStartDate() != null )
					kdpEffectStartDate.setValue(info.getEffectiveStartDate());
					if(info.getCoopLevel() != null){
						comboCoopLevel.setSelectedItem(info.getCoopLevel());
					}
					Date now = null;
					try {
						 now = FDCCommonServerHelper.getServerTime();
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					if(now != null && info.getEffectiveEndDate() != null && info.getEffectiveStartDate() != null){
						if(now.after(info.getEffectiveEndDate()) || now.before(info.getEffectiveStartDate())){
							int n = MsgBox.showConfirm2New(null, "当前战略合同已过期，继续使用该合同吗？");
							if(JOptionPane.NO_OPTION == n){
								prmtMainContract.setDataNoNotify(null);
								abort();
							}
						}
					}
					
				}
			}});
		
		//只显示已启用的合同费用项目
		EntityViewInfo viewInfo = this.prmtCharge.getEntityViewInfo();
		if (viewInfo == null) {
			viewInfo = new EntityViewInfo();
		}
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		viewInfo.setFilter(filterInfo);
		this.prmtCharge.setEntityViewInfo(viewInfo);
		tblCost.getStyleAttributes().setLocked(true);
		tblCost.checkParsed();
		//金额靠右
		txtamount.setHorizontalAlignment(JTextField.RIGHT);
		//不允许为负
		txtamount.setNegatived(false);
		txtLocalAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setPrecision(6);
		txtGrtRate.setEditable(true);
		txtExRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtExRate.setPrecision(6);
		EntityViewInfo invView = new EntityViewInfo();
		FilterInfo invFilter = new FilterInfo();
		invView.setFilter(invFilter);
		invFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		prmtinviteType.setEntityViewInfo(invView);

		//init currency
		removeDataChangeListener(comboCurrency);
		FDCClientHelper.initComboCurrency(comboCurrency, true);
		
		txtcontractName.setMaxLength(200);//此句代码必须在super之前,否则在加载保存好的数据的时候只会截取前80个字符显示 by Cassiel_peng
		super.onLoad();
		setMaxMinValueForCtrl();
		
		initEcoEntryTableStyle();
		sortTabbedPanel();

		//亿达需要，万科先注销
		//		fillCostPanel();

		setInviteCtrlVisible(false);
		EntityViewInfo viewContractSource = new EntityViewInfo();
		FilterInfo filterSource = new FilterInfo();
		filterSource.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		viewContractSource.setFilter(filterSource);
		this.contractSource.setEntityViewInfo(viewContractSource);
		
		updateButtonStatus();

		// 初始化合同类型F7
		prmtcontractType.setSelector(new ContractTypePromptSelector(this));

		//由于甲方基础资料级别为S4，构建filter时会自动加上CU隔离 为去除默认级别filter，修改掉
		//		EntityViewInfo view = new EntityViewInfo();
		//		FilterInfo filter = new FilterInfo();
		//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		//		HashSet set=new HashSet();
		//		set.add(OrgConstants.SYS_CU_ID);
		String cuId = editData.getCU().getId().toString();
		//		//
		//		set.add(cuId);
		//		filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
		//
		//		view.setFilter(filter);
		//		prmtlandDeveloper.setEntityViewInfo(view);
		//去掉默认级别filter
		prmtlandDeveloper.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
				HashSet set = new HashSet();
				set.add(OrgConstants.SYS_CU_ID);
				String cuId = editData.getCU().getId().toString();
				set.add(cuId);
				filter.getFilterItems().add(
						new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		prmtlandDeveloper.addCommitListener(new CommitListener() {
			public void willCommit(CommitEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
				HashSet set = new HashSet();
				set.add(OrgConstants.SYS_CU_ID);
				String cuId = editData.getCU().getId().toString();
				set.add(cuId);
				filter.getFilterItems().add(
						new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});

		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setRespDeptF7(prmtCreateOrg, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setPersonF7(prmtRespPerson, this,
				canSelectOtherOrgPerson ? null : cuId);

		actionAddLine.setEnabled(false);
		actionAddLine.setVisible(false);

		actionInsertLine.setEnabled(false);
		actionInsertLine.setVisible(false);

		actionRemoveLine.setEnabled(false);
		actionRemoveLine.setVisible(false);

		//		txtGrtAmount.setEditable(false);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);

		if (editData.getContractSource() != null) {
			setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		}

		if (STATUS_ADDNEW.equals(getOprtState())
				&& prmtcontractType.getData() != null) {
			ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType
					.getData();
			removeDetailTableRowsOfContractType();

			try {
				fillDetailByContractType(cType.getId().toString());
			} catch (Exception e) {
				handUIException(e);
			}
		}
		setContractType();

		if (editData != null
				&& (editData.getState() == FDCBillStateEnum.CANCEL || editData
						.isIsArchived())) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionSplit.setEnabled(false);
			btnSplit.setEnabled(false);
		}

		//合同审批前进行拆分
		if (splitBeforeAudit && editData.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (!FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			this.actionViewCost.setVisible(false);
		} else {
			this.actionViewCost.setVisible(true);
		}

		setPrecision();

		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, prmtpartB, cuId);
		FDCClientUtils.initSupplierF7(this, prmtpartC, cuId);
		FDCClientUtils.initSupplierF7(this, prmtwinUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowestPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowerPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtmiddlePriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthigherPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthighestPriceUni, cuId);

		addKDTableLisener();
		handleOldData();

		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		//当通过付款申请单查看合同时，可以查看到合同的附件信息。
		if (STATUS_FINDVIEW.equals(this.oprtState)) {

			this.actionAttachment.setVisible(true);
			this.actionAttachment.setEnabled(true);
			//没有启用 审批前必须拆分完全 参数时，查看不显示拆分按钮
			String prjId = editData.getOrgUnit().getId().toString();
			boolean isSplitBeforeAudit = FDCUtils.getDefaultFDCParamByKey(null,
					prjId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
			if (!isSplitBeforeAudit) {
				this.actionSplit.setEnabled(false);
				this.actionSplit.setVisible(false);
			}
		}

		getDetailInfoTable().getStyleAttributes().setWrapText(true);
		//getDetailInfoTable().getStyleAttributes().set
		this.disableTableMenus(this.getDetailInfoTable());
		//修改合同时判断是否为非独立结算的补充合同  
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ICell loCell = getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL);
			txtamount.setEditable(false);
			txtGrtRate.setRequired(false);
			txtGrtRate.setEditable(false);
			txtGrtAmount.setRequired(false);
			txtGrtAmount.setEditable(false);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			loCell.getStyleAttributes().setLocked(true);
			this.txtGrtRate.setEditable(false);
		}

		//业务日志判断为空时取期间中断
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		//当非查看状态时才获得焦点
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!getOprtState().equals(STATUS_VIEW)) {
					prmtcontractType.requestFocus(true);
				}
				//合同单据印花税率精度目前可以到0.01%，但是借款合同的印花税率为0.05‰，系统无法输入
				txtStampTaxRate.setPrecision(6);
			}
		});
		//根据参数是否显示合同费用项目
		if(!isShowCharge){
			this.conChargeType.setVisible(false);
			this.prmtCharge.setRequired(false);
			this.actionViewBgBalance.setVisible(false);
			this.actionViewBgBalance.setEnabled(false);
			this.menuItemViewBgBalance.setVisible(false);
			this.menuItemViewBgBalance.setEnabled(false);
		}else{
			this.conChargeType.setVisible(true);
			this.prmtCharge.setRequired(true);
			this.actionViewBgBalance.setVisible(true);
			this.actionViewBgBalance.setEnabled(true);
			this.menuItemViewBgBalance.setVisible(true);
			this.menuItemViewBgBalance.setEnabled(true);
		}
			//如果附件名列表中没有数据，那么查看附件按钮置灰
			boolean hasAttachment=getAttachmentNamesToShow();
			if(!hasAttachment){
				this.btnViewAttachment.setEnabled(false);
				this.btnViewAttachment.setVisible(true);
			}else{
				this.btnViewAttachment.setEnabled(true);	
				this.btnViewAttachment.setVisible(true);
			}
//		}
	
		this.comboAttachmentNameList.setEditable(true);
		//如果是新增单据，"查看正文"按钮也置灰(因为如果是"新增单据"附件列表一定为空，所以不需要再判断"查看附件"按钮)
//		if(STATUS_ADDNEW.equals(getOprtState())){
//			this.btnViewContrnt.setEnabled(false);
//			this.btnViewContrnt.setVisible(true);
//		}
		
		if(getOprtState()==STATUS_EDIT){
			EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
			EcoItemHelper.setBailRowBackColorWhenInit(this.tblBail, txtBailOriAmount, txtBailRate);			
		}

		if(this.editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}
	
		detailTableAutoFitRowHeight();
		
		this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.ALLRANGE);
		ContractTypeInfo info = (ContractTypeInfo) this.prmtcontractType.getValue();
		if(info!=null){
			if(ContractTypeOrgTypeEnum.BIGRANGE.equals(info.getOrgType())||ContractTypeOrgTypeEnum.SMALLRANGE.equals(info.getOrgType())){
				this.cbOrgType.setEnabled(false);
			}
			Set id = new HashSet();
			for (int i = 0; i < info.getContractWFTypeEntry().size(); i++) {
				if (info.getContractWFTypeEntry().get(i).getContractWFType() != null) {
					id.add(info.getContractWFTypeEntry().get(i).getContractWFType().getId().toString());
				}
			}
			
			Set inviteid = new HashSet();
			for (int i = 0; i < info.getInviteTypeEntry().size(); i++) {
				if (info.getInviteTypeEntry().get(i).getInviteType() != null) {
					inviteid.add(info.getInviteTypeEntry().get(i).getInviteType().getId().toString());
				}
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if (id.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtContractWFType.setEntityViewInfo(view);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if (inviteid.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", inviteid, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtInviteType.setEntityViewInfo(view);
			
			if(inviteid.size()==0){
				this.prmtInviteType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.prmtInviteType.setEnabled(false);
			}
		}else{
			this.prmtInviteType.setEnabled(false);
			this.prmtContractWFType.setEnabled(false);
			this.cbOrgType.setEnabled(false);
		}
		this.prmtpartB.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtlandDeveloper.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		if(ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
			this.prmtpartB.setEnabled(false);
			this.prmtlandDeveloper.setEnabled(false);
		}else{
			this.prmtpartB.setEnabled(true);
			this.prmtlandDeveloper.setEnabled(true);
		}
		if(this.editData.getCurProject().isIsWholeAgeStage()){
			this.btnProgram.setEnabled(false);
		}
		
		labSettleType.setVisible(false);
		btnAType.setVisible(false);
		btnBType.setVisible(false);
		BtnCType.setVisible(false);
		
		
		this.actionViewContent.setVisible(false);
		this.btnAgreement.setVisible(false);
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnUpLoad = new KDWorkButton();
		KDWorkButton btnAgreementText = new KDWorkButton();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionUpLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnUpLoad = (KDWorkButton)this.contAttachment.add(this.actionUpLoad);
		btnUpLoad.setText("上传标准合同");
		btnUpLoad.setSize(new Dimension(140, 19));
		
		this.actionAgreementText.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAgreementText = (KDWorkButton)this.contAttachment.add(this.actionAgreementText);
		btnAgreementText.setText("上传补充条款");
		btnAgreementText.setSize(new Dimension(140, 19));

		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		KDWorkButton btnDownLoad = new KDWorkButton();
		this.actionDownLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnDownLoad = (KDWorkButton) this.contMode.add(this.actionDownLoad);
		btnDownLoad.setText("下载标准合同范本");
		btnDownLoad.setSize(new Dimension(140, 19));
		
		this.contSrcAmount.getBoundLabel().setForeground(Color.RED);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		txtDes.setMaxLength(1000);
	}

	//业务日期变化引起,期间的变化
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		//super.pkbookedDate_dataChanged(e);

		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, true);

		this.comboCurrency_itemStateChanged(null);
	}

	private void setMaxMinValueForCtrl() {
		FDCClientHelper.setValueScopeForPercentCtrl(txtchgPercForWarn);
		FDCClientHelper.setValueScopeForPercentCtrl(txtpayPercForWarn);

		FDCClientHelper.setValueScopeForPercentCtrl(txtPayScale);
		FDCClientHelper.setValueScopeForPercentCtrl(txtStampTaxRate);
		FDCClientHelper.setValueScopeForPercentCtrl(txtGrtRate);

		this.txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
		this.txtamount.setMaximumValue(FDCHelper.MAX_VALUE);
		this.txtLocalAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtLocalAmount.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE2);
		txtExRate.setMaximumValue(FDCHelper.ONE_THOUSAND);
		txtExRate.setMinimumValue(FDCHelper.ZERO);

		txtNumber.setMaxLength(80);
//		txtcontractName.setMaxLength(80);

		KDTextField tblDetail_desc_TextField = new KDTextField();
		tblDetail_desc_TextField.setName("tblDetail_desc_TextField");
		tblDetail_desc_TextField.setMaxLength(80);
		KDTDefaultCellEditor tblDetail_desc_CellEditor = new KDTDefaultCellEditor(
				tblDetail_desc_TextField);
		tblDetail.getColumn("desc").setEditor(tblDetail_desc_CellEditor);
		
		this.txtcontractName.setMaxLength(200);
		EcoItemHelper.setEntryTableCtrl(this.tblEconItem, this.tblBail);
	}

	/**
	 * 
	 * 描述：保证Tab的顺序
	 * 
	 * @author:liupd 创建时间：2006-7-19
	 *               <p>
	 */
	private void sortTabbedPanel() {

		kDTabbedPane1.removeAll();

		kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
		kDTabbedPane1.add(this.panelInvite, resHelper.getString("panelInvite.constraints"));
		kDTabbedPane1.add(this.kdtSupplyEntry, resHelper.getString("kdtSupplyEntry.constraints"));
//		kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
		//亿达需要，万科先注销
		//		kDTabbedPane1.add(pnlCost, "成本信息");		
	}

	/**
	 * output prmtcontractType_willShow method
	 */
	protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception {
		if (STATUS_EDIT.equals(getOprtState())) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("changeContractType"));
		}
		initPrmtcontractType();
	}
	
	private void initPrmtcontractType(){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		ContractTypeInfo conTypeInfo = (ContractTypeInfo)prmtcontractType.getValue();
		if(conTypeInfo !=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("contractType.id",conTypeInfo.getId().toString()));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));
		prmtModel.setEntityViewInfo(view);
	}
	protected void prmtModel_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		ContractTypeInfo conTypeInfo = (ContractTypeInfo)prmtcontractType.getValue();
		if(conTypeInfo !=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("contractType.id",conTypeInfo.getId().toString()));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));
		prmtModel.setEntityViewInfo(view);
		prmtModel.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void prmtModel_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		deleteTypeAttachment(this.editData.getId().toString(),"标准合同");
		
		deleteContractContent();
		
		ContractModelInfo contractModel = (ContractModelInfo)this.prmtModel.getValue();
		if(contractModel!=null){
			//利用合同范本ID 去找合同范本
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("id");
			viewInfo.getSelector().add("fileName");
			viewInfo.getSelector().add("version");
			viewInfo.getSelector().add("fileType");
			viewInfo.getSelector().add("createTime");
			viewInfo.getSelector().add("creator.*");
			viewInfo.getSelector().add("contentFile");
			SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
			sorterItemInfo.setSortType(SortType.ASCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			sorterItemInfo = new SorterItemInfo("version");
			sorterItemInfo.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("parent", contractModel.getId().toString());
			viewInfo.setFilter(filterInfo);
			ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
			ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
			if(model!=null){
				String fullname = model.getFileType();
				editData.setContractModel(contractModel.getId().toString());
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("contractModel");
				BigDecimal version = new BigDecimal("1.0");
				ContractContentInfo contentInfo = new ContractContentInfo();
				contentInfo.setVersion(version);
				contentInfo.setContract(editData);
				contentInfo.setFileType(fullname);
				contentInfo.setContentFile(model.getContentFile());
				ContractContentFactory.getRemoteInstance().save(contentInfo);
				
				addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
			}
			this.prmtModel.setEnabled(false);
		}else{
			this.prmtModel.setEnabled(true);
		}
		fillAttachmnetTable();
	}
	
	//常旭说支持维护，所以另存一份
	private void updateModel(boolean oprt) throws Exception {
		if(prmtModel.getValue()==null){
			editData.setModel(null);
			return;
		}
		AttachmentInfo info = (AttachmentInfo)prmtModel.getValue();
		FilterInfo filter = new FilterInfo();
		if(oprt){
			filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(filter);
			editData.setModel(null);
			prmtModel.setValue(null);
			return;
		}
		if(editData.getModel()!=null&&editData.getModel().getId().toString().equals(info.getId().toString())){
			return;
		}
		if(editData.getModel()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getModel().getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(filter);
			if(oprt){
				return;
			}
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("boAttchAsso.*"));
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		evi.setSelector(sic);
		AttachmentInfo attachment = AttachmentFactory.getRemoteInstance().getAttachmentCollection(evi).get(0);
		attachment.setId(BOSUuid.create(attachment.getBOSType()));
		attachment.getBoAttchAsso().get(0).setId(BOSUuid.create("172F3A47"));
		attachment.getBoAttchAsso().get(0).setBoID(editData.getId().toString());
		AttachmentFactory.getRemoteInstance().addnew(attachment);
		editData.setModel(attachment);
		prmtModel.setValue(attachment);
	}
	
	protected void prmtcontractType_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		contractTypeChanged(newValue);
		
		super.prmtcontractType_dataChanged(e);
		
		detailTableAutoFitRowHeight();
		
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		this.prmtModel.setValue(null);
		ContractTypeInfo info = (ContractTypeInfo) newValue;
		if(info!=null){
			if(!ContractPropertyEnum.SUPPLY.equals(contractPropert.getSelectedItem())){
				if(this.editData.getProgrammingContract()!=null&&this.editData.getProgrammingContract().getContractType()!=null
						&&!this.editData.getProgrammingContract().getContractType().getId().equals(info.getId())){
					this.editData.setProgrammingContract(null);
					this.textFwContract.setText(null);
				}
			}
			if(ContractTypeOrgTypeEnum.BIGRANGE.equals(info.getOrgType())||ContractTypeOrgTypeEnum.SMALLRANGE.equals(info.getOrgType())){
				this.cbOrgType.setSelectedItem(info.getOrgType());
				this.cbOrgType.setEnabled(false);
			}else{
				this.cbOrgType.setSelectedItem(null);
				this.cbOrgType.setEnabled(true);
			}
			info=ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select contractWFTypeEntry.contractWFType.*,inviteTypeEntry.inviteType.* from where id='"+info.getId().toString()+"'");
			Set id = new HashSet();
			for (int i = 0; i < info.getContractWFTypeEntry().size(); i++) {
				if (info.getContractWFTypeEntry().get(i).getContractWFType() != null) {
					id.add(info.getContractWFTypeEntry().get(i).getContractWFType().getId().toString());
				}
			}
			
			Set inviteid = new HashSet();
			for (int i = 0; i < info.getInviteTypeEntry().size(); i++) {
				if (info.getInviteTypeEntry().get(i).getInviteType() != null) {
					inviteid.add(info.getInviteTypeEntry().get(i).getInviteType().getId().toString());
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if (id.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtContractWFType.setEntityViewInfo(view);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if (inviteid.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", inviteid, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtInviteType.setEntityViewInfo(view);
			if(inviteid.size()==0){
				this.prmtInviteType.setValue(null);
				this.prmtInviteType.setEnabled(false);
			}else{
				this.prmtInviteType.setEnabled(true);
			}
			
			if(this.prmtContractWFType.getValue()!=null&&!id.contains(((ContractWFTypeInfo)this.prmtContractWFType.getValue()).getId().toString())){
				this.prmtContractWFType.setValue(null);
			}else if(info.getContractWFTypeEntry().size()==1){
				this.prmtContractWFType.setValue(info.getContractWFTypeEntry().get(0).getContractWFType());
			}
			this.prmtContractWFType.setEnabled(true);
			
		}else{
			this.cbOrgType.setSelectedItem(null);
			this.cbOrgType.setEnabled(false);
			
			this.prmtContractWFType.setValue(null);
			this.prmtContractWFType.setEnabled(false);
			this.prmtInviteType.setValue(null);
			this.prmtInviteType.setEnabled(false);
		}
		getModeByInvite();
	}
	private boolean isExistCodingRule() {
		String currentOrgId = this.orgUnitInfo.getId().toString();
		boolean isExist = false;
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (currentOrgId == null || currentOrgId.trim().length() == 0) {
				// 当前用户所属组织不存在时，缺省实现是用集团的
				currentOrgId = OrgConstants.DEF_CU_ID;
			}
			if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(editData, currentOrgId)) {
				currentOrgId = FDCClientHelper.getCurrentOrgId();
				if (iCodingRuleManager.isExist(editData, currentOrgId, "codeType.number")) {
					isExist = true;
				} else if (iCodingRuleManager.isExist(editData, currentOrgId)) {
					isExist = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	/**
	 * 合同类型发生改变时，要做的工作
	 * @param newValue
	 */
	private void contractTypeChanged(Object newValue) throws Exception,
			BOSException, EASBizException, CodingRuleException {
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			ContractTypeInfo info = (ContractTypeInfo) newValue;
			//if (info != null&& 
			//update by renliang 2010-6-4
			 if((this.editData.getContractType() == null || !this.editData
							.getContractType().getId().equals(info.getId()))) {
				this.editData.setContractType(info);
//				if(isExistCodingRule()){
					this.handleCodingRule1();
//				}
			}
			removeDetailTableRowsOfContractType();
			fillDetailByContractType(info.getId().toString());
			chkCostSplit.setSelected(info.isIsCost());
			fillInfoByContractType(info);
		} else {
			if (newValue != null) {
				// 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
				if (STATUS_EDIT.equals(getOprtState())) {
					if ((this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED_VALUE))) {
						// prmtcontractType.setEnabled(false);
						actionSplit.setEnabled(true);
					} else {
						ContractTypeInfo info = (ContractTypeInfo) newValue;
						if (!info.isIsLeaf()) {
							MsgBox.showWarning(this, FDCClientUtils
									.getRes("selectLeaf"));
							prmtcontractType.setData(null);
							prmtcontractType.requestFocus();
							SysUtil.abort();
						}
						removeDetailTableRowsOfContractType();
						fillDetailByContractType(info.getId().toString());
						chkCostSplit.setSelected(info.isIsCost());
						fillInfoByContractType(info);
						if (this.editData.getContractType() != null
								///&& info != null update by renliang
								&& !this.editData.getContractType().getId()
										.equals(info.getId())) {
							this.editData.setContractType(info);
//							if(isExistCodingRule()){//启用 
								this.handleCodingRule1();
//							}
						}
					}
				}
			}
		}
		setCheckBoxValue();
	}

	/**
	 * 根据合同类型填充带过来的数据
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void fillInfoByContractType(ContractTypeInfo info)
			throws BOSException, EASBizException {
		//R100429-042合同类型改变时，责任部门不根据合同类型的改变而改变，即合同类型改不清空责任部门 by cassiel_peng 2010-05-21
		/*
		 * R100429-042之前的改动有问题，进入新增界面，选择合同类型，无法带出责任部门.
		 * 应该是，责任部门有值之后，责任部门不根据合同类型的改变而改变 by zhiyuan_tang
		 */
		if (info.getDutyOrgUnit() != null) {
			BOSUuid id = info.getDutyOrgUnit().getId();
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("isLeaf");
			CoreBaseInfo value = AdminOrgUnitFactory.getRemoteInstance()
			.getValue(new ObjectUuidPK(id), selectors);
			prmtRespDept.setValue(value);
		}

		txtPayScale.setNumberValue(info.getPayScale());

		txtStampTaxRate.setNumberValue(info.getStampTaxRate());
	}

	private void removeDetailTableRowsOfContractType() {

		for (int i = 0; i < getDetailInfoTable().getRowCount(); i++) {
			String rowKey = (String) getDetailInfoTable()
					.getCell(i, ROWKEY_COL).getValue();
			if (rowKey == null || rowKey.length() == 0) {
				getDetailInfoTable().removeRow(i);
				i--;
			}
		}
	}

	/**
	 * 根据合同类型填充合同详细信息页签的内容
	 * @param id
	 * @throws Exception
	 */
	private void fillDetailByContractType(String id) throws Exception {

		/**
		 * 其他自定义的合同类型的时候，系统没有自动显示备注
		 */
		if (!ContractClientUtils.isContractTypePre(id)) {
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(
					ContractClientUtils.getRes("remark"));
			row.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		ContractDetailDefCollection contractDetailDefCollection = ContractDetailDefFactory
				.getRemoteInstance().getContractDetailDefCollection(view);

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);

		for (Iterator iter = contractDetailDefCollection.iterator(); iter.hasNext();) {
			ContractDetailDefInfo element = (ContractDetailDefInfo) iter.next();
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(element.getName());
			KDTDefaultCellEditor editor = getEditorByDataType(element.getDataTypeEnum());
			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			
			
			if (element.getDataTypeEnum() == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).setValue(FDCHelper.getCurrentDate());
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat(
						"%r{yyyy-M-d}t");
			} else if (element.getDataTypeEnum() == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes()
						.setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			row.getCell(DATATYPE_COL).setValue(element.getDataTypeEnum());
			row.getCell(DETAIL_DEF_ID).setValue(element.getId().toString());

			row.getCell(DESC_COL).setEditor(editorString);
			
			// 如果合同详细定义为必录项，则背景色要显示为必录色  added by owen_wen 2010-09-10
			if (element.isIsMustInput()){
				this.setRequiredBGColor(row);
			}
		}

	}

	public KDTDefaultCellEditor getEditorByDataType(DataTypeEnum dataType) {
		if (dataType == DataTypeEnum.DATE) {
			//KDDatePicker datePicker = new KDDatePicker();
			return FDCClientHelper.getDateCellEditor();
		} else if (dataType == DataTypeEnum.BOOL) {
			KDComboBox booleanCombo = getBooleanCombo();
			return new KDTDefaultCellEditor(booleanCombo);
		} else if (dataType == DataTypeEnum.NUMBER) {
			return FDCClientHelper.getNumberCellEditor();
		} else if (dataType == DataTypeEnum.STRING) {

			KDTextArea indexValue_TextField = new KDTextArea();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setMaxLength(1000);
			indexValue_TextField.setColumns(10);
			indexValue_TextField.setWrapStyleWord(true);
			indexValue_TextField.setLineWrap(true);
			indexValue_TextField.setAutoscrolls(true);

			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			return indexValue_CellEditor;
		}
		return null;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(oprtType)) {
			prmtcontractType.setEnabled(true);
			actionSplit.setEnabled(false);
		} else {
			if (this.editData != null) {
				setContractType();
			} else {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			}
		}
		if (STATUS_VIEW.equals(oprtType) || "FINDVIEW".equals(oprtType)) {
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnInsertLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
			tblDetail.getStyleAttributes().setLocked(true);
			//			menuBiz.setVisible(false);
		}

		actionViewContent.setEnabled(true);

		if (STATUS_FINDVIEW.equals(oprtType)
				&& getUIContext().get("source") == null) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		} else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		}

		//合同审批前进行拆分
		if (splitBeforeAudit && editData != null && editData.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (editData != null
				&& !FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (oprtType.equals(STATUS_ADDNEW)) {
			actionContractPlan.setEnabled(false);
		} else {
			actionContractPlan.setEnabled(true);
		}

		if (mainContractId != null) {
			this.btnProgram.setEnabled(false);
		} else {
			this.btnProgram.setEnabled(true);
		}
		if(STATUS_ADDNEW.equals(oprtType) ||STATUS_EDIT.equals(oprtType)){
			btnProgram.setEnabled(true);
		}else{
			btnProgram.setEnabled(false);
		}
		if (oprtType.equals(OprtState.VIEW)) {
			this.actionUpLoad.setEnabled(false);
			this.actionAgreementText.setEnabled(false);
		}else{
			this.actionUpLoad.setEnabled(true);
			this.actionAgreementText.setEnabled(true);
		}
	}

	/*
	 * 20070315 jack 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
	 */
	private void setContractType() {
		if (STATUS_EDIT.equals(getOprtState())) {
			if (this.editData.getState() != null
					&& (this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED_VALUE))) {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			} else {
				prmtcontractType.setEnabled(true);
				actionSplit.setEnabled(false);
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.precision"));

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractType.isLeaf"));
		sic.add(new SelectorItemInfo("contractType.level"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longnumber"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("contractType.orgType"));
		
		sic.add(new SelectorItemInfo("codeType.id"));
		sic.add(new SelectorItemInfo("codeType.name"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.thirdType"));
		sic.add(new SelectorItemInfo("codeType.secondType"));
		
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractPlan.*"));

		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));

		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		
		sic.add(new SelectorItemInfo("payItems.*"));
		sic.add(new SelectorItemInfo("bail.*"));
//		sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
//		sic.add(new SelectorItemInfo("bail.entry.bailAmount"));
//		sic.add(new SelectorItemInfo("bail.entry.prop"));
//		sic.add(new SelectorItemInfo("bail.entry.bailDate"));
//		sic.add(new SelectorItemInfo("bail.entry.desc"));
		
		sic.add(new SelectorItemInfo("auditor.id"));
		
		sic.add(new SelectorItemInfo("sourceBillId"));
		
		sic.add(new SelectorItemInfo("mainContract.*"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("information"));
		
		sic.add(new SelectorItemInfo("overRate"));
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("bookDate"));
		
		sic.add(new SelectorItemInfo("createDept.id"));
		sic.add(new SelectorItemInfo("createDept.name"));
		sic.add(new SelectorItemInfo("createDept.number"));
		sic.add(new SelectorItemInfo("contractSettleType"));
		sic.add(new SelectorItemInfo("srcProID"));
		
		sic.add(new SelectorItemInfo("agreementID"));
		sic.add(new SelectorItemInfo("contractModel"));
		
		sic.add(new SelectorItemInfo("programmingContract.id"));
		sic.add(new SelectorItemInfo("programmingContract.amount"));
		sic.add(new SelectorItemInfo("programmingContract.name"));
		sic.add(new SelectorItemInfo("programmingContract.number"));
		sic.add(new SelectorItemInfo("programmingContract.balance"));
		sic.add(new SelectorItemInfo("programmingContract.estimateAmount"));
		
//		sic.add(new SelectorItemInfo("programmingContract.programming.*"));
		sic.add(new SelectorItemInfo("programmingContract.programming.project.id"));
		sic.add(new SelectorItemInfo("programmingContract.contractType.id"));
		sic.add(new SelectorItemInfo("contractType.contractWFTypeEntry.contractWFType.*"));
		sic.add(new SelectorItemInfo("contractType.inviteTypeEntry.inviteType.*"));
		return sic;
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

	/**
	 * 显示单据行
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		if(table==this.tblEconItem){
			if(obj!=null){
				dataBinder.loadLineFields(tblEconItem, row, obj);
			}
		}
		if(table==this.tblBail){
			if(obj!=null){
				dataBinder.loadLineFields(tblBail, row, obj);
			}
		}
	}

	private void storeDetailEntries() {
		ContractBillEntryInfo entryInfo = null;
		int count = getDetailInfoTable().getRowCount();
		ContractBillInfo billInfo = new ContractBillInfo();
		billInfo.setId(editData.getId());

		for (int i = 0; i < editData.getEntrys().size(); i++) {
			editData.getEntrys().removeObject(i);
			i--;
		}
		/*
		 * 如果有主合同编码,且不是直接合同,设置单据头上的主合同编码
		 */
		editData.setMainContractNumber(null);
		for (int i = 0; i < count; i++) {
			entryInfo = new ContractBillEntryInfo();

			entryInfo.setParent(billInfo);
			String detail = (String) getDetailInfoTable()
					.getCell(i, DETAIL_COL).getValue();
			DataTypeEnum dataType = (DataTypeEnum) getDetailInfoTable()
					.getCell(i, DATATYPE_COL).getValue();
			Object contentValue = getDetailInfoTable().getCell(i, CONTENT_COL)
					.getValue();
			String content = null;

			if (contentValue != null) {
				if (contentValue instanceof CoreBaseInfo) {
					content = ((CoreBaseInfo) contentValue).getId().toString();

				} else if (contentValue instanceof Date) {
					Date date = (Date) contentValue;
					content = DateFormat.getDateInstance().format(date);

				} else {
					content = contentValue.toString();
				}
			}

			String rowKey = (String) getDetailInfoTable().getCell(i, ROWKEY_COL).getValue();
			String desc = (String) getDetailInfoTable().getCell(i, DESC_COL).getValue();
			String detailDefId = (String) getDetailInfoTable().getCell(i, DETAIL_DEF_ID).getValue();

			entryInfo.setDetail(detail);

			entryInfo.setContent(content);
			entryInfo.setRowKey(rowKey);
			entryInfo.setDesc(desc);
			if (dataType != null) {
				entryInfo.setDataType(dataType);
			}
			entryInfo.setDetailDefID(detailDefId);
			editData.getEntrys().add(entryInfo);

			/*
			 * 如果有主合同编码,且不是直接合同,设置单据头上的主合同编码
			 */
			if (rowKey != null && rowKey.equals(NU_ROW)
					&& editData.getContractPropert() != ContractPropertyEnum.DIRECT) {
				if (contentValue != null && contentValue instanceof CoreBillBaseInfo) {
					String number = ((CoreBillBaseInfo) contentValue).getNumber();
					editData.setMainContractNumber(number);
				}
			}

			if (isUseAmtWithoutCost && rowKey != null && rowKey.equals(AM_ROW)) {
				//R110506-0418：非单独结算的补充合同不计成本金额取数口径不一致  By zhiyuan_tang
//				editData.setAmount(FDCHelper.toBigDecimal(content));
//				editData.setOriginalAmount(FDCHelper.divide(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
				editData.setOriginalAmount(FDCHelper.toBigDecimal(content));
				editData.setAmount(FDCHelper.multiply(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
			}

		}
	}
	private void detailTableAutoFitRowHeight() {
		//ADD by zhiyuan_tang 2010-08-06  合同详细自适应行高
		for (int i = 0; i< getDetailInfoTable().getRowCount(); i++) {
			KDTableHelper.autoFitRowHeight(getDetailInfoTable(), i);
		}
	}
	
	/**
	 * 设置表格单元为背景为必录色
	 * @author owen_wen 2010-09-10
	 */
	private void setRequiredBGColor(IRow row){
		row.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		row.getCell(DESC_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	}

	/**
	 * 载入合同详细的分录
	 */
	protected void loadDetailEntries() {
		if (STATUS_ADDNEW.equals(getOprtState()))
			return;
		getDetailInfoTable().removeRows(false);

		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();

		ContractDetailDefCollection detailColls = getConDetailsDef();
		
		ContractBillEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillEntryInfo) coll.get(i);
			IRow row = getDetailInfoTable().addRow();
			row.getCell(DETAIL_COL).setValue(entryInfo.getDetail());

			DataTypeEnum dataType = entryInfo.getDataType();
			if (dataType == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
			} else if (dataType == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
				row.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			//详细信息定义ID可能为空， 此处需添加判断。 modify by yzq at 2010-09-20
			if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
				ContractDetailDefInfo cddi = null;
				try {
					cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(
							new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
				} catch (Exception e1) {
					logger.warn(e1.getCause(), e1);
				} 
				if (cddi != null && cddi.isIsMustInput()) {
					setRequiredBGColor(row);
				}
			}

			KDTDefaultCellEditor editor = getEditorByDataType(dataType);

			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			if (entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)) {
				KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
				KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(kDBizPromptBoxContract);
				row.getCell(CONTENT_COL).setEditor(prmtContractEditor);
				ObjectValueRender objectValueRender = getF7Render();
				row.getCell(CONTENT_COL).setRenderer(objectValueRender);
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(LO_ROW)) {
				KDComboBox box = (KDComboBox) row.getCell(CONTENT_COL)
						.getEditor().getComponent();
				IsLonelyChangeListener isLonelyChangeListener = new IsLonelyChangeListener();
				box.addItemListener(isLonelyChangeListener);

				lastDispAddRows = true;
			}

			if (entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)) {
				Component component = row.getCell(CONTENT_COL).getEditor()
						.getComponent();
				if (component instanceof KDBizPromptBox) {
					KDBizPromptBox box = (KDBizPromptBox) row.getCell(
							CONTENT_COL).getEditor().getComponent();
					box.addDataChangeListener(new MyDataChangeListener());
				}
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NA_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);

			}
			//预估合同变动金额  2010330 wangxin
			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(ES_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);

			}
			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(ESID_ROW)) {
				row.getStyleAttributes().setHided(true);

			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NU_ROW)
					&& entryInfo.getContent() != null
					&& entryInfo.getContent().trim().length() > 0) {
				String id = entryInfo.getContent();
				try {
					ContractBillInfo contractBillInfo = ContractBillFactory
							.getRemoteInstance().getContractBillInfo(
									new ObjectUuidPK(id));
					row.getCell(CONTENT_COL).setValue(contractBillInfo);
					mainContractId = id;
				} catch (Exception e) {
					handUIException(e);
				}
				
			} else if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(AM_ROW)) {
				if (entryInfo.getContent() != null
						&& entryInfo.getContent().trim().length() > 0) {
					BigDecimal decm = new BigDecimal(entryInfo.getContent());
					row.getCell(CONTENT_COL).setValue(decm);
				}

				if (isUseAmtWithoutCost) {
					if (STATUS_FINDVIEW.equals(this.getOprtState())
							|| STATUS_VIEW.equals(this.getOprtState())) {
						row.getCell(CONTENT_COL).getStyleAttributes()
								.setLocked(true);
					} else
						row.getCell(CONTENT_COL).getStyleAttributes()
								.setLocked(false);
					txtamount.setEditable(false);
					setAmountRequired(false);
				} else {
					row.getCell(CONTENT_COL).getStyleAttributes().setLocked(
							true);
					txtamount.setEditable(true);
					setAmountRequired(true);
				}
			} else if (dataType == DataTypeEnum.BOOL) {
				BooleanEnum be = BooleanEnum.FALSE;
				if (entryInfo.getContent() != null
						&& entryInfo.getContent().equals(
								BooleanEnum.TRUE.getAlias())) {
					be = BooleanEnum.TRUE;

				}
				row.getCell(CONTENT_COL).setValue(be);
			} else if (dataType == DataTypeEnum.DATE
					&& !FDCHelper.isEmpty(entryInfo.getContent())) {
				DateFormat df = DateFormat.getDateInstance();

				Date date = null;
				try {
					date = df.parse(entryInfo.getContent());
				} catch (ParseException e) {
					e.printStackTrace();
					//throw new RuntimeException(e);
				}
				row.getCell(CONTENT_COL).setValue(date);
			} else {
				row.getCell(CONTENT_COL).setValue(entryInfo.getContent());
			}

			row.getCell(ROWKEY_COL).setValue(entryInfo.getRowKey());
			row.getCell(DESC_COL).setValue(entryInfo.getDesc());
			row.getCell(DATATYPE_COL).setValue(dataType);
			row.getCell(DETAIL_DEF_ID).setValue(entryInfo.getDetailDefID());
			//			lastDispAddRows=false;
			row.getCell(DESC_COL).setEditor(editorString);

			if (dataType == DataTypeEnum.STRING) {
				int height = row.getHeight();
				int lentgh1 = entryInfo.getContent() != null ? entryInfo
						.getContent().length() : 0;
				int lentgh2 = entryInfo.getDesc() != null ? entryInfo.getDesc()
						.length() : 0;
				int heightsize = lentgh1 / 75 > lentgh2 / 125 ? lentgh1 / 75
						: lentgh2 / 125;

				row.setHeight(height * (1 + heightsize));
			}
			if(STATUS_EDIT.equals(getOprtState())){
				//在保存时候已经有的合同详细信息从 detailColls 中移除，detailColls 中剩余的就是需要重新追加到表格尾部的记录了。
				if(detailColls!=null&&detailColls.size()>0){
					for(int j = 0;j<detailColls.size();j++){
						ContractDetailDefInfo detail = (ContractDetailDefInfo)detailColls.get(j);
						String detailId = detail.getId().toString();
						if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
							if(entryInfo.getDetailDefID().equals(detailId)){
								detailColls.remove(detail);
							}
						}
					}
				}
			}
		}
		//追加到表格尾部
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter.next();
					fillConDetails(editorString, detail);
				}
			}

			if (!lastDispAddRows) {
				fillDetailByPropert(editData.getContractPropert());
			}
			if (isUseAmtWithoutCost && mainContractId != null) {
				this.comboCurrency.setEnabled(false);
			}

			detailTableAutoFitRowHeight();
		}
	}
	private void fillConDetails(KDTDefaultCellEditor editorString, ContractDetailDefInfo detail) {
		IRow appendRow = this.tblDetail.addRow();
		appendRow.getCell(DETAIL_COL).setValue(detail.getName());
		KDTDefaultCellEditor _editor = getEditorByDataType(detail
				.getDataTypeEnum());
		if (_editor != null) {
			appendRow.getCell(CONTENT_COL).setEditor(_editor);
		}
		if (detail.getDataTypeEnum() == DataTypeEnum.DATE) {
			appendRow.getCell(CONTENT_COL).setValue(
					FDCHelper.getCurrentDate());
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setNumberFormat("%r{yyyy-M-d}t");
		} else if (detail.getDataTypeEnum() == DataTypeEnum.NUMBER) {
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		appendRow.getCell(DATATYPE_COL).setValue(
				detail.getDataTypeEnum());
		appendRow.getCell(DETAIL_DEF_ID).setValue(
				detail.getId().toString());

		appendRow.getCell(DESC_COL).setEditor(editorString);

		// 如果合同详细定义为必录项，则背景色要显示为必录色 added by owen_wen 2010-09-10
		if (detail.isIsMustInput()) {
			this.setRequiredBGColor(appendRow);
		}
	}
	private ContractDetailDefCollection getConDetailsDef() {
		String conTypeId = this.editData.getContractType().getId().toString();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("dataTypeEnum"));
		selector.add(new SelectorItemInfo("isMustInput"));
		selector.add(new SelectorItemInfo("name"));
		selector.add(new SelectorItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id",conTypeId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(selector);
		ContractDetailDefCollection detailColls = null;
		try {
			detailColls = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefCollection(view);
		} catch (BOSException e2) {
			e2.printStackTrace();
		}
		return detailColls;
	}

	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ContractBillInfo info = (ContractBillInfo) newData;
//		info.setId(BOSUuid.create(info.getBOSType()));
		info.setCurProject(editData.getCurProject());
		info.setContractType(editData.getContractType());
		info.setIsArchived(false);
		
		info.setProgrammingContract(null);
		this.textFwContract.setText(null);
	}

	/*
	 * 增加编辑控件的解锁。
	 */
	protected void unLockUI() {
		super.unLockUI();
		getDetailInfoTable().getStyleAttributes().setLocked(false);
		chkCostSplit.setEnabled(true);
		chkIsPartAMaterialCon.setEnabled(true);
		/*
		 * 原币金额是否可写由是否使用不计成本的金额来控制
		 */
		int rowIndex = getRowIndexByRowKey(AM_ROW);
		IRow row = getDetailInfoTable().getRow(rowIndex);
		if (row != null) {
			ICell cell = row.getCell(CONTENT_COL);
			if (isUseAmtWithoutCost) {
				if (rowIndex > 0)
					cell.getStyleAttributes().setLocked(false);
				txtamount.setEditable(false);
			} else {
				if (rowIndex > 0)
					cell.getStyleAttributes().setLocked(true);
				txtamount.setEditable(true);
			}
		}

		int conNameRowIdx = getRowIndexByRowKey(NA_ROW);

		if (conNameRowIdx > 0) {
			getDetailInfoTable().getRow(conNameRowIdx).getCell(CONTENT_COL)
					.getStyleAttributes().setLocked(true);
		}
	}

	protected void lockUIForViewStatus() {
		super.lockUIForViewStatus();
		chkCostSplit.setEnabled(false);
		chkIsPartAMaterialCon.setEnabled(false);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		prmtcontractType.setEnabled(true);
		kDDateCreateTime.setEnabled(false);
		comboCurrency.setEnabled(true);
		prmtModel.setEnabled(true);
		getDetailInfoTable().removeRows();
		ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType.getData();
		if (cType != null) {				
			fillDetailByContractType(cType.getId().toString());
		}

		lastDispAddRows = false;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		Object obj = txtExRate.getValue();

		//该合同已经进行了拆分，不能进行修改
		if (editData.getSplitState() != null
				&& !CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
			SysUtil.abort();
		}

		super.actionEdit_actionPerformed(e);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);
		//		没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
		if (STATUS_EDIT.equals(getOprtState())) {
			if ((this.editData.getState()
					.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED_VALUE))) {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			} else {
				prmtcontractType.setEnabled(true);
				//				actionSplit.setEnabled(true);
			}
		}
		comboCurrency_itemStateChanged(null);

		if (obj != null) {
			txtExRate.setValue(obj);
		}
		if (isUseAmtWithoutCost && mainContractId != null) {
			this.comboCurrency.setEnabled(false);
		}
		
		if(editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}
		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();
		
		ContractDetailDefCollection detailColls = getConDetailsDef();
		ContractBillEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillEntryInfo) coll.get(i);
			// 在保存时候已经有的合同详细信息从 detailColls 中移除，detailColls
			// 中剩余的就是需要重新追加到表格尾部的记录了。
			if (detailColls != null && detailColls.size() > 0) {
				for (int j = 0; j < detailColls.size(); j++) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) detailColls
							.get(j);
					String detailId = detail.getId().toString();
					if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
						if (entryInfo.getDetailDefID().equals(detailId)) {
							detailColls.remove(detail);
						}
					}
				}
			}
		}
	//追加到表格尾部
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter
							.next();
					fillConDetails(editorString, detail);
				}
			}
		}
		if(prmtModel.getValue()!= null){
			this.prmtModel.setEnabled(false);
		}
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ICell loCell = getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL);
			txtamount.setEditable(false);
			txtGrtRate.setRequired(false);
			txtGrtRate.setEditable(false);
			txtGrtAmount.setRequired(false);
			txtGrtAmount.setEditable(false);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			loCell.getStyleAttributes().setLocked(true);
			this.txtGrtRate.setEditable(false);
		}
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		//super.actionSplit_actionPerformed(e);
		
		if (getSelectBOID() == null)
			return;
		//boolean isCostSplit=chkCostSplit.isSelected();
		//合同拆分		jelon 12/30/2006
		String contrBillID = getSelectBOID();

		AbstractSplitInvokeStrategy invokeStra = new ContractSplitInvokeStrategy(
				contrBillID, this);
		invokeStra.invokeSplit();
	}

	public void actionDelSplit_actionPerformed(ActionEvent e) throws Exception {
		checkConInWF();
		//super.com
		if (getSelectBOID() == null)
			return;

		ContractBillInfo costBillInfo = null;
		//合同拆分		jelon 12/30/2006
		String contrBillID = getSelectBOID();

		SelectorItemCollection selectors = new SelectorItemCollection();

		selectors.add("id");
		selectors.add("splitState");

		costBillInfo = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(contrBillID)), selectors);

		//该合同已经进行了拆分，不能进行修改
		if (costBillInfo.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(costBillInfo
						.getSplitState())) {
			MsgBox.showWarning(this, "该合同还没有拆分");
			SysUtil.abort();
		}

		if (confirmRemove()) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contrBillID));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("id");

			ContractCostSplitCollection col = ContractCostSplitFactory
					.getRemoteInstance().getContractCostSplitCollection(view);
			IObjectPK[] pks = new IObjectPK[col.size()];
			for (int i = 0; i < col.size(); i++) {
				pks[i] = new ObjectUuidPK(col.get(i).getId().toString());
			}

			ContractCostSplitFactory.getRemoteInstance().delete(pks);

			ConNoCostSplitCollection nocol = ConNoCostSplitFactory
					.getRemoteInstance().getConNoCostSplitCollection(view);
			IObjectPK[] nopks = new IObjectPK[nocol.size()];
			for (int i = 0; i < nocol.size(); i++) {
				nopks[i] = new ObjectUuidPK(nocol.get(i).getId().toString());
			}

			ConNoCostSplitFactory.getRemoteInstance().delete(nopks);
			//ContractCostSplitFactory.getRemoteInstance().delete(filter);
			//ConNoCostSplitFactory.getRemoteInstance().delete(filter);	
		}
	}

	private void checkConInWF() {
		if (editData == null || editData.getSplitState() == null)
			return;
		//是否必须审核前拆分
		try {
			if (splitBeforeAudit
					&& FDCUtils.isRunningWorkflow(editData.getId().toString())
					&& CostSplitStateEnum.ALLSPLIT.equals(editData
							.getSplitState())) {
				MsgBox.showWarning(this, "合同在工作流，不能删除合同拆分！");
				SysUtil.abort();
			}
		} catch (BOSException e) {

			e.printStackTrace();
		}
	}

	public static void showSplitUI(CoreUIObject parentUI, String billId,
			String billPropName, String bosType, boolean isCostSplit)
			throws BOSException, UIException {
		String splitBillID = null;

		FDCBillInfo billInfo = null;
		CoreBaseCollection coll = null;

		String editName = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(billPropName, billId));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		if (isCostSplit) {
			// coll = ContractCostSplitFactory.getRemoteInstance().
			// getContractCostSplitCollection(view);
			editName = com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
			coll = ContractCostSplitFactory.getRemoteInstance().getCollection(view);
		} else {
			editName = com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class.getName();
			coll = ConNoCostSplitFactory.getRemoteInstance().getCollection(view);
		}

		boolean isSplited = false;
		boolean isAudited = false;

		if (coll.size() > 0) {
			billInfo = (FDCBillInfo) coll.get(0);
			splitBillID = billInfo.getId().toString();
			isSplited = true;

			if (billInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
				isAudited = true;
			}
		}

		UIContext uiContext = new UIContext(parentUI);
		String oprtState;

		if (isSplited) {
			uiContext.put(UIContext.ID, splitBillID);

			if (isAudited) {
				oprtState = OprtState.VIEW;
			} else {
				oprtState = OprtState.EDIT;
			}
		} else {
			uiContext.put("costBillID", billId);
			oprtState = OprtState.ADDNEW;
		}

		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(editName, uiContext, null, oprtState);

		uiWin.show();
	}

	//设置精度
	protected void setPrecision() {
		CurrencyInfo currency = editData.getCurrency();
		Date bookedDate = (Date) editData.getBookedDate();

		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,
					currency.getId(), company, bookedDate);
		} catch (Exception e) {
			e.printStackTrace();
			txtExRate.setPrecision(2);
			return;
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtExRate.setPrecision(exPrecision);
		BigDecimal exRate = editData.getExRate();
		txtExRate.setValue(exRate);
		txtamount.setPrecision(curPrecision);
		txtamount.setValue(editData.getOriginalAmount());
	}

	protected void comboCurrency_itemStateChanged(ItemEvent e) throws Exception {

		super.comboCurrency_itemStateChanged(e);
		if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState()))
			return;
		if (e == null || e.getStateChange() == ItemEvent.SELECTED) {
			CurrencyInfo currency = (CurrencyInfo) this.comboCurrency
					.getSelectedItem();
			//CurrencyInfo baseCurrency = FDCClientHelper.getBaseCurrency(SysContext.getSysContext().getCurrentFIUnit());
			Date bookedDate = (Date) pkbookedDate.getValue();

			ExchangeRateInfo exchangeRate = FDCClientHelper
					.getLocalExRateBySrcCurcy(this, currency.getId(), company,
							bookedDate);

			int curPrecision = FDCClientHelper.getPrecOfCurrency(currency
					.getId());
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;

			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}

			txtExRate.setPrecision(exPrecision);
			txtExRate.setValue(exRate);
			txtamount.setPrecision(curPrecision);

			if (baseCurrency != null
					&& baseCurrency.getId().equals(currency.getId())) {
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			} else {
				txtExRate.setEditable(true);
				txtExRate.setRequired(true);
				txtExRate.setEnabled(true);
			}
			calLocalAmt();
		}
	}

	protected void txtExRate_dataChanged(DataChangeEvent e) throws Exception {
		super.txtExRate_dataChanged(e);
		calLocalAmt();
	}
	/**
	 * 计算付款事项的原币金额或者比例   by Cassiel_peng 2009-8-26
	 */
	private void calPayItemAmt(){
		EcoItemHelper.calPayItemAmt(this.tblEconItem, this.tblBail, txtamount);
	}
	/**
	 * 计算履约保证金及返还部分的原币金额或者比例   by Cassiel_peng 2009-8-26
	 * 当合同金额发生改变时，应该以履约保证金额*返还比率 兰远军
	 */
	private void calBailAmt(){
	   EcoItemHelper.calBailAmt(tblBail, txtamount, txtBailOriAmount, txtBailRate);
	}
	/**
	 * 计算本币金额
	 *
	 */
	private void calLocalAmt() {
		if (txtamount.getBigDecimalValue() != null
				&& txtExRate.getBigDecimalValue() != null) {
//			BigDecimal lAmount = FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(),2).multiply(
//					FDCHelper.toBigDecimal(txtExRate.getBigDecimalValue(),2));
			/*
			 * 注：1.金额保存时尽量保留多位小数，以便后续计算准确
			 *     2.小数保存后取出进行运算或比较时可随意截取小数位
			 *     3.小数位数据相互计算时先计算再截取
			 */    
			
			BigDecimal lAmount = txtamount.getBigDecimalValue().multiply(
					txtExRate.getBigDecimalValue());
			txtLocalAmount.setNumberValue(lAmount);
		} else {
			txtLocalAmount.setNumberValue(null);
		}
		
		//需要计算本位币的地方，都需要重新计算印花税金额
		calStampTaxAmt();
		
		calGrtAmt();
	}

	/**
	 * 计算印花税金额
	 *
	 */
	private void calStampTaxAmt() {
		
		//使用不计成本的金额
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}
			BigDecimal originalAmount = (BigDecimal) newValue;
			//计算印花税
			if(originalAmount!=null&&txtExRate.getBigDecimalValue() != null&&txtStampTaxRate.getBigDecimalValue()!=null){
				BigDecimal stampTaxAmount = FDCHelper.multiply(originalAmount,txtExRate.getBigDecimalValue()).multiply(txtStampTaxRate.getBigDecimalValue());
				stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
						BigDecimal.ROUND_HALF_UP);
				txtStampTaxAmt.setNumberValue(stampTaxAmount);
			}else{
				txtStampTaxAmt.setNumberValue(null);
			}
			return;
		}

		//不使用不计成本的金额
		if (txtamount.getBigDecimalValue() != null
				&& txtExRate.getBigDecimalValue() != null&&txtStampTaxRate.getBigDecimalValue()!=null) {
			BigDecimal stampTaxAmount = FDCHelper.multiply(txtamount.getBigDecimalValue(),txtExRate.getBigDecimalValue()).multiply(txtStampTaxRate.getBigDecimalValue());
			stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtStampTaxAmt.setNumberValue(stampTaxAmount);
		} else {
			txtStampTaxAmt.setNumberValue(null);
		}
	}

	/**
	 * 计算保修金额
	 * 
	 */
	private void calGrtAmt() {

		//使用不计成本的金额,计算保修金额
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}

			//计算保修金额
			BigDecimal originalAmount = (BigDecimal) newValue;
			if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
				originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
			}
			BigDecimal grtAmount = originalAmount.multiply(txtGrtRate
					.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);

			return;
		}

		//不使用不计成本的金额
		if (txtamount.getBigDecimalValue() != null
				&& txtGrtRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
			BigDecimal grtAmount = txtamount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue()).multiply(
					txtGrtRate.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);
		} else {
			txtGrtAmount.setNumberValue(null);
		}
	}

	/**
	 * 描述： 合同金额<br>
	 * 
	 * <pre>
	 *       	 isUseAmtWithoutCost true 
	 *       				取不计成本的金额 
	 *            isUseAmtWithoutCost false
	 *                    不使用不计成本的金额
	 *   @return BigDecimal
	 * 
	 */
	private BigDecimal getAmount() {

		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}
			return FDCHelper.toBigDecimal(newValue);
		} else {
			return FDCHelper.toBigDecimal(txtamount.getBigDecimalValue());
		}
	}

	/**
	 * 描述：R090112-050保修金额是根据保修金比例自动计算的，不能直接录入。
	 * <p>
	 * 业务中的保修金额有的时候不根据比例制定，可以直接录入保修金额，再倒算保修金比例
	 * 
	 * @author pengwei_hou Date:2009-04-10
	 * @param flag
	 * @param e
	 */
	private void setGrtAmt(String flag, DataChangeEvent e) {
		BigDecimal amount = getAmount();
		if (amount != null && txtExRate.getBigDecimalValue() != null) {
			amount = amount.multiply(txtExRate.getBigDecimalValue());
		}
		if(FDCHelper.toBigDecimal(txtGrtAmount.getBigDecimalValue()).compareTo(amount) > 0){
			String msg = "保修金大于 ";
			if (isUseAmtWithoutCost) {
				msg = msg + "合同本币";
			} else {
				msg  = msg + "补充合同金额本币";
			}
			MsgBox.showWarning(this, msg);
			txtGrtAmount.setValue(FDCHelper.ZERO);
			SysUtil.abort();
		}
		if (flag.equals("txtGrtAmount")) {

			DataChangeListener[] listeners = (DataChangeListener[]) txtGrtRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtGrtRate.removeDataChangeListener(listeners[i]);
			}
			BigDecimal grtAmount = FDCHelper.toBigDecimal(txtGrtAmount.getBigDecimalValue());
			BigDecimal grtRate = FDCHelper.ZERO;
			if (FDCHelper.ZERO.compareTo(amount) != 0) {
				grtRate = grtAmount.multiply(FDCHelper.ONE_HUNDRED).divide(amount, 6, BigDecimal.ROUND_HALF_UP);
				txtGrtRate.setValue(grtRate);
			}
			for (int i = 0; i < listeners.length; i++) {
				txtGrtRate.addDataChangeListener(listeners[i]);
			}
		} else {
			DataChangeListener[] listeners = (DataChangeListener[]) txtGrtAmount
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtGrtAmount.removeDataChangeListener(listeners[i]);
			}

			BigDecimal grtRate = FDCHelper.toBigDecimal(txtGrtRate.getBigDecimalValue());
			BigDecimal grtAmount = grtRate.multiply(amount).divide(FDCHelper.ONE_HUNDRED, 2,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setValue(grtAmount);

			for (int i = 0; i < listeners.length; i++) {
				txtGrtAmount.addDataChangeListener(listeners[i]);
			}
		}
	}

	protected void txtStampTaxRate_dataChanged(DataChangeEvent e) throws Exception {
		calStampTaxAmt();
	}

	protected void txtamount_dataChanged(DataChangeEvent e) throws Exception {
		calLocalAmt();
		calStampTaxAmt();
		calPayItemAmt();//by Cassiel_peng
		calBailAmt();

		setCapticalAmount();
	}

	protected void txtGrtRate_dataChanged(DataChangeEvent e) throws Exception {
		calLocalAmt();
		//    	calGrtAmt();
	}

	protected void verifyInputForSave() throws Exception {

		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		
		this.verifyInputForContractDetail();
		
		super.verifyInputForSave();

		FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);
	
		checkProjStatus();
		
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("合同摘要信息大于系统约定长度(255)！");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("录入战略子合同时，战略主合同编码不能为空！");
				abort();
			}
		}
	}

	public void actionViewCost_actionPerformed(ActionEvent e) throws Exception {
		String selectBOID = getSelectBOID();
		if (selectBOID == null) {
			MsgBox.showWarning(this, "合同还未保存");
			SysUtil.abort();
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, selectBOID);
		uiContext.put(FDCConstants.FDC_INIT_PROJECT, curProject);

		ContractCostInfoUI.showEditUI(this, uiContext, getOprtState());
	}

	protected void tblDetail_editStopping(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent evt)
			throws Exception {
		KDTable table = (KDTable) evt.getSource();
		IRow entryRow = table.getRow(evt.getRowIndex());
		//ICell cell = entryRow.getCell(evt.getColIndex());

		Object newValue = evt.getValue();
		IColumn col = table.getColumn(evt.getColIndex());
		String colKey = col.getKey();

		//内容发生修改
		if (colKey.equals("content") && (newValue instanceof BigDecimal)
				&& txtStampTaxRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {

			if (isUseAmtWithoutCost && AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
				//
				if (mainContractId != null) {
					MsgContractHasSplit();
				}
				detailAmount = (BigDecimal) newValue;
				// 计算印花税
				BigDecimal originalAmount = (BigDecimal) newValue;
				if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
					originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
				}
				BigDecimal stampTaxAmount = originalAmount.multiply(txtStampTaxRate.getBigDecimalValue());
				stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtStampTaxAmt.setNumberValue(stampTaxAmount);

				// 计算保修金额
				BigDecimal grtAmount = originalAmount.multiply(txtGrtRate.getBigDecimalValue());
				grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtGrtAmount.setNumberValue(grtAmount);
			}
		}
		if (colKey.equals("content")&&NU_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())&&ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
			if(newValue==null){
				this.prmtlandDeveloper.setValue(null);
				this.prmtpartB.setValue(null);
			}else{
				ContractBillInfo contractBillInfo=(ContractBillInfo)newValue;
				if(contractBillInfo.getLandDeveloper()!=null){
					this.prmtlandDeveloper.setValue(LandDeveloperFactory.getRemoteInstance().getLandDeveloperInfo(new ObjectUuidPK(contractBillInfo.getLandDeveloper().getId())));
				}
				if(contractBillInfo.getPartB()!=null){
					this.prmtpartB.setValue(SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(contractBillInfo.getPartB().getId())));
				}
			}
		}
	}

	public void MsgContractHasSplit() throws EASBizException, BOSException {
		if (this.checkContractHasSplit(mainContractId))
			MsgBox.showInfo(this,
					"该非单独计算的补充合同金额审批后会追加到对应主合同签约金额中，如主合同拆分已被引用请先删除或者作废该合同拆分。");
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionRemove_actionPerformed(e);
	}

	protected void checkBeforeRemove() throws Exception {
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill", editData.getId()));
		view.setFilter(filter);
		view.getSelector().add("id");
		CoreBillBaseCollection coll = ContractCostSplitFactory.getRemoteInstance()
				.getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		if (iter.hasNext()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
	}

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		//获取当前单据的责任部门
		Object obj = editData.get("respDept");
		AdminOrgUnitInfo adminOrgInfo = null; 
		if(obj !=null){
			adminOrgInfo = (AdminOrgUnitInfo)obj;
		}
		
		super.actionCopy_actionPerformed(e);
		chkCostSplit.setEnabled(true);
		editData.setSplitState(null);
		this.pkbookedDate.setValue(this.bookedDate);
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		//this.cbPeriod.setValue(this.curPeriod);
		
		//获取当前用户的权限组织
		Set orgsSet = FDCUtils.getAuthorizedOrgs(null);
		//如果当前用户没有责任部门组织的权限，应该显示为空。
		if(adminOrgInfo!=null){
			if(orgsSet==null || (orgsSet !=null && !orgsSet.contains(adminOrgInfo.getId().toString()))){
				this.prmtRespDept.setValue(null);
				editData.setRespDept(null);
			}
		}
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();

		//		 如果是虚体成本中心，则不能拆分
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
//		if(STATUS_ADDNEW.equals(getOprtState())){
//			prmtModel.setEnabled(false);
//		}else{
//			prmtModel.setEnabled(true);
//		}
	}
	private KDTable table=tblEconItem;
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....在工作流中是不能新增或者是删除分录的，故。。。
		}
		else{
			if (this.tblEconItem.isFocusOwner()) {
				table=this.tblEconItem;
			} 
			if (this.tblBail.isFocusOwner()) {
				table=this.tblBail;
			}
			addLine(getDetailTable());
			appendFootRow(getDetailTable());
			EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		}
	}
	
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....因为在工作流审批时需要将"查看正文"的所有按钮功能都放开(在有权限的前提下)故将界面操作状态设置为了STATUS.EDIT..但在工作路中是不能新增或者是删除分录的，故。。。
		}else{
			if (this.tblEconItem.isFocusOwner()) {
				this.tblEconItem.checkParsed();
				int index = -1;
				index = this.tblEconItem.getSelectManager().getActiveRowIndex();
				if (index != -1) {
					tblEconItem.removeRow(index);
				} else {
			
				}
			} else if (this.tblBail.isFocusOwner()) {
				this.tblBail.checkParsed();
				int index = -1;
				index = this.tblBail.getSelectManager().getActiveRowIndex();

				if (index != -1) {
					tblBail.removeRow(index);
					if(this.tblBail.getRowCount()<=0){
						this.txtBailOriAmount.setRequired(false);
						this.txtBailRate.setRequired(false);
					}
				} else {
					
				}
			}
		}
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.menuItemViewContent.setIcon(EASResource
				.getIcon("imgTbtn_seeperformance"));
		this.btnViewContent.setIcon(EASResource
				.getIcon("imgTbtn_seeperformance"));
		this.menuItemSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		this.btnContractPlan.setIcon(EASResource
				.getIcon("imgTbtn_subjectsetting"));
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		btnDelSplit.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		menuItemViewContent.setText(menuItemViewContent.getText() + "(V)");
		menuItemViewContent.setMnemonic('V');
		menuItemSplit.setText(menuItemSplit.getText() + "(S)");
		menuItemSplit.setMnemonic('S');
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));	
		btnViewCost.setIcon(EASResource.getIcon("imgTbtn_sequencecheck"));
		initEcoEntryTableStyle();

	}

	protected void checkRef(String id) throws Exception {
		super.checkRef(id);

		ContractClientUtils.checkContractBillRef(this, id);

		if (!splitBeforeAudit) {
			boolean hasSettleSplit = checkContractHasSplit(id);
			if (hasSettleSplit) {
				MsgBox.showWarning("合同已经拆分,不能反审批!");
				SysUtil.abort();
				return;
			}
		}
	}

	private boolean checkContractHasSplit(String id) throws EASBizException,
			BOSException {
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("contractBill.id", id));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = false;
		if (ContractCostSplitFactory.getRemoteInstance().exists(filterSett)
				|| ConNoCostSplitFactory.getRemoteInstance().exists(filterSett)) {
			hasSettleSplit = true;
		}
		return hasSettleSplit;
	}

	/**
	 * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {

		if (STATUS_ADDNEW.equals(this.oprtState)||STATUS_EDIT.equals(this.oprtState)) {
			handleCodingRule1();
		}
//		if(STATUS_EDIT.equals(getOprtState())){
//			String orgId = this.orgUnitInfo.getId().toString();
//			if (orgId == null || orgId.trim().length() == 0) {
//				// 当前用户所属组织不存在时，缺省实现是用集团的
//				orgId = OrgConstants.DEF_CU_ID;
//			}
//			if(editData.getCodeType() != null){
//				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
//				if (iCodingRuleManager.isExist(editData, orgId,"codeType.number")) {
//					if(!iCodingRuleManager.isModifiable(editData, orgId,"codeType.number")){
//						this.txtNumber.setEnabled(false);
//					} 
//				}
//			}
//		}
	}

	private void handleCodingRule1() {
		String orgId = this.orgUnitInfo.getId().toString();
		String bindingProperty = "codeType.number";
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				// 当前用户所属组织不存在时，缺省实现是用集团的
				orgId = OrgConstants.DEF_CU_ID;
			}
			//设置编码类型
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractTypeInfo cti = editData.getContractType();
			if (editData.getContractType() != null) {
				if (cti.getLevel() != 1) {
					//取一级合同类别
					String number = editData.getContractType().getLongNumber();
					if (number.indexOf("!") != -1) {
						number = number.substring(0, number.indexOf("!"));
					}

					cti = ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select id where longNumber = '" + number+ "'");
				}
			}
			ContractCodingTypeCollection cctc = null;
			if (cti != null) {
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//所有合同,因为现在尚不知合同性质
				filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));//合同属于新增状态
				evi.setFilter(filter);
				cctc = ContractCodingTypeFactory.getRemoteInstance().getContractCodingTypeCollection(evi);
			}

			ContractCodingTypeInfo codingType = null;
			ContractCodingTypeInfo codingTypeAll = null;
			if (cctc != null && cctc.size() > 0)
				codingType = cctc.get(0);

			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractType.id", null));
			//新建的话，所有合同
			filter.getFilterItems().add(
					new FilterItemInfo("secondType",
							ContractSecondTypeEnum.OLD_VALUE));
			//新增状态
			filter.getFilterItems().add(
					new FilterItemInfo("thirdType",
							ContractThirdTypeEnum.NEW_VALUE));
			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getRemoteInstance()
					.getContractCodingTypeCollection(evi);
			if (cctc.size() > 0)
				codingTypeAll = cctc.get(0);

			/************
			 * 对于合同的编码规则，可以按照合同类型设置编码规则，还包括一个"全部"的编码类型
			 * 如果同时设置了全部，和单个类型的编码规则，会有冲突
			 * 所以在这里加入了下面这段代码
			 * 当合同的编码类型为某一个具体的类型时，上面的代码先取这个具体的类型对应的编码规则，如果没有取到[说明是没有设置编码规则，或没有启用]
			 * 则再取一遍"全部"类型对应的编码规则
			 * 即: 当明细的合同类型，没有设置编码规则的时候，取全部类型对应的编码规则[当在合同修改界面，修改合同类型时,会走此处]
			 */

			/****
			 * 成本中心，一级类型有对应的编码规则
			 */
			if (codingType != null) {
				editData.setCodeType(codingType);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			/***
			 * 成本中心，全部类型对应有编码规则
			 */
			if (codingTypeAll != null) {
				editData.setCodeType(codingTypeAll);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			orgId = this.company.getId().toString();
			/****
			 * 财务组织，一级类型有对应的编码规则
			 */
			if (codingType != null) {
				editData.setCodeType(codingType);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			/***
			 * 财务组织，全部类型对应有编码规则
			 */
			if (codingTypeAll != null) {
				editData.setCodeType(codingTypeAll);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			this.txtNumber.setEnabled(true);
			//在没有启用编码规则的情况下，不清空number的内容   By zhiyuan_tang 2010/06/29
//			this.txtNumber.setText(null);

		} catch (Exception err) {
			err.printStackTrace();
			// 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
			setNumberTextEnabled();
			this.handleException(err);
		}
	}

	/**
	 * 转换编码number，将"!"变为"."
	 * @param orgNumber
	 * @author owen_wen 2010-11-23
	 */
	private String convertNumber(String orgNumber){
		return orgNumber.replaceAll("!", ".");
	}
	
	protected boolean setNumber(ICodingRuleManager iCodingRuleManager,
			String orgId, String bindingProperty) throws CodingRuleException,
			EASBizException, BOSException {

		if (iCodingRuleManager.isExist(this.editData, orgId, bindingProperty)) {
			String number = "";
			//删除Number为null的判断  原因：选中一个有编码规则的合同类型，点击新增
			//number会被设置为获取到的合同编码，这样当再次改变合同类型时，不会进行进行取号处理  By zhiyuan_tang 2010/06/30
//			if (editData.getNumber() == null) {
			if (iCodingRuleManager.isUseIntermitNumber(editData, orgId,
					bindingProperty)) { // 编码规则中不允许断号					
				// 编码规则中启用了“断号支持”功能，此时只是读取当前最新编码，真正的抢号在保存时

				if (STATUS_ADDNEW.equals(this.oprtState)){ //只有新增时才需要去取number，如果是编辑，则要保留之前的number Added by Owe_wen 2010-09-16
					number = iCodingRuleManager.readNumber(editData, orgId, bindingProperty, "");
					//把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
					prepareNumber(editData, convertNumber(number));
				}
			} else if (iCodingRuleManager.isAddView(editData, orgId, bindingProperty)) { // 编码规则中启用了“新增显示”			
			//						number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
				// 没有启用断号支持功能，此时获取了编码规则产生的编码
				String costCenterId = null;
				if (editData instanceof FDCBillInfo
						&& ((FDCBillInfo) editData).getOrgUnit() != null) {
					costCenterId = ((FDCBillInfo) editData).getOrgUnit()
							.getId().toString();
				} else {
					costCenterId = SysContext.getSysContext()
							.getCurrentCostUnit().getId().toString();
				}
				try {
					if (STATUS_ADDNEW.equals(this.oprtState)){ //只有新增时才需要去取number，如果是编辑，则要保留之前的number Added by Owe_wen 2010-09-16
						number = prepareNumberForAddnew(iCodingRuleManager, (FDCBillInfo) editData, orgId, costCenterId, bindingProperty);
						// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
						prepareNumber(editData, convertNumber(number));
					}
				} catch (Exception e) {
					e.printStackTrace();
					handleCodingRuleError(e);
				}
			} else {
				this.txtNumber.setEnabled(false);
			}
//			}
			if (iCodingRuleManager.isModifiable(editData, orgId,bindingProperty)) {
				// 如果启用了用户可修改
				setNumberTextEnabled();
			} else {
				this.txtNumber.setEnabled(false);
			}
			return true;
		}
		return false;
	}

	// 编码规则中启用了“新增显示”,必须检验是否已经重复
	protected String prepareNumberForAddnew(ICodingRuleManager iCodingRuleManager, FDCBillInfo info,
			String orgId, String costerId, String bindingProperty) throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// 如果编码重复重新取编码
			if (bindingProperty != null) {
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			} else {
				number = iCodingRuleManager.getNumber(editData, orgId);
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (((IFDCBill) getBizInterface()).exists(filter) && i < 1000);

		return number;
	}

	protected void setNumberTextEnabled() {

		//		if(getNumberCtrl() != null) {
		//			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		//			
		//			if(currentCostUnit != null) {
		//				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentCostUnit.getId().toString());
		//				
		//				getNumberCtrl().setEnabled(isAllowModify);
		//				getNumberCtrl().setEditable(isAllowModify);
		//			}
		//		}
	}

	boolean amtWarned = false;

	protected void txtamount_focusGained(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtamount_focusGained(e);

		if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
			ContractClientUtils.checkSplitedForAmtChange(this, getSelectBOID());
			amtWarned = true;
		}
	}
	
	protected void afterSubmitAddNew() {
		//是否需要弹出预估合同变动  20120222 wangxin
		//启用了必须关联合约规划
//			try {
//				if((ContractPropertyEnum.DIRECT.equals(this.editData.getContractPropert())&&this.editData.getProgrammingContract()!=null)||
//						ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//					openContractEstimateChange();
//				}
//				
//			} catch (UIException e1) {
//				e1.printStackTrace();
//			}
			//EAS3G处理
//			try {
//				dealEAS3GAttachment();
//			} catch (BOSException e1) {
//				e1.printStackTrace();
//			} catch (EASBizException e2) {
//				e2.printStackTrace();
//			}
		
		super.afterSubmitAddNew();
		if(prmtcontractType.getValue()!=null){
			try {
				contractTypeChanged(prmtcontractType.getValue());
			} catch (CodingRuleException e) {
				handUIException(e);
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			} catch (Exception e) {
				handUIException(e);
			}
			detailTableAutoFitRowHeight();
		}
	}
	
	/**
	 * 拆分后，不允许修改“是否成本拆分”字段
	 */
	protected void chkCostSplit_mouseClicked(MouseEvent e) throws Exception {
		super.chkCostSplit_mouseClicked(e);

		if (STATUS_EDIT.equals(getOprtState())) {

			if (editData.getSplitState() != null && editData.getSplitState() != CostSplitStateEnum.NOSPLIT) {

				boolean b = chkCostSplit.isSelected();

				chkCostSplit.getModel().setSelected(!b);

				MsgBox.showWarning(this, ContractClientUtils.getRes("splitedNotChangeIsCSplit"));

				SysUtil.abort();
			}
		}
		setCheckBoxValue();
	}

	/**
	 * 描述：若是甲供材合同，则是否进入动态成本项为否，且不可修改. <br>
	 * 
	 * 2009-9-3 进入动态成本，和是否甲供材，没有关系了！by yong_zhou
	 * @param MouseEvent
	 *            e
	 */
	protected void chkIsPartAMaterialCon_mouseClicked(MouseEvent e)
			throws Exception {
		setCheckBoxValue();
	}
	private void setCheckBoxValue(){
		/*
		 * 2009-9-3 进入动态成本，和是否甲供材，没有关系了！by yong_zhou
		 */
//		if (chkIsPartAMaterialCon.isSelected()) {
//			chkCostSplit.setSelected(false);
//			chkCostSplit.setEnabled(false);
//			chkIsPartAMaterialCon.setEnabled(true);
//		}else{
//			chkCostSplit.setEnabled(true);
//		}
//		if (chkCostSplit.isSelected()) {
//			chkIsPartAMaterialCon.setSelected(false);
//			chkIsPartAMaterialCon.setEnabled(false);
//			chkCostSplit.setEnabled(true);
//		} else{
//			chkIsPartAMaterialCon.setEnabled(true);
//		}
	}
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
	}
	protected void verifyInput(ActionEvent e) throws Exception {

		//如果启用财务一体化,必须录入期间
		if (this.isIncorporation && cbPeriod.getValue() == null) {
			MsgBox.showConfirm2(this, "启用成本月结,必须录入期间");
			SysUtil.abort();
		}

		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);

		super.verifyInput(e);
		//		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
		//		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);

		/*
		 * 补充合同必须录入主合同编码
		 */
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ContractBillEntryCollection entrys = editData.getEntrys();
			boolean hasMainContNum = false;
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillEntryInfo element = (ContractBillEntryInfo) iter
						.next();
				String rowKey = element.getRowKey();
				if (rowKey != null && rowKey.equals(NU_ROW)
						&& element.getContent() != null
						&& element.getContent().length() > 0) {
					hasMainContNum = true;
					break;
				}
			}

			if (!hasMainContNum) {
				MsgBox.showWarning(this, "补充合同必须录入主合同编码(在合同详细信息中录入)");
				SysUtil.abort();
			}
		}

//		if (!chkCostSplit.isSelected()) {
//			MsgBox.showInfo(this, ContractClientUtils.getRes("NotEntryCost"));
//		}

		checkStampMatch();

		checkProjStatus();

	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	/**
	 * 提交时校验:履约保证金金额必须等于履约保证金及返还列表分录中返回金额之和  by Cassiel_peng  2008-8-26
	 */
	private void checkBailOriAmount() {
		BigDecimal itemAmtSum=FDCHelper.ZERO;
		for(int i=0;i<tblEconItem.getRowCount();i++){
			itemAmtSum=FDCHelper.add(itemAmtSum, tblEconItem.getRow(i).getCell("payAmount").getValue());
		}
		if(itemAmtSum.compareTo(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(),2))==1){
			MsgBox.showWarning("合同经济条款付款金额之和不能大于合同原币金额！");
			SysUtil.abort();
		}
		BigDecimal bailAmountSum=FDCHelper.ZERO;
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailAmount")!=null){
				bailAmountSum=FDCHelper.add(bailAmountSum, this.tblBail.getRow(i).getCell("bailAmount").getValue());
			}
		}
		if(bailAmountSum.compareTo(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2))!=0){
			MsgBox.showWarning("履约保证金金额必须等于履约保证金及返还列表分录中返回金额之和");
			SysUtil.abort();
		}
	}
	
	/**
	 * 校验 “合同详细信息” 中的必录项：如果必录项为空，给出提示
	 * @author owen_wen 2010-09-10
	 */
	private void verifyInputForContractDetail(){
		ContractBillEntryCollection coll = editData.getEntrys();
		
		for (int i = 0, size = coll.size(); i < size ; i++){
			ContractBillEntryInfo entryInfo = coll.get(i);
			
			ContractDetailDefInfo cddi = null;
			
			// 有可能是补充合同加上的分录，这里不作判断
			if (coll.get(i).getDetailDefID() == null)
				continue;
			
			try {
				cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
			} catch (EASBizException e1) {
				this.handleException(e1);
			} catch (BOSException e1) {
				this.handleException(e1);
			} catch (UuidException e1) {
				this.handleException(e1);
			}
			
			if (cddi!=null && cddi.isIsMustInput()){
				if (entryInfo.getContent() == null){
					String info = ContractClientUtils.getRes("conDtlCantEmpty");
					String[] args = new String[] {entryInfo.getDetail(), "内容"};
					FDCMsgBox.showInfo(this,FDCClientHelper.formatMessage(info, args));
					SysUtil.abort();
					return;
				}
			}
		}
	}
	protected BigDecimal getAccActOnLoadPcAmount(ProgrammingContractInfo pc) throws BOSException, SQLException{
		if(pc==null) return FDCHelper.ZERO;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(bill.famount) amount from T_CON_ContractWithoutText bill ");
		_builder.appendSql(" where bill.fProgrammingContract='"+pc.getId().toString()+"' and bill.fstate in('2SUBMITTED','3AUDITTING')");
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("amount")!=null)
				return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	protected void verifyInputForSubmint() throws Exception {
		this.verifyInputForContractDetail();
		checkAmout();
		//预算控制
		checkMbgCtrlBalance();
		
		//增加校验
		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);
		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
		FDCClientVerifyHelper.verifyEmpty(this, prmtRespDept);
		FDCClientVerifyHelper.verifyEmpty(this, prmtRespPerson);
		FDCClientVerifyHelper.verifyEmpty(this, prmtContractWFType);
		FDCClientVerifyHelper.verifyEmpty(this, cbOrgType);
		if(this.prmtInviteType.isEnabled()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtInviteType);
		}
		
		super.verifyInputForSubmint();
		if(tblBail.getRowCount()>0){
			FDCClientVerifyHelper.verifyEmpty(this, txtBailOriAmount);
			FDCClientVerifyHelper.verifyEmpty(this, txtBailRate);
		}
		EcoItemHelper.checkVeforeSubmit(tblEconItem, tblBail);
		checkBailOriAmount();
		
		checkBeforeSubmit();
		//合同提交审批前关校验是否关联进度任务
		if(isRelatedTask()){
			ContractClientUtils.checkConRelatedTaskSubmit(this.editData);
		}
		
		verifyContractProgrammingPara();
		
		
		//变态的需求： 集团的不需要检测合同结算类型
//		String orgName = this.editData.getOrgUnit().getDisplayName();
//		String targetName = "旭辉集团股份有限公司";
//		if(!this.btnAType.isSelected()&&!this.btnBType.isSelected()&&!this.BtnCType.isSelected()&& orgName.indexOf(targetName)==-1&&isNeedCheckContractType()){
//			FDCMsgBox.showWarning(this, "请填写合同结算类型！");
//			this.btnAType.requestFocus(true);
//			SysUtil.abort();
//		}
	}
	private void verifyContractProgrammingPara() throws BOSException, SQLException, EASBizException {
		ProgrammingContractInfo pc = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		String param = null;
		ObjectUuidPK pk = new ObjectUuidPK(editData.getOrgUnit().getId());
		param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
		
		String programControlMode = param;//0:严格控制， 1:提示控制；2:不提示
		if(programControlMode != null && !"2".equals(programControlMode)){
			if(editData==null||editData.getId()==null){
				FDCMsgBox.showWarning(this,"请先保存单据，并关联合约规划！");
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		if(pc!=null&&!ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
			if(ContractBillFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning("合约规划已经被其他合同关联，请重新选择！");
				SysUtil.abort();
			}
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			if(ContractPCSplitBillEntryFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning("合约规划已经被其他跨期合同关联，请重新选择！");
				SysUtil.abort();
			}
		}
		//======================合约框架控制合同签定逻辑控制=====================================================================
		//1、不但要启用关联合约框架的参数，还是进入动态成本的合同类型,且不是全期合同。
		if(!curProject.isIsWholeAgeStage()){
			param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
			if (!com.kingdee.util.StringUtils.isEmpty(param)) {
				int i = Integer.parseInt(param);
				boolean isAbort=false;
				if(pc==null){
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("programming.project.id", this.editData.getCurProject().getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
					if(editData.getContractType()!=null){
						filter.getFilterItems().add(new FilterItemInfo("contractType.id", editData.getContractType().getId().toString(), CompareType.EQUALS));
					}
					isAbort=ProgrammingContractFactory.getRemoteInstance().exists(filter);
				}
				switch (i) {
				case 0:
					// 严格控制时
					if (pc != null) {
						if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
							ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
//							if(pc.getEstimateAmount()==null||(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal&&((BigDecimal)cell.getValue()).compareTo(pc.getEstimateAmount())>0)){
//								FDCMsgBox.showWarning(this,"补充合同原币金额超过主合同合约规划的预估金额，不允许提交！");
//								SysUtil.abort();
//							}
							if(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal){
								BigDecimal amount= (BigDecimal) cell.getValue();
								BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
								if(amount.compareTo(balance)>0){
									FDCMsgBox.showWarning(this, "合同签约金额超过关联的合约的规划余额，不允许提交！");
									SysUtil.abort();
								}
//								ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, (BigDecimal) cell.getValue(), false,null);
							}
						}else{
							BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal estimate = (pc.getEstimateAmount()== null?FDCHelper.ZERO:pc.getEstimateAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
							String detail="规划余额："+balance+"\n预估金额："+estimate+"\n占用金额："+onLoadAmount;
							
							if (amount.compareTo(FDCHelper.subtract(FDCHelper.add(balance, estimate), onLoadAmount)) > 0) {
								FDCMsgBox.showDetailAndOK(this, "合同签约金额超过关联的合约的（规划余额+预估金额-占用金额），不允许提交！", detail, 2);
								SysUtil.abort();
							}
						}
					} else if(isAbort){
						FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交！");
						SysUtil.abort();
					}
					break;
				case 1:
					// 提示控制时
					if (pc != null) {
						if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
							ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
//							if(pc.getEstimateAmount()==null||(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal&&((BigDecimal)cell.getValue()).compareTo(pc.getEstimateAmount())>0)){
//								if(FDCMsgBox.showConfirm2(this,"补充合同原币金额超过主合同合约规划的预估金额，请确认是否提交？") == FDCMsgBox.CANCEL){
//									SysUtil.abort();
//								}
//							}
							if(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal){
								BigDecimal amount= (BigDecimal) cell.getValue();
								BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
								if(amount.compareTo(balance)>0){
									if(FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约的规划余额，请确认是否提交？")== FDCMsgBox.CANCEL){
										SysUtil.abort();
									}
								}
//								try {
//									ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, (BigDecimal) cell.getValue(), false,null);
//								} catch (EASBizException e) {
//									if(FDCMsgBox.showConfirm2(this,"金额超过预估合同变动金额，请确认是否提交？") == FDCMsgBox.CANCEL){
//										SysUtil.abort();
//									}
//								}
							}
						}else{
							BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal estimate = (pc.getEstimateAmount()== null?FDCHelper.ZERO:pc.getEstimateAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
							String detail="规划余额："+balance+"\n预估金额:："+estimate+"\n占用金额："+onLoadAmount;
							
							if (amount.compareTo(FDCHelper.subtract(FDCHelper.add(balance, estimate), onLoadAmount)) > 0) {
								if(FDCMsgBox.showConfirm3a(this, "合同签约金额超过关联的合约的（规划余额+预估金额-占用金额），请确认是否提交？",detail)== FDCMsgBox.CANCEL){
									SysUtil.abort();
								}
							}
						}
					} else if(isAbort) {
						FDCMsgBox.showWarning(this, "未关联框架合约，不允许提交！");
						SysUtil.abort();
					}
					break;
				case 2:
					// 不控制时
//					if (pc != null) {
//						if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//							ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
////							if(pc.getEstimateAmount()==null||(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal&&((BigDecimal)cell.getValue()).compareTo(pc.getEstimateAmount())>0)){
////								if(FDCMsgBox.showConfirm2(this,"补充合同原币金额超过主合同合约规划的预估金额，请确认是否提交？") == FDCMsgBox.CANCEL){
////									SysUtil.abort();
////								}
////							}
//							if(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal){
//								try {
//									ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, (BigDecimal) cell.getValue(), false);
//								} catch (EASBizException e) {
//									if(FDCMsgBox.showConfirm2(this,"金额超过预估合同变动金额，请确认是否提交？") == FDCMsgBox.CANCEL){
//										SysUtil.abort();
//									}
//								}
//							}
//						}else{
//							BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
//							BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
//							BigDecimal estimate = (pc.getEstimateAmount()== null?FDCHelper.ZERO:pc.getEstimateAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
//							BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
//							String detail="规划余额："+balance+"\n预估金额："+estimate+"\n占用金额："+onLoadAmount;
//							
//							if (amount.compareTo(FDCHelper.subtract(FDCHelper.add(balance, estimate), onLoadAmount)) > 0) {
//								if(FDCMsgBox.showConfirm3a(this, "合同签约金额超过关联的合约的（规划余额+预估金额-占用金额），请确认是否提交？",detail)== FDCMsgBox.CANCEL){
//									SysUtil.abort();
//								}
//							}
//						}
//					}
					break;
				}
			}
		}
	}
	/**
	 * 补充合同金额 <= 
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isOverEstimateAmount() throws EASBizException, BOSException {
		
		return false;
	}

//	private boolean isNeedCheckContractType(){
//		String  contractTypeNumber = this.editData.getContractType().getLongNumber().toUpperCase();
//		String respDeptNumber = this.editData.getRespDept().getNumber().toUpperCase();
//		respDeptNumber = respDeptNumber.substring(respDeptNumber.lastIndexOf("-") + 1,respDeptNumber.length());
//		if((contractTypeNumber.startsWith("02QQ")||contractTypeNumber.startsWith("03JA")||contractTypeNumber.startsWith("04JC")||contractTypeNumber.startsWith("05GJ"))
//				&& (respDeptNumber.equals("04")||respDeptNumber.equals("05")||respDeptNumber.equals("06")||respDeptNumber.equals("10"))){
//			return true;
//		}
//		return false;
//	}
	private boolean isCheckAmount() {
		Object param = getCtrlParam();
		String paramValue = param == null ? "" : param.toString();
		return true;
	}
	private Object getCtrlParam() {
		String orgPk = this.editData.getOrgUnit().getId().toString();
		IObjectPK orgpk = new ObjectUuidPK(orgPk);
		String param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", orgpk);
		return param == null ? "" : param;
	}	
	
	/**
	 * 启用参数"是否必须与计划任务进行关联"后，合同删除和反审批时，校验该合同下是否存在“合同与任务关联单”
	 * 如果有，则不允许删除和反审批 by cassiel 2010-08-09
	 */
	private void checkConRelatedTaskDelUnAudit() throws BOSException, SQLException{
		ContractBillInfo contract = this.editData;
		if(contract.getId()==null){
			return;
		}
		String contractBillId = this.editData.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID=? group by fid ");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count > 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("合同已被任务关联，不能执行此操作。");
			SysUtil.abort();
		}
		
	}
	
	private void checkBeforeSubmit() {
		if(editData.getEffectiveEndDate()!= null && editData.getEffectiveStartDate() != null){
			if(editData.getEffectiveEndDate().before(editData.getEffectiveStartDate())){
				FDCMsgBox.showError("合同有效截止日期小于开始日期！");
				abort();
			}
		}
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("合同摘要信息大于系统约定长度(255)！");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("录入战略子合同时，战略主合同编码不能为空！");
				abort();
			}
		}
	}

	private void checkProjStatus() {
//		如果项目状态已关闭，则不能选择是否成本拆分 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = null;
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id), selectors);
				
			
			}catch (Exception ex) {
				handUIException(ex);
			}
			
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String transferState = ProjectStatusInfo.transferID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && (projStateId.equals(closedState)||projStateId.equals(transferState))) {
					if(chkCostSplit.isSelected()) {
						MsgBox.showWarning(this, "该合同所在的工程项目已经处于关闭或中转状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交");
						SysUtil.abort();
					}
				}
			}
		}
	}

	private void checkStampMatch() {
		/**
		 * 印花税金额不等于合同金额*印花税率，提示
		 */
		BigDecimal stampTaxAmt = editData.getStampTaxAmt() == null ? FDCHelper.ZERO
				: editData.getStampTaxAmt();
		BigDecimal amount = editData.getAmount() == null ? FDCHelper.ZERO
				: editData.getAmount();
		BigDecimal stampTaxRate = editData.getStampTaxRate() == null ? FDCHelper.ZERO
				: editData.getStampTaxRate();
		stampTaxAmt = stampTaxAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
		if (editData.getStampTaxAmt() != null
				&& editData.getAmount() != null
				&& stampTaxAmt.compareTo(amount.multiply(stampTaxRate).divide(
						FDCConstants.B100, 2, BigDecimal.ROUND_HALF_UP)) != 0) {
			int result = MsgBox.showConfirm2(this, ContractClientUtils
					.getRes("stampNotMatchAmt"));
			if (result == MsgBox.NO || result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * 检查金额
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void checkAmout() throws EASBizException, BOSException {
		String projectId = editData.getCurProject().getId().toString();

		if (editData.isIsCoseSplit()
				&& (FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType) || FDCContractParamUI.RD_CONTROL
						.equalsIgnoreCase(controlType))) {
			if (editData.getAmount() == null) {
				editData.setAmount(FDCConstants.ZERO);
			}
			BigDecimal amiCost = null;
			//获取成本控制金额
			String msg = null;
			if (!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)) {
				amiCost = FDCCostRptFacadeFactory.getRemoteInstance()
						.getAimCost(projectId);
				msg = "目标成本";
			} else {
				amiCost = FDCCostRptFacadeFactory.getRemoteInstance()
						.getDynCost(projectId);
				msg = "动态成本";
			}
			if (amiCost == null)
				amiCost = FDCConstants.ZERO;

			//获取项目合同签约总金额，审核状态的数据
			String id = editData.getId() != null ? editData.getId().toString()
					: null;
			BigDecimal signAmount = ContractFacadeFactory.getRemoteInstance()
					.getProjectAmount(projectId, id);
			if (signAmount == null)
				signAmount = FDCConstants.ZERO;

			if (amiCost.compareTo(signAmount.add(editData.getAmount())) < 0) {
				//提示  严格控制
				FDCMsgBox.showWarning(this, "项目合同签约总金额已经超过了" + msg);
				if (!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)) {
					SysUtil.abort();
				}
			}

		}

	}

	public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception {
		String selectBOID = getSelectBOID();
		if (selectBOID == null) {
			MsgBox.showWarning(this, "合同还未保存，请先保存合同，再维护合同付款计划");
			SysUtil.abort();
		}
		ContractPayPlanEditUI.showEditUI(this, selectBOID, getOprtState());
	}
	
	/**
	 * 检查合同是否关联框架合约
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre() throws Exception {
		String billId = null;
		if(editData.getId() != null && ContractBillFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId()))){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("programmingContract");
			ContractBillInfo conInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()),sic);
			if(conInfo.getProgrammingContract() != null){
				billId = conInfo.getProgrammingContract().getId().toString();
			}
		}
		return billId;
		
	}


	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// 保存前反写所关联的框架合约“是否引用”字段
		updateProgrammingContract(this.editData.getProgrammingContract(),0);
		super.actionSave_actionPerformed(e);
		EcoItemHelper.setPayItemRowBackColor(this.tblEconItem);
		EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		// 保存后反写写所关联的框架合约状态
		updateProgrammingContract(this.editData.getProgrammingContract(),1);
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			btnSubmit.setEnabled(false);
			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
		}
//		prmtModel.setEnabled(true);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// 保存前反写所关联的框架合约“是否引用”字段
		updateProgrammingContract(this.editData.getProgrammingContract(),0);
		super.actionSubmit_actionPerformed(e);
		// 保存后反写写所关联的框架合约状态
		updateProgrammingContract(this.editData.getProgrammingContract(),1);
		if (this.getOprtState().equals("ADDNEW")) {
			this.actionSave.setEnabled(true);
			//R101231-259  合同不能连续录入，新增提交后，选择合同类型不能带出详细信息。 by zhiyuan_tang 2010-01-18
			removeDetailTableRowsOfContractType();
			ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType.getData();
			if (cType != null) {				
				fillDetailByContractType(cType.getId().toString());
			}
			fillDetailByPropert(ContractPropertyEnum.DIRECT);
		}
		handleOldData();
		EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
		EcoItemHelper.setBailRowBackColorWhenInit(tblBail, txtBailOriAmount, txtBailRate);
		
//		if(STATUS_FINDVIEW.equals(this.oprtState) ||STATUS_EDIT.equals(this.oprtState)){
//			try {
//				dealEAS3GAttachment();
//			} catch (BOSException e1) {
//				e1.printStackTrace();
//			} catch (EASBizException e2) {
//				e2.printStackTrace();
//			}
//		}
	}

	/**
	 * 1、总价闭口合同：不需要跳出“预估合同变动”界面；
	 * 2、非闭口合同:当合同类型为：桩基、土石方、土建总包、保温、涂料、面砖、真石漆、窗（幕墙）、精装总包、景观工程
	 * 		且合同金额小于合约规划金额则跳出“预估合同变动”；
	 * 3、补充合同 新增预估合同时合同是主合同。
	 * @throws UIException 
	 */
	private void openContractEstimateChange() throws UIException {
//		this.prmtcontractType.getValue();
		if(!this.chkIsOpen.isSelected()&&islitterAmount()&&ContractPropertyEnum.DIRECT.equals(this.editData.getContractPropert())&&this.editData.getProgrammingContract() != null){//直接合同
			UIContext uiContext = new UIContext(this);
			uiContext.put("contract",this.editData);
			uiContext.put("isFromContract",Boolean.TRUE);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractEstimateChangeEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		} else if( ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())&&islitterAmount()){//补充合同的金额小于预估金额
			UIContext uiContext = new UIContext(this);
			ContractBillInfo contractBillInfo = null;
			ContractBillEntryCollection  entryColl = editData.getEntrys();
			for(int i = 0 ; i < entryColl.size();i++){
				ContractBillEntryInfo entryInfo = entryColl.get(i);
				if(entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)&& entryInfo.getContent() != null
						&& entryInfo.getContent().trim().length() > 0){
					String id = entryInfo.getContent();
					try {
						contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id));
					} catch (Exception e) {
						handUIException(e);
					}
				}
			}
			uiContext.put("contract",contractBillInfo);
			uiContext.put("isFromContract",Boolean.TRUE);
			uiContext.put("suppyContractId",this.editData.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractEstimateChangeEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		}
	}

	/**
	 *
	 *1、合约规划逻辑 签约金额 = 主合同金额 + 补充合同金额
	 *2、是否弹出预估变更单条件： 控制金额 - 签约金额  >这次合同金额 
	 * @return
	 */
	private boolean islitterAmount() {
		BigDecimal amount = this.txtamount.getBigDecimalValue();
		BigDecimal planAmount = FDCHelper.ZERO;
		if(ContractPropertyEnum.DIRECT.equals(this.editData.getContractPropert())){
			planAmount = this.editData.getProgrammingContract().getAmount()!=null?this.editData.getProgrammingContract().getAmount():FDCHelper.ZERO;
			planAmount = FDCHelper.subtract(planAmount, this.editData.getProgrammingContract().getSignUpAmount());
			if(planAmount.compareTo(amount) > 0){
				return true;
			}
		}else if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
			String number = this.editData.getMainContractNumber();
			ContractBillInfo info =null;
			try {
				info = ContractBillFactory.getRemoteInstance().getContractBillInfo("select programmingContract.* where number = '"+number+"' ");
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			planAmount = info!=null?info.getProgrammingContract().getAmount():FDCHelper.ZERO;
			planAmount = FDCHelper.subtract(planAmount, info.getProgrammingContract().getSignUpAmount());
			if(planAmount.compareTo(amount) > 0){
				return true;
			}
		}
		
		return false;
	}

	// 提交时，控制预算余额
	private void checkMbgCtrlBalance() throws BOSException, EASBizException {
		//根据参数是否显示合同费用项目
		if (!isShowCharge || editData.getConChargeType() == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer("");
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(FDCConstants.ContractBill, null, editData);
		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null && balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(bgCtrlResultInfo.getItemName() + "(" + bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(FDCConstants.VoucherResource, "BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString() + "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource, "isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	protected void fillCostPanel() throws Exception {
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
			public void run() {
				try {
					fillCostPanelByAcct();
				} catch (Exception e) {
					handUIException(e);
				}
			}
		});

	}

	ContractCostHelper helper = null;

	//惰性加载costPanel
	protected void fillCostPanelByAcct() throws Exception {

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			return;
		}
		//aimCost hasHappen intendingHappen dynamicCost
		FDCHelper.formatTableNumber(tblCost, "aimCost");
		FDCHelper.formatTableNumber(tblCost, "hasHappen");
		FDCHelper.formatTableNumber(tblCost, "intendingHappen");
		FDCHelper.formatTableNumber(tblCost, "dynamicCost");
		FDCHelper.formatTableNumber(tblCost, "chayi");

		String selectObjId = this.curProject.getId().toString();
		DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory
				.getRemoteInstance().getDynamicCost(selectObjId, null, true);

		if (dynamicCostMap == null) {
			return;
		}

		Map acctMap = new HashMap();
		//获取当前合同的拆分科目			
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid", new Integer(0)));
		view.setFilter(filter);
		ContractCostSplitEntryCollection col = ContractCostSplitEntryFactory.getRemoteInstance()
				.getContractCostSplitEntryCollection(view);
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			ContractCostSplitEntryInfo element = (ContractCostSplitEntryInfo) iter.next();

			String costAcctId = element.getCostAccount().getId().toString();
			if (acctMap.containsKey(costAcctId)) {
				continue;
			}
			acctMap.put(costAcctId, element);
		}

		helper = new ContractCostHelper(tblCost, editData.getId().toString());
		helper.fillCostTable();
	}

	protected void tblCost_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (helper == null)
			return;
		helper.tblCost_tableClicked(this, e);
	}

	/*
	 *  在FDCBillEdit内将其改成抽象方法,以强制要求子类去实现
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return table;
	}

	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */

	public boolean isPrepareInit() {

		return true;
	}

	public boolean isPrepareActionSubmit() {
		//FDCClientVerifyHelper.verifyRequire(this);
		return false;
	}

	public boolean isPrepareActionSave() {
		//FDCClientVerifyHelper.verifyRequire(this);
		return false;
	}

	public RequestContext prepareActionSubmit(IItemAction itemAction)
			throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);

		return request;
	}

	/**
	 * EditUI提供的初始化handler参数方法
	 */
	protected void PrepareHandlerParam(RequestContext request) {
		super.PrepareHandlerParam(request);

	}
	//查看预算余额
	public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		//业务日期默认未设置，如果预算控制时以业务日期控制，会导致查看预算时查不到，手动设置一下
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		ContractBillInfo info = editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.ContractBill, null, info);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		info=null;
	}
	
	/**
	 * 得到调用FilterGetter类"下载附件"和"打开附件"的实例  by Cassiel_peng
	 */
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	/**
	 * 根据业务单据的ID得到该单据所有的关联附件并且将附件名称显示在下拉列表框中  
	 * 因为UI编辑区的"查看附件"和"查看正文"都只是在工作流中用来查看,因此禁止"维护"，故不需要处理权限。  by Cassiel_peng
	 */
	private boolean getAttachmentNamesToShow() throws Exception{
		        String boID = getSelectBOID();
		        boolean hasAttachment=false;
		        if(boID == null)
		        {
		            return hasAttachment;
		        } 
	        	SelectorItemCollection itemColl=new SelectorItemCollection();
	        	itemColl.add(new SelectorItemInfo("id"));
	        	itemColl.add(new SelectorItemInfo("attachment.name"));
	        	itemColl.add(new SelectorItemInfo("attachment.id"));
	        	EntityViewInfo view=new EntityViewInfo();
	        	view.getSelector().addObjectCollection(itemColl);
	        	view.getSorter().add(new SorterItemInfo("attachment.name"));
	        	FilterInfo filter=new FilterInfo();
	        	filter.getFilterItems().add(new FilterItemInfo("boID",getSelectBOID()));
	        	
	        	view.setFilter(filter);
	        	
	        	BoAttchAssoCollection boAttchColl=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
	        	if(boAttchColl!=null&&boAttchColl.size()>0){
	        		comboAttachmentNameList.removeAllItems();
	        		hasAttachment=true;
	        		for(int i=0;i<boAttchColl.size();i++){
	        			AttachmentInfo attachment=(AttachmentInfo)boAttchColl.get(i).getAttachment();
	        			this.comboAttachmentNameList.addItem(attachment);
	        			this.comboAttachmentNameList.setUserObject(attachment);
	        		}
	        	}
	        	return hasAttachment;
	        }
	
	/**
	 * 点击"查看附件"按钮   by Cassiel_peng
	 */
	protected void btnViewAttachment_actionPerformed(ActionEvent e) throws Exception {
		//得到下拉列表框中选中的附件的ID
		getFileGetter();
		Object selectObj=this.comboAttachmentNameList.getSelectedItem();
		if(selectObj!=null){
			String attachId=((AttachmentInfo)selectObj).getId().toString();
//			fileGetter.downloadAttachment(attachId);不需要下载附件
			fileGetter.viewAttachment(attachId);
		}
	};

	public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
		if(isUseWriteMark&&this.editData != null && this.editData.getId() != null&&this.editData instanceof FDCBillInfo){
			//单据在工作流中的情况已经在ContractContentUI加载的时候进行过处理，无需在此处再进行处理 
//			if(FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
//				ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData,STATUS_EDIT,this);
//			}else{
				ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData,getOprtState(),this);
//			}
		}else{
			super.actionViewContent_actionPerformed(e);
			ContractClientUtils.viewContent(this, getSelectBOID());
		}
	}
	protected void getModeByInvite() throws BOSException, EASBizException, IOException{
		if(editData.getProgrammingContract()!=null&&this.prmtcontractType.getValue()!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",editData.getProgrammingContract().getId().toString()));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("parent.id"));
			view.getSelector().add(new SelectorItemInfo("parent.inviteType.*"));
			InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection(view);
			if(col.size()>0){
				this.prmtInviteType.setValue(col.get(0).getParent().getInviteType());
				
				view=new EntityViewInfo();
				filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",col.get(0).getParent().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("contractModel.contractType.id",((ContractTypeInfo)this.prmtcontractType.getValue()).getId().toString()));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("contractModel.*"));
				view.getSelector().add(new SelectorItemInfo("contractModel.contractType.*"));
				InviteDocumentsCollection docCol=InviteDocumentsFactory.getRemoteInstance().getInviteDocumentsCollection(view);
				if(docCol.size()>0){
					this.prmtModel.setValue(null);
					removeDataChangeListener(prmtModel);
					this.prmtModel.setValue(docCol.get(0).getContractModel());
					addDataChangeListener(prmtModel);
					editData.setContractModel(docCol.get(0).getContractModel().getId().toString());
					
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add(new SelectorItemInfo("id"));
					sic.add(new SelectorItemInfo("attachment.*"));
					sic.add(new SelectorItemInfo("assoType"));
					sic.add(new SelectorItemInfo("boID"));
					view=new EntityViewInfo();
					filter=new FilterInfo();
					view.setFilter(filter);
					view.setSelector(sic);
					
					filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",docCol.get(0).getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("boID",docCol.get(0).getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("assoType","标准合同"));
					
					BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
					if(cols.size()>0){
						addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",cols.get(0).getAttachment().getName()+".",FormetFileSize(cols.get(0).getAttachment().getFile().length),cols.get(0).getAttachment().getFile(),null);
					}else{
						EntityViewInfo viewInfo = new EntityViewInfo();
						viewInfo.getSelector().add("id");
						viewInfo.getSelector().add("fileName");
						viewInfo.getSelector().add("version");
						viewInfo.getSelector().add("fileType");
						viewInfo.getSelector().add("createTime");
						viewInfo.getSelector().add("creator.*");
						viewInfo.getSelector().add("contentFile");
						SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
						sorterItemInfo.setSortType(SortType.ASCEND);
						viewInfo.getSorter().add(sorterItemInfo);
						sorterItemInfo = new SorterItemInfo("version");
						sorterItemInfo.setSortType(SortType.DESCEND);
						viewInfo.getSorter().add(sorterItemInfo);
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.appendFilterItem("parent", docCol.get(0).getContractModel().getId().toString());
						viewInfo.setFilter(filterInfo);
						ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
						ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
						if(model!=null){
							String fullname = model.getFileType();
							BigDecimal version = new BigDecimal("1.0");
							ContractContentInfo contentInfo = new ContractContentInfo();
							contentInfo.setVersion(version);
							contentInfo.setContract(editData);
							contentInfo.setFileType(fullname);
							contentInfo.setContentFile(model.getContentFile());
							ContractContentFactory.getRemoteInstance().save(contentInfo);
							
							addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
						}
					}
					this.prmtModel.setEnabled(false);
				}
			}
			fillAttachmnetTable();
		}
	}
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		showProgramContract();
		getModeByInvite();
		loadInvite();
	}
	protected void loadInvite() throws BOSException{
		this.tblInvite.removeRows();
		if(editData.getProgrammingContract()!=null){
			InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection("select parent.id from where programmingContract.id='"+editData.getProgrammingContract().getId().toString()+"'");
			if(col.size()>0){
				EntityViewInfo view = new EntityViewInfo();

				view.getSelector().add(new SelectorItemInfo("*"));
				view.getSelector().add(new SelectorItemInfo("respDept.name"));
				view.getSelector().add(new SelectorItemInfo("creator.name"));
				view.getSelector().add(new SelectorItemInfo("auditor.name"));

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", col.get(0).getParent().getId().toString()));

				view.setFilter(filter);

				TenderAccepterResultCollection tenderCol = TenderAccepterResultFactory.getRemoteInstance().getTenderAccepterResultCollection(view);
				for(int i=0;i<tenderCol.size();i++){
					TenderAccepterResultInfo info = tenderCol.get(i);

					IRow row = this.tblInvite.addRow();

					if (info != null) {
						row.getCell("id").setValue(info.getId());
						row.getCell("number").setValue(info.getNumber());

						if (info.getRespDept() != null) {row.getCell("respDept").setValue(info.getRespDept().getName());
						}
						if (info.getCreator() != null) {
							row.getCell("creator").setValue(info.getCreator().getName());
						}
						row.getCell("createDate").setValue(info.getCreateTime());
						if (info.getAuditor() != null) {
							row.getCell("auditor").setValue(info.getAuditor().getName());
						}
						row.getCell("auditDate").setValue(info.getAuditTime());
						row.getCell("name").setValue(info.getName());
						row.setUserObject(info);
					}
				}
				DirectAccepterResultCollection directCol = DirectAccepterResultFactory.getRemoteInstance().getDirectAccepterResultCollection(view);
				for(int i=0;i<directCol.size();i++){
					DirectAccepterResultInfo info = directCol.get(i);

					IRow row = this.tblInvite.addRow();

					if (info != null) {
						row.getCell("id").setValue(info.getId());
						row.getCell("number").setValue(info.getNumber());

						if (info.getRespDept() != null) {row.getCell("respDept").setValue(info.getRespDept().getName());
						}
						if (info.getCreator() != null) {
							row.getCell("creator").setValue(info.getCreator().getName());
						}
						row.getCell("createDate").setValue(info.getCreateTime());
						if (info.getAuditor() != null) {
							row.getCell("auditor").setValue(info.getAuditor().getName());
						}
						row.getCell("auditDate").setValue(info.getAuditTime());
						row.getCell("name").setValue(info.getName());
						row.setUserObject(info);
					}
				}
			}
		}
	}
	protected void loadSupplyEntry() throws BOSException, SQLException{
		this.kdtSupplyEntry.getStyleAttributes().setLocked(true);
	    this.kdtSupplyEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    this.kdtSupplyEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    this.kdtSupplyEntry.getColumn("localAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    this.kdtSupplyEntry.getColumn("localAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtSupplyEntry.removeRows();
		if(editData.getId()==null||!FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			this.contSrcAmount.setVisible(false);
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select con.fid id,con.fcontractPropert contractPropert,contractType.fname_l2 contractType,con.fnumber number,con.fname name,con.fbookedDate bookedDate,supplier.fname_l2 partB,con.foriginalAmount amount,con.famount localAmount");
		builder.appendSql(" from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid and con.fisAmtWithoutCost=1 and");
		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber and parent.fcurprojectid=con.fcurprojectid");
		builder.appendSql(" left join t_bd_supplier supplier on supplier.fid=con.fpartBId left join T_FDC_ContractType contractType on contractType.fid=con.fcontractTypeId where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
		builder.appendParam(" parent.fid", editData.getId().toString());
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			IRow row=this.kdtSupplyEntry.addRow();
			row.setUserObject(rowSet.getString("id"));
			ContractPropertyEnum contractProperty=ContractPropertyEnum.getEnum(rowSet.getString("contractPropert"));
			if(contractProperty!=null){
				row.getCell("contractPropert").setValue(contractProperty.getAlias());
			}
			row.getCell("contractType").setValue(rowSet.getString("contractType"));
			row.getCell("number").setValue(rowSet.getString("number"));
			row.getCell("name").setValue(rowSet.getString("name"));
			row.getCell("partB").setValue(rowSet.getString("partB"));
			row.getCell("bookedDate").setValue(rowSet.getDate("bookedDate"));
			row.getCell("amount").setValue(rowSet.getBigDecimal("amount"));
			row.getCell("localAmount").setValue(rowSet.getBigDecimal("localAmount"));
		}
		if(rowSet.size()==0){
			this.contSrcAmount.setVisible(false);
		}
	}
	protected void kdtSupplyEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			String id=(String)this.kdtSupplyEntry.getRow(e.getRowIndex()).getUserObject();
			if(id!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
	private void showProgramContract() throws Exception {
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ContractBillInfo contractBillInfo = editData;
		if(this.prmtcontractType.getValue()==null){
			FDCMsgBox.showWarning(this,"请先选择合同类型！");
			return;
		}
		contractBillInfo.setContractType((ContractTypeInfo) this.prmtcontractType.getValue());
		uiContext.put("contractBillInfo", contractBillInfo);
		//是否全期合同
		uiContext.put("isWholeAgeStage", new Boolean(curProject.isIsWholeAgeStage()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			this.textFwContract.setText(contractBillInfo.getProgrammingContract().getName());
		} else {
			this.textFwContract.setText(null);
		}
	}
	
	/**
	 * 实现 IWorkflowUIEnhancement 接口必须实现的方法getWorkflowUIEnhancement by Cassiel_peng 2009-9-21
	 */
	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement(){
			public List getApporveToolButtons(CoreUIObject uiObject) {
				
				List toolButtionsList=new ArrayList();
				btnViewContent.setVisible(true);
				toolButtionsList.add(btnViewContent);
				btnViewCost.setVisible(true);
				toolButtionsList.add(btnViewCost);
				btnProgram.setVisible(true);
				toolButtionsList.add(btnProgram);
				return toolButtionsList;
			}
		};
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData==null || editData.getId()==null){
			return;
		}
		checkMainSupplyCon();
	    if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionAudit_actionPerformed(e);
	}
	private void checkMainSupplyCon() throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select bill.fcontractpropert from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on  bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=entry.fcontent and main.fstate <> '4AUDITTED' ");
	    builder.appendSql("where ");
	    builder.appendParam("bill.fid", editData.getId().toString());
	    builder.appendSql(" and bill.fcontractpropert='SUPPLY' ");
	    builder.appendSql(" and entry.fdetail='对应主合同编码' ");
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()==1){
	    	rs.next();
	    	String prop = rs.getString("fcontractpropert");
	    	if("SUPPLY".equals(prop)){
	    		FDCMsgBox.showWarning(this,"您所选择的数据中存在主合同未审批的补充合同，不能进行此操作!");
		    	this.abort();
	    	}
	    }
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData==null || editData.getId()==null){
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select * from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on  main.fid=entry.fcontent ");
	    builder.appendSql("where");
	    builder.appendParam("main.fid", editData.getId().toString());
	    if(isSupply){
	    	builder.appendSql(" and bill.fstate='4AUDITTED'");
	    }
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this, "您所选择的数据存在被补充合同引用的合同，不能进行此操作!");
			this.abort();
		}
	    
	  //R110603-0148:如果存在变更审批单，则不允许反审批
	    if (ContractUtil.hasChangeAuditBill(null, editData.getId())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasChangeAuditBill"));
			this.abort();
    	}
    	
    	//R110603-0148:如果存在变更指令单，则不允许反审批
    	if (ContractUtil.hasContractChangeBill(null, editData.getId())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasContractChangeBill"));
			this.abort();
    	}
    	
	    if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionUnAudit_actionPerformed(e);
	}
	

	
	/**
	 * 处理R110125-045：合同管理_合同订立_合同录入_新增合同时印花税率无法带入的情况
	 * 重写了抽象类的此方法，只在印花税率等没有被赋值的时候，才给默认值 by zhiyuan_tang
	 */
	protected void applyDefaultValue(IObjectValue vo) {
		if (vo.get("originalAmount") == null) {
			vo.put("originalAmount",new java.math.BigDecimal(0));
		}
		if (vo.get("stampTaxRate") == null) {
			vo.put("stampTaxRate",new java.math.BigDecimal(0));
		}
		if (vo.get("stampTaxAmt") == null) {
			vo.put("stampTaxAmt",new java.math.BigDecimal(0));
		}
	}
	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}
	  
	/**
	 * 需求来源：R100806-236合同录入、变更、结算单据界面增加审批按钮
	 * <P>
	 * 为实现此BT需求，特重写以下方法
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	private void updateProgrammingContract(ProgrammingContractInfo pc, int isCiting) {
		try {
			String oldPCId=null;
			FDCSQLBuilder buildSQL = new FDCSQLBuilder();
			IRowSet iRowSet = null;
			if(this.editData.getId()!=null){
				buildSQL.appendSql("select fprogrammingContract from t_con_contractBill where fid='" + this.editData.getId().toString() + "'");
				iRowSet = buildSQL.executeQuery();
				while (iRowSet.next()) {
					oldPCId=iRowSet.getString("fprogrammingContract");
				}
			}
			if(isCiting==0){
				if(oldPCId==null||(pc!=null&&oldPCId.equals(pc.getId().toString()))){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("select count(*) count from t_con_contractBill where fprogrammingContract='" + oldPCId + "' and fid!='" + this.editData.getId().toString() + "'");
				int count = 0;
				iRowSet = buildSQL.executeQuery();
				if (iRowSet.next()) {
					count = iRowSet.getInt("count");
				}
				if(count>0){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " where FID = '" + oldPCId + "'");
				buildSQL.executeUpdate();
			}else if(pc!=null){
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " where FID = '" + pc.getId().toString() + "'");
				buildSQL.executeUpdate();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//add by david_yang PT043562 2011.03.29
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	
	public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}

		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", "1",CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", null,CompareType.EQUALS));
//			//添加补充协议
//			filter.getFilterItems().add(new FilterItemInfo("boID", this.editData.getAgreementID()));
//			
//			filter.getFilterItems().add(new FilterItemInfo("assoType","标准合同",CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2)");
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
//					if(boId.equals(boaInfo.getBoID())){
//						AttachmentInfo attachment = boaInfo.getAttachment();
//							IRow row = tblAttachement.addRow();
//							row.getCell("id").setValue(attachment.getId().toString());
//							row.getCell("seq").setValue(attachment.getAttachID());
//							row.getCell("name").setValue(attachment.getName());
//							row.getCell("date").setValue(attachment.getCreateTime());
//							row.getCell("isAgreement").setValue(Boolean.FALSE);
//					}else{
						AttachmentInfo attachment = boaInfo.getAttachment();
						IRow row = tblAttachement.addRow();
						row.getCell("id").setValue(attachment.getId().toString());
						row.getCell("seq").setValue(attachment.getAttachID());
						row.getCell("name").setValue(attachment.getName());
						row.getCell("date").setValue(attachment.getCreateTime());
						row.getCell("type").setValue(boaInfo.getAssoType());
						if((attachment.getBeizhu()==null||!attachment.getBeizhu().equals("1"))&&(boaInfo.getAssoType()!=null&&boaInfo.getAssoType().equals("标准合同"))){
							flag=true;
						}else if(boaInfo.getAssoType()!=null&&boaInfo.getAssoType().equals("补充协议")){
							flag =false;
						}
//					}
				}
				this.isHasAttchment = true;
			}else {
				this.isHasAttchment = false;
			}
			if(flag){
				if(this.prmtModel.getValue()!=null){
					this.chkIsStandardContract.setSelected(true);
				}
			} else {
				this.chkIsStandardContract.setSelected(false);
			}
		}
	}
	
	protected void tblAttachement_tableClicked(KDTMouseEvent e)
			throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
        {
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
			
        }
	}
	private boolean ctrlAgreementBtnStatus() throws EASBizException, BOSException{
		boolean isEdit = false;
		
	    if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
	    {
	        isEdit = true;
	    }
	    
	    if(isEdit){
	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
	        String creatorId=this.editData.getCreator()!=null?this.editData.getCreator().getId().toString():null;
			String uiName = ContractBillEditUI.class.getName();
			boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(userId),
    				new ObjectUuidPK(orgId),
    				new MetaDataPK(uiName),
    				new MetaDataPK("ActionAttamentCtrl") );
			//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
        	if(hasFunctionPermission){
        		//creatorId ==null; 为新增,不做控制
        		if(creatorId!=null){
        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
        			if(creatorCtrl&&!creatorId.equals(userId)){
        				//制单人要等于当前用户才行
        				isEdit=false;
        			}
        		}
        	}else{
        		isEdit=false;
        	}
        }
	    return isEdit;
	 }
	/**
	 *  //针对EAS-3G平台上看不到标准合同，与补充协议做改造
	 * @throws BOSException
	 * @throws EASBizException 
	 */
//	private void dealEAS3GAttachment() throws BOSException, EASBizException{String billId = this.editData.getId().toString();
//	IBoAttchAsso boFacad = BoAttchAssoFactory.getRemoteInstance();
//	IAttachment attachmentFacad = AttachmentFactory.getRemoteInstance();
//	FilterInfo filter1 = new FilterInfo();
//	filter1 = new FilterInfo();
//	//再次提交的时候：如果存在上次新增的合同正文，补充协议需要先删除，然后再次新增
//	filter1.getFilterItems().add(new FilterItemInfo("attachID",billId));
//	attachmentFacad.delete(filter1);
//	//合同正文(标准合同)：获取最大版本，新增一条附件
//	EntityViewInfo view1 = new EntityViewInfo();
//	filter1 = new FilterInfo();
//	filter1.getFilterItems().add(new FilterItemInfo("contract.id",this.editData.getId()));
//	SorterItemCollection sort = new SorterItemCollection();
//	SorterItemInfo sortItem = new SorterItemInfo("version");
//	sortItem.setSortType(SortType.DESCEND);
//	sort.add(sortItem);
//	view1.setFilter(filter1);
//	view1.setSorter(sort);
//	ContractContentCollection conColl = ContractContentFactory.getRemoteInstance().getContractContentCollection(view1);
//	ContractContentInfo conInfo = null;
//	if(conColl.size() > 0 ) {
//		 conInfo = conColl.get(0);
//		 AttachmentInfo info = new AttachmentInfo();
//		 info.setType("Microsoft Word 文档");
//		 info.setIsShared(false);
//		 info.setName(conInfo.getFileType());
//		 info.setFile(conInfo.getContentFile());
//		 info.setSimpleName("doc");
//		 info.setAttachID(billId);
//		 BoAttchAssoInfo boInfo = new BoAttchAssoInfo();
//		 boInfo.setBoID(billId);
//		 boInfo.setAttachment(info);
//		 info.getBoAttchAsso().clear();
//		 info.getBoAttchAsso().add(boInfo);
//		 attachmentFacad.addnew(info);
//	}
//	
//	//补充协议：直接新增一条附件的分录，boid = editData.id
//		EntityViewInfo view = new EntityViewInfo();
//		SelectorItemCollection sel = new SelectorItemCollection();
//		sel.add("*");
//		sel.add("attachment.*");
//		view.setSelector(sel);
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getAgreementID()));
//		view.setFilter(filter);
//		BoAttchAssoCollection attColl = boFacad.getBoAttchAssoCollection(view);
//		for(int i = 0 ; i < attColl.size(); i ++){
//			BoAttchAssoInfo attInfo = attColl.get(i);
//			attInfo = (BoAttchAssoInfo)attInfo.clone();
//			attInfo.setId(BOSUuid.create(attInfo.getBOSType()));
//			attInfo.setBoID(billId);
//			AttachmentInfo info = (AttachmentInfo)attInfo.getAttachment().clone();
//			info.getBoAttchAsso().clear();
//			info.getBoAttchAsso().add(attInfo);
//			info.setAttachID(billId);
//			info.setId(null);
//			attInfo.setAttachment(info);
//			attachmentFacad.addnew(info);
//		}
//	}
	protected void tblInvite_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.tblInvite.getRow(e.getRowIndex());
			if(row.getUserObject()==null) return;
			String editUI=null;
			if(row.getUserObject() instanceof TenderAccepterResultInfo){
				editUI=TenderAccepterResultEditUI.class.getName();
			}else{
				editUI=DirectAccepterResultEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell("id").getValue().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUI, uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtModel.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择标准合同！");
			return;
		}
		String id=null;
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.id"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","标准合同"));
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			sic=new SelectorItemCollection();
			sic.add(new SelectorItemInfo("fileType"));
			sic.add(new SelectorItemInfo("contentFile"));
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select top 1 fid from T_CON_ContractContent where fcontractid='"+this.editData.getId().toString()+"' order by fversion desc");
			final IRowSet rowSet = _builder.executeQuery();
			if(rowSet.size()>0){
				while (rowSet.next()) {
					ContractContentInfo info=ContractContentFactory.getRemoteInstance().getContractContentInfo(new ObjectUuidPK(rowSet.getString("fid")),sic);
					id=addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",info.getFileType(),FormetFileSize(info.getContentFile().length),info.getContentFile(),"1");
				}
			}else{
				ContractModelInfo contractModel=(ContractModelInfo) this.prmtModel.getValue();
				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.getSelector().add("id");
				viewInfo.getSelector().add("fileName");
				viewInfo.getSelector().add("version");
				viewInfo.getSelector().add("fileType");
				viewInfo.getSelector().add("createTime");
				viewInfo.getSelector().add("creator.*");
				viewInfo.getSelector().add("contentFile");
				SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
				sorterItemInfo.setSortType(SortType.ASCEND);
				viewInfo.getSorter().add(sorterItemInfo);
				sorterItemInfo = new SorterItemInfo("version");
				sorterItemInfo.setSortType(SortType.DESCEND);
				viewInfo.getSorter().add(sorterItemInfo);
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.appendFilterItem("parent", contractModel.getId().toString());
				viewInfo.setFilter(filterInfo);
				ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
				ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
				if(model!=null){
					String fullname = model.getFileType();
					editData.setContractModel(contractModel.getId().toString());
					SelectorItemCollection sel = new SelectorItemCollection();
					sel.add("contractModel");
					BigDecimal version = new BigDecimal("1.0");
					ContractContentInfo contentInfo = new ContractContentInfo();
					contentInfo.setVersion(version);
					contentInfo.setContract(editData);
					contentInfo.setFileType(fullname);
					contentInfo.setContentFile(model.getContentFile());
					ContractContentFactory.getRemoteInstance().save(contentInfo);
					
					addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
				}
			}
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"标准合同存在多个！");
			return;
		}else{
			id=cols.get(0).getAttachment().getId().toString(); 
		}
		String file=getFileGetter().downloadAttachment(id, this);
		if(file!=null&&!"".equals(file)){
			FDCMsgBox.showInfo(this,"标准合同下载成功！");
		}
	}

	public void actionUpLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtModel.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择标准合同！");
			return;
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.file"));
		sic.add(new SelectorItemInfo("attachment.name"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","标准合同"));
		
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			FDCMsgBox.showInfo(this,"无标准合同！");
			return;
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"标准合同存在多个！");
			return;
		}
		
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		FileFilter ff = new FileFilter(){
            public boolean accept(File pathname) {
                if (pathname.isFile()&&
                		pathname.getName().toUpperCase().endsWith(".DOC")) {
                    return true;
                }
                return false;
            }
			public String getDescription() {
				return ".DOC";
			}};
		fc.setFileFilter(ff);
		int retVal = fc.showOpenDialog(this);
		if(retVal == 0){
			File retFile=fc.getSelectedFile();
			if(retFile!=null){
				if(!retFile.exists()){
					FDCMsgBox.showInfo(this,Resrcs.getString("FileNotExisted"));
		        }else if(retFile!=null&&retFile.length() > 52428800L){
		        	FDCMsgBox.showInfo(this,Resrcs.getString("FileSizeNotAllowed"));
		        }else{
		        	File file = File.createTempFile("temp"+cols.get(0).getAttachment().getName(), ".DOC");
		    		FileOutputStream fos = new FileOutputStream(file);
		    		fos.write(cols.get(0).getAttachment().getFile());
		    		fos.close();
		    		
		    		FileInputStream in = new FileInputStream(file);
		    		FileInputStream newIn = new FileInputStream(retFile);
		    		try {
						WordExtractor extractor = new WordExtractor(in);
						String instr = extractor.getParagraphText()[0];
						if(instr.indexOf("\r\n")>0){
							instr=instr.split("\r\n")[0];
						}
						if(instr.indexOf("\r")>0){
							instr=instr.split("\r")[0];
						}
						in.close();
						
						WordExtractor newExtractor = new WordExtractor(newIn);
						String newInstr = newExtractor.getParagraphText()[0];
						if(newInstr.indexOf("\r\n")>0){
							newInstr=newInstr.split("\r\n")[0];
						}
						if(newInstr.indexOf("\r")>0){
							newInstr=newInstr.split("\r")[0];
						}
						newIn.close();
						
						if(!newInstr.equals(instr)){
			        		FDCMsgBox.showInfo(this,"标准合同版本不一致！");
			        		return;
			        	}
					} catch (Exception e2) {
						FDCMsgBox.showInfo(this,"请使用标准合同的Microsoft Word版本！");
		        		return;
					}finally{
						in.close();
						newIn.close();
					}
		        	
		        	long len = retFile.length();
		    		byte[] bytes = new byte[(int)len];
		    		
		    		FileInputStream fi=new FileInputStream(retFile);
		    		BufferedInputStream bi=new BufferedInputStream(fi);
		    		int size=fi.available();
		    		bi.read(bytes);
		    		deleteTypeAttachment(this.editData.getId().toString(),"标准合同");
		    		addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",retFile.getName(),FormetFileSize(size),bytes,null);
		    		bi.close();
		    		fi.close();
		    		
		    		FDCMsgBox.showInfo(this,"标准合同上传成功！");
		    		fillAttachmnetTable();
		        }
			}
		}
	}
	public void actionAgreementText_actionPerformed(ActionEvent e)throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        }
        if(this.editData.getAgreementID()!=null){
			acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
        }else{
        	this.editData.setAgreementID(BOSUuid.create(this.editData.getBOSType()).toString());
        	acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
        }
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getAgreementID()));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("attachment.size");
		sel.add("attachment.file");
		sel.add("attachment.name");
		view.setSelector(sel);
		view.setFilter(filter);
		BoAttchAssoCollection attColl = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		deleteTypeAttachment(this.editData.getId().toString(),"补充协议");
		for(int i = 0 ; i < attColl.size(); i ++){
	    	addModeAttachmentInfo(this.editData.getId().toString(),"补充协议",attColl.get(i).getAttachment().getName(),attColl.get(i).getAttachment().getSize(),attColl.get(i).getAttachment().getFile(),null);
		}
        fillAttachmnetTable();
	}
	private String addModeAttachmentInfo(String boId,String type,String fullname,String size,byte[] bytes,String beizhu) throws EASBizException, BOSException, IOException{
		AttachmentInfo info = new AttachmentInfo();
		info.setType("Microsoft Word 文档");
		info.setIsShared(false);
		if(fullname.indexOf(".")>0){
			info.setName(fullname.substring(0,fullname.lastIndexOf(".")));
		}else{
			info.setName(fullname);
		}
		info.setFile(bytes);
		info.setSize(size);
		info.setSimpleName("doc");
		info.setAttachID(boId);
		info.setBeizhu(beizhu);
		info.setDescription(type+"请勿删除！");
		BoAttchAssoInfo boInfo = new BoAttchAssoInfo();
		boInfo.setBoID(boId);
		boInfo.setAssoType(type);
		boInfo.setAttachment(info);
		info.getBoAttchAsso().clear();
		info.getBoAttchAsso().add(boInfo);
		IObjectPK pk=AttachmentFactory.getRemoteInstance().addnew(info);
		return pk.toString();
	}
	public String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	private void deleteContractContent() throws BOSException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" delete from T_CON_ContractContent where fcontractid='"+this.editData.getId().toString()+"'");
		_builder.executeUpdate();
	}
	public boolean destroyWindow() {
		//如果没有保存单据，需要把新增进去的合同范本删除
		boolean b = super.destroyWindow();
        if(b){
        	try {
    			IContractBill contractBillFacade = null;
    			contractBillFacade = ContractBillFactory.getRemoteInstance();
    			if(!contractBillFacade.exists(new ObjectUuidPK(this.editData.getId().toString()))){
    				deleteContractContent();
    				
    				if(this.editData.getAgreementID()!=null){
    					deleteAttachment(this.editData.getAgreementID());
    				}
    				deleteAttachment(this.editData.getId().toString());
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			logger.error(e);
    		}
        }
        return b;
	}
	protected void deleteTypeAttachment(String id,String type) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		filter.getFilterItems().add(new FilterItemInfo("assoType",type));
		
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			FilterInfo deleteFilter = new FilterInfo();
			deleteFilter.getFilterItems().add(new FilterItemInfo("id" , col.get(i).getAttachment().getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(deleteFilter);
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			EntityViewInfo attview=new EntityViewInfo();
			FilterInfo attfilter = new FilterInfo();
			
			attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
			attview.setFilter(attfilter);
			BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
			if(attcol.size()==1){
				BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
			}else if(attcol.size()>1){
				BoAttchAssoFactory.getRemoteInstance().delete(filter);
			}
		}
	}
}
