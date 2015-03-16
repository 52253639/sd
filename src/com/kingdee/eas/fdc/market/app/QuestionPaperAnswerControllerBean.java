package com.kingdee.eas.fdc.market.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.market.DocSubItemTypeEnum;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class QuestionPaperAnswerControllerBean extends AbstractQuestionPaperAnswerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.QuestionPaperAnswerControllerBean");
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	handleIntermitNumber(ctx, (QuestionPaperAnswerInfo) model);
    	
    	updateCustAssisInfo(ctx,(QuestionPaperAnswerInfo) model);
    	return super._submit(ctx, model);
	}
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		handleIntermitNumber(ctx, (QuestionPaperAnswerInfo) model);
		
		updateCustAssisInfo(ctx,(QuestionPaperAnswerInfo) model);
		return super._save(ctx, model);
	}
    
    
    //更新客户辅助资料信息
    private void updateCustAssisInfo(Context ctx,QuestionPaperAnswerInfo questInfo) throws BOSException, EASBizException {
    	if(true) return;
    	
    	questInfo.getQuestionPaper();
    	SHECustomerInfo sheCustInfo = questInfo.getSheCustomer();
    	if(sheCustInfo==null) return;	
    	
    	Map optionSelectedMap = new HashMap();			//为了校验答案是否被选中
    	QuestionPaperAnswerEntryCollection answerEntryColl = questInfo.getEntrys();
    	for (int i = 0; i < answerEntryColl.size(); i++) {
    		QuestionPaperAnswerEntryInfo answerEntryInfo = answerEntryColl.get(i);
    		optionSelectedMap.put(answerEntryInfo.getOption(), answerEntryInfo);
		}
    	
    	//CRMConstants.RECEPTION_TYPE_GROUP_ID;  接待方式
    	//CRMConstants.HABITATION_AREA_GROUP_ID;  居住区域
    	//CRMConstants.WORK_AREA_GROUP_ID;  工作区域
    	//CRMConstants.BIZ_SCOPE_GROUP_ID;  业务范围  	
    	//CRMConstants.KNOW_TYPE_GROUP_ID;  认知途径 
    	
    	DocumentItemCollection itemColl = DocumentItemFactory.getLocalInstance(ctx)
    					.getDocumentItemCollection("select *,optinos.relationId,subjectId.subItemType where subjectId.documentId.id = '"+questInfo.getQuestionPaper().getDocumentId()+"'");
    	for (int i = 0; i < itemColl.size(); i++) {
    		DocumentItemInfo itemInfo = itemColl.get(i);
    		if(itemInfo.getSubjectId().getSubItemType()!=null && itemInfo.getSubjectId().getSubItemType().equals(DocSubItemTypeEnum.Relation)) {
    			if(itemInfo.getRelatTypeId()==null) continue; 
    			
    			DocumentOptionCollection optionColl = itemInfo.getOptions();
    			for (int j = 0; j < optionColl.size(); j++) {
    				DocumentOptionInfo optionInfo = optionColl.get(j);
					if(optionSelectedMap.get(optionInfo.getId().toString())!=null) {
		    			//itemInfo.getRelatTypeId() 找到对于的名称 nameDefine 客户辅助资料类别id
						//optionInfo.getRelationId() 找到对应的 辅助资料对象  assisInfo   //客户辅助资料id
		    			//sheCustInfo.put(nameDefine, assisInfo);
					}
				}
    		}
		}
    	
//    	SelectorItemCollection upSelector = new SelectorItemCollection();
//    	upSelector.add(new SelectorItemInfo(nameDefine));
//    	SHECustomerFactory.getLocalInstance(ctx).updatePartial(sheCustInfo,upSelector);
    	
    }
    
    
    private static void handleIntermitNumber(Context ctx, QuestionPaperAnswerInfo info) throws BOSException, CodingRuleException, EASBizException {
		// 如果用户在客户端手工选择了断号,则此处不必在抢号
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();

		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}

		if (iCodingRuleManager.isExist(info, costUnitId)) {
			// 选择了断号支持或者没有选择新增显示,获取并设置编号
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
			// 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
			{
				// 启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}
}