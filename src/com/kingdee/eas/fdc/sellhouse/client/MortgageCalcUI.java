/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.BasicNumberTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.LoanCalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.LoanTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MortgageYearEnum;
import com.kingdee.eas.fdc.sellhouse.PaymentMethodEnum;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class MortgageCalcUI extends AbstractMortgageCalcUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8903085833445972192L;
	private static final Logger logger = CoreUIObject
			.getLogger(MortgageCalcUI.class);

	/**
	 * output class constructor
	 */
	public MortgageCalcUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		SpinnerNumberModel fol = new SpinnerNumberModel(1,0.01,2.01,0.10); //add by shilei
		this.texInterest.setEditable(false);
		this.numfloating.setModel(fol);
		this.txtRate.setValue(new Float(1));
		String companyID=SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
		IObjectPK comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		String str=ParamControlFactory.getRemoteInstance().getParamValue(comPK, "FDCSHE130_BANK");
		if(null!=str){
			texInterest.setText(str);
		}
		this.lblShangYeDaikuan.setVisible(false);
		this.kDLabelContainer1.setVisible(false);
		this.lblMonthTwo.setVisible(false);
		this.lblLoanTotalMoney.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.tbMain.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionViewSignature.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionViewSubmitProccess.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.txtreserveRate.setEditable(false);
		this.actionCalc.setVisible(true);
		this.actionClear.setVisible(true);
		this.btnCalc.setEnabled(true);
		this.btnClear.setEnabled(true);
		txtTotalLoan.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtTotalLoan.setPrecision(2);
		txtTotalLoan.setRoundingMode(2);
		txtLixi.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtLixi.setPrecision(2);
		txtLixi.setRoundingMode(2);
		txtMonthReturn.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtMonthReturn.setPrecision(2);
		txtMonthReturn.setRoundingMode(2);
		txtShouqi.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtShouqi.setPrecision(2);
		txtShouqi.setRoundingMode(2);
		txtreserveRate.setDataType(BasicNumberTextField.BIGDECIMAL_TYPE);
		txtreserveRate.setPrecision(2);
		txtreserveRate.setRoundingMode(2);
		txtTotal.setEditable(false);
    	txtTotalLoan.setEditable(false);
    	txtReturn.setEditable(false);
		txtLixi.setEditable(false);
		txtShouqi.setEditable(false);
		txtLoanMonth.setEditable(false);
		txtMonthReturn.setEditable(false);
		this.tbMain.setEditable(false);
		cbcLoan.setSelectedIndex(5);
		cbcMonageYear.setSelectedIndex(19);
		this.tbMain.getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_NUMBER_FTM);
		
		//新售楼添加
		BigDecimal totalAmount =(BigDecimal) this.getUIContext().get("totalAmount");
		txtTotal.setValue(totalAmount);
		this.lblCalcType.setVisible(false);
		this.lblPrice.setVisible(false);
		this.lblare.setVisible(false);
		this.kDLabel1.setVisible(false);
		this.kDLabel2.setVisible(false);
		cbcCalcType.setSelectedIndex(1);
		lblMoange.setVisible(true);
		BigDecimal aFundAmount =(BigDecimal) this.getUIContext().get("aFundAmount");
		BigDecimal loanAmount =(BigDecimal) this.getUIContext().get("loanAmount");
		if(aFundAmount!=null&&loanAmount!=null){
			if(aFundAmount.compareTo(FDCHelper.ZERO) > 0 && loanAmount.compareTo(FDCHelper.ZERO) > 0){
				this.cbcLoanType.setSelectedIndex(2);
			}else if(aFundAmount.compareTo(FDCHelper.ZERO) == 0 && loanAmount.compareTo(FDCHelper.ZERO) > 0){
				this.cbcLoanType.setSelectedIndex(0);
			}else if(aFundAmount.compareTo(FDCHelper.ZERO) > 0 && loanAmount.compareTo(FDCHelper.ZERO) == 0){
				this.cbcLoanType.setSelectedIndex(1);
			}
		}
	}
	
	public void loadFields(){
		super.loadFields();
		
	}
	protected void checkIsOUSealUp() throws Exception {
		// super.checkIsOUSealUp();
	}
	/**
	 * output actionCalc_actionPerformed
	 */
	public void actionCalc_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalc_actionPerformed(e);

		if (this.cbcType.getSelectedItem() != null) {
			PaymentMethodEnum type = (PaymentMethodEnum) this.cbcType
					.getSelectedItem();
			if (type.equals(PaymentMethodEnum.InterestOnPrincipal)) {
				// 等额本息
				calcInterestOnPrincipal();
			} else {
				// 等额本金
				calcCapital();
			}
		}
	}

	/**
	 * output actionClear_actionPerformed
	 */
	public void actionClear_actionPerformed(ActionEvent e) throws Exception {
		super.actionClear_actionPerformed(e);
		clearData();
	}

	protected KDTable getDetailTable() {
		return null;
	}
	protected void numfloatingstate_Changed(ChangeEvent e) throws Exception {   //add by shilei
		super.numfloatingstate_Changed(e);
		LoanTypeEnum loanTypeEnum = (LoanTypeEnum)cbcLoanType.getSelectedItem();
		String companyID=SysContext.getSysContext().getCurrentSaleUnit().getId().toString();
		IObjectPK comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		float m = Float.parseFloat(ParamControlFactory.getRemoteInstance().getParamValue(comPK, "FDCSHE130_BANK"));
		float numfloat = Float.parseFloat(numfloating.getValue().toString());
		if(loanTypeEnum.equals(loanTypeEnum.accommodation)){ //如果是公积金按揭
			txtreserveRate.setValue(new Float(m*numfloat));
		}else if(loanTypeEnum.equals(loanTypeEnum.business)){//如果是商业按揭
			txtRate.setValue(new Float(m*numfloat));
		}else if(loanTypeEnum.equals(loanTypeEnum.combination)){
			txtRate.setValue(new Float(m*numfloat));
			txtreserveRate.setValue(new Float(m*numfloat));
		}
	}
	public void cbcLoanType_itemStateChanged(java.awt.event.ItemEvent e) { 
		if (e.getItem() != null) {
			LoanTypeEnum type = (LoanTypeEnum) e.getItem();
			BigDecimal aFundAmount =(BigDecimal) this.getUIContext().get("aFundAmount");
			BigDecimal loanAmount =(BigDecimal) this.getUIContext().get("loanAmount");
			if (type.equals(LoanTypeEnum.combination)) {
				this.txtRate.setValue(new BigDecimal(1)); //add by shilei
				this.txtreserveRate.setValue(new BigDecimal(1));
				this.lblCalcType.setVisible(false);
				this.lblPrice.setVisible(false);
				this.lblare.setVisible(false);
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(true);
				this.lblShangYeDaikuan.setVisible(true);
				kDLabel1.setVisible(false);
				kDLabel2.setVisible(false);
				this.txtreserveRate.setEditable(true);
				this.txtRate.setEditable(true);
				this.lblMoange.setVisible(false);
//				lblMoageYear.setBounds(21, 150, 270, 19);
//				this.lblMoageYear.setVisible(false);
//				lblLoanTotalMoney.setVisible(true);
				this.txtShangyeDaikuan.setValue(loanAmount);
				this.txtGongjiJinDaikuan.setValue(aFundAmount);
				
				lblMoageYear.setBounds(335, 69, 270, 19);
				this.lblMoageYear.setVisible(true);
				
			} else if (type.equals(LoanTypeEnum.accommodation)) {
				this.txtRate.setValue(null);    //add by shilei
				this.txtreserveRate.setValue(new BigDecimal(1));
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				this.txtreserveRate.setEditable(true);
				this.txtRate.setEditable(false);
//				this.cbcCalcType.setSelectedIndex(0);
				this.lblMoange.setVisible(true);
//				lblMoageYear.setBounds(335, 152, 270, 19);
				this.lblPrice.setVisible(false);
				this.lblare.setVisible(false);
				this.kDLabel1.setVisible(false);
				this.kDLabel2.setVisible(false);
				lblLoanTotalMoney.setVisible(true); 
//				txtreserveRate.setEnabled(true);
				this.txtLoanTotalMoney.setValue(aFundAmount);
				
				lblMoageYear.setBounds(335, 69, 270, 19);
				this.lblMoageYear.setVisible(true);
			} else if (type.equals(LoanTypeEnum.business)) {
				this.txtreserveRate.setValue(null);
				this.txtRate.setValue(new BigDecimal(1));  //add by  shilei
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				this.txtreserveRate.setEditable(false);
				this.txtRate.setEditable(true);
//				this.cbcCalcType.setSelectedIndex(0);
				this.lblMoange.setVisible(true);
//				lblMoageYear.setBounds(335, 152, 270, 19);
				lblLoanTotalMoney.setVisible(true);
				this.txtLoanTotalMoney.setValue(loanAmount);
				
				lblMoageYear.setBounds(335, 69, 270, 19);
				this.lblMoageYear.setVisible(true);
			}
		}
		
		clearData();
	}

	public void cbcCalcType_itemStateChanged(java.awt.event.ItemEvent e) {
		if (e.getItem() != null) {

			LoanCalcTypeEnum type = (LoanCalcTypeEnum) e.getItem();

			if (type.equals(LoanCalcTypeEnum.TotalMoney)) {
				this.lblPrice.setVisible(false);
				this.lblare.setVisible(false);
				this.lblLoanTotalMoney.setVisible(true);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				kDLabel1.setVisible(false);
				kDLabel2.setVisible(false);
				// this.lblMoageYear.setVisible(false);
				this.lblMoange.setVisible(false);
				this.lblrate.setBounds(21, 150, 270, 19);
				this.lblreserveRate.setBounds(21, 187, 270, 19);
			} else {
				this.lblPrice.setVisible(true);
				this.lblare.setVisible(true);
				this.lblLoanTotalMoney.setVisible(false);
				this.kDLabelContainer1.setVisible(false);
				this.lblShangYeDaikuan.setVisible(false);
				kDLabel1.setVisible(true);
				kDLabel2.setVisible(true);
				// this.lblMoageYear.setVisible(true);
				this.lblMoange.setVisible(true);
				this.lblrate.setBounds(21, 187, 270, 19);
				this.lblreserveRate.setBounds(335, 187, 270, 19);
			}
		}
	}

	public void cbcType_itemStateChanged(java.awt.event.ItemEvent e) {
		if (e.getItem() != null) {

			PaymentMethodEnum type = (PaymentMethodEnum) e.getItem();

			if (type.equals(PaymentMethodEnum.Principal)) {
				kDLabelContainer13.setVisible(false);
				this.lblMonthTwo.setVisible(true);
				this.tbMain.setVisible(true);
				this.kDScrollPane1.setVisible(true);
			} else {
				kDLabelContainer13.setVisible(true);
				this.lblMonthTwo.setVisible(false);
				this.tbMain.setVisible(false);
				this.kDScrollPane1.setVisible(false);
			}
		}

		clearData();
	}

	private void clearData() {
		this.txtPrice.setValue(null);
		this.txtArea.setValue(null);
//		this.txtRate.setValue(null);
//		this.txtreserveRate.setValue(null);
//		this.txtTotal.setValue(null);
//		this.txtTotalLoan.setValue(null);
		this.txtReturn.setValue(null);
		this.txtLixi.setValue(null);
		this.txtShouqi.setValue(null);
		this.txtLoanMonth.setValue(null);
		this.txtMonthReturn.setValue(null);
		this.tbMain.removeRows();
//		this.txtLoanTotalMoney.setValue(null);
//		this.txtShangyeDaikuan.setValue(null);
//		this.txtGongjiJinDaikuan.setValue(null);
	}

	/**
	 * 等额本金
	 */
	private void calcCapital() {

		this.tbMain.removeRows();
		// 面积
//		BigDecimal area = FDCHelper.ZERO;
		// 单价
//		BigDecimal price = FDCHelper.ZERO;
		// 贷款总额
		BigDecimal loanTotal = FDCHelper.ZERO;

		// 汇率
		BigDecimal rate = FDCHelper.ZERO;

		LoanTypeEnum loanType = (LoanTypeEnum) this.cbcLoanType
				.getSelectedItem();

		// 商业汇率
		if (loanType.equals(LoanTypeEnum.business)) {
			if (this.txtRate.getBigDecimalValue() != null) {
				rate = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "商业汇率不能为空！");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "商业汇率不能小于0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "商业汇率不能大于100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// 按照面积和单价计算
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "面积不能为空！");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "单价不能为空！");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "面积不能小于0！");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "单价不能小于0！");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);

