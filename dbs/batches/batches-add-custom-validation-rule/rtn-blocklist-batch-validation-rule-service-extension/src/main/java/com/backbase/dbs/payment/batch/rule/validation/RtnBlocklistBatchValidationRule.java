package com.backbase.dbs.payment.batch.rule.validation;

import static com.backbase.dbs.payment.batch.rule.BatchFacts.FACT_BATCH_PAYMENT;

import com.backbase.dbs.payment.batch.config.BatchRuleInformation;
import com.backbase.dbs.payment.batch.model.BatchPayment;
import com.backbase.dbs.payment.batch.rule.AbstractBatchValidationRule;
import com.backbase.dbs.payment.batch.service.RtnBlocklistValidator;
import java.util.ArrayList;
import java.util.Map;
import org.jeasy.rules.api.Facts;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

public class RtnBlocklistBatchValidationRule extends AbstractBatchValidationRule {

    private final RtnBlocklistValidator rtnBlocklistValidator;

    public static class RtnBlocklist extends ArrayList<String> {

    }

    public RtnBlocklistBatchValidationRule(
        BatchRuleInformation batchRuleInformation,
        Map<String, Expression> errorContextExpressions,
        EvaluationContext evaluationContext,
        Expression conditionExpression, RtnBlocklistValidator rtnBlocklistValidator) {
        super(batchRuleInformation, errorContextExpressions, evaluationContext, conditionExpression);
        this.rtnBlocklistValidator = rtnBlocklistValidator;
    }

    @Override
    public boolean isValid(Facts facts) {
        BatchPayment batchPayment = facts.get(FACT_BATCH_PAYMENT);

        Map<String, String> ruleConfiguration = getBatchRuleInformation().getConfiguration();

        String rtnBlocklistUrl = ruleConfiguration.get("rtn-blocklist-url");

        return rtnBlocklistValidator.isValid(rtnBlocklistUrl, batchPayment.getCounterpartyBankBranchCode());
    }

}
