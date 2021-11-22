package com.backbase.dbs.payment.batch.rule.validation;

import static com.backbase.dbs.payment.batch.rule.BatchFacts.FACT_BATCH_PAYMENT;

import com.backbase.dbs.payment.batch.config.ManualBatchRuleInformation;
import com.backbase.dbs.payment.batch.model.engine.manual.BatchPaymentFact;
import com.backbase.dbs.payment.batch.rule.AbstractManualBatchValidationRule;
import com.backbase.dbs.payment.batch.service.RtnBlocklistValidator;
import java.util.Map;
import org.jeasy.rules.api.Facts;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

public class RtnBlocklistManualBatchValidationRule extends AbstractManualBatchValidationRule {

    private final RtnBlocklistValidator rtnBlocklistValidator;

    public RtnBlocklistManualBatchValidationRule(ManualBatchRuleInformation information,
        EvaluationContext evaluationContext,
        Expression conditionExpression,
        Map<String, Expression> errorContextExpressions, RtnBlocklistValidator rtnBlocklistValidator) {
        super(information, evaluationContext, conditionExpression, errorContextExpressions);
        this.rtnBlocklistValidator = rtnBlocklistValidator;
    }

    @Override
    public boolean isValid(Facts facts) {
        BatchPaymentFact batchPayment = facts.get(FACT_BATCH_PAYMENT);

        Map<String, String> ruleConfiguration = getManualBatchRuleInformation().getConfiguration();

        String rtnBlocklistUrl = ruleConfiguration.get("rtn-blocklist-url");

        return rtnBlocklistValidator.isValid(rtnBlocklistUrl, batchPayment.getCounterpartyBankBranchCode());
    }

}
