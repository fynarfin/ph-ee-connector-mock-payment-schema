package org.mifos.connector.mockPaymentSchema.api;

import org.mifos.connector.mockPaymentSchema.schema.BatchDTO;
import org.mifos.connector.mockPaymentSchema.schema.BatchDetailResponse;
import org.mifos.connector.mockPaymentSchema.schema.Transfer;
import org.mifos.connector.mockPaymentSchema.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mockapi/v1")
public class BatchApi {

    @Autowired
    private BatchService batchService;

    @GetMapping("/batch/summary")
    public BatchDTO batchSummary(@RequestParam(value = "batchId", required = false) String batchId){
        return batchService.getBatchSummary(batchId);
    }

    @GetMapping("/batch/detail")
    public BatchDetailResponse batchDetail(@RequestParam(value = "batchId", required = false) String batchId,
                                           @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return batchService.getBatchDetails(batchId, pageNo, pageSize);
    }

}
