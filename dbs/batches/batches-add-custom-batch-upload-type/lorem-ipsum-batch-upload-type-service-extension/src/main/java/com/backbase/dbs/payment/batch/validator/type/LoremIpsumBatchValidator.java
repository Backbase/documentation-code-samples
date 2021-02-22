
package com.backbase.dbs.payment.batch.validator.type;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.dbs.payment.batch.model.BatchHeader;
import com.backbase.dbs.payment.batch.model.BatchPayment;
import com.backbase.dbs.payment.batch.model.BatchUploadHeader;
import com.backbase.dbs.payment.batch.model.BatchUploadReport;
import com.backbase.dbs.payment.batch.parser.type.LoremIpsumBatchParser;
import com.backbase.dbs.payment.batch.service.ErrorFactory;
import com.backbase.dbs.payment.batch.validator.BatchTypeValidator;
import com.backbase.dbs.paymentorder.spec.v2.batchuploads.FileDto;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LoremIpsumBatchValidator implements BatchTypeValidator {

    private static final String[] BATCH_FILE_TYPES = {LoremIpsumBatchParser.LOREM_IPSUM_TYPE};

    private static final String[] BATCH_TYPES = {"SEPACT"};

    private static final Map<String, List<String>> BATCH_TYPE_FILE_EXTENSIONS =
        Collections.singletonMap(LoremIpsumBatchParser.LOREM_IPSUM_TYPE, Collections.singletonList("json"));

    public Map<String, List<String>> getSupportedBatchTypesExtensions() {
        return BATCH_TYPE_FILE_EXTENSIONS;
    }

    public String[] getSupportedBatchUploadTypes() {
        return BATCH_FILE_TYPES;
    }

    public String[] getSupportedBatchTypes() {
        return BATCH_TYPES;
    }

    public void validate(FileDto fileDto, BatchUploadReport batchUploadReport) {
        // add file validation logic here
    }

    public void validate(BatchUploadHeader batchUploadHeader, BatchUploadReport batchUploadReport) {
        // add batch upload pre-validation logic here
    }

    public void validate(BatchHeader batchHeader, BatchUploadReport batchUploadReport) {
        // add batch order pre-validation logic here

        if (batchHeader.getBatchTotalCreditAmount().compareTo(BigDecimal.ZERO) <= 0) {
            // We do not propagate batch order location because the upload always has one batch order
            batchUploadReport.addError(
                ErrorFactory.createAmountIsNotPositiveError("batch-total-amount",
                    batchHeader.getBatchTotalCreditAmount()));
        }
    }

    public void validate(BatchPayment batchPayment, BatchUploadReport batchUploadReport) {
        // add batch payment validation logic here

        if (batchPayment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            // We do propagate batch payment location to be able to easily locate the problematic payment
            batchUploadReport.addError(withLocation(
                ErrorFactory.createAmountIsNotPositiveError("amount", batchPayment.getAmount()),
                batchPayment.getLocation()));
        }
    }

    public void validateAfter(BatchHeader batchHeader, BatchUploadReport batchUploadReport) {
        // add batch order post-validation logic here

        // We check for error in this after-hook only to be able to collect several validation problems across the file
        // It is possible to check for errors in the pre-hook to have fail-fast behavior
        checkForErrors(batchUploadReport);
    }

    public void validateAfter(BatchUploadHeader batchUploadHeader, BatchUploadReport batchUploadReport) {
        // add batch upload post-validation logic here
    }

    private static Error withLocation(Error error, String location) {
        error.getContext().put("location", location);
        return error;
    }

    private static void checkForErrors(BatchUploadReport report) {
        if (report.hasErrors()) {
            throw new BadRequestException().withMessage("Batch is not valid").withErrors(report.getErrors());
        }
    }
}