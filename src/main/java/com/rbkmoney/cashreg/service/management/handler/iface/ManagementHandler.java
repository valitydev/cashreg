package com.rbkmoney.cashreg.service.management.handler.iface;

import com.rbkmoney.cashreg.domain.SourceData;
import com.rbkmoney.cashreg.service.mg.aggregate.mapper.ChangeType;
import com.rbkmoney.damsel.cashreg_processing.CashReg;
import com.rbkmoney.damsel.cashreg_processing.Change;

public interface ManagementHandler {

    default boolean filter(Change change, CashReg cashReg) {
        return getChangeType().getFilter().match(change);
    }

    SourceData handle(Change change, CashReg cashReg);

    ChangeType getChangeType();

}