//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					BigDecimal percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// 月利率
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// 计算每月固定偿还部分
//					BigDecimal firstPart = FDCHelper.divide(total,
//							new BigDecimal(String.valueOf(nianXian * 12)));
//					BigDecimal zongji = FDCHelper.ZERO;
//					
//					for (int i = 1; i <= nianXian * 12; i++) {
//						// （月数-1）
//						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
//								String.valueOf("1"));
//						// （本金/贷款年限）
//						BigDecimal two = FDCHelper.divide(total,
//								new BigDecimal(String.valueOf(nianXian * 12)),
//								BigDecimal.ROUND_UNNECESSARY, 2);
//						// （本金/贷款年限）*月利率
//						BigDecimal three = FDCHelper.multiply(two, rateMonth);
//						// （月数-1）（本金/贷款年限）*月利率
//						BigDecimal four = FDCHelper.multiply(one, three);
//						// (本金*月利率-（月数-1）（本金/贷款年限）*月利率)
//						BigDecimal five = FDCHelper.subtract(FDCHelper
//								.multiply(total, rateMonth), four);
//
//						BigDecimal result = FDCHelper.add(firstPart, five);
//
//						IRow row = this.tbMain.addRow();
//						if (row != null) {
//							row.getCell("qishu").setValue(i + "期");
//							row.getCell("price").setValue(result);
//						}
//						zongji = FDCHelper.add(zongji, result);
//					}
//					
//					//房款
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//贷款总额
//					this.txtTotalLoan.setValue(total);
//					//还款总额
//					txtReturn.setValue(zongji);
//					//利息
//					txtLixi.setValue(FDCHelper.subtract(zongji, total));
//					
//					//首期
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//贷款月数
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//				}
//				// 按照贷款总额
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "贷款总额不能为空！");
					SysUtil.abort();
				}

				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// 月利率
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
					// 计算每月固定偿还部分
					BigDecimal firstPart = FDCHelper.divide(loanTotal,
							new BigDecimal(String.valueOf(nianXian * 12)));

					BigDecimal zongji  = FDCHelper.ZERO;
					
					for (int i = 1; i <= nianXian * 12; i++) {
						// （月数-1）
						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
								String.valueOf("1"));
						// （本金/贷款年限）
						BigDecimal two = FDCHelper.divide(loanTotal,
								new BigDecimal(String.valueOf(nianXian * 12)),
								BigDecimal.ROUND_UNNECESSARY, 2);
						// （本金/贷款年限）*月利率
						BigDecimal three = FDCHelper.multiply(two, rateMonth);
						// （月数-1）（本金/贷款年限）*月利率
						BigDecimal four = FDCHelper.multiply(one, three);
						// (本金*月利率-（月数-1）（本金/贷款年限）*月利率)
						BigDecimal five = FDCHelper.subtract(FDCHelper
								.multiply(loanTotal, rateMonth), four);

						BigDecimal result = FDCHelper.add(firstPart, five);

						IRow row = this.tbMain.addRow();
						if (row != null) {
							row.getCell("qishu").setValue(i + "期");
							row.getCell("price").setValue(result);
						}
						
						zongji = FDCHelper.add(zongji, result);
					}
					
					//房款
