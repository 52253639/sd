/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ContractBillLinkProgContEditUI extends AbstractContractBillLinkProgContEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillLinkProgContEditUI.class);
	private ProgrammingContractInfo pcInfo = null;
	private ContractBillInfo contractBillInfo = null;
	private ContractWithoutTextInfo contractWithoutTextInfo = null;
	private boolean flag = false;
    
    /**
     * output class constructor
     */
    public ContractBillLinkProgContEditUI() throws Exception
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
    public void onLoad() throws Exception {
    	super.onLoad();
    	init();
    	Map uiContext = this.getUIContext();
    	Boolean isWholeAgeStage = (Boolean) uiContext.get("isWholeAgeStage");
    	if(isWholeAgeStage.booleanValue()){
    		ProgrammingContractForWholeAgeStageBox selectorBox = new ProgrammingContractForWholeAgeStageBox(this);
    		prmtContract.setSelector(selectorBox);
    	}else{
    		ProgrammingContractPromptBox selector=new ProgrammingContractPromptBox(this);
    		prmtContract.setSelector(selector);
    	}
		prmtContract.setEnabledMultiSelection(false);
		prmtContract.setQueryInfo(null);
		prmtContract.setEditFormat("$longNumber$");
		prmtContract.setDisplayFormat("$name$");
		prmtContract.setCommitFormat("$longNumber$");
		

		this.prmtContract.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				Object newValue = eventObj.getNewValue();
				if (newValue == null) {
					prmtContract.setValue(null);
					txtAmount.setNumberValue(null);
					txtControlAmount.setNumberValue(null);
					txtControlBalance.setNumberValue(null);
					return;
				}
				if (newValue instanceof ProgrammingContractInfo) {
					ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) newValue;
					//如果合约框架被引用，此合约不是最新的目标成本的修订版本，则不能被再次使用 20110327 wangxin
//					if(isNotLastestVersion(pcInfo)){
//						FDCMsgBox.showWarning("此合约规划未关联最新的目标成本，请重新修订合约规划！");
//						prmtContract.setValue(null);
//					}else{
						if(contractBillInfo!=null){
							if(isUsed(pcInfo)){
								FDCMsgBox.showWarning("合约规划已经被其他合同关联，请选择其他合约规划！");
								prmtContract.setValue(null);
							}
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
							try {
								if(ContractPCSplitBillEntryFactory.getRemoteInstance().exists(filter)){
									FDCMsgBox.showWarning("合约规划已经被其他跨期合同关联，请选择其他合约规划！");
									prmtContract.setValue(null);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							prmtContract.setValue(pcInfo);
							txtAmount.setNumberValue(pcInfo.getAmount());
							txtControlAmount.setNumberValue(pcInfo.getControlAmount());
							txtControlBalance.setNumberValue(pcInfo.getControlBalance());
						}
//					}
				}
			}

			private boolean isNotLastestVersion(ProgrammingContractInfo pcInfo) {
				SelectorItemCollection sel= new SelectorItemCollection();
				sel.add("programming.aimCost.*");
				sel.add("programming.project.*");
				ProgrammingContractInfo progammingInfo = null;
				try {
					progammingInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pcInfo.getId().toString()),sel);
				} catch (EASBizException e1) {
					e1.printStackTrace();
					logger.equals(e1);
				} catch (BOSException e1) {
					e1.printStackTrace();
					logger.equals(e1);
				}
				AimCostInfo aimcost = progammingInfo.getProgramming().getAimCost();
				CurProjectInfo project = progammingInfo.getProgramming().getProject();
				EntityViewInfo entityView = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("orgOrProId",project.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
				entityView.setFilter(filter);
				AimCostCollection aimCostColl = null;
				try {
					aimCostColl = AimCostFactory.getRemoteInstance().getAimCostCollection(entityView);
				} catch (BOSException e) {
					e.printStackTrace();
					logger.equals(e);
				}
				AimCostInfo newAimcost = null;
				if(aimCostColl.size()>0){
					newAimcost = aimCostColl.get(0);
					if(!aimcost.getId().toString().equals(newAimcost.getId().toString())){
						return true;
					}
				}
				
				return false;
			}

			private boolean isUsed(ProgrammingContractInfo pcInfo) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("contractPropert",ContractPropertyEnum.SUPPLY_VALUE,CompareType.NOTEQUALS));
				if(contractBillInfo.getId()!=null){
					filter.getFilterItems().add(new FilterItemInfo("id",contractBillInfo.getId().toString(),CompareType.NOTEQUALS));
				}
				try {
					if(ContractBillFactory.getRemoteInstance().exists(filter)){
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		
		Boolean isDisplay=true;
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISDISPLAYPCAMOUNT", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISDISPLAYPCAMOUNT")!=null){
				isDisplay=Boolean.parseBoolean(hmAllParam.get("FDC_ISDISPLAYPCAMOUNT").toString());
			}else{
				isDisplay=true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.kDLabelContainer2.setVisible(isDisplay);
    }
	/**
	 * 关联有框架合约时，在查看或编辑时把数据写入
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void init() throws EASBizException, BOSException {
		Map uiContext = this.getUIContext();
		contractBillInfo = (ContractBillInfo) uiContext.get("contractBillInfo");
		contractWithoutTextInfo = (ContractWithoutTextInfo) uiContext.get("contractWithoutTextInfo");
		setTextFormat(txtAmount);
		setTextFormat(txtControlAmount);
		setTextFormat(txtControlBalance);
		CurProjectInfo curProject = null;
		if(contractBillInfo!=null){
			curProject = contractBillInfo.getCurProject();
			flag = true;
		}
		if(contractWithoutTextInfo !=null){
			curProject = contractWithoutTextInfo.getCurProject();
		}
		if (curProject!= null) {
			EntityViewInfo entityView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			String pro = curProject.getId().toString();
			Set set = new HashSet();
			set.add(pro);
			filter.getFilterItems().add(new FilterItemInfo("project.id", set, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			if(contractBillInfo!=null){
				if(contractBillInfo.getContractType()!=null){
					filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractBillInfo.getContractType().getId().toString(), CompareType.EQUALS));
				}
			}else{
				if(contractWithoutTextInfo.getContractType()!=null){
					filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractWithoutTextInfo.getContractType().getId().toString(), CompareType.EQUALS));
				}
			}
			entityView.setFilter(filter);
			prmtContract.setEntityViewInfo(entityView);
		}

		if (this.getOprtState().equals(OprtState.ADDNEW) || this.getOprtState().equals(OprtState.EDIT)) {
			this.btnConfirm.setEnabled(true);
			this.btnCancel.setEnabled(true);
			this.prmtContract.setEnabled(true);
		}
		if (contractBillInfo !=null && contractBillInfo.getProgrammingContract() != null) {
			ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
			if (pcInfo != null) {
				this.prmtContract.setValue(pcInfo);
				this.txtAmount.setNumberValue(pcInfo.getAmount());
				this.txtControlAmount.setNumberValue(pcInfo.getControlAmount());
				this.txtControlBalance.setNumberValue(pcInfo.getControlBalance());
			}
		}
		
		if (contractWithoutTextInfo !=null &&  contractWithoutTextInfo.getProgrammingContract() != null) {
			ProgrammingContractInfo pcInfo = contractWithoutTextInfo.getProgrammingContract();
			if (pcInfo != null) {
				this.prmtContract.setValue(pcInfo);
				this.txtAmount.setNumberValue(pcInfo.getAmount());
				this.txtControlAmount.setNumberValue(pcInfo.getControlAmount());
				this.txtControlBalance.setNumberValue(pcInfo.getControlBalance());
			}
		}
	}
	
	/**
	 * 初始化KDFormattedTextField的相关基础属性txtControlBalance
	 * */
	private static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}
    /**
     * output actionConfirm_actionPerformed
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	pcInfo = (ProgrammingContractInfo) this.prmtContract.getValue();
    	BigDecimal amount = FDCHelper.ZERO;
    	if(flag){
    		contractBillInfo.setProgrammingContract(pcInfo);
    		amount = contractBillInfo.getAmount();
    	} else {
    		contractWithoutTextInfo.setProgrammingContract(pcInfo);
    		amount = contractWithoutTextInfo.getAmount();
    	}
		destroyWindow();
    }


	/**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	getUIContext().put("cancel", "cancel");
    	destroyWindow();
    }

}