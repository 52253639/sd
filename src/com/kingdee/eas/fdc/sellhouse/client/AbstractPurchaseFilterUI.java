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
public abstract class AbstractPurchaseFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPurchaseFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox4;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox5;
    protected EntityViewInfo queryPurchaseQuery = null;
    protected IMetaDataPK queryPurchaseQueryPK;
    /**
     * output class constructor
     */
    public AbstractPurchaseFilterUI() throws Exception
    {
        super();
        this.defaultObjectName = "queryPurchaseQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPurchaseFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        queryPurchaseQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "PurchaseQuery");
        this.kDCheckBox1 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox2 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox3 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox4 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox5 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDCheckBox1.setName("kDCheckBox1");
        this.kDCheckBox2.setName("kDCheckBox2");
        this.kDCheckBox3.setName("kDCheckBox3");
        this.kDCheckBox4.setName("kDCheckBox4");
        this.kDCheckBox5.setName("kDCheckBox5");
        // CustomerQueryPanel		
        this.setToolTipText(resHelper.getString("this.toolTipText"));
        // kDCheckBox1		
        this.kDCheckBox1.setText(resHelper.getString("kDCheckBox1.text"));
        this.kDCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kDCheckBox1_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDCheckBox2		
        this.kDCheckBox2.setText(resHelper.getString("kDCheckBox2.text"));
        this.kDCheckBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kDCheckBox2_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDCheckBox3		
        this.kDCheckBox3.setText(resHelper.getString("kDCheckBox3.text"));
        this.kDCheckBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kDCheckBox3_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDCheckBox4		
        this.kDCheckBox4.setText(resHelper.getString("kDCheckBox4.text"));
        this.kDCheckBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kDCheckBox4_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDCheckBox5		
        this.kDCheckBox5.setText(resHelper.getString("kDCheckBox5.text"));
        this.kDCheckBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kDCheckBox5_itemStateChanged(e);
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
        this.setBounds(new Rectangle(10, 10, 160, 160));
        this.setLayout(null);
        kDCheckBox1.setBounds(new Rectangle(10, 10, 140, 19));
        this.add(kDCheckBox1, null);
        kDCheckBox2.setBounds(new Rectangle(10, 34, 140, 19));
        this.add(kDCheckBox2, null);
        kDCheckBox3.setBounds(new Rectangle(10, 58, 140, 19));
        this.add(kDCheckBox3, null);
        kDCheckBox4.setBounds(new Rectangle(10, 82, 140, 19));
        this.add(kDCheckBox4, null);
        kDCheckBox5.setBounds(new Rectangle(10, 106, 140, 19));
        this.add(kDCheckBox5, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.PurchaseFilterUIHandler";
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
        this.queryPurchaseQuery = (EntityViewInfo)ov;
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
     * output kDCheckBox1_itemStateChanged method
     */
    protected void kDCheckBox1_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kDCheckBox2_itemStateChanged method
     */
    protected void kDCheckBox2_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kDCheckBox3_itemStateChanged method
     */
    protected void kDCheckBox3_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kDCheckBox4_itemStateChanged method
     */
    protected void kDCheckBox4_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kDCheckBox5_itemStateChanged method
     */
    protected void kDCheckBox5_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PurchaseFilterUI");
    }




}