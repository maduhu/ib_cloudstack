package org.apache.cloudstack.api;

import com.cloud.event.EventTypes;
import com.cloud.exception.*;
import com.cloud.ucs.manager.UcsManager;
import com.cloud.user.Account;
import org.apache.cloudstack.api.response.SuccessResponse;
import org.apache.cloudstack.api.response.UcsBladeResponse;
import org.apache.log4j.Logger;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: frank
 * Date: 9/13/13
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
@APICommand(name="disassociateUcsProfileFromBlade", description="disassociate a profile from a blade", responseObject=UcsBladeResponse.class)
public class DisassociateUcsProfileCmd extends  BaseAsyncCmd {
    private static Logger logger = Logger.getLogger(DisassociateUcsProfileCmd.class);

    @Inject
    private UcsManager mgr;

    @Parameter(name=ApiConstants.UCS_BLADE_ID, type=CommandType.UUID, entityType=UcsBladeResponse.class, description="blade id", required=true)
    private Long bladeId;

    @Override
    public String getEventType() {
        return EventTypes.EVENT_UCS_DISASSOCIATED_PROFILE;
    }

    @Override
    public String getEventDescription() {
        return "disassociate a profile from blade";
    }

    @Override
    public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException, ResourceAllocationException, NetworkRuleConflictException {
        try {
            UcsBladeResponse rsp = mgr.disassociateProfile(bladeId);
            rsp.setResponseName(getCommandName());
            this.setResponseObject(rsp);
        } catch(Exception e) {
            logger.warn(e.getMessage(), e);
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "disassociateucsprofilefrombladeresponse";
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM;
    }
}
