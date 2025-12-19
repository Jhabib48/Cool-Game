/*
    Checks if the fill strength is a prime number.
    Used by The Prime Directive ring to determine when to activate its 100% damage bonus.
*/
package Model.rings;
import Model.Fill;

public class PrimeCondition implements RingCondition {

    @Override
    public boolean isActivated(Fill fill) {
        ///  check if the num is a prime to activate
        if(fill == null) return false;
        return isPrime(fill.getStrength());
    }

    private boolean isPrime(int number) {
        boolean isPrime = number >= 2;
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}
