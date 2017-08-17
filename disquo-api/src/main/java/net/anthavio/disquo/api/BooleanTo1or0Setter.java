package net.anthavio.disquo.api;

import net.anthavio.httl.HttlRequestBuilder;
import net.anthavio.httl.api.VarSetter;

public class BooleanTo1or0Setter implements VarSetter<Boolean> {

    @Override
    public void set(Boolean value, String name, HttlRequestBuilder<?> builder) {
        if (value != null) {
            if (value.booleanValue()) {
                builder.param(name, 1);
            } else {
                builder.param(name, 0);
            }
        }
    }
}