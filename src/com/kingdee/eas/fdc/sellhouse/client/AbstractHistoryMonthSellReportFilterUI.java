/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractHistoryMonthSellReportFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractHistoryMonthSellReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioProductType;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton radioBuilding;
    protected com.kingdee.bos.ctrl.swing.KDButtonGroup groupShowType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Balance;
    /**
     * output class constructor
     */
    public AbstractHistoryMonthSellReportFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractHistoryMonthSellReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.radioProductType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.radioBuilding = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.groupShowType = new com.kingdee.bos.ctrl.swing.KDButtonGroup();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Balance = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel2.setName("kDPanel2");
        this.radioProductType.setName("radioProductType");
        this.radioBuilding.setName("radioBuilding");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.f7Balance.setName("f7Balance");
        // CustomerQueryPanel
        // kDPanel2		
        this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel2.border.title")));
        // radioProductType		
        this.radioProductType.setText(resHelper.getString("radioProductType.text"));		
        this.radioProductType.setSelected(true);
        // radioBuilding		
        this.radioBuilding.setText(resHelper.getString("radioBuilding.text"));
        // groupShowType
        this.groupShowType.add(this.radioProductType);
        this.groupShowType.add(this.radioBuilding);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // f7Balance		
        this.f7Balance.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7BalancePeriodQuery");		
        this.f7Balance.setDisplayFormat("$number$");		
        this.f7Balance.setEditFormat("$number$");		
        this.f7Balance.setCommitFormat("$number$");		
        this.f7Balance.setRequired(true);
        this.f7Balance.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SaleBalance_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 300));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 400, 300));
        kDPanel2.setBounds(new Rectangle(14, 62, 359, 80));
        this.add(kDPanel2, new KDLayout.Constraints(14, 62, 359, 80, 0));
        kDLabelContainer1.setBounds(new Rectangle(23, 26, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(23, 26, 270, 19, 0));
        //kDPanel2
        kDPanel2.setLayout(null);        radioProductType.setBounds(new Rectangle(34, 29, 106, 19));
        kDPanel2.add(radioProductType, null);
        radioBuilding.setBounds(new Rectangle(191, 29, 99, 19));
        kDPanel2.add(radioBuilding, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(f7Balance);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.HistoryMonthSellReportFilterUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output f7SaleBalance_dataChanged method
     */
    protected void f7SaleBalance_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "HistoryMonthSellReportFilterUI");
    }




}