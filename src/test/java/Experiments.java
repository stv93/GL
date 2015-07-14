import org.junit.Assert;
import other.DateMatcher;

import java.time.LocalDateTime;

/**
 * Created by tetiana.sviatska on 7/14/2015.
 */
public class Experiments {

    public static void main(String[] args) {
        Assert.assertThat(LocalDateTime.MIN, DateMatcher.equals(LocalDateTime.now()));
    }

}
