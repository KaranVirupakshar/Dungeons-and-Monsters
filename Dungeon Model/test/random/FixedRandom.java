package random;

import java.util.Random;

/**
 * FixedRandom class represents the fulfillment of generating a stream of pseudorandom numbers
 * for the game. This class overrides the default Random class and returns 1 for both nextInt()
 * and nextInt(bound).
 */
public class FixedRandom extends Random {

  /**
   * A method to get a fixed defined number instead of a random number.
   *
   * @return 1 when a random number is required.
   */
  @Override
  public int nextInt() {
    return 0;
  }

  /**
   * A method to get a fixed defined number instead of a random number.
   *
   * @return 1 when a random number in the range up to bound is required.
   */
  @Override
  public int nextInt(int bound) {
    return 0;
  }
}
