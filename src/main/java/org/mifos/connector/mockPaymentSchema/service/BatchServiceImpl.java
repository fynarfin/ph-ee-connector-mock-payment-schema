package org.mifos.connector.mockPaymentSchema.service;

import org.mifos.connector.mockPaymentSchema.schema.BatchDTO;
import org.mifos.connector.mockPaymentSchema.schema.BatchDetailResponse;
import org.mifos.connector.mockPaymentSchema.schema.Transfer;
import org.mifos.connector.mockPaymentSchema.schema.TransferStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BatchServiceImpl implements BatchService {

    private List<String> requestIds;

    private List<String> workflowInstanceKeys;

    private String payerPartyId = "835322416";

    private String payeePartyId = "27713803912";

    private int successTxnCount = 9;



    public BatchServiceImpl(){
        requestIds = new ArrayList<>(Arrays.asList("f1e22fe3-9740-4fba-97b6-78f43bfa7f2f",
                "39f6ac4d052e-72aa3ea4-e6f6-4880-877f", "a27631f6-6dd4-4d69-b4fc-8932bd721913",
                "3d21e6ea-c583-44ed-b94f-af909fa7616e", "15f9a0b0-2299-436d-8433-da564140ba66",
                "f1e22fe3-9740-4fba-97b6-78f49bfa7f2f", "39f6ac4d052e-72aa3ea4-e6f6-4380-877f",
                "a27631f6-6dd4-4d69-b4fc-8452bd721913", "3d21e6ea-c583-44ed-b94f-af909fu7616e",
                "15f9a0b0-2299-436d-8433-da667140ba66"));

        workflowInstanceKeys = new ArrayList<>(Arrays.asList("1257787875372524", "3265012419947587", "9816176217118279",
                "7686876624590145", "7089769829361117", "6120480408758686", "6313003282228195", "6310718206541276",
                "3936102393207027", "6460724468126500"));
    }

    @Override
    public BatchDTO getBatchSummary(String batchId) {
        Random random = new Random();
        int randomValue = random.nextInt(2) + 1;

        if(randomValue==1){
            return successfulBatchSummaryResponse(batchId);
        }

        return failureBatchSummaryResponse(batchId);
    }

    @Override
    public BatchDetailResponse getBatchDetails(String batchId, int pageNo, int pageSize) {


        List<Transfer> transactions = getTransactions(batchId);
        int toIndex = pageNo * pageSize;
        int fromIndex = toIndex - pageSize;
        toIndex = Math.min(toIndex, transactions.size());
        BatchDetailResponse batchDetailResponse = new BatchDetailResponse();
        batchDetailResponse.setContent(transactions.subList(fromIndex, toIndex));
        return batchDetailResponse;
    }

    private BatchDTO failureBatchSummaryResponse(String batchId) {
        BatchDTO batchDTO = new BatchDTO();

        batchDTO.setBatch_id(batchId);
        batchDTO.setRequest_id(null);
        batchDTO.setTotal(10L);
        batchDTO.setOngoing(7L);
        batchDTO.setFailed(0L);
        batchDTO.setSuccessful(3L);
        batchDTO.setTotalAmount(BigDecimal.valueOf(100));
        batchDTO.setSuccessfulAmount(BigDecimal.valueOf(30));
        batchDTO.setPendingAmount(BigDecimal.valueOf(10));
        batchDTO.setFailedAmount(BigDecimal.valueOf(60));
        batchDTO.setFile(null);
        batchDTO.setNotes("");
        batchDTO.setCreated_at(null);
        batchDTO.setStatus("Pending");
        batchDTO.setModes("");
        batchDTO.setPurpose("unknown purpose");
        batchDTO.setFailPercentage("0");
        batchDTO.setSuccessPercentage("30");

        return batchDTO;
    }

    private BatchDTO successfulBatchSummaryResponse(String batchId) {
        BatchDTO batchDTO = new BatchDTO();

        batchDTO.setBatch_id(batchId);
        batchDTO.setRequest_id(null);
        batchDTO.setTotal(10L);
        batchDTO.setOngoing(0L);
        batchDTO.setFailed(1L);
        batchDTO.setSuccessful(9L);
        batchDTO.setTotalAmount(BigDecimal.valueOf(100));
        batchDTO.setSuccessfulAmount(BigDecimal.valueOf(90));
        batchDTO.setPendingAmount(BigDecimal.valueOf(10));
        batchDTO.setFailedAmount(BigDecimal.ZERO);
        batchDTO.setFile(null);
        batchDTO.setNotes("");
        batchDTO.setCreated_at(null);
        batchDTO.setStatus("Pending");
        batchDTO.setModes("");
        batchDTO.setPurpose("unknown purpose");
        batchDTO.setFailPercentage("10");
        batchDTO.setSuccessPercentage("90");

        return batchDTO;
    }

    private List<Transfer> getTransactions(String batchId){
        List<Transfer> transactionList = new ArrayList<>();

        for(int i=0; i<10; i++){
            Transfer transfer;
            if(successTxnCount>0){
                transfer = getSingleTransaction(i, batchId, requestIds.get(i), workflowInstanceKeys.get(i), TransferStatus.COMPLETED);
            }
            else{
                transfer = getSingleTransaction(i, batchId, requestIds.get(i), workflowInstanceKeys.get(i), TransferStatus.IN_PROGRESS);
            }

            transactionList.add(transfer);
        }
        return transactionList;
    }

    private Transfer getSingleTransaction(int iteration, String batchId, String requestId, String workflowInstanceKey,
                                          TransferStatus status){
        Transfer transfer = new Transfer();

        transfer.setId(String.valueOf(iteration));
        transfer.setWorkflowInstanceKey(Long.parseLong(workflowInstanceKey));
        transfer.setTransactionId(requestId);
        transfer.setStartedAt(new Date(1685536200000L));
        transfer.setCompletedAt(new Date(1685536268000L));
        transfer.setStatus(status);
        transfer.setStatusDetail(null);
        transfer.setPayerDfspId(null);
        transfer.setPayerPartyId(payerPartyId);
        transfer.setPayerPartyIdType("MSISDN");
        transfer.setPayeeDfspId(null);
        transfer.setPayeePartyId(payeePartyId);
        transfer.setPayeePartyIdType("MSISDN");
        transfer.setPayeeFee(null);
        transfer.setPayeeFeeCurrency(null);
        transfer.setPayeeQuoteCode(null);
        transfer.setPayerFee(null);
        transfer.setPayerFeeCurrency(null);
        transfer.setPayerQuoteCode(null);
        transfer.setAmount(BigDecimal.valueOf(10));
        transfer.setCurrency("USD");
        transfer.setDirection("OUTGOING");
        transfer.setErrorInformation(null);
        transfer.setBatchId(batchId);

        return transfer;
    }
}
