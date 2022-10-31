package pwr.api.enums;

import pwr.api.entity.ship.*;

public enum ShipType {
    INTERCEPTOR()
            {
                @Override
                public Ship createInstance()
                {
                    return new Interceptor();
                }
            },
    CRUISER()
            {
                @Override
                public Ship createInstance()
                {
                    return new Cruiser();
                }
            },
    DREADNOUGHT()
            {
                @Override
                public Ship createInstance()
                {
                    return new Dreadnought();
                }
            };

    public abstract Ship createInstance();
}
