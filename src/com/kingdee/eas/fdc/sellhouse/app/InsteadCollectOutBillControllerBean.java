//package com.kingdee.eas.fdc.sellhouse.app;
//
//import org.apache.log4j.Logger;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import com.kingdee.bos.*;
//import com.kingdee.bos.dao.IObjectValue;
//
//import java.lang.String;
//import java.math.BigDecimal;
//
//import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
//import com.kingdee.eas.base.codingrule.ICodingRuleManager;
//import com.kingdee.eas.basedata.org.OrgUnitInfo;
//import com.kingdee.eas.basedata.person.PersonInfo;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.bos.metadata.entity.SelectorItemInfo;
//import com.kingdee.bos.dao.IObjectPK;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory;
//import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
//import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
//import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
//import com.kingdee.eas.fdc.sellhouse.SellHouseException;
//import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
//import com.kingdee.eas.fi.cas.ReceivingBillInfo;
//import com.kingdee.eas.framework.CoreBaseCollection;
//import com.kingdee.eas.util.SysUtil;
//import com.kingdee.eas.util.app.ContextUtil;
//
//public class InsteadCollectOutBillControllerBean extends AbstractInsteadCollectOutBillControllerBean
//{
//    private static Logger logger =
//        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.InsteadCollectOutBillControllerBean");
//
//    /**
//     * 批量代付
//     * yinshujuan
//     */
//	protected void _generateNewData(Context ctx, PersonInfo personInfo,
//			Date bizDate, Set rows) throws BOSException, SellHouseException{
//		if(rows==null||rows.size()==0)SysUtil.abort();
//		InsteadCollectOutBillInfo info = null;
//		PurchaseElsePayListEntryInfo entryInfo = null;
//		Iterator it = rows.iterator();
//		CoreBaseCollection coll = new CoreBaseCollection();
//		try
//		{
//		while(it.hasNext()) {
//				Map rowInfo = (Map)it.next();
////				String number = (String) rowInfo.get("number");//编码
//				String curAmountStr = (String)rowInfo.get("curAmount");
//				BigDecimal curAmount = new BigDecimal(curAmountStr);//本次代付金额
//				String remark = (String) rowInfo.get("remark");//备注
//				String elseEntryID = (String)rowInfo.get("elseEntryID");
//				String sellProjectId = rowInfo.get("sellProjectId").toString();
//				SellProjectInfo sellProject  = getSellProjectById(sellProjectId,ctx);
//				
//				entryInfo = getElsePayEntryInfo(ctx,elseEntryID); //其它应付分录
//				info = new InsteadCollectOutBillInfo();
//				
//				if(sellProject!=null){
//					info.setSellproject(sellProject);
//				}
//				
////				InsteadCollectOutBillFactory.getLocalInstance(ctx).addnew(coll);
//				
//				info.setManInsteadPay(personInfo);//本次代付人员
//				info.setBizDate(bizDate);//代付日期
//				//设置编码
//				setObjectNumber(ctx,info);
//				
////				info.setNumber(number);
//				info.setMoneyInsteadCur(curAmount);
//				info.setDescription(remark);
//				info.setPurchaseElsePayListEntry(entryInfo);
//				info.setState(FDCBillStateEnum.SUBMITTED);
////				SelectorItemCollection sic = new SelectorItemCollection();
////				sic.add(new SelectorItemInfo("number"));
////				sic.add(new SelectorItemInfo("moneyInsteadCur"));
////				sic.add(new SelectorItemInfo("manInsteadPay"));
////				sic.add(new SelectorItemInfo("purchaseElsePayListEntry"));
////				sic.add(new SelectorItemInfo("description"));
////				sic.add(new SelectorItemInfo("bizDate"));
////				sic.add(new SelectorItemInfo("state"));
//				coll.add(info);
////				InsteadCollectOutBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
//		}
//		
//			InsteadCollectOutBillFactory.getLocalInstance(ctx).addnew(coll);
//		} catch (EASBizException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	private SellProjectInfo getSellProjectById(String id,Context ctx){
//		SellProjectInfo info = null;
//		try {
//			SelectorItemCollection selector  = new SelectorItemCollection();
//			selector.add(new SelectorItemInfo("id"));
//			selector.add(new SelectorItemInfo("name"));
//			info = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(id),selector);
//		} catch (EASBizException e) {
//			logger.error("获得项目失败!"+e.getMessage());
//		} catch (BOSException e) {
//			logger.error("获得项目失败!"+e.getMessage());
//		}
//		
//		return info;
//	}
//	
//	private PurchaseElsePayListEntryInfo getElsePayEntryInfo(Context ctx,String id) throws EASBizException, BOSException {
//		PurchaseElsePayListEntryInfo entryInfo = null;
//		entryInfo = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryInfo(new ObjectUuidPK(id));
//		return entryInfo;
//	}
//
//	protected IObjectPK _save(Context ctx, IObjectValue model)
//			throws BOSException, EASBizException
//	{
//		InsteadCollectOutBillInfo billInfo = (InsteadCollectOutBillInfo)model;
//		if(billInfo.getNumber()==null) {
//			setObjectNumber(ctx,billInfo);
//		}
//		return super._save(ctx, model);
//	}
//
//	protected IObjectPK _submit(Context ctx, IObjectValue model)
//			throws BOSException, EASBizException
//	{
//		InsteadCollectOutBillInfo billInfo = (InsteadCollectOutBillInfo)model;
//		if(billInfo.getNumber()==null) {
//			setObjectNumber(ctx,billInfo);
//		}
//		return super._submit(ctx, model);
//	}
//	/**
//	 * 根据编码规则自动设置单据编号 
//	 * @author yinshujuan
//	 * @param ctx
//	 * @param billInfo
//	 * @throws BOSException
//	 * @throws EASBizException
//	 */
//	private void setObjectNumber(Context ctx,InsteadCollectOutBillInfo billInfo) throws BOSException, EASBizException {
//		OrgUnitInfo crrOu = ContextUtil.getCurrentOrgUnit(ctx);   //当前组织;
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
//		if(!iCodingRuleManager.isExist(new InsteadCollectOutBillInfo(), crrOu.getId().toString())){
//			String number = iCodingRuleManager.getNumber(new ReceivingBillInfo(), crrOu.getId().toString());
//			billInfo.setNumber(number);
//		}
//	}
//	
//}