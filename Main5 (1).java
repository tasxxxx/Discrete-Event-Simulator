import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Supplier;
import cs2030.util.Pair;
import cs2030.simulator.Simulate5;

class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Pair<Double, Supplier<Double>>> inputTimes;

        int numOfServers = sc.nextInt();
        int numOfCustomers = sc.nextInt();

        inputTimes = Stream.<Pair<Double, Supplier<Double>>>generate(() -> 
                Pair.of(sc.nextDouble(), () -> sc.nextDouble()))
            .limit(numOfCustomers)
            .collect(Collectors.toUnmodifiableList());

        Simulate5 sim = new Simulate5(numOfServers, inputTimes);
        System.out.println(sim.run());
    }
}
