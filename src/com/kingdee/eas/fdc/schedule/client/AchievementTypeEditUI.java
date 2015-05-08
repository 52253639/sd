/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.AchievementProfessionInfo;
import com.kingdee.eas.fdc.schedule.AchievementStageInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeFactory;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.LbFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;


/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述：
 * 
 * @author 李兵
 * @version EAS7.0
 * @createDate 2011-8-12
 * @see
 */
public class AchievementTypeEditUI extends AbstractAchievementTypeEditUI
{
	/**
	 *  编码最大长度放开到9999，added by Owen_wen 2013-8-26
	 */
	private static final int MaxNumberLength = 4;
	private static final Logger logger = CoreUIObject.getLogger(AchievementTypeEditUI.class);
    
    public AchievementTypeEditUI() throws Exception
    {
        super();
    }

    protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return kDBizMultiLangBox1;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		AchievementTypeInfo info = new AchievementTypeInfo();
		String maxNumber = null;
		try {
			// 得到最大编码
			maxNumber = LbFacadeFactory.getRemoteInstance().getBiggestNumber();
		} catch (Exception e) {
			this.handUIExceptionAndAbort(e);
		}
		String number = (Integer.parseInt(maxNumber) + 1) + "";
		if (number.length() > MaxNumberLength) { 
			FDCMsgBox.showInfo("编码已达到最大长度，不能新增");
			abort();
		}
		if (number.length() == 1) {
			number = "0" + number;
		}
		info.setNumber(number);
		AchievementStageInfo stage = (AchievementStageInfo) getUIContext().get("stage");
		AchievementProfessionInfo profession = (AchievementProfessionInfo) getUIContext().get("profession");
		if (stage != null) {
			info.setStage(stage);
		}
		if (profession != null) {
			info.setProfession(profession);
		}
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AchievementTypeFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		// 检查操作组织是否是成本中心
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}
		super.onLoad();
		this.windowTitle = "阶段性成果类别设置编辑界面";
		if (OprtState.VIEW.equals(getOprtState())) {
			this.windowTitle = "阶段性成果类别设置查看界面";
		}
		// 初始化几个F7
		initF7();
		// 屏蔽禁用，启用，删除按钮
		this.btnCancel.setEnabled(false);
		this.btnCancel.setVisible(false);
		this.btnRemove.setEnabled(false);
		this.btnRemove.setVisible(false);
		this.btnCancelCancel.setEnabled(false);
		this.btnCancelCancel.setVisible(false);
	}

	private void initF7() {
		// 成果阶段
		prmtStage.setQueryInfo("com.kingdee.eas.fdc.schedule.app.AchievementStageQuery");
		prmtStage.setDisplayFormat("$name$");
		prmtStage.setCommitFormat("$name$ $number$");
		prmtStage.setEditable(true);
		prmtStage.setRequired(true);
		// 成果专业
		prmtProfession.setQueryInfo("com.kingdee.eas.fdc.schedule.app.AchievementProfessionQuery");
		// 鼠标拿开显示的是什么
		prmtProfession.setDisplayFormat("$name$");
		prmtProfession.setCommitFormat("$name$ $number$");
		prmtProfession.setEditable(true);
		prmtProfession.setRequired(true);

		// 归档目录
		prmtDocDirectory.setQueryInfo("com.kingdee.eas.fdc.schedule.app.Copy_CategoryQuery");
		prmtDocDirectory.setDisplayFormat("$docArea.name$ / $name$");
		prmtDocDirectory.setCommitFormat("$name$ $number$");
		prmtDocDirectory.setEditable(true);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (FDCHelper.isEmpty(editData.getName())) {
			FDCMsgBox.showInfo("成果类别名称不能为空");
			SysUtil.abort();
		}
		// 判断成果阶段不能为空
		if (prmtStage.getValue() == null) {
			FDCMsgBox.showInfo("成果阶段不能为空！");
			SysUtil.abort();
		}
		// 判断成果专业不能为空
		if (prmtProfession.getValue() == null) {
			FDCMsgBox.showInfo("成果专业不能为空！");
			SysUtil.abort();
		}
		String num = this.txtNumber.getText();
		if (FDCHelper.isEmpty(num)) {
			FDCMsgBox.showInfo("成果类别编码不能为空");
			SysUtil.abort();
		}
		boolean flag = num.matches("\\d+");
		// 判断输入的编码
		
		if (flag == false) {
			FDCMsgBox.showInfo("只能输入数值型编码,例如08");
			SysUtil.abort();
		}
		if (num.length() > MaxNumberLength) {
			FDCMsgBox.showInfo("编码不能长于2位");
			SysUtil.abort();
		} else {
			if (num.length() == 1) {
				num = "0" + num;
			}
		}
		
	}
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("docDirectory.docArea.*"));
		return sic;
    }
}