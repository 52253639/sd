package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

public class QuestionHelp {

	 /**
	 * 描述：<方法描述>根据试题pk 和试题类型获取试题的全部信息
	 * @param  <参数描述>pk 试题pk 
	 * 			questionStyle 试题类型
	 * @return  <返回值描述> 试题全部信息
	 *
	 * @author:baihui_li
	 * 创建时间：2009-3-12 <p>
	 * 
	 * @see  <相关的类>
	 */
	public static QuestionBaseInfo getQuestion(String pk,String questionStyle) throws Exception, BOSException{
		 QuestionBaseInfo questionInfo=null;
		 
//			简答题
	        if(questionStyle.equals(QuestionStyle.ASKQUESTION_VALUE)){
	        	questionInfo =AskQuestionFactory.getRemoteInstance().getAskQuestionInfo(pk);
	        }
	        //判断题   
	        else  if(questionStyle.equals(QuestionStyle.JUDGEQUESTION_VALUE)){
	        	questionInfo =JudgeQuestionFactory.getRemoteInstance().getJudgeQuestionInfo(pk);
	       }
	        
	        //填空题 
	        else  if(questionStyle.equals(QuestionStyle.FILLBLANKQUESTION_VALUE)){
	        	questionInfo =FillBlankQuestionFactory.getRemoteInstance().getFillBlankQuestionInfo(pk);
	       } 
	        
	        //单选题
	        else  if(questionStyle.equals(QuestionStyle.SINGLESELECTQUESTION_VALUE)){
	        	EntityViewInfo viewInfo = new EntityViewInfo();
	    		FilterInfo fi = new FilterInfo();
	    		fi.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.SINGLESELECTQUESTION_VALUE, CompareType.EQUALS));
	    		viewInfo.setFilter(fi);
	    		
	        	questionInfo =SelectQuestionFactory.getRemoteInstance().getSelectQuestionCollection(viewInfo).get(0);
	        		
	         
	       } 
	        
	        // 多选题
	        else  if(questionStyle.equals(QuestionStyle.MUTISELECTQUESTION_VALUE)){
	        	EntityViewInfo viewInfo = new EntityViewInfo();
	    		FilterInfo fi = new FilterInfo();
	    		fi.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.MUTISELECTQUESTION_VALUE, CompareType.EQUALS));
	    		viewInfo.setFilter(fi);
	    		
	        	questionInfo =SelectQuestionFactory.getRemoteInstance().getSelectQuestionCollection(viewInfo).get(0);
	       } 
	        
		 
		 return questionInfo;
	 }

}
