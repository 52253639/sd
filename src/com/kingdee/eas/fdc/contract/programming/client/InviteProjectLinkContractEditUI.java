/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;

/**
 * output class name
 */
public class InviteProjectLinkContractEditUI extends AbstractInviteProjectLinkContractEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteProjectLinkContractEditUI.class);

	private CurProjectInfo project = null;
	private ProgrammingContractInfo pcInfo = null;
	private ProgrammingContractInfo oldPcInfo = null;
	private InviteProjectInfo ipInfo = null;

    /**
     * output class constructor
     */
    public InviteProjectLinkContractEditUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		init();
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
					prmtContract.setValue(pcInfo);
					txtAmount.setNumberValue(pcInfo.getAmount());
					txtControlAmount.setNumberValue(pcInfo.getControlAmount());
					txtControlBalance.setNumberValue(pcInfo.getControlBalance());
				}
			}
		});
	}

	/**
	 * 关联有框架合约时，在查看或编辑时把数据写入
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void init() throws EASBizException, BOSException {
		Map uiContext = this.getUIContext();
		project = (CurProjectInfo) uiContext.get("project");
		pcInfo = (ProgrammingContractInfo) uiContext.get("programmingContract");
		oldPcInfo = pcInfo;
		setTextFormat(txtAmount);
		setTextFormat(txtControlAmount);
		setTextFormat(txtControlBalance);
		this.btnConfirm.setEnabled(true);
		this.btnCancel.setEnabled(true);
		this.prmtContract.setEnabled(true);

		F7ProgrammingContract();

		if (pcInfo != null) {
			this.prmtContract.setData(pcInfo);
			this.txtAmount.setNumberValue(pcInfo.getAmount());
			this.txtControlAmount.setNumberValue(pcInfo.getControlAmount());
			this.txtControlBalance.setNumberValue(pcInfo.getControlBalance());
		}
	}

	/**
	 * 框架合约F7
	 */
	private void F7ProgrammingContract() {
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		String pro = project.getId().toString();
		Set set = new HashSet();
		set.add(pro);
		filter.getFilterItems().add(new FilterItemInfo("project.id", set, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
		entityView.setFilter(filter);
		prmtContract.setEntityViewInfo(entityView);
		ProgrammingContractPromptBox selector = new ProgrammingContractPromptBox(this);
		prmtContract.setEnabledMultiSelection(false);
		prmtContract.setQueryInfo(null);
		prmtContract.setEditFormat("$longNumber$");
		prmtContract.setDisplayFormat("$name$");
		prmtContract.setCommitFormat("$longNumber$");
		prmtContract.setSelector(selector);
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
	 * 确定（保存数据）
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		pcInfo = (ProgrammingContractInfo) this.prmtContract.getData();
		getUIContext().put("programmingContract", pcInfo);
		getUIContext().put("oldPcInfo", oldPcInfo);
		destroyWindow();
	}

	/**
	 * 取消
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}

}