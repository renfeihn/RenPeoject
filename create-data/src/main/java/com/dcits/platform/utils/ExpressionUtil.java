package com.dcits.platform.utils;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionUtil {
    public ExpressionUtil() {
    }

    public static <T> T getObject(ExpressionParser parser, StandardEvaluationContext context, String expr, Class<T> t) {
        try {
            return parser.parseExpression(expr).getValue(context, t);
        } catch (Exception var5) {
            System.out.println("表达式解析失败：" + expr);
            throw new RuntimeException(var5);
        }
    }

    public static boolean getBoolObject(ExpressionParser parser, StandardEvaluationContext context, String expr) {
        return ((Boolean)getObject(parser, context, expr, Boolean.TYPE)).booleanValue();
    }

    public static Object getObject(ExpressionParser parser, StandardEvaluationContext context, String expr) {
        try {
            return parser.parseExpression(expr).getValue(context);
        } catch (Exception var4) {
            System.out.println("表达式解析失败：" + expr);
            throw new RuntimeException(var4);
        }
    }
}
