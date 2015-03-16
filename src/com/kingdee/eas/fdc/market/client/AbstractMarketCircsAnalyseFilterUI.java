/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractMarketCircsAnalyseFilterUI extends com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketCircsAnalyseFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSurveyYear;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDComboBox surveyMonth;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdDate;
    protected com.kingdee.bos.ctrl.swing.KDRadioButton rdRoomType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtprovince;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtarea;
    /**
     * output class constructor
     */
    public AbstractMarketCircsAnalyseFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketCircsAnalyseFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSurveyYear = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.surveyMonth = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.rdDate = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.rdRoomType = new com.kingdee.bos.ctrl.swing.KDRadioButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtprovince = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtarea = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.txtSurveyYear.setName("txtSurveyYear");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.surveyMonth.setName("surveyMonth");
        this.kDPanel1.setName("kDPanel1");
        this.rdDate.setName("rdDate");
        this.rdRoomType.setName("rdRoomType");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.prmtprovince.setName("prmtprovince");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.prmtarea.setName("prmtarea");
        // CustomerQueryPanel
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setBoundLabelAlignment(7);		
        this.kDLabelContainer4.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // txtSurveyYear		
        this.txtSurveyYear.setRequired(true);
        this.txtSurveyYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtSurveyYear_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelLength(100);		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setVisible(true);		
        this.kDLabelContainer5.setBoundLabelAlignment(7);		
        this.kDLabelContainer5.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // surveyMonth		
        this.surveyMonth.setVisible(true);		
        this.surveyMonth.addItems(EnumUtils.getEnumList("com.kingdee.eas.hr.train.MonthEnum").toArray());		
        this.surveyMonth.setRequired(true);		
        this.surveyMonth.setEnabled(true);		
        this.surveyMonth.setForeground(new java.awt.Color(0,0,0));
        this.surveyMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    surveyMonth_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.surveyMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    surveyMonth_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));
        // rdDate		
        this.rdDate.setText(resHelper.getString("rdDate.text"));
        // rdRoomType		
        this.rdRoomType.setText(resHelper.getString("rdRoomType.text"));		
        this.rdRoomType.setSelected(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setVisible(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // prmtprovince		
        this.prmtprovince.setQueryInfo("com.kingdee.eas.basedata.assistant.app.ProvinceQuery");		
        this.prmtprovince.setVisible(true);		
        this.prmtprovince.setEditable(true);		
        this.prmtprovince.setDisplayFormat("$name$");		
        this.prmtprovince.setEditFormat("$number$");		
        this.prmtprovince.setCommitFormat("$number$");		
        this.prmtprovince.setRequired(true);		
        this.prmtprovince.setEnabled(false);		
        this.prmtprovince.setForeground(new java.awt.Color(0,0,0));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setVisible(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.getBoundLabel().setForeground(new java.awt.Color(0,0,0));
        // prmtarea		
        this.prmtarea.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CityQuery");		
        this.prmtarea.setVisible(true);		
        this.prmtarea.setEditable(true);		
        this.prmtarea.setDisplayFormat("$name$");		
        this.prmtarea.setEditFormat("$number$");		
        this.prmtarea.setCommitFormat("$number$");		
        this.prmtarea.setRequired(true);		
        this.prmtarea.setEnabled(false);		
        this.prmtarea.setForeground(new java.awt.Color(0,0,0));
        this.prmtarea.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtarea_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtarea.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtarea_willShow(e);
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

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 150));
        this.setLayout(null);
        kDLabelContainer4.setBounds(new Rectangle(14, 50, 270, 19));
        this.add(kDLabelContainer4, null);
        kDLabelContainer5.setBounds(new Rectangle(301, 50, 270, 19));
        this.add(kDLabelContainer5, null);
        kDPanel1.setBounds(new Rectangle(6, 86, 573, 52));
        this.add(kDPanel1, null);
        kDLabelContainer1.setBounds(new Rectangle(13, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(302, 10, 270, 19));
        this.add(kDLabelContainer2, null);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtSurveyYear);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(surveyMonth);
        //kDPanel1
        kDPanel1.setLayout(null);        rdDate.setBounds(new Rectangle(213, 17, 84, 19));
        kDPanel1.add(rdDate, null);
        rdRoomType.setBounds(new Rectangle(64, 17, 79, 19));
        kDPanel1.add(rdRoomType, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtprovince);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(prmtarea);

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
	    return "com.kingdee.eas.fdc.market.app.MarketCircsAnalyseFilterUIHandler";
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
     * output txtSurveyYear_focusLost method
     */
    protected void txtSurveyYear_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output surveyMonth_actionPerformed method
     */
    protected void surveyMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output surveyMonth_itemStateChanged method
     */
    protected void surveyMonth_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtarea_willCommit method
     */
    protected void prmtarea_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtarea_willShow method
     */
    protected void prmtarea_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "MarketCircsAnalyseFilterUI");
    }




}