package pwr.api.enums;

import pwr.api.entity.upgrade.*;

public enum FieldName {
    GLUON_COMPUTER()
            {
                @Override
                public Field createInstance()
                {
                    return new Computer1();
                }
            },
    POSITRON_COMPUTER()
            {
                @Override
                public Field createInstance()
                {
                    return new Computer2();
                }
            },
    ELECTRON_COMPUTER()
            {
                @Override
                public Field createInstance()
                {
                    return new Computer3();
                }
            },
    PHASE_SHIELD()
            {
                @Override
                public Field createInstance()
                {
                    return new Shield1();
                }
            },
    GAUSS_SHIELD()
            {
                @Override
                public Field createInstance()
                {
                    return new Shield2();
                }
            },
    ION_CANNON()
            {
                @Override
                public Field createInstance()
                {
                    return new Cannon1();
                }
            },
    PLASMA_CANNON()
            {
                @Override
                public Field createInstance()
                {
                    return new Cannon2();
                }
            },
    SOLITON_CANNON()
            {
                @Override
                public Field createInstance()
                {
                    return new Cannon3();
                }
            },
    ANTIMATTER_CANNON()
            {
                @Override
                public Field createInstance()
                {
                    return new Cannon4();
                }
            },
    FLUX_MISSILE()
            {
                @Override
                public Field createInstance()
                {
                    return new Missile1();
                }
            },
    PLASMA_MISSILE()
            {
                @Override
                public Field createInstance()
                {
                    return new Missile2();
                }
            },
    HULL()
            {
                @Override
                public Field createInstance()
                {
                    return new Hull1();
                }
            },
    IMPROVED_HULL()
            {
                @Override
                public Field createInstance()
                {
                    return new Hull2();
                }
            },
    CONIFOLD_FIELD()
            {
                @Override
                public Field createInstance()
                {
                    return new Hull3();
                }
            },
    NUCLEAR_DRIVE()
            {
                @Override
                public Field createInstance()
                {
                    return new Drive1();
                }
            },
    FUSION_DRIVE()
            {
                @Override
                public Field createInstance()
                {
                    return new Drive2();
                }
            },
    TACHYON_DRIVE()
            {
                @Override
                public Field createInstance()
                {
                    return new Drive3();
                }
            },

    ENERGY_SOURCE_1()
            {
                @Override
                public Field createInstance()
                {
                    return new EnergySource1();
                }
            },

    ENERGY_SOURCE_2()
            {
                @Override
                public Field createInstance()
                {
                    return new EnergySource1();
                }
            },

    ENERGY_SOURCE_3()
            {
                @Override
                public Field createInstance()
                {
                    return new EnergySource1();
                }
            };

    public abstract Field createInstance();
}
