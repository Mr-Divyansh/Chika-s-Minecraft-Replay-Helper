/*
 * Decompiled with CFR 0.152.
 */
package com.moulberry.flashback;

public class FreezeSlowdownFormula {
    private static final double[] freezePowers = new double[11];

    public static double calculateFreezeClientTickDerivative(double serverTicks, int frozenDelay, double freezePowerBase) {
        double freezePowerSq = freezePowerBase * freezePowerBase;
        double freezePower4 = freezePowerSq * freezePowerSq;
        return -4.0 * Math.log(freezePowerBase) * Math.pow(freezePowerBase, 4.0 * serverTicks / (double)frozenDelay) / ((double)frozenDelay - (double)frozenDelay * freezePower4);
    }

    public static double calculateFreezeClientTick(double serverTicks, int frozenDelay, double freezePowerBase) {
        return (1.0 - Math.pow(freezePowerBase, 4.0 * serverTicks / (double)frozenDelay)) / (1.0 - Math.pow(freezePowerBase, 4.0));
    }

    public static double getFreezePowerBase(int freezeTicks, double targetDerivative) {
        double freezePowerBase;
        if (freezeTicks < 0) {
            freezeTicks = 0;
        }
        if (freezeTicks > 10) {
            freezeTicks = 10;
        }
        if ((freezePowerBase = freezePowers[freezeTicks]) == 0.0) {
            FreezeSlowdownFormula.freezePowers[freezeTicks] = freezePowerBase = FreezeSlowdownFormula.calculateFreezePower(freezeTicks, targetDerivative);
        }
        return freezePowerBase;
    }

    private static double calculateFreezePower(int freezeTicks, double targetDerivative) {
        double freezePowerBaseMin = 0.05;
        double freezePowerBaseMax = 0.95;
        if (FreezeSlowdownFormula.computeDerivativeAtZero(freezeTicks, freezePowerBaseMin) < targetDerivative) {
            return freezePowerBaseMin;
        }
        if (FreezeSlowdownFormula.computeDerivativeAtZero(freezeTicks, freezePowerBaseMax) > targetDerivative) {
            return freezePowerBaseMax;
        }
        double freezePowerBaseMid = (freezePowerBaseMin + freezePowerBaseMax) / 2.0;
        for (int i = 0; i < 100; ++i) {
            if (FreezeSlowdownFormula.computeDerivativeAtZero(freezeTicks, freezePowerBaseMid) > targetDerivative) {
                freezePowerBaseMin = freezePowerBaseMid;
            } else {
                freezePowerBaseMax = freezePowerBaseMid;
            }
            freezePowerBaseMid = (freezePowerBaseMin + freezePowerBaseMax) / 2.0;
        }
        return freezePowerBaseMid;
    }

    private static double computeDerivativeAtZero(int freezeTicks, double freezePower) {
        double freezePowerSq = freezePower * freezePower;
        double freezePower4 = freezePowerSq * freezePowerSq;
        return -4.0 * Math.log(freezePower) / ((double)freezeTicks - (double)freezeTicks * freezePower4);
    }
}

