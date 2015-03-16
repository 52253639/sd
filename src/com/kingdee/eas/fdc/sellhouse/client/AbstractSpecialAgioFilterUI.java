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
public abstract class AbstractSpecialAgioFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSpecialAgioFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kdcSpecialAgio;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cmbSellState;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPayStyle;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtsellproject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAgio;
    protected com.kingdee.bos.ctrl.swing.KDTextField kDTextField1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker1;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker kDDatePicker2;
    /**
     * output class constructor
     */
    public AbstractSpecialAgioFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSpecialAgioFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdcSpecialAgio = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cmbSellState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtPayStyle = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtsellproject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtAgio = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTextField1 = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDDatePicker1 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDDatePicker2 = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kdcSpecialAgio.setName("kdcSpecialAgio");
        this.cmbSellState.setName("cmbSellState");
        this.prmtPayStyle.setName("prmtPayStyle");
        this.prmtsellproject.setName("prmtsellproject");
        this.prmtBuilding.setName("prmtBuilding");
        this.prmtAgio.setName("prmtAgio");
        this.kDTextField1.setName("kDTextField1");
        this.kDDatePicker1.setName("kDDatePicker1");
        this.kDDatePicker2.setName("kDDatePicker2");
        // CustomerQueryPanel
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(60);		
        this.kDLabelContainer3.setVisible(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(60);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(60);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(60);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(60);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(60);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(60);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(60);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // kdcSpecialAgio		
        this.kdcSpecialAgio.setText(resHelper.getString("kdcSpecialAgio.text"));
        this.kdcSpecialAgio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    kdcSpecialAgio_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // cmbSellState		
        this.cmbSellState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum").toArray());		
        this.cmbSellState.setVisible(false);
        // prmtPayStyle		
        this.prmtPayStyle.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");		
        this.prmtPayStyle.setDisplayFormat("$name$");		
        this.prmtPayStyle.setEditFormat("$number$");		
        this.prmtPayStyle.setCommitFormat("$number$");
        this.prmtPayStyle.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtPayStyle_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtPayStyle.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtPayStyle_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtsellproject		
        this.prmtsellproject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.prmtsellproject.setDisplayFormat("$name$");		
        this.prmtsellproject.setEditFormat("$number$");		
        this.prmtsellproject.setCommitFormat("$number$");		
        this.prmtsellproject.setRequired(true);
        // prmtBuilding		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$number$");		
        this.prmtBuilding.setCommitFormat("$number$");
        this.prmtBuilding.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtBuilding_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtBuilding.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtBuilding_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtAgio		
        this.prmtAgio.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.AgioQuery");		
        this.prmtAgio.setDisplayFormat("$name$");		
        this.prmtAgio.setEditFormat("$number$");		
        this.prmtAgio.setCommitFormat("$number$");
        this.prmtAgio.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtAgio_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAgio.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAgio_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTextField1
        // kDDatePicker1
        // kDDatePicker2
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
        this.setBounds(new Rectangle(10, 10, 1016, 600));
        this.setLayout(null);
        kDLabelContainer3.setBounds(new Rectangle(17, 172, 213, 19));
        this.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(230, 51, 213, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(6, 18, 213, 19));
        this.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(230, 18, 213, 19));
        this.add(kDLabelContainer6, null);
        kDLabelContainer7.setBounds(new Rectangle(6, 51, 213, 19));
        this.add(kDLabelContainer7, null);
        kDLabelContainer8.setBounds(new Rectangle(6, 117, 213, 19));
        this.add(kDLabelContainer8, null);
        kDLabelContainer1.setBounds(new Rectangle(6, 84, 213, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(230, 84, 213, 19));
        this.add(kDLabelContainer2, null);
        kdcSpecialAgio.setBounds(new Rectangle(230, 117, 213, 19));
        this.add(kdcSpecialAgio, null);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(cmbSellState);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(prmtPayStyle);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(prmtsellproject);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(prmtBuilding);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(prmtAgio);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(kDTextField1);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDDatePicker1);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kDDatePicker2);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.SpecialAgioFilterUIHandler";
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
     * output kdcSpecialAgio_actionPerformed method
     */
    protected void kdcSpecialAgio_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtPayStyle_willShow method
     */
    protected void prmtPayStyle_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtPayStyle_willCommit method
     */
    protected void prmtPayStyle_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtBuilding_willShow method
     */
    protected void prmtBuilding_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtBuilding_willCommit method
     */
    protected void prmtBuilding_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtAgio_willCommit method
     */
    protected void prmtAgio_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtAgio_willShow method
     */
    protected void prmtAgio_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SpecialAgioFilterUI");
    }




}