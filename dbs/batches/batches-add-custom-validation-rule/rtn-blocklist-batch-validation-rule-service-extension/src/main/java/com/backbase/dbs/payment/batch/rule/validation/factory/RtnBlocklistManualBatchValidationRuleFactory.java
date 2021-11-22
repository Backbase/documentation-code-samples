package com.backbase.dbs.payment.batch.rule.validation.factory;

import com.backbase.dbs.payment.batch.config.ManualBatchRuleInformation;
import com.backbase.dbs.payment.batch.rule.factory.AbstractSpelBatchValidationRuleFactory;
import com.backbase.dbs.payment.batch.rule.validation.ManualBatchValidationRule;
import com.backbase.dbs.payment.batch.rule.validation.RtnBlocklistManualBatchValidationRule;
import com.backbase.dbs.payment.batch.service.RtnBlocklistValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RtnBlocklistManualBatchValidationRuleFactory extends AbstractSpelBatchValidationRuleFactory
    implements ManualBatchValidationRuleFactory {

    private final RtnBlocklistValidator rtnBlocklistValidator;

    @Override
    public String getType() {
        return "rtn-blocklist-validation";
    }

    @Override
    public ManualBatchValidationRule buildRule(ManualBatchRuleInformation information) {
        return new RtnBlocklistManualBatchValidationRule(information,
            createEvaluationContext(),
            createConditionExpression(information),
            createErrorContextExpressions(information.getConfiguration(), information.getName()),
            rtnBlocklistValidator);
    }
}
