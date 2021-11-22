package com.backbase.dbs.payment.batch.rule.validation.factory;

import com.backbase.dbs.payment.batch.config.BatchRuleInformation;
import com.backbase.dbs.payment.batch.rule.factory.AbstractSpelBatchValidationRuleFactory;
import com.backbase.dbs.payment.batch.rule.validation.BatchValidationRule;
import com.backbase.dbs.payment.batch.rule.validation.RtnBlocklistBatchValidationRule;
import com.backbase.dbs.payment.batch.service.RtnBlocklistValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RtnBlocklistBatchValidationRuleFactory extends AbstractSpelBatchValidationRuleFactory
    implements BatchValidationRuleFactory {

    private final RtnBlocklistValidator rtnBlocklistValidator;

    @Override
    public String getType() {
        return "rtn-blocklist-validation";
    }

    @Override
    public BatchValidationRule buildRule(BatchRuleInformation information) {
        return new RtnBlocklistBatchValidationRule(information,
            createErrorContextExpressions(information.getConfiguration(), information.getName()),
            createEvaluationContext(),
            createConditionExpression(information), rtnBlocklistValidator);
    }
}