//					txtTotal.setValue(null);
					//贷款总额
					this.txtTotalLoan.setValue(loanTotal);
					//还款总额
					txtReturn.setValue(zongji);
					//利息
					txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					
					//首期
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//贷款月数
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
					
				}
//			}
			// 公积金
		} else if (loanType.equals(LoanTypeEnum.accommodation)) {

			if (this.txtreserveRate.getBigDecimalValue() != null) {
				rate = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "公积金汇率不能为空！");
				SysUtil.abort();
			}

			if (rate.compareTo(FDCHelper.ZERO) <=0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能小于0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED)>0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能大于100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// 按照面积和单价计算
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "面积不能为空！");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "单价不能为空！");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "面积不能小于0！");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "单价不能小于0！");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);
//
//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					BigDecimal percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// 月利率
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// 计算每月固定偿还部分
//					BigDecimal firstPart = FDCHelper.divide(total,
//							new BigDecimal(String.valueOf(nianXian * 12)));
//
//					BigDecimal zongji = FDCHelper.ZERO;
//					
//					for (int i = 1; i <= nianXian * 12; i++) {
//						// （月数-1）
//						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
//								String.valueOf("1"));
//						// （本金/贷款年限）
//						BigDecimal two = FDCHelper.divide(total,
//								new BigDecimal(String.valueOf(nianXian * 12)),
//								BigDecimal.ROUND_UNNECESSARY, 2);
//						// （本金/贷款年限）*月利率
//						BigDecimal three = FDCHelper.multiply(two, rateMonth);
//						// （月数-1）（本金/贷款年限）*月利率
//						BigDecimal four = FDCHelper.multiply(one, three);
//						// (本金*月利率-（月数-1）（本金/贷款年限）*月利率)
//						BigDecimal five = FDCHelper.subtract(FDCHelper
//								.multiply(total, rateMonth), four);
//
//						BigDecimal result = FDCHelper.add(firstPart, five);
//
//						IRow row = this.tbMain.addRow();
//						if (row != null) {
//							row.getCell("qishu").setValue(i + "期");
//							row.getCell("price").setValue(result);
//						}
//						
//						zongji = FDCHelper.add(zongji, result);
//					}
//					
//					//房款
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//贷款总额
//					this.txtTotalLoan.setValue(total);
//					//还款总额
//					txtReturn.setValue(zongji);
//					//利息
//					txtLixi.setValue(FDCHelper.subtract(zongji, total));
//					
//					//首期
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//贷款月数
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//				}
//				// 按照贷款总额
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "贷款总额不能为空！");
					SysUtil.abort();
				}

				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// 月利率
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
					// 计算每月固定偿还部分
					BigDecimal firstPart = FDCHelper.divide(loanTotal,
							new BigDecimal(String.valueOf(nianXian * 12)));

					BigDecimal zongji = FDCHelper.ZERO;
					
					for (int i = 1; i <= nianXian * 12; i++) {
						// （月数-1）
						BigDecimal one = FDCHelper.subtract(String.valueOf(i),
								String.valueOf("1"));
						// （本金/贷款年限）
						BigDecimal two = FDCHelper.divide(loanTotal,
								new BigDecimal(String.valueOf(nianXian * 12)),
								BigDecimal.ROUND_UNNECESSARY, 2);
						// （本金/贷款年限）*月利率
						BigDecimal three = FDCHelper.multiply(two, rateMonth);
						// （月数-1）（本金/贷款年限）*月利率
						BigDecimal four = FDCHelper.multiply(one, three);
						// (本金*月利率-（月数-1）（本金/贷款年限）*月利率)
						BigDecimal five = FDCHelper.subtract(FDCHelper
								.multiply(loanTotal, rateMonth), four);

						BigDecimal result = FDCHelper.add(firstPart, five);

						IRow row = this.tbMain.addRow();
						if (row != null) {
							row.getCell("qishu").setValue(i + "期");
							row.getCell("price").setValue(result);
						}
						zongji = FDCHelper.add(zongji, result);
					}
					
					//房款
