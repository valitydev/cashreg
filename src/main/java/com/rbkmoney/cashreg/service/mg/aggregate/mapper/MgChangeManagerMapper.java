package com.rbkmoney.cashreg.service.mg.aggregate.mapper;

import com.rbkmoney.cashreg.service.mg.aggregate.mapper.iface.Mapper;
import com.rbkmoney.cashreg.utils.ProtoUtils;
import com.rbkmoney.damsel.cashreg.processing.Change;
import com.rbkmoney.damsel.cashreg.processing.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MgChangeManagerMapper {

    private final List<Mapper> changeMappers;

    public Receipt handle(Change change) {
        log.info("MgChangeManagerMapper - process. Change {}", change);
        return changeMappers.stream()
                .filter(mapper -> mapper.filter(change))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find mapper"))
                .map(change);
    }

    public Receipt process(List<Change> changes) {
        log.info("MgChangeManagerMapper - process. List Changes {}", changes);
        return changes.stream().map(this::handle).reduce(new Receipt(), ProtoUtils::mergeReceipts);
    }

}
