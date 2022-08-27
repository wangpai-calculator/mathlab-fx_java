package org.wangpai.mathlab.exp.model;

/**
 * @since 2022-8-24
 */
@FunctionalInterface
public interface Action {
    void callback(Object data);
}