//					txtTotal.setValue(null);
					//贷款总额
					this.txtTotalLoan.setValue(loanTotal);
					//还款总额
					txtReturn.setValue(zongji);
					//利息
					txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					
					//首期
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//贷款月数
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
				}
//			}
			// 组合
		} else if (loanType.equals(LoanTypeEnum.combination)) {
			/**
			 * 按照组合
			 */

			BigDecimal shangye = FDCHelper.ZERO;
			BigDecimal gongjijin = FDCHelper.ZERO;

			BigDecimal shangyeDaikKuan = FDCHelper.ZERO;
			BigDecimal gongjijinDaiKuan = FDCHelper.ZERO;

			BigDecimal shangyeRateMonth = FDCHelper.ZERO;
			BigDecimal gongjijinRateMonth = FDCHelper.ZERO;

			if (this.txtShangyeDaikuan.getBigDecimalValue() != null) {
				shangyeDaikKuan = this.txtShangyeDaikuan.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "商业贷款不能为空！");
				SysUtil.abort();
			}
			if (this.txtGongjiJinDaikuan.getBigDecimalValue() != null) {
				gongjijinDaiKuan = this.txtGongjiJinDaikuan
						.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "公积金贷款不能为空！");
				SysUtil.abort();
			}
			if (this.txtRate.getBigDecimalValue() != null) {
				shangye = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "商业汇率不能为空！");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "商业汇率不能小于0!");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "商业汇率不能大于100!");
				SysUtil.abort();
			}
			if (this.txtreserveRate.getBigDecimalValue() != null) {
				gongjijin = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "公积金汇率不能为空！");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能小于0!");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能大于100!");
				SysUtil.abort();
			}
			BigDecimal total  = FDCHelper.add(shangyeDaikKuan, gongjijinDaiKuan);
			
			shangyeRateMonth = FDCHelper.divide(shangye, new BigDecimal(String
					.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijin, new BigDecimal(
					String.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			shangyeRateMonth = FDCHelper.divide(shangyeRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijinRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);

			MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
					.getSelectedItem();
			int nianXian = Integer.parseInt(year.getValue());

			// 计算每月固定偿还部分
			BigDecimal firstPartShangye = FDCHelper.divide(shangyeDaikKuan,
					new BigDecimal(String.valueOf(nianXian * 12)));
			BigDecimal firstPartgongjijinDaiKuan = FDCHelper.divide(
					gongjijinDaiKuan, new BigDecimal(String
							.valueOf(nianXian * 12)));
			
			BigDecimal zongji = FDCHelper.ZERO;
			
			for (int i = 1; i <= nianXian * 12; i++) {

				/**
				 * 以下为商业贷款部分
				 */
				// （月数-1）
				BigDecimal one = FDCHelper.subtract(String.valueOf(i), String
						.valueOf("1"));
				// （本金/贷款年限）
				BigDecimal two = FDCHelper.divide(shangyeDaikKuan,
						new BigDecimal(String.valueOf(nianXian * 12)),
						BigDecimal.ROUND_UNNECESSARY, 2);
				// （本金/贷款年限）*月利率
				BigDecimal three = FDCHelper.multiply(two, shangyeRateMonth);
				// （月数-1）（本金/贷款年限）*月利率
				BigDecimal four = FDCHelper.multiply(one, three);
				// (本金*月利率-（月数-1）（本金/贷款年限）*月利率)
				BigDecimal five = FDCHelper.subtract(FDCHelper.multiply(
						shangyeDaikKuan, shangyeRateMonth), four);

				BigDecimal result1 = FDCHelper.add(firstPartShangye, five);

				/**
				 * 以下为公积金部分
				 */
				// （本金/贷款年限）
				BigDecimal two1 = FDCHelper.divide(gongjijinDaiKuan,
						new BigDecimal(String.valueOf(nianXian * 12)),
						BigDecimal.ROUND_UNNECESSARY, 2);
				// （本金/贷款年限）*月利率
				BigDecimal three1 = FDCHelper
						.multiply(two1, gongjijinRateMonth);
				// （月数-1）（本金/贷款年限）*月利率
				BigDecimal four1 = FDCHelper.multiply(one, three1);
				// (本金*月利率-（月数-1）（本金/贷款年限）*月利率)
				BigDecimal five1 = FDCHelper.subtract(FDCHelper.multiply(
						gongjijinDaiKuan, gongjijinRateMonth), four1);

				BigDecimal result2 = FDCHelper.add(firstPartgongjijinDaiKuan,
						five1);
				IRow row = this.tbMain.addRow();
				if (row != null) {
					row.getCell("qishu").setValue(i + "期");
					row.getCell("price").setValue(
							FDCHelper.add(result1, result2));
				}
				
				zongji = FDCHelper.add(zongji, FDCHelper.add(result1, result2));
			}
			
			//房款
//			txtTotal.setValue(null);
			//贷款总额
			this.txtTotalLoan.setValue(total);
			//还款总额
			txtReturn.setValue(zongji);
			//利息
			txtLixi.setValue(FDCHelper.subtract(zongji, total));
			
			//首期
			txtShouqi.setValue(FDCHelper.ZERO);
			
			//贷款月数
			txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
		}
	}

	private void calcInterestOnPrincipal() {
		this.tbMain.removeRows();
		// 面积
//		BigDecimal area = FDCHelper.ZERO;
		// 单价
//		BigDecimal price = FDCHelper.ZERO;
		// 贷款总额
		BigDecimal loanTotal = FDCHelper.ZERO;
		
		// 房款总额
		BigDecimal totalAmount = FDCHelper.ZERO;
		if(this.txtTotal.getBigDecimalValue() != null){
			totalAmount = this.txtTotal.getBigDecimalValue();
		}

		// 汇率
		BigDecimal rate = FDCHelper.ZERO;

		LoanTypeEnum loanType = (LoanTypeEnum) this.cbcLoanType
				.getSelectedItem();

		// 商业汇率
		if (loanType.equals(LoanTypeEnum.business)) {
			if (this.txtRate.getBigDecimalValue() != null) {
				rate = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "商业汇率不能为空！");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "商业汇率不能小于0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showWarning(this, "商业汇率不能大于100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// 按照面积和单价计算
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "面积不能为空！");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "单价不能为空！");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "面积不能小于0！");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "单价不能小于0！");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);
//				BigDecimal percentBig = FDCHelper.ZERO;
//				
//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// 月利率
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// 计算每月固定偿还部分
//					// BigDecimal firstPart = FDCHelper.divide(total, new
//					// BigDecimal(String.valueOf(nianXian*12)));
//					// M=A*i*(1＋i)^n/((1＋i)^n-1)
//
//					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
//
//					// （1+月利率）
//					BigDecimal one = FDCHelper.add(rateMonth, String
//							.valueOf("1"));
//					// （1+月利率）^n
//					BigDecimal two = pow(one,nianXian * 12);
//					// （1+月利率）^n-1
//					BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
//					//one.pow(nianXian * 12 - 1);
//
//					// A*i
//					BigDecimal four = FDCHelper.multiply(total, rateMonth);
//
//					// M=A*i*(1＋i)^n
//					BigDecimal five = FDCHelper.multiply(four, two);
//
//					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
//					BigDecimal six = FDCHelper.divide(five, three,
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					this.txtMonthReturn.setValue(six);
//					
//					//房款
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//贷款总额
//					this.txtTotalLoan.setValue(total);
//					//还款总额
//					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
//					//利息
//					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), total));
//					
//					//首期
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//贷款月数
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//				}
//				
//				
//
//				// 按照贷款总额
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "贷款总额不能为空！");
					SysUtil.abort();
				}
				if(FDCHelper.subtract(loanTotal, totalAmount).compareTo(FDCHelper.ZERO) > 0){
					FDCMsgBox.showWarning(this, "贷款总额不能大于房款总额！");
					SysUtil.abort();
				}

				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// 月利率
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
					// 计算每月固定偿还部分
					// BigDecimal firstPart = FDCHelper.divide(total, new
					// BigDecimal(String.valueOf(nianXian*12)));
					// M=A*i*(1＋i)^n/((1＋i)^n-1)

					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)

					// （1+月利率）
					BigDecimal one = FDCHelper.add(rateMonth, String
							.valueOf("1"));
					// （1+月利率）^n
					BigDecimal two = pow(one,nianXian * 12);
					// （1+月利率）^n-1
					BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
					//one.pow(nianXian * 12 - 1);

					// A*i
					BigDecimal four = FDCHelper.multiply(loanTotal, rateMonth);

					// M=A*i*(1＋i)^n
					BigDecimal five = FDCHelper.multiply(four, two);

					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
					BigDecimal six = FDCHelper.divide(five, three,
							BigDecimal.ROUND_UNNECESSARY, 2);

					this.txtMonthReturn.setValue(six);
					

					//房款
