package com.backbase.dbs.payment.batch.parser.type;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.dbs.payment.batch.config.BatchConfiguration;
import com.backbase.dbs.payment.batch.model.BatchHeader;
import com.backbase.dbs.payment.batch.model.BatchPayment;
import com.backbase.dbs.payment.batch.model.BatchUploadHeader;
import com.backbase.dbs.payment.batch.parser.BatchParseCallback;
import com.backbase.dbs.payment.batch.parser.DigestBatchParser;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoremIpsumBatchParser extends DigestBatchParser {

    public static final String LOREM_IPSUM_TYPE = "LOREM_IPSUM";

    @Autowired
    public LoremIpsumBatchParser(BatchConfiguration batchConfiguration) {
        super(batchConfiguration);
    }

    public String getBatchUploadType() {
        return LOREM_IPSUM_TYPE;
    }

    public void parseBatches(InputStream inputStream, BatchParseCallback callback) {

        try (JsonParser parser = new JsonFactory().createParser(inputStream)) {
            callback.onBatchUpload(new BatchUploadHeader().batchFileType(LOREM_IPSUM_TYPE));
            BatchHeader batchHeader = new BatchHeader().currency("EUR").batchType("SEPACT");
            parser.nextToken();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                switch (parser.getCurrentName()) {
                    case "label":
                        batchHeader.batchName(parser.nextTextValue());
                        break;
                    case "priority":
                        // priority is non-standard field and will be stored in the additions map
                        batchHeader.additions(Collections.singletonMap("priority", parser.nextTextValue()));
                        break;
                    case "own-account":
                        batchHeader.originatorAccount(parser.nextTextValue());
                        break;
                    case "requested-execution-date":
                        batchHeader.requestedExecutionDate(
                            LocalDate.parse(parser.nextTextValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        break;
                    case "batch-total-amount":
                        batchHeader.batchTotalCreditAmount(new BigDecimal(parser.nextTextValue()));
                        break;
                    case "batch-payments-count":
                        batchHeader.batchPaymentsCount(Integer.valueOf(parser.nextTextValue()));
                        break;
                    case "payments":
                        callback.onBatch(batchHeader);
                        parser.nextToken();
                        while (parser.nextToken() != JsonToken.END_ARRAY) {
                            BatchPayment batchPayment = new BatchPayment().currency("EUR")
                                .location(formatJsonLocation(parser.getCurrentLocation()));
                            while (parser.nextToken() != JsonToken.END_OBJECT) {
                                switch (parser.getCurrentName()) {
                                    case "counterparty-name":
                                        batchPayment.counterpartyName(parser.nextTextValue());
                                        break;
                                    case "counterparty-account":
                                        batchPayment.counterpartyAccount(parser.nextTextValue());
                                        break;
                                    case "amount":
                                        batchPayment.amount(new BigDecimal(parser.nextTextValue()));
                                        break;
                                    default:
                                        throw new BadRequestException("Batch is not valid");
                                }
                            }
                            callback.onPaymentRecord(batchPayment);
                        }
                        callback.onBatchEnd();
                        break;
                    default:
                        throw new BadRequestException("Batch is not valid");
                }
            }
            callback.onBatchUploadEnd();
        } catch (Exception e) {
            callback.onBatchUploadError(new RuntimeException(e));
        }
    }

    private static String formatJsonLocation(JsonLocation jsonLocation) {
        return String.format("Line: %d, Column: %d", jsonLocation.getLineNr(), jsonLocation.getColumnNr());
    }
}