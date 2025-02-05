package br.com.tmvolpato.blockchain.application.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ConvertWeiOrEtherUtil {

    /**
     * 10^18
     */
    private static final BigDecimal WEI_IN_ETHER = new BigDecimal("1000000000000000000");

    private ConvertWeiOrEtherUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static BigInteger toWei(BigDecimal etherValue) {
        return etherValue.multiply(WEI_IN_ETHER).toBigInteger();
    }

    public static BigDecimal toEther(BigInteger weiValue) {
        return new BigDecimal(weiValue).divide(WEI_IN_ETHER);
    }
}