//					txtTotal.setValue(null);
					//贷款总额
					this.txtTotalLoan.setValue(loanTotal);
					//还款总额
					//txtReturn.setValue(zongji);
					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
					//利息
					//txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), loanTotal));
					
					//首期
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//贷款月数
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
				}
//			}
			// 公积金
		} else if (loanType.equals(LoanTypeEnum.accommodation)) {

			if (this.txtreserveRate.getBigDecimalValue() != null) {
				rate = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "公积金汇率不能为空！");
				SysUtil.abort();
			}

			if (rate.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能小于0!");
				SysUtil.abort();
			}
			if (rate.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能大于100!");
				SysUtil.abort();
			}

//			LoanCalcTypeEnum calcType = (LoanCalcTypeEnum) this.cbcCalcType
//					.getSelectedItem();
//			// 按照面积和单价计算
//			if (calcType.equals(LoanCalcTypeEnum.AreaAndPrice)) {
//				if (this.txtArea.getBigDecimalValue() != null) {
//					area = this.txtArea.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "面积不能为空！");
//					SysUtil.abort();
//				}
//				if (this.txtPrice.getBigDecimalValue() != null) {
//					price = this.txtPrice.getBigDecimalValue();
//				} else {
//					FDCMsgBox.showWarning(this, "单价不能为空！");
//					SysUtil.abort();
//				}
//				if(area.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "面积不能小于0！");
//					SysUtil.abort();
//				}
//				if(price.compareTo(FDCHelper.ZERO)<0){
//					FDCMsgBox.showWarning(this, "单价不能小于0！");
//					SysUtil.abort();
//				}
//
//				BigDecimal total = FDCHelper.multiply(area, price);
//				BigDecimal percentBig  = FDCHelper.ZERO;
//				if (this.cbcLoan.getSelectedItem() != null) {
//					PercentageEnum percent = (PercentageEnum) this.cbcLoan
//							.getSelectedItem();
//
//					percentBig = FDCHelper.divide(new BigDecimal(
//							percent.getValue()), new BigDecimal(String
//							.valueOf("10")), BigDecimal.ROUND_UNNECESSARY, 2);
//
//					total = FDCHelper.multiply(total, percentBig);
//				}
//
//				if (this.cbcMonageYear.getSelectedItem() != null) {
//
//					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
//							.getSelectedItem();
//					int nianXian = Integer.parseInt(year.getValue());
//
//					// 月利率
//					BigDecimal rateMonth = FDCHelper.divide(rate,
//							new BigDecimal(String.valueOf("100")),
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
//							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY, 2);
//					// 计算每月固定偿还部分
//					// BigDecimal firstPart = FDCHelper.divide(total, new
//					// BigDecimal(String.valueOf(nianXian*12)));
//					// M=A*i*(1＋i)^n/((1＋i)^n-1)
//
//					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
//
//					// （1+月利率）
//					BigDecimal one = FDCHelper.add(rateMonth, String
//							.valueOf("1"));
//					// （1+月利率）^n
//					BigDecimal two = pow(one,nianXian * 12);
//					// （1+月利率）^n-1
//					BigDecimal three = FDCHelper.subtract(two, FDCHelper.ONE); 
//					//one.pow(nianXian * 12 - 1);
//
//					// A*i
//					BigDecimal four = FDCHelper.multiply(total, rateMonth);
//
//					// M=A*i*(1＋i)^n
//					BigDecimal five = FDCHelper.multiply(four, two);
//
//					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
//					BigDecimal six = FDCHelper.divide(five, three,
//							BigDecimal.ROUND_UNNECESSARY, 2);
//
//					this.txtMonthReturn.setValue(six);
//					
//					//房款
//					txtTotal.setValue(FDCHelper.multiply(area, price));
//					//贷款总额
//					this.txtTotalLoan.setValue(total);
//					//还款总额
//					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
//					//利息
//					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), total));
//					
//					//首期
//					txtShouqi.setValue(FDCHelper.subtract(FDCHelper.multiply(area, price), total));
//					
//					//贷款月数
//					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
//			
//				}
//				// 按照贷款总额
//			} else {
				if (this.txtLoanTotalMoney.getBigDecimalValue() != null) {
					loanTotal = this.txtLoanTotalMoney.getBigDecimalValue();
				} else {
					FDCMsgBox.showWarning(this, "贷款总额不能为空！");
					SysUtil.abort();
				}

				if(FDCHelper.subtract(loanTotal, totalAmount).compareTo(FDCHelper.ZERO) > 0){
					FDCMsgBox.showWarning(this, "贷款总额不能大于房款总额！");
					SysUtil.abort();
				}
				if (this.cbcMonageYear.getSelectedItem() != null) {

					MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
							.getSelectedItem();
					int nianXian = Integer.parseInt(year.getValue());

					// 月利率
					BigDecimal rateMonth = FDCHelper.divide(rate,
							new BigDecimal(String.valueOf("100")),
							BigDecimal.ROUND_UNNECESSARY, 2);

					rateMonth = FDCHelper.divide(rateMonth, new BigDecimal(
							String.valueOf("12")), BigDecimal.ROUND_UNNECESSARY,6);
				
					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)

					// （1+月利率）
					BigDecimal one = FDCHelper.add(rateMonth, String
							.valueOf("1"));
					// （1+月利率）^n
					BigDecimal two = pow(one,nianXian * 12);
					// （1+月利率）^n-1
					BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
					//one.pow(nianXian * 12 - 1);

					// A*i
					BigDecimal four = FDCHelper.multiply(loanTotal, rateMonth);

					// M=A*i*(1＋i)^n
					BigDecimal five = FDCHelper.multiply(four, two);

					// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
					BigDecimal six = FDCHelper.divide(five, three,
							BigDecimal.ROUND_UNNECESSARY, 2);

					this.txtMonthReturn.setValue(six);
					
					//房款
