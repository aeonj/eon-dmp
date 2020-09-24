package eon.hg.fap.core;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Date;

public class TestSpel {

    @Test
    public void testSpel() {
        String ele_code="ele_code";
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp =parser.parseExpression("#ele_code == 'ele_code'");
        System.out.println(exp.getValue());
        System.out.println(new Date().getTime());

    }
}
