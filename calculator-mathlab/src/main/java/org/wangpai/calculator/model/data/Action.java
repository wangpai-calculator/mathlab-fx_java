package org.wangpai.calculator.model.data;

/**
 * @since 2022-8-24
 */
@FunctionalInterface
public interface Action {
    void callback(Object data);
}
