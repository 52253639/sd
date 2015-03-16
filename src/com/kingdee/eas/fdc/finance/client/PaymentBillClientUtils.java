package com.kingdee.eas.fdc.finance.client;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.client.EASResource;

/**
 * 用于绑定付款单编辑界面分录中的cell与属性
 * @author zhonghui_luo
 *
 */
public class PaymentBillClientUtils {
private static final Logger logger = CoreUIObject.getLogger(AbstractPaymentBillEditUI.class);
	
	
	/*
	 * Description:
	 * 		绑定单元格及要存储的值,其值所在的单元格为名称所对应的单元格的右边一个格
	 * @param table
	 * @param rowIndex
	 * @param colIndex		
	 * @param name			要显示的名称
	 * @param key 		值,可以用对象及其属性,如auditor.name
	 * @param bindCellMap
	 */
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * 值表格
		 */
		cell=table.getCell(rowIndex, colIndex+1);
//		cell.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		//绑定对应关系
		bindCellMap.put(key, cell);
	}

	/*
	 * Description:
	 *		绑定对象到单元格
	 * @param cell
	 * @param valueName
	 * @param valueProperty
	 * @param bindCellMap
	 */
	public static void bindCell(ICell cell,String key,HashMap bindCellMap){
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	public static void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,HashMap bindCellMap,boolean numberEditor){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * 值表格
		 */
		cell=table.getCell(rowIndex, colIndex+1);
		bindCell(cell, key, bindCellMap, numberEditor);
	}
	
	public static void bindCell(ICell cell,String key,HashMap bindCellMap,boolean numberEditor){
		if(numberEditor){
			cell.setEditor(getCellNumberEdit());
		}
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);;
        kdc.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
        kdc.setHorizontalAlignment(JTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public static boolean getValueFromCell(IObjectValue info,HashMap map){
		if(info==null||map==null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			Object cell=map.get(key);
			if(cell instanceof ICell){
				/*	
				 * 因IObjectValue的方法内屏蔽了值为null的key故不能通过key来判断
				 *  info是否有该属性
				 */
				int index = key.indexOf('.');
				if(index>0) {
					//ObjectValue
					String infoKey=key.substring(0,index);
					String propKey=key.substring(index+1);
					Object subinfo=info.get(infoKey);
					if (subinfo instanceof IObjectValue)
					{
						((IObjectValue) subinfo).put(propKey, ((ICell)cell).getValue());
						
					}
				
				}else{
					//非ObjectValue
					info.put(key,((ICell)cell).getValue());
				}
			}
		}
		
		return true;
	}
	public static boolean setValueToCell(IObjectValue info,HashMap map){
		if(info==null||map==null) return false;
		Set keys = map.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			if(key.equalsIgnoreCase(PaymentBillContants.PRJALLREQAMT)||
				key.equalsIgnoreCase(PaymentBillContants.ADDPRJALLREQAMT)||
				key.equalsIgnoreCase(PaymentBillContants.PAYPARTAMATLALLREQAMT)||
				key.equalsIgnoreCase(PaymentBillContants.CURPAID)){
				continue;
			}
			
			Object cell=map.get(key);
			int index = key.indexOf('.');
			Object value;
			if(index>0) {
				//objectValue
				String infoKey=key.substring(0,index);
				String propKey=key.substring(index+1);					
				Object subinfo=info.get(infoKey);
				if (subinfo instanceof IObjectValue)
				{
					value=((IObjectValue) subinfo).get(propKey);
					((ICell)cell).setValue(value);
				}
			}else{
				//非ObjectValue
				value=info.get(key);
				((ICell)cell).setValue(value);
			}
		}
		
		return true;
	}

	/**
	 * 描述：获取资源（从资源文件com.kingdee.eas.fdc.contract.client.ContractResource）
	 * @author sxhong  		Date 2006-9-14
	 * @param resName	资源项名称
	 * @return String 获取的资源
	 */
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.finance.client.PaymentBillResource",
						resName);
	}
		
	public static void showOprtOK(Component comp) {
		FDCClientUtils.showOprtOK(comp);
	}
	/**
	 * 【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】
	 * 在合同最终结算后，付款单提交、审批时,如果合同实付款(不含本次)加本次付款单合同内工程款本期发生大于合同结算价则提示，
     * 且不能提交或审批通过。  by cassiel_peng 2009-12-02
     * @see boolean com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils.checkForSaveSubmitAudit(Set paymenSet) throws Exception
	 */
	public static boolean checkProjectPriceInContract(Set paymentSet) throws EASBizException, BOSException{
		boolean retValue=true;
		PaymentBillInfo paymentBill=null;
		BigDecimal settlePrice=FDCHelper.ZERO;//合同结算金额
		BigDecimal thisTimePayAmt=FDCHelper.ZERO;//本次付款单合同内工程款本期发生
		BigDecimal prjPriceInCon=FDCHelper.ZERO;//合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计
		BigDecimal thisTimePrjPriceInCon=FDCHelper.ZERO;//本次的合同实付款(合同实付款(不含本次)加本次付款单合同内工程款本期发生大于合同结算价公式中的"本次"含义)
		BigDecimal conPayAmtExceptThisTime=FDCHelper.ZERO;//合同实付款(不含本次)=prjPriceInCon-thisTimePrjPriceInCon
		BigDecimal totalPrice=FDCHelper.ZERO;//合同实付款(不含本次)+本次付款单合同内工程款本期发生=conPayAmtExceptThisTime+thisTimePayAmt
		if(paymentSet!=null&&paymentSet.size()>0){
			paymentBill=(PaymentBillInfo)paymentSet.iterator().next();//此处不考虑有多少付款单,无论有几条付款单，对应的合同结算金额都是唯一的
			String contractId=paymentBill.getContractBillId();
			boolean isContractWithTxt=FDCUtils.isContractBill(null, contractId);
			//如果是无文本合同就不应该有下续逻辑控制
			if(isContractWithTxt){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				if(!contract.isHasSettled()){
					return retValue;
				}
				settlePrice=FDCHelper.toBigDecimal(contract.getSettleAmt(),2);//合同结算金额
				
				prjPriceInCon=ContractClientUtils.getPayAmt(contractId);//合同实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计
				
				if(paymentSet.size()==1){
					//注意本次的合同实付款应该如此取数:已关闭的付款申请单应当取对应的该付款单合同内工程款,未关闭的付款申请单应该取付款申请单合同内工程款合计
					SelectorItemCollection selector=new SelectorItemCollection();
					selector.add("hasClosed");
					selector.add("projectPriceInContract");
					PayRequestBillInfo payReqBill=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())),selector);
					if(payReqBill.isHasClosed()){//已经关闭
						thisTimePrjPriceInCon=FDCHelper.toBigDecimal(paymentBill.getProjectPriceInContract(),2);
					}else{
						thisTimePrjPriceInCon=FDCHelper.toBigDecimal(payReqBill.getProjectPriceInContract(),2);
					}
				}else{//此处一定是多张付款单批量审批
					for (Iterator iter=paymentSet.iterator();iter.hasNext();) {
						paymentBill=(PaymentBillInfo)iter.next();
						SelectorItemCollection selector=new SelectorItemCollection();
						selector.add("hasClosed");
						selector.add("projectPriceInContract");
						PayRequestBillInfo payReqBill=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())),selector);
						if(payReqBill.isHasClosed()){//已经关闭
							thisTimePrjPriceInCon=FDCHelper.toBigDecimal(FDCHelper.add(thisTimePrjPriceInCon, paymentBill.getProjectPriceInContract()),2);
						}else{
							thisTimePrjPriceInCon=FDCHelper.toBigDecimal(FDCHelper.add(thisTimePrjPriceInCon, payReqBill.getProjectPriceInContract()),2);
						}
					}
				}
				conPayAmtExceptThisTime=FDCHelper.toBigDecimal(FDCHelper.subtract(prjPriceInCon, thisTimePrjPriceInCon),2);
				totalPrice=FDCHelper.toBigDecimal(FDCHelper.add(conPayAmtExceptThisTime,thisTimePayAmt),2);
				if(settlePrice!=null && settlePrice.compareTo(totalPrice)==-1){
					retValue=false;
				}
			}
		}
		return retValue;
	}
	/**
	 * 在合同最终结算后,付款单提交、审批时，如果已审批的付款单合同内工程累计金额加本期发生大于合同结算价
	 * 则提示“付款单合同内工程款累计金额不能大于合同结算价”  by cassiel_peng  2009-08-12 <p>
	 * 由于部分逻辑发生变化：在合同最终结算后，付款单提交、审批时,如果合同实付款(不含本次)加本次付款单合同内工程款本期发生大于合同结算价则提示，
	 * 且不能提交或审批通过。此方法不再使用，作废 by cassiel_peng 2009-12-02
	 * @see boolean com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils.checkProjectPriceInContract(Set paymentSet)<p>
	 */
	public static boolean checkForSaveSubmitAudit(/*PaymentBillInfo paymentBill*/Set paymenSet) throws Exception{
		
		boolean retValue=true;
		PaymentBillInfo paymentBill=null;
		BigDecimal settlePrice=FDCHelper.ZERO;//合同结算单金额
		BigDecimal thisTimeAmt=FDCHelper.ZERO;//本次付款单的合同内工程金额
		BigDecimal beforeThisTime=FDCHelper.ZERO;//本次付款单之前的已审批的付款单的合同内工程合计金额
		BigDecimal totalPrice=FDCHelper.ZERO;//已审批的付款单的合同内工程合计金额与本次付款单金额之和
		if(paymenSet!=null&&paymenSet.size()>0){
			paymentBill=(PaymentBillInfo)paymenSet.iterator().next();//此处不考虑有多少付款单,无论有几条付款单，对应的合同结算金额都是唯一的
			String contractId=paymentBill.getContractBillId();
			boolean isContractWithTxt=FDCUtils.isContractBill(null, contractId);
			//如果是无文本合同就不应该有下续逻辑控制    by Cassiel_peng  2008-8-27
			if(isContractWithTxt){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				if(!contract.isHasSettled()){
					return retValue;
				}
				settlePrice=FDCHelper.toBigDecimal(contract.getSettleAmt(),2);
				
				EntityViewInfo view=new EntityViewInfo(); 
				FilterInfo filter=new FilterInfo();
				if(paymenSet.size()==1){//如果只有一张付款单
					thisTimeAmt=FDCHelper.toBigDecimal(paymentBill.getProjectPriceInContract(),2);
					view.getSelector().add("id");
					//合同结算之后,合同实体上的settleAmt(结算价)与最终结算单的 totalSettlePrice(累计结算金额)是相同的
					view.getSelector().add("settleAmt");
				}else{//多张付款单一起审批
					for (Iterator iter=paymenSet.iterator();iter.hasNext();) {
						paymentBill=(PaymentBillInfo)iter.next();
						thisTimeAmt=FDCHelper.toBigDecimal(FDCHelper.add(thisTimeAmt,FDCHelper.toBigDecimal(paymentBill.getProjectPriceInContract(),2)),2);
					}
				}
				
//				 TypeInfo payType=paymentBill.getFdcPayType().getPayType();有时候取不到数，所以还是直接写sql查吧！
				 /* FDCSQLBuilder builder=new FDCSQLBuilder();
				 builder.appendSql("select FPayTypeId from T_FDC_Type type \n ");
				 builder.appendSql("inner join T_FDC_PaymentType paytype on paytype.FPayTypeID=type.fid \n ");
				 builder.appendSql("inner join T_CAS_PaymentBill pay on paytype.Fid=pay.FFdcPayTypeID \n ");
				 builder.appendSql("where pay.fid=? \n ");
				 builder.addParam(paymentBill.getId().toString());
				 IRowSet rowSet=builder.executeQuery();
				 if(rowSet.next()){
					 String payTypeId=rowSet.getString("FPayTypeId");
					 if(payTypeId!=null&&!"".equals(payTypeId)){
//						 boolean isProgressAmt= payTypeId.getId().toString().equals(PaymentTypeInfo.progressID);
						 boolean isProgressAmt= payTypeId.equals(PaymentTypeInfo.progressID);
						 if(isProgressAmt){//如果为进度款
							 return retValue;
						 }else{//如果为保修款或者是结算款
		*/					 	view=new EntityViewInfo();
								view.getSelector().add("id");
								view.getSelector().add("projectPriceInContract");
								view.getSelector().add("billStatus");
								
								filter=new FilterInfo();
								filter.getFilterItems().add(new FilterItemInfo("contractBillId",paymentBill.getContractBillId().toString()));
								filter.getFilterItems().add(new FilterItemInfo("billStatus",new Integer(
										com.kingdee.eas.fi.cas.BillStatusEnum.AUDITED_VALUE),
										CompareType.GREATER_EQUALS));
								filter.getFilterItems().add(new FilterItemInfo("id",paymentBill.getId().toString(),CompareType.NOTINCLUDE));//本次付款单的合同内工程金额
								view.setFilter(filter);
								
								IPaymentBill payment=PaymentBillFactory.getRemoteInstance();
								PaymentBillCollection _coll=payment.getPaymentBillCollection(view);
								PaymentBillInfo info = null;
								for(Iterator it = _coll.iterator();it.hasNext();){
									info = (PaymentBillInfo) it.next();
									beforeThisTime = beforeThisTime.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract(),2));
								}
								totalPrice=FDCHelper.add(beforeThisTime, thisTimeAmt);
								if(settlePrice!=null && settlePrice.compareTo(totalPrice)==-1){
									retValue=false;
								}
//						 }
//					 }
//				 }
			}
		}
	return retValue;
	}
}





