//					txtTotal.setValue(null);
					//贷款总额
					this.txtTotalLoan.setValue(loanTotal);
					//还款总额
					//txtReturn.setValue(zongji);
					txtReturn.setValue(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))));
					//利息
					//txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
					txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(six, new BigDecimal(String.valueOf(nianXian*12))), loanTotal));
					
					//首期
					txtShouqi.setValue(FDCHelper.ZERO);
					
					//贷款月数
					txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));
				
				}
//			}
			// 组合
		} else if (loanType.equals(LoanTypeEnum.combination)) {
			/**
			 * 按照组合
			 */

			BigDecimal shangye = FDCHelper.ZERO;
			BigDecimal gongjijin = FDCHelper.ZERO;

			BigDecimal shangyeDaikKuan = FDCHelper.ZERO;
			BigDecimal gongjijinDaiKuan = FDCHelper.ZERO;

			BigDecimal shangyeRateMonth = FDCHelper.ZERO;
			BigDecimal gongjijinRateMonth = FDCHelper.ZERO;

			if (this.txtShangyeDaikuan.getBigDecimalValue() != null) {
				shangyeDaikKuan = this.txtShangyeDaikuan.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "商业贷款不能为空！");
				SysUtil.abort();
			}
			if (this.txtGongjiJinDaikuan.getBigDecimalValue() != null) {
				gongjijinDaiKuan = this.txtGongjiJinDaikuan
						.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "公积金贷款不能为空！");
				SysUtil.abort();
			}
			if (this.txtRate.getBigDecimalValue() != null) {
				shangye = this.txtRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "商业汇率不能为空！");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ZERO) <= 0) {
				FDCMsgBox.showWarning(this, "商业汇率不能小于0!");
				SysUtil.abort();
			}
			if (shangye.compareTo(FDCHelper.ONE_HUNDRED) > 0) {
				FDCMsgBox.showWarning(this, "商业汇率不能大于100!");
				SysUtil.abort();
			}
			if (this.txtreserveRate.getBigDecimalValue() != null) {
				gongjijin = this.txtreserveRate.getBigDecimalValue();
			} else {
				FDCMsgBox.showWarning(this, "公积金汇率不能为空！");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ZERO) <=0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能小于0!");
				SysUtil.abort();
			}
			if (gongjijin.compareTo(FDCHelper.ONE_HUNDRED) >0) {
				FDCMsgBox.showWarning(this, "公积金汇率不能大于100!");
				SysUtil.abort();
			}
			
			if(FDCHelper.subtract(shangyeDaikKuan.add(gongjijinDaiKuan), totalAmount).compareTo(FDCHelper.ZERO) > 0){
				FDCMsgBox.showWarning(this, "贷款总额不能大于房款总额！");
				SysUtil.abort();
			}

			shangyeRateMonth = FDCHelper.divide(shangye, new BigDecimal(String
					.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijin, new BigDecimal(
					String.valueOf("100")), BigDecimal.ROUND_UNNECESSARY, 2);
			shangyeRateMonth = FDCHelper.divide(shangyeRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);
			gongjijinRateMonth = FDCHelper.divide(gongjijinRateMonth,
					new BigDecimal(String.valueOf("12")),
					BigDecimal.ROUND_UNNECESSARY, 2);

			MortgageYearEnum year = (MortgageYearEnum) this.cbcMonageYear
					.getSelectedItem();
			int nianXian = Integer.parseInt(year.getValue());
			/**
			 * 以下为商业贷款部分
			 */
			// 月利率
			// 计算每月固定偿还部分
			// BigDecimal firstPart = FDCHelper.divide(total, new
			// BigDecimal(String.valueOf(nianXian*12)));
			// M=A*i*(1＋i)^n/((1＋i)^n-1)
			// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
			// （1+月利率）
			BigDecimal one = FDCHelper.add(shangyeRateMonth, String
					.valueOf("1"));
			// （1+月利率）^n
			BigDecimal two = pow(one,nianXian * 12);
			// （1+月利率）^n-1
			BigDecimal three = FDCHelper.subtract(two, new BigDecimal("1")); 
			//one.pow(nianXian * 12 - 1);

			// A*i
			BigDecimal four = FDCHelper.multiply(shangyeDaikKuan,
					shangyeRateMonth);

			// M=A*i*(1＋i)^n
			BigDecimal five = FDCHelper.multiply(four, two);

			// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
			BigDecimal six = FDCHelper.divide(five, three,
					BigDecimal.ROUND_UNNECESSARY, 2);

			/**
			 * 以下为公积金部分
			 */
			// 月利率
			// M=A*i*(1＋i)^n/((1＋i)^n-1)
			// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
			// （1+月利率）
			BigDecimal one1 = FDCHelper.add(gongjijinRateMonth, String
					.valueOf("1"));
			// （1+月利率）^n
			BigDecimal two1 = pow(one1,nianXian * 12);
			// （1+月利率）^n-1
			BigDecimal three1 = FDCHelper.subtract(two1, new BigDecimal("1")); 
			//one.pow(nianXian * 12 - 1);

			// A*i
			BigDecimal four1 = FDCHelper.multiply(gongjijinDaiKuan,
					gongjijinRateMonth);

			// M=A*i*(1＋i)^n
			BigDecimal five1 = FDCHelper.multiply(four1, two1);

			// 本金*月利率*（1+月利率）^n/((1＋月利率)^n-1)
			BigDecimal six1 = FDCHelper.divide(five1, three1,
					BigDecimal.ROUND_UNNECESSARY, 2);

			this.txtMonthReturn.setValue(FDCHelper.add(six, six1));
			
			BigDecimal daiKuanTotal = FDCHelper.ZERO;
			
			daiKuanTotal = FDCHelper.add(this.txtShangyeDaikuan.getBigDecimalValue(), this.txtGongjiJinDaikuan.getBigDecimalValue());
			//房款
//			txtTotal.setValue(null);
			//贷款总额
			this.txtTotalLoan.setValue(daiKuanTotal);
			//还款总额
			//txtReturn.setValue(zongji);
			txtReturn.setValue(FDCHelper.multiply(FDCHelper.add(six, six1), new BigDecimal(String.valueOf(nianXian*12))));
			//利息
			//txtLixi.setValue(FDCHelper.subtract(zongji, loanTotal));
			txtLixi.setValue(FDCHelper.subtract(FDCHelper.multiply(FDCHelper.add(six, six1), new BigDecimal(String.valueOf(nianXian*12))), daiKuanTotal));
			
			//首期
			txtShouqi.setValue(FDCHelper.ZERO);
			
			//贷款月数
			txtLoanMonth.setValue(new BigDecimal(String.valueOf(nianXian*12)));

		}
	}
	
	public static BigDecimal pow(BigDecimal dec1,int dec2){
		if(dec1==null){
			return null;
		}
		if(dec2<0){
			return null;
		}
		BigDecimal temp  = FDCHelper.ONE;
		
		if(dec2==0){
			return FDCHelper.ONE;
		}
		
		for (int i = 1; i <=dec2; i++) {
			temp =temp.multiply(dec1);
		}
		
		return temp;
	}
	
}