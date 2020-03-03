import java.util.HashMap;
import java.util.Map;

public class EqualityAndHashCode {

    static class Currency {

        String currencyCode;

        Currency(String currencyCode) {
            this.currencyCode = currencyCode;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Currency) {
                Currency currency = (Currency) obj;
                return currency.currencyCode.equals(this.currencyCode);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return currencyCode.hashCode();
        }
    }


    public static void main(String[] args) {

        Currency currency1 = new Currency("USD");

        Currency currency2 = new Currency("USD");

        System.out.println("Currency1: " + currency1 + ", hashCode: " + currency1.hashCode());
        System.out.println("Currency2: " + currency2 + ", hashCode: " + currency2.hashCode());

        System.out.println("Currency1 equals Currency2: " + currency1.equals(currency2));
        // 🔥 This never returns TRUE even when both equals method and hashcode is overridden to be same
        // Only when both objects are same
        System.out.println("Currency1 == Currency2: " + (currency1 == currency2));


        Map<Currency, Double> rates = new HashMap<>();

        rates.put(currency1, 1.1);

        // 🔥🔥🔥Hash Map KEYS should both meet the conditions of EQUALS and same HASH CODE
        /*
            HashMap uses hashCode(), == and equals() for entry lookup.
            The lookup sequence for a given key k is as follows:

            Use k.hashCode() to determine which bucket the entry is stored, if any
            If found, for each entry's key k1 in that bucket, if k == k1 || k.equals(k1), then return k1's entry
            Any other outcomes, no corresponding entry
         */
        Double result = rates.get(currency2);

        System.out.println("Result: " + result);


    }
}
