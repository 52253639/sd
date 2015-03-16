/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;


/**
 * output class name
 */
public class MarketQueryUI extends AbstractMarketQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketQueryUI.class);
    FilterInfo extendfilterInfo=null; 
    /**
     * output class constructor
     */
    public MarketQueryUI() throws Exception
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
    public void setExtendFilterItem(FilterInfo filterInfo)
    {
        extendfilterInfo=filterInfo;
    }
    public FilterInfo getFilterInfo()
    {
    	super.getFilterInfo();
        int index = 0;    
        StringBuffer maskString = new StringBuffer("");
        
        this.filterInfo = new FilterInfo();

        if (prmtJlb.getText() != null
                && prmtJlb.getText().length() != 0)      
        {
            FilterItemInfo filterItemInfo = new FilterItemInfo(
                    "movementtype.name",
                    prmtJlb.getText(), CompareType.EQUALS);
            this.filterInfo.getFilterItems().add(filterItemInfo);
            maskString.append("#" + index++);
        }
        this.filterInfo.setMaskString(maskString.toString());
        
        try
        {
            if(extendfilterInfo!=null)
            {
                filterInfo.mergeFilter(extendfilterInfo,"AND");
            }                      
        }
        catch(BOSException boex)
        {
            boex.printStackTrace();            
        }
//      为了解决问题:如果过滤UI选上“直接以该方案进入”，并且没有以保过的其它方案作为直接进入方案时，默认日期不起作用。
        if(filterInfo.getFilterItems().size() <= 0)
        {
            FilterItemInfo templateFilterItemInfo = new FilterItemInfo("id", new Integer(-1), CompareType.NOTEQUALS);

            filterInfo.getFilterItems().add(templateFilterItemInfo);
        }
        return this.filterInfo;
        
    }
    
}