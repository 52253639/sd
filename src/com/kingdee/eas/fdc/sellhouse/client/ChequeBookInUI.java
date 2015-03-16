/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CodeGenerater;
import com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChequeBookInUI extends AbstractChequeBookInUI {
	private static final Logger logger = CoreUIObject
	.getLogger(ChequeBookInUI.class);

	//	private CompanyOrgUnitInfo cur = null;

	public ChequeBookInUI() throws Exception {
		super();
		//		cur = ContextHelperFactory.getRemoteInstance().getCurrentCompany();
		//		// 当前公司为虚体时，提示用户“请切换实体公司！”
		//		if (!cur.isIsBizUnit()) {
		//			MsgBox.showInfo(this, "请切换至实体公司！");
		//			this.abort();
		//		}
		initHeadData();
		// 换掉说明文本框中的回车换行符
		String changeLine = this.txtRemark.getText().replaceAll("\\\\r", "\r")
		.replaceAll("\\\\n", "\n");
		this.txtRemark.setText(changeLine);
	}

	private void initHeadData() throws Exception {
		// 设置起始流水号下限，张数下限
		SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, 999999, 1);
		this.spinStartNum.setModel(sm);
		SpinnerNumberModel count = new SpinnerNumberModel(1, 1, 999999, 1);
		this.spinCount.setModel(count);

		this.comboChequeType.setRequired(true);
		this.f7Currency.setRequired(true);
		this.spinCount.setRequired(true);
		this.spinStartNum.setRequired(true);
		this.txtCodeRule.setRequired(true);
		this.f7Keeper.setRequired(true);
		this.f7KeepOrgUnit.setEnabled(false);
		this.f7Creator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		SHEHelper.setTextFormat(this.txtLimitAmount);
		this.txtLimitAmount.setNegatived(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.menuSubmitOption.setVisible(false);
		
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		if (getOprtState().equals(OprtState.ADDNEW)) {
			UserInfo curUser = SysContext.getSysContext().getCurrentUserInfo();

			this.f7Keeper.setValue(curUser);
			this.f7Creator.setValue(curUser);
			SaleOrgUnitInfo orgInfo = SysContext.getSysContext().getCurrentSaleUnit();
			if (orgInfo != null) {
				this.f7KeepOrgUnit.setValue(orgInfo);
			}
		}
		this.loadFields();
	}

	public void loadFields() {
		super.loadFields();
		
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		this.verifyEmpty(this, f7Currency, "币别不能为空");

		this.verifyEmpty(this, spinCount, "张数不能为空");
		this.verifyEmpty(this, spinStartNum, "起始流水号不能为空");

		// 编码规则不能为空
		if (this.txtCodeRule.getText() == null
				|| this.txtCodeRule.getText().trim().equals("")) {
			MsgBox.showInfo(this, "编码规则不能为空！");
			this.abort();
		}

		// 编码规则必有一个*号
		if (this.txtCodeRule.getText() != null
				&& this.txtCodeRule.getText().indexOf("*") < 0) {
			MsgBox.showInfo(this, "编码规则必有一个*号！");
			this.abort();
		}
		//
		String number = this.txtCodeRule.getText()
		+ this.spinCount.getIntegerVlaue();
		if (number.length() > 80) {
			MsgBox.showInfo(this, "支票编码规则长度+张数长度不能超过80个字符");
			SysUtil.abort();
		}

		this.verifyEmpty(this, f7Keeper, "保管人不能为空");
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);// 校验
		boolean checkIsDump = checkIsDump();

		if (checkIsDump) {
			MsgBox.showInfo(this, "存在重复的票据编号");
			return;
		}

		// 批量添加空白票据
		doAddBatch();

		// 显示成功信息
		setMessageText("登记成功");
		showMessage();
		loadFields();
		// initOldData(editData);
		// this.txtCodeRule.setText("*");
	}

	private void doAddBatch() throws Exception, BOSException, EASBizException {
		ChequeCollection collection = new ChequeCollection();
		String codeRule = this.txtCodeRule.getText();// 编码规则，由界面传入

		int count = Integer.parseInt(this.spinCount.getValue().toString());// 张数，由界面传入
		int beginIndex = Integer.parseInt(this.spinStartNum.getValue()
				.toString());// 起始流水号，由界面传入

		//		String batch = new Long(System.currentTimeMillis()).toString();// 暂时使用系统时间，以后加上编码规则

		// 设置编码规则、张数、起始流水号
		CodeGenerater codeGen = new CodeGenerater();
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(codeRule);

		for (int i = 0; i < count; i++) {
			ChequeInfo item = new ChequeInfo();
			item.setChequeType((ChequeTypeEnum) this.comboChequeType.getSelectedItem());
			item.setStatus(ChequeStatusEnum.Booked);
			item.setVerifyStatus(VerifyStatusEnum.NotVerified);
			item.setLimitAmount(this.txtLimitAmount.getBigDecimalValue());
			item.setCurrency((CurrencyInfo) this.f7Currency.getValue());
			//			item.setBatch(batch);//TODO 批次是否需要
			item.setNumber(codeGen.nextNumber());// 根据编码规则、张数、起始流水号生成支票编码
			item.setKeeper((UserInfo) this.f7Keeper.getValue());

			Object obj = this.f7KeepOrgUnit.getValue();
			if (obj instanceof SaleOrgUnitInfo) {
				SaleOrgUnitInfo saleOrgUnit = (SaleOrgUnitInfo) obj;
				item.setKeepOrgUnit(saleOrgUnit.castToFullOrgUnitInfo());	
			}

			item.setCreator((UserInfo) this.f7Creator.getValue());
			item.setCreateTime(this.pkCreateTime.getTimestamp());
			item.setDescription(this.txtDescription.getText());

			collection.add(item);
		}

		ChequeFactory.getRemoteInstance().addBatch(collection);
	}

	/**
	 * 判断是否有重复编码的票据
	 * */
	private boolean checkIsDump() throws BOSException, EASBizException {
		String codeRule = this.txtCodeRule.getText();// 编码规则，由界面传入
		int count = Integer.parseInt(this.spinCount.getValue().toString());// 张数，由界面传入
		int beginIndex = Integer.parseInt(this.spinStartNum.getValue().toString());// 起始流水号，由界面传入

		CodeGenerater codeGen = new CodeGenerater();
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(codeRule);
		String[] numbers = new String[count];

		for (int i = 0; i < count; i++) {
			numbers[i] = codeGen.nextNumber();
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", FDCHelper.getSetByArray(numbers),CompareType.INCLUDE));

		return ChequeFactory.getRemoteInstance().exists(filter);		
	}

	//重写避免基类报错
	protected void checkIsOUSealUp() throws Exception {
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		this.txtCodeRule.setText("*");
		super.actionAddNew_actionPerformed(e);
	}

	private void verifyEmpty(ChequeBookInUI inUI, KDSpinner spinner, String msg) {
		Integer integer = spinner.getIntegerVlaue();
		if (integer == null) {
			spinner.requestFocus(true);
			MsgBox.showWarning(inUI, msg);
			SysUtil.abort();
		}

	}

	private void verifyEmpty(CoreUIObject inUI, KDBizPromptBox bizBox,
			String msg) {
		Object content = bizBox.getData();
		if (content == null) {
			bizBox.requestFocus(true);
			MsgBox.showWarning(inUI, msg);
			SysUtil.abort();
		}
	}

	//bt338400 解决EDIT界面传入ID为空的BUG,另在LIST的界面登记票据时设置了opertstate为addnew,但单独从chequeBookInUI进入时没有
	//设置opertstate，所以在保存时会报空指针 bt338997 by lijun
	protected void inOnload() throws Exception {
		this.setOprtState("ADDNEW");
		// super.inOnload();
	}
}