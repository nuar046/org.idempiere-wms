/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2010 Heng Sin Low                							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.idempiere.component;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.base.event.LoginEventData;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.MDDOrderLine;
import org.eevolution.model.MWMInOutBoundLine;
import org.osgi.service.event.Event;

/**
 *
 * @author hengsin (new Event ModelValidator regime)
 * @author Victor Perez,
 * @contributor red1@red1.org (refactoring to new OSGi framework)
 *
 */
public class WMS_Validator extends AbstractEventHandler {
	private static CLogger log = CLogger.getCLogger(MFG_Validator.class);
	private String trxName = "";
	private PO po = null;
	@Override
	protected void initialize() { 
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, I_DD_OrderLine.Table_Name);
		log.info("WMS MODEL VALIDATOR IS NOW INITIALIZED");
	}

	@Override
	protected void doHandleEvent(Event event) {
		String type = event.getTopic();
		
		if (type.equals(IEventTopics.AFTER_LOGIN)) {
			LoginEventData eventData = getEventData(event);
			log.fine(" topic="+event.getTopic()+" AD_Client_ID="+eventData.getAD_Client_ID()
					+" AD_Org_ID="+eventData.getAD_Org_ID()+" AD_Role_ID="+eventData.getAD_Role_ID()
					+" AD_User_ID="+eventData.getAD_User_ID());
		}
		else 
		{
			setPo(getPO(event));
			setTrxName(po.get_TrxName());
			log.info(" topic="+event.getTopic()+" po="+po);
			if (po instanceof MDDOrderLine && IEventTopics.PO_AFTER_CHANGE == type)
			{
				MDDOrderLine oline = (MDDOrderLine)po;
				Integer WM_InOutBoundLine_ID = (Integer) oline.get_Value(MWMInOutBoundLine.COLUMNNAME_WM_InOutBoundLine_ID);
				if(WM_InOutBoundLine_ID != null && WM_InOutBoundLine_ID.intValue() > 0 && oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) >= 0)
				{	
					MWMInOutBoundLine obline = new MWMInOutBoundLine(oline.getCtx(),WM_InOutBoundLine_ID, trxName);
					obline.setPickedQty(oline.getQtyDelivered());
					obline.saveEx(trxName);	
				}
			}
	
		}
	}

	private void setPo(PO eventPO) {
		 po = eventPO;
	}

	private void setTrxName(String get_TrxName) {
		trxName = get_TrxName;		
	}

}
