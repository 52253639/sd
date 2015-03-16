package com.kingdee.eas.fdc.market.app;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.hr.train.MonthEnum;


public class MarketCircsAnalyseFacadeControllerBean extends AbstractMarketCircsAnalyseFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.MarketCircsAnalyseFacadeControllerBean");
	
    protected SchemaSource readySchemaSource(RptParams params, Context ctx) throws BOSException, EASBizException
	{
		SchemaSource ss = new SchemaSource();
		boolean isRoomProperty = ((Boolean) params
				.getObjectElement("rdRoomType.value")).booleanValue();
		boolean isDate = ((Boolean) params
				.getObjectElement("rdDate.value")).booleanValue();
		String loc = this.getLoc(ctx);
		ProvinceInfo proInfo= (ProvinceInfo)params.getObjectElement("prmtprovince.value");
		CityInfo city =(CityInfo) params.getObjectElement("prmtarea.value");
		String syear =params.getObjectElement("txtSurveyYear.value").toString();
		String smonth =params.getObjectElement("surveyMonth.value").toString();
		MonthEnum month =(MonthEnum)params.getObjectElement("surveyMonth.value");
		//由于DB2报转换异常，将month从String转为int
		String str = month.getValue();
		int int_month = Integer.parseInt(str);
		String configFile = null;
		StringBuffer factSql = new StringBuffer();
		StringBuffer itemSql = new StringBuffer();
		SqlParams sqlParams = new SqlParams();
		StringBuffer mdx = new StringBuffer();
		if (isRoomProperty){
			configFile = "market_Circs.xml";
			factSql.append(" select r.FHouseType ");
			factSql.append(" as FFaceID,");
			factSql.append(" r.FplateArea ,r.FSalesArea,r.FSalesNum,r.FSalesMoney ");
			factSql.append(" from T_MAR_MarketSurveyEntry as r right join T_MAR_MarketSurvey as hd on r.FparentID=hd.FID ");
			factSql.append(" where hd.FProvinceID='"+proInfo.getId().toString()+"'" );
			factSql.append(" and hd.FAreaID='"+city.getId().toString()+"'" );
			factSql.append(" and hd.FSurveyYear="+syear);
			factSql.append(" and hd.FSurveyMonth="+int_month);
			ss.setDataItem("Fact", factSql.toString(), null);
			
			StringBuffer outLookSql = new StringBuffer();
			outLookSql.append("select distinct rmt.FID as FID,rmt.FName_"+loc+" as FName from T_MAR_HouseAnlysis as rmt");
			ss.setDataItem("Face", outLookSql.toString(),null);
	
			mdx.append(" with member [Face].[Totals] as 'sum([Face].members)',caption='合计'");
			mdx.append(" select {[Measures].Members} on columns,{[Face].members,[Face].[Totals]} on rows from Fact");
			ss.setCaller(this.getClass());
			ss.setFilename(configFile);
			ss.setMdx(mdx.toString());
		}
		if (isDate){
			configFile = "market_CircsDate.xml";
			factSql.append(" select to_date(to_char(r.Fdatemonth,'YYYY-MM-DD')) ");
			factSql.append(" as FFaceID,");
			factSql.append(" r.FplateArea ,r.FSalesArea,r.FSalesNum,r.FSalesMoney ");
			factSql.append(" from T_MAR_MarketSurveyEntry as r right join T_MAR_MarketSurvey as hd on r.FparentID=hd.FID ");
			factSql.append(" where hd.FProvinceID='"+proInfo.getId().toString()+"'" );
			factSql.append(" and hd.FAreaID='"+city.getId().toString()+"'" );
			factSql.append(" and hd.FSurveyYear="+syear);
			factSql.append(" and hd.FSurveyMonth="+int_month);
			ss.setDataItem("Fact", factSql.toString(), null);
//			Calendar  cal=Calendar.getInstance();  
//			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-DD");
//			if(syear != null && !syear.trim().equals("")){
//				cal.set(Calendar.YEAR, Integer.parseInt(syear));
//			}
//			if(month.getValue() != null && !month.getValue().trim().equals("")){
//				cal.set(Calendar.MONTH, Integer.parseInt(month.getValue())-1);
//			}
//		    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		    String StrDate ="";
//			for(int j = 1;j<=days;j++){
//				cal.set(Calendar.DATE, j);
//				Date FDate =cal.getTime();
//				String strdate =sdf.format(FDate);
//				if (StrDate.compareTo("")==0){
//					StrDate =strdate;
//				}
//				StrDate =StrDate+","+strdate;
//			}

			StringBuffer outLookSql = new StringBuffer();
			outLookSql.append("select distinct to_date(to_char(rmt.Fdatemonth,'YYYY-MM-DD')) as FID,to_date(to_char(rmt.Fdatemonth,'YYYY-MM-DD')) as FName from T_MAR_MarketSurveyEntry as rmt");
			outLookSql.append("  where YEAR(rmt.Fdatemonth) ='"+syear+"' and MONTH(rmt.Fdatemonth)='"+month.getValue()+"' order by Fid");
			ss.setDataItem("Face", outLookSql.toString(),null);
	
			mdx.append(" with member [Face].[Totals] as 'sum([Face].members)',caption='合计'");
			mdx.append(" select {[Measures].Members} on columns,{[Face].members,[Face].[Totals]} on rows from Fact");
			ss.setCaller(this.getClass());
			ss.setFilename(configFile);
			ss.setMdx(mdx.toString());
		}
		return ss;
	}
}